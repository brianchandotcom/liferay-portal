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

package com.liferay.commerce.user.segment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for CommerceUserSegmentEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentEntryLocalServiceUtil
 * @see com.liferay.commerce.user.segment.service.base.CommerceUserSegmentEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceUserSegmentEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceUserSegmentEntryLocalServiceUtil} to access the commerce user segment entry local service. Add custom service methods to {@link com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce user segment entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	* @return the commerce user segment entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceUserSegmentEntry addCommerceUserSegmentEntry(
		CommerceUserSegmentEntry commerceUserSegmentEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceUserSegmentEntry addCommerceUserSegmentEntry(
		Map<Locale, String> nameMap, String key, boolean active,
		boolean system, double priority, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new commerce user segment entry with the primary key. Does not add the commerce user segment entry to the database.
	*
	* @param commerceUserSegmentEntryId the primary key for the new commerce user segment entry
	* @return the new commerce user segment entry
	*/
	@Transactional(enabled = false)
	public CommerceUserSegmentEntry createCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId);

	public void deleteCommerceUserSegmentEntries(long groupId)
		throws PortalException;

	/**
	* Deletes the commerce user segment entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	* @return the commerce user segment entry that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
		CommerceUserSegmentEntry commerceUserSegmentEntry)
		throws PortalException;

	/**
	* Deletes the commerce user segment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry that was removed
	* @throws PortalException if a commerce user segment entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommerceUserSegmentEntry fetchCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceUserSegmentEntry fetchCommerceUserSegmentEntry(
		long groupId, String key);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns a range of all the commerce user segment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @return the range of commerce user segment entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		long groupId, int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ArrayList<CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		long groupId, long userId) throws PortalException;

	/**
	* Returns the number of commerce user segment entries.
	*
	* @return the number of commerce user segment entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceUserSegmentEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceUserSegmentEntriesCount(long groupId);

	/**
	* Returns the commerce user segment entry with the primary key.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry
	* @throws PortalException if a commerce user segment entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceUserSegmentEntry getCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public void importSystemCommerceUserSegmentEntries(
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommerceUserSegmentEntry> searchCommerceUserSegmentEntries(
		long companyId, long groupId, String keywords, int start, int end,
		Sort sort) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommerceUserSegmentEntry> searchCommerceUserSegmentEntries(
		SearchContext searchContext) throws PortalException;

	/**
	* Updates the commerce user segment entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	* @return the commerce user segment entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
		CommerceUserSegmentEntry commerceUserSegmentEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId, Map<Locale, String> nameMap,
		String key, boolean active, double priority,
		ServiceContext serviceContext) throws PortalException;
}