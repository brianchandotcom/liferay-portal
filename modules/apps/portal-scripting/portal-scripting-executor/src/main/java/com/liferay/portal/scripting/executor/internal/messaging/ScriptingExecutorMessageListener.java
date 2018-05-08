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

package com.liferay.portal.scripting.executor.internal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.scripting.executor.internal.ScriptingExecutorMessagingConstants;

import java.io.InputStream;

import java.net.URL;

import java.util.HashMap;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class ScriptingExecutorMessageListener extends BaseMessageListener {

	public ScriptingExecutorMessageListener(Scripting scripting) {
		_scripting = scripting;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		String scriptingLanguage = message.getString(
			ScriptingExecutorMessagingConstants.MESSAGE_KEY_SCRIPTING_LANGUAGE);

		List<URL> urls = (List<URL>)message.get(
			ScriptingExecutorMessagingConstants.MESSAGE_KEY_URLS);

		for (URL url : urls) {
			try (InputStream inputStream = url.openStream()) {
				ClassLoader bundleClassLoader = (ClassLoader)message.get(
					ScriptingExecutorMessagingConstants.
						MESSAGE_KEY_BUNDLE_CLASS_LOADER);

				if (bundleClassLoader != null) {
					ClassLoader aggregateClassLoader =
						AggregateClassLoader.getAggregateClassLoader(
							contextClassLoader, bundleClassLoader);

					currentThread.setContextClassLoader(aggregateClassLoader);
				}

				_scripting.exec(
					null, new HashMap<String, Object>(), scriptingLanguage,
					StringUtil.read(inputStream));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to execute script " + url.getFile(), e);
				}
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ScriptingExecutorMessageListener.class);

	private final Scripting _scripting;

}