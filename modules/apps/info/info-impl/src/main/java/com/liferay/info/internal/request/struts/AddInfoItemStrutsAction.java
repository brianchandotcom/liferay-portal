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

import com.liferay.info.exception.InfoFormException;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true, property = "path=/info/add_info_item",
	service = StrutsAction.class
)
public class AddInfoItemStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String className = _portal.getClassName(
			ParamUtil.getLong(originalHttpServletRequest, "classNameId"));

		try {
			InfoItemCreator<Object> infoItemCreator =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemCreator.class, className);

			infoItemCreator.createFromInfoItemFieldValues(
				InfoItemFieldValues.builder(
				).build());
		}
		catch (InfoFormValidationException infoFormValidationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(infoFormValidationException);
			}

			SessionErrors.add(
				originalHttpServletRequest,
				infoFormValidationException.getInfoFieldUniqueId(),
				infoFormValidationException);

			httpServletResponse.sendRedirect(
				httpServletRequest.getHeader(HttpHeaders.REFERER));

			return null;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to create a new info item", exception);
			}

			InfoForm infoForm = _infoItemServiceTracker.getFirstInfoItemService(
				InfoForm.class, className);

			SessionErrors.add(
				originalHttpServletRequest, infoForm.getName(),
				new InfoFormException());

			httpServletResponse.sendRedirect(
				httpServletRequest.getHeader(HttpHeaders.REFERER));

			return null;
		}

		httpServletResponse.sendRedirect(httpServletRequest.getRequestURI());

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddInfoItemStrutsAction.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Portal _portal;

}