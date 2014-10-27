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

package com.liferay.portlet.dynamicdatamapping.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
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
 * @author Brian Wing Shun Chan
 * @see DDMStructureServiceHttp
 * @see com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap
 * @see com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil
 * @generated
 */
@ProviderType
public class DDMStructureServiceSoap {
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap addStructure(
		long userId, long groupId, long classNameId,
		java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.addStructure(userId, groupId,
					classNameId, nameMap, descriptionMap, ddmForm,
					serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Adds a structure referencing a default parent structure, using the portal
	* property <code>dynamic.data.lists.storage.type</code> storage type and
	* default structure type.
	*
	* @param userId the primary key of the structure's creator/owner
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the
	structure's related model
	* @param nameMap the structure's locales and localized names
	* @param descriptionMap the structure's locales and localized
	descriptions
	* @param xsd the structure's XML schema definition
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions,
	and group permissions for the structure.
	* @return the structure
	* @throws PortalException if a user with the primary key could not be
	found, if the user did not have permission to add the
	structure, if the XSD was not well-formed, or if a portal
	exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	long, Map, Map, DDMForm, ServiceContext)}
	*/
	@Deprecated
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
		java.lang.String[] descriptionMapValues,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
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
					descriptionMap, ddmForm, storageType, type, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Adds a structure referencing its parent structure.
	*
	* @param groupId the primary key of the group
	* @param parentStructureId the primary key of the parent structure
	(optionally {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants#DEFAULT_PARENT_STRUCTURE_ID})
	* @param classNameId the primary key of the class name for the
	structure's related model
	* @param structureKey the unique string identifying the structure
	(optionally <code>null</code>)
	* @param nameMap the structure's locales and localized names
	* @param descriptionMap the structure's locales and localized
	descriptions
	* @param xsd the structure's XML schema definition
	* @param storageType the structure's storage type. It can be "xml" or
	"expando". For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	* @param type the structure's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions,
	and group permissions for the structure.
	* @return the structure
	* @throws PortalException if the user did not have permission to add
	the structure, if the XSD is not well formed, or if a portal
	exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	long, String, Map, Map, DDMForm, String, int,
	ServiceContext)}
	*/
	@Deprecated
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

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap addStructure(
		long userId, long groupId, java.lang.String parentStructureKey,
		long classNameId, java.lang.String structureKey,
		java.lang.String[] nameMapLanguageIds,
		java.lang.String[] nameMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.addStructure(userId, groupId,
					parentStructureKey, classNameId, structureKey, nameMap,
					descriptionMap, ddmForm, storageType, type, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Adds a structure referencing the parent structure by its structure key.
	* In case the parent structure is not found, it uses the default parent
	* structure ID.
	*
	* @param userId the primary key of the structure's creator/owner
	* @param groupId the primary key of the group
	* @param parentStructureKey the unique string identifying the
	structure
	* @param classNameId the primary key of the class name for the
	structure's related model
	* @param structureKey unique string identifying the structure
	(optionally <code>null</code>)
	* @param nameMap the structure's locales and localized names
	* @param descriptionMap the structure's locales and localized
	descriptions
	* @param xsd the XML schema definition of the structure
	* @param storageType the storage type of the structure. It can be XML
	or expando. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	* @param type the structure's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	* @param serviceContext the service context to be applied. Must have
	the <code>ddmResource</code> attribute to check permissions.
	Can set the UUID, creation date, modification date, guest
	permissions, and group permissions for the structure.
	* @return the structure
	* @throws PortalException if a user with the primary key could not be
	found, if the user did not have permission to add the
	structure, if the XSD was not well-formed, or if a portal
	exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	String, long, String, Map, Map, DDMForm, String, int,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap addStructure(
		long userId, long groupId, java.lang.String parentStructureKey,
		long classNameId, java.lang.String structureKey,
		java.lang.String[] nameMapLanguageIds,
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
				DDMStructureServiceUtil.addStructure(userId, groupId,
					parentStructureKey, classNameId, structureKey, nameMap,
					descriptionMap, xsd, storageType, type, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
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
	* @throws PortalException if the user did not have permission to add the
	structure or if a portal exception occurred
	*/
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

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap copyStructure(
		long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.copyStructure(structureId,
					serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
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
	* @throws PortalException if the user did not have permission to delete the
	structure or if a portal exception occurred
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
	* @throws PortalException if the user did not have permission to view the
	structure or if a portal exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap fetchStructure(
		long groupId, long classNameId, java.lang.String structureKey)
		throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.fetchStructure(groupId, classNameId,
					structureKey);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap[] getJournalFolderStructures(
		long[] groupIds, long journalFolderId, int restrictionType)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> returnValue =
				DDMStructureServiceUtil.getJournalFolderStructures(groupIds,
					journalFolderId, restrictionType);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModels(returnValue);
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
	* @throws PortalException if the user did not have permission to view the
	structure or if a structure with the ID could not be found
	*/
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

	/**
	* Returns the structure matching the class name ID, structure key, and
	* group.
	*
	* @param groupId the primary key of the structure's group
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param structureKey the unique string identifying the structure
	* @return the matching structure
	* @throws PortalException if the user did not have permission to view the
	structure or if a matching structure could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap getStructure(
		long groupId, long classNameId, java.lang.String structureKey)
		throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.getStructure(groupId, classNameId,
					structureKey);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
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
	* @throws PortalException if the user did not have permission to view the
	structure or if a matching structure could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap getStructure(
		long groupId, long classNameId, java.lang.String structureKey,
		boolean includeAncestorStructures) throws RemoteException {
		try {
			com.liferay.portlet.dynamicdatamapping.model.DDMStructure returnValue =
				DDMStructureServiceUtil.getStructure(groupId, classNameId,
					structureKey, includeAncestorStructures);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all the structures in the group that the user has permission to
	* view.
	*
	* @param groupId the primary key of the group
	* @return the structures in the group that the user has permission to view
	*/
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

	/**
	* Returns all the structures in the groups that the user has permission to
	* view.
	*
	* @param groupIds the primary key of the groups
	* @return the structures in the groups that the user has permission to view
	*/
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

	/**
	* Returns all the structures matching the groups and class name ID that the
	* user has permission to view.
	*
	* @param groupIds the primary keys of the groups
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the structures matching the groups and class name ID that the
	user has permission to view
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap[] getStructures(
		long[] groupIds, long classNameId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> returnValue =
				DDMStructureServiceUtil.getStructures(groupIds, classNameId);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap[] getStructures(
		long[] groupIds, long classNameId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> returnValue =
				DDMStructureServiceUtil.getStructures(groupIds, classNameId,
					start, end);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModels(returnValue);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the structure's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the class names of the models
	the structures are related to
	* @param keywords the keywords (space separated), which may occur in the
	structure's name or description (optionally <code>null</code>)
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @param orderByComparator the comparator to order the structures
	(optionally <code>null</code>)
	* @return the range of matching structures ordered by the comparator
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap[] search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator)
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

	/**
	* Returns an ordered range of all the structures matching the groups, class
	* name IDs, name keyword, description keyword, storage type, and type.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the structure's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the class names of the models
	the structures are related to
	* @param name the name keywords
	* @param description the description keywords
	* @param storageType the structure's storage type. It can be "xml" or
	"expando". For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	* @param type the structure's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	* @param andOperator whether every field must match its keywords, or just
	one field
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @param orderByComparator the comparator to order the structures
	(optionally <code>null</code>)
	* @return the range of matching structures ordered by the comparator
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap[] search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator)
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

	/**
	* Returns the number of structures matching the groups and class name IDs,
	* and matching the keywords in the structure names and descriptions.
	*
	* @param companyId the primary key of the structure's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the class names of the models
	the structures are related to
	* @param keywords the keywords (space separated), which may occur in the
	structure's name or description (optionally <code>null</code>)
	* @return the number of matching structures
	*/
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

	/**
	* Returns the number of structures matching the groups, class name IDs,
	* name keyword, description keyword, storage type, and type
	*
	* @param companyId the primary key of the structure's company
	* @param groupIds the primary keys of the groups
	* @param classNameIds the primary keys of the class names of the models
	the structure's are related to
	* @param name the name keywords
	* @param description the description keywords
	* @param storageType the structure's storage type. It can be "xml" or
	"expando". For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	* @param type the structure's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	* @param andOperator whether every field must match its keywords, or just
	one field
	* @return the number of matching structures
	*/
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

	/**
	* Updates the structure matching the class name ID, structure key, and
	* group, replacing its old parent structure, name map, description map, and
	* XSD with new ones.
	*
	* @param groupId the primary key of the group
	* @param parentStructureId the primary key of the new parent structure
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param structureKey the unique string identifying the structure
	* @param nameMap the structure's new locales and localized names
	* @param descriptionMap the structure's new locales and localized
	description
	* @param xsd the structure's new XML schema definition
	* @param serviceContext the service context to be applied. Can set the
	modification date.
	* @return the updated structure
	* @throws PortalException if the user did not have permission to update the
	structure or if a portal exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap updateStructure(
		long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey, java.lang.String[] nameMapLanguageIds,
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
					parentStructureId, classNameId, structureKey, nameMap,
					descriptionMap, xsd, serviceContext);

			return com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the structure matching the structure ID, replacing the old parent
	* structure ID, name map, description map, and XSD with the new values.
	*
	* @param structureId the primary key of the structure
	* @param parentStructureId the new parent structure primary key
	* @param nameMap the structure's new locales and localized names
	* @param descriptionMap the structure's new locales and localized
	description
	* @param xsd the new XML schema definition of the structure
	* @param serviceContext the service context to be applied. Can set the
	modification date.
	* @return the updated structure
	* @throws PortalException if the user did not have permission to update the
	structure or if a portal exception occurred
	*/
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

	private static Log _log = LogFactoryUtil.getLog(DDMStructureServiceSoap.class);
}