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

package com.liferay.layout.taglib.internal.servlet;

import com.liferay.frontend.token.definition.FrontendToken;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.layout.page.template.util.LayoutStructureUtil;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.taglib.internal.util.SegmentsExperienceUtil;
import com.liferay.layout.util.structure.CommonStylesUtil;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.StyledLayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"osgi.http.whiteboard.context.path=/layout-common-styles",
		"osgi.http.whiteboard.servlet.name=com.liferay.layout.taglib.internal.servlet.LayoutStructureCommonStylesCSSServlet",
		"osgi.http.whiteboard.servlet.pattern=/layout-common-styles/*"
	},
	service = Servlet.class
)
public class LayoutStructureCommonStylesCSSServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletResponse.setContentType(ContentTypes.TEXT_CSS_UTF8);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		Map<String, String[]> parameterMap = HttpComponentsUtil.getParameterMap(
			httpServletRequest.getQueryString());

		String[] plids = parameterMap.get("plid");

		Layout layout = _layoutLocalService.fetchLayout(
			GetterUtil.getLong(plids[0]));

		if ((layout == null) ||
			(!layout.isTypeAssetDisplay() && !layout.isTypeContent())) {

			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				layout.getPlid(),
				SegmentsExperienceUtil.getSegmentsExperienceId(
					httpServletRequest));

		if (layoutStructure == null) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.write(".lfr-layout-structure-item-row {overflow: hidden;}");

		JSONObject frontendTokensJSONObject = _getFrontendTokensJSONObject(
			layout.getGroupId(), layout,
			ParamUtil.getBoolean(httpServletRequest, "styleBookEntryPreview"));

		List<LayoutStructureItem> layoutStructureItems =
			layoutStructure.getLayoutStructureItems();

		for (ViewportSize viewportSize : _sortedViewportSizes) {
			StringBundler cssSB = new StringBundler();

			for (LayoutStructureItem layoutStructureItem :
					layoutStructureItems) {

				if (!(layoutStructureItem instanceof
						StyledLayoutStructureItem)) {

					continue;
				}

				StyledLayoutStructureItem styledLayoutStructureItem =
					(StyledLayoutStructureItem)layoutStructureItem;

				cssSB.append(
					_getLayoutStructureItemCSS(
						frontendTokensJSONObject, styledLayoutStructureItem,
						viewportSize));

				String customCSS = _getCustomCSS(
					styledLayoutStructureItem, viewportSize);

				if (Validator.isNotNull(customCSS)) {
					cssSB.append(
						StringUtil.replace(
							customCSS, _FRAGMENT_CLASS_PLACEHOLDER,
							styledLayoutStructureItem.getUniqueCssClass()));
				}
			}

			if (cssSB.length() == 0) {
				continue;
			}

			if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
				printWriter.print(cssSB);
			}
			else {
				printWriter.print("@media screen and (max-width: ");
				printWriter.print(viewportSize.getMaxWidth());
				printWriter.print("px) {");
				printWriter.print(cssSB);
				printWriter.print(StringPool.CLOSE_CURLY_BRACE);
			}
		}
	}

	private JSONObject _createJSONObject(String json) {
		try {
			return _jsonFactory.createJSONObject(json);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return _jsonFactory.createJSONObject();
		}
	}

	private String _getCustomCSS(
		StyledLayoutStructureItem styledLayoutStructureItem,
		ViewportSize viewportSize) {

		if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
			return styledLayoutStructureItem.getCustomCSS();
		}

		Map<String, String> customCSSViewports =
			styledLayoutStructureItem.getCustomCSSViewports();

		return customCSSViewports.get(viewportSize.getViewportSizeId());
	}

	private JSONObject _getFrontendTokensJSONObject(
		long groupId, Layout layout, boolean styleBookEntryPreview) {

		JSONObject frontendTokensJSONObject = _jsonFactory.createJSONObject();

		StyleBookEntry styleBookEntry = null;

		if (!styleBookEntryPreview) {
			styleBookEntry = DefaultStyleBookEntryUtil.getDefaultStyleBookEntry(
				layout);
		}

		JSONObject frontendTokenValuesJSONObject =
			_jsonFactory.createJSONObject();

		if (styleBookEntry != null) {
			frontendTokenValuesJSONObject = _createJSONObject(
				styleBookEntry.getFrontendTokensValues());
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return _jsonFactory.createJSONObject();
		}

		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			group.getGroupId(), group.isLayoutSetPrototype());

		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry =
			ServletContextUtil.getFrontendTokenDefinitionRegistry();

		FrontendTokenDefinition frontendTokenDefinition =
			frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				layoutSet.getThemeId());

		if (frontendTokenDefinition == null) {
			return _jsonFactory.createJSONObject();
		}

		Collection<FrontendToken> frontendTokens =
			frontendTokenDefinition.getFrontendTokens();

		for (FrontendToken frontendToken : frontendTokens) {
			List<FrontendTokenMapping> frontendTokenMappings = new ArrayList<>(
				frontendToken.getFrontendTokenMappings(
					FrontendTokenMapping.TYPE_CSS_VARIABLE));

			if (ListUtil.isEmpty(frontendTokenMappings)) {
				continue;
			}

			frontendTokensJSONObject.put(
				frontendToken.getName(),
				JSONUtil.put(
					FrontendTokenMapping.TYPE_CSS_VARIABLE,
					() -> {
						FrontendTokenMapping frontendTokenMapping =
							frontendTokenMappings.get(0);

						return frontendTokenMapping.getValue();
					}
				).put(
					"value",
					Optional.ofNullable(
						frontendTokenValuesJSONObject.getJSONObject(
							frontendToken.getName())
					).map(
						valueJSONObject -> valueJSONObject.getString("value")
					).orElse(
						frontendToken.getDefaultValue()
					)
				));
		}

		return frontendTokensJSONObject;
	}

	private String _getLayoutStructureItemCSS(
		JSONObject frontendTokensJSONObject,
		StyledLayoutStructureItem styledLayoutStructureItem,
		ViewportSize viewportSize) {

		JSONObject stylesJSONObject = _getStylesJSONObject(
			styledLayoutStructureItem.getItemConfigJSONObject(), viewportSize);

		if (stylesJSONObject.length() == 0) {
			return StringPool.BLANK;
		}

		List<String> availableStyles = ListUtil.filter(
			CommonStylesUtil.getAvailableStyleNames(),
			styleName -> _includeStyles(
				styledLayoutStructureItem, styleName,
				stylesJSONObject.getString(styleName), viewportSize));

		if (ListUtil.isEmpty(availableStyles)) {
			return StringPool.BLANK;
		}

		StringBundler cssSB = new StringBundler(
			(availableStyles.size() * 2) + 4);

		cssSB.append(".lfr-layout-structure-item-");
		cssSB.append(styledLayoutStructureItem.getItemId());
		cssSB.append(" {\n");

		for (String styleName : availableStyles) {
			String value = stylesJSONObject.getString(styleName);

			cssSB.append(
				StringUtil.replace(
					CommonStylesUtil.getCSSTemplate(styleName), "{value}",
					_getStyleValue(
						frontendTokensJSONObject, styledLayoutStructureItem,
						styleName, value)));

			cssSB.append(StringPool.NEW_LINE);
		}

		cssSB.append("}\n");

		return cssSB.toString();
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

	private JSONObject _getStylesJSONObject(
		JSONObject itemConfigJSONObject, ViewportSize viewportSize) {

		if (Objects.equals(viewportSize, ViewportSize.DESKTOP)) {
			return itemConfigJSONObject.getJSONObject("styles");
		}

		return Optional.ofNullable(
			itemConfigJSONObject.getJSONObject(viewportSize.getViewportSizeId())
		).map(
			viewportJSONObject -> viewportJSONObject.getJSONObject("styles")
		).orElse(
			_jsonFactory.createJSONObject()
		);
	}

	private String _getStyleValue(
		JSONObject frontendTokensJSONObject,
		StyledLayoutStructureItem styledLayoutStructureItem, String styleName,
		String value) {

		if (styleName.startsWith("margin") || styleName.startsWith("padding")) {
			StringBundler sb = new StringBundler(5);

			String spacingValue = _spacings.get(value);

			if (Validator.isNotNull(spacingValue)) {
				sb.append("var(--spacer-");
				sb.append(value);
				sb.append(StringPool.COMMA);
				sb.append(spacingValue);
				sb.append("rem)");
			}
			else {
				sb.append(value);
			}

			return sb.toString();
		}

		if (Objects.equals(styleName, "backgroundImage")) {
			return "var(--lfr-background-image-" +
				styledLayoutStructureItem.getItemId() +
					StringPool.CLOSE_PARENTHESIS;
		}

		if (Objects.equals(styleName, "opacity")) {
			return String.valueOf(GetterUtil.getInteger(value, 100) / 100.0);
		}

		return _getStyleFromStyleBookEntry(frontendTokensJSONObject, value);
	}

	private boolean _includeStyles(
		StyledLayoutStructureItem styledLayoutStructureItem, String styleName,
		String value, ViewportSize viewportSize) {

		if (Validator.isNull(value) ||
			(Objects.equals(
				value, CommonStylesUtil.getDefaultStyleValue(styleName)) &&
			 Objects.equals(viewportSize, ViewportSize.DESKTOP))) {

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

	private static final String _FRAGMENT_CLASS_PLACEHOLDER =
		"[$FRAGMENT_CLASS$]";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStructureCommonStylesCSSServlet.class);

	private static final ViewportSize[] _sortedViewportSizes =
		ViewportSize.values();
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

	static {
		Arrays.sort(
			_sortedViewportSizes,
			Comparator.comparingInt(ViewportSize::getOrder));
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}