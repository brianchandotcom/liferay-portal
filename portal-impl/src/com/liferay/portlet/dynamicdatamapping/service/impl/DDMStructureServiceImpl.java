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
 * The DDM Structure service is responsible for accessing, creating, modifying
 * and deleting structures.
 *
 * For more information on ddm structures, see
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
	 * @param  classNameId the primary key of the class name of the model the
	 *         structure is related to
	 * @param  nameMap the structure's locales and localized names
	 * @param  descriptionMap the structure's locales and localized descriptions
	 * @param  xsd the XML schema definition of the structure
	 * @param  serviceContext the service context to be applied. Must have the
	 *         ddmResource attribute to check permissions. Can set the UUID,
	 *         creation date, modification date, guest permissions and group
	 *         permissions for the structure.
	 * @return the structure
	 * @throws PortalException if the creator user could not be found or if the
	 *         xsd is not well formed
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
	 * @param  classNameId the primary key of the class name of the model the
	 *         structure is related to
	 * @param  structureKey unique string identifying the structure (optionally
	 *         <code>null</code>)
	 * @param  nameMap the structure's locales and localized names
	 * @param  descriptionMap the structure's locales and localized descriptions
	 * @param  xsd the XML schema definition of the structure
	 * @param  storageType the storage type of the structure. It can be "xml" or
	 *         "expando". For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.storage.StorageType}
	 * @param  type the structure's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}
	 * @param  serviceContext the service context to be applied. Must have the
	 *         ddmResource attribute to check permissions. Can set the UUID,
	 *         creation date, modification date, guest permissions and group
	 *         permissions for the structure
	 * @return the structure
	 * @throws PortalException if the creator user could not be found or if the
	 *         xsd is not well formed
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
	 * Adds a structure referencing the parent structure by its structureKey. In
	 * case the parent structure is not found, it uses the default parent
	 * structure ID.
	 *
	 * @param  userId the primary key of the structure's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  parentStructureKey unique string identifying the structure
	 * @param  classNameId the primary key of the class name of the model the
	 *         structure is related to
	 * @param  structureKey unique string identifying the structure (optionally
	 *         <code>null</code>)
	 * @param  nameMap the structure's locales and localized names
	 * @param  descriptionMap the structure's locales and localized descriptions
	 * @param  xsd the XML schema definition of the structure
	 * @param  storageType the storage type of the structure. It can be "xml" or
	 *         "expando". For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.storage.StorageType}
	 * @param  type the structure's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}
	 * @param  serviceContext the service context to be applied. Must have the
	 *         ddmResource attribute to check permissions. Can set the UUID,
	 *         creation date, modification date, guest permissions and group
	 *         permissions for the structure
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
	 * Copies a structure: creates a new structure extracting all the values
	 * from the original one. Supports defining the new name and description.
	 *
	 * @param  structureId the primary key of the structure to be copied
	 * @param  nameMap the new structure's locales and localized names
	 * @param  descriptionMap the new structure's locales and localized
	 *         descriptions
	 * @param  serviceContext the service context to be applied. Must have the
	 *         ddmResource attribute to check permissions. Can set the UUID,
	 *         creation date, modification date, guest permissions and group
	 *         permissions for the structure
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
	 * Before deleting the structure, the system validates if the structure is
	 * required by another entity. In case it is needed it will throw an
	 * exception.
	 *
	 * @param  structureId the primary key of the structure that will be deleted
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
	 * Returns the structure with the matching structureKey in a given group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  structureKey unique string identifying the structure
	 * @return the structure with the structure key in the group, or
	 *         <code>null</code> if a matching structure could not be found
	 * @throws
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
	 * Returns a structure that has a matching primary key
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
	 * Returns a structure that has a matching structure key in a given group
	 *
	 * @param  groupId the primary key of the structure's group
	 * @param  structureKey unique string identifying the structure
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
	 * Returns a structure that has a matching structure key in a given group
	 * and optionally in the global scope.
	 *
	 * This method first searches in the give group and in case the structure is
	 * not found and includeGlobalStructures is set to <code>true</code>, then
	 * searches the
	 * global group.
	 *
	 * @param  groupId the primary key of the structure's group
	 * @param  structureKey unique string identifying the structure
	 * @param  includeGlobalStructures whether to include the global scope in
	 *         the search
	 * @return the matching structure
	 * @throws PortalException if the structure was not found
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
	 * Returns a list with all the structure present in a group
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
	 * Returns the list of structures that belong to the groups
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
	 * and groups that matches the class names IDs and include the keywords on
	 * its names or descriptions
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
	 * @param  companyId the primary key of the structures company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names of the models
	 *         the structures are related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         structure's name, or description (optionally <code>null</code>)
	 * @param  start the lower bound of the range of structures
	 * @param  end the upper bound of the range of structures (not inclusive)
	 * @param  orderByComparator the comparator to order the results by
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
	 * and groups that matches the class names IDs and include the keywords on
	 * its names or descriptions, matches storage type or type
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
	 * @param  companyId the primary key of the structures company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names of the models
	 *         the structures are related to
	 * @param  name the structure's name
	 * @param  description the structure's description
	 * @param  storageType the storage type of the structure. It can be "xml" or
	 *         "expando". For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.storage.StorageType}
	 * @param  type the structure's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of structures
	 * @param  end the upper bound of the range of structures (not inclusive)
	 * @param  orderByComparator the comparator to order the results by
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
	 * matches the class names IDs and include the keywords on its names or
	 * descriptions
	 *
	 * @param  companyId the primary key of the structures company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names of the models
	 *         the structures are related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         structure's name, or description (optionally <code>null</code>)
	 * @return the number of matching structures
	 * @throws
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
	 * matches the class names IDs and include the keywords on its names or
	 * descriptions, matches storage type or type
	 *
	 * @param  companyId the primary key of the structures company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the class names of the models
	 *         the structures are related to
	 * @param  name the structure's name
	 * @param  description the structure's description
	 * @param  storageType the storage type of the structure. It can be "xml" or
	 *         "expando". For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.storage.StorageType}
	 * @param  type the structure's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
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
	 * Updates the structure replacing the old parentStructureId, nameMap,
	 * descriptionMap and xsd with the new ones
	 *
	 * @param  structureId the primary key of the structure
	 * @param  parentStructureId the new parentStructureId
	 * @param  nameMap the structure's new locales and localized names
	 * @param  descriptionMap the structure's new locales and localized
	 *         description
	 * @param  xsd the new XML schema definition of the structure
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date
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
	 * Updates the structure matching the structure key and group replacing the
	 * old parentStructureId, nameMap, descriptionMap and xsd with the new ones
	 *
	 * @param  groupId the primary key of the group
	 * @param  parentStructureId the new parentStructureId
	 * @param  structureKey unique string identifying the structure
	 * @param  nameMap the structure's new locales and localized names
	 * @param  descriptionMap the structure's new locales and localized
	 *         description
	 * @param  xsd the new XML schema definition of the structure
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date
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