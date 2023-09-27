/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public MoreLikeThisQueryImpl(List<String> fields, String... likeTexts) {
		_fields.addAll(fields);

		Collections.addAll(_likeTexts, likeTexts);
	}

	public MoreLikeThisQueryImpl(Set<DocumentIdentifier> documentIdentifiers) {
		_documentIdentifiers.addAll(documentIdentifiers);
	}

	public MoreLikeThisQueryImpl(String[] fields, String... likeTexts) {
		Collections.addAll(_fields, fields);
		Collections.addAll(_likeTexts, likeTexts);
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public void addDocumentIdentifier(DocumentIdentifier documentIdentifier) {
		_documentIdentifiers.add(documentIdentifier);
	}

	@Override
	public void addDocumentIdentifiers(
		Collection<DocumentIdentifier> documentIdentifiers) {

		_documentIdentifiers.addAll(documentIdentifiers);
	}

	public void addDocumentIdentifiers(
		DocumentIdentifier... documentIdentifiers) {

		Collections.addAll(_documentIdentifiers, documentIdentifiers);
	}

	@Override
	public void addField(String field) {
		_fields.add(field);
	}

	@Override
	public void addFields(Collection<String> fields) {
		_fields.addAll(fields);
	}

	@Override
	public void addFields(String... fields) {
		Collections.addAll(_fields, fields);
	}

	@Override
	public void addLikeText(String likeText) {
		_likeTexts.add(likeText);
	}

	@Override
	public void addLikeTexts(Collection<String> likeTexts) {
		_likeTexts.addAll(likeTexts);
	}

	@Override
	public void addLikeTexts(String... likeTexts) {
		Collections.addAll(_likeTexts, likeTexts);
	}

	@Override
	public void addStopWord(String stopWord) {
		_stopWords.add(stopWord);
	}

	@Override
	public void addStopWords(Collection<String> stopWords) {
		_stopWords.addAll(stopWords);
	}

	@Override
	public void addStopWords(String... stopWords) {
		Collections.addAll(_stopWords, stopWords);
	}

	@Override
	public String getAnalyzer() {
		return _analyzer;
	}

	@Override
	public Set<DocumentIdentifier> getDocumentIdentifiers() {
		return Collections.unmodifiableSet(_documentIdentifiers);
	}

	@Override
	public List<String> getFields() {
		return Collections.unmodifiableList(_fields);
	}

	@Override
	public List<String> getLikeTexts() {
		return Collections.unmodifiableList(_likeTexts);
	}

	@Override
	public Integer getMaxDocFrequency() {
		return _maxDocFrequency;
	}

	@Override
	public Integer getMaxQueryTerms() {
		return _maxQueryTerms;
	}

	@Override
	public Integer getMaxWordLength() {
		return _maxWordLength;
	}

	@Override
	public Integer getMinDocFrequency() {
		return _minDocFrequency;
	}

	@Override
	public String getMinShouldMatch() {
		return _minShouldMatch;
	}

	@Override
	public Integer getMinTermFrequency() {
		return _minTermFrequency;
	}

	@Override
	public Integer getMinWordLength() {
		return _minWordLength;
	}

	@Override
	public Set<String> getStopWords() {
		return Collections.unmodifiableSet(_stopWords);
	}

	@Override
	public Float getTermBoost() {
		return _termBoost;
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public boolean isDocumentUIDsEmpty() {
		return _documentIdentifiers.isEmpty();
	}

	@Override
	public boolean isFieldsEmpty() {
		return _likeTexts.isEmpty();
	}

	@Override
	public Boolean isIncludeInput() {
		return _includeInput;
	}

	@Override
	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	@Override
	public void setIncludeInput(Boolean includeInput) {
		_includeInput = includeInput;
	}

	@Override
	public void setMaxDocFrequency(Integer maxDocFrequency) {
		_maxDocFrequency = maxDocFrequency;
	}

	@Override
	public void setMaxQueryTerms(Integer maxQueryTerms) {
		_maxQueryTerms = maxQueryTerms;
	}

	@Override
	public void setMaxWordLength(Integer maxWordLength) {
		_maxWordLength = maxWordLength;
	}

	@Override
	public void setMinDocFrequency(Integer minDocFrequency) {
		_minDocFrequency = minDocFrequency;
	}

	@Override
	public void setMinShouldMatch(String minShouldMatch) {
		_minShouldMatch = minShouldMatch;
	}

	@Override
	public void setMinTermFrequency(Integer minTermFrequency) {
		_minTermFrequency = minTermFrequency;
	}

	@Override
	public void setMinWordLength(Integer minWordLength) {
		_minWordLength = minWordLength;
	}

	@Override
	public void setTermBoost(Float termBoost) {
		_termBoost = termBoost;
	}

	@Override
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
		sb.append(_fields);
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

	public static class DocumentIdentifierImpl implements DocumentIdentifier {

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
	private final List<String> _fields = new ArrayList<>();
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