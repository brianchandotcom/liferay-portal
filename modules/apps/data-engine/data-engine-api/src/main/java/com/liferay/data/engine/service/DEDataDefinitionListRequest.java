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
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionListRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public int getEnd() {
		return _end;
	}

	public long getGroupId() {
		return _groupId;
	}

	public int getStart() {
		return _start;
	}

	public static final class Builder {

		public DEDataDefinitionListRequest build() {
			return _deDataDefinitionListRequest;
		}

		public Builder endingAt(int end) {
			_deDataDefinitionListRequest._end = end;

			return this;
		}

		public Builder inCompany(long companyId) {
			_deDataDefinitionListRequest._companyId = companyId;

			return this;
		}

		public Builder inGroup(long groupId) {
			_deDataDefinitionListRequest._groupId = groupId;

			return this;
		}

		public Builder startingAt(int start) {
			_deDataDefinitionListRequest._start = start;

			return this;
		}

		private final DEDataDefinitionListRequest _deDataDefinitionListRequest =
			new DEDataDefinitionListRequest();

	}

	private DEDataDefinitionListRequest() {
	}

	private long _companyId;
	private int _end = QueryUtil.ALL_POS;
	private long _groupId;
	private int _start = QueryUtil.ALL_POS;

}