/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.servlet.HttpSessionWrapper;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletSessionAttributeMap;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PortletSessionImpl implements LiferayPortletSession {

	public PortletSessionImpl(
		HttpSession session, PortletContext portletContext, String portletName,
		long plid) {

		this.session = _wrapHttpSession(session);
		this.portletContext = portletContext;

		StringBundler sb = new StringBundler(5);

		sb.append(PORTLET_SCOPE_NAMESPACE);
		sb.append(portletName);
		sb.append(LAYOUT_SEPARATOR);
		sb.append(plid);
		sb.append(StringPool.QUESTION);

		scopePrefix = sb.toString();
	}

	@Override
	public Object getAttribute(String name) {
		return getAttribute(name, PORTLET_SCOPE);
	}

	@Override
	public Object getAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalidated) {
			throw new IllegalStateException();
		}

		if (scope == PORTLET_SCOPE) {
			name = scopePrefix.concat(name);
		}

		return session.getAttribute(name);
	}

	@Override
	public Map<String, Object> getAttributeMap() {
		return getAttributeMap(PortletSession.PORTLET_SCOPE);
	}

	@Override
	public Map<String, Object> getAttributeMap(int scope) {
		if (scope == PORTLET_SCOPE) {
			return new PortletSessionAttributeMap(session, scopePrefix);
		}

		return new PortletSessionAttributeMap(session);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return getAttributeNames(PORTLET_SCOPE);
	}

	@Override
	public Enumeration<String> getAttributeNames(int scope) {
		if (scope != PORTLET_SCOPE) {
			return session.getAttributeNames();
		}

		List<String> attributeNames = new ArrayList<>();

		Enumeration<String> enu = session.getAttributeNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(scopePrefix)) {
				name = name.substring(scopePrefix.length());

				attributeNames.add(name);
			}
		}

		return Collections.enumeration(attributeNames);
	}

	@Override
	public long getCreationTime() {
		if (_invalidated) {
			throw new IllegalStateException();
		}

		return session.getCreationTime();
	}

	public HttpSession getHttpSession() {
		return session;
	}

	@Override
	public String getId() {
		return session.getId();
	}

	@Override
	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	@Override
	public PortletContext getPortletContext() {
		return portletContext;
	}

	@Override
	public void invalidate() {
		_invalidated = true;

		session.invalidate();
	}

	public boolean isInvalidated() {
		return _invalidated;
	}

	@Override
	public boolean isNew() {
		return session.isNew();
	}

	@Override
	public void removeAttribute(String name) {
		removeAttribute(name, PORTLET_SCOPE);
	}

	@Override
	public void removeAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (scope == PORTLET_SCOPE) {
			name = scopePrefix.concat(name);
		}

		session.removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		setAttribute(name, value, PORTLET_SCOPE);
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (scope == PORTLET_SCOPE) {
			name = scopePrefix.concat(name);
		}

		session.setAttribute(name, value);
	}

	@Override
	public void setHttpSession(HttpSession session) {
		this.session = _wrapHttpSession(session);
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		session.setMaxInactiveInterval(interval);
	}

	protected final PortletContext portletContext;
	protected final String scopePrefix;
	protected HttpSession session;

	private HttpSession _wrapHttpSession(HttpSession session) {
		if (PropsValues.PORTLET_SESSION_REPLICATE_ENABLED &&
			!(session instanceof SerializableHttpSessionWrapper)) {

			return new SerializableHttpSessionWrapper(session);
		}

		return session;
	}

	private boolean _invalidated;

	private static class LazySerializable implements Serializable {

		public byte[] getData() {
			return _data;
		}

		public Serializable getSerializable() {
			Deserializer deserializer = new Deserializer(
				ByteBuffer.wrap(_data));

			try {
				return deserializer.readObject();
			}
			catch (ClassNotFoundException classNotFoundException) {
				_log.error(
					"Unable to deserialize object", classNotFoundException);

				return null;
			}
		}

		private LazySerializable(byte[] data) {
			_data = data;
		}

		private static final Log _log = LogFactoryUtil.getLog(
			LazySerializable.class);

		private final byte[] _data;

	}

	private static class LazySerializableObjectWrapper
		implements Externalizable {

		/**
		 * The empty constructor is required by {@link Externalizable}. Do not
		 * use this for any other purpose.
		 */
		public LazySerializableObjectWrapper() {
		}

		public Serializable getSerializable() {
			if (_serializable instanceof LazySerializable) {
				LazySerializable lazySerializable =
					(LazySerializable)_serializable;

				Serializable serializable = lazySerializable.getSerializable();

				if (serializable == null) {
					return null;
				}

				_serializable = serializable;
			}

			return _serializable;
		}

		@Override
		public void readExternal(ObjectInput objectInput) throws IOException {
			byte[] data = new byte[objectInput.readInt()];

			objectInput.readFully(data);

			_serializable = new LazySerializable(data);
		}

		@Override
		public void writeExternal(ObjectOutput objectOutput)
			throws IOException {

			byte[] data = _getData();

			objectOutput.writeInt(data.length);

			objectOutput.write(data, 0, data.length);
		}

		private LazySerializableObjectWrapper(Serializable serializable) {
			_serializable = serializable;
		}

		private byte[] _getData() {
			if (_serializable instanceof LazySerializable) {
				LazySerializable lazySerializable =
					(LazySerializable)_serializable;

				return lazySerializable.getData();
			}

			Serializer serializer = new Serializer();

			serializer.writeObject(_serializable);

			ByteBuffer byteBuffer = serializer.toByteBuffer();

			return byteBuffer.array();
		}

		private volatile Serializable _serializable;

	}

	private static class SerializableHttpSessionWrapper
		extends HttpSessionWrapper {

		@Override
		public Object getAttribute(String name) {
			Object value = super.getAttribute(name);

			if (value instanceof LazySerializableObjectWrapper) {
				LazySerializableObjectWrapper lazySerializableObjectWrapper =
					(LazySerializableObjectWrapper)value;

				return lazySerializableObjectWrapper.getSerializable();
			}

			return value;
		}

		@Override
		public void setAttribute(String name, Object value) {
			if (!(value instanceof Serializable)) {
				super.setAttribute(name, value);

				return;
			}

			Class<?> clazz = value.getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			if ((classLoader != null) &&
				!PortalClassLoaderUtil.isPortalClassLoader(classLoader)) {

				value = new LazySerializableObjectWrapper((Serializable)value);
			}

			super.setAttribute(name, value);
		}

		private SerializableHttpSessionWrapper(HttpSession session) {
			super(session);
		}

	}

}