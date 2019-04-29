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

import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

/**
 * @author Zoltan Csaszi
 */
@ProviderType
public class CTCollectionNameException extends CTException {

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public CTCollectionNameException() {
		super(CompanyThreadLocal.getCompanyId());
	}

	public CTCollectionNameException(long companyId) {
		super(companyId);
	}

	public CTCollectionNameException(long companyId, String msg) {
		super(companyId, msg);
	}

	public CTCollectionNameException(
		long companyId, String msg, Throwable cause) {

		super(companyId, msg, cause);
	}

	public CTCollectionNameException(long companyId, Throwable cause) {
		super(companyId, cause);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public CTCollectionNameException(String msg) {
		super(CompanyThreadLocal.getCompanyId(), msg);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public CTCollectionNameException(String msg, Throwable cause) {
		super(CompanyThreadLocal.getCompanyId(), msg, cause);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public CTCollectionNameException(Throwable cause) {
		super(CompanyThreadLocal.getCompanyId(), cause);
	}

}