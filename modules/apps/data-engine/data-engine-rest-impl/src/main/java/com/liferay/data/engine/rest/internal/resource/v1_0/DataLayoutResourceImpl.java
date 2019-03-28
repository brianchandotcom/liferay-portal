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

package com.liferay.data.engine.rest.internal.resource.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.model.InternalDataRecordCollection;
import com.liferay.data.engine.rest.internal.resource.v1_0.util.DataEnginePermissionUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataLayoutResource;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/data-layout.properties",
	scope = ServiceScope.PROTOTYPE, service = DataLayoutResource.class
)
public class DataLayoutResourceImpl extends BaseDataLayoutResourceImpl {

	@Override
	public void deleteDataLayout(Long dataLayoutId) throws Exception {
		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataLayoutId,
			ActionKeys.DELETE);

		_ddmStructureLayoutLocalService.deleteDDMStructureLayout(dataLayoutId);
	}

	@Override
	public Page<DataLayout> getContentSpaceDataLayoutPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddmStructureLayoutLocalService.getStructureLayouts(
					contentSpaceId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toDataLayout),
			pagination,
			_ddmStructureLayoutLocalService.getStructureLayoutsCount(
				contentSpaceId));
	}

	@Override
	public DataLayout getDataLayout(Long dataLayoutId) throws Exception {
		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataLayoutId,
			ActionKeys.VIEW);

		return _toDataLayout(
			_ddmStructureLayoutLocalService.getDDMStructureLayout(
				dataLayoutId));
	}

	@Override
	public DataLayout postDataDefinitionDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		if (ArrayUtil.isEmpty(dataLayout.getName())) {
			throw new Exception("Name is required");
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.ADD_DATA_LAYOUT, ddmStructure.getGroupId(),
			_groupLocalService);

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.addStructureLayout(
				PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
				_getDDMStructureVersionId(dataLayout.getDataDefinitionId()),
				LocalizedValueUtil.toLocalizationMap(dataLayout.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataLayout.getDescription()),
				DataLayoutUtil.toJSON(dataLayout), new ServiceContext());

		dataLayout.setId(ddmStructureLayout.getStructureLayoutId());

		return dataLayout;
	}

	@Override
	public DataLayout putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception {

		if (ArrayUtil.isEmpty(dataLayout.getName())) {
			throw new Exception("Name is required");
		}

		_modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataLayoutId,
			ActionKeys.UPDATE);

		return _toDataLayout(
			_ddmStructureLayoutLocalService.updateStructureLayout(
				dataLayoutId,
				_getDDMStructureVersionId(dataLayout.getDataDefinitionId()),
				LocalizedValueUtil.toLocalizationMap(dataLayout.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataLayout.getDescription()),
				DataLayoutUtil.toJSON(dataLayout), new ServiceContext()));
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.rest.internal.model.InternalDataLayout)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<InternalDataRecordCollection>
			modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	private long _getDDMStructureId(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		return ddmStructure.getStructureId();
	}

	private long _getDDMStructureVersionId(Long deDataDefinitionId)
		throws Exception {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				deDataDefinitionId);

		return ddmStructureVersion.getStructureVersionId();
	}

	private DataLayout _toDataLayout(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		DataLayout dataLayout = DataLayoutUtil.toDataLayout(
			ddmStructureLayout.getDefinition());

		dataLayout.setDateCreated(ddmStructureLayout.getCreateDate());
		dataLayout.setDataDefinitionId(_getDDMStructureId(ddmStructureLayout));
		dataLayout.setId(ddmStructureLayout.getStructureLayoutId());
		dataLayout.setDescription(
			LocalizedValueUtil.toLocalizedValues(
				ddmStructureLayout.getDescriptionMap()));
		dataLayout.setDateModified(ddmStructureLayout.getModifiedDate());
		dataLayout.setName(
			LocalizedValueUtil.toLocalizedValues(
				ddmStructureLayout.getNameMap()));
		dataLayout.setUserId(ddmStructureLayout.getUserId());

		return dataLayout;
	}

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private ModelResourcePermission<InternalDataRecordCollection>
		_modelResourcePermission;

}