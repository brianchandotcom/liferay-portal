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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.GenericError;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseSLAExceptionMapper<T extends PortalException>
	implements ExceptionMapper<T> {

	public abstract List<GenericError> toGenericErrors(T portalException);

	@Override
	public Response toResponse(T portalException) {
		return Response.status(
			Response.Status.BAD_REQUEST
		).entity(
			toGenericErrors(portalException)
		).build();
	}

	protected String getMessage(String key) {
		return language.get(
			ResourceBundleUtil.getBundle(
				_acceptLanguage.getPreferredLocale(),
				BaseSLAExceptionMapper.class),
			key);
	}

	@Reference
	protected Language language;

	@Context
	private AcceptLanguage _acceptLanguage;

}