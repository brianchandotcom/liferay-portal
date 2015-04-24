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

package com.liferay.portlet.css.web.portlet;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletSetupUtil;
import com.liferay.portlet.css.web.upgrade.PortletCSSWebUpgrade;

import java.io.IOException;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class PortletCSSPortlet extends MVCPortlet {

	public void getLookAndFeel(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			Layout layout = themeDisplay.getLayout();

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			String portletId = ParamUtil.getString(
				resourceRequest, "portletId");

			if (!PortletPermissionUtil.contains(
					permissionChecker, layout, portletId,
					ActionKeys.CONFIGURATION)) {

				return;
			}

			PortletPreferences portletSetup =
				PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
					layout, portletId);

			JSONObject portletSetupJSONObject =
				PortletSetupUtil.cssToJSONObject(portletSetup);

			JSONObject defaultPortletTitlesJSONObject =
				JSONFactoryUtil.createJSONObject();

			for (Locale locale : LanguageUtil.getAvailableLocales(
					themeDisplay.getSiteGroupId())) {

				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);
				String languageId = LocaleUtil.toLanguageId(locale);

				defaultPortletTitlesJSONObject.put(
					languageId,
					PortalUtil.getPortletTitle(rootPortletId, languageId));
			}

			portletSetupJSONObject.put(
				"defaultPortletTitles", defaultPortletTitlesJSONObject);

			writeJSON(
				resourceRequest, resourceResponse,
				portletSetupJSONObject.toString());
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (resourceID.equals("getLookAndFeel")) {
			getLookAndFeel(resourceRequest, resourceResponse);
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

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
			PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
				layout, portletId);

		String css = ParamUtil.getString(actionRequest, "css");

		if (_log.isDebugEnabled()) {
			_log.debug("Updating css " + css);
		}

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(css);

		JSONObject portletData = jsonObj.getJSONObject("portletData");

		jsonObj.remove("portletData");

		css = jsonObj.toString();

		boolean useCustomTitle = portletData.getBoolean("useCustomTitle");
		String showBorders = portletData.getString("showBorders");
		String linkToLayoutUuid = GetterUtil.getString(
			portletData.getString("portletLinksTarget"));

		JSONObject titles = portletData.getJSONObject("titles");

		for (Locale locale : LanguageUtil.getAvailableLocales(
				themeDisplay.getSiteGroupId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			String title = null;

			if (titles.has(languageId)) {
				title = GetterUtil.getString(titles.getString(languageId));
			}

			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			String defaultPortletTitle = PortalUtil.getPortletTitle(
				rootPortletId, languageId);

			if ((title != null) &&
				!Validator.equals(defaultPortletTitle, title)) {

				portletSetup.setValue("portletSetupTitle_" + languageId, title);
			}
			else {
				portletSetup.reset("portletSetupTitle_" + languageId);
			}
		}

		portletSetup.setValue(
			"portletSetupUseCustomTitle", String.valueOf(useCustomTitle));

		if (Validator.isNotNull(showBorders)) {
			boolean showBordersBoolean = portletData.getBoolean("showBorders");

			portletSetup.setValue(
				"portletSetupShowBorders", String.valueOf(showBordersBoolean));
		}
		else {
			portletSetup.reset("portletSetupShowBorders");
		}

		if (Validator.isNotNull(linkToLayoutUuid)) {
			portletSetup.setValue(
				"portletSetupLinkToLayoutUuid", linkToLayoutUuid);
		}
		else {
			portletSetup.reset("portletSetupLinkToLayoutUuid");
		}

		portletSetup.setValue("portletSetupCss", css);

		if (PropsValues.MOBILE_DEVICE_STYLING_WAP_ENABLED) {
			JSONObject wapData = jsonObj.getJSONObject("wapData");

			String wapInitialWindowState = wapData.getString(
				"initialWindowState");
			String wapTitle = wapData.getString("title");

			portletSetup.setValue(
				"lfrWapInitialWindowState", wapInitialWindowState);
			portletSetup.setValue("lfrWapTitle", wapTitle);
		}
		else {
			portletSetup.reset("lfrWapInitialWindowState");
			portletSetup.reset("lfrWapTitle");
		}

		portletSetup.store();
	}

	@Reference(unbind = "-")
	protected void setPortletCSSWebUpgrade(
		PortletCSSWebUpgrade portletCSSWebUpgrade) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletCSSPortlet.class);

}