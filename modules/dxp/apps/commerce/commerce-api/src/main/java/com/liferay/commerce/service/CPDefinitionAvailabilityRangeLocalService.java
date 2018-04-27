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

import com.liferay.commerce.model.CPDefinitionAvailabilityRange;

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
 * Provides the local service interface for CPDefinitionAvailabilityRange. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionAvailabilityRangeLocalServiceUtil
 * @see com.liferay.commerce.service.base.CPDefinitionAvailabilityRangeLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionAvailabilityRangeLocalService
	extends BaseLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionAvailabilityRangeLocalServiceUtil} to access the cp definition availability range local service. Add custom service methods to {@link com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cp definition availability range to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionAvailabilityRange the cp definition availability range
	* @return the cp definition availability range that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionAvailabilityRange addCPDefinitionAvailabilityRange(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange);

	/**
	* Creates a new cp definition availability range with the primary key. Does not add the cp definition availability range to the database.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key for the new cp definition availability range
	* @return the new cp definition availability range
	*/
	@Transactional(enabled = false)
	public CPDefinitionAvailabilityRange createCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId);

	/**
	* Deletes the cp definition availability range from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionAvailabilityRange the cp definition availability range
	* @return the cp definition availability range that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionAvailabilityRange deleteCPDefinitionAvailabilityRange(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange);

	/**
	* Deletes the cp definition availability range with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	* @return the cp definition availability range that was removed
	* @throws PortalException if a cp definition availability range with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDefinitionAvailabilityRange deleteCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId) throws PortalException;

	public void deleteCPDefinitionAvailabilityRangeByCPDefinitionId(
		long cpDefinitionId);

	public void deleteCPDefinitionAvailabilityRanges(
		long commerceAvailabilityRangeId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
		long cpDefinitionId);

	/**
	* Returns the cp definition availability range matching the UUID and group.
	*
	* @param uuid the cp definition availability range's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRangeByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the cp definition availability range with the primary key.
	*
	* @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	* @return the cp definition availability range
	* @throws PortalException if a cp definition availability range with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionAvailabilityRange getCPDefinitionAvailabilityRange(
		long CPDefinitionAvailabilityRangeId) throws PortalException;

	/**
	* Returns the cp definition availability range matching the UUID and group.
	*
	* @param uuid the cp definition availability range's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition availability range
	* @throws PortalException if a matching cp definition availability range could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionAvailabilityRange getCPDefinitionAvailabilityRangeByUuidAndGroupId(
		String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the cp definition availability ranges.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @return the range of cp definition availability ranges
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionAvailabilityRange> getCPDefinitionAvailabilityRanges(
		int start, int end);

	/**
	* Returns all the cp definition availability ranges matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition availability ranges
	* @param companyId the primary key of the company
	* @return the matching cp definition availability ranges, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionAvailabilityRange> getCPDefinitionAvailabilityRangesByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	* Returns a range of cp definition availability ranges matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition availability ranges
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition availability ranges
	* @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition availability ranges, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionAvailabilityRange> getCPDefinitionAvailabilityRangesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator);

	/**
	* Returns the number of cp definition availability ranges.
	*
	* @return the number of cp definition availability ranges
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionAvailabilityRangesCount();

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
	* Updates the cp definition availability range in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionAvailabilityRange the cp definition availability range
	* @return the cp definition availability range that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionAvailabilityRange updateCPDefinitionAvailabilityRange(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange);

	public CPDefinitionAvailabilityRange updateCPDefinitionAvailabilityRange(
		long cpDefinitionAvailabilityRangeId, long cpDefinitionId,
		long commerceAvailabilityRangeId, ServiceContext serviceContext)
		throws PortalException;
}