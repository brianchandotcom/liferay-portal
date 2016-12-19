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

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.mobile.device.rules.model.MDRRuleGroup;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for MDRRuleGroup. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Edward C. Han
 * @see MDRRuleGroupLocalServiceUtil
 * @see com.liferay.mobile.device.rules.service.base.MDRRuleGroupLocalServiceBaseImpl
 * @see com.liferay.mobile.device.rules.service.impl.MDRRuleGroupLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MDRRuleGroupLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MDRRuleGroupLocalServiceUtil} to access the mdr rule group local service. Add custom service methods to {@link com.liferay.mobile.device.rules.service.impl.MDRRuleGroupLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the mdr rule group to the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the mdr rule group
	* @return the mdr rule group that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public MDRRuleGroup addMDRRuleGroup(MDRRuleGroup mdrRuleGroup);

	public MDRRuleGroup addRuleGroup(long groupId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		ServiceContext serviceContext) throws PortalException;

	public MDRRuleGroup copyRuleGroup(MDRRuleGroup ruleGroup, long groupId,
		ServiceContext serviceContext) throws PortalException;

	public MDRRuleGroup copyRuleGroup(long ruleGroupId, long groupId,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new mdr rule group with the primary key. Does not add the mdr rule group to the database.
	*
	* @param ruleGroupId the primary key for the new mdr rule group
	* @return the new mdr rule group
	*/
	public MDRRuleGroup createMDRRuleGroup(long ruleGroupId);

	/**
	* Deletes the mdr rule group from the database. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the mdr rule group
	* @return the mdr rule group that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public MDRRuleGroup deleteMDRRuleGroup(MDRRuleGroup mdrRuleGroup);

	/**
	* Deletes the mdr rule group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleGroupId the primary key of the mdr rule group
	* @return the mdr rule group that was removed
	* @throws PortalException if a mdr rule group with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public MDRRuleGroup deleteMDRRuleGroup(long ruleGroupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MDRRuleGroup fetchMDRRuleGroup(long ruleGroupId);

	/**
	* Returns the mdr rule group matching the UUID and group.
	*
	* @param uuid the mdr rule group's UUID
	* @param groupId the primary key of the group
	* @return the matching mdr rule group, or <code>null</code> if a matching mdr rule group could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MDRRuleGroup fetchMDRRuleGroupByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MDRRuleGroup fetchRuleGroup(long ruleGroupId);

	/**
	* Returns the mdr rule group with the primary key.
	*
	* @param ruleGroupId the primary key of the mdr rule group
	* @return the mdr rule group
	* @throws PortalException if a mdr rule group with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MDRRuleGroup getMDRRuleGroup(long ruleGroupId)
		throws PortalException;

	/**
	* Returns the mdr rule group matching the UUID and group.
	*
	* @param uuid the mdr rule group's UUID
	* @param groupId the primary key of the group
	* @return the matching mdr rule group
	* @throws PortalException if a matching mdr rule group could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MDRRuleGroup getMDRRuleGroupByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MDRRuleGroup getRuleGroup(long ruleGroupId)
		throws PortalException;

	/**
	* Updates the mdr rule group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param mdrRuleGroup the mdr rule group
	* @return the mdr rule group that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public MDRRuleGroup updateMDRRuleGroup(MDRRuleGroup mdrRuleGroup);

	public MDRRuleGroup updateRuleGroup(long ruleGroupId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Returns the number of mdr rule groups.
	*
	* @return the number of mdr rule groups
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMDRRuleGroupsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRuleGroupsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRuleGroupsCount(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchByKeywordsCount(long groupId, java.lang.String keywords,
		LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long groupId, java.lang.String name,
		LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns a range of all the mdr rule groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.mobile.device.rules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of mdr rule groups
	* @param end the upper bound of the range of mdr rule groups (not inclusive)
	* @return the range of mdr rule groups
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroup> getMDRRuleGroups(int start, int end);

	/**
	* Returns all the mdr rule groups matching the UUID and company.
	*
	* @param uuid the UUID of the mdr rule groups
	* @param companyId the primary key of the company
	* @return the matching mdr rule groups, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroup> getMDRRuleGroupsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroup> getMDRRuleGroupsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<MDRRuleGroup> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroup> getRuleGroups(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroup> getRuleGroups(long groupId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroup> getRuleGroups(long[] groupIds, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroup> search(long groupId, java.lang.String name,
		LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroup> searchByKeywords(long groupId,
		java.lang.String keywords,
		LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MDRRuleGroup> searchByKeywords(long groupId,
		java.lang.String keywords,
		LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		OrderByComparator<MDRRuleGroup> obc);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public void deleteRuleGroup(MDRRuleGroup ruleGroup);

	public void deleteRuleGroup(long ruleGroupId);

	public void deleteRuleGroups(long groupId);
}