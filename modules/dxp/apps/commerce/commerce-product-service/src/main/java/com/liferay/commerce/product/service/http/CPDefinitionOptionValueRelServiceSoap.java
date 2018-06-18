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

import com.liferay.commerce.product.service.CPDefinitionOptionValueRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CPDefinitionOptionValueRelServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.model.CPDefinitionOptionValueRel}, that is translated to a
 * {@link com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap}. Methods that SOAP cannot
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
 * @see CPDefinitionOptionValueRelServiceHttp
 * @see com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap
 * @see CPDefinitionOptionValueRelServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionOptionValueRelServiceSoap {
	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap addCPDefinitionOptionValueRel(
		long cpDefinitionOptionRelId, String[] nameMapLanguageIds,
		String[] nameMapValues, double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);

			com.liferay.commerce.product.model.CPDefinitionOptionValueRel returnValue =
				CPDefinitionOptionValueRelServiceUtil.addCPDefinitionOptionValueRel(cpDefinitionOptionRelId,
					nameMap, priority, key, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId) throws RemoteException {
		try {
			CPDefinitionOptionValueRelServiceUtil.deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap fetchCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionOptionValueRel returnValue =
				CPDefinitionOptionValueRelServiceUtil.fetchCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);

			return com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap getCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionOptionValueRel returnValue =
				CPDefinitionOptionValueRelServiceUtil.getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);

			return com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap[] getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> returnValue =
				CPDefinitionOptionValueRelServiceUtil.getCPDefinitionOptionValueRels(cpDefinitionOptionRelId,
					start, end);

			return com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap[] getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> returnValue =
				CPDefinitionOptionValueRelServiceUtil.getCPDefinitionOptionValueRels(cpDefinitionOptionRelId,
					start, end, orderByComparator);

			return com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap[] getCPDefinitionOptionValueRels(
		long groupId, String key, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> returnValue =
				CPDefinitionOptionValueRelServiceUtil.getCPDefinitionOptionValueRels(groupId,
					key, start, end);

			return com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCPDefinitionOptionValueRelsCount(
		long cpDefinitionOptionRelId) throws RemoteException {
		try {
			int returnValue = CPDefinitionOptionValueRelServiceUtil.getCPDefinitionOptionValueRelsCount(cpDefinitionOptionRelId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap updateCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId, String[] nameMapLanguageIds,
		String[] nameMapValues, double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);

			com.liferay.commerce.product.model.CPDefinitionOptionValueRel returnValue =
				CPDefinitionOptionValueRelServiceUtil.updateCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId,
					nameMap, priority, key, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionOptionValueRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionOptionValueRelServiceSoap.class);
}