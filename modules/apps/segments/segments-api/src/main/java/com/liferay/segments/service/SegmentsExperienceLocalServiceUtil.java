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

package com.liferay.segments.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsExperience;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for SegmentsExperience. This utility wraps
 * <code>com.liferay.segments.service.impl.SegmentsExperienceLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceLocalService
 * @generated
 */
public class SegmentsExperienceLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperienceLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SegmentsExperience addSegmentsExperience(
			long userId, long groupId, long segmentsEntryId, long classNameId,
			long classPK, Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSegmentsExperience(
			userId, groupId, segmentsEntryId, classNameId, classPK, nameMap,
			active, typeSettingsUnicodeProperties, serviceContext);
	}

	public static SegmentsExperience addSegmentsExperience(
			long userId, long groupId, long segmentsEntryId, long classNameId,
			long classPK, Map<java.util.Locale, String> nameMap, int priority,
			boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSegmentsExperience(
			userId, groupId, segmentsEntryId, classNameId, classPK, nameMap,
			priority, active, typeSettingsUnicodeProperties, serviceContext);
	}

	public static SegmentsExperience addSegmentsExperience(
			long userId, long groupId, long segmentsEntryId,
			String segmentsExperienceKey, long classNameId, long classPK,
			Map<java.util.Locale, String> nameMap, int priority, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSegmentsExperience(
			userId, groupId, segmentsEntryId, segmentsExperienceKey,
			classNameId, classPK, nameMap, priority, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	/**
	 * Adds the segments experience to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperienceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperience the segments experience
	 * @return the segments experience that was added
	 */
	public static SegmentsExperience addSegmentsExperience(
		SegmentsExperience segmentsExperience) {

		return getService().addSegmentsExperience(segmentsExperience);
	}

	public static SegmentsExperience appendSegmentsExperience(
			long userId, long groupId, long segmentsEntryId, long classNameId,
			long classPK, Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().appendSegmentsExperience(
			userId, groupId, segmentsEntryId, classNameId, classPK, nameMap,
			active, serviceContext);
	}

	public static SegmentsExperience appendSegmentsExperience(
			long userId, long groupId, long segmentsEntryId, long classNameId,
			long classPK, Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().appendSegmentsExperience(
			userId, groupId, segmentsEntryId, classNameId, classPK, nameMap,
			active, typeSettingsUnicodeProperties, serviceContext);
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
	 * Creates a new segments experience with the primary key. Does not add the segments experience to the database.
	 *
	 * @param segmentsExperienceId the primary key for the new segments experience
	 * @return the new segments experience
	 */
	public static SegmentsExperience createSegmentsExperience(
		long segmentsExperienceId) {

		return getService().createSegmentsExperience(segmentsExperienceId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteSegmentsEntrySegmentsExperiences(
			long segmentsEntryId)
		throws PortalException {

		getService().deleteSegmentsEntrySegmentsExperiences(segmentsEntryId);
	}

	/**
	 * Deletes the segments experience with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperienceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperienceId the primary key of the segments experience
	 * @return the segments experience that was removed
	 * @throws PortalException if a segments experience with the primary key could not be found
	 */
	public static SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException {

		return getService().deleteSegmentsExperience(segmentsExperienceId);
	}

	/**
	 * Deletes the segments experience from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperienceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperience the segments experience
	 * @return the segments experience that was removed
	 * @throws PortalException
	 */
	public static SegmentsExperience deleteSegmentsExperience(
			SegmentsExperience segmentsExperience)
		throws PortalException {

		return getService().deleteSegmentsExperience(segmentsExperience);
	}

	public static void deleteSegmentsExperiences(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		getService().deleteSegmentsExperiences(groupId, classNameId, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperienceModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperienceModelImpl</code>.
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

	public static long fetchDefaultSegmentsExperienceId(long plid) {
		return getService().fetchDefaultSegmentsExperienceId(plid);
	}

	public static SegmentsExperience fetchSegmentsExperience(
		long segmentsExperienceId) {

		return getService().fetchSegmentsExperience(segmentsExperienceId);
	}

	public static SegmentsExperience fetchSegmentsExperience(
		long groupId, long classNameId, long classPK, int priority) {

		return getService().fetchSegmentsExperience(
			groupId, classNameId, classPK, priority);
	}

	public static SegmentsExperience fetchSegmentsExperience(
		long groupId, String segmentsExperienceKey, long classNameId,
		long classPK) {

		return getService().fetchSegmentsExperience(
			groupId, segmentsExperienceKey, classNameId, classPK);
	}

	/**
	 * Returns the segments experience matching the UUID and group.
	 *
	 * @param uuid the segments experience's UUID
	 * @param groupId the primary key of the group
	 * @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	public static SegmentsExperience fetchSegmentsExperienceByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchSegmentsExperienceByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
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

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the segments experience with the primary key.
	 *
	 * @param segmentsExperienceId the primary key of the segments experience
	 * @return the segments experience
	 * @throws PortalException if a segments experience with the primary key could not be found
	 */
	public static SegmentsExperience getSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException {

		return getService().getSegmentsExperience(segmentsExperienceId);
	}

	public static SegmentsExperience getSegmentsExperience(
			long groupId, String segmentsExperienceKey, long classNameId,
			long classPK)
		throws PortalException {

		return getService().getSegmentsExperience(
			groupId, segmentsExperienceKey, classNameId, classPK);
	}

	/**
	 * Returns the segments experience matching the UUID and group.
	 *
	 * @param uuid the segments experience's UUID
	 * @param groupId the primary key of the group
	 * @return the matching segments experience
	 * @throws PortalException if a matching segments experience could not be found
	 */
	public static SegmentsExperience getSegmentsExperienceByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getSegmentsExperienceByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the segments experiences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperienceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @return the range of segments experiences
	 */
	public static List<SegmentsExperience> getSegmentsExperiences(
		int start, int end) {

		return getService().getSegmentsExperiences(start, end);
	}

	public static List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long classNameId, long classPK) {

		return getService().getSegmentsExperiences(
			groupId, classNameId, classPK);
	}

	public static List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long classNameId, long classPK, boolean active)
		throws PortalException {

		return getService().getSegmentsExperiences(
			groupId, classNameId, classPK, active);
	}

	public static List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long classNameId, long classPK, boolean active, int start,
		int end, OrderByComparator<SegmentsExperience> orderByComparator) {

		return getService().getSegmentsExperiences(
			groupId, classNameId, classPK, active, start, end,
			orderByComparator);
	}

	public static List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active) {

		return getService().getSegmentsExperiences(
			groupId, segmentsEntryIds, classNameId, classPK, active);
	}

	public static List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return getService().getSegmentsExperiences(
			groupId, segmentsEntryIds, classNameId, classPK, active, start, end,
			orderByComparator);
	}

	/**
	 * Returns all the segments experiences matching the UUID and company.
	 *
	 * @param uuid the UUID of the segments experiences
	 * @param companyId the primary key of the company
	 * @return the matching segments experiences, or an empty list if no matches were found
	 */
	public static List<SegmentsExperience>
		getSegmentsExperiencesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getSegmentsExperiencesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of segments experiences matching the UUID and company.
	 *
	 * @param uuid the UUID of the segments experiences
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of segments experiences
	 * @param end the upper bound of the range of segments experiences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching segments experiences, or an empty list if no matches were found
	 */
	public static List<SegmentsExperience>
		getSegmentsExperiencesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<SegmentsExperience> orderByComparator) {

		return getService().getSegmentsExperiencesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of segments experiences.
	 *
	 * @return the number of segments experiences
	 */
	public static int getSegmentsExperiencesCount() {
		return getService().getSegmentsExperiencesCount();
	}

	public static int getSegmentsExperiencesCount(
		long groupId, long classNameId, long classPK) {

		return getService().getSegmentsExperiencesCount(
			groupId, classNameId, classPK);
	}

	public static int getSegmentsExperiencesCount(
		long groupId, long classNameId, long classPK, boolean active) {

		return getService().getSegmentsExperiencesCount(
			groupId, classNameId, classPK, active);
	}

	public static SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<java.util.Locale, String> nameMap, boolean active)
		throws PortalException {

		return getService().updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, active);
	}

	public static SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties)
		throws PortalException {

		return getService().updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, active,
			typeSettingsUnicodeProperties);
	}

	/**
	 * Updates the segments experience in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperienceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperience the segments experience
	 * @return the segments experience that was updated
	 */
	public static SegmentsExperience updateSegmentsExperience(
		SegmentsExperience segmentsExperience) {

		return getService().updateSegmentsExperience(segmentsExperience);
	}

	public static SegmentsExperience updateSegmentsExperienceActive(
			long segmentsExperienceId, boolean active)
		throws PortalException {

		return getService().updateSegmentsExperienceActive(
			segmentsExperienceId, active);
	}

	public static SegmentsExperience updateSegmentsExperiencePriority(
			long segmentsExperienceId, int newPriority)
		throws PortalException {

		return getService().updateSegmentsExperiencePriority(
			segmentsExperienceId, newPriority);
	}

	public static SegmentsExperienceLocalService getService() {
		return _service;
	}

	private static volatile SegmentsExperienceLocalService _service;

}