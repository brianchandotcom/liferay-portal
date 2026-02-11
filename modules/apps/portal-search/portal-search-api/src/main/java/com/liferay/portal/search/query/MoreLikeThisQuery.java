/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public abstract class MoreLikeThisQuery extends Query {

	public abstract void addDocumentIdentifier(
		DocumentIdentifier documentIdentifier);

	public abstract void addDocumentIdentifiers(
		Collection<DocumentIdentifier> documentIdentifiers);

	public abstract void addDocumentIdentifiers(
		DocumentIdentifier... documentIdentifiers);

	public abstract void addField(String field);

	public abstract void addFields(Collection<String> fields);

	public abstract void addFields(String... fields);

	public abstract void addLikeText(String likeText);

	public abstract void addLikeTexts(Collection<String> likeTexts);

	public abstract void addLikeTexts(String... likeTexts);

	public abstract void addStopWord(String stopWord);

	public abstract void addStopWords(Collection<String> stopWords);

	public abstract void addStopWords(String... stopWords);

	public abstract String getAnalyzer();

	public abstract Set<DocumentIdentifier> getDocumentIdentifiers();

	public abstract List<String> getFields();

	public abstract List<String> getLikeTexts();

	public abstract Integer getMaxDocFrequency();

	public abstract Integer getMaxQueryTerms();

	public abstract Integer getMaxWordLength();

	public abstract Integer getMinDocFrequency();

	public abstract String getMinShouldMatch();

	public abstract Integer getMinTermFrequency();

	public abstract Integer getMinWordLength();

	public abstract Set<String> getStopWords();

	public abstract Float getTermBoost();

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public abstract String getType();

	public abstract boolean isDocumentUIDsEmpty();

	public abstract boolean isFieldsEmpty();

	public abstract Boolean isIncludeInput();

	public abstract void setAnalyzer(String analyzer);

	public abstract void setIncludeInput(Boolean includeInput);

	public abstract void setMaxDocFrequency(Integer maxDocFrequency);

	public abstract void setMaxQueryTerms(Integer maxQueryTerms);

	public abstract void setMaxWordLength(Integer maxWordLength);

	public abstract void setMinDocFrequency(Integer minDocFrequency);

	public abstract void setMinShouldMatch(String minShouldMatch);

	public abstract void setMinTermFrequency(Integer minTermFrequency);

	public abstract void setMinWordLength(Integer minWordLength);

	public abstract void setTermBoost(Float termBoost);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public abstract void setType(String type);

	public interface DocumentIdentifier {

		public String getId();

		public String getIndex();

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		@Deprecated
		public String getType();

	}

}