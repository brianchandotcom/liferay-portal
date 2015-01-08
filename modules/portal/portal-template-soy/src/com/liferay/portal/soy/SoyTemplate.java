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

package com.liferay.portal.soy;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.SoyFileSet.Builder;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.tofu.SoyTofu;
import com.google.template.soy.tofu.SoyTofu.Renderer;

import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.AbstractTemplate;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.TemplateResourceThreadLocal;

import java.io.Writer;

import java.net.URL;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.util.Map;

/**
 * @author Bruno Basto
 */
public class SoyTemplate extends AbstractTemplate {

	public SoyTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, Map<String, Object> context,
		Builder builder, TemplateContextHelper templateContextHelper,
		boolean privileged) {

		super(
			templateResource, errorTemplateResource, context,
			templateContextHelper, TemplateConstants.LANG_TYPE_SOY, 0);

		_builder = builder;
		_privileged = privileged;
	}

	protected SoyMapData getSoyContext() {
		SoyMapData soyContext = new SoyMapData();

		for (String key : context.keySet()) {
			if (key.equals("namespace")) {
				continue;
			}

			soyContext.put(key, get(key));
		}

		return soyContext;
	}

	protected SoyFileSet getSoyFileSet() throws PrivilegedActionException {
		SoyFileSet soyFileSet = null;

		if (_privileged) {
			soyFileSet = AccessController.doPrivileged(
				new TemplatePrivilegedExceptionAction(templateResource));
		}
		else {
			_builder.add(getTemplateResourceURL(templateResource));

			soyFileSet = _builder.build();
		}

		return soyFileSet;
	}

	protected URL getTemplateResourceURL(TemplateResource templateResource) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResource(templateResource.getTemplateId());
	}

	@Override
	protected void handleException(Exception exception, Writer writer)
		throws TemplateException {

		if ((exception instanceof Exception)) {
			put("exception", exception.getMessage());

			if (templateResource instanceof StringTemplateResource) {
				StringTemplateResource stringTemplateResource =
					(StringTemplateResource)templateResource;

				put("script", stringTemplateResource.getContent());
			}

			try {
				processTemplate(errorTemplateResource, writer);
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process Soy template " +
						errorTemplateResource.getTemplateId(),
					e);
			}
		}
		else {
			throw new TemplateException(
				"Unable to process Soy template " +
					templateResource.getTemplateId(),
				exception);
		}
	}

	@Override
	protected void processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception {

		TemplateResourceThreadLocal.setTemplateResource(
			TemplateConstants.LANG_TYPE_SOY, templateResource);

		try {
			String namespace = GetterUtil.getString(get("namespace"));

			if (Validator.isNull(namespace)) {
				throw new TemplateException("No namespace specified.");
			}

			SoyFileSet soyFileSet = getSoyFileSet();

			SoyTofu tofu = soyFileSet.compileToTofu();

			Renderer renderer = tofu.newRenderer(namespace);

			renderer.setData(getSoyContext());

			renderer.render(writer);
		}
		catch (PrivilegedActionException pae) {
			throw pae.getException();
		}
		finally {
			TemplateResourceThreadLocal.setTemplateResource(
				TemplateConstants.LANG_TYPE_SOY, null);
		}
	}

	private final Builder _builder;
	private final boolean _privileged;

	private class TemplatePrivilegedExceptionAction
		implements PrivilegedExceptionAction<SoyFileSet> {

		public TemplatePrivilegedExceptionAction(
			TemplateResource templateResource) {

			_templateResource = templateResource;
		}

		@Override
		public SoyFileSet run() throws Exception {
			_builder.add(getTemplateResourceURL(_templateResource));

			return _builder.build();
		}

		private final TemplateResource _templateResource;

	}

}