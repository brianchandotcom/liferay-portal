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

package com.liferay.portal.workflow.metrics.rest.internal.jaxrs.context.resolver.v1_0;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.ProcessImpl;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.SLAImpl;

import javax.annotation.Generated;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Component(
	property = {
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=portal-workflow-metrics-rest-application)",
		"osgi.jaxrs.name=portal-workflow-metrics-rest-application.v1_0.ObjectMapperContextResolver"
	},
	service = ContextResolver.class
)
@Generated("")
@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

	public ObjectMapper getContext(Class<?> clazz) {
		return _objectMapper;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			enable(SerializationFeature.INDENT_OUTPUT);
			registerModule(
				new SimpleModule("portal-workflow-metrics-rest-application", Version.unknownVersion()) {
					{
						setAbstractTypes(
							new SimpleAbstractTypeResolver() {
								{
									addMapping(Process.class, ProcessImpl.class);
									addMapping(SLA.class, SLAImpl.class);
	}
							});
	}
				});
			setDateFormat(new ISO8601DateFormat());
	}
	};

}