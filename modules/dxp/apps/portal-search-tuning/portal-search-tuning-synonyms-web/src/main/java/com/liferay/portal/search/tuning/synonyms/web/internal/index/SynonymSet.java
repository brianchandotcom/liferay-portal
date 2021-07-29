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

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

/**
 * @author Adam Brandizzi
 */
public class SynonymSet {

	public SynonymSet(SynonymSet synonymSet) {
		_synonymSetDocumentId = synonymSet._synonymSetDocumentId;
		_synonyms = synonymSet._synonyms;
	}

	public String getSynonyms() {
		return _synonyms;
	}

	public String getSynonymSetDocumentId() {
		return _synonymSetDocumentId;
	}

	public static class SynonymSetBuilder {

		public SynonymSetBuilder() {
			_synonymSet = new SynonymSet();
		}

		public SynonymSetBuilder(SynonymSet synonymSet) {
			_synonymSet = synonymSet;
		}

		public SynonymSet build() {
			return new SynonymSet(_synonymSet);
		}

		public SynonymSetBuilder synonyms(String synonyms) {
			_synonymSet._synonyms = synonyms;

			return this;
		}

		public SynonymSetBuilder synonymSetDocumentId(
			String synonymSetDocumentId) {

			_synonymSet._synonymSetDocumentId = synonymSetDocumentId;

			return this;
		}

		private final SynonymSet _synonymSet;

	}

	private SynonymSet() {
	}

	private String _synonyms;
	private String _synonymSetDocumentId;

}