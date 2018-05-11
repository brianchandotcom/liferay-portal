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

import com.liferay.commerce.product.model.CPOptionValue;

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
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for CPOptionValue. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPOptionValueLocalServiceUtil
 * @see com.liferay.commerce.product.service.base.CPOptionValueLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPOptionValueLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPOptionValueLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPOptionValueLocalServiceUtil} to access the cp option value local service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPOptionValueLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cp option value to the database. Also notifies the appropriate model listeners.
	*
	* @param cpOptionValue the cp option value
	* @return the cp option value that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPOptionValue addCPOptionValue(CPOptionValue cpOptionValue);

	@Indexable(type = IndexableType.REINDEX)
	public CPOptionValue addCPOptionValue(long cpOptionId,
		Map<Locale, String> nameMap, double priority, String key,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new cp option value with the primary key. Does not add the cp option value to the database.
	*
	* @param CPOptionValueId the primary key for the new cp option value
	* @return the new cp option value
	*/
	@Transactional(enabled = false)
	public CPOptionValue createCPOptionValue(long CPOptionValueId);

	/**
	* Deletes the cp option value from the database. Also notifies the appropriate model listeners.
	*
	* @param cpOptionValue the cp option value
	* @return the cp option value that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPOptionValue deleteCPOptionValue(CPOptionValue cpOptionValue)
		throws PortalException;

	/**
	* Deletes the cp option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPOptionValueId the primary key of the cp option value
	* @return the cp option value that was removed
	* @throws PortalException if a cp option value with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPOptionValue deleteCPOptionValue(long CPOptionValueId)
		throws PortalException;

	public void deleteCPOptionValues(long cpOptionId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CPOptionValue fetchCPOptionValue(long CPOptionValueId);

	/**
	* Returns the cp option value matching the UUID and group.
	*
	* @param uuid the cp option value's UUID
	* @param groupId the primary key of the group
	* @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOptionValue fetchCPOptionValueByUuidAndGroupId(String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the cp option value with the primary key.
	*
	* @param CPOptionValueId the primary key of the cp option value
	* @return the cp option value
	* @throws PortalException if a cp option value with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOptionValue getCPOptionValue(long CPOptionValueId)
		throws PortalException;

	/**
	* Returns the cp option value matching the UUID and group.
	*
	* @param uuid the cp option value's UUID
	* @param groupId the primary key of the group
	* @return the matching cp option value
	* @throws PortalException if a matching cp option value could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOptionValue getCPOptionValueByUuidAndGroupId(String uuid,
		long groupId) throws PortalException;

	/**
	* Returns a range of all the cp option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of cp option values
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOptionValue> getCPOptionValues(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOptionValue> getCPOptionValues(long cpOptionId, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOptionValue> getCPOptionValues(long cpOptionId, int start,
		int end, OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns all the cp option values matching the UUID and company.
	*
	* @param uuid the UUID of the cp option values
	* @param companyId the primary key of the company
	* @return the matching cp option values, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOptionValue> getCPOptionValuesByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	* Returns a range of cp option values matching the UUID and company.
	*
	* @param uuid the UUID of the cp option values
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp option values, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOptionValue> getCPOptionValuesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator);

	/**
	* Returns the number of cp option values.
	*
	* @return the number of cp option values
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPOptionValuesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPOptionValuesCount(long cpOptionId);

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
	public BaseModelSearchResult<CPOptionValue> searchCPOptionValues(
		long companyId, long groupId, long cpOptionId, String keywords,
		int start, int end, Sort sort) throws PortalException;

	/**
	* Updates the cp option value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpOptionValue the cp option value
	* @return the cp option value that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPOptionValue updateCPOptionValue(CPOptionValue cpOptionValue);

	@Indexable(type = IndexableType.REINDEX)
	public CPOptionValue updateCPOptionValue(long cpOptionValueId,
		Map<Locale, String> nameMap, double priority, String key,
		ServiceContext serviceContext) throws PortalException;
}