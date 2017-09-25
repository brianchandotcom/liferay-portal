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
 * Provides a wrapper for {@link LayoutPageTemplateLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateLocalService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateLocalServiceWrapper
	implements LayoutPageTemplateLocalService,
		ServiceWrapper<LayoutPageTemplateLocalService> {
	public LayoutPageTemplateLocalServiceWrapper(
		LayoutPageTemplateLocalService layoutPageTemplateLocalService) {
		_layoutPageTemplateLocalService = layoutPageTemplateLocalService;
	}

	/**
	* Adds the layout page template to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplate the layout page template
	* @return the layout page template that was added
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate addLayoutPageTemplate(
		com.liferay.layout.page.template.model.LayoutPageTemplate layoutPageTemplate) {
		return _layoutPageTemplateLocalService.addLayoutPageTemplate(layoutPageTemplate);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate addLayoutPageTemplate(
		long groupId, long userId, long layoutPageTemplateFolderId,
		java.lang.String name,
		java.util.Map<java.lang.Integer, com.liferay.fragment.model.FragmentEntry> layoutPageTemplateFragments,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateLocalService.addLayoutPageTemplate(groupId,
			userId, layoutPageTemplateFolderId, name,
			layoutPageTemplateFragments, serviceContext);
	}

	/**
	* Creates a new layout page template with the primary key. Does not add the layout page template to the database.
	*
	* @param layoutPageTemplateId the primary key for the new layout page template
	* @return the new layout page template
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate createLayoutPageTemplate(
		long layoutPageTemplateId) {
		return _layoutPageTemplateLocalService.createLayoutPageTemplate(layoutPageTemplateId);
	}

	/**
	* Deletes the layout page template from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplate the layout page template
	* @return the layout page template that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate deleteLayoutPageTemplate(
		com.liferay.layout.page.template.model.LayoutPageTemplate layoutPageTemplate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateLocalService.deleteLayoutPageTemplate(layoutPageTemplate);
	}

	/**
	* Deletes the layout page template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateId the primary key of the layout page template
	* @return the layout page template that was removed
	* @throws PortalException if a layout page template with the primary key could not be found
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate deleteLayoutPageTemplate(
		long layoutPageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateLocalService.deleteLayoutPageTemplate(layoutPageTemplateId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutPageTemplateLocalService.dynamicQuery();
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
		return _layoutPageTemplateLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.layout.page.template.model.impl.LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _layoutPageTemplateLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.layout.page.template.model.impl.LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _layoutPageTemplateLocalService.dynamicQuery(dynamicQuery,
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
		return _layoutPageTemplateLocalService.dynamicQueryCount(dynamicQuery);
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
		return _layoutPageTemplateLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate fetchLayoutPageTemplate(
		long layoutPageTemplateId) {
		return _layoutPageTemplateLocalService.fetchLayoutPageTemplate(layoutPageTemplateId);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> fetchLayoutPageTemplates(
		long layoutPageTemplateFolderId) {
		return _layoutPageTemplateLocalService.fetchLayoutPageTemplates(layoutPageTemplateFolderId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _layoutPageTemplateLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _layoutPageTemplateLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the layout page template with the primary key.
	*
	* @param layoutPageTemplateId the primary key of the layout page template
	* @return the layout page template
	* @throws PortalException if a layout page template with the primary key could not be found
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate getLayoutPageTemplate(
		long layoutPageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateLocalService.getLayoutPageTemplate(layoutPageTemplateId);
	}

	/**
	* Returns a range of all the layout page templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.layout.page.template.model.impl.LayoutPageTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page templates
	* @param end the upper bound of the range of layout page templates (not inclusive)
	* @return the range of layout page templates
	*/
	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getLayoutPageTemplates(
		int start, int end) {
		return _layoutPageTemplateLocalService.getLayoutPageTemplates(start, end);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getLayoutPageTemplates(
		long layoutPageTemplateFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateLocalService.getLayoutPageTemplates(layoutPageTemplateFolderId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getLayoutPageTemplates(
		long groupId, long layoutPageTemplateFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplate> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateLocalService.getLayoutPageTemplates(groupId,
			layoutPageTemplateFolderId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getLayoutPageTemplates(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplate> orderByComparator) {
		return _layoutPageTemplateLocalService.getLayoutPageTemplates(groupId,
			layoutPageTemplateFolderId, name, start, end, orderByComparator);
	}

	/**
	* Returns the number of layout page templates.
	*
	* @return the number of layout page templates
	*/
	@Override
	public int getLayoutPageTemplatesCount() {
		return _layoutPageTemplateLocalService.getLayoutPageTemplatesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutPageTemplateLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the layout page template in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplate the layout page template
	* @return the layout page template that was updated
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate updateLayoutPageTemplate(
		com.liferay.layout.page.template.model.LayoutPageTemplate layoutPageTemplate) {
		return _layoutPageTemplateLocalService.updateLayoutPageTemplate(layoutPageTemplate);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate updateLayoutPageTemplate(
		long layoutPageTemplateId, java.lang.String name,
		java.util.Map<java.lang.Integer, com.liferay.fragment.model.FragmentEntry> layoutPageTemplateFragments,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateLocalService.updateLayoutPageTemplate(layoutPageTemplateId,
			name, layoutPageTemplateFragments, serviceContext);
	}

	@Override
	public LayoutPageTemplateLocalService getWrappedService() {
		return _layoutPageTemplateLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateLocalService layoutPageTemplateLocalService) {
		_layoutPageTemplateLocalService = layoutPageTemplateLocalService;
	}

	private LayoutPageTemplateLocalService _layoutPageTemplateLocalService;
}