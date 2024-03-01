/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.builder;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.similar.results.web.internal.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.asset.publisher.AssetPublisherSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.blogs.BlogsSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.document.library.DocumentLibrarySimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.message.boards.MessageBoardsSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters.ClassNameClassPKSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters.ClassNameIdClassPKSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters.ClassUUIDSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters.EntryIdSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.url.parameters.UIDSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.wiki.WikiDisplaySimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.internal.contributor.wiki.WikiSimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SimilarResultsContributorsRegistry {

	public SimilarResultsContributorsRegistry(
		AssetEntryLocalService assetEntryLocalService,
		BlogsEntryLocalService blogsEntryLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFolderLocalService dlFolderLocalService,
		MBCategoryLocalService mbCategoryLocalService,
		MBMessageLocalService mbMessageLocalService, UIDFactory uidFactory,
		WikiNodeLocalService wikiNodeLocalService,
		WikiPageLocalService wikiPageLocalService) {

		_similarResultsContributors.add(
			new AssetPublisherSimilarResultsContributor(
				assetEntryLocalService, blogsEntryLocalService, uidFactory,
				wikiPageLocalService));
		_similarResultsContributors.add(
			new BlogsSimilarResultsContributor(
				blogsEntryLocalService, uidFactory));
		_similarResultsContributors.add(
			new ClassNameClassPKSimilarResultsContributor());
		_similarResultsContributors.add(
			new ClassNameIdClassPKSimilarResultsContributor(
				assetEntryLocalService));
		_similarResultsContributors.add(
			new ClassUUIDSimilarResultsContributor(assetEntryLocalService));
		_similarResultsContributors.add(
			new DocumentLibrarySimilarResultsContributor(
				assetEntryLocalService, dlFileEntryLocalService,
				dlFolderLocalService));
		_similarResultsContributors.add(
			new EntryIdSimilarResultsContributor(assetEntryLocalService));
		_similarResultsContributors.add(
			new MessageBoardsSimilarResultsContributor(
				assetEntryLocalService, mbCategoryLocalService,
				mbMessageLocalService));
		_similarResultsContributors.add(new UIDSimilarResultsContributor());
		_similarResultsContributors.add(
			new WikiDisplaySimilarResultsContributor(
				assetEntryLocalService, uidFactory, wikiNodeLocalService,
				wikiPageLocalService));
		_similarResultsContributors.add(
			new WikiSimilarResultsContributor(
				assetEntryLocalService, uidFactory, wikiNodeLocalService,
				wikiPageLocalService));
	}

	public SimilarResultsRoute detectRoute(String urlString) {
		if (Validator.isBlank(urlString)) {
			return null;
		}

		for (SimilarResultsContributor similarResultsContributor :
				_similarResultsContributors) {

			SimilarResultsRoute similarResultsRoute = _detectRoute(
				similarResultsContributor, urlString);

			if (similarResultsRoute != null) {
				return similarResultsRoute;
			}
		}

		return null;
	}

	private SimilarResultsRoute _detectRoute(
		SimilarResultsContributor similarResultsContributor, String urlString) {

		RouteBuilderImpl routeBuilderImpl = new RouteBuilderImpl();

		RouteHelper routeHelper = () -> urlString;

		try {
			similarResultsContributor.detectRoute(
				routeBuilderImpl, routeHelper);
		}
		catch (RuntimeException runtimeException) {
			if (_log.isDebugEnabled()) {
				_log.debug(runtimeException);
			}

			return null;
		}

		if (routeBuilderImpl.hasNoAttributes()) {
			return null;
		}

		routeBuilderImpl.contributor(similarResultsContributor);

		return routeBuilderImpl.build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SimilarResultsContributorsRegistry.class);

	private final List<SimilarResultsContributor> _similarResultsContributors =
		new ArrayList<>();

}