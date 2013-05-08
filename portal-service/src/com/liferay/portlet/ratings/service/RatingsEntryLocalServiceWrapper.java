/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RatingsEntryLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsEntryLocalService
 * @generated
 */
public class RatingsEntryLocalServiceWrapper implements RatingsEntryLocalService,
	ServiceWrapper<RatingsEntryLocalService> {
	public RatingsEntryLocalServiceWrapper(
		RatingsEntryLocalService ratingsEntryLocalService) {
		_ratingsEntryLocalService = ratingsEntryLocalService;
	}

	/**
	* Adds the ratings entry to the database. Also notifies the appropriate model listeners.
	*
	* @param ratingsEntry the ratings entry
	* @return the ratings entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry addRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.addRatingsEntry(ratingsEntry);
	}

	/**
	* Creates a new ratings entry with the primary key. Does not add the ratings entry to the database.
	*
	* @param entryId the primary key for the new ratings entry
	* @return the new ratings entry
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry createRatingsEntry(
		long entryId) {
		return _ratingsEntryLocalService.createRatingsEntry(entryId);
	}

	/**
	* Deletes the ratings entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry that was removed
	* @throws PortalException if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry deleteRatingsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.deleteRatingsEntry(entryId);
	}

	/**
	* Deletes the ratings entry from the database. Also notifies the appropriate model listeners.
	*
	* @param ratingsEntry the ratings entry
	* @return the ratings entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry deleteRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.deleteRatingsEntry(ratingsEntry);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ratingsEntryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry fetchRatingsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.fetchRatingsEntry(entryId);
	}

	/**
	* Returns the ratings entry with the primary key.
	*
	* @param entryId the primary key of the ratings entry
	* @return the ratings entry
	* @throws PortalException if a ratings entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry getRatingsEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getRatingsEntry(entryId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the ratings entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ratings entries
	* @param end the upper bound of the range of ratings entries (not inclusive)
	* @return the range of ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getRatingsEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getRatingsEntries(start, end);
	}

	/**
	* Returns the number of ratings entries.
	*
	* @return the number of ratings entries
	* @throws SystemException if a system exception occurred
	*/
	public int getRatingsEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getRatingsEntriesCount();
	}

	/**
	* Updates the ratings entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ratingsEntry the ratings entry
	* @return the ratings entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsEntry updateRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.updateRatingsEntry(ratingsEntry);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _ratingsEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ratingsEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	public void deleteEntry(long userId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ratingsEntryLocalService.deleteEntry(userId, className, classPK);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry fetchEntry(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.fetchEntry(userId, className, classPK);
	}

	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getEntries(
		long userId, java.lang.String className,
		java.util.List<java.lang.Long> classPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getEntries(userId, className, classPKs);
	}

	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getEntries(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getEntries(className, classPK);
	}

	public java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getEntries(
		java.lang.String className, long classPK, double score)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getEntries(className, classPK, score);
	}

	public int getEntriesCount(java.lang.String className, long classPK,
		double score)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getEntriesCount(className, classPK,
			score);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry getEntry(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.getEntry(userId, className, classPK);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry updateEntry(
		long userId, java.lang.String className, long classPK, double score,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntryLocalService.updateEntry(userId, className,
			classPK, score, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public RatingsEntryLocalService getWrappedRatingsEntryLocalService() {
		return _ratingsEntryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedRatingsEntryLocalService(
		RatingsEntryLocalService ratingsEntryLocalService) {
		_ratingsEntryLocalService = ratingsEntryLocalService;
	}

	public RatingsEntryLocalService getWrappedService() {
		return _ratingsEntryLocalService;
	}

	public void setWrappedService(
		RatingsEntryLocalService ratingsEntryLocalService) {
		_ratingsEntryLocalService = ratingsEntryLocalService;
	}

	private RatingsEntryLocalService _ratingsEntryLocalService;
}