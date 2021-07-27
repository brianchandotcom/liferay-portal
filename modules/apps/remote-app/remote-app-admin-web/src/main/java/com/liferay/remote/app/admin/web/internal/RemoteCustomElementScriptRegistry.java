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

package com.liferay.remote.app.admin.web.internal;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.remote.app.model.RemoteCustomElementEntry;
import com.liferay.remote.app.service.RemoteCustomElementEntryLocalService;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = RemoteCustomElementScriptRegistry.class)
public class RemoteCustomElementScriptRegistry {

	public Collection<String> getCustomElementScriptURLs() {
		return _customElementScriptsURLs;
	}

	public void register(RemoteCustomElementEntry remoteCustomElementEntry) {
		_customElementScriptsMap.put(
			remoteCustomElementEntry.getTagName(),
			remoteCustomElementEntry.getUrl());
	}

	public void unregister(RemoteCustomElementEntry remoteCustomElementEntry) {
		_customElementScriptsMap.remove(remoteCustomElementEntry.getTagName());
	}

	public void unregister(String tagName) {
		_customElementScriptsMap.remove(tagName);
	}

	@Activate
	protected void activate() {
		if (_log.isInfoEnabled()) {
			_log.info("Starting remote custom element entries");
		}

		for (RemoteCustomElementEntry remoteCustomElementEntry :
				_remoteCustomElementEntryLocalService.
					getRemoteCustomElementEntries(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			register(remoteCustomElementEntry);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info("Stopping remote custom element entries");
		}

		for (String tagName : _customElementScriptsMap.keySet()) {
			unregister(tagName);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteCustomElementScriptRegistry.class);

	private final Map<String, String> _customElementScriptsMap =
		new ConcurrentHashMap<>();
	private final Collection<String> _customElementScriptsURLs =
		Collections.unmodifiableCollection(_customElementScriptsMap.values());

	@Reference
	private RemoteCustomElementEntryLocalService
		_remoteCustomElementEntryLocalService;

}