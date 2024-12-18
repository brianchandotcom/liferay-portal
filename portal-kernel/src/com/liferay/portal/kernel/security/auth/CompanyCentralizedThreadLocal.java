/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.lang.SafeCloseable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Alberto Chaparro
 */
public class CompanyCentralizedThreadLocal<T>
	extends CentralizedThreadLocal<T> {

	public static List<CompanyCentralizedThreadLocal<?>>
		getCompanyCentralizedThreadLocals() {

		return _companyCentralizedThreadLocals;
	}

	public CompanyCentralizedThreadLocal(String name) {
		this(name, () -> null, true);
	}

	public CompanyCentralizedThreadLocal(String name, Supplier<T> supplier) {
		this(name, supplier, true);
	}

	public CompanyCentralizedThreadLocal(
		String name, Supplier<T> supplier, boolean shortLived) {

		this(name, supplier, null, shortLived);
	}

	public CompanyCentralizedThreadLocal(
		String name, Supplier<T> supplier, Function<T, T> copyFunction,
		boolean shortLived) {

		super(name, supplier, copyFunction, shortLived);

		_companyCentralizedThreadLocals.add(this);
	}

	@Override
	public SafeClosable setWithSafeClosable(T value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SafeCloseable setWithSafeCloseable(T value) {
		if (value == null) {
			value = initialValue();
		}

		return super.setWithSafeCloseable(value);
	}

	private static final List<CompanyCentralizedThreadLocal<?>>
		_companyCentralizedThreadLocals = new ArrayList<>();

}