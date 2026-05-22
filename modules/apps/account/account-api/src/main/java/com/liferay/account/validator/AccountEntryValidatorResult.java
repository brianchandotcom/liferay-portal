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

	public AccountEntryValidatorResult(boolean valid) {
		this(valid, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK);
	}

	public AccountEntryValidatorResult(boolean valid, String localizedMessage) {
		this(valid, StringPool.BLANK, StringPool.BLANK, localizedMessage);
	}

	public AccountEntryValidatorResult(
		boolean valid, String link, String localizedMessage) {

		this(valid, link, StringPool.BLANK, localizedMessage);
	}

	public AccountEntryValidatorResult(
		boolean valid, String link, String localizedLabel,
		String localizedMessage) {

		_valid = valid;
		_link = link;
		_localizedLabel = localizedLabel;
		_localizedMessage = localizedMessage;
	}

	public String getLink() {
		return _link;
	}

	public String getLocalizedLabel() {
		return _localizedLabel;
	}

	public String getLocalizedMessage() {
		return _localizedMessage;
	}

	public boolean hasMessageResult() {
		return Validator.isNotNull(getLocalizedMessage());
	}

	public boolean isValid() {
		return _valid;
	}

	private final String _link;
	private final String _localizedLabel;
	private final String _localizedMessage;
	private final boolean _valid;

}