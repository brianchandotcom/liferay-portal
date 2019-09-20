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

package com.liferay.portlet.configuration.css.web.internal.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.configuration.css.web.internal.constants.PortletConfigurationCSSPortletKeys;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.icon=/icons/portlet_css.png",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Portlet CSS",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PortletConfigurationCSSPortletKeys.PORTLET_CONFIGURATION_CSS,
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class PortletConfigurationCSSPortlet extends MVCPortlet {

	public void updateLookAndFeel(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String portletId = ParamUtil.getString(actionRequest, "portletId");

		if (!PortletPermissionUtil.contains(
				permissionChecker, layout, portletId,
				ActionKeys.CONFIGURATION)) {

			return;
		}

		PortletPreferences portletSetup =
			themeDisplay.getStrictLayoutPortletSetup(layout, portletId);

		String css = getCSS(actionRequest);

		if (_log.isDebugEnabled()) {
			_log.debug("Updating css " + css);
		}

		String portletDecoratorId = ParamUtil.getString(
			actionRequest, "portletDecoratorId");
		Map<Locale, String> customTitleMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "customTitle");
		boolean useCustomTitle = ParamUtil.getBoolean(
			actionRequest, "useCustomTitle");

		Set<Locale> locales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String title = null;

			if (customTitleMap.containsKey(locale)) {
				title = customTitleMap.get(locale);
			}

			String rootPortletId = PortletIdCodec.decodePortletName(portletId);

			String defaultPortletTitle = _portal.getPortletTitle(
				rootPortletId, languageId);

			if ((title != null) &&
				!Objects.equals(defaultPortletTitle, title)) {

				portletSetup.setValue("portletSetupTitle_" + languageId, title);
			}
			else {
				portletSetup.reset("portletSetupTitle_" + languageId);
			}
		}

		portletSetup.setValue(
			"portletSetupUseCustomTitle", String.valueOf(useCustomTitle));

		if (Validator.isNotNull(portletDecoratorId)) {
			portletSetup.setValue(
				"portletSetupPortletDecoratorId", portletDecoratorId);
		}
		else {
			portletSetup.reset("portletSetupPortletDecoratorId");
		}

		portletSetup.setValue("portletSetupCss", css);

		portletSetup.store();

		SessionMessages.add(
			actionRequest,
			_portal.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
			portletId);
	}

	protected JSONObject getAdvancedDataJSONObject(
		ActionRequest actionRequest) {

		String customCSS = ParamUtil.getString(actionRequest, "customCSS");

		JSONObject advancedDataJSONObject = JSONUtil.put(
			"customCSS", customCSS);

		String customCSSClassName = ParamUtil.getString(
			actionRequest, "customCSSClassName");

		advancedDataJSONObject.put("customCSSClassName", customCSSClassName);

		return advancedDataJSONObject;
	}

	protected JSONObject getBgDataJSONObject(ActionRequest actionRequest) {
		String backgroundColor = ParamUtil.getString(
			actionRequest, "backgroundColor");

		JSONObject bgDataJSONObject = JSONUtil.put(
			"backgroundColor", backgroundColor
		).put(
			"backgroundImage", StringPool.BLANK
		);

		JSONObject backgroundPositionLeftJSONObject = JSONUtil.put(
			"unit", StringPool.BLANK
		).put(
			"value", StringPool.BLANK
		);

		JSONObject backgroundPositionJSONObject = JSONUtil.put(
			"left", backgroundPositionLeftJSONObject);

		JSONObject backgroundPositionTopJSONObject = JSONUtil.put(
			"unit", StringPool.BLANK
		).put(
			"value", StringPool.BLANK
		);

		backgroundPositionJSONObject.put(
			"top", backgroundPositionTopJSONObject);

		bgDataJSONObject.put(
			"backgroundPosition", backgroundPositionJSONObject
		).put(
			"backgroundRepeat", StringPool.BLANK
		).put(
			"useBgImage", false
		);

		return bgDataJSONObject;
	}

	protected JSONObject getBorderDataJSONObject(ActionRequest actionRequest) {
		String borderColorBottom = ParamUtil.getString(
			actionRequest, "borderColorBottom");

		JSONObject borderColorJSONObject = JSONUtil.put(
			"bottom", borderColorBottom);

		String borderColorLeft = ParamUtil.getString(
			actionRequest, "borderColorLeft");

		borderColorJSONObject.put("left", borderColorLeft);

		String borderColorRight = ParamUtil.getString(
			actionRequest, "borderColorRight");

		borderColorJSONObject.put("right", borderColorRight);

		boolean useForAllColor = ParamUtil.getBoolean(
			actionRequest, "useForAllColor");

		borderColorJSONObject.put("sameForAll", useForAllColor);

		String borderColorTop = ParamUtil.getString(
			actionRequest, "borderColorTop");

		borderColorJSONObject.put("top", borderColorTop);

		JSONObject borderDataJSONObject = JSONUtil.put(
			"borderColor", borderColorJSONObject);

		String borderStyleBottom = ParamUtil.getString(
			actionRequest, "borderStyleBottom");

		JSONObject borderStyleJSONObject = JSONUtil.put(
			"bottom", borderStyleBottom);

		String borderStyleLeft = ParamUtil.getString(
			actionRequest, "borderStyleLeft");

		borderStyleJSONObject.put("left", borderStyleLeft);

		String borderStyleRight = ParamUtil.getString(
			actionRequest, "borderStyleRight");

		borderStyleJSONObject.put("right", borderStyleRight);

		boolean useForAllStyle = ParamUtil.getBoolean(
			actionRequest, "useForAllStyle");

		borderStyleJSONObject.put("sameForAll", useForAllStyle);

		String borderStyleTop = ParamUtil.getString(
			actionRequest, "borderStyleTop");

		borderStyleJSONObject.put("top", borderStyleTop);

		borderDataJSONObject.put("borderStyle", borderStyleJSONObject);

		String borderWidthBottomUnit = ParamUtil.getString(
			actionRequest, "borderWidthBottomUnit");

		JSONObject borderWidthBottomJSONObject = JSONUtil.put(
			"unit", borderWidthBottomUnit);

		String borderWidthBottom = ParamUtil.getString(
			actionRequest, "borderWidthBottom");

		borderWidthBottomJSONObject.put("value", borderWidthBottom);

		JSONObject borderWidthJSONObject = JSONUtil.put(
			"bottom", borderWidthBottomJSONObject);

		String borderWidthLeftUnit = ParamUtil.getString(
			actionRequest, "borderWidthLeftUnit");

		JSONObject borderWidthLeftJSONObject = JSONUtil.put(
			"unit", borderWidthLeftUnit);

		String borderWidthLeft = ParamUtil.getString(
			actionRequest, "borderWidthLeft");

		borderWidthLeftJSONObject.put("value", borderWidthLeft);

		borderWidthJSONObject.put("left", borderWidthLeftJSONObject);

		String borderWidthRightUnit = ParamUtil.getString(
			actionRequest, "borderWidthRightUnit");

		JSONObject borderWidthRightJSONObject = JSONUtil.put(
			"unit", borderWidthRightUnit);

		String borderWidthRight = ParamUtil.getString(
			actionRequest, "borderWidthRight");

		borderWidthRightJSONObject.put("value", borderWidthRight);

		borderWidthJSONObject.put("right", borderWidthRightJSONObject);

		boolean useForAllWidth = ParamUtil.getBoolean(
			actionRequest, "useForAllWidth");

		borderWidthJSONObject.put("sameForAll", useForAllWidth);

		String borderWidthTopUnit = ParamUtil.getString(
			actionRequest, "borderWidthTopUnit");

		JSONObject borderWidthTopJSONObject = JSONUtil.put(
			"unit", borderWidthTopUnit);

		String borderWidthTop = ParamUtil.getString(
			actionRequest, "borderWidthTop");

		borderWidthTopJSONObject.put("value", borderWidthTop);

		borderWidthJSONObject.put("top", borderWidthTopJSONObject);

		borderDataJSONObject.put("borderWidth", borderWidthJSONObject);

		return borderDataJSONObject;
	}

	protected String getCSS(ActionRequest actionRequest) {
		JSONObject cssJSONObject = JSONUtil.put(
			"advancedData", getAdvancedDataJSONObject(actionRequest)
		).put(
			"bgData", getBgDataJSONObject(actionRequest)
		).put(
			"borderData", getBorderDataJSONObject(actionRequest)
		).put(
			"spacingData", getSpacingDataJSONObject(actionRequest)
		).put(
			"textData", getTextDataJSONObject(actionRequest)
		);

		return cssJSONObject.toString();
	}

	protected JSONObject getSpacingDataJSONObject(ActionRequest actionRequest) {
		String marginBottomUnit = ParamUtil.getString(
			actionRequest, "marginBottomUnit");

		JSONObject marginBottomJSONObject = JSONUtil.put(
			"unit", marginBottomUnit);

		String marginBottom = ParamUtil.getString(
			actionRequest, "marginBottom");

		marginBottomJSONObject.put("value", marginBottom);

		JSONObject marginJSONObject = JSONUtil.put(
			"bottom", marginBottomJSONObject);

		String marginLeftUnit = ParamUtil.getString(
			actionRequest, "marginLeftUnit");

		JSONObject marginLeftJSONObject = JSONUtil.put("unit", marginLeftUnit);

		String marginLeft = ParamUtil.getString(actionRequest, "marginLeft");

		marginLeftJSONObject.put("value", marginLeft);

		marginJSONObject.put("left", marginLeftJSONObject);

		String marginRightUnit = ParamUtil.getString(
			actionRequest, "marginRightUnit");

		JSONObject marginRightJSONObject = JSONUtil.put(
			"unit", marginRightUnit);

		String marginRight = ParamUtil.getString(actionRequest, "marginRight");

		marginRightJSONObject.put("value", marginRight);

		marginJSONObject.put("right", marginRightJSONObject);

		boolean useForAllMargin = ParamUtil.getBoolean(
			actionRequest, "useForAllMargin");

		marginJSONObject.put("sameForAll", useForAllMargin);

		String marginTopUnit = ParamUtil.getString(
			actionRequest, "marginTopUnit");

		JSONObject marginTopJSONObject = JSONUtil.put("unit", marginTopUnit);

		String marginTop = ParamUtil.getString(actionRequest, "marginTop");

		marginTopJSONObject.put("value", marginTop);

		marginJSONObject.put("top", marginTopJSONObject);

		JSONObject spacingDataJSONObject = JSONUtil.put(
			"margin", marginJSONObject);

		String paddingBottomUnit = ParamUtil.getString(
			actionRequest, "paddingBottomUnit");

		JSONObject paddingBottomJSONObject = JSONUtil.put(
			"unit", paddingBottomUnit);

		String paddingBottom = ParamUtil.getString(
			actionRequest, "paddingBottom");

		paddingBottomJSONObject.put("value", paddingBottom);

		JSONObject paddingJSONObject = JSONUtil.put(
			"bottom", paddingBottomJSONObject);

		String paddingLeftUnit = ParamUtil.getString(
			actionRequest, "paddingLeftUnit");

		JSONObject paddingLeftJSONObject = JSONUtil.put(
			"unit", paddingLeftUnit);

		String paddingLeft = ParamUtil.getString(actionRequest, "paddingLeft");

		paddingLeftJSONObject.put("value", paddingLeft);

		paddingJSONObject.put("left", paddingLeftJSONObject);

		String paddingRightUnit = ParamUtil.getString(
			actionRequest, "paddingRightUnit");

		JSONObject paddingRightJSONObject = JSONUtil.put(
			"unit", paddingRightUnit);

		String paddingRight = ParamUtil.getString(
			actionRequest, "paddingRight");

		paddingRightJSONObject.put("value", paddingRight);

		paddingJSONObject.put("right", paddingRightJSONObject);

		boolean useForAllPadding = ParamUtil.getBoolean(
			actionRequest, "useForAllPadding");

		paddingJSONObject.put("sameForAll", useForAllPadding);

		String paddingTopUnit = ParamUtil.getString(
			actionRequest, "paddingTopUnit");

		JSONObject paddingTopJSONObject = JSONUtil.put("unit", paddingTopUnit);

		String paddingTop = ParamUtil.getString(actionRequest, "paddingTop");

		paddingTopJSONObject.put("value", paddingTop);

		paddingJSONObject.put("top", paddingTopJSONObject);

		spacingDataJSONObject.put("padding", paddingJSONObject);

		return spacingDataJSONObject;
	}

	protected JSONObject getTextDataJSONObject(ActionRequest actionRequest) {
		String fontColor = ParamUtil.getString(actionRequest, "fontColor");

		JSONObject textDataJSONObject = JSONUtil.put("color", fontColor);

		String fontFamily = ParamUtil.getString(actionRequest, "fontFamily");

		textDataJSONObject.put("fontFamily", fontFamily);

		String fontSize = ParamUtil.getString(actionRequest, "fontSize");

		textDataJSONObject.put("fontSize", fontSize);

		boolean fontItalic = ParamUtil.getBoolean(actionRequest, "fontItalic");

		textDataJSONObject.put(
			"fontStyle", fontItalic ? "italic" : StringPool.BLANK);

		boolean fontBold = ParamUtil.getBoolean(actionRequest, "fontBold");

		textDataJSONObject.put(
			"fontWeight", fontBold ? "bold" : StringPool.BLANK);

		String letterSpacing = ParamUtil.getString(
			actionRequest, "letterSpacing");

		textDataJSONObject.put("letterSpacing", letterSpacing);

		String lineHeight = ParamUtil.getString(actionRequest, "lineHeight");

		textDataJSONObject.put("lineHeight", lineHeight);

		String textAlign = ParamUtil.getString(actionRequest, "textAlign");

		textDataJSONObject.put("textAlign", textAlign);

		String textDecoration = ParamUtil.getString(
			actionRequest, "textDecoration");

		textDataJSONObject.put("textDecoration", textDecoration);

		String wordSpacing = ParamUtil.getString(actionRequest, "wordSpacing");

		textDataJSONObject.put("wordSpacing", wordSpacing);

		return textDataJSONObject;
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.portlet.configuration.css.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletConfigurationCSSPortlet.class);

	@Reference
	private Portal _portal;

}