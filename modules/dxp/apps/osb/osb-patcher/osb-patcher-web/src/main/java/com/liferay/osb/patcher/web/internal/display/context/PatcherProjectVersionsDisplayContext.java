/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.display.context;

import com.liferay.osb.patcher.model.PatcherProjectVersion;
import com.liferay.osb.patcher.service.PatcherProjectVersionLocalServiceUtil;
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
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.PortletURL;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PatcherProjectVersionsDisplayContext {

	public PatcherProjectVersionsDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public long getPatcherProductVersionId() {
		if (_patcherProductVersionId != null) {
			return _patcherProductVersionId;
		}

		_patcherProductVersionId = ParamUtil.getLong(
			_httpServletRequest, "patcherProductVersionId");

		return _patcherProductVersionId;
	}

	public SearchContainer<PatcherProjectVersion> getSearchContainer()
		throws Exception {

		if (_patcherProjectVersionSearchContainer != null) {
			return _patcherProjectVersionSearchContainer;
		}

		SearchContainer<PatcherProjectVersion>
			patcherProjectVersionSearchContainer = new SearchContainer<>(
				_renderRequest, _getPortletURL(), null,
				"there-are-no-project-versions");

		Indexer<PatcherProjectVersion> indexer = IndexerRegistryUtil.getIndexer(
			PatcherProjectVersion.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		searchContext.setEnd(patcherProjectVersionSearchContainer.getEnd());
		searchContext.setGroupIds(null);
		searchContext.setSorts(
			new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, true));
		searchContext.setStart(patcherProjectVersionSearchContainer.getStart());

		Hits hits = indexer.search(searchContext);

		patcherProjectVersionSearchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				SearchResultUtil.getSearchResults(
					hits, LocaleUtil.getDefault()),
				searchResult ->
					PatcherProjectVersionLocalServiceUtil.
						fetchPatcherProjectVersion(searchResult.getClassPK())),
			hits.getLength());

		_patcherProjectVersionSearchContainer =
			patcherProjectVersionSearchContainer;

		return _patcherProjectVersionSearchContainer;
	}

	private String _getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private PortletURL _getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		_portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/patcher/index_project_versions"
		).setKeywords(
			_getKeywords()
		).setTabs1(
			"project-versions"
		).setParameter(
			"patcherProductVersionId", getPatcherProductVersionId()
		).buildPortletURL();

		return _portletURL;
	}

	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private Long _patcherProductVersionId;
	private SearchContainer<PatcherProjectVersion>
		_patcherProjectVersionSearchContainer;
	private PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}