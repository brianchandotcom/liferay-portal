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

package com.liferay.portal.search.tuning.blueprints.keyword.index.index;

import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.KeywordEntryStatus;

import java.util.Date;
import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class KeywordEntry {

	public KeywordEntry(KeywordEntry keywordEntry) {
		_companyId = keywordEntry._companyId;
		_content = keywordEntry._content;
		_created = keywordEntry._created;
		_groupId = keywordEntry._groupId;
		_hitCount = keywordEntry._hitCount;
		_languageId = keywordEntry._languageId;
		_lastAccessed = keywordEntry._lastAccessed;
		_lastReported = keywordEntry._lastReported;
		_modified = keywordEntry._modified;
		_keywordEntryId = keywordEntry._keywordEntryId;
		_reportCount = keywordEntry._reportCount;
		_reports = keywordEntry._reports;
		_status = keywordEntry._status;
		_statusDate = keywordEntry._statusDate;
	}

	public Long getCompanyId() {
		return _companyId;
	}

	public String getContent() {
		return _content;
	}

	public Date getCreated() {
		return _created;
	}

	public Long getGroupId() {
		return _groupId;
	}

	public Long getHitCount() {
		return _hitCount;
	}

	public String getKeywordEntryId() {
		return _keywordEntryId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public Date getLastAccessed() {
		return _lastAccessed;
	}

	public Date getLastReported() {
		return _lastReported;
	}

	public Date getModified() {
		return _modified;
	}

	public Long getReportCount() {
		return _reportCount;
	}

	public List<String> getReports() {
		return _reports;
	}

	public KeywordEntryStatus getStatus() {
		return _status;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public static class KeywordEntryBuilder {

		public KeywordEntryBuilder() {
			_keywordEntry = new KeywordEntry();
		}

		public KeywordEntryBuilder(KeywordEntry keywordEntry) {
			_keywordEntry = keywordEntry;
		}

		public KeywordEntry build() {
			return new KeywordEntry(_keywordEntry);
		}

		public KeywordEntryBuilder companyId(Long companyId) {
			_keywordEntry._companyId = companyId;

			return this;
		}

		public KeywordEntryBuilder content(String content) {
			_keywordEntry._content = content;

			return this;
		}

		public KeywordEntryBuilder created(Date created) {
			_keywordEntry._created = created;

			return this;
		}

		public KeywordEntryBuilder groupId(Long groupId) {
			_keywordEntry._groupId = groupId;

			return this;
		}

		public KeywordEntryBuilder hitCount(Long hitCount) {
			_keywordEntry._hitCount = hitCount;

			return this;
		}

		public KeywordEntryBuilder keywordEntryId(String keywordEntryId) {
			_keywordEntry._keywordEntryId = keywordEntryId;

			return this;
		}

		public KeywordEntryBuilder languageId(String languageId) {
			_keywordEntry._languageId = languageId;

			return this;
		}

		public KeywordEntryBuilder lastAccessed(Date lastAccessed) {
			_keywordEntry._lastAccessed = lastAccessed;

			return this;
		}

		public KeywordEntryBuilder lastReported(Date lastReported) {
			_keywordEntry._lastReported = lastReported;

			return this;
		}

		public KeywordEntryBuilder modified(Date modified) {
			_keywordEntry._modified = modified;

			return this;
		}

		public KeywordEntryBuilder reportCount(List<String> reports) {
			_keywordEntry._reports = reports;

			return this;
		}

		public KeywordEntryBuilder reportCount(Long reportCount) {
			_keywordEntry._reportCount = reportCount;

			return this;
		}

		public KeywordEntryBuilder status(KeywordEntryStatus status) {
			_keywordEntry._status = status;

			return this;
		}

		public KeywordEntryBuilder statusDate(Date statusDate) {
			_keywordEntry._statusDate = statusDate;

			return this;
		}

		private final KeywordEntry _keywordEntry;

	}

	private KeywordEntry() {
	}

	private Long _companyId;
	private String _content;
	private Date _created;
	private Long _groupId;
	private Long _hitCount;
	private String _keywordEntryId;
	private String _languageId;
	private Date _lastAccessed;
	private Date _lastReported;
	private Date _modified;
	private Long _reportCount;
	private List<String> _reports;
	private KeywordEntryStatus _status;
	private Date _statusDate;

}