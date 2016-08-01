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

package com.liferay.portal.template.soy.internal;

import com.google.common.io.CharStreams;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.SoyFileSet.Builder;
import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.tofu.SoyTofu;
import com.google.template.soy.tofu.SoyTofu.Renderer;

import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.AbstractMultiResourceTemplate;
import com.liferay.portal.template.TemplateContextHelper;

import java.io.Reader;
import java.io.Writer;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Bruno Basto
 */
public class SoyTemplate extends AbstractMultiResourceTemplate {

	public SoyTemplate(
		List<TemplateResource> templateResources,
		TemplateResource errorTemplateResource, Map<String, Object> context,
		TemplateContextHelper templateContextHelper, boolean privileged) {

		super(
			templateResources, errorTemplateResource, context,
			templateContextHelper, TemplateConstants.LANG_TYPE_SOY, 0);

		_privileged = privileged;
	}

	protected SoyFileSet getSoyFileSet(List<TemplateResource> templateResources)
		throws Exception {

		SoyFileSet soyFileSet = null;

		if (_privileged) {
			soyFileSet = AccessController.doPrivileged(
				new TemplatePrivilegedExceptionAction(templateResources));
		}
		else {
			Builder builder = SoyFileSet.builder();

			for (TemplateResource templateResource : templateResources) {
				String templateContent = getTemplateContent(templateResource);

				builder.add(templateContent, templateResource.getTemplateId());
			}

			soyFileSet = builder.build();
		}

		return soyFileSet;
	}

	protected SoyMapData getSoyMapData() {
		SoyMapData soyMapData = new SoyMapData();

		for (String key : context.keySet()) {
			if (key.equals(TemplateConstants.NAMESPACE)) {
				continue;
			}

			soyMapData.put(key, get(key));
		}

		return soyMapData;
	}

	protected String getTemplateContent(TemplateResource templateResource)
		throws Exception {

		Reader reader = templateResource.getReader();

		return CharStreams.toString(reader);
	}

	@Override
	protected void handleException(Exception exception, Writer writer)
		throws TemplateException {

		put("exception", exception.getMessage());

		StringBundler sb = new StringBundler();

		for (TemplateResource templateResource : templateResources) {
			if (templateResource instanceof StringTemplateResource) {
				StringTemplateResource stringTemplateResource =
					(StringTemplateResource)templateResource;

				sb.append(stringTemplateResource.getContent());
			}
		}

		put("script", sb.toString());

		try {
			processTemplates(Arrays.asList(errorTemplateResource), writer);
		}
		catch (Exception e) {
			throw new TemplateException(
				"Unable to process Soy template " +
					errorTemplateResource.getTemplateId(),
				e);
		}
	}

	@Override
	protected void processTemplates(
			List<TemplateResource> templateResources, Writer writer)
		throws Exception {

		try {
			String namespace = GetterUtil.getString(
				get(TemplateConstants.NAMESPACE));

			if (Validator.isNull(namespace)) {
				throw new TemplateException("No namespace specified.");
			}

			SoyFileSet soyFileSet = getSoyFileSet(templateResources);

			SoyTofu soyTofu = soyFileSet.compileToTofu();

			Renderer renderer = soyTofu.newRenderer(namespace);

			renderer.setData(getSoyMapData());

			boolean renderStrict = GetterUtil.getBoolean(
				get(TemplateConstants.RENDER_STRICT), true);

			if (renderStrict) {
				SanitizedContent sanitizedContent = renderer.renderStrict();

				writer.write(sanitizedContent.stringValue());
			}
			else {
				writer.write(renderer.render());
			}
		}
		catch (PrivilegedActionException pae) {
			throw pae.getException();
		}
	}

	private final boolean _privileged;

	private class TemplatePrivilegedExceptionAction
		implements PrivilegedExceptionAction<SoyFileSet> {

		public TemplatePrivilegedExceptionAction(
			List<TemplateResource> templateResources) {

			_templateResources = templateResources;
		}

		@Override
		public SoyFileSet run() throws Exception {
			Builder builder = SoyFileSet.builder();

			for (TemplateResource templateResource : _templateResources) {
				String templateContent = getTemplateContent(templateResource);
				builder.add(templateContent, templateResource.getTemplateId());
			}

			return builder.build();
		}

		private final List<TemplateResource> _templateResources;

	}

}