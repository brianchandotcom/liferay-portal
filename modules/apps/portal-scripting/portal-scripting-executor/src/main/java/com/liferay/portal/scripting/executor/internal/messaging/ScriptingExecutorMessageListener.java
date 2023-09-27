/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scripting.executor.internal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.scripting.executor.internal.constants.ScriptingExecutorMessagingConstants;

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
					currentThread.setContextClassLoader(
						AggregateClassLoader.getAggregateClassLoader(
							contextClassLoader, bundleClassLoader));
				}

				_scripting.exec(
					null, new HashMap<String, Object>(), scriptingLanguage,
					StringUtil.read(inputStream));
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to execute script " + url.getFile(), exception);
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