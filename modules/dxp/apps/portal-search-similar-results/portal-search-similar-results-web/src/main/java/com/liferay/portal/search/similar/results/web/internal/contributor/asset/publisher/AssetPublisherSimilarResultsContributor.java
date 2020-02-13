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

package com.liferay.portal.search.similar.results.web.internal.contributor.asset.publisher;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.similar.results.web.internal.builder.AssetTypeUtil;
import com.liferay.portal.search.similar.results.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.similar.results.web.internal.util.http.HttpHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = SimilarResultsContributor.class)
public class AssetPublisherSimilarResultsContributor
	implements SimilarResultsContributor {

	@Override
	public void detectRoute(
		RouteBuilder routeBuilder, RouteHelper routeHelper) {

		String urlString = routeHelper.getURLString();

		String[] parameters = _httpHelper.getFriendlyURLParameters(urlString);

		SearchStringUtil.requireEquals("asset_publisher", parameters[0]);

		putAttribute(parameters[2], "type", routeBuilder);

		String assetEntryId = _httpHelper.getPortletIdParameter(
			urlString, "assetEntryId",
			_getAssetPublisherPortletId(parameters[1]));

		putAttribute(Long.valueOf(assetEntryId), "entryId", routeBuilder);
	}

	@Override
	public void resolveCriteria(
		CriteriaBuilder criteriaBuilder, CriteriaHelper criteriaHelper) {

		Long entryId = (Long)criteriaHelper.getRouteParameter("entryId");

		AssetEntry assetEntry = _assetEntryLocalService.fetchAssetEntry(
			entryId);

		if (assetEntry == null) {
			return;
		}

		criteriaBuilder.type(
			assetEntry.getClassName()
		).uid(
			_getUID(assetEntry)
		);
	}

	@Override
	public void writeDestination(
		DestinationBuilder destinationBuilder,
		DestinationHelper destinationHelper) {

		Long entryId = (Long)destinationHelper.getRouteParameter("entryId");

		String type = (String)destinationHelper.getRouteParameter("type");

		AssetEntry assetEntry = destinationHelper.getAssetEntry();

		destinationBuilder.replace(
			type,
			AssetTypeUtil.getAssetTypeByClassName(
				destinationHelper.getClassName())
		).replace(
			String.valueOf(entryId), String.valueOf(assetEntry.getEntryId())
		);
	}

	protected void putAttribute(
		Object value, String name, RouteBuilder routeBuilder) {

		routeBuilder.addAttribute(name, value);
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setHttpHelper(HttpHelper httpHelper) {
		_httpHelper = httpHelper;
	}

	@Reference(unbind = "-")
	protected void setUIDFactory(UIDFactory uidFactory) {
		_uidFactory = uidFactory;
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	private String _getAssetPublisherPortletId(String instanceId) {
		return AssetPublisherPortletKeys.ASSET_PUBLISHER + "_INSTANCE_" +
			instanceId;
	}

	private ClassedModel _getClassedModel(AssetEntry assetEntry) {
		if (Objects.equals(
				BlogsEntry.class.getName(), assetEntry.getClassName())) {

			return _blogsEntryLocalService.fetchBlogsEntryByUuidAndGroupId(
				assetEntry.getClassUuid(), assetEntry.getGroupId());
		}
		else if (Objects.equals(
					JournalArticle.class.getName(),
					assetEntry.getClassName())) {

			AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

			return (JournalArticle)assetRenderer.getAssetObject();
		}
		else if (Objects.equals(
					WikiPage.class.getName(), assetEntry.getClassName())) {

			return _wikiPageLocalService.fetchWikiPageByUuidAndGroupId(
				assetEntry.getClassUuid(), assetEntry.getGroupId());
		}

		return null;
	}

	private String _getUID(AssetEntry assetEntry) {
		ClassedModel classedModel = _getClassedModel(assetEntry);

		if (classedModel != null) {
			return _uidFactory.getUID(classedModel);
		}

		return Field.getUID(
			assetEntry.getClassName(), String.valueOf(assetEntry.getClassPK()));
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private BlogsEntryLocalService _blogsEntryLocalService;
	private HttpHelper _httpHelper;
	private UIDFactory _uidFactory;
	private WikiPageLocalService _wikiPageLocalService;

}