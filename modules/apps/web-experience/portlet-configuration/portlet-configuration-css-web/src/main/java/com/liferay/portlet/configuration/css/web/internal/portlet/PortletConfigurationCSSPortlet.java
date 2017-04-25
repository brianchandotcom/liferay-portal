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
import com.liferay.portal.kernel.portlet.PortletSetupUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.configuration.css.web.internal.constants.PortletConfigurationCSSPortletKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

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
		"javax.portlet.name=" + PortletConfigurationCSSPortletKeys.PORTLET_CONFIGURATION_CSS,
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class PortletConfigurationCSSPortlet extends MVCPortlet {

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
				themeDisplay.getStrictLayoutPortletSetup(layout, portletId);

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
					_portal.getPortletTitle(rootPortletId, languageId));
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
			themeDisplay.getStrictLayoutPortletSetup(layout, portletId);

		String css = ParamUtil.getString(actionRequest, "css");

		if (_log.isDebugEnabled()) {
			_log.debug("Updating css " + css);
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(css);

		JSONObject portletDataJSONObject = jsonObject.getJSONObject(
			"portletData");

		jsonObject.remove("portletData");

		css = jsonObject.toString();

		String linkToLayoutUuid = GetterUtil.getString(
			portletDataJSONObject.getString("portletLinksTarget"));
		String portletDecoratorId = portletDataJSONObject.getString(
			"portletDecoratorId");
		JSONObject titlesJSONObject = portletDataJSONObject.getJSONObject(
			"titles");
		boolean useCustomTitle = portletDataJSONObject.getBoolean(
			"useCustomTitle");

		Set<Locale> locales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String title = null;

			if (titlesJSONObject.has(languageId)) {
				title = GetterUtil.getString(
					titlesJSONObject.getString(languageId));
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