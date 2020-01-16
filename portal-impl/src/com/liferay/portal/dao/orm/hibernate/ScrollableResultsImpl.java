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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.ScrollableResults;

/**
 * @author Brian Wing Shun Chan
 */
public class ScrollableResultsImpl implements ScrollableResults {

	public ScrollableResultsImpl(
		org.hibernate.ScrollableResults scrollableResults) {

		_scrollableResults = scrollableResults;
	}

	@Override
	public boolean first() throws ORMException {
		try {
			return _scrollableResults.first();
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public Object[] get() throws ORMException {
		try {
			return _scrollableResults.get();
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public Object get(int i) throws ORMException {
		try {
			return _scrollableResults.get(i);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public boolean last() throws ORMException {
		try {
			return _scrollableResults.last();
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public boolean next() throws ORMException {
		try {
			return _scrollableResults.next();
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public boolean previous() throws ORMException {
		try {
			return _scrollableResults.previous();
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public boolean scroll(int i) throws ORMException {
		try {
			return _scrollableResults.scroll(i);
		}
		catch (Exception exception) {
			throw ExceptionTranslator.translate(exception);
		}
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(3);

		sb.append("{_scrollableResults=");
		sb.append(String.valueOf(_scrollableResults));
		sb.append("}");

		return sb.toString();
	}

	private final org.hibernate.ScrollableResults _scrollableResults;

}