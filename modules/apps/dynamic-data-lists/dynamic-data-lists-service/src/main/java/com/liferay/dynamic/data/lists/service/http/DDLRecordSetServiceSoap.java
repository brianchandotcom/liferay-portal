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

package com.liferay.dynamic.data.lists.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.service.DDLRecordSetServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link DDLRecordSetServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.dynamic.data.lists.model.DDLRecordSetSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.dynamic.data.lists.model.DDLRecordSet}, that is translated to a
 * {@link com.liferay.dynamic.data.lists.model.DDLRecordSetSoap}. Methods that SOAP cannot
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
 * @see DDLRecordSetServiceHttp
 * @see com.liferay.dynamic.data.lists.model.DDLRecordSetSoap
 * @see DDLRecordSetServiceUtil
 * @generated
 */
@ProviderType
public class DDLRecordSetServiceSoap {
	public static com.liferay.dynamic.data.lists.model.DDLRecordSetSoap addRecordSet(
		long groupId, long ddmStructureId, java.lang.String recordSetKey,
		java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, int minDisplayRows, int scope,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.dynamic.data.lists.model.DDLRecordSet returnValue = DDLRecordSetServiceUtil.addRecordSet(groupId,
					ddmStructureId, recordSetKey, nameMap, descriptionMap,
					minDisplayRows, scope, serviceContext);

			return com.liferay.dynamic.data.lists.model.DDLRecordSetSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteRecordSet(long recordSetId)
		throws RemoteException {
		try {
			DDLRecordSetServiceUtil.deleteRecordSet(recordSetId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetSoap getRecordSet(
		long recordSetId) throws RemoteException {
		try {
			com.liferay.dynamic.data.lists.model.DDLRecordSet returnValue = DDLRecordSetServiceUtil.getRecordSet(recordSetId);

			return com.liferay.dynamic.data.lists.model.DDLRecordSetSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetSoap[] getRecordSets(
		long[] groupIds) throws RemoteException {
		try {
			java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> returnValue =
				DDLRecordSetServiceUtil.getRecordSets(groupIds);

			return com.liferay.dynamic.data.lists.model.DDLRecordSetSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetSoap[] search(
		long companyId, long groupId, java.lang.String keywords, int scope,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSet> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> returnValue =
				DDLRecordSetServiceUtil.search(companyId, groupId, keywords,
					scope, start, end, orderByComparator);

			return com.liferay.dynamic.data.lists.model.DDLRecordSetSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetSoap[] search(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int scope, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSet> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> returnValue =
				DDLRecordSetServiceUtil.search(companyId, groupId, name,
					description, scope, andOperator, start, end,
					orderByComparator);

			return com.liferay.dynamic.data.lists.model.DDLRecordSetSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords, int scope) throws RemoteException {
		try {
			int returnValue = DDLRecordSetServiceUtil.searchCount(companyId,
					groupId, keywords, scope);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String name, java.lang.String description, int scope,
		boolean andOperator) throws RemoteException {
		try {
			int returnValue = DDLRecordSetServiceUtil.searchCount(companyId,
					groupId, name, description, scope, andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetSoap updateMinDisplayRows(
		long recordSetId, int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.dynamic.data.lists.model.DDLRecordSet returnValue = DDLRecordSetServiceUtil.updateMinDisplayRows(recordSetId,
					minDisplayRows, serviceContext);

			return com.liferay.dynamic.data.lists.model.DDLRecordSetSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetSoap updateRecordSet(
		long recordSetId,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues)
		throws RemoteException {
		try {
			com.liferay.dynamic.data.lists.model.DDLRecordSet returnValue = DDLRecordSetServiceUtil.updateRecordSet(recordSetId,
					settingsDDMFormValues);

			return com.liferay.dynamic.data.lists.model.DDLRecordSetSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetSoap updateRecordSet(
		long recordSetId, long ddmStructureId,
		java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.dynamic.data.lists.model.DDLRecordSet returnValue = DDLRecordSetServiceUtil.updateRecordSet(recordSetId,
					ddmStructureId, nameMap, descriptionMap, minDisplayRows,
					serviceContext);

			return com.liferay.dynamic.data.lists.model.DDLRecordSetSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetSoap updateRecordSet(
		long groupId, long ddmStructureId, java.lang.String recordSetKey,
		java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.dynamic.data.lists.model.DDLRecordSet returnValue = DDLRecordSetServiceUtil.updateRecordSet(groupId,
					ddmStructureId, recordSetKey, nameMap, descriptionMap,
					minDisplayRows, serviceContext);

			return com.liferay.dynamic.data.lists.model.DDLRecordSetSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDLRecordSetServiceSoap.class);
}