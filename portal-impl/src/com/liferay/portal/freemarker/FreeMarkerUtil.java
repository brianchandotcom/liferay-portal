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

package com.liferay.portal.freemarker;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;

import freemarker.cache.ClassTemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;

import java.io.Writer;

/**
 * @author Tariq Dweik
 * @author Brian Wing Shun Chan
 */
public class FreeMarkerUtil {

	public static String process(String name, Object context) throws Exception {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		process(name, context, unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	public static void process(String name, Object context, Writer writer)
		throws Exception {

		Template template = _getConfiguration().getTemplate(name);

		template.process(context, writer);
	}

	private static Configuration _getConfiguration() {
		if (_configuration != null) {
			return _configuration;
		}

		_configuration = new Configuration(Configuration.getVersion());

		DefaultObjectWrapperBuilder defaultObjectWrapperBuilder =
			new DefaultObjectWrapperBuilder(Configuration.getVersion());

		_configuration.setObjectWrapper(defaultObjectWrapperBuilder.build());

		_configuration.setTemplateLoader(
			new ClassTemplateLoader(FreeMarkerUtil.class, StringPool.SLASH));
		_configuration.setTemplateUpdateDelayMilliseconds(Long.MAX_VALUE);

		return _configuration;
	}

	private static Configuration _configuration;

}