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

package com.liferay.portal.search.similar.results.web.internal.portlet.shared.search;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.query.MoreLikeThisQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsContributorsRegistry;
import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsRoute;
import com.liferay.portal.search.similar.results.web.internal.constants.SimilarResultsPortletKeys;
import com.liferay.portal.search.similar.results.web.internal.portlet.SimilarResultsPortletPreferences;
import com.liferay.portal.search.similar.results.web.internal.portlet.SimilarResultsPortletPreferencesImpl;
import com.liferay.portal.search.similar.results.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Collections;
import java.util.Optional;

import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SimilarResultsPortletKeys.SIMILAR_RESULTS,
	service = PortletSharedSearchContributor.class
)
public class SimilarResultsPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		Optional<SimilarResultsRoute> optional =
			similarResultsContributorsRegistry.detectRoute(
				_getURLString(portletSharedSearchSettings));

		optional.flatMap(
			similarResultsRoute -> _getSimilarResultsInputOptional(
				getGroupId(portletSharedSearchSettings), similarResultsRoute)
		).ifPresent(
			similarResultsInput -> contribute(
				similarResultsInput, portletSharedSearchSettings)
		);
	}

	protected void contribute(
		Criteria criteria,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SimilarResultsPortletPreferences similarResultsPortletPreferences =
			new SimilarResultsPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferencesOptional());

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				Optional.of(
					similarResultsPortletPreferences.getFederatedSearchKey()));

		_filterByEntryClassName(
			criteria, portletSharedSearchSettings, searchRequestBuilder);

		_filterByGroupId(portletSharedSearchSettings, searchRequestBuilder);

		searchRequestBuilder.query(
			_getMoreLikeThisQuery(
				criteria.getUID(), similarResultsPortletPreferences)
		).emptySearchEnabled(
			true
		).size(
			similarResultsPortletPreferences.getMaxItemDisplay()
		);

		_setUIDRenderRequestAttribute(criteria, portletSharedSearchSettings);
	}

	protected long getGroupId(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		return themeDisplay.getScopeGroupId();
	}

	@Reference
	protected SimilarResultsContributorsRegistry
		similarResultsContributorsRegistry;

	private void _filterByEntryClassName(
		Criteria criteria,
		PortletSharedSearchSettings portletSharedSearchSettings,
		SearchRequestBuilder searchRequestBuilder) {

		Optional<String> optional =
			portletSharedSearchSettings.getParameterOptional(
				"similar.results.all.classes");

		if (optional.isPresent()) {
			return;
		}

		Optional<String> classNameOptional = criteria.getTypeOptional();

		classNameOptional.ifPresent(
			className -> {
				if (!Validator.isBlank(className)) {
					searchRequestBuilder.addComplexQueryPart(
						_getComplexQueryPart(
							_getEntryClassNameQuery(className)));
				}
			});
	}

	private void _filterByGroupId(
		PortletSharedSearchSettings portletSharedSearchSettings,
		SearchRequestBuilder searchRequestBuilder) {

		searchRequestBuilder.withSearchContext(
			searchContext -> searchContext.setGroupIds(
				new long[] {getGroupId(portletSharedSearchSettings)}));
	}

	private ComplexQueryPart _getComplexQueryPart(Query query) {
		return _complexQueryPartBuilderFactory.builder(
		).query(
			query
		).build();
	}

	private Query _getEntryClassNameQuery(String entryClassName) {
		return _queries.term(Field.ENTRY_CLASS_NAME, entryClassName);
	}

	private MoreLikeThisQuery _getMoreLikeThisQuery(
		String uid,
		SimilarResultsPortletPreferences similarResultsPortletPreferences) {

		MoreLikeThisQuery moreLikeThisQuery = _queries.moreLikeThis(
			Collections.singleton(
				_queries.documentIdentifier(
					similarResultsPortletPreferences.getIndexName(),
					similarResultsPortletPreferences.getDocType(), uid)));

		_populate(moreLikeThisQuery, similarResultsPortletPreferences);

		return moreLikeThisQuery;
	}

	private Optional<Criteria> _getSimilarResultsInputOptional(
		long groupId, SimilarResultsRoute similarResultsRoute) {

		SimilarResultsContributor similarResultsContributor =
			similarResultsRoute.getContributor();

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		CriteriaHelper criteriaHelper = new CriteriaHelperImpl(
			groupId, similarResultsRoute);

		similarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		return criteriaBuilderImpl.build();
	}

	private String _getURLString(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		return _portal.getCurrentURL(
			portletSharedSearchSettings.getRenderRequest());
	}

	private void _populate(
		MoreLikeThisQuery moreLikeThisQuery,
		SimilarResultsPortletPreferences similarResultsPortletPreferences) {

		String fields = similarResultsPortletPreferences.getFields();

		if (!Validator.isBlank(fields)) {
			moreLikeThisQuery.addFields(
				SearchStringUtil.splitAndUnquote(
					SearchStringUtil.maybe(fields)));
		}

		String stopWords = similarResultsPortletPreferences.getStopWords();

		if (!Validator.isBlank(stopWords)) {
			moreLikeThisQuery.addStopWords(
				SearchStringUtil.splitAndUnquote(
					SearchStringUtil.maybe(StringUtil.toLowerCase(stopWords))));
		}

		moreLikeThisQuery.setAnalyzer(
			similarResultsPortletPreferences.getAnalyzer());
		moreLikeThisQuery.setMaxDocFrequency(
			similarResultsPortletPreferences.getMaxDocFrequency());
		moreLikeThisQuery.setMaxQueryTerms(
			similarResultsPortletPreferences.getMaxQueryTerms());
		moreLikeThisQuery.setMaxWordLength(
			similarResultsPortletPreferences.getMaxWordLength());
		moreLikeThisQuery.setMinDocFrequency(
			similarResultsPortletPreferences.getMinDocFrequency());
		moreLikeThisQuery.setMinShouldMatch(
			similarResultsPortletPreferences.getMinShouldMatch());
		moreLikeThisQuery.setMinTermFrequency(
			similarResultsPortletPreferences.getMinTermFrequency());
		moreLikeThisQuery.setMinWordLength(
			similarResultsPortletPreferences.getMinWordLength());
		moreLikeThisQuery.setTermBoost(
			similarResultsPortletPreferences.getTermBoost());
	}

	private void _setUIDRenderRequestAttribute(
		Criteria criteria,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		RenderRequest renderRequest =
			portletSharedSearchSettings.getRenderRequest();

		renderRequest.setAttribute(Field.UID, criteria.getUID());
	}

	@Reference
	private ComplexQueryPartBuilderFactory _complexQueryPartBuilderFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

}