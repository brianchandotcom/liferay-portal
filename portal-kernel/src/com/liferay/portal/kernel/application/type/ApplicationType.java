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

package com.liferay.portal.kernel.application.type;

import java.util.Objects;

/**
 * @author Juergen Kappler
 */
public enum ApplicationType {

	FULL_PAGE_APPLICATION("full-page-application"), WIDGET("widget");

	public static ApplicationType parse(String value) {
		if (Objects.equals(FULL_PAGE_APPLICATION.getValue(), value)) {
			return FULL_PAGE_APPLICATION;
		}
		else if (Objects.equals(WIDGET.getValue(), value)) {
			return WIDGET;
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private ApplicationType(String value) {
		_value = value;
	}

	private final String _value;

}