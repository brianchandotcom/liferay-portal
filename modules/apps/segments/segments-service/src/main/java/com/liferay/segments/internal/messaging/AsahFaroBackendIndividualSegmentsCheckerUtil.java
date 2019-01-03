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

package com.liferay.segments.internal.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.internal.asah.client.AsahFaroBackendClient;
import com.liferay.segments.internal.asah.client.model.Individual;
import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.Results;
import com.liferay.segments.internal.asah.client.util.OrderByField;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
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
		if (_asahFaroBackendClient == null) {
			return;
		}

		_checkIndividualSegments();
		_checkIndividualSegmentsMemberships();
	}

	@Activate
	protected void activate() {
		String asahFaroBackendDataSourceId = System.getProperty(
			"asah.faro.backend.dataSourceId");

		String asahFaroBackendSecuritySignature = System.getProperty(
			"asah.faro.backend.security.signature");

		String asahFaroBackendUrl = System.getProperty("asah.faro.backend.url");

		if (Validator.isNull(asahFaroBackendDataSourceId) ||
			Validator.isNull(asahFaroBackendSecuritySignature) ||
			Validator.isNull(asahFaroBackendUrl)) {

			if (_log.isWarnEnabled()) {
				_log.warn("Unable to configure Asah Faro Backend Client");
			}

			return;
		}

		Properties properties = new Properties();

		properties.setProperty(
			"asahFaroBackendDataSourceId", asahFaroBackendDataSourceId);

		properties.setProperty(
			"asahFaroBackendSecuritySignature",
			asahFaroBackendSecuritySignature);

		properties.setProperty("asahFaroBackendUrl", asahFaroBackendUrl);

		ComponentInstance componentInstance = _componentFactory.newInstance(
			(Dictionary)properties);

		_asahFaroBackendClient =
			(AsahFaroBackendClient)componentInstance.getInstance();
	}

	@Reference(
		target = "(component.factory=AsahFaroBackendClient)", unbind = "-"
	)
	protected void setComponentFactory(ComponentFactory componentFactory) {
		_componentFactory = componentFactory;
	}

	private void _addIndividual(
		SegmentsEntry segmentsEntry, Individual individual,
		ServiceContext serviceContext) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Checking individual ", individual.getId(), " for segment ",
					segmentsEntry.getSegmentsEntryId()));
		}

		Map<String, Set<String>> dataSourceIndividualPKs =
			individual.getDataSourceIndividualPKs();

		Optional<Long> userIdOptional = _getUserIdOptional(
			dataSourceIndividualPKs);

		if (!userIdOptional.isPresent()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to find an user corresponding to individual ",
						individual.getId(), " with: segmentsEntryKey=",
						segmentsEntry.getKey(), "; segmentsEntryId=",
						segmentsEntry.getSegmentsEntryId(),
						"; dataSourceIndividualPKs=%s",
						dataSourceIndividualPKs));
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
				StringBundler.concat(
					"Unable to process individual ", individual.getId(),
					" with: segmentsEntryKey=", segmentsEntry.getKey(),
					"; segmentsEntryId=", segmentsEntry.getSegmentsEntryId(),
					"; dataSourceIndividualPKs=%s", dataSourceIndividualPKs),
				pe);
		}
	}

	private void _addIndividualSegment(
		IndividualSegment individualSegment, ServiceContext serviceContext) {

		Map<Locale, String> nameMap = new HashMap<Locale, String>() {
			{
				put(LocaleUtil.getDefault(), individualSegment.getName());
			}
		};

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				serviceContext.getScopeGroupId(), individualSegment.getId());

		try {
			if (segmentsEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Adding new segment from individual segment " +
							individualSegment.getId());
				}

				_segmentsEntryLocalService.addSegmentsEntry(
					nameMap, Collections.emptyMap(), true, null,
					individualSegment.getId(),
					SegmentsConstants.SOURCE_ASAH_FARO_BACKEND,
					User.class.getName(), serviceContext);

				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Updating existing segment ",
						segmentsEntry.getSegmentsEntryId(),
						" from individual segment ",
						individualSegment.getId()));
			}

			_segmentsEntryLocalService.updateSegmentsEntry(
				segmentsEntry.getSegmentsEntryId(), nameMap, null, true, null,
				individualSegment.getId(), serviceContext);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to process individual segment " +
					individualSegment.getId(),
				pe);
		}
	}

	private void _checkIndividualSegmentMemberships(SegmentsEntry segmentsEntry)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Checking memberships for segment ",
					segmentsEntry.getSegmentsEntryId(), " and individual ",
					"segment ", segmentsEntry.getKey()));
		}

		_segmentsEntryRelLocalService.deleteSegmentsEntryRels(
			segmentsEntry.getSegmentsEntryId());

		Results<Individual> individualResults;

		try {
			individualResults = _asahFaroBackendClient.getIndividualResults(
				segmentsEntry.getKey(), 1, _DELTA_INDIVIDUALS,
				Collections.singletonList(OrderByField.desc("dateModified")));

			int totalElements = individualResults.getTotal();

			if (_log.isDebugEnabled()) {
				_log.debug(
					totalElements + " individuals found for individual segment " +
						segmentsEntry.getKey());
			}

			if (totalElements == 0) {
				return;
			}

			int totalPages = (int)Math.ceil(
				(double)totalElements / _DELTA_INDIVIDUALS);

			ServiceContext serviceContext = _getServiceContext();

			int curPage = 1;

			while (true) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Checking individuals: page=", curPage, "; size=",
							_DELTA_INDIVIDUALS, "; total pages=", totalPages,
							"; total elements=", totalElements));
				}

				List<Individual> items = individualResults.getItems();

				items.forEach(
					individual -> _addIndividual(
						segmentsEntry, individual, serviceContext));

				curPage++;

				if (curPage > totalPages) {
					break;
				}

				individualResults = _asahFaroBackendClient.getIndividualResults(
					segmentsEntry.getKey(), curPage, _DELTA_INDIVIDUALS,
					Collections.singletonList(
						OrderByField.desc("dateModified")));
			}
		}
		catch (RuntimeException re) {
			_log.error(
				"Unable to retrieve individuals for individual segment " +
					segmentsEntry.getKey(),
				re);

			return;
		}
	}

	private void _checkIndividualSegments() throws PortalException {
		Results<IndividualSegment> individualSegmentResults = new Results<>();

		try {
			individualSegmentResults =
				_asahFaroBackendClient.getIndividualSegmentResults(
					1, _DELTA_INDIVIDUAL_SEGMENTS,
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
			individualSegment -> _addIndividualSegment(
				individualSegment, serviceContext));
	}

	private void _checkIndividualSegmentsMemberships() throws PortalException {
		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryLocalService.getSegmentsEntriesBySource(
				SegmentsConstants.SOURCE_ASAH_FARO_BACKEND, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		if (_log.isDebugEnabled()) {
			_log.debug(
				segmentsEntries.size() +
					" existing Asah Faro Backend segments found");
		}

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			_checkIndividualSegmentMemberships(segmentsEntry);
		}
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

	private Optional<Long> _getUserIdOptional(
		Map<String, Set<String>> dataSourceIndividualPKs) {

		Set<String> individualPKs = dataSourceIndividualPKs.get(
			_asahFaroBackendClient.getDataSourceId());

		if ((individualPKs == null) || individualPKs.isEmpty()) {
			return Optional.empty();
		}

		Iterator<String> iterator = individualPKs.iterator();

		String userUuid = iterator.next();

		User user = _userLocalService.fetchUserByUuidAndCompanyId(
			userUuid, _portal.getDefaultCompanyId());

		if (user == null) {
			return Optional.empty();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"User found with UUID ", userUuid, ": userId=",
					user.getUserId(), "; fullName=", user.getFullName()));
		}

		return Optional.of(user.getUserId());
	}

	private static final int _DELTA_INDIVIDUAL_SEGMENTS = 100;

	private static final int _DELTA_INDIVIDUALS = 20;

	private static final Log _log = LogFactoryUtil.getLog(
		AsahFaroBackendIndividualSegmentsCheckerUtil.class);

	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	private ComponentFactory _componentFactory;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	@Reference
	private UserLocalService _userLocalService;

}