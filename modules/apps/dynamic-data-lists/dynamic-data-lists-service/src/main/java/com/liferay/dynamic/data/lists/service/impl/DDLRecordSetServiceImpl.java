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

package com.liferay.dynamic.data.lists.service.impl;

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.base.DDLRecordSetServiceBaseImpl;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * dynamic data list (DDL) record sets. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DDLRecordSetServiceImpl extends DDLRecordSetServiceBaseImpl {

	@Override
	public DDLRecordSet addRecordSet(
			long groupId, long ddmStructureId, String recordSetKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			int minDisplayRows, int scope, ServiceContext serviceContext)
		throws PortalException {

		DDLPermission.check(
			getPermissionChecker(), groupId, DDLActionKeys.ADD_RECORD_SET);

		return ddlRecordSetLocalService.addRecordSet(
			getUserId(), groupId, ddmStructureId, recordSetKey, nameMap,
			descriptionMap, minDisplayRows, scope, serviceContext);
	}

	@Override
	public void deleteRecordSet(long recordSetId) throws PortalException {
		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.DELETE);

		ddlRecordSetLocalService.deleteRecordSet(recordSetId);
	}

	@Override
	public DDLRecordSet getRecordSet(long recordSetId) throws PortalException {
		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.VIEW);

		return ddlRecordSetLocalService.getRecordSet(recordSetId);
	}

	@Override
	public List<DDLRecordSet> getRecordSets(long[] groupIds) {
		return ddlRecordSetPersistence.filterFindByGroupId(groupIds);
	}

	@Override
	public List<DDLRecordSet> search(
		long companyId, long groupId, String keywords, int scope, int start,
		int end, OrderByComparator<DDLRecordSet> orderByComparator) {

		return ddlRecordSetFinder.filterFindByKeywords(
			companyId, groupId, keywords, scope, start, end, orderByComparator);
	}

	@Override
	public List<DDLRecordSet> search(
		long companyId, long groupId, String name, String description,
		int scope, boolean andOperator, int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator) {

		return ddlRecordSetFinder.filterFindByC_G_N_D_S(
			companyId, groupId, name, description, scope, andOperator, start,
			end, orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, long groupId, String keywords, int scope) {

		return ddlRecordSetFinder.filterCountByKeywords(
			companyId, groupId, keywords, scope);
	}

	@Override
	public int searchCount(
		long companyId, long groupId, String name, String description,
		int scope, boolean andOperator) {

		return ddlRecordSetFinder.filterCountByC_G_N_D_S(
			companyId, groupId, name, description, scope, andOperator);
	}

	@Override
	public DDLRecordSet updateMinDisplayRows(
			long recordSetId, int minDisplayRows, ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.UPDATE);

		return ddlRecordSetLocalService.updateMinDisplayRows(
			recordSetId, minDisplayRows, serviceContext);
	}

	@Override
	public DDLRecordSet updateRecordSet(
			long recordSetId, DDMFormValues settingsDDMFormValues)
		throws PortalException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.UPDATE);

		return ddlRecordSetLocalService.updateRecordSet(
			recordSetId, settingsDDMFormValues);
	}

	@Override
	public DDLRecordSet updateRecordSet(
			long recordSetId, long ddmStructureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int minDisplayRows,
			ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), recordSetId, ActionKeys.UPDATE);

		return ddlRecordSetLocalService.updateRecordSet(
			recordSetId, ddmStructureId, nameMap, descriptionMap,
			minDisplayRows, serviceContext);
	}

	@Override
	public DDLRecordSet updateRecordSet(
			long groupId, long ddmStructureId, String recordSetKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			int minDisplayRows, ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSetPermission.check(
			getPermissionChecker(), groupId, recordSetKey, ActionKeys.UPDATE);

		return ddlRecordSetLocalService.updateRecordSet(
			groupId, ddmStructureId, recordSetKey, nameMap, descriptionMap,
			minDisplayRows, serviceContext);
	}

}