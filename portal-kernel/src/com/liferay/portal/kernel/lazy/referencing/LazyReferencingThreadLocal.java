/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.lazy.referencing;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Stefano Motta
 */
public class LazyReferencingThreadLocal {

	public static SafeCloseable enableIncompleteModelWithSelfCloseable() {
		return _incompleteModel.setWithSafeCloseable(true);
	}

	public static SafeCloseable enableWithSelfCloseable() {
		return _enabled.setWithSafeCloseable(true);
	}

	public static boolean isEnabled() {
		return _enabled.get();
	}

	public static boolean isIncompleteModel() {
		return _incompleteModel.get();
	}

	private static final CentralizedThreadLocal<Boolean> _enabled =
		new CentralizedThreadLocal<>(
			LazyReferencingThreadLocal.class + "._enabled",
			() -> Boolean.FALSE);
	private static final CentralizedThreadLocal<Boolean> _incompleteModel =
		new CentralizedThreadLocal<>(
			LazyReferencingThreadLocal.class + "._incompleteModel",
			() -> Boolean.FALSE);

}