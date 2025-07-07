/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.display.context;

import com.liferay.osb.patcher.constants.WorkflowConstants;
import com.liferay.osb.patcher.model.PatcherFixPack;
import com.liferay.osb.patcher.service.PatcherFixPackLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import jakarta.portlet.PortletURL;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PatcherFixPacksDisplayContext {

	public PatcherFixPacksDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public SearchContainer<PatcherFixPack> getSearchContainer()
		throws Exception {

		if (_patcherPatcherFixPackSearchContainer != null) {
			return _patcherPatcherFixPackSearchContainer;
		}

		SearchContainer<PatcherFixPack> patcherPatcherFixPackSearchContainer =
			new SearchContainer<>(
				_renderRequest, _getPortletURL(), null,
				"there-are-no-fix-packs");

		Indexer<PatcherFixPack> indexer = IndexerRegistryUtil.getIndexer(
			PatcherFixPack.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		searchContext.setAttribute(
			"patcherFixComponentIdFilter", _getPatcherFixComponentId());
		searchContext.setAttribute(
			"patcherProjectVersionIdFilter", _getPatcherProjectVersionId());
		searchContext.setAttribute("statusFilter", _getStatus());
		searchContext.setEnd(patcherPatcherFixPackSearchContainer.getEnd());
		searchContext.setGroupIds(null);
		searchContext.setSorts(
			new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, true));
		searchContext.setStart(patcherPatcherFixPackSearchContainer.getStart());

		Hits hits = indexer.search(searchContext);

		patcherPatcherFixPackSearchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				SearchResultUtil.getSearchResults(
					hits, LocaleUtil.getDefault()),
				searchResult ->
					PatcherFixPackLocalServiceUtil.fetchPatcherFixPack(
						searchResult.getClassPK())),
			hits.getLength());

		_patcherPatcherFixPackSearchContainer =
			patcherPatcherFixPackSearchContainer;

		return _patcherPatcherFixPackSearchContainer;
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private Long _getPatcherFixComponentId() {
		if (_patcherFixComponentId != null) {
			return _patcherFixComponentId;
		}

		_patcherFixComponentId = ParamUtil.getLong(
			_httpServletRequest, "patcherFixComponentId");

		return _patcherFixComponentId;
	}

	private Long _getPatcherProjectVersionId() {
		if (_patcherProjectVersionId != null) {
			return _patcherProjectVersionId;
		}

		_patcherProjectVersionId = ParamUtil.getLong(
			_httpServletRequest, "patcherProjectVersionId");

		return _patcherProjectVersionId;
	}

	private PortletURL _getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		_portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/patcher/index_fix_packs"
		).setKeywords(
			_getKeywords()
		).setTabs1(
			"fix-packs"
		).setParameter(
			"patcherFixComponentId", _getPatcherFixComponentId()
		).setParameter(
			"patcherProjectVersionId", _getPatcherProjectVersionId()
		).setParameter(
			"status", _getStatus()
		).buildPortletURL();

		return _portletURL;
	}

	private int _getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			_httpServletRequest, "status", WorkflowConstants.STATUS_ANY);

		return _status;
	}

	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private Long _patcherFixComponentId;
	private SearchContainer<PatcherFixPack>
		_patcherPatcherFixPackSearchContainer;
	private Long _patcherProjectVersionId;
	private PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Integer _status;

}