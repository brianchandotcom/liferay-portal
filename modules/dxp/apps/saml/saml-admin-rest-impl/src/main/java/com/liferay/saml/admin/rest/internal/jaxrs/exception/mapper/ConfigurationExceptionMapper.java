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

package com.liferay.saml.admin.rest.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Saml.Admin.REST)",
		"osgi.jaxrs.extension=true"
	},
	service = ExceptionMapper.class
)
public class ConfigurationExceptionMapper
	extends BaseExceptionMapper<ConfigurationException> {

	@Override
	protected Problem getProblem(
		ConfigurationException configurationException) {

		return new Problem(configurationException);
	}

}