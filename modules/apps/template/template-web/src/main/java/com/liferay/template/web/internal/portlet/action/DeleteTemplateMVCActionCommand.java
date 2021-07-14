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

package com.liferay.template.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.template.web.internal.constants.TemplatePortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TemplatePortletKeys.TEMPLATE,
		"mvc.command.name=/template/delete_template"
	},
	service = MVCActionCommand.class
)
public class DeleteTemplateMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long deleteTemplateId = ParamUtil.getLong(actionRequest, "templateId");

		try {
			_ddmTemplateService.deleteTemplate(deleteTemplateId);

			hideDefaultSuccessMessage(actionRequest);

			MultiSessionMessages.add(
				actionRequest, "templateDeleted",
				_language.format(
					_portal.getHttpServletRequest(actionRequest),
					"you-successfully-deleted-x-templates",
					new Object[] {1}));
		}
		catch (PortalException portalException) {
			SessionErrors.add(actionRequest, portalException.getClass());

			hideDefaultErrorMessage(actionRequest);
		}
	}

	@Reference
	private DDMTemplateService _ddmTemplateService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}