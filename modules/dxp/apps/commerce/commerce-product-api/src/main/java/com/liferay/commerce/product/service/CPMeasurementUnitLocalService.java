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

import com.liferay.commerce.product.model.CPMeasurementUnit;

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
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for CPMeasurementUnit. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPMeasurementUnitLocalServiceUtil
 * @see com.liferay.commerce.product.service.base.CPMeasurementUnitLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPMeasurementUnitLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPMeasurementUnitLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPMeasurementUnitLocalServiceUtil} to access the cp measurement unit local service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPMeasurementUnitLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cp measurement unit to the database. Also notifies the appropriate model listeners.
	*
	* @param cpMeasurementUnit the cp measurement unit
	* @return the cp measurement unit that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPMeasurementUnit addCPMeasurementUnit(
		CPMeasurementUnit cpMeasurementUnit);

	public CPMeasurementUnit addCPMeasurementUnit(
		Map<Locale, java.lang.String> nameMap, java.lang.String key,
		double rate, boolean primary, double priority, int type,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new cp measurement unit with the primary key. Does not add the cp measurement unit to the database.
	*
	* @param CPMeasurementUnitId the primary key for the new cp measurement unit
	* @return the new cp measurement unit
	*/
	public CPMeasurementUnit createCPMeasurementUnit(long CPMeasurementUnitId);

	/**
	* Deletes the cp measurement unit from the database. Also notifies the appropriate model listeners.
	*
	* @param cpMeasurementUnit the cp measurement unit
	* @return the cp measurement unit that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPMeasurementUnit deleteCPMeasurementUnit(
		CPMeasurementUnit cpMeasurementUnit);

	/**
	* Deletes the cp measurement unit with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPMeasurementUnitId the primary key of the cp measurement unit
	* @return the cp measurement unit that was removed
	* @throws PortalException if a cp measurement unit with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPMeasurementUnit deleteCPMeasurementUnit(long CPMeasurementUnitId)
		throws PortalException;

	public void deleteCPMeasurementUnits(long groupId);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CPMeasurementUnit fetchCPMeasurementUnit(long CPMeasurementUnitId);

	/**
	* Returns the cp measurement unit matching the UUID and group.
	*
	* @param uuid the cp measurement unit's UUID
	* @param groupId the primary key of the group
	* @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPMeasurementUnit fetchCPMeasurementUnitByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPMeasurementUnit fetchPrimaryCPMeasurementUnit(long groupId,
		int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the cp measurement unit with the primary key.
	*
	* @param CPMeasurementUnitId the primary key of the cp measurement unit
	* @return the cp measurement unit
	* @throws PortalException if a cp measurement unit with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPMeasurementUnit getCPMeasurementUnit(long CPMeasurementUnitId)
		throws PortalException;

	/**
	* Returns the cp measurement unit matching the UUID and group.
	*
	* @param uuid the cp measurement unit's UUID
	* @param groupId the primary key of the group
	* @return the matching cp measurement unit
	* @throws PortalException if a matching cp measurement unit could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPMeasurementUnit getCPMeasurementUnitByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the cp measurement units.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @return the range of cp measurement units
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPMeasurementUnit> getCPMeasurementUnits(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPMeasurementUnit> getCPMeasurementUnits(long groupId,
		int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPMeasurementUnit> getCPMeasurementUnits(long groupId,
		java.lang.String[] keys, int type);

	/**
	* Returns all the cp measurement units matching the UUID and company.
	*
	* @param uuid the UUID of the cp measurement units
	* @param companyId the primary key of the company
	* @return the matching cp measurement units, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPMeasurementUnit> getCPMeasurementUnitsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of cp measurement units matching the UUID and company.
	*
	* @param uuid the UUID of the cp measurement units
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp measurement units, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPMeasurementUnit> getCPMeasurementUnitsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator);

	/**
	* Returns the number of cp measurement units.
	*
	* @return the number of cp measurement units
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPMeasurementUnitsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPMeasurementUnitsCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPMeasurementUnitsCount(long groupId, int type);

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

	public void importDefaultValues(ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the cp measurement unit in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpMeasurementUnit the cp measurement unit
	* @return the cp measurement unit that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPMeasurementUnit updateCPMeasurementUnit(
		CPMeasurementUnit cpMeasurementUnit);

	public CPMeasurementUnit updateCPMeasurementUnit(long cpMeasurementUnitId,
		Map<Locale, java.lang.String> nameMap, java.lang.String key,
		double rate, boolean primary, double priority, int type,
		ServiceContext serviceContext) throws PortalException;
}