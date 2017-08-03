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

package com.liferay.lcs.rest;

import java.util.Date;

/**
 * @author Igor Beslic
 */
public class LCSClusterEntryTokenImpl implements LCSClusterEntryToken {

	@Override
	public String getContent() {
		return _content;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public long getLcsClusterEntryId() {
		return _lcsClusterEntryId;
	}

	@Override
	public long getLcsClusterEntryTokenId() {
		return _lcsClusterEntryTokenId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setContent(String content) {
		_content = content;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public void setLcsClusterEntryId(long lcsClusterEntryId) {
		_lcsClusterEntryId = lcsClusterEntryId;
	}

	@Override
	public void setLcsClusterEntryTokenId(long lcsClusterEntryTokenId) {
		_lcsClusterEntryTokenId = lcsClusterEntryTokenId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	private String _content;
	private Date _createDate;
	private long _lcsClusterEntryId;
	private long _lcsClusterEntryTokenId;
	private long _userId;

}