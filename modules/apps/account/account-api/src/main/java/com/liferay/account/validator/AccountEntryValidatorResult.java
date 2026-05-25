/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.petra.string.StringPool;

import java.io.Serializable;

/**
 * @author Tancredi Covioli
 */
public final class AccountEntryValidatorResult implements Serializable {

	public static final String RESULT_BYPASSED = "bypassed";

	public static final String RESULT_FAILED = "failed";

	public static final String RESULT_PASSED = "passed";

	public static final String RESULT_WARNING = "warning";

	public static Builder builder(String key) {
		return new Builder(key);
	}

	public String getAdditionalProps() {
		return _additionalProps;
	}

	public String getButtonLabel() {
		return _buttonLabel;
	}

	public String getButtonLink() {
		return _buttonLink;
	}

	public String getKey() {
		return _key;
	}

	public String getMessage() {
		return _message;
	}

	public String getResult() {
		return _result;
	}

	public String getResultReason() {
		return _resultReason;
	}

	public boolean isValid() {
		return !RESULT_FAILED.equals(_result);
	}

	public static class Builder {

		public Builder additionalProps(String additionalProps) {
			_additionalProps = additionalProps;

			return this;
		}

		public AccountEntryValidatorResult build() {
			return new AccountEntryValidatorResult(
				_additionalProps, _key, _buttonLabel, _buttonLink, _message,
				_result, _resultReason);
		}

		public Builder button(String buttonLink) {
			_buttonLink = buttonLink;

			return this;
		}

		public Builder button(String buttonLabel, String buttonLink) {
			_buttonLabel = buttonLabel;
			_buttonLink = buttonLink;

			return this;
		}

		public Builder message(String message) {
			_message = message;

			return this;
		}

		public Builder resultBypassed(String resultReason) {
			_result = RESULT_BYPASSED;
			_resultReason = resultReason;

			return this;
		}

		public Builder resultFailed(String resultReason) {
			_result = RESULT_FAILED;
			_resultReason = resultReason;

			return this;
		}

		public Builder resultPassed(String resultReason) {
			_result = RESULT_PASSED;
			_resultReason = resultReason;

			return this;
		}

		public Builder resultWarning(String resultReason) {
			_result = RESULT_WARNING;
			_resultReason = resultReason;

			return this;
		}

		private Builder(String key) {
			_key = key;
		}

		private String _additionalProps = StringPool.BLANK;
		private String _buttonLabel = StringPool.BLANK;
		private String _buttonLink = StringPool.BLANK;
		private final String _key;
		private String _message = StringPool.BLANK;
		private String _result = RESULT_PASSED;
		private String _resultReason = StringPool.BLANK;

	}

	private AccountEntryValidatorResult(
		String additionalProps, String key, String labelKey, String link,
		String messageKey, String result, String resultReason) {

		_additionalProps = additionalProps;
		_key = key;
		_result = result;
		_resultReason = resultReason;

		_buttonLabel = labelKey;
		_buttonLink = link;
		_message = messageKey;
	}

	private final String _additionalProps;
	private final String _buttonLabel;
	private final String _buttonLink;
	private final String _key;
	private final String _message;
	private final String _result;
	private final String _resultReason;

}