/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.web.internal.portlet.action;

import com.liferay.audience.constants.AudiencePortletKeys;
import com.liferay.audience.exception.AudienceEntryNameException;
import com.liferay.audience.exception.NoSuchAudienceEntryException;
import com.liferay.audience.model.AudienceEntry;
import com.liferay.audience.service.AudienceEntryService;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"jakarta.portlet.name=" + AudiencePortletKeys.AUDIENCE,
		"mvc.command.name=/audience/update_audience_entry"
	},
	service = MVCActionCommand.class
)
public class UpdateAudienceEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long audienceEntryId = ParamUtil.getLong(
			actionRequest, "audienceEntryId");

		String json = ParamUtil.getString(actionRequest, "json");
		String name = ParamUtil.getString(actionRequest, "name");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AudienceEntry.class.getName(), actionRequest);

		try {
			AudienceEntry audienceEntry = null;

			if (audienceEntryId <= 0) {
				audienceEntry = _audienceEntryService.addAudienceEntry(
					null, json, name, serviceContext);
			}
			else {
				audienceEntry = _audienceEntryService.updateAudienceEntry(
					audienceEntryId, json, name);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				redirect = HttpComponentsUtil.setParameter(
					redirect, "audienceEntryId",
					audienceEntry.getAudienceEntryId());
			}

			boolean saveAndContinue = ParamUtil.get(
				actionRequest, "saveAndContinue", false);

			if (saveAndContinue) {
				redirect = _getSaveAndContinueRedirect(
					actionRequest, audienceEntry, redirect);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchAudienceEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof AudienceEntryNameException) {
				SessionErrors.add(
					actionRequest, exception.getClass(), exception);

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/audience/edit_audience_entry");
			}
			else {
				throw exception;
			}
		}
	}

	private String _getSaveAndContinueRedirect(
		ActionRequest actionRequest, AudienceEntry audienceEntry,
		String redirect) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(actionRequest);

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createRenderURL(
				_portal.getPortletId(actionRequest))
		).setMVCRenderCommandName(
			"/audience/edit_audience_entry"
		).setCMD(
			Constants.UPDATE
		).setRedirect(
			redirect
		).setParameter(
			"audienceEntryId", audienceEntry.getAudienceEntryId()
		).setWindowState(
			actionRequest.getWindowState()
		).buildString();
	}

	@Reference
	private AudienceEntryService _audienceEntryService;

	@Reference
	private Portal _portal;

}