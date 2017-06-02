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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
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
		"javax.portlet.init-param.template-path=/",
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

		String linkToLayoutUuid = ParamUtil.getString(
			actionRequest, "linkToLayoutUuid");
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

		if (Validator.isNotNull(linkToLayoutUuid)) {
			portletSetup.setValue(
				"portletSetupLinkToLayoutUuid", linkToLayoutUuid);
		}
		else {
			portletSetup.reset("portletSetupLinkToLayoutUuid");
		}

		if (Validator.isNotNull(portletDecoratorId)) {
			portletSetup.setValue(
				"portletSetupPortletDecoratorId", portletDecoratorId);
		}
		else {
			portletSetup.reset("portletSetupPortletDecoratorId");
		}

		portletSetup.setValue("portletSetupCss", css);

		portletSetup.store();
	}

	protected JSONObject getAdvancedDataJSONObject(
		ActionRequest actionRequest) {

		JSONObject advancedDataJSONObject = JSONFactoryUtil.createJSONObject();

		String customCSS = ParamUtil.getString(actionRequest, "customCSS");

		advancedDataJSONObject.put("customCSS", customCSS);

		String customCSSClassName = ParamUtil.getString(
			actionRequest, "customCSSClassName");

		advancedDataJSONObject.put("customCSSClassName", customCSSClassName);

		return advancedDataJSONObject;
	}

	protected JSONObject getBgDataJSONObject(ActionRequest actionRequest) {
		JSONObject bgDataJSONObject = JSONFactoryUtil.createJSONObject();

		String backgroundColor = ParamUtil.getString(
			actionRequest, "backgroundColor");

		bgDataJSONObject.put("backgroundColor", backgroundColor);

		bgDataJSONObject.put("backgroundImage", StringPool.BLANK);

		JSONObject backgroundPositionJSONObject =
			JSONFactoryUtil.createJSONObject();

		JSONObject backgroundPositionLeftJSONObject =
			JSONFactoryUtil.createJSONObject();

		backgroundPositionLeftJSONObject.put("unit", StringPool.BLANK);
		backgroundPositionLeftJSONObject.put("value", StringPool.BLANK);

		backgroundPositionJSONObject.put(
			"left", backgroundPositionLeftJSONObject);

		JSONObject backgroundPositionTopJSONObject =
			JSONFactoryUtil.createJSONObject();

		backgroundPositionTopJSONObject.put("unit", StringPool.BLANK);
		backgroundPositionTopJSONObject.put("value", StringPool.BLANK);

		backgroundPositionJSONObject.put(
			"top", backgroundPositionTopJSONObject);

		bgDataJSONObject.put(
			"backgroundPosition", backgroundPositionJSONObject);

		bgDataJSONObject.put("backgroundRepeat", StringPool.BLANK);
		bgDataJSONObject.put("useBgImage", false);

		return bgDataJSONObject;
	}

	protected JSONObject getBorderDataJSONObject(ActionRequest actionRequest) {
		JSONObject borderDataJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject borderColorJSONObject = JSONFactoryUtil.createJSONObject();

		String borderColorBottom = ParamUtil.getString(
			actionRequest, "borderColorBottom");

		borderColorJSONObject.put("bottom", borderColorBottom);

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

		borderDataJSONObject.put("borderColor", borderColorJSONObject);

		JSONObject borderStyleJSONObject = JSONFactoryUtil.createJSONObject();

		String borderStyleBottom = ParamUtil.getString(
			actionRequest, "borderStyleBottom");

		borderStyleJSONObject.put("bottom", borderStyleBottom);

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

		JSONObject borderWidthJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject borderWidthBottomJSONObject =
			JSONFactoryUtil.createJSONObject();

		String borderWidthBottomUnit = ParamUtil.getString(
			actionRequest, "borderWidthBottomUnit");

		borderWidthBottomJSONObject.put("unit", borderWidthBottomUnit);

		String borderWidthBottom = ParamUtil.getString(
			actionRequest, "borderWidthBottom");

		borderWidthBottomJSONObject.put("value", borderWidthBottom);

		borderWidthJSONObject.put("bottom", borderWidthBottomJSONObject);

		JSONObject borderWidthLeftJSONObject =
			JSONFactoryUtil.createJSONObject();

		String borderWidthLeftUnit = ParamUtil.getString(
			actionRequest, "borderWidthLeftUnit");

		borderWidthLeftJSONObject.put("unit", borderWidthLeftUnit);

		String borderWidthLeft = ParamUtil.getString(
			actionRequest, "borderWidthLeft");

		borderWidthLeftJSONObject.put("value", borderWidthLeft);

		borderWidthJSONObject.put("left", borderWidthLeftJSONObject);

		JSONObject borderWidthRightJSONObject =
			JSONFactoryUtil.createJSONObject();

		String borderWidthRightUnit = ParamUtil.getString(
			actionRequest, "borderWidthRightUnit");

		borderWidthRightJSONObject.put("unit", borderWidthRightUnit);

		String borderWidthRight = ParamUtil.getString(
			actionRequest, "borderWidthRight");

		borderWidthRightJSONObject.put("value", borderWidthRight);

		borderWidthJSONObject.put("right", borderWidthRightJSONObject);

		boolean useForAllWidth = ParamUtil.getBoolean(
			actionRequest, "useForAllWidth");

		borderWidthJSONObject.put("sameForAll", useForAllWidth);

		JSONObject borderWidthTopJSONObject =
			JSONFactoryUtil.createJSONObject();

		String borderWidthTopUnit = ParamUtil.getString(
			actionRequest, "borderWidthTopUnit");

		borderWidthTopJSONObject.put("unit", borderWidthTopUnit);

		String borderWidthTop = ParamUtil.getString(
			actionRequest, "borderWidthTop");

		borderWidthTopJSONObject.put("value", borderWidthTop);

		borderWidthJSONObject.put("top", borderWidthTopJSONObject);

		borderDataJSONObject.put("borderWidth", borderWidthJSONObject);

		return borderDataJSONObject;
	}

	protected String getCSS(ActionRequest actionRequest) {
		JSONObject cssJSONObject = JSONFactoryUtil.createJSONObject();

		cssJSONObject.put(
			"advancedData", getAdvancedDataJSONObject(actionRequest));
		cssJSONObject.put("bgData", getBgDataJSONObject(actionRequest));
		cssJSONObject.put("borderData", getBorderDataJSONObject(actionRequest));
		cssJSONObject.put(
			"spacingData", getSpacingDataJSONObject(actionRequest));

		cssJSONObject.put("textData", getTextDataJSONObject(actionRequest));

		return cssJSONObject.toString();
	}

	protected JSONObject getSpacingDataJSONObject(ActionRequest actionRequest) {
		JSONObject spacingDataJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject marginJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject marginBottomJSONObject = JSONFactoryUtil.createJSONObject();

		String marginBottomUnit = ParamUtil.getString(
			actionRequest, "marginBottomUnit");

		marginBottomJSONObject.put("unit", marginBottomUnit);

		String marginBottom = ParamUtil.getString(
			actionRequest, "marginBottom");

		marginBottomJSONObject.put("value", marginBottom);

		marginJSONObject.put("bottom", marginBottomJSONObject);

		JSONObject marginLeftJSONObject = JSONFactoryUtil.createJSONObject();

		String marginLeftUnit = ParamUtil.getString(
			actionRequest, "marginLeftUnit");

		marginLeftJSONObject.put("unit", marginLeftUnit);

		String marginLeft = ParamUtil.getString(actionRequest, "marginLeft");

		marginLeftJSONObject.put("value", marginLeft);

		marginJSONObject.put("left", marginLeftJSONObject);

		JSONObject marginRightJSONObject = JSONFactoryUtil.createJSONObject();

		String marginRightUnit = ParamUtil.getString(
			actionRequest, "marginRightUnit");

		marginRightJSONObject.put("unit", marginRightUnit);

		String marginRight = ParamUtil.getString(actionRequest, "marginRight");

		marginRightJSONObject.put("value", marginRight);

		marginJSONObject.put("right", marginRightJSONObject);

		boolean useForAllMargin = ParamUtil.getBoolean(
			actionRequest, "useForAllMargin");

		marginJSONObject.put("sameForAll", useForAllMargin);

		JSONObject marginTopJSONObject = JSONFactoryUtil.createJSONObject();

		String marginTopUnit = ParamUtil.getString(
			actionRequest, "marginTopUnit");

		marginTopJSONObject.put("unit", marginTopUnit);

		String marginTop = ParamUtil.getString(actionRequest, "marginTop");

		marginTopJSONObject.put("value", marginTop);

		marginJSONObject.put("top", marginTopJSONObject);

		spacingDataJSONObject.put("margin", marginJSONObject);

		JSONObject paddingJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject paddingBottomJSONObject = JSONFactoryUtil.createJSONObject();

		String paddingBottomUnit = ParamUtil.getString(
			actionRequest, "paddingBottomUnit");

		paddingBottomJSONObject.put("unit", paddingBottomUnit);

		String paddingBottom = ParamUtil.getString(
			actionRequest, "paddingBottom");

		paddingBottomJSONObject.put("value", paddingBottom);

		paddingJSONObject.put("bottom", paddingBottomJSONObject);

		JSONObject paddingLeftJSONObject = JSONFactoryUtil.createJSONObject();

		String paddingLeftUnit = ParamUtil.getString(
			actionRequest, "paddingLeftUnit");

		paddingLeftJSONObject.put("unit", paddingLeftUnit);

		String paddingLeft = ParamUtil.getString(actionRequest, "paddingLeft");

		paddingLeftJSONObject.put("value", paddingLeft);

		paddingJSONObject.put("left", paddingLeftJSONObject);

		JSONObject paddingRightJSONObject = JSONFactoryUtil.createJSONObject();

		String paddingRightUnit = ParamUtil.getString(
			actionRequest, "paddingRightUnit");

		paddingRightJSONObject.put("unit", paddingRightUnit);

		String paddingRight = ParamUtil.getString(
			actionRequest, "paddingRight");

		paddingRightJSONObject.put("value", paddingRight);

		paddingJSONObject.put("right", paddingRightJSONObject);

		boolean useForAllPadding = ParamUtil.getBoolean(
			actionRequest, "useForAllPadding");

		paddingJSONObject.put("sameForAll", useForAllPadding);

		JSONObject paddingTopJSONObject = JSONFactoryUtil.createJSONObject();

		String paddingTopUnit = ParamUtil.getString(
			actionRequest, "paddingTopUnit");

		paddingTopJSONObject.put("unit", paddingTopUnit);

		String paddingTop = ParamUtil.getString(actionRequest, "paddingTop");

		paddingTopJSONObject.put("value", paddingTop);

		paddingJSONObject.put("top", paddingTopJSONObject);

		spacingDataJSONObject.put("padding", paddingJSONObject);

		return spacingDataJSONObject;
	}

	protected JSONObject getTextDataJSONObject(ActionRequest actionRequest) {
		JSONObject textDataJSONObject = JSONFactoryUtil.createJSONObject();

		String fontColor = ParamUtil.getString(actionRequest, "fontColor");

		textDataJSONObject.put("color", fontColor);

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
		target = "(&(release.bundle.symbolic.name=com.liferay.portlet.configuration.css.web)(release.schema.version=1.0.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletConfigurationCSSPortlet.class);

	@Reference
	private Portal _portal;

}