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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class FieldConstants {

	public static final String BOOLEAN = "boolean";

	public static final String DATA_TYPE = "dataType";

	public static final String DATE = "date";

	public static final String DOCUMENT_LIBRARY = "document-library";

	public static final String DOUBLE = "double";

	public static final String EDITABLE = "editable";

	public static final String FILE_UPLOAD = "file-upload";

	public static final String FLOAT = "float";

	public static final String HTML = "html";

	public static final String INTEGER = "integer";

	public static final String LABEL = "label";

	public static final String LONG = "long";

	public static final String NAME = "name";

	public static final String NUMBER = "number";

	public static final String PREDIFINED_VALUE = "predefinedValue";

	public static final String REQUIRED = "required";

	public static final String SHORT = "short";

	public static final String SHOW = "showLabel";

	public static final String SORTABLE = "sortable";

	public static final String STRING = "string";

	public static final String TYPE = "type";

	public static final String VALUE = "value";

	public static final Serializable getSerializable(
		String type, List<Serializable> values) {

		if (type.equals(FieldConstants.BOOLEAN)) {
			return ArrayUtil.toArray(
				values.toArray(new Boolean[values.size()]));
		}
		else if (type.equals(FieldConstants.DATE)) {
			return values.toArray(new Date[values.size()]);
		}
		else if (type.equals(FieldConstants.DOUBLE)) {
			return ArrayUtil.toArray(values.toArray(new Double[values.size()]));
		}
		else if (type.equals(FieldConstants.FLOAT)) {
			return ArrayUtil.toArray(values.toArray(new Float[values.size()]));
		}
		else if (type.equals(FieldConstants.INTEGER)) {
			return ArrayUtil.toArray(
				values.toArray(new Integer[values.size()]));
		}
		else if (type.equals(FieldConstants.LONG)) {
			return ArrayUtil.toArray(values.toArray(new Long[values.size()]));
		}
		else if (type.equals(FieldConstants.NUMBER)) {
			return values.toArray(new Number[values.size()]);
		}
		else if (type.equals(FieldConstants.SHORT)) {
			return ArrayUtil.toArray(values.toArray(new Short[values.size()]));
		}
		else {
			return values.toArray(new String[values.size()]);
		}
	}

	public static final Serializable getSerializable(
		String type, String value) {

		if (type.equals(BOOLEAN)) {
			return GetterUtil.getBoolean(value);
		}
		else if (type.equals(DATE) && Validator.isNotNull(value)) {
			return new Date(GetterUtil.getLong(value));
		}
		else if (type.equals(DOUBLE)) {
			return GetterUtil.getDouble(value);
		}
		else if (type.equals(FLOAT)) {
			return GetterUtil.getFloat(value);
		}
		else if (type.equals(INTEGER)) {
			return GetterUtil.getInteger(value);
		}
		else if (type.equals(LONG)) {
			return GetterUtil.getLong(value);
		}
		else if (type.equals(NUMBER)) {
			return GetterUtil.getNumber(value);
		}
		else if (type.equals(SHORT)) {
			return GetterUtil.getShort(value);
		}
		else {
			return value;
		}
	}

}