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

package com.liferay.portal.template.velocity.internal;

import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceCache;
import com.liferay.portal.template.BaseTemplate;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.TemplateResourceThreadLocal;
import com.liferay.taglib.util.VelocityTaglib;
import com.liferay.taglib.util.VelocityTaglibImpl;

import java.io.Writer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;

/**
 * @author Tina Tian
 */
public class VelocityTemplate extends BaseTemplate {

	public VelocityTemplate(
		TemplateResource templateResource, Map<String, Object> context,
		VelocityEngine velocityEngine,
		TemplateContextHelper templateContextHelper,
		TemplateResourceCache templateResourceCache, boolean restricted) {

		super(templateResource, context, templateContextHelper, restricted);

		_velocityEngine = velocityEngine;
		_templateResourceCache = templateResourceCache;
		_restricted = restricted;

		_velocityContext = new VelocityContext(super.context);

		if (templateResourceCache.isEnabled()) {
			cacheTemplateResource(templateResourceCache, templateResource);
		}
	}

	@Override
	public void prepareTaglib(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		VelocityTaglib velocityTaglib = new VelocityTaglibImpl(
			httpServletRequest.getServletContext(), httpServletRequest,
			httpServletResponse, context);

		context.put("taglibLiferay", velocityTaglib);

		// Legacy support

		context.put("theme", velocityTaglib);
	}

	@Override
	protected void handleException(
			TemplateResource templateResource,
			TemplateResource errorTemplateResource, Exception exception1,
			Writer writer)
		throws TemplateException {

		if (_templateResourceCache.isEnabled()) {
			cacheTemplateResource(
				_templateResourceCache, errorTemplateResource);
		}

		put("exception", exception1.getMessage());

		if (templateResource instanceof StringTemplateResource) {
			StringTemplateResource stringTemplateResource =
				(StringTemplateResource)templateResource;

			put("script", stringTemplateResource.getContent());
		}

		if (exception1 instanceof ParseErrorException) {
			ParseErrorException parseErrorException =
				(ParseErrorException)exception1;

			put("column", parseErrorException.getColumnNumber());
			put("line", parseErrorException.getLineNumber());
		}

		try {
			processTemplate(errorTemplateResource, writer);
		}
		catch (Exception exception2) {
			throw new TemplateException(
				"Unable to process Velocity template " +
					errorTemplateResource.getTemplateId(),
				exception2);
		}
	}

	@Override
	protected void processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception {

		TemplateResourceThreadLocal.setTemplateResource(
			TemplateConstants.LANG_TYPE_VM, templateResource);

		if (_restricted) {
			RestrictedTemplateThreadLocal.setRestricted(true);
		}

		try {
			Template template = _velocityEngine.getTemplate(
				getTemplateResourceUUID(templateResource),
				TemplateConstants.DEFAUT_ENCODING);

			template.merge(_velocityContext, writer);
		}
		finally {
			TemplateResourceThreadLocal.setTemplateResource(
				TemplateConstants.LANG_TYPE_VM, null);

			if (_restricted) {
				RestrictedTemplateThreadLocal.setRestricted(false);
			}
		}
	}

	private final boolean _restricted;
	private final TemplateResourceCache _templateResourceCache;
	private final VelocityContext _velocityContext;
	private final VelocityEngine _velocityEngine;

}