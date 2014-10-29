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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.RequiredStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureDefinitionException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException;
import com.liferay.portlet.dynamicdatamapping.StructureNameException;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormTemplateSynchonizer;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * dynamic data mapping (DDM) structures.
 *
 * <p>
 * DDM structures (structures) are used in Liferay to store structured content
 * like document types, dynamic data definitions, or web contents.
 * </p>
 *
 * <p>
 * Structures support inheritance via parent structures. They also support
 * multi-language names and descriptions.
 * </p>
 *
 * <p>
 * Structures can be related to many models in Liferay, such as those for web
 * contents, dynamic data lists, and documents. This relationship can be
 * established via the model's class name ID.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class DDMStructureLocalServiceImpl
	extends DDMStructureLocalServiceBaseImpl {

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			String storageType, int type, ServiceContext serviceContext)
		throws PortalException {

		// Structure

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(structureKey)) {
			structureKey = String.valueOf(counterLocalService.increment());
		}
		else {
			structureKey = StringUtil.toUpperCase(structureKey.trim());
		}

		Date now = new Date();

		validate(
			groupId, parentStructureId, classNameId, structureKey, nameMap,
			ddmForm);

		long structureId = counterLocalService.increment();

		DDMStructure structure = ddmStructurePersistence.create(structureId);

		structure.setUuid(serviceContext.getUuid());
		structure.setGroupId(groupId);
		structure.setCompanyId(user.getCompanyId());
		structure.setUserId(user.getUserId());
		structure.setUserName(user.getFullName());
		structure.setCreateDate(serviceContext.getCreateDate(now));
		structure.setModifiedDate(serviceContext.getModifiedDate(now));
		structure.setParentStructureId(parentStructureId);
		structure.setClassNameId(classNameId);
		structure.setStructureKey(structureKey);
		structure.setVersion(DDMStructureConstants.VERSION_DEFAULT);
		structure.setNameMap(nameMap);
		structure.setDescriptionMap(descriptionMap);
		structure.setDefinition(DDMFormXSDSerializerUtil.serialize(ddmForm));
		structure.setStorageType(storageType);
		structure.setType(type);

		ddmStructurePersistence.update(structure);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addStructureResources(
				structure, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addStructureResources(
				structure, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Structure version

		addStructureVersion(structure, DDMStructureConstants.VERSION_DEFAULT);

		return structure;
	}

	/**
	 * Adds a structure referencing its parent structure.
	 *
	 * @param      userId the primary key of the structure's creator/owner
	 * @param      groupId the primary key of the group
	 * @param      parentStructureId the primary key of the parent structure
	 *             (optionally {@link
	 *             com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants#DEFAULT_PARENT_STRUCTURE_ID})
	 * @param      classNameId the primary key of the class name for the
	 *             structure's related model
	 * @param      structureKey the unique string identifying the structure
	 *             (optionally <code>null</code>)
	 * @param      nameMap the structure's locales and localized names
	 * @param      descriptionMap the structure's locales and localized
	 *             descriptions
	 * @param      definition the structure's XML schema definition
	 * @param      storageType the structure's storage type. It can be "xml" or
	 *             "expando". For more information, see {@link
	 *             com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	 * @param      type the structure's type. For more information, see {@link
	 *             com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param      serviceContext the service context to be applied. Can set the
	 *             UUID, creation date, modification date, guest permissions,
	 *             and group permissions for the structure.
	 * @return     the structure
	 * @throws     PortalException if a user with the primary key could not be
	 *             found, if the XSD was not well-formed, or if a portal
	 *             exception occurred
	 * @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	 *             long, long, String, Map, Map, DDMForm, String, int,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDMStructure addStructure(
			long userId, long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String definition,
			String storageType, int type, ServiceContext serviceContext)
		throws PortalException {

		DDMXMLUtil.validateXML(definition);

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		return addStructure(
			userId, groupId, parentStructureId, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, storageType, type,
			serviceContext);
	}

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, long classNameId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, ServiceContext serviceContext)
		throws PortalException {

		return addStructure(
			userId, groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			classNameId, null, nameMap, descriptionMap, ddmForm,
			PropsValues.DYNAMIC_DATA_LISTS_STORAGE_TYPE,
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	/**
	 * Adds a structure referencing a default parent structure, using the portal
	 * property <code>dynamic.data.lists.storage.type</code> storage type and
	 * default structure type.
	 *
	 * @param      userId the primary key of the structure's creator/owner
	 * @param      groupId the primary key of the group
	 * @param      classNameId the primary key of the class name for the
	 *             structure's related model
	 * @param      nameMap the structure's locales and localized names
	 * @param      descriptionMap the structure's locales and localized
	 *             descriptions
	 * @param      definition the structure's XML schema definition
	 * @param      serviceContext the service context to be applied. Can set the
	 *             UUID, creation date, modification date, guest permissions,
	 *             and group permissions for the structure.
	 * @return     the structure
	 * @throws     PortalException if a user with the primary key could not be
	 *             found, if the XSD was not well-formed, or if a portal
	 *             exception occurred
	 * @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	 *             long, Map, Map, DDMForm, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDMStructure addStructure(
			long userId, long groupId, long classNameId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String definition, ServiceContext serviceContext)
		throws PortalException {

		return addStructure(
			userId, groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			classNameId, null, nameMap, descriptionMap, definition,
			PropsValues.DYNAMIC_DATA_LISTS_STORAGE_TYPE,
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			String storageType, int type, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure parentStructure = fetchStructure(
			groupId, classNameId, parentStructureKey);

		long parentStructureId =
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID;

		if (parentStructure != null) {
			parentStructureId = parentStructure.getStructureId();
		}

		return addStructure(
			userId, groupId, parentStructureId, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, storageType, type,
			serviceContext);
	}

	/**
	 * Adds a structure referencing a default parent structure if the parent
	 * structure is not found.
	 *
	 * @param      userId the primary key of the structure's creator/owner
	 * @param      groupId the primary key of the group
	 * @param      parentStructureKey the unique string identifying the parent
	 *             structure (optionally <code>null</code>)
	 * @param      classNameId the primary key of the class name for the
	 *             structure's related model
	 * @param      structureKey the unique string identifying the structure
	 *             (optionally <code>null</code>)
	 * @param      nameMap the structure's locales and localized names
	 * @param      descriptionMap the structure's locales and localized
	 *             descriptions
	 * @param      definition the structure's XML schema definition
	 * @param      storageType the structure's storage type. It can be "xml" or
	 *             "expando". For more information, see {@link
	 *             com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	 * @param      type the structure's type. For more information, see {@link
	 *             com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param      serviceContext the service context to be applied. Can set the
	 *             UUID, creation date, modification date, guest permissions and
	 *             group permissions for the structure.
	 * @return     the structure
	 * @throws     PortalException if a user with the primary key could not be
	 *             found, if the XSD was not well-formed, or if a portal
	 *             exception occurred
	 * @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	 *             String, long, String, Map, Map, DDMForm, String, int,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String definition,
			String storageType, int type, ServiceContext serviceContext)
		throws PortalException {

		DDMXMLUtil.validateXML(definition);

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		return addStructure(
			userId, groupId, parentStructureKey, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, storageType, type,
			serviceContext);
	}

	/**
	 * Adds the resources to the structure.
	 *
	 * @param  structure the structure to add resources to
	 * @param  addGroupPermissions whether to add group permissions
	 * @param  addGuestPermissions whether to add guest permissions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void addStructureResources(
			DDMStructure structure, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), DDMStructure.class.getName(),
			structure.getStructureId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	/**
	 * Adds the model resources with the permissions to the structure.
	 *
	 * @param  structure the structure to add resources to
	 * @param  groupPermissions the group permissions to be added
	 * @param  guestPermissions the guest permissions to be added
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void addStructureResources(
			DDMStructure structure, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), DDMStructure.class.getName(),
			structure.getStructureId(), groupPermissions, guestPermissions);
	}

	/**
	 * Copies a structure, creating a new structure with all the values
	 * extracted from the original one. The new structure supports a new name
	 * and description.
	 *
	 * @param  userId the primary key of the structure's creator/owner
	 * @param  structureId the primary key of the structure to be copied
	 * @param  nameMap the new structure's locales and localized names
	 * @param  descriptionMap the new structure's locales and localized
	 *         descriptions
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions, and
	 *         group permissions for the structure.
	 * @return the new structure
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public DDMStructure copyStructure(
			long userId, long structureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		return addStructure(
			userId, structure.getGroupId(), structure.getParentStructureId(),
			structure.getClassNameId(), null, nameMap, descriptionMap,
			structure.getDefinition(), structure.getStorageType(),
			structure.getType(), serviceContext);
	}

	@Override
	public DDMStructure copyStructure(
			long userId, long structureId, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		return addStructure(
			userId, structure.getGroupId(), structure.getParentStructureId(),
			structure.getClassNameId(), null, structure.getNameMap(),
			structure.getDescriptionMap(), structure.getDefinition(),
			structure.getStorageType(), structure.getType(), serviceContext);
	}

	/**
	 * Deletes the structure and its resources.
	 *
	 * <p>
	 * Before deleting the structure, this method verifies whether the structure
	 * is required by another entity. If it is needed, an exception is thrown.
	 * </p>
	 *
	 * @param  structure the structure to be deleted
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteStructure(DDMStructure structure) throws PortalException {
		if (!GroupThreadLocal.isDeleteInProcess()) {
			if (ddmStructureLinkPersistence.countByStructureId(
					structure.getStructureId()) > 0) {

				throw new RequiredStructureException(
					RequiredStructureException.REFERENCED_STRUCTURE_LINK);
			}

			if (ddmStructurePersistence.countByParentStructureId(
					structure.getStructureId()) > 0) {

				throw new RequiredStructureException(
					RequiredStructureException.REFERENCED_STRUCTURE);
			}

			long classNameId = classNameLocalService.getClassNameId(
				DDMStructure.class);

			if (ddmTemplatePersistence.countByG_C_C(
					structure.getGroupId(), classNameId,
					structure.getPrimaryKey()) > 0) {

				throw new RequiredStructureException(
					RequiredStructureException.REFERENCED_TEMPLATE);
			}
		}

		// Structure

		ddmStructurePersistence.remove(structure);

		// Resources

		resourceLocalService.deleteResource(
			structure.getCompanyId(), DDMStructure.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, structure.getStructureId());
	}

	/**
	 * Deletes the structure and its resources.
	 *
	 * <p>
	 * Before deleting the structure, the system verifies whether the structure
	 * is required by another entity. If it is needed, an exception is thrown.
	 * </p>
	 *
	 * @param  structureId the primary key of the structure to be deleted
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteStructure(long structureId) throws PortalException {
		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		ddmStructureLocalService.deleteStructure(structure);
	}

	/**
	 * Deletes the matching structure and its resources.
	 *
	 * <p>
	 * Before deleting the structure, the system verifies whether the structure
	 * is required by another entity. If it is needed, an exception is thrown.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  structureKey the unique string identifying the structure
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		structureKey = getStructureKey(structureKey);

		DDMStructure structure = ddmStructurePersistence.findByG_C_S(
			groupId, classNameId, structureKey);

		ddmStructureLocalService.deleteStructure(structure);
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
	 * @param  groupId the primary key of the group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteStructures(long groupId) throws PortalException {
		List<DDMStructure> structures = ddmStructurePersistence.findByGroupId(
			groupId);

		deleteStructures(structures);
	}

	@Override
	public void deleteStructures(long groupId, long classNameId)
		throws PortalException {

		List<DDMStructure> structures = ddmStructurePersistence.findByG_C(
			groupId, classNameId);

		deleteStructures(structures);
	}

	/**
	 * Returns the structure with the ID.
	 *
	 * @param  structureId the primary key of the structure
	 * @return the structure with the structure ID, or <code>null</code> if a
	 *         matching structure could not be found
	 */
	@Override
	public DDMStructure fetchStructure(long structureId) {
		return ddmStructurePersistence.fetchByPrimaryKey(structureId);
	}

	/**
	 * Returns the structure matching the class name ID, structure key, and
	 * group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  structureKey the unique string identifying the structure
	 * @return the matching structure, or <code>null</code> if a matching
	 *         structure could not be found
	 */
	@Override
	public DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey) {

		structureKey = getStructureKey(structureKey);

		return ddmStructurePersistence.fetchByG_C_S(
			groupId, classNameId, structureKey);
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
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  structureKey the unique string identifying the structure
	 * @param  includeAncestorStructures whether to include ancestor sites (that
	 *         have sharing enabled) and include global scoped sites in the
	 *         search
	 * @return the matching structure, or <code>null</code> if a matching
	 *         structure could not be found
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public DDMStructure fetchStructure(
			long groupId, long classNameId, String structureKey,
			boolean includeAncestorStructures)
		throws PortalException {

		structureKey = getStructureKey(structureKey);

		DDMStructure structure = ddmStructurePersistence.fetchByG_C_S(
			groupId, classNameId, structureKey);

		if (structure != null) {
			return structure;
		}

		if (!includeAncestorStructures) {
			return null;
		}

		for (long ancestorSiteGroupId :
				PortalUtil.getAncestorSiteGroupIds(groupId)) {

			structure = ddmStructurePersistence.fetchByG_C_S(
				ancestorSiteGroupId, classNameId, structureKey);

			if (structure != null) {
				return structure;
			}
		}

		return null;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getClassStructures(long,
	 *             long)}
	 */
	@Deprecated
	@Override
	public List<DDMStructure> getClassStructures(long classNameId) {
		return ddmStructurePersistence.findByClassNameId(classNameId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getClassStructures(long,
	 *             long, int, int)}
	 */
	@Deprecated
	@Override
	public List<DDMStructure> getClassStructures(
		long classNameId, int start, int end) {

		return ddmStructurePersistence.findByClassNameId(
			classNameId, start, end);
	}

	/**
	 * Returns all the structures matching the class name ID.
	 *
	 * @param  companyId the primary key of the structure's company
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @return the structures matching the class name ID
	 */
	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId) {

		return ddmStructurePersistence.findByC_C(companyId, classNameId);
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
	 * @param  companyId the primary key of the structure's company
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  start the lower bound of the range of structures to return
	 * @param  end the upper bound of the range of structures to return (not
	 *         inclusive)
	 * @return the range of matching structures
	 */
	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end) {

		return ddmStructurePersistence.findByC_C(
			companyId, classNameId, start, end);
	}

	/**
	 * Returns all the structures matching the class name ID ordered by the
	 * comparator.
	 *
	 * @param  companyId the primary key of the structure's company
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  orderByComparator the comparator to order the structures
	 *         (optionally <code>null</code>)
	 * @return the matching structures ordered by the comparator
	 */
	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructurePersistence.findByC_C(
			companyId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			orderByComparator);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getClassStructures(long,
	 *             long, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public List<DDMStructure> getClassStructures(
		long classNameId, OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructurePersistence.findByClassNameId(
			classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			orderByComparator);
	}

	/**
	 * Returns all the structures for the document library file entry type.
	 *
	 * @param  dlFileEntryTypeId the primary key of the document library file
	 *         entry type
	 * @return the structures for the document library file entry type
	 */
	@Override
	public List<DDMStructure> getDLFileEntryTypeStructures(
		long dlFileEntryTypeId) {

		return dlFileEntryTypePersistence.getDDMStructures(dlFileEntryTypeId);
	}

	@Override
	public List<DDMStructure> getJournalFolderStructures(
			long[] groupIds, long journalFolderId, int restrictionType)
		throws PortalException {

		if (restrictionType ==
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW) {

			return journalFolderPersistence.getDDMStructures(journalFolderId);
		}

		List<DDMStructure> structures = null;

		journalFolderId =
			journalFolderLocalService.getOverridedDDMStructuresFolderId(
				journalFolderId);

		if (journalFolderId !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			structures = journalFolderPersistence.getDDMStructures(
				journalFolderId);
		}
		else {
			long classNameId = classNameLocalService.getClassNameId(
				JournalArticle.class);

			structures = ddmStructurePersistence.findByG_C(
				groupIds, classNameId);
		}

		return structures;
	}

	/**
	 * Returns the structure with the ID.
	 *
	 * @param  structureId the primary key of the structure
	 * @return the structure with the ID
	 * @throws PortalException if a structure with the ID could not be found
	 */
	@Override
	public DDMStructure getStructure(long structureId) throws PortalException {
		return ddmStructurePersistence.findByPrimaryKey(structureId);
	}

	/**
	 * Returns the structure matching the class name ID, structure key, and
	 * group.
	 *
	 * @param  groupId the primary key of the structure's group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  structureKey the unique string identifying the structure
	 * @return the matching structure
	 * @throws PortalException if a matching structure could not be found
	 */
	@Override
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		structureKey = getStructureKey(structureKey);

		return ddmStructurePersistence.findByG_C_S(
			groupId, classNameId, structureKey);
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
	 * @param  groupId the primary key of the structure's group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  structureKey the unique string identifying the structure
	 * @param  includeAncestorStructures whether to include ancestor sites (that
	 *         have sharing enabled) and include global scoped sites in the
	 *         search in the search
	 * @return the matching structure
	 * @throws PortalException if a matching structure could not be found
	 */
	@Override
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey,
			boolean includeAncestorStructures)
		throws PortalException {

		structureKey = getStructureKey(structureKey);

		DDMStructure structure = ddmStructurePersistence.fetchByG_C_S(
			groupId, classNameId, structureKey);

		if (structure != null) {
			return structure;
		}

		if (!includeAncestorStructures) {
			throw new NoSuchStructureException(
				"No DDMStructure exists with the structure key " +
					structureKey);
		}

		for (long curGroupId : PortalUtil.getAncestorSiteGroupIds(groupId)) {
			structure = ddmStructurePersistence.fetchByG_C_S(
				curGroupId, classNameId, structureKey);

			if (structure != null) {
				return structure;
			}
		}

		throw new NoSuchStructureException(
			"No DDMStructure exists with the structure key " +
				structureKey + " in the ancestor groups");
	}

	/**
	 * Returns all the structures matching the group, name, and description.
	 *
	 * @param  groupId the primary key of the structure's group
	 * @param  name the structure's name
	 * @param  description the structure's description
	 * @return the matching structures
	 */
	@Override
	public List<DDMStructure> getStructure(
		long groupId, String name, String description) {

		return ddmStructurePersistence.findByG_N_D(groupId, name, description);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getStructures}
	 */
	@Deprecated
	@Override
	public List<DDMStructure> getStructureEntries() {
		return getStructures();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getStructures(long)}
	 */
	@Deprecated
	@Override
	public List<DDMStructure> getStructureEntries(long groupId) {
		return getStructures(groupId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getStructures(long, int,
	 *             int)}
	 */
	@Deprecated
	@Override
	public List<DDMStructure> getStructureEntries(
		long groupId, int start, int end) {

		return getStructures(groupId, start, end);
	}

	/**
	 * Returns all the structures present in the system.
	 *
	 * @return the structures present in the system
	 */
	@Override
	public List<DDMStructure> getStructures() {
		return ddmStructurePersistence.findAll();
	}

	/**
	 * Returns all the structures present in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the structures present in the group
	 */
	@Override
	public List<DDMStructure> getStructures(long groupId) {
		return ddmStructurePersistence.findByGroupId(groupId);
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
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of structures to return
	 * @param  end the upper bound of the range of structures to return (not
	 *         inclusive)
	 * @return the range of matching structures
	 */
	@Override
	public List<DDMStructure> getStructures(long groupId, int start, int end) {
		return ddmStructurePersistence.findByGroupId(groupId, start, end);
	}

	/**
	 * Returns all the structures matching class name ID and group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @return the matching structures
	 */
	@Override
	public List<DDMStructure> getStructures(long groupId, long classNameId) {
		return ddmStructurePersistence.findByG_C(groupId, classNameId);
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
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  start the lower bound of the range of structures to return
	 * @param  end the upper bound of the range of structures to return (not
	 *         inclusive)
	 * @return the range of matching structures
	 */
	@Override
	public List<DDMStructure> getStructures(
		long groupId, long classNameId, int start, int end) {

		return ddmStructurePersistence.findByG_C(
			groupId, classNameId, start, end);
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
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  start the lower bound of the range of structures to return
	 * @param  end the upper bound of the range of structures to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the structures
	 *         (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	@Override
	public List<DDMStructure> getStructures(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructurePersistence.findByG_C(
			groupId, classNameId, start, end, orderByComparator);
	}

	@Override
	public List<DDMStructure> getStructures(
		long groupId, String name, String description) {

		return ddmStructurePersistence.findByG_N_D(groupId, name, description);
	}

	/**
	 * Returns all the structures belonging to the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @return the structures belonging to the groups
	 */
	@Override
	public List<DDMStructure> getStructures(long[] groupIds) {
		return ddmStructurePersistence.findByGroupId(groupIds);
	}

	/**
	 * Returns all the structures matching the class name ID and belonging to
	 * the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @return the matching structures
	 */
	@Override
	public List<DDMStructure> getStructures(long[] groupIds, long classNameId) {
		return ddmStructurePersistence.findByG_C(groupIds, classNameId);
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
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  start the lower bound of the range of structures to return
	 * @param  end the upper bound of the range of structures to return (not
	 *         inclusive)
	 * @return the range of matching structures
	 */
	@Override
	public List<DDMStructure> getStructures(
		long[] groupIds, long classNameId, int start, int end) {

		return ddmStructurePersistence.findByG_C(
			groupIds, classNameId, start, end);
	}

	/**
	 * Returns the number of structures belonging to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of structures belonging to the group
	 */
	@Override
	public int getStructuresCount(long groupId) {
		return ddmStructurePersistence.countByGroupId(groupId);
	}

	/**
	 * Returns the number of structures matching the class name ID and group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @return the number of matching structures
	 */
	@Override
	public int getStructuresCount(long groupId, long classNameId) {
		return ddmStructurePersistence.countByG_C(groupId, classNameId);
	}

	/**
	 * Returns the number of structures matching the class name ID and belonging
	 * to the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @return the number of matching structures
	 */
	@Override
	public int getStructuresCount(long[] groupIds, long classNameId) {
		return ddmStructurePersistence.countByG_C(groupIds, classNameId);
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
	 * @param  companyId the primary key of the structure's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names of the models
	 *         the structures are related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         structure's name or description (optionally <code>null</code>)
	 * @param  start the lower bound of the range of structures to return
	 * @param  end the upper bound of the range of structures to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the structures
	 *         (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	@Override
	public List<DDMStructure> search(
		long companyId, long[] groupIds, long[] classNameIds, String keywords,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructureFinder.findByKeywords(
			companyId, groupIds, classNameIds, keywords, start, end,
			orderByComparator);
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
	 * @param  companyId the primary key of the structure's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names of the models
	 *         the structures are related to
	 * @param  name the name keywords
	 * @param  description the description keywords
	 * @param  storageType the structure's storage type. It can be "xml" or
	 *         "expando". For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	 * @param  type the structure's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @param  start the lower bound of the range of structures to return
	 * @param  end the upper bound of the range of structures to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the structures
	 *         (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	@Override
	public List<DDMStructure> search(
		long companyId, long[] groupIds, long[] classNameIds, String name,
		String description, String storageType, int type, boolean andOperator,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructureFinder.findByC_G_C_N_D_S_T(
			companyId, groupIds, classNameIds, name, description, storageType,
			type, andOperator, start, end, orderByComparator);
	}

	/**
	 * Returns the number of structures matching the groups and class name IDs,
	 * and matching the keywords in the structure names and descriptions.
	 *
	 * @param  companyId the primary key of the structure's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names of the models
	 *         the structures are related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         structure's name or description (optionally <code>null</code>)
	 * @return the number of matching structures
	 */
	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] classNameIds, String keywords) {

		return ddmStructureFinder.countByKeywords(
			companyId, groupIds, classNameIds, keywords);
	}

	/**
	 * Returns the number of structures matching the groups, class name IDs,
	 * name keyword, description keyword, storage type, and type
	 *
	 * @param  companyId the primary key of the structure's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names of the models
	 *         the structure's are related to
	 * @param  name the name keywords
	 * @param  description the description keywords
	 * @param  storageType the structure's storage type. It can be "xml" or
	 *         "expando". For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	 * @param  type the structure's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @return the number of matching structures
	 */
	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] classNameIds, String name,
		String description, String storageType, int type, boolean andOperator) {

		return ddmStructureFinder.countByC_G_C_N_D_S_T(
			companyId, groupIds, classNameIds, name, description, storageType,
			type, andOperator);
	}

	/**
	 * Updates the structure matching the structure ID, replacing its XSD with a
	 * new one.
	 *
	 * @param  structureId the primary key of the structure
	 * @param  definition the structure's new XML schema definition
	 * @param  serviceContext the service context to be applied. Can set the
	 *         structure's modification date.
	 * @return the updated structure
	 * @throws PortalException if a matching structure could not be found, if
	 *         the XSD was not well-formed, or if a portal exception occurred
	 */
	@Override
	public DDMStructure updateDefinition(
			long structureId, String definition, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		return doUpdateStructure(
			structure.getParentStructureId(), structure.getNameMap(),
			structure.getDescriptionMap(), definition, serviceContext,
			structure);
	}

	/**
	 * Updates the structure matching the class name ID, structure key, and
	 * group, replacing its old parent structure, name map, description map, and
	 * XSD with new ones.
	 *
	 * @param  groupId the primary key of the group
	 * @param  parentStructureId the primary key of the new parent structure
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  structureKey the unique string identifying the structure
	 * @param  nameMap the structure's new locales and localized names
	 * @param  descriptionMap the structure's new locales and localized
	 *         description
	 * @param  definition the structure's new XML schema definition
	 * @param  serviceContext the service context to be applied. Can set the
	 *         structure's modification date.
	 * @return the updated structure
	 * @throws PortalException if a matching structure could not be found, if
	 *         the XSD was not well-formed, or if a portal exception occurred
	 */
	@Override
	public DDMStructure updateStructure(
			long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String definition,
			ServiceContext serviceContext)
		throws PortalException {

		structureKey = getStructureKey(structureKey);

		DDMStructure structure = ddmStructurePersistence.findByG_C_S(
			groupId, classNameId, structureKey);

		return doUpdateStructure(
			parentStructureId, nameMap, descriptionMap, definition,
			serviceContext, structure);
	}

	/**
	 * Updates the structure matching the structure ID, replacing its old parent
	 * structure, name map, description map, and XSD with new ones.
	 *
	 * @param  structureId the primary key of the structure
	 * @param  parentStructureId the primary key of the new parent structure
	 * @param  nameMap the structure's new locales and localized names
	 * @param  descriptionMap the structure's new locales and localized
	 *         descriptions
	 * @param  definition the structure's new XML schema definition
	 * @param  serviceContext the service context to be applied. Can set the
	 *         structure's modification date.
	 * @return the updated structure
	 * @throws PortalException if a matching structure could not be found, if
	 *         the XSD was not well-formed, or if a portal exception occurred
	 */
	@Override
	public DDMStructure updateStructure(
			long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String definition, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		return doUpdateStructure(
			parentStructureId, nameMap, descriptionMap, definition,
			serviceContext, structure);
	}

	protected DDMStructureVersion addStructureVersion(
		DDMStructure structure, String version) {

		long structureVersionId = counterLocalService.increment();

		DDMStructureVersion structureVersion =
			ddmStructureVersionPersistence.create(structureVersionId);

		structureVersion.setGroupId(structure.getGroupId());
		structureVersion.setCompanyId(structure.getCompanyId());
		structureVersion.setUserId(structure.getUserId());
		structureVersion.setUserName(structure.getUserName());
		structureVersion.setCreateDate(structure.getModifiedDate());
		structureVersion.setStructureId(structure.getStructureId());
		structureVersion.setVersion(version);
		structureVersion.setName(structure.getName());
		structureVersion.setDescription(structure.getDescription());
		structureVersion.setDefinition(structure.getDefinition());
		structureVersion.setStorageType(structure.getStorageType());
		structureVersion.setType(structure.getType());

		ddmStructureVersionPersistence.update(structureVersion);

		return structureVersion;
	}

	protected Set<Long> deleteStructures(List<DDMStructure> structures)
		throws PortalException {

		Set<Long> deletedStructureIds = new HashSet<Long>();

		for (DDMStructure structure : structures) {
			if (deletedStructureIds.contains(structure.getStructureId())) {
				continue;
			}

			if (!GroupThreadLocal.isDeleteInProcess()) {
				List<DDMStructure> childDDMStructures =
					ddmStructurePersistence.findByParentStructureId(
						structure.getStructureId());

				deletedStructureIds.addAll(
					deleteStructures(childDDMStructures));
			}

			ddmStructureLocalService.deleteStructure(structure);

			deletedStructureIds.add(structure.getStructureId());
		}

		return deletedStructureIds;
	}

	protected DDMStructure doUpdateStructure(
			long parentStructureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String definition,
			ServiceContext serviceContext, DDMStructure structure)
		throws PortalException {

		// Structure

		DDMXMLUtil.validateXML(definition);

		DDMForm parentDDMForm = getParentDDMForm(parentStructureId);
		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		validate(nameMap, parentDDMForm, ddmForm);

		structure.setModifiedDate(serviceContext.getModifiedDate(null));
		structure.setParentStructureId(parentStructureId);

		DDMStructureVersion latestStructureVersion =
			ddmStructureVersionLocalService.getLatestStructureVersion(
				structure.getStructureId());

		String version = getNextVersion(
			latestStructureVersion.getVersion(), false);

		structure.setVersion(version);
		structure.setNameMap(nameMap);
		structure.setDescriptionMap(descriptionMap);
		structure.setDefinition(definition);

		ddmStructurePersistence.update(structure);

		// Structure templates

		syncStructureTemplatesFields(structure);

		// Structure version

		addStructureVersion(structure, version);

		// Indexer

		Indexer indexer = IndexerRegistryUtil.getIndexer(
			structure.getClassName());

		if (indexer != null) {
			List<Long> ddmStructureIds = getChildrenStructureIds(
				structure.getGroupId(), structure.getStructureId());

			indexer.reindexDDMStructures(ddmStructureIds);
		}

		return structure;
	}

	protected void getChildrenStructureIds(
			List<Long> structureIds, long groupId, long parentStructureId)
		throws PortalException {

		List<DDMStructure> structures = ddmStructurePersistence.findByG_P(
			groupId, parentStructureId);

		for (DDMStructure structure : structures) {
			structureIds.add(structure.getStructureId());

			getChildrenStructureIds(
				structureIds, structure.getGroupId(),
				structure.getStructureId());
		}
	}

	protected List<Long> getChildrenStructureIds(long groupId, long structureId)
		throws PortalException {

		List<Long> structureIds = new ArrayList<Long>();

		getChildrenStructureIds(structureIds, groupId, structureId);

		structureIds.add(0, structureId);

		return structureIds;
	}

	protected Set<String> getDDMFormFieldsNames(DDMForm ddmForm) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Set<String> ddmFormFieldsNames = new HashSet<String>(
			ddmFormFieldsMap.size());

		for (String ddmFormFieldName : ddmFormFieldsMap.keySet()) {
			if (ddmFormFieldName.startsWith(StringPool.UNDERLINE)) {
				continue;
			}

			ddmFormFieldsNames.add(StringUtil.toLowerCase(ddmFormFieldName));
		}

		return ddmFormFieldsNames;
	}

	protected String getNextVersion(String version, boolean majorVersion) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		if (majorVersion) {
			versionParts[0]++;
			versionParts[1] = 0;
		}
		else {
			versionParts[1]++;
		}

		return versionParts[0] + StringPool.PERIOD + versionParts[1];
	}

	protected DDMForm getParentDDMForm(long parentStructureId) {
		DDMStructure parentStructure =
			ddmStructurePersistence.fetchByPrimaryKey(parentStructureId);

		if (parentStructure == null) {
			return null;
		}

		return parentStructure.getFullHierarchyDDMForm();
	}

	protected String getStructureKey(String structureKey) {
		if (structureKey != null) {
			structureKey = structureKey.trim();

			return StringUtil.toUpperCase(structureKey);
		}

		return StringPool.BLANK;
	}

	protected List<DDMTemplate> getStructureTemplates(
		DDMStructure structure, String type) {

		long classNameId = classNameLocalService.getClassNameId(
			DDMStructure.class);

		return ddmTemplateLocalService.getTemplates(
			structure.getGroupId(), classNameId, structure.getStructureId(),
			type);
	}

	protected void syncStructureTemplatesFields(final DDMStructure structure) {
		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					DDMFormTemplateSynchonizer ddmFormTemplateSynchonizer =
						new DDMFormTemplateSynchonizer(structure.getDDMForm());

					List<DDMTemplate> templates = getStructureTemplates(
						structure, DDMTemplateConstants.TEMPLATE_TYPE_FORM);

					ddmFormTemplateSynchonizer.setDDMFormTemplates(templates);

					ddmFormTemplateSynchonizer.synchronize();

					return null;
				}

			});
	}

	protected void validate(DDMForm parentDDMForm, DDMForm ddmForm)
		throws PortalException {

		Set<String> commonDDMFormFieldNames = SetUtil.intersect(
			getDDMFormFieldsNames(parentDDMForm),
			getDDMFormFieldsNames(ddmForm));

		if (!commonDDMFormFieldNames.isEmpty()) {
			throw new StructureDuplicateElementException();
		}
	}

	protected void validate(
			long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap, DDMForm ddmForm)
		throws PortalException {

		structureKey = getStructureKey(structureKey);

		DDMStructure structure = ddmStructurePersistence.fetchByG_C_S(
			groupId, classNameId, structureKey);

		if (structure != null) {
			StructureDuplicateStructureKeyException sdske =
				new StructureDuplicateStructureKeyException();

			sdske.setStructureKey(structure.getStructureKey());

			throw sdske;
		}

		DDMForm parentDDMForm = getParentDDMForm(parentStructureId);

		validate(nameMap, parentDDMForm, ddmForm);
	}

	protected void validate(
			Map<Locale, String> nameMap, DDMForm parentDDMForm, DDMForm ddmForm)
		throws PortalException {

		try {
			validate(nameMap, ddmForm.getDefaultLocale());

			if (parentDDMForm != null) {
				validate(parentDDMForm, ddmForm);
			}
		}
		catch (LocaleException le) {
			throw le;
		}
		catch (StructureDuplicateElementException sdee) {
			throw sdee;
		}
		catch (StructureNameException sne) {
			throw sne;
		}
		catch (StructureDefinitionException sde) {
			throw sde;
		}
		catch (Exception e) {
			throw new StructureDefinitionException();
		}
	}

	protected void validate(
			Map<Locale, String> nameMap, Locale contentDefaultLocale)
		throws PortalException {

		String name = nameMap.get(contentDefaultLocale);

		if (Validator.isNull(name)) {
			throw new StructureNameException();
		}

		Locale[] availableLocales = LanguageUtil.getAvailableLocales();

		if (!ArrayUtil.contains(availableLocales, contentDefaultLocale)) {
			Long companyId = CompanyThreadLocal.getCompanyId();

			LocaleException le = new LocaleException(
				LocaleException.TYPE_CONTENT,
				"The locale " + contentDefaultLocale +
					" is not available in company " + companyId);

			le.setSourceAvailableLocales(new Locale[] {contentDefaultLocale});
			le.setTargetAvailableLocales(availableLocales);

			throw le;
		}
	}

}