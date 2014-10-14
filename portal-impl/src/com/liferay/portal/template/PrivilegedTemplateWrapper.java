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

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;

import java.io.Writer;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class PrivilegedTemplateWrapper implements Template {

	public PrivilegedTemplateWrapper(
		AccessControlContext accessControlContext, Template template) {

		_accessControlContext = accessControlContext;
		_template = template;
	}

	@Override
	public void doProcessTemplate(Writer writer) throws Exception {
		AccessController.doPrivileged(
			new ProcessTemplatePrivilegedExceptionAction(_template, writer),
			_accessControlContext);
	}

	@Override
	public Object get(String key) {
		return _template.get(key);
	}

	@Override
	public String[] getKeys() {
		return _template.getKeys();
	}

	@Override
	public void prepare(HttpServletRequest request) {
		_template.prepare(request);
	}

	@Override
	public void processTemplate(Writer writer) throws TemplateException {
		try {
			doProcessTemplate(writer);
		}
		catch (PrivilegedActionException pae) {
			throw (TemplateException)pae.getException();
		}
		catch (Exception e) {
			throw new TemplateException();
		}
	}

	@Override
	public void put(String key, Object value) {
		_template.put(key, value);
	}

	private final AccessControlContext _accessControlContext;
	private Template _template;

	private static class ProcessTemplatePrivilegedExceptionAction
		implements PrivilegedExceptionAction<Void> {

		public ProcessTemplatePrivilegedExceptionAction(
			Template template, Writer writer) {

			_template = template;
			_writer = writer;
		}

		@Override
		public Void run() throws Exception {
			_template.processTemplate(_writer);

			return null;
		}

		private Template _template;
		private final Writer _writer;

	}

}