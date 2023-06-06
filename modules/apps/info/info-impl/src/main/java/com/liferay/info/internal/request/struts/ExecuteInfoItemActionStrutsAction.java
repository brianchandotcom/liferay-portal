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

package com.liferay.info.internal.request.struts;

import com.liferay.info.exception.InfoItemActionExecutionException;
import com.liferay.info.exception.InfoItemActionExecutionInvalidLayoutModeException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.action.executor.InfoItemActionExecutor;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "path=/portal/execute_info_item_action",
	service = StrutsAction.class
)
public class ExecuteInfoItemActionStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-169992")) {
			return null;
		}

		try {
			if (!Objects.equals(
					Constants.VIEW,
					ParamUtil.getString(httpServletRequest, "p_l_mode"))) {

				throw new InfoItemActionExecutionInvalidLayoutModeException();
			}

			Layout layout = _layoutLocalService.fetchLayout(
				ParamUtil.getLong(httpServletRequest, "plid"));

			if ((layout == null) || layout.isDraftLayout()) {
				throw new InfoItemActionExecutionInvalidLayoutModeException();
			}

			InfoItemActionExecutor<Object> infoItemActionExecutor =
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemActionExecutor.class,
					_portal.getClassName(
						ParamUtil.getLong(httpServletRequest, "classNameId")));

			if (infoItemActionExecutor == null) {
				throw new InfoItemActionExecutionException();
			}

			infoItemActionExecutor.executeInfoItemAction(
				new ClassPKInfoItemIdentifier(
					ParamUtil.getLong(httpServletRequest, "classPK")),
				ParamUtil.getString(httpServletRequest, "fieldId"));

			ServletResponseUtil.write(httpServletResponse, "{}");
		}
		catch (InfoItemActionExecutionException
					infoItemActionExecutionException) {

			if (_log.isDebugEnabled()) {
				_log.debug(infoItemActionExecutionException);
			}

			ServletResponseUtil.write(
				httpServletResponse,
				JSONUtil.put(
					"error",
					infoItemActionExecutionException.getLocalizedMessage(
						httpServletRequest.getLocale())
				).toString());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			InfoItemActionExecutionException infoItemActionExecutionException =
				new InfoItemActionExecutionException();

			ServletResponseUtil.write(
				httpServletResponse,
				JSONUtil.put(
					"error",
					infoItemActionExecutionException.getLocalizedMessage(
						httpServletRequest.getLocale())
				).toString());
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExecuteInfoItemActionStrutsAction.class);

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}