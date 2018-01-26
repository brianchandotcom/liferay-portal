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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.search.CPAttachmentFileEntryIndexer;
import com.liferay.commerce.product.search.CPInstanceIndexer;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CPInstanceHelperImpl implements CPInstanceHelper {

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long cpDefinitionId, String serializedDDMFormValues, int type)
		throws Exception {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries = new ArrayList<>();

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionId);

		long cpDefinitionClassNameId = _portal.getClassNameId(
			CPDefinition.class);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			serializedDDMFormValues);

		Indexer<CPAttachmentFileEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CPAttachmentFileEntry.class);

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(
			CPAttachmentFileEntryIndexer.FIELD_RELATED_ENTITY_CLASS_NAME_ID,
			cpDefinitionClassNameId);
		attributes.put(
			CPAttachmentFileEntryIndexer.FIELD_RELATED_ENTITY_CLASS_PK,
			cpDefinitionId);
		attributes.put(Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		attributes.put(Field.TYPE, type);

		List<String> optionsKeys = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString("key");

			String fieldName = "ATTRIBUTE_" + key + "_VALUES_IDS";

			optionsKeys.add(fieldName);

			JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray(
				jsonObject.getString("value"));

			String[] values = new String[valuesJSONArray.length()];

			for (int j = 0; j < valuesJSONArray.length(); j++) {
				values[j] = valuesJSONArray.getString(j);
			}

			attributes.put(fieldName, values);
		}

		attributes.put("OPTIONS", ArrayUtil.toStringArray(optionsKeys));

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(cpDefinition.getCompanyId());
		searchContext.setGroupIds(new long[] {cpDefinition.getGroupId()});

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Sort prioitySort = SortFactoryUtil.create(Field.PRIORITY, true);

		searchContext.setSorts(prioitySort);

		queryConfig.addSelectedFieldNames(Field.ENTRY_CLASS_PK);

		Hits hits = indexer.search(searchContext);

		Document[] documents = hits.getDocs();

		for (Document document : documents) {
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			cpAttachmentFileEntries.add(
				_cpAttachmentFileEntryService.getCPAttachmentFileEntry(
					classPK));
		}

		return cpAttachmentFileEntries;
	}

	@Override
	public DDMForm getCPAttachmentFileEntryDDMForm(
			long cpDefinitionId, Locale locale)
		throws PortalException {

		return _getDDMForm(cpDefinitionId, locale, false, true, true, false);
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRel(
			long cpDefinitionId, String optionFieldName,
			Map<String, String> optionMap)
		throws Exception {

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionId);

		Indexer<CPInstance> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPInstance.class);

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(
			CPInstanceIndexer.FIELD_CP_DEFINITION_ID, cpDefinitionId);
		attributes.put(Field.STATUS, WorkflowConstants.STATUS_APPROVED);

		List<String> optionsKeys = new ArrayList<>();

		for (Map.Entry<String, String> optionEntry : optionMap.entrySet()) {
			String fieldName =
				"ATTRIBUTE_" + optionEntry.getKey() + "_VALUE_ID";

			optionsKeys.add(fieldName);

			attributes.put(fieldName, optionEntry.getValue());
		}

		attributes.put("OPTIONS", ArrayUtil.toStringArray(optionsKeys));

		searchContext.setAttributes(attributes);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		searchContext.setCompanyId(cpDefinition.getCompanyId());
		searchContext.setGroupIds(new long[] {cpDefinition.getGroupId()});

		Hits hits = indexer.search(searchContext, optionFieldName);

		Document[] documents = hits.getDocs();

		List<Long> cpDefinitionOptionValueRelIsList = new ArrayList<>();

		for (Document document : documents) {
			long classPK = GetterUtil.getLong(document.get(optionFieldName));

			if (classPK > 0) {
				cpDefinitionOptionValueRelIsList.add(classPK);
			}
		}

		long[] cpDefinitionOptionValueRelIds = ArrayUtil.toLongArray(
			cpDefinitionOptionValueRelIsList);

		return _cpDefinitionOptionValueRelLocalService.
			getCPDefinitionOptionValueRels(cpDefinitionOptionValueRelIds);
	}

	@Override
	public CPInstance getCPInstance(
			long cpDefinitionId, String serializedDDMFormValues)
		throws Exception {

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionId);

		if (Validator.isNull(serializedDDMFormValues)) {
			serializedDDMFormValues = "[]";
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			serializedDDMFormValues);

		Indexer<CPInstance> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPInstance.class);

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(
			CPInstanceIndexer.FIELD_CP_DEFINITION_ID, cpDefinitionId);
		attributes.put(Field.STATUS, WorkflowConstants.STATUS_APPROVED);

		List<String> optionsKeys = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString("key");

			long cpDefinitionOptionRelId = GetterUtil.getLong(key);

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

			if (!cpDefinitionOptionRel.isSkuContributor()) {
				continue;
			}

			String fieldName = "ATTRIBUTE_" + key + "_VALUE_ID";

			JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray(
				jsonObject.getString("value"));

			if (valuesJSONArray.length() == 0) {
				continue;
			}

			String value = valuesJSONArray.getString(0);

			optionsKeys.add(fieldName);
			attributes.put(fieldName, value);
		}

		attributes.put("OPTIONS", ArrayUtil.toStringArray(optionsKeys));

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(cpDefinition.getCompanyId());
		searchContext.setGroupIds(new long[] {cpDefinition.getGroupId()});

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Hits hits = indexer.search(searchContext);

		Document[] documents = hits.getDocs();

		if (documents.length != 1) {
			return null;
		}

		Document document = documents[0];

		long cpInstanceId = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		return _cpInstanceService.fetchCPInstance(cpInstanceId);
	}

	@Override
	public DDMForm getCPInstanceDDMForm(
			long cpDefinitionId, Locale locale, boolean ignoreSKUCombinations,
			boolean skuContributor)
		throws PortalException {

		return _getDDMForm(
			cpDefinitionId, locale, ignoreSKUCombinations, skuContributor,
			false, false);
	}

	@Override
	public DDMForm getPublicStoreDDMForm(
			long cpDefinitionId, Locale locale, boolean ignoreSKUCombinations,
			boolean skuContributor)
		throws PortalException {

		return _getDDMForm(
			cpDefinitionId, locale, ignoreSKUCombinations, skuContributor,
			false, true);
	}

	@Override
	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
		parseCPInstanceJSONString(String json) throws PortalException {

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelListMap = new HashMap<>();

		if (Validator.isNull(json)) {
			return cpDefinitionOptionRelListMap;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long cpDefinitionOptionRelId = jsonObject.getLong("key");
			JSONArray valueJSONArray = jsonObject.getJSONArray("value");

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

			if (cpDefinitionOptionRel == null) {
				continue;
			}

			for (int j = 0; j < valueJSONArray.length(); j++) {
				long cpDefinitionOptionValueRelId = GetterUtil.getLong(
					valueJSONArray.getString(j));

				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelLocalService.
						fetchCPDefinitionOptionValueRel(
							cpDefinitionOptionValueRelId);

				if (cpDefinitionOptionValueRel == null) {
					continue;
				}

				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
					cpDefinitionOptionRelListMap.get(cpDefinitionOptionRel);

				if (cpDefinitionOptionValueRels == null) {
					cpDefinitionOptionValueRels = new ArrayList<>();

					cpDefinitionOptionRelListMap.put(
						cpDefinitionOptionRel, cpDefinitionOptionValueRels);
				}

				cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);
			}
		}

		return cpDefinitionOptionRelListMap;
	}

	@Override
	public List<KeyValuePair> parseJSONString(String json, Locale locale)
		throws PortalException {

		List<KeyValuePair> values = new ArrayList<>();

		if (Validator.isNull(json)) {
			return values;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long cpDefinitionOptionRelId = jsonObject.getLong("key");
			JSONArray valueJSONArray = jsonObject.getJSONArray("value");

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

			if (cpDefinitionOptionRel == null) {
				continue;
			}

			for (int j = 0; j < valueJSONArray.length(); j++) {
				String value = StringPool.BLANK;

				long cpDefinitionOptionValueRelId = GetterUtil.getLong(
					valueJSONArray.getString(j));

				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelLocalService.
						fetchCPDefinitionOptionValueRel(
							cpDefinitionOptionValueRelId);

				if (cpDefinitionOptionValueRel != null) {
					value = cpDefinitionOptionValueRel.getTitle(locale);
				}
				else {
					value = valueJSONArray.getString(j);
				}

				KeyValuePair keyValuePair = new KeyValuePair();

				keyValuePair.setKey(cpDefinitionOptionRel.getTitle(locale));
				keyValuePair.setValue(value);

				values.add(keyValuePair);
			}
		}

		return values;
	}

	@Override
	public String renderCPAttachmentFileEntryOptions(
			long cpDefinitionId, String json, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException {

		Locale locale = _portal.getLocale(renderRequest);

		DDMForm ddmForm = getCPAttachmentFileEntryDDMForm(
			cpDefinitionId, locale);

		return _render(
			cpDefinitionId, locale, ddmForm, json, renderRequest,
			renderResponse);
	}

	@Override
	public String renderCPInstanceOptions(
			long cpDefinitionId, String json, boolean ignoreSKUCombinations,
			boolean skuContributor, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException {

		Locale locale = _portal.getLocale(renderRequest);

		DDMForm ddmForm = getCPInstanceDDMForm(
			cpDefinitionId, locale, ignoreSKUCombinations, skuContributor);

		return _render(
			cpDefinitionId, locale, ddmForm, json, renderRequest,
			renderResponse);
	}

	@Override
	public String renderPublicStoreOptions(
			long cpDefinitionId, String json, boolean ignoreSKUCombinations,
			boolean skuContributor, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException {

		Locale locale = _portal.getLocale(renderRequest);

		DDMForm ddmForm = getPublicStoreDDMForm(
			cpDefinitionId, locale, ignoreSKUCombinations, skuContributor);

		return _render(
			cpDefinitionId, locale, ddmForm, json, renderRequest,
			renderResponse);
	}

	protected DDMFormRule createDDMFormRule(
		DDMForm ddmForm, long cpDefinitionId) {

		String action = createDDMFormRuleAction(ddmForm, cpDefinitionId);
		String condition = createDDMFormRuleCondition(ddmForm);

		return new DDMFormRule(condition, action);
	}

	/**
	 * Create a DDM form rule action as a call function, e.g.
	 * <pre>
	 * call(
	 * 	'getCPInstanceOptionsValues',
	 * 	concat(
	 * 		'cpDefinitionId=56698', ';', '56703=', getValue('56703'), ';',
	 * 		'56706=', getValue('56706')),
	 * 	'56703=color;56706=size')
	 * </pre>
	 */
	protected String createDDMFormRuleAction(
		DDMForm ddmForm, long cpDefinitionId) {

		String callFunctionStatement =
			"call('getCPInstanceOptionsValues', concat(%s), '%s')";

		return String.format(
			callFunctionStatement,
			createDDMFormRuleInputMapping(ddmForm, cpDefinitionId),
			createDDMFormRuleOutputMapping(ddmForm));
	}

	/**
	 * Create a DDM form rule condition. The rule action will contain a
	 * 'nonEmpty' statement for each field using 'OR` operator, e.g.
	 * <pre>
	 * 	not(isEmpty(getValue('{sizeFieldName}'))) OR
	 * 	not(isEmpty(getValue('{colorFieldName}')))
	 * </pre>
	 */
	protected String createDDMFormRuleCondition(DDMForm ddmForm) {
		String notEmptyStatement = "not(isEmpty(getValue('%s')))";

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		Stream<String> notEmptyStatementStream = stream.map(
			field -> {
				return String.format(notEmptyStatement, field.getName());
			});

		Collector<CharSequence, ?, String> collector = Collectors.joining(
			" OR ");

		return notEmptyStatementStream.collect(collector);
	}

	protected String createDDMFormRuleInputMapping(
		DDMForm ddmForm, long cpDefinitionId) {

		// The input information will be transformed in parameter request of
		// DDMDataProviderRequest class and it'll be accessible in the data
		// provider implementation.

		String inputMappingStatement = "'%s=', getValue('%s')";
		String delimiter = ", ';',";

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		Stream<String> inputMappingStatementStream = stream.map(
			field -> {
				return String.format(
					inputMappingStatement, field.getName(), field.getName());
			});

		inputMappingStatementStream = Stream.concat(
			Stream.of(
				String.format(
					"'cpDefinitionId=%s'", String.valueOf(cpDefinitionId))),
			inputMappingStatementStream);

		return inputMappingStatementStream.collect(
			Collectors.joining(delimiter));
	}

	protected String createDDMFormRuleOutputMapping(DDMForm ddmForm) {
		String outputMappingStatement = "%s=%s";

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		Stream<String> outputMappingStatementStream = stream.map(
			field -> {
				return String.format(
					outputMappingStatement, field.getName(), field.getName());
			});

		return outputMappingStatementStream.collect(
			Collectors.joining(StringPool.SEMICOLON));
	}

	private DDMForm _getDDMForm(
			long cpDefinitionId, Locale locale, boolean ignoreSKUCombinations,
			boolean skuContributor, boolean optional, boolean publicStore)
		throws PortalException {

		DDMForm ddmForm = new DDMForm();

		List<CPDefinitionOptionRel> cpDefinitionOptionRels;

		if (skuContributor) {
			cpDefinitionOptionRels =
				_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
					cpDefinitionId, true);
		}
		else {
			cpDefinitionOptionRels =
				_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
					cpDefinitionId);
		}

		if (cpDefinitionOptionRels.isEmpty()) {
			return null;
		}

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

			if (Validator.isNull(
					cpDefinitionOptionRel.getDDMFormFieldTypeName())) {

				continue;
			}

			DDMFormField ddmFormField = new DDMFormField(
				String.valueOf(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId()),
				cpDefinitionOptionRel.getDDMFormFieldTypeName());

			if (!cpDefinitionOptionValueRels.isEmpty()) {
				DDMFormFieldOptions ddmFormFieldOptions =
					new DDMFormFieldOptions();

				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
						cpDefinitionOptionValueRels) {

					String optionLabel = String.valueOf(
						cpDefinitionOptionValueRel.
							getCPDefinitionOptionValueRelId());

					ddmFormFieldOptions.addOptionLabel(
						optionLabel, locale,
						cpDefinitionOptionValueRel.getTitle(locale));
				}

				ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
			}

			LocalizedValue localizedValue = new LocalizedValue(locale);

			localizedValue.addString(
				locale, cpDefinitionOptionRel.getTitle(locale));

			ddmFormField.setLabel(localizedValue);

			boolean required = _isDDMFormRequired(
				cpDefinitionOptionRel, ignoreSKUCombinations, optional,
				publicStore);

			ddmFormField.setRequired(required);

			ddmForm.addDDMFormField(ddmFormField);
		}

		if (!ignoreSKUCombinations) {
			ddmForm.addDDMFormRule(createDDMFormRule(ddmForm, cpDefinitionId));
		}

		ddmForm.addAvailableLocale(locale);
		ddmForm.setDefaultLocale(locale);

		return ddmForm;
	}

	private boolean _isDDMFormRequired(
		CPDefinitionOptionRel cpDefinitionOptionRel,
		boolean ignoreSKUCombinations, boolean optional, boolean publicStore) {

		if (optional) {
			return false;
		}

		if (ignoreSKUCombinations) {
			return cpDefinitionOptionRel.getRequired();
		}

		if (publicStore) {
			if (cpDefinitionOptionRel.getRequired() ||
				cpDefinitionOptionRel.getSkuContributor()) {

				return true;
			}
		}
		else {
			return cpDefinitionOptionRel.getSkuContributor();
		}

		return false;
	}

	private String _render(
			long cpDefinitionId, Locale locale, DDMForm ddmForm, String json,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		if (ddmForm == null) {
			return StringPool.BLANK;
		}

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId(String.valueOf(cpDefinitionId));
		ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(httpServletResponse);
		ddmFormRenderingContext.setLocale(locale);
		ddmFormRenderingContext.setPortletNamespace(
			renderResponse.getNamespace());
		ddmFormRenderingContext.setShowRequiredFieldsWarning(false);

		if (Validator.isNotNull(json)) {
			DDMFormValues ddmFormValues = _ddmFormValuesHelper.deserialize(
				ddmForm, json, locale);

			if (ddmFormValues != null) {
				ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
			}
		}

		return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
	}

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private DDMFormValuesHelper _ddmFormValuesHelper;

	@Reference
	private Portal _portal;

}