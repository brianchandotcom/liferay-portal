/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructurePermission;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * dynamic data mapping (DDM) structures. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Marcellus Tavares
 * @see    com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLocalServiceImpl
 */
public class DDMStructureServiceImpl extends DDMStructureServiceBaseImpl {

	/**
	 * Adds a structure referencing a default parent structure, using the portal
	 * property <code>dynamic.data.lists.storage.type</code> storage type and
	 * default structure type.
	 *
	 * @param  userId the primary key of the structure's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  nameMap the structure's locales and localized names
	 * @param  descriptionMap the structure's locales and localized descriptions
	 * @param  xsd the structure's XML schema definition
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions, and
	 *         group permissions for the structure.
	 * @return the structure
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the user did not have permission to add the structure,
	 *         if the XSD was not well-formed, or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure addStructure(
			long userId, long groupId, long classNameId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = ParamUtil.getString(serviceContext, "ddmResource");

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ActionKeys.ADD_STRUCTURE);

		return ddmStructureLocalService.addStructure(
			getUserId(), groupId, classNameId, nameMap, descriptionMap, xsd,
			serviceContext);
	}

	/**
	 * Adds a structure referencing its parent structure.
	 *
	 * @param  groupId the primary key of the group
	 * @param  parentStructureId the primary key of the parent structure
	 *         (optionally {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants#DEFAULT_PARENT_STRUCTURE_ID})
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  structureKey the unique string identifying the structure
	 *         (optionally <code>null</code>)
	 * @param  nameMap the structure's locales and localized names
	 * @param  descriptionMap the structure's locales and localized descriptions
	 * @param  xsd the structure's XML schema definition
	 * @param  storageType the structure's storage type. It can be "xml" or
	 *         "expando". For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	 * @param  type the structure's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions, and
	 *         group permissions for the structure.
	 * @return the structure
	 * @throws PortalException if the user did not have permission to add the
	 *         structure, if the XSD is not well formed, or if a portal
	 *         exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure addStructure(
			long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsd, String storageType,
			int type, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = ParamUtil.getString(serviceContext, "ddmResource");

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ActionKeys.ADD_STRUCTURE);

		return ddmStructureLocalService.addStructure(
			getUserId(), groupId, parentStructureId, classNameId, structureKey,
			nameMap, descriptionMap, xsd, storageType, type, serviceContext);
	}

	/**
	 * Adds a structure referencing the parent structure by its structure key.
	 * In case the parent structure is not found, it uses the default parent
	 * structure ID.
	 *
	 * @param  userId the primary key of the structure's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  parentStructureKey the unique string identifying the structure
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  structureKey unique string identifying the structure (optionally
	 *         <code>null</code>)
	 * @param  nameMap the structure's locales and localized names
	 * @param  descriptionMap the structure's locales and localized descriptions
	 * @param  xsd the XML schema definition of the structure
	 * @param  storageType the storage type of the structure. It can be XML or
	 *         expando. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	 * @param  type the structure's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param  serviceContext the service context to be applied. Must have the
	 *         <code>ddmResource</code> attribute to check permissions. Can set
	 *         the UUID, creation date, modification date, guest permissions,
	 *         and group permissions for the structure.
	 * @return the structure
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the user did not have permission to add the structure,
	 *         if the XSD was not well-formed, or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsd, String storageType,
			int type, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = ParamUtil.getString(serviceContext, "ddmResource");

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ActionKeys.ADD_STRUCTURE);

		return ddmStructureLocalService.addStructure(
			userId, groupId, parentStructureKey, classNameId, structureKey,
			nameMap, descriptionMap, xsd, storageType, type, serviceContext);
	}

	/**
	 * Copies a structure, creating a new structure with all the values
	 * extracted from the original one. The new structure supports a new name
	 * and description.
	 *
	 * @param  structureId the primary key of the structure to be copied
	 * @param  nameMap the new structure's locales and localized names
	 * @param  descriptionMap the new structure's locales and localized
	 *         descriptions
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions, and
	 *         group permissions for the structure.
	 * @return the new structure
	 * @throws PortalException if the user did not have permission to add the
	 *         structure or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure copyStructure(
			long structureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = ParamUtil.getString(serviceContext, "ddmResource");

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ActionKeys.ADD_STRUCTURE);

		return ddmStructureLocalService.copyStructure(
			getUserId(), structureId, nameMap, descriptionMap, serviceContext);
	}

	public DDMStructure copyStructure(
			long structureId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = ParamUtil.getString(serviceContext, "ddmResource");

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ActionKeys.ADD_STRUCTURE);

		return ddmStructureLocalService.copyStructure(
			getUserId(), structureId, serviceContext);
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
	 * @throws PortalException if the user did not have permission to delete the
	 *         structure or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteStructure(long structureId)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.DELETE);

		ddmStructureLocalService.deleteStructure(structureId);
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
	 * @throws PortalException if the user did not have permission to view the
	 *         structure or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = ddmStructurePersistence.fetchByG_C_S(
			groupId, classNameId, structureKey);

		if (ddmStructure != null) {
			DDMStructurePermission.check(
				getPermissionChecker(), ddmStructure, ActionKeys.VIEW);
		}

		return ddmStructure;
	}

	/**
	 * Returns the structure with the ID.
	 *
	 * @param  structureId the primary key of the structure
	 * @return the structure with the ID
	 * @throws PortalException if the user did not have permission to view the
	 *         structure or if a structure with the ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure getStructure(long structureId)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.VIEW);

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
	 * @throws PortalException if the user did not have permission to view the
	 *         structure or if a matching structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), groupId, classNameId, structureKey,
			ActionKeys.VIEW);

		return ddmStructureLocalService.getStructure(
			groupId, classNameId, structureKey);
	}

	/**
	 * Returns the structure matching the class name ID, structure key, and
	 * group, optionally in the global scope.
	 *
	 * <p>
	 * This method first searches in the group. If the structure is still not
	 * found and <code>includeGlobalStructures</code> is set to
	 * <code>true</code>, this method searches the global group.
	 * </p>
	 *
	 * @param  groupId the primary key of the structure's group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  structureKey the unique string identifying the structure
	 * @param  includeGlobalStructures whether to include the global scope in
	 *         the search
	 * @return the matching structure
	 * @throws PortalException if the user did not have permission to view the
	 *         structure or if a matching structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey,
			boolean includeGlobalStructures)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), groupId, classNameId, structureKey,
			ActionKeys.VIEW);

		return ddmStructureLocalService.getStructure(
			groupId, classNameId, structureKey, includeGlobalStructures);
	}

	/**
	 * Returns all the structures in the group that the user has permission to
	 * view.
	 *
	 * @param  groupId the primary key of the group
	 * @return the structures in the group that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> getStructures(long groupId)
		throws SystemException {

		return ddmStructurePersistence.filterFindByGroupId(groupId);
	}

	/**
	 * Returns all the structures in the groups that the user has permission to
	 * view.
	 *
	 * @param  groupIds the primary key of the groups
	 * @return the structures in the groups that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> getStructures(long[] groupIds)
		throws SystemException {

		return ddmStructurePersistence.filterFindByGroupId(groupIds);
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
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> search(
			long companyId, long[] groupIds, long[] classNameIds,
			String keywords, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmStructureFinder.filterFindByKeywords(
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
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> search(
			long companyId, long[] groupIds, long[] classNameIds, String name,
			String description, String storageType, int type,
			boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmStructureFinder.filterFindByC_G_C_N_D_S_T(
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
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long[] groupIds, long[] classNameIds,
			String keywords)
		throws SystemException {

		return ddmStructureFinder.filterCountByKeywords(
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
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long[] groupIds, long[] classNameIds, String name,
			String description, String storageType, int type,
			boolean andOperator)
		throws SystemException {

		return ddmStructureFinder.filterCountByC_G_C_N_D_S_T(
			companyId, groupIds, classNameIds, name, description, storageType,
			type, andOperator);
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
	 * @param  xsd the structure's new XML schema definition
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date.
	 * @return the updated structure
	 * @throws PortalException if the user did not have permission to update the
	 *         structure or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure updateStructure(
			long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsd,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), groupId, classNameId, structureKey,
			ActionKeys.UPDATE);

		return ddmStructureLocalService.updateStructure(
			groupId, parentStructureId, classNameId, structureKey, nameMap,
			descriptionMap, xsd, serviceContext);
	}

	/**
	 * Updates the structure matching the structure ID, replacing the old parent
	 * structure ID, name map, description map, and XSD with the new values.
	 *
	 * @param  structureId the primary key of the structure
	 * @param  parentStructureId the new parent structure primary key
	 * @param  nameMap the structure's new locales and localized names
	 * @param  descriptionMap the structure's new locales and localized
	 *         description
	 * @param  xsd the new XML schema definition of the structure
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date.
	 * @return the updated structure
	 * @throws PortalException if the user did not have permission to update the
	 *         structure or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure updateStructure(
			long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.UPDATE);

		return ddmStructureLocalService.updateStructure(
			structureId, parentStructureId, nameMap, descriptionMap, xsd,
			serviceContext);
	}

}