/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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