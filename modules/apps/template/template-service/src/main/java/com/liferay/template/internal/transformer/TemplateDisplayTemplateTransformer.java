/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.transformer;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.templateparser.Transformer;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.transformer.TemplateNodeFactory;

import java.util.Map;

/**
 * @author Lourdes Fernández Besada
 */
public class TemplateDisplayTemplateTransformer {

	public TemplateDisplayTemplateTransformer(
		TemplateEntry templateEntry, InfoItemFieldValues infoItemFieldValues,
		TemplateNodeFactory templateNodeFactory) {

		_templateEntry = templateEntry;
		_infoItemFieldValues = infoItemFieldValues;
		_templateNodeFactory = templateNodeFactory;
	}

	public String transform(ThemeDisplay themeDisplay) throws Exception {
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

			TemplateNode templateNode = _templateNodeFactory.createTemplateNode(
				infoFieldValue, themeDisplay);

			contextObjects.put(infoField.getName(), templateNode);
			contextObjects.put(infoField.getUniqueId(), templateNode);
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

	private final InfoItemFieldValues _infoItemFieldValues;
	private final TemplateEntry _templateEntry;
	private final TemplateNodeFactory _templateNodeFactory;

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