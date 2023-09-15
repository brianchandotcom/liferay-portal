/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.resource.v2_0;

import com.google.gson.Gson;

import com.liferay.data.engine.constants.DataActionKeys;
import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.model.DEDataDefinitionFieldLink;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.dto.v2_0.DataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.dto.v2_0.util.DataDefinitionDDMFormUtil;
import com.liferay.data.engine.rest.internal.content.type.DataDefinitionContentTypeRegistry;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.odata.entity.v2_0.DataDefinitionEntityModel;
import com.liferay.data.engine.rest.internal.security.permission.resource.DataDefinitionModelResourcePermission;
import com.liferay.data.engine.rest.resource.exception.DataDefinitionValidationException;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataLayoutResource;
import com.liferay.data.engine.rest.resource.v2_0.DataListViewResource;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordCollectionResource;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.exception.RequiredStructureException;
import com.liferay.dynamic.data.mapping.form.builder.rule.DDMFormRuleDeserializer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
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
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.spi.converter.SPIDDMFormRuleConverter;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.dynamic.data.mapping.util.comparator.StructureCreateDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidator;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.permission.Permission;
import com.liferay.portal.vulcan.permission.PermissionUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

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
@CTAware
public class DataDefinitionResourceImpl extends BaseDataDefinitionResourceImpl {

	@Override
	public void deleteDataDefinition(Long dataDefinitionId) throws Exception {
		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.DELETE);

		List<DDMStructureLink> ddmStructureLinks =
			_ddmStructureLinkLocalService.getStructureLinks(dataDefinitionId);

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeRegistry.getDataDefinitionContentType(
				ddmStructure.getClassNameId());

		List<DEDataDefinitionFieldLink> deDataDefinitionFieldLinks =
			ListUtil.filter(
				_deDataDefinitionFieldLinkLocalService.
					getDEDataDefinitionFieldLinks(dataDefinitionId),
				deDataDefinitionFieldLink -> StringUtil.equals(
					deDataDefinitionFieldLink.getClassName(),
					DDMStructure.class.getName()));

		if ((ddmStructureLinks.size() > 1) ||
			(!dataDefinitionContentType.
				allowReferencedDataDefinitionDeletion() &&
			 !deDataDefinitionFieldLinks.isEmpty())) {

			throw new RequiredStructureException.
				MustNotDeleteStructureReferencedByStructureLinks(
					dataDefinitionId);
		}

		List<DDMTemplate> ddmTemplates = _ddmTemplateLocalService.getTemplates(
			dataDefinitionId);

		if (!ddmTemplates.isEmpty()) {
			throw new RequiredStructureException.
				MustNotDeleteStructureReferencedByTemplates(dataDefinitionId);
		}

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getChildrenStructures(dataDefinitionId);

		if (!ddmStructures.isEmpty()) {
			throw new RequiredStructureException.
				MustNotDeleteStructureThatHasChild(dataDefinitionId);
		}

		DataLayoutResource dataLayoutResource = _getDataLayoutResource(false);

		dataLayoutResource.deleteDataDefinitionDataLayout(dataDefinitionId);

		DataListViewResource.Builder dataListViewResourceBuilder =
			_dataListViewResourceFactory.create();

		DataListViewResource dataListViewResource =
			dataListViewResourceBuilder.checkPermissions(
				false
			).user(
				contextUser
			).build();

		dataListViewResource.deleteDataDefinitionDataListView(dataDefinitionId);

		_ddlRecordSetLocalService.deleteDDMStructureRecordSets(
			dataDefinitionId);

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_portal.getClassNameId(DDMStructure.class), dataDefinitionId);

		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
				deDataDefinitionFieldLinks) {

			_deDataDefinitionFieldLinkLocalService.
				deleteDEDataDefinitionFieldLink(deDataDefinitionFieldLink);

			if (deDataDefinitionFieldLink.getClassNameId() !=
					_portal.getClassNameId(DDMStructure.class)) {

				continue;
			}

			DataDefinition dataDefinition = _toDataDefinition(
				_ddmStructureLocalService.getStructure(
					deDataDefinitionFieldLink.getClassPK()));

			dataDefinition.setDataDefinitionFields(
				ArrayUtil.filter(
					dataDefinition.getDataDefinitionFields(),
					dataDefinitionField -> !StringUtil.equals(
						dataDefinitionField.getName(),
						deDataDefinitionFieldLink.getFieldName())));

			_removeFieldsFromDataLayoutsAndDataListViews(
				deDataDefinitionFieldLink.getClassPK(),
				_getRemovedFieldNames(dataDefinition, dataDefinition.getId()));

			_updateDataDefinition(
				dataDefinition, dataDefinition.getId(),
				DataDefinitionDDMFormUtil.toDDMForm(
					dataDefinition, _ddmFormFieldTypeServicesRegistry));
		}

		_ddmStructureLocalService.deleteStructure(dataDefinitionId);
	}

	@Override
	public DataDefinition getDataDefinition(Long dataDefinitionId)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.VIEW);

		return DataDefinitionUtil.toDataDefinition(
			_dataDefinitionContentTypeRegistry,
			_ddmFormFieldTypeServicesRegistry,
			_ddmStructureLocalService.getStructure(dataDefinitionId),
			_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter);
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

		for (String ddmFormFieldTypeName :
				_ddmFormFieldTypeServicesRegistry.getDDMFormFieldTypeNames()) {

			jsonArray.put(
				_getFieldTypeMetadataJSONObject(
					ddmFormFieldTypeName,
					_getResourceBundle(
						ddmFormFieldTypeName,
						contextAcceptLanguage.getPreferredLocale())));
		}

		return jsonArray.toString();
	}

	@Override
	public Page<Permission> getDataDefinitionPermissionsPage(
			Long dataDefinitionId, String roleNames)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.PERMISSIONS);

		String resourceName = getPermissionCheckerResourceName(
			dataDefinitionId);

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

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			siteId,
			_dataDefinitionContentTypeRegistry.getClassNameId(contentType),
			dataDefinitionKey);

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			ddmStructure.getStructureId(), ActionKeys.VIEW);

		return DataDefinitionUtil.toDataDefinition(
			_dataDefinitionContentTypeRegistry,
			_ddmFormFieldTypeServicesRegistry, ddmStructure,
			_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter);
	}

	@Override
	public Page<DataDefinition>
			getSiteDataDefinitionByContentTypeContentTypePage(
				Long siteId, String contentType, String keywords,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		if (pagination.getPageSize() > 250) {
			throw new ValidationException(
				_language.format(
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

		if (Validator.isNull(keywords)) {
			return Page.of(
				transform(
					_ddmStructureLocalService.getStructures(
						siteId,
						_dataDefinitionContentTypeRegistry.getClassNameId(
							contentType),
						pagination.getStartPosition(),
						pagination.getEndPosition(),
						_toOrderByComparator(
							(Sort)ArrayUtil.getValue(sorts, 0))),
					this::_toDataDefinition),
				pagination,
				_ddmStructureLocalService.getStructuresCount(
					siteId,
					_dataDefinitionContentTypeRegistry.getClassNameId(
						contentType)));
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null, DDMStructure.class.getName(), keywords, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.CLASS_NAME_ID, Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(
					Field.CLASS_NAME_ID,
					_dataDefinitionContentTypeRegistry.getClassNameId(
						contentType));
				searchContext.setAttribute(Field.DESCRIPTION, keywords);
				searchContext.setAttribute(Field.NAME, keywords);
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			sorts,
			document -> DataDefinitionUtil.toDataDefinition(
				_dataDefinitionContentTypeRegistry,
				_ddmFormFieldTypeServicesRegistry,
				_ddmStructureLocalService.getStructure(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))),
				_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter));
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
	public DataDefinition postDataDefinitionCopy(Long dataDefinitionId)
		throws Exception {

		DataDefinition dataDefinition = DataDefinitionUtil.toDataDefinition(
			_dataDefinitionContentTypeRegistry,
			_ddmFormFieldTypeServicesRegistry,
			_ddmStructureLocalService.getStructure(dataDefinitionId),
			_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter);

		_uniquifyDataDefinitionFields(dataDefinition);

		dataDefinition.setDataDefinitionKey(StringPool.BLANK);

		return _postSiteDataDefinitionByContentType(
			dataDefinition.getSiteId(), dataDefinition.getContentType(),
			dataDefinition, true);
	}

	@Override
	public DataDefinition postSiteDataDefinitionByContentType(
			Long siteId, String contentType, DataDefinition dataDefinition)
		throws Exception {

		return _postSiteDataDefinitionByContentType(
			siteId, contentType, dataDefinition, false);
	}

	@Override
	public DataDefinition putDataDefinition(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception {

		_dataDefinitionModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), dataDefinitionId,
			ActionKeys.UPDATE);

		DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

		if (dataLayout != null) {
			DataLayoutResource dataLayoutResource = _getDataLayoutResource(
				false);

			Long id = dataLayout.getId();

			if (id == null) {
				id = _getDefaultDataLayoutId(dataDefinitionId);
			}

			dataDefinition.setDefaultDataLayout(
				dataLayoutResource.putDataLayout(id, dataLayout));
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
			ddmStructure.getDefinition());

		DDMForm ddmForm = DataDefinitionDDMFormUtil.toDDMForm(
			dataDefinition, _ddmFormFieldTypeServicesRegistry);

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeRegistry.getDataDefinitionContentType(
				ddmStructure.getClassNameId());

		ddmForm.setAllowInvalidAvailableLocalesForProperty(
			dataDefinitionContentType.
				allowInvalidAvailableLocalesForProperty());

		ddmForm.setDefinitionSchemaVersion(
			definitionJSONObject.getString("definitionSchemaVersion"));

		_validate(dataDefinition, dataDefinitionContentType, ddmForm);

		List<DEDataDefinitionFieldLink> deDataDefinitionFieldLinks =
			_deDataDefinitionFieldLinkLocalService.
				getDEDataDefinitionFieldLinks(
					_portal.getClassNameId(DDMStructure.class),
					dataDefinitionId);

		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
				deDataDefinitionFieldLinks) {

			DataDefinition existingDataDefinition =
				DataDefinitionUtil.toDataDefinition(
					_dataDefinitionContentTypeRegistry,
					_ddmFormFieldTypeServicesRegistry,
					_ddmStructureLocalService.getStructure(
						deDataDefinitionFieldLink.getClassPK()),
					_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter);

			for (DataDefinitionField dataDefinitionField :
					existingDataDefinition.getDataDefinitionFields()) {

				long ddmStructureId = MapUtil.getLong(
					dataDefinitionField.getCustomProperties(),
					"ddmStructureId");

				if (ddmStructureId != dataDefinitionId) {
					continue;
				}

				Map<String, Object> customProperties =
					dataDefinitionField.getCustomProperties();

				Long id = dataLayout.getId();

				if (id == null) {
					id = _getDefaultDataLayoutId(dataDefinitionId);
				}

				DDMStructureLayout ddmStructureLayout =
					_ddmStructureLayoutLocalService.getStructureLayout(id);

				JSONArray jsonArray = JSONUtil.getValueAsJSONArray(
					_jsonFactory.createJSONObject(
						StringUtil.replace(
							ddmStructureLayout.getDefinition(),
							new String[] {"fieldNames"},
							new String[] {"fields"})),
					"JSONArray/pages", "Object/0", "JSONArray/rows");

				customProperties.put("rows", jsonArray.toString());

				DataDefinitionField[] nestedDataDefinitionFields =
					new DataDefinitionField[0];

				for (DataDefinitionField dataDefinitionField1 :
						dataDefinition.getDataDefinitionFields()) {

					Gson gson = new Gson();

					nestedDataDefinitionFields = ArrayUtil.append(
						nestedDataDefinitionFields,
						gson.fromJson(
							_jsonFactory.looseSerializeDeep(
								dataDefinitionField1),
							DataDefinitionField.class));
				}

				dataDefinitionField.setNestedDataDefinitionFields(
					nestedDataDefinitionFields);
			}

			putDataDefinition(
				existingDataDefinition.getId(), existingDataDefinition);
		}

		_removeFieldsFromDataLayoutsAndDataListViews(
			dataDefinitionId,
			_getRemovedFieldNames(dataDefinition, dataDefinitionId));

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_portal.getClassNameId(DDMStructure.class), dataDefinitionId);

		Long id = dataDefinition.getSiteId();

		if (id == null) {
			id = getPermissionCheckerGroupId(dataDefinitionId);
		}

		_addDataDefinitionFieldLinks(
			dataDefinitionId, ddmForm.getDDMFormFields(), id);

		_sortNestedDDMFormFields(ddmForm.getDDMFormFields());

		return _updateDataDefinition(dataDefinition, dataDefinitionId, ddmForm);
	}

	@Override
	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			(long)id);

		return ddmStructure.getGroupId();
	}

	@Override
	protected String getPermissionCheckerResourceName(Object id)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			(long)id);

		return ResourceActionsUtil.getCompositeModelName(
			_portal.getClassName(ddmStructure.getClassNameId()),
			DDMStructure.class.getName());
	}

	private void _addDataDefinitionFieldLinks(
			long dataDefinitionId, List<DDMFormField> ddmFormFields,
			long groupId)
		throws Exception {

		for (DDMFormField ddmFormField : ddmFormFields) {
			long fieldSetDDMStructureId = GetterUtil.getLong(
				ddmFormField.getProperty("ddmStructureId"));

			if (fieldSetDDMStructureId != 0) {
				_deDataDefinitionFieldLinkLocalService.
					addDEDataDefinitionFieldLink(
						groupId, _portal.getClassNameId(DDMStructure.class),
						dataDefinitionId, fieldSetDDMStructureId,
						ddmFormField.getName());

				_addDataDefinitionFieldLinks(
					dataDefinitionId, ddmFormField.getNestedDDMFormFields(),
					groupId);
			}
		}
	}

	private JSONObject _createFieldContextJSONObject(
		DDMFormFieldType ddmFormFieldType, Locale locale, String type) {

		Locale originalThemeDisplayLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setThemeDisplayLocale(locale);

		try {
			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				ddmFormFieldType.getDDMFormFieldTypeSettings());

			Set<Locale> availableLocales = null;

			DDMForm ddmForm = _getDDMForm();

			if (ddmForm != null) {
				availableLocales = ddmForm.getAvailableLocales();
			}
			else {
				Locale defaultLocal = LocaleThreadLocal.getSiteDefaultLocale();

				if (defaultLocal == null) {
					defaultLocal = LocaleThreadLocal.getDefaultLocale();
				}

				availableLocales = Collections.singleton(defaultLocal);
			}

			ddmFormFieldTypeSettingsDDMForm.setAvailableLocales(
				availableLocales);
			ddmFormFieldTypeSettingsDDMForm.setDefaultLocale(
				_getDefaultLocale());

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			ddmFormRenderingContext.setContainerId("settings");

			DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
				contextHttpServletRequest, ddmFormFieldTypeSettingsDDMForm);

			_setTypeDDMFormFieldValue(ddmFormValues, type);

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setHttpServletRequest(
				contextHttpServletRequest);
			ddmFormRenderingContext.setLocale(_getDefaultLocale());
			ddmFormRenderingContext.setPortletNamespace(
				_portal.getPortletNamespace(
					_portal.getPortletId(contextHttpServletRequest)));
			ddmFormRenderingContext.setReturnFullContext(true);

			return _jsonFactory.createJSONObject(
				_jsonFactory.looseSerializeDeep(
					_ddmFormTemplateContextFactory.create(
						ddmFormFieldTypeSettingsDDMForm,
						DDMFormLayoutFactory.create(
							ddmFormFieldType.getDDMFormFieldTypeSettings()),
						ddmFormRenderingContext)));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
		finally {
			LocaleThreadLocal.setThemeDisplayLocale(originalThemeDisplayLocale);
		}

		return null;
	}

	private DataLayoutResource _getDataLayoutResource(boolean checkPermission) {
		DataLayoutResource.Builder dataLayoutResourceBuilder =
			_dataLayoutResourceFactory.create();

		return dataLayoutResourceBuilder.checkPermissions(
			checkPermission
		).user(
			contextUser
		).build();
	}

	private DataRecordCollectionResource _getDataRecordCollectionResource(
		boolean checkPermission) {

		DataRecordCollectionResource.Builder
			dataRecordCollectionResourceBuilder =
				_dataRecordCollectionResourceFactory.create();

		return dataRecordCollectionResourceBuilder.checkPermissions(
			checkPermission
		).user(
			contextUser
		).build();
	}

	private DDMForm _getDDMForm() {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchDDMStructure(
			ParamUtil.getLong(contextHttpServletRequest, "ddmStructureId"));

		if (ddmStructure == null) {
			return null;
		}

		return ddmStructure.getDDMForm();
	}

	private long _getDefaultDataLayoutId(long dataDefinitionId) {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchDDMStructure(
			dataDefinitionId);

		if (ddmStructure == null) {
			return 0L;
		}

		return ddmStructure.getDefaultDDMStructureLayoutId();
	}

	private Locale _getDefaultLocale() {
		DDMForm ddmForm = _getDDMForm();

		if (ddmForm != null) {
			return ddmForm.getDefaultLocale();
		}

		String i18nLanguageId = (String)contextHttpServletRequest.getAttribute(
			WebKeys.I18N_LANGUAGE_ID);

		if (Validator.isNotNull(i18nLanguageId)) {
			return LocaleUtil.fromLanguageId(i18nLanguageId);
		}

		Locale locale = LocaleThreadLocal.getSiteDefaultLocale();

		if (locale != null) {
			return locale;
		}

		return LocaleThreadLocal.getDefaultLocale();
	}

	private JSONObject _getFieldTypeMetadataJSONObject(
		String ddmFormFieldName, ResourceBundle resourceBundle) {

		Map<String, Object> ddmFormFieldTypeProperties =
			_ddmFormFieldTypeServicesRegistry.getDDMFormFieldTypeProperties(
				ddmFormFieldName);

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesRegistry.getDDMFormFieldType(
				ddmFormFieldName);

		JSONObject jsonObject = JSONUtil.put(
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
			"javaScriptModule", _resolveModuleName(ddmFormFieldType)
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
				ddmFormFieldType, contextAcceptLanguage.getPreferredLocale(),
				ddmFormFieldName)
		).put(
			"system",
			MapUtil.getBoolean(
				ddmFormFieldTypeProperties, "ddm.form.field.type.system")
		);

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if ((themeDisplay != null) &&
			StringUtil.equals(ddmFormFieldType.getName(), "rich_text")) {

			EditorConfiguration editorConfiguration =
				_editorConfigurationFactory.getEditorConfiguration(
					StringPool.BLANK, ddmFormFieldType.getName(),
					"ckeditor_classic", new HashMap<String, Object>(),
					themeDisplay,
					RequestBackedPortletURLFactoryUtil.create(
						contextHttpServletRequest));

			Map<String, Object> data = editorConfiguration.getData();

			jsonObject.put("editorConfig", data.get("editorConfig"));
		}

		return jsonObject;
	}

	private String[] _getRemovedFieldNames(
			DataDefinition dataDefinition, long dataDefinitionId)
		throws Exception {

		List<String> removedFieldNames = new ArrayList<>();

		DDMForm ddmForm = DataDefinitionDDMFormUtil.toDDMForm(
			dataDefinition, _ddmFormFieldTypeServicesRegistry);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		String[] fieldNames = ArrayUtil.toStringArray(
			ddmFormFieldsMap.keySet());

		DataDefinition existingDataDefinition =
			DataDefinitionUtil.toDataDefinition(
				_dataDefinitionContentTypeRegistry,
				_ddmFormFieldTypeServicesRegistry,
				_ddmStructureLocalService.getStructure(dataDefinitionId),
				_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter);

		DDMForm existingDDMForm = DataDefinitionDDMFormUtil.toDDMForm(
			existingDataDefinition, _ddmFormFieldTypeServicesRegistry);

		Map<String, DDMFormField> existingDDMFormFieldsMap =
			existingDDMForm.getDDMFormFieldsMap(true);

		for (Map.Entry<String, DDMFormField> entry :
				existingDDMFormFieldsMap.entrySet()) {

			if (ArrayUtil.contains(fieldNames, entry.getKey())) {
				continue;
			}

			removedFieldNames.add(entry.getKey());

			DDMFormField ddmFormField = entry.getValue();

			if (!Objects.equals(ddmFormField.getType(), "fieldset")) {
				continue;
			}

			long ddmStructureId = MapUtil.getLong(
				ddmFormField.getProperties(), "ddmStructureId");

			DDMStructure fieldSetDDMStructure =
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId);

			if (fieldSetDDMStructure == null) {
				continue;
			}

			Map<String, DDMFormField> map =
				fieldSetDDMStructure.getFullHierarchyDDMFormFieldsMap(false);

			removedFieldNames.addAll(map.keySet());
		}

		return removedFieldNames.toArray(new String[0]);
	}

	private ResourceBundle _getResourceBundle(
		String ddmFormFieldTypeName, Locale locale) {

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesRegistry.getDDMFormFieldType(
				ddmFormFieldTypeName);

		return new AggregateResourceBundle(
			ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass()),
			ResourceBundleUtil.getBundle(
				"content.Language", locale, ddmFormFieldType.getClass()),
			_portal.getResourceBundle(locale));
	}

	private ThemeDisplay _getThemeDisplay() {
		if (contextHttpServletRequest == null) {
			return null;
		}

		return (ThemeDisplay)contextHttpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	private DataDefinition _postSiteDataDefinitionByContentType(
			Long siteId, String contentType, DataDefinition dataDefinition,
			boolean copyPermissions)
		throws Exception {

		_dataDefinitionModelResourcePermission.checkPortletPermission(
			PermissionThreadLocal.getPermissionChecker(), contentType, siteId,
			DataActionKeys.ADD_DATA_DEFINITION);

		DDMForm ddmForm = DataDefinitionDDMFormUtil.toDDMForm(
			dataDefinition, _ddmFormFieldTypeServicesRegistry);

		DataDefinitionContentType dataDefinitionContentType =
			_dataDefinitionContentTypeRegistry.getDataDefinitionContentType(
				contentType);

		ddmForm.setAllowInvalidAvailableLocalesForProperty(
			dataDefinitionContentType.
				allowInvalidAvailableLocalesForProperty());

		ddmForm.setDefinitionSchemaVersion("2.0");

		_validate(dataDefinition, dataDefinitionContentType, ddmForm);

		_sortNestedDDMFormFields(ddmForm.getDDMFormFields());

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(builder.build());

		DDMStructure ddmStructure = _ddmStructureLocalService.addStructure(
			PrincipalThreadLocal.getUserId(), siteId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			_dataDefinitionContentTypeRegistry.getClassNameId(contentType),
			dataDefinition.getDataDefinitionKey(),
			LocalizedValueUtil.toLocaleStringMap(dataDefinition.getName()),
			LocalizedValueUtil.toLocaleStringMap(
				dataDefinition.getDescription()),
			ddmFormSerializerSerializeResponse.getContent(),
			GetterUtil.getString(
				dataDefinition.getStorageType(),
				StorageType.DEFAULT.getValue()),
			new ServiceContext());

		DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

		if (dataLayout != null) {
			dataLayout.setDataLayoutKey(ddmStructure.getStructureKey());

			if (Validator.isNull(dataLayout.getName())) {
				dataLayout.setName(dataDefinition.getName());
			}

			DataLayoutResource dataLayoutResource = _getDataLayoutResource(
				false);

			try {
				dataDefinition.setDefaultDataLayout(
					dataLayoutResource.postDataDefinitionDataLayout(
						ddmStructure.getStructureId(), dataLayout));
			}
			catch (Exception exception) {
				_ddmStructureLocalService.deleteStructure(ddmStructure);

				throw exception;
			}
		}

		_addDataDefinitionFieldLinks(
			ddmStructure.getStructureId(), ddmForm.getDDMFormFields(),
			ddmStructure.getGroupId());

		Long oldDataDefinitionId = dataDefinition.getId();

		dataDefinition = DataDefinitionUtil.toDataDefinition(
			_dataDefinitionContentTypeRegistry,
			_ddmFormFieldTypeServicesRegistry, ddmStructure,
			_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter);

		if (copyPermissions) {
			_resourceLocalService.copyModelResources(
				ddmStructure.getCompanyId(),
				_ddmPermissionSupport.getStructureModelResourceName(
					ddmStructure.getClassName()),
				oldDataDefinitionId, ddmStructure.getPrimaryKey());
		}
		else {
			_resourceLocalService.addResources(
				contextCompany.getCompanyId(), siteId,
				PrincipalThreadLocal.getUserId(),
				ResourceActionsUtil.getCompositeModelName(
					_portal.getClassName(
						_dataDefinitionContentTypeRegistry.getClassNameId(
							contentType)),
					DDMStructure.class.getName()),
				dataDefinition.getId(), false, false, false);
		}

		DataRecordCollectionResource dataRecordCollectionResource =
			_getDataRecordCollectionResource(false);

		dataRecordCollectionResource.postDataDefinitionDataRecordCollection(
			dataDefinition.getId(),
			new DataRecordCollection() {
				{
					dataDefinitionId = ddmStructure.getStructureId();
					dataRecordCollectionKey = ddmStructure.getStructureKey();
					description = LocalizedValueUtil.toStringObjectMap(
						ddmStructure.getDescriptionMap());
					name = LocalizedValueUtil.toStringObjectMap(
						ddmStructure.getNameMap());
				}
			});

		return dataDefinition;
	}

	private void _removeFieldsFromDataLayout(
		DataLayout dataLayout, String[] fieldNames) {

		Map<String, Object> dataLayoutFields = dataLayout.getDataLayoutFields();

		Set<String> dataLayoutFieldNames = dataLayoutFields.keySet();

		dataLayoutFieldNames.removeIf(
			dataLayoutFieldName -> ArrayUtil.contains(
				fieldNames, dataLayoutFieldName));

		for (DataLayoutPage dataLayoutPage : dataLayout.getDataLayoutPages()) {
			for (DataLayoutRow dataLayoutRow :
					dataLayoutPage.getDataLayoutRows()) {

				for (DataLayoutColumn dataLayoutColumn :
						dataLayoutRow.getDataLayoutColumns()) {

					dataLayoutColumn.setFieldNames(
						ArrayUtil.filter(
							dataLayoutColumn.getFieldNames(),
							fieldName -> !ArrayUtil.contains(
								fieldNames, fieldName)));
				}

				dataLayoutRow.setDataLayoutColumns(
					ArrayUtil.filter(
						dataLayoutRow.getDataLayoutColumns(),
						column ->
							!(ArrayUtil.isEmpty(column.getFieldNames()) &&
							  (column.getColumnSize() == 12))));
			}

			dataLayoutPage.setDataLayoutRows(
				ArrayUtil.filter(
					dataLayoutPage.getDataLayoutRows(),
					row -> !ArrayUtil.isEmpty(row.getDataLayoutColumns())));
		}
	}

	private void _removeFieldsFromDataLayouts(
			Set<Long> ddmStructureLayoutIds, String[] fieldNames)
		throws Exception {

		for (Long ddmStructureLayoutId : ddmStructureLayoutIds) {
			DDMStructureLayout ddmStructureLayout =
				_ddmStructureLayoutLocalService.getStructureLayout(
					ddmStructureLayoutId);

			DataLayout dataLayout = DataLayoutUtil.toDataLayout(
				_ddmFormFieldTypeServicesRegistry,
				ddmStructureLayout.getDDMFormLayout(),
				_spiDDMFormRuleConverter);

			_removeFieldsFromDataLayout(dataLayout, fieldNames);

			DDMStructure ddmStructure = ddmStructureLayout.getDDMStructure();

			DDMFormLayout ddmFormLayout = ddmStructureLayout.getDDMFormLayout();

			String definitionSchemaVersion =
				ddmFormLayout.getDefinitionSchemaVersion();

			ddmFormLayout = DataLayoutUtil.toDDMFormLayout(
				dataLayout, ddmStructure.getDDMForm(),
				_ddmFormFieldTypeServicesRegistry, _ddmFormRuleDeserializer);

			ddmFormLayout.setDefinitionSchemaVersion(definitionSchemaVersion);

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

	private void _removeFieldsFromDataLayoutsAndDataListViews(
			long dataDefinitionId, String[] fieldNames)
		throws Exception {

		Set<Long> ddmStructureLayoutIds = new HashSet<>();
		Set<Long> deDataListViewIds = new HashSet<>();

		ddmStructureLayoutIds.addAll(
			transform(
				_deDataDefinitionFieldLinkLocalService.
					getDEDataDefinitionFieldLinks(
						_portal.getClassNameId(DDMStructureLayout.class),
						dataDefinitionId, fieldNames),
				DEDataDefinitionFieldLink::getClassPK));
		deDataListViewIds.addAll(
			transform(
				_deDataDefinitionFieldLinkLocalService.
					getDEDataDefinitionFieldLinks(
						_portal.getClassNameId(DEDataListView.class),
						dataDefinitionId, fieldNames),
				DEDataDefinitionFieldLink::getClassPK));

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_portal.getClassNameId(DDMStructureLayout.class), dataDefinitionId,
			fieldNames);
		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLinks(
			_portal.getClassNameId(DEDataListView.class), dataDefinitionId,
			fieldNames);

		_removeFieldsFromDataLayouts(ddmStructureLayoutIds, fieldNames);
		_removeFieldsFromDataListViews(deDataListViewIds, fieldNames);
	}

	private void _removeFieldsFromDataListViews(
			Set<Long> deDataListViewIds, String[] removedFieldNames)
		throws Exception {

		DataListViewResource.Builder dataListViewResourceBuilder =
			_dataListViewResourceFactory.create();

		DataListViewResource dataListViewResource =
			dataListViewResourceBuilder.checkPermissions(
				false
			).user(
				contextUser
			).build();

		for (Long deDataListViewId : deDataListViewIds) {
			DataListView dataListView = dataListViewResource.getDataListView(
				deDataListViewId);

			dataListView.setFieldNames(
				ArrayUtil.filter(
					JSONUtil.toStringArray(
						_jsonFactory.createJSONArray(
							dataListView.getFieldNames())),
					fieldName -> !ArrayUtil.contains(
						removedFieldNames, fieldName)));

			dataListViewResource.putDataListView(
				dataListView.getId(), dataListView);
		}
	}

	private String _resolveModuleName(DDMFormFieldType ddmFormFieldType) {
		if (Validator.isNull(ddmFormFieldType.getModuleName())) {
			return StringPool.BLANK;
		}

		if (ddmFormFieldType.isCustomDDMFormFieldType()) {
			return ddmFormFieldType.getModuleName();
		}

		return _npmResolver.resolveModuleName(ddmFormFieldType.getModuleName());
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

	private void _sortNestedDDMFormFields(List<DDMFormField> ddmFormFields)
		throws Exception {

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (!StringUtil.equals(
					ddmFormField.getType(),
					DDMFormFieldTypeConstants.FIELDSET)) {

				continue;
			}

			JSONArray rowsJSONArray = null;

			if (ddmFormField.getProperty("rows") instanceof String) {
				rowsJSONArray = _jsonFactory.createJSONArray(
					GetterUtil.getString(ddmFormField.getProperty("rows")));
			}
			else {
				rowsJSONArray = _jsonFactory.createJSONArray(
					_jsonFactory.looseSerializeDeep(
						ddmFormField.getProperty("rows")));
			}

			Map<String, DDMFormField> nestedDDMFormFieldsMap =
				ddmFormField.getNestedDDMFormFieldsMap();

			List<DDMFormField> sortedNestedDDMFormFields = new ArrayList<>();

			for (int i = 0; i < rowsJSONArray.length(); i++) {
				JSONObject rowJSONObject = rowsJSONArray.getJSONObject(i);

				if (rowJSONObject == null) {
					rowJSONObject = _jsonFactory.createJSONObject(
						(String)rowsJSONArray.get(i));
				}

				JSONArray columnsJSONArray = rowJSONObject.getJSONArray(
					"columns");

				for (int j = 0; j < columnsJSONArray.length(); j++) {
					JSONObject columnJSONObject =
						columnsJSONArray.getJSONObject(j);

					for (String fieldName :
							JSONUtil.toStringList(
								columnJSONObject.getJSONArray("fields"))) {

						DDMFormField nestedDDMFormField =
							nestedDDMFormFieldsMap.get(fieldName);

						if (StringUtil.equals(
								nestedDDMFormField.getType(),
								DDMFormFieldTypeConstants.FIELDSET)) {

							_sortNestedDDMFormFields(
								ListUtil.toList(nestedDDMFormField));
						}

						sortedNestedDDMFormFields.add(nestedDDMFormField);
					}
				}
			}

			ddmFormField.setNestedDDMFormFields(sortedNestedDDMFormFields);
		}
	}

	private DataDefinition _toDataDefinition(DDMStructure ddmStructure)
		throws Exception {

		return DataDefinitionUtil.toDataDefinition(
			_dataDefinitionContentTypeRegistry,
			_ddmFormFieldTypeServicesRegistry, ddmStructure,
			_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter);
	}

	private DataDefinitionValidationException
		_toDataDefinitionValidationException(
			DDMFormValidationException ddmFormValidationException) {

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustNotDuplicateFieldName) {

			DDMFormValidationException.MustNotDuplicateFieldName
				mustNotDuplicateFieldName =
					(DDMFormValidationException.MustNotDuplicateFieldName)
						ddmFormValidationException;

			return new DataDefinitionValidationException.
				MustNotDuplicateFieldName(
					mustNotDuplicateFieldName.getDuplicatedFieldNames());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustNotDuplicateFieldReference) {

			DDMFormValidationException.MustNotDuplicateFieldReference
				mustNotDuplicateFieldReference =
					(DDMFormValidationException.MustNotDuplicateFieldReference)
						ddmFormValidationException;

			return new DataDefinitionValidationException.
				MustNotDuplicateFieldReference(
					mustNotDuplicateFieldReference.
						getDuplicatedFieldReferences());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetAvailableLocales) {

			return new DataDefinitionValidationException.
				MustSetAvailableLocales();
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetDefaultLocale) {

			return new DataDefinitionValidationException.MustSetDefaultLocale();
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.
					MustSetDefaultLocaleAsAvailableLocale) {

			DDMFormValidationException.MustSetDefaultLocaleAsAvailableLocale
				mustSetDefaultLocaleAsAvailableLocale =
					(DDMFormValidationException.
						MustSetDefaultLocaleAsAvailableLocale)
							ddmFormValidationException;

			return new DataDefinitionValidationException.
				MustSetDefaultLocaleAsAvailableLocale(
					mustSetDefaultLocaleAsAvailableLocale.getDefaultLocale());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetFieldsForForm) {

			return new DataDefinitionValidationException.MustSetFields();
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetFieldType) {

			DDMFormValidationException.MustSetFieldType mustSetFieldType =
				(DDMFormValidationException.MustSetFieldType)
					ddmFormValidationException;

			return new DataDefinitionValidationException.MustSetFieldType(
				mustSetFieldType.getFieldName());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetOptionsForField) {

			DDMFormValidationException.MustSetOptionsForField
				mustSetOptionsForField =
					(DDMFormValidationException.MustSetOptionsForField)
						ddmFormValidationException;

			return new DataDefinitionValidationException.MustSetOptionsForField(
				mustSetOptionsForField.getFieldLabel(),
				mustSetOptionsForField.getFieldName());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.
					MustSetValidAvailableLocalesForProperty) {

			DDMFormValidationException.MustSetValidAvailableLocalesForProperty
				mustSetValidAvailableLocalesForProperty =
					(DDMFormValidationException.
						MustSetValidAvailableLocalesForProperty)
							ddmFormValidationException;

			return new DataDefinitionValidationException.
				MustSetValidAvailableLocalesForProperty(
					mustSetValidAvailableLocalesForProperty.getFieldName(),
					mustSetValidAvailableLocalesForProperty.getProperty());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetValidCharactersForFieldName) {

			DDMFormValidationException.MustSetValidCharactersForFieldName
				mustSetValidCharactersForFieldName =
					(DDMFormValidationException.
						MustSetValidCharactersForFieldName)
							ddmFormValidationException;

			return new DataDefinitionValidationException.
				MustSetValidCharactersForFieldName(
					mustSetValidCharactersForFieldName.getFieldName());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetValidCharactersForFieldType) {

			DDMFormValidationException.MustSetValidCharactersForFieldType
				mustSetValidCharactersForFieldType =
					(DDMFormValidationException.
						MustSetValidCharactersForFieldType)
							ddmFormValidationException;

			return new DataDefinitionValidationException.
				MustSetValidCharactersForFieldType(
					mustSetValidCharactersForFieldType.getFieldType());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.
					MustSetValidDefaultLocaleForProperty) {

			DDMFormValidationException.MustSetValidDefaultLocaleForProperty
				mustSetValidDefaultLocaleForProperty =
					(DDMFormValidationException.
						MustSetValidDefaultLocaleForProperty)
							ddmFormValidationException;

			return new DataDefinitionValidationException.
				MustSetValidDefaultLocaleForProperty(
					mustSetValidDefaultLocaleForProperty.getFieldName(),
					mustSetValidDefaultLocaleForProperty.getProperty());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetValidFormRuleExpression) {

			DDMFormValidationException.MustSetValidFormRuleExpression
				mustSetValidFormRuleExpression =
					(DDMFormValidationException.MustSetValidFormRuleExpression)
						ddmFormValidationException;

			return new DataDefinitionValidationException.
				MustSetValidRuleExpression(
					mustSetValidFormRuleExpression.getExpression(),
					mustSetValidFormRuleExpression.getMessage());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetValidIndexType) {

			DDMFormValidationException.MustSetValidIndexType
				mustSetValidIndexType =
					(DDMFormValidationException.MustSetValidIndexType)
						ddmFormValidationException;

			return new DataDefinitionValidationException.MustSetValidIndexType(
				mustSetValidIndexType.getFieldName());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetValidType) {

			DDMFormValidationException.MustSetValidType mustSetValidType =
				(DDMFormValidationException.MustSetValidType)
					ddmFormValidationException;

			return new DataDefinitionValidationException.MustSetValidType(
				mustSetValidType.getFieldType());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetValidValidationExpression) {

			DDMFormValidationException.MustSetValidValidationExpression
				mustSetValidValidationExpression =
					(DDMFormValidationException.
						MustSetValidValidationExpression)
							ddmFormValidationException;

			return new DataDefinitionValidationException.
				MustSetValidValidationExpression(
					mustSetValidValidationExpression.getFieldName(),
					mustSetValidValidationExpression.getExpression());
		}

		if (ddmFormValidationException instanceof
				DDMFormValidationException.MustSetValidVisibilityExpression) {

			DDMFormValidationException.MustSetValidVisibilityExpression
				mustSetValidVisibilityExpression =
					(DDMFormValidationException.
						MustSetValidVisibilityExpression)
							ddmFormValidationException;

			return new DataDefinitionValidationException.
				MustSetValidVisibilityExpression(
					mustSetValidVisibilityExpression.getFieldName(),
					mustSetValidVisibilityExpression.getExpression());
		}

		return new DataDefinitionValidationException(
			ddmFormValidationException.getCause());
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

	private void _uniquifyDataDefinitionFields(DataDefinition dataDefinition) {
		for (DataDefinitionField dataDefinitionField :
				dataDefinition.getDataDefinitionFields()) {

			_uniquifyDataDefinitionFields(dataDefinitionField);
		}

		_updateDataLayout(dataDefinition.getDefaultDataLayout());
	}

	private void _uniquifyDataDefinitionFields(
		DataDefinitionField dataDefinitionField) {

		dataDefinitionField.setName("CopyOf" + dataDefinitionField.getName());

		Map<String, Object> customProperties =
			dataDefinitionField.getCustomProperties();

		if (customProperties.containsKey("fieldReference")) {
			customProperties.put(
				"fieldReference",
				"CopyOf" + customProperties.get("fieldReference"));
		}

		if (!customProperties.containsKey("structureId") &&
			!Objects.equals(
				dataDefinitionField.getFieldType(),
				DDMFormFieldTypeConstants.FIELDSET)) {

			for (DataDefinitionField nestedDataDefinitionField :
					dataDefinitionField.getNestedDataDefinitionFields()) {

				_uniquifyDataDefinitionFields(nestedDataDefinitionField);
			}
		}
	}

	private DataDefinition _updateDataDefinition(
			DataDefinition dataDefinition, Long dataDefinitionId,
			DDMForm ddmForm)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			dataDefinitionId);

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(builder.build());

		return DataDefinitionUtil.toDataDefinition(
			_dataDefinitionContentTypeRegistry,
			_ddmFormFieldTypeServicesRegistry,
			_ddmStructureLocalService.updateStructure(
				PrincipalThreadLocal.getUserId(), dataDefinitionId,
				GetterUtil.getLong(
					ddmStructure.getParentStructureId(),
					DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID),
				dataDefinition.getDataDefinitionKey(),
				LocalizedValueUtil.toLocaleStringMap(dataDefinition.getName()),
				LocalizedValueUtil.toLocaleStringMap(
					dataDefinition.getDescription()),
				ddmFormSerializerSerializeResponse.getContent(),
				new ServiceContext()),
			_ddmStructureLayoutLocalService, _spiDDMFormRuleConverter);
	}

	private void _updateDataLayout(DataLayout dataLayout) {
		for (DataLayoutPage dataLayoutPage : dataLayout.getDataLayoutPages()) {
			_updateDataLayoutRows(dataLayoutPage.getDataLayoutRows());
		}
	}

	private void _updateDataLayoutRows(DataLayoutRow[] dataLayoutRows) {
		for (DataLayoutRow dataLayoutRow : dataLayoutRows) {
			for (DataLayoutColumn dataLayoutColumn :
					dataLayoutRow.getDataLayoutColumns()) {

				String[] dataLayoutColumnFieldNames =
					dataLayoutColumn.getFieldNames();

				for (int i = 0; i < dataLayoutColumnFieldNames.length; i++) {
					dataLayoutColumnFieldNames[i] =
						"CopyOf" + dataLayoutColumnFieldNames[i];
				}
			}
		}
	}

	private void _validate(
			DataDefinition dataDefinition,
			DataDefinitionContentType dataDefinitionContentType,
			DDMForm ddmForm)
		throws Exception {

		try {
			Map<String, Object> name = dataDefinition.getName();

			Locale defaultLocale = ddmForm.getDefaultLocale();

			if ((name == null) ||
				Validator.isNull(
					name.get(LocaleUtil.toLanguageId(defaultLocale)))) {

				throw new DataDefinitionValidationException.MustSetValidName(
					"Name is null for locale " +
						defaultLocale.getDisplayName());
			}

			_ddmFormValidator.validate(ddmForm);
		}
		catch (DDMFormValidationException ddmFormValidationException) {
			if ((ddmFormValidationException instanceof
					DDMFormValidationException.MustSetFieldsForForm) &&
				dataDefinitionContentType.allowEmptyDataDefinition()) {

				return;
			}

			throw _toDataDefinitionValidationException(
				ddmFormValidationException);
		}
		catch (DataDefinitionValidationException
					dataDefinitionValidationException) {

			throw dataDefinitionValidationException;
		}
		catch (Exception exception) {
			throw new DataDefinitionValidationException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataDefinitionResourceImpl.class);

	private static final EntityModel _entityModel =
		new DataDefinitionEntityModel();

	@Reference
	private DataDefinitionContentTypeRegistry
		_dataDefinitionContentTypeRegistry;

	@Reference
	private DataDefinitionModelResourcePermission
		_dataDefinitionModelResourcePermission;

	@Reference
	private DataLayoutResource.Factory _dataLayoutResourceFactory;

	@Reference
	private DataListViewResource.Factory _dataListViewResourceFactory;

	@Reference
	private DataRecordCollectionResource.Factory
		_dataRecordCollectionResourceFactory;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMFormFieldTypeServicesRegistry _ddmFormFieldTypeServicesRegistry;

	@Reference(target = "(ddm.form.layout.serializer.type=json)")
	private DDMFormLayoutSerializer _ddmFormLayoutSerializer;

	@Reference
	private DDMFormRuleDeserializer _ddmFormRuleDeserializer;

	@Reference(target = "(ddm.form.serializer.type=json)")
	private DDMFormSerializer _ddmFormSerializer;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private DDMFormValidator _ddmFormValidator;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private DDMPermissionSupport _ddmPermissionSupport;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

	@Reference
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Reference
	private EditorConfigurationFactory _editorConfigurationFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private SPIDDMFormRuleConverter _spiDDMFormRuleConverter;

}