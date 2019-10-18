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

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = SimilarResultsContributor.class)
public class ClassUUIDSimilarResultsContributor
	implements SimilarResultsContributor {

	public static final String CLASS_UUID = "classUuid";

	@Override
	public void detectRoute(
		RouteBuilder routeBuilder, RouteHelper routeHelper) {

		routeBuilder.addAttribute(
			CLASS_UUID,
			Objects.requireNonNull(
				_httpHelper.getPortletIdParameter(
					routeHelper.getURLString(), CLASS_UUID)));
	}

	@Override
	public void resolveCriteria(
		CriteriaBuilder criteriaBuilder, CriteriaHelper criteriaHelper) {

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			criteriaHelper.getGroupId(),
			(String)criteriaHelper.getRouteParameter(CLASS_UUID));

		if (assetEntry == null) {
			return;
		}

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
			CLASS_UUID, assetEntry.getClassUuid());
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private HttpHelper _httpHelper;

}