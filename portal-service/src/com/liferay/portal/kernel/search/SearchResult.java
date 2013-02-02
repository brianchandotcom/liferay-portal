/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

	public void addFileEntry(FileEntry fileEntry) {
		_fileEntries.add(fileEntry);
	}

	public void addMBMessage(MBMessage mbMessage) {
		_mbMessages.add(mbMessage);
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

	public List<FileEntry> getFileEntries() {
		return _fileEntries;
	}

	public List<MBMessage> getMBMessages() {
		return _mbMessages;
	}

	public Summary getSummary() {
		return _summary;
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
	private List<FileEntry> _fileEntries = new ArrayList<FileEntry>();
	private List<MBMessage> _mbMessages = new ArrayList<MBMessage>();
	private Summary _summary;

}