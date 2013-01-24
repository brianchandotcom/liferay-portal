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
 * Represents the Dynamic Data Mapping (DDM) Structure service responsible for
 * accessing, creating, modifying, and deleting structures.
 *
 * For more information on DDM structures, see
 * {@link
 * com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureLocalServiceImpl}.
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Marcellus Tavares
 */
public class DDMStructureServiceImpl extends DDMStructureServiceBaseImpl {

	/**
	 * Adds a structure.
	 *
	 * @param  userId the primary key of the structure's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  nameMap the structure's locales and localized names
	 * @param  descriptionMap the structure's locales and localized descriptions
	 * @param  xsd the XML schema definition of the structure
	 * @param  serviceContext the service context to be applied. Must have the
	 *         <code>ddmResource</code> attribute to check permissions. Can set
	 *         the UUID, creation date, modification date, guest permissions,
	 *         and group permissions for the structure.
	 * @return the structure
	 * @throws PortalException if the creator user could not be found, if the
	 *         XSD is not well formed, or if a portal exception occurred
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
	 * Adds a structure.
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
	 * @throws PortalException if the creator user could not be found, if the
	 *         XSD is not well formed, or if a portal exception occurred
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
	 * Adds a structure referencing the parent structure by its structure key. In
	 * case the parent structure is not found, it uses the default parent
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
	 * @throws PortalException if a portal exception occurred
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
	 * Creates a new structure by extracting all the values from the original
	 * one. The new structure supports a new name and description.
	 *
	 * @param  structureId the primary key of the structure to be copied
	 * @param  nameMap the new structure's locales and localized names
	 * @param  descriptionMap the new structure's locales and localized
	 *         descriptions
	 * @param  serviceContext the service context to be applied. Must have the
	 *         <code>ddmResource</code> attribute to check permissions. Can set
	 *         the UUID, creation date, modification date, guest permissions,
	 *         and group permissions for the structure.
	 * @return the structure
	 * @throws PortalException if a portal exception occurred
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
	 * Deletes a structure and its resources.
	 *
	 * <p>
	 * Before deleting the structure, the system validates if the structure is
	 * required by another entity. If it is needed, an exception is thrown.
	 * </p>
	 *
	 * @param  structureId the primary key of the structure to be deleted
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteStructure(long structureId)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.DELETE);

		ddmStructureLocalService.deleteStructure(structureId);
	}

	/**
	 * Returns the structure with the matching structure key in a group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  structureKey the unique string identifying the structure
	 * @return the structure with the structure key in the group, or
	 *         <code>null</code> if a structure could not be found
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure fetchStructure(long groupId, String structureKey)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = ddmStructurePersistence.fetchByG_S(
			groupId, structureKey);

		if (ddmStructure != null) {
			DDMStructurePermission.check(
				getPermissionChecker(), ddmStructure, ActionKeys.VIEW);
		}

		return ddmStructure;
	}

	/**
	 * Returns a structure that has a matching primary key.
	 *
	 * @param  structureId the primary key of the structure
	 * @return the matching structure
	 * @throws PortalException if the structure was not found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure getStructure(long structureId)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.VIEW);

		return ddmStructurePersistence.findByPrimaryKey(structureId);
	}

	/**
	 * Returns a structure by structure key in a group.
	 *
	 * @param  groupId the primary key of the structure's group
	 * @param  structureKey the unique string identifying the structure
	 * @return the matching structure
	 * @throws PortalException if the structure was not found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure getStructure(long groupId, String structureKey)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.VIEW);

		return ddmStructureLocalService.getStructure(groupId, structureKey);
	}

	/**
	 * Returns a structure by structure key in a given group or global group.
	 *
	 * <p>
	 * This method first searches in the given group and if the structure is not
	 * found and <code>includeGlobalStructures</code> is set to
	 * <code>true</code>, then searches the global group.
	 * </p>
	 *
	 * @param  groupId the primary key of the structure's group
	 * @param  structureKey the unique string identifying the structure
	 * @param  includeGlobalStructures the option to include the global scope
	 *         group search
	 * @return the matching structure
	 * @throws PortalException if the structure was not found or if a portal
	 *         exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure getStructure(
			long groupId, String structureKey, boolean includeGlobalStructures)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.VIEW);

		return ddmStructureLocalService.getStructure(
			groupId, structureKey, includeGlobalStructures);
	}

	/**
	 * Returns a list with all the structures present in a group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the list of structures in the group
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> getStructures(long groupId)
		throws SystemException {

		return ddmStructurePersistence.filterFindByGroupId(groupId);
	}

	/**
	 * Returns a list of structures that belong to the groups.
	 *
	 * @param  groupIds the primary key of the groups
	 * @return the list of structures that belongs to the groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructure> getStructures(long[] groupIds)
		throws SystemException {

		return ddmStructurePersistence.filterFindByGroupId(groupIds);
	}

	/**
	 * Returns an ordered range of all the structures belonging to the company
	 * and groups that match the class name IDs. The method also returns
	 * keywords for the structures' names and descriptions.
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
	 * @param  companyId the primary key of the structures' company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names for the
	 *         structures' related models
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         structure's name or description (optionally <code>null</code>)
	 * @param  start the lower bound of the range of structures
	 * @param  end the upper bound of the range of structures (not inclusive)
	 * @param  orderByComparator the comparator that orders the results
	 *         (optionally <code>null</code>)
	 * @return the matching structures ordered by comparator
	 *         <code>orderByComparator</code>
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
	 * Returns an ordered range of all the structures belonging to the company
	 * and groups that match the class name IDs. This method also returns the
	 * keywords for the structures' names and descriptions, and matches storage
	 * type or type.
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
	 * @param  companyId the primary key of the structures' company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names for the
	 *         structures' related models
	 * @param  name the structure's name
	 * @param  description the structure's description
	 * @param  storageType the storage type of the structure. It can be XML or
	 *         expando. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	 * @param  type the structure's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param  andOperator the option that every field must match its keyword,
	 *         or only one field must match its keyword
	 * @param  start the lower bound of the range of structures
	 * @param  end the upper bound of the range of structures (not inclusive)
	 * @param  orderByComparator the comparator that orders the results
	 *         (optionally <code>null</code>)
	 * @return the matching structures ordered by comparator
	 *         <code>orderByComparator</code>
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
	 * Returns the number of structures belonging to the company and groups that
	 * match the class name IDs. This method also returns the keywords for the
	 * structures' names and descriptions.
	 *
	 * @param  companyId the primary key of the structures' company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names for the
	 *         structures' related models
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
	 * Returns the number of structures belonging to the company and groups that
	 * match the class name IDs. This method also returns the keywords for the
	 * structures' names and descriptions, and matches storage type or type.
	 *
	 * @param  companyId the primary key of the structures' company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names for the
	 *         structures' related models
	 * @param  name the structure's name
	 * @param  description the structure's description
	 * @param  storageType the storage type of the structure. It can be XML or
	 *         expando. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.storage.StorageType}.
	 * @param  type the structure's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}.
	 * @param  andOperator the option that every field must match its keywords,
	 *         or only one field must match its keywords
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
	 * Updates the structure's parent structure ID, name map, description map,
	 * and XSD with the new values.
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
	 * @throws PortalException if a portal exception occurred
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

	/**
	 * Updates the structure matching the structure key and group, replacing the
	 * old parent structure ID, name map, description map, and XSD with the new
	 * values.
	 *
	 * @param  groupId the primary key of the group
	 * @param  parentStructureId the new parent structure primary key
	 * @param  structureKey the unique string identifying the structure
	 * @param  nameMap the structure's new locales and localized names
	 * @param  descriptionMap the structure's new locales and localized
	 *         description
	 * @param  xsd the new XML schema definition of the structure
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date.
	 * @return the updated structure
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructure updateStructure(
			long groupId, long parentStructureId, String structureKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructurePermission.check(
			getPermissionChecker(), groupId, structureKey, ActionKeys.UPDATE);

		return ddmStructureLocalService.updateStructure(
			groupId, parentStructureId, structureKey, nameMap, descriptionMap,
			xsd, serviceContext);
	}

}