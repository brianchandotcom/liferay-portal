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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for FragmentEntryLink. This utility wraps
 * <code>com.liferay.fragment.service.impl.FragmentEntryLinkLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLinkLocalService
 * @generated
 */
public class FragmentEntryLinkLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentEntryLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the fragment entry link to the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryLink the fragment entry link
	 * @return the fragment entry link that was added
	 */
	public static com.liferay.fragment.model.FragmentEntryLink
		addFragmentEntryLink(
			com.liferay.fragment.model.FragmentEntryLink fragmentEntryLink) {

		return getService().addFragmentEntryLink(fragmentEntryLink);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntryLink(long, long, long, long, long, long,
	 String, String, String, String, String, String, int, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntryLink
			addFragmentEntryLink(
				long userId, long groupId, long originalFragmentEntryLinkId,
				long fragmentEntryId, long classNameId, long classPK,
				String rendererKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntryLink(
			userId, groupId, originalFragmentEntryLinkId, fragmentEntryId,
			classNameId, classPK, rendererKey, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntryLink(long, long, long, long, long, long,
	 String, String, String, String, String, String, int, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntryLink
			addFragmentEntryLink(
				long userId, long groupId, long originalFragmentEntryLinkId,
				long fragmentEntryId, long classNameId, long classPK,
				String css, String html, String js, String editableValues,
				int position,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntryLink(
			userId, groupId, originalFragmentEntryLinkId, fragmentEntryId,
			classNameId, classPK, css, html, js, editableValues, position,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntryLink(long, long, long, long, long, long,
	 String, String, String, String, String, String, int, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntryLink
			addFragmentEntryLink(
				long userId, long groupId, long originalFragmentEntryLinkId,
				long fragmentEntryId, long classNameId, long classPK,
				String css, String html, String js, String editableValues,
				int position, String rendererKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntryLink(
			userId, groupId, originalFragmentEntryLinkId, fragmentEntryId,
			classNameId, classPK, css, html, js, editableValues, position,
			rendererKey, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntryLink(long, long, long, long, long, long,
	 String, String, String, String, String, String, int, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntryLink
			addFragmentEntryLink(
				long userId, long groupId, long originalFragmentEntryLinkId,
				long fragmentEntryId, long classNameId, long classPK,
				String css, String html, String js, String editableValues,
				String namespace, int position, String rendererKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntryLink(
			userId, groupId, originalFragmentEntryLinkId, fragmentEntryId,
			classNameId, classPK, css, html, js, editableValues, namespace,
			position, rendererKey, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntryLink
			addFragmentEntryLink(
				long userId, long groupId, long originalFragmentEntryLinkId,
				long fragmentEntryId, long classNameId, long classPK,
				String css, String html, String js, String configuration,
				String editableValues, String namespace, int position,
				String rendererKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntryLink(
			userId, groupId, originalFragmentEntryLinkId, fragmentEntryId,
			classNameId, classPK, css, html, js, configuration, editableValues,
			namespace, position, rendererKey, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntryLink(long, long, long, long, long, long,
	 String, String, String, String, String, String, int, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntryLink
			addFragmentEntryLink(
				long userId, long groupId, long fragmentEntryId,
				long classNameId, long classPK, String css, String html,
				String js, String editableValues, int position,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntryLink(
			userId, groupId, fragmentEntryId, classNameId, classPK, css, html,
			js, editableValues, position, serviceContext);
	}

	/**
	 * Creates a new fragment entry link with the primary key. Does not add the fragment entry link to the database.
	 *
	 * @param fragmentEntryLinkId the primary key for the new fragment entry link
	 * @return the new fragment entry link
	 */
	public static com.liferay.fragment.model.FragmentEntryLink
		createFragmentEntryLink(long fragmentEntryLinkId) {

		return getService().createFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	 * Deletes the fragment entry link from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryLink the fragment entry link
	 * @return the fragment entry link that was removed
	 */
	public static com.liferay.fragment.model.FragmentEntryLink
		deleteFragmentEntryLink(
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
	public static com.liferay.fragment.model.FragmentEntryLink
			deleteFragmentEntryLink(long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFragmentEntryLink(fragmentEntryLinkId);
	}

	public static void deleteFragmentEntryLinks(long groupId) {
		getService().deleteFragmentEntryLinks(groupId);
	}

	public static void deleteFragmentEntryLinks(long[] fragmentEntryLinkIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteFragmentEntryLinks(fragmentEntryLinkIds);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		deleteLayoutPageTemplateEntryFragmentEntryLinks(
			long groupId, long classNameId, long classPK) {

		return getService().deleteLayoutPageTemplateEntryFragmentEntryLinks(
			groupId, classNameId, classPK);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.fragment.model.FragmentEntryLink
		fetchFragmentEntryLink(long fragmentEntryLinkId) {

		return getService().fetchFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	 * Returns the fragment entry link matching the UUID and group.
	 *
	 * @param uuid the fragment entry link's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	public static com.liferay.fragment.model.FragmentEntryLink
		fetchFragmentEntryLinkByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchFragmentEntryLinkByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static int getClassedModelFragmentEntryLinksCount(
		long groupId, long classNameId, long classPK) {

		return getService().getClassedModelFragmentEntryLinksCount(
			groupId, classNameId, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns the fragment entry link with the primary key.
	 *
	 * @param fragmentEntryLinkId the primary key of the fragment entry link
	 * @return the fragment entry link
	 * @throws PortalException if a fragment entry link with the primary key could not be found
	 */
	public static com.liferay.fragment.model.FragmentEntryLink
			getFragmentEntryLink(long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	 * Returns the fragment entry link matching the UUID and group.
	 *
	 * @param uuid the fragment entry link's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment entry link
	 * @throws PortalException if a matching fragment entry link could not be found
	 */
	public static com.liferay.fragment.model.FragmentEntryLink
			getFragmentEntryLinkByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFragmentEntryLinkByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the fragment entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of fragment entry links
	 */
	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		getFragmentEntryLinks(int start, int end) {

		return getService().getFragmentEntryLinks(start, end);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		getFragmentEntryLinks(
			long groupId, long fragmentEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntryLink>
					orderByComparator) {

		return getService().getFragmentEntryLinks(
			groupId, fragmentEntryId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		getFragmentEntryLinks(long groupId, long classNameId, long classPK) {

		return getService().getFragmentEntryLinks(
			groupId, classNameId, classPK);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		getFragmentEntryLinks(
			long groupId, long fragmentEntryId, long classNameId,
			int layoutPageTemplateType, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntryLink>
					orderByComparator) {

		return getService().getFragmentEntryLinks(
			groupId, fragmentEntryId, classNameId, layoutPageTemplateType,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		getFragmentEntryLinks(
			long groupId, long fragmentEntryId, long classNameId, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntryLink>
					orderByComparator) {

		return getService().getFragmentEntryLinks(
			groupId, fragmentEntryId, classNameId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		getFragmentEntryLinks(String rendererKey) {

		return getService().getFragmentEntryLinks(rendererKey);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		getFragmentEntryLinksByFragmentEntryId(long fragmentEntryId) {

		return getService().getFragmentEntryLinksByFragmentEntryId(
			fragmentEntryId);
	}

	/**
	 * Returns all the fragment entry links matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment entry links
	 * @param companyId the primary key of the company
	 * @return the matching fragment entry links, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		getFragmentEntryLinksByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getFragmentEntryLinksByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of fragment entry links matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment entry links
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching fragment entry links, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.fragment.model.FragmentEntryLink>
		getFragmentEntryLinksByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentEntryLink>
					orderByComparator) {

		return getService().getFragmentEntryLinksByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of fragment entry links.
	 *
	 * @return the number of fragment entry links
	 */
	public static int getFragmentEntryLinksCount() {
		return getService().getFragmentEntryLinksCount();
	}

	public static int getFragmentEntryLinksCount(
		long groupId, long fragmentEntryId) {

		return getService().getFragmentEntryLinksCount(
			groupId, fragmentEntryId);
	}

	public static int getFragmentEntryLinksCount(
		long groupId, long fragmentEntryId, long classNameId) {

		return getService().getFragmentEntryLinksCount(
			groupId, fragmentEntryId, classNameId);
	}

	public static int getFragmentEntryLinksCount(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateType) {

		return getService().getFragmentEntryLinksCount(
			groupId, fragmentEntryId, classNameId, layoutPageTemplateType);
	}

	public static int getFragmentEntryLinksCountByFragmentEntryId(
		long fragmentEntryId) {

		return getService().getFragmentEntryLinksCountByFragmentEntryId(
			fragmentEntryId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void updateClassedModel(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateClassedModel(classNameId, classPK);
	}

	/**
	 * Updates the fragment entry link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryLink the fragment entry link
	 * @return the fragment entry link that was updated
	 */
	public static com.liferay.fragment.model.FragmentEntryLink
		updateFragmentEntryLink(
			com.liferay.fragment.model.FragmentEntryLink fragmentEntryLink) {

		return getService().updateFragmentEntryLink(fragmentEntryLink);
	}

	public static com.liferay.fragment.model.FragmentEntryLink
			updateFragmentEntryLink(long fragmentEntryLinkId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntryLink(
			fragmentEntryLinkId, position);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #updateFragmentEntryLink(long, long, long, long, long, long,
	 String, String, String, String, String, String, int,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntryLink
			updateFragmentEntryLink(
				long userId, long fragmentEntryLinkId,
				long originalFragmentEntryLinkId, long fragmentEntryId,
				long classNameId, long classPK, String css, String html,
				String js, String editableValues, int position,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntryLink(
			userId, fragmentEntryLinkId, originalFragmentEntryLinkId,
			fragmentEntryId, classNameId, classPK, css, html, js,
			editableValues, position, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #updateFragmentEntryLink(long, long, long, long, long, long,
	 String, String, String, String, String, String, int,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntryLink
			updateFragmentEntryLink(
				long userId, long fragmentEntryLinkId,
				long originalFragmentEntryLinkId, long fragmentEntryId,
				long classNameId, long classPK, String css, String html,
				String js, String editableValues, String namespace,
				int position,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntryLink(
			userId, fragmentEntryLinkId, originalFragmentEntryLinkId,
			fragmentEntryId, classNameId, classPK, css, html, js,
			editableValues, namespace, position, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntryLink
			updateFragmentEntryLink(
				long userId, long fragmentEntryLinkId,
				long originalFragmentEntryLinkId, long fragmentEntryId,
				long classNameId, long classPK, String css, String html,
				String js, String configuration, String editableValues,
				String namespace, int position,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntryLink(
			userId, fragmentEntryLinkId, originalFragmentEntryLinkId,
			fragmentEntryId, classNameId, classPK, css, html, js, configuration,
			editableValues, namespace, position, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntryLink
			updateFragmentEntryLink(
				long fragmentEntryLinkId, String editableValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues);
	}

	public static com.liferay.fragment.model.FragmentEntryLink
			updateFragmentEntryLink(
				long fragmentEntryLinkId, String editableValues,
				boolean updateClassedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues, updateClassedModel);
	}

	public static void updateFragmentEntryLinks(
			long userId, long groupId, long classNameId, long classPK,
			long[] fragmentEntryIds, String editableValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateFragmentEntryLinks(
			userId, groupId, classNameId, classPK, fragmentEntryIds,
			editableValues, serviceContext);
	}

	public static void updateFragmentEntryLinks(
			java.util.Map<Long, String> fragmentEntryLinksEditableValuesMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateFragmentEntryLinks(
			fragmentEntryLinksEditableValuesMap);
	}

	public static void updateLatestChanges(long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateLatestChanges(fragmentEntryLinkId);
	}

	public static FragmentEntryLinkLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<FragmentEntryLinkLocalService, FragmentEntryLinkLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			FragmentEntryLinkLocalService.class);

		ServiceTracker
			<FragmentEntryLinkLocalService, FragmentEntryLinkLocalService>
				serviceTracker =
					new ServiceTracker
						<FragmentEntryLinkLocalService,
						 FragmentEntryLinkLocalService>(
							 bundle.getBundleContext(),
							 FragmentEntryLinkLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}