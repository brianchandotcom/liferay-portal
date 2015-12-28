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
 * Provides a wrapper for {@link MDRRuleGroupLocalService}.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupLocalService
 * @generated
 */
@ProviderType
public class MDRRuleGroupLocalServiceWrapper implements MDRRuleGroupLocalService,
	ServiceWrapper<MDRRuleGroupLocalService> {
	public MDRRuleGroupLocalServiceWrapper(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {
		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	/**
	* Adds the m d r rule group to the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the m d r rule group
	* @return the m d r rule group that was added
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup addMDRRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup mdrRuleGroup) {
		return _mdrRuleGroupLocalService.addMDRRuleGroup(mdrRuleGroup);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup addRuleGroup(
		long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupLocalService.addRuleGroup(groupId, nameMap,
			descriptionMap, serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup copyRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup ruleGroup,
		long groupId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupLocalService.copyRuleGroup(ruleGroup, groupId,
			serviceContext);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup copyRuleGroup(
		long ruleGroupId, long groupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupLocalService.copyRuleGroup(ruleGroupId, groupId,
			serviceContext);
	}

	/**
	* Creates a new m d r rule group with the primary key. Does not add the m d r rule group to the database.
	*
	* @param ruleGroupId the primary key for the new m d r rule group
	* @return the new m d r rule group
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup createMDRRuleGroup(
		long ruleGroupId) {
		return _mdrRuleGroupLocalService.createMDRRuleGroup(ruleGroupId);
	}

	/**
	* Deletes the m d r rule group from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the m d r rule group
	* @return the m d r rule group that was removed
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup deleteMDRRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup mdrRuleGroup) {
		return _mdrRuleGroupLocalService.deleteMDRRuleGroup(mdrRuleGroup);
	}

	/**
	* Deletes the m d r rule group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleGroupId the primary key of the m d r rule group
	* @return the m d r rule group that was removed
	* @throws PortalException if a m d r rule group with the primary key could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup deleteMDRRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupLocalService.deleteMDRRuleGroup(ruleGroupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup ruleGroup) {
		_mdrRuleGroupLocalService.deleteRuleGroup(ruleGroup);
	}

	@Override
	public void deleteRuleGroup(long ruleGroupId) {
		_mdrRuleGroupLocalService.deleteRuleGroup(ruleGroupId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrRuleGroupLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _mdrRuleGroupLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup fetchMDRRuleGroup(
		long ruleGroupId) {
		return _mdrRuleGroupLocalService.fetchMDRRuleGroup(ruleGroupId);
	}

	/**
	* Returns the m d r rule group matching the UUID and group.
	*
	* @param uuid the m d r rule group's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup fetchMDRRuleGroupByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _mdrRuleGroupLocalService.fetchMDRRuleGroupByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup fetchRuleGroup(
		long ruleGroupId) {
		return _mdrRuleGroupLocalService.fetchRuleGroup(ruleGroupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _mdrRuleGroupLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _mdrRuleGroupLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _mdrRuleGroupLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the m d r rule group with the primary key.
	*
	* @param ruleGroupId the primary key of the m d r rule group
	* @return the m d r rule group
	* @throws PortalException if a m d r rule group with the primary key could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup getMDRRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupLocalService.getMDRRuleGroup(ruleGroupId);
	}

	/**
	* Returns the m d r rule group matching the UUID and group.
	*
	* @param uuid the m d r rule group's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r rule group
	* @throws PortalException if a matching m d r rule group could not be found
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup getMDRRuleGroupByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupLocalService.getMDRRuleGroupByUuidAndGroupId(uuid,
			groupId);
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
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getMDRRuleGroups(
		int start, int end) {
		return _mdrRuleGroupLocalService.getMDRRuleGroups(start, end);
	}

	/**
	* Returns all the m d r rule groups matching the UUID and company.
	*
	* @param uuid the UUID of the m d r rule groups
	* @param companyId the primary key of the company
	* @return the matching m d r rule groups, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getMDRRuleGroupsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _mdrRuleGroupLocalService.getMDRRuleGroupsByUuidAndCompanyId(uuid,
			companyId);
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
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getMDRRuleGroupsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.mobile.device.rules.model.MDRRuleGroup> orderByComparator) {
		return _mdrRuleGroupLocalService.getMDRRuleGroupsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of m d r rule groups.
	*
	* @return the number of m d r rule groups
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
	public java.lang.String getOSGiServiceIdentifier() {
		return _mdrRuleGroupLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
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
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getRuleGroups(
		long groupId) {
		return _mdrRuleGroupLocalService.getRuleGroups(groupId);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getRuleGroups(
		long groupId, int start, int end) {
		return _mdrRuleGroupLocalService.getRuleGroups(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> getRuleGroups(
		long[] groupIds, int start, int end) {
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

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long, String,
	LinkedHashMap, boolean, int, int)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> search(
		long groupId, java.lang.String name, boolean andOperator, int start,
		int end) {
		return _mdrRuleGroupLocalService.search(groupId, name, andOperator,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> search(
		long groupId, java.lang.String name,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end) {
		return _mdrRuleGroupLocalService.search(groupId, name, params,
			andOperator, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #searchByKeywords(long,
	String, LinkedHashMap, boolean, int, int)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> searchByKeywords(
		long groupId, java.lang.String keywords, boolean andOperator,
		int start, int end) {
		return _mdrRuleGroupLocalService.searchByKeywords(groupId, keywords,
			andOperator, start, end);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> searchByKeywords(
		long groupId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end) {
		return _mdrRuleGroupLocalService.searchByKeywords(groupId, keywords,
			params, andOperator, start, end);
	}

	@Override
	public java.util.List<com.liferay.mobile.device.rules.model.MDRRuleGroup> searchByKeywords(
		long groupId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.mobile.device.rules.model.MDRRuleGroup> obc) {
		return _mdrRuleGroupLocalService.searchByKeywords(groupId, keywords,
			params, andOperator, start, end, obc);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #searchByKeywordsCount(long,
	String, LinkedHashMap, boolean)}
	*/
	@Deprecated
	@Override
	public int searchByKeywordsCount(long groupId, java.lang.String keywords,
		boolean andOperator) {
		return _mdrRuleGroupLocalService.searchByKeywordsCount(groupId,
			keywords, andOperator);
	}

	@Override
	public int searchByKeywordsCount(long groupId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return _mdrRuleGroupLocalService.searchByKeywordsCount(groupId,
			keywords, params, andOperator);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #searchCount(long, String,
	LinkedHashMap, boolean)}
	*/
	@Deprecated
	@Override
	public int searchCount(long groupId, java.lang.String name,
		boolean andOperator) {
		return _mdrRuleGroupLocalService.searchCount(groupId, name, andOperator);
	}

	@Override
	public int searchCount(long groupId, java.lang.String name,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return _mdrRuleGroupLocalService.searchCount(groupId, name, params,
			andOperator);
	}

	/**
	* Updates the m d r rule group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the m d r rule group
	* @return the m d r rule group that was updated
	*/
	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup updateMDRRuleGroup(
		com.liferay.mobile.device.rules.model.MDRRuleGroup mdrRuleGroup) {
		return _mdrRuleGroupLocalService.updateMDRRuleGroup(mdrRuleGroup);
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRRuleGroup updateRuleGroup(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mdrRuleGroupLocalService.updateRuleGroup(ruleGroupId, nameMap,
			descriptionMap, serviceContext);
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