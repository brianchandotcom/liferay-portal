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

package com.liferay.portal.template;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tina Tian
 */
public abstract class AbstractTemplate implements Template {

	public AbstractTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource,
		TemplateContextHelper templateContextHelper) {

		if (templateResource == null) {
			throw new IllegalArgumentException("TemplateResource is null");
		}

		if (templateContextHelper == null) {
			throw new IllegalArgumentException("TemplateContextHelper is null");
		}

		_templateResource = templateResource;
		_errorTemplateResource = errorTemplateResource;
		_templateContextHelper = templateContextHelper;
	}

	public void prepare(HttpServletRequest request) {
		_templateContextHelper.prepare(this, request);
	}

	public boolean processTemplate(Writer writer) throws TemplateException {
		if (_errorTemplateResource == null) {
			try {
				processTemplate(_templateResource, writer);

				return true;
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process template " +
						_templateResource.getTemplateId(),
					e);
			}
		}

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			processTemplate(_templateResource, unsyncStringWriter);

			StringBundler sb = unsyncStringWriter.getStringBundler();

			sb.writeTo(writer);

			return true;
		}
		catch (Exception e) {
			handleException(
				_templateResource, _errorTemplateResource, e, writer);

			return false;
		}
	}

	protected abstract void handleException(
			TemplateResource templateResource,
			TemplateResource errorTemplateResource, Exception exception,
			Writer writer)
		throws TemplateException;

	protected abstract void processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception;

	private TemplateResource _errorTemplateResource;
	private TemplateContextHelper _templateContextHelper;
	private TemplateResource _templateResource;

}