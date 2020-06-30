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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.exception.CPDefinitionIgnoreSKUCombinationsException;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.internal.catalog.CPSkuImpl;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceOptionValueRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.commerce.product.util.DDMFormValuesUtil;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionValueRelPriorityComparator;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(immediate = true, service = CPInstanceHelper.class)
public class CPInstanceHelperImpl implements CPInstanceHelper {

	@Override
	public CPInstance fetchCPInstance(
			long cpDefinitionId, String serializedDDMFormValues)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (cpDefinition.isIgnoreSKUCombinations()) {
			return getDefaultCPInstance(cpDefinitionId);
		}

		if (JsonHelper.isEmpty(serializedDDMFormValues)) {
			throw new IllegalArgumentException("Required parameter missing");
		}

		return _fetchCPInstanceBySKUContributors(
			cpDefinitionId, serializedDDMFormValues);
	}

	@Override
	public List<CPDefinitionOptionValueRel> filterCPDefinitionOptionValueRels(
			long cpDefinitionOptionRelId,
			List<Long> skuCombinationCPDefinitionOptionValueRelIds)
		throws PortalException {

		List<CPInstanceOptionValueRel> cpInstanceOptionValueRels =
			_cpInstanceOptionValueRelLocalService.
				getCPDefinitionOptionRelCPInstanceOptionValueRels(
					cpDefinitionOptionRelId);

		List<CPDefinitionOptionValueRel> filtered = new ArrayList<>();

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				cpInstanceOptionValueRels) {

			if (!_hasCPInstanceCPDefinitionOptionValueRelIds(
					cpInstanceOptionValueRel.getCPInstanceId(),
					skuCombinationCPDefinitionOptionValueRelIds)) {

				continue;
			}

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelLocalService.
					getCPInstanceCPDefinitionOptionValueRel(
						cpDefinitionOptionRelId,
						cpInstanceOptionValueRel.getCPInstanceId());

			if (filtered.contains(cpDefinitionOptionValueRel)) {
				continue;
			}

			filtered.add(cpDefinitionOptionValueRel);
		}

		Collections.sort(filtered);

		return filtered;
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long commerceAccountId, long commerceChannelGroupId,
			long cpDefinitionId, String serializedDDMFormValues, int type)
		throws Exception {

		return getCPAttachmentFileEntries(
			commerceAccountId, commerceChannelGroupId, cpDefinitionId,
			serializedDDMFormValues, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long commerceAccountId, long commerceChannelGroupId,
			long cpDefinitionId, String serializedDDMFormValues, int type,
			int start, int end)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		_commerceProductViewPermission.check(
			permissionChecker, commerceAccountId, commerceChannelGroupId,
			cpDefinitionId);

		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
			cpDefinitionId, serializedDDMFormValues, type, start, end);
	}

	@Override
	public DDMForm getCPAttachmentFileEntryDDMForm(
			long cpDefinitionId, Locale locale)
		throws PortalException {

		return _getDDMForm(cpDefinitionId, locale, false, true, true, false);
	}

	@Override
	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			getCPDefinitionOptionRelsMap(long cpDefinitionId, String json)
		throws PortalException {

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelListMap = new HashMap<>();

		if (Validator.isNull(json)) {
			return cpDefinitionOptionRelListMap;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.
					fetchCPDefinitionOptionRelByKey(
						cpDefinitionId, jsonObject.getString("key"));

			if (cpDefinitionOptionRel == null) {
				continue;
			}

			JSONArray valueJSONArray = jsonObject.getJSONArray("value");

			for (int j = 0; j < valueJSONArray.length(); j++) {
				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelLocalService.
						fetchCPDefinitionOptionValueRel(
							cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
							valueJSONArray.getString(j));

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
	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			getCPInstanceCPDefinitionOptionRelsMap(long cpInstanceId)
		throws PortalException {

		List<CPInstanceOptionValueRel> cpInstanceCPInstanceOptionValueRels =
			_cpInstanceOptionValueRelLocalService.
				getCPInstanceCPInstanceOptionValueRels(cpInstanceId);

		if (cpInstanceCPInstanceOptionValueRels.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelsMap = new HashMap<>();

		for (CPInstanceOptionValueRel cpInstanceCPInstanceOptionValueRel :
				cpInstanceCPInstanceOptionValueRels) {

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
					cpInstanceCPInstanceOptionValueRel.
						getCPDefinitionOptionRelId());

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRelsMap.get(cpDefinitionOptionRel);

			if (cpDefinitionOptionValueRels == null) {
				cpDefinitionOptionValueRels = new ArrayList<>();

				cpDefinitionOptionRelsMap.put(
					cpDefinitionOptionRel, cpDefinitionOptionValueRels);
			}

			cpDefinitionOptionValueRels.add(
				_cpDefinitionOptionValueRelLocalService.
					getCPDefinitionOptionValueRel(
						cpInstanceCPInstanceOptionValueRel.
							getCPDefinitionOptionValueRelId()));
		}

		return cpDefinitionOptionRelsMap;
	}

	@Override
	public List<CPDefinitionOptionValueRel>
		getCPInstanceCPDefinitionOptionValueRels(
			long cpDefinitionId, long cpDefinitionOptionRelId) {

		List<CPInstanceOptionValueRel> cpDefinitionCPInstanceOptionValueRels =
			_cpInstanceOptionValueRelLocalService.
				getCPDefinitionCPInstanceOptionValueRels(cpDefinitionId);

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			new ArrayList<>();

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				cpDefinitionCPInstanceOptionValueRels) {

			if (cpDefinitionOptionRelId !=
					cpInstanceOptionValueRel.getCPDefinitionOptionRelId()) {

				continue;
			}

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelLocalService.
					fetchCPDefinitionOptionValueRel(
						cpInstanceOptionValueRel.
							getCPDefinitionOptionValueRelId());

			if ((cpDefinitionOptionValueRel != null) &&
				!cpDefinitionOptionValueRels.contains(
					cpDefinitionOptionValueRel)) {

				cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);
			}
		}

		Collections.sort(
			cpDefinitionOptionValueRels,
			new CPDefinitionOptionValueRelPriorityComparator(true));

		return cpDefinitionOptionValueRels;
	}

	@Override
	public List<CPInstanceOptionValueRel>
		getCPInstanceCPInstanceOptionValueRels(long cpInstanceId) {

		return _cpInstanceOptionValueRelLocalService.
			getCPInstanceCPInstanceOptionValueRels(cpInstanceId);
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
	public String getCPInstanceThumbnailSrc(long cpInstanceId)
		throws Exception {

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		Map<String, List<String>>
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys =
				_cpDefinitionOptionRelLocalService.
					getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
						cpInstanceId);

		JSONArray keyValuesJSONArray = DDMFormValuesUtil.toJSONArray(
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys);

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
				cpInstance.getCPDefinitionId(), keyValuesJSONArray.toString(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE, 0, 1);

		if (cpAttachmentFileEntries.isEmpty()) {
			CPDefinition cpDefinition = cpInstance.getCPDefinition();

			return cpDefinition.getDefaultImageThumbnailSrc();
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntries.get(0);

		return _commerceMediaResolver.getThumbnailUrl(
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	@Override
	public CPInstance getDefaultCPInstance(long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (!cpDefinition.isIgnoreSKUCombinations()) {
			throw new CPDefinitionIgnoreSKUCombinationsException(
				"Unable to get default CP instance if SKU combination present");
		}

		List<CPInstance> approvedCPInstances =
			_cpInstanceLocalService.getCPDefinitionApprovedCPInstances(
				cpDefinitionId);

		if (approvedCPInstances.isEmpty()) {
			return null;
		}

		if (approvedCPInstances.size() > 1) {
			throw new NoSuchCPInstanceException(
				"Unable to find default CP instance for CP definition ID " +
					cpDefinitionId);
		}

		return approvedCPInstances.get(0);
	}

	@Override
	public CPSku getDefaultCPSku(CPCatalogEntry cpCatalogEntry)
		throws Exception {

		if (!cpCatalogEntry.isIgnoreSKUCombinations()) {
			return null;
		}

		CPInstance cpInstance = getDefaultCPInstance(
			cpCatalogEntry.getCPDefinitionId());

		if (cpInstance == null) {
			return null;
		}

		return new CPSkuImpl(cpInstance);
	}

	@Override
	public List<KeyValuePair> getKeyValuePairs(
			long cpDefinitionId, String json, Locale locale)
		throws PortalException {

		List<KeyValuePair> values = new ArrayList<>();

		if (Validator.isNull(json)) {
			return values;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString("key");

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.
					fetchCPDefinitionOptionRelByKey(cpDefinitionId, key);

			if (cpDefinitionOptionRel == null) {
				continue;
			}

			JSONArray valueJSONArray = jsonObject.getJSONArray("value");

			for (int j = 0; j < valueJSONArray.length(); j++) {
				String value = valueJSONArray.getString(j);

				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelLocalService.
						fetchCPDefinitionOptionValueRel(
							cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
							value);

				if (cpDefinitionOptionValueRel != null) {
					value = cpDefinitionOptionValueRel.getName(locale);
				}
				else {
					value = valueJSONArray.getString(j);
				}

				KeyValuePair keyValuePair = new KeyValuePair();

				keyValuePair.setKey(cpDefinitionOptionRel.getName(locale));
				keyValuePair.setValue(value);

				values.add(keyValuePair);
			}
		}

		return values;
	}

	@Override
	public DDMForm getPublicStoreDDMForm(
			long groupId, long commerceAccountId, long cpDefinitionId,
			Locale locale, boolean ignoreSKUCombinations,
			boolean skuContributor)
		throws PortalException {

		DDMForm ddmForm = _getDDMForm(
			cpDefinitionId, locale, ignoreSKUCombinations, skuContributor,
			false, true);

		if (!ignoreSKUCombinations) {
			ddmForm.addDDMFormRule(
				createDDMFormRule(
					ddmForm, groupId, commerceAccountId, cpDefinitionId));
		}

		return ddmForm;
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

		CommerceAccount commerceAccount =
			_commerceAccountHelper.getCurrentCommerceAccount(
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(
						_portal.getScopeGroupId(renderRequest)),
				_portal.getHttpServletRequest(renderRequest));

		long commerceAccountId = 0;

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		DDMForm ddmForm = getPublicStoreDDMForm(
			_portal.getScopeGroupId(renderRequest), commerceAccountId,
			cpDefinitionId, locale, ignoreSKUCombinations, skuContributor);

		return _render(
			cpDefinitionId, locale, ddmForm, json, renderRequest,
			renderResponse);
	}

	protected DDMFormRule createDDMFormRule(
		DDMForm ddmForm, long groupId, long commerceAccountId,
		long cpDefinitionId) {

		String action = createDDMFormRuleAction(
			ddmForm, groupId, commerceAccountId, cpDefinitionId);

		return new DDMFormRule("TRUE", action);
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
		DDMForm ddmForm, long groupId, long commerceAccountId,
		long cpDefinitionId) {

		String callFunctionStatement =
			"call('getCPInstanceOptionsValues', concat(%s), '%s')";

		return String.format(
			callFunctionStatement,
			createDDMFormRuleInputMapping(
				ddmForm, groupId, commerceAccountId, cpDefinitionId),
			createDDMFormRuleOutputMapping(ddmForm));
	}

	protected String createDDMFormRuleInputMapping(
		DDMForm ddmForm, long groupId, long commerceAccountId,
		long cpDefinitionId) {

		// The input information will be transformed in parameter request of
		// DDMDataProviderRequest class and it'll be accessible in the data
		// provider implementation.

		String inputMappingStatement = "'%s=', getValue('%s')";
		String delimiter = ", ';',";

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		Stream<String> inputMappingStatementStream = stream.map(
			field -> String.format(
				inputMappingStatement, field.getName(), field.getName()));

		inputMappingStatementStream = Stream.concat(
			Stream.of(
				String.format(
					"'cpDefinitionId=%s'", String.valueOf(cpDefinitionId))),
			inputMappingStatementStream);

		inputMappingStatementStream = Stream.concat(
			Stream.of(String.format("'groupId=%s'", String.valueOf(groupId))),
			inputMappingStatementStream);

		inputMappingStatementStream = Stream.concat(
			Stream.of(
				String.format(
					"'commerceAccountId=%s'",
					String.valueOf(commerceAccountId))),
			inputMappingStatementStream);

		return inputMappingStatementStream.collect(
			Collectors.joining(delimiter));
	}

	protected String createDDMFormRuleOutputMapping(DDMForm ddmForm) {
		String outputMappingStatement = "%s=%s";

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		Stream<String> stringStream = stream.map(
			field -> String.format(
				outputMappingStatement, field.getName(), field.getName()));

		return stringStream.collect(Collectors.joining(StringPool.SEMICOLON));
	}

	private CPInstance _fetchCPInstanceBySKUContributors(
			long cpDefinitionId, String json)
		throws PortalException {

		int skuContributorCPDefinitionOptionRelsCount =
			_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(
				cpDefinitionId, true);

		if (skuContributorCPDefinitionOptionRelsCount == 0) {
			return null;
		}

		Map<Long, List<Long>>
			cpDefinitionOptionRelCPDefinitionOptionValueRelIds =
				_cpDefinitionOptionRelLocalService.
					getCPDefinitionOptionRelCPDefinitionOptionValueRelIds(
						cpDefinitionId, true, json);

		if (cpDefinitionOptionRelCPDefinitionOptionValueRelIds.isEmpty() ||
			(skuContributorCPDefinitionOptionRelsCount !=
				cpDefinitionOptionRelCPDefinitionOptionValueRelIds.size())) {

			return null;
		}

		List<CPInstanceOptionValueRel> cpDefinitionCPInstanceOptionValueRels =
			_cpInstanceOptionValueRelLocalService.
				getCPDefinitionCPInstanceOptionValueRels(cpDefinitionId);

		Map<Long, Integer> cpInstanceCPInstanceOptionValueHits =
			new HashMap<>();

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				cpDefinitionCPInstanceOptionValueRels) {

			if (!cpDefinitionOptionRelCPDefinitionOptionValueRelIds.containsKey(
					cpInstanceOptionValueRel.getCPDefinitionOptionRelId())) {

				continue;
			}

			List<Long> cpDefinitionOptionValueIds =
				cpDefinitionOptionRelCPDefinitionOptionValueRelIds.get(
					cpInstanceOptionValueRel.getCPDefinitionOptionRelId());

			if (!cpDefinitionOptionValueIds.contains(
					cpInstanceOptionValueRel.
						getCPDefinitionOptionValueRelId())) {

				continue;
			}

			if (cpInstanceCPInstanceOptionValueHits.containsKey(
					cpInstanceOptionValueRel.getCPInstanceId())) {

				cpInstanceCPInstanceOptionValueHits.put(
					cpInstanceOptionValueRel.getCPInstanceId(),
					cpInstanceCPInstanceOptionValueHits.get(
						cpInstanceOptionValueRel.getCPInstanceId()) + 1);

				continue;
			}

			cpInstanceCPInstanceOptionValueHits.put(
				cpInstanceOptionValueRel.getCPInstanceId(), 1);
		}

		if (cpInstanceCPInstanceOptionValueHits.isEmpty()) {
			return null;
		}

		long cpInstanceId = _getTopId(cpInstanceCPInstanceOptionValueHits);

		if (skuContributorCPDefinitionOptionRelsCount !=
				cpInstanceCPInstanceOptionValueHits.get(cpInstanceId)) {

			return null;
		}

		return _cpInstanceLocalService.getCPInstance(cpInstanceId);
	}

	private DDMForm _getDDMForm(
			long cpDefinitionId, Locale locale, boolean ignoreSKUCombinations,
			boolean skuContributor, boolean optional, boolean publicStore)
		throws PortalException {

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

		DDMForm ddmForm = new DDMForm();

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			if (Validator.isNull(
					cpDefinitionOptionRel.getDDMFormFieldTypeName())) {

				continue;
			}

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels = null;

			if (cpDefinitionOptionRel.isSkuContributor() && publicStore) {
				cpDefinitionOptionValueRels =
					getCPInstanceCPDefinitionOptionValueRels(
						cpDefinitionId,
						cpDefinitionOptionRel.getCPDefinitionOptionRelId());
			}
			else {
				cpDefinitionOptionValueRels =
					cpDefinitionOptionRel.getCPDefinitionOptionValueRels();
			}

			DDMFormField ddmFormField = _getDDMFormField(
				cpDefinitionOptionRel, cpDefinitionOptionValueRels, locale);

			ddmFormField.setRequired(
				_isDDMFormRequired(
					cpDefinitionOptionRel, ignoreSKUCombinations, optional,
					publicStore));

			ddmForm.addDDMFormField(ddmFormField);
		}

		ddmForm.addAvailableLocale(locale);
		ddmForm.setDefaultLocale(locale);

		return ddmForm;
	}

	private DDMFormField _getDDMFormField(
		CPDefinitionOptionRel cpDefinitionOptionRel,
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
		Locale locale) {

		DDMFormField ddmFormField = new DDMFormField(
			cpDefinitionOptionRel.getKey(),
			cpDefinitionOptionRel.getDDMFormFieldTypeName());

		LocalizedValue ddmFormFieldLabelLocalizedValue = new LocalizedValue(
			locale);

		ddmFormFieldLabelLocalizedValue.addString(
			locale, cpDefinitionOptionRel.getName(locale));

		ddmFormField.setLabel(ddmFormFieldLabelLocalizedValue);

		if (cpDefinitionOptionValueRels.isEmpty()) {
			return ddmFormField;
		}

		DDMFormFieldOptions ddmFormFieldOptions = _getDDMFormFieldOptions(
			cpDefinitionOptionValueRels, locale);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		if (cpDefinitionOptionRel.isSkuContributor()) {
			ddmFormField.setPredefinedValue(
				_getDDMFormFieldPredefinedValue(ddmFormFieldOptions));
		}

		return ddmFormField;
	}

	private DDMFormFieldOptions _getDDMFormFieldOptions(
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
		Locale locale) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			ddmFormFieldOptions.addOptionLabel(
				cpDefinitionOptionValueRel.getKey(), locale,
				cpDefinitionOptionValueRel.getName(locale));
		}

		return ddmFormFieldOptions;
	}

	private LocalizedValue _getDDMFormFieldPredefinedValue(
		DDMFormFieldOptions ddmFormFieldOptions) {

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		if (options.isEmpty()) {
			return new LocalizedValue(ddmFormFieldOptions.getDefaultLocale());
		}

		for (Map.Entry<String, LocalizedValue> entry : options.entrySet()) {
			LocalizedValue localizedValue = new LocalizedValue();

			LocalizedValue curLocalizedValue = entry.getValue();

			localizedValue.addString(
				curLocalizedValue.getDefaultLocale(), entry.getKey());

			return localizedValue;
		}

		throw new IllegalArgumentException(
			"Provided DDM field options miss valid field value");
	}

	private long _getTopId(Map<Long, Integer> idIdHits) {
		long topId = 0;
		int topIdHits = 0;

		for (Map.Entry<Long, Integer> idIdHitsEntry : idIdHits.entrySet()) {
			if (topIdHits > idIdHitsEntry.getValue()) {
				continue;
			}

			topId = idIdHitsEntry.getKey();
			topIdHits = idIdHitsEntry.getValue();
		}

		return topId;
	}

	private boolean _hasCPInstanceCPDefinitionOptionValueRelIds(
		long cpInstanceId,
		List<Long> skuCombinationCPDefinitionOptionValueRelIds) {

		for (Long skuCombinationCPDefinitionOptionValueRelId :
				skuCombinationCPDefinitionOptionValueRelIds) {

			if (!_cpInstanceOptionValueRelLocalService.
					hasCPInstanceCPDefinitionOptionValueRel(
						skuCombinationCPDefinitionOptionValueRelId,
						cpInstanceId)) {

				return false;
			}
		}

		return true;
	}

	private boolean _isDDMFormRequired(
		CPDefinitionOptionRel cpDefinitionOptionRel,
		boolean ignoreSKUCombinations, boolean optional, boolean publicStore) {

		if (optional) {
			return false;
		}

		Map<String, Object> properties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				cpDefinitionOptionRel.getDDMFormFieldTypeName());

		String fieldTypeDataDomain = MapUtil.getString(
			properties, "ddm.form.field.type.data.domain");

		if (Validator.isNotNull(fieldTypeDataDomain) &&
			fieldTypeDataDomain.equals("list")) {

			int cpDefinitionOptionValueRelsCount =
				_cpDefinitionOptionValueRelLocalService.
					getCPDefinitionOptionValueRelsCount(
						cpDefinitionOptionRel.getCPDefinitionOptionRelId());

			if (cpDefinitionOptionValueRelsCount == 0) {
				return false;
			}
		}

		if (ignoreSKUCombinations) {
			return cpDefinitionOptionRel.isRequired();
		}

		if (cpDefinitionOptionRel.isSkuContributor() ||
			(publicStore && cpDefinitionOptionRel.isRequired())) {

			return true;
		}

		return false;
	}

	private String _render(
			long cpDefinitionId, Locale locale, DDMForm ddmForm, String json,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		if (ddmForm == null) {
			return StringPool.BLANK;
		}

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId(
			"ProductOptions" + String.valueOf(cpDefinitionId));
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
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPInstanceOptionValueRelLocalService
		_cpInstanceOptionValueRelLocalService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private DDMFormValuesHelper _ddmFormValuesHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}