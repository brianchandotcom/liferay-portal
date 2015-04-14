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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

/**
 * @author Brian Wing Shun Chan
 */
public class LuceneFields {

	public static Field getDate(String field) {
		return getDate(field, new Date());
	}

	public static Field getDate(String field, Date date) {
		if (date == null) {
			return getDate(field);
		}
		else {
			return new Field(
				field,
				DateTools.dateToString(date, DateTools.Resolution.SECOND),
				Field.Store.YES, Field.Index.NOT_ANALYZED);
		}
	}

	public static Field getKeyword(String field, double keyword) {
		return getKeyword(field, String.valueOf(keyword));
	}

	public static Field getKeyword(String field, long keyword) {
		return getKeyword(field, String.valueOf(keyword));
	}

	public static Field getKeyword(String field, Long keyword) {
		return getKeyword(field, keyword.longValue());
	}

	public static Field getKeyword(String field, String keyword) {
		//keyword = KeywordsUtil.escape(keyword);

		Field fieldObj = new Field(
			field, keyword, Field.Store.YES, Field.Index.NOT_ANALYZED);

		//fieldObj.setBoost(0);

		return fieldObj;
	}

	public static NumericField getNumber(
		String field, String number, Class<? extends Number> clazz) {

		NumericField numericField = new NumericField(
			field, Field.Store.YES, true);

		if (clazz.equals(Float.class)) {
			numericField.setFloatValue(GetterUtil.getFloat(number));
		}
		else if (clazz.equals(Integer.class)) {
			numericField.setIntValue(GetterUtil.getInteger(number));
		}
		else if (clazz.equals(Long.class)) {
			numericField.setLongValue(GetterUtil.getLong(number));
		}
		else {
			numericField.setDoubleValue(GetterUtil.getDouble(number));
		}

		return numericField;
	}

	public static Field getText(String field, String text) {
		return new Field(field, text, Field.Store.YES, Field.Index.ANALYZED);
	}

	public static Field getText(String field, StringBuilder sb) {
		return getText(field, sb.toString());
	}

	public static String getUID(String portletId, long field1) {
		return getUID(portletId, String.valueOf(field1));
	}

	public static String getUID(String portletId, long field1, String field2) {
		return getUID(portletId, String.valueOf(field1), field2);
	}

	public static String getUID(String portletId, Long field1) {
		return getUID(portletId, field1.longValue());
	}

	public static String getUID(String portletId, Long field1, String field2) {
		return getUID(portletId, field1.longValue(), field2);
	}

	public static String getUID(String portletId, String field1) {
		return getUID(portletId, field1, null);
	}

	public static String getUID(
		String portletId, String field1, String field2) {

		return getUID(portletId, field1, field2, null);
	}

	public static String getUID(
		String portletId, String field1, String field2, String field3) {

		String uid = portletId + _UID_PORTLET + field1;

		if (field2 != null) {
			uid += _UID_FIELD + field2;
		}

		if (field3 != null) {
			uid += _UID_FIELD + field3;
		}

		return uid;
	}

	private static final String _UID_FIELD = "_FIELD_";

	private static final String _UID_PORTLET = "_PORTLET_";

}