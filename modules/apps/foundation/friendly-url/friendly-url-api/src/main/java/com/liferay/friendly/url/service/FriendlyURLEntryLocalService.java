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

package com.liferay.friendly.url.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryException;
import com.liferay.friendly.url.model.FriendlyURLEntry;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for FriendlyURLEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalServiceUtil
 * @see com.liferay.friendly.url.service.base.FriendlyURLEntryLocalServiceBaseImpl
 * @see com.liferay.friendly.url.service.impl.FriendlyURLEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface FriendlyURLEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FriendlyURLEntryLocalServiceUtil} to access the friendly url entry local service. Add custom service methods to {@link com.liferay.friendly.url.service.impl.FriendlyURLEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the friendly url entry to the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntry the friendly url entry
	* @return the friendly url entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public FriendlyURLEntry addFriendlyURLEntry(
		FriendlyURLEntry friendlyURLEntry);

	public FriendlyURLEntry addFriendlyURLEntry(long groupId, long companyId,
		java.lang.Class<?> clazz, long classPK, java.lang.String urlTitle,
		ServiceContext serviceContext) throws PortalException;

	public FriendlyURLEntry addFriendlyURLEntry(long groupId, long companyId,
		long classNameId, long classPK, java.lang.String urlTitle,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new friendly url entry with the primary key. Does not add the friendly url entry to the database.
	*
	* @param friendlyURLEntryId the primary key for the new friendly url entry
	* @return the new friendly url entry
	*/
	public FriendlyURLEntry createFriendlyURLEntry(long friendlyURLEntryId);

	/**
	* Deletes the friendly url entry from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntry the friendly url entry
	* @return the friendly url entry that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public FriendlyURLEntry deleteFriendlyURLEntry(
		FriendlyURLEntry friendlyURLEntry);

	/**
	* Deletes the friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntryId the primary key of the friendly url entry
	* @return the friendly url entry that was removed
	* @throws PortalException if a friendly url entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public FriendlyURLEntry deleteFriendlyURLEntry(long friendlyURLEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURLEntry fetchFriendlyURLEntry(long friendlyURLEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURLEntry fetchFriendlyURLEntry(long groupId, long companyId,
		java.lang.Class<?> clazz, java.lang.String urlTitle);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURLEntry fetchFriendlyURLEntry(long groupId, long companyId,
		long classNameId, java.lang.String urlTitle);

	/**
	* Returns the friendly url entry matching the UUID and group.
	*
	* @param uuid the friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURLEntry fetchFriendlyURLEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	/**
	* Returns the friendly url entry with the primary key.
	*
	* @param friendlyURLEntryId the primary key of the friendly url entry
	* @return the friendly url entry
	* @throws PortalException if a friendly url entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURLEntry getFriendlyURLEntry(long friendlyURLEntryId)
		throws PortalException;

	/**
	* Returns the friendly url entry matching the UUID and group.
	*
	* @param uuid the friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly url entry
	* @throws PortalException if a matching friendly url entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURLEntry getFriendlyURLEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURLEntry getMainFriendlyURLEntry(long groupId,
		long companyId, java.lang.Class<?> clazz, long classPK)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURLEntry getMainFriendlyURLEntry(long groupId,
		long companyId, long classNameId, long classPK)
		throws PortalException;

	/**
	* Updates the friendly url entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntry the friendly url entry
	* @return the friendly url entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public FriendlyURLEntry updateFriendlyURLEntry(
		FriendlyURLEntry friendlyURLEntry);

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
	* Returns the number of friendly url entries.
	*
	* @return the number of friendly url entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFriendlyURLEntriesCount();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getUniqueUrlTitle(long groupId, long companyId,
		long classNameId, long classPK, java.lang.String urlTitle);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @return the range of friendly url entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FriendlyURLEntry> getFriendlyURLEntries(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FriendlyURLEntry> getFriendlyURLEntries(long groupId,
		long companyId, long classNameId, long classPK);

	/**
	* Returns all the friendly url entries matching the UUID and company.
	*
	* @param uuid the UUID of the friendly url entries
	* @param companyId the primary key of the company
	* @return the matching friendly url entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FriendlyURLEntry> getFriendlyURLEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of friendly url entries matching the UUID and company.
	*
	* @param uuid the UUID of the friendly url entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching friendly url entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FriendlyURLEntry> getFriendlyURLEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator);

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

	public void deleteFriendlyURLEntry(long groupId, long companyId,
		java.lang.Class<?> clazz, long classPK);

	public void deleteFriendlyURLEntry(long groupId, long companyId,
		java.lang.Class<?> clazz, long classPK, java.lang.String urlTitle)
		throws NoSuchFriendlyURLEntryException;

	public void deleteFriendlyURLEntry(long groupId, long companyId,
		long classNameId, long classPK, java.lang.String urlTitle)
		throws NoSuchFriendlyURLEntryException;

	public void deleteGroupFriendlyURLEntries(long groupId, long classNameId);

	public void validate(long groupId, long companyId, long classNameId,
		java.lang.String urlTitle) throws PortalException;

	public void validate(long groupId, long companyId, long classNameId,
		long classPK, java.lang.String urlTitle) throws PortalException;
}