/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Brian I. Kim
 * @author Crescenzo Rega
 */
public class CommerceOrderThreadLocal {

	public static boolean isDeleteInProcess() {
		return _deleteInProcess.get();
	}

	public static boolean isSkipValidateAccountOrdersLimit() {
		return _skipValidateAccountOrdersLimit.get();
	}

	public static void setDeleteInProcess(boolean deleteInProcess) {
		_deleteInProcess.set(deleteInProcess);
	}

	public static void setSkipValidateAccountOrdersLimit(
		boolean skipValidateAccountOrdersLimit) {

		_skipValidateAccountOrdersLimit.set(skipValidateAccountOrdersLimit);
	}

	private static final ThreadLocal<Boolean> _deleteInProcess =
		new CentralizedThreadLocal<>(
			CommerceOrderThreadLocal.class + "._deleteInProcess",
			() -> Boolean.FALSE);
	private static final ThreadLocal<Boolean> _skipValidateAccountOrdersLimit =
		new CentralizedThreadLocal<>(
			CommerceReturnThreadLocal.class +
				"._skipValidateAccountOrdersLimit",
			() -> Boolean.FALSE);

}