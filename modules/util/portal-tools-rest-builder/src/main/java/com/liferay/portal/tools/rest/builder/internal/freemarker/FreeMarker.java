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

package com.liferay.portal.tools.rest.builder.internal.freemarker;

import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;

import freemarker.cache.ClassTemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;

import java.util.Map;

/**
 * @author Peter Shin
 */
public class FreeMarker {

	public FreeMarker() {
		_configuration.setNumberFormat("computer");

		DefaultObjectWrapperBuilder defaultObjectWrapperBuilder =
			new DefaultObjectWrapperBuilder(Configuration.getVersion());

		_configuration.setObjectWrapper(defaultObjectWrapperBuilder.build());

		ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(
			FreeMarker.class, "/");

		_configuration.setTemplateLoader(classTemplateLoader);

		_configuration.setTemplateUpdateDelayMilliseconds(Long.MAX_VALUE);
	}

	public String processTemplate(
			File copyrightFile, String name, Map<String, Object> context)
		throws Exception {

		Template template = _configuration.getTemplate(name);

		StringWriter stringWriter = new StringWriter();

		template.process(context, stringWriter);

		StringBuffer stringBuffer = stringWriter.getBuffer();

		String content = stringBuffer.toString();

		if ((copyrightFile != null) && copyrightFile.exists()) {
			content = FileUtil.read(copyrightFile) + "\n\n" + content;
		}

		return content.replace("\r\n", "\n");
	}

	private static final Configuration _configuration = new Configuration(
		Configuration.getVersion());

}