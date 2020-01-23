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
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.internal.constants.DataActionKeys;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeTracker;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataRecordCollectionUtil;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataDefinitionEntityModel;
import com.liferay.data.engine.rest.internal.resource.util.DataEnginePermissionUtil;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataDefinitionModelResourcePermission;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.data.engine.spi.resource.SPIDataRecordCollectionResource;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.dynamic.data.mapping.util.comparator.StructureCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.ModelPermissionsUtil;
import com.liferay.portal.vulcan.permission.Permission;
import com.liferay.portal.vulcan.permission.PermissionUtil;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.validation.ValidationException;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/data-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = DataDefinitionResource.class
)
public class DataDefinitionResourceImpl
	extends BaseDataDefinitionResourceImpl implements EntityModelResource {

	@Override
	public void deleteDataDefinition(Long dataDefinitionId) throws Exception {
		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.DELETE);

		_ddlRecordSetLocalService.deleteDDMStructureRecordSets(
			dataDefinitionId);

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		_ddmStructureLocalService.deleteDDMStructure(ddmStructure);

		List<DDMStructureVersion> ddmStructureVersions =
			_ddmStructureVersionLocalService.getStructureVersions(
				dataDefinitionId);

		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			_ddmStructureLayoutLocalService.deleteDDMStructureLayouts(
				ddmStructure.getClassNameId(), ddmStructureVersion);

			_ddmStructureVersionLocalService.deleteDDMStructureVersion(
				ddmStructureVersion);
		}

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			dataDefinitionId);

		_deDataListViewLocalService.deleteDEDataListViews(dataDefinitionId);
	}

	@Override
	public DataDefinition getDataDefinition(Long dataDefinitionId)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.VIEW);

		return DataDefinitionUtil.toDataDefinition(
			_ddmFormFieldTypeServicesTracker,
			_ddmStructureLocalService.getStructure(dataDefinitionId));
	}

	@Override
	public Page<DataDefinition> getDataDefinitionByContentTypeContentTypePage(
			String contentType, String keywords, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return getSiteDataDefinitionByContentTypeContentTypePage(
			_portal.getSiteGroupId(contextCompany.getGroupId()), contentType,
			keywords, pagination, sorts);
	}

	@Override
	public String getDataDefinitionDataDefinitionFieldFieldTypes()
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		Set<String> ddmFormFieldTypeNames =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeNames();

		Stream<String> stream = ddmFormFieldTypeNames.stream();

		stream.map(
			ddmFormFieldTypeName -> _getFieldTypeMetadataJSONObject(
				contextAcceptLanguage, ddmFormFieldTypeName,
				contextHttpServletRequest,
				_getResourceBundle(contextAcceptLanguage.getPreferredLocale()))
		).filter(
			jsonObject -> !jsonObject.getBoolean("system")
		).forEach(
			jsonArray::put
		);

		return jsonArray.toJSONString();
	}

	@Override
	public String getDataDefinitionDataDefinitionFieldLinks(
			Long dataDefinitionId, String fieldName)
		throws Exception {

		return JSONUtil.put(
			"dataLayouts",
			transformToArray(
				_deDataDefinitionFieldLinkLocalService.
					getDEDataDefinitionFieldLinks(
						_getClassNameId(dataDefinitionId), dataDefinitionId,
						fieldName),
				deDataDefinitionFieldLink -> {
					DDMStructureLayout ddmStructureLayout =
						_ddmStructureLayoutLocalService.getDDMStructureLayout(
							deDataDefinitionFieldLink.getClassPK());

					return ddmStructureLayout.getName(
						ddmStructureLayout.getDefaultLanguageId());
				},
				String.class)
		).put(
			"dataListViews",
			transformToArray(
				_deDataDefinitionFieldLinkLocalService.
					getDEDataDefinitionFieldLinks(
						_portal.getClassNameId(DEDataListView.class),
						dataDefinitionId, fieldName),
				deDataDefinitionFieldLink -> {
					DEDataListView deDataListView =
						_deDataListViewLocalService.getDEDataListView(
							deDataDefinitionFieldLink.getClassPK());

					return deDataListView.getName(
						deDataListView.getDefaultLanguageId());
				},
				String.class)
		).toString();
	}

	public Page<Permission> getDataDefinitionPermissionsPage(
			Long dataDefinitionId, String roleNames)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService,
			ddmStructure.getGroupId());

		String resourceName = _getResourceName(ddmStructure);

		return Page.of(
			transform(
				PermissionUtil.getRoles(
					contextCompany, roleLocalService,
					StringUtil.split(roleNames)),
				role -> PermissionUtil.toPermission(
					contextCompany.getCompanyId(), dataDefinitionId,
					resourceActionLocalService.getResourceActions(resourceName),
					resourceName, resourcePermissionLocalService, role)));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public DataDefinition getSiteDataDefinitionByContentTypeByDataDefinitionKey(
			Long siteId, String contentType, String dataDefinitionKey)
		throws Exception {

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		return DataDefinitionUtil.toDataDefinition(
			_ddmFormFieldTypeServicesTracker,
			_ddmStructureLocalService.getStructure(
				siteId, dataDefinitionContentType.getClassNameId(),
				dataDefinitionKey));
	}

	@Override
	public Page<DataDefinition>
			getSiteDataDefinitionByContentTypeContentTypePage(
				Long siteId, String contentType, String keywords,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
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

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		if (Validator.isNull(keywords)) {
			return Page.of(
				transform(
					_ddmStructureLocalService.getStructures(
						siteId, dataDefinitionContentType.getClassNameId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					this::_toDataDefinition),
				pagination,
				_ddmStructureLocalService.getStructuresCount(
					siteId, dataDefinitionContentType.getClassNameId()));
		}

		return SearchUtil.search(
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
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			document -> DataDefinitionUtil.toDataDefinition(
				_ddmFormFieldTypeServicesTracker,
				_ddmStructureLocalService.getStructure(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public DataDefinition postDataDefinitionByContentType(
			String contentType, DataDefinition dataDefinition)
		throws Exception {

		return postSiteDataDefinitionByContentType(
			_portal.getSiteGroupId(contextCompany.getGroupId()), contentType,
			dataDefinition);
	}

	@Override
	public DataDefinition postSiteDataDefinitionByContentType(
			Long siteId, String contentType, DataDefinition dataDefinition)
		throws Exception {

		DataEnginePermissionUtil.checkPermission(
			DataActionKeys.ADD_DATA_DEFINITION, groupLocalService, siteId);

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeTracker.getDataDefinitionContentType(
				contentType);

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(
				DataDefinitionUtil.toDDMForm(
					dataDefinition, _ddmFormFieldTypeServicesTracker));

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(builder.build());

		dataDefinition = DataDefinitionUtil.toDataDefinition(
			_ddmFormFieldTypeServicesTracker,
			_ddmStructureLocalService.addStructure(
				PrincipalThreadLocal.getUserId(), siteId,
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				dataDefinitionContentType.getClassNameId(),
				dataDefinition.getDataDefinitionKey(),
				LocalizedValueUtil.toLocaleStringMap(dataDefinition.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					dataDefinition.getDescription()),
				ddmFormSerializerSerializeResponse.getContent(),
				GetterUtil.getString(dataDefinition.getStorageType(), "json"),
				new ServiceContext()));

		_resourceLocalService.addResources(
			contextCompany.getCompanyId(), siteId,
			PrincipalThreadLocal.getUserId(),
			ResourceActionsUtil.getCompositeModelName(
				_portal.getClassName(
					dataDefinitionContentType.getClassNameId()),
				DDMStructure.class.getName()),
			dataDefinition.getId(), false, false, false);

		SPIDataRecordCollectionResource<DataRecordCollection>
			spiDataRecordCollectionResource =
				new SPIDataRecordCollectionResource<>(
					_ddlRecordSetLocalService, _ddmStructureLocalService,
					_portal, _resourceLocalService,
					DataRecordCollectionUtil::toDataRecordCollection);

		spiDataRecordCollectionResource.postDataDefinitionDataRecordCollection(
			contextCompany, dataDefinition.getId(),
			dataDefinition.getDataDefinitionKey(),
			dataDefinition.getDescription(), dataDefinition.getName());

		return dataDefinition;
	}

	@Override
	public DataDefinition putDataDefinition(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.UPDATE);

		_updateFieldNames(dataDefinitionId, dataDefinition);

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(
				DataDefinitionUtil.toDDMForm(
					dataDefinition, _ddmFormFieldTypeServicesTracker));

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(builder.build());

		return DataDefinitionUtil.toDataDefinition(
			_ddmFormFieldTypeServicesTracker,
			_ddmStructureLocalService.updateStructure(
				PrincipalThreadLocal.getUserId(), dataDefinitionId,
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				LocalizedValueUtil.toLocaleStringMap(dataDefinition.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					dataDefinition.getDescription()),
				ddmFormSerializerSerializeResponse.getContent(),
				new ServiceContext()));
	}

	@Override
	public void putDataDefinitionPermission(
			Long dataDefinitionId, Permission[] permissions)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		DataEnginePermissionUtil.checkPermission(
			ActionKeys.PERMISSIONS, groupLocalService,
			ddmStructure.getGroupId());

		String resourceName = _getResourceName(ddmStructure);

		resourcePermissionLocalService.updateResourcePermissions(
			contextCompany.getCompanyId(), ddmStructure.getGroupId(),
			resourceName, String.valueOf(dataDefinitionId),
			ModelPermissionsUtil.toModelPermissions(
				contextCompany.getCompanyId(), permissions, dataDefinitionId,
				resourceName, resourceActionLocalService,
				resourcePermissionLocalService, roleLocalService));
	}

	private JSONObject _createFieldContextJSONObject(
		DDMFormFieldType ddmFormFieldType,
		HttpServletRequest httpServletRequest, Locale locale, String type) {

		Locale originalThemeDisplayLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setThemeDisplayLocale(locale);

		try {
			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				ddmFormFieldType.getDDMFormFieldTypeSettings());

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			ddmFormRenderingContext.setContainerId("settings");

			DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
				httpServletRequest, ddmFormFieldTypeSettingsDDMForm);

			_setTypeDDMFormFieldValue(ddmFormValues, type);

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
			ddmFormRenderingContext.setLocale(locale);
			ddmFormRenderingContext.setPortletNamespace(
				ParamUtil.getString(httpServletRequest, "portletNamespace"));
			ddmFormRenderingContext.setReturnFullContext(true);

			return JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerializeDeep(
					_ddmFormTemplateContextFactory.create(
						ddmFormFieldTypeSettingsDDMForm,
						DDMFormLayoutFactory.create(
							ddmFormFieldType.getDDMFormFieldTypeSettings()),
						ddmFormRenderingContext)));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
		finally {
			LocaleThreadLocal.setThemeDisplayLocale(originalThemeDisplayLocale);
		}

		return null;
	}

	private long _getClassNameId(long dataDefinitionId) throws PortalException {
		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		return ddmStructure.getClassNameId();
	}

	private JSONObject _getFieldTypeMetadataJSONObject(
		AcceptLanguage acceptLanguage, String ddmFormFieldName,
		HttpServletRequest httpServletRequest, ResourceBundle resourceBundle) {

		Map<String, Object> ddmFormFieldTypeProperties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				ddmFormFieldName);

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
				ddmFormFieldName);

		return JSONUtil.put(
			"description",
			_translate(
				MapUtil.getString(
					ddmFormFieldTypeProperties,
					"ddm.form.field.type.description"),
				resourceBundle)
		).put(
			"displayOrder",
			MapUtil.getInteger(
				ddmFormFieldTypeProperties, "ddm.form.field.type.display.order",
				Integer.MAX_VALUE)
		).put(
			"group",
			MapUtil.getString(
				ddmFormFieldTypeProperties, "ddm.form.field.type.group")
		).put(
			"icon",
			MapUtil.getString(
				ddmFormFieldTypeProperties, "ddm.form.field.type.icon")
		).put(
			"javaScriptModule",
			_resolveModuleName(
				GetterUtil.getString(ddmFormFieldType.getModuleName()))
		).put(
			"label",
			_translate(
				MapUtil.getString(
					ddmFormFieldTypeProperties, "ddm.form.field.type.label"),
				resourceBundle)
		).put(
			"name", ddmFormFieldName
		).put(
			"scope",
			MapUtil.getString(
				ddmFormFieldTypeProperties, "ddm.form.field.type.scope")
		).put(
			"settingsContext",
			_createFieldContextJSONObject(
				ddmFormFieldType, httpServletRequest,
				acceptLanguage.getPreferredLocale(), ddmFormFieldName)
		).put(
			"system",
			MapUtil.getBoolean(
				ddmFormFieldTypeProperties, "ddm.form.field.type.system")
		);
	}

	private String[] _getRemovedFieldNames(
		DataDefinition dataDefinition, DDMStructure ddmStructure) {

		DataDefinition existingDataDefinition =
			DataDefinitionUtil.toDataDefinition(
				_ddmFormFieldTypeServicesTracker, ddmStructure);

		return _removeFieldNames(
			transform(
				existingDataDefinition.getDataDefinitionFields(),
				dataDefinitionField -> dataDefinitionField.getName(),
				String.class),
			transform(
				dataDefinition.getDataDefinitionFields(),
				dataDefinitionField -> dataDefinitionField.getName(),
				String.class));
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return new AggregateResourceBundle(
			ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass()),
			_portal.getResourceBundle(locale));
	}

	private String _getResourceName(DDMStructure ddmStructure) {
		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDMStructure.class.getName());
	}

	private String[] _removeFieldNames(
		String[] currentFieldNames, String[] removedFieldNames) {

		return ArrayUtil.filter(
			currentFieldNames,
			fieldName -> !ArrayUtil.contains(removedFieldNames, fieldName));
	}

	private String _resolveModuleName(String moduleName) {
		if (Validator.isNull(moduleName)) {
			return StringPool.BLANK;
		}

		return _npmResolver.resolveModuleName(moduleName);
	}

	private void _setTypeDDMFormFieldValue(
		DDMFormValues ddmFormValues, String type) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"type");

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		ddmFormFieldValue.setValue(new UnlocalizedValue(type));
	}

	private DataDefinition _toDataDefinition(DDMStructure ddmStructure)
		throws Exception {

		return DataDefinitionUtil.toDataDefinition(
			_ddmFormFieldTypeServicesTracker, ddmStructure);
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

	private String _translate(String key, ResourceBundle resourceBundle) {
		if (Validator.isNull(key)) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(
			ResourceBundleUtil.getString(resourceBundle, key), key);
	}

	private void _updateDataLayoutFieldNames(
		DataLayout dataLayout, String[] removedFieldNames) {

		Stream<DataLayoutPage> dataLayoutPages = Arrays.stream(
			dataLayout.getDataLayoutPages());

		dataLayoutPages.forEach(
			dataLayoutPage -> {
				Stream<DataLayoutRow> dataLayoutRows = Arrays.stream(
					dataLayoutPage.getDataLayoutRows());

				dataLayoutRows.forEach(
					dataLayoutRow -> {
						Stream<DataLayoutColumn> dataLayoutColumns =
							Arrays.stream(dataLayoutRow.getDataLayoutColumns());

						dataLayoutColumns.forEach(
							dataLayoutColumn -> dataLayoutColumn.setFieldNames(
								_removeFieldNames(
									dataLayoutColumn.getFieldNames(),
									removedFieldNames)));

						dataLayoutRow.setDataLayoutColumns(
							ArrayUtil.filter(
								dataLayoutRow.getDataLayoutColumns(),
								column -> !ArrayUtil.isEmpty(
									column.getFieldNames())));
					});

				dataLayoutPage.setDataLayoutRows(
					ArrayUtil.filter(
						dataLayoutPage.getDataLayoutRows(),
						row -> !ArrayUtil.isEmpty(row.getDataLayoutColumns())));
			});
	}

	private void _updateDataLayouts(
			Set<Long> ddmStructureLayoutIds, String[] removedFieldNames)
		throws Exception {

		for (Long ddmStructureLayoutId : ddmStructureLayoutIds) {
			DDMStructureLayout ddmStructureLayout =
				_ddmStructureLayoutLocalService.getStructureLayout(
					ddmStructureLayoutId);

			DataLayout dataLayout = DataLayoutUtil.toDataLayout(
				ddmStructureLayout.getDDMFormLayout());

			_updateDataLayoutFieldNames(dataLayout, removedFieldNames);

			DDMFormLayout ddmFormLayout = DataLayoutUtil.toDDMFormLayout(
				dataLayout);

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
		throws PortalException {

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
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		String[] removedFieldNames = _getRemovedFieldNames(
			dataDefinition, ddmStructure);

		Set<Long> ddmStructureLayoutIds = new HashSet<>();
		Set<Long> deDataListViewIds = new HashSet<>();

		for (String removedFieldName : removedFieldNames) {
			ddmStructureLayoutIds.addAll(
				transform(
					_deDataDefinitionFieldLinkLocalService.
						getDEDataDefinitionFieldLinks(
							_getClassNameId(dataDefinitionId),
							ddmStructure.getStructureId(), removedFieldName),
					deDataDefinitionFieldLink ->
						deDataDefinitionFieldLink.getClassPK()));

			deDataListViewIds.addAll(
				transform(
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

	private static final Log _log = LogFactoryUtil.getLog(
		DataDefinitionResourceImpl.class);

	private static final EntityModel _entityModel =
		new DataDefinitionEntityModel();

	@Reference
	private DataDefinitionContentTypeTracker _dataDefinitionContentTypeTracker;

	@Reference
	private DataDefinitionModelResourcePermission
		_dataDefinitionModelResourcePermission;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference(target = "(ddm.form.layout.serializer.type=json)")
	private DDMFormLayoutSerializer _ddmFormLayoutSerializer;

	@Reference(target = "(ddm.form.serializer.type=json)")
	private DDMFormSerializer _ddmFormSerializer;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

	@Reference
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

}