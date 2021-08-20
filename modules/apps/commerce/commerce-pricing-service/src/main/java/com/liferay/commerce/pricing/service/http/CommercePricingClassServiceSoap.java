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

package com.liferay.commerce.pricing.service.http;

import com.liferay.commerce.pricing.service.CommercePricingClassServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>CommercePricingClassServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.pricing.model.CommercePricingClassSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.pricing.model.CommercePricingClass</code>, that is translated to a
 * <code>com.liferay.commerce.pricing.model.CommercePricingClassSoap</code>. Methods that SOAP
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
 * @author Riccardo Alberti
 * @see CommercePricingClassServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePricingClassServiceSoap {

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			addCommercePricingClass(
				String externalReferenceCode, String[] titleMapLanguageIds,
				String[] titleMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.addCommercePricingClass(
						externalReferenceCode, titleMap, descriptionMap,
						serviceContext);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			addOrUpdateCommercePricingClass(
				String externalReferenceCode, long commercePricingClassId,
				String[] titleMapLanguageIds, String[] titleMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.
						addOrUpdateCommercePricingClass(
							externalReferenceCode, commercePricingClassId,
							titleMap, descriptionMap, serviceContext);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			deleteCommercePricingClass(long commercePricingClassId)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.deleteCommercePricingClass(
						commercePricingClassId);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.
						fetchByExternalReferenceCode(
							externalReferenceCode, companyId);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			fetchCommercePricingClass(long commercePricingClassId)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.fetchCommercePricingClass(
						commercePricingClassId);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			getCommercePricingClass(long commercePricingClassId)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.getCommercePricingClass(
						commercePricingClassId);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePricingClassCountByCPDefinitionId(
			long cpDefinitionId, String title)
		throws RemoteException {

		try {
			int returnValue =
				CommercePricingClassServiceUtil.
					getCommercePricingClassCountByCPDefinitionId(
						cpDefinitionId, title);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap[]
			getCommercePricingClasses(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.pricing.model.CommercePricingClass>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.CommercePricingClass>
					returnValue =
						CommercePricingClassServiceUtil.
							getCommercePricingClasses(
								companyId, start, end, orderByComparator);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePricingClassesCount(long companyId)
		throws RemoteException {

		try {
			int returnValue =
				CommercePricingClassServiceUtil.getCommercePricingClassesCount(
					companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePricingClassesCount(
			long cpDefinitionId, String title)
		throws RemoteException {

		try {
			int returnValue =
				CommercePricingClassServiceUtil.getCommercePricingClassesCount(
					cpDefinitionId, title);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap[]
			searchByCPDefinitionId(
				long cpDefinitionId, String title, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.pricing.model.CommercePricingClass>
					returnValue =
						CommercePricingClassServiceUtil.searchByCPDefinitionId(
							cpDefinitionId, title, start, end);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			updateCommercePricingClass(
				long commercePricingClassId, String[] titleMapLanguageIds,
				String[] titleMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.updateCommercePricingClass(
						commercePricingClassId, titleMap, descriptionMap,
						serviceContext);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClassSoap
			updateCommercePricingClassExternalReferenceCode(
				String externalReferenceCode, long commercePricingClassId)
		throws RemoteException {

		try {
			com.liferay.commerce.pricing.model.CommercePricingClass
				returnValue =
					CommercePricingClassServiceUtil.
						updateCommercePricingClassExternalReferenceCode(
							externalReferenceCode, commercePricingClassId);

			return com.liferay.commerce.pricing.model.CommercePricingClassSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePricingClassServiceSoap.class);

}