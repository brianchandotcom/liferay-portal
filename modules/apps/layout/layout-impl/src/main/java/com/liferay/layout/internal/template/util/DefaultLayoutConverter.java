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

package com.liferay.layout.internal.template.util;

import com.liferay.layout.internal.configuration.LayoutConverterConfiguration;
import com.liferay.layout.util.template.LayoutColumn;
import com.liferay.layout.util.template.LayoutConversionResult;
import com.liferay.layout.util.template.LayoutConverter;
import com.liferay.layout.util.template.LayoutData;
import com.liferay.layout.util.template.LayoutRow;
import com.liferay.layout.util.template.LayoutTypeSettingsInspectorUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Rubén Pulido
 */
@Component(
	configurationPid = "com.liferay.layout.internal.configuration.LayoutConverterConfiguration",
	immediate = true, property = "layout.template.id=default",
	service = LayoutConverter.class
)
public class DefaultLayoutConverter implements LayoutConverter {

	@Override
	public LayoutData convert(Layout layout) {
		LayoutConversionResult layoutConversionResult = convert(
			layout, LocaleUtil.getSiteDefault());

		return layoutConversionResult.getLayoutData();
	}

	@Override
	public LayoutConversionResult convert(Layout layout, Locale locale) {
		String[] conversionWarningMessages = _getConversionWarningMessages(
			layout, locale);

		if (!_isLayoutTemplateParseable(layout)) {
			return LayoutConversionResult.of(
				LayoutData.of(
					layout,
					layoutRow -> layoutRow.addLayoutColumns(
						layoutColumn -> layoutColumn.addAllPortlets())),
				conversionWarningMessages);
		}

		List<UnsafeConsumer<LayoutRow, Exception>> rowUnsafeConsumers =
			new ArrayList<>();

		Document layoutTemplateDocument = _getLayoutTemplateDocument(layout);

		for (Element rowElement :
				layoutTemplateDocument.select(".portlet-layout.row")) {

			List<UnsafeConsumer<LayoutColumn, Exception>>
				columnUnsafeConsumers = new ArrayList<>();

			for (Element columnElement :
					rowElement.getElementsByClass("portlet-column")) {

				UnsafeConsumer<LayoutColumn, Exception> columnUnsafeConsumer =
					layoutColumn -> {
						layoutColumn.addPortlets(columnElement.id());

						int columnSize = 12;

						for (String className : columnElement.classNames()) {
							if (className.startsWith(
									_CSS_CLASS_COLUMN_PREFIX)) {

								columnSize = GetterUtil.getInteger(
									className.substring(
										_CSS_CLASS_COLUMN_PREFIX.length()),
									12);

								break;
							}
						}

						layoutColumn.setSize(columnSize);
					};

				columnUnsafeConsumers.add(columnUnsafeConsumer);
			}

			UnsafeConsumer<LayoutRow, Exception> rowUnsafeConsumer =
				layoutRow -> layoutRow.addLayoutColumns(
					columnUnsafeConsumers.toArray(new UnsafeConsumer[0]));

			rowUnsafeConsumers.add(rowUnsafeConsumer);
		}

		return LayoutConversionResult.of(
			LayoutData.of(
				layout, rowUnsafeConsumers.toArray(new UnsafeConsumer[0])),
			conversionWarningMessages);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutConverterConfiguration = ConfigurableUtil.createConfigurable(
			LayoutConverterConfiguration.class, properties);
	}

	private String[] _getConversionWarningMessages(
		Layout layout, Locale locale) {

		List<String> conversionWarningMessages = new ArrayList<>();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());
		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		if (LayoutTypeSettingsInspectorUtil.hasNestedPortletsPortlet(
				typeSettingsUnicodeProperties)) {

			conversionWarningMessages.add(
				LanguageUtil.get(
					resourceBundle,
					"this-page-uses-nested-applications-widgets.-they-have-" +
						"been-placed-in-a-single-column-and-may-require-" +
							"manual-reorganization"));
		}

		if (LayoutTypeSettingsInspectorUtil.isCustomizableLayout(
				typeSettingsUnicodeProperties)) {

			conversionWarningMessages.add(
				LanguageUtil.get(
					resourceBundle,
					"this-page-has-customizable-columns.-this-capability-is-" +
						"not-supported-for-content-pages-and-will-be-lost-if-" +
							"the-conversion-draft-is-published"));
		}

		if (_isLayoutTemplateParseable(layout) &&
			!ArrayUtil.contains(
				_layoutConverterConfiguration.verifiedLayoutTemplateIds(),
				typeSettingsUnicodeProperties.getProperty(
					LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID))) {

			conversionWarningMessages.add(
				LanguageUtil.get(
					resourceBundle,
					"this-page-uses-a-custom-page-layout.-a-best-effort-" +
						"conversion-has-been-performed.-verify-the-" +
							"conversion-draft-before-publishing-it"));
		}

		if (!_isLayoutTemplateParseable(layout)) {
			conversionWarningMessages.add(
				LanguageUtil.get(
					resourceBundle,
					"this-page-uses-a-custom-page-layout.-all-widgets-have-" +
						"been-placed-in-a-single-column-and-will-require-" +
							"manual-reorganization"));
		}

		return conversionWarningMessages.toArray(new String[0]);
	}

	private Document _getLayoutTemplateDocument(Layout layout) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		Document document = Jsoup.parseBodyFragment(
			layoutTemplate.getContent());

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private boolean _isLayoutTemplateParseable(Layout layout) {
		Document layoutTemplateDocument = _getLayoutTemplateDocument(layout);

		Elements rowElements = layoutTemplateDocument.select(
			".portlet-layout.row");

		if (rowElements.isEmpty()) {
			return false;
		}

		for (Element rowElement : rowElements) {
			Elements columnElements = rowElement.getElementsByClass(
				"portlet-column");

			if (columnElements.isEmpty()) {
				return false;
			}

			for (Element columnElement : columnElements) {
				int columnSize = 0;

				for (String className : columnElement.classNames()) {
					if (className.startsWith(_CSS_CLASS_COLUMN_PREFIX)) {
						columnSize = GetterUtil.getInteger(
							className.substring(
								_CSS_CLASS_COLUMN_PREFIX.length()));

						break;
					}
				}

				if (columnSize == 0) {
					return false;
				}
			}
		}

		return true;
	}

	private static final String _CSS_CLASS_COLUMN_PREFIX = "col-md-";

	private volatile LayoutConverterConfiguration _layoutConverterConfiguration;

}