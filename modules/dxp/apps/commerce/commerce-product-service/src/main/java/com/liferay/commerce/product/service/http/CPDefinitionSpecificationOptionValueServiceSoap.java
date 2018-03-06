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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CPDefinitionSpecificationOptionValueServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue}, that is translated to a
 * {@link com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * @see CPDefinitionSpecificationOptionValueServiceHttp
 * @see com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap
 * @see CPDefinitionSpecificationOptionValueServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionSpecificationOptionValueServiceSoap {
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap addCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId,
		long cpOptionCategoryId, java.lang.String[] valueMapLanguageIds,
		java.lang.String[] valueMapValues, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> valueMap = LocalizationUtil.getLocalizationMap(valueMapLanguageIds,
					valueMapValues);

			com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue returnValue =
				CPDefinitionSpecificationOptionValueServiceUtil.addCPDefinitionSpecificationOptionValue(cpDefinitionId,
					cpSpecificationOptionId, cpOptionCategoryId, valueMap,
					priority, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId) throws RemoteException {
		try {
			CPDefinitionSpecificationOptionValueServiceUtil.deleteCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap fetchCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue returnValue =
				CPDefinitionSpecificationOptionValueServiceUtil.fetchCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId);

			return com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap fetchCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue returnValue =
				CPDefinitionSpecificationOptionValueServiceUtil.fetchCPDefinitionSpecificationOptionValue(cpDefinitionId,
					cpSpecificationOptionId);

			return com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap getCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue returnValue =
				CPDefinitionSpecificationOptionValueServiceUtil.getCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId);

			return com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap[] getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> returnValue =
				CPDefinitionSpecificationOptionValueServiceUtil.getCPDefinitionSpecificationOptionValues(cpDefinitionId);

			return com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap[] getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId, long cpOptionCategoryId) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> returnValue =
				CPDefinitionSpecificationOptionValueServiceUtil.getCPDefinitionSpecificationOptionValues(cpDefinitionId,
					cpOptionCategoryId);

			return com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap updateCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId, long cpOptionCategoryId,
		java.lang.String[] valueMapLanguageIds,
		java.lang.String[] valueMapValues, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> valueMap = LocalizationUtil.getLocalizationMap(valueMapLanguageIds,
					valueMapValues);

			com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue returnValue =
				CPDefinitionSpecificationOptionValueServiceUtil.updateCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId,
					cpOptionCategoryId, valueMap, priority, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionSpecificationOptionValueServiceSoap.class);
}