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

import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Drew Brokke
 */
public class CSSClassNames {

	public static String build(Consumer<Builder> builderConsumer) {
		Builder builder = new Builder();

		builderConsumer.accept(builder);

		return builder.build();
	}

	public static class Builder {

		public Builder add(String cssClassName) {
			return _add(cssClassName, true);
		}

		public Builder add(String... cssClassNames) {
			for (String cssClassName : cssClassNames) {
				_add(cssClassName, true);
			}

			return this;
		}

		public Builder add(String cssClassName, boolean condition) {
			return _add(cssClassName, condition);
		}

		protected Builder() {
		}

		protected String build() {
			return _cssClassNamesStreamBuilder.build(
			).distinct(
			).sorted(
			).collect(
				_JOIN_BY_SPACE_COLLECTOR
			);
		}

		private Builder _add(String cssClassName, boolean condition) {
			if (condition) {
				_cssClassNamesStreamBuilder.accept(cssClassName);
			}

			return this;
		}

		private final Stream.Builder<String> _cssClassNamesStreamBuilder =
			Stream.builder();

	}

	private CSSClassNames() {
	}

	private static final Collector<CharSequence, ?, String>
		_JOIN_BY_SPACE_COLLECTOR = Collectors.joining(StringPool.SPACE);

}