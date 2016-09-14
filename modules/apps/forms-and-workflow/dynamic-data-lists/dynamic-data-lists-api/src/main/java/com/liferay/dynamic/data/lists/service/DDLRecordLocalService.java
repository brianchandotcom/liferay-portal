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

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;

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
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.SearchContext;
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
 * Provides the local service interface for DDLRecord. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordLocalServiceUtil
 * @see com.liferay.dynamic.data.lists.service.base.DDLRecordLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDLRecordLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDLRecordLocalServiceUtil} to access the d d l record local service. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the d d l record to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDLRecord addDDLRecord(DDLRecord ddlRecord);

	/**
	* Adds a record referencing the record set.
	*
	* @param userId the primary key of the record's creator/owner
	* @param groupId the primary key of the record's group
	* @param recordSetId the primary key of the record set
	* @param displayIndex the index position in which the record is displayed
	in the spreadsheet view
	* @param ddmFormValues the record values. See <code>DDMFormValues</code>
	in the <code>dynamic.data.mapping.api</code> module.
	* @param serviceContext the service context to be applied. This can set
	the UUID, guest permissions, and group permissions for the
	record.
	* @return the record
	* @throws PortalException if a portal exception occurred
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDLRecord addRecord(long userId, long groupId, long recordSetId,
		int displayIndex, DDMFormValues ddmFormValues,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Adds a record that's based on the fields object and that references the
	* record set.
	*
	* @param userId the primary key of the record's creator/owner
	* @param groupId the primary key of the record's group
	* @param recordSetId the primary key of the record set
	* @param displayIndex the index position in which the record is
	displayed in the spreadsheet view.
	* @param fields the record values. See the dynamic-data-mapping-api
	module's Fields class for more information.
	* @param serviceContext the service context to be applied. This can
	set the UUID, guest permissions, and group permissions for
	the record.
	* @return the record
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 1.1.0, replaced by {@link #addRecord(long, long, int,
	DDMFormValues, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DDLRecord addRecord(long userId, long groupId, long recordSetId,
		int displayIndex, Fields fields, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds a record that's based on the fields map and that references the
	* record set.
	*
	* @param userId the primary key of the record's creator/owner
	* @param groupId the primary key of the record's group
	* @param recordSetId the primary key of the record set
	* @param displayIndex the index position in which the record is
	displayed in the spreadsheet view
	* @param fieldsMap the record values. The fieldsMap is a map of field
	names and their serializable values.
	* @param serviceContext the service context to be applied. This can
	set the UUID, guest permissions, and group permissions for
	the record.
	* @return the record
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 1.1.0, replaced by {@link #addRecord(long, long, int,
	DDMFormValues, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DDLRecord addRecord(long userId, long groupId, long recordSetId,
		int displayIndex, Map<java.lang.String, Serializable> fieldsMap,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	*
	* @param recordId the primary key for the new d d l record
	* @return the new d d l record
	*/
	public DDLRecord createDDLRecord(long recordId);

	/**
	* Deletes the d d l record from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDLRecord deleteDDLRecord(DDLRecord ddlRecord);

	/**
	* Deletes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record that was removed
	* @throws PortalException if a d d l record with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDLRecord deleteDDLRecord(long recordId) throws PortalException;

	/**
	* Deletes the record and its resources.
	*
	* @param record the record to be deleted
	* @return the record
	* @throws PortalException if a portal exception occurred
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public DDLRecord deleteRecord(DDLRecord record) throws PortalException;

	/**
	* Disassociates the locale from the record.
	*
	* @param recordId the primary key of the record
	* @param locale the locale of the record values to be removed
	* @param serviceContext the service context to be applied. This can
	set the record modified date.
	* @return the affected record
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 1.1.0, replaced by {@link #updateRecord(long, boolean,
	int, DDMFormValues, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DDLRecord deleteRecordLocale(long recordId, Locale locale,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecord fetchDDLRecord(long recordId);

	/**
	* Returns the d d l record matching the UUID and group.
	*
	* @param uuid the d d l record's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecord fetchDDLRecordByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	/**
	* Returns the record with the ID.
	*
	* @param recordId the primary key of the record
	* @return the record with the ID, or <code>null</code> if a matching record
	could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecord fetchRecord(long recordId);

	/**
	* Returns the d d l record with the primary key.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record
	* @throws PortalException if a d d l record with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecord getDDLRecord(long recordId) throws PortalException;

	/**
	* Returns the d d l record matching the UUID and group.
	*
	* @param uuid the d d l record's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record
	* @throws PortalException if a matching d d l record could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecord getDDLRecordByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	/**
	* Returns the record with the ID.
	*
	* @param recordId the primary key of the record
	* @return the record with the ID
	* @throws PortalException if a portal exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecord getRecord(long recordId) throws PortalException;

	/**
	* Updates the d d l record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDLRecord updateDDLRecord(DDLRecord ddlRecord);

	/**
	* Updates the record, replacing its display index and values.
	*
	* @param userId the primary key of the user updating the record
	* @param recordId the primary key of the record
	* @param majorVersion whether this update is a major change. A major
	change increments the record's major version number.
	* @param displayIndex the index position in which the record is displayed
	in the spreadsheet view
	* @param ddmFormValues the record values. See <code>DDMFormValues</code>
	in the <code>dynamic.data.mapping.api</code> module.
	* @param serviceContext the service context to be applied. This can set
	the record modified date.
	* @return the record
	* @throws PortalException if a portal exception occurred
	*/
	public DDLRecord updateRecord(long userId, long recordId,
		boolean majorVersion, int displayIndex, DDMFormValues ddmFormValues,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Updates a record, replacing its display index and values.
	*
	* @param userId the primary key of the user updating the record
	* @param recordId the primary key of the record
	* @param majorVersion whether this update is a major change. A major
	change increments the record's major version number
	* @param displayIndex the index position in which the record is
	displayed in the spreadsheet view.
	* @param fields the record values. See <code>Fields</code> in the
	<code>dynamic.data.mapping.api</code> module.
	* @param mergeFields whether to merge the new fields with the existing
	ones; otherwise replace the existing fields
	* @param serviceContext the service context to be applied. This can
	set the record modified date.
	* @return the record
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 1.1.0, replaced by {@link #updateRecord(long, long,
	boolean, int, DDMFormValues, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DDLRecord updateRecord(long userId, long recordId,
		boolean majorVersion, int displayIndex, Fields fields,
		boolean mergeFields, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates a record, replacing its display index and values.
	*
	* @param userId the primary key of the user updating the record
	* @param recordId the primary key of the record
	* @param displayIndex the index position in which the record is
	displayed in the spreadsheet view
	* @param fieldsMap the record values. The fieldsMap is a map of field
	names and its Serializable values.
	* @param mergeFields whether to merge the new fields with the existing
	ones; otherwise replace the existing fields
	* @param serviceContext the service context to be applied. This can
	set the record modified date.
	* @return the record
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 1.1.0, replaced by {@link #updateRecord(long, long,
	boolean, int, DDMFormValues, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DDLRecord updateRecord(long userId, long recordId, int displayIndex,
		Map<java.lang.String, Serializable> fieldsMap, boolean mergeFields,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Updates the workflow status of the record version.
	*
	* @param userId the primary key of the user updating the record's workflow
	status
	* @param recordVersionId the primary key of the record version
	* @param status
	* @param serviceContext the service context to be applied. This can set
	the modification date and status date.
	* @return the record
	* @throws PortalException if a portal exception occurred
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDLRecord updateStatus(long userId, long recordVersionId,
		int status, ServiceContext serviceContext) throws PortalException;

	/**
	* @deprecated As of 1.1.0, replaced by {@link
	DDLRecordVersionLocalService#getLatestRecordVersion(long)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordVersion getLatestRecordVersion(long recordId)
		throws PortalException;

	/**
	* @deprecated As of 1.1.0, replaced by {@link
	DDLRecordVersionLocalService#getRecordVersion(
	long, String)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordVersion getRecordVersion(long recordId,
		java.lang.String version) throws PortalException;

	/**
	* @deprecated As of 1.1.0, replaced by {@link
	DDLRecordVersionLocalService#getRecordVersion(
	long)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException;

	/**
	* Returns the DDM form values object associated with the record storage ID
	* See <code>DDLRecord#getDDMFormValues</code> in the
	* <code>com.liferay.dynamic.data.lists.api</code> module.
	*
	* @param ddmStorageId the storage ID associated with the record
	* @return the DDM form values
	* @throws StorageException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormValues getDDMFormValues(long ddmStorageId)
		throws StorageException;

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
	* Searches for records documents indexed by the search engine.
	*
	* @param searchContext the search context to be applied for searching
	documents. For more information, see <code>SearchContext</code>
	in the <code>portal-kernel</code> module.
	* @return BaseModelSearchResult containing the list of records that matched
	the search criteria
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<DDLRecord> searchDDLRecords(
		SearchContext searchContext);

	/**
	* Returns hits to all the records indexed by the search engine matching the
	* search context.
	*
	* @param searchContext the search context to be applied for searching
	records. For more information, see <code>SearchContext</code> in
	the <code>portal-kernel</code> module.
	* @return the hits of the records that matched the search criteria.
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	/**
	* Returns the number of records matching the company, workflow status, and
	* scope.
	*
	* @param companyId the primary key of the record's company
	* @param status the record's workflow status. For more information search
	the portal kernel's WorkflowConstants class for constants
	starting with the "STATUS_" prefix.
	* @param scope the record's scope. For more information search the
	dynamic-data-lists-api module's DDLRecordSetConstants class for
	constants starting with the "SCOPE_" prefix.
	* @return the number of matching records
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyRecordsCount(long companyId, int status, int scope);

	/**
	* Returns the number of d d l records.
	*
	* @return the number of d d l records
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDDLRecordsCount();

	/**
	* @deprecated As of 1.1.0, replaced by {@link
	DDLRecordVersionLocalService#getRecordVersionsCount(
	long)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecordVersionsCount(long recordId);

	/**
	* Returns the number of records matching the record set ID and workflow
	* status.
	*
	* @param recordSetId the record's record set ID
	* @param status the record's workflow status. For more information search
	the portal kernel's WorkflowConstants class for constants
	starting with the "STATUS_" prefix.
	* @return the number of matching records
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecordsCount(long recordSetId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.Long[] getMinAndMaxCompanyRecordIds(long companyId,
		int status, int scope);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns an ordered range of all the records matching the company,
	* workflow status, and scope.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	* full result set.
	* </p>
	*
	* @param companyId the primary key of the record's company
	* @param status the record's workflow status. For more information search
	the portal kernel's WorkflowConstants class for constants
	starting with the "STATUS_" prefix.
	* @param scope the record's scope. For more information search the
	dynamic-data-lists-api module's DDLRecordSetConstants class for
	constants starting with the "SCOPE_" prefix.
	* @param start the lower bound of the range of records to return
	* @param end the upper bound of the range of records to return (not
	inclusive)
	* @param orderByComparator the comparator to order the records
	* @return the range of matching records ordered by the comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getCompanyRecords(long companyId, int status,
		int scope, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns a range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of d d l records
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getDDLRecords(int start, int end);

	/**
	* Returns all the d d l records matching the UUID and company.
	*
	* @param uuid the UUID of the d d l records
	* @param companyId the primary key of the company
	* @return the matching d d l records, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getDDLRecordsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of d d l records matching the UUID and company.
	*
	* @param uuid the UUID of the d d l records
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching d d l records, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getDDLRecordsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getMinAndMaxCompanyRecords(long companyId,
		int status, int scope, long minRecordId, long maxRecordId);

	/**
	* @deprecated As of 1.1.0, replaced by {@link
	DDLRecordVersionLocalService#getRecordVersions(
	long, int, int, OrderByComparator)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecordVersion> getRecordVersions(long recordId, int start,
		int end, OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* Returns all the records matching the record set ID
	*
	* @param recordSetId the record's record set ID
	* @return the matching records
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getRecords(long recordSetId);

	/**
	* Returns an ordered range of all the records matching the record set ID
	* and workflow status.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	* full result set.
	* </p>
	*
	* @param recordSetId the record's record set ID
	* @param status the record's workflow status. For more information search
	the portal kernel's WorkflowConstants class for constants
	starting with the "STATUS_" prefix.
	* @param start the lower bound of the range of records to return
	* @param end the upper bound of the range of records to return (not
	inclusive)
	* @param orderByComparator the comparator to order the records
	* @return the range of matching records ordered by the comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getRecords(long recordSetId, int status, int start,
		int end, OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns all the records matching the record set ID and user ID.
	*
	* @param recordSetId the record's record set ID
	* @param userId the user ID the records belong to
	* @return the list of matching records ordered by the comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getRecords(long recordSetId, long userId);

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

	/**
	* Deletes the record and its resources.
	*
	* @param recordId the primary key of the record to be deleted
	* @throws PortalException if a portal exception occurred
	*/
	public void deleteRecord(long recordId) throws PortalException;

	/**
	* Deletes all the record set's records.
	*
	* @param recordSetId the primary key of the record set from which to
	delete records
	* @throws PortalException if a portal exception occurred
	*/
	public void deleteRecords(long recordSetId) throws PortalException;

	/**
	* Reverts the record to the given version.
	*
	* @param userId the primary key of the user who is reverting the record
	* @param recordId the primary key of the record
	* @param version the version to revert to
	* @param serviceContext the service context to be applied. This can set
	the record modified date.
	* @throws PortalException if a portal exception occurred
	*/
	public void revertRecord(long userId, long recordId,
		java.lang.String version, ServiceContext serviceContext)
		throws PortalException;

	/**
	* @deprecated As of 1.1.0, replaced by {@link #revertRecord(long, long,
	String, ServiceContext)}
	*/
	@java.lang.Deprecated
	public void revertRecordVersion(long userId, long recordId,
		java.lang.String version, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the record's asset with new asset categories, tag names, and link
	* entries, removing and adding them as necessary.
	*
	* @param userId the primary key of the user updating the record's asset
	* @param record the record
	* @param recordVersion the record version
	* @param assetCategoryIds the primary keys of the new asset categories
	* @param assetTagNames the new asset tag names
	* @param locale the locale to apply to the asset
	* @param priority the new priority
	* @throws PortalException if a portal exception occurred
	*/
	public void updateAsset(long userId, DDLRecord record,
		DDLRecordVersion recordVersion, long[] assetCategoryIds,
		java.lang.String[] assetTagNames, Locale locale,
		java.lang.Double priority) throws PortalException;
}