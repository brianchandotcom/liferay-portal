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

import com.liferay.commerce.product.service.CommerceProductDefinitionOptionValueRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CommerceProductDefinitionOptionValueRelServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel}, that is translated to a
 * {@link com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap}. Methods that SOAP cannot
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
 * @see CommerceProductDefinitionOptionValueRelServiceHttp
 * @see com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap
 * @see CommerceProductDefinitionOptionValueRelServiceUtil
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelServiceSoap {
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap addCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionRelId,
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues, int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel returnValue =
				CommerceProductDefinitionOptionValueRelServiceUtil.addCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionRelId,
					titleMap, priority, serviceContext);

			return com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap deleteCommerceProductDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap commerceProductDefinitionOptionValueRel)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel returnValue =
				CommerceProductDefinitionOptionValueRelServiceUtil.deleteCommerceProductDefinitionOptionValueRel(com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl.toModel(
						commerceProductDefinitionOptionValueRel));

			return com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap deleteCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel returnValue =
				CommerceProductDefinitionOptionValueRelServiceUtil.deleteCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);

			return com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap fetchCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel returnValue =
				CommerceProductDefinitionOptionValueRelServiceUtil.fetchCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);

			return com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap getCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel returnValue =
				CommerceProductDefinitionOptionValueRelServiceUtil.getCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);

			return com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap[] getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> returnValue =
				CommerceProductDefinitionOptionValueRelServiceUtil.getCommerceProductDefinitionOptionValueRels(commerceProductDefinitionOptionRelId,
					start, end);

			return com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap[] getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> returnValue =
				CommerceProductDefinitionOptionValueRelServiceUtil.getCommerceProductDefinitionOptionValueRels(commerceProductDefinitionOptionRelId,
					start, end, orderByComparator);

			return com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceProductDefinitionOptionValueRelsCount(
		long commerceProductDefinitionOptionRelId) throws RemoteException {
		try {
			int returnValue = CommerceProductDefinitionOptionValueRelServiceUtil.getCommerceProductDefinitionOptionValueRelsCount(commerceProductDefinitionOptionRelId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap updateCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId,
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues, int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel returnValue =
				CommerceProductDefinitionOptionValueRelServiceUtil.updateCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId,
					titleMap, priority, serviceContext);

			return com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceProductDefinitionOptionValueRelServiceSoap.class);
}