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

package com.liferay.journal.internal.upgrade.v1_1_6;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Vendel Toreki
 */
public class UpgradeAssetDisplayPageEntry extends UpgradeProcess {

	public UpgradeAssetDisplayPageEntry(
		AssetDisplayPageEntryLocalService assetDisplayPageEntryLocalService,
		CompanyLocalService companyLocalService) {

		_assetDisplayPageEntryLocalService = assetDisplayPageEntryLocalService;
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			updateAssetDisplayPageEntry(company);
		}
	}

	protected void updateAssetDisplayPageEntry(Company company)
		throws Exception {

		_initGroupIdMaps(company.getCompanyId());

		StringBuilder sb = new StringBuilder(15);

		sb.append("select JournalArticle.groupId, ");
		sb.append("JournalArticle.resourcePrimKey, AssetEntry.classUuid from ");
		sb.append("JournalArticle inner join AssetEntry on ( ");
		sb.append("AssetEntry.classNameId = ? and AssetEntry.classPK = ");
		sb.append("JournalArticle.resourcePrimKey) where ");
		sb.append("JournalArticle.companyId = ? and ");
		sb.append("JournalArticle.layoutUuid is not null and ");
		sb.append("JournalArticle.layoutUuid != '' and not exists (select ");
		sb.append("1 from AssetDisplayPageEntry where ");
		sb.append("AssetDisplayPageEntry.groupId = JournalArticle.groupId ");
		sb.append("and AssetDisplayPageEntry.classNameId = ? and ");
		sb.append("AssetDisplayPageEntry.classPK = ");
		sb.append("JournalArticle.resourcePrimKey) group by ");
		sb.append("JournalArticle.groupId, JournalArticle.resourcePrimKey, ");
		sb.append("AssetEntry.classUuid ");

		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);
		User user = company.getDefaultUser();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				SQLTransformer.transform(sb.toString()))) {

			ps1.setLong(1, journalArticleClassNameId);
			ps1.setLong(2, company.getCompanyId());
			ps1.setLong(3, journalArticleClassNameId);

			List<SaveAssetDisplayPageEntryCallable>
				saveAssetDisplayPageEntryCallables = new ArrayList<>();

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long resourcePrimKey = rs.getLong("resourcePrimKey");
					String journalArticleUuid = rs.getString("classUuid");

					SaveAssetDisplayPageEntryCallable
						saveAssetDisplayPageEntryCallable =
							new SaveAssetDisplayPageEntryCallable(
								groupId, user.getUserId(),
								journalArticleClassNameId, resourcePrimKey,
								_generateLocalStagingAwareUUID(
									groupId, journalArticleUuid));

					saveAssetDisplayPageEntryCallables.add(
						saveAssetDisplayPageEntryCallable);
				}
			}

			ExecutorService executorService = Executors.newWorkStealingPool();

			List<Future<Boolean>> futures = executorService.invokeAll(
				saveAssetDisplayPageEntryCallables);

			executorService.shutdown();

			for (Future<Boolean> future : futures) {
				boolean success = GetterUtil.get(future.get(), true);

				if (!success) {
					throw new UpgradeException(
						"Unable to add asset display pages for the journal " +
							"articles");
				}
			}
		}
	}

	private String _generateLocalStagingAwareUUID(
		long groupId, String journalArticleUuid) {

		if (!_stagedGroupIds.contains(groupId)) {
			return PortalUUIDUtil.generate();
		}

		long liveGroupId = groupId;

		if (_stagingToLiveGroupIdMap.containsKey(groupId)) {
			liveGroupId = _stagingToLiveGroupIdMap.get(groupId);
		}

		if (!_uuidsByGroupsMap.containsKey(liveGroupId)) {
			_uuidsByGroupsMap.put(liveGroupId, new HashMap<>());
		}

		Map<String, String> uuids = _uuidsByGroupsMap.get(liveGroupId);

		if (uuids.containsKey(journalArticleUuid)) {
			return uuids.get(journalArticleUuid);
		}

		String newUuid = PortalUUIDUtil.generate();

		uuids.put(journalArticleUuid, newUuid);

		return newUuid;
	}

	private void _initGroupIdMaps(long companyId) throws Exception {
		_stagedGroupIds.clear();
		_stagingToLiveGroupIdMap.clear();
		_uuidsByGroupsMap.clear();

		StringBuilder sb = new StringBuilder(3);

		sb.append("select groupId, liveGroupId from Group_ where ");
		sb.append("companyId = ? and liveGroupId is not null and ");
		sb.append("liveGroupId != 0 and remoteStagingGroupCount = 0");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				SQLTransformer.transform(sb.toString()))) {

			ps.setLong(1, companyId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long liveGroupId = rs.getLong("liveGroupId");

					_stagingToLiveGroupIdMap.put(groupId, liveGroupId);
					_stagedGroupIds.add(groupId);
					_stagedGroupIds.add(liveGroupId);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeAssetDisplayPageEntry.class);

	private final AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;
	private final CompanyLocalService _companyLocalService;
	private Set<Long> _stagedGroupIds = new HashSet<>();
	private Map<Long, Long> _stagingToLiveGroupIdMap = new HashMap<>();
	private Map<Long, Map<String, String>> _uuidsByGroupsMap = new HashMap<>();

	private class SaveAssetDisplayPageEntryCallable
		implements Callable<Boolean> {

		public SaveAssetDisplayPageEntryCallable(
			long groupId, long userId, long classNameId, long classPK,
			String uuid) {

			_groupId = groupId;
			_userId = userId;
			_classNameId = classNameId;
			_classPK = classPK;
			_uuid = uuid;
		}

		@Override
		public Boolean call() throws Exception {
			try {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setUuid(_uuid);

				_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
					_userId, _groupId, _classNameId, _classPK, 0,
					AssetDisplayPageConstants.TYPE_SPECIFIC, serviceContext);
			}
			catch (Exception e) {
				_log.error(
					"Unable to add asset display page entry for article " +
						_classPK,
					e);

				return false;
			}

			return true;
		}

		private final long _classNameId;
		private final long _classPK;
		private final long _groupId;
		private final long _userId;
		private final String _uuid;

	}

}