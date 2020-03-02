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

package com.liferay.data.engine.rest.internal.resource.v2_0;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.content.type.DataDefinitionContentTypeTracker;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataLayoutEntityModel;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataDefinitionModelResourcePermission;
import com.liferay.data.engine.rest.resource.v2_0.DataLayoutResource;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.spi.model.SPIDataLayout;
import com.liferay.data.engine.spi.resource.SPIDataLayoutResource;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-layout.properties",
	scope = ServiceScope.PROTOTYPE, service = DataLayoutResource.class
)
public class DataLayoutResourceImpl
	extends BaseDataLayoutResourceImpl implements EntityModelResource {

	@Override
	public void deleteDataLayout(Long dataLayoutId) throws Exception {
		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		DDMStructure ddmStructure = ddmStructureLayout.getDDMStructure();

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructure.getStructureId(), ActionKeys.DELETE);

		SPIDataLayoutResource spiDataLayoutResource =
			_getSPIDataLayoutResource();

		spiDataLayoutResource.deleteDataLayout(dataLayoutId);
	}

	@Override
	public Page<DataLayout> getDataDefinitionDataLayoutsPage(
			Long dataDefinitionId, String keywords, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		SPIDataLayoutResource spiDataLayoutResource =
			_getSPIDataLayoutResource();

		Page<SPIDataLayout> page = spiDataLayoutResource.getDataLayouts(
			dataDefinitionId, keywords,
			contextAcceptLanguage.getPreferredLocale(), pagination, sorts);

		Collection<SPIDataLayout> items = page.getItems();

		Stream<SPIDataLayout> stream = items.stream();

		return Page.of(
			stream.map(
				DataLayoutUtil::toDataLayout
			).collect(
				Collectors.toList()
			),
			pagination, page.getTotalCount());
	}

	@Override
	public DataLayout getDataLayout(Long dataLayoutId) throws Exception {
		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructureLayout.getDDMStructureId(), ActionKeys.VIEW);

		SPIDataLayoutResource spiDataLayoutResource =
			_getSPIDataLayoutResource();

		return DataLayoutUtil.toDataLayout(
			spiDataLayoutResource.getDataLayout(dataLayoutId));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public DataLayout getSiteDataLayoutByContentTypeByDataLayoutKey(
			Long siteId, String contentType, String dataLayoutKey)
		throws Exception {

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(
				siteId, dataDefinitionContentType.getClassNameId(),
				dataLayoutKey);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructureLayout.getDDMStructureId(), ActionKeys.VIEW);

		SPIDataLayoutResource spiDataLayoutResource =
			_getSPIDataLayoutResource();

		return DataLayoutUtil.toDataLayout(
			spiDataLayoutResource.getDataLayout(
				dataDefinitionContentType.getClassNameId(), dataLayoutKey,
				siteId));
	}

	@Override
	public DataLayout postDataDefinitionDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		_dataDefinitionModelResourcePermission.checkPortletPermission(
			PermissionThreadLocal.getPermissionChecker(), ddmStructure,
			DataActionKeys.ADD_DATA_DEFINITION);

		SPIDataLayoutResource spiDataLayoutResource =
			_getSPIDataLayoutResource();

		SPIDataLayout spiDataLayout = DataLayoutUtil.toSPIDataLayout(
			dataLayout);

		spiDataLayout.setDataDefinitionId(dataDefinitionId);

		return DataLayoutUtil.toDataLayout(
			spiDataLayoutResource.addDataLayout(spiDataLayout));
	}

	@Override
	public DataLayout putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception {

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructureLayout.getDDMStructureId(), ActionKeys.UPDATE);

		SPIDataLayoutResource spiDataLayoutResource =
			_getSPIDataLayoutResource();

		SPIDataLayout spiDataLayout = DataLayoutUtil.toSPIDataLayout(
			dataLayout);

		spiDataLayout.setId(dataLayoutId);
		spiDataLayout.setSiteId(ddmStructureLayout.getGroupId());

		return DataLayoutUtil.toDataLayout(
			spiDataLayoutResource.updateDataLayout(spiDataLayout));
	}

	private SPIDataLayoutResource _getSPIDataLayoutResource() {
		return new SPIDataLayoutResource(
			_ddmFormLayoutSerializer, _ddmStructureLayoutLocalService,
			_ddmStructureLocalService, _ddmStructureVersionLocalService,
			_deDataDefinitionFieldLinkLocalService);
	}

	private static final EntityModel _entityModel = new DataLayoutEntityModel();

	@Reference
	private DataDefinitionContentTypeTracker _dataDefinitionContentTypeTracker;

	@Reference
	private DataDefinitionModelResourcePermission
		_dataDefinitionModelResourcePermission;

	@Reference(target = "(ddm.form.layout.serializer.type=json)")
	private DDMFormLayoutSerializer _ddmFormLayoutSerializer;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

}