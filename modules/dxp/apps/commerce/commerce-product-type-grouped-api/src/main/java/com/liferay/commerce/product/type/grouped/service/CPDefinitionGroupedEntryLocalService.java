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

package com.liferay.commerce.product.type.grouped.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;

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
 * Provides the local service interface for CPDefinitionGroupedEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryLocalServiceUtil
 * @see com.liferay.commerce.product.type.grouped.service.base.CPDefinitionGroupedEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionGroupedEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionGroupedEntryLocalServiceUtil} to access the cp definition grouped entry local service. Add custom service methods to {@link com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addCPDefinitionGroupedEntries(long cpDefinitionId,
		long[] entryCPDefinitionIds, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds the cp definition grouped entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionGroupedEntry the cp definition grouped entry
	* @return the cp definition grouped entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry);

	public CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
		long cpDefinitionId, long entryCPDefinitionId, double priority,
		int quantity, ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new cp definition grouped entry with the primary key. Does not add the cp definition grouped entry to the database.
	*
	* @param CPDefinitionGroupedEntryId the primary key for the new cp definition grouped entry
	* @return the new cp definition grouped entry
	*/
	@Transactional(enabled = false)
	public CPDefinitionGroupedEntry createCPDefinitionGroupedEntry(
		long CPDefinitionGroupedEntryId);

	public void deleteCPDefinitionGroupedEntries(long cpDefinitionId);

	/**
	* Deletes the cp definition grouped entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionGroupedEntry the cp definition grouped entry
	* @return the cp definition grouped entry that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDefinitionGroupedEntry deleteCPDefinitionGroupedEntry(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry);

	/**
	* Deletes the cp definition grouped entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	* @return the cp definition grouped entry that was removed
	* @throws PortalException if a cp definition grouped entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDefinitionGroupedEntry deleteCPDefinitionGroupedEntry(
		long CPDefinitionGroupedEntryId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CPDefinitionGroupedEntry fetchCPDefinitionGroupedEntry(
		long CPDefinitionGroupedEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionGroupedEntry fetchCPDefinitionGroupedEntryByC_E(
		long cpDefinitionId, long entryCPDefinitionId);

	/**
	* Returns the cp definition grouped entry matching the UUID and group.
	*
	* @param uuid the cp definition grouped entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionGroupedEntry fetchCPDefinitionGroupedEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns a range of all the cp definition grouped entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @return the range of cp definition grouped entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		long cpDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntriesByCPDefinitionId(
		long cpDefinitionId);

	/**
	* Returns all the cp definition grouped entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition grouped entries
	* @param companyId the primary key of the company
	* @return the matching cp definition grouped entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of cp definition grouped entries matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition grouped entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition grouped entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator);

	/**
	* Returns the number of cp definition grouped entries.
	*
	* @return the number of cp definition grouped entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionGroupedEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionGroupedEntriesCount(long cpDefinitionId);

	/**
	* Returns the cp definition grouped entry with the primary key.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	* @return the cp definition grouped entry
	* @throws PortalException if a cp definition grouped entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionGroupedEntry getCPDefinitionGroupedEntry(
		long CPDefinitionGroupedEntryId) throws PortalException;

	/**
	* Returns the cp definition grouped entry matching the UUID and group.
	*
	* @param uuid the cp definition grouped entry's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition grouped entry
	* @throws PortalException if a matching cp definition grouped entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionGroupedEntry getCPDefinitionGroupedEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

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
	* Updates the cp definition grouped entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionGroupedEntry the cp definition grouped entry
	* @return the cp definition grouped entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry);

	public CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
		long cpDefinitionGroupedEntryId, double priority, int quantity)
		throws PortalException;
}