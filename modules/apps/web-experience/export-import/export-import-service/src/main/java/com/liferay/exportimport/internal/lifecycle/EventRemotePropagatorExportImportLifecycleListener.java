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

package com.liferay.exportimport.internal.lifecycle;

import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED;

import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.exportimport.service.http.StagingServiceHttp;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = ExportImportLifecycleListener.class)
public class EventRemotePropagatorExportImportLifecycleListener
	implements ExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	public void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		if (eventNeedsToBePropagated(exportImportLifecycleEvent)) {
			propagateEvent(exportImportLifecycleEvent);
		}
	}

	@Activate
	protected void activate() {
		_propagatedEventTypes.add(EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED);
		_propagatedEventTypes.add(EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED);
		_propagatedEventTypes.add(EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED);
	}

	protected boolean eventNeedsToBePropagated(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		if (!_propagatedEventTypes.contains(
				exportImportLifecycleEvent.getCode())) {

			return false;
		}

		List<Serializable> attributes =
			exportImportLifecycleEvent.getAttributes();

		ExportImportConfiguration exportImportConfiguration =
			(ExportImportConfiguration)attributes.get(0);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long sourceGroupId = MapUtil.getLong(settingsMap, "sourceGroupId");

		Group sourceGroup = _groupLocalService.fetchGroup(sourceGroupId);

		if (Validator.isNull(sourceGroup) || !sourceGroup.isStagedRemotely()) {
			return false;
		}

		long targetGroupId = MapUtil.getLong(settingsMap, "targetGroupId");

		Group targetGroup = _groupLocalService.fetchGroup(targetGroupId);

		UnicodeProperties typeSettings =
			sourceGroup.getTypeSettingsProperties();

		String remoteGroupUUID = typeSettings.getProperty("remoteGroupUUID");

		// If the target group can be found and the UUID's are also match,
		// then we must not propagate the event because it means remote staging
		// is configured between two sites on the same portal instance

		if (Validator.isNotNull(remoteGroupUUID) &&
			Validator.isNotNull(targetGroup) &&
			StringUtil.equals(remoteGroupUUID, targetGroup.getUuid())) {

			return false;
		}

		return true;
	}

	protected Optional<HttpPrincipal> getHttpPrincipal(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = permissionChecker.getUser();

		HttpPrincipal httpPrincipal = null;

		try {
			httpPrincipal = new HttpPrincipal(
				getRemoteURL(exportImportLifecycleEvent), user.getLogin(),
				user.getPassword(), user.getPasswordEncrypted());
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to generate HttpPrincipal to user " +
						user.getFullName(),
					pe);
			}
		}

		return Optional.ofNullable(httpPrincipal);
	}

	protected String getRemoteURL(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		List<Serializable> attributes =
			exportImportLifecycleEvent.getAttributes();

		ExportImportConfiguration exportImportConfiguration =
			(ExportImportConfiguration)attributes.get(0);

		return StagingUtil.buildRemoteURL(exportImportConfiguration);
	}

	protected void propagateEvent(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		getHttpPrincipal(exportImportLifecycleEvent).ifPresent(
			httpPrincipal -> {
				try {
					StagingServiceHttp.propagateExportImportLifecycleEvent(
						httpPrincipal, exportImportLifecycleEvent.getCode(),
						exportImportLifecycleEvent.getProcessFlag(),
						exportImportLifecycleEvent.getProcessId(),
						exportImportLifecycleEvent.getAttributes());
				}
				catch (PortalException pe) {
					_log.error(
						"Unable to propagate staging lifecycle event to the " +
							"remote live site",
						pe);
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EventRemotePropagatorExportImportLifecycleListener.class);

	@Reference
	private GroupLocalService _groupLocalService;

	private final Set<Integer> _propagatedEventTypes = new HashSet<>();

}