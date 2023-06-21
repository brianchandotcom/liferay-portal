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

package com.liferay.portal.search.admin.web.internal.display.context.builder;

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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.admin.web.internal.display.context.IndexActionsDisplayContext;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.configuration.ReindexConfiguration;

import java.util.Map;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Olivia Yu
 */
public class IndexActionsDisplayContextBuilder {

	public IndexActionsDisplayContextBuilder(
		Language language, Portal portal,
		ReindexConfiguration reindexConfiguration, RenderRequest renderRequest,
		SearchCapabilities searchCapabilities) {

		_language = language;
		_portal = portal;
		_reindexConfiguration = reindexConfiguration;
		_renderRequest = renderRequest;
		_searchCapabilities = searchCapabilities;

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
			"initialCompanyIds", _getInitialCompanyIds()
		).put(
			"initialExecutionMode", _getInitialExecutionMode()
		).put(
			"initialScope", _getInitialScope()
		).put(
			"isConcurrentModeSupported",
			_searchCapabilities.isConcurrentModeSupported()
		).put(
			"virtualInstances", _getVirtualInstancesJSONArray()
		).build();
	}

	private long[] _getInitialCompanyIds() {
		return StringUtil.split(
			ParamUtil.getString(_httpServletRequest, "companyIds"), 0L);
	}

	private String _getInitialExecutionMode() {
		return ParamUtil.getString(
			_httpServletRequest, "executionMode",
			_reindexConfiguration.defaultReindexExecutionMode());
	}

	private String _getInitialScope() {
		return ParamUtil.getString(_httpServletRequest, "scope");
	}

	private JSONArray _getVirtualInstancesJSONArray() {
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
		IndexActionsDisplayContextBuilder.class);

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final ReindexConfiguration _reindexConfiguration;
	private final RenderRequest _renderRequest;
	private final SearchCapabilities _searchCapabilities;

}