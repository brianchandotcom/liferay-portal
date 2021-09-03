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

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CPDefinitionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>CPDefinitionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.product.model.CPDefinitionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.product.model.CPDefinition</code>, that is translated to a
 * <code>com.liferay.commerce.product.model.CPDefinitionSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CPDefinitionServiceSoap {

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			copyCPDefinition(long cpDefinitionId, long groupId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.copyCPDefinition(
					cpDefinitionId, groupId);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteAssetCategoryCPDefinition(
			long cpDefinitionId, long categoryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			CPDefinitionServiceUtil.deleteAssetCategoryCPDefinition(
				cpDefinitionId, categoryId, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCPDefinition(long cpDefinitionId)
		throws RemoteException {

		try {
			CPDefinitionServiceUtil.deleteCPDefinition(cpDefinitionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			fetchCPDefinition(long cpDefinitionId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.fetchCPDefinition(cpDefinitionId);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			fetchCPDefinitionByCProductExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.
					fetchCPDefinitionByCProductExternalReferenceCode(
						externalReferenceCode, companyId);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			fetchCPDefinitionByCProductId(long cProductId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.fetchCPDefinitionByCProductId(
					cProductId);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			getCPDefinition(long cpDefinitionId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.getCPDefinition(cpDefinitionId);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap[]
			getCPDefinitions(
				long groupId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CPDefinition>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinition>
				returnValue = CPDefinitionServiceUtil.getCPDefinitions(
					groupId, status, start, end, orderByComparator);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCPDefinitionsCount(long groupId, int status)
		throws RemoteException {

		try {
			int returnValue = CPDefinitionServiceUtil.getCPDefinitionsCount(
				groupId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntrySoap
			getDefaultImageCPAttachmentFileEntry(long cpDefinitionId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPAttachmentFileEntry
				returnValue =
					CPDefinitionServiceUtil.
						getDefaultImageCPAttachmentFileEntry(cpDefinitionId);

			return com.liferay.commerce.product.model.CPAttachmentFileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public static String getLayoutUuid(long cpDefinitionId)
		throws RemoteException {

		try {
			String returnValue = CPDefinitionServiceUtil.getLayoutUuid(
				cpDefinitionId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static String getUrlTitleMapAsXML(long cpDefinitionId)
		throws RemoteException {

		try {
			String returnValue = CPDefinitionServiceUtil.getUrlTitleMapAsXML(
				cpDefinitionId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			updateCPDefinition(
				long cpDefinitionId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] shortDescriptionMapLanguageIds,
				String[] shortDescriptionMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String[] urlTitleMapLanguageIds,
				String[] urlTitleMapValues, String[] metaTitleMapLanguageIds,
				String[] metaTitleMapValues,
				String[] metaDescriptionMapLanguageIds,
				String[] metaDescriptionMapValues,
				String[] metaKeywordsMapLanguageIds,
				String[] metaKeywordsMapValues, boolean ignoreSKUCombinations,
				String ddmStructureKey, boolean published, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> shortDescriptionMap =
				LocalizationUtil.getLocalizationMap(
					shortDescriptionMapLanguageIds, shortDescriptionMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);
			Map<Locale, String> urlTitleMap =
				LocalizationUtil.getLocalizationMap(
					urlTitleMapLanguageIds, urlTitleMapValues);
			Map<Locale, String> metaTitleMap =
				LocalizationUtil.getLocalizationMap(
					metaTitleMapLanguageIds, metaTitleMapValues);
			Map<Locale, String> metaDescriptionMap =
				LocalizationUtil.getLocalizationMap(
					metaDescriptionMapLanguageIds, metaDescriptionMapValues);
			Map<Locale, String> metaKeywordsMap =
				LocalizationUtil.getLocalizationMap(
					metaKeywordsMapLanguageIds, metaKeywordsMapValues);

			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.updateCPDefinition(
					cpDefinitionId, nameMap, shortDescriptionMap,
					descriptionMap, urlTitleMap, metaTitleMap,
					metaDescriptionMap, metaKeywordsMap, ignoreSKUCombinations,
					ddmStructureKey, published, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			updateCPDefinitionAccountGroupFilter(
				long cpDefinitionId, boolean enable)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.updateCPDefinitionAccountGroupFilter(
					cpDefinitionId, enable);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			updateCPDefinitionCategorization(
				long cpDefinitionId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.updateCPDefinitionCategorization(
					cpDefinitionId, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			updateCPDefinitionChannelFilter(long cpDefinitionId, boolean enable)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.updateCPDefinitionChannelFilter(
					cpDefinitionId, enable);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public static void updateCPDisplayLayout(
			long cpDefinitionId, String layoutUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			CPDefinitionServiceUtil.updateCPDisplayLayout(
				cpDefinitionId, layoutUuid, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			updateExternalReferenceCode(
				String externalReferenceCode, long cpDefinitionId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.updateExternalReferenceCode(
					externalReferenceCode, cpDefinitionId);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			updateShippingInfo(
				long cpDefinitionId, boolean shippable, boolean freeShipping,
				boolean shipSeparately, double shippingExtraPrice, double width,
				double height, double depth, double weight,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.updateShippingInfo(
					cpDefinitionId, shippable, freeShipping, shipSeparately,
					shippingExtraPrice, width, height, depth, weight,
					serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap
			updateTaxCategoryInfo(
				long cpDefinitionId, long cpTaxCategoryId, boolean taxExempt,
				boolean telcoOrElectronics)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinition returnValue =
				CPDefinitionServiceUtil.updateTaxCategoryInfo(
					cpDefinitionId, cpTaxCategoryId, taxExempt,
					telcoOrElectronics);

			return com.liferay.commerce.product.model.CPDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionServiceSoap.class);

}