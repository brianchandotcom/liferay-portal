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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceAvailabilityRangeServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CommerceAvailabilityRangeServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommerceAvailabilityRangeSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommerceAvailabilityRange}, that is translated to a
 * {@link com.liferay.commerce.model.CommerceAvailabilityRangeSoap}. Methods that SOAP cannot
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
 * @see CommerceAvailabilityRangeServiceHttp
 * @see com.liferay.commerce.model.CommerceAvailabilityRangeSoap
 * @see CommerceAvailabilityRangeServiceUtil
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeServiceSoap {
	public static com.liferay.commerce.model.CommerceAvailabilityRangeSoap addCommerceAvailabilityRange(
		String[] titleMapLanguageIds, String[] titleMapValues, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.commerce.model.CommerceAvailabilityRange returnValue = CommerceAvailabilityRangeServiceUtil.addCommerceAvailabilityRange(titleMap,
					priority, serviceContext);

			return com.liferay.commerce.model.CommerceAvailabilityRangeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceAvailabilityRange(
		long commerceAvailabilityRangeId) throws RemoteException {
		try {
			CommerceAvailabilityRangeServiceUtil.deleteCommerceAvailabilityRange(commerceAvailabilityRangeId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAvailabilityRangeSoap getCommerceAvailabilityRange(
		long commerceAvailabilityRangeId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceAvailabilityRange returnValue = CommerceAvailabilityRangeServiceUtil.getCommerceAvailabilityRange(commerceAvailabilityRangeId);

			return com.liferay.commerce.model.CommerceAvailabilityRangeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAvailabilityRangeSoap[] getCommerceAvailabilityRanges(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityRange> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> returnValue =
				CommerceAvailabilityRangeServiceUtil.getCommerceAvailabilityRanges(groupId,
					start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceAvailabilityRangeSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceAvailabilityRangesCount(long groupId)
		throws RemoteException {
		try {
			int returnValue = CommerceAvailabilityRangeServiceUtil.getCommerceAvailabilityRangesCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAvailabilityRangeSoap updateCommerceAvailabilityRange(
		long commerceAvailabilityRangeId, String[] titleMapLanguageIds,
		String[] titleMapValues, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.commerce.model.CommerceAvailabilityRange returnValue = CommerceAvailabilityRangeServiceUtil.updateCommerceAvailabilityRange(commerceAvailabilityRangeId,
					titleMap, priority, serviceContext);

			return com.liferay.commerce.model.CommerceAvailabilityRangeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceAvailabilityRangeServiceSoap.class);
}