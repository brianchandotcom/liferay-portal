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

package com.liferay.web.hook.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.web.hook.internal.WebHookMessageListenerRegistrar;
import com.liferay.web.hook.model.WebHookEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = ModelListener.class)
public class WebHookEntryModelListener extends BaseModelListener<WebHookEntry> {

	@Override
	public void onAfterCreate(WebHookEntry webHookEntry) {
		_webHookMessageListenerRegistrar.registerMessageListener(webHookEntry);
	}

	@Override
	public void onAfterUpdate(WebHookEntry webHookEntry) {
		_webHookMessageListenerRegistrar.unregisterMessageListener(
			webHookEntry);

		_webHookMessageListenerRegistrar.registerMessageListener(webHookEntry);
	}

	@Override
	public void onBeforeRemove(WebHookEntry webHookEntry)
		throws ModelListenerException {

		_webHookMessageListenerRegistrar.unregisterMessageListener(
			webHookEntry);
	}

	@Reference
	private WebHookMessageListenerRegistrar _webHookMessageListenerRegistrar;

}