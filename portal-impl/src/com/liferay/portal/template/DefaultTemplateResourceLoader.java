/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tina Tian
 */
@DoPrivileged
public class DefaultTemplateResourceLoader implements TemplateResourceLoader {

	public DefaultTemplateResourceLoader(
		String name, String[] templateResourceParserClassNames,
		long modificationCheckInterval) {

		if (Validator.isNull(name)) {
			throw new IllegalArgumentException(
				"Template resource loader name is null");
		}

		if (templateResourceParserClassNames == null) {
			throw new IllegalArgumentException(
				"Template resource parser class names is null");
		}

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

		String cacheName = TemplateResourceLoader.class.getName();

		cacheName = cacheName.concat(StringPool.PERIOD).concat(name);

		_portalCache = MultiVMPoolUtil.getCache(cacheName);

		CacheListener<String, TemplateResource> cacheListener =
			new TemplateResourceCacheListener(name);

		_portalCache.registerCacheListener(
			cacheListener, CacheListenerScope.ALL);
	}

	@Override
	public void clearCache() {
		_portalCache.removeAll();
	}

	@Override
	public void clearCache(String templateId) {
		_portalCache.remove(templateId);
	}

	@Override
	public void destroy() {
		_portalCache.destroy();

		_templateResourceParsers.clear();
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public TemplateResource getTemplateResource(String templateId) {
		TemplateResource templateResource = _loadFromCache(templateId);

		if (templateResource != null) {
			if (templateResource instanceof NullHolderTemplateResource) {
				return null;
			}

			return templateResource;
		}

		templateResource = _loadFromParser(templateId);

		if (_modificationCheckInterval != 0) {
			if (templateResource == null) {
				_portalCache.put(templateId, new NullHolderTemplateResource());
			}
			else {
				_portalCache.put(templateId, templateResource);
			}
		}

		return templateResource;
	}

	@Override
	public boolean hasTemplateResource(String templateId) {
		TemplateResource templateResource = getTemplateResource(templateId);

		if (templateResource != null) {
			return true;
		}

		return false;
	}

	private TemplateResource _loadFromCache(String templateId) {
		if (_modificationCheckInterval == 0) {
			return null;
		}

		Object object = _portalCache.get(templateId);

		if (object == null) {
			return null;
		}

		if (!(object instanceof TemplateResource)) {
			_portalCache.remove(templateId);

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Remove template " + templateId +
						" because it is not a template resource");
			}

			return null;
		}

		TemplateResource templateResource = (TemplateResource)object;

		if (_modificationCheckInterval > 0) {
			long expireTime =
				templateResource.getLastModified() + _modificationCheckInterval;

			if (System.currentTimeMillis() > expireTime) {
				_portalCache.remove(templateId);

				templateResource = null;

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Remove expired template resource " + templateId);
				}
			}
		}

		return templateResource;
	}

	private TemplateResource _loadFromParser(String templateId) {
		for (TemplateResourceParser templateResourceParser :
				_templateResourceParsers) {

			try {
				TemplateResource templateResource =
					templateResourceParser.getTemplateResource(templateId);

				if (templateResource != null) {
					if ((_modificationCheckInterval != 0) &&
						(!_name.equals(TemplateConstants.LANG_TYPE_VM) ||
						 !templateId.contains(
							 SandboxHandler.SANDBOX_MARKER))) {

						templateResource = new CacheTemplateResource(
							templateResource);
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

		return null;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultTemplateResourceLoader.class);

	private long _modificationCheckInterval;
	private String _name;
	private PortalCache<String, TemplateResource> _portalCache;
	private Set<TemplateResourceParser> _templateResourceParsers =
		new HashSet<TemplateResourceParser>();

	private static class NullHolderTemplateResource
		implements TemplateResource {

		/**
		 * The empty constructor is required by {@link java.io.Externalizable}.
		 * Do not use this for any other purpose.
		 */
		public NullHolderTemplateResource() {
		}

		public long getLastModified() {
			return _lastModified;
		}

		public Reader getReader() {
			return null;
		}

		public String getTemplateId() {
			return null;
		}

		public void readExternal(ObjectInput objectInput) throws IOException {
			_lastModified = objectInput.readLong();
		}

		public void writeExternal(ObjectOutput objectOutput)
			throws IOException {

			objectOutput.writeLong(_lastModified);
		}

		private long _lastModified = System.currentTimeMillis();

	}

}