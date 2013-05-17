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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for BTEntry. This utility wraps
 * {@link com.liferay.portlet.backgroundtask.service.impl.BTEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BTEntryLocalService
 * @see com.liferay.portlet.backgroundtask.service.base.BTEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.backgroundtask.service.impl.BTEntryLocalServiceImpl
 * @generated
 */
public class BTEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.backgroundtask.service.impl.BTEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the b t entry to the database. Also notifies the appropriate model listeners.
	*
	* @param btEntry the b t entry
	* @return the b t entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BTEntry addBTEntry(
		com.liferay.portlet.backgroundtask.model.BTEntry btEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addBTEntry(btEntry);
	}

	/**
	* Creates a new b t entry with the primary key. Does not add the b t entry to the database.
	*
	* @param btEntryId the primary key for the new b t entry
	* @return the new b t entry
	*/
	public static com.liferay.portlet.backgroundtask.model.BTEntry createBTEntry(
		long btEntryId) {
		return getService().createBTEntry(btEntryId);
	}

	/**
	* Deletes the b t entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param btEntryId the primary key of the b t entry
	* @return the b t entry that was removed
	* @throws PortalException if a b t entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BTEntry deleteBTEntry(
		long btEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteBTEntry(btEntryId);
	}

	/**
	* Deletes the b t entry from the database. Also notifies the appropriate model listeners.
	*
	* @param btEntry the b t entry
	* @return the b t entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BTEntry deleteBTEntry(
		com.liferay.portlet.backgroundtask.model.BTEntry btEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteBTEntry(btEntry);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.backgroundtask.model.BTEntry fetchBTEntry(
		long btEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchBTEntry(btEntryId);
	}

	/**
	* Returns the b t entry with the primary key.
	*
	* @param btEntryId the primary key of the b t entry
	* @return the b t entry
	* @throws PortalException if a b t entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BTEntry getBTEntry(
		long btEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getBTEntry(btEntryId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<com.liferay.portlet.backgroundtask.model.BTEntry> getBTEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getBTEntries(start, end);
	}

	/**
	* Returns the number of b t entries.
	*
	* @return the number of b t entries
	* @throws SystemException if a system exception occurred
	*/
	public static int getBTEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getBTEntriesCount();
	}

	/**
	* Updates the b t entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param btEntry the b t entry
	* @return the b t entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.backgroundtask.model.BTEntry updateBTEntry(
		com.liferay.portlet.backgroundtask.model.BTEntry btEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateBTEntry(btEntry);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static BTEntryLocalService getService() {
		if (_service == null) {
			_service = (BTEntryLocalService)PortalBeanLocatorUtil.locate(BTEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(BTEntryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(BTEntryLocalService service) {
	}

	private static BTEntryLocalService _service;
}