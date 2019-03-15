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
 * @author Marcelo Mello
 */
public class DEDataLayoutCountResponse {

	public int getTotal() {
		return _total;
	}

	public static final class Builder {

		public static Builder newBuilder(int total) {
			return new Builder(total);
		}

		public static DEDataLayoutCountResponse of(int total) {
			return newBuilder(
				total
			).build();
		}

		public DEDataLayoutCountResponse build() {
			return _deDataLayoutCountResponse;
		}

		private Builder(int total) {
			_deDataLayoutCountResponse._total = total;
		}

		private final DEDataLayoutCountResponse _deDataLayoutCountResponse =
			new DEDataLayoutCountResponse();

	}

	private DEDataLayoutCountResponse() {
	}

	private int _total;

}