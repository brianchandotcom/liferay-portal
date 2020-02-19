/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.wiki;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.similar.results.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.similar.results.web.internal.util.http.HttpHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = SimilarResultsContributor.class)
public class WikiDisplaySimilarResultsContributor
	extends BaseWikiSimilarResultsContributor {

	@Override
	public void detectRoute(
		RouteBuilder routeBuilder, RouteHelper routeHelper) {

		String urlString = routeHelper.getURLString();

		SearchStringUtil.requireStartsWith(
			WikiPortletKeys.WIKI_DISPLAY,
			_httpHelper.getPortletIdParameter(urlString, "p_p_id"));

		routeBuilder.addAttribute(
			"nodeName", _httpHelper.getPortletIdParameter(urlString, "nodeName")
		).addAttribute(
			"title", _httpHelper.getPortletIdParameter(urlString, "title")
		);
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		super.setAssetEntryLocalService(assetEntryLocalService);
	}

	@Reference(unbind = "-")
	protected void setHttpHelper(HttpHelper httpHelper) {
		_httpHelper = httpHelper;
	}

	@Reference(unbind = "-")
	protected void setUIDFactory(UIDFactory uidFactory) {
		super.setUIDFactory(uidFactory);
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		super.setWikiNodeLocalService(wikiNodeLocalService);
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		super.setWikiPageLocalService(wikiPageLocalService);
	}

	private HttpHelper _httpHelper;

}