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

package com.liferay.template.internal.transformer;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.CategoriesInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.type.categorization.Category;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.templateparser.Transformer;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;
import com.liferay.template.model.TemplateEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class TemplateDisplayTemplateTransformer {

	public TemplateDisplayTemplateTransformer(
		TemplateEntry templateEntry, InfoItemFieldValues infoItemFieldValues) {

		_templateEntry = templateEntry;
		_infoItemFieldValues = infoItemFieldValues;
	}

	public String transform(Locale locale) throws Exception {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		Transformer transformer = TransformerHolder.getTransformer();

		Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
			PortletDisplayTemplateConstants.CURRENT_URL,
			themeDisplay.getURLCurrent()
		).put(
			PortletDisplayTemplateConstants.LOCALE, themeDisplay.getLocale()
		).put(
			PortletDisplayTemplateConstants.THEME_DISPLAY, themeDisplay
		).build();

		for (InfoFieldValue<Object> infoFieldValue :
				_infoItemFieldValues.getInfoFieldValues()) {

			InfoField<?> infoField = infoFieldValue.getInfoField();

			if (StringUtil.startsWith(
					infoField.getName(),
					PortletDisplayTemplate.DISPLAY_STYLE_PREFIX)) {

				continue;
			}

			TemplateNode templateNode = _createTemplateNode(
				infoField, infoFieldValue, themeDisplay);

			contextObjects.put(infoField.getName(), templateNode);
		}

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				InfoItemFormProvider.class.getName());

		contextObjects.putAll(templateHandler.getCustomContextObjects());

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
			_templateEntry.getDDMTemplateId());

		return transformer.transform(
			themeDisplay, contextObjects, ddmTemplate.getScript(),
			TemplateConstants.LANG_TYPE_FTL, new UnsyncStringWriter(),
			themeDisplay.getRequest(), themeDisplay.getResponse());
	}

	private TemplateNode _createTemplateNode(
		InfoField<?> infoField, InfoFieldValue<Object> infoFieldValue,
		ThemeDisplay themeDisplay) {

		Object data = infoFieldValue.getValue(themeDisplay.getLocale());

		if (Validator.isNull(data)) {
			return new TemplateNode(
				themeDisplay, infoField.getName(), StringPool.BLANK,
				StringPool.BLANK, new HashMap<>());
		}

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		if (Objects.equals(CategoriesInfoFieldType.INSTANCE, infoFieldType)) {
			List<Category> categories = (List<Category>)data;

			if (categories.isEmpty()) {
				return new TemplateNode(
					themeDisplay, infoField.getName(), StringPool.BLANK,
					StringPool.BLANK, new HashMap<>());
			}

			Category firstCategory = categories.get(0);

			TemplateNode templateNode = new TemplateNode(
				themeDisplay, infoField.getName(),
				firstCategory.getLabel(themeDisplay.getLocale()),
				infoFieldType.getName(),
				HashMapBuilder.put(
					"key", firstCategory.getKey()
				).put(
					"label", firstCategory.getLabel(themeDisplay.getLocale())
				).build());

			templateNode.appendSibling(templateNode);

			for (int i = 1; i < categories.size(); i++) {
				Category siblingCategory = categories.get(i);

				templateNode.appendSibling(
					new TemplateNode(
						themeDisplay, infoField.getName(),
						siblingCategory.getLabel(themeDisplay.getLocale()),
						infoFieldType.getName(),
						HashMapBuilder.put(
							"key", siblingCategory.getKey()
						).put(
							"label",
							siblingCategory.getLabel(themeDisplay.getLocale())
						).build()));
			}

			return templateNode;
		}

		return new TemplateNode(
			themeDisplay, infoField.getName(), String.valueOf(data),
			StringPool.BLANK, new HashMap<>());
	}

	private final InfoItemFieldValues _infoItemFieldValues;
	private final TemplateEntry _templateEntry;

	private static class TransformerHolder {

		public static Transformer getTransformer() {
			return _transformer;
		}

		private static final Transformer _transformer = new Transformer(
			StringPool.BLANK, true) {

			@Override
			protected String getErrorTemplateId(
				String errorTemplatePropertyKey, String langType) {

				return "com/liferay/template/service/internal/transformer" +
					"/dependencies/error.ftl";
			}

		};

	}

}