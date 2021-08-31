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

package com.liferay.web.hook.internal.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.web.hook.model.WebHookEntry;

import java.net.HttpURLConnection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Eduardo García
 */
public class WebHookMessageListener extends BaseMessageListener {

	public WebHookMessageListener(WebHookEntry webHookEntry) {
		_webHookEntry = webHookEntry;
	}

	public synchronized void register(BundleContext bundleContext) {
		if (_serviceRegistration != null) {
			throw new IllegalStateException(
				"Message Listener is already registered");
		}

		_serviceRegistration = bundleContext.registerService(
			MessageListener.class, this,
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", _webHookEntry.getDestination()
			).build());
	}

	public synchronized void unregister() {
		if (_serviceRegistration == null) {
			throw new IllegalStateException(
				"Message Listener is not registered");
		}

		_serviceRegistration.unregister();

		_serviceRegistration = null;
	}

	@Override
	protected void doReceive(Message message) {
		long companyId = message.getLong("companyId");

		if (_webHookEntry.getCompanyId() != companyId) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Processing message " + message);
		}

		try {
			Http.Options options = new Http.Options();

			options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
			options.setBody(
				message.toString(), ContentTypes.APPLICATION_JSON,
				StringPool.UTF8);
			options.setLocation(_webHookEntry.getUrl());
			options.setPost(true);

			String responseJSON = HttpUtil.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new PortalException(
					StringBundler.concat(
						"Response code ", response.getResponseCode(), ": ",
						responseJSON));
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to send message to url " + _webHookEntry.getUrl(),
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebHookMessageListener.class);

	private ServiceRegistration<MessageListener> _serviceRegistration;
	private final WebHookEntry _webHookEntry;

}