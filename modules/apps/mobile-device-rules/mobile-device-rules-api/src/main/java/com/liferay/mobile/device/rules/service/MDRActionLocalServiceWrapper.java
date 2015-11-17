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
 * Provides a wrapper for {@link MDRActionLocalService}.
 *
 * @author Edward C. Han
 * @see MDRActionLocalService
 * @generated
 */
@ProviderType
public class MDRActionLocalServiceWrapper implements MDRActionLocalService,
	ServiceWrapper<MDRActionLocalService> {
	public MDRActionLocalServiceWrapper(
		MDRActionLocalService mdrActionLocalService) {
		_mdrActionLocalService = mdrActionLocalService;
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRAction addAction(
		long ruleGroupInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrActionLocalService.addAction(ruleGroupInstanceId, nameMap,
			descriptionMap, type, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRAction addAction(
		long ruleGroupInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrActionLocalService.addAction(ruleGroupInstanceId, nameMap,
			descriptionMap, type, typeSettingsProperties, serviceContext);
	}

	/**
	* Adds the m d r action to the database. Also notifies the appropriate model listeners.
	*
	* @param mdrAction the m d r action
	* @return the m d r action that was added
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRAction addMDRAction(
		com.liferay.mobile.device.rules.model.MDRAction mdrAction) {
		return _mdrActionLocalService.addMDRAction(mdrAction);
	}

	/**
	* Creates a new m d r action with the primary key. Does not add the m d r action to the database.
	*
	* @param actionId the primary key for the new m d r action
	* @return the new m d r action
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRAction createMDRAction(
		long actionId) {
		return _mdrActionLocalService.createMDRAction(actionId);
	}

	@Override
	public void deleteAction(
		com.liferay.mobile.device.rules.model.MDRAction action) {
		_mdrActionLocalService.deleteAction(action);
	}

	@Override
	public void deleteAction(long actionId) {
		_mdrActionLocalService.deleteAction(actionId);
	}

	@Override
	public void deleteActions(long ruleGroupInstanceId) {
		_mdrActionLocalService.deleteActions(ruleGroupInstanceId);
	}

	/**
	* Deletes the m d r action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action that was removed
	* @throws PortalException if a m d r action with the primary key could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRAction deleteMDRAction(
		long actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrActionLocalService.deleteMDRAction(actionId);
	}

	/**
	* Deletes the m d r action from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrAction the m d r action
	* @return the m d r action that was removed
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRAction deleteMDRAction(
		com.liferay.mobile.device.rules.model.MDRAction mdrAction) {
		return _mdrActionLocalService.deleteMDRAction(mdrAction);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrActionLocalService.deletePersistedModel(persistedModel);
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
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _mdrActionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrActionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrActionLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _mdrActionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _mdrActionLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRAction fetchAction(
		long actionId) {
		return _mdrActionLocalService.fetchAction(actionId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRAction fetchMDRAction(
		long actionId) {
		return _mdrActionLocalService.fetchMDRAction(actionId);
	}

	/**
	* Returns the m d r action matching the UUID and group.
	*
	* @param uuid the m d r action's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r action, or <code>null</code> if a matching m d r action could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRAction fetchMDRActionByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _mdrActionLocalService.fetchMDRActionByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRAction getAction(
		long actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrActionLocalService.getAction(actionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _mdrActionLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRAction> getActions(
		long ruleGroupInstanceId) {
		return _mdrActionLocalService.getActions(ruleGroupInstanceId);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRAction> getActions(
		long ruleGroupInstanceId, int start, int end) {
		return _mdrActionLocalService.getActions(ruleGroupInstanceId, start, end);
	}

	@Override
	public int getActionsCount(long ruleGroupInstanceId) {
		return _mdrActionLocalService.getActionsCount(ruleGroupInstanceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _mdrActionLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _mdrActionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the m d r action with the primary key.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action
	* @throws PortalException if a m d r action with the primary key could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRAction getMDRAction(
		long actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrActionLocalService.getMDRAction(actionId);
	}

	/**
	* Returns the m d r action matching the UUID and group.
	*
	* @param uuid the m d r action's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r action
	* @throws PortalException if a matching m d r action could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRAction getMDRActionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrActionLocalService.getMDRActionByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the m d r actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of m d r actions
	*/
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRAction> getMDRActions(
		int start, int end) {
		return _mdrActionLocalService.getMDRActions(start, end);
	}

	/**
	* Returns all the m d r actions matching the UUID and company.
	*
	* @param uuid the UUID of the m d r actions
	* @param companyId the primary key of the company
	* @return the matching m d r actions, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRAction> getMDRActionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _mdrActionLocalService.getMDRActionsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of m d r actions matching the UUID and company.
	*
	* @param uuid the UUID of the m d r actions
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching m d r actions, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRAction> getMDRActionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.mobile.device.rules.model.MDRAction> orderByComparator) {
		return _mdrActionLocalService.getMDRActionsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of m d r actions.
	*
	* @return the number of m d r actions
	*/
	@Override
	public int getMDRActionsCount() {
		return _mdrActionLocalService.getMDRActionsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _mdrActionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrActionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRAction updateAction(
		long actionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrActionLocalService.updateAction(actionId, nameMap,
			descriptionMap, type, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRAction updateAction(
		long actionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrActionLocalService.updateAction(actionId, nameMap,
			descriptionMap, type, typeSettingsProperties, serviceContext);
	}

	/**
	* Updates the m d r action in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrAction the m d r action
	* @return the m d r action that was updated
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRAction updateMDRAction(
		com.liferay.mobile.device.rules.model.MDRAction mdrAction) {
		return _mdrActionLocalService.updateMDRAction(mdrAction);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public MDRActionLocalService getWrappedMDRActionLocalService() {
		return _mdrActionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
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