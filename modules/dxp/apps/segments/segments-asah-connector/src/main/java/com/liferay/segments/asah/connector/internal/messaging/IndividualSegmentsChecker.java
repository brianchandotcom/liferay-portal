/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.messaging;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.cache.AsahSegmentsEntryCache;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.connector.internal.client.model.Individual;
import com.liferay.segments.asah.connector.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.util.OrderByField;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = IndividualSegmentsChecker.class)
public class IndividualSegmentsChecker {

	public void checkIndividualSegments() throws Exception {
		_checkIndividualSegments();
		_checkIndividualSegmentsMemberships();
	}

	@Activate
	protected void activate() {
		_asahFaroBackendClient = new AsahFaroBackendClientImpl(
			_analyticsSettingsManager, _http);
	}

	@Deactivate
	protected void deactivate() {
		_asahFaroBackendClient = null;
	}

	private void _addSegmentsEntry(
		long companyId, IndividualSegment individualSegment) {

		try {
			ServiceContext serviceContext = _getServiceContext(companyId);

			SegmentsEntry segmentsEntry =
				_segmentsEntryLocalService.fetchSegmentsEntry(
					serviceContext.getScopeGroupId(), individualSegment.getId(),
					true);

			Map<Locale, String> nameMap = Collections.singletonMap(
				_portal.getSiteDefaultLocale(serviceContext.getScopeGroupId()),
				individualSegment.getName());

			if (segmentsEntry == null) {
				_segmentsEntryLocalService.addSegmentsEntry(
					individualSegment.getId(), nameMap, Collections.emptyMap(),
					true, null, SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND,
					User.class.getName(), serviceContext);

				return;
			}

			_segmentsEntryLocalService.updateSegmentsEntry(
				segmentsEntry.getSegmentsEntryId(), individualSegment.getId(),
				nameMap, null, true, null, serviceContext);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to process individual segment " +
					individualSegment.getId(),
				portalException);
		}
	}

	private void _addSegmentsEntryRels(
		SegmentsEntry segmentsEntry, Set<Long> userIds) {

		try {
			_segmentsEntryLocalService.addSegmentsEntryClassPKs(
				segmentsEntry.getSegmentsEntryId(),
				ArrayUtil.toLongArray(userIds),
				_getServiceContext(segmentsEntry.getCompanyId()));
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to process individuals " + userIds, portalException);
		}
	}

	private void _checkIndividualSegmentMemberships(SegmentsEntry segmentsEntry)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				segmentsEntry.getCompanyId());

		_segmentsEntryRelLocalService.deleteSegmentsEntryRels(
			segmentsEntry.getSegmentsEntryId());

		Results<Individual> individualResults;

		try {
			individualResults = _asahFaroBackendClient.getIndividualResults(
				segmentsEntry.getCompanyId(),
				segmentsEntry.getSegmentsEntryKey(), 1, _DELTA,
				Collections.singletonList(OrderByField.desc("dateModified")));

			int totalElements = individualResults.getTotal();

			if (_log.isDebugEnabled()) {
				_log.debug(
					totalElements +
						" individuals found for individual segment " +
							segmentsEntry.getSegmentsEntryKey());
			}

			if (totalElements == 0) {
				return;
			}

			Set<Long> userIds = new HashSet<>();

			int totalPages = (int)Math.ceil((double)totalElements / _DELTA);

			int curPage = 1;

			while (true) {
				List<Individual> individuals = individualResults.getItems();

				individuals.forEach(
					individual -> {
						Long userId = _getUserId(
							segmentsEntry.getCompanyId(),
							analyticsConfiguration.
								liferayAnalyticsDataSourceId(),
							individual);

						if (userId != null) {
							userIds.add(userId);
						}
						else {
							List<String> individualSegmentIds =
								individual.getIndividualSegmentIds();

							if (ListUtil.isNotEmpty(individualSegmentIds)) {
								String individualPK = _getIndividualPK(
									analyticsConfiguration.
										liferayAnalyticsDataSourceId(),
									individual);

								if (individualPK != null) {
									try {
										_putSegmentsEntryIdsCache(
											segmentsEntry.getCompanyId(),
											individualPK, individualSegmentIds);
									}
									catch (PortalException portalException) {
										_log.error(
											"Unable to cache segments entry " +
												"IDs for user ID " + userId,
											portalException);
									}
								}
							}
						}
					});

				curPage++;

				if (curPage > totalPages) {
					break;
				}

				individualResults = _asahFaroBackendClient.getIndividualResults(
					segmentsEntry.getCompanyId(),
					segmentsEntry.getSegmentsEntryKey(), curPage, _DELTA,
					Collections.singletonList(
						OrderByField.desc("dateModified")));
			}

			if (!userIds.isEmpty()) {
				_addSegmentsEntryRels(segmentsEntry, userIds);
			}
		}
		catch (RuntimeException runtimeException) {
			_log.error(
				"Unable to retrieve individuals for individual segment " +
					segmentsEntry.getSegmentsEntryKey(),
				runtimeException);
		}
	}

	private void _checkIndividualSegments() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				if (_analyticsSettingsManager.isAnalyticsEnabled(companyId)) {
					_checkIndividualSegments(companyId);
				}
			});
	}

	private void _checkIndividualSegments(long companyId) {
		Results<IndividualSegment> individualSegmentResults = new Results<>();

		try {
			individualSegmentResults =
				_asahFaroBackendClient.getIndividualSegmentResults(
					companyId, 1, _DELTA,
					Collections.singletonList(
						OrderByField.desc("dateModified")));
		}
		catch (RuntimeException runtimeException) {
			_log.error(
				"Unable to retrieve individual segments", runtimeException);

			return;
		}

		int totalElements = individualSegmentResults.getTotal();

		if (_log.isDebugEnabled()) {
			_log.debug(totalElements + " active individual segments found");
		}

		if (totalElements == 0) {
			return;
		}

		List<IndividualSegment> individualSegments =
			individualSegmentResults.getItems();

		individualSegments.forEach(
			individualSegment -> _addSegmentsEntry(
				companyId, individualSegment));
	}

	private void _checkIndividualSegmentsMemberships() throws Exception {
		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryLocalService.getSegmentsEntriesBySource(
				SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			_checkIndividualSegmentMemberships(segmentsEntry);
		}
	}

	private String _getIndividualPK(
		String dataSourceId, Individual individual) {

		for (Individual.DataSourceIndividualPK dataSourceIndividualPK :
				individual.getDataSourceIndividualPKs()) {

			if (Objects.equals(
					dataSourceId, dataSourceIndividualPK.getDataSourceId())) {

				for (String individualPK :
						dataSourceIndividualPK.getIndividualPKs()) {

					return individualPK;
				}
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Unable to find a user corresponding to individual " +
					individual.getId());
		}

		return null;
	}

	private ServiceContext _getServiceContext(long companyId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		Company company = _companyLocalService.getCompany(companyId);

		serviceContext.setScopeGroupId(company.getGroupId());

		User user = company.getGuestUser();

		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private Long _getUserId(
		long companyId, String dataSourceId, Individual individual) {

		for (Individual.DataSourceIndividualPK dataSourceIndividualPK :
				individual.getDataSourceIndividualPKs()) {

			if (Objects.equals(
					dataSourceId, dataSourceIndividualPK.getDataSourceId())) {

				for (String individualUuid :
						dataSourceIndividualPK.getIndividualPKs()) {

					User user = _userLocalService.fetchUserByUuidAndCompanyId(
						individualUuid, companyId);

					if (user != null) {
						return user.getUserId();
					}
				}

				break;
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Unable to find a user corresponding to individual " +
					individual.getId());
		}

		return null;
	}

	private void _putSegmentsEntryIdsCache(
			long companyId, String userId, List<String> individualSegmentIds)
		throws PortalException {

		ServiceContext serviceContext = _getServiceContext(companyId);

		List<Long> segmentsEntryIds = TransformUtil.transform(
			individualSegmentIds,
			individualSegmentId -> {
				SegmentsEntry curSegmentsEntry =
					_segmentsEntryLocalService.fetchSegmentsEntry(
						serviceContext.getScopeGroupId(), individualSegmentId,
						true);

				if (curSegmentsEntry != null) {
					return curSegmentsEntry.getSegmentsEntryId();
				}

				return null;
			});

		_asahSegmentsEntryCache.putSegmentsEntryIds(
			userId, ArrayUtil.toLongArray(segmentsEntryIds));
	}

	private static final int _DELTA = 100;

	private static final Log _log = LogFactoryUtil.getLog(
		IndividualSegmentsChecker.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	private volatile AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private AsahSegmentsEntryCache _asahSegmentsEntryCache;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Http _http;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	@Reference
	private UserLocalService _userLocalService;

}