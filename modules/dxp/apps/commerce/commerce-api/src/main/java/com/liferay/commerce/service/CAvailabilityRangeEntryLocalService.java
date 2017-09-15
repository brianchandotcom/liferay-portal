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

import com.liferay.commerce.model.CAvailabilityRangeEntry;

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
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
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
 * Provides the local service interface for CAvailabilityRangeEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CAvailabilityRangeEntryLocalServiceUtil
 * @see com.liferay.commerce.service.base.CAvailabilityRangeEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CAvailabilityRangeEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CAvailabilityRangeEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CAvailabilityRangeEntryLocalServiceUtil} to access the c availability range entry local service. Add custom service methods to {@link com.liferay.commerce.service.impl.CAvailabilityRangeEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the c availability range entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cAvailabilityRangeEntry the c availability range entry
	* @return the c availability range entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CAvailabilityRangeEntry addCAvailabilityRangeEntry(
		CAvailabilityRangeEntry cAvailabilityRangeEntry);

	/**
	* Creates a new c availability range entry with the primary key. Does not add the c availability range entry to the database.
	*
	* @param CAvailabilityRangeEntryId the primary key for the new c availability range entry
	* @return the new c availability range entry
	*/
	public CAvailabilityRangeEntry createCAvailabilityRangeEntry(
		long CAvailabilityRangeEntryId);

	public void deleteCAvailabilityRangeEntries(long groupId);

	/**
	* Deletes the c availability range entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cAvailabilityRangeEntry the c availability range entry
	* @return the c availability range entry that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CAvailabilityRangeEntry deleteCAvailabilityRangeEntry(
		CAvailabilityRangeEntry cAvailabilityRangeEntry);

	/**
	* Deletes the c availability range entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	* @return the c availability range entry that was removed
	* @throws PortalException if a c availability range entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CAvailabilityRangeEntry deleteCAvailabilityRangeEntry(
		long CAvailabilityRangeEntryId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CAvailabilityRangeEntry fetchCAvailabilityRangeEntry(
		long CAvailabilityRangeEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CAvailabilityRangeEntry fetchCAvailabilityRangeEntry(long groupId,
		long cpDefinitionId);

	/**
	* Returns the c availability range entry matching the UUID and group.
	*
	* @param uuid the c availability range entry's UUID
	* @param groupId the primary key of the group
	* @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CAvailabilityRangeEntry fetchCAvailabilityRangeEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns a range of all the c availability range entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @return the range of c availability range entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CAvailabilityRangeEntry> getCAvailabilityRangeEntries(
		int start, int end);

	/**
	* Returns all the c availability range entries matching the UUID and company.
	*
	* @param uuid the UUID of the c availability range entries
	* @param companyId the primary key of the company
	* @return the matching c availability range entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CAvailabilityRangeEntry> getCAvailabilityRangeEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of c availability range entries matching the UUID and company.
	*
	* @param uuid the UUID of the c availability range entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of c availability range entries
	* @param end the upper bound of the range of c availability range entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching c availability range entries, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CAvailabilityRangeEntry> getCAvailabilityRangeEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator);

	/**
	* Returns the number of c availability range entries.
	*
	* @return the number of c availability range entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCAvailabilityRangeEntriesCount();

	/**
	* Returns the c availability range entry with the primary key.
	*
	* @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	* @return the c availability range entry
	* @throws PortalException if a c availability range entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CAvailabilityRangeEntry getCAvailabilityRangeEntry(
		long CAvailabilityRangeEntryId) throws PortalException;

	/**
	* Returns the c availability range entry matching the UUID and group.
	*
	* @param uuid the c availability range entry's UUID
	* @param groupId the primary key of the group
	* @return the matching c availability range entry
	* @throws PortalException if a matching c availability range entry could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CAvailabilityRangeEntry getCAvailabilityRangeEntryByUuidAndGroupId(
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
	* Updates the c availability range entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cAvailabilityRangeEntry the c availability range entry
	* @return the c availability range entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CAvailabilityRangeEntry updateCAvailabilityRangeEntry(
		CAvailabilityRangeEntry cAvailabilityRangeEntry);

	public CAvailabilityRangeEntry updateCAvailabilityRangeEntry(
		long cAvailabilityRangeEntryId, long cpDefinitionId,
		long commerceAvailabilityRangeId, ServiceContext serviceContext)
		throws PortalException;
}