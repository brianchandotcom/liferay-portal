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

package com.liferay.portal.kernel.search;

import com.liferay.portal.model.BaseModel;

import java.io.Serializable;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class BaseModelSearchResult<T extends BaseModel<T>>
	implements Serializable {

	public BaseModelSearchResult(List<T> baseModels, int length) {
		_baseModels = baseModels;
		_length = length;
	}

	public List<T> getBaseModels() {
		return _baseModels;
	}

	public int getLength() {
		return _length;
	}

	private List<T> _baseModels;
	private int _length;

}