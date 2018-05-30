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

package com.liferay.commerce.shipping.engine.fixed.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CommerceShippingFixedOptionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption}, that is translated to a
 * {@link com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap}. Methods that SOAP cannot
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
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionServiceHttp
 * @see com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap
 * @see CommerceShippingFixedOptionServiceUtil
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionServiceSoap {
	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap addCommerceShippingFixedOption(
		long commerceShippingMethodId, String[] nameMapLanguageIds,
		String[] nameMapValues, String[] descriptionMapLanguageIds,
		String[] descriptionMapValues, java.math.BigDecimal amount,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption returnValue =
				CommerceShippingFixedOptionServiceUtil.addCommerceShippingFixedOption(commerceShippingMethodId,
					nameMap, descriptionMap, amount, priority, serviceContext);

			return com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceShippingFixedOption(
		long commerceShippingFixedOptionId) throws RemoteException {
		try {
			CommerceShippingFixedOptionServiceUtil.deleteCommerceShippingFixedOption(commerceShippingFixedOptionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap fetchCommerceShippingFixedOption(
		long commerceShippingFixedOptionId) throws RemoteException {
		try {
			com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption returnValue =
				CommerceShippingFixedOptionServiceUtil.fetchCommerceShippingFixedOption(commerceShippingFixedOptionId);

			return com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap[] getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> returnValue =
				CommerceShippingFixedOptionServiceUtil.getCommerceShippingFixedOptions(commerceShippingMethodId,
					start, end);

			return com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap[] getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> returnValue =
				CommerceShippingFixedOptionServiceUtil.getCommerceShippingFixedOptions(commerceShippingMethodId,
					start, end, orderByComparator);

			return com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceShippingFixedOptionsCount(
		long commerceShippingMethodId) throws RemoteException {
		try {
			int returnValue = CommerceShippingFixedOptionServiceUtil.getCommerceShippingFixedOptionsCount(commerceShippingMethodId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap updateCommerceShippingFixedOption(
		long commerceShippingFixedOptionId, String[] nameMapLanguageIds,
		String[] nameMapValues, String[] descriptionMapLanguageIds,
		String[] descriptionMapValues, java.math.BigDecimal amount,
		double priority) throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption returnValue =
				CommerceShippingFixedOptionServiceUtil.updateCommerceShippingFixedOption(commerceShippingFixedOptionId,
					nameMap, descriptionMap, amount, priority);

			return com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceShippingFixedOptionServiceSoap.class);
}