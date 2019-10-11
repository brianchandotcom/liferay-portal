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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for MDRRule. This utility wraps
 * <code>com.liferay.mobile.device.rules.service.impl.MDRRuleLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Edward C. Han
 * @see MDRRuleLocalService
 * @generated
 */
public class MDRRuleLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.mobile.device.rules.service.impl.MDRRuleLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the mdr rule to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mdrRule the mdr rule
	 * @return the mdr rule that was added
	 */
	public static com.liferay.mobile.device.rules.model.MDRRule addMDRRule(
		com.liferay.mobile.device.rules.model.MDRRule mdrRule) {

		return getService().addMDRRule(mdrRule);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule addRule(
			long ruleGroupId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, String type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRule(
			ruleGroupId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule addRule(
			long ruleGroupId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRule(
			ruleGroupId, nameMap, descriptionMap, type, typeSettingsProperties,
			serviceContext);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule copyRule(
			long ruleId, long ruleGroupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().copyRule(ruleId, ruleGroupId, serviceContext);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule copyRule(
			com.liferay.mobile.device.rules.model.MDRRule rule,
			long ruleGroupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().copyRule(rule, ruleGroupId, serviceContext);
	}

	/**
	 * Creates a new mdr rule with the primary key. Does not add the mdr rule to the database.
	 *
	 * @param ruleId the primary key for the new mdr rule
	 * @return the new mdr rule
	 */
	public static com.liferay.mobile.device.rules.model.MDRRule createMDRRule(
		long ruleId) {

		return getService().createMDRRule(ruleId);
	}

	/**
	 * Deletes the mdr rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ruleId the primary key of the mdr rule
	 * @return the mdr rule that was removed
	 * @throws PortalException if a mdr rule with the primary key could not be found
	 */
	public static com.liferay.mobile.device.rules.model.MDRRule deleteMDRRule(
			long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteMDRRule(ruleId);
	}

	/**
	 * Deletes the mdr rule from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mdrRule the mdr rule
	 * @return the mdr rule that was removed
	 */
	public static com.liferay.mobile.device.rules.model.MDRRule deleteMDRRule(
		com.liferay.mobile.device.rules.model.MDRRule mdrRule) {

		return getService().deleteMDRRule(mdrRule);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteRule(long ruleId) {
		getService().deleteRule(ruleId);
	}

	public static void deleteRule(
		com.liferay.mobile.device.rules.model.MDRRule rule) {

		getService().deleteRule(rule);
	}

	public static void deleteRules(long ruleGroupId) {
		getService().deleteRules(ruleGroupId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.mobile.device.rules.model.impl.MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.mobile.device.rules.model.impl.MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule fetchMDRRule(
		long ruleId) {

		return getService().fetchMDRRule(ruleId);
	}

	/**
	 * Returns the mdr rule matching the UUID and group.
	 *
	 * @param uuid the mdr rule's UUID
	 * @param groupId the primary key of the group
	 * @return the matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	 */
	public static com.liferay.mobile.device.rules.model.MDRRule
		fetchMDRRuleByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchMDRRuleByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule fetchRule(
		long ruleId) {

		return getService().fetchRule(ruleId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the mdr rule with the primary key.
	 *
	 * @param ruleId the primary key of the mdr rule
	 * @return the mdr rule
	 * @throws PortalException if a mdr rule with the primary key could not be found
	 */
	public static com.liferay.mobile.device.rules.model.MDRRule getMDRRule(
			long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMDRRule(ruleId);
	}

	/**
	 * Returns the mdr rule matching the UUID and group.
	 *
	 * @param uuid the mdr rule's UUID
	 * @param groupId the primary key of the group
	 * @return the matching mdr rule
	 * @throws PortalException if a matching mdr rule could not be found
	 */
	public static com.liferay.mobile.device.rules.model.MDRRule
			getMDRRuleByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMDRRuleByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the mdr rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.mobile.device.rules.model.impl.MDRRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rules
	 * @param end the upper bound of the range of mdr rules (not inclusive)
	 * @return the range of mdr rules
	 */
	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRule>
		getMDRRules(int start, int end) {

		return getService().getMDRRules(start, end);
	}

	/**
	 * Returns all the mdr rules matching the UUID and company.
	 *
	 * @param uuid the UUID of the mdr rules
	 * @param companyId the primary key of the company
	 * @return the matching mdr rules, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRule>
		getMDRRulesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getMDRRulesByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRule>
		getMDRRulesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.mobile.device.rules.model.MDRRule>
					orderByComparator) {

		return getService().getMDRRulesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of mdr rules.
	 *
	 * @return the number of mdr rules
	 */
	public static int getMDRRulesCount() {
		return getService().getMDRRulesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule getRule(
			long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRule(ruleId);
	}

	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRule>
		getRules(long ruleGroupId) {

		return getService().getRules(ruleGroupId);
	}

	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRule>
		getRules(long ruleGroupId, int start, int end) {

		return getService().getRules(ruleGroupId, start, end);
	}

	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRule>
		getRules(
			long ruleGroupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.mobile.device.rules.model.MDRRule> obc) {

		return getService().getRules(ruleGroupId, start, end, obc);
	}

	public static int getRulesCount(long ruleGroupId) {
		return getService().getRulesCount(ruleGroupId);
	}

	/**
	 * Updates the mdr rule in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mdrRule the mdr rule
	 * @return the mdr rule that was updated
	 */
	public static com.liferay.mobile.device.rules.model.MDRRule updateMDRRule(
		com.liferay.mobile.device.rules.model.MDRRule mdrRule) {

		return getService().updateMDRRule(mdrRule);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule updateRule(
			long ruleId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, String type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRule(
			ruleId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	public static com.liferay.mobile.device.rules.model.MDRRule updateRule(
			long ruleId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRule(
			ruleId, nameMap, descriptionMap, type, typeSettingsProperties,
			serviceContext);
	}

	public static MDRRuleLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MDRRuleLocalService, MDRRuleLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MDRRuleLocalService.class);

		ServiceTracker<MDRRuleLocalService, MDRRuleLocalService>
			serviceTracker =
				new ServiceTracker<MDRRuleLocalService, MDRRuleLocalService>(
					bundle.getBundleContext(), MDRRuleLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}