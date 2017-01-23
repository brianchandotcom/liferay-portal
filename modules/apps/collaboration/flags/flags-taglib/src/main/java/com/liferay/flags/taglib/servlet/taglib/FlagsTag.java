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
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Julio Camarero
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

			putValue("enabled", inTrash);

			putValue("id", randomNamespace + "id");

			String cssClass = randomNamespace;

			if (enabled) {
				cssClass = randomNamespace + " flag-enable";
			}

			putValue("cssClass", cssClass);

			String flagsPortletNamespace = PortalUtil.getPortletNamespace(
				PortletKeys.FLAGS);
			JSONObject dataJSON = JSONFactoryUtil.createJSONObject();

			dataJSON.put(flagsPortletNamespace + "className", className);
			dataJSON.put(flagsPortletNamespace + "classPK", classPK);
			dataJSON.put(
				flagsPortletNamespace + "contentTitle",
				context.get("contentTitle"));
			dataJSON.put(
				flagsPortletNamespace + "contentURL",
				PortalUtil.getPortalURL(request) + _getCurrentURL());
			dataJSON.put(
				flagsPortletNamespace + "reportedUserId",
				context.get("reportedUserId"));

			putValue("data", dataJSON);

			if (Validator.isNull(context.get("label"))) {
				putValue("label", true);
			}

			if (Validator.isNull(context.get("message"))) {
				putValue("message", LanguageUtil.get(request, "flag"));
			}

			FlagsGroupServiceConfiguration flagsGroupServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					FlagsGroupServiceConfiguration.class,
					themeDisplay.getCompanyId());

			putValue(
				"signedUser",
				flagsGroupServiceConfiguration.guestUsersEnabled() ||
				 themeDisplay.isSignedIn());

			PortletURL renderURL = PortletURLFactoryUtil.create(
				request, PortletKeys.FLAGS, PortletRequest.RENDER_PHASE);

			renderURL.setParameter("mvcRenderCommandName", "/flags/edit_entry");
			renderURL.setWindowState(LiferayWindowState.EXCLUSIVE);

			putValue("uri", renderURL.toString());
		}
		catch (PortalException pe) {
			pe.printStackTrace();
		}
		catch (WindowStateException wse) {
		}

		putValue("pathThemeImages", themeDisplay.getPathThemeImages());

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

	private String _getCurrentURL() {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse =
			(PortletResponse)request.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

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

}