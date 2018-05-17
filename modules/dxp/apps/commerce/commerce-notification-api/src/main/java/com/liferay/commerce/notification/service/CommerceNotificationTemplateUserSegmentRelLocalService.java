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

package com.liferay.commerce.notification.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel;

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
 * Provides the local service interface for CommerceNotificationTemplateUserSegmentRel. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateUserSegmentRelLocalServiceUtil
 * @see com.liferay.commerce.notification.service.base.CommerceNotificationTemplateUserSegmentRelLocalServiceBaseImpl
 * @see com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateUserSegmentRelLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceNotificationTemplateUserSegmentRelLocalService
	extends BaseLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceNotificationTemplateUserSegmentRelLocalServiceUtil} to access the commerce notification template user segment rel local service. Add custom service methods to {@link com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateUserSegmentRelLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce notification template user segment rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateUserSegmentRel the commerce notification template user segment rel
	* @return the commerce notification template user segment rel that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceNotificationTemplateUserSegmentRel addCommerceNotificationTemplateUserSegmentRel(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel);

	public CommerceNotificationTemplateUserSegmentRel addCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new commerce notification template user segment rel with the primary key. Does not add the commerce notification template user segment rel to the database.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key for the new commerce notification template user segment rel
	* @return the new commerce notification template user segment rel
	*/
	@Transactional(enabled = false)
	public CommerceNotificationTemplateUserSegmentRel createCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId);

	public void deleteCNTemplateUserSegmentRelsByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId);

	public void deleteCNTemplateUserSegmentRelsByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Deletes the commerce notification template user segment rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateUserSegmentRel the commerce notification template user segment rel
	* @return the commerce notification template user segment rel that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceNotificationTemplateUserSegmentRel deleteCommerceNotificationTemplateUserSegmentRel(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel);

	/**
	* Deletes the commerce notification template user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	* @return the commerce notification template user segment rel that was removed
	* @throws PortalException if a commerce notification template user segment rel with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceNotificationTemplateUserSegmentRel deleteCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommerceNotificationTemplateUserSegmentRel fetchCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceNotificationTemplateUserSegmentRel fetchCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the commerce notification template user segment rel with the primary key.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	* @return the commerce notification template user segment rel
	* @throws PortalException if a commerce notification template user segment rel with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceNotificationTemplateUserSegmentRel getCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId)
		throws PortalException;

	/**
	* Returns a range of all the commerce notification template user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @return the range of commerce notification template user segment rels
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceNotificationTemplateUserSegmentRel> getCommerceNotificationTemplateUserSegmentRels(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceNotificationTemplateUserSegmentRel> getCommerceNotificationTemplateUserSegmentRels(
		long commerceNotificationTemplateId, int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator);

	/**
	* Returns the number of commerce notification template user segment rels.
	*
	* @return the number of commerce notification template user segment rels
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceNotificationTemplateUserSegmentRelsCount();

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

	/**
	* Updates the commerce notification template user segment rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateUserSegmentRel the commerce notification template user segment rel
	* @return the commerce notification template user segment rel that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceNotificationTemplateUserSegmentRel updateCommerceNotificationTemplateUserSegmentRel(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel);
}