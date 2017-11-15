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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for LayoutPageTemplateFragment. This utility wraps
 * {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateFragmentLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFragmentLocalService
 * @see com.liferay.layout.page.template.service.base.LayoutPageTemplateFragmentLocalServiceBaseImpl
 * @see com.liferay.layout.page.template.service.impl.LayoutPageTemplateFragmentLocalServiceImpl
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFragmentLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateFragmentLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the layout page template fragment to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFragment the layout page template fragment
	* @return the layout page template fragment that was added
	*/
	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment addLayoutPageTemplateFragment(
		com.liferay.layout.page.template.model.LayoutPageTemplateFragment layoutPageTemplateFragment) {
		return getService()
				   .addLayoutPageTemplateFragment(layoutPageTemplateFragment);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment addLayoutPageTemplateFragment(
		long userId, long groupId, long layoutPageTemplateEntryId,
		long fragmentEntryId, int position,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutPageTemplateFragment(userId, groupId,
			layoutPageTemplateEntryId, fragmentEntryId, position, serviceContext);
	}

	/**
	* Creates a new layout page template fragment with the primary key. Does not add the layout page template fragment to the database.
	*
	* @param layoutPageTemplateFragmentId the primary key for the new layout page template fragment
	* @return the new layout page template fragment
	*/
	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment createLayoutPageTemplateFragment(
		long layoutPageTemplateFragmentId) {
		return getService()
				   .createLayoutPageTemplateFragment(layoutPageTemplateFragmentId);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> deleteByLayoutPageTemplateEntry(
		long groupId, long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteByLayoutPageTemplateEntry(groupId,
			layoutPageTemplateEntryId);
	}

	/**
	* Deletes the layout page template fragment from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFragment the layout page template fragment
	* @return the layout page template fragment that was removed
	* @throws PortalException
	*/
	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
		com.liferay.layout.page.template.model.LayoutPageTemplateFragment layoutPageTemplateFragment)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteLayoutPageTemplateFragment(layoutPageTemplateFragment);
	}

	/**
	* Deletes the layout page template fragment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFragmentId the primary key of the layout page template fragment
	* @return the layout page template fragment that was removed
	* @throws PortalException if a layout page template fragment with the primary key could not be found
	*/
	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
		long layoutPageTemplateFragmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteLayoutPageTemplateFragment(layoutPageTemplateFragmentId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment fetchLayoutPageTemplateFragment(
		long layoutPageTemplateFragmentId) {
		return getService()
				   .fetchLayoutPageTemplateFragment(layoutPageTemplateFragmentId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the layout page template fragment with the primary key.
	*
	* @param layoutPageTemplateFragmentId the primary key of the layout page template fragment
	* @return the layout page template fragment
	* @throws PortalException if a layout page template fragment with the primary key could not be found
	*/
	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment getLayoutPageTemplateFragment(
		long layoutPageTemplateFragmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLayoutPageTemplateFragment(layoutPageTemplateFragmentId);
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
	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> getLayoutPageTemplateFragments(
		int start, int end) {
		return getService().getLayoutPageTemplateFragments(start, end);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> getLayoutPageTemplateFragmentsByPageTemplate(
		long groupId, long layoutPageTemplateEntryId) {
		return getService()
				   .getLayoutPageTemplateFragmentsByPageTemplate(groupId,
			layoutPageTemplateEntryId);
	}

	/**
	* Returns the number of layout page template fragments.
	*
	* @return the number of layout page template fragments
	*/
	public static int getLayoutPageTemplateFragmentsCount() {
		return getService().getLayoutPageTemplateFragmentsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the layout page template fragment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutPageTemplateFragment the layout page template fragment
	* @return the layout page template fragment that was updated
	*/
	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment updateLayoutPageTemplateFragment(
		com.liferay.layout.page.template.model.LayoutPageTemplateFragment layoutPageTemplateFragment) {
		return getService()
				   .updateLayoutPageTemplateFragment(layoutPageTemplateFragment);
	}

	public static LayoutPageTemplateFragmentLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LayoutPageTemplateFragmentLocalService, LayoutPageTemplateFragmentLocalService> _serviceTracker =
		ServiceTrackerFactory.open(LayoutPageTemplateFragmentLocalService.class);
}