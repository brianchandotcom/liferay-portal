/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.messaging;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserModel;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.cache.AsahSegmentsEntryCache;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.connector.internal.client.JSONWebServiceClient;
import com.liferay.segments.asah.connector.internal.client.model.Individual;
import com.liferay.segments.asah.connector.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.util.OrderByField;
import com.liferay.segments.asah.connector.internal.util.AsahUtil;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryModel;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = IndividualSegmentsChecker.class)
public class IndividualSegmentsChecker {

	public void checkIndividualSegments() {
		_checkIndividualSegments();
		_checkIndividualSegmentsMemberships();
	}

	public void checkIndividualSegments(long companyId, String individualPK)
		throws PortalException {

		if ((_asahSegmentsEntryCache.getSegmentsEntryIds(individualPK) !=
				null) ||
			!AsahUtil.isAnalyticsEnabled(companyId)) {

			return;
		}

		Individual individual = _asahFaroBackendClient.getIndividual(
			companyId, individualPK);

		if (individual == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get individual " + individualPK);
			}

			return;
		}

		List<String> individualSegmentIds =
			individual.getIndividualSegmentIds();

		if (ListUtil.isEmpty(individualSegmentIds)) {
			return;
		}

		ServiceContext serviceContext = _getServiceContext(companyId);

		Stream<String> stream = individualSegmentIds.stream();

		_asahSegmentsEntryCache.putSegmentsEntryIds(
			individualPK,
			stream.map(
				segmentsEntryKey ->
					_segmentsEntryLocalService.fetchSegmentsEntry(
						serviceContext.getScopeGroupId(), segmentsEntryKey,
						true)
			).filter(
				Objects::nonNull
			).mapToLong(
				SegmentsEntryModel::getSegmentsEntryId
			).toArray());
	}

	@Activate
	protected void activate() {
		_asahFaroBackendClient = new AsahFaroBackendClientImpl(
			_jsonWebServiceClient);
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
				ArrayUtil.toArray(userIds.toArray(new Long[0])),
				_getServiceContext(segmentsEntry.getCompanyId()));
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to process individuals " + userIds, portalException);
		}
	}

	private void _checkIndividualSegmentMemberships(
		SegmentsEntry segmentsEntry) {

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
						Optional<Long> userIdOptional = _getUserIdOptional(
							segmentsEntry.getCompanyId(), individual);

						userIdOptional.ifPresent(userIds::add);
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

	private void _checkIndividualSegments() {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				if (AsahUtil.isAnalyticsEnabled(companyId)) {
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

	private void _checkIndividualSegmentsMemberships() {
		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryLocalService.getSegmentsEntriesBySource(
				SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			_checkIndividualSegmentMemberships(segmentsEntry);
		}
	}

	private ServiceContext _getServiceContext(long companyId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		Company company = _companyLocalService.getCompany(companyId);

		serviceContext.setScopeGroupId(company.getGroupId());

		User user = company.getDefaultUser();

		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private Optional<Long> _getUserIdOptional(
		long companyId, Individual individual) {

		Optional<Long> userIdOptional = Optional.empty();

		List<Individual.DataSourceIndividualPK> dataSourceIndividualPKs =
			individual.getDataSourceIndividualPKs();

		Stream<Individual.DataSourceIndividualPK>
			dataSourceIndividualPKsStream = dataSourceIndividualPKs.stream();

		List<String> individualUuids = dataSourceIndividualPKsStream.filter(
			dataSourceIndividualPK -> Objects.equals(
				_asahFaroBackendClient.getDataSourceId(companyId),
				dataSourceIndividualPK.getDataSourceId())
		).findFirst(
		).map(
			Individual.DataSourceIndividualPK::getIndividualPKs
		).orElse(
			Collections.emptyList()
		);

		if (!ListUtil.isEmpty(individualUuids)) {
			Stream<String> individualUuidsStream = individualUuids.stream();

			userIdOptional = individualUuidsStream.map(
				individualUuid -> _userLocalService.fetchUserByUuidAndCompanyId(
					individualUuid, companyId)
			).filter(
				Objects::nonNull
			).findFirst(
			).map(
				UserModel::getUserId
			);
		}

		if (!userIdOptional.isPresent()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find a user corresponding to individual " +
						individual.getId());
			}
		}

		return userIdOptional;
	}

	private static final int _DELTA = 100;

	private static final Log _log = LogFactoryUtil.getLog(
		IndividualSegmentsChecker.class);

	private volatile AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private AsahSegmentsEntryCache _asahSegmentsEntryCache;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JSONWebServiceClient _jsonWebServiceClient;

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