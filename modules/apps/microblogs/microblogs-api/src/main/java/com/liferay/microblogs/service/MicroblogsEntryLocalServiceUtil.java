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

package com.liferay.microblogs.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for MicroblogsEntry. This utility wraps
 * <code>com.liferay.microblogs.service.impl.MicroblogsEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntryLocalService
 * @generated
 */
public class MicroblogsEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.microblogs.service.impl.MicroblogsEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MicroblogsEntryLocalServiceUtil} to access the microblogs entry local service. Add custom service methods to <code>com.liferay.microblogs.service.impl.MicroblogsEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.microblogs.model.MicroblogsEntry
			addMicroblogsEntry(
				long userId, long creatorClassNameId, long creatorClassPK,
				String content, int type, long parentMicroblogsEntryId,
				int socialRelationType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addMicroblogsEntry(
			userId, creatorClassNameId, creatorClassPK, content, type,
			parentMicroblogsEntryId, socialRelationType, serviceContext);
	}

	public static com.liferay.microblogs.model.MicroblogsEntry
			addMicroblogsEntry(
				long userId, String content, int type,
				long parentMicroblogsEntryId, int socialRelationType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addMicroblogsEntry(
			userId, content, type, parentMicroblogsEntryId, socialRelationType,
			serviceContext);
	}

	/**
	 * Adds the microblogs entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param microblogsEntry the microblogs entry
	 * @return the microblogs entry that was added
	 */
	public static com.liferay.microblogs.model.MicroblogsEntry
		addMicroblogsEntry(
			com.liferay.microblogs.model.MicroblogsEntry microblogsEntry) {

		return getService().addMicroblogsEntry(microblogsEntry);
	}

	/**
	 * Creates a new microblogs entry with the primary key. Does not add the microblogs entry to the database.
	 *
	 * @param microblogsEntryId the primary key for the new microblogs entry
	 * @return the new microblogs entry
	 */
	public static com.liferay.microblogs.model.MicroblogsEntry
		createMicroblogsEntry(long microblogsEntryId) {

		return getService().createMicroblogsEntry(microblogsEntryId);
	}

	public static void deleteMicroblogsEntries(
			long creatorClassNameId, long creatorClassPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteMicroblogsEntries(
			creatorClassNameId, creatorClassPK);
	}

	/**
	 * Deletes the microblogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param microblogsEntryId the primary key of the microblogs entry
	 * @return the microblogs entry that was removed
	 * @throws PortalException if a microblogs entry with the primary key could not be found
	 */
	public static com.liferay.microblogs.model.MicroblogsEntry
			deleteMicroblogsEntry(long microblogsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteMicroblogsEntry(microblogsEntryId);
	}

	/**
	 * Deletes the microblogs entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param microblogsEntry the microblogs entry
	 * @return the microblogs entry that was removed
	 * @throws PortalException
	 */
	public static com.liferay.microblogs.model.MicroblogsEntry
			deleteMicroblogsEntry(
				com.liferay.microblogs.model.MicroblogsEntry microblogsEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteMicroblogsEntry(microblogsEntry);
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

	public static void deleteUserMicroblogsEntries(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteUserMicroblogsEntries(userId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.microblogs.model.impl.MicroblogsEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.microblogs.model.impl.MicroblogsEntryModelImpl</code>.
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

	public static com.liferay.microblogs.model.MicroblogsEntry
		fetchMicroblogsEntry(long microblogsEntryId) {

		return getService().fetchMicroblogsEntry(microblogsEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
		getCompanyMicroblogsEntries(long companyId, int start, int end) {

		return getService().getCompanyMicroblogsEntries(companyId, start, end);
	}

	public static int getCompanyMicroblogsEntriesCount(long companyId) {
		return getService().getCompanyMicroblogsEntriesCount(companyId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the microblogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.microblogs.model.impl.MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of microblogs entries
	 */
	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
		getMicroblogsEntries(int start, int end) {

		return getService().getMicroblogsEntries(start, end);
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
		getMicroblogsEntries(
			long companyId, long creatorClassNameId, int type, int start,
			int end, com.liferay.portal.kernel.util.OrderByComparator obc) {

		return getService().getMicroblogsEntries(
			companyId, creatorClassNameId, type, start, end, obc);
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
		getMicroblogsEntries(
			long companyId, long creatorClassNameId, long creatorClassPK,
			int start, int end) {

		return getService().getMicroblogsEntries(
			companyId, creatorClassNameId, creatorClassPK, start, end);
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
		getMicroblogsEntries(
			long companyId, long creatorClassNameId, long creatorClassPK,
			int type, int start, int end) {

		return getService().getMicroblogsEntries(
			companyId, creatorClassNameId, creatorClassPK, type, start, end);
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
		getMicroblogsEntries(
			long companyId, long creatorClassNameId, long creatorClassPK,
			String assetTagName, boolean andOperator, int start, int end) {

		return getService().getMicroblogsEntries(
			companyId, creatorClassNameId, creatorClassPK, assetTagName,
			andOperator, start, end);
	}

	/**
	 * Returns the number of microblogs entries.
	 *
	 * @return the number of microblogs entries
	 */
	public static int getMicroblogsEntriesCount() {
		return getService().getMicroblogsEntriesCount();
	}

	public static int getMicroblogsEntriesCount(
		long companyId, long creatorClassNameId, long creatorClassPK) {

		return getService().getMicroblogsEntriesCount(
			companyId, creatorClassNameId, creatorClassPK);
	}

	public static int getMicroblogsEntriesCount(
		long companyId, long creatorClassNameId, long creatorClassPK,
		int type) {

		return getService().getMicroblogsEntriesCount(
			companyId, creatorClassNameId, creatorClassPK, type);
	}

	public static int getMicroblogsEntriesCount(
		long companyId, long creatorClassNameId, long creatorClassPK,
		String assetTagName, boolean andOperator) {

		return getService().getMicroblogsEntriesCount(
			companyId, creatorClassNameId, creatorClassPK, assetTagName,
			andOperator);
	}

	public static int getMicroblogsEntriesCount(
		long companyId, long creatorClassNameId, String assetTagName) {

		return getService().getMicroblogsEntriesCount(
			companyId, creatorClassNameId, assetTagName);
	}

	/**
	 * Returns the microblogs entry with the primary key.
	 *
	 * @param microblogsEntryId the primary key of the microblogs entry
	 * @return the microblogs entry
	 * @throws PortalException if a microblogs entry with the primary key could not be found
	 */
	public static com.liferay.microblogs.model.MicroblogsEntry
			getMicroblogsEntry(long microblogsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMicroblogsEntry(microblogsEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
		getParentMicroblogsEntryMicroblogsEntries(
			int type, long parentMicroblogsEntryId, int start, int end) {

		return getService().getParentMicroblogsEntryMicroblogsEntries(
			type, parentMicroblogsEntryId, start, end);
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
		getParentMicroblogsEntryMicroblogsEntries(
			int type, long parentMicroblogsEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.microblogs.model.MicroblogsEntry>
					orderByComparator) {

		return getService().getParentMicroblogsEntryMicroblogsEntries(
			type, parentMicroblogsEntryId, start, end, orderByComparator);
	}

	public static int getParentMicroblogsEntryMicroblogsEntriesCount(
		int type, long parentMicroblogsEntryId) {

		return getService().getParentMicroblogsEntryMicroblogsEntriesCount(
			type, parentMicroblogsEntryId);
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
		getUserMicroblogsEntries(long userId, int start, int end) {

		return getService().getUserMicroblogsEntries(userId, start, end);
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
		getUserMicroblogsEntries(long userId, int type, int start, int end) {

		return getService().getUserMicroblogsEntries(userId, type, start, end);
	}

	public static int getUserMicroblogsEntriesCount(long userId) {
		return getService().getUserMicroblogsEntriesCount(userId);
	}

	public static int getUserMicroblogsEntriesCount(long userId, int type) {
		return getService().getUserMicroblogsEntriesCount(userId, type);
	}

	public static void updateAsset(
			com.liferay.microblogs.model.MicroblogsEntry microblogsEntry,
			long[] assetCategoryIds, String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateAsset(
			microblogsEntry, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.microblogs.model.MicroblogsEntry
			updateMicroblogsEntry(
				long microblogsEntryId, String content, int socialRelationType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateMicroblogsEntry(
			microblogsEntryId, content, socialRelationType, serviceContext);
	}

	/**
	 * Updates the microblogs entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param microblogsEntry the microblogs entry
	 * @return the microblogs entry that was updated
	 */
	public static com.liferay.microblogs.model.MicroblogsEntry
		updateMicroblogsEntry(
			com.liferay.microblogs.model.MicroblogsEntry microblogsEntry) {

		return getService().updateMicroblogsEntry(microblogsEntry);
	}

	public static MicroblogsEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<MicroblogsEntryLocalService, MicroblogsEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			MicroblogsEntryLocalService.class);

		ServiceTracker<MicroblogsEntryLocalService, MicroblogsEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<MicroblogsEntryLocalService, MicroblogsEntryLocalService>(
						bundle.getBundleContext(),
						MicroblogsEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}