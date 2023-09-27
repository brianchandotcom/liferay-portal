/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testhook.util;

import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Time;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 */
public class TestHookUtil {

	public static File getStartupActionFile() {
		return _instance._getStartupActionFile();
	}

	private TestHookUtil() {
		String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

		_startupActionFileName =
			tmpDir + "/liferay/testhook/" + Time.getTimestamp();
	}

	private File _getStartupActionFile() {
		return new File(_startupActionFileName);
	}

	private static final TestHookUtil _instance = new TestHookUtil();

	private final String _startupActionFileName;

}