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
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.version=3.0"
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

		String css = _getCSS(actionRequest);

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

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.portlet.configuration.css.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private JSONObject _getAdvancedDataJSONObject(ActionRequest actionRequest) {
		return JSONUtil.put(
			"customCSS", ParamUtil.getString(actionRequest, "customCSS")
		).put(
			"customCSSClassName",
			ParamUtil.getString(actionRequest, "customCSSClassName")
		);
	}

	private JSONObject _getBgDataJSONObject(ActionRequest actionRequest) {
		return JSONUtil.put(
			"backgroundColor",
			ParamUtil.getString(actionRequest, "backgroundColor")
		).put(
			"backgroundImage", StringPool.BLANK
		).put(
			"backgroundPosition",
			JSONUtil.put(
				"left",
				JSONUtil.put(
					"unit", StringPool.BLANK
				).put(
					"value", StringPool.BLANK
				)
			).put(
				"top",
				JSONUtil.put(
					"unit", StringPool.BLANK
				).put(
					"value", StringPool.BLANK
				)
			)
		).put(
			"backgroundRepeat", StringPool.BLANK
		).put(
			"useBgImage", false
		);
	}

	private JSONObject _getBorderDataJSONObject(ActionRequest actionRequest) {
		return JSONUtil.put(
			"borderColor",
			JSONUtil.put(
				"bottom",
				ParamUtil.getString(actionRequest, "borderColorBottom")
			).put(
				"left", ParamUtil.getString(actionRequest, "borderColorLeft")
			).put(
				"right", ParamUtil.getString(actionRequest, "borderColorRight")
			).put(
				"sameForAll",
				ParamUtil.getBoolean(actionRequest, "useForAllColor")
			).put(
				"top", ParamUtil.getString(actionRequest, "borderColorTop")
			)
		).put(
			"borderStyle",
			JSONUtil.put(
				"bottom",
				ParamUtil.getString(actionRequest, "borderStyleBottom")
			).put(
				"left", ParamUtil.getString(actionRequest, "borderStyleLeft")
			).put(
				"right", ParamUtil.getString(actionRequest, "borderStyleRight")
			).put(
				"sameForAll",
				ParamUtil.getBoolean(actionRequest, "useForAllStyle")
			).put(
				"top", ParamUtil.getString(actionRequest, "borderStyleTop")
			)
		).put(
			"borderWidth",
			JSONUtil.put(
				"bottom",
				JSONUtil.put(
					"unit",
					ParamUtil.getString(actionRequest, "borderWidthBottomUnit")
				).put(
					"value",
					ParamUtil.getString(actionRequest, "borderWidthBottom")
				)
			).put(
				"left",
				JSONUtil.put(
					"unit",
					ParamUtil.getString(actionRequest, "borderWidthLeftUnit")
				).put(
					"value",
					ParamUtil.getString(actionRequest, "borderWidthLeft")
				)
			).put(
				"right",
				JSONUtil.put(
					"unit",
					ParamUtil.getString(actionRequest, "borderWidthRightUnit")
				).put(
					"value",
					ParamUtil.getString(actionRequest, "borderWidthRight")
				)
			).put(
				"sameForAll",
				ParamUtil.getBoolean(actionRequest, "useForAllWidth")
			).put(
				"top",
				JSONUtil.put(
					"unit",
					ParamUtil.getString(actionRequest, "borderWidthTopUnit")
				).put(
					"value",
					ParamUtil.getString(actionRequest, "borderWidthTop")
				)
			)
		);
	}

	private String _getCSS(ActionRequest actionRequest) {
		return JSONUtil.put(
			"advancedData", _getAdvancedDataJSONObject(actionRequest)
		).put(
			"bgData", _getBgDataJSONObject(actionRequest)
		).put(
			"borderData", _getBorderDataJSONObject(actionRequest)
		).put(
			"spacingData", _getSpacingDataJSONObject(actionRequest)
		).put(
			"textData", _getTextDataJSONObject(actionRequest)
		).toString();
	}

	private JSONObject _getSpacingDataJSONObject(ActionRequest actionRequest) {
		return JSONUtil.put(
			"margin",
			JSONUtil.put(
				"bottom",
				JSONUtil.put(
					"unit",
					ParamUtil.getString(actionRequest, "marginBottomUnit")
				).put(
					"value", ParamUtil.getString(actionRequest, "marginBottom")
				)
			).put(
				"left",
				JSONUtil.put(
					"unit", ParamUtil.getString(actionRequest, "marginLeftUnit")
				).put(
					"value", ParamUtil.getString(actionRequest, "marginLeft")
				)
			).put(
				"right",
				JSONUtil.put(
					"unit",
					ParamUtil.getString(actionRequest, "marginRightUnit")
				).put(
					"value", ParamUtil.getString(actionRequest, "marginRight")
				)
			).put(
				"sameForAll",
				ParamUtil.getBoolean(actionRequest, "useForAllMargin")
			).put(
				"top",
				JSONUtil.put(
					"unit", ParamUtil.getString(actionRequest, "marginTopUnit")
				).put(
					"value", ParamUtil.getString(actionRequest, "marginTop")
				)
			)
		).put(
			"padding",
			JSONUtil.put(
				"bottom",
				JSONUtil.put(
					"unit",
					ParamUtil.getString(actionRequest, "paddingBottomUnit")
				).put(
					"value", ParamUtil.getString(actionRequest, "paddingBottom")
				)
			).put(
				"left",
				JSONUtil.put(
					"unit",
					ParamUtil.getString(actionRequest, "paddingLeftUnit")
				).put(
					"value", ParamUtil.getString(actionRequest, "paddingLeft")
				)
			).put(
				"right",
				JSONUtil.put(
					"unit",
					ParamUtil.getString(actionRequest, "paddingRightUnit")
				).put(
					"value", ParamUtil.getString(actionRequest, "paddingRight")
				)
			).put(
				"sameForAll",
				ParamUtil.getBoolean(actionRequest, "useForAllPadding")
			).put(
				"top",
				JSONUtil.put(
					"unit", ParamUtil.getString(actionRequest, "paddingTopUnit")
				).put(
					"value", ParamUtil.getString(actionRequest, "paddingTop")
				)
			)
		);
	}

	private JSONObject _getTextDataJSONObject(ActionRequest actionRequest) {
		String fontStyle = StringPool.BLANK;

		if (ParamUtil.getBoolean(actionRequest, "fontItalic")) {
			fontStyle = "italic";
		}

		String fontWeight = StringPool.BLANK;

		if (ParamUtil.getBoolean(actionRequest, "fontBold")) {
			fontWeight = "bold";
		}

		return JSONUtil.put(
			"color", ParamUtil.getString(actionRequest, "fontColor")
		).put(
			"fontFamily", ParamUtil.getString(actionRequest, "fontFamily")
		).put(
			"fontSize", ParamUtil.getString(actionRequest, "fontSize")
		).put(
			"fontStyle", fontStyle
		).put(
			"fontWeight", fontWeight
		).put(
			"letterSpacing", ParamUtil.getString(actionRequest, "letterSpacing")
		).put(
			"lineHeight", ParamUtil.getString(actionRequest, "lineHeight")
		).put(
			"textAlign", ParamUtil.getString(actionRequest, "textAlign")
		).put(
			"textDecoration",
			ParamUtil.getString(actionRequest, "textDecoration")
		).put(
			"wordSpacing", ParamUtil.getString(actionRequest, "wordSpacing")
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletConfigurationCSSPortlet.class);

	@Reference
	private Portal _portal;

}