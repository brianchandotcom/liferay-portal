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

package com.liferay.vulcan.liferay.portal.internal.filter;

import com.liferay.vulcan.liferay.portal.filter.ClassNameClassPKFilter;

/**
 * @author Alejandro Hernández
 */
public class ClassNameClassPKFilterImpl implements ClassNameClassPKFilter {

	public ClassNameClassPKFilterImpl(
		String className, Long classPK, String type) {

		_className = className;
		_classPK = classPK;
		_type = type;
	}

	public String getClassName() {
		return _className;
	}

	public Long getClassPK() {
		return _classPK;
	}

	@Override
	public Long getId() {
		return _classPK;
	}

	@Override
	public String getType() {
		return _type;
	}

	private final String _className;
	private final Long _classPK;
	private final String _type;

}