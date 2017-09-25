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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutPageTemplateFolderLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFolderLocalService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFolderLocalServiceWrapper
	implements LayoutPageTemplateFolderLocalService,
		ServiceWrapper<LayoutPageTemplateFolderLocalService> {
	public LayoutPageTemplateFolderLocalServiceWrapper(
		LayoutPageTemplateFolderLocalService layoutPageTemplateFolderLocalService) {
		_layoutPageTemplateFolderLocalService = layoutPageTemplateFolderLocalService;
	}

	/**
	* Adds the layout page template folder to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFolder the layout page template folder
	* @return the layout page template folder that was added
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder addLayoutPageTemplateFolder(
		com.liferay.layout.page.template.model.LayoutPageTemplateFolder layoutPageTemplateFolder) {
		return _layoutPageTemplateFolderLocalService.addLayoutPageTemplateFolder(layoutPageTemplateFolder);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder addLayoutPageTemplateFolder(
		long userId, long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderLocalService.addLayoutPageTemplateFolder(userId,
			groupId, name, description, serviceContext);
	}

	/**
	* Creates a new layout page template folder with the primary key. Does not add the layout page template folder to the database.
	*
	* @param layoutPageTemplateFolderId the primary key for the new layout page template folder
	* @return the new layout page template folder
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder createLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId) {
		return _layoutPageTemplateFolderLocalService.createLayoutPageTemplateFolder(layoutPageTemplateFolderId);
	}

	/**
	* Deletes the layout page template folder from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFolder the layout page template folder
	* @return the layout page template folder that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder deleteLayoutPageTemplateFolder(
		com.liferay.layout.page.template.model.LayoutPageTemplateFolder layoutPageTemplateFolder)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderLocalService.deleteLayoutPageTemplateFolder(layoutPageTemplateFolder);
	}

	/**
	* Deletes the layout page template folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFolderId the primary key of the layout page template folder
	* @return the layout page template folder that was removed
	* @throws PortalException if a layout page template folder with the primary key could not be found
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder deleteLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderLocalService.deleteLayoutPageTemplateFolder(layoutPageTemplateFolderId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutPageTemplateFolderLocalService.dynamicQuery();
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
		return _layoutPageTemplateFolderLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.layout.page.template.model.impl.LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _layoutPageTemplateFolderLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.layout.page.template.model.impl.LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _layoutPageTemplateFolderLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _layoutPageTemplateFolderLocalService.dynamicQueryCount(dynamicQuery);
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
		return _layoutPageTemplateFolderLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder fetchLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId) {
		return _layoutPageTemplateFolderLocalService.fetchLayoutPageTemplateFolder(layoutPageTemplateFolderId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _layoutPageTemplateFolderLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _layoutPageTemplateFolderLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the layout page template folder with the primary key.
	*
	* @param layoutPageTemplateFolderId the primary key of the layout page template folder
	* @return the layout page template folder
	* @throws PortalException if a layout page template folder with the primary key could not be found
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder getLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderLocalService.getLayoutPageTemplateFolder(layoutPageTemplateFolderId);
	}

	/**
	* Returns a range of all the layout page template folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.layout.page.template.model.impl.LayoutPageTemplateFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template folders
	* @param end the upper bound of the range of layout page template folders (not inclusive)
	* @return the range of layout page template folders
	*/
	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		int start, int end) {
		return _layoutPageTemplateFolderLocalService.getLayoutPageTemplateFolders(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderLocalService.getLayoutPageTemplateFolders(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderLocalService.getLayoutPageTemplateFolders(groupId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> orderByComparator) {
		return _layoutPageTemplateFolderLocalService.getLayoutPageTemplateFolders(groupId,
			name, start, end, orderByComparator);
	}

	/**
	* Returns the number of layout page template folders.
	*
	* @return the number of layout page template folders
	*/
	@Override
	public int getLayoutPageTemplateFoldersCount() {
		return _layoutPageTemplateFolderLocalService.getLayoutPageTemplateFoldersCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutPageTemplateFolderLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the layout page template folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFolder the layout page template folder
	* @return the layout page template folder that was updated
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder updateLayoutPageTemplateFolder(
		com.liferay.layout.page.template.model.LayoutPageTemplateFolder layoutPageTemplateFolder) {
		return _layoutPageTemplateFolderLocalService.updateLayoutPageTemplateFolder(layoutPageTemplateFolder);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder updateLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderLocalService.updateLayoutPageTemplateFolder(layoutPageTemplateFolderId,
			name, description);
	}

	@Override
	public LayoutPageTemplateFolderLocalService getWrappedService() {
		return _layoutPageTemplateFolderLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateFolderLocalService layoutPageTemplateFolderLocalService) {
		_layoutPageTemplateFolderLocalService = layoutPageTemplateFolderLocalService;
	}

	private LayoutPageTemplateFolderLocalService _layoutPageTemplateFolderLocalService;
}