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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.messageboards.model.MBMessage;

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

	public void addFileEntry(FileEntry fileEntry, Summary summary) {
		Tuple tuple = new Tuple(fileEntry, summary);

		_fileEntryTuples.add(tuple);
	}

	public void addMBMessage(MBMessage mbMessage) {
		_mbMessages.add(mbMessage);
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

	public List<Tuple> getFileEntryTuples() {
		return _fileEntryTuples;
	}

	public List<MBMessage> getMBMessages() {
		return _mbMessages;
	}

	public Summary getSummary() {
		return _summary;
	}

	public List<String> getVersions() {
		return _versions;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setMessages(List<MBMessage> mbMessages) {
		_mbMessages = mbMessages;
	}

	public void setSummary(Summary summary) {
		_summary = summary;
	}

	private String _className;
	private long _classPK;
	private final List<Tuple> _fileEntryTuples = new ArrayList<Tuple>();
	private List<MBMessage> _mbMessages = new ArrayList<MBMessage>();
	private Summary _summary;
	private final List<String> _versions = new ArrayList<String>();

}