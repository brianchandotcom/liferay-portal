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

package com.liferay.commerce.product.ddm.internal;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.ddm.DDMHelper;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceOptionValueRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.commerce.product.util.JsonHelper;
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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponseFactory;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(enabled = false, immediate = true, service = DDMHelper.class)
public class DDMHelperImpl implements DDMHelper {

	@Override
	public DDMForm getCPAttachmentFileEntryDDMForm(
		Locale locale,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels) {

		return _getDDMForm(
			locale, false, true, false,
			cpDefinitionOptionRelCPDefinitionOptionValueRels);
	}

	@Override
	public DDMForm getCPInstanceDDMForm(
		Locale locale, boolean ignoreSKUCombinations,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels) {

		return _getDDMForm(
			locale, ignoreSKUCombinations, false, false,
			cpDefinitionOptionRelCPDefinitionOptionValueRels);
	}

	@Override
	public DDMForm getPublicStoreDDMForm(
		long groupId, long commerceAccountId, long cpDefinitionId,
		Locale locale, boolean ignoreSKUCombinations,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels,
		long companyId, long userId) {

		DDMForm ddmForm = _getDDMForm(
			locale, ignoreSKUCombinations, false, true,
			cpDefinitionOptionRelCPDefinitionOptionValueRels);

		if (!ignoreSKUCombinations) {
			ddmForm.addDDMFormRule(
				_createDDMFormRule(
					ddmForm, groupId, commerceAccountId, cpDefinitionId,
					companyId, userId, locale));
		}

		return ddmForm;
	}

	public String renderCPAttachmentFileEntryOptions(
			long cpDefinitionId, String json, PageContext pageContext,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException {

		Locale locale = _portal.getLocale(renderRequest);

		DDMForm ddmForm = getCPAttachmentFileEntryDDMForm(
			locale, cpDefinitionOptionRelCPDefinitionOptionValueRels);

		return _render(
			cpDefinitionId, locale, ddmForm, json, pageContext, renderRequest,
			renderResponse);
	}

	@Override
	public String renderCPAttachmentFileEntryOptions(
			long cpDefinitionId, String json, RenderRequest renderRequest,
			RenderResponse renderResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String renderCPInstanceOptions(
			long cpDefinitionId, String json, boolean ignoreSKUCombinations,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException {

		Locale locale = _portal.getLocale(renderRequest);

		DDMForm ddmForm = getCPInstanceDDMForm(
			locale, ignoreSKUCombinations,
			cpDefinitionOptionRelCPDefinitionOptionValueRels);

		return _render(
			cpDefinitionId, locale, ddmForm, json, renderRequest,
			renderResponse);
	}

	@Override
	public String renderPublicStoreOptions(
			long cpDefinitionId, String json, boolean ignoreSKUCombinations,
			RenderRequest renderRequest, RenderResponse renderResponse,
			Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelCPDefinitionOptionValueRels)
		throws PortalException {

		Locale locale = _portal.getLocale(renderRequest);

		long commerceAccountId = 0;

		CommerceAccount commerceAccount =
			_commerceAccountHelper.getCurrentCommerceAccount(
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(
						_portal.getScopeGroupId(renderRequest)),
				_portal.getHttpServletRequest(renderRequest));

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMForm ddmForm = getPublicStoreDDMForm(
			_portal.getScopeGroupId(renderRequest), commerceAccountId,
			cpDefinitionId, locale, ignoreSKUCombinations,
			cpDefinitionOptionRelCPDefinitionOptionValueRels,
			themeDisplay.getCompanyId(), themeDisplay.getUserId());

		return _render(
			cpDefinitionId, locale, ddmForm, json, renderRequest,
			renderResponse);
	}

	private DDMFormRule _createDDMFormRule(
		DDMForm ddmForm, long groupId, long commerceAccountId,
		long cpDefinitionId, long companyId, long userId, Locale locale) {

		String action = _createDDMFormRuleAction(
			ddmForm, groupId, commerceAccountId, cpDefinitionId, companyId,
			userId, locale);

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
	private String _createDDMFormRuleAction(
		DDMForm ddmForm, long groupId, long commerceAccountId,
		long cpDefinitionId, long companyId, long userId, Locale locale) {

		String callFunctionStatement =
			"call('getCPInstanceOptionsValues', concat(%s), '%s')";

		return String.format(
			callFunctionStatement,
			_createDDMFormRuleInputMapping(
				ddmForm, groupId, commerceAccountId, cpDefinitionId, companyId,
				userId, locale),
			_createDDMFormRuleOutputMapping(ddmForm));
	}

	private String _createDDMFormRuleInputMapping(
		DDMForm ddmForm, long groupId, long commerceAccountId,
		long cpDefinitionId, long companyId, long userId, Locale locale) {

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
				String.format("'companyId=%s'", String.valueOf(companyId))),
			inputMappingStatementStream);

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

		inputMappingStatementStream = Stream.concat(
			Stream.of(String.format("'userId=%s'", String.valueOf(userId))),
			inputMappingStatementStream);

		inputMappingStatementStream = Stream.concat(
			Stream.of(
				String.format("'locale=%s'", LocaleUtil.toLanguageId(locale))),
			inputMappingStatementStream);

		return inputMappingStatementStream.collect(
			Collectors.joining(delimiter));
	}

	private String _createDDMFormRuleOutputMapping(DDMForm ddmForm) {
		String outputMappingStatement = "%s=%s";

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		Stream<String> stringStream = stream.map(
			field -> String.format(
				outputMappingStatement, field.getName(), field.getName()));

		return stringStream.collect(Collectors.joining(StringPool.SEMICOLON));
	}

	private DDMForm _getDDMForm(
		Locale locale, boolean ignoreSKUCombinations, boolean optional,
		boolean publicStore,
		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelCPDefinitionOptionValueRels) {

		if (cpDefinitionOptionRelCPDefinitionOptionValueRels.isEmpty()) {
			return null;
		}

		DDMForm ddmForm = new DDMForm();

		for (Map.Entry<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
				cpDefinitionOptionRelEntry :
					cpDefinitionOptionRelCPDefinitionOptionValueRels.
						entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel =
				cpDefinitionOptionRelEntry.getKey();

			if (Validator.isNull(
					cpDefinitionOptionRel.getDDMFormFieldTypeName())) {

				continue;
			}

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRelEntry.getValue();

			DDMFormField ddmFormField = _getDDMFormField(
				cpDefinitionOptionRel, cpDefinitionOptionValueRels, locale);

			if (publicStore) {
				_setPredefinedValue(ddmFormField, cpDefinitionOptionRel);

				if (!optional) {
					ddmFormField.setRequired(
						_isDDMFormFieldRequired(
							cpDefinitionOptionRel, ignoreSKUCombinations,
							publicStore));
				}
			}
			else {
				ddmFormField.setRequired(!optional);
			}

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
		DDMFormFieldOptions ddmFormFieldOptions, boolean arrayValueFieldType,
		String optionValueKey) {

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		if (options.isEmpty()) {
			return new LocalizedValue(ddmFormFieldOptions.getDefaultLocale());
		}

		for (Map.Entry<String, LocalizedValue> entry : options.entrySet()) {
			LocalizedValue localizedValue = new LocalizedValue();

			LocalizedValue curLocalizedValue = entry.getValue();

			if (arrayValueFieldType) {
				localizedValue.addString(
					curLocalizedValue.getDefaultLocale(),
					String.format("[\"%s\"]", entry.getKey()));
			}
			else {
				localizedValue.addString(
					curLocalizedValue.getDefaultLocale(), entry.getKey());
			}

			if (Validator.isNull(optionValueKey) ||
				Objects.equals(optionValueKey, entry.getKey())) {

				return localizedValue;
			}
		}

		throw new IllegalArgumentException(
			"Provided DDM field options miss valid field value");
	}

	private boolean _isArrayValueCPDefinitionOptionRelFieldType(
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		if (ArrayUtil.contains(
				_ARRAY_VALUE_FIELD_TYPE,
				cpDefinitionOptionRel.getDDMFormFieldTypeName())) {

			return true;
		}

		return false;
	}

	private boolean _isDDMFormFieldRequired(
		CPDefinitionOptionRel cpDefinitionOptionRel,
		boolean ignoreSKUCombinations, boolean publicStore) {

		if (_isIterableCPDefinitionOptionRelFieldType(cpDefinitionOptionRel) &&
			!_cpDefinitionOptionValueRelLocalService.
				hasCPDefinitionOptionValueRels(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId())) {

			return false;
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

	private boolean _isIterableCPDefinitionOptionRelFieldType(
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		if (ArrayUtil.contains(
				CPConstants.PRODUCT_OPTION_MULTIPLE_VALUES_FIELD_TYPES,
				cpDefinitionOptionRel.getDDMFormFieldTypeName())) {

			return true;
		}

		return false;
	}

	private String _render(
			long cpDefinitionId, Locale locale, DDMForm ddmForm, String json,
			PageContext pageContext, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException {

		if (ddmForm == null) {
			return StringPool.BLANK;
		}

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		if (pageContext != null) {
			httpServletResponse =
				PipingServletResponseFactory.createPipingServletResponse(
					pageContext);
		}

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

	private String _render(
			long cpDefinitionId, Locale locale, DDMForm ddmForm, String json,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		return _render(
			cpDefinitionId, locale, ddmForm, json, null, renderRequest,
			renderResponse);
	}

	private void _setPredefinedValue(
		DDMFormField ddmFormField,
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		CPDefinitionOptionValueRel preselectedCPDefinitionOptionValueRel =
			cpDefinitionOptionRel.fetchPreselectedCPDefinitionOptionValueRel();

		String predefinedValueKey = null;

		if (preselectedCPDefinitionOptionValueRel != null) {
			predefinedValueKey = preselectedCPDefinitionOptionValueRel.getKey();
		}

		if (Validator.isNull(predefinedValueKey) &&
			!cpDefinitionOptionRel.isSkuContributor()) {

			return;
		}

		ddmFormField.setPredefinedValue(
			_getDDMFormFieldPredefinedValue(
				ddmFormField.getDDMFormFieldOptions(),
				_isArrayValueCPDefinitionOptionRelFieldType(
					cpDefinitionOptionRel),
				predefinedValueKey));
	}

	private static final String[] _ARRAY_VALUE_FIELD_TYPE = {
		"select", "checkbox", "checkbox_multiple"
	};

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
	private JsonHelper _jsonHelper;

	@Reference
	private Portal _portal;

}