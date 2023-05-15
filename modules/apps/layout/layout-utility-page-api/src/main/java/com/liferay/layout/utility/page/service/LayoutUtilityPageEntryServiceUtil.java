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

package com.liferay.layout.utility.page.service;

import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for LayoutUtilityPageEntry. This utility wraps
 * <code>com.liferay.layout.utility.page.service.impl.LayoutUtilityPageEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutUtilityPageEntryService
 * @generated
 */
public class LayoutUtilityPageEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.utility.page.service.impl.LayoutUtilityPageEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static LayoutUtilityPageEntry addLayoutUtilityPageEntry(
			String externalReferenceCode, long groupId, long plid,
			long previewFileEntryId, boolean defaultLayoutUtilityPageEntry,
			String name, String type, long masterLayoutPlid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addLayoutUtilityPageEntry(
			externalReferenceCode, groupId, plid, previewFileEntryId,
			defaultLayoutUtilityPageEntry, name, type, masterLayoutPlid,
			serviceContext);
	}

	public static LayoutUtilityPageEntry copyLayoutUtilityPageEntry(
			long groupId, long sourceLayoutUtilityPageEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws Exception {

		return getService().copyLayoutUtilityPageEntry(
			groupId, sourceLayoutUtilityPageEntryId, serviceContext);
	}

	public static LayoutUtilityPageEntry deleteLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId)
		throws PortalException {

		return getService().deleteLayoutUtilityPageEntry(
			layoutUtilityPageEntryId);
	}

	public static LayoutUtilityPageEntry fetchLayoutUtilityPageEntry(
		long layoutUtilityPageEntryId) {

		return getService().fetchLayoutUtilityPageEntry(
			layoutUtilityPageEntryId);
	}

	public static LayoutUtilityPageEntry getDefaultLayoutUtilityPageEntry(
			long groupId, String type)
		throws PortalException {

		return getService().getDefaultLayoutUtilityPageEntry(groupId, type);
	}

	public static List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId) {

		return getService().getLayoutUtilityPageEntries(groupId);
	}

	public static List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getService().getLayoutUtilityPageEntries(
			groupId, start, end, orderByComparator);
	}

	public static List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId, String type, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return getService().getLayoutUtilityPageEntries(
			groupId, type, start, end, orderByComparator);
	}

	public static int getLayoutUtilityPageEntriesCount(long groupId) {
		return getService().getLayoutUtilityPageEntriesCount(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static LayoutUtilityPageEntry setDefaultLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId)
		throws PortalException {

		return getService().setDefaultLayoutUtilityPageEntry(
			layoutUtilityPageEntryId);
	}

	public static LayoutUtilityPageEntry unsetDefaultLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId)
		throws PortalException {

		return getService().unsetDefaultLayoutUtilityPageEntry(
			layoutUtilityPageEntryId);
	}

	public static LayoutUtilityPageEntry updateLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId, long previewFileEntryId)
		throws PortalException {

		return getService().updateLayoutUtilityPageEntry(
			layoutUtilityPageEntryId, previewFileEntryId);
	}

	public static LayoutUtilityPageEntry updateLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId, String name)
		throws PortalException {

		return getService().updateLayoutUtilityPageEntry(
			layoutUtilityPageEntryId, name);
	}

	public static LayoutUtilityPageEntryService getService() {
		return _service;
	}

	public static void setService(LayoutUtilityPageEntryService service) {
		_service = service;
	}

	private static volatile LayoutUtilityPageEntryService _service;

}