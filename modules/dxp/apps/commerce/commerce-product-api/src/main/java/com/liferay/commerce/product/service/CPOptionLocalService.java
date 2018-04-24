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

import com.liferay.commerce.product.model.CPOption;

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
 * Provides the local service interface for CPOption. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPOptionLocalServiceUtil
 * @see com.liferay.commerce.product.service.base.CPOptionLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPOptionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPOptionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPOptionLocalServiceUtil} to access the cp option local service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPOptionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cp option to the database. Also notifies the appropriate model listeners.
	*
	* @param cpOption the cp option
	* @return the cp option that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPOption addCPOption(CPOption cpOption);

	@Indexable(type = IndexableType.REINDEX)
	public CPOption addCPOption(Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, boolean facetable,
		boolean required, boolean skuContributor, java.lang.String key,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new cp option with the primary key. Does not add the cp option to the database.
	*
	* @param CPOptionId the primary key for the new cp option
	* @return the new cp option
	*/
	@Transactional(enabled = false)
	public CPOption createCPOption(long CPOptionId);

	/**
	* Deletes the cp option from the database. Also notifies the appropriate model listeners.
	*
	* @param cpOption the cp option
	* @return the cp option that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPOption deleteCPOption(CPOption cpOption) throws PortalException;

	/**
	* Deletes the cp option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPOptionId the primary key of the cp option
	* @return the cp option that was removed
	* @throws PortalException if a cp option with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPOption deleteCPOption(long CPOptionId) throws PortalException;

	public void deleteCPOptions(long groupId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CPOption fetchCPOption(long CPOptionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOption fetchCPOption(long groupId, java.lang.String key)
		throws PortalException;

	/**
	* Returns the cp option matching the UUID and group.
	*
	* @param uuid the cp option's UUID
	* @param groupId the primary key of the group
	* @return the matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOption fetchCPOptionByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the cp option with the primary key.
	*
	* @param CPOptionId the primary key of the cp option
	* @return the cp option
	* @throws PortalException if a cp option with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOption getCPOption(long CPOptionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOption getCPOption(long groupId, java.lang.String key)
		throws PortalException;

	/**
	* Returns the cp option matching the UUID and group.
	*
	* @param uuid the cp option's UUID
	* @param groupId the primary key of the group
	* @return the matching cp option
	* @throws PortalException if a matching cp option could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPOption getCPOptionByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	/**
	* Returns a range of all the cp options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of cp options
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOption> getCPOptions(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOption> getCPOptions(long groupId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOption> getCPOptions(long groupId, int start, int end,
		OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns all the cp options matching the UUID and company.
	*
	* @param uuid the UUID of the cp options
	* @param companyId the primary key of the company
	* @return the matching cp options, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOption> getCPOptionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of cp options matching the UUID and company.
	*
	* @param uuid the UUID of the cp options
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp options, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPOption> getCPOptionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPOption> orderByComparator);

	/**
	* Returns the number of cp options.
	*
	* @return the number of cp options
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPOptionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPOptionsCount(long groupId);

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPOption> searchCPOptions(long companyId,
		long groupId, java.lang.String keywords, int start, int end, Sort sort)
		throws PortalException;

	public CPOption setFacetable(long cpOptionId, boolean facetable)
		throws PortalException;

	public CPOption setRequired(long cpOptionId, boolean required)
		throws PortalException;

	public CPOption setSkuContributor(long cpOptionId, boolean skuContributor)
		throws PortalException;

	/**
	* Updates the cp option in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpOption the cp option
	* @return the cp option that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPOption updateCPOption(CPOption cpOption);

	@Indexable(type = IndexableType.REINDEX)
	public CPOption updateCPOption(long cpOptionId,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, boolean facetable,
		boolean required, boolean skuContributor, java.lang.String key,
		ServiceContext serviceContext) throws PortalException;
}