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

package com.liferay.app.builder.web.internal.portlet.action;

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

import java.util.Optional;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
public abstract class BaseAppBuilderMVCResourceCommand<T>
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
						resourceRequest, resourceResponse);

					if (resultOptional.isPresent()) {
						JSONPortletResponseUtil.writeJSON(
							resourceRequest, resourceResponse,
							resultOptional.get());
					}

					return null;
				});
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
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
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
		BaseAppBuilderMVCResourceCommand.class);

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

}