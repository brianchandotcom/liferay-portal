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
 * @author Paulo Albuquerque
 * @author Selton Guedes
 */
public class ObjectLayoutBoxTypeException extends PortalException {

	public ObjectLayoutBoxTypeException() {
	}

	public ObjectLayoutBoxTypeException(String msg) {
		super(msg);
	}

	public ObjectLayoutBoxTypeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectLayoutBoxTypeException(Throwable throwable) {
		super(throwable);
	}

	public static class Categorization extends ObjectLayoutBoxTypeException {

		public Categorization(String msg) {
			super(msg);
		}

	}

	public static class Comments extends ObjectLayoutBoxTypeException {

		public Comments(String msg) {
			super(msg);
		}

	}

}