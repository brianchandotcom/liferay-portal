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

package com.liferay.document.library.google.docs.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.StringPool;

import freemarker.cache.ClassTemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Zaera
 */
public class FreeMarkerRenderer {

	public FreeMarkerRenderer(String templatePath) throws IOException {
		Configuration configuration = new Configuration();

		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setTemplateLoader(
			new ClassTemplateLoader(
				FreeMarkerRenderer.class, StringPool.SLASH));
		configuration.setTemplateUpdateDelay(Integer.MAX_VALUE);

		_template = configuration.getTemplate(templatePath);
	}

	public String render() throws IOException, TemplateException {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		_template.process(_attributes, unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	public void setAttribute(String key, Object value) {
		_attributes.put(key, value);
	}

	private final Map<String, Object> _attributes = new HashMap<>();
	private final Template _template;

}