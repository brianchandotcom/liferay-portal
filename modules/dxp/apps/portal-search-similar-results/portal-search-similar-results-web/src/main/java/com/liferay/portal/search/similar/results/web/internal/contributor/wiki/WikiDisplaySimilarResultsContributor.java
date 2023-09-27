/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.wiki;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.similar.results.web.internal.helper.HttpHelper;
import com.liferay.portal.search.similar.results.web.internal.util.SearchStringUtil;
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
			URLCodec.decodeURL(
				_httpHelper.getPortletIdParameter(urlString, "p_p_id")));

		routeBuilder.addAttribute(
			"nodeName",
			URLCodec.decodeURL(
				_httpHelper.getPortletIdParameter(urlString, "nodeName"))
		).addAttribute(
			"title",
			URLCodec.decodeURL(
				_httpHelper.getPortletIdParameter(urlString, "title"))
		);
	}

	@Override
	protected AssetEntryLocalService getAssetEntryLocalService() {
		return _assetEntryLocalService;
	}

	@Override
	protected UIDFactory getUidFactory() {
		return _uidFactory;
	}

	@Override
	protected WikiNodeLocalService getWikiNodeLocalService() {
		return _wikiNodeLocalService;
	}

	@Override
	protected WikiPageLocalService getWikiPageLocalService() {
		return _wikiPageLocalService;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private HttpHelper _httpHelper;

	@Reference
	private UIDFactory _uidFactory;

	@Reference
	private WikiNodeLocalService _wikiNodeLocalService;

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

}