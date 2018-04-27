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

import com.liferay.commerce.product.service.CPOptionValueServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CPOptionValueServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.model.CPOptionValueSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.model.CPOptionValue}, that is translated to a
 * {@link com.liferay.commerce.product.model.CPOptionValueSoap}. Methods that SOAP cannot
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
 * @see CPOptionValueServiceHttp
 * @see com.liferay.commerce.product.model.CPOptionValueSoap
 * @see CPOptionValueServiceUtil
 * @generated
 */
@ProviderType
public class CPOptionValueServiceSoap {
	public static com.liferay.commerce.product.model.CPOptionValueSoap addCPOptionValue(
		long cpOptionId, String[] titleMapLanguageIds, String[] titleMapValues,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.commerce.product.model.CPOptionValue returnValue = CPOptionValueServiceUtil.addCPOptionValue(cpOptionId,
					titleMap, priority, key, serviceContext);

			return com.liferay.commerce.product.model.CPOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap deleteCPOptionValue(
		com.liferay.commerce.product.model.CPOptionValueSoap cpOptionValue)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPOptionValue returnValue = CPOptionValueServiceUtil.deleteCPOptionValue(com.liferay.commerce.product.model.impl.CPOptionValueModelImpl.toModel(
						cpOptionValue));

			return com.liferay.commerce.product.model.CPOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap deleteCPOptionValue(
		long cpOptionValueId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPOptionValue returnValue = CPOptionValueServiceUtil.deleteCPOptionValue(cpOptionValueId);

			return com.liferay.commerce.product.model.CPOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap fetchCPOptionValue(
		long cpOptionValueId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPOptionValue returnValue = CPOptionValueServiceUtil.fetchCPOptionValue(cpOptionValueId);

			return com.liferay.commerce.product.model.CPOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap getCPOptionValue(
		long cpOptionValueId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPOptionValue returnValue = CPOptionValueServiceUtil.getCPOptionValue(cpOptionValueId);

			return com.liferay.commerce.product.model.CPOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap[] getCPOptionValues(
		long cpOptionId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPOptionValue> returnValue =
				CPOptionValueServiceUtil.getCPOptionValues(cpOptionId, start,
					end);

			return com.liferay.commerce.product.model.CPOptionValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap[] getCPOptionValues(
		long cpOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionValue> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPOptionValue> returnValue =
				CPOptionValueServiceUtil.getCPOptionValues(cpOptionId, start,
					end, orderByComparator);

			return com.liferay.commerce.product.model.CPOptionValueSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCPOptionValuesCount(long cpOptionId)
		throws RemoteException {
		try {
			int returnValue = CPOptionValueServiceUtil.getCPOptionValuesCount(cpOptionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValueSoap updateCPOptionValue(
		long cpOptionValueId, String[] titleMapLanguageIds,
		String[] titleMapValues, double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.commerce.product.model.CPOptionValue returnValue = CPOptionValueServiceUtil.updateCPOptionValue(cpOptionValueId,
					titleMap, priority, key, serviceContext);

			return com.liferay.commerce.product.model.CPOptionValueSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPOptionValueServiceSoap.class);
}