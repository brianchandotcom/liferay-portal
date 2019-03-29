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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.template.TemplateResourceThreadLocal;

import freemarker.cache.TemplateCache;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

import java.lang.reflect.Constructor;

import java.util.Locale;

/**
 * @author Tina Tian
 */
public class LiferayTemplateCache extends TemplateCache {

	public LiferayTemplateCache(
			Configuration configuration,
			TemplateResourceLoader templateResourceLoader,
			PortalCache<TemplateResource, MaybeMissingTemplate> portalCache)
		throws Exception {

		super(null, configuration);

		_configuration = configuration;
		_templateResourceLoader = templateResourceLoader;
		_portalCache = portalCache;

		_constructor = MaybeMissingTemplate.class.getDeclaredConstructor(
			Template.class);

		_constructor.setAccessible(true);
	}

	@Override
	public void clear() {
		if (_portalCache != null) {
			_portalCache.removeAll();
		}
	}

	@Override
	public MaybeMissingTemplate getTemplate(
			String templateId, Locale locale, Object customLookupCondition,
			String encoding, boolean parse)
		throws IOException {

		if (templateId == null) {
			throw new IllegalArgumentException("Argument \"name\" is null");
		}

		if (locale == null) {
			throw new IllegalArgumentException("Argument \"locale\" is null");
		}

		if (encoding == null) {
			throw new IllegalArgumentException("Argument \"encoding\" is null");
		}

		TemplateResource templateResource = null;

		if (templateId.startsWith(
				TemplateConstants.TEMPLATE_RESOURCE_UUID_PREFIX)) {

			templateResource = TemplateResourceThreadLocal.getTemplateResource(
				TemplateConstants.LANG_TYPE_FTL);
		}
		else {
			try {
				templateResource = _templateResourceLoader.getTemplateResource(
					templateId);
			}
			catch (Exception e) {
				templateResource = null;
			}
		}

		if (templateResource == null) {
			throw new IOException(
				"Unable to find FreeMarker template with ID " + templateId);
		}

		MaybeMissingTemplate maybeMissingTemplate = null;

		if (_portalCache != null) {
			maybeMissingTemplate = _portalCache.get(templateResource);

			if (maybeMissingTemplate != null) {
				return maybeMissingTemplate;
			}
		}

		Template template = new Template(
			templateResource.getTemplateId(), templateResource.getReader(),
			_configuration);

		try {
			maybeMissingTemplate = _constructor.newInstance(template);

			if (_portalCache != null) {
				_portalCache.put(templateResource, maybeMissingTemplate);
			}

			return maybeMissingTemplate;
		}
		catch (ReflectiveOperationException roe) {
			throw new IOException(roe);
		}
	}

	private final Configuration _configuration;
	private final Constructor<MaybeMissingTemplate> _constructor;
	private final PortalCache<TemplateResource, MaybeMissingTemplate>
		_portalCache;
	private final TemplateResourceLoader _templateResourceLoader;

}