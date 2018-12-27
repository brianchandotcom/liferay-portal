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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.internal.asah.client.AsahFaroBackendClient;
import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.Results;
import com.liferay.segments.internal.asah.client.util.OrderByField;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
		Results<IndividualSegment> individualSegmentResults;

		try {
			individualSegmentResults =
				_asahFaroBackendClient.getIndividualSegmentResults(
					1, _MAX_PAGE_SIZE,
					Collections.singletonList(
						OrderByField.desc("dateModified")));
		}
		catch (RuntimeException re) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to retrieve individual segments", re);
			}

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

		List<IndividualSegment> items = individualSegmentResults.getItems();

		items.forEach(
			individualSegment -> _addIndividualSegment(
				individualSegment, serviceContext));
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
						"Adding new segment from individual segment with ID " +
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
						"Updating existing segment with ID ",
						segmentsEntry.getSegmentsEntryId(),
						" from individual segment with ID ",
						individualSegment.getId()));
			}

			_segmentsEntryLocalService.updateSegmentsEntry(
				segmentsEntry.getSegmentsEntryId(), nameMap, null, true, null,
				individualSegment.getId(), serviceContext);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to process individual segment with ID " +
					individualSegment.getId(),
				pe);
		}
	}

	private ServiceContext _getServiceContext() throws PortalException {
		long companyId = _portal.getDefaultCompanyId();

		Company company = _companyLocalService.getCompany(companyId);

		User user = company.getDefaultUser();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(company.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private static final int _MAX_PAGE_SIZE = 100;

	private static final Log _log = LogFactoryUtil.getLog(
		AsahFaroBackendIndividualSegmentsCheckerUtil.class);

	@Reference
	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}