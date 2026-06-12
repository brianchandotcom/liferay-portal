/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node;

import com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util.VariablesUtil;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.graph.PathElement;
import com.liferay.portal.workflow.kaleo.runtime.node.BaseNodeExecutor;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeSettingLocalService;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fabian Bouché
 * @author Iliyan Peychev
 */
@Component(service = NodeExecutor.class)
public class HTTPRequestNodeExecutor extends BaseNodeExecutor {

	@Override
	public NodeType getNodeType() {
		return NodeType.HTTP_REQUEST;
	}

	@Override
	protected boolean doEnter(
		KaleoNode currentKaleoNode, ExecutionContext executionContext) {

		return true;
	}

	@Override
	protected void doExecute(
			KaleoNode currentKaleoNode, ExecutionContext executionContext,
			List<PathElement> remainingPathElements)
		throws PortalException {

		Map<String, String> kaleoNodeSettingValues = new HashMap<>();

		List<KaleoNodeSetting> kaleoNodeSettings =
			_kaleoNodeSettingLocalService.getKaleoNodeSettings(
				currentKaleoNode.getKaleoNodeId());

		for (KaleoNodeSetting kaleoNodeSetting : kaleoNodeSettings) {
			kaleoNodeSettingValues.put(
				kaleoNodeSetting.getName(), kaleoNodeSetting.getValue());
		}

		Map<String, Serializable> workflowContext =
			executionContext.getWorkflowContext();

		try {
			String url = VariablesUtil.applyInputVariables(
				executionContext, "url", kaleoNodeSettingValues);

			KaleoInstanceToken kaleoInstanceToken =
				executionContext.getKaleoInstanceToken();

			if (url.startsWith("/")) {
				Company company = _companyLocalService.getCompany(
					kaleoInstanceToken.getCompanyId());

				String portalURL = _portal.getPortalURL(
					company.getVirtualHostname(),
					_portal.getPortalServerPort(false), false);

				url = portalURL + url;
			}

			Http.Options options = new Http.Options();

			String userToken = _decryptUserToken(
				kaleoInstanceToken.getCompanyId(),
				GetterUtil.getString(workflowContext.get("userToken")));

			if (Validator.isNotNull(userToken)) {
				options.addHeader(
					"Liferay-AI-Hub-Cell-On-Behalf-Of", userToken);
			}

			String requestBody = VariablesUtil.applyInputVariables(
				executionContext, "requestBody", kaleoNodeSettingValues, true);

			if (Validator.isNotNull(requestBody)) {
				options.addHeader(
					HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
				options.setBody(
					requestBody, ContentTypes.APPLICATION_JSON, "UTF-8");
			}

			options.setLocation(url);
			options.setMethod(
				Http.Method.valueOf(
					StringUtil.toUpperCase(
						GetterUtil.getString(
							kaleoNodeSettingValues.get("httpMethod"), "GET"))));

			String responseBody = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			int responseCode = response.getResponseCode();

			if ((responseCode < 200) || (responseCode >= 300)) {
				throw new PortalException(
					StringBundler.concat(
						"HTTP call node \"", currentKaleoNode.getName(),
						"\" failed with response code ", responseCode));
			}

			_applyOutputVariables(
				VariablesUtil.getVariablesJSONArray(
					"outputVariables", kaleoNodeSettingValues),
				responseBody, workflowContext);

			String sseEventSinkKey = GetterUtil.getString(
				workflowContext.get("sseEventSinkKey"));

			if (Validator.isNotNull(sseEventSinkKey)) {
				SseUtil.send(
					currentKaleoNode.getName(), "Node Completed", null,
					sseEventSinkKey);
			}
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Exception exception) {
			throw new PortalException(
				StringBundler.concat(
					"Unable to execute HTTP call node \"",
					currentKaleoNode.getName(), "\""),
				exception);
		}

		KaleoTransition kaleoTransition =
			currentKaleoNode.getDefaultKaleoTransition();

		remainingPathElements.add(
			new PathElement(
				currentKaleoNode, kaleoTransition.getTargetKaleoNode(),
				new ExecutionContext(
					executionContext.getKaleoInstanceToken(), workflowContext,
					executionContext.getServiceContext())));
	}

	@Override
	protected void doExit(
		KaleoNode currentKaleoNode, ExecutionContext executionContext,
		List<PathElement> remainingPathElements) {
	}

	private void _applyOutputVariables(
			JSONArray outputVariablesJSONArray, String responseBody,
			Map<String, Serializable> workflowContext)
		throws Exception {

		if ((outputVariablesJSONArray == null) ||
			(outputVariablesJSONArray.length() == 0)) {

			return;
		}

		JSONObject responseJSONObject = null;

		for (int i = 0; i < outputVariablesJSONArray.length(); i++) {
			JSONObject outputVariableJSONObject =
				outputVariablesJSONArray.getJSONObject(i);

			String name = outputVariableJSONObject.getString("name");

			String path = outputVariableJSONObject.getString("path");

			if (Validator.isNull(path)) {
				workflowContext.put(name, responseBody);

				continue;
			}

			if (responseJSONObject == null) {
				responseJSONObject = _jsonFactory.createJSONObject(
					responseBody);
			}

			if (path.startsWith("output.")) {
				path = path.substring("output.".length());
			}

			Object value = responseJSONObject.get(path);

			if (value != null) {
				workflowContext.put(name, String.valueOf(value));
			}
		}
	}

	private String _decryptUserToken(long companyId, String encryptedUserToken)
		throws Exception {

		if (Validator.isNull(encryptedUserToken)) {
			return null;
		}

		Company company = _companyLocalService.getCompany(companyId);

		return EncryptorUtil.decrypt(company.getKeyObj(), encryptedUserToken);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private KaleoNodeSettingLocalService _kaleoNodeSettingLocalService;

	@Reference
	private Portal _portal;

}