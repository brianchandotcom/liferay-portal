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

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.model.DEDataDefinitionFieldLink;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionFieldLink;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataListView;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeTracker;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionFieldLinkResource;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.spi.converter.SPIDDMFormRuleConverter;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-definition-field-link.properties",
	scope = ServiceScope.PROTOTYPE,
	service = DataDefinitionFieldLinkResource.class
)
@CTAware
public class DataDefinitionFieldLinkResourceImpl
	extends BaseDataDefinitionFieldLinkResourceImpl {

	@Override
	public Page<DataDefinitionFieldLink>
			getDataDefinitionDataDefinitionFieldLinkPage(
				Long dataDefinitionId, String fieldName)
		throws Exception {

		List<DEDataDefinitionFieldLink> deDataDefinitionFieldLinks = null;

		if (Validator.isNotNull(fieldName)) {
			deDataDefinitionFieldLinks =
				_deDataDefinitionFieldLinkLocalService.
					getDEDataDefinitionFieldLinks(
						dataDefinitionId, new String[] {fieldName});
		}
		else {
			deDataDefinitionFieldLinks =
				_deDataDefinitionFieldLinkLocalService.
					getDEDataDefinitionFieldLinks(dataDefinitionId);
		}

		Map<Long, DataDefinitionFieldLink> dataDefinitionFieldLinksMap =
			new HashMap<>();

		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
				deDataDefinitionFieldLinks) {

			_addDataDefinitionFieldLink(
				dataDefinitionFieldLinksMap, dataDefinitionId,
				deDataDefinitionFieldLink, fieldName);
		}

		return Page.of(dataDefinitionFieldLinksMap.values());
	}

	private void _addDataDefinitionFieldLink(
			Map<Long, DataDefinitionFieldLink> dataDefinitionFieldLinks,
			Long dataDefinitionId,
			DEDataDefinitionFieldLink deDataDefinitionFieldLink,
			String fieldName)
		throws Exception {

		if (_portal.getClassNameId(DDMStructure.class) ==
				deDataDefinitionFieldLink.getClassNameId()) {

			dataDefinitionFieldLinks.putIfAbsent(
				deDataDefinitionFieldLink.getClassPK(),
				_createDataDefinitionFieldLink(
					deDataDefinitionFieldLink.getClassPK()));
		}
		else if (_portal.getClassNameId(DDMStructureLayout.class) ==
					deDataDefinitionFieldLink.getClassNameId()) {

			DDMStructureLayout ddmStructureLayout =
				_ddmStructureLayoutLocalService.getStructureLayout(
					deDataDefinitionFieldLink.getClassPK());

			if (Validator.isNull(fieldName) &&
				(dataDefinitionId == ddmStructureLayout.getDDMStructureId())) {

				return;
			}

			DataDefinitionFieldLink dataDefinitionFieldLink =
				dataDefinitionFieldLinks.getOrDefault(
					ddmStructureLayout.getDDMStructureId(),
					_createDataDefinitionFieldLink(
						ddmStructureLayout.getDDMStructureId()));

			dataDefinitionFieldLink.setDataLayouts(
				ArrayUtil.append(
					dataDefinitionFieldLink.getDataLayouts(),
					new DataLayout() {
						{
							id = ddmStructureLayout.getStructureLayoutId();
							name = LocalizedValueUtil.toStringObjectMap(
								ddmStructureLayout.getNameMap());
						}
					}));

			dataDefinitionFieldLinks.put(
				ddmStructureLayout.getDDMStructureId(),
				dataDefinitionFieldLink);
		}
		else if (_portal.getClassNameId(DEDataListView.class) ==
					deDataDefinitionFieldLink.getClassNameId()) {

			DEDataListView deDataListView =
				_deDataListViewLocalService.getDEDataListView(
					deDataDefinitionFieldLink.getClassPK());

			DataDefinitionFieldLink dataDefinitionFieldLink =
				dataDefinitionFieldLinks.getOrDefault(
					deDataListView.getDdmStructureId(),
					_createDataDefinitionFieldLink(
						deDataListView.getDdmStructureId()));

			dataDefinitionFieldLink.setDataListViews(
				ArrayUtil.append(
					dataDefinitionFieldLink.getDataListViews(),
					new DataListView() {
						{
							id =
								deDataDefinitionFieldLink.
									getDeDataDefinitionFieldLinkId();
							name = LocalizedValueUtil.toStringObjectMap(
								deDataListView.getNameMap());
						}
					}));

			dataDefinitionFieldLinks.put(
				deDataListView.getDdmStructureId(), dataDefinitionFieldLink);
		}
	}

	private DataDefinitionFieldLink _createDataDefinitionFieldLink(
			long dataDefinitionId)
		throws Exception {

		return new DataDefinitionFieldLink() {
			{
				dataDefinition = DataDefinitionUtil.toDataDefinition(
					_dataDefinitionContentTypeTracker,
					_ddmFormFieldTypeServicesTracker,
					_ddmStructureLocalService.getDDMStructure(dataDefinitionId),
					_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter);
				dataLayouts = new DataLayout[0];
				dataListViews = new DataListView[0];
			}
		};
	}

	@Reference
	private DataDefinitionContentTypeTracker _dataDefinitionContentTypeTracker;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

	@Reference
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SPIDDMFormRuleConverter _spiDDMFormRuleConverter;

}