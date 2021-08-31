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

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

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

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentEntryLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the fragment entry link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryLink the fragment entry link
	 * @return the fragment entry link that was added
	 */
	public static FragmentEntryLink addFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink) {

		return getService().addFragmentEntryLink(fragmentEntryLink);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addFragmentEntryLink(long, long, long, long, long, long,
	 String, String, String, String, String, String, int, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static FragmentEntryLink addFragmentEntryLink(
			long userId, long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long segmentsExperienceId, long classNameId,
			long classPK, String css, String html, String js,
			String configuration, String editableValues, String namespace,
			int position, String rendererKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFragmentEntryLink(
			userId, groupId, originalFragmentEntryLinkId, fragmentEntryId,
			segmentsExperienceId, classNameId, classPK, css, html, js,
			configuration, editableValues, namespace, position, rendererKey,
			serviceContext);
	}

	public static FragmentEntryLink addFragmentEntryLink(
			long userId, long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long segmentsExperienceId, long plid,
			String css, String html, String js, String configuration,
			String editableValues, String namespace, int position,
			String rendererKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFragmentEntryLink(
			userId, groupId, originalFragmentEntryLinkId, fragmentEntryId,
			segmentsExperienceId, plid, css, html, js, configuration,
			editableValues, namespace, position, rendererKey, serviceContext);
	}

	/**
	 * Creates a new fragment entry link with the primary key. Does not add the fragment entry link to the database.
	 *
	 * @param fragmentEntryLinkId the primary key for the new fragment entry link
	 * @return the new fragment entry link
	 */
	public static FragmentEntryLink createFragmentEntryLink(
		long fragmentEntryLinkId) {

		return getService().createFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the fragment entry link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryLink the fragment entry link
	 * @return the fragment entry link that was removed
	 */
	public static FragmentEntryLink deleteFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink) {

		return getService().deleteFragmentEntryLink(fragmentEntryLink);
	}

	/**
	 * Deletes the fragment entry link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryLinkId the primary key of the fragment entry link
	 * @return the fragment entry link that was removed
	 * @throws PortalException if a fragment entry link with the primary key could not be found
	 */
	public static FragmentEntryLink deleteFragmentEntryLink(
			long fragmentEntryLinkId)
		throws PortalException {

		return getService().deleteFragmentEntryLink(fragmentEntryLinkId);
	}

	public static void deleteFragmentEntryLinks(long groupId) {
		getService().deleteFragmentEntryLinks(groupId);
	}

	public static void deleteFragmentEntryLinks(long[] fragmentEntryLinkIds)
		throws PortalException {

		getService().deleteFragmentEntryLinks(fragmentEntryLinkIds);
	}

	public static List<FragmentEntryLink>
		deleteLayoutPageTemplateEntryFragmentEntryLinks(
			long groupId, long plid) {

		return getService().deleteLayoutPageTemplateEntryFragmentEntryLinks(
			groupId, plid);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #deleteLayoutPageTemplateEntryFragmentEntryLinks(long, long)}
	 */
	@Deprecated
	public static List<FragmentEntryLink>
		deleteLayoutPageTemplateEntryFragmentEntryLinks(
			long groupId, long classNameId, long classPK) {

		return getService().deleteLayoutPageTemplateEntryFragmentEntryLinks(
			groupId, classNameId, classPK);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static FragmentEntryLink fetchFragmentEntryLink(
		long fragmentEntryLinkId) {

		return getService().fetchFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	 * Returns the fragment entry link matching the UUID and group.
	 *
	 * @param uuid the fragment entry link's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	public static FragmentEntryLink fetchFragmentEntryLinkByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchFragmentEntryLinkByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<FragmentEntryLink>
		getAllFragmentEntryLinksByFragmentEntryId(
			long groupId, long fragmentEntryId, int start, int end,
			OrderByComparator<FragmentEntryLink> orderByComparator) {

		return getService().getAllFragmentEntryLinksByFragmentEntryId(
			groupId, fragmentEntryId, start, end, orderByComparator);
	}

	public static int getAllFragmentEntryLinksCountByFragmentEntryId(
		long groupId, long fragmentEntryId) {

		return getService().getAllFragmentEntryLinksCountByFragmentEntryId(
			groupId, fragmentEntryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFragmentEntryLinksCountByPlid(long, long)}
	 */
	@Deprecated
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
	public static FragmentEntryLink getFragmentEntryLink(
			long fragmentEntryLinkId)
		throws PortalException {

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
	public static FragmentEntryLink getFragmentEntryLinkByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

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
	public static List<FragmentEntryLink> getFragmentEntryLinks(
		int start, int end) {

		return getService().getFragmentEntryLinks(start, end);
	}

	public static List<FragmentEntryLink> getFragmentEntryLinks(
		int type, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return getService().getFragmentEntryLinks(
			type, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getAllFragmentEntryLinksByFragmentEntryId(long, long, int,
	 int, OrderByComparator)}
	 */
	@Deprecated
	public static List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return getService().getFragmentEntryLinks(
			groupId, fragmentEntryId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFragmentEntryLinksByPlid(long, long)}
	 */
	@Deprecated
	public static List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long classNameId, long classPK) {

		return getService().getFragmentEntryLinks(
			groupId, classNameId, classPK);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getLayoutPageTemplateFragmentEntryLinksByFragmentEntryId(
	 long, long, int, int, int, OrderByComparator)}
	 */
	@Deprecated
	public static List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateType, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return getService().getFragmentEntryLinks(
			groupId, fragmentEntryId, classNameId, layoutPageTemplateType,
			start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFragmentEntryLinks(long, long, int, int,
	 OrderByComparator)}
	 */
	@Deprecated
	public static List<FragmentEntryLink> getFragmentEntryLinks(
		long groupId, long fragmentEntryId, long classNameId, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator) {

		return getService().getFragmentEntryLinks(
			groupId, fragmentEntryId, classNameId, start, end,
			orderByComparator);
	}

	public static List<FragmentEntryLink> getFragmentEntryLinks(
		String rendererKey) {

		return getService().getFragmentEntryLinks(rendererKey);
	}

	public static List<FragmentEntryLink>
		getFragmentEntryLinksByFragmentEntryId(long fragmentEntryId) {

		return getService().getFragmentEntryLinksByFragmentEntryId(
			fragmentEntryId);
	}

	public static List<FragmentEntryLink> getFragmentEntryLinksByPlid(
		long groupId, long plid) {

		return getService().getFragmentEntryLinksByPlid(groupId, plid);
	}

	public static List<FragmentEntryLink>
		getFragmentEntryLinksBySegmentsExperienceId(
			long groupId, long segmentsExperienceId, long plid) {

		return getService().getFragmentEntryLinksBySegmentsExperienceId(
			groupId, segmentsExperienceId, plid);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFragmentEntryLinksBySegmentsExperienceId(long, long,
	 long)}
	 */
	@Deprecated
	public static List<FragmentEntryLink>
		getFragmentEntryLinksBySegmentsExperienceId(
			long groupId, long segmentsExperienceId, long classNameId,
			long classPK) {

		return getService().getFragmentEntryLinksBySegmentsExperienceId(
			groupId, segmentsExperienceId, classNameId, classPK);
	}

	public static List<FragmentEntryLink>
		getFragmentEntryLinksBySegmentsExperienceId(
			long groupId, long segmentsExperienceId, long plid,
			String rendererKey) {

		return getService().getFragmentEntryLinksBySegmentsExperienceId(
			groupId, segmentsExperienceId, plid, rendererKey);
	}

	/**
	 * Returns all the fragment entry links matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment entry links
	 * @param companyId the primary key of the company
	 * @return the matching fragment entry links, or an empty list if no matches were found
	 */
	public static List<FragmentEntryLink>
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
	public static List<FragmentEntryLink>
		getFragmentEntryLinksByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<FragmentEntryLink> orderByComparator) {

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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getAllFragmentEntryLinksCountByFragmentEntryId(long, long)}
	 */
	@Deprecated
	public static int getFragmentEntryLinksCount(
		long groupId, long fragmentEntryId) {

		return getService().getFragmentEntryLinksCount(
			groupId, fragmentEntryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getFragmentEntryLinksCount(long, long)}
	 */
	@Deprecated
	public static int getFragmentEntryLinksCount(
		long groupId, long fragmentEntryId, long classNameId) {

		return getService().getFragmentEntryLinksCount(
			groupId, fragmentEntryId, classNameId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #getLayoutPageTemplateFragmentEntryLinksCountByFragmentEntryId(
	 long, long, int)}
	 */
	@Deprecated
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

	public static int getFragmentEntryLinksCountByPlid(
		long groupId, long plid) {

		return getService().getFragmentEntryLinksCountByPlid(groupId, plid);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static List<FragmentEntryLink>
		getLayoutFragmentEntryLinksByFragmentEntryId(
			long groupId, long fragmentEntryId, int start, int end,
			OrderByComparator<FragmentEntryLink> orderByComparator) {

		return getService().getLayoutFragmentEntryLinksByFragmentEntryId(
			groupId, fragmentEntryId, start, end, orderByComparator);
	}

	public static int getLayoutFragmentEntryLinksCountByFragmentEntryId(
		long groupId, long fragmentEntryId) {

		return getService().getLayoutFragmentEntryLinksCountByFragmentEntryId(
			groupId, fragmentEntryId);
	}

	public static List<FragmentEntryLink>
		getLayoutPageTemplateFragmentEntryLinksByFragmentEntryId(
			long groupId, long fragmentEntryId, int layoutPageTemplateType,
			int start, int end,
			OrderByComparator<FragmentEntryLink> orderByComparator) {

		return getService().
			getLayoutPageTemplateFragmentEntryLinksByFragmentEntryId(
				groupId, fragmentEntryId, layoutPageTemplateType, start, end,
				orderByComparator);
	}

	public static int
		getLayoutPageTemplateFragmentEntryLinksCountByFragmentEntryId(
			long groupId, long fragmentEntryId, int layoutPageTemplateType) {

		return getService().
			getLayoutPageTemplateFragmentEntryLinksCountByFragmentEntryId(
				groupId, fragmentEntryId, layoutPageTemplateType);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void updateClassedModel(long plid) {
		getService().updateClassedModel(plid);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateClassedModel(long)}
	 */
	@Deprecated
	public static void updateClassedModel(long classNameId, long classPK)
		throws PortalException {

		getService().updateClassedModel(classNameId, classPK);
	}

	/**
	 * Updates the fragment entry link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FragmentEntryLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fragmentEntryLink the fragment entry link
	 * @return the fragment entry link that was updated
	 */
	public static FragmentEntryLink updateFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink) {

		return getService().updateFragmentEntryLink(fragmentEntryLink);
	}

	public static FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, int position)
		throws PortalException {

		return getService().updateFragmentEntryLink(
			fragmentEntryLinkId, position);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFragmentEntryLink(long, long, long, long, long,
	 String, String, String, String, String, String, int,
	 ServiceContext)}
	 */
	@Deprecated
	public static FragmentEntryLink updateFragmentEntryLink(
			long userId, long fragmentEntryLinkId,
			long originalFragmentEntryLinkId, long fragmentEntryId,
			long classNameId, long classPK, String css, String html, String js,
			String configuration, String editableValues, String namespace,
			int position,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFragmentEntryLink(
			userId, fragmentEntryLinkId, originalFragmentEntryLinkId,
			fragmentEntryId, classNameId, classPK, css, html, js, configuration,
			editableValues, namespace, position, serviceContext);
	}

	public static FragmentEntryLink updateFragmentEntryLink(
			long userId, long fragmentEntryLinkId,
			long originalFragmentEntryLinkId, long fragmentEntryId, long plid,
			String css, String html, String js, String configuration,
			String editableValues, String namespace, int position,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFragmentEntryLink(
			userId, fragmentEntryLinkId, originalFragmentEntryLinkId,
			fragmentEntryId, plid, css, html, js, configuration, editableValues,
			namespace, position, serviceContext);
	}

	public static FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues)
		throws PortalException {

		return getService().updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues);
	}

	public static FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues,
			boolean updateClassedModel)
		throws PortalException {

		return getService().updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues, updateClassedModel);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFragmentEntryLinks(long, long, long, long[], String,
	 ServiceContext)}
	 */
	@Deprecated
	public static void updateFragmentEntryLinks(
			long userId, long groupId, long classNameId, long classPK,
			long[] fragmentEntryIds, String editableValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().updateFragmentEntryLinks(
			userId, groupId, classNameId, classPK, fragmentEntryIds,
			editableValues, serviceContext);
	}

	public static void updateFragmentEntryLinks(
			long userId, long groupId, long plid, long[] fragmentEntryIds,
			String editableValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().updateFragmentEntryLinks(
			userId, groupId, plid, fragmentEntryIds, editableValues,
			serviceContext);
	}

	public static void updateFragmentEntryLinks(
			Map<Long, String> fragmentEntryLinksEditableValuesMap)
		throws PortalException {

		getService().updateFragmentEntryLinks(
			fragmentEntryLinksEditableValuesMap);
	}

	public static void updateLatestChanges(long fragmentEntryLinkId)
		throws PortalException {

		getService().updateLatestChanges(fragmentEntryLinkId);
	}

	public static FragmentEntryLinkLocalService getService() {
		return _service;
	}

	private static volatile FragmentEntryLinkLocalService _service;

}