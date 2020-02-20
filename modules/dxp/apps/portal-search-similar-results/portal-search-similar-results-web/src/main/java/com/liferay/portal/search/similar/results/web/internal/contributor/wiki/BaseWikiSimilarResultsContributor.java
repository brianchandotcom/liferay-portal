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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public abstract class BaseWikiSimilarResultsContributor
	implements SimilarResultsContributor {

	@Override
	public abstract void detectRoute(
		RouteBuilder routeBuilder, RouteHelper routeHelper);

	@Override
	public void resolveCriteria(
		CriteriaBuilder criteriaBuilder, CriteriaHelper criteriaHelper) {

		long groupId = criteriaHelper.getGroupId();

		String nodeName = (String)criteriaHelper.getRouteParameter("nodeName");

		WikiNode wikiNode = _wikiNodeLocalService.fetchNode(groupId, nodeName);

		if (wikiNode == null) {
			return;
		}

		String title = (String)criteriaHelper.getRouteParameter("title");

		WikiPage wikiPage = _wikiPageLocalService.fetchPage(
			wikiNode.getNodeId(), title, 1.0);

		if (wikiPage == null) {
			return;
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			groupId, wikiPage.getUuid());

		if (assetEntry == null) {
			return;
		}

		criteriaBuilder.type(
			assetEntry.getClassName()
		).uid(
			_uidFactory.getUID(wikiPage)
		);
	}

	@Override
	public void writeDestination(
		DestinationBuilder destinationBuilder,
		DestinationHelper destinationHelper) {

		String className = destinationHelper.getClassName();
		String nodeName = (String)destinationHelper.getRouteParameter(
			"nodeName");
		String title = (String)destinationHelper.getRouteParameter("title");

		if (className.equals(WikiPage.class.getName())) {
			AssetRenderer<?> assetRenderer =
				destinationHelper.getAssetRenderer();

			WikiPage wikiPage = (WikiPage)assetRenderer.getAssetObject();

			WikiNode wikiNode = wikiPage.getNode();

			destinationBuilder.replace(
				nodeName, wikiNode.getName()
			).replace(
				title, wikiPage.getTitle()
			);
		}
	}

	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	protected void setUIDFactory(UIDFactory uidFactory) {
		_uidFactory = uidFactory;
	}

	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private UIDFactory _uidFactory;
	private WikiNodeLocalService _wikiNodeLocalService;
	private WikiPageLocalService _wikiPageLocalService;

}