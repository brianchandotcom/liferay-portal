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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.TemplateResourceThreadLocal;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import freemarker.cache.TemplateCache;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.util.Locale;

/**
 * @author Tina Tian
 */
public class LiferayTemplateCache extends TemplateCache {

	public LiferayTemplateCache(Configuration configuration) {
		_configuration = configuration;

		String cacheName = TemplateResource.class.getName();

		cacheName = cacheName.concat(StringPool.POUND).concat(
			TemplateConstants.LANG_TYPE_FTL);

		_portalCache = SingleVMPoolUtil.getCache(cacheName);
	}

	@Override
	public Template getTemplate(
			String templateId, Locale locale, String encoding, boolean parse)
		throws IOException {

		String[] macroTemplateIds = PropsUtil.getArray(
			PropsKeys.FREEMARKER_ENGINE_MACRO_LIBRARY);

		for (String macroTemplateId : macroTemplateIds) {
			int pos = macroTemplateId.indexOf(" as ");

			if (pos != -1) {
				macroTemplateId = macroTemplateId.substring(0, pos);
			}

			if (templateId.equals(macroTemplateId)) {

				// This template is provided by the portal, so invoke it from an
				// access controller

				try {
					return AccessController.doPrivileged(
						new TemplatePrivilegedExceptionAction(
							macroTemplateId, locale, encoding));
				}
				catch (PrivilegedActionException pae) {
					throw (IOException)pae.getException();
				}
			}
		}

		return doGetTemplate(templateId, locale, encoding);
	}

	private Template doGetTemplate(
			String templateId, Locale locale, String encoding)
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
				templateResource =
					TemplateResourceLoaderUtil.getTemplateResource(
						TemplateConstants.LANG_TYPE_FTL, templateId);
			}
			catch (Exception e) {
				templateResource = null;
			}
		}

		if (templateResource == null) {
			throw new IOException(
				"Unable to find FreeMarker template with ID " + templateId);
		}

		Object object = _portalCache.get(templateResource);

		if ((object != null) && (object instanceof Template)) {
			return (Template)object;
		}

		Template template = new Template(
			templateResource.getTemplateId(), templateResource.getReader(),
			_configuration, TemplateConstants.DEFAUT_ENCODING);

		if (PropsValues.
				FREEMARKER_ENGINE_RESOURCE_MODIFICATION_CHECK_INTERVAL != 0) {

			_portalCache.put(templateResource, template);
		}

		return template;
	}

	private final Configuration _configuration;
	private final PortalCache<TemplateResource, Object> _portalCache;

	private class TemplatePrivilegedExceptionAction
		implements PrivilegedExceptionAction<Template> {

		public TemplatePrivilegedExceptionAction(
			String templateId, Locale locale, String encoding) {

			_templateId = templateId;
			_locale = locale;
			_encoding = encoding;
		}

		@Override
		public Template run() throws Exception {
			return doGetTemplate(_templateId, _locale, _encoding);
		}

		private final String _encoding;
		private final Locale _locale;
		private final String _templateId;

	}

}