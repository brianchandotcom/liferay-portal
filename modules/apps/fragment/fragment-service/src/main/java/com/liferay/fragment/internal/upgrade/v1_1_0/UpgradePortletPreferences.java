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
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ricardo Couso
 */
public class UpgradePortletPreferences extends UpgradeProcess {

	public UpgradePortletPreferences(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
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
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select groupId, companyId, classPK, namespace from " +
					"FragmentEntryLink");
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"delete from PortletPreferences where " +
						"portletPreferencesId = ?"));
			PreparedStatement ps3 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update PortletPreferences set plid = ? where " +
						"portletPreferencesId = ?"));
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long classPK = rs.getLong("classPK");
				String namespace = rs.getString("namespace");

				try {
					Map<Long, Long> portletPreferencesMap =
						_getPortletPreferencesMap(
							companyId, groupId, namespace);

					if (portletPreferencesMap.isEmpty()) {
						continue;
					}

					Long companyPortletPreferencesId = null;
					Long groupPortletPreferencesId = null;
					Long layoutPortletPreferencesId = null;

					long companyControlPanelPlid =
						_companyControlPanelPlids.get(companyId);
					long groupControlPanelPlid = _groupControlPanelPlids.get(
						groupId);
					long layoutPlid = classPK;

					for (Map.Entry<Long, Long> entry :
							portletPreferencesMap.entrySet()) {

						Long portletPreferencesId = entry.getKey();
						Long portletPreferencesPlid = entry.getValue();

						if (portletPreferencesPlid == companyControlPanelPlid) {
							companyPortletPreferencesId = portletPreferencesId;
						}
						else if (portletPreferencesPlid ==
									groupControlPanelPlid) {

							groupPortletPreferencesId = portletPreferencesId;
						}
						else if (portletPreferencesPlid == layoutPlid) {
							layoutPortletPreferencesId = portletPreferencesId;
						}
					}

					if (groupPortletPreferencesId != null) {
						if (companyPortletPreferencesId != null) {
							ps2.setLong(1, companyPortletPreferencesId);
							ps2.addBatch();
						}

						if (layoutPortletPreferencesId != null) {
							ps2.setLong(1, layoutPortletPreferencesId);
							ps2.addBatch();
						}

						ps3.setLong(1, classPK);
						ps3.setLong(2, groupPortletPreferencesId);
						ps3.addBatch();
					}
					else if (companyPortletPreferencesId != null) {
						if (layoutPortletPreferencesId != null) {
							ps2.setLong(1, layoutPortletPreferencesId);
							ps2.addBatch();
						}

						ps3.setLong(1, classPK);
						ps3.setLong(2, companyPortletPreferencesId);
						ps3.addBatch();
					}
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}
			}

			ps2.executeBatch();

			ps3.executeBatch();
		}
	}

	private void _computeControlPanelPlids() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select Layout.plid, Group_.groupKey from Layout inner ");
		sb.append("join Group_ on Layout.groupId = Group_.groupId where ");
		sb.append("Layout.type_ = '");
		sb.append(LayoutConstants.TYPE_CONTROL_PANEL);
		sb.append("'");

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

	private Map<Long, Long> _getPortletPreferencesMap(
			long companyId, long groupId, String namespace)
		throws Exception {

		Map<Long, Long> portletPreferencesMap = new HashMap<>();

		long companyControlPanelPlid = _companyControlPanelPlids.get(companyId);

		StringBundler sb = new StringBundler(11);

		sb.append("select PortletPreferences.portletPreferencesId, ");
		sb.append("PortletPreferences.plid from PortletPreferences inner ");
		sb.append("join Layout on PortletPreferences.plid = Layout.plid ");
		sb.append("where PortletPreferences.portletId like ");
		sb.append("CONCAT('%_INSTANCE_', '");
		sb.append(namespace);
		sb.append("') and (Layout.groupId = ");
		sb.append(groupId);
		sb.append(" or PortletPreferences.plid = ");
		sb.append(companyControlPanelPlid);
		sb.append(")");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				long portletPreferencesPlid = rs.getLong("plid");

				portletPreferencesMap.put(
					portletPreferencesId, portletPreferencesPlid);
			}
		}

		return portletPreferencesMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletPreferences.class);

	private static final Map<Long, Long> _companyControlPanelPlids =
		new HashMap<>();
	private static final Map<Long, Long> _groupControlPanelPlids =
		new HashMap<>();

	private final LayoutLocalService _layoutLocalService;

}