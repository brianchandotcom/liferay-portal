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
import com.liferay.dynamic.data.mapping.util.comparator.StructureLayoutNameComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	public List<DEDataLayout> getDataLayouts(
			long dataDefinitionId, String keywords, int start, int end,
			OrderByComparator orderByComparator)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return _transform(
				_ddmStructureLayoutLocalService.getStructureLayouts(
					ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
					_getDDMStructureVersionId(dataDefinitionId), start, end,
					orderByComparator));
		}

		return _transform(
			_search(
				ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
				ddmStructure.getClassNameId(),
				_getDDMStructureVersionId(dataDefinitionId), keywords, start,
				end, orderByComparator));
	}

	@Override
	public int getDataLayoutsCount(long dataDefinitionId, String keywords)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		if (Validator.isNull(keywords)) {
			return _ddmStructureLayoutLocalService.getStructureLayoutsCount(
				ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
				_getDDMStructureVersionId(dataDefinitionId));
		}

		return _searchCount(
			ddmStructure.getCompanyId(), ddmStructure.getGroupId(),
			ddmStructure.getClassNameId(),
			_getDDMStructureVersionId(dataDefinitionId), keywords);
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

	private Sort _getSorts(OrderByComparator orderByComparator) {
		boolean reverse = !orderByComparator.isAscending();

		if (orderByComparator instanceof StructureLayoutCreateDateComparator) {
			return new Sort(
				Field.getSortableFieldName(Field.CREATE_DATE), Sort.STRING_TYPE,
				reverse);
		}
		else if (orderByComparator instanceof StructureLayoutNameComparator) {
			return new Sort(
				Field.getSortableFieldName(Field.NAME), Sort.STRING_TYPE,
				reverse);
		}

		return new Sort(
			Field.getSortableFieldName(Field.MODIFIED_DATE), Sort.STRING_TYPE,
			reverse);
	}

	private List<DDMStructureLayout> _search(
		long companyId, long groupId, long classNameId, long structureVersionId,
		String keywords, int start, int end,
		OrderByComparator orderByComparator) {

		try {
			List<DDMStructureLayout> ddmStructureLayouts = new ArrayList<>();

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				DDMStructureLayout.class);

			SearchContext searchContext = new SearchContext();

			searchContext.setAttribute(Field.CLASS_NAME_ID, classNameId);
			searchContext.setAttribute(Field.DESCRIPTION, keywords);
			searchContext.setAttribute(Field.NAME, keywords);
			searchContext.setAttribute(
				"structureVersionId", structureVersionId);
			searchContext.setCompanyId(companyId);
			searchContext.setGroupIds(new long[] {groupId});
			searchContext.setEnd(end);
			searchContext.setSorts(_getSorts(orderByComparator));
			searchContext.setStart(start);

			Hits hits = indexer.search(searchContext);

			for (Document document : hits.getDocs()) {
				long entryClassPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				DDMStructureLayout ddmStructureLayout =
					_ddmStructureLayoutLocalService.getStructureLayout(
						entryClassPK);

				ddmStructureLayouts.add(ddmStructureLayout);
			}

			return ddmStructureLayouts;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return Collections.emptyList();
	}

	private int _searchCount(
		long companyId, long groupId, long classNameId, long structureVersionId,
		String keywords) {

		try {
			Indexer indexer = IndexerRegistryUtil.getIndexer(
				DDMStructureLayout.class);

			SearchContext searchContext = new SearchContext();

			searchContext.setAttribute(Field.CLASS_NAME_ID, classNameId);
			searchContext.setAttribute(Field.DESCRIPTION, keywords);
			searchContext.setAttribute(Field.NAME, keywords);
			searchContext.setAttribute(
				"structureVersionId", structureVersionId);
			searchContext.setCompanyId(companyId);
			searchContext.setGroupIds(new long[] {groupId});

			return (int)indexer.searchCount(searchContext);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return 0;
	}

	private List<DEDataLayout> _transform(
			List<DDMStructureLayout> ddmStructureLayouts)
		throws Exception {

		List<DEDataLayout> deDataLayouts = new ArrayList<>(
			ddmStructureLayouts.size());

		for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
			deDataLayouts.add(
				DataLayoutUtil.toDEDataLayout(ddmStructureLayout));
		}

		return deDataLayouts;
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

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataLayoutAppImpl.class);

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