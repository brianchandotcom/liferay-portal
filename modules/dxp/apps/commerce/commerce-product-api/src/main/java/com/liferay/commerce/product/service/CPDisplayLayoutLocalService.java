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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDisplayLayout;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

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
 * Provides the local service interface for CPDisplayLayout. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPDisplayLayoutLocalServiceUtil
 * @see com.liferay.commerce.product.service.base.CPDisplayLayoutLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDisplayLayoutLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDisplayLayoutLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDisplayLayoutLocalServiceUtil} to access the cp display layout local service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDisplayLayoutLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPDisplayLayout addCPDisplayLayout(java.lang.Class<?> clazz,
		long classPK, java.lang.String layoutUuid, ServiceContext serviceContext);

	/**
	* Adds the cp display layout to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDisplayLayout the cp display layout
	* @return the cp display layout that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDisplayLayout addCPDisplayLayout(CPDisplayLayout cpDisplayLayout);

	/**
	* Creates a new cp display layout with the primary key. Does not add the cp display layout to the database.
	*
	* @param CPDisplayLayoutId the primary key for the new cp display layout
	* @return the new cp display layout
	*/
	public CPDisplayLayout createCPDisplayLayout(long CPDisplayLayoutId);

	public void deleteCPDisplayLayout(java.lang.Class<?> clazz, long classPK);

	/**
	* Deletes the cp display layout from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDisplayLayout the cp display layout
	* @return the cp display layout that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDisplayLayout deleteCPDisplayLayout(
		CPDisplayLayout cpDisplayLayout);

	/**
	* Deletes the cp display layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDisplayLayoutId the primary key of the cp display layout
	* @return the cp display layout that was removed
	* @throws PortalException if a cp display layout with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDisplayLayout deleteCPDisplayLayout(long CPDisplayLayoutId)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CPDisplayLayout fetchCPDisplayLayout(java.lang.Class<?> clazz,
		long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDisplayLayout fetchCPDisplayLayout(long CPDisplayLayoutId);

	/**
	* Returns the cp display layout matching the UUID and group.
	*
	* @param uuid the cp display layout's UUID
	* @param groupId the primary key of the group
	* @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDisplayLayout fetchCPDisplayLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the cp display layout with the primary key.
	*
	* @param CPDisplayLayoutId the primary key of the cp display layout
	* @return the cp display layout
	* @throws PortalException if a cp display layout with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDisplayLayout getCPDisplayLayout(long CPDisplayLayoutId)
		throws PortalException;

	/**
	* Returns the cp display layout matching the UUID and group.
	*
	* @param uuid the cp display layout's UUID
	* @param groupId the primary key of the group
	* @return the matching cp display layout
	* @throws PortalException if a matching cp display layout could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDisplayLayout getCPDisplayLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the cp display layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @return the range of cp display layouts
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDisplayLayout> getCPDisplayLayouts(int start, int end);

	/**
	* Returns all the cp display layouts matching the UUID and company.
	*
	* @param uuid the UUID of the cp display layouts
	* @param companyId the primary key of the company
	* @return the matching cp display layouts, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDisplayLayout> getCPDisplayLayoutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of cp display layouts matching the UUID and company.
	*
	* @param uuid the UUID of the cp display layouts
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp display layouts, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDisplayLayout> getCPDisplayLayoutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator);

	/**
	* Returns the number of cp display layouts.
	*
	* @return the number of cp display layouts
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDisplayLayoutsCount();

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
	* Updates the cp display layout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDisplayLayout the cp display layout
	* @return the cp display layout that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDisplayLayout updateCPDisplayLayout(
		CPDisplayLayout cpDisplayLayout);
}