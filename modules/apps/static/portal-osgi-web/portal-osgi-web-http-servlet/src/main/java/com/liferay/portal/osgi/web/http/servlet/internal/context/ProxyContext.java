/*******************************************************************************
 * Copyright (c) 2005, 2015 Cognos Incorporated, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.context;

import com.liferay.portal.osgi.web.http.servlet.internal.util.Const;

import java.io.File;
import java.io.Serializable;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 */
public class ProxyContext {

	public ProxyContext(String contextName, ServletContext servletContext) {
		_servletContext = servletContext;

		File tempDir = (File)servletContext.getAttribute(
			_JAVAX_SERVLET_CONTEXT_TEMPDIR);

		if (tempDir != null) {
			tempDir = new File(tempDir, "proxytemp");

			_proxyContextTempDir = new File(tempDir, contextName);

			deleteDirectory(_proxyContextTempDir);

			_proxyContextTempDir.mkdirs();
		}
		else {
			_proxyContextTempDir = null;
		}
	}

	public void createContextAttributes(ContextController controller) {
		synchronized (_attributesMap) {
			ContextAttributes contextAttributes = _attributesMap.get(
				controller);

			if (contextAttributes == null) {
				contextAttributes = new ContextAttributes(controller);

				_attributesMap.put(controller, contextAttributes);
			}

			contextAttributes.addReference();
		}
	}

	public void destroy() {
		if (_proxyContextTempDir != null) {
			deleteDirectory(_proxyContextTempDir);
		}
	}

	public void destroyContextAttributes(ContextController controller) {
		synchronized (_attributesMap) {
			ContextAttributes contextAttributes = _attributesMap.get(
				controller);

			if (contextAttributes == null) {
				throw new IllegalStateException("too many calls");
			}

			if (contextAttributes.removeReference() == 0) {
				_attributesMap.remove(controller);
				contextAttributes.destroy();
			}
		}
	}

	public Dictionary<String, Object> getContextAttributes(
		ContextController controller) {

		return _attributesMap.get(controller);
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	public String getServletPath() {
		return Const.BLANK;
	}

	public class ContextAttributes
		extends Dictionary<String, Object> implements Serializable {

		public ContextAttributes(ContextController controller) {
			if (_proxyContextTempDir != null) {
				File contextTempDir = new File(
					_proxyContextTempDir, "hc_" + controller.hashCode());

				contextTempDir.mkdirs();

				put(_JAVAX_SERVLET_CONTEXT_TEMPDIR, contextTempDir);
			}
		}

		public int addReference() {
			return _referenceCount.incrementAndGet();
		}

		public void destroy() {
			File contextTempDir = (File)get(_JAVAX_SERVLET_CONTEXT_TEMPDIR);

			if (contextTempDir != null) {
				deleteDirectory(contextTempDir);
			}
		}

		@Override
		public Enumeration<Object> elements() {
			return Collections.enumeration(_map.values());
		}

		@Override
		public Object get(Object key) {
			return _map.get(key);
		}

		@Override
		public boolean isEmpty() {
			return _map.isEmpty();
		}

		@Override
		public Enumeration<String> keys() {
			return Collections.enumeration(_map.keySet());
		}

		@Override
		public Object put(String key, Object value) {
			return _map.put(key, value);
		}

		@Override
		public Object remove(Object key) {
			return _map.remove(key);
		}

		public int removeReference() {
			return _referenceCount.decrementAndGet();
		}

		@Override
		public int size() {
			return _map.size();
		}

		private static final long serialVersionUID = 1916670423277243587L;

		private final Map<String, Object> _map = new ConcurrentHashMap<>();
		private final AtomicInteger _referenceCount = new AtomicInteger();

	}

	/**
	 * deleteDirectory is a convenience method to recursively delete a directory
	 * @param directory - the directory to delete.
	 * @return was the delete succesful
	 */
	protected static boolean deleteDirectory(File directory) {
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();

			for (File file : files) {
				if (file.isDirectory()) {
					deleteDirectory(file);
				}
				else {
					file.delete();
				}
			}
		}

		return directory.delete();
	}

	private static final String _JAVAX_SERVLET_CONTEXT_TEMPDIR =
		"javax.servlet.context.tempdir";

	private final ConcurrentMap<ContextController, ContextAttributes>
		_attributesMap = new ConcurrentHashMap<>();
	private final File _proxyContextTempDir;
	private final ServletContext _servletContext;

}