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

package com.liferay.commerce.price.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel;

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
 * Provides the local service interface for CommercePriceListUserSegmentEntryRel. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserSegmentEntryRelLocalServiceUtil
 * @see com.liferay.commerce.price.list.service.base.CommercePriceListUserSegmentEntryRelLocalServiceBaseImpl
 * @see com.liferay.commerce.price.list.service.impl.CommercePriceListUserSegmentEntryRelLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommercePriceListUserSegmentEntryRelLocalService
	extends BaseLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListUserSegmentEntryRelLocalServiceUtil} to access the commerce price list user segment entry rel local service. Add custom service methods to {@link com.liferay.commerce.price.list.service.impl.CommercePriceListUserSegmentEntryRelLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce price list user segment entry rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserSegmentEntryRel the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceListUserSegmentEntryRel addCommercePriceListUserSegmentEntryRel(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel);

	public CommercePriceListUserSegmentEntryRel addCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId, int order,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new commerce price list user segment entry rel with the primary key. Does not add the commerce price list user segment entry rel to the database.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key for the new commerce price list user segment entry rel
	* @return the new commerce price list user segment entry rel
	*/
	@Transactional(enabled = false)
	public CommercePriceListUserSegmentEntryRel createCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId);

	/**
	* Deletes the commerce price list user segment entry rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserSegmentEntryRel the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommercePriceListUserSegmentEntryRel deleteCommercePriceListUserSegmentEntryRel(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel)
		throws PortalException;

	/**
	* Deletes the commerce price list user segment entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel that was removed
	* @throws PortalException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommercePriceListUserSegmentEntryRel deleteCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId) throws PortalException;

	public void deleteCommercePriceListUserSegmentEntryRels(
		long commercePriceListId);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId);

	/**
	* Returns the commerce price list user segment entry rel matching the UUID and group.
	*
	* @param uuid the commerce price list user segment entry rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRelByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the commerce price list user segment entry rel with the primary key.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel
	* @throws PortalException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceListUserSegmentEntryRel getCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId) throws PortalException;

	/**
	* Returns the commerce price list user segment entry rel matching the UUID and group.
	*
	* @param uuid the commerce price list user segment entry rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list user segment entry rel
	* @throws PortalException if a matching commerce price list user segment entry rel could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceListUserSegmentEntryRel getCommercePriceListUserSegmentEntryRelByUuidAndGroupId(
		String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the commerce price list user segment entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @return the range of commerce price list user segment entry rels
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns all the commerce price list user segment entry rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list user segment entry rels
	* @param companyId the primary key of the company
	* @return the matching commerce price list user segment entry rels, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRelsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	* Returns a range of commerce price list user segment entry rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list user segment entry rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce price list user segment entry rels, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRelsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns the number of commerce price list user segment entry rels.
	*
	* @return the number of commerce price list user segment entry rels
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceListUserSegmentEntryRelsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceListUserSegmentEntryRelsCount(
		long commercePriceListId);

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
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Updates the commerce price list user segment entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserSegmentEntryRel the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceListUserSegmentEntryRel updateCommercePriceListUserSegmentEntryRel(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel);

	public CommercePriceListUserSegmentEntryRel updateCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId, int order,
		ServiceContext serviceContext) throws PortalException;
}