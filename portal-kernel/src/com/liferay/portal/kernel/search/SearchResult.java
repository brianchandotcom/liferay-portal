/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class SearchResult {

	public SearchResult(String className, long classPK) {
		_className = className;
		_classPK = classPK;
	}

	public void addComment(Comment comment, Summary summary) {
		_commentRelatedSearchResults.add(
			new RelatedSearchResult<>(comment, summary));
	}

	public void addFileEntry(FileEntry fileEntry, Summary summary) {
		_fileEntryRelatedSearchResults.add(
			new RelatedSearchResult<>(fileEntry, summary));
	}

	public void addVersion(String version) {
		_versions.add(version);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SearchResult)) {
			return false;
		}

		SearchResult searchResult = (SearchResult)object;

		if ((_classPK == searchResult._classPK) &&
			Objects.equals(_className, searchResult._className)) {

			return true;
		}

		return false;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public List<RelatedSearchResult<Comment>> getCommentRelatedSearchResults() {
		return _commentRelatedSearchResults;
	}

	public List<RelatedSearchResult<FileEntry>>
		getFileEntryRelatedSearchResults() {

		return _fileEntryRelatedSearchResults;
	}

	public Summary getSummary() {
		return _summary;
	}

	public List<String> getVersions() {
		return _versions;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _classPK);

		return HashUtil.hash(hash, _className);
	}

	public void setSummary(Summary summary) {
		_summary = summary;
	}

	private final String _className;
	private long _classPK;
	private final List<RelatedSearchResult<Comment>>
		_commentRelatedSearchResults = new ArrayList<>();
	private final List<RelatedSearchResult<FileEntry>>
		_fileEntryRelatedSearchResults = new ArrayList<>();
	private Summary _summary;
	private final List<String> _versions = new ArrayList<>();

}