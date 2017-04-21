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
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.Release;
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

			String rootPortletId = PortletConstants.getRootPortletId(portletId);

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
		String borderColorBottom = ParamUtil.getString(
			actionRequest, "borderColorBottom");
		String borderColorLeft = ParamUtil.getString(
			actionRequest, "borderColorLeft");
		String borderColorRight = ParamUtil.getString(
			actionRequest, "borderColorRight");
		String borderColorTop = ParamUtil.getString(
			actionRequest, "borderColorTop");
		String borderStyleBottom = ParamUtil.getString(
			actionRequest, "borderStyleBottom");
		String borderStyleLeft = ParamUtil.getString(
			actionRequest, "borderStyleLeft");
		String borderStyleRight = ParamUtil.getString(
			actionRequest, "borderStyleRight");
		String borderStyleTop = ParamUtil.getString(
			actionRequest, "borderStyleTop");
		String borderWidthBottom = ParamUtil.getString(
			actionRequest, "borderWidthBottom");
		String borderWidthBottomUnit = ParamUtil.getString(
			actionRequest, "borderWidthBottomUnit");
		String borderWidthLeft = ParamUtil.getString(
			actionRequest, "borderWidthLeft");
		String borderWidthLeftUnit = ParamUtil.getString(
			actionRequest, "borderWidthLeftUnit");
		String borderWidthRight = ParamUtil.getString(
			actionRequest, "borderWidthRight");
		String borderWidthRightUnit = ParamUtil.getString(
			actionRequest, "borderWidthRightUnit");
		String borderWidthTop = ParamUtil.getString(
			actionRequest, "borderWidthTop");
		String borderWidthTopUnit = ParamUtil.getString(
			actionRequest, "borderWidthTopUnit");
		boolean useForAllColor = ParamUtil.getBoolean(
			actionRequest, "useForAllColor");
		boolean useForAllStyle = ParamUtil.getBoolean(
			actionRequest, "useForAllStyle");
		boolean useForAllWidth = ParamUtil.getBoolean(
			actionRequest, "useForAllWidth");

		JSONObject borderColorJSONObject = JSONFactoryUtil.createJSONObject();

		borderColorJSONObject.put("bottom", borderColorBottom);
		borderColorJSONObject.put("left", borderColorLeft);
		borderColorJSONObject.put("right", borderColorRight);
		borderColorJSONObject.put("sameForAll", useForAllColor);
		borderColorJSONObject.put("top", borderColorTop);

		JSONObject borderStyleJSONObject = JSONFactoryUtil.createJSONObject();

		borderStyleJSONObject.put("bottom", borderStyleBottom);
		borderStyleJSONObject.put("left", borderStyleLeft);
		borderStyleJSONObject.put("right", borderStyleRight);
		borderStyleJSONObject.put("sameForAll", useForAllStyle);
		borderStyleJSONObject.put("top", borderStyleTop);

		JSONObject borderWidthBottomJSONObject =
			JSONFactoryUtil.createJSONObject();

		borderWidthBottomJSONObject.put("unit", borderWidthBottomUnit);
		borderWidthBottomJSONObject.put("value", borderWidthBottom);

		JSONObject borderWidthLeftJSONObject =
			JSONFactoryUtil.createJSONObject();

		borderWidthLeftJSONObject.put("unit", borderWidthLeftUnit);
		borderWidthLeftJSONObject.put("value", borderWidthLeft);

		JSONObject borderWidthRightJSONObject =
			JSONFactoryUtil.createJSONObject();

		borderWidthRightJSONObject.put("unit", borderWidthRightUnit);
		borderWidthRightJSONObject.put("value", borderWidthRight);

		JSONObject borderWidthTopJSONObject =
			JSONFactoryUtil.createJSONObject();

		borderWidthTopJSONObject.put("unit", borderWidthTopUnit);
		borderWidthTopJSONObject.put("value", borderWidthTop);

		JSONObject borderWidthJSONObject = JSONFactoryUtil.createJSONObject();

		borderWidthJSONObject.put("bottom", borderWidthBottomJSONObject);
		borderWidthJSONObject.put("left", borderWidthLeftJSONObject);
		borderWidthJSONObject.put("right", borderWidthRightJSONObject);
		borderWidthJSONObject.put("sameForAll", useForAllWidth);
		borderWidthJSONObject.put("top", borderWidthTopJSONObject);

		JSONObject borderDataJSONObject = JSONFactoryUtil.createJSONObject();

		borderDataJSONObject.put("borderColor", borderColorJSONObject);
		borderDataJSONObject.put("borderStyle", borderStyleJSONObject);
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
		String marginBottom = ParamUtil.getString(
			actionRequest, "marginBottom");
		String marginBottomUnit = ParamUtil.getString(
			actionRequest, "marginBottomUnit");
		String marginLeft = ParamUtil.getString(actionRequest, "marginLeft");
		String marginLeftUnit = ParamUtil.getString(
			actionRequest, "marginLeftUnit");
		String marginRight = ParamUtil.getString(actionRequest, "marginRight");
		String marginRightUnit = ParamUtil.getString(
			actionRequest, "marginRightUnit");
		String marginTop = ParamUtil.getString(actionRequest, "marginTop");
		String marginTopUnit = ParamUtil.getString(
			actionRequest, "marginTopUnit");
		String paddingBottom = ParamUtil.getString(
			actionRequest, "paddingBottom");
		String paddingBottomUnit = ParamUtil.getString(
			actionRequest, "paddingBottomUnit");
		String paddingLeft = ParamUtil.getString(actionRequest, "paddingLeft");
		String paddingLeftUnit = ParamUtil.getString(
			actionRequest, "paddingLeftUnit");
		String paddingRight = ParamUtil.getString(
			actionRequest, "paddingRight");
		String paddingRightUnit = ParamUtil.getString(
			actionRequest, "paddingRightUnit");
		String paddingTop = ParamUtil.getString(actionRequest, "paddingTop");
		String paddingTopUnit = ParamUtil.getString(
			actionRequest, "paddingTopUnit");
		boolean useForAllPadding = ParamUtil.getBoolean(
			actionRequest, "useForAllPadding");
		boolean useForAllMargin = ParamUtil.getBoolean(
			actionRequest, "useForAllMargin");

		JSONObject marginBottomJSONObject = JSONFactoryUtil.createJSONObject();

		marginBottomJSONObject.put("unit", marginBottomUnit);
		marginBottomJSONObject.put("value", marginBottom);

		JSONObject marginLeftJSONObject = JSONFactoryUtil.createJSONObject();

		marginLeftJSONObject.put("unit", marginLeftUnit);
		marginLeftJSONObject.put("value", marginLeft);

		JSONObject marginRightJSONObject = JSONFactoryUtil.createJSONObject();

		marginRightJSONObject.put("unit", marginRightUnit);
		marginRightJSONObject.put("value", marginRight);

		JSONObject marginTopJSONObject = JSONFactoryUtil.createJSONObject();

		marginTopJSONObject.put("unit", marginTopUnit);
		marginTopJSONObject.put("value", marginTop);

		JSONObject marginJSONObject = JSONFactoryUtil.createJSONObject();

		marginJSONObject.put("bottom", marginBottomJSONObject);
		marginJSONObject.put("left", marginLeftJSONObject);
		marginJSONObject.put("right", marginRightJSONObject);
		marginJSONObject.put("sameForAll", useForAllMargin);
		marginJSONObject.put("top", marginTopJSONObject);

		JSONObject paddingBottomJSONObject = JSONFactoryUtil.createJSONObject();

		paddingBottomJSONObject.put("unit", paddingBottomUnit);
		paddingBottomJSONObject.put("value", paddingBottom);

		JSONObject paddingLeftJSONObject = JSONFactoryUtil.createJSONObject();

		paddingLeftJSONObject.put("unit", paddingLeftUnit);
		paddingLeftJSONObject.put("value", paddingLeft);

		JSONObject paddingRightJSONObject = JSONFactoryUtil.createJSONObject();

		paddingRightJSONObject.put("unit", paddingRightUnit);
		paddingRightJSONObject.put("value", paddingRight);

		JSONObject paddingTopJSONObject = JSONFactoryUtil.createJSONObject();

		paddingTopJSONObject.put("unit", paddingTopUnit);
		paddingTopJSONObject.put("value", paddingTop);

		JSONObject paddingJSONObject = JSONFactoryUtil.createJSONObject();

		paddingJSONObject.put("bottom", paddingBottomJSONObject);
		paddingJSONObject.put("left", paddingLeftJSONObject);
		paddingJSONObject.put("right", paddingRightJSONObject);
		paddingJSONObject.put("sameForAll", useForAllPadding);
		paddingJSONObject.put("top", paddingTopJSONObject);

		JSONObject spacingDataJSONObject = JSONFactoryUtil.createJSONObject();

		spacingDataJSONObject.put("margin", marginJSONObject);
		spacingDataJSONObject.put("padding", paddingJSONObject);

		return spacingDataJSONObject;
	}

	protected JSONObject getTextDataJSONObject(ActionRequest actionRequest) {
		boolean fontBold = ParamUtil.getBoolean(actionRequest, "fontBold");
		String fontColor = ParamUtil.getString(actionRequest, "fontColor");
		String fontFamily = ParamUtil.getString(actionRequest, "fontFamily");
		boolean fontItalic = ParamUtil.getBoolean(actionRequest, "fontItalic");
		String fontSize = ParamUtil.getString(actionRequest, "fontSize");
		String letterSpacing = ParamUtil.getString(
			actionRequest, "letterSpacing");
		String lineHeight = ParamUtil.getString(actionRequest, "lineHeight");
		String textAlign = ParamUtil.getString(actionRequest, "textAlign");
		String textDecoration = ParamUtil.getString(
			actionRequest, "textDecoration");
		String wordSpacing = ParamUtil.getString(actionRequest, "wordSpacing");

		JSONObject textDataJSONObject = JSONFactoryUtil.createJSONObject();

		textDataJSONObject.put("color", fontColor);
		textDataJSONObject.put("fontFamily", fontFamily);
		textDataJSONObject.put("fontSize", fontSize);
		textDataJSONObject.put(
			"fontStyle", fontItalic ? "italic" : StringPool.BLANK);
		textDataJSONObject.put(
			"fontWeight", fontBold ? "bold" : StringPool.BLANK);
		textDataJSONObject.put("letterSpacing", letterSpacing);
		textDataJSONObject.put("lineHeight", lineHeight);
		textDataJSONObject.put("textAlign", textAlign);
		textDataJSONObject.put("textDecoration", textDecoration);
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