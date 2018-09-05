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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.template.TemplateResourceThreadLocal;

import java.io.IOException;
import java.io.Reader;

import java.lang.reflect.Field;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
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

		return super.getLoaderNameForResource(source);
	}

	@Override
	public Resource getResource(
			String resourceName, int resourceType, String encoding)
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
			templateResource = _templateResourceLoader.getTemplateResource(
				resourceName);
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

		if (_resourceModificationCheckInterval != 0) {
			_portalCache.put(templateResource, template);
		}

		return template;
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

		_resourceModificationCheckInterval = GetterUtil.getInteger(
			extendedProperties.get(
				"liferay." + VelocityEngine.RESOURCE_LOADER +
					".resourceModificationCheckInterval"),
			60);
		_templateResourceLoader =
			(TemplateResourceLoader)extendedProperties.get(
				VelocityTemplateResourceLoader.class.getName());

		SingleVMPool singleVMPool = (SingleVMPool)runtimeServices.getProperty(
			PortalCacheManagerNames.SINGLE_VM);

		_portalCache =
			(PortalCache<TemplateResource, Object>)singleVMPool.getPortalCache(
				StringBundler.concat(
					TemplateResource.class.getName(), StringPool.POUND,
					TemplateConstants.LANG_TYPE_VM));

		_portalCache.removeAll();

		super.initialize(runtimeServices);
	}

	private Template _createTemplate(TemplateResource templateResource)
		throws IOException {

		Template template = new LiferayTemplate(templateResource);

		template.setEncoding(TemplateConstants.DEFAUT_ENCODING);
		template.setName(templateResource.getTemplateId());
		template.setResourceLoader(new LiferayResourceLoader());
		template.setRuntimeServices(rsvc);

		template.process();

		return template;
	}

	private PortalCache<TemplateResource, Object> _portalCache;
	private int _resourceModificationCheckInterval = 60;
	private TemplateResourceLoader _templateResourceLoader;

	private static class LiferayTemplate extends Template {

		public LiferayTemplate(TemplateResource templateResource) {
			_templateResource = templateResource;
		}

		@Override
		public boolean process() throws IOException, ParseErrorException {
			data = null;

			try (Reader reader = _templateResource.getReader()) {
				data = rsvc.parse(reader, name);

				initDocument();

				return true;
			}
			catch (Exception e) {
				ParseErrorException pee = new ParseErrorException(
					"Unable to parse Velocity template");

				pee.addSuppressed(e);

				throw pee;
			}
		}

		private final TemplateResource _templateResource;

	}

}