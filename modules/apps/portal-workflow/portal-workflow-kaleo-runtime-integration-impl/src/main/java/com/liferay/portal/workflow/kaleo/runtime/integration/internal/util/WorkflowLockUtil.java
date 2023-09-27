/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Preston Crary
 */
public class WorkflowLockUtil {

	public static String encodeKey(String name, int version) {
		return StringBundler.concat(
			name, StringPool.POUND, StringUtil.toHexString(version));
	}

	private WorkflowLockUtil() {
	}

}