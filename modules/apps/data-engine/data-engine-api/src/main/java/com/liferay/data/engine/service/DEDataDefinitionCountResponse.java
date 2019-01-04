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
public class DEDataDefinitionCountResponse {

	public int getTotal() {
		return _total;
	}

	public static final class Builder {

		public static Builder newBuilder(int total) {
			return new Builder(total);
		}

		public static DEDataDefinitionCountResponse of(int total) {
			return newBuilder(
				total
			).build();
		}

		public DEDataDefinitionCountResponse build() {
			return _deDataDefinitionCountResponse;
		}

		private Builder(int total) {
			_deDataDefinitionCountResponse._total = total;
		}

		private final DEDataDefinitionCountResponse
			_deDataDefinitionCountResponse =
				new DEDataDefinitionCountResponse();

	}

	private DEDataDefinitionCountResponse() {
	}

	private int _total;

}