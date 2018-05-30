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

import com.liferay.commerce.product.service.CPDefinitionOptionRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CPDefinitionOptionRelServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.model.CPDefinitionOptionRelSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.model.CPDefinitionOptionRel}, that is translated to a
 * {@link com.liferay.commerce.product.model.CPDefinitionOptionRelSoap}. Methods that SOAP cannot
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
 * @see CPDefinitionOptionRelServiceHttp
 * @see com.liferay.commerce.product.model.CPDefinitionOptionRelSoap
 * @see CPDefinitionOptionRelServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelServiceSoap {
	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId, boolean importOptionValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionOptionRel returnValue =
				CPDefinitionOptionRelServiceUtil.addCPDefinitionOptionRel(cpDefinitionId,
					cpOptionId, importOptionValue, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId, String[] nameMapLanguageIds,
		String[] nameMapValues, String[] descriptionMapLanguageIds,
		String[] descriptionMapValues, String ddmFormFieldTypeName,
		double priority, boolean facetable, boolean required,
		boolean skuContributor, boolean importOptionValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.commerce.product.model.CPDefinitionOptionRel returnValue =
				CPDefinitionOptionRelServiceUtil.addCPDefinitionOptionRel(cpDefinitionId,
					cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
					priority, facetable, required, skuContributor,
					importOptionValue, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionOptionRel returnValue =
				CPDefinitionOptionRelServiceUtil.addCPDefinitionOptionRel(cpDefinitionId,
					cpOptionId, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap deleteCPDefinitionOptionRel(
		com.liferay.commerce.product.model.CPDefinitionOptionRelSoap cpDefinitionOptionRel)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionOptionRel returnValue =
				CPDefinitionOptionRelServiceUtil.deleteCPDefinitionOptionRel(com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl.toModel(
						cpDefinitionOptionRel));

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap deleteCPDefinitionOptionRel(
		long cpDefinitionOptionRelId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionOptionRel returnValue =
				CPDefinitionOptionRelServiceUtil.deleteCPDefinitionOptionRel(cpDefinitionOptionRelId);

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap fetchCPDefinitionOptionRel(
		long cpDefinitionOptionRelId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionOptionRel returnValue =
				CPDefinitionOptionRelServiceUtil.fetchCPDefinitionOptionRel(cpDefinitionOptionRelId);

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap getCPDefinitionOptionRel(
		long cpDefinitionOptionRelId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinitionOptionRel returnValue =
				CPDefinitionOptionRelServiceUtil.getCPDefinitionOptionRel(cpDefinitionOptionRelId);

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap[] getCPDefinitionOptionRels(
		long cpDefinitionId, boolean skuContributor) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> returnValue =
				CPDefinitionOptionRelServiceUtil.getCPDefinitionOptionRels(cpDefinitionId,
					skuContributor);

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap[] getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> returnValue =
				CPDefinitionOptionRelServiceUtil.getCPDefinitionOptionRels(cpDefinitionId,
					start, end);

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap[] getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionRel> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> returnValue =
				CPDefinitionOptionRelServiceUtil.getCPDefinitionOptionRels(cpDefinitionId,
					start, end, orderByComparator);

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCPDefinitionOptionRelsCount(long cpDefinitionId)
		throws RemoteException {
		try {
			int returnValue = CPDefinitionOptionRelServiceUtil.getCPDefinitionOptionRelsCount(cpDefinitionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCPDefinitionOptionRelsCount(long cpDefinitionId,
		boolean skuContributor) throws RemoteException {
		try {
			int returnValue = CPDefinitionOptionRelServiceUtil.getCPDefinitionOptionRelsCount(cpDefinitionId,
					skuContributor);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRelSoap updateCPDefinitionOptionRel(
		long cpDefinitionOptionRelId, long cpOptionId,
		String[] nameMapLanguageIds, String[] nameMapValues,
		String[] descriptionMapLanguageIds, String[] descriptionMapValues,
		String ddmFormFieldTypeName, double priority, boolean facetable,
		boolean required, boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.commerce.product.model.CPDefinitionOptionRel returnValue =
				CPDefinitionOptionRelServiceUtil.updateCPDefinitionOptionRel(cpDefinitionOptionRelId,
					cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
					priority, facetable, required, skuContributor,
					serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionOptionRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionOptionRelServiceSoap.class);
}