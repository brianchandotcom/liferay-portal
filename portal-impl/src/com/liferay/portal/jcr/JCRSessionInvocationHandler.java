/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jcr;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Binary;
import javax.jcr.Session;

/**
 * @author Raymond Augé
 */
public class JCRSessionInvocationHandler implements InvocationHandler {

	public JCRSessionInvocationHandler(Session session) {
		_session = session;

		if (_log.isDebugEnabled()) {
			_log.debug("starting a new session " + _session);
		}
	}

	public Object invoke(
			Object proxy, Method method, Object[] args)
		throws Throwable {

		if (method.getName().equals("close")) {
			if (_log.isDebugEnabled()) {
				_log.debug("closing a session " + _session);
			}

			Iterator<Entry<String, Binary>> iterator =
				_valuesMap.entrySet().iterator();

			while (iterator.hasNext()) {
				Binary binary = iterator.next().getValue();

				binary.dispose();

				iterator.remove();
			}

			_session.logout();

			_valuesMap = null;
			_session = null;

			return null;
		}
		else if (method.getName().equals("logout")) {
			if (_log.isDebugEnabled()) {
				_log.debug("skipping logout for session " + _session);
			}

			return null;
		}
		else if (method.getName().equals("put")) {
			String key = (String)args[0];
			Binary value = (Binary)args[1];

			if (_log.isDebugEnabled()) {
				_log.debug("tacking a Binary in session " + _session);
			}

			_valuesMap.put(key, value);

			return null;
		}

		try {
			return method.invoke(_session, args);
		}
		catch (InvocationTargetException ite) {
			throw ite.getCause();
		}
		catch (Exception e) {
			throw e;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JCRSessionInvocationHandler.class);

	private Map<String,Binary> _valuesMap = new HashMap<String,Binary>();
	private Session _session;

}