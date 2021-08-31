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

package com.liferay.web.hook.internal;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.web.hook.internal.messaging.WebHookMessageListener;
import com.liferay.web.hook.model.WebHookEntry;
import com.liferay.web.hook.service.WebHookEntryLocalService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = WebHookMessageListenerRegistrar.class)
public class WebHookMessageListenerRegistrar {

	public void registerMessageListener(WebHookEntry webHookEntry) {
		_registerMessageListener(webHookEntry);
	}

	public void unregisterMessageListener(WebHookEntry webHookEntry) {
		_unregisterMessageListener(webHookEntry.getWebHookEntryId());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		if (_log.isInfoEnabled()) {
			_log.info("Registering web hook entries");
		}

		for (WebHookEntry webHookEntry :
				webHookEntryLocalService.getWebHookEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			registerMessageListener(webHookEntry);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info("Unregistering web hook entries");
		}

		for (long webHookEntryId : _webHookMessageListeners.keySet()) {
			_unregisterMessageListener(webHookEntryId);
		}
	}

	@Reference
	protected WebHookEntryLocalService webHookEntryLocalService;

	private void _registerMessageListener(WebHookEntry webHookEntry) {
		WebHookMessageListener webHookMessageListener =
			new WebHookMessageListener(webHookEntry);

		long webHookEntryId = webHookEntry.getWebHookEntryId();

		WebHookMessageListener existingWebHookMessageListener =
			_webHookMessageListeners.putIfAbsent(
				webHookEntryId, webHookMessageListener);

		if (existingWebHookMessageListener != null) {
			throw new IllegalStateException(
				"Web hook entry " + webHookEntryId + " is already registered");
		}

		webHookMessageListener.register(_bundleContext);

		if (_log.isInfoEnabled()) {
			_log.info("Registered web hook entry " + webHookEntryId);
		}
	}

	private void _unregisterMessageListener(long webHookEntryId) {
		WebHookMessageListener webHookMessageListener =
			_webHookMessageListeners.remove(webHookEntryId);

		if (webHookMessageListener != null) {
			webHookMessageListener.unregister();

			if (_log.isInfoEnabled()) {
				_log.info("Unregistered web hook entry " + webHookEntryId);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebHookMessageListenerRegistrar.class);

	private BundleContext _bundleContext;
	private final ConcurrentMap<Long, WebHookMessageListener>
		_webHookMessageListeners = new ConcurrentHashMap<>();

}