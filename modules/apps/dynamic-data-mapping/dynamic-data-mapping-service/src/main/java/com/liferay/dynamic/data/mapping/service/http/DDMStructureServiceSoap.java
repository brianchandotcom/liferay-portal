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

import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>DDMStructureServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.dynamic.data.mapping.model.DDMStructureSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.dynamic.data.mapping.model.DDMStructure</code>, that is translated to a
 * <code>com.liferay.dynamic.data.mapping.model.DDMStructureSoap</code>. Methods that SOAP
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
 * @see DDMStructureServiceHttp
 * @generated
 */
public class DDMStructureServiceSoap {

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			addStructure(
				long groupId, long parentStructureId, long classNameId,
				String structureKey, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				String storageType, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.addStructure(
					groupId, parentStructureId, classNameId, structureKey,
					nameMap, descriptionMap, ddmForm, ddmFormLayout,
					storageType, type, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			addStructure(
				long groupId, long classNameId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				String storageType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.addStructure(
					groupId, classNameId, nameMap, descriptionMap, ddmForm,
					ddmFormLayout, storageType, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			addStructure(
				long groupId, String parentStructureKey, long classNameId,
				String structureKey, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				String storageType, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.addStructure(
					groupId, parentStructureKey, classNameId, structureKey,
					nameMap, descriptionMap, ddmForm, ddmFormLayout,
					storageType, type, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Copies a structure, creating a new structure with all the values
	 * extracted from the original one. The new structure supports a new name
	 * and description.
	 *
	 * @param structureId the primary key of the structure to be copied
	 * @param nameMap the new structure's locales and localized names
	 * @param descriptionMap the new structure's locales and localized
	 descriptions
	 * @param serviceContext the service context to be applied. Can set the
	 UUID, creation date, modification date, guest permissions, and
	 group permissions for the structure.
	 * @return the new structure
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			copyStructure(
				long structureId, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.copyStructure(
					structureId, nameMap, descriptionMap, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			copyStructure(
				long structureId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.copyStructure(
					structureId, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Deletes the structure and its resources.
	 *
	 * <p>
	 * Before deleting the structure, the system verifies whether the structure
	 * is required by another entity. If it is needed, an exception is thrown.
	 * </p>
	 *
	 * @param structureId the primary key of the structure to be deleted
	 */
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

	/**
	 * Returns the structure matching the class name ID, structure key, and
	 * group.
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param structureKey the unique string identifying the structure
	 * @return the matching structure, or <code>null</code> if a matching
	 structure could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			fetchStructure(long groupId, long classNameId, String structureKey)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.fetchStructure(
					groupId, classNameId, structureKey);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			fetchStructure(
				long groupId, long classNameId, String structureKey,
				boolean includeAncestorStructures)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.fetchStructure(
					groupId, classNameId, structureKey,
					includeAncestorStructures);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the structure with the ID.
	 *
	 * @param structureId the primary key of the structure
	 * @return the structure with the ID
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			getStructure(long structureId)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.getStructure(structureId);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the structure matching the class name ID, structure key, and
	 * group.
	 *
	 * @param groupId the primary key of the structure's group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param structureKey the unique string identifying the structure
	 * @return the matching structure
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			getStructure(long groupId, long classNameId, String structureKey)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.getStructure(
					groupId, classNameId, structureKey);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the structure matching the class name ID, structure key, and
	 * group, optionally searching ancestor sites (that have sharing enabled)
	 * and global scoped sites.
	 *
	 * <p>
	 * This method first searches in the group. If the structure is still not
	 * found and <code>includeAncestorStructures</code> is set to
	 * <code>true</code>, this method searches the group's ancestor sites (that
	 * have sharing enabled) and lastly searches global scoped sites.
	 * </p>
	 *
	 * @param groupId the primary key of the structure's group
	 * @param classNameId the primary key of the class name for the structure's
	 related model
	 * @param structureKey the unique string identifying the structure
	 * @param includeAncestorStructures whether to include ancestor sites (that
	 have sharing enabled) and include global scoped sites in the
	 search
	 * @return the matching structure
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			getStructure(
				long groupId, long classNameId, String structureKey,
				boolean includeAncestorStructures)
		throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.getStructure(
					groupId, classNameId, structureKey,
					includeAncestorStructures);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap[]
			getStructures(
				long companyId, long[] groupIds, long classNameId, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
				returnValue = DDMStructureServiceUtil.getStructures(
					companyId, groupIds, classNameId, status);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap[]
			getStructures(
				long companyId, long[] groupIds, long classNameId, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
				returnValue = DDMStructureServiceUtil.getStructures(
					companyId, groupIds, classNameId, status, start, end,
					orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap[]
			getStructures(
				long companyId, long[] groupIds, long classNameId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
				returnValue = DDMStructureServiceUtil.getStructures(
					companyId, groupIds, classNameId, start, end,
					orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap[]
			getStructures(
				long companyId, long[] groupIds, long classNameId,
				String keywords, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
				returnValue = DDMStructureServiceUtil.getStructures(
					companyId, groupIds, classNameId, keywords, status, start,
					end, orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getStructuresCount(
			long companyId, long[] groupIds, long classNameId)
		throws RemoteException {

		try {
			int returnValue = DDMStructureServiceUtil.getStructuresCount(
				companyId, groupIds, classNameId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getStructuresCount(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int status)
		throws RemoteException {

		try {
			int returnValue = DDMStructureServiceUtil.getStructuresCount(
				companyId, groupIds, classNameId, keywords, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void revertStructure(
			long structureId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			DDMStructureServiceUtil.revertStructure(
				structureId, version, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the structures matching the groups and
	 * class name IDs, and matching the keywords in the structure names and
	 * descriptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the structure's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name of the model the
	 structure is related to
	 * @param keywords the keywords (space separated), which may occur in the
	 structure's name or description (optionally <code>null</code>)
	 * @param type the structure's type. For more information, see {@link
	 com.liferay.dynamic.data.mapping.model.DDMStructureConstants}.
	 * @param status the workflow's status.
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the structures
	 (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap[]
			search(
				long companyId, long[] groupIds, long classNameId,
				String keywords, int type, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
				returnValue = DDMStructureServiceUtil.search(
					companyId, groupIds, classNameId, keywords, type, status,
					start, end, orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the structures matching the groups and
	 * class name IDs, and matching the keywords in the structure names and
	 * descriptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the structure's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name of the model the
	 structure is related to
	 * @param keywords the keywords (space separated), which may occur in the
	 structure's name or description (optionally <code>null</code>)
	 * @param status the workflow's status.
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the structures
	 (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap[]
			search(
				long companyId, long[] groupIds, long classNameId,
				String keywords, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
				returnValue = DDMStructureServiceUtil.search(
					companyId, groupIds, classNameId, keywords, status, start,
					end, orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns an ordered range of all the structures matching the groups, class
	 * name IDs, name keyword, description keyword, storage type, and type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the structure's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name of the model the
	 structure is related to
	 * @param name the name keywords
	 * @param description the description keywords
	 * @param storageType the structure's storage type. It can be "xml" or
	 "expando". For more information, see {@link
	 com.liferay.dynamic.data.mapping.storage.StorageType}.
	 * @param type the structure's type. For more information, see {@link
	 com.liferay.dynamic.data.mapping.model.DDMStructureConstants}.
	 * @param status the workflow's status.
	 * @param andOperator whether every field must match its keywords, or just
	 one field
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the structures
	 (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap[]
			search(
				long companyId, long[] groupIds, long classNameId, String name,
				String description, String storageType, int type, int status,
				boolean andOperator, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
				returnValue = DDMStructureServiceUtil.search(
					companyId, groupIds, classNameId, name, description,
					storageType, type, status, andOperator, start, end,
					orderByComparator);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the number of structures matching the groups and class name IDs,
	 * and matching the keywords in the structure names and descriptions.
	 *
	 * @param companyId the primary key of the structure's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name of the model the
	 structure is related to
	 * @param keywords the keywords (space separated), which may occur in the
	 structure's name or description (optionally <code>null</code>)
	 * @param status the workflow's status.
	 * @return the number of matching structures
	 */
	public static int searchCount(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int status)
		throws RemoteException {

		try {
			int returnValue = DDMStructureServiceUtil.searchCount(
				companyId, groupIds, classNameId, keywords, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the number of structures matching the groups and class name IDs,
	 * and matching the keywords in the structure names and descriptions.
	 *
	 * @param companyId the primary key of the structure's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name of the model the
	 structure is related to
	 * @param keywords the keywords (space separated), which may occur in the
	 structure's name or description (optionally <code>null</code>)
	 * @param type the structure's type. For more information, see {@link
	 com.liferay.dynamic.data.mapping.model.DDMStructureConstants}.
	 * @param status the workflow's status.
	 * @return the number of matching structures
	 */
	public static int searchCount(
			long companyId, long[] groupIds, long classNameId, String keywords,
			int type, int status)
		throws RemoteException {

		try {
			int returnValue = DDMStructureServiceUtil.searchCount(
				companyId, groupIds, classNameId, keywords, type, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the number of structures matching the groups, class name IDs,
	 * name keyword, description keyword, storage type, and type
	 *
	 * @param companyId the primary key of the structure's company
	 * @param groupIds the primary keys of the groups
	 * @param classNameId the primary key of the class name of the model the
	 structure is related to
	 * @param name the name keywords
	 * @param description the description keywords
	 * @param storageType the structure's storage type. It can be "xml" or
	 "expando". For more information, see {@link
	 com.liferay.dynamic.data.mapping.storage.StorageType}.
	 * @param type the structure's type. For more information, see {@link
	 com.liferay.dynamic.data.mapping.model.DDMStructureConstants}.
	 * @param andOperator whether every field must match its keywords, or just
	 one field
	 * @return the number of matching structures
	 */
	public static int searchCount(
			long companyId, long[] groupIds, long classNameId, String name,
			String description, String storageType, int type, int status,
			boolean andOperator)
		throws RemoteException {

		try {
			int returnValue = DDMStructureServiceUtil.searchCount(
				companyId, groupIds, classNameId, name, description,
				storageType, type, status, andOperator);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			updateStructure(
				long groupId, long parentStructureId, long classNameId,
				String structureKey, String[] nameMapLanguageIds,
				String[] nameMapValues, String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.updateStructure(
					groupId, parentStructureId, classNameId, structureKey,
					nameMap, descriptionMap, ddmForm, ddmFormLayout,
					serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap
			updateStructure(
				long structureId, long parentStructureId,
				String[] nameMapLanguageIds, String[] nameMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.updateStructure(
					structureId, parentStructureId, nameMap, descriptionMap,
					ddmForm, ddmFormLayout, serviceContext);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMStructureServiceSoap.class);

}