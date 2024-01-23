/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys;

import java.io.IOException;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 * @author Gustavo Lima
 */
@Component(
	property = {
		"javax.portlet.name=" + ResultRankingsPortletKeys.RESULT_RANKINGS,
		"mvc.command.name=/result_rankings/get_sites"
	},
	service = MVCResourceCommand.class
)
public class GetSitesMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			_writeJSONPortletResponse(
				resourceRequest, resourceResponse,
				_getJSONObject(resourceRequest, resourceResponse));

			return false;
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new RuntimeException(exception);
		}
	}

	protected JSONObject getSiteByExternalReferenceCodeJSONObject(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = _groupService.fetchGroupByExternalReferenceCode(
			ParamUtil.getString(resourceRequest, "externalReferenceCode"),
			themeDisplay.getCompanyId());

		if (group == null) {
			return null;
		}

		return JSONUtil.put(
			"descriptiveName",
			group.getDescriptiveName(themeDisplay.getLocale())
		).put(
			"externalReferenceCode", group.getExternalReferenceCode()
		).put(
			"groupId", group.getGroupId()
		).put(
			"name", group.getName(themeDisplay.getLocale())
		);
	}

	private JSONObject _getJSONObject(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		if (cmd.equals("getSiteByExternalReferenceCodeJSONObject")) {
			return getSiteByExternalReferenceCodeJSONObject(
				resourceRequest, resourceResponse);
		}

		return null;
	}

	private void _writeJSONPortletResponse(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		JSONObject jsonObject) {

		if (jsonObject == null) {
			return;
		}

		try {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetSitesMVCResourceCommand.class);

	@Reference
	private GroupService _groupService;

}