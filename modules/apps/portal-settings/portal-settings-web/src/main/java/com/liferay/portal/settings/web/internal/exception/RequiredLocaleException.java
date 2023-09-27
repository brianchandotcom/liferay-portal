/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.settings.web.internal.exception;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;

import java.util.List;

/**
 * @author Samuel Trong Tran
 */
public class RequiredLocaleException extends PortalException {

	public RequiredLocaleException() {
	}

	public RequiredLocaleException(List<Group> groups) throws PortalException {
		this(
			_getRequiredLocaleMessageArguments(groups),
			_getRequiredLocaleMessageKey(groups));
	}

	public RequiredLocaleException(String messageKey) {
		_messageKey = messageKey;
	}

	public RequiredLocaleException(
		String[] messageArguments, String messageKey) {

		_messageArguments = messageArguments;
		_messageKey = messageKey;
	}

	public RequiredLocaleException(Throwable throwable) {
		super(throwable);
	}

	public String[] getMessageArguments() {
		return _messageArguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public void setMessageArguments(String[] messageArguments) {
		_messageArguments = messageArguments;
	}

	public void setMessageKey(String messageKey) {
		_messageKey = messageKey;
	}

	private static String[] _getRequiredLocaleMessageArguments(
			List<Group> groups)
		throws PortalException {

		if (groups.isEmpty()) {
			return new String[0];
		}
		else if (groups.size() == 1) {
			Group group = groups.get(0);

			return new String[] {group.getDescriptiveName()};
		}
		else if (groups.size() == 2) {
			Group group1 = groups.get(0);
			Group group2 = groups.get(1);

			return new String[] {
				group1.getDescriptiveName(), group2.getDescriptiveName()
			};
		}

		Group group1 = groups.get(0);
		Group group2 = groups.get(1);

		return new String[] {
			group1.getDescriptiveName(), group2.getDescriptiveName(),
			String.valueOf(groups.size() - 2)
		};
	}

	private static String _getRequiredLocaleMessageKey(List<Group> groups) {
		if (groups.isEmpty()) {
			return StringPool.BLANK;
		}
		else if (groups.size() == 1) {
			return "language-cannot-be-removed-because-it-is-in-use-by-the-" +
				"following-site-x";
		}
		else if (groups.size() == 2) {
			return "one-or-more-languages-cannot-be-removed-because-they-are-" +
				"in-use-by-the-following-sites-x-and-x";
		}

		return "one-or-more-languages-cannot-be-removed-because-they-are-in-" +
			"use-by-the-following-sites-x,-x-and-x-more";
	}

	private String[] _messageArguments;
	private String _messageKey;

}