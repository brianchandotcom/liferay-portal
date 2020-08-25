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

package com.liferay.ratings.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RatingsEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntryLocalService
 * @generated
 */
public class RatingsEntryLocalServiceWrapper
	implements RatingsEntryLocalService,
			   ServiceWrapper<RatingsEntryLocalService> {

	public RatingsEntryLocalServiceWrapper(
		RatingsEntryLocalService ratingsEntryLocalService) {

		_ratingsEntryLocalService = ratingsEntryLocalService;
	}

	/**
	 * Adds the ratings entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RatingsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ratingsEntry the ratings entry
	 * @return the ratings entry that was added
	 */
	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry addRatingsEntry(
		com.liferay.ratings.kernel.model.RatingsEntry ratingsEntry) {

		return _ratingsEntryLocalService.addRatingsEntry(ratingsEntry);
	}

	/**
	 * Creates a new ratings entry with the primary key. Does not add the ratings entry to the database.
	 *
	 * @param entryId the primary key for the new ratings entry
	 * @return the new ratings entry
	 */
	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry createRatingsEntry(
		long entryId) {

		return _ratingsEntryLocalService.createRatingsEntry(entryId);
	}

	@Override
	public void deleteEntry(long userId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ratingsEntryLocalService.deleteEntry(userId, className, classPK);
	}

	@Override
	public void deleteEntry(
			com.liferay.ratings.kernel.model.RatingsEntry entry, long userId,
			String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ratingsEntryLocalService.deleteEntry(
			entry, userId, className, classPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ratingsEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the ratings entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RatingsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param entryId the primary key of the ratings entry
	 * @return the ratings entry that was removed
	 * @throws PortalException if a ratings entry with the primary key could not be found
	 */
	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry deleteRatingsEntry(
			long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ratingsEntryLocalService.deleteRatingsEntry(entryId);
	}

	/**
	 * Deletes the ratings entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RatingsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ratingsEntry the ratings entry
	 * @return the ratings entry that was removed
	 */
	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry deleteRatingsEntry(
		com.liferay.ratings.kernel.model.RatingsEntry ratingsEntry) {

		return _ratingsEntryLocalService.deleteRatingsEntry(ratingsEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ratingsEntryLocalService.dynamicQuery();
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

		return _ratingsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl</code>.
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

		return _ratingsEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl</code>.
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

		return _ratingsEntryLocalService.dynamicQuery(
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

		return _ratingsEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ratingsEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry fetchEntry(
		long userId, String className, long classPK) {

		return _ratingsEntryLocalService.fetchEntry(userId, className, classPK);
	}

	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry fetchRatingsEntry(
		long entryId) {

		return _ratingsEntryLocalService.fetchRatingsEntry(entryId);
	}

	/**
	 * Returns the ratings entry with the matching UUID and company.
	 *
	 * @param uuid the ratings entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry
		fetchRatingsEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _ratingsEntryLocalService.fetchRatingsEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ratingsEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.ratings.kernel.model.RatingsEntry>
		getEntries(
			long userId, String className, java.util.List<Long> classPKs) {

		return _ratingsEntryLocalService.getEntries(
			userId, className, classPKs);
	}

	@Override
	public java.util.Map<Long, com.liferay.ratings.kernel.model.RatingsEntry>
		getEntries(long userId, String className, long[] classPKs) {

		return _ratingsEntryLocalService.getEntries(
			userId, className, classPKs);
	}

	@Override
	public java.util.List<com.liferay.ratings.kernel.model.RatingsEntry>
		getEntries(String className, long classPK) {

		return _ratingsEntryLocalService.getEntries(className, classPK);
	}

	@Override
	public java.util.List<com.liferay.ratings.kernel.model.RatingsEntry>
		getEntries(String className, long classPK, double score) {

		return _ratingsEntryLocalService.getEntries(className, classPK, score);
	}

	@Override
	public int getEntriesCount(String className, long classPK, double score) {
		return _ratingsEntryLocalService.getEntriesCount(
			className, classPK, score);
	}

	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry getEntry(
			long userId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ratingsEntryLocalService.getEntry(userId, className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _ratingsEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ratingsEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ratingsEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ratingsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the ratings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @return the range of ratings entries
	 */
	@Override
	public java.util.List<com.liferay.ratings.kernel.model.RatingsEntry>
		getRatingsEntries(int start, int end) {

		return _ratingsEntryLocalService.getRatingsEntries(start, end);
	}

	/**
	 * Returns the number of ratings entries.
	 *
	 * @return the number of ratings entries
	 */
	@Override
	public int getRatingsEntriesCount() {
		return _ratingsEntryLocalService.getRatingsEntriesCount();
	}

	/**
	 * Returns the ratings entry with the primary key.
	 *
	 * @param entryId the primary key of the ratings entry
	 * @return the ratings entry
	 * @throws PortalException if a ratings entry with the primary key could not be found
	 */
	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry getRatingsEntry(
			long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ratingsEntryLocalService.getRatingsEntry(entryId);
	}

	/**
	 * Returns the ratings entry with the matching UUID and company.
	 *
	 * @param uuid the ratings entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching ratings entry
	 * @throws PortalException if a matching ratings entry could not be found
	 */
	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry
			getRatingsEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ratingsEntryLocalService.getRatingsEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry updateEntry(
			long userId, String className, long classPK, double score,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ratingsEntryLocalService.updateEntry(
			userId, className, classPK, score, serviceContext);
	}

	/**
	 * Updates the ratings entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RatingsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ratingsEntry the ratings entry
	 * @return the ratings entry that was updated
	 */
	@Override
	public com.liferay.ratings.kernel.model.RatingsEntry updateRatingsEntry(
		com.liferay.ratings.kernel.model.RatingsEntry ratingsEntry) {

		return _ratingsEntryLocalService.updateRatingsEntry(ratingsEntry);
	}

	@Override
	public RatingsEntryLocalService getWrappedService() {
		return _ratingsEntryLocalService;
	}

	@Override
	public void setWrappedService(
		RatingsEntryLocalService ratingsEntryLocalService) {

		_ratingsEntryLocalService = ratingsEntryLocalService;
	}

	private RatingsEntryLocalService _ratingsEntryLocalService;

}