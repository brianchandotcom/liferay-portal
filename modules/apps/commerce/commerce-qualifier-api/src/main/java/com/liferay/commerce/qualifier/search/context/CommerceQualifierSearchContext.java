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

package com.liferay.commerce.qualifier.search.context;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Riccardo Alberti
 */
public class CommerceQualifierSearchContext implements Serializable {

	public Map<String, ?> getSourceAttributes() {
		return _sourceAttributes;
	}

	public Map<String, ?> getTargetAttributes() {
		return _targetAttributes;
	}

	public static class Builder {

		public CommerceQualifierSearchContext build() {
			CommerceQualifierSearchContext commerceQualifierSearchContext =
				new CommerceQualifierSearchContext();

			commerceQualifierSearchContext._sourceAttributes =
				_sourceAttributes;
			commerceQualifierSearchContext._targetAttributes =
				_targetAttributes;

			return commerceQualifierSearchContext;
		}

		public Builder setSourceAttribute(String key, Boolean value) {
			_sourceAttributes.put(key, value);

			return this;
		}

		public Builder setSourceAttribute(String key, Number value) {
			_sourceAttributes.put(key, value);

			return this;
		}

		public Builder setSourceAttribute(String key, Number[] value) {
			_sourceAttributes.put(key, value);

			return this;
		}

		public Builder setSourceAttribute(String key, String value) {
			_sourceAttributes.put(key, value);

			return this;
		}

		public Builder setSourceAttribute(String key, String[] value) {
			_sourceAttributes.put(key, value);

			return this;
		}

		public Builder setTargetAttribute(String key, Long value) {
			_targetAttributes.put(key, value);

			return this;
		}

		public Builder setTargetAttribute(String key, long[] value) {
			_targetAttributes.put(key, ArrayUtil.toLongArray(value));

			return this;
		}

		public Builder setTargetAttribute(String key, Long[] value) {
			_targetAttributes.put(key, value);

			return this;
		}

		private final Map<String, Serializable> _sourceAttributes =
			new HashMap<>();
		private final Map<String, Serializable> _targetAttributes =
			new HashMap<>();

	}

	private CommerceQualifierSearchContext() {
	}

	private Map<String, ?> _sourceAttributes;
	private Map<String, ?> _targetAttributes;

}