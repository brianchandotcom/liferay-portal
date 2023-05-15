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

package com.liferay.segments.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsEntry;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for SegmentsEntry. This utility wraps
 * <code>com.liferay.segments.service.impl.SegmentsEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryService
 * @generated
 */
public class SegmentsEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, boolean active,
			String criteria, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSegmentsEntry(
			segmentsEntryKey, nameMap, descriptionMap, active, criteria, type,
			serviceContext);
	}

	public static SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, boolean active,
			String criteria, String source, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSegmentsEntry(
			segmentsEntryKey, nameMap, descriptionMap, active, criteria, source,
			type, serviceContext);
	}

	public static void addSegmentsEntryClassPKs(
			long segmentsEntryId, long[] classPKs,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().addSegmentsEntryClassPKs(
			segmentsEntryId, classPKs, serviceContext);
	}

	public static SegmentsEntry deleteSegmentsEntry(long segmentsEntryId)
		throws PortalException {

		return getService().deleteSegmentsEntry(segmentsEntryId);
	}

	public static void deleteSegmentsEntryClassPKs(
			long segmentsEntryId, long[] classPKs)
		throws PortalException {

		getService().deleteSegmentsEntryClassPKs(segmentsEntryId, classPKs);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<SegmentsEntry> getSegmentsEntries(
		long groupId, boolean includeAncestorSegmentsEntries) {

		return getService().getSegmentsEntries(
			groupId, includeAncestorSegmentsEntries);
	}

	public static List<SegmentsEntry> getSegmentsEntries(
		long groupId, boolean includeAncestorSegmentsEntries, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {

		return getService().getSegmentsEntries(
			groupId, includeAncestorSegmentsEntries, start, end,
			orderByComparator);
	}

	public static List<SegmentsEntry> getSegmentsEntries(
		long companyId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return getService().getSegmentsEntries(
			companyId, start, end, orderByComparator);
	}

	public static int getSegmentsEntriesCount(long companyId) {
		return getService().getSegmentsEntriesCount(companyId);
	}

	public static int getSegmentsEntriesCount(
		long groupId, boolean includeAncestorSegmentsEntries) {

		return getService().getSegmentsEntriesCount(
			groupId, includeAncestorSegmentsEntries);
	}

	public static SegmentsEntry getSegmentsEntry(long segmentsEntryId)
		throws PortalException {

		return getService().getSegmentsEntry(segmentsEntryId);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<SegmentsEntry> searchSegmentsEntries(
				long companyId, long groupId, String keywords,
				boolean includeAncestorSegmentsEntries, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchSegmentsEntries(
			companyId, groupId, keywords, includeAncestorSegmentsEntries, start,
			end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<SegmentsEntry> searchSegmentsEntries(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchSegmentsEntries(
			companyId, keywords, start, end, sort);
	}

	public static SegmentsEntry updateSegmentsEntry(
			long segmentsEntryId, String segmentsEntryKey,
			Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, boolean active,
			String criteria,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateSegmentsEntry(
			segmentsEntryId, segmentsEntryKey, nameMap, descriptionMap, active,
			criteria, serviceContext);
	}

	public static SegmentsEntryService getService() {
		return _service;
	}

	public static void setService(SegmentsEntryService service) {
		_service = service;
	}

	private static volatile SegmentsEntryService _service;

}