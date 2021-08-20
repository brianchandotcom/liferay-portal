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

package com.liferay.commerce.tax.service.http;

import com.liferay.commerce.tax.service.CommerceTaxMethodServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>CommerceTaxMethodServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.tax.model.CommerceTaxMethodSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.tax.model.CommerceTaxMethod</code>, that is translated to a
 * <code>com.liferay.commerce.tax.model.CommerceTaxMethodSoap</code>. Methods that SOAP
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
 * @see CommerceTaxMethodServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceTaxMethodServiceSoap {

	public static com.liferay.commerce.tax.model.CommerceTaxMethodSoap
			addCommerceTaxMethod(
				long groupId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String engineKey,
				boolean percentage, boolean active)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.commerce.tax.model.CommerceTaxMethod returnValue =
				CommerceTaxMethodServiceUtil.addCommerceTaxMethod(
					groupId, nameMap, descriptionMap, engineKey, percentage,
					active);

			return com.liferay.commerce.tax.model.CommerceTaxMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.tax.model.CommerceTaxMethodSoap
			addCommerceTaxMethod(
				String[] nameMapLanguageIds, String[] nameMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, String engineKey,
				boolean percentage, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.commerce.tax.model.CommerceTaxMethod returnValue =
				CommerceTaxMethodServiceUtil.addCommerceTaxMethod(
					nameMap, descriptionMap, engineKey, percentage, active,
					serviceContext);

			return com.liferay.commerce.tax.model.CommerceTaxMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.tax.model.CommerceTaxMethodSoap
			createCommerceTaxMethod(long groupId, long commerceTaxMethodId)
		throws RemoteException {

		try {
			com.liferay.commerce.tax.model.CommerceTaxMethod returnValue =
				CommerceTaxMethodServiceUtil.createCommerceTaxMethod(
					groupId, commerceTaxMethodId);

			return com.liferay.commerce.tax.model.CommerceTaxMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceTaxMethod(long commerceTaxMethodId)
		throws RemoteException {

		try {
			CommerceTaxMethodServiceUtil.deleteCommerceTaxMethod(
				commerceTaxMethodId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.tax.model.CommerceTaxMethodSoap
			fetchCommerceTaxMethod(long groupId, String engineKey)
		throws RemoteException {

		try {
			com.liferay.commerce.tax.model.CommerceTaxMethod returnValue =
				CommerceTaxMethodServiceUtil.fetchCommerceTaxMethod(
					groupId, engineKey);

			return com.liferay.commerce.tax.model.CommerceTaxMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.tax.model.CommerceTaxMethodSoap
			getCommerceTaxMethod(long commerceTaxMethodId)
		throws RemoteException {

		try {
			com.liferay.commerce.tax.model.CommerceTaxMethod returnValue =
				CommerceTaxMethodServiceUtil.getCommerceTaxMethod(
					commerceTaxMethodId);

			return com.liferay.commerce.tax.model.CommerceTaxMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.tax.model.CommerceTaxMethodSoap[]
			getCommerceTaxMethods(long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.tax.model.CommerceTaxMethod>
				returnValue =
					CommerceTaxMethodServiceUtil.getCommerceTaxMethods(groupId);

			return com.liferay.commerce.tax.model.CommerceTaxMethodSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.tax.model.CommerceTaxMethodSoap[]
			getCommerceTaxMethods(long groupId, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.tax.model.CommerceTaxMethod>
				returnValue =
					CommerceTaxMethodServiceUtil.getCommerceTaxMethods(
						groupId, active);

			return com.liferay.commerce.tax.model.CommerceTaxMethodSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.tax.model.CommerceTaxMethodSoap
			setActive(long commerceTaxMethodId, boolean active)
		throws RemoteException {

		try {
			com.liferay.commerce.tax.model.CommerceTaxMethod returnValue =
				CommerceTaxMethodServiceUtil.setActive(
					commerceTaxMethodId, active);

			return com.liferay.commerce.tax.model.CommerceTaxMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.tax.model.CommerceTaxMethodSoap
			updateCommerceTaxMethod(
				long commerceTaxMethodId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, boolean percentage,
				boolean active)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.commerce.tax.model.CommerceTaxMethod returnValue =
				CommerceTaxMethodServiceUtil.updateCommerceTaxMethod(
					commerceTaxMethodId, nameMap, descriptionMap, percentage,
					active);

			return com.liferay.commerce.tax.model.CommerceTaxMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceTaxMethodServiceSoap.class);

}