/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.display.context;

import com.liferay.osb.patcher.model.PatcherBuild;
import com.liferay.osb.patcher.service.PatcherBuildLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PatcherViewBuildsDisplayContext {

	public PatcherViewBuildsDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public PatcherBuild getPatcherBuild() {
		if (_patcherBuild != null) {
			return _patcherBuild;
		}

		long patcherBuildId = ParamUtil.getLong(
			_httpServletRequest, "patcherBuildId");

		_patcherBuild = PatcherBuildLocalServiceUtil.fetchPatcherBuild(
			patcherBuildId);

		return _patcherBuild;
	}

	public SearchContainer<PatcherBuild> getSearchContainer() throws Exception {
		if (_patcherBuildSearchContainer != null) {
			return _patcherBuildSearchContainer;
		}

		SearchContainer<PatcherBuild> patcherBuildSearchContainer =
			new SearchContainer<>(
				_renderRequest, _renderResponse.createRenderURL(), null, null);

		Indexer<PatcherBuild> indexer = IndexerRegistryUtil.getIndexer(
			PatcherBuild.class);

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		PatcherBuild patcherBuild = getPatcherBuild();

		searchContext.setAttribute("buildsViewSearch", Boolean.TRUE);
		searchContext.setAttribute("key", patcherBuild.getKey());

		searchContext.setGroupIds(null);
		searchContext.setSorts(new Sort("keyVersion", true));

		Hits hits = indexer.search(searchContext);

		patcherBuildSearchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				SearchResultUtil.getSearchResults(
					hits, LocaleUtil.getDefault()),
				searchResult -> PatcherBuildLocalServiceUtil.fetchPatcherBuild(
					searchResult.getClassPK())),
			hits.getLength());

		_patcherBuildSearchContainer = patcherBuildSearchContainer;

		return _patcherBuildSearchContainer;
	}

	private final HttpServletRequest _httpServletRequest;
	private PatcherBuild _patcherBuild;
	private SearchContainer<PatcherBuild> _patcherBuildSearchContainer;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}