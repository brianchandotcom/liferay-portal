/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.portlet.action;

import com.liferay.portal.instances.background.task.PortalInstancesOperationType;
import com.liferay.portal.instances.background.task.constants.PortalInstancesBackgroundTaskConstants;
import com.liferay.portal.instances.constants.PortalInstancesPortletKeys;
import com.liferay.portal.instances.exception.PortalInstanceAlreadyBeingAddedException;
import com.liferay.portal.instances.web.internal.background.task.AddInstanceBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.exception.CompanyMxException;
import com.liferay.portal.kernel.exception.CompanyVirtualHostException;
import com.liferay.portal.kernel.exception.CompanyWebIdException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalInstances;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.io.Serializable;

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

		Locale locale = actionRequest.getLocale();

		hideDefaultSuccessMessage(actionRequest);

		try {
			String webId = _addBackgroundTask(actionRequest);

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

	private String _addBackgroundTask(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException.MustBeOmniadmin(permissionChecker);
		}

		String webId = ParamUtil.getString(actionRequest, "webId");
		String virtualHostname = StringUtil.toLowerCase(
			StringUtil.trim(
				ParamUtil.getString(actionRequest, "virtualHostname")));
		String mx = ParamUtil.getString(actionRequest, "mx");
		int maxUsers = ParamUtil.getInteger(actionRequest, "maxUsers");

		_companyLocalService.validateCompany(
			webId, virtualHostname, mx, maxUsers);

		String name = PortalInstancesOperationType.ADD.getBackgroundTaskName(
			webId);
		String taskExecutorClassName =
			AddInstanceBackgroundTaskExecutor.class.getName();

		int count = _backgroundTaskManager.getBackgroundTasksCount(
			BackgroundTaskConstants.GROUP_ID_DEFAULT, name,
			taskExecutorClassName, false);

		if (count > 0) {
			throw new PortalInstanceAlreadyBeingAddedException(
				"Virtual instance " + webId + " is already being added");
		}

		_backgroundTaskManager.addBackgroundTask(
			themeDisplay.getUserId(), BackgroundTaskConstants.GROUP_ID_DEFAULT,
			name, taskExecutorClassName,
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
				() -> _encryptDefaultAdminPassword(
					ParamUtil.getString(
						actionRequest, "defaultAdminPassword", null))
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
			).build(),
			new ServiceContext());

		return webId;
	}

	private String _encryptDefaultAdminPassword(String defaultAdminPassword)
		throws Exception {

		if (Validator.isNull(defaultAdminPassword)) {
			return null;
		}

		Company company = _companyLocalService.getCompanyById(
			PortalInstances.getDefaultCompanyId());

		return _encryptor.encrypt(company.getKeyObj(), defaultAdminPassword);
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
		else if (exception instanceof PrincipalException.MustBeOmniadmin) {
			return "you-must-be-an-admin-to-complete-this-action";
		}
		else if (exception instanceof
					PortalInstanceAlreadyBeingAddedException) {

			return "a-virtual-instance-with-this-web-id-is-already-being-added";
		}

		return "an-unexpected-error-occurred";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddInstanceMVCActionCommand.class);

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Encryptor _encryptor;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}