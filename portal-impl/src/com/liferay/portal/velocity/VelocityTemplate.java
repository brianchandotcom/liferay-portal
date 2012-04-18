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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.util.PropsValues;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * @author Tina Tian
 */
public class VelocityTemplate implements Template {

	public VelocityTemplate(
		String templateId, String templateContent, String errorTemplateId,
		String errorTemplateContent, VelocityContext context,
		VelocityEngine velocityEngine,
		TemplateContextHelper templateContextHelper) {

		if (context == null) {
			_velocityContext = new VelocityContext();
		}
		else {
			_velocityContext = new VelocityContext(context);
		}

		_templateId = templateId;
		_templateContent = templateContent;

		if (errorTemplateId != null) {
			_errorTemplateId = errorTemplateId;
			_errorTemplateContent = errorTemplateContent;
			_hasErrorTemplate = true;
		}

		_velocityEngine = velocityEngine;
		_templateContextHelper = templateContextHelper;
	}

	public Object get(String key) {
		return _velocityContext.get(key);
	}

	public void prepare(HttpServletRequest request) throws TemplateException {
		_templateContextHelper.prepare(this, request);
	}

	public boolean processTemplate(Writer writer) throws TemplateException {
		handleTemplateContent(_templateId, _templateContent);

		if (!_hasErrorTemplate) {
			try {
				return _velocityEngine.mergeTemplate(
					_templateId, StringPool.UTF8, _velocityContext, writer);
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process freemarker template " + _templateId, e);
			}
		}

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			boolean result = _velocityEngine.mergeTemplate(
				_templateId, StringPool.UTF8, _velocityContext,
				unsyncStringWriter);

			unsyncStringWriter.getStringBundler().writeTo(writer);

			return result;
		}
		catch (Exception e) {
			put("exception", e.getMessage());
			put("script", _templateContent);

			if (e instanceof ParseErrorException) {
				ParseErrorException pee = (ParseErrorException)e;

				put("column", pee.getColumnNumber());
				put("line", pee.getLineNumber());
			}

			handleTemplateContent(_errorTemplateId, _errorTemplateContent);

			try {
				 _velocityEngine.mergeTemplate(
					_errorTemplateId, StringPool.UTF8, _velocityContext,
					writer);

				return false;
			}
			catch (Exception ex) {
				throw new TemplateException(
					"Unable to process freemarker template " + _errorTemplateId,
					ex);
			}
		}
	}

	public void put(String key, Object value) {
		if (value == null) {
			return;
		}

		_velocityContext.put(key, value);
	}

	protected void handleTemplateContent(
		String templateId, String templateContent) {

		if (Validator.isNotNull(templateContent) &&
			(!PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED ||
			 !_velocityEngine.resourceExists(templateId))) {

			StringResourceRepository stringResourceRepository =
				StringResourceLoader.getRepository();

			stringResourceRepository.putStringResource(
				templateId, templateContent);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Added " + templateId +
						" to the Velocity template repository");
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VelocityTemplate.class);

	private String _errorTemplateContent;
	private String _errorTemplateId;
	private boolean _hasErrorTemplate;
	private String _templateContent;
	private TemplateContextHelper _templateContextHelper;
	private String _templateId;
	private VelocityContext _velocityContext;
	private VelocityEngine _velocityEngine;

}