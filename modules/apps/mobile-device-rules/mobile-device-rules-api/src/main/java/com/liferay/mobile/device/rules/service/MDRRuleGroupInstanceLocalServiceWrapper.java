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

package com.liferay.mobile.device.rules.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MDRRuleGroupInstanceLocalService}.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstanceLocalService
 * @generated
 */
@ProviderType
public class MDRRuleGroupInstanceLocalServiceWrapper
	implements MDRRuleGroupInstanceLocalService,
		ServiceWrapper<MDRRuleGroupInstanceLocalService> {
	public MDRRuleGroupInstanceLocalServiceWrapper(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {
		_mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	/**
	* Adds the m d r rule group instance to the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroupInstance the m d r rule group instance
	* @return the m d r rule group instance that was added
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance addMDRRuleGroupInstance(
		com.liferay.mobile.device.rules.model.MDRRuleGroupInstance mdrRuleGroupInstance) {
		return _mdrRuleGroupInstanceLocalService.addMDRRuleGroupInstance(mdrRuleGroupInstance);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceLocalService.addRuleGroupInstance(groupId,
			className, classPK, ruleGroupId, priority, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance addRuleGroupInstance(
		long groupId, java.lang.String className, long classPK,
		long ruleGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceLocalService.addRuleGroupInstance(groupId,
			className, classPK, ruleGroupId, serviceContext);
	}

	/**
	* Creates a new m d r rule group instance with the primary key. Does not add the m d r rule group instance to the database.
	*
	* @param ruleGroupInstanceId the primary key for the new m d r rule group instance
	* @return the new m d r rule group instance
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance createMDRRuleGroupInstance(
		long ruleGroupInstanceId) {
		return _mdrRuleGroupInstanceLocalService.createMDRRuleGroupInstance(ruleGroupInstanceId);
	}

	@Override
	public void deleteGroupRuleGroupInstances(long groupId) {
		_mdrRuleGroupInstanceLocalService.deleteGroupRuleGroupInstances(groupId);
	}

	/**
	* Deletes the m d r rule group instance from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroupInstance the m d r rule group instance
	* @return the m d r rule group instance that was removed
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance deleteMDRRuleGroupInstance(
		com.liferay.mobile.device.rules.model.MDRRuleGroupInstance mdrRuleGroupInstance) {
		return _mdrRuleGroupInstanceLocalService.deleteMDRRuleGroupInstance(mdrRuleGroupInstance);
	}

	/**
	* Deletes the m d r rule group instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance that was removed
	* @throws PortalException if a m d r rule group instance with the primary key could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance deleteMDRRuleGroupInstance(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceLocalService.deleteMDRRuleGroupInstance(ruleGroupInstanceId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteRuleGroupInstance(
		com.liferay.mobile.device.rules.model.MDRRuleGroupInstance ruleGroupInstance) {
		_mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(ruleGroupInstance);
	}

	@Override
	public void deleteRuleGroupInstance(long ruleGroupInstanceId) {
		_mdrRuleGroupInstanceLocalService.deleteRuleGroupInstance(ruleGroupInstanceId);
	}

	@Override
	public void deleteRuleGroupInstances(long ruleGroupId) {
		_mdrRuleGroupInstanceLocalService.deleteRuleGroupInstances(ruleGroupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mdrRuleGroupInstanceLocalService.dynamicQuery();
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
		return _mdrRuleGroupInstanceLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrRuleGroupInstanceLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrRuleGroupInstanceLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _mdrRuleGroupInstanceLocalService.dynamicQueryCount(dynamicQuery);
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
		return _mdrRuleGroupInstanceLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance fetchMDRRuleGroupInstance(
		long ruleGroupInstanceId) {
		return _mdrRuleGroupInstanceLocalService.fetchMDRRuleGroupInstance(ruleGroupInstanceId);
	}

	/**
	* Returns the m d r rule group instance matching the UUID and group.
	*
	* @param uuid the m d r rule group instance's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance fetchMDRRuleGroupInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _mdrRuleGroupInstanceLocalService.fetchMDRRuleGroupInstanceByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance fetchRuleGroupInstance(
		java.lang.String className, long classPK, long ruleGroupId) {
		return _mdrRuleGroupInstanceLocalService.fetchRuleGroupInstance(className,
			classPK, ruleGroupId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance fetchRuleGroupInstance(
		long ruleGroupInstanceId) {
		return _mdrRuleGroupInstanceLocalService.fetchRuleGroupInstance(ruleGroupInstanceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _mdrRuleGroupInstanceLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _mdrRuleGroupInstanceLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _mdrRuleGroupInstanceLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the m d r rule group instance with the primary key.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance
	* @throws PortalException if a m d r rule group instance with the primary key could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance getMDRRuleGroupInstance(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstance(ruleGroupInstanceId);
	}

	/**
	* Returns the m d r rule group instance matching the UUID and group.
	*
	* @param uuid the m d r rule group instance's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r rule group instance
	* @throws PortalException if a matching m d r rule group instance could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance getMDRRuleGroupInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstanceByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the m d r rule group instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of m d r rule group instances
	*/
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> getMDRRuleGroupInstances(
		int start, int end) {
		return _mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstances(start,
			end);
	}

	/**
	* Returns all the m d r rule group instances matching the UUID and company.
	*
	* @param uuid the UUID of the m d r rule group instances
	* @param companyId the primary key of the company
	* @return the matching m d r rule group instances, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> getMDRRuleGroupInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstancesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of m d r rule group instances matching the UUID and company.
	*
	* @param uuid the UUID of the m d r rule group instances
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching m d r rule group instances, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> getMDRRuleGroupInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> orderByComparator) {
		return _mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstancesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of m d r rule group instances.
	*
	* @return the number of m d r rule group instances
	*/
	@Override
	public int getMDRRuleGroupInstancesCount() {
		return _mdrRuleGroupInstanceLocalService.getMDRRuleGroupInstancesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _mdrRuleGroupInstanceLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance getRuleGroupInstance(
		java.lang.String className, long classPK, long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstance(className,
			classPK, ruleGroupId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance getRuleGroupInstance(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstance(ruleGroupInstanceId);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		java.lang.String className, long classPK) {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstances(className,
			classPK);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		java.lang.String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> orderByComparator) {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstances(className,
			classPK, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		long ruleGroupId) {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstances(ruleGroupId);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		long ruleGroupId, int start, int end) {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstances(ruleGroupId,
			start, end);
	}

	@Override
	public int getRuleGroupInstancesCount(java.lang.String className,
		long classPK) {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstancesCount(className,
			classPK);
	}

	@Override
	public int getRuleGroupInstancesCount(long ruleGroupId) {
		return _mdrRuleGroupInstanceLocalService.getRuleGroupInstancesCount(ruleGroupId);
	}

	/**
	* Updates the m d r rule group instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroupInstance the m d r rule group instance
	* @return the m d r rule group instance that was updated
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance updateMDRRuleGroupInstance(
		com.liferay.mobile.device.rules.model.MDRRuleGroupInstance mdrRuleGroupInstance) {
		return _mdrRuleGroupInstanceLocalService.updateMDRRuleGroupInstance(mdrRuleGroupInstance);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroupInstance updateRuleGroupInstance(
		long ruleGroupInstanceId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupInstanceLocalService.updateRuleGroupInstance(ruleGroupInstanceId,
			priority);
	}

	@Override
	public MDRRuleGroupInstanceLocalService getWrappedService() {
		return _mdrRuleGroupInstanceLocalService;
	}

	@Override
	public void setWrappedService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {
		_mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	private MDRRuleGroupInstanceLocalService _mdrRuleGroupInstanceLocalService;
}