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

package com.liferay.users.admin.web.internal.util;

import com.liferay.petra.string.StringPool;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Drew Brokke
 */
public class CSSClassNames {

	public static Builder builder(String... cssClasses) {
		Builder builder = new Builder();

		builder.add(cssClasses);

		return builder;
	}

	public static class Builder {

		public Builder add(String cssClass) {
			return _add(cssClass, true);
		}

		public Builder add(String... cssClasses) {
			for (String cssClass : cssClasses) {
				_add(cssClass, true);
			}

			return this;
		}

		public Builder add(String cssClass, boolean condition) {
			return _add(cssClass, condition);
		}

		public String build() {
			return _build();
		}

		private Builder() {
		}

		private Builder _add(String cssClass, boolean condition) {
			if (condition) {
				_streamBuilder.accept(cssClass);
			}

			return this;
		}

		private String _build() {
			return _streamBuilder.build(
			).distinct(
			).sorted(
			).collect(
				Collectors.joining(StringPool.SPACE)
			);
		}

		private final Stream.Builder<String> _streamBuilder = Stream.builder();

	}

	private CSSClassNames() {
	}

}