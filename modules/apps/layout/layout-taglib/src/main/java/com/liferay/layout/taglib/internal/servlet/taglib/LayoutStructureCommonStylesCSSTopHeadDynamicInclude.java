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

package com.liferay.layout.taglib.internal.servlet.taglib;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.taglib.internal.util.LayoutStructureUtil;
import com.liferay.layout.util.structure.CommonStylesUtil;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.StyledLayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = DynamicInclude.class)
public class LayoutStructureCommonStylesCSSTopHeadDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String dynamicIncludeKey)
		throws IOException {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-132571"))) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeAssetDisplay() && !layout.isTypeContent()) {
			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.print("<style id=\"layout-common-styles\"");
		printWriter.print(" data-senna-track=\"temporary\" ");
		printWriter.print("type=\"text/css\">\n");

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				httpServletRequest, themeDisplay.getPlid());

		if (layoutStructure == null) {
			return;
		}

		JSONObject frontendTokensJSONObject = _getFrontendTokensJSONObject(
			themeDisplay.getSiteGroupId(), themeDisplay.getLayout(),
			themeDisplay.getLocale(),
			ParamUtil.getBoolean(httpServletRequest, "styleBookEntryPreview"));

		List<LayoutStructureItem> layoutStructureItems =
			layoutStructure.getLayoutStructureItems();

		for (LayoutStructureItem layoutStructureItem : layoutStructureItems) {
			String css = _getLayoutStructureItemCSS(
				frontendTokensJSONObject, layoutStructureItem);

			if (Validator.isNotNull(css)) {
				printWriter.print(css);
			}
		}

		printWriter.print("</style>\n");
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	private JSONObject _createJSONObject(String json) {
		try {
			return JSONFactoryUtil.createJSONObject(json);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	private boolean _includeStyles(
		StyledLayoutStructureItem styledLayoutStructureItem, String styleName,
		String value) {

		if (Validator.isNull(value) ||
			Objects.equals(
				value, CommonStylesUtil.getDefaultStyleValue(styleName))) {

			return false;
		}

		if (!(styledLayoutStructureItem instanceof
			ContainerStyledLayoutStructureItem)) {

			return true;
		}

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)styledLayoutStructureItem;

		if (!Objects.equals(
			containerStyledLayoutStructureItem.getWidthType(), "fixed")) {

			return true;
		}

		if (Objects.equals(styleName, "marginLeft") ||
			Objects.equals(styleName, "marginRight")) {

			return false;
		}

		return true;
	}

	private List<String> _getAvailableStyleNames() {
		try {
			return CommonStylesUtil.getAvailableStyleNames();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return Collections.emptyList();
		}
	}

	private JSONObject _getFrontendTokensJSONObject(
		long groupId, Layout layout, Locale locale,
		boolean styleBookEntryPreview) {

		JSONObject frontendTokensJSONObject =
			JSONFactoryUtil.createJSONObject();

		StyleBookEntry styleBookEntry = null;

		if (!styleBookEntryPreview) {
			styleBookEntry = DefaultStyleBookEntryUtil.getDefaultStyleBookEntry(
				layout);
		}

		JSONObject frontendTokenValuesJSONObject =
			JSONFactoryUtil.createJSONObject();

		if (styleBookEntry != null) {
			frontendTokenValuesJSONObject = _createJSONObject(
				styleBookEntry.getFrontendTokensValues());
		}

		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry =
			ServletContextUtil.getFrontendTokenDefinitionRegistry();

		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			groupId, false);

		FrontendTokenDefinition frontendTokenDefinition =
			frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				layoutSet.getThemeId());

		if (frontendTokenDefinition == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		JSONObject frontendTokenDefinitionJSONObject = _createJSONObject(
			frontendTokenDefinition.getJSON(locale));

		JSONArray frontendTokenCategoriesJSONArray =
			frontendTokenDefinitionJSONObject.getJSONArray(
				"frontendTokenCategories");

		for (int i = 0; i < frontendTokenCategoriesJSONArray.length(); i++) {
			JSONObject frontendTokenCategoryJSONObject =
				frontendTokenCategoriesJSONArray.getJSONObject(i);

			JSONArray frontendTokenSetsJSONArray =
				frontendTokenCategoryJSONObject.getJSONArray(
					"frontendTokenSets");

			for (int j = 0; j < frontendTokenSetsJSONArray.length(); j++) {
				JSONObject frontendTokenSetJSONObject =
					frontendTokenSetsJSONArray.getJSONObject(j);

				JSONArray frontendTokensJSONArray =
					frontendTokenSetJSONObject.getJSONArray("frontendTokens");

				for (int k = 0; k < frontendTokensJSONArray.length(); k++) {
					JSONObject frontendTokenJSONObject =
						frontendTokensJSONArray.getJSONObject(k);

					String cssVariable = StringPool.BLANK;

					JSONArray mappingsJSONArray =
						frontendTokenJSONObject.getJSONArray("mappings");

					for (int l = 0; l < mappingsJSONArray.length(); l++) {
						JSONObject mappingJSONObject =
							mappingsJSONArray.getJSONObject(l);

						if (Objects.equals(
								mappingJSONObject.getString("type"),
								FrontendTokenMapping.TYPE_CSS_VARIABLE)) {

							cssVariable = mappingJSONObject.getString("value");
						}
					}

					String value = StringPool.BLANK;

					String name = frontendTokenJSONObject.getString("name");

					JSONObject valueJSONObject =
						frontendTokenValuesJSONObject.getJSONObject(name);

					if (valueJSONObject != null) {
						value = valueJSONObject.getString("value");
					}
					else {
						value = frontendTokenJSONObject.getString(
							"defaultValue");
					}

					frontendTokensJSONObject.put(
						name,
						JSONUtil.put(
							FrontendTokenMapping.TYPE_CSS_VARIABLE, cssVariable
						).put(
							"value", value
						));
				}
			}
		}

		return frontendTokensJSONObject;
	}

	private String _getLayoutStructureItemCSS(
		JSONObject frontendTokensJSONObject,
		LayoutStructureItem layoutStructureItem) {

		if (!(layoutStructureItem instanceof StyledLayoutStructureItem)) {
			return StringPool.BLANK;
		}

		StringBundler cssSB = new StringBundler(74);

		StyledLayoutStructureItem styledLayoutStructureItem =
			(StyledLayoutStructureItem)layoutStructureItem;

		List<String> availableStyleNames = _getAvailableStyleNames();

		JSONObject itemConfigJSONObject =
			styledLayoutStructureItem.getItemConfigJSONObject();

		JSONObject stylesJSONObject = itemConfigJSONObject.getJSONObject(
			"styles");

		for (String styleName : availableStyleNames) {
			String value = stylesJSONObject.getString(styleName);

			if (!_includeStyles(
					styledLayoutStructureItem, styleName, value)) {

				continue;
			}

			cssSB.append(
				StringUtil.replace(
					CommonStylesUtil.getCSSTemplate(styleName), "{value}",
					_getStyleValue(
						frontendTokensJSONObject, styledLayoutStructureItem,
						styleName, value)));
			cssSB.append(StringPool.NEW_LINE);
		}

		String css = cssSB.toString();

		if (Validator.isNull(css)) {
			return StringPool.BLANK;
		}

		StringBundler cssRuleSB = new StringBundler(5);

		cssRuleSB.append(".lfr-layout-structure-item-");
		cssRuleSB.append(layoutStructureItem.getItemId());
		cssRuleSB.append(" {\n");
		cssRuleSB.append(css);
		cssRuleSB.append("}\n");

		return cssRuleSB.toString();
	}

	private String _getStyleFromStyleBookEntry(
		JSONObject frontendTokensJSONObject, String styleValue) {

		JSONObject styleValueJSONObject =
			frontendTokensJSONObject.getJSONObject(styleValue);

		if (styleValueJSONObject == null) {
			return styleValue;
		}

		String cssVariable = styleValueJSONObject.getString(
			FrontendTokenMapping.TYPE_CSS_VARIABLE);

		return "var(--" + cssVariable + ")";
	}

	private String _getStyleValue(
		JSONObject frontendTokensJSONObject,
		StyledLayoutStructureItem styledLayoutStructureItem, String styleName,
		String value) {

		if (styleName.startsWith("margin") || styleName.startsWith("padding")) {
			StringBundler sb = new StringBundler(5);

			sb.append("var(--spacer-");
			sb.append(value);
			sb.append(StringPool.COMMA);
			sb.append(_spacings.get(value));
			sb.append("rem)");

			return sb.toString();
		}

		if (Objects.equals(styleName, "backgroundImage")) {
			return "var(--lfr-" + styledLayoutStructureItem.getItemId() +
				"-background-image)";
		}

		if (Objects.equals(styleName, "opacity")) {
			return String.valueOf(GetterUtil.getInteger(value, 100) / 100.0);
		}

		return _getStyleFromStyleBookEntry(frontendTokensJSONObject, value);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStructureCommonStylesCSSTopHeadDynamicInclude.class);

	private static final Map<String, String> _spacings = HashMapBuilder.put(
		"0", "0"
	).put(
		"1", "0.25"
	).put(
		"2", "0.5"
	).put(
		"3", "1"
	).put(
		"4", "1.5"
	).put(
		"5", "3"
	).put(
		"6", "4.5"
	).put(
		"7", "6"
	).put(
		"8", "7.5"
	).put(
		"9", "9"
	).put(
		"10", "10"
	).build();

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}