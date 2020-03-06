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

package com.liferay.data.engine.service.impl;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataLayoutApp;
import com.liferay.data.engine.service.impl.util.DataLayoutUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutNameComparator;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.ValidationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DEDataLayoutApp.class)
public class DEDataLayoutAppImpl implements DEDataLayoutApp {

	@Override
	public DEDataLayout addDataLayout(
			DEDataLayout deDataLayout, ServiceContext serviceContext)
		throws Exception {

		String content = DataLayoutUtil.serialize(
			_ddmFormLayoutSerializer, deDataLayout);

		_validate(content, deDataLayout.getName());

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			deDataLayout.getDataDefinitionId());

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.addStructureLayout(
				PrincipalThreadLocal.getUserId(), ddmStructure.getGroupId(),
				ddmStructure.getClassNameId(), deDataLayout.getDataLayoutKey(),
				_getDDMStructureVersionId(deDataLayout.getDataDefinitionId()),
				LocalizedValueUtil.toLocaleStringMap(deDataLayout.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					deDataLayout.getDescription()),
				content, serviceContext);

		_addDataDefinitionFieldLinks(
			ddmStructureLayout.getGroupId(), ddmStructure.getClassNameId(),
			ddmStructureLayout.getStructureLayoutId(),
			deDataLayout.getDataDefinitionId(), _getFieldNames(content));

		return DataLayoutUtil.toDEDataLayout(ddmStructureLayout);
	}

	@Override
	public void deleteDataLayout(long dataLayoutId) throws Exception {
		_deleteDataLayout(
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId));
	}

	@Override
	public void deleteDataLayoutDataDefinition(long dataDefinitionId)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		List<DDMStructureVersion> ddmStructureVersions =
			_ddmStructureVersionLocalService.getStructureVersions(
				dataDefinitionId);

		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			List<DDMStructureLayout> ddmStructureLayouts =
				_ddmStructureLayoutLocalService.getStructureLayouts(
					ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
					ddmStructureVersion.getStructureVersionId());

			for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
				_deleteDataLayout(ddmStructureLayout);
			}
		}
	}

	@Override
	public DEDataLayout fetchDataLayout(long dataLayoutId) {
		try {
			return DataLayoutUtil.toDEDataLayout(
				_ddmStructureLayoutLocalService.fetchDDMStructureLayout(
					dataLayoutId));
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Override
	public DEDataLayout fetchDataLayout(
		long groupId, long classNameId, String dataLayoutKey) {

		try {
			return getDataLayout(groupId, classNameId, dataLayoutKey);
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Override
	public DEDataLayout getDataLayout(long dataLayoutId) throws Exception {
		return DataLayoutUtil.toDEDataLayout(
			_ddmStructureLayoutLocalService.getDDMStructureLayout(
				dataLayoutId));
	}

	@Override
	public DEDataLayout getDataLayout(
			long groupId, long classNameId, String dataLayoutKey)
		throws Exception {

		return DataLayoutUtil.toDEDataLayout(
			_ddmStructureLayoutLocalService.getStructureLayout(
				groupId, classNameId, dataLayoutKey));
	}

	@Override
	public Page<DEDataLayout> getDataLayouts(
			long dataDefinitionId, String keywords, Locale locale,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					locale, "page-size-is-greater-than-x", 250));
		}

		if (ArrayUtil.isEmpty(sorts)) {
			sorts = new Sort[] {
				new Sort(
					Field.getSortableFieldName(Field.MODIFIED_DATE),
					Sort.STRING_TYPE, true)
			};
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return Page.of(
				TransformUtil.transform(
					_ddmStructureLayoutLocalService.getStructureLayouts(
						ddmStructure.getGroupId(),
						ddmStructure.getClassNameId(),
						_getDDMStructureVersionId(dataDefinitionId),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					DataLayoutUtil::toDEDataLayout),
				pagination,
				_ddmStructureLayoutLocalService.getStructureLayoutsCount(
					ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
					_getDDMStructureVersionId(dataDefinitionId)));
		}

		return SearchUtil.search(
			booleanQuery -> {
			},
			null, DDMStructureLayout.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID, ddmStructure.getClassNameId());
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setAttribute(
					"structureVersionId",
					_getDDMStructureVersionId(dataDefinitionId));
				searchContext.setCompanyId(ddmStructure.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			document -> DataLayoutUtil.toDEDataLayout(
				_ddmStructureLayoutLocalService.getStructureLayout(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public DEDataLayout updateDataLayout(
			DEDataLayout deDataLayout, ServiceContext serviceContext)
		throws Exception {

		String content = DataLayoutUtil.serialize(
			_ddmFormLayoutSerializer, deDataLayout);

		_validate(content, deDataLayout.getName());

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(
				deDataLayout.getId());

		DDMStructure ddmStructure = ddmStructureLayout.getDDMStructure();

		ddmStructureLayout =
			_ddmStructureLayoutLocalService.updateStructureLayout(
				deDataLayout.getId(),
				_getDDMStructureVersionId(ddmStructure.getStructureId()),
				LocalizedValueUtil.toLocaleStringMap(deDataLayout.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					deDataLayout.getDescription()),
				content, serviceContext);

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(), deDataLayout.getId());

		_addDataDefinitionFieldLinks(
			ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
			deDataLayout.getId(), ddmStructure.getStructureId(),
			_getFieldNames(content));

		return DataLayoutUtil.toDEDataLayout(ddmStructureLayout);
	}

	private void _addDataDefinitionFieldLinks(
			long groupId, long classNameId, long dataLayoutId,
			long dataDefinitionId, List<String> fieldNames)
		throws Exception {

		for (String fieldName : fieldNames) {
			_deDataDefinitionFieldLinkLocalService.addDEDataDefinitionFieldLink(
				groupId, classNameId, dataLayoutId, dataDefinitionId,
				fieldName);
		}
	}

	private void _deleteDataLayout(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		DDMStructure ddmStructure = ddmStructureLayout.getDDMStructure();

		_ddmStructureLayoutLocalService.deleteDDMStructureLayout(
			ddmStructureLayout);

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			ddmStructure.getClassNameId(),
			ddmStructureLayout.getStructureLayoutId());
	}

	private long _getDDMStructureVersionId(long deDataDefinitionId)
		throws Exception {

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				deDataDefinitionId);

		return ddmStructureVersion.getStructureVersionId();
	}

	private List<String> _getFieldNames(String content) {
		DocumentContext documentContext = JsonPath.parse(content);

		return documentContext.read(
			"$[\"pages\"][*][\"rows\"][*][\"columns\"][*][\"fieldNames\"][*]");
	}

	private OrderByComparator<DDMStructureLayout> _toOrderByComparator(
		Sort sort) {

		boolean ascending = !sort.isReverse();

		String sortFieldName = sort.getFieldName();

		if (StringUtil.startsWith(sortFieldName, "createDate")) {
			return new StructureLayoutCreateDateComparator(ascending);
		}
		else if (StringUtil.startsWith(sortFieldName, "localized_name")) {
			return new StructureLayoutNameComparator(ascending);
		}

		return new StructureLayoutModifiedDateComparator(ascending);
	}

	private void _validate(String content, Map<String, Object> name) {
		if (MapUtil.isEmpty(name)) {
			throw new ValidationException("Name is required");
		}

		name.forEach(
			(locale, localizedName) -> {
				if (Validator.isNull(localizedName)) {
					throw new ValidationException("Name is required");
				}
			});

		List<String> fieldNames = _getFieldNames(content);

		if (ListUtil.isEmpty(fieldNames)) {
			throw new ValidationException("Layout is empty");
		}
	}

	@Reference
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