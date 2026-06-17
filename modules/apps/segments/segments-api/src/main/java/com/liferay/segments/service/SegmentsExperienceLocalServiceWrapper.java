/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
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
	public SegmentsExperience addDefaultSegmentsExperience(
			String externalReferenceCode, long userId, long plid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.addDefaultSegmentsExperience(
			externalReferenceCode, userId, plid, serviceContext);
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
	public SegmentsExperience addSegmentsExperience(
			String externalReferenceCode, long userId, long groupId,
			String segmentsEntryERC, String segmentsEntryScopeERC, long plid,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.addSegmentsExperience(
			externalReferenceCode, userId, groupId, segmentsEntryERC,
			segmentsEntryScopeERC, plid, nameMap, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	/**
	 * Adds a segments experience to a page, generating its key automatically.
	 *
	 * <p>
	 * The supplied priority is compacted with the priorities of the other
	 * experiences on the page so the priority of the returned segments
	 * experience may differ from the value supplied. See {@link
	 * #addSegmentsExperience(String, long, long, String, String, String, long,
	 * Map, int, boolean, UnicodeProperties, ServiceContext)} for the full
	 * priority-compaction contract.
	 * </p>
	 *
	 * @param externalReferenceCode the segments experience's external reference
	 code
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @param segmentsEntryERC the external reference code of the segments entry
	 the experience is associated with
	 * @param segmentsEntryScopeERC the external reference code of the segments
	 entry's scope
	 * @param plid the primary key of the layout
	 * @param nameMap the segments experience's locales and localized names
	 * @param priority the requested priority within the page. It must be unique
	 among the page's experiences, but it is compacted, so the
	 persisted value may differ.
	 * @param active whether the segments experience is active
	 * @param typeSettingsUnicodeProperties the segments experience's type
	 settings
	 * @param serviceContext the service context to be applied. Can set the
	 UUID, creation date, modification date, guest permissions, and
	 group permissions for the segments experience.
	 * @return the segments experience
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public SegmentsExperience addSegmentsExperience(
			String externalReferenceCode, long userId, long groupId,
			String segmentsEntryERC, String segmentsEntryScopeERC, long plid,
			java.util.Map<java.util.Locale, String> nameMap, int priority,
			boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.addSegmentsExperience(
			externalReferenceCode, userId, groupId, segmentsEntryERC,
			segmentsEntryScopeERC, plid, nameMap, priority, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	/**
	 * Adds a segments experience to a page and compacts the priorities of every
	 * experience on that page.
	 *
	 * <p>
	 * The supplied priority must be unique within the page but it is not
	 * persisted verbatim. After the experience is created, the service renumbers
	 * the priorities of all the experiences on the page into sequential values
	 * (1, 2, 3, ... for active experiences and -1, -2, -3, ... for inactive
	 * experiences), mirroring {@link #updateSegmentsExperiencePriority(long,
	 * long, int)} and {@link #deleteSegmentsExperience(SegmentsExperience)}. The
	 * priority of the returned segments experience as well as the priorities of
	 * the other experiences on the same page, may therefore differ from the
	 * value supplied.
	 * </p>
	 *
	 * @param externalReferenceCode the segments experience's external reference
	 code
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @param segmentsEntryERC the external reference code of the segments entry
	 the experience is associated with
	 * @param segmentsEntryScopeERC the external reference code of the segments
	 entry's scope
	 * @param segmentsExperienceKey the key that identifies the segments
	 experience
	 * @param plid the primary key of the layout
	 * @param nameMap the segments experience's locales and localized names
	 * @param priority the requested priority within the page. It must be unique
	 among the page's experiences, but it is compacted as described
	 above, so the persisted value may differ.
	 * @param active whether the segments experience is active
	 * @param typeSettingsUnicodeProperties the segments experience's type
	 settings
	 * @param serviceContext the service context to be applied. Can set the
	 UUID, creation date, modification date, guest permissions, and
	 group permissions for the segments experience.
	 * @return the segments experience
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public SegmentsExperience addSegmentsExperience(
			String externalReferenceCode, long userId, long groupId,
			String segmentsEntryERC, String segmentsEntryScopeERC,
			String segmentsExperienceKey, long plid,
			java.util.Map<java.util.Locale, String> nameMap, int priority,
			boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.addSegmentsExperience(
			externalReferenceCode, userId, groupId, segmentsEntryERC,
			segmentsEntryScopeERC, segmentsExperienceKey, plid, nameMap,
			priority, active, typeSettingsUnicodeProperties, serviceContext);
	}

	@Override
	public SegmentsExperience appendSegmentsExperience(
			long userId, long groupId, String segmentsEntryERC,
			String segmentsEntryScopeERC, long plid,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.appendSegmentsExperience(
			userId, groupId, segmentsEntryERC, segmentsEntryScopeERC, plid,
			nameMap, active, serviceContext);
	}

	@Override
	public SegmentsExperience appendSegmentsExperience(
			long userId, long groupId, String segmentsEntryERC,
			String segmentsEntryScopeERC, long plid,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.appendSegmentsExperience(
			userId, groupId, segmentsEntryERC, segmentsEntryScopeERC, plid,
			nameMap, active, typeSettingsUnicodeProperties, serviceContext);
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
	public void deleteSegmentsEntrySegmentsExperiences(
			long groupId, String segmentsEntryERC, String segmentsEntryScopeERC)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsExperienceLocalService.deleteSegmentsEntrySegmentsExperiences(
			groupId, segmentsEntryERC, segmentsEntryScopeERC);
	}

	@Override
	public void deleteSegmentsEntrySegmentsExperiences(
			String segmentsEntryERC, String segmentsEntryScopeERC)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsExperienceLocalService.deleteSegmentsEntrySegmentsExperiences(
			segmentsEntryERC, segmentsEntryScopeERC);
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

	/**
	 * Deletes the segments experience with the external reference code and
	 * compacts the priorities of the remaining experiences on the page.
	 *
	 * <p>
	 * See {@link #deleteSegmentsExperience(SegmentsExperience)} for the full
	 * priority-compaction contract.
	 * </p>
	 *
	 * @param externalReferenceCode the external reference code of the segments
	 experience
	 * @param groupId the primary key of the group
	 * @return the deleted segments experience
	 * @throws PortalException if a segments experience with the external
	 reference code could not be found
	 */
	@Override
	public SegmentsExperience deleteSegmentsExperience(
			String externalReferenceCode, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.deleteSegmentsExperience(
			externalReferenceCode, groupId);
	}

	@Override
	public void deleteSegmentsExperiences(long groupId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsExperienceLocalService.deleteSegmentsExperiences(
			groupId, plid);
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
	public SegmentsExperience fetchDefaultSegmentsExperience(long plid) {
		return _segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
			plid);
	}

	@Override
	public long fetchDefaultSegmentsExperienceId(long plid) {
		return _segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
			plid);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long segmentsExperienceId) {

		return _segmentsExperienceLocalService.fetchSegmentsExperience(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long groupId, long plid, int priority) {

		return _segmentsExperienceLocalService.fetchSegmentsExperience(
			groupId, plid, priority);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long groupId, String segmentsExperienceKey, long plid) {

		return _segmentsExperienceLocalService.fetchSegmentsExperience(
			groupId, segmentsExperienceKey, plid);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperienceByExternalReferenceCode(
		String externalReferenceCode, long groupId) {

		return _segmentsExperienceLocalService.
			fetchSegmentsExperienceByExternalReferenceCode(
				externalReferenceCode, groupId);
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

	@Override
	public int getLowestPriority(long groupId, long plid) {
		return _segmentsExperienceLocalService.getLowestPriority(groupId, plid);
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
			long groupId, String segmentsExperienceKey, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.getSegmentsExperience(
			groupId, segmentsExperienceKey, plid);
	}

	@Override
	public SegmentsExperience getSegmentsExperienceByExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.
			getSegmentsExperienceByExternalReferenceCode(
				externalReferenceCode, groupId);
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
			long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, active);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long plid) {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, plid);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long plid, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, plid, active);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long plid, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience>
			orderByComparator) {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, plid, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
		long groupId, String[] segmentsEntryERCs, String segmentsEntryScopeERC,
		long plid, boolean active) {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, segmentsEntryERCs, segmentsEntryScopeERC, plid, active);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
		long groupId, String[] segmentsEntryERCs, String segmentsEntryScopeERC,
		long plid, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperience>
			orderByComparator) {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupId, segmentsEntryERCs, segmentsEntryScopeERC, plid, active,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<SegmentsExperience> getSegmentsExperiences(
		long[] groupIds, boolean active) {

		return _segmentsExperienceLocalService.getSegmentsExperiences(
			groupIds, active);
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
	public int getSegmentsExperiencesCount(long groupId, long plid) {
		return _segmentsExperienceLocalService.getSegmentsExperiencesCount(
			groupId, plid);
	}

	@Override
	public int getSegmentsExperiencesCount(
		long groupId, long plid, boolean active) {

		return _segmentsExperienceLocalService.getSegmentsExperiencesCount(
			groupId, plid, active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long userId, long segmentsExperienceId, String segmentsEntryERC,
			String segmentsEntryScopeERC,
			java.util.Map<java.util.Locale, String> nameMap, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.updateSegmentsExperience(
			userId, segmentsExperienceId, segmentsEntryERC,
			segmentsEntryScopeERC, nameMap, active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long userId, long segmentsExperienceId, String segmentsEntryERC,
			String segmentsEntryScopeERC,
			java.util.Map<java.util.Locale, String> nameMap, boolean active,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.updateSegmentsExperience(
			userId, segmentsExperienceId, segmentsEntryERC,
			segmentsEntryScopeERC, nameMap, active,
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
			long userId, long segmentsExperienceId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.updateSegmentsExperienceActive(
			userId, segmentsExperienceId, active);
	}

	/**
	 * Updates the priority of a segments experience and compacts the priorities
	 * of every experience on the page.
	 *
	 * <p>
	 * If another experience already holds <code>newPriority</code>, the two
	 * experiences swap priorities; otherwise the experience is simply moved to
	 * <code>newPriority</code>. A <code>newPriority</code> of <code>0</code>
	 * deactivates an active experience and activates an inactive one. After the
	 * move, the service renumbers the priorities of all the experiences on the
	 * page into sequential values (1, 2, 3, ... for active experiences and -1,
	 * -2, -3, ... for inactive experiences) so the priority of the returned
	 * segments experience as well as the priorities of the other experiences on
	 * the same page, may differ from <code>newPriority</code>. This matches the
	 * compaction performed by {@link #addSegmentsExperience(String, long, long,
	 * String, String, String, long, Map, int, boolean, UnicodeProperties,
	 * ServiceContext)} and {@link
	 * #deleteSegmentsExperience(SegmentsExperience)}.
	 * </p>
	 *
	 * @param userId the primary key of the user
	 * @param segmentsExperienceId the primary key of the segments experience
	 * @param newPriority the requested priority within the page. Use
	 <code>0</code> to toggle the experience between active and
	 inactive.
	 * @return the updated segments experience
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public SegmentsExperience updateSegmentsExperiencePriority(
			long userId, long segmentsExperienceId, int newPriority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperienceLocalService.updateSegmentsExperiencePriority(
			userId, segmentsExperienceId, newPriority);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _segmentsExperienceLocalService.getBasePersistence();
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
// LIFERAY-SERVICE-BUILDER-HASH:-1518698435