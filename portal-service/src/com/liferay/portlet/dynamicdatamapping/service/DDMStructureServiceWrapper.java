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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMStructureService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureService
 * @generated
 */
@ProviderType
public class DDMStructureServiceWrapper implements DDMStructureService,
	ServiceWrapper<DDMStructureService> {
	public DDMStructureServiceWrapper(DDMStructureService ddmStructureService) {
		_ddmStructureService = ddmStructureService;
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.addStructure(groupId, parentStructureId,
			classNameId, structureKey, nameMap, descriptionMap, ddmForm,
			storageType, type, serviceContext);
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
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd, java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.addStructure(groupId, parentStructureId,
			classNameId, structureKey, nameMap, descriptionMap, xsd,
			storageType, type, serviceContext);
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, long classNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.addStructure(userId, groupId, classNameId,
			nameMap, descriptionMap, ddmForm, serviceContext);
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
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, long classNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.addStructure(userId, groupId, classNameId,
			nameMap, descriptionMap, xsd, serviceContext);
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, java.lang.String parentStructureKey,
		long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.addStructure(userId, groupId,
			parentStructureKey, classNameId, structureKey, nameMap,
			descriptionMap, ddmForm, storageType, type, serviceContext);
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
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, java.lang.String parentStructureKey,
		long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd, java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.addStructure(userId, groupId,
			parentStructureKey, classNameId, structureKey, nameMap,
			descriptionMap, xsd, storageType, type, serviceContext);
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
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure copyStructure(
		long structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.copyStructure(structureId, nameMap,
			descriptionMap, serviceContext);
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure copyStructure(
		long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.copyStructure(structureId, serviceContext);
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
	@Override
	public void deleteStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddmStructureService.deleteStructure(structureId);
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
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		long groupId, long classNameId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.fetchStructure(groupId, classNameId,
			structureKey);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _ddmStructureService.getBeanIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getJournalFolderStructures(
		long[] groupIds, long journalFolderId, int restrictionType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.getJournalFolderStructures(groupIds,
			journalFolderId, restrictionType);
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
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, long classNameId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.getStructure(groupId, classNameId,
			structureKey);
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
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, long classNameId, java.lang.String structureKey,
		boolean includeAncestorStructures)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.getStructure(groupId, classNameId,
			structureKey, includeAncestorStructures);
	}

	/**
	* Returns the structure with the ID.
	*
	* @param structureId the primary key of the structure
	* @return the structure with the ID
	* @throws PortalException if the user did not have permission to view the
	structure or if a structure with the ID could not be found
	*/
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.getStructure(structureId);
	}

	/**
	* Returns all the structures in the group that the user has permission to
	* view.
	*
	* @param groupId the primary key of the group
	* @return the structures in the group that the user has permission to view
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long groupId) {
		return _ddmStructureService.getStructures(groupId);
	}

	/**
	* Returns all the structures in the groups that the user has permission to
	* view.
	*
	* @param groupIds the primary key of the groups
	* @return the structures in the groups that the user has permission to view
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long[] groupIds) {
		return _ddmStructureService.getStructures(groupIds);
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
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long[] groupIds, long classNameId) {
		return _ddmStructureService.getStructures(groupIds, classNameId);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		long[] groupIds, long classNameId, int start, int end) {
		return _ddmStructureService.getStructures(groupIds, classNameId, start,
			end);
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
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		return _ddmStructureService.search(companyId, groupIds, classNameIds,
			keywords, start, end, orderByComparator);
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
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, boolean andOperator, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		return _ddmStructureService.search(companyId, groupIds, classNameIds,
			name, description, storageType, type, andOperator, start, end,
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
	@Override
	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String keywords) {
		return _ddmStructureService.searchCount(companyId, groupIds,
			classNameIds, keywords);
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
	@Override
	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String name,
		java.lang.String description, java.lang.String storageType, int type,
		boolean andOperator) {
		return _ddmStructureService.searchCount(companyId, groupIds,
			classNameIds, name, description, storageType, type, andOperator);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddmStructureService.setBeanIdentifier(beanIdentifier);
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
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.updateStructure(groupId, parentStructureId,
			classNameId, structureKey, nameMap, descriptionMap, xsd,
			serviceContext);
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
	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long structureId, long parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureService.updateStructure(structureId,
			parentStructureId, nameMap, descriptionMap, xsd, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DDMStructureService getWrappedDDMStructureService() {
		return _ddmStructureService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDDMStructureService(
		DDMStructureService ddmStructureService) {
		_ddmStructureService = ddmStructureService;
	}

	@Override
	public DDMStructureService getWrappedService() {
		return _ddmStructureService;
	}

	@Override
	public void setWrappedService(DDMStructureService ddmStructureService) {
		_ddmStructureService = ddmStructureService;
	}

	private DDMStructureService _ddmStructureService;
}