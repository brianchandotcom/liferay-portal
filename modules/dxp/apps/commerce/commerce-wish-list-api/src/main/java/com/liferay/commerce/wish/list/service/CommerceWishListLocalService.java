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

package com.liferay.commerce.wish.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.wish.list.model.CommerceWishList;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable;
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
 * Provides the local service interface for CommerceWishList. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListLocalServiceUtil
 * @see com.liferay.commerce.wish.list.service.base.CommerceWishListLocalServiceBaseImpl
 * @see com.liferay.commerce.wish.list.service.impl.CommerceWishListLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceWishListLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceWishListLocalServiceUtil} to access the commerce wish list local service. Add custom service methods to {@link com.liferay.commerce.wish.list.service.impl.CommerceWishListLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce wish list to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishList the commerce wish list
	* @return the commerce wish list that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceWishList addCommerceWishList(
		CommerceWishList commerceWishList);

	public CommerceWishList addCommerceWishList(java.lang.String name,
		boolean defaultWishList, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new commerce wish list with the primary key. Does not add the commerce wish list to the database.
	*
	* @param commerceWishListId the primary key for the new commerce wish list
	* @return the new commerce wish list
	*/
	public CommerceWishList createCommerceWishList(long commerceWishListId);

	/**
	* Deletes the commerce wish list from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishList the commerce wish list
	* @return the commerce wish list that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceWishList deleteCommerceWishList(
		CommerceWishList commerceWishList);

	/**
	* Deletes the commerce wish list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishListId the primary key of the commerce wish list
	* @return the commerce wish list that was removed
	* @throws PortalException if a commerce wish list with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceWishList deleteCommerceWishList(long commerceWishListId)
		throws PortalException;

	public void deleteCommerceWishListsByGroupId(long groupId);

	public void deleteCommerceWishListsByUserId(long userId);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommerceWishList fetchCommerceWishList(long commerceWishListId);

	/**
	* Returns the commerce wish list matching the UUID and group.
	*
	* @param uuid the commerce wish list's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceWishList fetchCommerceWishListByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the commerce wish list with the primary key.
	*
	* @param commerceWishListId the primary key of the commerce wish list
	* @return the commerce wish list
	* @throws PortalException if a commerce wish list with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceWishList getCommerceWishList(long commerceWishListId)
		throws PortalException;

	/**
	* Returns the commerce wish list matching the UUID and group.
	*
	* @param uuid the commerce wish list's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce wish list
	* @throws PortalException if a matching commerce wish list could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceWishList getCommerceWishListByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the commerce wish lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @return the range of commerce wish lists
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWishList> getCommerceWishLists(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWishList> getCommerceWishLists(long groupId, int start,
		int end, OrderByComparator<CommerceWishList> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWishList> getCommerceWishLists(long groupId,
		long userId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns all the commerce wish lists matching the UUID and company.
	*
	* @param uuid the UUID of the commerce wish lists
	* @param companyId the primary key of the company
	* @return the matching commerce wish lists, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWishList> getCommerceWishListsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of commerce wish lists matching the UUID and company.
	*
	* @param uuid the UUID of the commerce wish lists
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce wish lists, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWishList> getCommerceWishListsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the number of commerce wish lists.
	*
	* @return the number of commerce wish lists
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceWishListsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceWishListsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceWishListsCount(long groupId, long userId);

	@ThreadLocalCachable
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceWishList getDefaultCommerceWishList(long groupId,
		long userId, java.lang.String guestUuid) throws PortalException;

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

	/**
	* Updates the commerce wish list in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceWishList the commerce wish list
	* @return the commerce wish list that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceWishList updateCommerceWishList(
		CommerceWishList commerceWishList);

	public CommerceWishList updateCommerceWishList(long commerceWishListId,
		java.lang.String name, boolean defaultWishList)
		throws PortalException;
}