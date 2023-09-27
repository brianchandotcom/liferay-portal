/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.search.cluster.StatsInformation;
import com.liferay.portal.search.cluster.StatsInformationFactory;
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

		if (_isStatsInformationAvailable()) {
			StatsInformation statsInformation =
				_statsInformationFactory.getStatsInformation();

			indexActionsDisplayContext.setAvailableDiskSpace(
				statsInformation.getAvailableDiskSpace());
			indexActionsDisplayContext.setCurrentDiskSpaceUsed(
				statsInformation.getUsedDiskSpace());
			indexActionsDisplayContext.setIsLowOnDiskSpace(
				_isLowOnDiskSpace(
					statsInformation.getAvailableDiskSpace(),
					statsInformation.getSizeOfLargestIndex()));
		}

		return indexActionsDisplayContext;
	}

	public void setStatsInformationFactory(
		StatsInformationFactory statsInformationFactory) {

		_statsInformationFactory = statsInformationFactory;
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

	private boolean _isLowOnDiskSpace(
		double availableDiskSpace, double largestIndexSize) {

		if (availableDiskSpace < (largestIndexSize * 1.5)) {
			return true;
		}

		return false;
	}

	private boolean _isStatsInformationAvailable() {
		if (_statsInformationFactory != null) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexActionsDisplayContextBuilder.class);

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final ReindexConfiguration _reindexConfiguration;
	private final RenderRequest _renderRequest;
	private final SearchCapabilities _searchCapabilities;
	private StatsInformationFactory _statsInformationFactory;

}