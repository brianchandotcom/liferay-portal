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

package com.liferay.commerce.order.rule.web.internal.portlet.action;

import com.liferay.commerce.order.rule.constants.COREntryPortletKeys;
import com.liferay.commerce.order.rule.exception.NoSuchCOREntryException;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + COREntryPortletKeys.COR_ENTRY,
		"mvc.command.name=/cor_entry/edit_cor_entry_external_reference_code"
	},
	service = MVCActionCommand.class
)
public class EditCOREntryExternalReferenceCodeMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long corEntryId = ParamUtil.getLong(actionRequest, "corEntryId");

			COREntry corEntry = _corEntryService.getCOREntry(corEntryId);

			String externalReferenceCode = ParamUtil.getString(
				actionRequest, "externalReferenceCode");

			_corEntryService.updateCOREntryExternalReferenceCode(
				externalReferenceCode, corEntry.getCOREntryId());
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCOREntryException) {
				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				_log.error(exception, exception);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCOREntryExternalReferenceCodeMVCActionCommand.class);

	@Reference
	private COREntryService _corEntryService;

}