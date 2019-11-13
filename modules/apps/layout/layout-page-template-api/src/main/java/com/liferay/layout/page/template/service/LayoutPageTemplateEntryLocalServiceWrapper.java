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
 * Provides a wrapper for {@link LayoutPageTemplateEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryLocalService
 * @generated
 */
public class LayoutPageTemplateEntryLocalServiceWrapper
	implements LayoutPageTemplateEntryLocalService,
			   ServiceWrapper<LayoutPageTemplateEntryLocalService> {

	public LayoutPageTemplateEntryLocalServiceWrapper(
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService) {

		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateEntryLocalServiceUtil} to access the layout page template entry local service. Add custom service methods to <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addGlobalLayoutPageTemplateEntry(
				com.liferay.portal.kernel.model.LayoutPrototype layoutPrototype)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.
			addGlobalLayoutPageTemplateEntry(layoutPrototype);
	}

	/**
	 * Adds the layout page template entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntry the layout page template entry
	 * @return the layout page template entry that was added
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		addLayoutPageTemplateEntry(
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				layoutPageTemplateEntry) {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			layoutPageTemplateEntry);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addLayoutPageTemplateEntry(
				com.liferay.portal.kernel.model.LayoutPrototype layoutPrototype)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			layoutPrototype);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addLayoutPageTemplateEntry(
				long userId, long groupId, long layoutPageTemplateCollectionId,
				long classNameId, long classTypeId, String name, int type,
				boolean defaultTemplate, long layoutPrototypeId,
				long previewFileEntryId, long plid, int status,
				long masterLayoutPlid,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, classNameId,
			classTypeId, name, type, defaultTemplate, layoutPrototypeId,
			previewFileEntryId, plid, status, masterLayoutPlid, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addLayoutPageTemplateEntry(long, long, long, long, long,
	 String, int, boolean, long, long, long, int, long,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addLayoutPageTemplateEntry(
				long userId, long groupId, long layoutPageTemplateCollectionId,
				long classNameId, long classTypeId, String name, int type,
				boolean defaultTemplate, long layoutPrototypeId,
				long previewFileEntryId, long plid, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, classNameId,
			classTypeId, name, type, defaultTemplate, layoutPrototypeId,
			previewFileEntryId, plid, status, serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addLayoutPageTemplateEntry(
				long userId, long groupId, long layoutPageTemplateCollectionId,
				long classNameId, long classTypeId, String name, int type,
				int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, classNameId,
			classTypeId, name, type, status, serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addLayoutPageTemplateEntry(
				long userId, long groupId, long layoutPageTemplateCollectionId,
				String name, int type, int status, long masterLayoutPlid,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, name, type, status,
			masterLayoutPlid, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addLayoutPageTemplateEntry(long, long, long, String, int,
	 int, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			addLayoutPageTemplateEntry(
				long userId, long groupId, long layoutPageTemplateCollectionId,
				String name, int type, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, name, type, status,
			serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			copyLayoutPageTemplateEntry(
				long userId, long groupId, long layoutPageTemplateCollectionId,
				long layoutPageTemplateEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.copyLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId,
			layoutPageTemplateEntryId, serviceContext);
	}

	/**
	 * Creates a new layout page template entry with the primary key. Does not add the layout page template entry to the database.
	 *
	 * @param layoutPageTemplateEntryId the primary key for the new layout page template entry
	 * @return the new layout page template entry
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		createLayoutPageTemplateEntry(long layoutPageTemplateEntryId) {

		return _layoutPageTemplateEntryLocalService.
			createLayoutPageTemplateEntry(layoutPageTemplateEntryId);
	}

	/**
	 * Deletes the layout page template entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntry the layout page template entry
	 * @return the layout page template entry that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			deleteLayoutPageTemplateEntry(
				com.liferay.layout.page.template.model.LayoutPageTemplateEntry
					layoutPageTemplateEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.
			deleteLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	/**
	 * Deletes the layout page template entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the layout page template entry
	 * @return the layout page template entry that was removed
	 * @throws PortalException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			deleteLayoutPageTemplateEntry(long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.
			deleteLayoutPageTemplateEntry(layoutPageTemplateEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutPageTemplateEntryLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _layoutPageTemplateEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _layoutPageTemplateEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _layoutPageTemplateEntryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _layoutPageTemplateEntryLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _layoutPageTemplateEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		fetchDefaultLayoutPageTemplateEntry(
			long groupId, long classNameId, long classTypeId) {

		return _layoutPageTemplateEntryLocalService.
			fetchDefaultLayoutPageTemplateEntry(
				groupId, classNameId, classTypeId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		fetchFirstLayoutPageTemplateEntry(long layoutPrototypeId) {

		return _layoutPageTemplateEntryLocalService.
			fetchFirstLayoutPageTemplateEntry(layoutPrototypeId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		fetchLayoutPageTemplateEntry(long layoutPageTemplateEntryId) {

		return _layoutPageTemplateEntryLocalService.
			fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		fetchLayoutPageTemplateEntry(long groupId, String name, int type) {

		return _layoutPageTemplateEntryLocalService.
			fetchLayoutPageTemplateEntry(groupId, name, type);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		fetchLayoutPageTemplateEntryByPlid(long plid) {

		return _layoutPageTemplateEntryLocalService.
			fetchLayoutPageTemplateEntryByPlid(plid);
	}

	/**
	 * Returns the layout page template entry matching the UUID and group.
	 *
	 * @param uuid the layout page template entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		fetchLayoutPageTemplateEntryByUuidAndGroupId(
			String uuid, long groupId) {

		return _layoutPageTemplateEntryLocalService.
			fetchLayoutPageTemplateEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _layoutPageTemplateEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _layoutPageTemplateEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _layoutPageTemplateEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the layout page template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of layout page template entries
	 */
	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(int start, int end) {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntries(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(long groupId) {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntries(groupId);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long layoutPageTemplateCollectionId) {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntries(
				groupId, layoutPageTemplateCollectionId);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long layoutPageTemplateCollectionId, int status) {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntries(
				groupId, layoutPageTemplateCollectionId, status);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long layoutPageTemplateCollectionId, int start,
				int end) {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntries(
				groupId, layoutPageTemplateCollectionId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntries(
				long groupId, long layoutPageTemplateCollectionId, int status,
				int start, int end) {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntries(
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

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntries(
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

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntries(
				groupId, layoutPageTemplateCollectionId, start, end,
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

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntries(
				groupId, layoutPageTemplateCollectionId, name, status, start,
				end, orderByComparator);
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

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntries(
				groupId, layoutPageTemplateCollectionId, name, start, end,
				orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntriesByLayoutPrototypeId(
				long layoutPrototypeId) {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntriesByLayoutPrototypeId(layoutPrototypeId);
	}

	/**
	 * Returns all the layout page template entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template entries
	 * @param companyId the primary key of the company
	 * @return the matching layout page template entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntriesByUuidAndCompanyId(
				String uuid, long companyId) {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of layout page template entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout page template entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntry>
			getLayoutPageTemplateEntriesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateEntry> orderByComparator) {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntriesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout page template entries.
	 *
	 * @return the number of layout page template entries
	 */
	@Override
	public int getLayoutPageTemplateEntriesCount() {
		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntriesCount();
	}

	/**
	 * Returns the layout page template entry with the primary key.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the layout page template entry
	 * @return the layout page template entry
	 * @throws PortalException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			getLayoutPageTemplateEntry(long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntry(
			layoutPageTemplateEntryId);
	}

	/**
	 * Returns the layout page template entry matching the UUID and group.
	 *
	 * @param uuid the layout page template entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template entry
	 * @throws PortalException if a matching layout page template entry could not be found
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			getLayoutPageTemplateEntryByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.
			getLayoutPageTemplateEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutPageTemplateEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the layout page template entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntry the layout page template entry
	 * @return the layout page template entry that was updated
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		updateLayoutPageTemplateEntry(
			com.liferay.layout.page.template.model.LayoutPageTemplateEntry
				layoutPageTemplateEntry) {

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
		updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, boolean defaultTemplate) {

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				layoutPageTemplateEntryId, defaultTemplate);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateLayoutPageTemplateEntry(
				long layoutPageTemplateEntryId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				layoutPageTemplateEntryId, previewFileEntryId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateLayoutPageTemplateEntry(
				long userId, long layoutPageTemplateEntryId, String name,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				userId, layoutPageTemplateEntryId, name, status);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateLayoutPageTemplateEntry(
				long layoutPageTemplateEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntryId, name);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateLayoutPageTemplateEntry(
				long layoutPageTemplateEntryId, String name,
				long[] fragmentEntryIds, String editableValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				layoutPageTemplateEntryId, name, fragmentEntryIds,
				editableValues, serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry
			updateStatus(
				long userId, long layoutPageTemplateEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryLocalService.updateStatus(
			userId, layoutPageTemplateEntryId, status);
	}

	@Override
	public LayoutPageTemplateEntryLocalService getWrappedService() {
		return _layoutPageTemplateEntryLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService) {

		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
	}

	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}