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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSON;

/**
 * @author Igor Spasic
 */
@JSON
public class One {

	public String getFone() {
		return _fone;
	}

	@JSON
	public int getFtwo() {
		return _ftwo;
	}

	public long getNot() {
		return _not;
	}

	private final String _fone = "string";
	private final int _ftwo = 173;

	@JSON(include = false)
	private final long _not = -1;

}