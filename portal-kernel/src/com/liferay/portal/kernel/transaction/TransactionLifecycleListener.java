/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.transaction;

/**
 * @author Shuyang Zhou
 */
public interface TransactionLifecycleListener {

	public void committed(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus);

	public void created(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus);

	public void rollbacked(
		TransactionAttribute transactionAttribute,
		TransactionStatus transactionStatus, Throwable throwable);

}