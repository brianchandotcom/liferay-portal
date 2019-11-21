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

package com.liferay.layout.page.template.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutPageTemplateEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryService
 * @generated
 */
public class LayoutPageTemplateEntryServiceWrapper
	implements LayoutPageTemplateEntryService,
			   ServiceWrapper<LayoutPageTemplateEntryService> {

	public LayoutPageTemplateEntryServiceWrapper(
		LayoutPageTemplateEntryService layoutPageTemplateEntryService) {

		_layoutPageTemplateEntryService = layoutPageTemplateEntryService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateEntryServiceUtil} to access the layout page template entry remote service. Add custom service methods to <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addLayoutPageTemplateEntry(
				long groupId, long layoutPageTemplateCollectionId,
				long classNameId, long classTypeId, String name, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
			groupId, layoutPageTemplateCollectionId, classNameId, classTypeId,
			name, status, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addLayoutPageTemplateEntry(long, long, String, int, long,
	 int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addLayoutPageTemplateEntry(
				long groupId, long layoutPageTemplateCollectionId, String name,
				int type, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
			groupId, layoutPageTemplateCollectionId, name, type, status,
			serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addLayoutPageTemplateEntry(
				long groupId, long layoutPageTemplateCollectionId, String name,
				int type, long masterLayoutPlid, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
			groupId, layoutPageTemplateCollectionId, name, type,
			masterLayoutPlid, status, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addLayoutPageTemplateEntry(long, long, long, long, String,
	 int, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addLayoutPageTemplateEntry(
				long groupId, long layoutPageTemplateCollectionId, String name,
				int status, long classNameId, long classTypeId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
			groupId, layoutPageTemplateCollectionId, name, status, classNameId,
			classTypeId, serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			copyLayoutPageTemplateEntry(
				long groupId, long layoutPageTemplateCollectionId,
				long layoutPageTemplateEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.copyLayoutPageTemplateEntry(
			groupId, layoutPageTemplateCollectionId, layoutPageTemplateEntryId,
			serviceContext);
	}

	@Override
	public void deleteLayoutPageTemplateEntries(
			long[] layoutPageTemplateEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutPageTemplateEntryService.deleteLayoutPageTemplateEntries(
			layoutPageTemplateEntryIds);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			deleteLayoutPageTemplateEntry(long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.deleteLayoutPageTemplateEntry(
			layoutPageTemplateEntryId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		fetchDefaultLayoutPageTemplateEntry(
			long groupId, long classNameId, long classTypeId) {

		return _layoutPageTemplateEntryService.
			fetchDefaultLayoutPageTemplateEntry(
				groupId, classNameId, classTypeId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			fetchLayoutPageTemplateEntry(long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.fetchLayoutPageTemplateEntry(
			layoutPageTemplateEntryId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		fetchLayoutPageTemplateEntryByUuidAndGroupId(
			String uuid, long groupId) {

		return _layoutPageTemplateEntryService.
			fetchLayoutPageTemplateEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, int type, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, type, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, type, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long classNameId, int type,
				boolean defaultTemplate) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, classNameId, type, defaultTemplate);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long layoutPageTemplateCollectionId, int start,
				int end) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long layoutPageTemplateCollectionId, int status,
				int start, int end) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId, status, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long layoutPageTemplateCollectionId, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId, status, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long layoutPageTemplateCollectionId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long classNameId, long classTypeId, int type) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, classNameId, classTypeId, type);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long classNameId, long classTypeId, int type,
				int status) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, classNameId, classTypeId, type, status);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long classNameId, long classTypeId, int type,
				int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, classNameId, classTypeId, type, status, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long classNameId, long classTypeId, int type,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, classNameId, classTypeId, type, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long classNameId, long classTypeId, String name,
				int type, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, classNameId, classTypeId, name, type, status, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long classNameId, long classTypeId, String name,
				int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, classNameId, classTypeId, name, type, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long layoutPageTemplateCollectionId, String name,
				int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId, name, status, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long layoutPageTemplateCollectionId, String name,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId, name, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, String name, int type, int status, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, name, type, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, String name, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
			groupId, name, type, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntriesByType(
				long groupId, long layoutPageTemplateCollectionId, int type,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesByType(
				groupId, layoutPageTemplateCollectionId, type, start, end,
				orderByComparator);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(long groupId, int type) {
		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(groupId, type);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, int type, int status) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(groupId, type, status);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long layoutPageTemplateCollectionId) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(
				groupId, layoutPageTemplateCollectionId);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long layoutPageTemplateCollectionId, int status) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(
				groupId, layoutPageTemplateCollectionId, status);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long classNameId, long classTypeId, int type) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(
				groupId, classNameId, classTypeId, type);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long classNameId, long classTypeId, int type,
		int status) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(
				groupId, classNameId, classTypeId, type, status);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long classNameId, long classTypeId, String name,
		int type) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(
				groupId, classNameId, classTypeId, name, type);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(
				groupId, classNameId, classTypeId, name, type, status);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long layoutPageTemplateCollectionId, String name) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(
				groupId, layoutPageTemplateCollectionId, name);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(
				groupId, layoutPageTemplateCollectionId, name, status);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, String name, int type) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(groupId, name, type);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(
		long groupId, String name, int type, int status) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCount(groupId, name, type, status);
	}

	@Override
	public int getLayoutPageTemplateEntriesCountByType(
		long groupId, long layoutPageTemplateCollectionId, int type) {

		return _layoutPageTemplateEntryService.
			getLayoutPageTemplateEntriesCountByType(
				groupId, layoutPageTemplateCollectionId, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutPageTemplateEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateLayoutPageTemplateEntry(
				long layoutPageTemplateEntryId, boolean defaultTemplate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntryId, defaultTemplate);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateLayoutPageTemplateEntry(
				long layoutPageTemplateEntryId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntryId, previewFileEntryId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateLayoutPageTemplateEntry(
				long layoutPageTemplateEntryId, long[] fragmentEntryIds,
				String editableValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntryId, fragmentEntryIds, editableValues,
			serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateLayoutPageTemplateEntry(
				long layoutPageTemplateEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntryId, name);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateLayoutPageTemplateEntry(
				long layoutPageTemplateEntryId, String name,
				long[] fragmentEntryIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntryId, name, fragmentEntryIds, serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateStatus(long layoutPageTemplateEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryService.updateStatus(
			layoutPageTemplateEntryId, status);
	}

	@Override
	public LayoutPageTemplateEntryService getWrappedService() {
		return _layoutPageTemplateEntryService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateEntryService layoutPageTemplateEntryService) {

		_layoutPageTemplateEntryService = layoutPageTemplateEntryService;
	}

	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

}