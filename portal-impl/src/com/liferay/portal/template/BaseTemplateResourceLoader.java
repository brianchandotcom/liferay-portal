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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.kernel.util.InstanceFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tina Tian
 */
public abstract class BaseTemplateResourceLoader
	implements TemplateResourceLoader {

	public void clearCache() {
		_portalCache.removeAll();
	}

	public void clearCache(String templateId) {
		_portalCache.remove(templateId);
	}

	public void destroy() {
		_portalCache.destroy();
		_templateResourceParsers.clear();
	}

	public TemplateResource getTemplateResource(String templateId)
		throws TemplateException {

		TemplateResource templateResource = null;

		Object object = _portalCache.get(templateId);

		if ((object != null) && (object instanceof TemplateResource)) {
			templateResource = (TemplateResource)object;

			if (!checkInterval) {
				return templateResource;
			}

			if ((templateResource.getLastModified() + interval) >
				System.currentTimeMillis()) {

				return templateResource;
			}
		}

		for (TemplateResourceParser templateResourceParser :
			_templateResourceParsers) {

			templateResource = templateResourceParser.getTemplateResource(
				templateId);

			if (templateResource != null) {
				if (!TemplateManager.VELOCITY.equals(
						getTemplateResourceLoaderName()) ||
					!templateId.contains(SandboxHandler.SANDBOX_MARKER)) {

					_portalCache.put(templateId, templateResource);
				}

				break;
			}
		}

		return templateResource;
	}

	public abstract String getTemplateResourceLoaderName();

	public boolean hasTemplateResource(String templateId) {
		try {
			TemplateResource templateResource = getTemplateResource(templateId);

			if (templateResource != null) {
				return true;
			}
		}
		catch (Exception ex) {
			_log.warn(ex, ex);
		}

		return false;
	}

	public abstract void init() throws TemplateException;

	protected void initTemplateResourceParsers(
		String[] templateResourceParsers) {

		for (String templateResourceParser : templateResourceParsers) {
			try {
				TemplateResourceParser resourceParser =
					(TemplateResourceParser)InstanceFactory.newInstance(
						templateResourceParser);

				_templateResourceParsers.add(resourceParser);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected boolean checkInterval;
	protected int interval;

	private static Log _log = LogFactoryUtil.getLog(
		TemplateResourceLoader.class);

	private PortalCache _portalCache = MultiVMPoolUtil.getCache(
		TemplateResourceLoader.class.getName());
	private Set<TemplateResourceParser> _templateResourceParsers =
		new HashSet<TemplateResourceParser>();

}