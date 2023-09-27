/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.log.Jdk14LogImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Shuyang Zhou
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *             com.liferay.portal.test.log.LoggerTestUtil}
 */
@Deprecated
public class JDKLoggerTestUtil {

	public static CaptureHandler configureJDKLogger(String name, Level level) {
		LogWrapper logWrapper = (LogWrapper)LogFactoryUtil.getLog(name);

		Log log = logWrapper.getWrappedLog();

		if (!(log instanceof Jdk14LogImpl)) {
			throw new IllegalStateException(
				"Log " + name + " is not a JDK logger");
		}

		Jdk14LogImpl jdk14LogImpl = (Jdk14LogImpl)log;

		Logger logger = jdk14LogImpl.getWrappedLogger();

		CaptureHandler captureHandler = new CaptureHandler(logger, level);

		logger.addHandler(captureHandler);

		return captureHandler;
	}

	static {

		// See LPS-32051 and LPS-32471

		LogFactoryUtil.getLog(JDKLoggerTestUtil.class);
	}

}