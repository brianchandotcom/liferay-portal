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

import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for MDRRuleGroup. This utility wraps
 * <code>com.liferay.mobile.device.rules.service.impl.MDRRuleGroupLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupLocalService
 * @generated
 */
public class MDRRuleGroupLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.mobile.device.rules.service.impl.MDRRuleGroupLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the mdr rule group to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MDRRuleGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mdrRuleGroup the mdr rule group
	 * @return the mdr rule group that was added
	 */
	public static MDRRuleGroup addMDRRuleGroup(MDRRuleGroup mdrRuleGroup) {
		return getService().addMDRRuleGroup(mdrRuleGroup);
	}

	public static MDRRuleGroup addRuleGroup(
			long groupId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addRuleGroup(
			groupId, nameMap, descriptionMap, serviceContext);
	}

	public static MDRRuleGroup copyRuleGroup(
			long ruleGroupId, long groupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().copyRuleGroup(ruleGroupId, groupId, serviceContext);
	}

	public static MDRRuleGroup copyRuleGroup(
			MDRRuleGroup ruleGroup, long groupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().copyRuleGroup(ruleGroup, groupId, serviceContext);
	}

	/**
	 * Creates a new mdr rule group with the primary key. Does not add the mdr rule group to the database.
	 *
	 * @param ruleGroupId the primary key for the new mdr rule group
	 * @return the new mdr rule group
	 */
	public static MDRRuleGroup createMDRRuleGroup(long ruleGroupId) {
		return getService().createMDRRuleGroup(ruleGroupId);
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
	 * Deletes the mdr rule group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MDRRuleGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ruleGroupId the primary key of the mdr rule group
	 * @return the mdr rule group that was removed
	 * @throws PortalException if a mdr rule group with the primary key could not be found
	 */
	public static MDRRuleGroup deleteMDRRuleGroup(long ruleGroupId)
		throws PortalException {

		return getService().deleteMDRRuleGroup(ruleGroupId);
	}

	/**
	 * Deletes the mdr rule group from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MDRRuleGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mdrRuleGroup the mdr rule group
	 * @return the mdr rule group that was removed
	 */
	public static MDRRuleGroup deleteMDRRuleGroup(MDRRuleGroup mdrRuleGroup) {
		return getService().deleteMDRRuleGroup(mdrRuleGroup);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteRuleGroup(long ruleGroupId) {
		getService().deleteRuleGroup(ruleGroupId);
	}

	public static void deleteRuleGroup(MDRRuleGroup ruleGroup) {
		getService().deleteRuleGroup(ruleGroup);
	}

	public static void deleteRuleGroups(long groupId) {
		getService().deleteRuleGroups(groupId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.mobile.device.rules.model.impl.MDRRuleGroupModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.mobile.device.rules.model.impl.MDRRuleGroupModelImpl</code>.
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

	public static MDRRuleGroup fetchMDRRuleGroup(long ruleGroupId) {
		return getService().fetchMDRRuleGroup(ruleGroupId);
	}

	/**
	 * Returns the mdr rule group matching the UUID and group.
	 *
	 * @param uuid the mdr rule group's UUID
	 * @param groupId the primary key of the group
	 * @return the matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	 */
	public static MDRRuleGroup fetchMDRRuleGroupByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchMDRRuleGroupByUuidAndGroupId(uuid, groupId);
	}

	public static MDRRuleGroup fetchRuleGroup(long ruleGroupId) {
		return getService().fetchRuleGroup(ruleGroupId);
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
	 * Returns the mdr rule group with the primary key.
	 *
	 * @param ruleGroupId the primary key of the mdr rule group
	 * @return the mdr rule group
	 * @throws PortalException if a mdr rule group with the primary key could not be found
	 */
	public static MDRRuleGroup getMDRRuleGroup(long ruleGroupId)
		throws PortalException {

		return getService().getMDRRuleGroup(ruleGroupId);
	}

	/**
	 * Returns the mdr rule group matching the UUID and group.
	 *
	 * @param uuid the mdr rule group's UUID
	 * @param groupId the primary key of the group
	 * @return the matching mdr rule group
	 * @throws PortalException if a matching mdr rule group could not be found
	 */
	public static MDRRuleGroup getMDRRuleGroupByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getMDRRuleGroupByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the mdr rule groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.mobile.device.rules.model.impl.MDRRuleGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @return the range of mdr rule groups
	 */
	public static List<MDRRuleGroup> getMDRRuleGroups(int start, int end) {
		return getService().getMDRRuleGroups(start, end);
	}

	/**
	 * Returns all the mdr rule groups matching the UUID and company.
	 *
	 * @param uuid the UUID of the mdr rule groups
	 * @param companyId the primary key of the company
	 * @return the matching mdr rule groups, or an empty list if no matches were found
	 */
	public static List<MDRRuleGroup> getMDRRuleGroupsByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getMDRRuleGroupsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of mdr rule groups matching the UUID and company.
	 *
	 * @param uuid the UUID of the mdr rule groups
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of mdr rule groups
	 * @param end the upper bound of the range of mdr rule groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching mdr rule groups, or an empty list if no matches were found
	 */
	public static List<MDRRuleGroup> getMDRRuleGroupsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<MDRRuleGroup> orderByComparator) {

		return getService().getMDRRuleGroupsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of mdr rule groups.
	 *
	 * @return the number of mdr rule groups
	 */
	public static int getMDRRuleGroupsCount() {
		return getService().getMDRRuleGroupsCount();
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

	public static MDRRuleGroup getRuleGroup(long ruleGroupId)
		throws PortalException {

		return getService().getRuleGroup(ruleGroupId);
	}

	public static List<MDRRuleGroup> getRuleGroups(long groupId) {
		return getService().getRuleGroups(groupId);
	}

	public static List<MDRRuleGroup> getRuleGroups(
		long groupId, int start, int end) {

		return getService().getRuleGroups(groupId, start, end);
	}

	public static List<MDRRuleGroup> getRuleGroups(
		long[] groupIds, int start, int end) {

		return getService().getRuleGroups(groupIds, start, end);
	}

	public static int getRuleGroupsCount(long groupId) {
		return getService().getRuleGroupsCount(groupId);
	}

	public static int getRuleGroupsCount(long[] groupIds) {
		return getService().getRuleGroupsCount(groupIds);
	}

	public static List<MDRRuleGroup> search(
		long groupId, String name,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end) {

		return getService().search(
			groupId, name, params, andOperator, start, end);
	}

	public static List<MDRRuleGroup> searchByKeywords(
		long groupId, String keywords,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end) {

		return getService().searchByKeywords(
			groupId, keywords, params, andOperator, start, end);
	}

	public static List<MDRRuleGroup> searchByKeywords(
		long groupId, String keywords,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end, OrderByComparator<MDRRuleGroup> orderByComparator) {

		return getService().searchByKeywords(
			groupId, keywords, params, andOperator, start, end,
			orderByComparator);
	}

	public static int searchByKeywordsCount(
		long groupId, String keywords,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getService().searchByKeywordsCount(
			groupId, keywords, params, andOperator);
	}

	public static int searchCount(
		long groupId, String name,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return getService().searchCount(groupId, name, params, andOperator);
	}

	/**
	 * Updates the mdr rule group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MDRRuleGroupLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mdrRuleGroup the mdr rule group
	 * @return the mdr rule group that was updated
	 */
	public static MDRRuleGroup updateMDRRuleGroup(MDRRuleGroup mdrRuleGroup) {
		return getService().updateMDRRuleGroup(mdrRuleGroup);
	}

	public static MDRRuleGroup updateRuleGroup(
			long ruleGroupId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateRuleGroup(
			ruleGroupId, nameMap, descriptionMap, serviceContext);
	}

	public static MDRRuleGroupLocalService getService() {
		return _service;
	}

	private static volatile MDRRuleGroupLocalService _service;

}