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
 * Provides a wrapper for {@link MDRRuleLocalService}.
 *
 * @author    Edward C. Han
 * @see       MDRRuleLocalService
 * @generated
 */
public class MDRRuleLocalServiceWrapper implements MDRRuleLocalService,
	ServiceWrapper<MDRRuleLocalService> {
	public MDRRuleLocalServiceWrapper(MDRRuleLocalService mdrRuleLocalService) {
		_mdrRuleLocalService = mdrRuleLocalService;
	}

	/**
	* Adds the m d r rule to the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRule the m d r rule
	* @return the m d r rule that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule addMDRRule(
		com.liferay.portlet.mobiledevicerules.model.MDRRule mdrRule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.addMDRRule(mdrRule);
	}

	/**
	* Creates a new m d r rule with the primary key. Does not add the m d r rule to the database.
	*
	* @param ruleId the primary key for the new m d r rule
	* @return the new m d r rule
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule createMDRRule(
		long ruleId) {
		return _mdrRuleLocalService.createMDRRule(ruleId);
	}

	/**
	* Deletes the m d r rule with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleId the primary key of the m d r rule
	* @return the m d r rule that was removed
	* @throws PortalException if a m d r rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule deleteMDRRule(
		long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.deleteMDRRule(ruleId);
	}

	/**
	* Deletes the m d r rule from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRule the m d r rule
	* @return the m d r rule that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule deleteMDRRule(
		com.liferay.portlet.mobiledevicerules.model.MDRRule mdrRule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.deleteMDRRule(mdrRule);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mdrRuleLocalService.dynamicQuery();
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
		return _mdrRuleLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrRuleLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrRuleLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _mdrRuleLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule fetchMDRRule(
		long ruleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.fetchMDRRule(ruleId);
	}

	/**
	* Returns the m d r rule with the primary key.
	*
	* @param ruleId the primary key of the m d r rule
	* @return the m d r rule
	* @throws PortalException if a m d r rule with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule getMDRRule(
		long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.getMDRRule(ruleId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the m d r rule matching the UUID and group.
	*
	* @param uuid the m d r rule's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r rule
	* @throws PortalException if a matching m d r rule could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule getMDRRuleByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.getMDRRuleByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the m d r rules.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rules
	* @param end the upper bound of the range of m d r rules (not inclusive)
	* @return the range of m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> getMDRRules(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.getMDRRules(start, end);
	}

	/**
	* Returns the number of m d r rules.
	*
	* @return the number of m d r rules
	* @throws SystemException if a system exception occurred
	*/
	public int getMDRRulesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.getMDRRulesCount();
	}

	/**
	* Updates the m d r rule in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrRule the m d r rule
	* @return the m d r rule that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRule updateMDRRule(
		com.liferay.portlet.mobiledevicerules.model.MDRRule mdrRule)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.updateMDRRule(mdrRule);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _mdrRuleLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_mdrRuleLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule addRule(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.addRule(ruleGroupId, nameMap,
			descriptionMap, type, typeSettings, serviceContext);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule addRule(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.addRule(ruleGroupId, nameMap,
			descriptionMap, type, typeSettingsProperties, serviceContext);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule copyRule(
		long ruleId, long ruleGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.copyRule(ruleId, ruleGroupId, serviceContext);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule copyRule(
		com.liferay.portlet.mobiledevicerules.model.MDRRule rule,
		long ruleGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.copyRule(rule, ruleGroupId, serviceContext);
	}

	public void deleteRule(long ruleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleLocalService.deleteRule(ruleId);
	}

	public void deleteRule(
		com.liferay.portlet.mobiledevicerules.model.MDRRule rule)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleLocalService.deleteRule(rule);
	}

	public void deleteRules(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleLocalService.deleteRules(ruleGroupId);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule fetchRule(
		long ruleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.fetchRule(ruleId);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule getRule(
		long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.getRule(ruleId);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> getRules(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.getRules(ruleGroupId);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRule> getRules(
		long ruleGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.getRules(ruleGroupId, start, end);
	}

	public int getRulesCount(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.getRulesCount(ruleGroupId);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule updateRule(
		long ruleId, java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String typeSettings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.updateRule(ruleId, nameMap, descriptionMap,
			type, typeSettings, serviceContext);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRule updateRule(
		long ruleId, java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleLocalService.updateRule(ruleId, nameMap, descriptionMap,
			type, typeSettingsProperties, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public MDRRuleLocalService getWrappedMDRRuleLocalService() {
		return _mdrRuleLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedMDRRuleLocalService(
		MDRRuleLocalService mdrRuleLocalService) {
		_mdrRuleLocalService = mdrRuleLocalService;
	}

	public MDRRuleLocalService getWrappedService() {
		return _mdrRuleLocalService;
	}

	public void setWrappedService(MDRRuleLocalService mdrRuleLocalService) {
		_mdrRuleLocalService = mdrRuleLocalService;
	}

	private MDRRuleLocalService _mdrRuleLocalService;
}