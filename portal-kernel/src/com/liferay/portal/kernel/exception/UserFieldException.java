/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan McCann
 */
public class UserFieldException extends PortalException {

	public UserFieldException() {
	}

	public UserFieldException(String msg) {
		super(msg);
	}

	public UserFieldException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public UserFieldException(Throwable throwable) {
		super(throwable);
	}

	public void addField(String field) {
		_fields.add(field);
	}

	public List<String> getFields() {
		return _fields;
	}

	public boolean hasFields() {
		return !_fields.isEmpty();
	}

	private final List<String> _fields = new ArrayList<>();

}