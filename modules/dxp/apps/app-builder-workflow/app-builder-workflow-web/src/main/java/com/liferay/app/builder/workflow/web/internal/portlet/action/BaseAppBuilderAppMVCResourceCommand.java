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

package com.liferay.app.builder.workflow.web.internal.portlet.action;

import com.liferay.app.builder.workflow.exception.DuplicateAppBuilderWorkflowTaskLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.workflow.exception.IncompleteWorkflowInstancesException;
import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

import java.util.Optional;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseAppBuilderAppMVCResourceCommand<T>
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					Optional<T> resultOptional = doTransactionalCommand(
						resourceRequest);

					if (resultOptional.isPresent()) {
						JSONPortletResponseUtil.writeJSON(
							resourceRequest, resourceResponse,
							resultOptional.get());
					}

					return null;
				});
		}
		catch (DuplicateAppBuilderWorkflowTaskLinkException |
			   KaleoDefinitionValidationException.DuplicateNode exception) {

			_handleException(
				language.get(
					ResourceBundleUtil.getModuleAndPortalResourceBundle(
						portal.getLocale(resourceRequest), getClass()),
					"step-names-must-be-unique"),
				resourceRequest, resourceResponse, exception);
		}
		catch (IncompleteWorkflowInstancesException
					incompleteWorkflowInstancesException) {

			String message =
				"there-are-x-pending-entries-using-this-workflow-app";

			int workflowInstancesCount =
				incompleteWorkflowInstancesException.
					getWorkflowInstancesCount();

			if (workflowInstancesCount == 1) {
				message = "there-is-a-pending-entry-using-this-workflow-app";
			}

			_handleException(
				language.format(
					ResourceBundleUtil.getModuleAndPortalResourceBundle(
						portal.getLocale(resourceRequest), getClass()),
					message, workflowInstancesCount),
				resourceRequest, resourceResponse,
				incompleteWorkflowInstancesException);
		}
		catch (PortalException portalException) {
			_handleException(
				portalException.getMessage(), resourceRequest, resourceResponse,
				portalException);
		}
		catch (Throwable throwable) {
			_handleException(
				language.get(
					portal.getHttpServletRequest(resourceRequest),
					"your-request-failed-to-complete"),
				resourceRequest, resourceResponse, throwable);
		}
	}

	protected abstract Optional<T> doTransactionalCommand(
			ResourceRequest resourceRequest)
		throws Exception;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Language language;

	@Reference
	protected Portal portal;

	private void _handleException(
			String errorMessage, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, Throwable throwable)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(throwable, throwable);
		}

		HttpServletResponse httpServletResponse = portal.getHttpServletResponse(
			resourceResponse);

		httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put("errorMessage", errorMessage));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAppBuilderAppMVCResourceCommand.class);

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

}