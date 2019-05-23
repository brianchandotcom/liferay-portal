/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.client.resource.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.http.HttpInvoker;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.InstanceSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class InstanceResource {

	public static Page<Instance> getProcessInstancesPage(
			Long processId, String[] slaStatuses, String[] statuses,
			String[] taskKeys, Integer timeRange, Pagination pagination)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getProcessInstancesPageHttpResponse(
				processId, slaStatuses, statuses, taskKeys, timeRange,
				pagination);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, InstanceSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse getProcessInstancesPageHttpResponse(
			Long processId, String[] slaStatuses, String[] statuses,
			String[] taskKeys, Integer timeRange, Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (slaStatuses != null) {
			for (int i = 0; i < slaStatuses.length; i++) {
				httpInvoker.parameter(
					"slaStatuses", String.valueOf(slaStatuses[i]));
			}
		}

		if (statuses != null) {
			for (int i = 0; i < statuses.length; i++) {
				httpInvoker.parameter("statuses", String.valueOf(statuses[i]));
			}
		}

		if (taskKeys != null) {
			for (int i = 0; i < taskKeys.length; i++) {
				httpInvoker.parameter("taskKeys", String.valueOf(taskKeys[i]));
			}
		}

		if (timeRange != null) {
			httpInvoker.parameter("timeRange", String.valueOf(timeRange));
		}

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		httpInvoker.path(
			"http://localhost:8080/o/portal-workflow-metrics/v1.0/processes/{processId}/instances",
			processId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Instance getProcessInstance(Long processId, Long instanceId)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = getProcessInstanceHttpResponse(
			processId, instanceId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return InstanceSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse getProcessInstanceHttpResponse(
			Long processId, Long instanceId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/portal-workflow-metrics/v1.0/processes/{processId}/instances/{instanceId}",
			processId, instanceId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	private static final Logger _logger = Logger.getLogger(
		InstanceResource.class.getName());

}