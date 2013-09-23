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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnmodifiableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class QueryUtil {

	public static final int ALL_POS = -1;

	public static Comparable<?>[] getPrevAndNext(
		Query query, int count, OrderByComparator obc,
		Comparable<?> comparable) {

		int pos = count;
		int boundary = 0;

		Comparable<?>[] array = new Comparable[3];

		DB db = DBFactoryUtil.getDB();

		if (!db.isSupportsScrollableResults()) {
			if (_log.isWarnEnabled()) {
				_log.warn("Database does not support scrollable results");
			}

			return array;
		}

		ScrollableResults sr = query.scroll();

		if (sr.first()) {
			while (true) {
				Object obj = sr.get(0);

				if (obj == null) {
					if (_log.isWarnEnabled()) {
						_log.warn("Object is null");
					}

					break;
				}

				Comparable<?> curComparable = (Comparable<?>)obj;

				int value = obc.compare(comparable, curComparable);

				if (_log.isDebugEnabled()) {
					_log.debug("Comparison result is " + value);
				}

				if (value == 0) {
					if (!comparable.equals(curComparable)) {
						break;
					}

					array[1] = curComparable;

					if (sr.previous()) {
						array[0] = (Comparable<?>)sr.get(0);
					}

					sr.next();

					if (sr.next()) {
						array[2] = (Comparable<?>)sr.get(0);
					}

					break;
				}

				if (pos == 1) {
					break;
				}

				pos = (int)Math.ceil(pos / 2.0);

				int scrollPos = pos;

				if (value < 0) {
					scrollPos = scrollPos * -1;
				}

				boundary += scrollPos;

				if (boundary < 0) {
					scrollPos = scrollPos + (boundary * -1) + 1;

					boundary = 0;
				}

				if (boundary > count) {
					scrollPos = scrollPos - (boundary - count);

					boundary = scrollPos;
				}

				if (_log.isDebugEnabled()) {
					_log.debug("Scroll " + scrollPos);
				}

				if (!sr.scroll(scrollPos)) {
					if (value < 0) {
						if (!sr.next()) {
							break;
						}
					}
					else {
						if (!sr.previous()) {
							break;
						}
					}
				}
			}
		}

		return array;
	}

	public static Iterator<?> iterate(
		Query query, Dialect dialect, int start, int end) {

		return iterate(query, dialect, start, end, true);
	}

	public static Iterator<?> iterate(
		Query query, Dialect dialect, int start, int end,
		boolean unmodifiable) {

		return list(query, dialect, start, end).iterator();
	}

	public static List<?> list(
		Query query, Dialect dialect, int start, int end) {

		return list(query, dialect, start, end, true);
	}

	public static List<?> list(
		Query query, Dialect dialect, int start, int end,
		boolean unmodifiable) {

		if ((start == ALL_POS) && (end == ALL_POS)) {
			return query.list(unmodifiable);
		}

		if (start < 0) {
			start = 0;
		}

		if (end < start) {
			end = start;
		}

		if (start == end) {
			if (unmodifiable) {
				return Collections.emptyList();
			}
			else {
				return new ArrayList<Object>();
			}
		}

		if (dialect.supportsLimit()) {
			query.setMaxResults(end - start);
			query.setFirstResult(start);

			return query.list(unmodifiable);
		}

		List<Object> list = new ArrayList<Object>();

		DB db = DBFactoryUtil.getDB();

		if (!db.isSupportsScrollableResults()) {
			if (_log.isWarnEnabled()) {
				_log.warn("Database does not support scrollable results");
			}

			return list;
		}

		ScrollableResults sr = query.scroll();

		if (sr.first() && sr.scroll(start)) {
			for (int i = start; i < end; i++) {
				Object[] array = sr.get();

				if (array.length == 1) {
					list.add(array[0]);
				}
				else {
					list.add(array);
				}

				if (!sr.next()) {
					break;
				}
			}
		}

		if (unmodifiable) {
			return new UnmodifiableList<Object>(list);
		}
		else {
			return list;
		}
	}

	public static List<?> randomList(
		Query query, Dialect dialect, int total, int num) {

		return randomList(query, dialect, total, num, true);
	}

	public static List<?> randomList(
		Query query, Dialect dialect, int total, int num,
		boolean unmodifiable) {

		if ((total == 0) || (num == 0)) {
			return new ArrayList<Object>();
		}

		if (num >= total) {
			return list(query, dialect, ALL_POS, ALL_POS, true);
		}

		int[] scrollIds = RandomUtil.nextInts(total, num);

		List<Object> list = new ArrayList<Object>();

		DB db = DBFactoryUtil.getDB();

		if (!db.isSupportsScrollableResults()) {
			if (_log.isWarnEnabled()) {
				_log.warn("Database does not support scrollable results");
			}

			return list;
		}

		ScrollableResults sr = query.scroll();

		for (int i = 0; i < scrollIds.length; i++) {
			if (sr.scroll(scrollIds[i])) {
				Object[] array = sr.get();

				if (array.length == 1) {
					list.add(array[0]);
				}
				else {
					list.add(array);
				}

				sr.first();
			}
		}

		if (unmodifiable) {
			return new UnmodifiableList<Object>(list);
		}
		else {
			return list;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(QueryUtil.class);

}