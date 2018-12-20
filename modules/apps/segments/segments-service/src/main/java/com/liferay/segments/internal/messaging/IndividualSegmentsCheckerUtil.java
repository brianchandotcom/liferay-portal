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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.internal.asah.client.AsahFaroBackendClient;
import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.Results;
import com.liferay.segments.internal.asah.client.util.OrderByField;
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
@Component(immediate = true, service = IndividualSegmentsCheckerUtil.class)
public class IndividualSegmentsCheckerUtil {

	public void checkIndividualSegments() throws PortalException {
		_deleteAsahFaroBackendSegmentEntries();

		Results<IndividualSegment> firstPageIndividualSegmentResults =
			_asahFaroBackendClient.getIndividualSegmentResults(
				1, _PAGE_SIZE,
				Collections.singletonList(OrderByField.desc("dateModified")));

		int totalElements = firstPageIndividualSegmentResults.getTotal();

		if (_log.isDebugEnabled()) {
			_log.debug(totalElements + " Active Segments found");
		}

		int totalPages = (int)Math.ceil((double)totalElements / _PAGE_SIZE);

		if (totalPages == 0) {
			return;
		}

		ServiceContext serviceContext = _getServiceContext();

		if (totalPages > 0) {
			_checkIndividualSegmentResultsPage(
				firstPageIndividualSegmentResults, totalPages, 1,
				firstPageIndividualSegmentResults.getTotal(), serviceContext);
		}

		if (totalPages > 1) {
			for (int i = 2; i <= totalPages; i++) {
				_checkIndividualSegmentResultsPage(
					_asahFaroBackendClient.getIndividualSegmentResults(
						i, _PAGE_SIZE,
						Collections.singletonList(
							OrderByField.desc("dateModified"))),
					totalPages, i, totalElements, serviceContext);
			}
		}
	}

	private void _checkIndividualSegment(
		IndividualSegment individualSegment, ServiceContext serviceContext) {

		try {
			Map<Locale, String> nameMap = new HashMap<Locale, String>() {
				{
					put(LocaleUtil.getDefault(), individualSegment.getName());
				}
			};

			_segmentsEntryLocalService.addSegmentsEntry(
				nameMap, Collections.emptyMap(), true, null,
				individualSegment.getId(),
				SegmentsConstants.SOURCE_ASAH_FARO_BACKEND,
				User.class.getName(), serviceContext);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to Check Individual Segment with ID: " +
					individualSegment.getId(),
				pe);
		}
	}

	private void _checkIndividualSegmentResultsPage(
		Results<IndividualSegment> pageIndividualSegmentResults, int totalPages,
		int currentPage, int totalElements, ServiceContext serviceContext) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Checking Individual Segments: Current Page %d; Page " +
						"Size: %s; Total Pages: %d; Total elements: %d",
					currentPage, _PAGE_SIZE, totalPages, totalElements));
		}

		List<IndividualSegment> items = pageIndividualSegmentResults.getItems();

		items.forEach(
			individualSegment -> _checkIndividualSegment(
				individualSegment, serviceContext));
	}

	private void _deleteAsahFaroBackendSegmentEntries() {
		try {
			_segmentsEntryLocalService.deleteSegmentsEntries(
				SegmentsConstants.SOURCE_ASAH_FARO_BACKEND);
		}
		catch (PortalException pe) {
			_log.error(
				"Error deleting segments entries: " + pe.getMessage(), pe);
		}
	}

	private ServiceContext _getServiceContext() throws PortalException {
		long companyId = PortalUtil.getDefaultCompanyId();

		Company company = _companyLocalService.getCompany(companyId);

		User user = company.getDefaultUser();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(company.getGroupId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private static final int _PAGE_SIZE = 2;

	private static final Log _log = LogFactoryUtil.getLog(
		IndividualSegmentsCheckerUtil.class);

	@Reference
	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}