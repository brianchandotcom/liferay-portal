/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.search.similar.results.web.internal.helper.HttpHelper;
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
public class ClassNameClassPKSimilarResultsContributor
	implements SimilarResultsContributor {

	public static final String CLASS_NAME = "className";

	public static final String CLASS_PK = "classPK";

	@Override
	public void detectRoute(
		RouteBuilder routeBuilder, RouteHelper routeHelper) {

		String urlString = HttpComponentsUtil.decodePath(
			routeHelper.getURLString());

		routeBuilder.addAttribute(
			CLASS_NAME,
			Objects.requireNonNull(
				_httpHelper.getPortletIdParameter(urlString, CLASS_NAME))
		).addAttribute(
			CLASS_PK,
			Long.valueOf(_httpHelper.getPortletIdParameter(urlString, CLASS_PK))
		);
	}

	@Override
	public void resolveCriteria(
		CriteriaBuilder criteriaBuilder, CriteriaHelper criteriaHelper) {

		criteriaBuilder.uid(
			Field.getUID(
				(String)criteriaHelper.getRouteParameter(CLASS_NAME),
				String.valueOf(criteriaHelper.getRouteParameter(CLASS_PK))));
	}

	@Override
	public void writeDestination(
		DestinationBuilder destinationBuilder,
		DestinationHelper destinationHelper) {

		AssetEntry assetEntry = destinationHelper.getAssetEntry();

		destinationBuilder.replaceParameter(
			CLASS_NAME, assetEntry.getClassName()
		).replaceParameter(
			CLASS_PK, String.valueOf(assetEntry.getClassPK())
		);
	}

	@Reference
	private HttpHelper _httpHelper;

}