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
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutUtilityPageEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutUtilityPageEntryService
 * @generated
 */
public class LayoutUtilityPageEntryServiceWrapper
	implements LayoutUtilityPageEntryService,
			   ServiceWrapper<LayoutUtilityPageEntryService> {

	public LayoutUtilityPageEntryServiceWrapper() {
		this(null);
	}

	public LayoutUtilityPageEntryServiceWrapper(
		LayoutUtilityPageEntryService layoutUtilityPageEntryService) {

		_layoutUtilityPageEntryService = layoutUtilityPageEntryService;
	}

	@Override
	public LayoutUtilityPageEntry addLayoutUtilityPageEntry(
			String externalReferenceCode, long groupId, long plid,
			long previewFileEntryId, boolean defaultLayoutUtilityPageEntry,
			String name, String type, long masterLayoutPlid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
			externalReferenceCode, groupId, plid, previewFileEntryId,
			defaultLayoutUtilityPageEntry, name, type, masterLayoutPlid);
	}

	@Override
	public LayoutUtilityPageEntry copyLayoutUtilityPageEntry(
			long groupId, long layoutUtilityPageEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutUtilityPageEntryService.copyLayoutUtilityPageEntry(
			groupId, layoutUtilityPageEntryId, serviceContext);
	}

	@Override
	public LayoutUtilityPageEntry deleteLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutUtilityPageEntryService.deleteLayoutUtilityPageEntry(
			layoutUtilityPageEntryId);
	}

	@Override
	public LayoutUtilityPageEntry fetchLayoutUtilityPageEntry(
		long layoutUtilityPageEntryId) {

		return _layoutUtilityPageEntryService.fetchLayoutUtilityPageEntry(
			layoutUtilityPageEntryId);
	}

	@Override
	public LayoutUtilityPageEntry getDefaultLayoutUtilityPageEntry(
			long groupId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutUtilityPageEntryService.getDefaultLayoutUtilityPageEntry(
			groupId, type);
	}

	@Override
	public java.util.List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId) {

		return _layoutUtilityPageEntryService.getLayoutUtilityPageEntries(
			groupId);
	}

	@Override
	public java.util.List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator) {

		return _layoutUtilityPageEntryService.getLayoutUtilityPageEntries(
			groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LayoutUtilityPageEntry>
			orderByComparator) {

		return _layoutUtilityPageEntryService.getLayoutUtilityPageEntries(
			groupId, type, start, end, orderByComparator);
	}

	@Override
	public int getLayoutUtilityPageEntriesCount(long groupId) {
		return _layoutUtilityPageEntryService.getLayoutUtilityPageEntriesCount(
			groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutUtilityPageEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public LayoutUtilityPageEntry setDefaultLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutUtilityPageEntryService.setDefaultLayoutUtilityPageEntry(
			layoutUtilityPageEntryId);
	}

	@Override
	public LayoutUtilityPageEntry updateLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutUtilityPageEntryService.updateLayoutUtilityPageEntry(
			layoutUtilityPageEntryId, previewFileEntryId);
	}

	@Override
	public LayoutUtilityPageEntry updateLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutUtilityPageEntryService.updateLayoutUtilityPageEntry(
			layoutUtilityPageEntryId, name);
	}

	@Override
	public LayoutUtilityPageEntryService getWrappedService() {
		return _layoutUtilityPageEntryService;
	}

	@Override
	public void setWrappedService(
		LayoutUtilityPageEntryService layoutUtilityPageEntryService) {

		_layoutUtilityPageEntryService = layoutUtilityPageEntryService;
	}

	private LayoutUtilityPageEntryService _layoutUtilityPageEntryService;

}