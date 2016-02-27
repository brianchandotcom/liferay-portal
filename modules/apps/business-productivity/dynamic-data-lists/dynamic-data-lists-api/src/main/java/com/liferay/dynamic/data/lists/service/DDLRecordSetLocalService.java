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

package com.liferay.dynamic.data.lists.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

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

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for DDLRecordSet. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetLocalServiceUtil
 * @see com.liferay.dynamic.data.lists.service.base.DDLRecordSetLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordSetLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDLRecordSetLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDLRecordSetLocalServiceUtil} to access the d d l record set local service. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the d d l record set to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSet the d d l record set
	* @return the d d l record set that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDLRecordSet addDDLRecordSet(DDLRecordSet ddlRecordSet);

	public DDLRecordSet addRecordSet(long userId, long groupId,
		long ddmStructureId, java.lang.String recordSetKey,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, int minDisplayRows,
		int scope, ServiceContext serviceContext) throws PortalException;

	public void addRecordSetResources(DDLRecordSet recordSet,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addRecordSetResources(DDLRecordSet recordSet,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws PortalException;

	/**
	* Creates a new d d l record set with the primary key. Does not add the d d l record set to the database.
	*
	* @param recordSetId the primary key for the new d d l record set
	* @return the new d d l record set
	*/
	public DDLRecordSet createDDLRecordSet(long recordSetId);

	/**
	* Deletes the d d l record set from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSet the d d l record set
	* @return the d d l record set that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDLRecordSet deleteDDLRecordSet(DDLRecordSet ddlRecordSet);

	/**
	* Deletes the d d l record set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordSetId the primary key of the d d l record set
	* @return the d d l record set that was removed
	* @throws PortalException if a d d l record set with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDLRecordSet deleteDDLRecordSet(long recordSetId)
		throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public void deleteRecordSet(long groupId, java.lang.String recordSetKey)
		throws PortalException;

	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public void deleteRecordSet(DDLRecordSet recordSet)
		throws PortalException;

	public void deleteRecordSet(long recordSetId) throws PortalException;

	public void deleteRecordSets(long groupId) throws PortalException;

	public DynamicQuery dynamicQuery();

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordSet fetchDDLRecordSet(long recordSetId);

	/**
	* Returns the d d l record set matching the UUID and group.
	*
	* @param uuid the d d l record set's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordSet fetchDDLRecordSetByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordSet fetchRecordSet(long groupId,
		java.lang.String recordSetKey);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordSet fetchRecordSet(long recordSetId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the d d l record set with the primary key.
	*
	* @param recordSetId the primary key of the d d l record set
	* @return the d d l record set
	* @throws PortalException if a d d l record set with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordSet getDDLRecordSet(long recordSetId)
		throws PortalException;

	/**
	* Returns the d d l record set matching the UUID and group.
	*
	* @param uuid the d d l record set's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record set
	* @throws PortalException if a matching d d l record set could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordSet getDDLRecordSetByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	/**
	* Returns a range of all the d d l record sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @return the range of d d l record sets
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecordSet> getDDLRecordSets(int start, int end);

	/**
	* Returns all the d d l record sets matching the UUID and company.
	*
	* @param uuid the UUID of the d d l record sets
	* @param companyId the primary key of the company
	* @return the matching d d l record sets, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecordSet> getDDLRecordSetsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of d d l record sets matching the UUID and company.
	*
	* @param uuid the UUID of the d d l record sets
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching d d l record sets, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecordSet> getDDLRecordSetsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator);

	/**
	* Returns the number of d d l record sets.
	*
	* @return the number of d d l record sets
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDDLRecordSetsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordSet getRecordSet(long groupId, java.lang.String recordSetKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordSet getRecordSet(long recordSetId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormValues getRecordSetSettingsDDMFormValues(
		DDLRecordSet recordSet) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordSetSettings getRecordSetSettingsModel(
		DDLRecordSet recordSet) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecordSet> getRecordSets(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecordSetsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecordSet> search(long companyId, long groupId,
		java.lang.String keywords, int scope, int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecordSet> search(long companyId, long groupId,
		java.lang.String name, java.lang.String description, int scope,
		boolean andOperator, int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long groupId,
		java.lang.String keywords, int scope);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long groupId, java.lang.String name,
		java.lang.String description, int scope, boolean andOperator);

	/**
	* Updates the d d l record set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSet the d d l record set
	* @return the d d l record set that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDLRecordSet updateDDLRecordSet(DDLRecordSet ddlRecordSet);

	public DDLRecordSet updateMinDisplayRows(long recordSetId,
		int minDisplayRows, ServiceContext serviceContext)
		throws PortalException;

	public DDLRecordSet updateRecordSet(long groupId, long ddmStructureId,
		java.lang.String recordSetKey, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, int minDisplayRows,
		ServiceContext serviceContext) throws PortalException;

	public DDLRecordSet updateRecordSet(long recordSetId, long ddmStructureId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, int minDisplayRows,
		ServiceContext serviceContext) throws PortalException;

	public DDLRecordSet updateRecordSet(long recordSetId,
		DDMFormValues settingsDDMFormValues) throws PortalException;
}