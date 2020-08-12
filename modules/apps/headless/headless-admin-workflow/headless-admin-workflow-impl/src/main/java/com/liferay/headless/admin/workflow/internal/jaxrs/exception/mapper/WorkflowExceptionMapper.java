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

package com.liferay.headless.admin.workflow.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTaskDueDateException;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Admin.Workflow)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Admin.Workflow.WorkflowExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class WorkflowExceptionMapper
	extends BaseExceptionMapper<WorkflowException> {

	@Override
	protected Problem getProblem(WorkflowException workflowException) {
		return new Problem(
			Response.Status.BAD_REQUEST, _getMessage(workflowException));
	}

	private String _getMessage(WorkflowException workflowException) {
		Throwable rootThrowable = _getRootThrowable(workflowException);

		if (rootThrowable instanceof WorkflowTaskDueDateException) {
			return _language.get(
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					_acceptLanguage.getPreferredLocale(),
					WorkflowExceptionMapper.class),
				"please-enter-a-valid-due-date");
		}

		return rootThrowable.getMessage();
	}

	private Throwable _getRootThrowable(Throwable throwable) {
		if ((throwable.getCause() == null) ||
			(!(throwable.getCause() instanceof IllegalArgumentException) &&
			 !(throwable.getCause() instanceof NoSuchRoleException) &&
			 !(throwable.getCause() instanceof
				 PrincipalException.MustBeCompanyAdmin) &&
			 !(throwable.getCause() instanceof
				 PrincipalException.MustBeOmniadmin) &&
			 !(throwable.getCause() instanceof WorkflowException))) {

			return throwable;
		}

		return _getRootThrowable(throwable.getCause());
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	@Reference
	private Language _language;

}