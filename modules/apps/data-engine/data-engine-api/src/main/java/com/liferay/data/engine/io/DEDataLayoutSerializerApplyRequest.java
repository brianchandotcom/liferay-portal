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

package com.liferay.data.engine.io;

import com.liferay.data.engine.model.DEDataLayout;

/**
 * {@link DEDataLayout} serialize request class.
 * @author Jeyvison Nascimento
 * @review
 */
public class DEDataLayoutSerializerApplyRequest {

	/**
	 * Returns the {@link DEDataLayout} object that will be serialized
	 * @return the DEDataLayout object
	 * @review
	 */
	public DEDataLayout getDEDataLayout() {
		return _deDataLayout;
	}

	/**
	 * Inner builder that assembles the request.
	 */
	public static final class Builder {

		/**
		 * Creates a new Builder with a DEDataLayout parameter
		 * @param deDataLayout The {@link DEDataLayout} that will be serialized
		 * @return the builder object
		 */
		public static Builder newBuilder(DEDataLayout deDataLayout) {
			return new Builder(deDataLayout);
		}

		/**
		 * Creates a new request out of a {@link DEDataLayout} object.
		 * @param deDataLayout The {@link DEDataLayout} that will be serialized
		 * @return the {@link DEDataLayoutSerializerApplyRequest} object
		 */
		public static DEDataLayoutSerializerApplyRequest of(
			DEDataLayout deDataLayout) {

			return newBuilder(
				deDataLayout
			).build();
		}

		/**
		 * Builds the request.
		 * @return the {@link DEDataLayoutSerializerApplyRequest} object.
		 */
		public DEDataLayoutSerializerApplyRequest build() {
			return _deDataLayoutSerializerApplyRequest;
		}

		private Builder(DEDataLayout deDataLayout) {
			_deDataLayoutSerializerApplyRequest._deDataLayout = deDataLayout;
		}

		private DEDataLayoutSerializerApplyRequest
			_deDataLayoutSerializerApplyRequest =
				new DEDataLayoutSerializerApplyRequest();

	}

	private DEDataLayoutSerializerApplyRequest() {
	}

	private DEDataLayout _deDataLayout;

}