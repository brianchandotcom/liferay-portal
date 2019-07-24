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

package com.liferay.portal.search.query;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface MoreLikeThisQuery extends Query {

	public void addDocumentIdentifier(DocumentIdentifier documentIdentifier);

	public void addDocumentIdentifiers(
		Collection<DocumentIdentifier> documentIdentifiers);

	public void addDocumentIdentifiers(
		DocumentIdentifier... documentIdentifiers);

	public void addField(String field);

	public void addFields(Collection<String> fields);

	public void addFields(String... fields);

	public void addLikeText(String likeText);

	public void addLikeTexts(Collection<String> likeTexts);

	public void addLikeTexts(String... likeTexts);

	public void addStopWord(String stopWord);

	public void addStopWords(Collection<String> stopWords);

	public void addStopWords(String... stopWords);

	public String getAnalyzer();

	public Set<DocumentIdentifier> getDocumentIdentifiers();

	public List<String> getFields();

	public List<String> getLikeTexts();

	public Integer getMaxDocFrequency();

	public Integer getMaxQueryTerms();

	public Integer getMaxWordLength();

	public Integer getMinDocFrequency();

	public String getMinShouldMatch();

	public Integer getMinTermFrequency();

	public Integer getMinWordLength();

	public Set<String> getStopWords();

	public Float getTermBoost();

	public String getType();

	public boolean isDocumentUIDsEmpty();

	public boolean isFieldsEmpty();

	public Boolean isIncludeInput();

	public void setAnalyzer(String analyzer);

	public void setIncludeInput(Boolean includeInput);

	public void setMaxDocFrequency(Integer maxDocFrequency);

	public void setMaxQueryTerms(Integer maxQueryTerms);

	public void setMaxWordLength(Integer maxWordLength);

	public void setMinDocFrequency(Integer minDocFrequency);

	public void setMinShouldMatch(String minShouldMatch);

	public void setMinTermFrequency(Integer minTermFrequency);

	public void setMinWordLength(Integer minWordLength);

	public void setTermBoost(Float termBoost);

	public void setType(String type);

	public interface DocumentIdentifier {

		public String getId();

		public String getIndex();

		public String getType();

	}

}