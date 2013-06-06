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

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.RequiredStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateStructureKeyException;
import com.liferay.portlet.dynamicdatamapping.StructureNameException;
import com.liferay.portlet.dynamicdatamapping.StructureXsdException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateHelperUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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

	/**
	 * Adds a structure referencing its parent structure.
	 *
	 * @param  userId the primary key of the structure's creator/owner
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
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the XSD was not well-formed, or if a portal exception
	 *         occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure addStructure(
			long userId, long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsd, String storageType,
			int type, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Structure

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(structureKey)) {
			structureKey = String.valueOf(counterLocalService.increment());
		}
		else {
			structureKey = structureKey.trim().toUpperCase();
		}

		try {
			xsd = DDMXMLUtil.validateXML(xsd);
			xsd = DDMXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new StructureXsdException();
		}

		Date now = new Date();

		validate(groupId, classNameId, structureKey, nameMap, xsd);

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
		structure.setNameMap(nameMap);
		structure.setDescriptionMap(descriptionMap);
		structure.setXsd(xsd);
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

		return structure;
	}

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
	 *         found, if the XSD was not well-formed, or if a portal exception
	 *         occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure addStructure(
			long userId, long groupId, long classNameId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addStructure(
			userId, groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			classNameId, null, nameMap, descriptionMap, xsd,
			PropsValues.DYNAMIC_DATA_LISTS_STORAGE_TYPE,
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	/**
	 * Adds a structure referencing a default parent structure if the parent
	 * structure is not found.
	 *
	 * @param  userId the primary key of the structure's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  parentStructureKey the unique string identifying the parent
	 *         structure (optionally <code>null</code>)
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
	 *         UUID, creation date, modification date, guest permissions and
	 *         group permissions for the structure.
	 * @return the structure
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if the XSD was not well-formed, or if a portal exception
	 *         occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsd, String storageType,
			int type, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructure parentStructure = fetchStructure(
			groupId, classNameId, parentStructureKey);

		long parentStructureId =
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID;

		if (parentStructure != null) {
			parentStructureId = parentStructure.getStructureId();
		}

		return addStructure(
			userId, groupId, parentStructureId, classNameId, structureKey,
			nameMap, descriptionMap, xsd, storageType, type, serviceContext);
	}

	/**
	 * Adds the resources to the structure.
	 *
	 * @param  structure the structure to add resources to
	 * @param  addGroupPermissions whether to add group permissions
	 * @param  addGuestPermissions whether to add guest permissions
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addStructureResources(
			DDMStructure structure, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addStructureResources(
			DDMStructure structure, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure copyStructure(
			long userId, long structureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		return addStructure(
			userId, structure.getGroupId(), structure.getParentStructureId(),
			structure.getClassNameId(), null, nameMap, descriptionMap,
			structure.getXsd(), structure.getStorageType(), structure.getType(),
			serviceContext);
	}

	@Override
	public DDMStructure copyStructure(
			long userId, long structureId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		return addStructure(
			userId, structure.getGroupId(), structure.getParentStructureId(),
			structure.getClassNameId(), null, structure.getNameMap(),
			structure.getDescriptionMap(), structure.getXsd(),
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteStructure(DDMStructure structure)
		throws PortalException, SystemException {

		if (ddmStructureLinkPersistence.countByStructureId(
				structure.getStructureId()) > 0) {

			throw new RequiredStructureException(
				RequiredStructureException.REFERENCED_STRUCTURE_LINK);
		}

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		if (ddmTemplatePersistence.countByG_C_C(
				structure.getGroupId(), classNameId,
				structure.getPrimaryKey()) > 0) {

			throw new RequiredStructureException(
				RequiredStructureException.REFERENCED_TEMPLATE);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteStructure(long structureId)
		throws PortalException, SystemException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		deleteStructure(structure);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException, SystemException {

		structureKey = structureKey.trim().toUpperCase();

		DDMStructure structure = ddmStructurePersistence.findByG_C_S(
			groupId, classNameId, structureKey);

		deleteStructure(structure);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteStructures(long groupId)
		throws PortalException, SystemException {

		List<DDMStructure> structures = ddmStructurePersistence.findByGroupId(
			groupId);

		for (DDMStructure structure : structures) {
			deleteStructure(structure);
		}
	}

	/**
	 * Returns the structure with the ID.
	 *
	 * @param  structureId the primary key of the structure
	 * @return the structure with the structure ID, or <code>null</code> if a
	 *         matching structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure fetchStructure(long structureId)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure fetchStructure(
			long groupId, long classNameId, String structureKey)
		throws SystemException {

		structureKey = structureKey.trim().toUpperCase();

		return ddmStructurePersistence.fetchByG_C_S(
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
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @param  structureKey the unique string identifying the structure
	 * @param  includeGlobalStructures whether to include the global scope in
	 *         the search
	 * @return the matching structure, or <code>null</code> if a matching
	 *         structure could not be found
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure fetchStructure(
			long groupId, long classNameId, String structureKey,
			boolean includeGlobalStructures)
		throws PortalException, SystemException {

		structureKey = structureKey.trim().toUpperCase();

		DDMStructure structure = ddmStructurePersistence.fetchByG_C_S(
			groupId, classNameId, structureKey);

		if ((structure != null) || !includeGlobalStructures) {
			return structure;
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Group companyGroup = groupLocalService.getCompanyGroup(
			group.getCompanyId());

		return ddmStructurePersistence.fetchByG_C_S(
			companyGroup.getGroupId(), classNameId, structureKey);
	}

	/**
	 * Returns the structure matching the UUID and group.
	 *
	 * @param  uuid the structure's UUID
	 * @param  groupId the primary key of the structure's group
	 * @return the matching structure, or <code>null</code> if a matching
	 *         structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure fetchStructure(String uuid, long groupId)
		throws SystemException {

		return ddmStructurePersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getClassStructures(long,
	 *             long)}
	 */
	@Override
	public List<DDMStructure> getClassStructures(long classNameId)
		throws SystemException {

		return ddmStructurePersistence.findByClassNameId(classNameId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getClassStructures(long,
	 *             long, int, int)}
	 */
	@Override
	public List<DDMStructure> getClassStructures(
			long classNameId, int start, int end)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getClassStructures(
			long companyId, long classNameId)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getClassStructures(
			long companyId, long classNameId, int start, int end)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getClassStructures(
			long companyId, long classNameId,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmStructurePersistence.findByC_C(
			companyId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			orderByComparator);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getClassStructures(long,
	 *             long, OrderByComparator)}
	 */
	@Override
	public List<DDMStructure> getClassStructures(
			long classNameId, OrderByComparator orderByComparator)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getDLFileEntryTypeStructures(
			long dlFileEntryTypeId)
		throws SystemException {

		return dlFileEntryTypePersistence.getDDMStructures(dlFileEntryTypeId);
	}

	/**
	 * Returns the structure with the ID.
	 *
	 * @param  structureId the primary key of the structure
	 * @return the structure with the ID
	 * @throws PortalException if a structure with the ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure getStructure(long structureId)
		throws PortalException, SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException, SystemException {

		structureKey = structureKey.trim().toUpperCase();

		return ddmStructurePersistence.findByG_C_S(
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
	 * @throws PortalException if a matching structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey,
			boolean includeGlobalStructures)
		throws PortalException, SystemException {

		structureKey = structureKey.trim().toUpperCase();

		DDMStructure structure = ddmStructurePersistence.fetchByG_C_S(
			groupId, classNameId, structureKey);

		if (structure != null) {
			return structure;
		}

		if (!includeGlobalStructures) {
			throw new NoSuchStructureException(
				"No DDMStructure exists with the structure key " +
					structureKey);
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Group companyGroup = groupLocalService.getCompanyGroup(
			group.getCompanyId());

		return ddmStructurePersistence.findByG_C_S(
			companyGroup.getGroupId(), classNameId, structureKey);
	}

	/**
	 * Returns all the structures matching the group, name, and description.
	 *
	 * @param  groupId the primary key of the structure's group
	 * @param  name the structure's name
	 * @param  description the structure's description
	 * @return the matching structures
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getStructure(
			long groupId, String name, String description)
		throws SystemException {

		return ddmStructurePersistence.findByG_N_D(groupId, name, description);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getStructures}
	 */
	@Override
	public List<DDMStructure> getStructureEntries() throws SystemException {
		return getStructures();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getStructures(long)}
	 */
	@Override
	public List<DDMStructure> getStructureEntries(long groupId)
		throws SystemException {

		return getStructures(groupId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getStructures(long, int,
	 *             int)}
	 */
	@Override
	public List<DDMStructure> getStructureEntries(
			long groupId, int start, int end)
		throws SystemException {

		return getStructures(groupId, start, end);
	}

	/**
	 * Returns all the structures present in the system.
	 *
	 * @return the structures present in the system
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getStructures() throws SystemException {
		return ddmStructurePersistence.findAll();
	}

	/**
	 * Returns all the structures present in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the structures present in the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getStructures(long groupId)
		throws SystemException {

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
	 * @return the range of matching structures, or <code>null</code> if no
	 *         matches could be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getStructures(long groupId, int start, int end)
		throws SystemException {

		return ddmStructurePersistence.findByGroupId(groupId, start, end);
	}

	/**
	 * Returns all the structures matching class name ID and group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @return the matching structures
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getStructures(long groupId, long classNameId)
		throws SystemException {

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
	 * @return the matching structures, or <code>null</code> if no matching
	 *         structures could be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getStructures(
			long groupId, long classNameId, int start, int end)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getStructures(
			long groupId, long classNameId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmStructurePersistence.findByG_C(
			groupId, classNameId, start, end, orderByComparator);
	}

	@Override
	public List<DDMStructure> getStructures(
			long groupId, String name, String description)
		throws SystemException {

		return ddmStructurePersistence.findByG_N_D(groupId, name, description);
	}

	/**
	 * Returns all the structures belonging to the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @return the structures belonging to the groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getStructures(long[] groupIds)
		throws SystemException {

		return ddmStructurePersistence.findByGroupId(groupIds);
	}

	/**
	 * Returns all the structures matching the class name ID and belonging to
	 * the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @return the structures matching the class name ID and belonging to the
	 *         groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> getStructures(long[] groupIds, long classNameId)
		throws SystemException {

		return ddmStructurePersistence.findByG_C(groupIds, classNameId);
	}

	/**
	 * Returns the number of structures belonging to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of structures belonging to the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getStructuresCount(long groupId) throws SystemException {
		return ddmStructurePersistence.countByGroupId(groupId);
	}

	/**
	 * Returns the number of structures matching the class name ID and group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the structure's
	 *         related model
	 * @return the number of matching structures
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getStructuresCount(long groupId, long classNameId)
		throws SystemException {

		return ddmStructurePersistence.countByG_C(groupId, classNameId);
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
	@Override
	public List<DDMStructure> search(
			long companyId, long[] groupIds, long[] classNameIds,
			String keywords, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DDMStructure> search(
			long companyId, long[] groupIds, long[] classNameIds, String name,
			String description, String storageType, int type,
			boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] classNameIds,
			String keywords)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] classNameIds, String name,
			String description, String storageType, int type,
			boolean andOperator)
		throws SystemException {

		return ddmStructureFinder.countByC_G_C_N_D_S_T(
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
	 *         structure's modification date.
	 * @return the updated structure
	 * @throws PortalException if a matching structure could not be found, if
	 *         the XSD was not well-formed, or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure updateStructure(
			long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsd,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		structureKey = structureKey.trim().toUpperCase();

		DDMStructure structure = ddmStructurePersistence.findByG_C_S(
			groupId, classNameId, structureKey);

		return doUpdateStructure(
			parentStructureId, nameMap, descriptionMap, xsd, serviceContext,
			structure);
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
	 * @param  xsd the structure's new XML schema definition
	 * @param  serviceContext the service context to be applied. Can set the
	 *         structure's modification date.
	 * @return the updated structure
	 * @throws PortalException if a matching structure could not be found, if
	 *         the XSD was not well-formed, or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure updateStructure(
			long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		return doUpdateStructure(
			parentStructureId, nameMap, descriptionMap, xsd, serviceContext,
			structure);
	}

	/**
	 * Updates the structure matching the structure ID, replacing its XSD with a
	 * new one.
	 *
	 * @param  structureId the primary key of the structure
	 * @param  xsd the structure's new XML schema definition
	 * @param  serviceContext the service context to be applied. Can set the
	 *         structure's modification date.
	 * @return the updated structure
	 * @throws PortalException if a matching structure could not be found, if
	 *         the XSD was not well-formed, or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructure updateXSD(
			long structureId, String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		return doUpdateStructure(
			structure.getParentStructureId(), structure.getNameMap(),
			structure.getDescriptionMap(), xsd, serviceContext, structure);
	}

	/**
	 * Updates the structure matching the structure ID, replacing the metadata
	 * entry of the named field.
	 *
	 * @param  structureId the primary key of the structure
	 * @param  fieldName the name of the field whose metadata to update
	 * @param  metadataEntryName the metadata entry's name
	 * @param  metadataEntryValue the metadata entry's value
	 * @param  serviceContext the service context to be applied. Can set the
	 *         structure's modification date.
	 * @throws PortalException if a matching structure could not be found, if
	 *         the XSD was not well-formed, or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updateXSDFieldMetadata(
			long structureId, String fieldName, String metadataEntryName,
			String metadataEntryValue, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = fetchDDMStructure(structureId);

		if (ddmStructure == null) {
			return;
		}

		String xsd = ddmStructure.getXsd();

		try {
			Document document = SAXReaderUtil.read(xsd);

			Element rootElement = document.getRootElement();

			List<Element> dynamicElementElements = rootElement.elements(
				"dynamic-element");

			for (Element dynamicElementElement : dynamicElementElements) {
				String dynamicElementElementFieldName = GetterUtil.getString(
					dynamicElementElement.attributeValue("name"));

				if (!dynamicElementElementFieldName.equals(fieldName)) {
					continue;
				}

				List<Element> metadataElements = dynamicElementElement.elements(
					"meta-data");

				for (Element metadataElement : metadataElements) {
					List<Element> metadataEntryElements =
						metadataElement.elements();

					for (Element metadataEntryElement : metadataEntryElements) {
						String metadataEntryElementName = GetterUtil.getString(
							metadataEntryElement.attributeValue("name"));

						if (metadataEntryElementName.equals(
								metadataEntryName)) {

							metadataEntryElement.setText(metadataEntryValue);
						}
					}
				}
			}

			updateXSD(structureId, document.asXML(), serviceContext);
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
	}

	protected void appendNewStructureRequiredFields(
		DDMStructure structure, Document templateDocument) {

		String xsd = structure.getXsd();

		Document structureDocument = null;

		try {
			structureDocument = SAXReaderUtil.read(xsd);
		}
		catch (DocumentException de) {
			if (_log.isWarnEnabled()) {
				_log.warn(de, de);
			}

			return;
		}

		Element templateElement = templateDocument.getRootElement();

		XPath structureXPath = SAXReaderUtil.createXPath(
			"//dynamic-element[.//meta-data/entry[@name=\"required\"]=" +
				"\"true\"]");

		List<Node> nodes = structureXPath.selectNodes(structureDocument);

		for (Node node : nodes) {
			Element element = (Element)node;

			String name = element.attributeValue("name");

			name = HtmlUtil.escapeXPathAttribute(name);

			XPath templateXPath = SAXReaderUtil.createXPath(
				"//dynamic-element[@name=" + name + "]");

			if (!templateXPath.booleanValueOf(templateDocument)) {
				templateElement.add(element.createCopy());
			}
		}
	}

	protected DDMStructure doUpdateStructure(
			long parentStructureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsd,
			ServiceContext serviceContext, DDMStructure structure)
		throws PortalException, SystemException {

		try {
			xsd = DDMXMLUtil.validateXML(xsd);
			xsd = DDMXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new StructureXsdException();
		}

		validate(nameMap, xsd);

		structure.setModifiedDate(serviceContext.getModifiedDate(null));
		structure.setParentStructureId(parentStructureId);
		structure.setNameMap(nameMap);
		structure.setDescriptionMap(descriptionMap);
		structure.setXsd(xsd);

		ddmStructurePersistence.update(structure);

		syncStructureTemplatesFields(structure);

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
			List<Long> structureIds, long groupId, long structureId)
		throws PortalException, SystemException {

		List<DDMStructure> structures = ddmStructurePersistence.findByG_P(
			groupId, structureId);

		for (DDMStructure structure : structures) {
			structureIds.add(structure.getStructureId());

			getChildrenStructureIds(
				structureIds, structure.getGroupId(),
				structure.getParentStructureId());
		}
	}

	protected List<Long> getChildrenStructureIds(long groupId, long structureId)
		throws PortalException, SystemException {

		List<Long> structureIds = new ArrayList<Long>();

		getChildrenStructureIds(structureIds, groupId, structureId);

		structureIds.add(0, structureId);

		return structureIds;
	}

	protected void syncStructureTemplatesFields(DDMStructure structure)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		List<DDMTemplate> templates = ddmTemplateLocalService.getTemplates(
			structure.getGroupId(), classNameId, structure.getStructureId(),
			DDMTemplateConstants.TEMPLATE_TYPE_FORM);

		for (DDMTemplate template : templates) {
			String script = template.getScript();

			Document templateDocument = null;

			try {
				templateDocument = SAXReaderUtil.read(script);
			}
			catch (DocumentException de) {
				if (_log.isWarnEnabled()) {
					_log.warn(de, de);
				}

				continue;
			}

			Element templateRootElement = templateDocument.getRootElement();

			syncStructureTemplatesFields(template, templateRootElement);

			appendNewStructureRequiredFields(structure, templateDocument);

			try {
				script = DDMXMLUtil.formatXML(templateDocument.asXML());
			}
			catch (Exception e) {
				throw new StructureXsdException();
			}

			template.setScript(script);

			ddmTemplatePersistence.update(template);
		}
	}

	protected void syncStructureTemplatesFields(
			DDMTemplate template, Element templateElement)
		throws PortalException, SystemException {

		DDMStructure structure = DDMTemplateHelperUtil.fetchStructure(template);

		List<Element> dynamicElementElements = templateElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			String dataType = dynamicElementElement.attributeValue("dataType");
			String fieldName = dynamicElementElement.attributeValue("name");

			if (Validator.isNull(dataType)) {
				continue;
			}

			if (!structure.hasField(fieldName)) {
				templateElement.remove(dynamicElementElement);

				continue;
			}

			String mode = template.getMode();

			if (mode.equals(DDMTemplateConstants.TEMPLATE_MODE_CREATE)) {
				boolean fieldRequired = structure.getFieldRequired(fieldName);

				List<Element> metadataElements = dynamicElementElement.elements(
					"meta-data");

				for (Element metadataElement : metadataElements) {
					for (Element metadataEntryElement :
							metadataElement.elements()) {

						String attributeName =
							metadataEntryElement.attributeValue("name");

						if (fieldRequired && attributeName.equals("required")) {
							metadataEntryElement.setText("true");
						}
					}
				}
			}

			syncStructureTemplatesFields(template, dynamicElementElement);
		}
	}

	protected void validate(List<Element> elements, Set<String> names)
		throws PortalException {

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("meta-data")) {
				continue;
			}

			String name = element.attributeValue("name", StringPool.BLANK);
			String type = element.attributeValue("type", StringPool.BLANK);

			if (Validator.isNull(name) ||
				name.startsWith(DDMStructureConstants.XSD_NAME_RESERVED)) {

				throw new StructureXsdException();
			}

			char[] charArray = name.toCharArray();

			for (int i = 0; i < charArray.length; i++) {
				if (!Character.isLetterOrDigit(charArray[i]) &&
					(charArray[i] != CharPool.DASH) &&
					(charArray[i] != CharPool.UNDERLINE)) {

					throw new StructureXsdException();
				}
			}

			String path = name;

			Element parentElement = element.getParent();

			while (!parentElement.isRootElement()) {
				path =
					parentElement.attributeValue("name", StringPool.BLANK) +
						StringPool.SLASH + path;

				parentElement = parentElement.getParent();
			}

			path = path.toLowerCase();

			if (names.contains(path)) {
				throw new StructureDuplicateElementException();
			}
			else {
				names.add(path);
			}

			if (Validator.isNull(type)) {
				throw new StructureXsdException();
			}

			validate(element.elements(), names);
		}
	}

	protected void validate(
			long groupId, long classNameId, String structureKey,
			Map<Locale, String> nameMap, String xsd)
		throws PortalException, SystemException {

		structureKey = structureKey.trim().toUpperCase();

		DDMStructure structure = ddmStructurePersistence.fetchByG_C_S(
			groupId, classNameId, structureKey);

		if (structure != null) {
			StructureDuplicateStructureKeyException sdske =
				new StructureDuplicateStructureKeyException();

			sdske.setStructureKey(structure.getStructureKey());

			throw sdske;
		}

		validate(nameMap, xsd);
	}

	protected void validate(Map<Locale, String> nameMap, String xsd)
		throws PortalException {

		if (Validator.isNull(xsd)) {
			throw new StructureXsdException();
		}
		else {
			try {
				List<Element> elements = new ArrayList<Element>();

				Document document = SAXReaderUtil.read(xsd);

				Element rootElement = document.getRootElement();

				List<Element> rootElementElements = rootElement.elements();

				if (rootElementElements.isEmpty()) {
					throw new StructureXsdException();
				}

				Locale contentDefaultLocale = LocaleUtil.fromLanguageId(
					rootElement.attributeValue("default-locale"));

				validateLanguages(nameMap, contentDefaultLocale);

				elements.addAll(rootElement.elements());

				Set<String> elNames = new HashSet<String>();

				validate(elements, elNames);
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
			catch (StructureXsdException sxe) {
				throw sxe;
			}
			catch (Exception e) {
				throw new StructureXsdException();
			}
		}
	}

	protected void validateLanguages(
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
				"The locale " + contentDefaultLocale +
					" is not available in company " + companyId);

			le.setSourceAvailableLocales(new Locale[] {contentDefaultLocale});
			le.setTargetAvailableLocales(availableLocales);

			throw le;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMStructureLocalServiceImpl.class);

}