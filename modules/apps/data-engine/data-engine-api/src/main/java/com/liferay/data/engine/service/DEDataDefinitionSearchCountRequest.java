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

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionSearchCountRequest {

	public long getCompanyId() {
		return _companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getKeywords() {
		return _keywords;
	}

	public static final class Builder {

		public DEDataDefinitionSearchCountRequest build() {
			return _deDataDefinitionSearchCountRequest;
		}

		public Builder havingKeywords(String keywords) {
			_deDataDefinitionSearchCountRequest._keywords = keywords;

			return this;
		}

		public Builder inCompany(long companyId) {
			_deDataDefinitionSearchCountRequest._companyId = companyId;

			return this;
		}

		public Builder inGroup(long groupId) {
			_deDataDefinitionSearchCountRequest._groupId = groupId;

			return this;
		}

		private final DEDataDefinitionSearchCountRequest
			_deDataDefinitionSearchCountRequest =
				new DEDataDefinitionSearchCountRequest();

	}

	private DEDataDefinitionSearchCountRequest() {
	}

	private long _companyId;
	private long _groupId;
	private String _keywords;

}