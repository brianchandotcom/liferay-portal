/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionService}.
 *
 * @author Marco Leo
 * @see CPDefinitionService
 * @generated
 */
@ProviderType
public class CPDefinitionServiceWrapper implements CPDefinitionService,
	ServiceWrapper<CPDefinitionService> {
	public CPDefinitionServiceWrapper(CPDefinitionService cpDefinitionService) {
		_cpDefinitionService = cpDefinitionService;
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition addCPDefinition(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> shortDescriptionMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.util.Map<java.util.Locale, String> urlTitleMap,
		java.util.Map<java.util.Locale, String> metaTitleMap,
		java.util.Map<java.util.Locale, String> metaDescriptionMap,
		java.util.Map<java.util.Locale, String> metaKeywordsMap,
		String productTypeName, boolean ignoreSKUCombinations,
		boolean shippable, boolean freeShipping, boolean shipSeparately,
		double shippingExtraPrice, double width, double height, double depth,
		double weight, long cpTaxCategoryId, boolean taxExempt,
		boolean telcoOrElectronics, String ddmStructureKey, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.addCPDefinition(nameMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaDescriptionMap, metaKeywordsMap, productTypeName,
			ignoreSKUCombinations, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, cpTaxCategoryId,
			taxExempt, telcoOrElectronics, ddmStructureKey, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition addCPDefinition(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> shortDescriptionMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.util.Map<java.util.Locale, String> urlTitleMap,
		java.util.Map<java.util.Locale, String> metaTitleMap,
		java.util.Map<java.util.Locale, String> metaDescriptionMap,
		java.util.Map<java.util.Locale, String> metaKeywordsMap,
		String productTypeName, boolean ignoreSKUCombinations,
		String ddmStructureKey, boolean published, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.addCPDefinition(nameMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaDescriptionMap, metaKeywordsMap, productTypeName,
			ignoreSKUCombinations, ddmStructureKey, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public void deleteAssetCategoryCPDefinition(long cpDefinitionId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionService.deleteAssetCategoryCPDefinition(cpDefinitionId,
			categoryId);
	}

	@Override
	public void deleteCPDefinition(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionService.deleteCPDefinition(cpDefinitionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition fetchCPDefinition(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.fetchCPDefinition(cpDefinitionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.getCPDefinition(cpDefinitionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitions(
		long groupId, String productTypeName, String languageId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinition> orderByComparator) {
		return _cpDefinitionService.getCPDefinitions(groupId, productTypeName,
			languageId, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitionsByCategoryId(
		long categoryId, int start, int end) {
		return _cpDefinitionService.getCPDefinitionsByCategoryId(categoryId,
			start, end);
	}

	@Override
	public int getCPDefinitionsCount(long groupId, String productTypeName,
		String languageId, int status) {
		return _cpDefinitionService.getCPDefinitionsCount(groupId,
			productTypeName, languageId, status);
	}

	@Override
	public int getCPDefinitionsCountByCategoryId(long categoryId) {
		return _cpDefinitionService.getCPDefinitionsCountByCategoryId(categoryId);
	}

	@Override
	public String getLayoutUuid(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.getLayoutUuid(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionService.getOSGiServiceIdentifier();
	}

	@Override
	public String getUrlTitleMapAsXML(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.getUrlTitleMapAsXML(cpDefinitionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition moveCPDefinitionToTrash(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.moveCPDefinitionToTrash(cpDefinitionId);
	}

	@Override
	public void restoreCPDefinitionFromTrash(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionService.restoreCPDefinitionFromTrash(cpDefinitionId);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinition> searchCPDefinitions(
		long companyId, long groupId, String keywords, int status, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.searchCPDefinitions(companyId, groupId,
			keywords, status, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinition> searchCPDefinitions(
		long companyId, long groupId, String keywords, String filterFields,
		String filterValues, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.searchCPDefinitions(companyId, groupId,
			keywords, filterFields, filterValues, start, end, sort);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition updateCPDefinition(
		long cpDefinitionId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> shortDescriptionMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.util.Map<java.util.Locale, String> urlTitleMap,
		java.util.Map<java.util.Locale, String> metaTitleMap,
		java.util.Map<java.util.Locale, String> metaDescriptionMap,
		java.util.Map<java.util.Locale, String> metaKeywordsMap,
		boolean ignoreSKUCombinations, String ddmStructureKey,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.updateCPDefinition(cpDefinitionId, nameMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaDescriptionMap, metaKeywordsMap, ignoreSKUCombinations,
			ddmStructureKey, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition updateCPDefinitionCategorization(
		long cpDefinitionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.updateCPDefinitionCategorization(cpDefinitionId,
			serviceContext);
	}

	@Override
	public void updateCPDisplayLayout(long cpDefinitionId, String layoutUuid,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionService.updateCPDisplayLayout(cpDefinitionId, layoutUuid,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition updateShippingInfo(
		long cpDefinitionId, boolean shippable, boolean freeShipping,
		boolean shipSeparately, double shippingExtraPrice, double width,
		double height, double depth, double weight,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.updateShippingInfo(cpDefinitionId,
			shippable, freeShipping, shipSeparately, shippingExtraPrice, width,
			height, depth, weight, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition updateTaxCategoryInfo(
		long cpDefinitionId, long cpTaxCategoryId, boolean taxExempt,
		boolean telcoOrElectronics)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.updateTaxCategoryInfo(cpDefinitionId,
			cpTaxCategoryId, taxExempt, telcoOrElectronics);
	}

	@Override
	public CPDefinitionService getWrappedService() {
		return _cpDefinitionService;
	}

	@Override
	public void setWrappedService(CPDefinitionService cpDefinitionService) {
		_cpDefinitionService = cpDefinitionService;
	}

	private CPDefinitionService _cpDefinitionService;
}