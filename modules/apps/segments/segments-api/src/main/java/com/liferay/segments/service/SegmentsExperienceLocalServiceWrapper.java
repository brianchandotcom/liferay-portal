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

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.segments.model.SegmentsExperience;

/**
 * Provides a wrapper for {@link SegmentsExperienceLocalService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceLocalService
 * @generated
 */
public class SegmentsExperienceLocalServiceWrapper
	implements SegmentsExperienceLocalService,
			   ServiceWrapper<SegmentsExperienceLocalService> {

	public SegmentsExperienceLocalServiceWrapper() {
		this(null);
	}

	public SegmentsExperienceLocalServiceWrapper(
		SegmentsExperienceLocalService segmentsExperienceLocalService) {

		_segmentsExperienceLocalService = segmentsExperienceLocalService;
	}

	@Override
	public SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.addSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	@Override
	public SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> nameMap, int priority,
			boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.addSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, priority, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	@Override
	public SegmentsExperience addSegmentsExperience(
			long segmentsEntryId, String segmentsExperienceKey,
			long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> nameMap, int priority,
			boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.addSegmentsExperience(
			segmentsEntryId, segmentsExperienceKey, classNameId, classPK,
			nameMap, priority, active, typeSettingsUnicodeProperties,
			serviceContext);
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
	@Override
	public SegmentsExperience addSegmentsExperience(
		SegmentsExperience segmentsExperience) {

		return _segmentsExperienceLocalService.addSegmentsExperience(
			segmentsExperience);
	}

	@Override
	public SegmentsExperience appendSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.appendSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, active,
			serviceContext);
	}

	@Override
	public SegmentsExperience appendSegmentsExperience(
			long segmentsEntryId, long classNameId, long classPK,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.appendSegmentsExperience(
			segmentsEntryId, classNameId, classPK, nameMap, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new segments experience with the primary key. Does not add the segments experience to the database.
	 *
	 * @param segmentsExperienceId the primary key for the new segments experience
	 * @return the new segments experience
	 */
	@Override
	public SegmentsExperience createSegmentsExperience(
		long segmentsExperienceId) {

		return _segmentsExperienceLocalService.createSegmentsExperience(
			segmentsExperienceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public void deleteSegmentsEntrySegmentsExperiences(long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsExperienceLocalService.deleteSegmentsEntrySegmentsExperiences(
			segmentsEntryId);
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
	@Override
	public SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperienceId);
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
	@Override
	public SegmentsExperience deleteSegmentsExperience(
			SegmentsExperience segmentsExperience)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperience);
	}

	@Override
	public void deleteSegmentsExperiences(
			long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsExperienceLocalService.deleteSegmentsExperiences(
			groupId, classNameId, classPK);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _segmentsExperienceLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _segmentsExperienceLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _segmentsExperienceLocalService.dynamicQuery();
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

		return _segmentsExperienceLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _segmentsExperienceLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _segmentsExperienceLocalService.dynamicQuery(
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

		return _segmentsExperienceLocalService.dynamicQueryCount(dynamicQuery);
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

		return _segmentsExperienceLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long segmentsExperienceId) {

		return _segmentsExperienceLocalService.fetchSegmentsExperience(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long groupId, long classNameId, long classPK, int priority) {

		return _segmentsExperienceLocalService.fetchSegmentsExperience(
			groupId, classNameId, classPK, priority);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long groupId, String segmentsExperienceKey, long classNameId,
		long classPK) {

		return _segmentsExperienceLocalService.fetchSegmentsExperience(
			groupId, segmentsExperienceKey, classNameId, classPK);
	}

	/**
	 * Returns the segments experience matching the UUID and group.
	 *
	 * @param uuid the segments experience's UUID
	 * @param groupId the primary key of the group
	 * @return the matching segments experience, or <code>null</code> if a matching segments experience could not be found
	 */
	@Override
	public SegmentsExperience fetchSegmentsExperienceByUuidAndGroupId(
		String uuid, long groupId) {

		return _segmentsExperienceLocalService.
			fetchSegmentsExperienceByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _segmentsExperienceLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _segmentsExperienceLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _segmentsExperienceLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsExperienceLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the segments experience with the primary key.
	 *
	 * @param segmentsExperienceId the primary key of the segments experience
	 * @return the segments experience
	 * @throws PortalException if a segments experience with the primary key could not be found
	 */
	@Override
	public SegmentsExperience getSegmentsExperience(long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.getSegmentsExperience(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience getSegmentsExperience(
			long groupId, String segmentsExperienceKey, long classNameId,
			long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.getSegmentsExperience(
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
	@Override
	public SegmentsExperience getSegmentsExperienceByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.
			getSegmentsExperienceByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
		int start, int end) {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			start, end);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long classNameId, long classPK) {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, classNameId, classPK);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long classNameId, long classPK, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, classNameId, classPK, active);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long classNameId, long classPK, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience>
			orderByComparator) {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, classNameId, classPK, active, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active) {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, segmentsEntryIds, classNameId, classPK, active);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long[] segmentsEntryIds, long classNameId, long classPK,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience>
			orderByComparator) {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
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
	@Override
	public java.util.List<SegmentsExperience>
		getSegmentsExperiencesByUuidAndCompanyId(String uuid, long companyId) {

		return _segmentsExperienceLocalService.
			getSegmentsExperiencesByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List<SegmentsExperience>
		getSegmentsExperiencesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience>
				orderByComparator) {

		return _segmentsExperienceLocalService.
			getSegmentsExperiencesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of segments experiences.
	 *
	 * @return the number of segments experiences
	 */
	@Override
	public int getSegmentsExperiencesCount() {
		return _segmentsExperienceLocalService.getSegmentsExperiencesCount();
	}

	@Override
	public int getSegmentsExperiencesCount(
		long groupId, long classNameId, long classPK) {

		return _segmentsExperienceLocalService.getSegmentsExperiencesCount(
			groupId, classNameId, classPK);
	}

	@Override
	public int getSegmentsExperiencesCount(
		long groupId, long classNameId, long classPK, boolean active) {

		return _segmentsExperienceLocalService.getSegmentsExperiencesCount(
			groupId, classNameId, classPK, active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			java.util.Map<java.util.Locale, String> nameMap, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.updateSegmentsExperience(
			segmentsExperienceId, segmentsEntryId, nameMap, active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long segmentsExperienceId, long segmentsEntryId,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.updateSegmentsExperience(
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
	@Override
	public SegmentsExperience updateSegmentsExperience(
		SegmentsExperience segmentsExperience) {

		return _segmentsExperienceLocalService.updateSegmentsExperience(
			segmentsExperience);
	}

	@Override
	public SegmentsExperience updateSegmentsExperienceActive(
			long segmentsExperienceId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.updateSegmentsExperienceActive(
			segmentsExperienceId, active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperiencePriority(
			long segmentsExperienceId, int newPriority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.updateSegmentsExperiencePriority(
			segmentsExperienceId, newPriority);
	}

	@Override
	public CTPersistence<SegmentsExperience> getCTPersistence() {
		return _segmentsExperienceLocalService.getCTPersistence();
	}

	@Override
	public Class<SegmentsExperience> getModelClass() {
		return _segmentsExperienceLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<SegmentsExperience>, R, E>
				updateUnsafeFunction)
		throws E {

		return _segmentsExperienceLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public SegmentsExperienceLocalService getWrappedService() {
		return _segmentsExperienceLocalService;
	}

	@Override
	public void setWrappedService(
		SegmentsExperienceLocalService segmentsExperienceLocalService) {

		_segmentsExperienceLocalService = segmentsExperienceLocalService;
	}

	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}