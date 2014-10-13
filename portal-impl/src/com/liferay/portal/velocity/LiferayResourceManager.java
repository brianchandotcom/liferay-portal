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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.TemplateResourceThreadLocal;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.Reader;

import java.lang.reflect.Field;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.Template;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.ResourceManager;
import org.apache.velocity.runtime.resource.ResourceManagerImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class LiferayResourceManager extends ResourceManagerImpl {

	public LiferayResourceManager() {
		String cacheName = TemplateResource.class.getName();

		cacheName = cacheName.concat(StringPool.POUND).concat(
			TemplateConstants.LANG_TYPE_VM);

		_portalCache = SingleVMPoolUtil.getCache(cacheName);
	}

	@Override
	public String getLoaderNameForResource(String source) {

		// Velocity's default implementation makes its cache useless because
		// getResourceStream is called to test the availability of a template

		if ((globalCache.get(ResourceManager.RESOURCE_CONTENT + source) !=
				null) ||
			(globalCache.get(ResourceManager.RESOURCE_TEMPLATE + source) !=
				null)) {

			return LiferayResourceLoader.class.getName();
		}
		else {
			return super.getLoaderNameForResource(source);
		}
	}

	@Override
	public Resource getResource(
			final String resourceName, final int resourceType,
			final String encoding)
		throws Exception, ParseErrorException, ResourceNotFoundException {

		String[] macroTemplateIds = PropsUtil.getArray(
			PropsKeys.VELOCITY_ENGINE_VELOCIMACRO_LIBRARY);

		for (String macroTemplateId : macroTemplateIds) {
			if (resourceName.equals(macroTemplateId)) {

				// This resource is provided by the portal, so invoke it from an
				// access controller

				try {
					return AccessController.doPrivileged(
						new ResourcePrivilegedExceptionAction(
							resourceName, resourceType, encoding));
				}
				catch (PrivilegedActionException pae) {
					throw (IOException)pae.getException();
				}
			}
		}

		return doGetResource(resourceName, resourceType, encoding);
	}

	@Override
	public synchronized void initialize(RuntimeServices runtimeServices)
		throws Exception {

		ExtendedProperties extendedProperties =
			runtimeServices.getConfiguration();

		Field field = ReflectionUtil.getDeclaredField(
			RuntimeInstance.class, "configuration");

		field.set(
			runtimeServices, new FastExtendedProperties(extendedProperties));

		super.initialize(runtimeServices);
	}

	private Template _createTemplate(TemplateResource templateResource)
		throws IOException {

		Template template = new LiferayTemplate(templateResource.getReader());

		template.setEncoding(TemplateConstants.DEFAUT_ENCODING);
		template.setName(templateResource.getTemplateId());
		template.setResourceLoader(new LiferayResourceLoader());
		template.setRuntimeServices(rsvc);

		template.process();

		return template;
	}

	private Resource doGetResource(
			final String resourceName, final int resourceType,
			final String encoding)
		throws Exception, ParseErrorException, ResourceNotFoundException {

		if (resourceType != ResourceManager.RESOURCE_TEMPLATE) {
			return super.getResource(resourceName, resourceType, encoding);
		}

		TemplateResource templateResource = null;

		if (resourceName.startsWith(
				TemplateConstants.TEMPLATE_RESOURCE_UUID_PREFIX)) {

			templateResource = TemplateResourceThreadLocal.getTemplateResource(
				TemplateConstants.LANG_TYPE_VM);
		}
		else {
			templateResource = TemplateResourceLoaderUtil.getTemplateResource(
				TemplateConstants.LANG_TYPE_VM, resourceName);
		}

		if (templateResource == null) {
			throw new ResourceNotFoundException(
				"Unable to find Velocity template with ID " + resourceName);
		}

		Object object = _portalCache.get(templateResource);

		if ((object != null) && (object instanceof Template)) {
			return (Template)object;
		}

		Template template = _createTemplate(templateResource);

		if (PropsValues.VELOCITY_ENGINE_RESOURCE_MODIFICATION_CHECK_INTERVAL !=
				0) {

			_portalCache.put(templateResource, template);
		}

		return template;
	}

	private final PortalCache<TemplateResource, Object> _portalCache;

	private class LiferayTemplate extends Template {

		public LiferayTemplate(Reader reader) {
			_reader = reader;
		}

		@Override
		public boolean process() throws IOException, ParseErrorException {
			data = null;

			try {
				data = rsvc.parse(_reader, name);

				initDocument();

				return true;
			}
			catch (Exception e) {
				throw new ParseErrorException(
					"Unable to parse Velocity template");
			}
			finally {
				if (_reader != null) {
					_reader.close();
				}
			}
		}

		private final Reader _reader;

	}

	private class ResourcePrivilegedExceptionAction
		implements PrivilegedExceptionAction<Resource> {

		public ResourcePrivilegedExceptionAction(
			String resourceName, int resourceType, String encoding) {

			_resourceName = resourceName;
			_resourceType = resourceType;
			_encoding = encoding;
		}

		@Override
		public Resource run() throws Exception {
			return doGetResource(_resourceName, _resourceType, _encoding);
		}

		private final String _encoding;
		private final String _resourceName;
		private final int _resourceType;

	}

}