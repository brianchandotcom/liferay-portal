/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import org.hibernate.Session;

/**
 * @author Shuyang Zhou
 */
public class LastSessionRecorderUtil {

	public static void syncLastSessionState() throws SystemException {
		Session session = _lastSessionThreadLocal.get();

		if ((session != null) && session.isOpen()) {
			try {
				session.flush();
				session.clear();
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}
	}

	protected static void setLastSession(Session session) {
		_lastSessionThreadLocal.set(session);
	}

	private static ThreadLocal<Session> _lastSessionThreadLocal =
		new AutoResetThreadLocal<Session>(
			LastSessionRecorderUtil.class.getName() +
				"._lastSessionThreadLocal");

}