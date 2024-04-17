/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.thread.local;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Raymond Augé
 */
public class AgentPortalK8sThreadLocal {

	public static SafeCloseable executeOnCurrentNodeWithSafeCloseable() {
		_executeOnCurrentNode.set(true);

		return () -> _executeOnCurrentNode.set(false);
	}

	public static boolean isExecuteOnCurrentNode() {
		return _executeOnCurrentNode.get();
	}

	private static final ThreadLocal<Boolean> _executeOnCurrentNode =
		new CentralizedThreadLocal<>(
			AgentPortalK8sThreadLocal.class + "._executeOnCurrentNode",
			() -> Boolean.FALSE);

}