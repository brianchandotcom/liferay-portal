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
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataListView;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataDefinitionEntityModel;
import com.liferay.data.engine.rest.internal.resource.util.DataEnginePermissionUtil;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataDefinitionModelResourcePermission;
import com.liferay.data.engine.rest.resource.v2_0.DataListViewResource;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.data.engine.util.comparator.DEDataListViewCreateDateComparator;
import com.liferay.data.engine.util.comparator.DEDataListViewModifiedDateComparator;
import com.liferay.data.engine.util.comparator.DEDataListViewNameComparator;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.validation.ValidationException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-list-view.properties",
	scope = ServiceScope.PROTOTYPE, service = DataListViewResource.class
)
public class DataListViewResourceImpl
	extends BaseDataListViewResourceImpl implements EntityModelResource {

	@Override
	public void deleteDataListView(Long dataListViewId) throws Exception {
		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			_getDDMStructureId(
				_deDataListViewLocalService.getDEDataListView(dataListViewId)),
			ActionKeys.DELETE);

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_getClassNameId(), dataListViewId);

		_deDataListViewLocalService.deleteDEDataListView(dataListViewId);
	}

	@Override
	public Page<DataListView> getDataDefinitionDataListViewsPage(
			Long dataDefinitionId, String keywords, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new BadRequestException(
				LanguageUtil.format(
					contextAcceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
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
				transform(
					_deDataListViewLocalService.getDEDataListViews(
						ddmStructure.getGroupId(),
						contextCompany.getCompanyId(),
						ddmStructure.getStructureId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					this::_toDataListView),
				pagination,
				_deDataListViewLocalService.getDEDataListViewsCount(
					ddmStructure.getGroupId(), contextCompany.getCompanyId(),
					ddmStructure.getStructureId()));
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null, DEDataListView.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setAttribute("ddmStructureId", dataDefinitionId);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(
					new long[] {ddmStructure.getGroupId()});
			},
			sorts,
			document -> _toDataListView(
				_deDataListViewLocalService.getDEDataListView(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public DataListView getDataListView(Long dataListViewId) throws Exception {
		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			_getDDMStructureId(
				_deDataListViewLocalService.getDEDataListView(dataListViewId)),
			ActionKeys.VIEW);

		return _toDataListView(
			_deDataListViewLocalService.getDEDataListView(dataListViewId));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public DataListView postDataDefinitionDataListView(
			Long dataDefinitionId, DataListView dataListView)
		throws Exception {

		if (ArrayUtil.isEmpty(dataListView.getFieldNames())) {
			throw new ValidationException("View is empty");
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.ADD_DATA_DEFINITION, _groupLocalService,
			ddmStructure.getGroupId());

		dataListView = _toDataListView(
			_deDataListViewLocalService.addDEDataListView(
				ddmStructure.getGroupId(), contextCompany.getCompanyId(),
				PrincipalThreadLocal.getUserId(),
				_toJSON(dataListView.getAppliedFilters()), dataDefinitionId,
				Arrays.toString(dataListView.getFieldNames()),
				LocalizedValueUtil.toLocaleStringMap(dataListView.getName()),
				dataListView.getSortField()));

		_addDataDefinitionFieldLinks(
			dataListView.getDataDefinitionId(), dataListView.getId(),
			dataListView.getFieldNames(), dataListView.getSiteId());

		return dataListView;
	}

	@Override
	public DataListView putDataListView(
			Long dataListViewId, DataListView dataListView)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			_getDDMStructureId(
				_deDataListViewLocalService.getDEDataListView(dataListViewId)),
			ActionKeys.UPDATE);

		if (ArrayUtil.isEmpty(dataListView.getFieldNames())) {
			throw new ValidationException("View is empty");
		}

		dataListView = _toDataListView(
			_deDataListViewLocalService.updateDEDataListView(
				dataListViewId, _toJSON(dataListView.getAppliedFilters()),
				Arrays.toString(dataListView.getFieldNames()),
				LocalizedValueUtil.toLocaleStringMap(dataListView.getName()),
				dataListView.getSortField()));

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_getClassNameId(), dataListViewId);

		_addDataDefinitionFieldLinks(
			dataListView.getDataDefinitionId(), dataListView.getId(),
			dataListView.getFieldNames(), dataListView.getSiteId());

		return dataListView;
	}

	private void _addDataDefinitionFieldLinks(
		long dataDefinitionId, long dataListViewId, String[] fieldNames,
		long groupId) {

		for (String fieldName : fieldNames) {
			_deDataDefinitionFieldLinkLocalService.addDEDataDefinitionFieldLink(
				groupId, _getClassNameId(), dataListViewId, dataDefinitionId,
				fieldName);
		}
	}

	private long _getClassNameId() {
		return _portal.getClassNameId(DEDataListView.class);
	}

	private long _getDDMStructureId(DEDataListView deDataListView) {
		return deDataListView.getDdmStructureId();
	}

	private DataListView _toDataListView(DEDataListView deDataListView)
		throws Exception {

		return new DataListView() {
			{
				appliedFilters = _toMap(deDataListView.getAppliedFilters());
				dataDefinitionId = deDataListView.getDdmStructureId();
				dateCreated = deDataListView.getCreateDate();
				dateModified = deDataListView.getModifiedDate();
				fieldNames = JSONUtil.toStringArray(
					_jsonFactory.createJSONArray(
						deDataListView.getFieldNames()));
				id = deDataListView.getPrimaryKey();
				name = LocalizedValueUtil.toStringObjectMap(
					deDataListView.getNameMap());
				siteId = deDataListView.getGroupId();
				sortField = deDataListView.getSortField();
				userId = deDataListView.getUserId();
			}
		};
	}

	private String _toJSON(Map<String, Object> appliedFilters) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (MapUtil.isEmpty(appliedFilters)) {
			return jsonObject.toString();
		}

		for (Map.Entry<String, Object> entry : appliedFilters.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject.toString();
	}

	private Map<String, Object> _toMap(String json) throws Exception {
		Map<String, Object> map = new HashMap<>();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		Set<String> keySet = jsonObject.keySet();

		Iterator<String> iterator = keySet.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (jsonObject.get(key) instanceof JSONObject) {
				map.put(
					key,
					_toMap(
						jsonObject.get(
							key
						).toString()));
			}
			else {
				map.put(key, jsonObject.get(key));
			}
		}

		return map;
	}

	private OrderByComparator<DEDataListView> _toOrderByComparator(Sort sort) {
		boolean ascending = !sort.isReverse();

		String sortFieldName = sort.getFieldName();

		if (StringUtil.startsWith(sortFieldName, "createDate")) {
			return new DEDataListViewCreateDateComparator(ascending);
		}
		else if (StringUtil.startsWith(sortFieldName, "localized_name")) {
			return new DEDataListViewNameComparator(ascending);
		}

		return new DEDataListViewModifiedDateComparator(ascending);
	}

	private static final EntityModel _entityModel =
		new DataDefinitionEntityModel();

	@Reference
	private DataDefinitionModelResourcePermission
		_dataDefinitionModelResourcePermission;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

	@Reference
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}