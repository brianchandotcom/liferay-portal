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
 * @author Gabriel Albuquerque
 */
public class DEDataDefinitionSearchRequest {

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

		public DEDataDefinitionSearchRequest build() {
			return _deDataDefinitionSearchRequest;
		}

		public Builder endingAt(int end) {
			_deDataDefinitionSearchRequest._end = end;

			return this;
		}

		public Builder havingKeywords(String keywords) {
			_deDataDefinitionSearchRequest._keywords = keywords;

			return this;
		}

		public Builder inCompany(long companyId) {
			_deDataDefinitionSearchRequest._companyId = companyId;

			return this;
		}

		public Builder inGroup(long groupId) {
			_deDataDefinitionSearchRequest._groupId = groupId;

			return this;
		}

		public Builder startingAt(int start) {
			_deDataDefinitionSearchRequest._start = start;

			return this;
		}

		private final DEDataDefinitionSearchRequest
			_deDataDefinitionSearchRequest =
				new DEDataDefinitionSearchRequest();

	}

	private DEDataDefinitionSearchRequest() {
	}

	private long _companyId;
	private int _end = QueryUtil.ALL_POS;
	private long _groupId;
	private String _keywords;
	private int _start = QueryUtil.ALL_POS;

}