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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.document.library.kernel.service.DLFileEntryTypeServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>DLFileEntryTypeServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.document.library.kernel.model.DLFileEntryTypeSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.document.library.kernel.model.DLFileEntryType</code>, that is translated to a
 * <code>com.liferay.document.library.kernel.model.DLFileEntryTypeSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
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
 * @see DLFileEntryTypeServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DLFileEntryTypeServiceSoap {

	public static com.liferay.document.library.kernel.model.DLFileEntryTypeSoap
			addFileEntryType(
				long groupId, long dataDefinitionId, String fileEntryTypeKey,
				String[] nameMapLanguageIds, String[] nameMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.document.library.kernel.model.DLFileEntryType
				returnValue = DLFileEntryTypeServiceUtil.addFileEntryType(
					groupId, dataDefinitionId, fileEntryTypeKey, nameMap,
					descriptionMap, serviceContext);

			return com.liferay.document.library.kernel.model.
				DLFileEntryTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addFileEntryType(long, String, Map, Map, long,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.document.library.kernel.model.DLFileEntryTypeSoap
			addFileEntryType(
				long groupId, String fileEntryTypeKey,
				String[] nameMapLanguageIds, String[] nameMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, long[] ddmStructureIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.document.library.kernel.model.DLFileEntryType
				returnValue = DLFileEntryTypeServiceUtil.addFileEntryType(
					groupId, fileEntryTypeKey, nameMap, descriptionMap,
					ddmStructureIds, serviceContext);

			return com.liferay.document.library.kernel.model.
				DLFileEntryTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addFileEntryType(long, String, Map, Map, long,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.document.library.kernel.model.DLFileEntryTypeSoap
			addFileEntryType(
				long groupId, String name, String description,
				long[] ddmStructureIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileEntryType
				returnValue = DLFileEntryTypeServiceUtil.addFileEntryType(
					groupId, name, description, ddmStructureIds,
					serviceContext);

			return com.liferay.document.library.kernel.model.
				DLFileEntryTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteFileEntryType(long fileEntryTypeId)
		throws RemoteException {

		try {
			DLFileEntryTypeServiceUtil.deleteFileEntryType(fileEntryTypeId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntryTypeSoap
			getFileEntryType(long fileEntryTypeId)
		throws RemoteException {

		try {
			com.liferay.document.library.kernel.model.DLFileEntryType
				returnValue = DLFileEntryTypeServiceUtil.getFileEntryType(
					fileEntryTypeId);

			return com.liferay.document.library.kernel.model.
				DLFileEntryTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.document.library.kernel.model.DLFileEntryTypeSoap[]
				getFileEntryTypes(long[] groupIds)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntryType>
					returnValue = DLFileEntryTypeServiceUtil.getFileEntryTypes(
						groupIds);

			return com.liferay.document.library.kernel.model.
				DLFileEntryTypeSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.document.library.kernel.model.DLFileEntryTypeSoap[]
				getFileEntryTypes(long[] groupIds, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntryType>
					returnValue = DLFileEntryTypeServiceUtil.getFileEntryTypes(
						groupIds, start, end);

			return com.liferay.document.library.kernel.model.
				DLFileEntryTypeSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getFileEntryTypesCount(long[] groupIds)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryTypeServiceUtil.getFileEntryTypesCount(
				groupIds);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.document.library.kernel.model.DLFileEntryTypeSoap[]
				getFolderFileEntryTypes(
					long[] groupIds, long folderId, boolean inherited)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntryType>
					returnValue =
						DLFileEntryTypeServiceUtil.getFolderFileEntryTypes(
							groupIds, folderId, inherited);

			return com.liferay.document.library.kernel.model.
				DLFileEntryTypeSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.document.library.kernel.model.DLFileEntryTypeSoap[] search(
				long companyId, long folderId, long[] groupIds, String keywords,
				boolean includeBasicFileEntryType, boolean inherited, int start,
				int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntryType>
					returnValue = DLFileEntryTypeServiceUtil.search(
						companyId, folderId, groupIds, keywords,
						includeBasicFileEntryType, inherited, start, end);

			return com.liferay.document.library.kernel.model.
				DLFileEntryTypeSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.document.library.kernel.model.DLFileEntryTypeSoap[] search(
				long companyId, long[] groupIds, String keywords,
				boolean includeBasicFileEntryType, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntryType>
						orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntryType>
					returnValue = DLFileEntryTypeServiceUtil.search(
						companyId, groupIds, keywords,
						includeBasicFileEntryType, start, end,
						orderByComparator);

			return com.liferay.document.library.kernel.model.
				DLFileEntryTypeSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int searchCount(
			long companyId, long folderId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType, boolean inherited)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryTypeServiceUtil.searchCount(
				companyId, folderId, groupIds, keywords,
				includeBasicFileEntryType, inherited);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int searchCount(
			long companyId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType)
		throws RemoteException {

		try {
			int returnValue = DLFileEntryTypeServiceUtil.searchCount(
				companyId, groupIds, keywords, includeBasicFileEntryType);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntryTypeSoap
			updateFileEntryType(
				long fileEntryTypeId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.document.library.kernel.model.DLFileEntryType
				returnValue = DLFileEntryTypeServiceUtil.updateFileEntryType(
					fileEntryTypeId, nameMap, descriptionMap);

			return com.liferay.document.library.kernel.model.
				DLFileEntryTypeSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFileEntryType(long, Map, Map)}
	 */
	@Deprecated
	public static void updateFileEntryType(
			long fileEntryTypeId, String[] nameMapLanguageIds,
			String[] nameMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, long[] ddmStructureIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			DLFileEntryTypeServiceUtil.updateFileEntryType(
				fileEntryTypeId, nameMap, descriptionMap, ddmStructureIds,
				serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFileEntryType(long, Map, Map)}
	 */
	@Deprecated
	public static void updateFileEntryType(
			long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			DLFileEntryTypeServiceUtil.updateFileEntryType(
				fileEntryTypeId, name, description, ddmStructureIds,
				serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFileEntryTypeServiceSoap.class);

}