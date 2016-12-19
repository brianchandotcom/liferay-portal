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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for LayoutFriendlyURL. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutFriendlyURLLocalServiceUtil
 * @see com.liferay.portal.service.base.LayoutFriendlyURLLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutFriendlyURLLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutFriendlyURLLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutFriendlyURLLocalServiceUtil} to access the layout friendly url local service. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutFriendlyURLLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Adds the layout friendly url to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURL the layout friendly url
	* @return the layout friendly url that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public LayoutFriendlyURL addLayoutFriendlyURL(
		LayoutFriendlyURL layoutFriendlyURL);

	public LayoutFriendlyURL addLayoutFriendlyURL(long userId, long companyId,
		long groupId, long plid, boolean privateLayout,
		java.lang.String friendlyURL, java.lang.String languageId,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new layout friendly url with the primary key. Does not add the layout friendly url to the database.
	*
	* @param layoutFriendlyURLId the primary key for the new layout friendly url
	* @return the new layout friendly url
	*/
	public LayoutFriendlyURL createLayoutFriendlyURL(long layoutFriendlyURLId);

	/**
	* Deletes the layout friendly url from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURL the layout friendly url
	* @return the layout friendly url that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public LayoutFriendlyURL deleteLayoutFriendlyURL(
		LayoutFriendlyURL layoutFriendlyURL);

	/**
	* Deletes the layout friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly url
	* @return the layout friendly url that was removed
	* @throws PortalException if a layout friendly url with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public LayoutFriendlyURL deleteLayoutFriendlyURL(long layoutFriendlyURLId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutFriendlyURL fetchFirstLayoutFriendlyURL(long groupId,
		boolean privateLayout, java.lang.String friendlyURL);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutFriendlyURL fetchLayoutFriendlyURL(long groupId,
		boolean privateLayout, java.lang.String friendlyURL,
		java.lang.String languageId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutFriendlyURL fetchLayoutFriendlyURL(long layoutFriendlyURLId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutFriendlyURL fetchLayoutFriendlyURL(long plid,
		java.lang.String languageId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutFriendlyURL fetchLayoutFriendlyURL(long plid,
		java.lang.String languageId, boolean useDefault);

	/**
	* Returns the layout friendly url matching the UUID and group.
	*
	* @param uuid the layout friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching layout friendly url, or <code>null</code> if a matching layout friendly url could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutFriendlyURL fetchLayoutFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	/**
	* Returns the layout friendly url with the primary key.
	*
	* @param layoutFriendlyURLId the primary key of the layout friendly url
	* @return the layout friendly url
	* @throws PortalException if a layout friendly url with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutFriendlyURL getLayoutFriendlyURL(long layoutFriendlyURLId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutFriendlyURL getLayoutFriendlyURL(long plid,
		java.lang.String languageId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutFriendlyURL getLayoutFriendlyURL(long plid,
		java.lang.String languageId, boolean useDefault)
		throws PortalException;

	/**
	* Returns the layout friendly url matching the UUID and group.
	*
	* @param uuid the layout friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching layout friendly url
	* @throws PortalException if a matching layout friendly url could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutFriendlyURL getLayoutFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Updates the layout friendly url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutFriendlyURL the layout friendly url
	* @return the layout friendly url that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public LayoutFriendlyURL updateLayoutFriendlyURL(
		LayoutFriendlyURL layoutFriendlyURL);

	public LayoutFriendlyURL updateLayoutFriendlyURL(long userId,
		long companyId, long groupId, long plid, boolean privateLayout,
		java.lang.String friendlyURL, java.lang.String languageId,
		ServiceContext serviceContext) throws PortalException;

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
	* Returns the number of layout friendly urls.
	*
	* @return the number of layout friendly urls
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutFriendlyURLsCount();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public List<LayoutFriendlyURL> addLayoutFriendlyURLs(long userId,
		long companyId, long groupId, long plid, boolean privateLayout,
		Map<Locale, java.lang.String> friendlyURLMap,
		ServiceContext serviceContext) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the layout friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @return the range of layout friendly urls
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutFriendlyURL> getLayoutFriendlyURLs(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutFriendlyURL> getLayoutFriendlyURLs(long plid);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutFriendlyURL> getLayoutFriendlyURLs(long plid,
		java.lang.String friendlyURL, int start, int end);

	/**
	* Returns all the layout friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the layout friendly urls
	* @param companyId the primary key of the company
	* @return the matching layout friendly urls, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutFriendlyURL> getLayoutFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of layout friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the layout friendly urls
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of layout friendly urls
	* @param end the upper bound of the range of layout friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching layout friendly urls, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutFriendlyURL> getLayoutFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutFriendlyURL> orderByComparator);

	public List<LayoutFriendlyURL> updateLayoutFriendlyURLs(long userId,
		long companyId, long groupId, long plid, boolean privateLayout,
		Map<Locale, java.lang.String> friendlyURLMap,
		ServiceContext serviceContext) throws PortalException;

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

	public void deleteLayoutFriendlyURL(long plid, java.lang.String languageId);

	public void deleteLayoutFriendlyURLs(long plid);
}