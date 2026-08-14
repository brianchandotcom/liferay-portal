/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.instances.web.internal.background.task.AddVirtualInstanceBackgroundTaskExecutor;
import com.liferay.portal.instances.web.internal.constants.PortalInstancesBackgroundTaskConstants;
import com.liferay.portal.instances.web.internal.constants.PortalInstancesPortletKeys;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
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
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

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

		Locale locale = actionRequest.getLocale();

		hideDefaultSuccessMessage(actionRequest);

		try {
			_addBackgroundTask(actionRequest);

			jsonObject.put(
				"successMessage",
				_language.format(
					locale, "the-x-operation-has-started-successfully", "add"));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			String errorMessage = "an-unexpected-error-occurred";

			if (exception instanceof CompanyMaxUsersException) {
				errorMessage = "please-enter-a-valid-max-users";
			}
			else if (exception instanceof CompanyMxException) {
				errorMessage = "please-enter-a-valid-mail-domain";
			}
			else if (exception instanceof CompanyVirtualHostException) {
				errorMessage = "please-enter-a-valid-virtual-host";
			}
			else if (exception instanceof CompanyWebIdException) {
				errorMessage = "please-enter-a-valid-web-id";
			}

			jsonObject.put("error", _language.get(locale, errorMessage));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private void _addBackgroundTask(ActionRequest actionRequest)
		throws Exception {

		String webId = ParamUtil.getString(actionRequest, "webId");
		String virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(
				ParamUtil.getString(actionRequest, "virtualHostname")));
		String mx = ParamUtil.getString(actionRequest, "mx");
		int maxUsers = ParamUtil.getInteger(actionRequest, "maxUsers");

		_companyLocalService.validateWebId(webId);
		_companyLocalService.validateVirtualHost(webId, virtualHostname);
		_companyLocalService.validateMx(-1, mx);
		_companyLocalService.validateMaxUsers(maxUsers);

		Map<String, Serializable> taskContextMap =
			HashMapBuilder.<String, Serializable>put(
				PortalInstancesBackgroundTaskConstants.ACTIVE,
				ParamUtil.getBoolean(actionRequest, "active")
			).put(
				PortalInstancesBackgroundTaskConstants.
					DEFAULT_ADMIN_EMAIL_ADDRESS,
				() -> ParamUtil.getString(
					actionRequest, "defaultAdminEmailAddress", null)
			).put(
				PortalInstancesBackgroundTaskConstants.DEFAULT_ADMIN_FIRST_NAME,
				() -> ParamUtil.getString(
					actionRequest, "defaultAdminFirstName", null)
			).put(
				PortalInstancesBackgroundTaskConstants.DEFAULT_ADMIN_LAST_NAME,
				() -> ParamUtil.getString(
					actionRequest, "defaultAdminLastName", null)
			).put(
				PortalInstancesBackgroundTaskConstants.
					DEFAULT_ADMIN_MIDDLE_NAME,
				() -> ParamUtil.getString(
					actionRequest, "defaultAdminMiddleName", null)
			).put(
				PortalInstancesBackgroundTaskConstants.DEFAULT_ADMIN_PASSWORD,
				() -> ParamUtil.getString(
					actionRequest, "defaultAdminPassword", null)
			).put(
				PortalInstancesBackgroundTaskConstants.
					DEFAULT_ADMIN_SCREEN_NAME,
				() -> ParamUtil.getString(
					actionRequest, "defaultAdminScreenName", null)
			).put(
				PortalInstancesBackgroundTaskConstants.MAX_USERS, maxUsers
			).put(
				PortalInstancesBackgroundTaskConstants.MX, mx
			).put(
				PortalInstancesBackgroundTaskConstants.SITE_INITIALIZER_KEY,
				ParamUtil.getString(actionRequest, "siteInitializerKey")
			).put(
				PortalInstancesBackgroundTaskConstants.VIRTUAL_HOSTNAME,
				virtualHostname
			).put(
				PortalInstancesBackgroundTaskConstants.WEB_ID, webId
			).build();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_backgroundTaskManager.addBackgroundTask(
			themeDisplay.getUserId(), BackgroundTaskConstants.GROUP_ID_DEFAULT,
			PortalInstancesBackgroundTaskConstants.NAME_ADD_VIRTUAL_INSTANCE +
				StringPool.POUND + webId,
			AddVirtualInstanceBackgroundTaskExecutor.class.getName(),
			taskContextMap, new ServiceContext());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddInstanceMVCActionCommand.class);

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}