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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.List;

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
		"osgi.jaxrs.name=Liferay.Headless.Admin.Workflow.RequiredWorkflowDefinitionExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class RequiredWorkflowDefinitionExceptionMapper
	extends BaseExceptionMapper<RequiredWorkflowDefinitionException> {

	@Override
	protected Problem getProblem(
		RequiredWorkflowDefinitionException
			requiredWorkflowDefinitionException) {

		return new Problem(
			Response.Status.BAD_REQUEST,
			_language.format(
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					_acceptLanguage.getPreferredLocale(),
					RequiredWorkflowDefinitionExceptionMapper.class),
				_getMessageKey(requiredWorkflowDefinitionException),
				_getMessageArguments(requiredWorkflowDefinitionException)));
	}

	private String _getLocalizedAssetName(String className) {
		return ResourceActionsUtil.getModelResource(
			_acceptLanguage.getPreferredLocale(), className);
	}

	private Object[] _getMessageArguments(
		RequiredWorkflowDefinitionException
			requiredWorkflowDefinitionException) {

		List<WorkflowDefinitionLink> workflowDefinitionLinks =
			requiredWorkflowDefinitionException.getWorkflowDefinitionLinks();

		if (workflowDefinitionLinks.isEmpty()) {
			return new Object[0];
		}
		else if (workflowDefinitionLinks.size() == 1) {
			WorkflowDefinitionLink workflowDefinitionLink =
				workflowDefinitionLinks.get(0);

			return new Object[] {
				_getLocalizedAssetName(workflowDefinitionLink.getClassName()),
				StringPool.BLANK
			};
		}
		else if (workflowDefinitionLinks.size() == 2) {
			WorkflowDefinitionLink workflowDefinitionLink1 =
				workflowDefinitionLinks.get(0);
			WorkflowDefinitionLink workflowDefinitionLink2 =
				workflowDefinitionLinks.get(1);

			return new Object[] {
				_getLocalizedAssetName(workflowDefinitionLink1.getClassName()),
				_getLocalizedAssetName(workflowDefinitionLink2.getClassName()),
				StringPool.BLANK
			};
		}
		else {
			int moreAssets = workflowDefinitionLinks.size() - 2;

			WorkflowDefinitionLink workflowDefinitionLink1 =
				workflowDefinitionLinks.get(0);
			WorkflowDefinitionLink workflowDefinitionLink2 =
				workflowDefinitionLinks.get(1);

			return new Object[] {
				_getLocalizedAssetName(workflowDefinitionLink1.getClassName()),
				_getLocalizedAssetName(workflowDefinitionLink2.getClassName()),
				moreAssets, StringPool.BLANK
			};
		}
	}

	private String _getMessageKey(
		RequiredWorkflowDefinitionException
			requiredWorkflowDefinitionException) {

		List<WorkflowDefinitionLink> workflowDefinitionLinks =
			requiredWorkflowDefinitionException.getWorkflowDefinitionLinks();

		if (workflowDefinitionLinks.isEmpty()) {
			return StringPool.BLANK;
		}
		else if (workflowDefinitionLinks.size() == 1) {
			return "workflow-in-use-remove-assignement-to-x-x";
		}
		else if (workflowDefinitionLinks.size() == 2) {
			return "workflow-in-use-remove-assignements-to-x-and-x-x";
		}

		return "workflow-in-use-remove-assignements-to-x-x-and-x-more-x";
	}

	@Context
	private AcceptLanguage _acceptLanguage;

	@Reference
	private Language _language;

}