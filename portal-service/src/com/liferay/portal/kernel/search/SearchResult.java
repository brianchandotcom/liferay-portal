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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.comments.Comment;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SearchResult)) {
			return false;
		}

		SearchResult searchResult = (SearchResult)obj;

		if (Validator.equals(_classPK, searchResult._classPK) &&
			Validator.equals(_className, searchResult._className)) {

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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setClassName(String className) {
		_className = className;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setSummary(Summary summary) {
		_summary = summary;
	}

	private String _className;
	private long _classPK;
	private final List<RelatedSearchResult<Comment>>
		_commentRelatedSearchResults = new ArrayList<>();
	private final List<RelatedSearchResult<FileEntry>>
		_fileEntryRelatedSearchResults = new ArrayList<>();
	private Summary _summary;
	private final List<String> _versions = new ArrayList<>();

}