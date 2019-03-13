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
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataLayoutResource;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/Data-layout.properties",
	scope = ServiceScope.PROTOTYPE, service = DataLayoutResource.class
)
public class DataLayoutResourceImpl extends BaseDataLayoutResourceImpl {

	@Override
	public boolean deleteDataLayout(Long dataLayoutId) throws Exception {
		_ddmStructureLayoutLocalService.deleteDDMStructureLayout(dataLayoutId);

		return true;
	}

	@Override
	public DataLayout getDataLayout(Long dataLayoutId) throws Exception {
		return _toDataLayout(
			_ddmStructureLayoutLocalService.getDDMStructureLayout(
				dataLayoutId));
	}

	@Override
	public DataLayout postDataLayout(Long contentSpaceId, DataLayout dataLayout)
		throws Exception {

		LocalizedValue[] name = dataLayout.getName();

		if (name.length < 1) {
			throw new Exception("Layout name cannot be null");
		}

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.addStructureLayout(
				PrincipalThreadLocal.getUserId(), contentSpaceId,
				_getDDMStructureVersionId(dataLayout.getDataDefinitionId()),
				LocalizedValueUtil.toLocalizationMap(name),
				LocalizedValueUtil.toLocalizationMap(
					dataLayout.getDescription()),
				DataLayoutUtil.toJSONString(dataLayout), new ServiceContext());

		dataLayout.setId(ddmStructureLayout.getStructureLayoutId());

		return dataLayout;
	}

	@Override
	public DataLayout putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception {

		LocalizedValue[] name = dataLayout.getName();

		if (name.length < 1) {
			throw new Exception("Layout name cannot be null");
		}

		return _toDataLayout(
			_ddmStructureLayoutLocalService.updateStructureLayout(
				dataLayout.getId(),
				_getDDMStructureVersionId(dataLayout.getDataDefinitionId()),
				LocalizedValueUtil.toLocalizationMap(name),
				LocalizedValueUtil.toLocalizationMap(
					dataLayout.getDescription()),
				DataLayoutUtil.toJSONString(dataLayout), new ServiceContext()));
	}

	private Long _getDDMStructureId(DDMStructureLayout ddmStructureLayout)
		throws PortalException {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		return ddmStructure.getStructureId();
	}

	private Long _getDDMStructureVersionId(Long deDataDefinitionId)
		throws PortalException {

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
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

}