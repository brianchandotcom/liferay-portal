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

package com.liferay.portal.search.tuning.blueprints.misspellings.index;

import java.util.Date;
import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class MisspellingSet {

	public MisspellingSet(MisspellingSet misspellings) {
		_misspellings = misspellings._misspellings;

		_created = misspellings._created;
		_groupId = misspellings._groupId;
		_languageId = misspellings._languageId;
		_misspellingSetId = misspellings._misspellingSetId;
		_modified = misspellings._modified;
		_phrase = misspellings._phrase;
		_userId = misspellings._userId;
	}

	public Date getCreated() {
		return _created;
	}

	public Long getGroupId() {
		return _groupId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public List<String> getMisspellings() {
		return _misspellings;
	}

	public String getMisspellingSetId() {
		return _misspellingSetId;
	}

	public Date getModified() {
		return _modified;
	}

	public String getPhrase() {
		return _phrase;
	}

	public Long getUserId() {
		return _userId;
	}

	public static class MisspellingSetBuilder {

		public MisspellingSetBuilder() {
			_misspellingSet = new MisspellingSet();
		}

		public MisspellingSetBuilder(MisspellingSet misspellingSet) {
			_misspellingSet = misspellingSet;
		}

		public MisspellingSet build() {
			return new MisspellingSet(_misspellingSet);
		}

		public MisspellingSetBuilder created(Date created) {
			_misspellingSet._created = created;

			return this;
		}

		public MisspellingSetBuilder groupId(Long groupId) {
			_misspellingSet._groupId = groupId;

			return this;
		}

		public MisspellingSetBuilder languageId(String languageId) {
			_misspellingSet._languageId = languageId;

			return this;
		}

		public MisspellingSetBuilder misspellings(List<String> misspellings) {
			_misspellingSet._misspellings = misspellings;

			return this;
		}

		public MisspellingSetBuilder misspellingSetId(String misspellingSetId) {
			_misspellingSet._misspellingSetId = misspellingSetId;

			return this;
		}

		public MisspellingSetBuilder modified(Date modified) {
			_misspellingSet._modified = modified;

			return this;
		}

		public MisspellingSetBuilder phrase(String phrase) {
			_misspellingSet._phrase = phrase;

			return this;
		}

		public MisspellingSetBuilder userId(Long userId) {
			_misspellingSet._userId = userId;

			return this;
		}

		private final MisspellingSet _misspellingSet;

	}

	private MisspellingSet() {
	}

	private Date _created;
	private Long _groupId;
	private String _languageId;
	private List<String> _misspellings;
	private String _misspellingSetId;
	private Date _modified;
	private String _phrase;
	private Long _userId;

}