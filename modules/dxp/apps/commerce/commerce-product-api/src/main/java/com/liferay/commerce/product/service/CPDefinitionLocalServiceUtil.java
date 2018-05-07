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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPDefinition. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPDefinitionLocalService
 * @see com.liferay.commerce.product.service.base.CPDefinitionLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp definition to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinition the cp definition
	* @return the cp definition that was added
	*/
	public static com.liferay.commerce.product.model.CPDefinition addCPDefinition(
		com.liferay.commerce.product.model.CPDefinition cpDefinition) {
		return getService().addCPDefinition(cpDefinition);
	}

	public static com.liferay.commerce.product.model.CPDefinition addCPDefinition(
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
		boolean hasDefaultInstance,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinition(nameMap, shortDescriptionMap,
			descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap,
			metaKeywordsMap, productTypeName, ignoreSKUCombinations, shippable,
			freeShipping, shipSeparately, shippingExtraPrice, width, height,
			depth, weight, cpTaxCategoryId, taxExempt, telcoOrElectronics,
			ddmStructureKey, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			hasDefaultInstance, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition addCPDefinition(
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
		return getService()
				   .addCPDefinition(nameMap, shortDescriptionMap,
			descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap,
			metaKeywordsMap, productTypeName, ignoreSKUCombinations, shippable,
			freeShipping, shipSeparately, shippingExtraPrice, width, height,
			depth, weight, cpTaxCategoryId, taxExempt, telcoOrElectronics,
			ddmStructureKey, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition addCPDefinition(
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
		int expirationDateMinute, boolean neverExpire, String defaultSku,
		String externalReferenceCode,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinition(nameMap, shortDescriptionMap,
			descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap,
			metaKeywordsMap, productTypeName, ignoreSKUCombinations, shippable,
			freeShipping, shipSeparately, shippingExtraPrice, width, height,
			depth, weight, cpTaxCategoryId, taxExempt, telcoOrElectronics,
			ddmStructureKey, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, defaultSku,
			externalReferenceCode, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition addCPDefinition(
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
		return getService()
				   .addCPDefinition(nameMap, shortDescriptionMap,
			descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap,
			metaKeywordsMap, productTypeName, ignoreSKUCombinations,
			ddmStructureKey, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static void checkCPDefinitions()
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkCPDefinitions();
	}

	/**
	* Creates a new cp definition with the primary key. Does not add the cp definition to the database.
	*
	* @param CPDefinitionId the primary key for the new cp definition
	* @return the new cp definition
	*/
	public static com.liferay.commerce.product.model.CPDefinition createCPDefinition(
		long CPDefinitionId) {
		return getService().createCPDefinition(CPDefinitionId);
	}

	public static void deleteAssetCategoryCPDefinition(long cpDefinitionId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteAssetCategoryCPDefinition(cpDefinitionId, categoryId);
	}

	/**
	* Deletes the cp definition from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinition the cp definition
	* @return the cp definition that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CPDefinition deleteCPDefinition(
		com.liferay.commerce.product.model.CPDefinition cpDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinition(cpDefinition);
	}

	/**
	* Deletes the cp definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionId the primary key of the cp definition
	* @return the cp definition that was removed
	* @throws PortalException if a cp definition with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinition deleteCPDefinition(
		long CPDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinition(CPDefinitionId);
	}

	public static void deleteCPDefinitions(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPDefinitions(groupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.commerce.product.model.CPDefinition fetchCPDefinition(
		long CPDefinitionId) {
		return getService().fetchCPDefinition(CPDefinitionId);
	}

	/**
	* Returns the cp definition matching the UUID and group.
	*
	* @param uuid the cp definition's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinition fetchCPDefinitionByUuidAndGroupId(
		String uuid, long groupId) {
		return getService().fetchCPDefinitionByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionLocalization fetchCPDefinitionLocalization(
		long CPDefinitionId, String languageId) {
		return getService()
				   .fetchCPDefinitionLocalization(CPDefinitionId, languageId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the cp definition with the primary key.
	*
	* @param CPDefinitionId the primary key of the cp definition
	* @return the cp definition
	* @throws PortalException if a cp definition with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinition getCPDefinition(
		long CPDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinition(CPDefinitionId);
	}

	/**
	* Returns the cp definition matching the UUID and group.
	*
	* @param uuid the cp definition's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition
	* @throws PortalException if a matching cp definition could not be found
	*/
	public static com.liferay.commerce.product.model.CPDefinition getCPDefinitionByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.Map<java.util.Locale, String> getCPDefinitionDescriptionMap(
		long cpDefinitionId) {
		return getService().getCPDefinitionDescriptionMap(cpDefinitionId);
	}

	public static com.liferay.commerce.product.model.CPDefinitionLocalization getCPDefinitionLocalization(
		long CPDefinitionId, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionLocalization(CPDefinitionId, languageId);
	}

	public static java.util.List<String> getCPDefinitionLocalizationLanguageIds(
		long cpDefinitionId) {
		return getService()
				   .getCPDefinitionLocalizationLanguageIds(cpDefinitionId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionLocalization> getCPDefinitionLocalizations(
		long CPDefinitionId) {
		return getService().getCPDefinitionLocalizations(CPDefinitionId);
	}

	public static java.util.Map<java.util.Locale, String> getCPDefinitionMetaDescriptionMap(
		long cpDefinitionId) {
		return getService().getCPDefinitionMetaDescriptionMap(cpDefinitionId);
	}

	public static java.util.Map<java.util.Locale, String> getCPDefinitionMetaKeywordsMap(
		long cpDefinitionId) {
		return getService().getCPDefinitionMetaKeywordsMap(cpDefinitionId);
	}

	public static java.util.Map<java.util.Locale, String> getCPDefinitionMetaTitleMap(
		long cpDefinitionId) {
		return getService().getCPDefinitionMetaTitleMap(cpDefinitionId);
	}

	public static java.util.Map<java.util.Locale, String> getCPDefinitionNameMap(
		long cpDefinitionId) {
		return getService().getCPDefinitionNameMap(cpDefinitionId);
	}

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
	public static java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitions(
		int start, int end) {
		return getService().getCPDefinitions(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitionsByCategoryId(
		long categoryId, int start, int end) {
		return getService().getCPDefinitionsByCategoryId(categoryId, start, end);
	}

	/**
	* Returns all the cp definitions matching the UUID and company.
	*
	* @param uuid the UUID of the cp definitions
	* @param companyId the primary key of the company
	* @return the matching cp definitions, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitionsByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService().getCPDefinitionsByUuidAndCompanyId(uuid, companyId);
	}

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
	public static java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinition> orderByComparator) {
		return getService()
				   .getCPDefinitionsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of cp definitions.
	*
	* @return the number of cp definitions
	*/
	public static int getCPDefinitionsCount() {
		return getService().getCPDefinitionsCount();
	}

	public static int getCPDefinitionsCountByCategoryId(long categoryId) {
		return getService().getCPDefinitionsCountByCategoryId(categoryId);
	}

	public static java.util.Map<java.util.Locale, String> getCPDefinitionShortDescriptionMap(
		long cpDefinitionId) {
		return getService().getCPDefinitionShortDescriptionMap(cpDefinitionId);
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry getDefaultImage(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDefaultImage(cpDefinitionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	public static String getLayoutUuid(long cpDefinitionId) {
		return getService().getLayoutUuid(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.Map<java.util.Locale, String> getUrlTitleMap(
		long cpDefinitionId) {
		return getService().getUrlTitleMap(cpDefinitionId);
	}

	public static String getUrlTitleMapAsXML(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUrlTitleMapAsXML(cpDefinitionId);
	}

	public static void moveCPDefinitionsToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().moveCPDefinitionsToTrash(groupId, userId);
	}

	/**
	* Moves the commerce product definition to the recycle bin.
	*
	* @param userId the primary key of the user moving the commerce product
	definition
	* @param cpDefinition the commerce product definition to be moved
	* @return the moved commerce product definition
	*/
	public static com.liferay.commerce.product.model.CPDefinition moveCPDefinitionToTrash(
		long userId,
		com.liferay.commerce.product.model.CPDefinition cpDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveCPDefinitionToTrash(userId, cpDefinition);
	}

	/**
	* Moves the commerce product definition with the ID to the recycle bin.
	*
	* @param userId the primary key of the user moving the commerce product
	definition
	* @param cpDefinitionId the primary key of the commerce product definition
	to be moved
	* @return the moved commerce product definition
	*/
	public static com.liferay.commerce.product.model.CPDefinition moveCPDefinitionToTrash(
		long userId, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveCPDefinitionToTrash(userId, cpDefinitionId);
	}

	/**
	* Restores the commerce product definition with the ID from the recycle
	* bin.
	*
	* @param userId the primary key of the user restoring the commerce product
	definition
	* @param cpDefinitionId the primary key of the commerce product definition
	to be restored
	* @return the restored commerce product definition from the recycle bin
	*/
	public static com.liferay.commerce.product.model.CPDefinition restoreCPDefinitionFromTrash(
		long userId, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().restoreCPDefinitionFromTrash(userId, cpDefinitionId);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinition> searchCPDefinitions(
		long companyId, long groupId, String keywords, int status, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPDefinitions(companyId, groupId, keywords, status,
			start, end, sort);
	}

	public static void updateAsset(long userId,
		com.liferay.commerce.product.model.CPDefinition cpDefinition,
		long[] assetCategoryIds, String[] assetTagNames,
		long[] assetLinkEntryIds, Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateAsset(userId, cpDefinition, assetCategoryIds, assetTagNames,
			assetLinkEntryIds, priority);
	}

	/**
	* Updates the cp definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinition the cp definition
	* @return the cp definition that was updated
	*/
	public static com.liferay.commerce.product.model.CPDefinition updateCPDefinition(
		com.liferay.commerce.product.model.CPDefinition cpDefinition) {
		return getService().updateCPDefinition(cpDefinition);
	}

	public static com.liferay.commerce.product.model.CPDefinition updateCPDefinition(
		long cpDefinitionId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> shortDescriptionMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.util.Map<java.util.Locale, String> urlTitleMap,
		java.util.Map<java.util.Locale, String> metaTitleMap,
		java.util.Map<java.util.Locale, String> metaDescriptionMap,
		java.util.Map<java.util.Locale, String> metaKeywordsMap,
		boolean ignoreSKUCombinations, boolean shippable, boolean freeShipping,
		boolean shipSeparately, double shippingExtraPrice, double width,
		double height, double depth, double weight, long cpTaxCategoryId,
		boolean taxExempt, boolean telcoOrElectronics, String ddmStructureKey,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinition(cpDefinitionId, nameMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaDescriptionMap, metaKeywordsMap, ignoreSKUCombinations,
			shippable, freeShipping, shipSeparately, shippingExtraPrice, width,
			height, depth, weight, cpTaxCategoryId, taxExempt,
			telcoOrElectronics, ddmStructureKey, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition updateCPDefinition(
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
		return getService()
				   .updateCPDefinition(cpDefinitionId, nameMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaDescriptionMap, metaKeywordsMap, ignoreSKUCombinations,
			ddmStructureKey, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition updateCPDefinitionCategorization(
		long cpDefinitionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionCategorization(cpDefinitionId,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition updateCPDefinitionIgnoreSKUCombinations(
		long cpDefinitionId, boolean ignoreSKUCombinations,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionIgnoreSKUCombinations(cpDefinitionId,
			ignoreSKUCombinations, serviceContext);
	}

	public static void updateCPDefinitionsByCPTaxCategoryId(
		long cpTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateCPDefinitionsByCPTaxCategoryId(cpTaxCategoryId);
	}

	public static com.liferay.commerce.product.model.CPDefinition updateShippingInfo(
		long cpDefinitionId, boolean shippable, boolean freeShipping,
		boolean shipSeparately, double shippingExtraPrice, double width,
		double height, double depth, double weight,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateShippingInfo(cpDefinitionId, shippable, freeShipping,
			shipSeparately, shippingExtraPrice, width, height, depth, weight,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition updateStatus(
		long userId, long cpDefinitionId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, cpDefinitionId, status,
			serviceContext, workflowContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition updateTaxCategoryInfo(
		long cpDefinitionId, long cpTaxCategoryId, boolean taxExempt,
		boolean telcoOrElectronics)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateTaxCategoryInfo(cpDefinitionId, cpTaxCategoryId,
			taxExempt, telcoOrElectronics);
	}

	public static CPDefinitionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionLocalService, CPDefinitionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionLocalService.class);

		ServiceTracker<CPDefinitionLocalService, CPDefinitionLocalService> serviceTracker =
			new ServiceTracker<CPDefinitionLocalService, CPDefinitionLocalService>(bundle.getBundleContext(),
				CPDefinitionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}