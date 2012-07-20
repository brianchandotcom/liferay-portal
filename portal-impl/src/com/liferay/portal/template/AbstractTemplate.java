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

import com.liferay.portal.deploy.sandbox.SandboxHandler;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tina Tian
 */
public abstract class AbstractTemplate implements Template {

	public AbstractTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource,
		TemplateContextHelper templateContextHelper,
		String templateResourceLoaderName, long interval) {

		if (templateResource == null) {
			throw new IllegalArgumentException("TemplateResource is null");
		}

		if (templateContextHelper == null) {
			throw new IllegalArgumentException("TemplateContextHelper is null");
		}

		this.templateResource = templateResource;
		this.errorTemplateResource = errorTemplateResource;

		if (templateResourceLoaderName == null) {
			throw new IllegalArgumentException(
				"Name of TemplateResourceLoader is null");
		}

		_templateContextHelper = templateContextHelper;

		_cacheTemplateResource(templateResourceLoaderName, interval);
	}

	public void prepare(HttpServletRequest request) {
		_templateContextHelper.prepare(this, request);
	}

	public boolean processTemplate(Writer writer) throws TemplateException {
		if (errorTemplateResource == null) {
			try {
				processTemplate(templateResource, writer);

				return true;
			}
			catch (Exception e) {
				throw new TemplateException(
					"Unable to process template " +
						templateResource.getTemplateId(),
					e);
			}
		}

		try {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			processTemplate(templateResource, unsyncStringWriter);

			StringBundler sb = unsyncStringWriter.getStringBundler();

			sb.writeTo(writer);

			return true;
		}
		catch (Exception e) {
			handleException(e, writer);

			return false;
		}
	}

	protected abstract void handleException(Exception exception, Writer writer)
		throws TemplateException;

	protected abstract void processTemplate(
			TemplateResource templateResource, Writer writer)
		throws Exception;

	protected TemplateResource errorTemplateResource;
	protected TemplateResource templateResource;

	private void _cacheTemplateResource(
		String templateResourceLoaderName, long interval) {

		if (interval == 0) {
			return;
		}

		if (templateResourceLoaderName.equals(TemplateManager.VELOCITY) &&
			templateResource.getTemplateId().contains(
				SandboxHandler.SANDBOX_MARKER)) {

			return;
		}

		if (!(templateResource instanceof StringTemplateResource) &&
			!(templateResource instanceof CacheTemplateResource)) {

			templateResource = new CacheTemplateResource(templateResource);
		}

		String cacheName = TemplateResourceLoader.class.getName().concat(
			StringPool.POUND).concat(templateResourceLoaderName);

		PortalCache portalCache = MultiVMPoolUtil.getCache(cacheName);

		Object object = portalCache.get(templateResource.getTemplateId());

		if ((object == null) || !templateResource.equals(object)) {
			portalCache.put(templateResource.getTemplateId(), templateResource);
		}

		if (errorTemplateResource == null) {
			return;
		}

		if (templateResourceLoaderName.equals(TemplateManager.VELOCITY) &&
			errorTemplateResource.getTemplateId().contains(
				SandboxHandler.SANDBOX_MARKER)) {

			return;
		}

		if (!(errorTemplateResource instanceof StringTemplateResource) &&
			!(errorTemplateResource instanceof CacheTemplateResource)) {

			errorTemplateResource = new CacheTemplateResource(
				errorTemplateResource);
		}

		object = portalCache.get(errorTemplateResource.getTemplateId());

		if ((object == null) || !errorTemplateResource.equals(object)) {
			portalCache.put(
				errorTemplateResource.getTemplateId(), errorTemplateResource);
		}
	}

	private TemplateContextHelper _templateContextHelper;

}