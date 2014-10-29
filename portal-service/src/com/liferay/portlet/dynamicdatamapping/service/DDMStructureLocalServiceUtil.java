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

package com.liferay.portlet.dynamicdatamapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for DDMStructure. This utility wraps
 * {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLocalService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDMStructureLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m structure to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was added
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure) {
		return getService().addDDMStructure(ddmStructure);
	}

	public static void addDLFileEntryTypeDDMStructure(long fileEntryTypeId,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure) {
		getService()
			.addDLFileEntryTypeDDMStructure(fileEntryTypeId, ddmStructure);
	}

	public static void addDLFileEntryTypeDDMStructure(long fileEntryTypeId,
		long structureId) {
		getService().addDLFileEntryTypeDDMStructure(fileEntryTypeId, structureId);
	}

	public static void addDLFileEntryTypeDDMStructures(long fileEntryTypeId,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> DDMStructures) {
		getService()
			.addDLFileEntryTypeDDMStructures(fileEntryTypeId, DDMStructures);
	}

	public static void addDLFileEntryTypeDDMStructures(long fileEntryTypeId,
		long[] structureIds) {
		getService()
			.addDLFileEntryTypeDDMStructures(fileEntryTypeId, structureIds);
	}

	public static void addJournalFolderDDMStructure(long folderId,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure) {
		getService().addJournalFolderDDMStructure(folderId, ddmStructure);
	}

	public static void addJournalFolderDDMStructure(long folderId,
		long structureId) {
		getService().addJournalFolderDDMStructure(folderId, structureId);
	}

	public static void addJournalFolderDDMStructures(long folderId,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> DDMStructures) {
		getService().addJournalFolderDDMStructures(folderId, DDMStructures);
	}

	public static void addJournalFolderDDMStructures(long folderId,
		long[] structureIds) {
		getService().addJournalFolderDDMStructures(folderId, structureIds);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, long classNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addStructure(userId, groupId, classNameId, nameMap,
			descriptionMap, ddmForm, serviceContext);
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
	* @param definition the structure's XML schema definition
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions,
	and group permissions for the structure.
	* @return the structure
	* @throws PortalException if a user with the primary key could not be
	found, if the XSD was not well-formed, or if a portal
	exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	long, Map, Map, DDMForm, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, long classNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String definition,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addStructure(userId, groupId, classNameId, nameMap,
			descriptionMap, definition, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addStructure(userId, groupId, parentStructureId,
			classNameId, structureKey, nameMap, descriptionMap, ddmForm,
			storageType, type, serviceContext);
	}

	/**
	* Adds a structure referencing its parent structure.
	*
	* @param userId the primary key of the structure's creator/owner
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
	* @param definition the structure's XML schema definition
	* @param storageType the structure's storage type. It can be "xml" or
	"expando". For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	* @param type the structure's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions,
	and group permissions for the structure.
	* @return the structure
	* @throws PortalException if a user with the primary key could not be
	found, if the XSD was not well-formed, or if a portal
	exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	long, long, String, Map, Map, DDMForm, String, int,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String definition, java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addStructure(userId, groupId, parentStructureId,
			classNameId, structureKey, nameMap, descriptionMap, definition,
			storageType, type, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, java.lang.String parentStructureKey,
		long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addStructure(userId, groupId, parentStructureKey,
			classNameId, structureKey, nameMap, descriptionMap, ddmForm,
			storageType, type, serviceContext);
	}

	/**
	* Adds a structure referencing a default parent structure if the parent
	* structure is not found.
	*
	* @param userId the primary key of the structure's creator/owner
	* @param groupId the primary key of the group
	* @param parentStructureKey the unique string identifying the parent
	structure (optionally <code>null</code>)
	* @param classNameId the primary key of the class name for the
	structure's related model
	* @param structureKey the unique string identifying the structure
	(optionally <code>null</code>)
	* @param nameMap the structure's locales and localized names
	* @param descriptionMap the structure's locales and localized
	descriptions
	* @param definition the structure's XML schema definition
	* @param storageType the structure's storage type. It can be "xml" or
	"expando". For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	* @param type the structure's type. For more information, see {@link
	com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions and
	group permissions for the structure.
	* @return the structure
	* @throws PortalException if a user with the primary key could not be
	found, if the XSD was not well-formed, or if a portal
	exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	String, long, String, Map, Map, DDMForm, String, int,
	ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, java.lang.String parentStructureKey,
		long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String definition, java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addStructure(userId, groupId, parentStructureKey,
			classNameId, structureKey, nameMap, descriptionMap, definition,
			storageType, type, serviceContext);
	}

	/**
	* Adds the resources to the structure.
	*
	* @param structure the structure to add resources to
	* @param addGroupPermissions whether to add group permissions
	* @param addGuestPermissions whether to add guest permissions
	* @throws PortalException if a portal exception occurred
	*/
	public static void addStructureResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addStructureResources(structure, addGroupPermissions,
			addGuestPermissions);
	}

	/**
	* Adds the model resources with the permissions to the structure.
	*
	* @param structure the structure to add resources to
	* @param groupPermissions the group permissions to be added
	* @param guestPermissions the guest permissions to be added
	* @throws PortalException if a portal exception occurred
	*/
	public static void addStructureResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addStructureResources(structure, groupPermissions, guestPermissions);
	}

	public static void clearDLFileEntryTypeDDMStructures(long fileEntryTypeId) {
		getService().clearDLFileEntryTypeDDMStructures(fileEntryTypeId);
	}

	public static void clearJournalFolderDDMStructures(long folderId) {
		getService().clearJournalFolderDDMStructures(folderId);
	}

	/**
	* Copies a structure, creating a new structure with all the values
	* extracted from the original one. The new structure supports a new name
	* and description.
	*
	* @param userId the primary key of the structure's creator/owner
	* @param structureId the primary key of the structure to be copied
	* @param nameMap the new structure's locales and localized names
	* @param descriptionMap the new structure's locales and localized
	descriptions
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions, and
	group permissions for the structure.
	* @return the new structure
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure copyStructure(
		long userId, long structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .copyStructure(userId, structureId, nameMap, descriptionMap,
			serviceContext);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure copyStructure(
		long userId, long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().copyStructure(userId, structureId, serviceContext);
	}

	/**
	* Creates a new d d m structure with the primary key. Does not add the d d m structure to the database.
	*
	* @param structureId the primary key for the new d d m structure
	* @return the new d d m structure
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure createDDMStructure(
		long structureId) {
		return getService().createDDMStructure(structureId);
	}

	/**
	* Deletes the d d m structure from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was removed
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure deleteDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure) {
		return getService().deleteDDMStructure(ddmStructure);
	}

	/**
	* Deletes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure that was removed
	* @throws PortalException if a d d m structure with the primary key could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure deleteDDMStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDMStructure(structureId);
	}

	public static void deleteDLFileEntryTypeDDMStructure(long fileEntryTypeId,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure) {
		getService()
			.deleteDLFileEntryTypeDDMStructure(fileEntryTypeId, ddmStructure);
	}

	public static void deleteDLFileEntryTypeDDMStructure(long fileEntryTypeId,
		long structureId) {
		getService()
			.deleteDLFileEntryTypeDDMStructure(fileEntryTypeId, structureId);
	}

	public static void deleteDLFileEntryTypeDDMStructures(
		long fileEntryTypeId,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> DDMStructures) {
		getService()
			.deleteDLFileEntryTypeDDMStructures(fileEntryTypeId, DDMStructures);
	}

	public static void deleteDLFileEntryTypeDDMStructures(
		long fileEntryTypeId, long[] structureIds) {
		getService()
			.deleteDLFileEntryTypeDDMStructures(fileEntryTypeId, structureIds);
	}

	public static void deleteJournalFolderDDMStructure(long folderId,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure) {
		getService().deleteJournalFolderDDMStructure(folderId, ddmStructure);
	}

	public static void deleteJournalFolderDDMStructure(long folderId,
		long structureId) {
		getService().deleteJournalFolderDDMStructure(folderId, structureId);
	}

	public static void deleteJournalFolderDDMStructures(long folderId,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> DDMStructures) {
		getService().deleteJournalFolderDDMStructures(folderId, DDMStructures);
	}

	public static void deleteJournalFolderDDMStructures(long folderId,
		long[] structureIds) {
		getService().deleteJournalFolderDDMStructures(folderId, structureIds);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the matching structure and its resources.
	*
	* <p>
	* Before deleting the structure, the system verifies whether the structure
	* is required by another entity. If it is needed, an exception is thrown.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param structureKey the unique string identifying the structure
	* @throws PortalException if a portal exception occurred
	*/
	public static void deleteStructure(long groupId, long classNameId,
		java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteStructure(groupId, classNameId, structureKey);
	}

	/**
	* Deletes the structure and its resources.
	*
	* <p>
	* Before deleting the structure, this method verifies whether the structure
	* is required by another entity. If it is needed, an exception is thrown.
	* </p>
	*
	* @param structure the structure to be deleted
	* @throws PortalException if a portal exception occurred
	*/
	public static void deleteStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteStructure(structure);
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
	* @throws PortalException if a portal exception occurred
	*/
	public static void deleteStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteStructure(structureId);
	}

	/**
	* Deletes all the structures of the group.
	*
	* <p>
	* Before deleting the structures, the system verifies whether each
	* structure is required by another entity. If any of the structures are
	* needed, an exception is thrown.
	* </p>
	*
	* @param groupId the primary key of the group
	* @throws PortalException if a portal exception occurred
	*/
	public static void deleteStructures(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteStructures(groupId);
	}

	public static void deleteStructures(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteStructures(groupId, classNameId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchDDMStructure(
		long structureId) {
		return getService().fetchDDMStructure(structureId);
	}

	/**
	* Returns the d d m structure matching the UUID and group.
	*
	* @param uuid the d d m structure's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchDDMStructureByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchDDMStructureByUuidAndGroupId(uuid, groupId);
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
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		long groupId, long classNameId, java.lang.String structureKey) {
		return getService().fetchStructure(groupId, classNameId, structureKey);
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
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param structureKey the unique string identifying the structure
	* @param includeAncestorStructures whether to include ancestor sites (that
	have sharing enabled) and include global scoped sites in the
	search
	* @return the matching structure, or <code>null</code> if a matching
	structure could not be found
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		long groupId, long classNameId, java.lang.String structureKey,
		boolean includeAncestorStructures)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchStructure(groupId, classNameId, structureKey,
			includeAncestorStructures);
	}

	/**
	* Returns the structure with the ID.
	*
	* @param structureId the primary key of the structure
	* @return the structure with the structure ID, or <code>null</code> if a
	matching structure could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		long structureId) {
		return getService().fetchStructure(structureId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getClassStructures(long,
	long)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long classNameId) {
		return getService().getClassStructures(classNameId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getClassStructures(long,
	long, OrderByComparator)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		return getService().getClassStructures(classNameId, orderByComparator);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getClassStructures(long,
	long, int, int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long classNameId, int start, int end) {
		return getService().getClassStructures(classNameId, start, end);
	}

	/**
	* Returns all the structures matching the class name ID.
	*
	* @param companyId the primary key of the structure's company
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the structures matching the class name ID
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long companyId, long classNameId) {
		return getService().getClassStructures(companyId, classNameId);
	}

	/**
	* Returns all the structures matching the class name ID ordered by the
	* comparator.
	*
	* @param companyId the primary key of the structure's company
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param orderByComparator the comparator to order the structures
	(optionally <code>null</code>)
	* @return the matching structures ordered by the comparator
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		return getService()
				   .getClassStructures(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns a range of all the structures matching the class name ID.
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
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @return the range of matching structures
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end) {
		return getService()
				   .getClassStructures(companyId, classNameId, start, end);
	}

	/**
	* Returns the d d m structure with the primary key.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure
	* @throws PortalException if a d d m structure with the primary key could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMStructure(structureId);
	}

	/**
	* Returns the d d m structure matching the UUID and group.
	*
	* @param uuid the d d m structure's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m structure
	* @throws PortalException if a matching d d m structure could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructureByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMStructureByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of d d m structures
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		int start, int end) {
		return getService().getDDMStructures(start, end);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructuresByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getDDMStructuresByUuidAndCompanyId(uuid, companyId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructuresByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		return getService()
				   .getDDMStructuresByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of d d m structures.
	*
	* @return the number of d d m structures
	*/
	public static int getDDMStructuresCount() {
		return getService().getDDMStructuresCount();
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDLFileEntryTypeDDMStructures(
		long fileEntryTypeId) {
		return getService().getDLFileEntryTypeDDMStructures(fileEntryTypeId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDLFileEntryTypeDDMStructures(
		long fileEntryTypeId, int start, int end) {
		return getService()
				   .getDLFileEntryTypeDDMStructures(fileEntryTypeId, start, end);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDLFileEntryTypeDDMStructures(
		long fileEntryTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		return getService()
				   .getDLFileEntryTypeDDMStructures(fileEntryTypeId, start,
			end, orderByComparator);
	}

	public static int getDLFileEntryTypeDDMStructuresCount(long fileEntryTypeId) {
		return getService().getDLFileEntryTypeDDMStructuresCount(fileEntryTypeId);
	}

	/**
	* Returns the fileEntryTypeIds of the document library file entry types associated with the d d m structure.
	*
	* @param structureId the structureId of the d d m structure
	* @return long[] the fileEntryTypeIds of document library file entry types associated with the d d m structure
	*/
	public static long[] getDLFileEntryTypePrimaryKeys(long structureId) {
		return getService().getDLFileEntryTypePrimaryKeys(structureId);
	}

	/**
	* Returns all the structures for the document library file entry type.
	*
	* @param dlFileEntryTypeId the primary key of the document library file
	entry type
	* @return the structures for the document library file entry type
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDLFileEntryTypeStructures(
		long dlFileEntryTypeId) {
		return getService().getDLFileEntryTypeStructures(dlFileEntryTypeId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getJournalFolderDDMStructures(
		long folderId) {
		return getService().getJournalFolderDDMStructures(folderId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getJournalFolderDDMStructures(
		long folderId, int start, int end) {
		return getService().getJournalFolderDDMStructures(folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getJournalFolderDDMStructures(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		return getService()
				   .getJournalFolderDDMStructures(folderId, start, end,
			orderByComparator);
	}

	public static int getJournalFolderDDMStructuresCount(long folderId) {
		return getService().getJournalFolderDDMStructuresCount(folderId);
	}

	/**
	* Returns the folderIds of the journal folders associated with the d d m structure.
	*
	* @param structureId the structureId of the d d m structure
	* @return long[] the folderIds of journal folders associated with the d d m structure
	*/
	public static long[] getJournalFolderPrimaryKeys(long structureId) {
		return getService().getJournalFolderPrimaryKeys(structureId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getJournalFolderStructures(
		long[] groupIds, long journalFolderId, int restrictionType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getJournalFolderStructures(groupIds, journalFolderId,
			restrictionType);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
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
	* @throws PortalException if a matching structure could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, long classNameId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStructure(groupId, classNameId, structureKey);
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
	search in the search
	* @return the matching structure
	* @throws PortalException if a matching structure could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, long classNameId, java.lang.String structureKey,
		boolean includeAncestorStructures)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getStructure(groupId, classNameId, structureKey,
			includeAncestorStructures);
	}

	/**
	* Returns all the structures matching the group, name, and description.
	*
	* @param groupId the primary key of the structure's group
	* @param name the structure's name
	* @param description the structure's description
	* @return the matching structures
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructure(
		long groupId, java.lang.String name, java.lang.String description) {
		return getService().getStructure(groupId, name, description);
	}

	/**
	* Returns the structure with the ID.
	*
	* @param structureId the primary key of the structure
	* @return the structure with the ID
	* @throws PortalException if a structure with the ID could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getStructure(structureId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getStructures}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries() {
		return getService().getStructureEntries();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getStructures(long)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries(
		long groupId) {
		return getService().getStructureEntries(groupId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getStructures(long, int,
	int)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries(
		long groupId, int start, int end) {
		return getService().getStructureEntries(groupId, start, end);
	}

	/**
	* Returns all the structures present in the system.
	*
	* @return the structures present in the system
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures() {
		return getService().getStructures();
	}

	/**
	* Returns all the structures present in the group.
	*
	* @param groupId the primary key of the group
	* @return the structures present in the group
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId) {
		return getService().getStructures(groupId);
	}

	/**
	* Returns all the structures matching class name ID and group.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the matching structures
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId, long classNameId) {
		return getService().getStructures(groupId, classNameId);
	}

	/**
	* Returns a range of all the structures that match the class name ID and
	* group.
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
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @return the range of matching structures
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId, long classNameId, int start, int end) {
		return getService().getStructures(groupId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the structures matching the class name ID
	* and group.
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
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @param orderByComparator the comparator to order the structures
	(optionally <code>null</code>)
	* @return the range of matching structures ordered by the comparator
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		return getService()
				   .getStructures(groupId, classNameId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId, java.lang.String name, java.lang.String description) {
		return getService().getStructures(groupId, name, description);
	}

	/**
	* Returns a range of all the structures belonging to the group.
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
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @return the range of matching structures
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId, int start, int end) {
		return getService().getStructures(groupId, start, end);
	}

	/**
	* Returns all the structures belonging to the groups.
	*
	* @param groupIds the primary keys of the groups
	* @return the structures belonging to the groups
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long[] groupIds) {
		return getService().getStructures(groupIds);
	}

	/**
	* Returns all the structures matching the class name ID and belonging to
	* the groups.
	*
	* @param groupIds the primary keys of the groups
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the matching structures
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long[] groupIds, long classNameId) {
		return getService().getStructures(groupIds, classNameId);
	}

	/**
	* Returns a range of all the structures matching the class name ID and
	* belonging to the groups.
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
	* @param groupIds the primary keys of the groups
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @return the range of matching structures
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long[] groupIds, long classNameId, int start, int end) {
		return getService().getStructures(groupIds, classNameId, start, end);
	}

	/**
	* Returns the number of structures belonging to the group.
	*
	* @param groupId the primary key of the group
	* @return the number of structures belonging to the group
	*/
	public static int getStructuresCount(long groupId) {
		return getService().getStructuresCount(groupId);
	}

	/**
	* Returns the number of structures matching the class name ID and group.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the number of matching structures
	*/
	public static int getStructuresCount(long groupId, long classNameId) {
		return getService().getStructuresCount(groupId, classNameId);
	}

	/**
	* Returns the number of structures matching the class name ID and belonging
	* to the groups.
	*
	* @param groupIds the primary keys of the groups
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the number of matching structures
	*/
	public static int getStructuresCount(long[] groupIds, long classNameId) {
		return getService().getStructuresCount(groupIds, classNameId);
	}

	public static boolean hasDLFileEntryTypeDDMStructure(long fileEntryTypeId,
		long structureId) {
		return getService()
				   .hasDLFileEntryTypeDDMStructure(fileEntryTypeId, structureId);
	}

	public static boolean hasDLFileEntryTypeDDMStructures(long fileEntryTypeId) {
		return getService().hasDLFileEntryTypeDDMStructures(fileEntryTypeId);
	}

	public static boolean hasJournalFolderDDMStructure(long folderId,
		long structureId) {
		return getService().hasJournalFolderDDMStructure(folderId, structureId);
	}

	public static boolean hasJournalFolderDDMStructures(long folderId) {
		return getService().hasJournalFolderDDMStructures(folderId);
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
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		return getService()
				   .search(companyId, groupIds, classNameIds, keywords, start,
			end, orderByComparator);
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
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		return getService()
				   .search(companyId, groupIds, classNameIds, name,
			description, storageType, type, andOperator, start, end,
			orderByComparator);
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
		long[] classNameIds, java.lang.String keywords) {
		return getService()
				   .searchCount(companyId, groupIds, classNameIds, keywords);
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
		boolean andOperator) {
		return getService()
				   .searchCount(companyId, groupIds, classNameIds, name,
			description, storageType, type, andOperator);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void setDLFileEntryTypeDDMStructures(long fileEntryTypeId,
		long[] structureIds) {
		getService()
			.setDLFileEntryTypeDDMStructures(fileEntryTypeId, structureIds);
	}

	public static void setJournalFolderDDMStructures(long folderId,
		long[] structureIds) {
		getService().setJournalFolderDDMStructures(folderId, structureIds);
	}

	/**
	* Updates the d d m structure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was updated
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure) {
		return getService().updateDDMStructure(ddmStructure);
	}

	/**
	* Updates the structure matching the structure ID, replacing its XSD with a
	* new one.
	*
	* @param structureId the primary key of the structure
	* @param definition the structure's new XML schema definition
	* @param serviceContext the service context to be applied. Can set the
	structure's modification date.
	* @return the updated structure
	* @throws PortalException if a matching structure could not be found, if
	the XSD was not well-formed, or if a portal exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateDefinition(
		long structureId, java.lang.String definition,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateDefinition(structureId, definition, serviceContext);
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
	* @param definition the structure's new XML schema definition
	* @param serviceContext the service context to be applied. Can set the
	structure's modification date.
	* @return the updated structure
	* @throws PortalException if a matching structure could not be found, if
	the XSD was not well-formed, or if a portal exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String definition,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStructure(groupId, parentStructureId, classNameId,
			structureKey, nameMap, descriptionMap, definition, serviceContext);
	}

	/**
	* Updates the structure matching the structure ID, replacing its old parent
	* structure, name map, description map, and XSD with new ones.
	*
	* @param structureId the primary key of the structure
	* @param parentStructureId the primary key of the new parent structure
	* @param nameMap the structure's new locales and localized names
	* @param descriptionMap the structure's new locales and localized
	descriptions
	* @param definition the structure's new XML schema definition
	* @param serviceContext the service context to be applied. Can set the
	structure's modification date.
	* @return the updated structure
	* @throws PortalException if a matching structure could not be found, if
	the XSD was not well-formed, or if a portal exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long structureId, long parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String definition,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStructure(structureId, parentStructureId, nameMap,
			descriptionMap, definition, serviceContext);
	}

	public static DDMStructureLocalService getService() {
		if (_service == null) {
			_service = (DDMStructureLocalService)PortalBeanLocatorUtil.locate(DDMStructureLocalService.class.getName());

			ReferenceRegistry.registerReference(DDMStructureLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DDMStructureLocalService service) {
	}

	private static DDMStructureLocalService _service;
}