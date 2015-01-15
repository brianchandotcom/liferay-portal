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

package com.liferay.portal.template.freemarker;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.template.DefaultTemplateResourceLoader;
import com.liferay.portal.template.freemarker.configuration.FreemarkerEngineConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Spasic
 */
@Component(
		configurationPid = "com.liferay.portal.template.freemarker",
		configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
		service = TemplateResourceLoader.class
)
public class FreemarkerTemplateResourceLoader implements TemplateResourceLoader{

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_freemarkerEngineConfiguration = Configurable.createConfigurable(
			FreemarkerEngineConfiguration.class, properties);

		_defaultTemplateResourceLoader = new DefaultTemplateResourceLoader(
			TemplateConstants.LANG_TYPE_FTL,
			_freemarkerEngineConfiguration.templateParsers(),
			_freemarkerEngineConfiguration.resourceModificationCheck());
	}

	@Override
	public void clearCache() {
		_defaultTemplateResourceLoader.clearCache();
	}

	@Override
	public void clearCache(String templateId) {
		_defaultTemplateResourceLoader.clearCache(templateId);
	}

	@Override
	public void destroy() {
		_defaultTemplateResourceLoader.destroy();
	}

	@Override
	public String getName() {
		return _defaultTemplateResourceLoader.getName();
	}

	@Override
	public TemplateResource getTemplateResource(String templateId) {
		return _defaultTemplateResourceLoader.getTemplateResource(templateId);
	}

	@Override
	public boolean hasTemplateResource(String templateId) {
		return _defaultTemplateResourceLoader.hasTemplateResource(templateId);
	}

	@Reference(unbind = "-")
	protected void setMultiVMPoolUtil(MultiVMPoolUtil multiVMPoolUtil) {
		// This reference ensures that MultipVMPoolUtil is ready for use.
	}

	@Reference(unbind = "-")
	protected void setSingleVMPoolUtil(SingleVMPoolUtil singleVMPoolUtil) {
		// This reference ensures that SingleVMPoolUtil is ready for use.
	}

	private static volatile DefaultTemplateResourceLoader
		_defaultTemplateResourceLoader;

	private static volatile FreemarkerEngineConfiguration
		_freemarkerEngineConfiguration;
}