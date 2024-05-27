/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.upgrade.v3_4_1;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.model.FriendlyURLEntryMapping;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutFriendlyURLEntryUpgradeProcess extends UpgradeProcess {

	public LayoutFriendlyURLEntryUpgradeProcess(
		ClassNameLocalService classNameLocalService, Portal portal,
		ResourceActions resourceActions) {

		_classNameLocalService = classNameLocalService;
		_portal = portal;
		_resourceActions = resourceActions;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			Map<Long, String> defaultLanguageIds = new ConcurrentHashMap<>();

			String sql = StringBundler.concat(
				"select distinct LayoutFriendlyURL.ctCollectionId, ",
				"LayoutFriendlyURL.groupId, LayoutFriendlyURL.companyId, ",
				"LayoutFriendlyURL.plid, LayoutFriendlyURL.privateLayout, ",
				"CASE WHEN LayoutFriendlyURL.privateLayout = [$TRUE$] THEN ",
				_classNameLocalService.getClassNameId(
					_resourceActions.getCompositeModelName(
						Layout.class.getName(), Boolean.TRUE.toString())),
				" ELSE ",
				_classNameLocalService.getClassNameId(
					_resourceActions.getCompositeModelName(
						Layout.class.getName(), Boolean.FALSE.toString())),
				" END as classNameId from LayoutFriendlyURL left join ",
				"FriendlyURLEntryLocalization on ",
				"(FriendlyURLEntryLocalization.ctCollectionId = ",
				"LayoutFriendlyURL.ctCollectionId and ",
				"FriendlyURLEntryLocalization.languageId = ",
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
				StringBundler.concat(
					"insert into FriendlyURLEntryLocalization (mvccVersion, ",
					"ctCollectionId, friendlyURLEntryLocalizationId, ",
					"companyId, friendlyURLEntryId, languageId, urlTitle, ",
					"groupId, classNameId, classPK) values (?, ?, ?, ?, ?, ?, ",
					"?, ?, ?, ?)"),
				resultSet -> new Object[] {
					resultSet.getLong("ctCollectionId"),
					resultSet.getLong("groupId"),
					resultSet.getLong("companyId"), resultSet.getLong("plid"),
					resultSet.getBoolean("privateLayout"),
					resultSet.getLong("classNameId")
				},
				(values, preparedStatement) -> {
					long ctCollectionId = (Long)values[0];
					long groupId = (Long)values[1];
					long companyId = (Long)values[2];
					long plid = (Long)values[3];
					boolean privateLayout = (Boolean)values[4];
					long classNameId = (Long)values[5];

					try {
						long friendlyURLEntryId =
							_addFriendlyURLEntryIfAbsentAndGetId(
								classNameId, plid, companyId, ctCollectionId,
								defaultLanguageIds, groupId);

						if (friendlyURLEntryId == 0) {
							return;
						}

						Map<String, String> friendlyURLMap = _getFriendlyURLMap(
							companyId, classNameId, ctCollectionId, groupId,
							plid, privateLayout);

						for (Map.Entry<String, String> entry :
								friendlyURLMap.entrySet()) {

							preparedStatement.setLong(1, 0);
							preparedStatement.setLong(2, ctCollectionId);
							preparedStatement.setLong(
								3,
								increment(
									FriendlyURLEntryLocalization.class.
										getName()));
							preparedStatement.setLong(4, companyId);
							preparedStatement.setLong(5, friendlyURLEntryId);
							preparedStatement.setString(6, entry.getKey());
							preparedStatement.setString(7, entry.getValue());
							preparedStatement.setLong(8, groupId);
							preparedStatement.setLong(9, classNameId);
							preparedStatement.setLong(10, plid);

							preparedStatement.executeUpdate();
						}
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

	private long _addFriendlyURLEntryIfAbsentAndGetId(
			long classNameId, long classPK, long companyId, long ctCollectionId,
			Map<Long, String> defaultLanguageIds, long groupId)
		throws Exception {

		long friendlyURLEntryId = _getMappedFriendlyURLEntryId(
			classNameId, classPK, ctCollectionId);

		if (friendlyURLEntryId > 0) {
			return friendlyURLEntryId;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select friendlyURLEntryId from FriendlyURLEntry where ",
					"ctCollectionId = ? and groupId = ? and companyId = ? and ",
					"classNameId = ? and classPK = ?"))) {

			preparedStatement.setLong(1, ctCollectionId);
			preparedStatement.setLong(2, groupId);
			preparedStatement.setLong(3, companyId);
			preparedStatement.setLong(4, classNameId);
			preparedStatement.setLong(5, classPK);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					friendlyURLEntryId = resultSet.getLong(
						"friendlyURLEntryId");
				}
			}
		}

		if (friendlyURLEntryId > 0) {
			_addFriendlyURLEntryMapping(
				classNameId, classPK, ctCollectionId, companyId,
				friendlyURLEntryId);

			return friendlyURLEntryId;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into FriendlyURLEntry (mvccVersion, ",
					"ctCollectionId, uuid_, defaultLanguageId, ",
					"friendlyURLEntryId, groupId, companyId, createDate, ",
					"modifiedDate, classNameId, classPK) values (?, ?, ?, ?, ",
					"?, ?, ?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, ctCollectionId);
			preparedStatement.setString(3, PortalUUIDUtil.generate());

			String defaultLanguageId = defaultLanguageIds.computeIfAbsent(
				groupId, curGroupId -> _getSiteDefaultLocale(curGroupId));

			preparedStatement.setString(4, defaultLanguageId);

			friendlyURLEntryId = increment(FriendlyURLEntry.class.getName());

			preparedStatement.setLong(5, friendlyURLEntryId);

			preparedStatement.setLong(6, groupId);
			preparedStatement.setLong(7, companyId);

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			preparedStatement.setTimestamp(8, timestamp);
			preparedStatement.setTimestamp(9, timestamp);

			preparedStatement.setLong(10, classNameId);
			preparedStatement.setLong(11, classPK);

			preparedStatement.executeUpdate();

			_addFriendlyURLEntryMapping(
				classNameId, classPK, ctCollectionId, companyId,
				friendlyURLEntryId);

			return friendlyURLEntryId;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add friendly URL Entry", exception);
			}
		}

		return 0;
	}

	private void _addFriendlyURLEntryMapping(
			long classNameId, long classPK, long ctCollectionId, long companyId,
			long friendlyURLEntryId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into FriendlyURLEntryMapping (mvccVersion, ",
					"ctCollectionId, friendlyURLEntryMappingId, companyId, ",
					"classNameId, classPK, friendlyURLEntryId) values (?, ?, ",
					"?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, ctCollectionId);
			preparedStatement.setLong(
				3, increment(FriendlyURLEntryMapping.class.getName()));
			preparedStatement.setLong(4, companyId);
			preparedStatement.setLong(5, classNameId);
			preparedStatement.setLong(6, classPK);
			preparedStatement.setLong(7, friendlyURLEntryId);

			preparedStatement.executeUpdate();
		}
	}

	private Map<String, String> _getFriendlyURLMap(
			long companyId, long classNameId, long ctCollectionId, long groupId,
			long plid, boolean privateLayout)
		throws Exception {

		Map<String, String> friendlyURLMap = new HashMap<>();

		String sql = StringBundler.concat(
			"select LayoutFriendlyURL.friendlyURL, ",
			"LayoutFriendlyURL.languageId from LayoutFriendlyURL left join ",
			"FriendlyURLEntryLocalization on ",
			"(FriendlyURLEntryLocalization.ctCollectionId = ",
			"LayoutFriendlyURL.ctCollectionId and ",
			"FriendlyURLEntryLocalization.languageId = ",
			"LayoutFriendlyURL.languageId and ",
			"FriendlyURLEntryLocalization.urlTitle = ",
			"LayoutFriendlyURL.friendlyURL and ",
			"FriendlyURLEntryLocalization.groupId = LayoutFriendlyURL.groupId ",
			"and FriendlyURLEntryLocalization.classNameId = ? and ",
			"FriendlyURLEntryLocalization.classPK = LayoutFriendlyURL.plid) ",
			"where LayoutFriendlyURL.ctCollectionId = ? and ",
			"LayoutFriendlyURL.groupId = ? and LayoutFriendlyURL.companyId = ",
			"? and LayoutFriendlyURL.plid = ? and ",
			"LayoutFriendlyURL.privateLayout = ? and ",
			"FriendlyURLEntryLocalization.friendlyURLEntryLocalizationId is ",
			"null");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(sql))) {

			preparedStatement.setLong(1, classNameId);
			preparedStatement.setLong(2, ctCollectionId);
			preparedStatement.setLong(3, groupId);
			preparedStatement.setLong(4, companyId);
			preparedStatement.setLong(5, plid);
			preparedStatement.setBoolean(6, privateLayout);

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

	private long _getMappedFriendlyURLEntryId(
			long classNameId, long classPK, long ctCollectionId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select friendlyURLEntryId from FriendlyURLEntryMapping " +
					"where ctCollectionId = ? and classNameId = ? and " +
						"classPK = ?")) {

			preparedStatement.setLong(1, ctCollectionId);
			preparedStatement.setLong(2, classNameId);
			preparedStatement.setLong(3, classPK);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("friendlyURLEntryId");
				}
			}
		}

		return 0;
	}

	private String _getSiteDefaultLocale(long groupId) {
		try {
			return LocaleUtil.toLanguageId(
				_portal.getSiteDefaultLocale(groupId));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get default locale group ID " + groupId,
					portalException);
			}

			throw new RuntimeException(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutFriendlyURLEntryUpgradeProcess.class);

	private final ClassNameLocalService _classNameLocalService;
	private final Portal _portal;
	private final ResourceActions _resourceActions;

}