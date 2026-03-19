/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat720.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LikeFinderEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LikeFinderEntryLocalService
 * @generated
 */
public class LikeFinderEntryLocalServiceWrapper
	implements LikeFinderEntryLocalService,
			   ServiceWrapper<LikeFinderEntryLocalService> {

	public LikeFinderEntryLocalServiceWrapper(
		LikeFinderEntryLocalService likeFinderEntryLocalService) {

		_likeFinderEntryLocalService = likeFinderEntryLocalService;
	}

	/**
	 * Adds the like finder entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LikeFinderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param likeFinderEntry the like finder entry
	 * @return the like finder entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat720.model.
		LikeFinderEntry addLikeFinderEntry(
			com.liferay.portal.tools.service.builder.test.compat720.model.
				LikeFinderEntry likeFinderEntry) {

		return _likeFinderEntryLocalService.addLikeFinderEntry(likeFinderEntry);
	}

	/**
	 * Creates a new like finder entry with the primary key. Does not add the like finder entry to the database.
	 *
	 * @param likeFinderEntryId the primary key for the new like finder entry
	 * @return the new like finder entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat720.model.
		LikeFinderEntry createLikeFinderEntry(long likeFinderEntryId) {

		return _likeFinderEntryLocalService.createLikeFinderEntry(
			likeFinderEntryId);
	}

	/**
	 * Deletes the like finder entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LikeFinderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param likeFinderEntry the like finder entry
	 * @return the like finder entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat720.model.
		LikeFinderEntry deleteLikeFinderEntry(
			com.liferay.portal.tools.service.builder.test.compat720.model.
				LikeFinderEntry likeFinderEntry) {

		return _likeFinderEntryLocalService.deleteLikeFinderEntry(
			likeFinderEntry);
	}

	/**
	 * Deletes the like finder entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LikeFinderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param likeFinderEntryId the primary key of the like finder entry
	 * @return the like finder entry that was removed
	 * @throws PortalException if a like finder entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat720.model.
		LikeFinderEntry deleteLikeFinderEntry(long likeFinderEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _likeFinderEntryLocalService.deleteLikeFinderEntry(
			likeFinderEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _likeFinderEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _likeFinderEntryLocalService.dynamicQuery();
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

		return _likeFinderEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat720.model.impl.LikeFinderEntryModelImpl</code>.
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

		return _likeFinderEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat720.model.impl.LikeFinderEntryModelImpl</code>.
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

		return _likeFinderEntryLocalService.dynamicQuery(
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

		return _likeFinderEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _likeFinderEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.compat720.model.
		LikeFinderEntry fetchLikeFinderEntry(long likeFinderEntryId) {

		return _likeFinderEntryLocalService.fetchLikeFinderEntry(
			likeFinderEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _likeFinderEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _likeFinderEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the like finder entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat720.model.impl.LikeFinderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of like finder entries
	 * @param end the upper bound of the range of like finder entries (not inclusive)
	 * @return the range of like finder entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat720.model.
			LikeFinderEntry> getLikeFinderEntries(int start, int end) {

		return _likeFinderEntryLocalService.getLikeFinderEntries(start, end);
	}

	/**
	 * Returns the number of like finder entries.
	 *
	 * @return the number of like finder entries
	 */
	@Override
	public int getLikeFinderEntriesCount() {
		return _likeFinderEntryLocalService.getLikeFinderEntriesCount();
	}

	/**
	 * Returns the like finder entry with the primary key.
	 *
	 * @param likeFinderEntryId the primary key of the like finder entry
	 * @return the like finder entry
	 * @throws PortalException if a like finder entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat720.model.
		LikeFinderEntry getLikeFinderEntry(long likeFinderEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _likeFinderEntryLocalService.getLikeFinderEntry(
			likeFinderEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _likeFinderEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _likeFinderEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the like finder entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LikeFinderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param likeFinderEntry the like finder entry
	 * @return the like finder entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat720.model.
		LikeFinderEntry updateLikeFinderEntry(
			com.liferay.portal.tools.service.builder.test.compat720.model.
				LikeFinderEntry likeFinderEntry) {

		return _likeFinderEntryLocalService.updateLikeFinderEntry(
			likeFinderEntry);
	}

	@Override
	public LikeFinderEntryLocalService getWrappedService() {
		return _likeFinderEntryLocalService;
	}

	@Override
	public void setWrappedService(
		LikeFinderEntryLocalService likeFinderEntryLocalService) {

		_likeFinderEntryLocalService = likeFinderEntryLocalService;
	}

	private LikeFinderEntryLocalService _likeFinderEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-753586467