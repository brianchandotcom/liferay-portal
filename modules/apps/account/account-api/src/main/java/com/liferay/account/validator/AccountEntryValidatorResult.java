/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.account.constants.AccountEntryValidatorResultConstants;
import com.liferay.petra.string.StringPool;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author Tancredi Covioli
 */
public final class AccountEntryValidatorResult implements Serializable {

	public static Builder builder(String key) {
		return new Builder(key);
	}

	public String getActionLabel() {
		return _actionLabel;
	}

	public String getActionURL() {
		return _actionURL;
	}

	public Map<String, String> getAdditionalProps() {
		return _additionalProps;
	}

	public String getKey() {
		return _key;
	}

	public String getResultMessage() {
		return _resultMessage;
	}

	public String getResultStatus() {
		return _resultStatus;
	}

	public boolean isValid() {
		return !Objects.equals(
			AccountEntryValidatorResultConstants.FAILURE, _resultStatus);
	}

	public static class Builder {

		public Builder action(String actionURL) {
			_actionURL = actionURL;

			return this;
		}

		public Builder action(String actionLabel, String actionURL) {
			_actionLabel = actionLabel;
			_actionURL = actionURL;

			return this;
		}

		public Builder additionalProps(Map<String, String> additionalProps) {
			_additionalProps = additionalProps;

			return this;
		}

		public AccountEntryValidatorResult build() {
			return new AccountEntryValidatorResult(
				_actionLabel, _actionURL, _additionalProps, _key, _resultStatus,
				_resultMessage);
		}

		public Builder resultMessage(String resultMessage) {
			_resultMessage = resultMessage;

			return this;
		}

		public Builder resultStatus(String resultStatus) {
			_resultStatus = resultStatus;

			return this;
		}

		private Builder(String key) {
			_key = key;
		}

		private String _actionLabel = StringPool.BLANK;
		private String _actionURL = StringPool.BLANK;
		private Map<String, String> _additionalProps = Collections.emptyMap();
		private final String _key;
		private String _resultMessage = StringPool.BLANK;
		private String _resultStatus =
			AccountEntryValidatorResultConstants.SUCCESS;

	}

	private AccountEntryValidatorResult(
		String actionLabel, String actionURL,
		Map<String, String> additionalProps, String key, String resultStatus,
		String resultMessage) {

		_actionLabel = actionLabel;
		_actionURL = actionURL;
		_additionalProps = additionalProps;
		_key = key;
		_resultStatus = resultStatus;
		_resultMessage = resultMessage;
	}

	private final String _actionLabel;
	private final String _actionURL;
	private final Map<String, String> _additionalProps;
	private final String _key;
	private final String _resultMessage;
	private final String _resultStatus;

}