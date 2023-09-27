/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Roberto Díaz
 */
public class DuplicateQueryRuleException extends PortalException {

	public DuplicateQueryRuleException(
		boolean contains, boolean andOperator, String name) {

		_contains = contains;
		_andOperator = andOperator;
		_name = name;
	}

	public String getName() {
		return _name;
	}

	public boolean isAndOperator() {
		return _andOperator;
	}

	public boolean isContains() {
		return _contains;
	}

	private final boolean _andOperator;
	private final boolean _contains;
	private final String _name;

}