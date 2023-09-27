/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;
import com.liferay.template.internal.transformer.TemplateDisplayTemplateTransformer;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;
import com.liferay.template.transformer.TemplateNodeFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = TemplateInfoItemFieldSetProvider.class)
public class TemplateInfoItemFieldSetProviderImpl
	implements TemplateInfoItemFieldSetProvider {

	@Override
	public InfoFieldSet getInfoFieldSet(
		String infoItemClassName, String infoItemFormVariationKey) {

		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			consumer -> {
				for (TemplateEntry templateEntry :
						_getTemplateEntries(
							infoItemClassName, infoItemFormVariationKey)) {

					consumer.accept(_getInfoField(templateEntry));
				}
			}
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "templates")
		).name(
			"templates"
		).build();
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
		String infoItemClassName, String infoItemFormVariationKey,
		Object itemObject) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		for (TemplateEntry templateEntry :
				_getTemplateEntries(
					infoItemClassName, infoItemFormVariationKey)) {

			infoFieldValues.add(
				new InfoFieldValue<>(
					_getInfoField(templateEntry),
					() -> InfoLocalizedValue.function(
						locale -> _getValue(
							itemObject, locale, templateEntry))));
		}

		return infoFieldValues;
	}

	private InfoField<?> _getInfoField(TemplateEntry templateEntry) {
		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchDDMTemplate(
			templateEntry.getDDMTemplateId());

		return InfoField.builder(
		).infoFieldType(
			HTMLInfoFieldType.INSTANCE
		).namespace(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX
		).name(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				templateEntry.getTemplateEntryId()
		).labelInfoLocalizedValue(
			InfoLocalizedValue.<String>builder(
			).defaultLocale(
				LocaleUtil.fromLanguageId(ddmTemplate.getDefaultLanguageId())
			).values(
				ddmTemplate.getNameMap()
			).build()
		).build();
	}

	private List<TemplateEntry> _getTemplateEntries(
			String infoItemClassName, String infoItemFormVariationKey)
		throws RuntimeException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return Collections.emptyList();
		}

		try {
			return _templateEntryLocalService.getTemplateEntries(
				_stagingGroupHelper.getStagedPortletGroupId(
					serviceContext.getScopeGroupId(),
					TemplatePortletKeys.TEMPLATE),
				infoItemClassName, infoItemFormVariationKey, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return Collections.emptyList();
		}
	}

	private String _getValue(
		Object itemObject, Locale locale, TemplateEntry templateEntry) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((serviceContext == null) ||
			(serviceContext.getThemeDisplay() == null)) {

			return StringPool.BLANK;
		}

		ThemeDisplay currentThemeDisplay = serviceContext.getThemeDisplay();

		ThemeDisplay themeDisplay = null;

		try {
			themeDisplay = (ThemeDisplay)currentThemeDisplay.clone();

			themeDisplay.setLocale(locale);
		}
		catch (CloneNotSupportedException cloneNotSupportedException) {
			_log.error(
				"Unable to clone theme display", cloneNotSupportedException);
		}

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		InfoItemFieldValues infoItemFieldValues = InfoItemFieldValues.builder(
		).build();

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				templateEntry.getInfoItemClassName());

		if (infoItemFieldValuesProvider != null) {
			infoItemFieldValues =
				infoItemFieldValuesProvider.getInfoItemFieldValues(itemObject);
		}

		TemplateDisplayTemplateTransformer templateDisplayTemplateTransformer =
			new TemplateDisplayTemplateTransformer(
				templateEntry, infoItemFieldValues, _templateNodeFactory);

		try {
			return templateDisplayTemplateTransformer.transform(themeDisplay);
		}
		catch (Exception exception) {
			_log.error("Unable to transform template", exception);
		}
		finally {
			themeDisplay.setLocale(currentThemeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateInfoItemFieldSetProviderImpl.class);

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	@Reference
	private TemplateEntryLocalService _templateEntryLocalService;

	@Reference
	private TemplateNodeFactory _templateNodeFactory;

}