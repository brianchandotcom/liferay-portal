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

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLocalization;

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
 * Provides the local service interface for CPDefinition. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPDefinitionLocalServiceUtil
 * @see com.liferay.commerce.product.service.base.CPDefinitionLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionLocalServiceUtil} to access the cp definition local service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cp definition to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinition the cp definition
	* @return the cp definition that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinition addCPDefinition(CPDefinition cpDefinition);

	@Indexable(type = IndexableType.REINDEX)
	public CPDefinition addCPDefinition(
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> shortDescriptionMap,
		Map<Locale, java.lang.String> descriptionMap,
		Map<Locale, java.lang.String> urlTitleMap,
		Map<Locale, java.lang.String> metaTitleMap,
		Map<Locale, java.lang.String> metaKeywordsMap,
		Map<Locale, java.lang.String> metaDescriptionMap,
		java.lang.String productTypeName, boolean ignoreSKUCombinations,
		boolean shippable, boolean freeShipping, boolean shipSeparately,
		double shippingExtraPrice, double width, double height, double depth,
		double weight, java.lang.String ddmStructureKey, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		boolean hasDefaultInstance, ServiceContext serviceContext)
		throws PortalException;

	public CPDefinition addCPDefinition(
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> shortDescriptionMap,
		Map<Locale, java.lang.String> descriptionMap,
		Map<Locale, java.lang.String> urlTitleMap,
		Map<Locale, java.lang.String> metaTitleMap,
		Map<Locale, java.lang.String> metaKeywordsMap,
		Map<Locale, java.lang.String> metaDescriptionMap,
		java.lang.String productTypeName, boolean ignoreSKUCombinations,
		boolean shippable, boolean freeShipping, boolean shipSeparately,
		double shippingExtraPrice, double width, double height, double depth,
		double weight, java.lang.String ddmStructureKey, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinition addCPDefinition(
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> shortDescriptionMap,
		Map<Locale, java.lang.String> descriptionMap,
		Map<Locale, java.lang.String> urlTitleMap,
		Map<Locale, java.lang.String> metaTitleMap,
		Map<Locale, java.lang.String> metaKeywordsMap,
		Map<Locale, java.lang.String> metaDescriptionMap,
		java.lang.String productTypeName, boolean ignoreSKUCombinations,
		java.lang.String ddmStructureKey, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public void checkCPDefinitions() throws PortalException;

	/**
	* Creates a new cp definition with the primary key. Does not add the cp definition to the database.
	*
	* @param CPDefinitionId the primary key for the new cp definition
	* @return the new cp definition
	*/
	public CPDefinition createCPDefinition(long CPDefinitionId);

	public void deleteAssetCategoryCPDefinition(long cpDefinitionId,
		long categoryId) throws PortalException;

	/**
	* Deletes the cp definition from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinition the cp definition
	* @return the cp definition that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinition deleteCPDefinition(CPDefinition cpDefinition)
		throws PortalException;

	/**
	* Deletes the cp definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionId the primary key of the cp definition
	* @return the cp definition that was removed
	* @throws PortalException if a cp definition with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDefinition deleteCPDefinition(long CPDefinitionId)
		throws PortalException;

	public void deleteCPDefinitions(long groupId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CPDefinition fetchCPDefinition(long CPDefinitionId);

	/**
	* Returns the cp definition matching the UUID and group.
	*
	* @param uuid the cp definition's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinition fetchCPDefinitionByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionLocalization fetchCPDefinitionLocalization(
		long CPDefinitionId, java.lang.String languageId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the cp definition with the primary key.
	*
	* @param CPDefinitionId the primary key of the cp definition
	* @return the cp definition
	* @throws PortalException if a cp definition with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinition getCPDefinition(long CPDefinitionId)
		throws PortalException;

	/**
	* Returns the cp definition matching the UUID and group.
	*
	* @param uuid the cp definition's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition
	* @throws PortalException if a matching cp definition could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinition getCPDefinitionByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Locale, java.lang.String> getCPDefinitionDescriptionMap(
		long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionLocalization getCPDefinitionLocalization(
		long CPDefinitionId, java.lang.String languageId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.String> getCPDefinitionLocalizationLanguageIds(
		long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionLocalization> getCPDefinitionLocalizations(
		long CPDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Locale, java.lang.String> getCPDefinitionMetaDescriptionMap(
		long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Locale, java.lang.String> getCPDefinitionMetaKeywordsMap(
		long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Locale, java.lang.String> getCPDefinitionMetaTitleMap(
		long cpDefinitionId);

	/**
	* Returns a range of all the cp definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of cp definitions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinition> getCPDefinitions(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinition> getCPDefinitionsByCategoryId(long categoryId,
		int start, int end);

	/**
	* Returns all the cp definitions matching the UUID and company.
	*
	* @param uuid the UUID of the cp definitions
	* @param companyId the primary key of the company
	* @return the matching cp definitions, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinition> getCPDefinitionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of cp definitions matching the UUID and company.
	*
	* @param uuid the UUID of the cp definitions
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definitions, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinition> getCPDefinitionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator);

	/**
	* Returns the number of cp definitions.
	*
	* @return the number of cp definitions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionsCountByCategoryId(long categoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Locale, java.lang.String> getCPDefinitionShortDescriptionMap(
		long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Locale, java.lang.String> getCPDefinitionTitleMap(
		long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPAttachmentFileEntry getDefaultImage(long cpDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getLayoutUuid(long cpDefinitionId);

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
	public Map<Locale, java.lang.String> getUrlTitleMap(long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getUrlTitleMapAsXML(long cpDefinitionId)
		throws PortalException;

	public void moveCPDefinitionsToTrash(long groupId, long userId)
		throws PortalException;

	/**
	* Moves the commerce product definition to the recycle bin.
	*
	* @param userId the primary key of the user moving the commerce product definition
	* @param cpDefinition the commerce product definition to be moved
	* @return the moved commerce product definition
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinition moveCPDefinitionToTrash(long userId,
		CPDefinition cpDefinition) throws PortalException;

	/**
	* Moves the commerce product definition with the ID to the recycle bin.
	*
	* @param userId the primary key of the user moving the commerce product definition
	* @param cpDefinitionId the primary key of the commerce product definition to be moved
	* @return the moved commerce product definition
	*/
	public CPDefinition moveCPDefinitionToTrash(long userId, long cpDefinitionId)
		throws PortalException;

	/**
	* Restores the commerce product definition with the ID from the recycle bin.
	*
	* @param userId the primary key of the user restoring the commerce product definition
	* @param cpDefinitionId the primary key of the commerce product definition to be restored
	* @return the restored commerce product definition from the recycle bin
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinition restoreCPDefinitionFromTrash(long userId,
		long cpDefinitionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPDefinition> searchCPDefinitions(
		long companyId, long groupId, java.lang.String keywords, int status,
		int start, int end, Sort sort) throws PortalException;

	public void updateAsset(long userId, CPDefinition cpDefinition,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws PortalException;

	/**
	* Updates the cp definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinition the cp definition
	* @return the cp definition that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinition updateCPDefinition(CPDefinition cpDefinition);

	@Indexable(type = IndexableType.REINDEX)
	public CPDefinition updateCPDefinition(long cpDefinitionId,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> shortDescriptionMap,
		Map<Locale, java.lang.String> descriptionMap,
		Map<Locale, java.lang.String> urlTitleMap,
		Map<Locale, java.lang.String> metaTitleMap,
		Map<Locale, java.lang.String> metaKeywordsMap,
		Map<Locale, java.lang.String> metaDescriptionMap,
		boolean ignoreSKUCombinations, boolean shippable, boolean freeShipping,
		boolean shipSeparately, double shippingExtraPrice, double width,
		double height, double depth, double weight,
		java.lang.String ddmStructureKey, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinition updateCPDefinition(long cpDefinitionId,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> shortDescriptionMap,
		Map<Locale, java.lang.String> descriptionMap,
		Map<Locale, java.lang.String> urlTitleMap,
		Map<Locale, java.lang.String> metaTitleMap,
		Map<Locale, java.lang.String> metaKeywordsMap,
		Map<Locale, java.lang.String> metaDescriptionMap,
		boolean ignoreSKUCombinations, java.lang.String ddmStructureKey,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CPDefinition updateCPDefinitionCategorization(long cpDefinitionId,
		ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CPDefinition updateCPDefinitionIgnoreSKUCombinations(
		long cpDefinitionId, boolean ignoreSKUCombinations)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CPDefinition updateShippingInfo(long cpDefinitionId,
		boolean shippable, boolean freeShipping, boolean shipSeparately,
		double shippingExtraPrice, double width, double height, double depth,
		double weight, ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CPDefinition updateStatus(long userId, long cpDefinitionId,
		int status, ServiceContext serviceContext,
		Map<java.lang.String, Serializable> workflowContext)
		throws PortalException;
}