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

package com.liferay.portal.funcio.internal.workflow;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.funcio.internal.workflow.configuration.FuncioActionExecutorFactoryConfiguration;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutorException;
import com.liferay.portal.workflow.kaleo.runtime.util.ScriptingContextBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(factory = "funcio.action.executor", service = ActionExecutor.class)
public class FuncioActionExecutor implements ActionExecutor {

	@Override
	public void execute(
			KaleoAction kaleoAction, ExecutionContext executionContext)
		throws ActionExecutorException {

		try {
			Map<String, Object> inputObjects =
				_scriptingContextBuilder.buildScriptingContext(
					executionContext);

			JSONObject payloadJSONObject = _jsonFactory.createJSONObject();

			inputObjects.forEach(
				(k, v) -> {
					if (v instanceof Number || v instanceof String) {
						payloadJSONObject.put(k, v);

						return;
					}

					try {
						JSONObject jsonObject = _jsonFactory.createJSONObject(
							_jsonFactory.serialize(v));

						if (jsonObject.has("javaClass")) {
							if (jsonObject.has("serializable")) {
								payloadJSONObject.put(
									k,
									jsonObject.getJSONObject("serializable"));
							}
							else if (jsonObject.has("list")) {
								payloadJSONObject.put(
									k, jsonObject.getJSONArray("list"));
							}
							else if (jsonObject.has("map")) {
								payloadJSONObject.put(
									k, jsonObject.getJSONObject("map"));
							}
							else {
								payloadJSONObject.put(k, jsonObject);
							}
						}
						else {
							payloadJSONObject.put(k, jsonObject);
						}
					}
					catch (Exception exception) {
						_log.error(exception);
					}
				});

			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				executionContext.getKaleoTaskInstanceToken();

			long workflowTaskId =
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId();

			payloadJSONObject.put(
				"next-transitions",
				_workflowTaskManager.getNextTransitionNames(
					kaleoAction.getUserId(), workflowTaskId)
			).put(
				"transition-uri",
				StringBundler.concat(
					"/o/headless-admin-workflow/v1.0/workflow-tasks/",
					workflowTaskId, "/change-transition")
			).put(
				"workflowTaskId", workflowTaskId
			);

			payloadJSONObject.remove("serviceContext");
			payloadJSONObject.remove("workflowContext");

			JSONObject kaleoTaskInstanceTokenJSONObject =
				payloadJSONObject.getJSONObject("kaleoTaskInstanceToken");

			kaleoTaskInstanceTokenJSONObject.remove("workflowContext");

			@SuppressWarnings("unchecked")
			List<WorkflowTaskAssignee> assignees =
				(List<WorkflowTaskAssignee>)inputObjects.get(
					"workflowTaskAssignees");

			if (assignees.isEmpty()) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"When specifing an action of type function#<id> ",
							"there must always be a User pre-assigned to the ",
							"task ", kaleoAction));
				}

				return;
			}

			WorkflowTaskAssignee workflowTaskAssignee = assignees.get(0);

			if (workflowTaskAssignee.getAssigneeClassName() !=
					User.class.getName()) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"When specifing an action of type function#<id> ",
							"the first pre-assigment must be of type User ",
							kaleoAction));
				}

				return;
			}

			Http.Options options = new Http.Options();

			options.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
			options.setBody(
				payloadJSONObject.toString(), ContentTypes.APPLICATION_JSON,
				StringPool.UTF8);
			options.setLocation(_location);
			options.setMethod(Http.Method.POST);
			options.setTimeout(_timeout);

			_http.URLtoByteArray(options);
		}
		catch (Exception exception) {
			throw new ActionExecutorException(exception);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		FuncioActionExecutorFactoryConfiguration
			funcioActionExecutorFactoryConfiguration =
				ConfigurableUtil.createConfigurable(
					FuncioActionExecutorFactoryConfiguration.class, properties);
		Company company = _getCompany(properties);

		_location = _getLocation(
			funcioActionExecutorFactoryConfiguration,
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					company.getCompanyId(),
					funcioActionExecutorFactoryConfiguration.
						oauth2Application()));

		_timeout = _funcioActionExecutorFactoryConfiguration.timeout();
	}

	private Company _getCompany(Map<String, Object> properties)
		throws Exception {

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId > 0) {
			return _companyLocalService.getCompanyById(companyId);
		}

		String webId = (String)properties.get(
			"dxp.lxc.liferay.com.virtualInstanceId");

		if (Validator.isNotNull(webId)) {
			if (Objects.equals(webId, "default")) {
				webId = PropsValues.COMPANY_DEFAULT_WEB_ID;
			}

			return _companyLocalService.getCompanyByWebId(webId);
		}

		throw new IllegalStateException(
			"The property \"companyId\" or " +
				"\"dxp.lxc.liferay.com.virtualInstanceId\" must be set");
	}

	private String _getLocation(
		FuncioActionExecutorFactoryConfiguration
			funcioActionExecutorFactoryConfiguration,
		OAuth2Application oAuth2Application) {

		String resourcePath =
			funcioActionExecutorFactoryConfiguration.resourcePath();

		if (resourcePath.contains(Http.PROTOCOL_DELIMITER)) {
			return resourcePath;
		}

		if (resourcePath.startsWith(StringPool.SLASH)) {
			resourcePath = resourcePath.substring(1);
		}

		String homePageURL = oAuth2Application.getHomePageURL();

		if (homePageURL.endsWith(StringPool.SLASH)) {
			homePageURL = homePageURL.substring(0, homePageURL.length() - 1);
		}

		return StringBundler.concat(
			homePageURL, StringPool.SLASH, resourcePath);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FuncioActionExecutor.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	private FuncioActionExecutorFactoryConfiguration
		_funcioActionExecutorFactoryConfiguration;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	private String _location;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private ScriptingContextBuilder _scriptingContextBuilder;

	private int _timeout;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}