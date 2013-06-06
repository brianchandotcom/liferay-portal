/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Roberto Díaz
 */
public class DuplicateAssetQueryRuleException extends PortalException {

	public DuplicateAssetQueryRuleException(
		boolean andOperator, boolean contains, String name) {

		super();

		_andOperator = andOperator;
		_contains = contains;
		_name = name;
	}

	public boolean getAndOperator() {
		return _andOperator;
	}

	public boolean getContains() {
		return _contains;
	}

	public String getName() {
		return _name;
	}

	private boolean _andOperator;
	private boolean _contains;
	private String _name;

}