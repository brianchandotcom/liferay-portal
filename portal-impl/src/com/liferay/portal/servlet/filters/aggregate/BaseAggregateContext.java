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

package com.liferay.portal.servlet.filters.aggregate;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedList;

/**
 * @author Raymond Augé
 * @author Eduardo Lundgren
 */
public abstract class BaseAggregateContext implements AggregateContext {

	@Override
	public String getFullPath(String path) {
		String listPath = _generatePathFromList();

		return listPath.concat(path);
	}

	@Override
	public String getResourcePath(String path) {
		return getFullPath(path);
	}

	@Override
	public String popPath() {
		if (_list.isEmpty()) {
			return null;
		}

		return _list.pop();
	}

	@Override
	public void pushPath(String path) {
		if (Validator.isNotNull(path)) {
			_list.push(path);
		}
	}

	@Override
	public String shiftPath() {
		if (_list.isEmpty()) {
			return null;
		}

		return _list.removeLast();
	}

	@Override
	public void unshiftPath(String path) {
		if (Validator.isNotNull(path)) {
			_list.addLast(path);
		}
	}

	private String _generatePathFromList() {
		StringBundler sb = new StringBundler(_list.size());

		for (int i = _list.size() - 1; i >= 0; i--) {
			String path = _list.get(i);

			sb.append(path);

			if (!path.endsWith(StringPool.SLASH)) {
				sb.append(StringPool.SLASH);
			}
		}

		return StringUtil.replace(
			sb.toString(), StringPool.DOUBLE_SLASH, StringPool.SLASH);
	}

	private LinkedList<String> _list = new LinkedList<String>();

}