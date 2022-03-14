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

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectEntryValuesException extends PortalException {

	public static class Exceeds280Characters
		extends ObjectEntryValuesException {

		public Exceeds280Characters() {
			super("The maximum length is 280 characters for text fields");
		}

	}

	public static class ExceedsIntegerSize extends ObjectEntryValuesException {

		public ExceedsIntegerSize() {
			super("Object entry value exceeds integer field allowed size");
		}

	}

	public static class ExceedsLongMaxSize extends ObjectEntryValuesException {

		public ExceedsLongMaxSize() {
			super("Object entry value exceeds maximum long field allowed size");
		}

	}

	public static class ExceedsLongMinSize extends ObjectEntryValuesException {

		public ExceedsLongMinSize() {
			super("Object entry value exceeds minimum long field allowed size");
		}

	}

	public static class ExceedsLongSize extends ObjectEntryValuesException {

		public ExceedsLongSize() {
			super("Object entry value exceeds long field allowed size");
		}

	}

	public static class ObjectFieldNotMapped
		extends ObjectEntryValuesException {

		public ObjectFieldNotMapped(String objectFieldName) {
			super(
				String.format(
					"Object field name \"%s\" is not mapped to a valid list " +
						"type entry",
					objectFieldName));
		}

	}

	public static class OneToOneConstraintViolation
		extends ObjectEntryValuesException {

		public OneToOneConstraintViolation(
			String columnName, long columnValue, String tableName) {

			super(
				String.format(
					"One to one constraint violation for %s.%s with value %s",
					tableName, columnName, columnValue));
		}

	}

	public static class RequiredValue extends ObjectEntryValuesException {

		public RequiredValue(String objectFieldName) {
			super(
				String.format(
					"No value was provided for required object field \"%s\"",
					objectFieldName));
		}

	}

	private ObjectEntryValuesException(String message) {
		super(message);
	}

}