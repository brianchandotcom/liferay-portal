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

package com.liferay.portal.kernel.template.bundle.templateresourceloaderutil;

import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;

import org.osgi.service.component.annotations.Component;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestTemplateResourceLoader implements TemplateResourceLoader {

	public static final String TEST_TEMPLATE_RESOURCE_LOADER_NAME =
		"TEST_TEMPLATE_RESOURCE_LOADER_NAME";

	@Override
	public void clearCache() {
		return;
	}

	@Override
	public void clearCache(String templateId) {
		return;
	}

	@Override
	public void destroy() {
		return;
	}

	@Override
	public String getName() {
		return TEST_TEMPLATE_RESOURCE_LOADER_NAME;
	}

	@Override
	public TemplateResource getTemplateResource(String templateId) {
		if (templateId.equals(
				TestTemplateResource.TEST_TEMPLATE_RESOURCE_TEMPLATE_ID)) {

			return new TestTemplateResource();
		}

		return null;
	}

	@Override
	public boolean hasTemplateResource(String templateId) {
		if (templateId.equals(
				TestTemplateResource.TEST_TEMPLATE_RESOURCE_TEMPLATE_ID)) {

			return true;
		}

		return false;
	}

}