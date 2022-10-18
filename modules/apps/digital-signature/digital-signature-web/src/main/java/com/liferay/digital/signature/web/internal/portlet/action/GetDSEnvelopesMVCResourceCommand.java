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

package com.liferay.digital.signature.web.internal.portlet.action;

import com.liferay.digital.signature.constants.DigitalSignaturePortletKeys;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nicolas Moura
 * @author Victor Trajano
 */
@Component(
	property = {
		"javax.portlet.name=" + DigitalSignaturePortletKeys.DIGITAL_SIGNATURE,
		"mvc.command.name=/digital_signature/get_ds_envelopes"
	},
	service = MVCResourceCommand.class
)
public class GetDSEnvelopesMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Page<DSEnvelope> page = _dsEnvelopeManager.getDSEnvelopesPage(
			themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId(),
			ParamUtil.getString(resourceRequest, "from_date", "2000-01-01"),
			StringUtil.replace(
				ParamUtil.getString(resourceRequest, "keywords"),
				CharPool.SPACE, CharPool.PLUS),
			StringUtil.removeSubstring(
				ParamUtil.getString(resourceRequest, "sort", "desc"),
				"createdLocalDateTime:"),
			Pagination.of(
				ParamUtil.getInteger(resourceRequest, "page"),
				ParamUtil.getInteger(resourceRequest, "pageSize")),
			ParamUtil.getString(resourceRequest, "status"));

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, page.toString());
	}

	@Reference
	private DSEnvelopeManager _dsEnvelopeManager;

}