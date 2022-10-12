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

package com.liferay.commerce.machine.learning.internal.recommendation;

import com.liferay.commerce.machine.learning.internal.recommendation.constants.CommerceMLRecommendationField;
import com.liferay.commerce.machine.learning.internal.search.api.CommerceMLIndexer;
import com.liferay.commerce.machine.learning.recommendation.UserCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.UserCommerceMLRecommendationManager;
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
@Component(
	immediate = true, service = UserCommerceMLRecommendationManager.class
)
public class UserCommerceMLRecommendationManagerImpl
	extends BaseCommerceMLRecommendationServiceImpl
		<UserCommerceMLRecommendation>
	implements UserCommerceMLRecommendationManager {

	@Override
	public UserCommerceMLRecommendation addUserCommerceMLRecommendation(
			UserCommerceMLRecommendation userCommerceMLRecommendation)
		throws PortalException {

		return addCommerceMLRecommendation(
			userCommerceMLRecommendation,
			_commerceMLIndexer.getIndexName(
				userCommerceMLRecommendation.getCompanyId()),
			_commerceMLIndexer.getDocumentType());
	}

	@Override
	public UserCommerceMLRecommendation create() {
		return new UserCommerceMLRecommendationImpl();
	}

	@Override
	public List<UserCommerceMLRecommendation> getUserCommerceMLRecommendations(
			long companyId, long commerceAccountId, long[] assetCategoryIds)
		throws PortalException {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			new String[] {_commerceMLIndexer.getIndexName(companyId)});

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
							Field.ENTRY_CLASS_PK,
							String.valueOf(commerceAccountId)),
						BooleanClauseOccur.MUST);
				}
			});

		searchSearchRequest.setQuery(booleanQuery);

		searchSearchRequest.setSize(SEARCH_SEARCH_REQUEST_SIZE);

		Sort sort = SortFactoryUtil.create(
			CommerceMLRecommendationField.SCORE, Sort.FLOAT_TYPE, true);

		searchSearchRequest.setSorts(new Sort[] {sort});

		searchSearchRequest.setStats(Collections.emptyMap());

		return getSearchResults(searchSearchRequest);
	}

	@Override
	protected Document toDocument(UserCommerceMLRecommendation model) {
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
	protected UserCommerceMLRecommendation toModel(Document document) {
		UserCommerceMLRecommendation userCommerceMLRecommendation =
			getCommerceMLRecommendation(
				new UserCommerceMLRecommendationImpl(), document);

		userCommerceMLRecommendation.setAssetCategoryIds(
			GetterUtil.getLongValues(
				document.getValues(Field.ASSET_CATEGORY_IDS)));
		userCommerceMLRecommendation.setEntryClassPK(
			GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));

		return userCommerceMLRecommendation;
	}

	@Reference(
		target = "(component.name=com.liferay.commerce.machine.learning.internal.recommendation.search.index.UserCommerceMLRecommendationIndexer)"
	)
	private CommerceMLIndexer _commerceMLIndexer;

}