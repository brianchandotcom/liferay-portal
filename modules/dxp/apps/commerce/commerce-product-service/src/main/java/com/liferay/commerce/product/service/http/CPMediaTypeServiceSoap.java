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

import com.liferay.commerce.product.service.CPMediaTypeServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CPMediaTypeServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.model.CPMediaTypeSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.model.CPMediaType}, that is translated to a
 * {@link com.liferay.commerce.product.model.CPMediaTypeSoap}. Methods that SOAP cannot
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
 * @see CPMediaTypeServiceHttp
 * @see com.liferay.commerce.product.model.CPMediaTypeSoap
 * @see CPMediaTypeServiceUtil
 * @generated
 */
@ProviderType
public class CPMediaTypeServiceSoap {
	public static com.liferay.commerce.product.model.CPMediaTypeSoap addCPMediaType(
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.commerce.product.model.CPMediaType returnValue = CPMediaTypeServiceUtil.addCPMediaType(titleMap,
					descriptionMap, priority, serviceContext);

			return com.liferay.commerce.product.model.CPMediaTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPMediaTypeSoap deleteCPMediaType(
		com.liferay.commerce.product.model.CPMediaTypeSoap cpMediaType)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPMediaType returnValue = CPMediaTypeServiceUtil.deleteCPMediaType(com.liferay.commerce.product.model.impl.CPMediaTypeModelImpl.toModel(
						cpMediaType));

			return com.liferay.commerce.product.model.CPMediaTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPMediaTypeSoap deleteCPMediaType(
		long cpMediaTypeId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPMediaType returnValue = CPMediaTypeServiceUtil.deleteCPMediaType(cpMediaTypeId);

			return com.liferay.commerce.product.model.CPMediaTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPMediaTypeSoap fetchCPMediaType(
		long cpMediaTypeId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPMediaType returnValue = CPMediaTypeServiceUtil.fetchCPMediaType(cpMediaTypeId);

			return com.liferay.commerce.product.model.CPMediaTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPMediaTypeSoap getCPMediaType(
		long cpMediaTypeId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPMediaType returnValue = CPMediaTypeServiceUtil.getCPMediaType(cpMediaTypeId);

			return com.liferay.commerce.product.model.CPMediaTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPMediaTypeSoap[] getCPMediaTypes(
		long groupId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPMediaType> returnValue =
				CPMediaTypeServiceUtil.getCPMediaTypes(groupId, start, end);

			return com.liferay.commerce.product.model.CPMediaTypeSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPMediaTypeSoap[] getCPMediaTypes(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPMediaType> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPMediaType> returnValue =
				CPMediaTypeServiceUtil.getCPMediaTypes(groupId, start, end,
					orderByComparator);

			return com.liferay.commerce.product.model.CPMediaTypeSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCPMediaTypesCount(long groupId)
		throws RemoteException {
		try {
			int returnValue = CPMediaTypeServiceUtil.getCPMediaTypesCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPMediaTypeSoap updateCPMediaType(
		long cpMediaTypeId, java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.commerce.product.model.CPMediaType returnValue = CPMediaTypeServiceUtil.updateCPMediaType(cpMediaTypeId,
					titleMap, descriptionMap, priority, serviceContext);

			return com.liferay.commerce.product.model.CPMediaTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPMediaTypeServiceSoap.class);
}