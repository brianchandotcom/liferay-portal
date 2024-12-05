/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Alberto Chaparro
 */
public class CentralizedCompanyThreadLocal<T>
	extends CentralizedThreadLocal<T> {

	public static List<CentralizedCompanyThreadLocal<?>>
		getCentralizedCompanyThreadLocals() {

		return _centralizedCompanyThreadLocals;
	}

	public CentralizedCompanyThreadLocal(boolean shortLived) {
		this(null, () -> null, shortLived);
	}

	public CentralizedCompanyThreadLocal(String name) {
		this(name, () -> null, true);
	}

	public CentralizedCompanyThreadLocal(String name, Supplier<T> supplier) {
		this(name, supplier, true);
	}

	public CentralizedCompanyThreadLocal(
		String name, Supplier<T> supplier, boolean shortLived) {

		this(name, supplier, null, shortLived);
	}

	public CentralizedCompanyThreadLocal(
		String name, Supplier<T> supplier, Function<T, T> copyFunction,
		boolean shortLived) {

		super(name, supplier, copyFunction, shortLived);

		_centralizedCompanyThreadLocals.add(this);
	}

	@Override
	public void remove() {
		T object = get();

		if (object instanceof Collection) {
			Collection<?> collection = (Collection<?>)object;

			collection.clear();
		}
		else {
			super.remove();
		}
	}

	@Override
	public SafeCloseable setWithSafeCloseable(T value) {
		if (value == null) {
			value = initialValue();
		}

		return super.setWithSafeCloseable(value);
	}

	private static final List<CentralizedCompanyThreadLocal<?>>
		_centralizedCompanyThreadLocals = new ArrayList<>();

}