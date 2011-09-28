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

package com.liferay.portlet.mobiledevicerules.service;

/**
 * <p>
 * This class is a wrapper for {@link MDRRuleGroupInstanceLocalService}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       MDRRuleGroupInstanceLocalService
 * @generated
 */
public class MDRRuleGroupInstanceLocalServiceWrapper
	implements MDRRuleGroupInstanceLocalService {
	public MDRRuleGroupInstanceLocalServiceWrapper(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {
		_mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	/**
	* Adds the m d r rule group instance to the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroupInstance the m d r rule group instance
	* @return the m d r rule group instance that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance addMDRRuleGroupInstance(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance mdrRuleGroupInstance)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.addMDRRuleGroupInstance(mdrRuleGroupInstance);
	}

	/**
	* Creates a new m d r rule group instance with the primary key. Does not add the m d r rule group instance to the database.
	*
	* @param ruleGroupInstanceId the primary key for the new m d r rule group instance
	* @return the new m d r rule group instance
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance createMDRRuleGroupInstance(
		long ruleGroupInstanceId) {
		return _mdrRuleGroupInstanceLocalService.createMDRRuleGroupInstance(ruleGroupInstanceId);
	}

	/**
	* Deletes the m d r rule group instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @throws PortalException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteMDRRuleGroupInstance(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleGroupInstanceLocalService.deleteMDRRuleGroupInstance(ruleGroupInstanceId);
	}

	/**
	* Deletes the m d r rule group instance from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroupInstance the m d r rule group instance
	* @throws SystemException if a system exception occurred
	*/
	public void deleteMDRRuleGroupInstance(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance mdrRuleGroupInstance)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleGroupInstanceLocalService.deleteMDRRuleGroupInstance(mdrRuleGroupInstance);
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
		return _mdrRuleGroupInstanceLocalService.dynamicQuery(dynamicQuery);
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
		return _mdrRuleGroupInstanceLocalService.dynamicQuery(dynamicQuery,
			start, end);
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
		return _mdrRuleGroupInstanceLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _mdrRuleGroupInstanceLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the m d r rule group instance with the primary key.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance
	* @throws PortalException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance getMDRRuleGroupInstance(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstance(ruleGroupInstanceId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the m d r rule group instance with the UUID in the group.
	*
	* @param uuid the UUID of m d r rule group instance
	* @param groupId the group id of the m d r rule group instance
	* @return the m d r rule group instance
	* @throws PortalException if a m d r rule group instance with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance getMDRRuleGroupInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstanceByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the m d r rule group instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> getMDRRuleGroupInstances(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstances(start,
			end);
	}

	/**
	* Returns the number of m d r rule group instances.
	*
	* @return the number of m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public int getMDRRuleGroupInstancesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstancesCount();
	}

	/**
	* Updates the m d r rule group instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroupInstance the m d r rule group instance
	* @return the m d r rule group instance that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance updateMDRRuleGroupInstance(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance mdrRuleGroupInstance)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.updateMDRRuleGroupInstance(mdrRuleGroupInstance);
	}

	/**
	* Updates the m d r rule group instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroupInstance the m d r rule group instance
	* @param merge whether to merge the m d r rule group instance with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the m d r rule group instance that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance updateMDRRuleGroupInstance(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance mdrRuleGroupInstance,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.updateMDRRuleGroupInstance(mdrRuleGroupInstance,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _mdrRuleGroupInstanceLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_mdrRuleGroupInstanceLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.addRuleGroupInstance(groupId,
			className, classPK, ruleGroupId, priority, serviceContext);
	}

	public void deleteRuleGroupInstance(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(ruleGroupInstanceId);
	}

	public void deleteRuleGroupInstance(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance ruleGroupInstance)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(ruleGroupInstance);
	}

	public void deleteRuleGroupInstances(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleGroupInstanceLocalService.deleteRuleGroupInstances(ruleGroupId);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchRuleGroupInstance(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.fetchRuleGroupInstance(ruleGroupInstanceId);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchRuleGroupInstance(
		java.lang.String className, long classPK, long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.fetchRuleGroupInstance(className,
			classPK, ruleGroupId);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance getRuleGroupInstance(
		java.lang.String className, long classPK, long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstance(className,
			classPK, ruleGroupId);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstances(ruleGroupId);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		long ruleGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstances(ruleGroupId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstances(className,
			classPK);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		java.lang.String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstances(className,
			classPK, start, end, orderByComparator);
	}

	public int getRuleGroupInstancesCount(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstancesCount(ruleGroupId);
	}

	public int getRuleGroupInstancesCount(java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstancesCount(className,
			classPK);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance updateRuleGroupInstance(
		long ruleGroupInstanceId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupInstanceLocalService.updateRuleGroupInstance(ruleGroupInstanceId,
			priority);
	}

	public MDRRuleGroupInstanceLocalService getWrappedMDRRuleGroupInstanceLocalService() {
		return _mdrRuleGroupInstanceLocalService;
	}

	public void setWrappedMDRRuleGroupInstanceLocalService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {
		_mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	private MDRRuleGroupInstanceLocalService _mdrRuleGroupInstanceLocalService;
}