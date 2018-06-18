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

package com.liferay.commerce.product.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPSpecificationOptionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CPSpecificationOptionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.model.CPSpecificationOptionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.model.CPSpecificationOption}, that is translated to a
 * {@link com.liferay.commerce.product.model.CPSpecificationOptionSoap}. Methods that SOAP cannot
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
 * @see CPSpecificationOptionServiceHttp
 * @see com.liferay.commerce.product.model.CPSpecificationOptionSoap
 * @see CPSpecificationOptionServiceUtil
 * @generated
 */
@ProviderType
public class CPSpecificationOptionServiceSoap {
	public static com.liferay.commerce.product.model.CPSpecificationOptionSoap addCPSpecificationOption(
		long cpOptionCategoryId, String[] titleMapLanguageIds,
		String[] titleMapValues, String[] descriptionMapLanguageIds,
		String[] descriptionMapValues, boolean facetable, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.commerce.product.model.CPSpecificationOption returnValue =
				CPSpecificationOptionServiceUtil.addCPSpecificationOption(cpOptionCategoryId,
					titleMap, descriptionMap, facetable, key, serviceContext);

			return com.liferay.commerce.product.model.CPSpecificationOptionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCPSpecificationOption(long cpSpecificationOptionId)
		throws RemoteException {
		try {
			CPSpecificationOptionServiceUtil.deleteCPSpecificationOption(cpSpecificationOptionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPSpecificationOptionSoap fetchCPSpecificationOption(
		long cpSpecificationOptionId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPSpecificationOption returnValue =
				CPSpecificationOptionServiceUtil.fetchCPSpecificationOption(cpSpecificationOptionId);

			return com.liferay.commerce.product.model.CPSpecificationOptionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPSpecificationOptionSoap getCPSpecificationOption(
		long cpSpecificationOptionId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPSpecificationOption returnValue =
				CPSpecificationOptionServiceUtil.getCPSpecificationOption(cpSpecificationOptionId);

			return com.liferay.commerce.product.model.CPSpecificationOptionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPSpecificationOptionSoap[] getCPSpecificationOptions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPSpecificationOption> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPSpecificationOption> returnValue =
				CPSpecificationOptionServiceUtil.getCPSpecificationOptions(groupId,
					start, end, orderByComparator);

			return com.liferay.commerce.product.model.CPSpecificationOptionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCPSpecificationOptionsCount(long groupId)
		throws RemoteException {
		try {
			int returnValue = CPSpecificationOptionServiceUtil.getCPSpecificationOptionsCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPSpecificationOptionSoap updateCPSpecificationOption(
		long cpSpecificationOptionId, long cpOptionCategoryId,
		String[] titleMapLanguageIds, String[] titleMapValues,
		String[] descriptionMapLanguageIds, String[] descriptionMapValues,
		boolean facetable, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.commerce.product.model.CPSpecificationOption returnValue =
				CPSpecificationOptionServiceUtil.updateCPSpecificationOption(cpSpecificationOptionId,
					cpOptionCategoryId, titleMap, descriptionMap, facetable,
					key, serviceContext);

			return com.liferay.commerce.product.model.CPSpecificationOptionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPSpecificationOptionServiceSoap.class);
}