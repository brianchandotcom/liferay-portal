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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MDRRuleLocalService}.
 *
 * @author Edward C. Han
 * @see MDRRuleLocalService
 * @generated
 */
@ProviderType
public class MDRRuleLocalServiceWrapper implements MDRRuleLocalService,
	ServiceWrapper<MDRRuleLocalService> {
	public MDRRuleLocalServiceWrapper(MDRRuleLocalService mdrRuleLocalService) {
		_mdrRuleLocalService = mdrRuleLocalService;
	}

	/**
	* Adds the mdr rule to the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRule the mdr rule
	* @return the mdr rule that was added
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRule addMDRRule(
		com.liferay.mobile.device.rules.model.MDRRule mdrRule) {
		return _mdrRuleLocalService.addMDRRule(mdrRule);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRule addRule(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.addRule(ruleGroupId, nameMap,
			descriptionMap, type, typeSettingsProperties, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRule addRule(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.addRule(ruleGroupId, nameMap,
			descriptionMap, type, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRule copyRule(
		com.liferay.mobile.device.rules.model.MDRRule rule, long ruleGroupId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.copyRule(rule, ruleGroupId, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRule copyRule(long ruleId,
		long ruleGroupId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.copyRule(ruleId, ruleGroupId, serviceContext);
	}

	/**
	* Creates a new mdr rule with the primary key. Does not add the mdr rule to the database.
	*
	* @param ruleId the primary key for the new mdr rule
	* @return the new mdr rule
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRule createMDRRule(
		long ruleId) {
		return _mdrRuleLocalService.createMDRRule(ruleId);
	}

	/**
	* Deletes the mdr rule from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRule the mdr rule
	* @return the mdr rule that was removed
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRule deleteMDRRule(
		com.liferay.mobile.device.rules.model.MDRRule mdrRule) {
		return _mdrRuleLocalService.deleteMDRRule(mdrRule);
	}

	/**
	* Deletes the mdr rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleId the primary key of the mdr rule
	* @return the mdr rule that was removed
	* @throws PortalException if a mdr rule with the primary key could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRule deleteMDRRule(
		long ruleId) throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.deleteMDRRule(ruleId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRule fetchMDRRule(
		long ruleId) {
		return _mdrRuleLocalService.fetchMDRRule(ruleId);
	}

	/**
	* Returns the mdr rule matching the UUID and group.
	*
	* @param uuid the mdr rule's UUID
	* @param groupId the primary key of the group
	* @return the matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRule fetchMDRRuleByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _mdrRuleLocalService.fetchMDRRuleByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRule fetchRule(long ruleId) {
		return _mdrRuleLocalService.fetchRule(ruleId);
	}

	/**
	* Returns the mdr rule with the primary key.
	*
	* @param ruleId the primary key of the mdr rule
	* @return the mdr rule
	* @throws PortalException if a mdr rule with the primary key could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRule getMDRRule(long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.getMDRRule(ruleId);
	}

	/**
	* Returns the mdr rule matching the UUID and group.
	*
	* @param uuid the mdr rule's UUID
	* @param groupId the primary key of the group
	* @return the matching mdr rule
	* @throws PortalException if a matching mdr rule could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRule getMDRRuleByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.getMDRRuleByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRule getRule(long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.getRule(ruleId);
	}

	/**
	* Updates the mdr rule in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrRule the mdr rule
	* @return the mdr rule that was updated
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRule updateMDRRule(
		com.liferay.mobile.device.rules.model.MDRRule mdrRule) {
		return _mdrRuleLocalService.updateMDRRule(mdrRule);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRule updateRule(
		long ruleId, java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.updateRule(ruleId, nameMap, descriptionMap,
			type, typeSettingsProperties, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRule updateRule(
		long ruleId, java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.updateRule(ruleId, nameMap, descriptionMap,
			type, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _mdrRuleLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mdrRuleLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _mdrRuleLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _mdrRuleLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of mdr rules.
	*
	* @return the number of mdr rules
	*/
	@Override
	public int getMDRRulesCount() {
		return _mdrRuleLocalService.getMDRRulesCount();
	}

	@Override
	public int getRulesCount(long ruleGroupId) {
		return _mdrRuleLocalService.getRulesCount(ruleGroupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _mdrRuleLocalService.getOSGiServiceIdentifier();
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
		return _mdrRuleLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrRuleLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrRuleLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the mdr rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of mdr rules
	* @param end the upper bound of the range of mdr rules (not inclusive)
	* @return the range of mdr rules
	*/
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRule> getMDRRules(
		int start, int end) {
		return _mdrRuleLocalService.getMDRRules(start, end);
	}

	/**
	* Returns all the mdr rules matching the UUID and company.
	*
	* @param uuid the UUID of the mdr rules
	* @param companyId the primary key of the company
	* @return the matching mdr rules, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRule> getMDRRulesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _mdrRuleLocalService.getMDRRulesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of mdr rules matching the UUID and company.
	*
	* @param uuid the UUID of the mdr rules
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of mdr rules
	* @param end the upper bound of the range of mdr rules (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching mdr rules, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRule> getMDRRulesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.mobile.device.rules.model.MDRRule> orderByComparator) {
		return _mdrRuleLocalService.getMDRRulesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRule> getRules(
		long ruleGroupId) {
		return _mdrRuleLocalService.getRules(ruleGroupId);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRule> getRules(
		long ruleGroupId, int start, int end) {
		return _mdrRuleLocalService.getRules(ruleGroupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRule> getRules(
		long ruleGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.mobile.device.rules.model.MDRRule> obc) {
		return _mdrRuleLocalService.getRules(ruleGroupId, start, end, obc);
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
		return _mdrRuleLocalService.dynamicQueryCount(dynamicQuery);
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
		return _mdrRuleLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public void deleteRule(com.liferay.mobile.device.rules.model.MDRRule rule) {
		_mdrRuleLocalService.deleteRule(rule);
	}

	@Override
	public void deleteRule(long ruleId) {
		_mdrRuleLocalService.deleteRule(ruleId);
	}

	@Override
	public void deleteRules(long ruleGroupId) {
		_mdrRuleLocalService.deleteRules(ruleGroupId);
	}

	@Override
	public MDRRuleLocalService getWrappedService() {
		return _mdrRuleLocalService;
	}

	@Override
	public void setWrappedService(MDRRuleLocalService mdrRuleLocalService) {
		_mdrRuleLocalService = mdrRuleLocalService;
	}

	private MDRRuleLocalService _mdrRuleLocalService;
}