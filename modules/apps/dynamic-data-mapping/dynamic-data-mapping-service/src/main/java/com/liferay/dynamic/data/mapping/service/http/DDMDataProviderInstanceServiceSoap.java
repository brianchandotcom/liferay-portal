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

package com.liferay.dynamic.data.mapping.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link DDMDataProviderInstanceServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance}, that is translated to a
 * {@link com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap}. Methods that SOAP cannot
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
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceServiceHttp
 * @see com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap
 * @see DDMDataProviderInstanceServiceUtil
 * @generated
 */
@ProviderType
public class DDMDataProviderInstanceServiceSoap {
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap addDataProviderInstance(
		long groupId, java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance returnValue =
				DDMDataProviderInstanceServiceUtil.addDataProviderInstance(groupId,
					nameMap, descriptionMap, ddmFormValues, type, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteDataProviderInstance(long dataProviderInstanceId)
		throws RemoteException {
		try {
			DDMDataProviderInstanceServiceUtil.deleteDataProviderInstance(dataProviderInstanceId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap fetchDataProviderInstance(
		long dataProviderInstanceId) throws RemoteException {
		try {
			com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance returnValue =
				DDMDataProviderInstanceServiceUtil.fetchDataProviderInstance(dataProviderInstanceId);

			return com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap getDataProviderInstance(
		long dataProviderInstanceId) throws RemoteException {
		try {
			com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance returnValue =
				DDMDataProviderInstanceServiceUtil.getDataProviderInstance(dataProviderInstanceId);

			return com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap[] search(
		long companyId, long[] groupIds, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> returnValue =
				DDMDataProviderInstanceServiceUtil.search(companyId, groupIds,
					keywords, start, end, orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap[] search(
		long companyId, long[] groupIds, java.lang.String name,
		java.lang.String description, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> returnValue =
				DDMDataProviderInstanceServiceUtil.search(companyId, groupIds,
					name, description, andOperator, start, end,
					orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords) throws RemoteException {
		try {
			int returnValue = DDMDataProviderInstanceServiceUtil.searchCount(companyId,
					groupIds, keywords);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String name, java.lang.String description, boolean andOperator)
		throws RemoteException {
		try {
			int returnValue = DDMDataProviderInstanceServiceUtil.searchCount(companyId,
					groupIds, name, description, andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap updateDataProviderInstance(
		long dataProviderInstanceId, java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance returnValue =
				DDMDataProviderInstanceServiceUtil.updateDataProviderInstance(dataProviderInstanceId,
					nameMap, descriptionMap, ddmFormValues, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMDataProviderInstanceServiceSoap.class);
}