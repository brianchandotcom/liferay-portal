/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.learn.LearnMessageUtil;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class AddRankingDisplayContext {

	public AddRankingDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public Map<String, Object> getProps() {
		return HashMapBuilder.<String, Object>put(
			"cancelURL", ParamUtil.getString(_renderRequest, "redirect")
		).put(
			"fetchSitesURL",
			ResourceURLBuilder.createResourceURL(
				_renderResponse
			).setCMD(
				"getSitesJSONObject"
			).setResourceID(
				"/result_rankings/get_sites"
			).buildString()
		).put(
			"formName", "addResultRankingsFm"
		).put(
			"learnResources",
			LearnMessageUtil.getReactDataJSONObject(
				"portal-search-tuning-rankings-web")
		).put(
			"namespace", _renderResponse.getNamespace()
		).build();
	}

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}