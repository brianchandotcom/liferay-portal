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

import com.liferay.mobile.device.rules.model.MDRRule;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

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

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.mobile.device.rules.service.impl.MDRRuleLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the mdr rule to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MDRRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mdrRule the mdr rule
	 * @return the mdr rule that was added
	 */
	public static MDRRule addMDRRule(MDRRule mdrRule) {
		return getService().addMDRRule(mdrRule);
	}

	public static MDRRule addRule(
			long ruleGroupId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addRule(
			ruleGroupId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	public static MDRRule addRule(
			long ruleGroupId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addRule(
			ruleGroupId, nameMap, descriptionMap, type,
			typeSettingsUnicodeProperties, serviceContext);
	}

	public static MDRRule copyRule(
			long ruleId, long ruleGroupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().copyRule(ruleId, ruleGroupId, serviceContext);
	}

	public static MDRRule copyRule(
			MDRRule rule, long ruleGroupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().copyRule(rule, ruleGroupId, serviceContext);
	}

	/**
	 * Creates a new mdr rule with the primary key. Does not add the mdr rule to the database.
	 *
	 * @param ruleId the primary key for the new mdr rule
	 * @return the new mdr rule
	 */
	public static MDRRule createMDRRule(long ruleId) {
		return getService().createMDRRule(ruleId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the mdr rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MDRRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ruleId the primary key of the mdr rule
	 * @return the mdr rule that was removed
	 * @throws PortalException if a mdr rule with the primary key could not be found
	 */
	public static MDRRule deleteMDRRule(long ruleId) throws PortalException {
		return getService().deleteMDRRule(ruleId);
	}

	/**
	 * Deletes the mdr rule from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MDRRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mdrRule the mdr rule
	 * @return the mdr rule that was removed
	 */
	public static MDRRule deleteMDRRule(MDRRule mdrRule) {
		return getService().deleteMDRRule(mdrRule);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteRule(long ruleId) {
		getService().deleteRule(ruleId);
	}

	public static void deleteRule(MDRRule rule) {
		getService().deleteRule(rule);
	}

	public static void deleteRules(long ruleGroupId) {
		getService().deleteRules(ruleGroupId);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static MDRRule fetchMDRRule(long ruleId) {
		return getService().fetchMDRRule(ruleId);
	}

	/**
	 * Returns the mdr rule matching the UUID and group.
	 *
	 * @param uuid the mdr rule's UUID
	 * @param groupId the primary key of the group
	 * @return the matching mdr rule, or <code>null</code> if a matching mdr rule could not be found
	 */
	public static MDRRule fetchMDRRuleByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchMDRRuleByUuidAndGroupId(uuid, groupId);
	}

	public static MDRRule fetchRule(long ruleId) {
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
	public static MDRRule getMDRRule(long ruleId) throws PortalException {
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
	public static MDRRule getMDRRuleByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

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
	public static List<MDRRule> getMDRRules(int start, int end) {
		return getService().getMDRRules(start, end);
	}

	/**
	 * Returns all the mdr rules matching the UUID and company.
	 *
	 * @param uuid the UUID of the mdr rules
	 * @param companyId the primary key of the company
	 * @return the matching mdr rules, or an empty list if no matches were found
	 */
	public static List<MDRRule> getMDRRulesByUuidAndCompanyId(
		String uuid, long companyId) {

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
	public static List<MDRRule> getMDRRulesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MDRRule> orderByComparator) {

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

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static MDRRule getRule(long ruleId) throws PortalException {
		return getService().getRule(ruleId);
	}

	public static List<MDRRule> getRules(long ruleGroupId) {
		return getService().getRules(ruleGroupId);
	}

	public static List<MDRRule> getRules(long ruleGroupId, int start, int end) {
		return getService().getRules(ruleGroupId, start, end);
	}

	public static List<MDRRule> getRules(
		long ruleGroupId, int start, int end,
		OrderByComparator<MDRRule> orderByComparator) {

		return getService().getRules(
			ruleGroupId, start, end, orderByComparator);
	}

	public static int getRulesCount(long ruleGroupId) {
		return getService().getRulesCount(ruleGroupId);
	}

	/**
	 * Updates the mdr rule in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MDRRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mdrRule the mdr rule
	 * @return the mdr rule that was updated
	 */
	public static MDRRule updateMDRRule(MDRRule mdrRule) {
		return getService().updateMDRRule(mdrRule);
	}

	public static MDRRule updateRule(
			long ruleId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateRule(
			ruleId, nameMap, descriptionMap, type, typeSettings,
			serviceContext);
	}

	public static MDRRule updateRule(
			long ruleId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateRule(
			ruleId, nameMap, descriptionMap, type,
			typeSettingsUnicodeProperties, serviceContext);
	}

	public static MDRRuleLocalService getService() {
		return _service;
	}

	private static volatile MDRRuleLocalService _service;

}