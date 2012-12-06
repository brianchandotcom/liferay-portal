/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.dynamicdatamapping.model.DDMStructure}, that is translated to a
 * {@link com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap}. Methods that SOAP cannot
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
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureServiceHttp
 * @see       com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap
 * @see       com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil
 * @generated
 */
public class DDMStructureServiceSoap {
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap addStructure(
		long userId, long groupId, long classNameId,
		java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.addStructure(userId, groupId,
					classNameId, nameMap, descriptionMap, xsd, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap addStructure(
		long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey, java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String xsd,
		java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.addStructure(groupId,
					parentStructureId, classNameId, structureKey, nameMap,
					descriptionMap, xsd, storageType, type, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap copyStructure(
		long structureId, java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.copyStructure(structureId, nameMap,
					descriptionMap, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteStructure(long structureId)
		throws RemoteException {
		try {
			DDMStructureServiceUtil.deleteStructure(structureId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap fetchStructure(
		long groupId, java.lang.String structureKey) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.fetchStructure(groupId, structureKey);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap getStructure(
		long structureId) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.getStructure(structureId);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap getStructure(
		long groupId, java.lang.String structureKey) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.getStructure(groupId, structureKey);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap getStructure(
		long groupId, java.lang.String structureKey,
		boolean includeGlobalStructures) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.getStructure(groupId, structureKey,
					includeGlobalStructures);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap[] getStructures(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> returnValue =
				DDMStructureServiceUtil.getStructures(groupId);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap[] getStructures(
		long[] groupIds) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> returnValue =
				DDMStructureServiceUtil.getStructures(groupIds);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap[] search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> returnValue =
				DDMStructureServiceUtil.search(companyId, groupIds,
					classNameIds, keywords, start, end, orderByComparator);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap[] search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> returnValue =
				DDMStructureServiceUtil.search(companyId, groupIds,
					classNameIds, name, description, storageType, type,
					andOperator, start, end, orderByComparator);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String keywords)
		throws RemoteException {
		try {
			int returnValue = DDMStructureServiceUtil.searchCount(companyId,
					groupIds, classNameIds, keywords);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String name,
		java.lang.String description, java.lang.String storageType, int type,
		boolean andOperator) throws RemoteException {
		try {
			int returnValue = DDMStructureServiceUtil.searchCount(companyId,
					groupIds, classNameIds, name, description, storageType,
					type, andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap updateStructure(
		long structureId, long parentStructureId,
		java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.updateStructure(structureId,
					parentStructureId, nameMap, descriptionMap, xsd,
					serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap updateStructure(
		long groupId, long parentStructureId, java.lang.String structureKey,
		java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.updateStructure(groupId,
					parentStructureId, structureKey, nameMap, descriptionMap,
					xsd, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStructureServiceSoap.class);
}