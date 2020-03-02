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

package com.liferay.data.engine.spi.resource;

import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.content.type.DataDefinitionContentTypeTracker;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.data.engine.spi.model.SPIDataDefinition;
import com.liferay.data.engine.spi.model.SPIDataLayout;
import com.liferay.data.engine.spi.model.SPIDataRecordCollection;
import com.liferay.data.engine.spi.util.DataDefinitionUtil;
import com.liferay.data.engine.spi.util.DataLayoutUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.StructureCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ValidationException;

/**
 * @author Jeyvison Nascimento
 */
public class SPIDataDefinitionResource {

	public SPIDataDefinitionResource(
		DataDefinitionContentTypeTracker dataDefinitionContentTypeTracker,
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormLayoutSerializer ddmFormLayoutSerializer,
		DDMFormSerializer ddmFormSerializer,
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DDMStructureVersionLocalService ddmStructureVersionLocalService,
		DEDataDefinitionFieldLinkLocalService
			deDataDefinitionFieldLinkLocalService,
		DEDataListViewLocalService deDataListViewLocalService,
		JSONFactory jsonFactory, Portal portal,
		ResourceLocalService resourceLocalService) {

		_dataDefinitionContentTypeTracker = dataDefinitionContentTypeTracker;
		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmFormLayoutSerializer = ddmFormLayoutSerializer;
		_ddmFormSerializer = ddmFormSerializer;
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
		_deDataDefinitionFieldLinkLocalService =
			deDataDefinitionFieldLinkLocalService;
		_deDataListViewLocalService = deDataListViewLocalService;
		_jsonFactory = jsonFactory;
		_portal = portal;
		_resourceLocalService = resourceLocalService;
	}

	public SPIDataDefinition addDataDefinitionByContentType(
			long companyId, String contentType, long site,
			SPIDataDefinition spiDataDefinition)
		throws Exception {

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(
				DataDefinitionUtil.toDDMForm(
					_ddmFormFieldTypeServicesTracker, spiDataDefinition));

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(builder.build());

		SPIDataLayout spiDataLayout =
			spiDataDefinition.getSPIDefaultDataLayout();

		spiDataDefinition = DataDefinitionUtil.toSPIDataDefinition(
			_ddmFormFieldTypeServicesTracker,
			_addDataDefinitionByContentType(
				companyId, ddmFormSerializerSerializeResponse.getContent(),
				contentType, spiDataDefinition.getDataDefinitionKey(),
				spiDataDefinition.getDescription(), spiDataDefinition.getName(),
				site, spiDataDefinition.getStorageType()));

		if (spiDataLayout != null) {
			spiDataLayout.setDataDefinitionId(spiDataDefinition.getId());
			spiDataLayout.setDataLayoutKey(
				spiDataDefinition.getDataDefinitionKey());

			SPIDataLayoutResource spiDataLayoutResource =
				_getSPIDataLayoutResource();

			spiDataDefinition.setSPIDefaultDataLayout(
				spiDataLayoutResource.addDataLayout(spiDataLayout));
		}

		return spiDataDefinition;
	}

	public void deleteDataDefinition(long dataDefinitionId) throws Exception {
		_ddlRecordSetLocalService.deleteDDMStructureRecordSets(
			dataDefinitionId);

		SPIDataLayoutResource spiDataLayoutResource =
			_getSPIDataLayoutResource();

		spiDataLayoutResource.deleteDataLayoutDataDefinition(dataDefinitionId);

		_ddmStructureLocalService.deleteDDMStructure(dataDefinitionId);

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			dataDefinitionId);

		_deDataListViewLocalService.deleteDEDataListViews(dataDefinitionId);
	}

	public SPIDataDefinition getDataDefinition(long dataDefinitionId)
		throws Exception {

		return DataDefinitionUtil.toSPIDataDefinition(
			_ddmFormFieldTypeServicesTracker,
			_ddmStructureLocalService.getStructure(dataDefinitionId));
	}

	public Page<SPIDataDefinition> getDataDefinitionsByContentTypePage(
			long companyId, String contentType, String keywords, long groupId,
			Locale locale, Pagination pagination, Sort[] sorts)
		throws Exception {

		return getSiteDataDefinitionsByContentTypePage(
			companyId, contentType, keywords, locale, pagination,
			_portal.getSiteGroupId(groupId), sorts);
	}

	public SPIDataDefinition
			getSiteDataDefinitionByContentTypeByDataDefinitionKey(
				String contentType, String dataDefinitionKey, long siteId)
		throws Exception {

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		return DataDefinitionUtil.toSPIDataDefinition(
			_ddmFormFieldTypeServicesTracker,
			_ddmStructureLocalService.getStructure(
				siteId, dataDefinitionContentType.getClassNameId(),
				dataDefinitionKey));
	}

	public Page<SPIDataDefinition> getSiteDataDefinitionsByContentTypePage(
			long companyId, String contentType, String keywords, Locale locale,
			Pagination pagination, long siteId, Sort[] sorts)
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

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		if (Validator.isNull(keywords)) {
			return Page.of(
				TransformUtil.transform(
					_ddmStructureLocalService.getStructures(
						siteId, dataDefinitionContentType.getClassNameId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					ddmStructure -> DataDefinitionUtil.toSPIDataDefinition(
						_ddmFormFieldTypeServicesTracker, ddmStructure)),
				pagination,
				_ddmStructureLocalService.getStructuresCount(
					siteId, dataDefinitionContentType.getClassNameId()));
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null, DDMStructure.class, keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.CLASS_NAME_ID, Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID,
					dataDefinitionContentType.getClassNameId());
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setCompanyId(companyId);
				searchContext.setGroupIds(new long[] {siteId});
			},
			sorts,
			document -> DataDefinitionUtil.toSPIDataDefinition(
				_ddmFormFieldTypeServicesTracker,
				_ddmStructureLocalService.getStructure(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	public SPIDataDefinition updateDataDefinition(
			long dataDefinitionId, SPIDataDefinition spiDataDefinition)
		throws Exception {

		_updateFieldNames(dataDefinitionId, spiDataDefinition);

		SPIDataLayout spiDataLayout =
			spiDataDefinition.getSPIDefaultDataLayout();

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(
				DataDefinitionUtil.toDDMForm(
					_ddmFormFieldTypeServicesTracker, spiDataDefinition));

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(builder.build());

		spiDataDefinition = DataDefinitionUtil.toSPIDataDefinition(
			_ddmFormFieldTypeServicesTracker,
			_ddmStructureLocalService.updateStructure(
				PrincipalThreadLocal.getUserId(), dataDefinitionId,
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				LocalizedValueUtil.toLocaleStringMap(
					spiDataDefinition.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					spiDataDefinition.getDescription()),
				ddmFormSerializerSerializeResponse.getContent(),
				new ServiceContext()));

		if (spiDataLayout != null) {
			spiDataLayout.setDataLayoutKey(
				spiDataDefinition.getDataDefinitionKey());

			SPIDataLayoutResource spiDataLayoutResource =
				_getSPIDataLayoutResource();

			if (spiDataLayout.getId() == null) {
				SPIDataLayout spiDefaultDataLayout = _getDefaultDataLayout(
					spiDataDefinition.getId(), spiDataLayoutResource);

				spiDataLayout.setId(spiDefaultDataLayout.getId());
			}

			spiDataDefinition.setSPIDefaultDataLayout(
				spiDataLayoutResource.updateDataLayout(spiDataLayout));
		}

		return spiDataDefinition;
	}

	private DDMStructure _addDataDefinitionByContentType(
			long companyId, String content, String contentType,
			String dataDefinitionKey, Map<String, Object> description,
			Map<String, Object> name, long siteId, String storageType)
		throws Exception {

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		DDMStructure ddmStructure = _ddmStructureLocalService.addStructure(
			PrincipalThreadLocal.getUserId(), siteId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			dataDefinitionContentType.getClassNameId(), dataDefinitionKey,
			LocalizedValueUtil.toLocaleStringMap(name),
			LocalizedValueUtil.toLocaleStringMap(description), content,
			GetterUtil.getString(storageType, "json"), new ServiceContext());

		_resourceLocalService.addResources(
			companyId, siteId, PrincipalThreadLocal.getUserId(),
			ResourceActionsUtil.getCompositeModelName(
				_portal.getClassName(
					dataDefinitionContentType.getClassNameId()),
				DDMStructure.class.getName()),
			ddmStructure.getStructureId(), false, false, false);

		SPIDataRecordCollectionResource spiDataRecordCollectionResource =
			new SPIDataRecordCollectionResource(
				_ddlRecordSetLocalService, _ddmStructureLocalService, _portal,
				_resourceLocalService);

		SPIDataRecordCollection spiDataRecordCollection =
			new SPIDataRecordCollection();

		spiDataRecordCollection.setDataDefinitionId(
			ddmStructure.getStructureId());
		spiDataRecordCollection.setDataRecordCollectionKey(
			ddmStructure.getStructureKey());
		spiDataRecordCollection.setDescription(
			LocalizedValueUtil.toStringObjectMap(
				ddmStructure.getDescriptionMap()));
		spiDataRecordCollection.setName(
			LocalizedValueUtil.toStringObjectMap(ddmStructure.getNameMap()));

		spiDataRecordCollectionResource.addDataRecordCollection(
			ddmStructure.getStructureId(), spiDataRecordCollection);

		return ddmStructure;
	}

	private long _getClassNameId(long dataDefinitionId) throws Exception {
		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		return ddmStructure.getClassNameId();
	}

	private SPIDataLayout _getDefaultDataLayout(
			long dataDefinitionId, SPIDataLayoutResource spiDataLayoutResource)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		return spiDataLayoutResource.getDataLayout(
			ddmStructure.getClassNameId(), ddmStructure.getStructureKey(),
			ddmStructure.getGroupId());
	}

	private String[] _getRemovedFieldNames(
			DDMStructure ddmStructure, SPIDataDefinition spiDataDefinition)
		throws Exception {

		SPIDataDefinition existingSPIDataDefinition =
			DataDefinitionUtil.toSPIDataDefinition(
				_ddmFormFieldTypeServicesTracker, ddmStructure);

		return _removeFieldNames(
			TransformUtil.transform(
				existingSPIDataDefinition.getSPIDataDefinitionFields(),
				spiDataDefinitionField -> spiDataDefinitionField.getName(),
				String.class),
			TransformUtil.transform(
				spiDataDefinition.getSPIDataDefinitionFields(),
				spiDataDefinitionField -> spiDataDefinitionField.getName(),
				String.class));
	}

	private SPIDataLayoutResource _getSPIDataLayoutResource() {
		return new SPIDataLayoutResource(
			_ddmFormLayoutSerializer, _ddmStructureLayoutLocalService,
			_ddmStructureLocalService, _ddmStructureVersionLocalService,
			_deDataDefinitionFieldLinkLocalService);
	}

	private String[] _removeFieldNames(
		String[] currentFieldNames, String[] removedFieldNames) {

		return ArrayUtil.filter(
			currentFieldNames,
			fieldName -> !ArrayUtil.contains(removedFieldNames, fieldName));
	}

	private OrderByComparator<DDMStructure> _toOrderByComparator(Sort sort) {
		boolean ascending = !sort.isReverse();

		String sortFieldName = sort.getFieldName();

		if (StringUtil.startsWith(sortFieldName, "createDate")) {
			return new StructureCreateDateComparator(ascending);
		}
		else if (StringUtil.startsWith(sortFieldName, "localized_name")) {
			return new StructureNameComparator(ascending);
		}

		return new StructureModifiedDateComparator(ascending);
	}

	private void _updateDataLayoutFieldNames(
		SPIDataLayout spiDataLayout, String[] removedFieldNames) {

		Stream.of(
			spiDataLayout.getSPIDataLayoutPages()
		).forEach(
			spiDataLayoutPage -> {
				Stream.of(
					spiDataLayoutPage.getSPIDataLayoutRows()
				).forEach(
					spiDataLayoutRow -> {
						Stream.of(
							spiDataLayoutRow.getSPIDataLayoutColumns()
						).forEach(
							dataLayoutColumn -> dataLayoutColumn.setFieldNames(
								_removeFieldNames(
									dataLayoutColumn.getFieldNames(),
									removedFieldNames))
						);

						spiDataLayoutRow.setSPIDataLayoutColumns(
							ArrayUtil.filter(
								spiDataLayoutRow.getSPIDataLayoutColumns(),
								column -> !ArrayUtil.isEmpty(
									column.getFieldNames())));
					}
				);

				spiDataLayoutPage.setSPIDataLayoutRows(
					ArrayUtil.filter(
						spiDataLayoutPage.getSPIDataLayoutRows(),
						row -> !ArrayUtil.isEmpty(
							row.getSPIDataLayoutColumns())));
			}
		);
	}

	private void _updateDataLayouts(
			Set<Long> ddmStructureLayoutIds, String[] removedFieldNames)
		throws Exception {

		for (Long ddmStructureLayoutId : ddmStructureLayoutIds) {
			DDMStructureLayout ddmStructureLayout =
				_ddmStructureLayoutLocalService.getStructureLayout(
					ddmStructureLayoutId);

			SPIDataLayout spiDataLayout = DataLayoutUtil.toSPIDataLayout(
				ddmStructureLayout.getDDMFormLayout());

			_updateDataLayoutFieldNames(spiDataLayout, removedFieldNames);

			DDMFormLayout ddmFormLayout = DataLayoutUtil.toDDMFormLayout(
				spiDataLayout);

			DDMFormLayoutSerializerSerializeRequest.Builder builder =
				DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
					ddmFormLayout);

			DDMFormLayoutSerializerSerializeResponse
				ddmFormLayoutSerializerSerializeResponse =
					_ddmFormLayoutSerializer.serialize(builder.build());

			ddmStructureLayout.setDefinition(
				ddmFormLayoutSerializerSerializeResponse.getContent());

			_ddmStructureLayoutLocalService.updateDDMStructureLayout(
				ddmStructureLayout);
		}
	}

	private void _updateDataListViews(
			Set<Long> deDataListViewIds, String[] removedFieldNames)
		throws Exception {

		for (Long deDataListViewId : deDataListViewIds) {
			DEDataListView deDataListView =
				_deDataListViewLocalService.getDEDataListView(deDataListViewId);

			String[] fieldNames = JSONUtil.toStringArray(
				_jsonFactory.createJSONArray(deDataListView.getFieldNames()));

			deDataListView.setFieldNames(
				Arrays.toString(
					_removeFieldNames(fieldNames, removedFieldNames)));

			_deDataListViewLocalService.updateDEDataListView(deDataListView);
		}
	}

	private void _updateFieldNames(
			Long dataDefinitionId, SPIDataDefinition spiDataDefinition)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		String[] removedFieldNames = _getRemovedFieldNames(
			ddmStructure, spiDataDefinition);

		Set<Long> ddmStructureLayoutIds = new HashSet<>();
		Set<Long> deDataListViewIds = new HashSet<>();

		for (String removedFieldName : removedFieldNames) {
			ddmStructureLayoutIds.addAll(
				TransformUtil.transform(
					_deDataDefinitionFieldLinkLocalService.
						getDEDataDefinitionFieldLinks(
							_getClassNameId(dataDefinitionId),
							ddmStructure.getStructureId(), removedFieldName),
					deDataDefinitionFieldLink ->
						deDataDefinitionFieldLink.getClassPK()));

			deDataListViewIds.addAll(
				TransformUtil.transform(
					_deDataDefinitionFieldLinkLocalService.
						getDEDataDefinitionFieldLinks(
							_portal.getClassNameId(DEDataListView.class),
							ddmStructure.getStructureId(), removedFieldName),
					deDataDefinitionFieldLink ->
						deDataDefinitionFieldLink.getClassPK()));

			_deDataDefinitionFieldLinkLocalService.
				deleteDEDataDefinitionFieldLinks(
					_getClassNameId(dataDefinitionId),
					ddmStructure.getStructureId(), removedFieldName);

			_deDataDefinitionFieldLinkLocalService.
				deleteDEDataDefinitionFieldLinks(
					_portal.getClassNameId(DEDataListView.class),
					ddmStructure.getStructureId(), removedFieldName);
		}

		_updateDataLayouts(ddmStructureLayoutIds, removedFieldNames);
		_updateDataListViews(deDataListViewIds, removedFieldNames);
	}

	private final DataDefinitionContentTypeTracker
		_dataDefinitionContentTypeTracker;
	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormLayoutSerializer _ddmFormLayoutSerializer;
	private final DDMFormSerializer _ddmFormSerializer;
	private final DDMStructureLayoutLocalService
		_ddmStructureLayoutLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private final DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;
	private final DEDataListViewLocalService _deDataListViewLocalService;
	private final JSONFactory _jsonFactory;
	private final Portal _portal;
	private final ResourceLocalService _resourceLocalService;

}