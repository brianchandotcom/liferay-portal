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

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.portal.instances.service.PortalInstancesLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Olivia Yu
 */
public class IndexActionsDisplayBuilder {

	public IndexActionsDisplayBuilder(
		Http http, Language language, Portal portal,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_http = http;
		_language = language;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = portal.getHttpServletRequest(renderRequest);
	}

	public IndexActionsDisplayContext build() {
		IndexActionsDisplayContext indexActionsDisplayContext =
			new IndexActionsDisplayContext();

		indexActionsDisplayContext.setData(getData());

		return indexActionsDisplayContext;
	}

	protected Map<String, Object> getData() {
		return HashMapBuilder.<String, Object>put(
			"initialCompanyIds", getInitialCompanyIds()
		).put(
			"initialScope", getInitialScope()
		).put(
			"virtualInstances", getVirtualInstancesJSONArray()
		).build();
	}

	protected long[] getInitialCompanyIds() {
		return StringUtil.split(
			ParamUtil.getString(_httpServletRequest, "companyIds"), 0L);
	}

	protected String getInitialScope() {
		return ParamUtil.getString(_httpServletRequest, "scope");
	}

	protected JSONArray getVirtualInstancesJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		long[] companyIds = PortalInstancesLocalServiceUtil.getCompanyIds();

		if (!ArrayUtil.contains(companyIds, CompanyConstants.SYSTEM)) {
			jsonArray.put(
				JSONUtil.put(
					"id", CompanyConstants.SYSTEM
				).put(
					"name", _language.get(_httpServletRequest, "system")
				));
		}

		for (long companyId : companyIds) {
			try {
				Company company = CompanyLocalServiceUtil.getCompany(companyId);

				jsonArray.put(
					JSONUtil.put(
						"id", companyId
					).put(
						"name", company.getWebId()
					));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to get company with company ID " + companyId,
						exception);
				}
			}
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexActionsDisplayBuilder.class);

	private final Http _http;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}