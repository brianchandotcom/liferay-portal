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
 * Provides a wrapper for {@link MDRRuleGroupLocalService}.
 *
 * @author    Edward C. Han
 * @see       MDRRuleGroupLocalService
 * @generated
 */
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
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup addMDRRuleGroup(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup mdrRuleGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.addMDRRuleGroup(mdrRuleGroup);
	}

	/**
	* Creates a new m d r rule group with the primary key. Does not add the m d r rule group to the database.
	*
	* @param ruleGroupId the primary key for the new m d r rule group
	* @return the new m d r rule group
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup createMDRRuleGroup(
		long ruleGroupId) {
		return _mdrRuleGroupLocalService.createMDRRuleGroup(ruleGroupId);
	}

	/**
	* Deletes the m d r rule group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleGroupId the primary key of the m d r rule group
	* @return the m d r rule group that was removed
	* @throws PortalException if a m d r rule group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup deleteMDRRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.deleteMDRRuleGroup(ruleGroupId);
	}

	/**
	* Deletes the m d r rule group from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the m d r rule group
	* @return the m d r rule group that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup deleteMDRRuleGroup(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup mdrRuleGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.deleteMDRRuleGroup(mdrRuleGroup);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mdrRuleGroupLocalService.dynamicQuery();
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
		return _mdrRuleGroupLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrRuleGroupLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _mdrRuleGroupLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _mdrRuleGroupLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchMDRRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.fetchMDRRuleGroup(ruleGroupId);
	}

	/**
	* Returns the m d r rule group with the primary key.
	*
	* @param ruleGroupId the primary key of the m d r rule group
	* @return the m d r rule group
	* @throws PortalException if a m d r rule group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup getMDRRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.getMDRRuleGroup(ruleGroupId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the m d r rule group matching the UUID and group.
	*
	* @param uuid the m d r rule group's UUID
	* @param groupId the primary key of the group
	* @return the matching m d r rule group
	* @throws PortalException if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup getMDRRuleGroupByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.getMDRRuleGroupByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the m d r rule groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @return the range of m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> getMDRRuleGroups(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.getMDRRuleGroups(start, end);
	}

	/**
	* Returns the number of m d r rule groups.
	*
	* @return the number of m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public int getMDRRuleGroupsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.getMDRRuleGroupsCount();
	}

	/**
	* Updates the m d r rule group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the m d r rule group
	* @return the m d r rule group that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup updateMDRRuleGroup(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup mdrRuleGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.updateMDRRuleGroup(mdrRuleGroup);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _mdrRuleGroupLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_mdrRuleGroupLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup addRuleGroup(
		long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.addRuleGroup(groupId, nameMap,
			descriptionMap, serviceContext);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup copyRuleGroup(
		long ruleGroupId, long groupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.copyRuleGroup(ruleGroupId, groupId,
			serviceContext);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup copyRuleGroup(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup ruleGroup,
		long groupId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.copyRuleGroup(ruleGroup, groupId,
			serviceContext);
	}

	public void deleteRuleGroup(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleGroupLocalService.deleteRuleGroup(ruleGroupId);
	}

	public void deleteRuleGroup(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup ruleGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleGroupLocalService.deleteRuleGroup(ruleGroup);
	}

	public void deleteRuleGroups(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrRuleGroupLocalService.deleteRuleGroups(groupId);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.fetchRuleGroup(ruleGroupId);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup getRuleGroup(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.getRuleGroup(ruleGroupId);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> getRuleGroups(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.getRuleGroups(groupId);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> getRuleGroups(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.getRuleGroups(groupId, start, end);
	}

	public int getRuleGroupsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.getRuleGroupsCount(groupId);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> search(
		long groupId, java.lang.String name, boolean andOperator, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.search(groupId, name, andOperator,
			start, end);
	}

	public java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> searchByKeywords(
		long groupId, java.lang.String keywords, boolean andOperator,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.searchByKeywords(groupId, keywords,
			andOperator, start, end);
	}

	public int searchByKeywordsCount(long groupId, java.lang.String keywords,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.searchByKeywordsCount(groupId,
			keywords, andOperator);
	}

	public int searchCount(long groupId, java.lang.String name,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.searchCount(groupId, name, andOperator);
	}

	public com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup updateRuleGroup(
		long ruleGroupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mdrRuleGroupLocalService.updateRuleGroup(ruleGroupId, nameMap,
			descriptionMap, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public MDRRuleGroupLocalService getWrappedMDRRuleGroupLocalService() {
		return _mdrRuleGroupLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedMDRRuleGroupLocalService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {
		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	public MDRRuleGroupLocalService getWrappedService() {
		return _mdrRuleGroupLocalService;
	}

	public void setWrappedService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {
		_mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	private MDRRuleGroupLocalService _mdrRuleGroupLocalService;
}