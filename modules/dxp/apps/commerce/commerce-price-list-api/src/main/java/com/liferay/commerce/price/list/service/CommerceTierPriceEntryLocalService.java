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

import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
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

import java.math.BigDecimal;

import java.util.List;

/**
 * Provides the local service interface for CommerceTierPriceEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntryLocalServiceUtil
 * @see com.liferay.commerce.price.list.service.base.CommerceTierPriceEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.price.list.service.impl.CommerceTierPriceEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceTierPriceEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTierPriceEntryLocalServiceUtil} to access the commerce tier price entry local service. Add custom service methods to {@link com.liferay.commerce.price.list.service.impl.CommerceTierPriceEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce tier price entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTierPriceEntry the commerce tier price entry
	* @return the commerce tier price entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
		CommerceTierPriceEntry commerceTierPriceEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
		long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
		int minQuantity, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new commerce tier price entry with the primary key. Does not add the commerce tier price entry to the database.
	*
	* @param commerceTierPriceEntryId the primary key for the new commerce tier price entry
	* @return the new commerce tier price entry
	*/
	@Transactional(enabled = false)
	public CommerceTierPriceEntry createCommerceTierPriceEntry(
		long commerceTierPriceEntryId);

	public void deleteCommerceTierPriceEntries(long commercePriceEntryId)
		throws PortalException;

	/**
	* Deletes the commerce tier price entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTierPriceEntry the commerce tier price entry
	* @return the commerce tier price entry that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceTierPriceEntry deleteCommerceTierPriceEntry(
		CommerceTierPriceEntry commerceTierPriceEntry)
		throws PortalException;

	/**
	* Deletes the commerce tier price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	* @return the commerce tier price entry that was removed
	* @throws PortalException if a commerce tier price entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceTierPriceEntry deleteCommerceTierPriceEntry(
		long commerceTierPriceEntryId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommerceTierPriceEntry fetchCommerceTierPriceEntry(
		long commerceTierPriceEntryId);

	/**
	* Returns the commerce tier price entry matching the UUID and group.
	*
	* @param uuid the commerce tier price entry's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceTierPriceEntry fetchCommerceTierPriceEntryByUuidAndGroupId(
		String uuid, long groupId);

	public CommerceTierPriceEntry findClosestCommerceTierPriceEntry(
		long commercePriceEntryId, int quantity);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns a range of all the commerce tier price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of commerce tier price entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
		long commercePriceEntryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns all the commerce tier price entries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce tier price entries
	* @param companyId the primary key of the company
	* @return the matching commerce tier price entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntriesByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	* Returns a range of commerce tier price entries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce tier price entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce tier price entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the number of commerce tier price entries.
	*
	* @return the number of commerce tier price entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceTierPriceEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceTierPriceEntriesCount(long commercePriceEntryId);

	/**
	* Returns the commerce tier price entry with the primary key.
	*
	* @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	* @return the commerce tier price entry
	* @throws PortalException if a commerce tier price entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceTierPriceEntry getCommerceTierPriceEntry(
		long commerceTierPriceEntryId) throws PortalException;

	/**
	* Returns the commerce tier price entry matching the UUID and group.
	*
	* @param uuid the commerce tier price entry's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce tier price entry
	* @throws PortalException if a matching commerce tier price entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceTierPriceEntry getCommerceTierPriceEntryByUuidAndGroupId(
		String uuid, long groupId) throws PortalException;

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommerceTierPriceEntry> searchCommerceTierPriceEntries(
		long companyId, long groupId, long commercePriceEntryId,
		String keywords, int start, int end, Sort sort)
		throws PortalException;

	/**
	* Updates the commerce tier price entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTierPriceEntry the commerce tier price entry
	* @return the commerce tier price entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
		CommerceTierPriceEntry commerceTierPriceEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
		long commerceTierPriceEntryId, BigDecimal price, BigDecimal promoPrice,
		int minQuantity, ServiceContext serviceContext)
		throws PortalException;
}