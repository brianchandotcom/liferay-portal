/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.upgrade.v1_4_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutUtilityPageEntryLayoutFriendlyURLUpgradeProcess
	extends UpgradeProcess {

	public LayoutUtilityPageEntryLayoutFriendlyURLUpgradeProcess(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String sql = StringBundler.concat(
			"select Layout.plid, LayoutUtilityPageEntry.name from Layout ",
			"inner join LayoutUtilityPageEntry on ",
			"LayoutUtilityPageEntry.externalReferenceCode like 'LFR-%' and ",
			"LayoutUtilityPageEntry.plid = Layout.plid where ",
			"Layout.friendlyURL = CONCAT('/', CAST_TEXT(Layout.layoutId))");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(sql))) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					_updateLayoutFriendlyURL(
						resultSet.getString("name"), resultSet.getLong("plid"));
				}
			}
		}
	}

	private boolean _existLayoutFriendlyURL(
			long groupId, long plid, boolean privateLayout, String friendlyURL)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select 1 from LayoutFriendlyURL where groupId = ? and plid " +
					"<> ? and privateLayout = ? and friendlyURL = ?")) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, plid);
			preparedStatement.setBoolean(3, privateLayout);
			preparedStatement.setString(4, friendlyURL);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		}
	}

	private void _updateLayoutFriendlyURL(String name, long plid) {
		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			String baseFriendlyURL =
				StringPool.SLASH +
					FriendlyURLNormalizerUtil.normalizeWithEncoding(name);

			String friendlyURL = StringBundler.concat(
				baseFriendlyURL, StringPool.DASH, 1);

			boolean existLayoutFriendlyURL = _existLayoutFriendlyURL(
				layout.getGroupId(), layout.getPlid(), layout.isPrivateLayout(),
				friendlyURL);

			for (int i = 2; existLayoutFriendlyURL; i++) {
				friendlyURL = StringBundler.concat(
					baseFriendlyURL, StringPool.DASH, i);

				existLayoutFriendlyURL = _existLayoutFriendlyURL(
					layout.getGroupId(), layout.getPlid(),
					layout.isPrivateLayout(), friendlyURL);
			}

			_layoutLocalService.updateFriendlyURL(
				layout.getUserId(), layout.getPlid(), friendlyURL,
				layout.getDefaultLanguageId());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutUtilityPageEntryLayoutFriendlyURLUpgradeProcess.class);

	private final LayoutLocalService _layoutLocalService;

}