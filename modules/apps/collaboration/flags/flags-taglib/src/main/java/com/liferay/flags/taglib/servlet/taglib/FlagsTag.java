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

package com.liferay.flags.taglib.servlet.taglib;

import com.liferay.flags.configuration.FlagsGroupServiceConfiguration;
import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Julio Camarero
 * @author Ambrin Chaudhary
 */
public class FlagsTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

		try {
			Map<String, Object> context = getContext();

			boolean enabled = (Boolean)context.get("enabled");

			putValue("cssClass", _getCssClass(randomNamespace, enabled));

			putValue("data", _getDataJSON(context, className, classPK));

			putValue("flagsEnabled", _isFlagsEnabled(themeDisplay));

			putValue("id", randomNamespace + "id");

			putValue("enabled", enabled);

			boolean label = GetterUtil.getBoolean(context.get("label"), true);

			putValue("label", label);

			String message = GetterUtil.getString(
				context.get("message"), LanguageUtil.get(request, "flag"));

			putValue("message", message);

			putValue("pathThemeImages", themeDisplay.getPathThemeImages());

			putValue("uri", _getUri());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		setTemplateNamespace("Flags.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "flags-taglib/flags/Flags.es";
	}

	public void setClassName(String className) {
		putValue("className", className);
	}

	public void setClassPK(long classPK) {
		putValue("classPK", classPK);
	}

	public void setContentTitle(String contentTitle) {
		putValue("contentTitle", contentTitle);
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setLabel(boolean label) {
		putValue("label", label);
	}

	public void setMessage(String message) {
		putValue("message", LanguageUtil.get(request, message));
	}

	public void setReportedUserId(long reportedUserId) {
		putValue("reportedUserId", reportedUserId);
	}

	private String _getCssClass(String randomNamespace, boolean enabled) {
		String cssClass = randomNamespace;

		if (enabled) {
			cssClass = randomNamespace + " flag-enable";
		}

		return cssClass;
	}

	private String _getCurrentURL() {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		String currentURL;

		if ((portletRequest != null) && (portletResponse != null)) {
			PortletURL currentURLObj = PortletURLUtil.getCurrent(
				PortalUtil.getLiferayPortletRequest(portletRequest),
				PortalUtil.getLiferayPortletResponse(portletResponse));

			currentURL = currentURLObj.toString();
		}
		else {
			currentURL = PortalUtil.getCurrentURL(request);
		}

		return currentURL;
	}

	private JSONObject _getDataJSON(
		Map<String, Object> context, String className, long classPK) {

		String namespace = PortalUtil.getPortletNamespace(PortletKeys.FLAGS);

		JSONObject dataJSON = JSONFactoryUtil.createJSONObject();

		dataJSON.put(namespace + "className", className);
		dataJSON.put(namespace + "classPK", classPK);
		dataJSON.put(namespace + "contentTitle", context.get("contentTitle"));
		dataJSON.put(
			namespace + "contentURL",
			PortalUtil.getPortalURL(request) + _getCurrentURL());
		dataJSON.put(
			namespace + "reportedUserId", context.get("reportedUserId"));

		return dataJSON;
	}

	private String _getUri() throws WindowStateException {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, PortletKeys.FLAGS, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", "/flags/edit_entry");
		portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);

		return portletURL.toString();
	}

	private boolean _isFlagsEnabled(ThemeDisplay themeDisplay)
		throws PortalException {

		FlagsGroupServiceConfiguration flagsGroupServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				FlagsGroupServiceConfiguration.class,
				themeDisplay.getCompanyId());

		boolean flagsEnabled = false;

		if (flagsGroupServiceConfiguration.guestUsersEnabled() ||
			themeDisplay.isSignedIn()) {

			flagsEnabled = true;
		}

		return flagsEnabled;
	}

	private static final Log _log = LogFactoryUtil.getLog(FlagsTag.class);

}