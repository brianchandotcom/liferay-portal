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
import com.liferay.remote.app.admin.web.internal.portlet.RemoteAppPortlet;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

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
@Component(immediate = true, service = RemoteAppPortletRegistrar.class)
public class RemoteAppPortletRegistrar {

	public void registerPortlet(RemoteAppEntry remoteAppEntry) {
		_registerPortlet(remoteAppEntry);
	}

	public void unregisterPortlet(RemoteAppEntry remoteAppEntry) {
		_unregisterPortlet(remoteAppEntry.getEntryId());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		if (_log.isInfoEnabled()) {
			_log.info("Starting remote apps");
		}

		for (RemoteAppEntry remoteAppEntry :
				remoteAppEntryLocalService.getRemoteAppEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			registerPortlet(remoteAppEntry);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info("Stopping remote apps");
		}

		Collection<RemoteAppPortlet> remoteAppPortlets;

		synchronized (_remoteAppPortlets) {
			remoteAppPortlets = _remoteAppPortlets.values();

			_remoteAppPortlets.clear();
		}

		for (RemoteAppPortlet remoteAppPortlet : remoteAppPortlets) {
			remoteAppPortlet.unregister();
		}
	}

	@Reference
	protected RemoteAppEntryLocalService remoteAppEntryLocalService;

	private void _registerPortlet(RemoteAppEntry remoteAppEntry) {
		RemoteAppPortlet remoteAppPortlet = new RemoteAppPortlet(
			remoteAppEntry);

		long remoteAppEntryId = remoteAppEntry.getEntryId();

		synchronized (_remoteAppPortlets) {
			if (_remoteAppPortlets.containsKey(remoteAppEntryId)) {
				throw new IllegalStateException(
					"Remote app " + remoteAppEntryId +
						" is already registered.");
			}

			_remoteAppPortlets.put(remoteAppEntryId, remoteAppPortlet);

			remoteAppPortlet.register(_bundleContext);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Started remote app " + remoteAppPortlet.getName());
		}
	}

	private void _unregisterPortlet(long id) {
		RemoteAppPortlet remoteAppPortlet;

		synchronized (_remoteAppPortlets) {
			remoteAppPortlet = _remoteAppPortlets.remove(id);
		}

		if (remoteAppPortlet != null) {
			remoteAppPortlet.unregister();

			if (_log.isInfoEnabled()) {
				_log.info("Stopped remote app " + remoteAppPortlet.getName());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteAppPortletRegistrar.class);

	private BundleContext _bundleContext;
	private final Map<Long, RemoteAppPortlet> _remoteAppPortlets =
		new HashMap<>();

}