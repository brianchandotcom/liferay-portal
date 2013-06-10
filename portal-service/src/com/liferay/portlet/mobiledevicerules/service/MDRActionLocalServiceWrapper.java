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

package com.liferay.portlet.mobiledevicerules.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MDRActionLocalService}.
 *
 * @author Edward C. Han
 * @see MDRActionLocalService
 * @generated
 */
public class MDRActionLocalServiceWrapper implements MDRActionLocalService,
	ServiceWrapper<MDRActionLocalService> {
	public MDRActionLocalServiceWrapper(
		MDRActionLocalService mdrActionLocalService) {
		_mdrActionLocalService = mdrActionLocalService;
	}

	/**
	* Adds the m d r action to the database. Also notifies the appropriate model listeners.
	*
	* @param mdrAction the m d r action
	* @return the m d r action that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction addMDRAction(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.addMDRAction(mdrAction);
	}

	/**
	* Creates a new m d r action with the primary key. Does not add the m d r action to the database.
	*
	* @param actionId the primary key for the new m d r action
	* @return the new m d r action
	*/
	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction createMDRAction(
		long actionId) {
		return _mdrActionLocalService.createMDRAction(actionId);
	}

	/**
	* Deletes the m d r action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action that was removed
	* @throws PortalException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction deleteMDRAction(
		long actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.deleteMDRAction(actionId);
	}

	/**
	* Deletes the m d r action from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrAction the m d r action
	* @return the m d r action that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction deleteMDRAction(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.deleteMDRAction(mdrAction);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mdrActionLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction fetchMDRAction(
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.fetchMDRAction(actionId);
	}

	/**
	* Returns the m d r action with the primary key.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action
	* @throws PortalException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction getMDRAction(
		long actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.getMDRAction(actionId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the m d r action matching the UUID and group.
	*
	* @param uuid the m d r action's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r action
	* @throws PortalException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction getMDRActionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.getMDRActionByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the m d r actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of m d r actions
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> getMDRActions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.getMDRActions(start, end);
	}

	/**
	* Returns the number of m d r actions.
	*
	* @return the number of m d r actions
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getMDRActionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.getMDRActionsCount();
	}

	/**
	* Updates the m d r action in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrAction the m d r action
	* @return the m d r action that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction updateMDRAction(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.updateMDRAction(mdrAction);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _mdrActionLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_mdrActionLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction addAction(
		long ruleGroupInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.addAction(ruleGroupInstanceId, nameMap,
			descriptionMap, type, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction addAction(
		long ruleGroupInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.addAction(ruleGroupInstanceId, nameMap,
			descriptionMap, type, typeSettingsProperties, serviceContext);
	}

	@Override
	public void deleteAction(long actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mdrActionLocalService.deleteAction(actionId);
	}

	@Override
	public void deleteAction(
		com.liferay.portlet.mobiledevicerules.model.MDRAction action)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mdrActionLocalService.deleteAction(action);
	}

	@Override
	public void deleteActions(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mdrActionLocalService.deleteActions(ruleGroupInstanceId);
	}

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction fetchAction(
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.fetchAction(actionId);
	}

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction getAction(
		long actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.getAction(actionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> getActions(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.getActions(ruleGroupInstanceId);
	}

	@Override
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> getActions(
		long ruleGroupInstanceId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.getActions(ruleGroupInstanceId, start, end);
	}

	@Override
	public int getActionsCount(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.getActionsCount(ruleGroupInstanceId);
	}

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction updateAction(
		long actionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.updateAction(actionId, nameMap,
			descriptionMap, type, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction updateAction(
		long actionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrActionLocalService.updateAction(actionId, nameMap,
			descriptionMap, type, typeSettingsProperties, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public MDRActionLocalService getWrappedMDRActionLocalService() {
		return _mdrActionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedMDRActionLocalService(
		MDRActionLocalService mdrActionLocalService) {
		_mdrActionLocalService = mdrActionLocalService;
	}

	@Override
	public MDRActionLocalService getWrappedService() {
		return _mdrActionLocalService;
	}

	@Override
	public void setWrappedService(MDRActionLocalService mdrActionLocalService) {
		_mdrActionLocalService = mdrActionLocalService;
	}

	private MDRActionLocalService _mdrActionLocalService;
}