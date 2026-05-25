/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * @author Tancredi Covioli
 */
public class AccountEntryValidatorResult implements Serializable {

	public AccountEntryValidatorResult() {
		this(true, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK);
	}

	public AccountEntryValidatorResult(boolean valid, String messageKey) {
		this(valid, StringPool.BLANK, StringPool.BLANK, messageKey);
	}

	public AccountEntryValidatorResult(
		boolean valid, String link, String messageKey) {

		this(valid, link, StringPool.BLANK, messageKey);
	}

	public AccountEntryValidatorResult(
		boolean valid, String link, String labelKey, String messageKey) {

		_valid = valid;
		_link = link;
		_labelKey = labelKey;
		_messageKey = messageKey;
	}

	public String getLabelKey() {
		return _labelKey;
	}

	public String getLink() {
		return _link;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public boolean hasMessageResult() {
		return Validator.isNotNull(getMessageKey());
	}

	public boolean isValid() {
		return _valid;
	}

	private final String _labelKey;
	private final String _link;
	private final String _messageKey;
	private final boolean _valid;

}