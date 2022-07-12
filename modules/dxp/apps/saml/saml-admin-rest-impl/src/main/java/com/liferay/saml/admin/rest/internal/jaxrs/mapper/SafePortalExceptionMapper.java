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

package com.liferay.saml.admin.rest.internal.jaxrs.mapper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.exception.CredentialException;
import com.liferay.saml.runtime.exception.EntityIdException;

import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code PortalException} to a {@code 400} error.
 *
 * @author Stian Sigvartsen
 * @review
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Saml.Admin.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Saml.Admin.REST.PortalExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class SafePortalExceptionMapper
	extends BaseExceptionMapper<PortalException> {

	@Override
	public Response toResponse(PortalException portalException) {
		portalException = _unwrapPortalException(portalException);

		if (_safePortalExceptions.contains(portalException.getClass())) {
			Problem problem = getProblem(portalException);

			return Response.status(
				problem.getStatus()
			).entity(
				problem
			).type(
				getMediaType()
			).build();
		}

		return super.toResponse(portalException);
	}

	@Override
	protected Problem getProblem(PortalException portalException) {
		return new Problem(
			Response.Status.BAD_REQUEST, portalException.getMessage());
	}

	private PortalException _unwrapPortalException(
		PortalException portalException) {

		if (portalException instanceof SamlException) {
			Throwable throwable = portalException.getCause();

			if (throwable instanceof PortalException) {
				return (PortalException)throwable;
			}
		}

		return portalException;
	}

	private final Set<Class<? extends Exception>> _safePortalExceptions =
		SetUtil.fromArray(
			ConfigurationException.class, CredentialException.class,
			EntityIdException.class);

}