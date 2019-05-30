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

package com.liferay.exportimport.changeset.internal.manager;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.ChangesetEnvironment;
import com.liferay.exportimport.changeset.ChangesetManager;
import com.liferay.exportimport.changeset.constants.ChangesetPortletKeys;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactory;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = ChangesetManager.class)
public class ChangesetManagerImpl implements ChangesetManager {

	@Override
	public void addChangeset(Changeset changeset) {
		Objects.nonNull(changeset);

		String changesetUuid = changeset.getUuid();

		if (_changesets.containsKey(changesetUuid)) {
			return;
		}

		_changesets.put(changesetUuid, changeset);
	}

	@Override
	public void clearChangesets() {
		_changesets = new HashMap<>();
	}

	@Override
	public boolean hasChangeset(String changesetUuid) {
		return _changesets.containsKey(changesetUuid);
	}

	@Override
	public Optional<Changeset> peekChangeset(String changesetUuid) {
		return Optional.ofNullable(_changesets.get(changesetUuid));
	}

	@Override
	public Optional<Changeset> popChangeset(String changesetUuid) {
		Changeset changeset = _changesets.remove(changesetUuid);

		return Optional.ofNullable(changeset);
	}

	@Override
	public long publishChangeset(
		Changeset changeset, ChangesetEnvironment changesetEnvironment) {

		addChangeset(changeset);

		long groupId = changesetEnvironment.getGroupId();

		Group scopeGroup = _groupLocalService.fetchGroup(groupId);

		if (!scopeGroup.isStagingGroup() && !scopeGroup.isStagedRemotely()) {
			return 0;
		}

		String portletId = changesetEnvironment.getPortletId();

		if (!scopeGroup.isStagedPortlet(portletId)) {
			return 0;
		}

		long liveGroupId = 0;

		if (scopeGroup.isStagingGroup()) {
			liveGroupId = scopeGroup.getLiveGroupId();
		}
		else if (scopeGroup.isStagedRemotely()) {
			liveGroupId = scopeGroup.getRemoteLiveGroupId();
		}

		Map<String, String[]> parameterMap =
			_exportImportConfigurationParameterMapFactory.buildParameterMap();

		parameterMap.put("changesetUuid", new String[] {changeset.getUuid()});

		Map<String, String> envParameterMap =
			changesetEnvironment.getParameterMap();

		Set<Map.Entry<String, String>> entrySet = envParameterMap.entrySet();

		Stream<Map.Entry<String, String>> entryStream = entrySet.stream();

		entryStream.forEach(
			entry -> parameterMap.put(
				entry.getKey(), new String[] {entry.getValue()}));

		User user = _userLocalService.fetchUser(
			changesetEnvironment.getUserId());

		Map<String, Serializable> settingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildPublishPortletSettingsMap(
					user, groupId, changesetEnvironment.getPlid(), liveGroupId,
					changesetEnvironment.getPlid(),
					ChangesetPortletKeys.CHANGESET, parameterMap);

		try {
			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(), portletId,
						ExportImportConfigurationConstants.TYPE_PUBLISH_PORTLET,
						settingsMap);

			return _staging.publishPortlet(
				user.getUserId(), exportImportConfiguration);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to publish changeset: " + pe.getMessage());
			}

			return 0;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetManagerImpl.class);

	private Map<String, Changeset> _changesets = new HashMap<>();

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportConfigurationParameterMapFactory
		_exportImportConfigurationParameterMapFactory;

	@Reference
	private ExportImportConfigurationSettingsMapFactory
		_exportImportConfigurationSettingsMapFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Staging _staging;

	@Reference
	private UserLocalService _userLocalService;

}