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

package com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.similar.results.web.internal.util.http.HttpHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = SimilarResultsContributor.class)
public class EntryIdSimilarResultsContributor
	implements SimilarResultsContributor {

	public static final String ENTRY_ID = "entryId";

	@Override
	public void detectRoute(
		RouteBuilder routeBuilder, RouteHelper routeHelper) {

		routeBuilder.addAttribute(
			ENTRY_ID,
			Long.valueOf(
				_httpHelper.getPortletIdParameter(
					routeHelper.getURLString(), ENTRY_ID)));
	}

	@Override
	public void resolveCriteria(
		CriteriaBuilder criteriaBuilder, CriteriaHelper criteriaHelper) {

		Long entryId = (Long)criteriaHelper.getRouteParameter(ENTRY_ID);

		AssetEntry assetEntry = _assetEntryLocalService.fetchAssetEntry(
			entryId);

		criteriaBuilder.uid(
			Field.getUID(
				assetEntry.getClassName(),
				String.valueOf(assetEntry.getClassPK())));
	}

	@Reference(unbind = "-")
	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	public void setHttpHelper(HttpHelper httpHelper) {
		_httpHelper = httpHelper;
	}

	@Override
	public void writeDestination(
		DestinationBuilder destinationBuilder,
		DestinationHelper destinationHelper) {

		AssetEntry assetEntry = destinationHelper.getAssetEntry();

		destinationBuilder.replaceParameter(
			ENTRY_ID, String.valueOf(assetEntry.getEntryId()));
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private HttpHelper _httpHelper;

}