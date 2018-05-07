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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CPDefinition. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionServiceUtil
 * @see com.liferay.commerce.product.service.base.CPDefinitionServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPDefinition"}, service = CPDefinitionService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionServiceUtil} to access the cp definition remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPDefinition addCPDefinition(Map<Locale, String> nameMap,
		Map<Locale, String> shortDescriptionMap,
		Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
		Map<Locale, String> metaTitleMap,
		Map<Locale, String> metaDescriptionMap,
		Map<Locale, String> metaKeywordsMap, String productTypeName,
		boolean ignoreSKUCombinations, boolean shippable, boolean freeShipping,
		boolean shipSeparately, double shippingExtraPrice, double width,
		double height, double depth, double weight, long cpTaxCategoryId,
		boolean taxExempt, boolean telcoOrElectronics, String ddmStructureKey,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinition addCPDefinition(Map<Locale, String> nameMap,
		Map<Locale, String> shortDescriptionMap,
		Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
		Map<Locale, String> metaTitleMap,
		Map<Locale, String> metaDescriptionMap,
		Map<Locale, String> metaKeywordsMap, String productTypeName,
		boolean ignoreSKUCombinations, String ddmStructureKey,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public void deleteAssetCategoryCPDefinition(long cpDefinitionId,
		long categoryId) throws PortalException;

	public CPDefinition deleteCPDefinition(long cpDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinition fetchCPDefinition(long cpDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinition getCPDefinition(long cpDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinition> getCPDefinitions(long groupId,
		String productTypeName, String languageId, int status, int start,
		int end, OrderByComparator<CPDefinition> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinition> getCPDefinitionsByCategoryId(long categoryId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionsCount(long groupId, String productTypeName,
		String languageId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionsCountByCategoryId(long categoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPAttachmentFileEntry getDefaultImage(long cpDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getLayoutUuid(long cpDefinitionId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getUrlTitleMapAsXML(long cpDefinitionId)
		throws PortalException;

	public CPDefinition moveCPDefinitionToTrash(long cpDefinitionId)
		throws PortalException;

	public void restoreCPDefinitionFromTrash(long cpDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPDefinition> searchCPDefinitions(
		long companyId, long groupId, String keywords, int status, int start,
		int end, Sort sort) throws PortalException;

	public CPDefinition updateCPDefinition(long cpDefinitionId,
		Map<Locale, String> nameMap, Map<Locale, String> shortDescriptionMap,
		Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
		Map<Locale, String> metaTitleMap,
		Map<Locale, String> metaDescriptionMap,
		Map<Locale, String> metaKeywordsMap, boolean ignoreSKUCombinations,
		boolean shippable, boolean freeShipping, boolean shipSeparately,
		double shippingExtraPrice, double width, double height, double depth,
		double weight, long cpTaxCategoryId, boolean taxExempt,
		boolean telcoOrElectronics, String ddmStructureKey, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinition updateCPDefinition(long cpDefinitionId,
		Map<Locale, String> nameMap, Map<Locale, String> shortDescriptionMap,
		Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
		Map<Locale, String> metaTitleMap,
		Map<Locale, String> metaDescriptionMap,
		Map<Locale, String> metaKeywordsMap, boolean ignoreSKUCombinations,
		String ddmStructureKey, boolean published, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinition updateCPDefinitionCategorization(long cpDefinitionId,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinition updateCPDefinitionIgnoreSKUCombinations(
		long cpDefinitionId, boolean ignoreSKUCombinations,
		ServiceContext serviceContext) throws PortalException;

	public void updateCPDisplayLayout(long cpDefinitionId, String layoutUuid,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinition updateShippingInfo(long cpDefinitionId,
		boolean shippable, boolean freeShipping, boolean shipSeparately,
		double shippingExtraPrice, double width, double height, double depth,
		double weight, ServiceContext serviceContext) throws PortalException;

	public CPDefinition updateStatus(long userId, long cpDefinitionId,
		int status, ServiceContext serviceContext,
		Map<String, Serializable> workflowContext) throws PortalException;

	public CPDefinition updateTaxCategoryInfo(long cpDefinitionId,
		long cpTaxCategoryId, boolean taxExempt, boolean telcoOrElectronics)
		throws PortalException;
}