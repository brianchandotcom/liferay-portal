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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.exception.InvalidStructureVersionException;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.exception.RequiredStructureException;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.exception.StructureDuplicateElementException;
import com.liferay.dynamic.data.mapping.exception.StructureDuplicateStructureKeyException;
import com.liferay.dynamic.data.mapping.exception.StructureNameException;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.service.permission.DDMStructurePermission;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.dynamic.data.mapping.util.DDMXMLUtil;
import com.liferay.dynamic.data.mapping.util.impl.DDMFormTemplateSynchonizer;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidator;
import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.DDMStructureIndexer;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.permission.ModelPermissions;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.Collections;
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
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException {

		// Structure

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(structureKey)) {
			structureKey = String.valueOf(counterLocalService.increment());
		}
		else {
			structureKey = StringUtil.toUpperCase(structureKey.trim());
		}

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
		structure.setVersionUserId(user.getUserId());
		structure.setVersionUserName(user.getFullName());
		structure.setParentStructureId(parentStructureId);
		structure.setClassNameId(classNameId);
		structure.setStructureKey(structureKey);
		structure.setVersion(DDMStructureConstants.VERSION_DEFAULT);
		structure.setNameMap(nameMap);
		structure.setDescriptionMap(descriptionMap);
		structure.setDefinition(DDMFormJSONSerializerUtil.serialize(ddmForm));
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
				structure, serviceContext.getModelPermissions());
		}

		// Structure version

		DDMStructureVersion structureVersion = addStructureVersion(
			user, structure, DDMStructureConstants.VERSION_DEFAULT,
			serviceContext);

		// Structure layout

		ddmStructureLayoutLocalService.addStructureLayout(
			userId, groupId, structureVersion.getStructureVersionId(),
			ddmFormLayout, serviceContext);

		return structure;
	}

	/**
	 * Adds a structure referencing its parent structure.
	 *
	 * @param      userId the primary key of the structure's creator/owner
	 * @param      groupId the primary key of the group
	 * @param      parentStructureId the primary key of the parent structure
	 *             (optionally {@link
	 *             DDMStructureConstants#DEFAULT_PARENT_STRUCTURE_ID})
	 * @param      classNameId the primary key of the class name for the
	 *             structure's related model
	 * @param      structureKey the unique string identifying the structure
	 *             (optionally <code>null</code>)
	 * @param      nameMap the structure's locales and localized names
	 * @param      descriptionMap the structure's locales and localized
	 *             descriptions
	 * @param      definition the structure's XML schema definition
	 * @param      storageType the structure's storage type. It can be "xml" or
	 *             "expando". For more information, see {@link StorageType}.
	 * @param      type the structure's type. For more information, see {@link
	 *             DDMStructureConstants}.
	 * @param      serviceContext the service context to be applied. Can set the
	 *             UUID, creation date, modification date, guest permissions,
	 *             and group permissions for the structure.
	 * @return     the structure
	 * @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	 *             long, long, String, Map, Map, DDMForm, DDMFormLayout, String,
	 *             int, ServiceContext)}
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

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return addStructure(
			userId, groupId, parentStructureId, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType, type,
			serviceContext);
	}

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, long classNameId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout, String storageType,
			ServiceContext serviceContext)
		throws PortalException {

		return addStructure(
			userId, groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			classNameId, null, nameMap, descriptionMap, ddmForm, ddmFormLayout,
			storageType, DDMStructureConstants.TYPE_DEFAULT, serviceContext);
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
	 * @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	 *             long, Map, Map, DDMForm, DDMFormLayout, ServiceContext)}
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
			StorageType.JSON.toString(), DDMStructureConstants.TYPE_DEFAULT,
			serviceContext);
	}

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
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
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType, type,
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
	 *             "expando". For more information, see {@link StorageType}.
	 * @param      type the structure's type. For more information, see {@link
	 *             DDMStructureConstants}.
	 * @param      serviceContext the service context to be applied. Can set the
	 *             UUID, creation date, modification date, guest permissions and
	 *             group permissions for the structure.
	 * @return     the structure
	 * @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	 *             String, long, String, Map, Map, DDMForm, DDMFormLayout,
	 *             String, int, ServiceContext)}
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

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return addStructure(
			userId, groupId, parentStructureKey, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType, type,
			serviceContext);
	}

	/**
	 * Adds the resources to the structure.
	 *
	 * @param structure the structure to add resources to
	 * @param addGroupPermissions whether to add group permissions
	 * @param addGuestPermissions whether to add guest permissions
	 */
	@Override
	public void addStructureResources(
			DDMStructure structure, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		String resourceName =
			DDMStructurePermission.getStructureModelResourceName(
				structure.getClassNameId());

		resourceLocalService.addResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), resourceName, structure.getStructureId(),
			false, addGroupPermissions, addGuestPermissions);
	}

	/**
	 * Adds the model resources with the permissions to the structure.
	 *
	 * @param structure the structure to add resources to
	 * @param modelPermissions the model permissions to be added
	 */
	@Override
	public void addStructureResources(
			DDMStructure structure, ModelPermissions modelPermissions)
		throws PortalException {

		String resourceName =
			DDMStructurePermission.getStructureModelResourceName(
				structure.getClassNameId());

		resourceLocalService.addModelResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), resourceName, structure.getStructureId(),
			modelPermissions);
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
			structure.getDDMForm(), structure.getDDMFormLayout(),
			structure.getStorageType(), structure.getType(), serviceContext);
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
			structure.getDescriptionMap(), structure.getDDMForm(),
			structure.getDDMFormLayout(), structure.getStorageType(),
			structure.getType(), serviceContext);
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
	 */
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteStructure(DDMStructure structure) throws PortalException {
		if (!GroupThreadLocal.isDeleteInProcess()) {
			String classNameDDLRecordSet =
				"com.liferay.dynamic.data.lists.model.DDLRecordSet";

			if (structure.getClassName().equals(classNameDDLRecordSet)) {
				if (ddmStructureLinkPersistence.countByStructureId(
						structure.getStructureId()) > 0) {

				throw new RequiredStructureException.
					MustNotDeleteStructureReferencedByStructureLinks(
						structure.getStructureId());
				}
			}

			if (ddmStructurePersistence.countByParentStructureId(
					structure.getStructureId()) > 0) {

				throw new RequiredStructureException.
					MustNotDeleteStructureThatHasChild(
						structure.getStructureId());
			}

			long classNameId = classNameLocalService.getClassNameId(
				DDMStructure.class);

			if (ddmTemplatePersistence.countByG_C_C(
					structure.getGroupId(), classNameId,
					structure.getPrimaryKey()) > 0) {

				throw new RequiredStructureException.
					MustNotDeleteStructureReferencedByTemplates(
						structure.getStructureId());
			}
		}

		// Structure

		ddmStructurePersistence.remove(structure);

		// Structure links

		ddmStructureLinkPersistence.removeByStructureId(
			structure.getStructureId());

		// Structure versions

		List<DDMStructureVersion> structureVersions =
			ddmStructureVersionLocalService.getStructureVersions(
				structure.getStructureId());

		for (DDMStructureVersion structureVersion : structureVersions) {
			ddmStructureLayoutPersistence.removeByStructureVersionId(
				structureVersion.getStructureVersionId());

			ddmStructureVersionPersistence.remove(structureVersion);
		}

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
	 * @param structureId the primary key of the structure to be deleted
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
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the class name for the structure's
	 *        related model
	 * @param structureKey the unique string identifying the structure
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
	 * @param groupId the primary key of the group
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 * Returns the structure with the ID.
	 *
	 * @param  structureId the primary key of the structure
	 * @return the structure with the ID
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
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

	@Override
	public void revertStructure(
			long userId, long structureId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructureVersion structureVersion =
			ddmStructureVersionLocalService.getStructureVersion(
				structureId, version);

		if (!structureVersion.isApproved()) {
			throw new InvalidStructureVersionException(
				"Unable to revert from an unapproved file version");
		}

		DDMStructure structure = structureVersion.getStructure();

		serviceContext.setAttribute("majorVersion", Boolean.TRUE);
		serviceContext.setAttribute(
			"status", WorkflowConstants.STATUS_APPROVED);
		serviceContext.setCommand(Constants.REVERT);

		ddmStructureLocalService.updateStructure(
			userId, structure.getGroupId(),
			structureVersion.getParentStructureId(), structure.getClassNameId(),
			structure.getStructureKey(), structureVersion.getNameMap(),
			structureVersion.getDescriptionMap(), structureVersion.getDDMForm(),
			structureVersion.getDDMFormLayout(), serviceContext);
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
	 * @param  companyId the primary key of the structure's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameId the primary key of the class name of the model the
	 *         structure is related to
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
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructureFinder.findByKeywords(
			companyId, groupIds, classNameId, keywords, status, start, end,
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
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the structure's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameId the primary key of the class name of the model the
	 *         structure is related to
	 * @param  name the name keywords
	 * @param  description the description keywords
	 * @param  storageType the structure's storage type. It can be "xml" or
	 *         "expando". For more information, see {@link StorageType}.
	 * @param  type the structure's type. For more information, see {@link
	 *         DDMStructureConstants}.
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
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructureFinder.findByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, name, description, storageType,
			type, status, andOperator, start, end, orderByComparator);
	}

	/**
	 * Returns the number of structures matching the groups and class name IDs,
	 * and matching the keywords in the structure names and descriptions.
	 *
	 * @param  companyId the primary key of the structure's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameId the primary key of the class name of the model the
	 *         structure is related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         structure's name or description (optionally <code>null</code>)
	 * @return the number of matching structures
	 */
	@Override
	public int searchCount(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status) {

		return ddmStructureFinder.countByKeywords(
			companyId, groupIds, classNameId, keywords, status);
	}

	/**
	 * Returns the number of structures matching the groups, class name IDs,
	 * name keyword, description keyword, storage type, and type
	 *
	 * @param  companyId the primary key of the structure's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameId
	 * @param  name the name keywords
	 * @param  description the description keywords
	 * @param  storageType the structure's storage type. It can be "xml" or
	 *         "expando". For more information, see {@link StorageType}.
	 * @param  type the structure's type. For more information, see {@link
	 *         DDMStructureConstants}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @return the number of matching structures
	 */
	@Override
	public int searchCount(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator) {

		return ddmStructureFinder.countByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, name, description, storageType,
			type, status, andOperator);
	}

	@Override
	public DDMStructure updateStructure(
			long userId, long structureId, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		return doUpdateStructure(
			userId, structure.getParentStructureId(), structure.getNameMap(),
			structure.getDescriptionMap(), ddmForm, ddmFormLayout,
			serviceContext, structure);
	}

	@Override
	public DDMStructure updateStructure(
			long userId, long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, ServiceContext serviceContext)
		throws PortalException {

		structureKey = getStructureKey(structureKey);

		DDMStructure structure = ddmStructurePersistence.findByG_C_S(
			groupId, classNameId, structureKey);

		return doUpdateStructure(
			userId, parentStructureId, nameMap, descriptionMap, ddmForm,
			ddmFormLayout, serviceContext, structure);
	}

	@Override
	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		return doUpdateStructure(
			userId, parentStructureId, nameMap, descriptionMap, ddmForm,
			ddmFormLayout, serviceContext, structure);
	}

	/**
	 * Updates the structure matching the class name ID, structure key, and
	 * group, replacing its old parent structure, name map, description map, and
	 * XSD with new ones.
	 *
	 * @param      groupId the primary key of the group
	 * @param      parentStructureId the primary key of the new parent structure
	 * @param      classNameId the primary key of the class name for the
	 *             structure's related model
	 * @param      structureKey the unique string identifying the structure
	 * @param      nameMap the structure's new locales and localized names
	 * @param      descriptionMap the structure's new locales and localized
	 *             description
	 * @param      definition the structure's new XML schema definition
	 * @param      serviceContext the service context to be applied. Can set the
	 *             structure's modification date.
	 * @return     the updated structure
	 * @deprecated As of 7.0.0, replaced by {@link #updateStructure(long, long,
	 *             long, long, String, Map, Map, DDMForm, DDMFormLayout,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDMStructure updateStructure(
			long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String definition,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByG_C_S(
			groupId, classNameId, structureKey);

		long userId = PortalUtil.getValidUserId(
			structure.getCompanyId(), serviceContext.getUserId());

		DDMXMLUtil.validateXML(definition);

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		structureKey = getStructureKey(structureKey);

		return doUpdateStructure(
			userId, parentStructureId, nameMap, descriptionMap, ddmForm,
			ddmFormLayout, serviceContext, structure);
	}

	/**
	 * Updates the structure matching the structure ID, replacing its old parent
	 * structure, name map, description map, and XSD with new ones.
	 *
	 * @param      structureId the primary key of the structure
	 * @param      parentStructureId the primary key of the new parent structure
	 * @param      nameMap the structure's new locales and localized names
	 * @param      descriptionMap the structure's new locales and localized
	 *             descriptions
	 * @param      definition the structure's new XML schema definition
	 * @param      serviceContext the service context to be applied. Can set the
	 *             structure's modification date.
	 * @return     the updated structure
	 * @deprecated As of 7.0.0, replaced by {@link #updateStructure(long, long,
	 *             long, Map, Map, DDMForm, DDMFormLayout, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDMStructure updateStructure(
			long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String definition, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		long userId = PortalUtil.getValidUserId(
			structure.getCompanyId(), serviceContext.getUserId());

		DDMXMLUtil.validateXML(definition);

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return doUpdateStructure(
			userId, parentStructureId, nameMap, descriptionMap, ddmForm,
			ddmFormLayout, serviceContext, structure);
	}

	/**
	 * Updates the structure matching the structure ID, replacing its XSD with a
	 * new one.
	 *
	 * @param      structureId the primary key of the structure
	 * @param      definition the structure's new XML schema definition
	 * @param      serviceContext the service context to be applied. Can set the
	 *             structure's modification date.
	 * @return     the updated structure
	 * @deprecated As of 7.0.0, replaced by {@link #updateStructure(long,
	 *             DDMForm, DDMFormLayout, ServiceContext)}
	 */
	@Deprecated
	@Override
	public DDMStructure updateXSD(
			long structureId, String definition, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		long userId = PortalUtil.getValidUserId(
			structure.getCompanyId(), serviceContext.getUserId());

		DDMXMLUtil.validateXML(definition);

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		return doUpdateStructure(
			userId, structure.getParentStructureId(), structure.getNameMap(),
			structure.getDescriptionMap(), ddmForm, ddmFormLayout,
			serviceContext, structure);
	}

	protected DDMStructureVersion addStructureVersion(
		User user, DDMStructure structure, String version,
		ServiceContext serviceContext) {

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
		structureVersion.setParentStructureId(structure.getParentStructureId());
		structureVersion.setName(structure.getName());
		structureVersion.setDescription(structure.getDescription());
		structureVersion.setDefinition(structure.getDefinition());
		structureVersion.setStorageType(structure.getStorageType());
		structureVersion.setType(structure.getType());

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_APPROVED);

		structureVersion.setStatus(status);

		structureVersion.setStatusByUserId(user.getUserId());
		structureVersion.setStatusByUserName(user.getFullName());
		structureVersion.setStatusDate(structure.getModifiedDate());

		ddmStructureVersionPersistence.update(structureVersion);

		return structureVersion;
	}

	protected Set<Long> deleteStructures(List<DDMStructure> structures)
		throws PortalException {

		Set<Long> deletedStructureIds = new HashSet<>();

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
			long userId, long parentStructureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, ServiceContext serviceContext,
			DDMStructure structure)
		throws PortalException {

		// Structure

		User user = userPersistence.findByPrimaryKey(userId);

		DDMForm parentDDMForm = getParentDDMForm(parentStructureId);

		validate(nameMap, parentDDMForm, ddmForm);

		structure.setParentStructureId(parentStructureId);

		DDMStructureVersion latestStructureVersion =
			ddmStructureVersionLocalService.getLatestStructureVersion(
				structure.getStructureId());

		boolean majorVersion = GetterUtil.getBoolean(
			serviceContext.getAttribute("majorVersion"));

		String version = getNextVersion(
			latestStructureVersion.getVersion(), majorVersion);

		structure.setVersion(version);
		structure.setNameMap(nameMap);
		structure.setVersionUserId(user.getUserId());
		structure.setVersionUserName(user.getFullName());
		structure.setDescriptionMap(descriptionMap);
		structure.setDefinition(DDMFormJSONSerializerUtil.serialize(ddmForm));

		// Structure version

		DDMStructureVersion structureVersion = addStructureVersion(
			user, structure, version, serviceContext);

		// Structure layout

		ddmStructureLayoutLocalService.addStructureLayout(
			structureVersion.getUserId(), structureVersion.getGroupId(),
			structureVersion.getStructureVersionId(), ddmFormLayout,
			serviceContext);

		if (!structureVersion.isApproved()) {
			return structure;
		}

		ddmStructurePersistence.update(structure);

		// Structure templates

		syncStructureTemplatesFields(structure);

		// Indexer

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			structure.getClassName());

		if (indexer instanceof DDMStructureIndexer) {
			DDMStructureIndexer ddmStructureIndexer =
				(DDMStructureIndexer)indexer;

			List<Long> ddmStructureIds = getChildrenStructureIds(
				structure.getGroupId(), structure.getStructureId());

			ddmStructureIndexer.reindexDDMStructures(ddmStructureIds);
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

		List<Long> structureIds = new ArrayList<>();

		getChildrenStructureIds(structureIds, groupId, structureId);

		structureIds.add(0, structureId);

		return structureIds;
	}

	protected Set<String> getDDMFormFieldsNames(DDMForm ddmForm) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Set<String> ddmFormFieldsNames = new HashSet<>(ddmFormFieldsMap.size());

		for (String ddmFormFieldName : ddmFormFieldsMap.keySet()) {
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
		TransactionCommitCallbackUtil.registerCallback(
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

	protected void validate(DDMForm ddmForm) throws PortalException {
		ddmFormValidator.validate(ddmForm);
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

			validate(ddmForm);

			if (parentDDMForm != null) {
				validate(parentDDMForm, ddmForm);
			}
		}
		catch (DDMFormValidationException ddmfve) {
			throw ddmfve;
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

		if (!LanguageUtil.isAvailableLocale(contentDefaultLocale)) {
			Long companyId = CompanyThreadLocal.getCompanyId();

			LocaleException le = new LocaleException(
				LocaleException.TYPE_CONTENT,
				"The locale " + contentDefaultLocale +
					" is not available in company " + companyId);

			le.setSourceAvailableLocales(
				Collections.singleton(contentDefaultLocale));
			le.setTargetAvailableLocales(LanguageUtil.getAvailableLocales());

			throw le;
		}
	}

	@ServiceReference(type = DDMFormValidator.class)
	protected DDMFormValidator ddmFormValidator;

}