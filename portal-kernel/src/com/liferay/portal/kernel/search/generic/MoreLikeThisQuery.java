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

package com.liferay.portal.kernel.search.generic;

import com.liferay.portal.kernel.search.BaseQueryImpl;
import com.liferay.portal.kernel.search.query.QueryVisitor;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class MoreLikeThisQuery extends BaseQueryImpl {

	public MoreLikeThisQuery(long companyId) {
		_companyId = companyId;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	public void addDocumentUID(String documentUID) {
		_documentUIDs.add(documentUID);
	}

	public void addDocumentUIDs(Collection<String> documentUIDs) {
		_documentUIDs.addAll(documentUIDs);
	}

	public void addDocumentUIDs(String... documentUIDs) {
		Collections.addAll(_documentUIDs, documentUIDs);
	}

	public void addField(String field) {
		_fields.add(field);
	}

	public void addFields(Collection<String> fields) {
		_fields.addAll(fields);
	}

	public void addFields(String... fields) {
		Collections.addAll(_fields, fields);
	}

	public void addStopWord(String stopWord) {
		_stopWords.add(stopWord);
	}

	public void addStopWords(Collection<String> stopWords) {
		_stopWords.addAll(stopWords);
	}

	public void addStopWords(String... stopWords) {
		Collections.addAll(_stopWords, stopWords);
	}

	public String getAnalyzer() {
		return _analyzer;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Set<String> getDocumentUIDs() {
		return Collections.unmodifiableSet(_documentUIDs);
	}

	public List<String> getFields() {
		return Collections.unmodifiableList(_fields);
	}

	public String getLikeText() {
		return _likeText;
	}

	public Integer getMaxDocFrequency() {
		return _maxDocFrequency;
	}

	public Integer getMaxQueryTerms() {
		return _maxQueryTerms;
	}

	public Integer getMaxWordLength() {
		return _maxWordLength;
	}

	public Integer getMinDocFrequency() {
		return _minDocFrequency;
	}

	public String getMinShouldMatch() {
		return _minShouldMatch;
	}

	public Integer getMinTermFrequency() {
		return _minTermFrequency;
	}

	public Integer getMinWordLength() {
		return _minWordLength;
	}

	public Set<String> getStopWords() {
		return Collections.unmodifiableSet(_stopWords);
	}

	public Float getTermBoost() {
		return _termBoost;
	}

	public String getType() {
		return _type;
	}

	public boolean isDocumentUIDsEmpty() {
		return _documentUIDs.isEmpty();
	}

	public boolean isFieldsEmpty() {
		return _fields.isEmpty();
	}

	public Boolean isIncludeInput() {
		return _includeInput;
	}

	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	public void setIncludeInput(Boolean includeInput) {
		_includeInput = includeInput;
	}

	public void setLikeText(String likeText) {
		_likeText = likeText;
	}

	public void setMaxDocFrequency(Integer maxDocFrequency) {
		_maxDocFrequency = maxDocFrequency;
	}

	public void setMaxQueryTerms(Integer maxQueryTerms) {
		_maxQueryTerms = maxQueryTerms;
	}

	public void setMaxWordLength(Integer maxWordLength) {
		_maxWordLength = maxWordLength;
	}

	public void setMinDocFrequency(Integer minDocFrequency) {
		_minDocFrequency = minDocFrequency;
	}

	public void setMinShouldMatch(String minShouldMatch) {
		_minShouldMatch = minShouldMatch;
	}

	public void setMinTermFrequency(Integer minTermFrequency) {
		_minTermFrequency = minTermFrequency;
	}

	public void setMinWordLength(Integer minWordLength) {
		_minWordLength = minWordLength;
	}

	public void setTermBoost(Float termBoost) {
		_termBoost = termBoost;
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

		sb.append("{analyzer=");
		sb.append(_analyzer);
		sb.append(", className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", companyId=");
		sb.append(_companyId);
		sb.append(", documentUIDs=");
		sb.append(_documentUIDs);
		sb.append(", fields=");
		sb.append(_fields);
		sb.append(", includeInput=");
		sb.append(_includeInput);
		sb.append(", likeText=");
		sb.append(_likeText);
		sb.append(", maxDocFrequency=");
		sb.append(_maxDocFrequency);
		sb.append(", maxQueryTerms=");
		sb.append(_maxQueryTerms);
		sb.append(", maxWordLength=");
		sb.append(_maxWordLength);
		sb.append(", minDocFrequency=");
		sb.append(_minDocFrequency);
		sb.append(", minShouldMatch=");
		sb.append(_minShouldMatch);
		sb.append(", minTermFrequency=");
		sb.append(_minTermFrequency);
		sb.append(", minWordLength=");
		sb.append(_minWordLength);
		sb.append(", stopWords=");
		sb.append(_stopWords);
		sb.append(", termBoost=");
		sb.append(_termBoost);
		sb.append(", type=");
		sb.append(_type);
		sb.append("}");

		return sb.toString();
	}

	private String _analyzer;
	private final long _companyId;
	private final Set<String> _documentUIDs = new HashSet<>();
	private final List<String> _fields = new ArrayList<>();
	private Boolean _includeInput;
	private String _likeText;
	private Integer _maxDocFrequency;
	private Integer _maxQueryTerms;
	private Integer _maxWordLength;
	private Integer _minDocFrequency;
	private String _minShouldMatch;
	private Integer _minTermFrequency;
	private Integer _minWordLength;
	private final Set<String> _stopWords = new HashSet<>();
	private Float _termBoost;
	private String _type;

}