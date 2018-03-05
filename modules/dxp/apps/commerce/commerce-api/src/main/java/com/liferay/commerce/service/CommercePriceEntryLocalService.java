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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommercePriceEntry;

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

import java.util.List;

/**
 * Provides the local service interface for CommercePriceEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryLocalServiceUtil
 * @see com.liferay.commerce.service.base.CommercePriceEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePriceEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommercePriceEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceEntryLocalServiceUtil} to access the commerce price entry local service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePriceEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce price entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceEntry the commerce price entry
	* @return the commerce price entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry addCommercePriceEntry(
		CommercePriceEntry commercePriceEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry addCommercePriceEntry(long cpInstanceId,
		long commercePriceListId, double price, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new commerce price entry with the primary key. Does not add the commerce price entry to the database.
	*
	* @param commercePriceEntryId the primary key for the new commerce price entry
	* @return the new commerce price entry
	*/
	public CommercePriceEntry createCommercePriceEntry(
		long commercePriceEntryId);

	public void deleteCommercePriceEntries(long commercePriceListId)
		throws PortalException;

	public void deleteCommercePriceEntriesByCPInstanceId(long cpInstanceId)
		throws PortalException;

	/**
	* Deletes the commerce price entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceEntry the commerce price entry
	* @return the commerce price entry that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommercePriceEntry deleteCommercePriceEntry(
		CommercePriceEntry commercePriceEntry) throws PortalException;

	/**
	* Deletes the commerce price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceEntryId the primary key of the commerce price entry
	* @return the commerce price entry that was removed
	* @throws PortalException if a commerce price entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommercePriceEntry deleteCommercePriceEntry(
		long commercePriceEntryId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommercePriceEntry fetchCommercePriceEntry(long commercePriceEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntry(long cpInstanceId,
		long commercePriceListId);

	/**
	* Returns the commerce price entry matching the UUID and group.
	*
	* @param uuid the commerce price entry's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns a range of all the commerce price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of commerce price entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntries(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns all the commerce price entries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price entries
	* @param companyId the primary key of the company
	* @return the matching commerce price entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of commerce price entries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce price entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the number of commerce price entries.
	*
	* @return the number of commerce price entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceEntriesCount(long commercePriceListId);

	/**
	* Returns the commerce price entry with the primary key.
	*
	* @param commercePriceEntryId the primary key of the commerce price entry
	* @return the commerce price entry
	* @throws PortalException if a commerce price entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry getCommercePriceEntry(long commercePriceEntryId)
		throws PortalException;

	/**
	* Returns the commerce price entry matching the UUID and group.
	*
	* @param uuid the commerce price entry's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price entry
	* @throws PortalException if a matching commerce price entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry getCommercePriceEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId);

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
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommercePriceEntry> searchCommercePriceEntries(
		long companyId, long groupId, long commercePriceListId,
		java.lang.String keywords, int start, int end, Sort sort)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry setHasTierPrice(long commercePriceEntryId,
		boolean hasTierPrice) throws PortalException;

	/**
	* Updates the commerce price entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePriceEntry the commerce price entry
	* @return the commerce price entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry updateCommercePriceEntry(
		CommercePriceEntry commercePriceEntry);

	@Indexable(type = IndexableType.REINDEX)
	public CommercePriceEntry updateCommercePriceEntry(
		long commercePriceEntryId, double price, ServiceContext serviceContext)
		throws PortalException;
}