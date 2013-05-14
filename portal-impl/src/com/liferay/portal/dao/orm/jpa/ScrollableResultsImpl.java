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

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.ScrollableResults;

import java.util.List;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class ScrollableResultsImpl implements ScrollableResults {

	public ScrollableResultsImpl(List<?> results) {
		_results = results;
		_last = _results.size();
	}

	@Override
	public boolean first() throws ORMException {
		if (_results.isEmpty()) {
			return false;
		}

		_current = 1;

		return true;
	}

	@Override
	public Object[] get() throws ORMException {
		Object[] result = null;

		Object object = _results.get(_current - 1);

		if (object instanceof Object[]) {
			result = (Object[])object;
		}
		else {
			result = new Object[] {object};
		}

		return result;
	}

	@Override
	public Object get(int i) throws ORMException {
		Object result = null;

		Object object = _results.get(_current - 1);

		if (object instanceof Object[]) {
			result = ((Object[])object)[i];
		}
		else {
			result = object;
		}

		return result;
	}

	@Override
	public boolean last() throws ORMException {
		if (_results.isEmpty()) {
			return false;
		}

		_current = _last;

		return true;
	}

	@Override
	public boolean next() throws ORMException {
		if (_current == _last) {
			return false;
		}

		_current++;

		return true;
	}

	@Override
	public boolean previous() throws ORMException {
		if (_current == 1) {
			return false;
		}

		_current--;

		return true;
	}

	@Override
	public boolean scroll(int i) throws ORMException {
		if (((_current + i) < 1) || ((_current + i) > _last)) {
			return false;
		}

		_current += i;

		return true;
	}

	private int _current = 0;
	private int _last = 0;
	private List<?> _results;

}