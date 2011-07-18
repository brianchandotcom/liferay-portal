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
 * This class is a wrapper for {@link DeviceProfileRuleLocalService}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       DeviceProfileRuleLocalService
 * @generated
 */
public class DeviceProfileRuleLocalServiceWrapper
	implements DeviceProfileRuleLocalService {
	public DeviceProfileRuleLocalServiceWrapper(
		DeviceProfileRuleLocalService deviceProfileRuleLocalService) {
		_deviceProfileRuleLocalService = deviceProfileRuleLocalService;
	}

	/**
	* Adds the device profile rule to the database. Also notifies the appropriate model listeners.
	*
	* @param deviceProfileRule the device profile rule
	* @return the device profile rule that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfileRule addDeviceProfileRule(
		com.liferay.portal.mobile.model.DeviceProfileRule deviceProfileRule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.addDeviceProfileRule(deviceProfileRule);
	}

	/**
	* Creates a new device profile rule with the primary key. Does not add the device profile rule to the database.
	*
	* @param deviceProfileRuleId the primary key for the new device profile rule
	* @return the new device profile rule
	*/
	public com.liferay.portal.mobile.model.DeviceProfileRule createDeviceProfileRule(
		long deviceProfileRuleId) {
		return _deviceProfileRuleLocalService.createDeviceProfileRule(deviceProfileRuleId);
	}

	/**
	* Deletes the device profile rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param deviceProfileRuleId the primary key of the device profile rule
	* @throws PortalException if a device profile rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDeviceProfileRule(long deviceProfileRuleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_deviceProfileRuleLocalService.deleteDeviceProfileRule(deviceProfileRuleId);
	}

	/**
	* Deletes the device profile rule from the database. Also notifies the appropriate model listeners.
	*
	* @param deviceProfileRule the device profile rule
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDeviceProfileRule(
		com.liferay.portal.mobile.model.DeviceProfileRule deviceProfileRule)
		throws com.liferay.portal.kernel.exception.SystemException {
		_deviceProfileRuleLocalService.deleteDeviceProfileRule(deviceProfileRule);
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
		return _deviceProfileRuleLocalService.dynamicQuery(dynamicQuery);
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
		return _deviceProfileRuleLocalService.dynamicQuery(dynamicQuery, start,
			end);
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
		return _deviceProfileRuleLocalService.dynamicQuery(dynamicQuery, start,
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
		return _deviceProfileRuleLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the device profile rule with the primary key.
	*
	* @param deviceProfileRuleId the primary key of the device profile rule
	* @return the device profile rule
	* @throws PortalException if a device profile rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfileRule getDeviceProfileRule(
		long deviceProfileRuleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.getDeviceProfileRule(deviceProfileRuleId);
	}

	/**
	* Returns a range of all the device profile rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of device profile rules
	* @param end the upper bound of the range of device profile rules (not inclusive)
	* @return the range of device profile rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> getDeviceProfileRules(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.getDeviceProfileRules(start, end);
	}

	/**
	* Returns the number of device profile rules.
	*
	* @return the number of device profile rules
	* @throws SystemException if a system exception occurred
	*/
	public int getDeviceProfileRulesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.getDeviceProfileRulesCount();
	}

	/**
	* Updates the device profile rule in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param deviceProfileRule the device profile rule
	* @return the device profile rule that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfileRule updateDeviceProfileRule(
		com.liferay.portal.mobile.model.DeviceProfileRule deviceProfileRule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.updateDeviceProfileRule(deviceProfileRule);
	}

	/**
	* Updates the device profile rule in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param deviceProfileRule the device profile rule
	* @param merge whether to merge the device profile rule with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the device profile rule that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.mobile.model.DeviceProfileRule updateDeviceProfileRule(
		com.liferay.portal.mobile.model.DeviceProfileRule deviceProfileRule,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.updateDeviceProfileRule(deviceProfileRule,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _deviceProfileRuleLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_deviceProfileRuleLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.mobile.model.DeviceProfileRule addDeviceProfileRule(
		long deviceProfileId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String rule,
		com.liferay.portal.kernel.util.UnicodeProperties ruleTypeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.addDeviceProfileRule(deviceProfileId,
			nameMap, descriptionMap, rule, ruleTypeSettingsProperties);
	}

	public com.liferay.portal.mobile.model.DeviceProfileRule addDeviceProfileRule(
		long deviceProfileId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String rule, java.lang.String ruleTypeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.addDeviceProfileRule(deviceProfileId,
			nameMap, descriptionMap, rule, ruleTypeSettings);
	}

	public void deleteDeviceProfileRules(long deviceProfileId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_deviceProfileRuleLocalService.deleteDeviceProfileRules(deviceProfileId);
	}

	public com.liferay.portal.mobile.model.DeviceProfileRule fetchDeviceProfileRule(
		long deviceProfileRuleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.fetchDeviceProfileRule(deviceProfileRuleId);
	}

	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> getDeviceProfileRules(
		long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.getDeviceProfileRules(deviceProfileId);
	}

	public java.util.List<com.liferay.portal.mobile.model.DeviceProfileRule> getDeviceProfileRules(
		long deviceProfileId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.getDeviceProfileRules(deviceProfileId,
			start, end);
	}

	public int getDeviceProfileRulesCount(long deviceProfileId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deviceProfileRuleLocalService.getDeviceProfileRulesCount(deviceProfileId);
	}

	public DeviceProfileRuleLocalService getWrappedDeviceProfileRuleLocalService() {
		return _deviceProfileRuleLocalService;
	}

	public void setWrappedDeviceProfileRuleLocalService(
		DeviceProfileRuleLocalService deviceProfileRuleLocalService) {
		_deviceProfileRuleLocalService = deviceProfileRuleLocalService;
	}

	private DeviceProfileRuleLocalService _deviceProfileRuleLocalService;
}