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

package com.liferay.fragment.internal.upgrade.v1_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ricardo Couso
 */
public class UpgradePortletPreferences extends UpgradeProcess {

	public UpgradePortletPreferences(
		LayoutLocalService layoutLocalService,
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_layoutLocalService = layoutLocalService;
		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	protected void deleteGroupControlPanelLayouts() throws PortalException {
		for (Long groupControlPanelLayoutPlid :
				_groupControlPanelPlids.values()) {

			_layoutLocalService.deleteLayout(groupControlPanelLayoutPlid);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		_computeControlPanelPlids();

		if (_groupControlPanelPlids.isEmpty()) {
			return;
		}

		upgradePortletPreferences();

		deleteGroupControlPanelLayouts();
	}

	protected void upgradePortletPreferences() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select classPK, companyId, groupId, namespace from " +
					"FragmentEntryLink;");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long classPK = rs.getLong("classPK");
				long companyId = rs.getLong("companyId");
				long groupId = rs.getLong("groupId");
				String namespace = rs.getString("namespace");

				try {
					List<PortletPreferences> portletPreferencesList =
						_getPortletPreferencesList(
							companyId, groupId, namespace);

					_processPortletPreferencesList(
						companyId, groupId, classPK, portletPreferencesList);
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}
			}
		}
	}

	private void _computeControlPanelPlids() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select Layout.plid, Group_.groupKey from Layout inner ");
		sb.append("join Group_ on Layout.groupId = Group_.groupId where ");
		sb.append("Layout.type_ = '");
		sb.append(LayoutConstants.TYPE_CONTROL_PANEL);
		sb.append("';");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String groupKey = rs.getString("groupKey");

				long plid = rs.getLong("plid");

				Layout layout = _layoutLocalService.getLayout(plid);

				if (groupKey.equals(GroupConstants.CONTROL_PANEL)) {
					_companyControlPanelPlids.put(layout.getCompanyId(), plid);
				}
				else {
					_groupControlPanelPlids.put(layout.getGroupId(), plid);
				}
			}
		}
	}

	private void _deleteIfNotNull(PortletPreferences portletPreferences) {
		if (portletPreferences != null) {
			_portletPreferencesLocalService.deletePortletPreferences(
				portletPreferences);
		}
	}

	private List<PortletPreferences> _getPortletPreferencesList(
			long companyId, long groupId, String namespace)
		throws Exception {

		List<PortletPreferences> portletPreferencesList = new ArrayList<>();

		long companyControlPanelPlid = _companyControlPanelPlids.get(companyId);

		StringBundler sb = new StringBundler(10);

		sb.append("select PortletPreferences.portletPreferencesId from ");
		sb.append("PortletPreferences inner join Layout on ");
		sb.append("PortletPreferences.plid = Layout.plid where ");
		sb.append("PortletPreferences.portletId like CONCAT('%_INSTANCE_', '");
		sb.append(namespace);
		sb.append("') and (Layout.groupId = ");
		sb.append(groupId);
		sb.append(" or PortletPreferences.plid = ");
		sb.append(companyControlPanelPlid);
		sb.append(");");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");

				portletPreferencesList.add(
					_portletPreferencesLocalService.getPortletPreferences(
						portletPreferencesId));
			}
		}

		return portletPreferencesList;
	}

	private void _processPortletPreferencesList(
		long companyId, long groupId, long classPK,
		List<PortletPreferences> portletPreferencesList) {

		if (portletPreferencesList.isEmpty()) {
			return;
		}

		PortletPreferences companyPortletPreferences = null;
		PortletPreferences groupPortletPreferences = null;
		PortletPreferences layoutPortletPreferences = null;

		long companyControlPanelPlid = _companyControlPanelPlids.get(companyId);
		long groupControlPanelPlid = _groupControlPanelPlids.get(groupId);
		long layoutPlid = classPK;

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			if (portletPreferences.getPlid() == companyControlPanelPlid) {
				companyPortletPreferences = portletPreferences;
			}
			else if (portletPreferences.getPlid() == groupControlPanelPlid) {
				groupPortletPreferences = portletPreferences;
			}
			else if (portletPreferences.getPlid() == layoutPlid) {
				layoutPortletPreferences = portletPreferences;
			}
		}

		if (groupPortletPreferences != null) {
			_deleteIfNotNull(companyPortletPreferences);
			_deleteIfNotNull(layoutPortletPreferences);
			_updatePlid(groupPortletPreferences, classPK);
		}
		else if (companyPortletPreferences != null) {
			_deleteIfNotNull(layoutPortletPreferences);
			_updatePlid(companyPortletPreferences, classPK);
		}
	}

	private void _updatePlid(PortletPreferences portletPreferences, long plid) {
		portletPreferences.setPlid(plid);

		_portletPreferencesLocalService.updatePortletPreferences(
			portletPreferences);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletPreferences.class);

	private static final Map<Long, Long> _companyControlPanelPlids =
		new HashMap<>();
	private static final Map<Long, Long> _groupControlPanelPlids =
		new HashMap<>();

	private final LayoutLocalService _layoutLocalService;
	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;

}