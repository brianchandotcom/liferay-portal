/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.filters.compoundsessionid;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-18587.
 * </p>
 *
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class CompoundSessionIdSplitterUtil {

	public static String getSessionIdDelimiter() {
		return _SESSION_ID_DELIMITER;
	}

	public static boolean hasSessionDelimiter() {
		if (_SESSION_ID_DELIMITER == null) {
			return false;
		}

		return true;
	}

	public static String parseSessionId(String sessionId) {
		if (_SESSION_ID_DELIMITER == null) {
			return sessionId;
		}

		int pos = sessionId.indexOf(_SESSION_ID_DELIMITER);

		if (pos == -1) {
			return sessionId;
		}

		return sessionId.substring(0, pos);
	}

	private static final String _SESSION_ID_DELIMITER;

	static {
		String sessionIdDelimiter = PropsUtil.get(
			PropsKeys.SESSION_ID_DELIMITER);

		if (Validator.isNull(sessionIdDelimiter)) {
			sessionIdDelimiter = PropsUtil.get(
				"session.id." + ServerDetector.getServerId() + ".delimiter");
		}

		if (Validator.isNotNull(sessionIdDelimiter)) {
			_SESSION_ID_DELIMITER = sessionIdDelimiter;
		}
		else {
			_SESSION_ID_DELIMITER = null;
		}
	}

}