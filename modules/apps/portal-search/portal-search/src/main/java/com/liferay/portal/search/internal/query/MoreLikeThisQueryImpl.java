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

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.query.MoreLikeThisQuery;
import com.liferay.portal.search.query.QueryVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class MoreLikeThisQueryImpl
	extends BaseQueryImpl implements MoreLikeThisQuery {

	public MoreLikeThisQueryImpl(List<String> likeTexts) {
		_likeTexts.addAll(likeTexts);
	}

	public MoreLikeThisQueryImpl(String... likeTexts) {
		Collections.addAll(_likeTexts, likeTexts);
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public void addDocumentIdentifier(DocumentIdentifier documentIdentifier) {
		_documentIdentifiers.add(documentIdentifier);
	}

	public void addDocumentIdentifiers(
		Collection<DocumentIdentifier> documentIdentifiers) {

		_documentIdentifiers.addAll(documentIdentifiers);
	}

	public void addDocumentIdentifiers(
		DocumentIdentifier... documentIdentifiers) {

		Collections.addAll(_documentIdentifiers, documentIdentifiers);
	}

	public void addLikeText(String likeText) {
		_likeTexts.add(likeText);
	}

	public void addLikeTexts(Collection<String> likeTexts) {
		_likeTexts.addAll(likeTexts);
	}

	public void addLikeTexts(String... likeTexts) {
		Collections.addAll(_likeTexts, likeTexts);
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

	public Set<DocumentIdentifier> getDocumentIdentifiers() {
		return Collections.unmodifiableSet(_documentIdentifiers);
	}

	public List<String> getLikeTexts() {
		return Collections.unmodifiableList(_likeTexts);
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
		return _documentIdentifiers.isEmpty();
	}

	public boolean isFieldsEmpty() {
		return _likeTexts.isEmpty();
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
		StringBundler sb = new StringBundler(33);

		sb.append("{analyzer=");
		sb.append(_analyzer);
		sb.append(", className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", documentIdentifiers=");
		sb.append(_documentIdentifiers);
		sb.append(", fields=");
		sb.append(_likeTexts);
		sb.append(", includeInput=");
		sb.append(_includeInput);
		sb.append(", likeTexts=");
		sb.append(_likeTexts);
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

	public class DocumentIdentifierImpl implements DocumentIdentifier {

		public DocumentIdentifierImpl(String index, String id) {
			_index = index;
			_id = id;
			_type = null;
		}

		public DocumentIdentifierImpl(String index, String type, String id) {
			_index = index;
			_type = type;
			_id = id;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if ((object == null) || (getClass() != object.getClass())) {
				return false;
			}

			DocumentIdentifier documentIdentifier = (DocumentIdentifier)object;

			if (Objects.equals(_index, documentIdentifier.getIndex()) &&
				Objects.equals(_type, documentIdentifier.getType()) &&
				Objects.equals(_id, documentIdentifier.getId())) {

				return true;
			}

			return false;
		}

		public String getId() {
			return _id;
		}

		public String getIndex() {
			return _index;
		}

		public String getType() {
			return _type;
		}

		@Override
		public int hashCode() {
			return Objects.hash(_index, _type, _id);
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(9);

			sb.append("{className=");

			Class<?> clazz = getClass();

			sb.append(clazz.getSimpleName());

			sb.append(", id=");
			sb.append(_id);
			sb.append(", index=");
			sb.append(_index);
			sb.append(", type=");
			sb.append(_type);
			sb.append("}");

			return sb.toString();
		}

		private final String _id;
		private final String _index;
		private final String _type;

	}

	private static final long serialVersionUID = 1L;

	private String _analyzer;
	private final Set<DocumentIdentifier> _documentIdentifiers =
		new HashSet<>();
	private Boolean _includeInput;
	private final List<String> _likeTexts = new ArrayList<>();
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