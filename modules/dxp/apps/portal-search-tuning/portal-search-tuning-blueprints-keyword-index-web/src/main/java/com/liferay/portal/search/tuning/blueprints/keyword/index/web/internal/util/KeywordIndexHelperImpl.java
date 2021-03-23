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

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.util;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.tuning.blueprints.content.analysis.constants.ModerationReason;
import com.liferay.portal.search.tuning.blueprints.content.analysis.request.ContentAnalysisRequest;
import com.liferay.portal.search.tuning.blueprints.content.analysis.response.ModerationAnalysisResponse;
import com.liferay.portal.search.tuning.blueprints.content.analysis.service.ContentAnalysisService;
import com.liferay.portal.search.tuning.blueprints.keyword.index.configuration.KeywordIndexConfiguration;
import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.KeywordEntryStatus;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexName;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.util.KeywordIndexHelper;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.DocumentToKeywordEntryTranslator;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexReader;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.tuning.blueprints.keyword.index.configuration.KeywordIndexConfiguration",
	immediate = true, service = KeywordIndexHelper.class
)
public class KeywordIndexHelperImpl implements KeywordIndexHelper {

	@Override
	public void addActiveKeywordEntry(
		long companyId, long groupId, String languageId, String keywords) {

		_keywordIndexWriter.create(
			_keywordIndexNameBuilder.getKeywordIndexName(companyId),
			new KeywordEntry.KeywordEntryBuilder().companyId(
				companyId
			).content(
				keywords
			).created(
				new Date()
			).groupId(
				groupId
			).hitCount(
				1L
			).languageId(
				languageId
			).lastAccessed(
				new Date()
			).modified(
				new Date()
			).status(
				KeywordEntryStatus.ACTIVE
			).statusDate(
				new Date()
			).build());
	}

	@Override
	public void addReportedKeywordEntry(
		long companyId, long groupId, String languageId, String keywords,
		KeywordEntryStatus keywordEntryStatus,
		ModerationReason moderationReason, String reporter) {

		Date now = new Date();

		_keywordIndexWriter.create(
			_keywordIndexNameBuilder.getKeywordIndexName(companyId),
			new KeywordEntry.KeywordEntryBuilder().companyId(
				companyId
			).addReport(
				KeywordIndexUtil.createReportEntry(moderationReason, reporter)
			).content(
				keywords
			).created(
				new Date()
			).groupId(
				groupId
			).hitCount(
				1L
			).languageId(
				languageId
			).lastAccessed(
				now
			).modified(
				now
			).lastReported(
				now
			).reportCount(
				1L
			).status(
				keywordEntryStatus
			).statusDate(
				now
			).build());
	}

	@Override
	public void deleteCompanyKeywordEntries(long companyId) {
		int count = GetterUtil.getInteger(
			getCompanyKeywordEntriesCount(companyId));

		if (count == 0) {
			return;
		}

		SearchHits searchHits = _searchCompanyKeywordEntries(
			companyId, 0, count);

		_deleteCompanyKeywordEntries(companyId, searchHits);
	}

	@Override
	public List<KeywordEntry> getCompanyKeywordEntries(long companyId) {
		SearchHits searchHits = _searchCompanyKeywordEntries(
			companyId, 0, getCompanyKeywordEntriesCount(companyId));

		if (searchHits.getTotalHits() == 0) {
			return Collections.emptyList();
		}

		return _translateToKeywordEntries(searchHits);
	}

	@Override
	public int getCompanyKeywordEntriesCount(long companyId) {
		CountSearchRequest countSearchRequest = new CountSearchRequest();

		countSearchRequest.setIndexNames(_getKeywordIndexName(companyId));

		countSearchRequest.setQuery(_queries.matchAll());

		CountSearchResponse countSearchResponse = _searchEngineAdapter.execute(
			countSearchRequest);

		return GetterUtil.getInteger(countSearchResponse.getCount());
	}

	@Override
	public void indexKeywords(
		long companyId, long groupId, String languageId, String keywords) {

		if (!_keywordIndexConfiguration.enableKeywordIndexing()) {
			return;
		}

		Optional<String> queryStringIdOptional =
			_keywordIndexReader.fetchIdOptional(
				_keywordIndexNameBuilder.getKeywordIndexName(companyId),
				companyId, groupId, keywords);

		if (queryStringIdOptional.isPresent()) {
			_updateHitcount(companyId, queryStringIdOptional.get());
		}
		else {
			_indexKeywords(companyId, groupId, languageId, keywords);
		}
	}

	public boolean isAnalyzedLanguage(String languageId) {
		return KeywordIndexUtil.isAnalyzedLanguage(languageId);
	}

	@Override
	public void reportKeywordEntry(
		long companyId, long groupId, String keywords,
		ModerationReason moderationReason, String reporter) {

		KeywordIndexName keywordIndexName =
			_keywordIndexNameBuilder.getKeywordIndexName(companyId);

		Optional<String> keywordEntryIdOptional =
			_keywordIndexReader.fetchIdOptional(
				keywordIndexName, companyId, groupId, keywords);

		if (keywordEntryIdOptional.isPresent()) {
			_keywordIndexWriter.addReport(
				keywordIndexName, keywordEntryIdOptional.get(),
				moderationReason, reporter);
		}
	}

	@Override
	public void reportKeywordEntry(
		long companyId, String keywordEntryId,
		ModerationReason moderationReason, String reporter) {

		KeywordIndexName keywordIndexName =
			_keywordIndexNameBuilder.getKeywordIndexName(companyId);

		Optional<KeywordEntry> keywordEntryOptional =
			_keywordIndexReader.fetchKeywordEntryOptional(
				keywordIndexName, keywordEntryId);

		if (keywordEntryOptional.isPresent()) {
			_keywordIndexWriter.addReport(
				keywordIndexName, keywordEntryId, moderationReason, reporter);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_keywordIndexConfiguration = ConfigurableUtil.createConfigurable(
			KeywordIndexConfiguration.class, properties);
	}

	private void _deleteCompanyKeywordEntries(
		long companyId, SearchHits searchHits) {

		List<SearchHit> hits = searchHits.getSearchHits();

		Stream<SearchHit> stream = hits.stream();

		stream.forEach(
			s -> _keywordIndexWriter.remove(
				_keywordIndexNameBuilder.getKeywordIndexName(companyId),
				s.getId()));
	}

	private String _getKeywordIndexName(long companyId) {
		KeywordIndexName keywordIndexName =
			_keywordIndexNameBuilder.getKeywordIndexName(companyId);

		return keywordIndexName.getIndexName();
	}

	private void _indexKeywords(
		long companyId, long groupId, String languageId, String keywords) {

		Optional<List<ModerationAnalysisResponse>>
			moderationAnalysisResponsesOptional =
				_contentAnalysisService.analyzeModeration(
					new ContentAnalysisRequest.Builder().payload(
						keywords
					).mimeType(
						"text/plain"
					).build());

		if (!moderationAnalysisResponsesOptional.isPresent()) {
			addActiveKeywordEntry(companyId, groupId, languageId, keywords);

			return;
		}

		List<ModerationAnalysisResponse> moderationAnalysisResponses =
			moderationAnalysisResponsesOptional.get();

		boolean reported = false;

		for (ModerationAnalysisResponse moderationAnalysisResponse :
				moderationAnalysisResponses) {

			if (moderationAnalysisResponse.isModerationRecommended()) {
				addReportedKeywordEntry(
					companyId, groupId, languageId, keywords,
					KeywordEntryStatus.BLACKLISTED,
					moderationAnalysisResponse.getModerationReason(),
					moderationAnalysisResponse.getReporter());
				reported = true;

				break;
			}
		}

		if (!reported) {
			addActiveKeywordEntry(companyId, groupId, languageId, keywords);
		}
	}

	private SearchHits _searchCompanyKeywordEntries(
		long companyId, int start, int size) {

		return _searchCompanyKeywordEntries(
			companyId, _queries.matchAll(), start, size);
	}

	private SearchHits _searchCompanyKeywordEntries(
		long companyId, Query query, int start, int size) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setIndexNames(_getKeywordIndexName(companyId));
		searchSearchRequest.setPreferLocalCluster(false);
		searchSearchRequest.setQuery(query);
		searchSearchRequest.setSize(size);
		searchSearchRequest.setStart(start);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		return searchSearchResponse.getSearchHits();
	}

	private List<KeywordEntry> _translateToKeywordEntries(
		SearchHits searchHits) {

		List<KeywordEntry> keywordEntries = new ArrayList<>();

		List<SearchHit> hits = searchHits.getSearchHits();

		Stream<SearchHit> stream = hits.stream();

		stream.forEach(
			s -> keywordEntries.add(
				_documentToKeywordEntryTranslator.translate(
					s.getDocument(), s.getId())));

		return keywordEntries;
	}

	private void _updateHitcount(long companyId, String id) {
		_keywordIndexWriter.addHit(
			_keywordIndexNameBuilder.getKeywordIndexName(companyId), id);
	}

	@Reference
	private ContentAnalysisService _contentAnalysisService;

	@Reference
	private DocumentToKeywordEntryTranslator _documentToKeywordEntryTranslator;

	private volatile KeywordIndexConfiguration _keywordIndexConfiguration;

	@Reference
	private KeywordIndexNameBuilder _keywordIndexNameBuilder;

	@Reference
	private KeywordIndexReader _keywordIndexReader;

	@Reference
	private KeywordIndexWriter _keywordIndexWriter;

	@Reference
	private Queries _queries;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}