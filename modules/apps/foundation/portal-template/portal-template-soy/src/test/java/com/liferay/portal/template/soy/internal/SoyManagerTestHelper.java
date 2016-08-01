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

package com.liferay.portal.template.soy.internal;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcellus Tavares
 * @author Bruno Basto
 */
public class SoyManagerTestHelper {

	public Template getTemplate(List<String> fileNames) {
		List<TemplateResource> templateResources = new ArrayList<>();

		for (String fileName : fileNames) {
			templateResources.add(getTemplateResource(fileName));
		}

		return _soyManager.getTemplate(templateResources, false);
	}

	public Template getTemplate(String fileName) {
		TemplateResource templateResource = getTemplateResource(fileName);

		return _soyManager.getTemplate(templateResource, false);
	}

	public void setUp() throws Exception {
		setUpSoyManager();
	}

	public void tearDown() {
		_soyManager.destroy();
	}

	protected TemplateResource getTemplateResource(String name) {
		TemplateResource templateResource = null;

		String resource = _TPL_PATH.concat(name);

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL url = classLoader.getResource(resource);

		if (url != null) {
			templateResource = new URLTemplateResource(resource, url);
		}

		return templateResource;
	}

	protected void setUpSoyManager() throws Exception {
		_soyManager = new SoyManager();

		_soyManager.setTemplateContextHelper(new SoyTemplateContextHelper());

		_soyManager.init();
	}

	private static final String _TPL_PATH =
		"com/liferay/portal/template/soy/dependencies/";

	private SoyManager _soyManager;

}