/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.portlet.action;

import com.liferay.portal.instances.constants.PortalInstancesPortletKeys;
import com.liferay.portal.instances.exception.PortalInstanceAlreadyBeingAddedException;
import com.liferay.portal.instances.manager.PortalInstanceManager;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.exception.CompanyMxException;
import com.liferay.portal.kernel.exception.CompanyVirtualHostException;
import com.liferay.portal.kernel.exception.CompanyWebIdException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán Grande
 */
@Component(
	property = {
		"jakarta.portlet.name=" + PortalInstancesPortletKeys.PORTAL_INSTANCES,
		"mvc.command.name=/portal_instances/add_instance"
	},
	service = MVCActionCommand.class
)
public class AddInstanceMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		hideDefaultSuccessMessage(actionRequest);

		Locale locale = actionRequest.getLocale();

		try {
			String webId = ParamUtil.getString(actionRequest, "webId");

			_addPortalInstance(actionRequest, webId);

			jsonObject.put(
				"startMessage",
				_language.format(
					locale, "the-virtual-instance-x-is-being-added",
					HtmlUtil.escape(webId), false));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			jsonObject.put(
				"error", _language.get(locale, _getErrorMessageKey(exception)));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private void _addPortalInstance(ActionRequest actionRequest, String webId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_portalInstanceManager.addPortalInstance(
			ParamUtil.getBoolean(actionRequest, "active"),
			ParamUtil.getString(
				actionRequest, "defaultAdminEmailAddress", null),
			ParamUtil.getString(actionRequest, "defaultAdminFirstName", null),
			ParamUtil.getString(actionRequest, "defaultAdminLastName", null),
			ParamUtil.getString(actionRequest, "defaultAdminMiddleName", null),
			ParamUtil.getString(actionRequest, "defaultAdminPassword", null),
			ParamUtil.getString(actionRequest, "defaultAdminScreenName", null),
			ParamUtil.getInteger(actionRequest, "maxUsers"),
			ParamUtil.getString(actionRequest, "mx"),
			ParamUtil.getString(actionRequest, "siteInitializerKey"),
			themeDisplay.getUserId(),
			StringUtil.toLowerCase(
				StringUtil.trim(
					ParamUtil.getString(actionRequest, "virtualHostname"))),
			webId);
	}

	private String _getErrorMessageKey(Exception exception) {
		if (exception instanceof CompanyMaxUsersException) {
			return "please-enter-a-valid-max-users";
		}
		else if (exception instanceof CompanyMxException) {
			return "please-enter-a-valid-mail-domain";
		}
		else if (exception instanceof CompanyVirtualHostException) {
			return "please-enter-a-valid-virtual-host";
		}
		else if (exception instanceof CompanyWebIdException) {
			return "please-enter-a-valid-web-id";
		}
		else if (exception instanceof
					PortalInstanceAlreadyBeingAddedException) {

			return "a-virtual-instance-with-this-web-id-is-already-being-added";
		}
		else if (exception instanceof PrincipalException.MustBeOmniadmin) {
			return "you-must-be-an-admin-to-complete-this-action";
		}

		return "an-unexpected-error-occurred";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddInstanceMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private PortalInstanceManager _portalInstanceManager;

}