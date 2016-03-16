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
	 * Never modify or reference this interface directly. Always use {@link DDLRecordLocalServiceUtil} to access the DDL record local service. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the DDL record to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the DDL record
	* @return the DDL record that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDLRecord addDDLRecord(DDLRecord ddlRecord);

	@Indexable(type = IndexableType.REINDEX)
	public DDLRecord addRecord(long userId, long groupId, long recordSetId,
		int displayIndex, DDMFormValues ddmFormValues,
		ServiceContext serviceContext) throws PortalException;

	public DDLRecord addRecord(long userId, long groupId, long recordSetId,
		int displayIndex, Fields fields, ServiceContext serviceContext)
		throws PortalException;

	public DDLRecord addRecord(long userId, long groupId, long recordSetId,
		int displayIndex, Map<java.lang.String, Serializable> fieldsMap,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new DDL record with the primary key. Does not add the DDL record to the database.
	*
	* @param recordId the primary key for the new DDL record
	* @return the new DDL record
	*/
	public DDLRecord createDDLRecord(long recordId);

	/**
	* Deletes the DDL record from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the DDL record
	* @return the DDL record that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDLRecord deleteDDLRecord(DDLRecord ddlRecord);

	/**
	* Deletes the DDL record with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordId the primary key of the DDL record
	* @return the DDL record that was removed
	* @throws PortalException if a DDL record with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDLRecord deleteDDLRecord(long recordId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public DDLRecord deleteRecord(DDLRecord record) throws PortalException;

	public void deleteRecord(long recordId) throws PortalException;

	public DDLRecord deleteRecordLocale(long recordId, Locale locale,
		ServiceContext serviceContext) throws PortalException;

	public void deleteRecords(long recordSetId) throws PortalException;

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
	public DDLRecord fetchDDLRecord(long recordId);

	/**
	* Returns the DDL record matching the UUID and group.
	*
	* @param uuid the DDL record's UUID
	* @param groupId the primary key of the group
	* @return the matching DDL record, or <code>null</code> if a matching DDL record could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecord fetchDDLRecordByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecord fetchRecord(long recordId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getCompanyRecords(long companyId, int status,
		int scope, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyRecordsCount(long companyId, int status, int scope);

	/**
	* Returns the DDL record with the primary key.
	*
	* @param recordId the primary key of the DDL record
	* @return the DDL record
	* @throws PortalException if a DDL record with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecord getDDLRecord(long recordId) throws PortalException;

	/**
	* Returns the DDL record matching the UUID and group.
	*
	* @param uuid the DDL record's UUID
	* @param groupId the primary key of the group
	* @return the matching DDL record
	* @throws PortalException if a matching DDL record could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecord getDDLRecordByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	/**
	* Returns a range of all the DDL records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of DDL records
	* @param end the upper bound of the range of DDL records (not inclusive)
	* @return the range of DDL records
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getDDLRecords(int start, int end);

	/**
	* Returns all the DDL records matching the UUID and company.
	*
	* @param uuid the UUID of the DDL records
	* @param companyId the primary key of the company
	* @return the matching DDL records, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getDDLRecordsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of DDL records matching the UUID and company.
	*
	* @param uuid the UUID of the DDL records
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of DDL records
	* @param end the upper bound of the range of DDL records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching DDL records, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getDDLRecordsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator);

	/**
	* Returns the number of DDL records.
	*
	* @return the number of DDL records
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDDLRecordsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMFormValues getDDMFormValues(long ddmStorageId)
		throws StorageException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getLatestRecordVersion(
	long)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordVersion getLatestRecordVersion(long recordId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.Long[] getMinAndMaxCompanyRecordIds(long companyId,
		int status, int scope);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getMinAndMaxCompanyRecords(long companyId,
		int status, int scope, long minRecordId, long maxRecordId);

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
	public DDLRecord getRecord(long recordId) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersion(
	long, String)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordVersion getRecordVersion(long recordId,
		java.lang.String version) throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersion(
	long)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersions(
	long, int, int, OrderByComparator)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecordVersion> getRecordVersions(long recordId, int start,
		int end, OrderByComparator<DDLRecordVersion> orderByComparator);

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersionsCount(
	long)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecordVersionsCount(long recordId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getRecords(long recordSetId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getRecords(long recordSetId, int status, int start,
		int end, OrderByComparator<DDLRecord> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDLRecord> getRecords(long recordSetId, long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecordsCount(long recordSetId, int status);

	public void revertRecord(long userId, long recordId,
		java.lang.String version, ServiceContext serviceContext)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #revertRecord(long, long,
	String, ServiceContext)}
	*/
	@java.lang.Deprecated
	public void revertRecordVersion(long userId, long recordId,
		java.lang.String version, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<DDLRecord> searchDDLRecords(
		SearchContext searchContext);

	public void updateAsset(long userId, DDLRecord record,
		DDLRecordVersion recordVersion, long[] assetCategoryIds,
		java.lang.String[] assetTagNames, Locale locale,
		java.lang.Double priority) throws PortalException;

	/**
	* Updates the DDL record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the DDL record
	* @return the DDL record that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDLRecord updateDDLRecord(DDLRecord ddlRecord);

	public DDLRecord updateRecord(long userId, long recordId, int displayIndex,
		Map<java.lang.String, Serializable> fieldsMap, boolean mergeFields,
		ServiceContext serviceContext) throws PortalException;

	public DDLRecord updateRecord(long userId, long recordId,
		boolean majorVersion, int displayIndex, DDMFormValues ddmFormValues,
		ServiceContext serviceContext) throws PortalException;

	public DDLRecord updateRecord(long userId, long recordId,
		boolean majorVersion, int displayIndex, Fields fields,
		boolean mergeFields, ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public DDLRecord updateStatus(long userId, long recordVersionId,
		int status, ServiceContext serviceContext) throws PortalException;
}