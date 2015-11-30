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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for DDMStructure. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLocalServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMStructureLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMStructureLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMStructureLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStructureLocalServiceUtil} to access the d d m structure local service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMStructureLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the d d m structure to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.dynamic.data.mapping.model.DDMStructure addDDMStructure(
		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure);

	public com.liferay.dynamic.data.mapping.model.DDMStructure addStructure(
		long userId, long groupId, long classNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout ddmFormLayout,
		java.lang.String storageType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

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
	* @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	long, Map, Map, DDMForm, DDMFormLayout, ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.dynamic.data.mapping.model.DDMStructure addStructure(
		long userId, long groupId, long classNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String definition,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMStructure addStructure(
		long userId, long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout ddmFormLayout,
		java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds a structure referencing its parent structure.
	*
	* @param userId the primary key of the structure's creator/owner
	* @param groupId the primary key of the group
	* @param parentStructureId the primary key of the parent structure
	(optionally {@link
	DDMStructureConstants#DEFAULT_PARENT_STRUCTURE_ID})
	* @param classNameId the primary key of the class name for the
	structure's related model
	* @param structureKey the unique string identifying the structure
	(optionally <code>null</code>)
	* @param nameMap the structure's locales and localized names
	* @param descriptionMap the structure's locales and localized
	descriptions
	* @param definition the structure's XML schema definition
	* @param storageType the structure's storage type. It can be "xml" or
	"expando". For more information, see {@link StorageType}.
	* @param type the structure's type. For more information, see {@link
	DDMStructureConstants}.
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions,
	and group permissions for the structure.
	* @return the structure
	* @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	long, long, String, Map, Map, DDMForm, DDMFormLayout, String,
	int, ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.dynamic.data.mapping.model.DDMStructure addStructure(
		long userId, long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String definition, java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMStructure addStructure(
		long userId, long groupId, java.lang.String parentStructureKey,
		long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout ddmFormLayout,
		java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

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
	"expando". For more information, see {@link StorageType}.
	* @param type the structure's type. For more information, see {@link
	DDMStructureConstants}.
	* @param serviceContext the service context to be applied. Can set the
	UUID, creation date, modification date, guest permissions and
	group permissions for the structure.
	* @return the structure
	* @deprecated As of 7.0.0, replaced by {@link #addStructure(long, long,
	String, long, String, Map, Map, DDMForm, DDMFormLayout,
	String, int, ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.dynamic.data.mapping.model.DDMStructure addStructure(
		long userId, long groupId, java.lang.String parentStructureKey,
		long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String definition, java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds the resources to the structure.
	*
	* @param structure the structure to add resources to
	* @param addGroupPermissions whether to add group permissions
	* @param addGuestPermissions whether to add guest permissions
	*/
	public void addStructureResources(
		com.liferay.dynamic.data.mapping.model.DDMStructure structure,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	/**
	* Adds the model resources with the permissions to the structure.
	*
	* @param structure the structure to add resources to
	* @param modelPermissions the model permissions to be added
	*/
	public void addStructureResources(
		com.liferay.dynamic.data.mapping.model.DDMStructure structure,
		com.liferay.portal.service.permission.ModelPermissions modelPermissions)
		throws PortalException;

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
	*/
	public com.liferay.dynamic.data.mapping.model.DDMStructure copyStructure(
		long userId, long structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMStructure copyStructure(
		long userId, long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new d d m structure with the primary key. Does not add the d d m structure to the database.
	*
	* @param structureId the primary key for the new d d m structure
	* @return the new d d m structure
	*/
	public com.liferay.dynamic.data.mapping.model.DDMStructure createDDMStructure(
		long structureId);

	/**
	* Deletes the d d m structure from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.dynamic.data.mapping.model.DDMStructure deleteDDMStructure(
		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure);

	/**
	* Deletes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure that was removed
	* @throws PortalException if a d d m structure with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.dynamic.data.mapping.model.DDMStructure deleteDDMStructure(
		long structureId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

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
	*/
	public void deleteStructure(long groupId, long classNameId,
		java.lang.String structureKey) throws PortalException;

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
	@com.liferay.portal.kernel.systemevent.SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteStructure(
		com.liferay.dynamic.data.mapping.model.DDMStructure structure)
		throws PortalException;

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
	public void deleteStructures(long groupId) throws PortalException;

	public void deleteStructures(long groupId, long classNameId)
		throws PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStructure fetchDDMStructure(
		long structureId);

	/**
	* Returns the d d m structure matching the UUID and group.
	*
	* @param uuid the d d m structure's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStructure fetchDDMStructureByUuidAndGroupId(
		java.lang.String uuid, long groupId);

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
	public com.liferay.dynamic.data.mapping.model.DDMStructure fetchStructure(
		long groupId, long classNameId, java.lang.String structureKey);

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
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStructure fetchStructure(
		long groupId, long classNameId, java.lang.String structureKey,
		boolean includeAncestorStructures) throws PortalException;

	/**
	* Returns the structure with the ID.
	*
	* @param structureId the primary key of the structure
	* @return the structure with the structure ID, or <code>null</code> if a
	matching structure could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStructure fetchStructure(
		long structureId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns all the structures matching the class name ID.
	*
	* @param companyId the primary key of the structure's company
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the structures matching the class name ID
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getClassStructures(
		long companyId, long classNameId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getClassStructures(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMStructure> orderByComparator);

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
	* @param companyId the primary key of the structure's company
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @return the range of matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getClassStructures(
		long companyId, long classNameId, int start, int end);

	/**
	* Returns the d d m structure with the primary key.
	*
	* @param structureId the primary key of the d d m structure
	* @return the d d m structure
	* @throws PortalException if a d d m structure with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStructure getDDMStructure(
		long structureId) throws PortalException;

	/**
	* Returns the d d m structure matching the UUID and group.
	*
	* @param uuid the d d m structure's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m structure
	* @throws PortalException if a matching d d m structure could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStructure getDDMStructureByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @return the range of d d m structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getDDMStructures(
		int start, int end);

	/**
	* Returns all the d d m structures matching the UUID and company.
	*
	* @param uuid the UUID of the d d m structures
	* @param companyId the primary key of the company
	* @return the matching d d m structures, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getDDMStructuresByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of d d m structures matching the UUID and company.
	*
	* @param uuid the UUID of the d d m structures
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of d d m structures
	* @param end the upper bound of the range of d d m structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching d d m structures, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getDDMStructuresByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMStructure> orderByComparator);

	/**
	* Returns the number of d d m structures.
	*
	* @return the number of d d m structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDDMStructuresCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

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
	public com.liferay.dynamic.data.mapping.model.DDMStructure getStructure(
		long groupId, long classNameId, java.lang.String structureKey)
		throws PortalException;

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
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStructure getStructure(
		long groupId, long classNameId, java.lang.String structureKey,
		boolean includeAncestorStructures) throws PortalException;

	/**
	* Returns all the structures matching the group, name, and description.
	*
	* @param groupId the primary key of the structure's group
	* @param name the structure's name
	* @param description the structure's description
	* @return the matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructure(
		long groupId, java.lang.String name, java.lang.String description);

	/**
	* Returns the structure with the ID.
	*
	* @param structureId the primary key of the structure
	* @return the structure with the ID
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.dynamic.data.mapping.model.DDMStructure getStructure(
		long structureId) throws PortalException;

	/**
	* Returns all the structures present in the system.
	*
	* @return the structures present in the system
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures();

	/**
	* Returns all the structures present in the group.
	*
	* @param groupId the primary key of the group
	* @return the structures present in the group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
		long groupId);

	/**
	* Returns all the structures matching class name ID and group.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
		long groupId, long classNameId);

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
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @return the range of matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
		long groupId, long classNameId, int start, int end);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMStructure> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
		long groupId, java.lang.String name, java.lang.String description);

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
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @return the range of matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
		long groupId, int start, int end);

	/**
	* Returns all the structures belonging to the groups.
	*
	* @param groupIds the primary keys of the groups
	* @return the structures belonging to the groups
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
		long[] groupIds);

	/**
	* Returns all the structures matching the class name ID and belonging to
	* the groups.
	*
	* @param groupIds the primary keys of the groups
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
		long[] groupIds, long classNameId);

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
	* @param groupIds the primary keys of the groups
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @param start the lower bound of the range of structures to return
	* @param end the upper bound of the range of structures to return (not
	inclusive)
	* @return the range of matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
		long[] groupIds, long classNameId, int start, int end);

	/**
	* Returns the number of structures belonging to the group.
	*
	* @param groupId the primary key of the group
	* @return the number of structures belonging to the group
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getStructuresCount(long groupId);

	/**
	* Returns the number of structures matching the class name ID and group.
	*
	* @param groupId the primary key of the group
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the number of matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getStructuresCount(long groupId, long classNameId);

	/**
	* Returns the number of structures matching the class name ID and belonging
	* to the groups.
	*
	* @param groupIds the primary keys of the groups
	* @param classNameId the primary key of the class name for the structure's
	related model
	* @return the number of matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getStructuresCount(long[] groupIds, long classNameId);

	public void revertStructure(long userId, long structureId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

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
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> search(
		long companyId, long[] groupIds, long classNameId,
		java.lang.String keywords, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMStructure> orderByComparator);

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
	"expando". For more information, see {@link StorageType}.
	* @param type the structure's type. For more information, see {@link
	DDMStructureConstants}.
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
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> search(
		long companyId, long[] groupIds, long classNameId,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, int status,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMStructure> orderByComparator);

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
	* @param classNameId
	* @param name the name keywords
	* @param description the description keywords
	* @param storageType the structure's storage type. It can be "xml" or
	"expando". For more information, see {@link StorageType}.
	* @param type the structure's type. For more information, see {@link
	DDMStructureConstants}.
	* @param andOperator whether every field must match its keywords, or just
	one field
	* @return the number of matching structures
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long[] groupIds, long classNameId,
		java.lang.String name, java.lang.String description,
		java.lang.String storageType, int type, int status, boolean andOperator);

	/**
	* Updates the d d m structure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure
	* @return the d d m structure that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.dynamic.data.mapping.model.DDMStructure updateDDMStructure(
		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure);

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
	structure's modification date.
	* @return the updated structure
	* @deprecated As of 7.0.0, replaced by {@link #updateStructure(long, long,
	long, long, String, Map, Map, DDMForm, DDMFormLayout,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.dynamic.data.mapping.model.DDMStructure updateStructure(
		long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String definition,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

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
	* @deprecated As of 7.0.0, replaced by {@link #updateStructure(long, long,
	long, Map, Map, DDMForm, DDMFormLayout, ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.dynamic.data.mapping.model.DDMStructure updateStructure(
		long structureId, long parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String definition,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMStructure updateStructure(
		long userId, long groupId, long parentStructureId, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout ddmFormLayout,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMStructure updateStructure(
		long userId, long structureId,
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout ddmFormLayout,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMStructure updateStructure(
		long userId, long structureId, long parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout ddmFormLayout,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the structure matching the structure ID, replacing its XSD with a
	* new one.
	*
	* @param structureId the primary key of the structure
	* @param definition the structure's new XML schema definition
	* @param serviceContext the service context to be applied. Can set the
	structure's modification date.
	* @return the updated structure
	* @deprecated As of 7.0.0, replaced by {@link #updateStructure(long,
	DDMForm, DDMFormLayout, ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.dynamic.data.mapping.model.DDMStructure updateXSD(
		long structureId, java.lang.String definition,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}