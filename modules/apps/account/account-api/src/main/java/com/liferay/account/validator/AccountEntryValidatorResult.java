/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.account.constants.AccountEntryValidatorConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;

import java.io.Serializable;

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

	public JSONObject getAdditionalProps() {
		return _jsonObject;
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
			AccountEntryValidatorConstants.RESULT_FAILURE, _resultStatus);
	}

	public static class Builder {

		public Builder actionLabel(String actionLabel) {
			_actionLabel = actionLabel;

			return this;
		}

		public Builder actionURL(String actionURL) {
			_actionURL = actionURL;

			return this;
		}

		public Builder additionalProps(JSONObject jsonObject) {
			_jsonObject = jsonObject;

			return this;
		}

		public AccountEntryValidatorResult build() {
			return new AccountEntryValidatorResult(
				_actionLabel, _actionURL, _jsonObject, _key, _resultStatus,
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
		private JSONObject _jsonObject;
		private final String _key;
		private String _resultMessage = StringPool.BLANK;
		private String _resultStatus =
			AccountEntryValidatorConstants.RESULT_SUCCESS;

	}

	private AccountEntryValidatorResult(
		String actionLabel, String actionURL, JSONObject jsonObject, String key,
		String resultStatus, String resultMessage) {

		_actionLabel = actionLabel;
		_actionURL = actionURL;
		_jsonObject = jsonObject;
		_key = key;
		_resultStatus = resultStatus;
		_resultMessage = resultMessage;
	}

	private final String _actionLabel;
	private final String _actionURL;
	private final JSONObject _jsonObject;
	private final String _key;
	private final String _resultMessage;
	private final String _resultStatus;

}