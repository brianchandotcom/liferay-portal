/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tika.internal.util;

import com.liferay.petra.process.ProcessConfig;
import com.liferay.portal.util.PortalClassPathUtil;

/**
 * @author Shuyang Zhou
 */
public class ProcessConfigUtil {

	public static ProcessConfig getProcessConfig() {
		return _processConfig;
	}

	private static final ProcessConfig _processConfig =
		PortalClassPathUtil.createBundleProcessConfig(ProcessConfigUtil.class);

}