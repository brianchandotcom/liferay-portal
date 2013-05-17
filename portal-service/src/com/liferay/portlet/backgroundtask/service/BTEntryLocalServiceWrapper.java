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

package com.liferay.portlet.backgroundtask.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BTEntryLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       BTEntryLocalService
 * @generated
 */
public class BTEntryLocalServiceWrapper implements BTEntryLocalService,
	ServiceWrapper<BTEntryLocalService> {
	public BTEntryLocalServiceWrapper(BTEntryLocalService btEntryLocalService) {
		_btEntryLocalService = btEntryLocalService;
	}

	/**
	* Adds the b t entry to the database. Also notifies the appropriate model listeners.
	*
	* @param btEntry the b t entry
	* @return the b t entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.backgroundtask.model.BTEntry addBTEntry(
		com.liferay.portlet.backgroundtask.model.BTEntry btEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntryLocalService.addBTEntry(btEntry);
	}

	/**
	* Creates a new b t entry with the primary key. Does not add the b t entry to the database.
	*
	* @param btEntryId the primary key for the new b t entry
	* @return the new b t entry
	*/
	public com.liferay.portlet.backgroundtask.model.BTEntry createBTEntry(
		long btEntryId) {
		return _btEntryLocalService.createBTEntry(btEntryId);
	}

	/**
	* Deletes the b t entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param btEntryId the primary key of the b t entry
	* @return the b t entry that was removed
	* @throws PortalException if a b t entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.backgroundtask.model.BTEntry deleteBTEntry(
		long btEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _btEntryLocalService.deleteBTEntry(btEntryId);
	}

	/**
	* Deletes the b t entry from the database. Also notifies the appropriate model listeners.
	*
	* @param btEntry the b t entry
	* @return the b t entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.backgroundtask.model.BTEntry deleteBTEntry(
		com.liferay.portlet.backgroundtask.model.BTEntry btEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntryLocalService.deleteBTEntry(btEntry);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _btEntryLocalService.dynamicQuery();
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
		return _btEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BTEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _btEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BTEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _btEntryLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _btEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.backgroundtask.model.BTEntry fetchBTEntry(
		long btEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntryLocalService.fetchBTEntry(btEntryId);
	}

	/**
	* Returns the b t entry with the primary key.
	*
	* @param btEntryId the primary key of the b t entry
	* @return the b t entry
	* @throws PortalException if a b t entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.backgroundtask.model.BTEntry getBTEntry(
		long btEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _btEntryLocalService.getBTEntry(btEntryId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _btEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the b t entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BTEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of b t entries
	* @param end the upper bound of the range of b t entries (not inclusive)
	* @return the range of b t entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.backgroundtask.model.BTEntry> getBTEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntryLocalService.getBTEntries(start, end);
	}

	/**
	* Returns the number of b t entries.
	*
	* @return the number of b t entries
	* @throws SystemException if a system exception occurred
	*/
	public int getBTEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntryLocalService.getBTEntriesCount();
	}

	/**
	* Updates the b t entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param btEntry the b t entry
	* @return the b t entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.backgroundtask.model.BTEntry updateBTEntry(
		com.liferay.portlet.backgroundtask.model.BTEntry btEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _btEntryLocalService.updateBTEntry(btEntry);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _btEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_btEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public BTEntryLocalService getWrappedBTEntryLocalService() {
		return _btEntryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedBTEntryLocalService(
		BTEntryLocalService btEntryLocalService) {
		_btEntryLocalService = btEntryLocalService;
	}

	public BTEntryLocalService getWrappedService() {
		return _btEntryLocalService;
	}

	public void setWrappedService(BTEntryLocalService btEntryLocalService) {
		_btEntryLocalService = btEntryLocalService;
	}

	private BTEntryLocalService _btEntryLocalService;
}