/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.segments.asah.internal.messaging;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.asah.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.internal.client.JSONWebServiceClient;
import com.liferay.segments.asah.internal.client.model.Individual;
import com.liferay.segments.asah.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.internal.client.model.Results;
import com.liferay.segments.asah.internal.client.util.OrderByField;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	service = AsahFaroBackendIndividualSegmentsCheckerUtil.class
)
public class AsahFaroBackendIndividualSegmentsCheckerUtil {

	public void checkIndividualSegments() throws PortalException {
		Optional<AsahFaroBackendClient> asahFaroBackendClientOptional =
			_createAsahFaroBackendClient();

		if (!asahFaroBackendClientOptional.isPresent()) {
			return;
		}

		_asahFaroBackendClient = asahFaroBackendClientOptional.get();

		_checkIndividualSegments();
		_checkIndividualSegmentsMemberships();
	}

	private void _addSegmentsEntry(
		IndividualSegment individualSegment, ServiceContext serviceContext) {

		Map<Locale, String> nameMap = new HashMap<Locale, String>() {
			{
				put(LocaleUtil.getDefault(), individualSegment.getName());
			}
		};

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				serviceContext.getScopeGroupId(), individualSegment.getId(),
				true);

		try {
			if (segmentsEntry == null) {
				_segmentsEntryLocalService.addSegmentsEntry(
					individualSegment.getId(), nameMap, Collections.emptyMap(),
					true, null, SegmentsConstants.SOURCE_ASAH_FARO_BACKEND,
					User.class.getName(), serviceContext);

				return;
			}

			_segmentsEntryLocalService.updateSegmentsEntry(
				segmentsEntry.getSegmentsEntryId(), individualSegment.getId(),
				nameMap, null, true, null, serviceContext);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to process individual segment " +
					individualSegment.getId(),
				pe);
		}
	}

	private void _addSegmentsEntryRel(
		SegmentsEntry segmentsEntry, Individual individual,
		ServiceContext serviceContext) {

		Optional<Long> userIdOptional = _getUserIdOptional(individual);

		if (!userIdOptional.isPresent()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find a user corresponding to individual " +
						individual.getId());
			}

			return;
		}

		try {
			long userClassNameId = _classNameLocalService.getClassNameId(
				User.class.getName());

			_segmentsEntryRelLocalService.addSegmentsEntryRel(
				segmentsEntry.getSegmentsEntryId(), userClassNameId,
				userIdOptional.get(), serviceContext);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to process individual " + individual.getId(), pe);
		}
	}

	private void _checkIndividualSegmentMemberships(SegmentsEntry segmentsEntry)
		throws PortalException {

		_segmentsEntryRelLocalService.deleteSegmentsEntryRels(
			segmentsEntry.getSegmentsEntryId());

		Results<Individual> individualResults;

		try {
			individualResults = _asahFaroBackendClient.getIndividualResults(
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

			int totalPages = (int)Math.ceil((double)totalElements / _DELTA);

			ServiceContext serviceContext = _getServiceContext();

			int curPage = 1;

			while (true) {
				List<Individual> individuals = individualResults.getItems();

				individuals.forEach(
					individual -> _addSegmentsEntryRel(
						segmentsEntry, individual, serviceContext));

				curPage++;

				if (curPage > totalPages) {
					break;
				}

				individualResults = _asahFaroBackendClient.getIndividualResults(
					segmentsEntry.getSegmentsEntryKey(), curPage, _DELTA,
					Collections.singletonList(
						OrderByField.desc("dateModified")));
			}
		}
		catch (RuntimeException re) {
			_log.error(
				"Unable to retrieve individuals for individual segment " +
					segmentsEntry.getSegmentsEntryKey(),
				re);
		}
	}

	private void _checkIndividualSegments() throws PortalException {
		Results<IndividualSegment> individualSegmentResults = new Results<>();

		try {
			individualSegmentResults =
				_asahFaroBackendClient.getIndividualSegmentResults(
					1, _DELTA,
					Collections.singletonList(
						OrderByField.desc("dateModified")));
		}
		catch (RuntimeException re) {
			_log.error("Unable to retrieve individual segments", re);

			return;
		}

		int totalElements = individualSegmentResults.getTotal();

		if (_log.isDebugEnabled()) {
			_log.debug(totalElements + " active individual segments found");
		}

		if (totalElements == 0) {
			return;
		}

		ServiceContext serviceContext = _getServiceContext();

		List<IndividualSegment> individualSegments =
			individualSegmentResults.getItems();

		individualSegments.forEach(
			individualSegment -> _addSegmentsEntry(
				individualSegment, serviceContext));
	}

	private void _checkIndividualSegmentsMemberships() throws PortalException {
		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryLocalService.getSegmentsEntriesBySource(
				SegmentsConstants.SOURCE_ASAH_FARO_BACKEND, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			_checkIndividualSegmentMemberships(segmentsEntry);
		}
	}

	private Optional<AsahFaroBackendClient> _createAsahFaroBackendClient() {
		Company company = _companyLocalService.fetchCompany(
			_portal.getDefaultCompanyId());

		PortletPreferences portletPreferences =
			_portalPreferencesLocalService.getPreferences(
				company.getCompanyId(), PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		String asahFaroBackendDataSourceId = GetterUtil.getString(
			portletPreferences.getValue("liferayAnalyticsDataSourceId", null));
		String asahFaroBackendSecuritySignature = GetterUtil.getString(
			portletPreferences.getValue(
				"liferayAnalyticsFaroBackendSecuritySignature", null));
		String asahFaroBackendURL = GetterUtil.getString(
			portletPreferences.getValue(
				"liferayAnalyticsFaroBackendURL", null));

		if (Validator.isNull(asahFaroBackendDataSourceId) ||
			Validator.isNull(asahFaroBackendSecuritySignature) ||
			Validator.isNull(asahFaroBackendURL)) {

			if (_log.isInfoEnabled()) {
				_log.info("Unable to configure Asah Faro backend client");
			}

			return Optional.empty();
		}

		return Optional.of(
			new AsahFaroBackendClientImpl(
				_jsonWebServiceClient, asahFaroBackendDataSourceId,
				asahFaroBackendSecuritySignature, asahFaroBackendURL));
	}

	private ServiceContext _getServiceContext() throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		Company company = _companyLocalService.getCompany(
			_portal.getDefaultCompanyId());

		serviceContext.setScopeGroupId(company.getGroupId());

		User user = company.getDefaultUser();

		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private Optional<Long> _getUserIdOptional(Individual individual) {
		List<Individual.DataSourceIndividualPK> dataSourceIndividualPKs =
			individual.getDataSourceIndividualPKs();

		Stream<Individual.DataSourceIndividualPK> dataSourceIndividualPKStream =
			dataSourceIndividualPKs.stream();

		List<String> individualUuids = dataSourceIndividualPKStream.filter(
			dataSourceIndividualPK -> Objects.equals(
				_asahFaroBackendClient.getDataSourceId(),
				dataSourceIndividualPK.getDataSourceId())
		).findFirst(
		).map(
			Individual.DataSourceIndividualPK::getIndividualPKs
		).orElse(
			Collections.emptyList()
		);

		if (ListUtil.isEmpty(individualUuids)) {
			return Optional.empty();
		}

		Stream<String> individualUuidStream = individualUuids.stream();

		return individualUuidStream.map(
			individualUuid -> _userLocalService.fetchUserByUuidAndCompanyId(
				individualUuid, _portal.getDefaultCompanyId())
		).filter(
			Objects::nonNull
		).findFirst(
		).map(
			UserModel::getUserId
		);
	}

	private static final int _DELTA = 100;

	private static final Log _log = LogFactoryUtil.getLog(
		AsahFaroBackendIndividualSegmentsCheckerUtil.class);

	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JSONWebServiceClient _jsonWebServiceClient;

	@Reference
	private Portal _portal;

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	@Reference
	private UserLocalService _userLocalService;

}