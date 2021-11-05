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

	public ObjectEntryValuesException() {
	}

	public ObjectEntryValuesException(String msg) {
		super(msg);
	}

	public ObjectEntryValuesException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectEntryValuesException(Throwable throwable) {
		super(throwable);
	}

	public static class IntegerSizeExceeded extends ObjectEntryValuesException {

		public IntegerSizeExceeded() {
			super("Object entry value exceeds Integer field allowed size.");
		}

	}

	public static class LongMaxSizeExceeded extends ObjectEntryValuesException {

		public LongMaxSizeExceeded() {
			super(
				"Object entry value exceeds maximum Long field allowed size.");
		}

	}

	public static class LongMinSizeExceeded extends ObjectEntryValuesException {

		public LongMinSizeExceeded() {
			super(
				"Object entry value exceeds minimum Long field allowed size.");
		}

	}

	public static class LongSizeExceeded extends ObjectEntryValuesException {

		public LongSizeExceeded() {
			super("Object entry value exceeds Long field allowed size.");
		}

	}

	public static class MustBeLessThan280Characters
		extends ObjectEntryValuesException {

		public MustBeLessThan280Characters() {
			super("The maximum length is 280 characters for text fields.");
		}

	}

}