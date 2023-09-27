/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lifecycle;

import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.constants.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.staging.StagingURLHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.exportimport.service.http.StagingServiceHttp;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(service = ExportImportLifecycleListener.class)
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

		if (_eventNeedsToBePropagated(exportImportLifecycleEvent)) {
			_propagateEvent(exportImportLifecycleEvent);
		}
	}

	@Activate
	protected void activate() {
		_propagatedEventTypes.add(
			ExportImportLifecycleConstants.
				EVENT_PUBLICATION_LAYOUT_REMOTE_FAILED);
		_propagatedEventTypes.add(
			ExportImportLifecycleConstants.
				EVENT_PUBLICATION_LAYOUT_REMOTE_STARTED);
		_propagatedEventTypes.add(
			ExportImportLifecycleConstants.
				EVENT_PUBLICATION_LAYOUT_REMOTE_SUCCEEDED);
	}

	private boolean _eventNeedsToBePropagated(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		if (!_propagatedEventTypes.contains(
				exportImportLifecycleEvent.getCode())) {

			return false;
		}

		ExportImportConfiguration exportImportConfiguration =
			_getExportImportConfiguration(exportImportLifecycleEvent);

		if (exportImportConfiguration == null) {
			return false;
		}

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		long sourceGroupId = GetterUtil.getLong(
			settingsMap.get("sourceGroupId"),
			GroupConstants.ANY_PARENT_GROUP_ID);

		Group sourceGroup = null;

		if (sourceGroupId > 0) {
			sourceGroup = _groupLocalService.fetchGroup(sourceGroupId);
		}

		if ((sourceGroup == null) || !sourceGroup.isStagedRemotely()) {
			return false;
		}

		long targetGroupId = GetterUtil.getLong(
			settingsMap.get("targetGroupId"),
			GroupConstants.ANY_PARENT_GROUP_ID);

		Group targetGroup = null;

		if (targetGroupId > 0) {
			targetGroup = _groupLocalService.fetchGroup(targetGroupId);
		}

		if (targetGroup != null) {
			UnicodeProperties typeSettingsUnicodeProperties =
				sourceGroup.getTypeSettingsProperties();

			String remoteGroupUUID = typeSettingsUnicodeProperties.getProperty(
				"remoteGroupUUID");

			// If the target group can be found and the UUID's also match, then
			// we must not propagate the event because it means remote staging
			// is configured between two sites on the same portal instance

			if (Validator.isNotNull(remoteGroupUUID) &&
				StringUtil.equals(remoteGroupUUID, targetGroup.getUuid())) {

				return false;
			}
		}

		return !Objects.equals(
			GetterUtil.getString(settingsMap.get("remoteAddress")),
			"localhost");
	}

	private ExportImportConfiguration _getExportImportConfiguration(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		List<Serializable> attributes =
			exportImportLifecycleEvent.getAttributes();

		return (ExportImportConfiguration)attributes.get(0);
	}

	private HttpPrincipal _getHttpPrincipal(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		ExportImportConfiguration exportImportConfiguration =
			_getExportImportConfiguration(exportImportLifecycleEvent);

		if (exportImportConfiguration == null) {
			return null;
		}

		User user = _userLocalService.fetchUser(
			MapUtil.getLong(
				exportImportConfiguration.getSettingsMap(), "userId"));

		if (user == null) {
			return null;
		}

		return _getHttpPrincipal(
			user, _getRemoteURL(exportImportLifecycleEvent));
	}

	private HttpPrincipal _getHttpPrincipal(User user, String remoteURL) {
		HttpPrincipal httpPrincipal = null;

		try {
			httpPrincipal = new HttpPrincipal(
				remoteURL, user.getLogin(), user.getPassword(),
				user.isPasswordEncrypted());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to generate HttpPrincipal for user " +
						user.getFullName(),
					portalException);
			}
		}

		return httpPrincipal;
	}

	private String _getRemoteURL(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		List<Serializable> attributes =
			exportImportLifecycleEvent.getAttributes();

		ExportImportConfiguration exportImportConfiguration =
			(ExportImportConfiguration)attributes.get(0);

		return _stagingURLHelper.buildRemoteURL(exportImportConfiguration);
	}

	private void _propagateEvent(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		HttpPrincipal httpPrincipal = _getHttpPrincipal(
			exportImportLifecycleEvent);

		if (httpPrincipal == null) {
			return;
		}

		try {
			StagingServiceHttp.propagateExportImportLifecycleEvent(
				httpPrincipal, exportImportLifecycleEvent.getCode(),
				exportImportLifecycleEvent.getProcessFlag(),
				exportImportLifecycleEvent.getProcessId(),
				exportImportLifecycleEvent.getAttributes());
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to propagate staging lifecycle event to the remote " +
					"live site",
				portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EventRemotePropagatorExportImportLifecycleListener.class);

	@Reference
	private GroupLocalService _groupLocalService;

	private final Set<Integer> _propagatedEventTypes = new HashSet<>();

	@Reference
	private StagingURLHelper _stagingURLHelper;

	@Reference
	private UserLocalService _userLocalService;

}