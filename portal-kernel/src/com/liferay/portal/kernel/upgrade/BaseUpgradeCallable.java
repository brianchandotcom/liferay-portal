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

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.concurrent.Callable;

/**
 * @author Alberto Chaparro
 */
public abstract class BaseUpgradeCallable<T> implements Callable<T> {

	public BaseUpgradeCallable() {
		_companyId = CompanyThreadLocal.getCompanyId();
	}

	@Override
	public T call() throws Exception {
		try (SafeCloseable safeCloseable = CompanyThreadLocal.lock(
				_companyId)) {

			return doCall();
		}
	}

	protected abstract T doCall() throws Exception;

	private final Long _companyId;

}