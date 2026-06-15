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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
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

		String sseEventSinkKey = GetterUtil.getString(
			workflowContext.get("sseEventSinkKey"));

		try {
			String url = VariablesUtil.applyInputVariables(
				executionContext, "url", kaleoNodeSettingValues);

			KaleoInstanceToken kaleoInstanceToken =
				executionContext.getKaleoInstanceToken();

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
			options.setTimeout(
				GetterUtil.getInteger(
					kaleoNodeSettingValues.get("timeout"), 10000));

			String responseBody = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			int responseCode = response.getResponseCode();

			if ((responseCode < 200) || (responseCode >= 300)) {
				throw new PortalException(
					StringBundler.concat(
						"HTTP call node \"", currentKaleoNode.getName(),
						"\" failed with response code ", responseCode));
			}

			VariablesUtil.applyOutputVariables(
				VariablesUtil.getVariablesJSONArray(
					"outputVariables", kaleoNodeSettingValues),
				responseBody, workflowContext);

			if (Validator.isNotNull(sseEventSinkKey)) {
				SseUtil.send(
					currentKaleoNode.getName(), "Node Completed", null,
					sseEventSinkKey);
			}
		}
		catch (Exception exception) {
			if (Validator.isNotNull(sseEventSinkKey)) {
				SseUtil.send(
					currentKaleoNode.getName(), "Node Failed", null,
					sseEventSinkKey);
			}

			if (exception instanceof PortalException) {
				throw (PortalException)exception;
			}

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
	private KaleoNodeSettingLocalService _kaleoNodeSettingLocalService;

}