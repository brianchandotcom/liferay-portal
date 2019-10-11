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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MDRRuleGroupLocalService}.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupLocalService
 * @generated
 */
public class MDRRuleGroupLocalServiceWrapper
	implements MDRRuleGroupLocalService,
			   ServiceWrapper<MDRRuleGroupLocalService> {

	public MDRRuleGroupLocalServiceWrapper(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {

		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	/**
	 * Adds the mdr rule group to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mdrRuleGroup the mdr rule group
	 * @return the mdr rule group that was added
	 */
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup addMDRRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup mdrRuleGroup) {

		return _mdrRuleGroupLocalService.addMDRRuleGroup(mdrRuleGroup);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup addRuleGroup(
			long groupId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mdrRuleGroupLocalService.addRuleGroup(
			groupId, nameMap, descriptionMap, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup copyRuleGroup(
			long ruleGroupId, long groupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mdrRuleGroupLocalService.copyRuleGroup(
			ruleGroupId, groupId, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup copyRuleGroup(
			com.liferay.mobile.device.rules.model.MDRRuleGroup ruleGroup,
			long groupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mdrRuleGroupLocalService.copyRuleGroup(
			ruleGroup, groupId, serviceContext);
	}

	/**
	 * Creates a new mdr rule group with the primary key. Does not add the mdr rule group to the database.
	 *
	 * @param ruleGroupId the primary key for the new mdr rule group
	 * @return the new mdr rule group
	 */
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup
		createMDRRuleGroup(long ruleGroupId) {

		return _mdrRuleGroupLocalService.createMDRRuleGroup(ruleGroupId);
	}

	/**
	 * Deletes the mdr rule group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ruleGroupId the primary key of the mdr rule group
	 * @return the mdr rule group that was removed
	 * @throws PortalException if a mdr rule group with the primary key could not be found
	 */
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup
			deleteMDRRuleGroup(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mdrRuleGroupLocalService.deleteMDRRuleGroup(ruleGroupId);
	}

	/**
	 * Deletes the mdr rule group from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mdrRuleGroup the mdr rule group
	 * @return the mdr rule group that was removed
	 */
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup
		deleteMDRRuleGroup(
			com.liferay.mobile.device.rules.model.MDRRuleGroup mdrRuleGroup) {

		return _mdrRuleGroupLocalService.deleteMDRRuleGroup(mdrRuleGroup);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mdrRuleGroupLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteRuleGroup(long ruleGroupId) {
		_mdrRuleGroupLocalService.deleteRuleGroup(ruleGroupId);
	}

	@Override
	public void deleteRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup ruleGroup) {

		_mdrRuleGroupLocalService.deleteRuleGroup(ruleGroup);
	}

	@Override
	public void deleteRuleGroups(long groupId) {
		_mdrRuleGroupLocalService.deleteRuleGroups(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mdrRuleGroupLocalService.dynamicQuery();
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

		return _mdrRuleGroupLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _mdrRuleGroupLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _mdrRuleGroupLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _mdrRuleGroupLocalService.dynamicQueryCount(dynamicQuery);
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

		return _mdrRuleGroupLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup fetchMDRRuleGroup(
		long ruleGroupId) {

		return _mdrRuleGroupLocalService.fetchMDRRuleGroup(ruleGroupId);
	}

	/**
	 * Returns the mdr rule group matching the UUID and group.
	 *
	 * @param uuid the mdr rule group's UUID
	 * @param groupId the primary key of the group
	 * @return the matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	 */
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup
		fetchMDRRuleGroupByUuidAndGroupId(String uuid, long groupId) {

		return _mdrRuleGroupLocalService.fetchMDRRuleGroupByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup fetchRuleGroup(
		long ruleGroupId) {

		return _mdrRuleGroupLocalService.fetchRuleGroup(ruleGroupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _mdrRuleGroupLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _mdrRuleGroupLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mdrRuleGroupLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the mdr rule group with the primary key.
	 *
	 * @param ruleGroupId the primary key of the mdr rule group
	 * @return the mdr rule group
	 * @throws PortalException if a mdr rule group with the primary key could not be found
	 */
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup getMDRRuleGroup(
			long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mdrRuleGroupLocalService.getMDRRuleGroup(ruleGroupId);
	}

	/**
	 * Returns the mdr rule group matching the UUID and group.
	 *
	 * @param uuid the mdr rule group's UUID
	 * @param groupId the primary key of the group
	 * @return the matching mdr rule group
	 * @throws PortalException if a matching mdr rule group could not be found
	 */
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup
			getMDRRuleGroupByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mdrRuleGroupLocalService.getMDRRuleGroupByUuidAndGroupId(
			uuid, groupId);
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
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		getMDRRuleGroups(int start, int end) {

		return _mdrRuleGroupLocalService.getMDRRuleGroups(start, end);
	}

	/**
	 * Returns all the mdr rule groups matching the UUID and company.
	 *
	 * @param uuid the UUID of the mdr rule groups
	 * @param companyId the primary key of the company
	 * @return the matching mdr rule groups, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		getMDRRuleGroupsByUuidAndCompanyId(String uuid, long companyId) {

		return _mdrRuleGroupLocalService.getMDRRuleGroupsByUuidAndCompanyId(
			uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		getMDRRuleGroupsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.mobile.device.rules.model.MDRRuleGroup>
					orderByComparator) {

		return _mdrRuleGroupLocalService.getMDRRuleGroupsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of mdr rule groups.
	 *
	 * @return the number of mdr rule groups
	 */
	@Override
	public int getMDRRuleGroupsCount() {
		return _mdrRuleGroupLocalService.getMDRRuleGroupsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mdrRuleGroupLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mdrRuleGroupLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup getRuleGroup(
			long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mdrRuleGroupLocalService.getRuleGroup(ruleGroupId);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		getRuleGroups(long groupId) {

		return _mdrRuleGroupLocalService.getRuleGroups(groupId);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		getRuleGroups(long groupId, int start, int end) {

		return _mdrRuleGroupLocalService.getRuleGroups(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		getRuleGroups(long[] groupIds, int start, int end) {

		return _mdrRuleGroupLocalService.getRuleGroups(groupIds, start, end);
	}

	@Override
	public int getRuleGroupsCount(long groupId) {
		return _mdrRuleGroupLocalService.getRuleGroupsCount(groupId);
	}

	@Override
	public int getRuleGroupsCount(long[] groupIds) {
		return _mdrRuleGroupLocalService.getRuleGroupsCount(groupIds);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		search(
			long groupId, String name,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end) {

		return _mdrRuleGroupLocalService.search(
			groupId, name, params, andOperator, start, end);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		searchByKeywords(
			long groupId, String keywords,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end) {

		return _mdrRuleGroupLocalService.searchByKeywords(
			groupId, keywords, params, andOperator, start, end);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup>
		searchByKeywords(
			long groupId, String keywords,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.mobile.device.rules.model.MDRRuleGroup> obc) {

		return _mdrRuleGroupLocalService.searchByKeywords(
			groupId, keywords, params, andOperator, start, end, obc);
	}

	@Override
	public int searchByKeywordsCount(
		long groupId, String keywords,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return _mdrRuleGroupLocalService.searchByKeywordsCount(
			groupId, keywords, params, andOperator);
	}

	@Override
	public int searchCount(
		long groupId, String name,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator) {

		return _mdrRuleGroupLocalService.searchCount(
			groupId, name, params, andOperator);
	}

	/**
	 * Updates the mdr rule group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mdrRuleGroup the mdr rule group
	 * @return the mdr rule group that was updated
	 */
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup
		updateMDRRuleGroup(
			com.liferay.mobile.device.rules.model.MDRRuleGroup mdrRuleGroup) {

		return _mdrRuleGroupLocalService.updateMDRRuleGroup(mdrRuleGroup);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup updateRuleGroup(
			long ruleGroupId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mdrRuleGroupLocalService.updateRuleGroup(
			ruleGroupId, nameMap, descriptionMap, serviceContext);
	}

	@Override
	public MDRRuleGroupLocalService getWrappedService() {
		return _mdrRuleGroupLocalService;
	}

	@Override
	public void setWrappedService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {

		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	private MDRRuleGroupLocalService _mdrRuleGroupLocalService;

}