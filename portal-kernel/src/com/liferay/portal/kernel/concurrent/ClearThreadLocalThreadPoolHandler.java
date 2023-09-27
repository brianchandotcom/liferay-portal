/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Shuyang Zhou
 */
public class ClearThreadLocalThreadPoolHandler
	extends ThreadPoolHandlerAdapter {

	@Override
	public void afterExecute(Runnable runnable, Throwable throwable) {
		CentralizedThreadLocal.clearShortLivedThreadLocals();
	}

}