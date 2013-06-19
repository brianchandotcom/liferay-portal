/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.servlet.filters.compoundsessionid;

import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdSplitter;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

/**
 * @author Michael C. Han
 */
public class CompoundSessionIdSplitterImpl
	implements CompoundSessionIdSplitter {

	@Override
	public String getSessionIdDelimiter() {
		return _sessionIdDelimiter;
	}

	@Override
	public boolean hasSessionDelimiter() {
		return Validator.isNotNull(_sessionIdDelimiter);
	}

	@Override
	public String parseSessionId(String sessionId) {
		if (Validator.isNull(_sessionIdDelimiter)) {
			return sessionId;
		}

		int pos = sessionId.indexOf(_sessionIdDelimiter);

		if (pos == -1) {
			return sessionId;
		}

		return sessionId.substring(0, pos);
	}

	private static String _sessionIdDelimiter;

	static {
		String sessionIdDelimiter = PropsValues.SESSION_ID_DELIMITER;

		if (Validator.isNull(sessionIdDelimiter)) {
			sessionIdDelimiter = PropsUtil.get(
				"session.id." + ServerDetector.getServerId() + ".delimiter");
		}

		if (sessionIdDelimiter == null) {
			sessionIdDelimiter = StringPool.BLANK;
		}

		_sessionIdDelimiter = sessionIdDelimiter;
	}

}
