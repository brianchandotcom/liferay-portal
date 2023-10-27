/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation;

import com.liferay.analytics.machine.learning.content.UserContentRecommendation;
import com.liferay.analytics.machine.learning.content.UserContentRecommendationManager;
import com.liferay.analytics.machine.learning.internal.recommendation.constants.RecommendationField;
import com.liferay.analytics.machine.learning.internal.search.api.RecommendationIndexer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = UserContentRecommendationManager.class)
public class UserContentRecommendationManagerImpl
	extends BaseRecommendationManagerImpl<UserContentRecommendation>
	implements UserContentRecommendationManager {

	@Override
	public UserContentRecommendation addUserContentRecommendation(
			UserContentRecommendation userContentRecommendation)
		throws PortalException {

		return addRecommendation(
			userContentRecommendation,
			_recommendationIndexer.getIndexName(
				userContentRecommendation.getCompanyId()));
	}

	@Override
	public UserContentRecommendation create() {
		return new UserContentRecommendationImpl();
	}

	@Override
	public List<UserContentRecommendation> getUserContentRecommendations(
			long[] assetCategoryIds, long companyId, long userId)
		throws PortalException {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			new String[] {_recommendationIndexer.getIndexName(companyId)});

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		if (assetCategoryIds != null) {
			for (long categoryId : assetCategoryIds) {
				TermQuery categoryIdTermQuery = new TermQueryImpl(
					Field.ASSET_CATEGORY_IDS, String.valueOf(categoryId));

				booleanQuery.add(categoryIdTermQuery, BooleanClauseOccur.MUST);
			}
		}

		booleanQuery.setPreBooleanFilter(
			new BooleanFilter() {
				{
					add(
						new TermFilter(
							Field.COMPANY_ID, String.valueOf(companyId)),
						BooleanClauseOccur.MUST);
					add(
						new TermFilter(
							Field.ENTRY_CLASS_PK, String.valueOf(userId)),
						BooleanClauseOccur.MUST);
				}
			});

		searchSearchRequest.setQuery(booleanQuery);

		searchSearchRequest.setSize(SEARCH_SEARCH_REQUEST_SIZE);

		Sort sort = SortFactoryUtil.create(
			RecommendationField.SCORE, Sort.FLOAT_TYPE, true);

		searchSearchRequest.setSorts(new Sort[] {sort});

		searchSearchRequest.setStats(Collections.emptyMap());

		return getSearchResults(searchSearchRequest);
	}

	@Override
	protected Document toDocument(UserContentRecommendation model) {
		Document document = getDocument(model);

		document.addKeyword(
			Field.UID,
			String.valueOf(
				getHash(
					model.getEntryClassPK(),
					model.getRecommendedEntryClassPK())));
		document.addNumber(
			Field.ASSET_CATEGORY_IDS, model.getAssetCategoryIds());
		document.addNumber(Field.ENTRY_CLASS_PK, model.getEntryClassPK());

		return document;
	}

	@Override
	protected UserContentRecommendation toModel(Document document) {
		UserContentRecommendation userContentRecommendation = getRecommendation(
			new UserContentRecommendationImpl(), document);

		userContentRecommendation.setAssetCategoryIds(
			GetterUtil.getLongValues(
				document.getValues(Field.ASSET_CATEGORY_IDS)));
		userContentRecommendation.setEntryClassPK(
			GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));

		return userContentRecommendation;
	}

	@Reference(
		target = "(component.name=com.liferay.analytics.machine.learning.internal.recommendation.search.UserContentRecommendationIndexer)"
	)
	private RecommendationIndexer _recommendationIndexer;

}