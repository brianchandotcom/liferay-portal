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

import com.liferay.friendly.url.exception.NoSuchFriendlyURLException;
import com.liferay.friendly.url.model.FriendlyURL;

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
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for FriendlyURL. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLLocalServiceUtil
 * @see com.liferay.friendly.url.service.base.FriendlyURLLocalServiceBaseImpl
 * @see com.liferay.friendly.url.service.impl.FriendlyURLLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface FriendlyURLLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FriendlyURLLocalServiceUtil} to access the friendly url local service. Add custom service methods to {@link com.liferay.friendly.url.service.impl.FriendlyURLLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the friendly url to the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURL the friendly url
	* @return the friendly url that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public FriendlyURL addFriendlyURL(FriendlyURL friendlyURL);

	public FriendlyURL addFriendlyURL(long groupId, long companyId,
		java.lang.Class<?> clazz, long classPK, java.lang.String urlTitle)
		throws PortalException;

	public FriendlyURL addFriendlyURL(long groupId, long companyId,
		long classNameId, long classPK, java.lang.String urlTitle)
		throws PortalException;

	/**
	* Creates a new friendly url with the primary key. Does not add the friendly url to the database.
	*
	* @param friendlyURLId the primary key for the new friendly url
	* @return the new friendly url
	*/
	public FriendlyURL createFriendlyURL(long friendlyURLId);

	/**
	* Deletes the friendly url from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURL the friendly url
	* @return the friendly url that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public FriendlyURL deleteFriendlyURL(FriendlyURL friendlyURL);

	/**
	* Deletes the friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLId the primary key of the friendly url
	* @return the friendly url that was removed
	* @throws PortalException if a friendly url with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public FriendlyURL deleteFriendlyURL(long friendlyURLId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURL fetchFriendlyURL(long friendlyURLId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURL fetchFriendlyURL(long groupId, long companyId,
		java.lang.Class<?> clazz, java.lang.String urlTitle);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURL fetchFriendlyURL(long groupId, long companyId,
		long classNameId, java.lang.String urlTitle);

	/**
	* Returns the friendly url matching the UUID and group.
	*
	* @param uuid the friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURL fetchFriendlyURLByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	/**
	* Returns the friendly url with the primary key.
	*
	* @param friendlyURLId the primary key of the friendly url
	* @return the friendly url
	* @throws PortalException if a friendly url with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURL getFriendlyURL(long friendlyURLId)
		throws PortalException;

	/**
	* Returns the friendly url matching the UUID and group.
	*
	* @param uuid the friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly url
	* @throws PortalException if a matching friendly url could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURL getFriendlyURLByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURL getMainFriendlyURL(long groupId, long companyId,
		java.lang.Class<?> clazz, long classPK) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FriendlyURL getMainFriendlyURL(long groupId, long companyId,
		long classNameId, long classPK) throws PortalException;

	/**
	* Updates the friendly url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param friendlyURL the friendly url
	* @return the friendly url that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public FriendlyURL updateFriendlyURL(FriendlyURL friendlyURL);

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
	* Returns the number of friendly urls.
	*
	* @return the number of friendly urls
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFriendlyURLsCount();

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of friendly urls
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FriendlyURL> getFriendlyURLs(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FriendlyURL> getFriendlyURLs(long groupId, long companyId,
		long classNameId, long classPK);

	/**
	* Returns all the friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the friendly urls
	* @param companyId the primary key of the company
	* @return the matching friendly urls, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FriendlyURL> getFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the friendly urls
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching friendly urls, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FriendlyURL> getFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator);

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

	public void deleteFriendlyURL(long groupId, long companyId,
		java.lang.Class<?> clazz, long classPK);

	public void deleteFriendlyURL(long groupId, long companyId,
		java.lang.Class<?> clazz, long classPK, java.lang.String urlTitle)
		throws NoSuchFriendlyURLException;

	public void deleteFriendlyURL(long groupId, long companyId,
		long classNameId, long classPK, java.lang.String urlTitle)
		throws NoSuchFriendlyURLException;

	public void deleteGroupFriendlyURLs(long groupId, long classNameId);

	public void validate(long groupId, long companyId, long classNameId,
		java.lang.String urlTitle) throws PortalException;

	public void validate(long groupId, long companyId, long classNameId,
		long classPK, java.lang.String urlTitle) throws PortalException;
}