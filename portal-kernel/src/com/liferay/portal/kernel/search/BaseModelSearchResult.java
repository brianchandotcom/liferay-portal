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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class BaseModelSearchResult<T extends BaseModel<T>>
	implements Serializable {

	public BaseModelSearchResult(List<T> baseModels, int length) {
		if (baseModels == null) {
			_baseModels = Collections.emptyList();
		}
		else {
			_baseModels = baseModels;
		}

		_length = length;
	}

	public List<T> getBaseModels() {
		return _baseModels;
	}

	public int getLength() {
		return _length;
	}

	@Override
	public String toString() {
		if (_baseModels.isEmpty()) {
			return "{baseModels={}, length=".concat(
				String.valueOf(_length)).concat(StringPool.CLOSE_BRACKET);
		}

		StringBundler sb = new StringBundler(2 * _baseModels.size() + 3);

		sb.append("{baseModels={");

		for (T baseModel : _baseModels) {
			sb.append(baseModel);
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setStringAt(StringPool.CLOSE_BRACKET, sb.index() - 1);

		sb.append(", length=");
		sb.append(_length);
		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
	}

	private final List<T> _baseModels;
	private final int _length;

}