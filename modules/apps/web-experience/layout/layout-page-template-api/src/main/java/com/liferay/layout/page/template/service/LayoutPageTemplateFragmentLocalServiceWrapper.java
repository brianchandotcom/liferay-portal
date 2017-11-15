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
 * Provides a wrapper for {@link LayoutPageTemplateFragmentLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFragmentLocalService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFragmentLocalServiceWrapper
	implements LayoutPageTemplateFragmentLocalService,
		ServiceWrapper<LayoutPageTemplateFragmentLocalService> {
	public LayoutPageTemplateFragmentLocalServiceWrapper(
		LayoutPageTemplateFragmentLocalService layoutPageTemplateFragmentLocalService) {
		_layoutPageTemplateFragmentLocalService = layoutPageTemplateFragmentLocalService;
	}

	/**
	* Adds the layout page template fragment to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFragment the layout page template fragment
	* @return the layout page template fragment that was added
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment addLayoutPageTemplateFragment(
		com.liferay.layout.page.template.model.LayoutPageTemplateFragment layoutPageTemplateFragment) {
		return _layoutPageTemplateFragmentLocalService.addLayoutPageTemplateFragment(layoutPageTemplateFragment);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment addLayoutPageTemplateFragment(
		long userId, long groupId, long layoutPageTemplateEntryId,
		long fragmentEntryId, int position,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentLocalService.addLayoutPageTemplateFragment(userId,
			groupId, layoutPageTemplateEntryId, fragmentEntryId, position,
			serviceContext);
	}

	/**
	* Creates a new layout page template fragment with the primary key. Does not add the layout page template fragment to the database.
	*
	* @param layoutPageTemplateFragmentId the primary key for the new layout page template fragment
	* @return the new layout page template fragment
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment createLayoutPageTemplateFragment(
		long layoutPageTemplateFragmentId) {
		return _layoutPageTemplateFragmentLocalService.createLayoutPageTemplateFragment(layoutPageTemplateFragmentId);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> deleteByLayoutPageTemplateEntry(
		long groupId, long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentLocalService.deleteByLayoutPageTemplateEntry(groupId,
			layoutPageTemplateEntryId);
	}

	/**
	* Deletes the layout page template fragment from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFragment the layout page template fragment
	* @return the layout page template fragment that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
		com.liferay.layout.page.template.model.LayoutPageTemplateFragment layoutPageTemplateFragment)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentLocalService.deleteLayoutPageTemplateFragment(layoutPageTemplateFragment);
	}

	/**
	* Deletes the layout page template fragment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFragmentId the primary key of the layout page template fragment
	* @return the layout page template fragment that was removed
	* @throws PortalException if a layout page template fragment with the primary key could not be found
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
		long layoutPageTemplateFragmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentLocalService.deleteLayoutPageTemplateFragment(layoutPageTemplateFragmentId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutPageTemplateFragmentLocalService.dynamicQuery();
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
		return _layoutPageTemplateFragmentLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.layout.page.template.model.impl.LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _layoutPageTemplateFragmentLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.layout.page.template.model.impl.LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _layoutPageTemplateFragmentLocalService.dynamicQuery(dynamicQuery,
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
		return _layoutPageTemplateFragmentLocalService.dynamicQueryCount(dynamicQuery);
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
		return _layoutPageTemplateFragmentLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment fetchLayoutPageTemplateFragment(
		long layoutPageTemplateFragmentId) {
		return _layoutPageTemplateFragmentLocalService.fetchLayoutPageTemplateFragment(layoutPageTemplateFragmentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _layoutPageTemplateFragmentLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _layoutPageTemplateFragmentLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the layout page template fragment with the primary key.
	*
	* @param layoutPageTemplateFragmentId the primary key of the layout page template fragment
	* @return the layout page template fragment
	* @throws PortalException if a layout page template fragment with the primary key could not be found
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment getLayoutPageTemplateFragment(
		long layoutPageTemplateFragmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentLocalService.getLayoutPageTemplateFragment(layoutPageTemplateFragmentId);
	}

	/**
	* Returns a range of all the layout page template fragments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.layout.page.template.model.impl.LayoutPageTemplateFragmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout page template fragments
	* @param end the upper bound of the range of layout page template fragments (not inclusive)
	* @return the range of layout page template fragments
	*/
	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> getLayoutPageTemplateFragments(
		int start, int end) {
		return _layoutPageTemplateFragmentLocalService.getLayoutPageTemplateFragments(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> getLayoutPageTemplateFragmentsByPageTemplate(
		long groupId, long layoutPageTemplateEntryId) {
		return _layoutPageTemplateFragmentLocalService.getLayoutPageTemplateFragmentsByPageTemplate(groupId,
			layoutPageTemplateEntryId);
	}

	/**
	* Returns the number of layout page template fragments.
	*
	* @return the number of layout page template fragments
	*/
	@Override
	public int getLayoutPageTemplateFragmentsCount() {
		return _layoutPageTemplateFragmentLocalService.getLayoutPageTemplateFragmentsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutPageTemplateFragmentLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the layout page template fragment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFragment the layout page template fragment
	* @return the layout page template fragment that was updated
	*/
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment updateLayoutPageTemplateFragment(
		com.liferay.layout.page.template.model.LayoutPageTemplateFragment layoutPageTemplateFragment) {
		return _layoutPageTemplateFragmentLocalService.updateLayoutPageTemplateFragment(layoutPageTemplateFragment);
	}

	@Override
	public LayoutPageTemplateFragmentLocalService getWrappedService() {
		return _layoutPageTemplateFragmentLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateFragmentLocalService layoutPageTemplateFragmentLocalService) {
		_layoutPageTemplateFragmentLocalService = layoutPageTemplateFragmentLocalService;
	}

	private LayoutPageTemplateFragmentLocalService _layoutPageTemplateFragmentLocalService;
}