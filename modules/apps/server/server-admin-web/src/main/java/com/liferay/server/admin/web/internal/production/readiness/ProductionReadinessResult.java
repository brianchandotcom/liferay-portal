/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.production.readiness;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

/**
 * @author Lily Chi
 */
public class ProductionReadinessResult {

	public static Builder builder(String category, String key) {
		return new Builder(category, key);
	}

	public String getCategory() {
		return _category;
	}

	public String getCurrentValue() {
		return _currentValue;
	}

	public String getKey() {
		return _key;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public Object[] getMessageParameters() {
		return _messageParameters;
	}

	public String getRecommendedValue() {
		return _recommendedValue;
	}

	public boolean isPass() {
		return _pass;
	}

	public static class Builder {

		public Builder currentValue(String currentValue) {
			_currentValue = currentValue;

			return this;
		}

		public ProductionReadinessResult fail() {
			return _build(false);
		}

		public Builder messageKeySuffix(String messageKeySuffix) {
			_messageKeySuffix = messageKeySuffix;

			return this;
		}

		public Builder messageParameters(Object... messageParameters) {
			_messageParameters = messageParameters;

			return this;
		}

		public ProductionReadinessResult pass() {
			return _build(true);
		}

		public Builder recommendedValue(String recommendedValue) {
			_recommendedValue = recommendedValue;

			return this;
		}

		private Builder(String category, String key) {
			_category = category;
			_key = key;
		}

		private ProductionReadinessResult _build(boolean pass) {
			StringBundler sb = new StringBundler(6);

			sb.append("production-readiness-rule-");
			sb.append(_key);

			if (_messageKeySuffix != null) {
				sb.append(CharPool.DASH);
				sb.append(_messageKeySuffix);
			}

			sb.append(CharPool.DASH);
			sb.append(pass ? "pass" : "fail");

			return new ProductionReadinessResult(
				_category, _currentValue, _key, sb.toString(),
				_messageParameters, pass, _recommendedValue);
		}

		private final String _category;
		private String _currentValue;
		private final String _key;
		private String _messageKeySuffix;
		private Object[] _messageParameters = new Object[0];
		private String _recommendedValue;

	}

	private ProductionReadinessResult(
		String category, String currentValue, String key, String messageKey,
		Object[] messageParameters, boolean pass, String recommendedValue) {

		_category = category;
		_currentValue = currentValue;
		_key = key;
		_messageKey = messageKey;
		_messageParameters = messageParameters;
		_pass = pass;
		_recommendedValue = recommendedValue;
	}

	private final String _category;
	private final String _currentValue;
	private final String _key;
	private final String _messageKey;
	private final Object[] _messageParameters;
	private final boolean _pass;
	private final String _recommendedValue;

}