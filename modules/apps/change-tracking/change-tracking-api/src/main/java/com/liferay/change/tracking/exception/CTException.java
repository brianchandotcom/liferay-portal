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

package com.liferay.change.tracking.exception;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Gergely Mathe
 * @deprecated As of Mueller (7.2.x)
 */
@Deprecated
@ProviderType
public class CTException extends com.liferay.change.tracking.CTException {

	public CTException(long companyId) {
		super(companyId);
	}

	public CTException(long companyId, String msg) {
		super(companyId, msg);
	}

	public CTException(long companyId, String msg, Throwable cause) {
		super(companyId, msg, cause);
	}

	public CTException(long companyId, Throwable cause) {
		super(companyId, cause);
	}

	public long getCompanyId() {
		return super.getCompanyId();
	}

}