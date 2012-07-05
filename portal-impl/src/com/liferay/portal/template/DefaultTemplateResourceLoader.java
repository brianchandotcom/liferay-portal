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
public class DefaultTemplateResourceLoader implements TemplateResourceLoader {

	public DefaultTemplateResourceLoader(
		String name, String[] templateResourceParserClassNames,
		long modificationCheckInterval) {

		_name = name;

		for (String templateResourceParserClassName :
				templateResourceParserClassNames) {

			try {
				TemplateResourceParser templateResourceParser =
					(TemplateResourceParser)InstanceFactory.newInstance(
						templateResourceParserClassName);

				_templateResourceParsers.add(templateResourceParser);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		_modificationCheckInterval = modificationCheckInterval;
	}

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

	public String getName() {
		return _name;
	}

	public TemplateResource getTemplateResource(String templateId) {
		TemplateResource templateResource = null;

		Object object = _portalCache.get(templateId);

		if (object != null) {
			if (object instanceof TemplateResource) {
				templateResource = (TemplateResource)object;

				if (_modificationCheckInterval < 0) {
					return templateResource;
				}

				long expireTime =
					templateResource.getLastModified() +
						_modificationCheckInterval;

				if (expireTime > System.currentTimeMillis()) {
					return templateResource;
				}
				else {
					_portalCache.remove(templateId);

					if (_log.isDebugEnabled()) {
						_log.debug("Reload stale template " + templateId);
					}
				}
			}
			else if (object instanceof DummyTemplateResource) {
				return null;
			}
			else {
				_portalCache.remove(templateId);

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Remove template " + templateId +
							" because it is not a template resource");
				}
			}
		}

		for (TemplateResourceParser templateResourceParser :
				_templateResourceParsers) {

			try {
				templateResource = templateResourceParser.getTemplateResource(
					templateId);

				if (templateResource != null) {
					if ((_modificationCheckInterval != 0) &&
						(!_name.equals(TemplateManager.VELOCITY) ||
							!templateId.contains(
								SandboxHandler.SANDBOX_MARKER))) {

						templateResource = new TemplateResourceCacheWrapper(
							templateResource);

						_portalCache.put(templateId, templateResource);
					}

					return templateResource;
				}
			}
			catch (TemplateException te) {
				_log.warn(
					"Unable to parse template " + templateId + " with parser " +
						templateResourceParser,
					te);
			}
		}

		_portalCache.put(templateId, new DummyTemplateResource());

		return null;
	}

	public boolean hasTemplateResource(String templateId) {
		TemplateResource templateResource = getTemplateResource(templateId);

		if (templateResource != null) {
			return true;
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultTemplateResourceLoader.class);

	private long _modificationCheckInterval;
	private String _name;
	private PortalCache _portalCache = MultiVMPoolUtil.getCache(
		TemplateResourceLoader.class.getName());
	private Set<TemplateResourceParser> _templateResourceParsers =
		new HashSet<TemplateResourceParser>();

	private class DummyTemplateResource {
	}

}