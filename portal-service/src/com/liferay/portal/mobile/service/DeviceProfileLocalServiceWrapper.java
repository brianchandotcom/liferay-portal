/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.mobile.service;

/**
 * <p>
 * This class is a wrapper for {@link DeviceProfileLocalService}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       DeviceProfileLocalService
 * @generated
 */
public class DeviceProfileLocalServiceWrapper
	implements DeviceProfileLocalService {
	public DeviceProfileLocalServiceWrapper(
		DeviceProfileLocalService deviceProfileLocalService) {
		_deviceProfileLocalService = deviceProfileLocalService;
	}

	/**
	* Adds the device profile to the database. Also notifies the appropriate model listeners.
	*
	* @param deviceProfile the device profile
	* @return the device profile that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfile addDeviceProfile(
		com.liferay.portal.mobile.model.DeviceProfile deviceProfile)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileLocalService.addDeviceProfile(deviceProfile);
	}

	/**
	* Creates a new device profile with the primary key. Does not add the device profile to the database.
	*
	* @param deviceProfileId the primary key for the new device profile
	* @return the new device profile
	*/
	public com.liferay.portal.mobile.model.DeviceProfile createDeviceProfile(
		long deviceProfileId) {
		return _deviceProfileLocalService.createDeviceProfile(deviceProfileId);
	}

	/**
	* Deletes the device profile with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param deviceProfileId the primary key of the device profile
	* @throws PortalException if a device profile with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDeviceProfile(long deviceProfileId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_deviceProfileLocalService.deleteDeviceProfile(deviceProfileId);
	}

	/**
	* Deletes the device profile from the database. Also notifies the appropriate model listeners.
	*
	* @param deviceProfile the device profile
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDeviceProfile(
		com.liferay.portal.mobile.model.DeviceProfile deviceProfile)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_deviceProfileLocalService.deleteDeviceProfile(deviceProfile);
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
		return _deviceProfileLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
		return _deviceProfileLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
		return _deviceProfileLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _deviceProfileLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the device profile with the primary key.
	*
	* @param deviceProfileId the primary key of the device profile
	* @return the device profile
	* @throws PortalException if a device profile with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfile getDeviceProfile(
		long deviceProfileId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileLocalService.getDeviceProfile(deviceProfileId);
	}

	/**
	* Returns a range of all the device profiles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of device profiles
	* @param end the upper bound of the range of device profiles (not inclusive)
	* @return the range of device profiles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfile> getDeviceProfiles(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileLocalService.getDeviceProfiles(start, end);
	}

	/**
	* Returns the number of device profiles.
	*
	* @return the number of device profiles
	* @throws SystemException if a system exception occurred
	*/
	public int getDeviceProfilesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileLocalService.getDeviceProfilesCount();
	}

	/**
	* Updates the device profile in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param deviceProfile the device profile
	* @return the device profile that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfile updateDeviceProfile(
		com.liferay.portal.mobile.model.DeviceProfile deviceProfile)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileLocalService.updateDeviceProfile(deviceProfile);
	}

	/**
	* Updates the device profile in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param deviceProfile the device profile
	* @param merge whether to merge the device profile with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the device profile that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfile updateDeviceProfile(
		com.liferay.portal.mobile.model.DeviceProfile deviceProfile,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileLocalService.updateDeviceProfile(deviceProfile,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _deviceProfileLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_deviceProfileLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.mobile.model.DeviceProfile addDeviceProfile(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileLocalService.addDeviceProfile(nameMap,
			descriptionMap);
	}

	public com.liferay.portal.mobile.model.DeviceProfile fetchByPrimaryKey(
		long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileLocalService.fetchByPrimaryKey(deviceProfileId);
	}

	public DeviceProfileLocalService getWrappedDeviceProfileLocalService() {
		return _deviceProfileLocalService;
	}

	public void setWrappedDeviceProfileLocalService(
		DeviceProfileLocalService deviceProfileLocalService) {
		_deviceProfileLocalService = deviceProfileLocalService;
	}

	private DeviceProfileLocalService _deviceProfileLocalService;
}