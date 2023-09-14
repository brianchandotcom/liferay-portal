/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.system.util;

import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Gabriel Albuquerque
 */
public class SystemObjectDefinitionManagementChecker {

	public static boolean isInvokerBundleAllowed() {
		if (PortalRunMode.isTestMode()) {
			return true;
		}

		return ArrayUtil.exists(
			_ALLOWED_INVOKER_BUNDLE_SYMBOLIC_NAMES,
			allowedInvokerBundleSymbolicName -> StringUtil.startsWith(
				BatchEngineUnitThreadLocal.getInvokerBundleNamespace(),
				allowedInvokerBundleSymbolicName));
	}

	private static final String[] _ALLOWED_INVOKER_BUNDLE_SYMBOLIC_NAMES = {
		"com.liferay.headless.builder.impl", "com.liferay.object.service"
	};

}