/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.transaction;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Shuyang Zhou
 */
@ProviderType
public interface TransactionStatus {

	public boolean isCompleted();

	public boolean isNewTransaction();

	public boolean isRollbackOnly();

	public void suppressLifecycleListenerThrowable(Throwable throwable);

}