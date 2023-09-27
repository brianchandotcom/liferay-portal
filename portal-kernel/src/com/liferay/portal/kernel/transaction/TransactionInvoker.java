/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.transaction;

import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public interface TransactionInvoker {

	public <T> T invoke(
			TransactionConfig transactionConfig, Callable<T> callable)
		throws Throwable;

}