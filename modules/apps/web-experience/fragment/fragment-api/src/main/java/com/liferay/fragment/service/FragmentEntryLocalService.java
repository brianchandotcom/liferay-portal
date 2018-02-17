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

package com.liferay.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.model.FragmentEntry;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
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
 * Provides the local service interface for FragmentEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLocalServiceUtil
 * @see com.liferay.fragment.service.base.FragmentEntryLocalServiceBaseImpl
 * @see com.liferay.fragment.service.impl.FragmentEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface FragmentEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentEntryLocalServiceUtil} to access the fragment entry local service. Add custom service methods to {@link com.liferay.fragment.service.impl.FragmentEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the fragment entry to the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntry the fragment entry
	* @return the fragment entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public FragmentEntry addFragmentEntry(FragmentEntry fragmentEntry);

	public FragmentEntry addFragmentEntry(long userId, long groupId,
		long fragmentCollectionId, java.lang.String name, int status,
		ServiceContext serviceContext) throws PortalException;

	public FragmentEntry addFragmentEntry(long userId, long groupId,
		long fragmentCollectionId, java.lang.String fragmentEntryKey,
		java.lang.String name, int status, ServiceContext serviceContext)
		throws PortalException;

	public FragmentEntry addFragmentEntry(long userId, long groupId,
		long fragmentCollectionId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js, int status,
		ServiceContext serviceContext) throws PortalException;

	public FragmentEntry addFragmentEntry(long userId, long groupId,
		long fragmentCollectionId, java.lang.String fragmentEntryKey,
		java.lang.String name, java.lang.String css, java.lang.String html,
		java.lang.String js, int status, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new fragment entry with the primary key. Does not add the fragment entry to the database.
	*
	* @param fragmentEntryId the primary key for the new fragment entry
	* @return the new fragment entry
	*/
	public FragmentEntry createFragmentEntry(long fragmentEntryId);

	/**
	* Deletes the fragment entry from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntry the fragment entry
	* @return the fragment entry that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	public FragmentEntry deleteFragmentEntry(FragmentEntry fragmentEntry)
		throws PortalException;

	/**
	* Deletes the fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryId the primary key of the fragment entry
	* @return the fragment entry that was removed
	* @throws PortalException if a fragment entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public FragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentEntry fetchFragmentEntry(long groupId,
		java.lang.String fragmentEntryKey);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns a range of all the fragment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of fragment entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long fragmentCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long fragmentCollectionId,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long fragmentCollectionId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long groupId,
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentEntry> getFragmentEntries(long groupId,
		long fragmentCollectionId, java.lang.String name, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator);

	/**
	* Returns the number of fragment entries.
	*
	* @return the number of fragment entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentEntriesCount(long fragmentCollectionId);

	/**
	* Returns the fragment entry with the primary key.
	*
	* @param fragmentEntryId the primary key of the fragment entry
	* @return the fragment entry
	* @throws PortalException if a fragment entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentEntry getFragmentEntry(long fragmentEntryId)
		throws PortalException;

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
	public java.lang.String[] getTempFileNames(long userId, long groupId,
		java.lang.String folderName) throws PortalException;

	/**
	* Updates the fragment entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntry the fragment entry
	* @return the fragment entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public FragmentEntry updateFragmentEntry(FragmentEntry fragmentEntry);

	public FragmentEntry updateFragmentEntry(long userId, long fragmentEntryId,
		java.lang.String name, java.lang.String css, java.lang.String html,
		java.lang.String js, int status, ServiceContext serviceContext)
		throws PortalException;

	public FragmentEntry updateFragmentEntry(long fragmentEntryId,
		java.lang.String name) throws PortalException;
}