/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.system.util;

import com.liferay.batch.engine.util.BatchEngineThreadLocal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Set;

/**
 * @author Gabriel Albuquerque
 */
public class SystemObjectDefinitionManagementChecker {

	public static boolean isInvokerBundleAllowed() {
		String invokerBundleNamespace =
			BatchEngineThreadLocal.getInvokerBundleNamespace();

		for (String allowedInvokerBundleSymbolicName :
				_allowedInvokerBundleSymbolicNames) {

			if (StringUtil.startsWith(
					invokerBundleNamespace, allowedInvokerBundleSymbolicName)) {

				return true;
			}
		}

		return false;
	}

	private static final Set<String> _allowedInvokerBundleSymbolicNames =
		Collections.unmodifiableSet(
			SetUtil.fromArray(
				"com.liferay.headless.builder", "com.liferay.object.service"));

}