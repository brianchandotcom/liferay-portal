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
import com.liferay.petra.function.UnsafeFunction;
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

import java.util.Collections;
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
			ThemeDisplay themeDisplay)
		throws Exception {

		Object data = infoFieldValue.getValue(themeDisplay.getLocale());

		if (Validator.isNull(data)) {
			return new TemplateNode(
				themeDisplay, infoField.getName(), StringPool.BLANK,
				StringPool.BLANK, Collections.emptyMap());
		}

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		if (Objects.equals(CategoriesInfoFieldType.INSTANCE, infoFieldType)) {
			List<Category> categories = (List<Category>)data;

			return _createTemplateNode(
				infoField.getName(), themeDisplay, categories,
				category -> new TemplateNode(
					themeDisplay, infoField.getName(),
					category.getLabel(themeDisplay.getLocale()),
					infoFieldType.getName(),
					HashMapBuilder.put(
						"key", category.getKey()
					).put(
						"label", category.getLabel(themeDisplay.getLocale())
					).build()));
		}
		else if (data instanceof List) {
			List<Object> list = (List<Object>)data;

			return _createTemplateNode(
				infoField.getName(), themeDisplay, list,
				object -> new TemplateNode(
					themeDisplay, infoField.getName(), String.valueOf(object),
					infoFieldType.getName(), Collections.emptyMap()));
		}

		return new TemplateNode(
			themeDisplay, infoField.getName(), String.valueOf(data),
			StringPool.BLANK, Collections.emptyMap());
	}

	private <T> TemplateNode _createTemplateNode(
			String fieldName, ThemeDisplay themeDisplay, List<T> list,
			UnsafeFunction<T, TemplateNode, Exception> unsafeFunction)
		throws Exception {

		if (list.isEmpty()) {
			return new TemplateNode(
				themeDisplay, fieldName, StringPool.BLANK, StringPool.BLANK,
				Collections.emptyMap());
		}

		T firstItem = list.get(0);

		TemplateNode templateNode = unsafeFunction.apply(firstItem);

		templateNode.appendSibling(templateNode);

		for (int i = 1; i < list.size(); i++) {
			T item = list.get(i);

			templateNode.appendSibling(unsafeFunction.apply(item));
		}

		return templateNode;
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