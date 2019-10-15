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

package com.liferay.portal.workflow.metrics.rest.internal.jaxrs.exception.mapper;

import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionTimeframeException;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.GenericError;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Portal.Workflow.Metrics.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Portal.Workflow.Metrics.REST.SLATimeframeExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class SLATimeframeExceptionMapper
	extends BaseSLAExceptionMapper
		<WorkflowMetricsSLADefinitionTimeframeException> {

	@Override
	public List<GenericError> toGenericErrors(
		WorkflowMetricsSLADefinitionTimeframeException
			workflowMetricsSLADefinitionTimeframeException) {

		List<GenericError> genericErrors = Stream.of(
			workflowMetricsSLADefinitionTimeframeException.getFieldNames()
		).flatMap(
			List::parallelStream
		).map(
			fieldName -> {
				GenericError genericError = new GenericError();

				genericError.setFieldName(fieldName);
				genericError.setMessage(
					getMessage("selected-option-is-no-longer-available"));

				return genericError;
			}
		).collect(
			Collectors.toList()
		);

		genericErrors.add(
			new GenericError() {
				{
					message = SLATimeframeExceptionMapper.this.getMessage(
						"the-time-frame-options-changed-in-the-workflow-" +
							"definition");
				}
			});

		return genericErrors;
	}

}