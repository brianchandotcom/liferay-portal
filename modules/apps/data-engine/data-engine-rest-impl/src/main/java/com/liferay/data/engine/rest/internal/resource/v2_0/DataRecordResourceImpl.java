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

import com.liferay.data.engine.constants.DataActionKeys;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeTracker;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataRecordEntityModel;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataRecordCollectionModelResourcePermission;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataRecordModelResourcePermission;
import com.liferay.data.engine.rest.internal.storage.DataRecordExporter;
import com.liferay.data.engine.rest.internal.storage.DataStorageTracker;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordResource;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.data.engine.storage.DataStorage;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.spi.converter.SPIDDMFormRuleConverter;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.NestedSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.ValidationException;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-record.properties",
	scope = ServiceScope.PROTOTYPE, service = DataRecordResource.class
)
public class DataRecordResourceImpl
	extends BaseDataRecordResourceImpl implements EntityModelResource {

	@Override
	public void deleteDataRecord(Long dataRecordId) throws Exception {
		_dataRecordModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataRecordId,
			DataActionKeys.DELETE_DATA_RECORD);

		DDLRecord ddlRecord = _ddlRecordLocalService.getDDLRecord(dataRecordId);

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DataStorage dataStorage = _getDataStorage(
			ddmStructure.getStorageType());

		dataStorage.delete(ddlRecord.getDDMStorageId());

		_ddmStorageLinkLocalService.deleteClassStorageLink(
			ddlRecord.getDDMStorageId());

		_ddlRecordLocalService.deleteDDLRecord(dataRecordId);
	}

	@Override
	public Page<DataRecord> getDataDefinitionDataRecordsPage(
			Long dataDefinitionId, Long dataListViewId, String keywords,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return getDataRecordCollectionDataRecordsPage(
			_getDefaultDataRecordCollectionId(dataDefinitionId), dataListViewId,
			keywords, pagination, sorts);
	}

	@Override
	public DataRecord getDataRecord(Long dataRecordId) throws Exception {
		_dataRecordModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataRecordId,
			DataActionKeys.VIEW_DATA_RECORD);

		return _toDataRecord(_ddlRecordLocalService.getDDLRecord(dataRecordId));
	}

	@Override
	public String getDataRecordCollectionDataRecordExport(
			Long dataRecordCollectionId, Pagination pagination)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					contextAcceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
		}

		_dataRecordCollectionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, DataActionKeys.EXPORT_DATA_RECORDS);

		DataRecordExporter dataRecordExporter = new DataRecordExporter(
			_dataDefinitionContentTypeTracker, _ddlRecordSetLocalService,
			_ddmFormFieldTypeServicesTracker, _ddmStructureLayoutLocalService,
			_spiDDMFormRuleConverter);

		return dataRecordExporter.export(
			transform(
				_ddlRecordLocalService.getRecords(
					dataRecordCollectionId, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toDataRecord));
	}

	@Override
	public Page<DataRecord> getDataRecordCollectionDataRecordsPage(
			Long dataRecordCollectionId, Long dataListViewId, String keywords,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				LanguageUtil.format(
					contextAcceptLanguage.getPreferredLocale(),
					"page-size-is-greater-than-x", 250));
		}

		_dataRecordCollectionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, DataActionKeys.VIEW_DATA_RECORD);

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getDDLRecordSet(
			dataRecordCollectionId);

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			_getBooleanFilter(dataListViewId, ddlRecordSet), DDLRecord.class,
			keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				if (sorts != null) {
					_searchRequestBuilderFactory.builder(
						searchContext
					).sorts(
						_getFieldSorts(ddlRecordSet.getDDMStructure(), sorts)
					);
				}

				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_ANY);
				searchContext.setAttribute(
					"recordSetId", dataRecordCollectionId);
				searchContext.setAttribute(
					"recordSetScope", ddlRecordSet.getScope());
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setUserId(0);
			},
			null,
			document -> _toDataRecord(
				_ddlRecordLocalService.fetchRecord(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws PortalException {

		long dataDefinitionId = GetterUtil.getLong(
			(String)multivaluedMap.getFirst("dataDefinitionId"));

		if (dataDefinitionId <= 0) {
			long dataRecordCollectionId = GetterUtil.getLong(
				(String)multivaluedMap.getFirst("dataRecordCollectionId"));

			if (dataRecordCollectionId > 0) {
				DDLRecordSet ddlRecordSet =
					_ddlRecordSetLocalService.getDDLRecordSet(
						dataRecordCollectionId);

				DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

				dataDefinitionId = ddmStructure.getStructureId();
			}
		}

		List<EntityField> entityFields = new ArrayList<>();

		if (dataDefinitionId > 0) {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(dataDefinitionId);

			for (String fieldName : ddmStructure.getFieldNames()) {
				entityFields.add(
					new StringEntityField(fieldName, locale -> fieldName));
			}
		}

		return new DataRecordEntityModel(entityFields);
	}

	@Override
	public DataRecord postDataDefinitionDataRecord(
			Long dataDefinitionId, DataRecord dataRecord)
		throws Exception {

		return postDataRecordCollectionDataRecord(
			_getDefaultDataRecordCollectionId(dataDefinitionId), dataRecord);
	}

	@Override
	public DataRecord postDataRecordCollectionDataRecord(
			Long dataRecordCollectionId, DataRecord dataRecord)
		throws Exception {

		_dataRecordCollectionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			dataRecordCollectionId, DataActionKeys.ADD_DATA_RECORD);

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			dataRecordCollectionId);

		dataRecord.setDataRecordCollectionId(dataRecordCollectionId);

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DataStorage dataStorage = _getDataStorage(
			ddmStructure.getStorageType());

		long ddmStorageId = dataStorage.save(
			ddlRecordSet.getRecordSetId(), dataRecord.getDataRecordValues(),
			ddlRecordSet.getGroupId());

		DDLRecordSetVersion ddlRecordSetVersion =
			ddlRecordSet.getRecordSetVersion();

		DDMStructureVersion ddmStructureVersion =
			ddlRecordSetVersion.getDDMStructureVersion();

		_ddmStorageLinkLocalService.addStorageLink(
			_portal.getClassNameId(DataRecord.class.getName()), ddmStorageId,
			ddmStructureVersion.getStructureVersionId(), new ServiceContext());

		return _toDataRecord(
			_ddlRecordLocalService.addRecord(
				PrincipalThreadLocal.getUserId(), ddlRecordSet.getGroupId(),
				ddmStorageId, dataRecord.getDataRecordCollectionId(),
				StringPool.BLANK, 0, new ServiceContext()));
	}

	@Override
	public DataRecord putDataRecord(Long dataRecordId, DataRecord dataRecord)
		throws Exception {

		_dataRecordModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataRecordId,
			DataActionKeys.UPDATE_DATA_RECORD);

		DDLRecord ddlRecord = _ddlRecordLocalService.getRecord(dataRecordId);

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		dataRecord.setDataRecordCollectionId(ddlRecordSet.getRecordSetId());

		dataRecord.setId(dataRecordId);

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DataStorage dataStorage = _getDataStorage(
			ddmStructure.getStorageType());

		long ddmStorageId = dataStorage.save(
			ddlRecordSet.getRecordSetId(), dataRecord.getDataRecordValues(),
			ddlRecord.getGroupId());

		DDLRecordSetVersion ddlRecordSetVersion =
			ddlRecordSet.getRecordSetVersion();

		DDMStructureVersion ddmStructureVersion =
			ddlRecordSetVersion.getDDMStructureVersion();

		_ddmStorageLinkLocalService.addStorageLink(
			_portal.getClassNameId(DataRecord.class.getName()), ddmStorageId,
			ddmStructureVersion.getStructureVersionId(), new ServiceContext());

		_ddlRecordLocalService.updateRecord(
			PrincipalThreadLocal.getUserId(), dataRecordId, ddmStorageId,
			new ServiceContext());

		return dataRecord;
	}

	@Override
	protected void preparePatch(
		DataRecord dataRecord, DataRecord existingDataRecord) {

		if (dataRecord.getDataRecordValues() != null) {
			existingDataRecord.setDataRecordValues(
				() -> {
					DataRecord getDataRecord = getDataRecord(
						existingDataRecord.getId());

					Map<String, Object> dataRecordValues =
						getDataRecord.getDataRecordValues();

					dataRecordValues.putAll(dataRecord.getDataRecordValues());

					return dataRecordValues;
				});
		}
	}

	private BooleanFilter _getBooleanFilter(
			Long dataListViewId, DDLRecordSet ddlRecordSet)
		throws Exception {

		BooleanFilter booleanFilter = new BooleanFilter();

		if (Validator.isNull(dataListViewId)) {
			return booleanFilter;
		}

		DEDataListView deDataListView =
			_deDataListViewLocalService.getDEDataListView(dataListViewId);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			deDataListView.getAppliedFilters());

		String[] fieldNames = JSONUtil.toStringArray(
			JSONFactoryUtil.createJSONArray(deDataListView.getFieldNames()));

		for (String fieldName : fieldNames) {
			JSONArray jsonArray = (JSONArray)jsonObject.get(fieldName);

			if (jsonArray == null) {
				continue;
			}

			BooleanFilter fieldBooleanFilter = new BooleanFilter();

			for (String value : JSONUtil.toStringArray(jsonArray)) {
				DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

				String fieldType = ddmStructure.getFieldType(fieldName);

				if (fieldType.equals("select")) {
					value = StringBundler.concat(
						StringPool.OPEN_BRACKET, value,
						StringPool.CLOSE_BRACKET);
				}

				fieldBooleanFilter.add(
					_ddmIndexer.createFieldValueQueryFilter(
						_getIndexFieldName(
							ddmStructure.getStructureId(), fieldName,
							contextAcceptLanguage.getPreferredLocale()),
						value, contextAcceptLanguage.getPreferredLocale()),
					BooleanClauseOccur.SHOULD);
			}

			booleanFilter.add(fieldBooleanFilter, BooleanClauseOccur.MUST);
		}

		return booleanFilter;
	}

	private DataStorage _getDataStorage(String dataStorageType) {
		if (Validator.isNull(dataStorageType)) {
			throw new ValidationException("Data storage type is null");
		}

		DataStorage dataStorage = _dataStorageTracker.getDataStorage(
			dataStorageType);

		if (dataStorage == null) {
			throw new ValidationException(
				"Unsupported data storage type: " + dataStorageType);
		}

		return dataStorage;
	}

	private long _getDefaultDataRecordCollectionId(Long dataDefinitionId)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			ddmStructure.getGroupId(), ddmStructure.getStructureKey());

		return ddlRecordSet.getRecordSetId();
	}

	private FieldSort[] _getFieldSorts(DDMStructure ddmStructure, Sort[] sorts)
		throws PortalException {

		List<FieldSort> fieldSorts = new ArrayList<>();

		for (Sort sort : sorts) {
			String fieldName = sort.getFieldName();

			FieldSort fieldSort = _sorts.field(
				_getSortableFieldName(ddmStructure, fieldName));

			if (sort.isReverse()) {
				fieldSort.setSortOrder(SortOrder.DESC);
			}

			NestedSort nestedSort = _sorts.nested(DDMIndexer.DDM_FIELD_ARRAY);

			nestedSort.setFilterQuery(
				_queries.term(
					StringBundler.concat(
						DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
						DDMIndexer.DDM_FIELD_NAME),
					_getIndexFieldName(
						ddmStructure.getStructureId(), fieldName,
						contextAcceptLanguage.getPreferredLocale())));

			fieldSort.setNestedSort(nestedSort);

			fieldSorts.add(fieldSort);
		}

		return fieldSorts.toArray(new FieldSort[0]);
	}

	private String _getIndexFieldName(
		long ddmStructureId, String fieldName, Locale locale) {

		return _ddmIndexer.encodeName(ddmStructureId, fieldName, locale);
	}

	private String _getSortableFieldName(
			DDMStructure ddmStructure, String fieldName)
		throws PortalException {

		StringBundler sb = new StringBundler(5);

		sb.append(DDMIndexer.DDM_FIELD_ARRAY);
		sb.append(StringPool.PERIOD);
		sb.append(
			_ddmIndexer.getValueFieldName(
				ddmStructure.getFieldProperty(fieldName, "indexType"),
				contextAcceptLanguage.getPreferredLocale()));
		sb.append(StringPool.UNDERLINE);

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(fieldName);

		String ddmFormFieldType = ddmFormField.getType();

		String suffix = "String";

		if (StringUtil.equals(ddmFormFieldType, DDMFormFieldType.DECIMAL) ||
			StringUtil.equals(ddmFormFieldType, DDMFormFieldType.INTEGER) ||
			StringUtil.equals(ddmFormFieldType, DDMFormFieldType.NUMBER) ||
			StringUtil.equals(ddmFormFieldType, DDMFormFieldType.NUMERIC)) {

			suffix = "Number";
		}

		sb.append(suffix);

		return Field.getSortableFieldName(sb.toString());
	}

	private DataRecord _toDataRecord(DDLRecord ddlRecord) throws Exception {
		if (ddlRecord == null) {
			return null;
		}

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DataStorage dataStorage = _getDataStorage(
			ddmStructure.getStorageType());

		return new DataRecord() {
			{
				dataRecordCollectionId = ddlRecordSet.getRecordSetId();
				dataRecordValues = dataStorage.get(
					ddmStructure.getStructureId(), ddlRecord.getDDMStorageId());
				id = ddlRecord.getRecordId();
			}
		};
	}

	@Reference
	private DataDefinitionContentTypeTracker _dataDefinitionContentTypeTracker;

	@Reference
	private DataRecordCollectionModelResourcePermission
		_dataRecordCollectionModelResourcePermission;

	@Reference
	private DataRecordModelResourcePermission
		_dataRecordModelResourcePermission;

	@Reference
	private DataStorageTracker _dataStorageTracker;

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private Sorts _sorts;

	@Reference
	private SPIDDMFormRuleConverter _spiDDMFormRuleConverter;

}