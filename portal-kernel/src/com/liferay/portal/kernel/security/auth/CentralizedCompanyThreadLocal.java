/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.ArrayList;
import java.util.List;
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

	public CentralizedCompanyThreadLocal(String name, Supplier<T> supplier) {
		super(name, supplier);

		_centralizedCompanyThreadLocals.add(this);
	}

	private static final List<CentralizedCompanyThreadLocal<?>>
		_centralizedCompanyThreadLocals = new ArrayList<>();

}