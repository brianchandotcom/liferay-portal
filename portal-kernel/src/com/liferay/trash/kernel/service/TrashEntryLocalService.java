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

package com.liferay.trash.kernel.service;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;

import com.liferay.trash.kernel.model.TrashEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for TrashEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryLocalServiceUtil
 * @see com.liferay.portlet.trash.service.base.TrashEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl
 * @deprecated As of Judson (7.1.x), replaced by {@link
           com.liferay.trash.service.impl.TrashEntryLocalServiceImpl}
 * @generated
 */
@Deprecated
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface TrashEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TrashEntryLocalServiceUtil} to access the trash entry local service. Add custom service methods to {@link com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<TrashEntry> searchTrashEntries(
		long companyId, long groupId, long userId, java.lang.String keywords,
		int start, int end, Sort sort);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(long companyId, long groupId, long userId,
		java.lang.String keywords, int start, int end, Sort sort);

	/**
	* Adds the trash entry to the database. Also notifies the appropriate model listeners.
	*
	* @param trashEntry the trash entry
	* @return the trash entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public TrashEntry addTrashEntry(TrashEntry trashEntry);

	/**
	* Moves an entry to trash.
	*
	* @param userId the primary key of the user removing the entity
	* @param groupId the primary key of the entry's group
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @param classUuid the UUID of the entity's class
	* @param referrerClassName the referrer class name used to add a deletion
	{@link SystemEvent}
	* @param status the status of the entity prior to being moved to trash
	* @param statusOVPs the primary keys and statuses of any of the entry's
	versions (e.g., {@link
	com.liferay.portlet.documentlibrary.model.DLFileVersion})
	* @param typeSettingsProperties the type settings properties
	* @return the trashEntry
	*/
	public TrashEntry addTrashEntry(long userId, long groupId,
		java.lang.String className, long classPK, java.lang.String classUuid,
		java.lang.String referrerClassName, int status,
		List<ObjectValuePair<java.lang.Long, java.lang.Integer>> statusOVPs,
		UnicodeProperties typeSettingsProperties) throws PortalException;

	/**
	* Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	*
	* @param entryId the primary key for the new trash entry
	* @return the new trash entry
	*/
	public TrashEntry createTrashEntry(long entryId);

	@Indexable(type = IndexableType.DELETE)
	public TrashEntry deleteEntry(TrashEntry trashEntry);

	/**
	* Deletes the trash entry with the entity class name and primary key.
	*
	* @param className the class name of entity
	* @param classPK the primary key of the entry
	* @return the trash entry with the entity class name and primary key
	*/
	public TrashEntry deleteEntry(java.lang.String className, long classPK);

	public void deleteEntries(long groupId, boolean deleteTrashedModels);

	/**
	* Deletes the trash entry with the primary key.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry with the primary key
	*/
	public TrashEntry deleteEntry(long entryId);

	/**
	* Deletes the trash entry from the database. Also notifies the appropriate model listeners.
	*
	* @param trashEntry the trash entry
	* @return the trash entry that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public TrashEntry deleteTrashEntry(TrashEntry trashEntry);

	/**
	* Deletes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry that was removed
	* @throws PortalException if a trash entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public TrashEntry deleteTrashEntry(long entryId) throws PortalException;

	/**
	* Returns the trash entry with the entity class name and primary key.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the trash entry with the entity class name and primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TrashEntry fetchEntry(java.lang.String className, long classPK);

	/**
	* Returns the trash entry with the primary key.
	*
	* @param entryId the primary key of the entry
	* @return the trash entry with the primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TrashEntry fetchEntry(long entryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TrashEntry fetchTrashEntry(long entryId);

	/**
	* Returns the entry with the entity class name and primary key.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the trash entry with the entity class name and primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TrashEntry getEntry(java.lang.String className, long classPK)
		throws PortalException;

	/**
	* Returns the trash entry with the primary key.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry with the primary key
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TrashEntry getEntry(long entryId) throws PortalException;

	/**
	* Returns the trash entry with the primary key.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry
	* @throws PortalException if a trash entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TrashEntry getTrashEntry(long entryId) throws PortalException;

	/**
	* Updates the trash entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param trashEntry the trash entry
	* @return the trash entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public TrashEntry updateTrashEntry(TrashEntry trashEntry);

	/**
	* Returns the number of trash entries with the group ID.
	*
	* @param groupId the primary key of the group
	* @return the number of matching trash entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long groupId);

	/**
	* Returns the number of trash entries.
	*
	* @return the number of trash entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTrashEntriesCount();

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns the trash entries with the matching group ID.
	*
	* @param groupId the primary key of the group
	* @return the trash entries with the group ID
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TrashEntry> getEntries(long groupId);

	/**
	* Returns a range of all the trash entries matching the group ID.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of trash entries to return
	* @param end the upper bound of the range of trash entries to return (not
	inclusive)
	* @return the range of matching trash entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TrashEntry> getEntries(long groupId, int start, int end);

	/**
	* Returns a range of all the trash entries matching the group ID.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of trash entries to return
	* @param end the upper bound of the range of trash entries to return (not
	inclusive)
	* @param obc the comparator to order the trash entries (optionally
	<code>null</code>)
	* @return the range of matching trash entries ordered by comparator
	<code>obc</code>
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TrashEntry> getEntries(long groupId, int start, int end,
		OrderByComparator<TrashEntry> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TrashEntry> getEntries(long groupId, java.lang.String className);

	/**
	* Returns a range of all the trash entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @return the range of trash entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TrashEntry> getTrashEntries(int start, int end);

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

	public void checkEntries() throws PortalException;

	public void deleteEntries(long groupId);
}