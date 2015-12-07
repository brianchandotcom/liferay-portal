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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for MDRRuleGroup. This utility wraps
 * {@link com.liferay.mobile.device.rules.service.impl.MDRRuleGroupLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupLocalService
 * @see com.liferay.mobile.device.rules.service.base.MDRRuleGroupLocalServiceBaseImpl
 * @see com.liferay.mobile.device.rules.service.impl.MDRRuleGroupLocalServiceImpl
 * @generated
 */
@ProviderType
public class MDRRuleGroupLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.mobile.device.rules.service.impl.MDRRuleGroupLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the m d r rule group to the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the m d r rule group
	* @return the m d r rule group that was added
	*/
	public static com.liferay.mobile.device.rules.model.MDRRuleGroup addMDRRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup mdrRuleGroup) {
		return getService().addMDRRuleGroup(mdrRuleGroup);
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup addRuleGroup(
		long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRuleGroup(groupId, nameMap, descriptionMap,
			serviceContext);
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup copyRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup ruleGroup,
		long groupId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().copyRuleGroup(ruleGroup, groupId, serviceContext);
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup copyRuleGroup(
		long ruleGroupId, long groupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().copyRuleGroup(ruleGroupId, groupId, serviceContext);
	}

	/**
	* Creates a new m d r rule group with the primary key. Does not add the m d r rule group to the database.
	*
	* @param ruleGroupId the primary key for the new m d r rule group
	* @return the new m d r rule group
	*/
	public static com.liferay.mobile.device.rules.model.MDRRuleGroup createMDRRuleGroup(
		long ruleGroupId) {
		return getService().createMDRRuleGroup(ruleGroupId);
	}

	/**
	* Deletes the m d r rule group from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the m d r rule group
	* @return the m d r rule group that was removed
	*/
	public static com.liferay.mobile.device.rules.model.MDRRuleGroup deleteMDRRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup mdrRuleGroup) {
		return getService().deleteMDRRuleGroup(mdrRuleGroup);
	}

	/**
	* Deletes the m d r rule group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleGroupId the primary key of the m d r rule group
	* @return the m d r rule group that was removed
	* @throws PortalException if a m d r rule group with the primary key could not be found
	*/
	public static com.liferay.mobile.device.rules.model.MDRRuleGroup deleteMDRRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMDRRuleGroup(ruleGroupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup ruleGroup) {
		getService().deleteRuleGroup(ruleGroup);
	}

	public static void deleteRuleGroup(long ruleGroupId) {
		getService().deleteRuleGroup(ruleGroupId);
	}

	public static void deleteRuleGroups(long groupId) {
		getService().deleteRuleGroups(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup fetchMDRRuleGroup(
		long ruleGroupId) {
		return getService().fetchMDRRuleGroup(ruleGroupId);
	}

	/**
	* Returns the m d r rule group matching the UUID and group.
	*
	* @param uuid the m d r rule group's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	*/
	public static com.liferay.mobile.device.rules.model.MDRRuleGroup fetchMDRRuleGroupByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchMDRRuleGroupByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup fetchRuleGroup(
		long ruleGroupId) {
		return getService().fetchRuleGroup(ruleGroupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the m d r rule group with the primary key.
	*
	* @param ruleGroupId the primary key of the m d r rule group
	* @return the m d r rule group
	* @throws PortalException if a m d r rule group with the primary key could not be found
	*/
	public static com.liferay.mobile.device.rules.model.MDRRuleGroup getMDRRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMDRRuleGroup(ruleGroupId);
	}

	/**
	* Returns the m d r rule group matching the UUID and group.
	*
	* @param uuid the m d r rule group's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r rule group
	* @throws PortalException if a matching m d r rule group could not be found
	*/
	public static com.liferay.mobile.device.rules.model.MDRRuleGroup getMDRRuleGroupByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMDRRuleGroupByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the m d r rule groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @return the range of m d r rule groups
	*/
	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getMDRRuleGroups(
		int start, int end) {
		return getService().getMDRRuleGroups(start, end);
	}

	/**
	* Returns all the m d r rule groups matching the UUID and company.
	*
	* @param uuid the UUID of the m d r rule groups
	* @param companyId the primary key of the company
	* @return the matching m d r rule groups, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getMDRRuleGroupsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getMDRRuleGroupsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of m d r rule groups matching the UUID and company.
	*
	* @param uuid the UUID of the m d r rule groups
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching m d r rule groups, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getMDRRuleGroupsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.mobile.device.rules.model.MDRRuleGroup> orderByComparator) {
		return getService()
				   .getMDRRuleGroupsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of m d r rule groups.
	*
	* @return the number of m d r rule groups
	*/
	public static int getMDRRuleGroupsCount() {
		return getService().getMDRRuleGroupsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup getRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRuleGroup(ruleGroupId);
	}

	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getRuleGroups(
		long groupId) {
		return getService().getRuleGroups(groupId);
	}

	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getRuleGroups(
		long groupId, int start, int end) {
		return getService().getRuleGroups(groupId, start, end);
	}

	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getRuleGroups(
		long[] groupIds, int start, int end) {
		return getService().getRuleGroups(groupIds, start, end);
	}

	public static int getRuleGroupsCount(long groupId) {
		return getService().getRuleGroupsCount(groupId);
	}

	public static int getRuleGroupsCount(long[] groupIds) {
		return getService().getRuleGroupsCount(groupIds);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long, String,
	LinkedHashMap, boolean, int, int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> search(
		long groupId, java.lang.String name, boolean andOperator, int start,
		int end) {
		return getService().search(groupId, name, andOperator, start, end);
	}

	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> search(
		long groupId, java.lang.String name,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end) {
		return getService()
				   .search(groupId, name, params, andOperator, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #searchByKeywords(long,
	String, LinkedHashMap, boolean, int, int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> searchByKeywords(
		long groupId, java.lang.String keywords, boolean andOperator,
		int start, int end) {
		return getService()
				   .searchByKeywords(groupId, keywords, andOperator, start, end);
	}

	public static java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> searchByKeywords(
		long groupId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end) {
		return getService()
				   .searchByKeywords(groupId, keywords, params, andOperator,
			start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #searchByKeywordsCount(long,
	String, LinkedHashMap, boolean)}
	*/
	@Deprecated
	public static int searchByKeywordsCount(long groupId,
		java.lang.String keywords, boolean andOperator) {
		return getService().searchByKeywordsCount(groupId, keywords, andOperator);
	}

	public static int searchByKeywordsCount(long groupId,
		java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return getService()
				   .searchByKeywordsCount(groupId, keywords, params, andOperator);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #searchCount(long, String,
	LinkedHashMap, boolean)}
	*/
	@Deprecated
	public static int searchCount(long groupId, java.lang.String name,
		boolean andOperator) {
		return getService().searchCount(groupId, name, andOperator);
	}

	public static int searchCount(long groupId, java.lang.String name,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return getService().searchCount(groupId, name, params, andOperator);
	}

	/**
	* Updates the m d r rule group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the m d r rule group
	* @return the m d r rule group that was updated
	*/
	public static com.liferay.mobile.device.rules.model.MDRRuleGroup updateMDRRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup mdrRuleGroup) {
		return getService().updateMDRRuleGroup(mdrRuleGroup);
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup updateRuleGroup(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRuleGroup(ruleGroupId, nameMap, descriptionMap,
			serviceContext);
	}

	public static MDRRuleGroupLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(MDRRuleGroupLocalService service) {
	}

	private static ServiceTracker<MDRRuleGroupLocalService, MDRRuleGroupLocalService> _serviceTracker =
		ServiceTrackerFactory.open(MDRRuleGroupLocalService.class);
}