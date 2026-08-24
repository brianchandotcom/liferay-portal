/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Shuyang Zhou
 */
public class WorkflowWaitForCompletionThreadLocal {

	public static boolean isWaitForCompletion() {
		return _waitForCompletion.get();
	}

	public static SafeCloseable setWaitForCompletionWithSafeCloseable(
		boolean waitForCompletion) {

		return _waitForCompletion.setWithSafeCloseable(waitForCompletion);
	}

	private static final CentralizedThreadLocal<Boolean> _waitForCompletion =
		new CentralizedThreadLocal<>(
			WorkflowWaitForCompletionThreadLocal.class + "._waitForCompletion",
			() -> Boolean.FALSE);

}