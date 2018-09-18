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

	public RequiredLocaleException(Throwable cause) {
		super(cause);
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
		else {
			Group group1 = groups.get(0);
			Group group2 = groups.get(1);

			return new String[] {
				group1.getDescriptiveName(), group2.getDescriptiveName(),
				String.valueOf(groups.size() - 2)
			};
		}
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