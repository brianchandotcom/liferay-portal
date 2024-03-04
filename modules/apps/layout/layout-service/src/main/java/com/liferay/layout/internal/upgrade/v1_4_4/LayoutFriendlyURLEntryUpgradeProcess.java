/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_4_4;

import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutFriendlyURLEntryUpgradeProcess extends UpgradeProcess {

	public LayoutFriendlyURLEntryUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		FriendlyURLEntryLocalService friendlyURLEntryLocalService,
		ResourceActions resourceActions) {

		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;

		_privateLayoutClassNameId = classNameLocalService.getClassNameId(
			resourceActions.getCompositeModelName(
				Layout.class.getName(), Boolean.TRUE.toString()));
		_publicLayoutClassNameId = classNameLocalService.getClassNameId(
			resourceActions.getCompositeModelName(
				Layout.class.getName(), Boolean.FALSE.toString()));
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String sql = StringBundler.concat(
				"select distinct LayoutFriendlyURL.groupId, ",
				"LayoutFriendlyURL.plid, LayoutFriendlyURL.privateLayout, ",
				"CASE WHEN LayoutFriendlyURL.privateLayout = [$TRUE$] THEN ",
				_privateLayoutClassNameId, " ELSE ", _publicLayoutClassNameId,
				" END as classNameId from LayoutFriendlyURL left join ",
				"FriendlyURLEntryLocalization on ",
				"(FriendlyURLEntryLocalization.languageId = ",
				"LayoutFriendlyURL.languageId and ",
				"FriendlyURLEntryLocalization.urlTitle = ",
				"LayoutFriendlyURL.friendlyURL and ",
				"FriendlyURLEntryLocalization.groupId = ",
				"LayoutFriendlyURL.groupId and ",
				"FriendlyURLEntryLocalization.classNameId = classNameId and ",
				"FriendlyURLEntryLocalization.classPK = ",
				"LayoutFriendlyURL.plid) where ",
				"FriendlyURLEntryLocalization.friendlyURLEntryLocalizationId ",
				"is null");

			processConcurrently(
				SQLTransformer.transform(sql),
				resultSet -> new Object[] {
					resultSet.getLong("groupId"), resultSet.getLong("plid"),
					resultSet.getBoolean("privateLayout"),
					resultSet.getLong("classNameId")
				},
				values -> {
					long groupId = (Long)values[0];
					long plid = (Long)values[1];
					boolean privateLayout = (Boolean)values[2];
					long classNameId = (Long)values[3];

					try {
						_friendlyURLEntryLocalService.addFriendlyURLEntry(
							groupId, classNameId, plid,
							_getFriendlyURLMap(groupId, plid, privateLayout),
							new ServiceContext());
					}
					catch (Exception exception) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Unable to add friendly URL entry for ",
									"PLID ", plid, " in group ", groupId),
								exception);
						}
					}
				},
				"Unable to create friendly URL entries for layout friendly " +
					"URLs");
		}
	}

	private Map<String, String> _getFriendlyURLMap(
			long groupId, long plid, boolean privateLayout)
		throws Exception {

		Map<String, String> friendlyURLMap = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select friendlyURL, languageId from LayoutFriendlyURL where " +
					"groupId = ? and plid = ? and privateLayout = ? ")) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, plid);
			preparedStatement.setBoolean(3, privateLayout);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					friendlyURLMap.put(
						resultSet.getString("languageId"),
						resultSet.getString("friendlyURL"));
				}
			}
		}

		return friendlyURLMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutFriendlyURLEntryUpgradeProcess.class);

	private final FriendlyURLEntryLocalService _friendlyURLEntryLocalService;
	private final long _privateLayoutClassNameId;
	private final long _publicLayoutClassNameId;

}