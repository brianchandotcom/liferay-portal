/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.freemarker;

import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;

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