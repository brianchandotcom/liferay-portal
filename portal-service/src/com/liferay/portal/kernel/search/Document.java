/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.text.ParseException;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public interface Document extends Serializable {

	public void add(Field field);

	public void addDate(String name, Date value);

	public void addFile(String name, byte[] bytes, String fileExt)
		throws IOException;

	public void addFile(String name, File file, String fileExt)
		throws IOException;

	public void addFile(String name, InputStream is, String fileExt)
		throws IOException;

	public void addKeyword(String name, boolean value);

	public void addKeyword(String name, Boolean value);

	public void addKeyword(String name, boolean[] values);

	public void addKeyword(String name, Boolean[] values);

	public void addKeyword(String name, double value);

	public void addKeyword(String name, Double value);

	public void addKeyword(String name, double[] values);

	public void addKeyword(String name, Double[] values);

	public void addKeyword(String name, float value);

	public void addKeyword(String name, Float value);

	public void addKeyword(String name, float[] values);

	public void addKeyword(String name, Float[] values);

	public void addKeyword(String name, int value);

	public void addKeyword(String name, int[] values);

	public void addKeyword(String name, Integer value);

	public void addKeyword(String name, Integer[] values);

	public void addKeyword(String name, long value);

	public void addKeyword(String name, Long value);

	public void addKeyword(String name, long[] values);

	public void addKeyword(String name, Long[] values);

	public void addKeyword(String name, short value);

	public void addKeyword(String name, Short value);

	public void addKeyword(String name, short[] values);

	public void addKeyword(String name, Short[] values);

	public void addKeyword(String name, String value);

	public void addKeyword(String name, String value, boolean lowerCase);

	public void addKeyword(String name, String[] values);

	public void addLocalizedKeyword(String name, Map<Locale, String> values);

	public void addLocalizedText(String name, Map<Locale, String> values);

	/**
	 * @deprecated
	 */
	public void addModifiedDate();

	/**
	 * @deprecated
	 */
	public void addModifiedDate(Date modifiedDate);

	public void addNumber(String name, double value);

	public void addNumber(String name, Double value);

	public void addNumber(String name, double[] values);

	public void addNumber(String name, Double[] values);

	public void addNumber(String name, float value);

	public void addNumber(String name, Float value);

	public void addNumber(String name, float[] values);

	public void addNumber(String name, Float[] values);

	public void addNumber(String name, int value);

	public void addNumber(String name, int[] values);

	public void addNumber(String name, Integer value);

	public void addNumber(String name, Integer[] values);

	public void addNumber(String name, long value);

	public void addNumber(String name, Long value);

	public void addNumber(String name, long[] values);

	public void addNumber(String name, Long[] values);

	public void addNumber(String name, String value);

	public void addNumber(String name, String[] values);

	public void addText(String name, String value);

	public void addText(String name, String[] values);

	public void addUID(String portletId, long field1);

	public void addUID(String portletId, long field1, String field2);

	public void addUID(String portletId, Long field1);

	public void addUID(String portletId, Long field1, String field2);

	public void addUID(String portletId, String field1);

	public void addUID(String portletId, String field1, String field2);

	public void addUID(
		String portletId, String field1, String field2, String field3);

	public void addUID(
		String portletId, String field1, String field2, String field3,
		String field4);

	public String get(Locale locale, String name);

	public String get(Locale locale, String name, String defaultName);

	public String get(String name);

	public String get(String name, String defaultName);

	public Date getDate(String name) throws ParseException;

	public Field getField(String name);

	public Map<String, Field> getFields();

	public String getPortletId();

	public String getUID();

	public String[] getValues(String name);

	public void remove(String name);

}