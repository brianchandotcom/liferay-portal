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
import com.liferay.portal.kernel.util.HashUtil;
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addMBMessage(
	 *             MBMessage, Summary)}
	 */
	@Deprecated
	public void addMBMessage(MBMessage mbMessage) {
		Summary summary = new Summary(null, mbMessage.getBody());

		summary.setEscape(false);

		addMBMessage(mbMessage, summary);
	}

	public void addMBMessage(MBMessage mbMessage, Summary summary) {
		_mbMessageTuples.add(new Tuple(mbMessage, summary));
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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement.
	 */
	@Deprecated
	public List<MBMessage> getMBMessages() {
		List<MBMessage> mbMessages = new ArrayList<>();

		for (Tuple tuple : _mbMessageTuples) {
			mbMessages.add((MBMessage)tuple.getObject(0));
		}

		return mbMessages;
	}

	public List<Tuple> getMBMessageTuples() {
		return _mbMessageTuples;
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
	 * @deprecated As of 7.0.0, with no direct replacement.
	 */
	@Deprecated
	public void setClassName(String className) {
		_className = className;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement.
	 */
	@Deprecated
	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement.
	 */
	@Deprecated
	public void setMessages(List<MBMessage> mbMessages) {
		_mbMessageTuples.clear();

		for (MBMessage mbMessage : mbMessages) {
			addMBMessage(mbMessage);
		}
	}

	public void setSummary(Summary summary) {
		_summary = summary;
	}

	private String _className;
	private long _classPK;
	private final List<Tuple> _fileEntryTuples = new ArrayList<>();
	private final List<Tuple> _mbMessageTuples = new ArrayList<>();
	private Summary _summary;
	private final List<String> _versions = new ArrayList<>();

}