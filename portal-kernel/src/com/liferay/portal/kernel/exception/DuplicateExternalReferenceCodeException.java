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

package com.liferay.portal.kernel.exception;

/**
 * The base class for all exceptions caused by duplicate external reference
 * in different entity types. This base class is necessary in order for portlets
 * to properly consistently identify sub-classes as session errors.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.kernel.portlet.LiferayPortlet#isSessionErrorException(Throwable)
 */
public class DuplicateExternalReferenceCodeException extends SystemException {

	public DuplicateExternalReferenceCodeException() {
	}

	public DuplicateExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}