/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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