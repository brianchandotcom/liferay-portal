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

import com.liferay.dynamic.data.mapping.internal.search.util.DDMSearchHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureServiceBaseImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * dynamic data mapping (DDM) structures. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Marcellus Tavares
 * @see    DDMStructureLocalServiceImpl
 */
@Component(
	property = {
		"json.web.service.context.name=ddm",
		"json.web.service.context.path=DDMStructure"
	},
	service = AopService.class
)
public class DDMStructureServiceImpl extends DDMStructureServiceBaseImpl {

	@Override
	public DDMStructure addStructure(
			long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_ddmPermissionSupport.checkAddStructurePermission(
			getPermissionChecker(), groupId, classNameId);

		return ddmStructureLocalService.addStructure(
			getUserId(), groupId, parentStructureId, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType, type,
			serviceContext);
	}

	@Override
	public DDMStructure addStructure(
			long groupId, long classNameId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType,
			ServiceContext serviceContext)
		throws PortalException {

		_ddmPermissionSupport.checkAddStructurePermission(
			getPermissionChecker(), groupId, classNameId);

		return ddmStructureLocalService.addStructure(
			getUserId(), groupId, classNameId, nameMap, descriptionMap, ddmForm,
			ddmFormLayout, storageType, serviceContext);
	}

	@Override
	public DDMStructure addStructure(
			long groupId, String parentStructureKey, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_ddmPermissionSupport.checkAddStructurePermission(
			getPermissionChecker(), groupId, classNameId);

		return ddmStructureLocalService.addStructure(
			getUserId(), groupId, parentStructureKey, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType, type,
			serviceContext);
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
	 */
	@Override
	public DDMStructure copyStructure(
			long structureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		_ddmStructureModelResourcePermission.check(
			getPermissionChecker(), structure, ActionKeys.VIEW);

		_ddmPermissionSupport.checkAddStructurePermission(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			structure.getClassNameId());

		return ddmStructureLocalService.copyStructure(
			getUserId(), structureId, nameMap, descriptionMap, serviceContext);
	}

	@Override
	public DDMStructure copyStructure(
			long structureId, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByPrimaryKey(
			structureId);

		_ddmStructureModelResourcePermission.check(
			getPermissionChecker(), structure, ActionKeys.VIEW);

		_ddmPermissionSupport.checkAddStructurePermission(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			structure.getClassNameId());

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
	 * @param structureId the primary key of the structure to be deleted
	 */
	@Override
	public void deleteStructure(long structureId) throws PortalException {
		_ddmStructureModelResourcePermission.check(
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
	 */
	@Override
	public DDMStructure fetchStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		DDMStructure ddmStructure = ddmStructurePersistence.fetchByG_C_S(
			groupId, classNameId, structureKey);

		if (ddmStructure != null) {
			_ddmStructureModelResourcePermission.check(
				getPermissionChecker(), ddmStructure, ActionKeys.VIEW);
		}

		return ddmStructure;
	}

	@Override
	public DDMStructure fetchStructure(
			long groupId, long classNameId, String structureKey,
			boolean includeAncestorStructures)
		throws PortalException {

		DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
			groupId, classNameId, structureKey, includeAncestorStructures);

		if (ddmStructure != null) {
			_ddmStructureModelResourcePermission.check(
				getPermissionChecker(), ddmStructure, ActionKeys.VIEW);
		}

		return ddmStructure;
	}

	/**
	 * Returns the structure with the ID.
	 *
	 * @param  structureId the primary key of the structure
	 * @return the structure with the ID
	 */
	@Override
	public DDMStructure getStructure(long structureId) throws PortalException {
		_ddmStructureModelResourcePermission.check(
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
	 */
	@Override
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey)
		throws PortalException {

		DDMStructure structure = ddmStructureLocalService.getStructure(
			groupId, classNameId, structureKey);

		_ddmStructureModelResourcePermission.check(
			getPermissionChecker(), structure, ActionKeys.VIEW);

		return structure;
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
	 *         search
	 * @return the matching structure
	 */
	@Override
	public DDMStructure getStructure(
			long groupId, long classNameId, String structureKey,
			boolean includeAncestorStructures)
		throws PortalException {

		DDMStructure structure = ddmStructureLocalService.getStructure(
			groupId, classNameId, structureKey, includeAncestorStructures);

		_ddmStructureModelResourcePermission.check(
			getPermissionChecker(), structure, ActionKeys.VIEW);

		return structure;
	}

	@Override
	public List<DDMStructure> getStructures(
		long companyId, long[] groupIds, long classNameId, int status) {

		try {
			SearchContext searchContext =
				_ddmSearchHelper.buildStructureSearchContext(
					companyId, groupIds, getUserId(), classNameId, null,
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, null,
					status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			return _ddmSearchHelper.doSearch(
				searchContext, DDMStructure.class,
				ddmStructurePersistence::findByPrimaryKey);
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return Collections.emptyList();
	}

	@Override
	public List<DDMStructure> getStructures(
		long companyId, long[] groupIds, long classNameId, int status,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {

		try {
			SearchContext searchContext =
				_ddmSearchHelper.buildStructureSearchContext(
					companyId, groupIds, getUserId(), classNameId, null,
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, null,
					status, start, end, orderByComparator);

			return _ddmSearchHelper.doSearch(
				searchContext, DDMStructure.class,
				ddmStructurePersistence::findByPrimaryKey);
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return Collections.emptyList();
	}

	@Override
	public List<DDMStructure> getStructures(
		long companyId, long[] groupIds, long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructureFinder.filterFindByC_G_C_S(
			companyId, groupIds, classNameId, WorkflowConstants.STATUS_ANY,
			start, end, orderByComparator);
	}

	@Override
	public List<DDMStructure> getStructures(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		return ddmStructureLocalService.getStructures(
			companyId, groupIds, classNameId, keywords, status, start, end,
			orderByComparator);
	}

	@Override
	public int getStructuresCount(
		long companyId, long[] groupIds, long classNameId) {

		return _ddmStructureFinder.filterCountByC_G_C_S(
			companyId, groupIds, classNameId, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public int getStructuresCount(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status) {

		return ddmStructureLocalService.getStructuresCount(
			companyId, groupIds, classNameId, keywords, status);
	}

	@Override
	public void revertStructure(
			long structureId, String version, ServiceContext serviceContext)
		throws PortalException {

		_ddmStructureModelResourcePermission.check(
			getPermissionChecker(), structureId, ActionKeys.UPDATE);

		ddmStructureLocalService.revertStructure(
			getUserId(), structureId, version, serviceContext);
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
	 * @param  type the structure's type. For more information, see {@link
	 *         com.liferay.dynamic.data.mapping.model.DDMStructureConstants}.
	 * @param  status the workflow's status.
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
		int type, int status, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		try {
			SearchContext searchContext =
				_ddmSearchHelper.buildStructureSearchContext(
					companyId, groupIds, getUserId(), classNameId, null,
					keywords, keywords, StringPool.BLANK, type, status, start,
					end, orderByComparator);

			return _ddmSearchHelper.doSearch(
				searchContext, DDMStructure.class,
				ddmStructurePersistence::findByPrimaryKey);
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return Collections.emptyList();
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
	 * @param  status the workflow's status.
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

		try {
			SearchContext searchContext =
				_ddmSearchHelper.buildStructureSearchContext(
					companyId, groupIds, getUserId(), classNameId, null,
					keywords, keywords, StringPool.BLANK, null, status, start,
					end, orderByComparator);

			return _ddmSearchHelper.doSearch(
				searchContext, DDMStructure.class,
				ddmStructureLocalService::fetchStructure);
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return Collections.emptyList();
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
	 *         "expando". For more information, see {@link
	 *         com.liferay.dynamic.data.mapping.storage.StorageType}.
	 * @param  type the structure's type. For more information, see {@link
	 *         com.liferay.dynamic.data.mapping.model.DDMStructureConstants}.
	 * @param  status the workflow's status.
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

		try {
			SearchContext searchContext =
				_ddmSearchHelper.buildStructureSearchContext(
					companyId, groupIds, getUserId(), classNameId, null, name,
					description, storageType, type, status, start, end,
					orderByComparator);

			return _ddmSearchHelper.doSearch(
				searchContext, DDMStructure.class,
				ddmStructurePersistence::findByPrimaryKey);
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return Collections.emptyList();
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
	 * @param  status the workflow's status.
	 * @return the number of matching structures
	 */
	@Override
	public int searchCount(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status) {

		try {
			SearchContext searchContext =
				_ddmSearchHelper.buildStructureSearchContext(
					companyId, groupIds, getUserId(), classNameId, null,
					keywords, keywords, StringPool.BLANK, null, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			return _ddmSearchHelper.doSearchCount(
				searchContext, DDMStructure.class);
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return 0;
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
	 * @param  type the structure's type. For more information, see {@link
	 *         com.liferay.dynamic.data.mapping.model.DDMStructureConstants}.
	 * @param  status the workflow's status.
	 * @return the number of matching structures
	 */
	@Override
	public int searchCount(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int type, int status) {

		try {
			SearchContext searchContext =
				_ddmSearchHelper.buildStructureSearchContext(
					companyId, groupIds, getUserId(), classNameId, null,
					keywords, keywords, StringPool.BLANK, type, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			return _ddmSearchHelper.doSearchCount(
				searchContext, DDMStructure.class);
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return 0;
	}

	/**
	 * Returns the number of structures matching the groups, class name IDs,
	 * name keyword, description keyword, storage type, and type
	 *
	 * @param  companyId the primary key of the structure's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameId the primary key of the class name of the model the
	 *         structure is related to
	 * @param  name the name keywords
	 * @param  description the description keywords
	 * @param  storageType the structure's storage type. It can be "xml" or
	 *         "expando". For more information, see {@link
	 *         com.liferay.dynamic.data.mapping.storage.StorageType}.
	 * @param  type the structure's type. For more information, see {@link
	 *         com.liferay.dynamic.data.mapping.model.DDMStructureConstants}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @return the number of matching structures
	 */
	@Override
	public int searchCount(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator) {

		try {
			SearchContext searchContext =
				_ddmSearchHelper.buildStructureSearchContext(
					companyId, groupIds, getUserId(), classNameId, null, name,
					description, storageType, type, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			return _ddmSearchHelper.doSearchCount(
				searchContext, DDMStructure.class);
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return 0;
	}

	@Override
	public DDMStructure updateStructure(
			long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, ServiceContext serviceContext)
		throws PortalException {

		DDMStructure structure = ddmStructurePersistence.findByG_C_S(
			groupId, classNameId, structureKey);

		_ddmStructureModelResourcePermission.check(
			getPermissionChecker(), structure, ActionKeys.UPDATE);

		return ddmStructureLocalService.updateStructure(
			getUserId(), groupId, parentStructureId, classNameId, structureKey,
			nameMap, descriptionMap, ddmForm, ddmFormLayout, serviceContext);
	}

	@Override
	public DDMStructure updateStructure(
			long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		_ddmStructureModelResourcePermission.check(
			getPermissionChecker(), structureId, ActionKeys.UPDATE);

		return ddmStructureLocalService.updateStructure(
			getUserId(), structureId, parentStructureId, nameMap,
			descriptionMap, ddmForm, ddmFormLayout, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureServiceImpl.class);

	private static volatile ModelResourcePermission<DDMStructure>
		_ddmStructureModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DDMStructureServiceImpl.class,
				"_ddmStructureModelResourcePermission", DDMStructure.class);

	@Reference
	private DDMPermissionSupport _ddmPermissionSupport;

	@Reference
	private DDMSearchHelper _ddmSearchHelper;

	@Reference
	private DDMStructureFinder _ddmStructureFinder;

}