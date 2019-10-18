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
public class ClassNameIdClassPKSimilarResultsContributor
	implements SimilarResultsContributor {

	public static final String CLASS_NAME_ID = "classNameId";

	public static final String CLASS_PK = "classPK";

	@Override
	public void detectRoute(
		RouteBuilder routeBuilder, RouteHelper routeHelper) {

		String urlString = routeHelper.getURLString();

		routeBuilder.addAttribute(
			CLASS_NAME_ID,
			Long.valueOf(
				_httpHelper.getPortletIdParameter(urlString, CLASS_NAME_ID))
		).addAttribute(
			CLASS_PK,
			Long.valueOf(_httpHelper.getPortletIdParameter(urlString, CLASS_PK))
		);
	}

	@Override
	public void resolveCriteria(
		CriteriaBuilder criteriaBuilder, CriteriaHelper criteriaHelper) {

		Long classNameId = (Long)criteriaHelper.getRouteParameter(
			CLASS_NAME_ID);
		Long classPK = (Long)criteriaHelper.getRouteParameter(CLASS_PK);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			classNameId, classPK);

		if (assetEntry == null) {
			return;
		}

		criteriaBuilder.uid(
			Field.getUID(assetEntry.getClassName(), String.valueOf(classPK)));
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
			CLASS_NAME_ID, String.valueOf(assetEntry.getClassNameId())
		).replaceParameter(
			CLASS_PK, String.valueOf(assetEntry.getClassPK())
		);
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private HttpHelper _httpHelper;

}