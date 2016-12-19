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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for DDMStructure. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMStructureServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMStructureServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=ddm", "json.web.service.context.path=DDMStructure"}, service = DDMStructureService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMStructureService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureServiceUtil} to access the ddm structure remote service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMStructureServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public DDMStructure addStructure(long groupId, long parentStructureId,
		long classNameId, java.lang.String structureKey,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, DDMForm ddmForm,
		DDMFormLayout ddmFormLayout, java.lang.String storageType, int type,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Adds a structure referencing its parent structure.
	*
	* @param groupId the primary key of the group
	* @param parentStructureId the primary key of the parent structure
	(optionally {@link
	com.liferay.dynamic.data.mapping.model.DDMStructureConstants#DEFAULT_PARENT_STRUCTURE_ID})
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
	com.liferay.dynamic.data.mapping.storage.StorageType}.
	* @param type the structure's type. For more information, see {@link
	com.liferay.dynamic.data.mapping.model.DDMStructureConstants}.
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions,
	and group permissions for the structure.
	* @return the structure
	* @deprecated As of 2.1.0, replaced by {@link #addStructure(long, long,
	long, String, Map, Map, DDMForm, DDMFormLayout, String, int,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public DDMStructure addStructure(long groupId, long parentStructureId,
		long classNameId, java.lang.String structureKey,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, java.lang.String xsd,
		java.lang.String storageType, int type, ServiceContext serviceContext)
		throws PortalException;

	public DDMStructure addStructure(long userId, long groupId,
		java.lang.String parentStructureKey, long classNameId,
		java.lang.String structureKey, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, DDMForm ddmForm,
		DDMFormLayout ddmFormLayout, java.lang.String storageType, int type,
		ServiceContext serviceContext) throws PortalException;

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
	com.liferay.dynamic.data.mapping.storage.StorageType}.
	* @param type the structure's type. For more information, see {@link
	com.liferay.dynamic.data.mapping.model.DDMStructureConstants}.
	* @param serviceContext the service context to be applied. Must have
	the <code>ddmResource</code> attribute to check permissions.
	Can set the UUID, creation date, modification date, guest
	permissions, and group permissions for the structure.
	* @return the structure
	* @deprecated As of 2.1.0, replaced by {@link #addStructure(long, long,
	String, long, String, Map, Map, DDMForm, DDMFormLayout,
	String, int, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DDMStructure addStructure(long userId, long groupId,
		java.lang.String parentStructureKey, long classNameId,
		java.lang.String structureKey, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, java.lang.String xsd,
		java.lang.String storageType, int type, ServiceContext serviceContext)
		throws PortalException;

	public DDMStructure addStructure(long userId, long groupId,
		long classNameId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, DDMForm ddmForm,
		DDMFormLayout ddmFormLayout, java.lang.String storageType,
		ServiceContext serviceContext) throws PortalException;

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
	* @deprecated As of 2.1.0, replaced by {@link #addStructure(long, long,
	long, Map, Map, DDMForm, DDMFormLayout, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DDMStructure addStructure(long userId, long groupId,
		long classNameId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, java.lang.String xsd,
		ServiceContext serviceContext) throws PortalException;

	public DDMStructure copyStructure(long structureId,
		ServiceContext serviceContext) throws PortalException;

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
	public DDMStructure copyStructure(long structureId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		ServiceContext serviceContext) throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructure fetchStructure(long groupId, long classNameId,
		java.lang.String structureKey) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructure fetchStructure(long groupId, long classNameId,
		java.lang.String structureKey, boolean includeAncestorStructures)
		throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructure getStructure(long groupId, long classNameId,
		java.lang.String structureKey) throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructure getStructure(long groupId, long classNameId,
		java.lang.String structureKey, boolean includeAncestorStructures)
		throws PortalException;

	/**
	* Returns the structure with the ID.
	*
	* @param structureId the primary key of the structure
	* @return the structure with the ID
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructure getStructure(long structureId)
		throws PortalException;

	public DDMStructure updateStructure(long groupId, long parentStructureId,
		long classNameId, java.lang.String structureKey,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, DDMForm ddmForm,
		DDMFormLayout ddmFormLayout, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the structure matching the class name ID, structure key, and
	* group, replacing its old parent structure, name map, description map, and
	* XSD with new ones.
	*
	* @param groupId the primary key of the group
	* @param parentStructureId the primary key of the new parent structure
	* @param classNameId the primary key of the class name for the
	structure's related model
	* @param structureKey the unique string identifying the structure
	* @param nameMap the structure's new locales and localized names
	* @param descriptionMap the structure's new locales and localized
	description
	* @param definition the structure's new XML schema definition
	* @param serviceContext the service context to be applied. Can set the
	modification date.
	* @return the updated structure
	* @deprecated As of 2.1.0, replaced by {@link #updateStructure(long, long,
	long, String, Map, Map, DDMForm, DDMFormLayout,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public DDMStructure updateStructure(long groupId, long parentStructureId,
		long classNameId, java.lang.String structureKey,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String definition, ServiceContext serviceContext)
		throws PortalException;

	public DDMStructure updateStructure(long structureId,
		long parentStructureId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, DDMForm ddmForm,
		DDMFormLayout ddmFormLayout, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the structure matching the structure ID, replacing the old parent
	* structure ID, name map, description map, and XSD with the new values.
	*
	* @param structureId the primary key of the structure
	* @param parentStructureId the new parent structure primary key
	* @param nameMap the structure's new locales and localized names
	* @param descriptionMap the structure's new locales and localized
	description
	* @param definition the new XML schema definition of the structure
	* @param serviceContext the service context to be applied. Can set the
	modification date.
	* @return the updated structure
	* @deprecated As of 2.1.0, replaced by {@link #updateStructure(long, long,
	Map, Map, DDMForm, DDMFormLayout, ServiceContext)}
	*/
	@java.lang.Deprecated
	public DDMStructure updateStructure(long structureId,
		long parentStructureId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap,
		java.lang.String definition, ServiceContext serviceContext)
		throws PortalException;

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
	* @return the number of matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds, long classNameId,
		java.lang.String keywords, int status);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds, long classNameId,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, int status, boolean andOperator);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> getStructures(long companyId, long[] groupIds,
		long classNameId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> getStructures(long companyId, long[] groupIds,
		long classNameId, int status, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator);

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
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @param orderByComparator the comparator to order the structures
	(optionally <code>null</code>)
	* @return the range of matching structures ordered by the comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> search(long companyId, long[] groupIds,
		long classNameId, java.lang.String keywords, int status, int start,
		int end, OrderByComparator<DDMStructure> orderByComparator);

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
	* @param andOperator whether every field must match its keywords, or just
	one field
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @param orderByComparator the comparator to order the structures
	(optionally <code>null</code>)
	* @return the range of matching structures ordered by the comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> search(long companyId, long[] groupIds,
		long classNameId, java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator);

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
	public void deleteStructure(long structureId) throws PortalException;

	public void revertStructure(long structureId, java.lang.String version,
		ServiceContext serviceContext) throws PortalException;
}