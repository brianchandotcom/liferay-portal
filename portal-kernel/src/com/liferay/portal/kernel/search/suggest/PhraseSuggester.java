/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.search.suggest;

import com.liferay.portal.kernel.search.Query;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class PhraseSuggester extends BaseSuggester {

	public PhraseSuggester(String name, String field) {
		super(name, field);
	}

	public PhraseSuggester(String name, String field, String value) {
		super(name, field, value);
	}

	@Override
	public <T> T accept(SuggesterVisitor<T> suggesterVisitor) {
		return suggesterVisitor.visit(this);
	}

	public void addCandidateGenerator(CandidateGenerator candidateGenerator) {
		_candidateGenerators.add(candidateGenerator);
	}

	public String getAnalyzer() {
		return _analyzer;
	}

	public Set<CandidateGenerator> getCandidateGenerators() {
		return Collections.unmodifiableSet(_candidateGenerators);
	}

	public Collate getCollate() {
		return _collate;
	}

	public Float getConfidence() {
		return _confidence;
	}

	public Integer getGramSize() {
		return _gramSize;
	}

	public Float getMaxErrors() {
		return _maxErrors;
	}

	public String getPostHighlightFilter() {
		return _postHighlightFilter;
	}

	public String getPreHighlightFilter() {
		return _preHighlightFilter;
	}

	public Float getRealWordErrorLikelihood() {
		return _realWordErrorLikelihood;
	}

	public String getSeparator() {
		return _separator;
	}

	public Integer getShardSize() {
		return _shardSize;
	}

	public Integer getSize() {
		return _size;
	}

	public Integer getTokenLimit() {
		return _tokenLimit;
	}

	public Boolean isForceUnigrams() {
		return _forceUnigrams;
	}

	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	public void setCollate(Collate collate) {
		_collate = collate;
	}

	public void setConfidence(Float confidence) {
		_confidence = confidence;
	}

	public void setForceUnigrams(Boolean forceUnigrams) {
		_forceUnigrams = forceUnigrams;
	}

	public void setGramSize(Integer gramSize) {
		_gramSize = gramSize;
	}

	public void setMaxErrors(Float maxErrors) {
		_maxErrors = maxErrors;
	}

	public void setPostHighlightFilter(String postHighlightFilter) {
		_postHighlightFilter = postHighlightFilter;
	}

	public void setPreHighlightFilter(String preHighlightFilter) {
		_preHighlightFilter = preHighlightFilter;
	}

	public void setRealWordErrorLikelihood(Float realWordErrorLikelihood) {
		_realWordErrorLikelihood = realWordErrorLikelihood;
	}

	public void setSeparator(String separator) {
		_separator = separator;
	}

	public void setShardSize(Integer shardSize) {
		_shardSize = shardSize;
	}

	public void setSize(Integer size) {
		_size = size;
	}

	public void setTokenLimit(Integer tokenLimit) {
		_tokenLimit = tokenLimit;
	}

	public static class CandidateGenerator {

		public CandidateGenerator(String field) {
			_field = field;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if ((object == null) || (object.getClass() != getClass())) {
				return false;
			}

			CandidateGenerator candidateGenerator = (CandidateGenerator)object;

			return _field.equals(candidateGenerator.getField());
		}

		public Float getAccuracy() {
			return _accuracy;
		}

		public String getField() {
			return _field;
		}

		public Integer getMaxEdits() {
			return _maxEdits;
		}

		public Integer getMaxExpansions() {
			return _maxExpansions;
		}

		public Integer getMaxInspections() {
			return _maxInspections;
		}

		public Integer getMaxTermFreq() {
			return _maxTermFreq;
		}

		public Integer getMinDocFreq() {
			return _minDocFreq;
		}

		public Integer getMinWordLength() {
			return _minWordLength;
		}

		public String getPostFilterAnalyzer() {
			return _postFilterAnalyzer;
		}

		public String getPreFilterAnalyzer() {
			return _preFilterAnalyzer;
		}

		public Integer getPrefixLength() {
			return _prefixLength;
		}

		public Integer getSize() {
			return _size;
		}

		public Sort getSort() {
			return _sort;
		}

		public StringDistance getStringDistance() {
			return _stringDistance;
		}

		public SuggestMode getSuggestMode() {
			return _suggestMode;
		}

		@Override
		public int hashCode() {
			return _field.hashCode();
		}

		public void setAccuracy(Float accuracy) {
			_accuracy = accuracy;
		}

		public void setMaxEdits(Integer maxEdits) {
			_maxEdits = maxEdits;
		}

		public void setMaxExpansions(Integer maxExpansions) {
			_maxExpansions = maxExpansions;
		}

		public void setMaxInspections(Integer maxInspections) {
			_maxInspections = maxInspections;
		}

		public void setMaxTermFreq(Integer maxTermFreq) {
			_maxTermFreq = maxTermFreq;
		}

		public void setMinDocFreq(Integer minDocFreq) {
			_minDocFreq = minDocFreq;
		}

		public void setMinWordLength(Integer minWordLength) {
			_minWordLength = minWordLength;
		}

		public void setPostFilterAnalyzer(String postFilterAnalyzer) {
			_postFilterAnalyzer = postFilterAnalyzer;
		}

		public void setPreFilterAnalyzer(String preFilterAnalyzer) {
			_preFilterAnalyzer = preFilterAnalyzer;
		}

		public void setPrefixLength(Integer prefixLength) {
			_prefixLength = prefixLength;
		}

		public void setSize(Integer size) {
			_size = size;
		}

		public void setSort(Sort sort) {
			_sort = sort;
		}

		public void setStringDistance(StringDistance stringDistance) {
			_stringDistance = stringDistance;
		}

		public void setSuggestMode(SuggestMode suggestMode) {
			_suggestMode = suggestMode;
		}

		private Float _accuracy;
		private final String _field;
		private Integer _maxEdits;
		private Integer _maxExpansions;
		private Integer _maxInspections;
		private Integer _maxTermFreq;
		private Integer _minDocFreq;
		private Integer _minWordLength;
		private String _postFilterAnalyzer;
		private String _preFilterAnalyzer;
		private Integer _prefixLength;
		private Integer _size;
		private Sort _sort;
		private StringDistance _stringDistance;
		private SuggestMode _suggestMode;

	}

	public static class Collate {

		public Collate(Query query) {
			_query = query;
		}

		public void addParams(String key, Object value) {
			_params.put(key, value);
		}

		public Map<String, Object> getParams() {
			return _params;
		}

		public Query getQuery() {
			return _query;
		}

		public Boolean isPrune() {
			return _prune;
		}

		public void setPrune(Boolean prune) {
			_prune = prune;
		}

		private final Map<String, Object> _params = new HashMap<>();
		private Boolean _prune;
		private final Query _query;

	}

	private String _analyzer;
	private final Set<CandidateGenerator> _candidateGenerators =
		new HashSet<>();
	private Collate _collate;
	private Float _confidence;
	private Boolean _forceUnigrams;
	private Integer _gramSize;
	private Float _maxErrors;
	private String _postHighlightFilter;
	private String _preHighlightFilter;
	private Float _realWordErrorLikelihood;
	private String _separator;
	private Integer _shardSize;
	private Integer _size;
	private Integer _tokenLimit;

}