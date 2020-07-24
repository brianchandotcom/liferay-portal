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

package com.liferay.remote.web.app.web.internal;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.remote.web.app.model.RemoteWebAppEntry;
import com.liferay.remote.web.app.service.RemoteWebAppEntryLocalService;
import com.liferay.remote.web.app.web.internal.portlet.RemoteWebAppPortlet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = RemoteWebAppPortletRegistrar.class)
public class RemoteWebAppPortletRegistrar {

	public void registerPortlet(RemoteWebAppEntry remoteWebAppEntry) {
		_registerPortlet(remoteWebAppEntry);
	}

	public void unregisterPortlet(RemoteWebAppEntry remoteWebAppEntry) {
		_unregisterPortlet(remoteWebAppEntry.getEntryId());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		if (_log.isInfoEnabled()) {
			_log.info("Starting remote web apps");
		}

		for (RemoteWebAppEntry remoteWebAppEntry :
				remoteWebAppEntryLocalService.getRemoteWebAppEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			registerPortlet(remoteWebAppEntry);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info("Stopping all remote web apps");
		}

		Collection<RemoteWebAppPortlet> remoteWebAppPortlets;

		synchronized (_remoteWebAppPortlets) {
			remoteWebAppPortlets = _remoteWebAppPortlets.values();

			_remoteWebAppPortlets.clear();
		}

		for (RemoteWebAppPortlet remoteWebAppPortlet : remoteWebAppPortlets) {
			remoteWebAppPortlet.unregister();
		}
	}

	@Reference
	protected RemoteWebAppEntryLocalService remoteWebAppEntryLocalService;

	private void _registerPortlet(RemoteWebAppEntry remoteWebAppEntry) {
		RemoteWebAppPortlet remoteWebAppPortlet = new RemoteWebAppPortlet(
			remoteWebAppEntry);

		long entryId = remoteWebAppEntry.getEntryId();

		synchronized (_remoteWebAppPortlets) {
			if (_remoteWebAppPortlets.containsKey(entryId)) {
				throw new IllegalStateException(
					"Portlet " + entryId + " is already registered");
			}

			_remoteWebAppPortlets.put(entryId, remoteWebAppPortlet);

			remoteWebAppPortlet.register(_bundleContext);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Started remote web app " + remoteWebAppPortlet.getName());
		}
	}

	private void _unregisterPortlet(long id) {
		RemoteWebAppPortlet remoteWebAppPortlet;

		synchronized (_remoteWebAppPortlets) {
			remoteWebAppPortlet = _remoteWebAppPortlets.remove(id);
		}

		if (remoteWebAppPortlet != null) {
			remoteWebAppPortlet.unregister();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Stopped remote web app " + remoteWebAppPortlet.getName());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteWebAppPortletRegistrar.class);

	private BundleContext _bundleContext;
	private final Map<Long, RemoteWebAppPortlet> _remoteWebAppPortlets =
		new HashMap<>();

}