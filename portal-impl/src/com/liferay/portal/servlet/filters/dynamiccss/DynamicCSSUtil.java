/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.dynamiccss;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.scripting.ruby.RubyExecutor;
import com.liferay.util.ContentUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Raymond Augé
 * @author Sergio Sánchez
 */
public class DynamicCSSUtil {

	public static void init() {
		try {
			_rubyScript = ContentUtil.get(
				PortalClassLoaderUtil.getClassLoader(),
				"com/liferay/portal/servlet/filters/dynamiccss/main.rb");
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static String parseSass(String cssRealPath, String content)
		throws ScriptingException {

		if (!DynamicCSSFilter.ENABLED) {
			return content;
		}

		Map<String, Object> inputObjects = new HashMap<String, Object>();

		inputObjects.put("content", content);
		inputObjects.put("cssRealPath", cssRealPath);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		UnsyncPrintWriter unsyncPrintWriter = UnsyncPrintWriterPool.borrow(
			unsyncByteArrayOutputStream);

		inputObjects.put("out", unsyncPrintWriter);

		_rubyExecutor.eval(null, inputObjects, null, _rubyScript);

		unsyncPrintWriter.flush();

		String parsedContent = unsyncByteArrayOutputStream.toString();

		if (Validator.isNotNull(parsedContent)) {
			return parsedContent;
		}

		return content;
	}

	private static Log _log = LogFactoryUtil.getLog(DynamicCSSUtil.class);

	private static RubyExecutor _rubyExecutor = new RubyExecutor();
	private static String _rubyScript;

}