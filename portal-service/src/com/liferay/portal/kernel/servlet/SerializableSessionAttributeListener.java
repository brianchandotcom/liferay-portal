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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * @author Bruno Farache
 */
public class SerializableSessionAttributeListener
	extends BasePortalLifecycle implements HttpSessionAttributeListener {

	public void attributeAdded(
		HttpSessionBindingEvent httpSessionBindingEvent) {

		if (!_sessionVerifySerializableAttribute) {
			return;
		}

		String name = httpSessionBindingEvent.getName();
		Object value = httpSessionBindingEvent.getValue();

		if (value instanceof Serializable) {
			return;
		}

		Class<?> clazz = value.getClass();

		_log.error(
			clazz.getName() +
				" is not serializable and will prevent this session from " +
					"being replicated");

		if (_requiresSerializable == null) {
			HttpSession session = httpSessionBindingEvent.getSession();

			ServletContext servletContext = session.getServletContext();

			_requiresSerializable = Boolean.valueOf(
				GetterUtil.getBoolean(
					servletContext.getInitParameter(
						"session-attributes-requires-serializable")));
		}

		if (_requiresSerializable) {
			HttpSession session = httpSessionBindingEvent.getSession();

			session.removeAttribute(name);
		}
	}

	public void attributeRemoved(
		HttpSessionBindingEvent httpSessionBindingEvent) {
	}

	public void attributeReplaced(
		HttpSessionBindingEvent httpSessionBindingEvent) {

		attributeAdded(httpSessionBindingEvent);
	}

	@Override
	protected void doPortalDestroy() throws Exception {
	}

	@Override
	protected void doPortalInit() throws Exception {
		_sessionVerifySerializableAttribute = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SESSION_VERIFY_SERIALIZABLE_ATTRIBUTE),
			true);
	}

	private static Log _log = LogFactoryUtil.getLog(
		SerializableSessionAttributeListener.class);

	private Boolean _requiresSerializable;
	private boolean _sessionVerifySerializableAttribute;

}