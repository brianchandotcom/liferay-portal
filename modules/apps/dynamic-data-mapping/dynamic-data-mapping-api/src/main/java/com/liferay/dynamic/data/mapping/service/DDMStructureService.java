/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for DDMStructure. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface DDMStructureService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMStructureServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the ddm structure remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link DDMStructureServiceUtil} if injection and service tracking are not available.
	 */
	public DDMStructure addStructure(
			long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException;

	public DDMStructure addStructure(
			long groupId, long classNameId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType,
			ServiceContext serviceContext)
		throws PortalException;

	public DDMStructure addStructure(
			long groupId, String parentStructureKey, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Copies a structure, creating a new structure with all the values
	 * extracted from the original one. The new structure supports a new name
	 * and description.
	 *
	 * @param sourceStructureId the primary key of the structure to be copied
	 * @param nameMap the new structure's locales and localized names
	 * @param descriptionMap the new structure's locales and localized
	 descriptions
	 * @param serviceContext the service context to be applied. Can set the
	 UUID, creation date, modification date, guest permissions, and
	 group permissions for the structure.
	 * @return the new structure
	 */
	public DDMStructure copyStructure(
			long sourceStructureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException;

	public DDMStructure copyStructure(
			long sourceStructureId, ServiceContext serviceContext)
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
	public DDMStructure fetchStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructure fetchStructure(
			long groupId, long classNameId, String structureKey,
			boolean includeAncestorStructures)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * Returns the structure with the ID.
	 *
	 * @param structureId the primary key of the structure
	 * @return the structure with the ID
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructure getStructure(long structureId) throws PortalException;

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
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
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
	 search
	 * @return the matching structure
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey,
			boolean includeAncestorStructures)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> getStructures(
		long companyId, long[] groupIds, long classNameId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> getStructures(
		long companyId, long[] groupIds, long classNameId, int status,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> getStructures(
		long companyId, long[] groupIds, long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> getStructures(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getStructuresCount(
		long companyId, long[] groupIds, long classNameId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getStructuresCount(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status);

	public void revertStructure(
			long structureId, String version, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> search(
			long companyId, long[] groupIds, long classNameId, long classPK,
			String keywords, int status, int start, int end,
			OrderByComparator<DDMStructure> orderByComparator)
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
	 * @param type the structure's type. For more information, see {@link
	 com.liferay.dynamic.data.mapping.constants.DDMStructureConstants}.
	 * @param status the workflow's status.
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the structures
	 (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> search(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int type, int status, int start, int end,
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
	 * @param status the workflow's status.
	 * @param start the lower bound of the range of structures to return
	 * @param end the upper bound of the range of structures to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the structures
	 (optionally <code>null</code>)
	 * @return the range of matching structures ordered by the comparator
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMStructure> search(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator);

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
	 com.liferay.dynamic.data.mapping.constants.DDMStructureConstants}.
	 * @param status the workflow's status.
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
	public List<DDMStructure> search(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(
			long companyId, long[] groupIds, long classNameId, long classPK,
			String keywords, int status)
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
	 * @param status the workflow's status.
	 * @return the number of matching structures
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status);

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
	 * @param type the structure's type. For more information, see {@link
	 com.liferay.dynamic.data.mapping.constants.DDMStructureConstants}.
	 * @param status the workflow's status.
	 * @return the number of matching structures
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int type, int status);

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
	 com.liferay.dynamic.data.mapping.constants.DDMStructureConstants}.
	 * @param andOperator whether every field must match its keywords, or just
	 one field
	 * @return the number of matching structures
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator);

	public DDMStructure updateStructure(
			long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, ServiceContext serviceContext)
		throws PortalException;

	public DDMStructure updateStructure(
			long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException;

}