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

package com.liferay.fragment.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for FragmentEntryLink. This utility wraps
 * {@link com.liferay.fragment.service.impl.FragmentEntryLinkLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLinkLocalService
 * @see com.liferay.fragment.service.base.FragmentEntryLinkLocalServiceBaseImpl
 * @see com.liferay.fragment.service.impl.FragmentEntryLinkLocalServiceImpl
 * @generated
 */
@ProviderType
public class FragmentEntryLinkLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.fragment.service.impl.FragmentEntryLinkLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the fragment entry link to the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLink the fragment entry link
	* @return the fragment entry link that was added
	*/
	public static com.liferay.fragment.model.FragmentEntryLink addFragmentEntryLink(
		com.liferay.fragment.model.FragmentEntryLink fragmentEntryLink) {
		return getService().addFragmentEntryLink(fragmentEntryLink);
	}

	public static com.liferay.fragment.model.FragmentEntryLink addFragmentEntryLink(
		long groupId, long originalFragmentEntryLinkId, long fragmentEntryId,
		long classNameId, long classPK, java.lang.String css,
		java.lang.String html, java.lang.String js,
		java.lang.String editableValues, int position) {
		return getService()
				   .addFragmentEntryLink(groupId, originalFragmentEntryLinkId,
			fragmentEntryId, classNameId, classPK, css, html, js,
			editableValues, position);
	}

	public static com.liferay.fragment.model.FragmentEntryLink addFragmentEntryLink(
		long groupId, long fragmentEntryId, long classNameId, long classPK,
		java.lang.String css, java.lang.String html, java.lang.String js,
		java.lang.String editableValues, int position) {
		return getService()
				   .addFragmentEntryLink(groupId, fragmentEntryId, classNameId,
			classPK, css, html, js, editableValues, position);
	}

	/**
	* Creates a new fragment entry link with the primary key. Does not add the fragment entry link to the database.
	*
	* @param fragmentEntryLinkId the primary key for the new fragment entry link
	* @return the new fragment entry link
	*/
	public static com.liferay.fragment.model.FragmentEntryLink createFragmentEntryLink(
		long fragmentEntryLinkId) {
		return getService().createFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	* Deletes the fragment entry link from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLink the fragment entry link
	* @return the fragment entry link that was removed
	*/
	public static com.liferay.fragment.model.FragmentEntryLink deleteFragmentEntryLink(
		com.liferay.fragment.model.FragmentEntryLink fragmentEntryLink) {
		return getService().deleteFragmentEntryLink(fragmentEntryLink);
	}

	/**
	* Deletes the fragment entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLinkId the primary key of the fragment entry link
	* @return the fragment entry link that was removed
	* @throws PortalException if a fragment entry link with the primary key could not be found
	*/
	public static com.liferay.fragment.model.FragmentEntryLink deleteFragmentEntryLink(
		long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteFragmentEntryLink(fragmentEntryLinkId);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink> deleteLayoutPageTemplateEntryFragmentEntryLinks(
		long groupId, long classNameId, long classPK) {
		return getService()
				   .deleteLayoutPageTemplateEntryFragmentEntryLinks(groupId,
			classNameId, classPK);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.fragment.model.FragmentEntryLink fetchFragmentEntryLink(
		long fragmentEntryLinkId) {
		return getService().fetchFragmentEntryLink(fragmentEntryLinkId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the fragment entry link with the primary key.
	*
	* @param fragmentEntryLinkId the primary key of the fragment entry link
	* @return the fragment entry link
	* @throws PortalException if a fragment entry link with the primary key could not be found
	*/
	public static com.liferay.fragment.model.FragmentEntryLink getFragmentEntryLink(
		long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	* Returns a range of all the fragment entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entry links
	* @param end the upper bound of the range of fragment entry links (not inclusive)
	* @return the range of fragment entry links
	*/
	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink> getFragmentEntryLinks(
		int start, int end) {
		return getService().getFragmentEntryLinks(start, end);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long classNameId, long classPK) {
		return getService().getFragmentEntryLinks(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of fragment entry links.
	*
	* @return the number of fragment entry links
	*/
	public static int getFragmentEntryLinksCount() {
		return getService().getFragmentEntryLinksCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
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
	* Updates the fragment entry link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryLink the fragment entry link
	* @return the fragment entry link that was updated
	*/
	public static com.liferay.fragment.model.FragmentEntryLink updateFragmentEntryLink(
		com.liferay.fragment.model.FragmentEntryLink fragmentEntryLink) {
		return getService().updateFragmentEntryLink(fragmentEntryLink);
	}

	public static com.liferay.fragment.model.FragmentEntryLink updateFragmentEntryLink(
		long fragmentEntryLinkId, int position) {
		return getService()
				   .updateFragmentEntryLink(fragmentEntryLinkId, position);
	}

	public static com.liferay.fragment.model.FragmentEntryLink updateFragmentEntryLink(
		long fragmentEntryLinkId, java.lang.String editableValues) {
		return getService()
				   .updateFragmentEntryLink(fragmentEntryLinkId, editableValues);
	}

	public static void updateFragmentEntryLinks(long groupId, long classNameId,
		long classPK, long[] fragmentEntryIds, java.lang.String editableValues)
		throws com.liferay.portal.kernel.json.JSONException {
		getService()
			.updateFragmentEntryLinks(groupId, classNameId, classPK,
			fragmentEntryIds, editableValues);
	}

	public static FragmentEntryLinkLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FragmentEntryLinkLocalService, FragmentEntryLinkLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FragmentEntryLinkLocalService.class);

		ServiceTracker<FragmentEntryLinkLocalService, FragmentEntryLinkLocalService> serviceTracker =
			new ServiceTracker<FragmentEntryLinkLocalService, FragmentEntryLinkLocalService>(bundle.getBundleContext(),
				FragmentEntryLinkLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}