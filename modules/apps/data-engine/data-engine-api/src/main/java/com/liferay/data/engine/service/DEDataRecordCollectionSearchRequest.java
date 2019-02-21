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

package com.liferay.data.engine.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;

/**
 * @author Marcelo Mello
 */
public class DEDataRecordCollectionSearchRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public int getEnd() {
		return _end;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getKeywords() {
		return _keywords;
	}

	public int getStart() {
		return _start;
	}

	public static final class Builder {

		public DEDataRecordCollectionSearchRequest build() {
			return _deDataRecordCollectionSearchRequest;
		}

		public Builder endingAt(int end) {
			_deDataRecordCollectionSearchRequest._end = end;

			return this;
		}

		public Builder havingKeywords(String keywords) {
			_deDataRecordCollectionSearchRequest._keywords = keywords;

			return this;
		}

		public Builder inCompany(long companyId) {
			_deDataRecordCollectionSearchRequest._companyId = companyId;

			return this;
		}

		public Builder inGroup(long groupId) {
			_deDataRecordCollectionSearchRequest._groupId = groupId;

			return this;
		}

		public Builder startingAt(int start) {
			_deDataRecordCollectionSearchRequest._start = start;

			return this;
		}

		private final DEDataRecordCollectionSearchRequest
			_deDataRecordCollectionSearchRequest =
				new DEDataRecordCollectionSearchRequest();

	}

	private DEDataRecordCollectionSearchRequest() {
	}

	private long _companyId;
	private int _end = QueryUtil.ALL_POS;
	private long _groupId;
	private String _keywords;
	private int _start = QueryUtil.ALL_POS;

}