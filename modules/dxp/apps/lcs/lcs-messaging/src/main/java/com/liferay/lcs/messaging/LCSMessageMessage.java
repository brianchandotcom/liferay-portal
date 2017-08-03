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

package com.liferay.lcs.messaging;

/**
 * @author Ivica Cardic
 */
public class LCSMessageMessage extends Message {

	public String getContent() {
		return _content;
	}

	public long getCorpProjectId() {
		return _corpProjectId;
	}

	public long getSourceMessageId() {
		return _sourceMessageId;
	}

	public int getType() {
		return _type;
	}

	public boolean isDelete() {
		return _delete;
	}

	public void setContent(String content) {
		_content = content;
	}

	public void setCorpProjectId(long corpProjectId) {
		_corpProjectId = corpProjectId;
	}

	public void setDelete(boolean delete) {
		_delete = delete;
	}

	public void setSourceMessageId(long sourceMessageId) {
		_sourceMessageId = sourceMessageId;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _content;
	private long _corpProjectId;
	private boolean _delete;
	private long _sourceMessageId;
	private int _type;

}