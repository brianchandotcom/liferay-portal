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

import com.liferay.commerce.product.model.CPSpecificationOption;

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
 * Provides the local service interface for CPSpecificationOption. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPSpecificationOptionLocalServiceUtil
 * @see com.liferay.commerce.product.service.base.CPSpecificationOptionLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPSpecificationOptionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPSpecificationOptionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPSpecificationOptionLocalServiceUtil} to access the cp specification option local service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPSpecificationOptionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cp specification option to the database. Also notifies the appropriate model listeners.
	*
	* @param cpSpecificationOption the cp specification option
	* @return the cp specification option that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPSpecificationOption addCPSpecificationOption(
		CPSpecificationOption cpSpecificationOption);

	@Indexable(type = IndexableType.REINDEX)
	public CPSpecificationOption addCPSpecificationOption(
		long cpOptionCategoryId, Map<Locale, String> titleMap,
		Map<Locale, String> descriptionMap, boolean facetable, String key,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new cp specification option with the primary key. Does not add the cp specification option to the database.
	*
	* @param CPSpecificationOptionId the primary key for the new cp specification option
	* @return the new cp specification option
	*/
	@Transactional(enabled = false)
	public CPSpecificationOption createCPSpecificationOption(
		long CPSpecificationOptionId);

	/**
	* Deletes the cp specification option from the database. Also notifies the appropriate model listeners.
	*
	* @param cpSpecificationOption the cp specification option
	* @return the cp specification option that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPSpecificationOption deleteCPSpecificationOption(
		CPSpecificationOption cpSpecificationOption) throws PortalException;

	/**
	* Deletes the cp specification option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPSpecificationOptionId the primary key of the cp specification option
	* @return the cp specification option that was removed
	* @throws PortalException if a cp specification option with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPSpecificationOption deleteCPSpecificationOption(
		long CPSpecificationOptionId) throws PortalException;

	public void deleteCPSpecificationOptions(long groupId)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CPSpecificationOption fetchCPSpecificationOption(
		long CPSpecificationOptionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPSpecificationOption fetchCPSpecificationOption(long groupId,
		String key);

	/**
	* Returns the cp specification option matching the UUID and group.
	*
	* @param uuid the cp specification option's UUID
	* @param groupId the primary key of the group
	* @return the matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPSpecificationOption fetchCPSpecificationOptionByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the cp specification option with the primary key.
	*
	* @param CPSpecificationOptionId the primary key of the cp specification option
	* @return the cp specification option
	* @throws PortalException if a cp specification option with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPSpecificationOption getCPSpecificationOption(
		long CPSpecificationOptionId) throws PortalException;

	/**
	* Returns the cp specification option matching the UUID and group.
	*
	* @param uuid the cp specification option's UUID
	* @param groupId the primary key of the group
	* @return the matching cp specification option
	* @throws PortalException if a matching cp specification option could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPSpecificationOption getCPSpecificationOptionByUuidAndGroupId(
		String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the cp specification options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of cp specification options
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPSpecificationOption> getCPSpecificationOptions(int start,
		int end);

	/**
	* Returns all the cp specification options matching the UUID and company.
	*
	* @param uuid the UUID of the cp specification options
	* @param companyId the primary key of the company
	* @return the matching cp specification options, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPSpecificationOption> getCPSpecificationOptionsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	* Returns a range of cp specification options matching the UUID and company.
	*
	* @param uuid the UUID of the cp specification options
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp specification options, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPSpecificationOption> getCPSpecificationOptionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPSpecificationOption> orderByComparator);

	/**
	* Returns the number of cp specification options.
	*
	* @return the number of cp specification options
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPSpecificationOptionsCount();

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
	public BaseModelSearchResult<CPSpecificationOption> searchCPSpecificationOptions(
		long companyId, long groupId, String keywords, int start, int end,
		Sort sort) throws PortalException;

	public CPSpecificationOption updateCPOptionCategoryId(
		long cpSpecificationOptionId, long cpOptionCategoryId)
		throws PortalException;

	/**
	* Updates the cp specification option in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpSpecificationOption the cp specification option
	* @return the cp specification option that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPSpecificationOption updateCPSpecificationOption(
		CPSpecificationOption cpSpecificationOption);

	@Indexable(type = IndexableType.REINDEX)
	public CPSpecificationOption updateCPSpecificationOption(
		long cpSpecificationOptionId, long cpOptionCategoryId,
		Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
		boolean facetable, String key, ServiceContext serviceContext)
		throws PortalException;
}