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

import com.liferay.dynamic.data.mapping.exception.InvalidParentStructureException;
import com.liferay.dynamic.data.mapping.exception.InvalidStructureVersionException;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.exception.RequiredStructureException;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.exception.StructureDuplicateElementException;
import com.liferay.dynamic.data.mapping.exception.StructureDuplicateStructureKeyException;
import com.liferay.dynamic.data.mapping.exception.StructureNameException;
import com.liferay.dynamic.data.mapping.internal.background.task.DDMStructureIndexerBackgroundTaskExecutor;
import com.liferay.dynamic.data.mapping.internal.background.task.DDMStructureIndexerTracker;
import com.liferay.dynamic.data.mapping.internal.search.util.DDMSearchHelper;
import com.liferay.dynamic.data.mapping.internal.util.DDMFormTemplateSynchonizer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMXML;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidator;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.DDMStructureIndexer;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure",
	service = AopService.class
)
public class DDMStructureLocalServiceImpl
	extends DDMStructureLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructure addStructure(
			long userId, long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException {

		// Structure

		User user = userLocalService.getUser(userId);

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
		structure.setDescriptionMap(descriptionMap, ddmForm.getDefaultLocale());
		structure.setNameMap(nameMap, ddmForm.getDefaultLocale());
		structure.setDefinition(serializeJSONDDMForm(ddmForm));
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

		_ddmStructureLayoutLocalService.addStructureLayout(
			userId, groupId, structureVersion.getStructureVersionId(),
			ddmFormLayout, serviceContext);

		// Data provider instance links

		addDataProviderInstanceLinks(groupId, structureId, ddmForm);

		return structure;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructure addStructure(
			long userId, long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String definition,
			String storageType, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		if (Validator.isNull(structureKey)) {
			structureKey = String.valueOf(counterLocalService.increment());
		}
		else {
			structureKey = StringUtil.toUpperCase(structureKey.trim());
		}

		long structureId = counterLocalService.increment();

		DDMStructure structure = ddmStructurePersistence.create(structureId);

		structure.setUuid(serviceContext.getUuid());
		structure.setGroupId(groupId);
		structure.setCompanyId(user.getCompanyId());
		structure.setUserId(user.getUserId());
		structure.setUserName(user.getFullName());
		structure.setVersionUserId(user.getUserId());
		structure.setVersionUserName(user.getFullName());
		structure.setCreateDate(new Date());
		structure.setModifiedDate(new Date());
		structure.setParentStructureId(parentStructureId);
		structure.setClassNameId(classNameId);
		structure.setStructureKey(structureKey);
		structure.setVersion(DDMStructureConstants.VERSION_DEFAULT);
		structure.setNameMap(nameMap);
		structure.setDescriptionMap(descriptionMap);
		structure.setDefinition(definition);
		structure.setStorageType(storageType);
		structure.setType(DDMStructureConstants.TYPE_DEFAULT);

		ddmStructurePersistence.update(structure);

		addStructureVersion(
			user, structure, DDMStructureConstants.VERSION_DEFAULT,
			serviceContext);

		return structure;
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
			_ddmPermissionSupport.getStructureModelResourceName(
				structure.getClassName());

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
			_ddmPermissionSupport.getStructureModelResourceName(
				structure.getClassName());

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
	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public DDMStructure deleteStructure(DDMStructure structure)
		throws PortalException {

		if (!GroupThreadLocal.isDeleteInProcess()) {
			int count = ddmStructureLinkPersistence.countByStructureId(
				structure.getStructureId());

			if (count > 0) {
				throw new RequiredStructureException.
					MustNotDeleteStructureReferencedByStructureLinks(
						structure.getStructureId());
			}

			count = ddmStructurePersistence.countByParentStructureId(
				structure.getStructureId());

			if (count > 0) {
				throw new RequiredStructureException.
					MustNotDeleteStructureThatHasChild(
						structure.getStructureId());
			}

			count = ddmTemplatePersistence.countByClassPK(
				structure.getStructureId());

			if (count > 0) {
				throw new RequiredStructureException.
					MustNotDeleteStructureReferencedByTemplates(
						structure.getStructureId());
			}
		}

		// Structure

		ddmStructurePersistence.remove(structure);

		// Data provider instance links

		ddmDataProviderInstanceLinkPersistence.removeByStructureId(
			structure.getStructureId());

		// Structure links

		ddmStructureLinkPersistence.removeByStructureId(
			structure.getStructureId());

		// Structure versions

		List<DDMStructureVersion> structureVersions =
			_ddmStructureVersionLocalService.getStructureVersions(
				structure.getStructureId());

		for (DDMStructureVersion structureVersion : structureVersions) {
			ddmStructureLayoutPersistence.removeByStructureVersionId(
				structureVersion.getStructureVersionId());

			ddmStructureVersionPersistence.remove(structureVersion);
		}

		// Resources

		String resourceName =
			_ddmPermissionSupport.getStructureModelResourceName(
				structure.getClassName());

		resourceLocalService.deleteResource(
			structure.getCompanyId(), resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, structure.getStructureId());

		// Background tasks

		String backgroundTaskName =
			DDMStructureIndexerBackgroundTaskExecutor.getBackgroundTaskName(
				structure.getStructureId());

		_backgroundTaskManager.deleteGroupBackgroundTasks(
			structure.getGroupId(), backgroundTaskName,
			DDMStructureIndexerBackgroundTaskExecutor.class.getName());

		return structure;
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
		boolean includeAncestorStructures) {

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
				_portal.getAncestorSiteGroupIds(groupId)) {

			structure = ddmStructurePersistence.fetchByG_C_S(
				ancestorSiteGroupId, classNameId, structureKey);

			if (structure != null) {
				return structure;
			}
		}

		return null;
	}

	@Override
	public DDMStructure fetchStructureByUuidAndGroupId(
		String uuid, long groupId, boolean includeAncestorStructures) {

		DDMStructure structure = ddmStructurePersistence.fetchByUUID_G(
			uuid, groupId);

		if (structure != null) {
			return structure;
		}

		if (!includeAncestorStructures) {
			return null;
		}

		for (long ancestorSiteGroupId :
				_portal.getAncestorSiteGroupIds(groupId)) {

			structure = ddmStructurePersistence.fetchByUUID_G(
				uuid, ancestorSiteGroupId);

			if (structure != null) {
				return structure;
			}
		}

		return null;
	}

	@Override
	public List<DDMStructure> getChildrenStructures(long parentStructureId) {
		return ddmStructurePersistence.findByParentStructureId(
			parentStructureId);
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

		for (long curGroupId : _portal.getAncestorSiteGroupIds(groupId)) {
			structure = ddmStructurePersistence.fetchByG_C_S(
				curGroupId, classNameId, structureKey);

			if (structure != null) {
				return structure;
			}
		}

		throw new NoSuchStructureException(
			"No DDMStructure exists with the structure key " + structureKey +
				" in the ancestor groups");
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

	@Override
	@Transactional(enabled = false)
	public DDMForm getStructureDDMForm(DDMStructure structure)
		throws PortalException {

		return deserializeJSONDDMForm(structure.getDefinition());
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
		long companyId, long[] groupIds, long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructureFinder.findByC_G_C_S(
			companyId, groupIds, classNameId, WorkflowConstants.STATUS_ANY,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMStructure> getStructures(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructureFinder.findByKeywords(
			companyId, groupIds, classNameId, keywords, status, start, end,
			orderByComparator);
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

	@Override
	public List<DDMStructure> getStructures(
		long[] groupIds, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructurePersistence.findByG_C(
			groupIds, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the structures matching the group, class
	 * name ID, name, and description.
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
	 * @param  name the name keywords
	 * @param  description the description keywords
	 * @param  start the lower bound of the range of structures to return
	 * @param  end the upper bound of the range of structures to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the structures
	 *         (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	@Override
	public List<DDMStructure> getStructures(
		long[] groupIds, long classNameId, String name, String description,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructurePersistence.findByG_C_N_D(
			groupIds, classNameId, name, description, start, end,
			orderByComparator);
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

	@Override
	public int getStructuresCount(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status) {

		return ddmStructureFinder.countByKeywords(
			companyId, groupIds, classNameId, keywords, status);
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
	public String prepareLocalizedDefinitionForImport(
		DDMStructure structure, Locale defaultImportLocale) {

		DDMForm ddmForm = _ddm.updateDDMFormDefaultLocale(
			structure.getDDMForm(), defaultImportLocale);

		return serializeJSONDDMForm(ddmForm);
	}

	@Override
	public void revertStructure(
			long userId, long structureId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructureVersion structureVersion =
			_ddmStructureVersionLocalService.getStructureVersion(
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

	@Override
	public List<DDMStructure> search(
			long companyId, long[] groupIds, long classNameId, long classPK,
			String keywords, int start, int end,
			OrderByComparator<DDMStructure> orderByComparator)
		throws PortalException {

		SearchContext searchContext =
			_ddmSearchHelper.buildStructureSearchContext(
				companyId, groupIds, classNameId, classPK, keywords, keywords,
				StringPool.BLANK, null, WorkflowConstants.STATUS_ANY, start,
				end, orderByComparator);

		return _ddmSearchHelper.doSearch(
			searchContext, DDMStructure.class,
			ddmStructurePersistence::findByPrimaryKey);
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

		SearchContext searchContext =
			_ddmSearchHelper.buildStructureSearchContext(
				companyId, groupIds, classNameId, null, keywords, keywords,
				StringPool.BLANK, null, status, start, end, orderByComparator);

		return _ddmSearchHelper.doSearch(
			searchContext, DDMStructure.class,
			ddmStructurePersistence::findByPrimaryKey);
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

		SearchContext searchContext =
			_ddmSearchHelper.buildStructureSearchContext(
				companyId, groupIds, classNameId, null, name, description,
				storageType, type, status, start, end, orderByComparator);

		return _ddmSearchHelper.doSearch(
			searchContext, DDMStructure.class,
			ddmStructurePersistence::findByPrimaryKey);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long classNameId, long classPK,
			String keywords)
		throws PortalException {

		SearchContext searchContext =
			_ddmSearchHelper.buildStructureSearchContext(
				companyId, groupIds, classNameId, classPK, keywords, keywords,
				StringPool.BLANK, null, WorkflowConstants.STATUS_ANY,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return _ddmSearchHelper.doSearchCount(
			searchContext, DDMStructure.class);
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

		SearchContext searchContext =
			_ddmSearchHelper.buildStructureSearchContext(
				companyId, groupIds, classNameId, null, keywords, keywords,
				StringPool.BLANK, null, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		return _ddmSearchHelper.doSearchCount(
			searchContext, DDMStructure.class);
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

		SearchContext searchContext =
			_ddmSearchHelper.buildStructureSearchContext(
				companyId, groupIds, classNameId, null, name, description,
				storageType, type, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		return _ddmSearchHelper.doSearchCount(
			searchContext, DDMStructure.class);
	}

	@Indexable(type = IndexableType.REINDEX)
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

	@Indexable(type = IndexableType.REINDEX)
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

	@Indexable(type = IndexableType.REINDEX)
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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String definition, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		User user = userLocalService.getUser(userId);

		structure.setUserId(userId);
		structure.setParentStructureId(parentStructureId);

		DDMStructureVersion latestStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				structure.getStructureId());

		structure.setVersion(latestStructureVersion.getVersion());

		structure.setNameMap(nameMap);
		structure.setVersionUserId(user.getUserId());
		structure.setVersionUserName(user.getFullName());
		structure.setModifiedDate(new Date());
		structure.setDescriptionMap(descriptionMap);
		structure.setDefinition(definition);

		ddmStructurePersistence.update(structure);

		reindexStructure(structure, serviceContext);

		return structure;
	}

	protected void addDataProviderInstanceLinks(
		long groupId, long structureId, DDMForm ddmForm) {

		Set<Long> dataProviderInstanceIds = getDataProviderInstanceIds(
			groupId, ddmForm);

		for (Long dataProviderInstanceId : dataProviderInstanceIds) {
			_ddmDataProviderInstanceLinkLocalService.
				addDataProviderInstanceLink(
					dataProviderInstanceId, structureId);
		}
	}

	protected DDMStructureVersion addStructureVersion(
		User user, DDMStructure structure, String version,
		ServiceContext serviceContext) {

		long structureVersionId = counterLocalService.increment();

		DDMStructureVersion structureVersion =
			ddmStructureVersionPersistence.create(structureVersionId);

		structureVersion.setGroupId(structure.getGroupId());
		structureVersion.setCompanyId(structure.getCompanyId());
		structureVersion.setUserId(user.getUserId());
		structureVersion.setUserName(user.getFullName());
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

		if (ListUtil.isEmpty(structures)) {
			return Collections.emptySet();
		}

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

	protected DDMForm deserializeDDMForm(
		String content, DDMFormDeserializer ddmFormDeserializer) {

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	protected DDMForm deserializeJSONDDMForm(String content) {
		return deserializeDDMForm(content, _jsonDDMFormDeserializer);
	}

	protected DDMForm deserializeXSDDDMForm(String content) {
		return deserializeDDMForm(content, _xsdDDMFormDeserializer);
	}

	protected DDMStructure doUpdateStructure(
			long userId, long parentStructureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, ServiceContext serviceContext,
			DDMStructure structure)
		throws PortalException {

		// Structure

		User user = userLocalService.getUser(userId);

		DDMForm parentDDMForm = getParentDDMForm(parentStructureId);

		validateParentStructure(structure.getStructureId(), parentStructureId);
		validate(nameMap, parentDDMForm, ddmForm);

		structure.setUserId(userId);
		structure.setParentStructureId(parentStructureId);

		DDMStructureVersion latestStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				structure.getStructureId());

		boolean majorVersion = GetterUtil.getBoolean(
			serviceContext.getAttribute("majorVersion"));

		String version = getNextVersion(
			latestStructureVersion.getVersion(), majorVersion);

		structure.setVersion(version);

		structure.setNameMap(nameMap, ddmForm.getDefaultLocale());
		structure.setVersionUserId(user.getUserId());
		structure.setVersionUserName(user.getFullName());
		structure.setDescriptionMap(descriptionMap, ddmForm.getDefaultLocale());
		structure.setDefinition(serializeJSONDDMForm(ddmForm));

		// Structure version

		DDMStructureVersion structureVersion = addStructureVersion(
			user, structure, version, serviceContext);

		// Structure layout

		// Explicitly pop UUID from service context to ensure no lingering
		// values remain there from other components (e.g. Journal)

		serviceContext.getUuid();

		_ddmStructureLayoutLocalService.addStructureLayout(
			structureVersion.getUserId(), structureVersion.getGroupId(),
			structureVersion.getStructureVersionId(), ddmFormLayout,
			serviceContext);

		if (!structureVersion.isApproved() && structureVersion.isPending()) {
			return structure;
		}

		ddmStructurePersistence.update(structure);

		// Structure templates

		syncStructureTemplatesFields(structure);

		// Data provider instance links

		updateDataProviderInstanceLinks(
			structure.getGroupId(), structure.getStructureId(), ddmForm);

		// Indexer

		reindexStructure(structure, serviceContext);

		return structure;
	}

	protected Set<Long> getDataProviderInstanceIds(
		long groupId, DDMForm ddmForm) {

		Set<Long> dataProviderInstanceIds = new HashSet<>();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			long[] ddmDataProviderInstanceIds = getDDMDataProviderInstanceIds(
				ddmFormField.getProperty("ddmDataProviderInstanceId"));

			for (long ddmDataProviderInstanceId : ddmDataProviderInstanceIds) {
				if (ddmDataProviderInstanceId > 0) {
					dataProviderInstanceIds.add(ddmDataProviderInstanceId);
				}
			}
		}

		for (DDMFormRule ddmFormRule : ddmForm.getDDMFormRules()) {
			Set<Long> ddmFormDataProviderInstanceIds =
				getDataProviderInstanceIds(groupId, ddmFormRule);

			dataProviderInstanceIds.addAll(ddmFormDataProviderInstanceIds);
		}

		return dataProviderInstanceIds;
	}

	protected Set<Long> getDataProviderInstanceIds(
		long groupId, DDMFormRule ddmFormRule) {

		List<String> actions = ddmFormRule.getActions();

		if (ListUtil.isEmpty(actions)) {
			return Collections.emptySet();
		}

		Set<Long> dataProviderInstanceIds = new HashSet<>();

		for (String action : actions) {
			Matcher matcher = _callFunctionPattern.matcher(action);

			while (matcher.find()) {
				String dataProviderUuid = matcher.group(1);

				DDMDataProviderInstance dataProviderInstance =
					_ddmDataProviderInstanceLocalService.
						fetchDDMDataProviderInstanceByUuidAndGroupId(
							dataProviderUuid, groupId);

				if (dataProviderInstance != null) {
					dataProviderInstanceIds.add(
						dataProviderInstance.getDataProviderInstanceId());
				}
			}
		}

		return dataProviderInstanceIds;
	}

	protected long[] getDDMDataProviderInstanceIds(JSONArray jsonArray) {
		long[] ddmDataProviderInstanceIds = new long[jsonArray.length()];

		for (int i = 0; i < jsonArray.length(); i++) {
			ddmDataProviderInstanceIds[i] = jsonArray.getLong(i);
		}

		return ddmDataProviderInstanceIds;
	}

	protected long[] getDDMDataProviderInstanceIds(
		Object ddmDataProviderInstanceId) {

		if (ddmDataProviderInstanceId instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)ddmDataProviderInstanceId;

			return getDDMDataProviderInstanceIds(jsonArray);
		}
		else if (ddmDataProviderInstanceId instanceof String) {
			try {
				JSONArray jsonArray = _jsonFactory.createJSONArray(
					(String)ddmDataProviderInstanceId);

				return getDDMDataProviderInstanceIds(jsonArray);
			}
			catch (Exception e) {
			}
		}

		long ddmDataProviderInstanceIdLong = GetterUtil.getLong(
			ddmDataProviderInstanceId);

		if (ddmDataProviderInstanceIdLong > 0) {
			return new long[] {ddmDataProviderInstanceIdLong};
		}

		return GetterUtil.DEFAULT_LONG_VALUES;
	}

	protected Set<String> getDDMFormFieldsNames(DDMForm ddmForm) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		if (MapUtil.isEmpty(ddmFormFieldsMap)) {
			return Collections.emptySet();
		}

		Set<String> ddmFormFieldsNames = new HashSet<>();

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

		return _ddmTemplateLocalService.getTemplates(
			structure.getGroupId(), classNameId, structure.getStructureId(),
			type);
	}

	protected void reindexStructure(
			DDMStructure structure, ServiceContext serviceContext)
		throws PortalException {

		if (!serviceContext.isIndexingEnabled()) {
			return;
		}

		DDMStructureIndexer ddmStructureIndexer =
			_ddmStructureIndexerTracker.getDDMStructureIndexer(
				structure.getClassName());

		if (ddmStructureIndexer == null) {
			return;
		}

		String backgroundTaskName =
			DDMStructureIndexerBackgroundTaskExecutor.getBackgroundTaskName(
				structure.getStructureId());

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				"structureId", structure.getStructureId()
			).build();

		_backgroundTaskManager.addBackgroundTask(
			structure.getUserId(), structure.getGroupId(), backgroundTaskName,
			DDMStructureIndexerBackgroundTaskExecutor.class.getName(),
			taskContextMap, serviceContext);
	}

	protected String serializeJSONDDMForm(DDMForm ddmForm) {
		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_jsonDDMFormSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	protected void syncStructureTemplatesFields(final DDMStructure structure) {
		TransactionCommitCallbackUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					DDMFormTemplateSynchonizer ddmFormTemplateSynchonizer =
						new DDMFormTemplateSynchonizer(
							structure.getDDMForm(), _jsonDDMFormDeserializer,
							_jsonDDMFormSerializer, _ddmTemplateLocalService);

					List<DDMTemplate> templates = getStructureTemplates(
						structure, DDMTemplateConstants.TEMPLATE_TYPE_FORM);

					ddmFormTemplateSynchonizer.setDDMFormTemplates(templates);

					ddmFormTemplateSynchonizer.synchronize();

					return null;
				}

			});
	}

	protected void updateDataProviderInstanceLinks(
		long groupId, long structureId, DDMForm ddmForm) {

		Set<Long> dataProviderInstanceIds = getDataProviderInstanceIds(
			groupId, ddmForm);

		List<DDMDataProviderInstanceLink> dataProviderInstanceLinks =
			_ddmDataProviderInstanceLinkLocalService.
				getDataProviderInstanceLinks(structureId);

		for (DDMDataProviderInstanceLink dataProviderInstanceLink :
				dataProviderInstanceLinks) {

			if (dataProviderInstanceIds.remove(
					dataProviderInstanceLink.getDataProviderInstanceId())) {

				continue;
			}

			_ddmDataProviderInstanceLinkLocalService.
				deleteDataProviderInstanceLink(dataProviderInstanceLink);
		}

		for (Long dataProviderInstanceId : dataProviderInstanceIds) {
			_ddmDataProviderInstanceLinkLocalService.
				addDataProviderInstanceLink(
					dataProviderInstanceId, structureId);
		}
	}

	protected void validate(DDMForm ddmForm) throws PortalException {
		_ddmFormValidator.validate(ddmForm);
	}

	protected void validate(DDMForm parentDDMForm, DDMForm ddmForm)
		throws PortalException {

		Set<String> commonDDMFormFieldNames = SetUtil.intersect(
			getDDMFormFieldsNames(parentDDMForm),
			getDDMFormFieldsNames(ddmForm));

		if (!commonDDMFormFieldNames.isEmpty()) {
			throw new StructureDuplicateElementException(
				"Duplicate DDM form field names: " +
					StringUtil.merge(commonDDMFormFieldNames));
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

		validate(nameMap, getParentDDMForm(parentStructureId), ddmForm);
	}

	protected void validate(
			Map<Locale, String> nameMap, DDMForm parentDDMForm, DDMForm ddmForm)
		throws PortalException {

		try {
			if (!ExportImportThreadLocal.isImportInProcess()) {
				validate(nameMap, ddmForm.getDefaultLocale());
			}

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
			throw new StructureDefinitionException(e);
		}
	}

	protected void validate(
			Map<Locale, String> nameMap, Locale contentDefaultLocale)
		throws PortalException {

		String name = nameMap.get(contentDefaultLocale);

		if (Validator.isNull(name)) {
			throw new StructureNameException(
				"Name is null for locale " +
					contentDefaultLocale.getDisplayName());
		}

		if (!LanguageUtil.isAvailableLocale(contentDefaultLocale)) {
			LocaleException le = new LocaleException(
				LocaleException.TYPE_CONTENT,
				StringBundler.concat(
					"The locale ", contentDefaultLocale,
					" is not available in company ",
					CompanyThreadLocal.getCompanyId()));

			le.setSourceAvailableLocales(
				Collections.singleton(contentDefaultLocale));
			le.setTargetAvailableLocales(LanguageUtil.getAvailableLocales());

			throw le;
		}
	}

	protected void validateParentStructure(
			long structureId, long parentStructureId)
		throws PortalException {

		while (parentStructureId != 0) {
			DDMStructure parentStructure =
				ddmStructurePersistence.fetchByPrimaryKey(parentStructureId);

			if (structureId == parentStructure.getStructureId()) {
				throw new InvalidParentStructureException();
			}

			parentStructureId = parentStructure.getParentStructureId();
		}
	}

	private static final Pattern _callFunctionPattern = Pattern.compile(
		"call\\(\\s*\'([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-" +
			"[0-9a-f]{12})\'\\s*,\\s*\'(.*)\'\\s*,\\s*\'(.*)\'\\s*\\)");

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMDataProviderInstanceLinkLocalService
		_ddmDataProviderInstanceLinkLocalService;

	@Reference
	private DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;

	@Reference
	private DDMFormValidator _ddmFormValidator;

	@Reference
	private DDMPermissionSupport _ddmPermissionSupport;

	@Reference
	private DDMSearchHelper _ddmSearchHelper;

	@Reference
	private DDMStructureIndexerTracker _ddmStructureIndexerTracker;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DDMXML _ddmXML;

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _jsonDDMFormDeserializer;

	@Reference(target = "(ddm.form.serializer.type=json)")
	private DDMFormSerializer _jsonDDMFormSerializer;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference(target = "(ddm.form.deserializer.type=xsd)")
	private DDMFormDeserializer _xsdDDMFormDeserializer;

}