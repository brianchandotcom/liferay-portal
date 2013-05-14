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

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;

import java.io.Writer;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * @author Raymond Augé
 */
public abstract class AbstractProcessingTemplate implements Template {

	public abstract TemplateContextHelper getTemplateContextHelper();

	@Override
	public final void processTemplate(Writer writer) throws TemplateException {
		TemplateControlContext templateControlContext =
			getTemplateContextHelper().getTemplateControlContext();

		AccessControlContext accessControlContext =
			templateControlContext.getAccessControlContext();

		if (accessControlContext == null) {
			doProcessTemplate(writer);

			return;
		}

		try {
			AccessController.doPrivileged(
				new DoProcessTemplatePrivilegedExceptionAction(writer),
				accessControlContext);
		}
		catch (PrivilegedActionException pae) {
			throw (TemplateException)pae.getException();
		}
	}

	protected abstract void doProcessTemplate(Writer writer)
		throws TemplateException;

	private class DoProcessTemplatePrivilegedExceptionAction
		implements PrivilegedExceptionAction<Void> {

		public DoProcessTemplatePrivilegedExceptionAction(Writer writer) {
			_writer = writer;
		}

		public Void run() throws Exception {
			doProcessTemplate(_writer);

			return null;
		}

		private Writer _writer;

	}

}