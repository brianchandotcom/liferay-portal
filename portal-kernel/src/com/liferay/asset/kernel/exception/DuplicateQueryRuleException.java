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