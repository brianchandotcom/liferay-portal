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
import java.util.List;
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

		StringBuilder sb = new StringBuilder(9);

		sb.append("select groupId, resourcePrimKey from JournalArticle ");
		sb.append("where JournalArticle.companyId = ? and ");
		sb.append("JournalArticle.layoutUuid is not null and ");
		sb.append("JournalArticle.layoutUuid != '' and not exists ( ");
		sb.append("select 1 from AssetDisplayPageEntry where ");
		sb.append("AssetDisplayPageEntry.groupId = JournalArticle.groupId ");
		sb.append("and AssetDisplayPageEntry.classNameId = ? and ");
		sb.append("AssetDisplayPageEntry.classPK = ");
		sb.append("JournalArticle.resourcePrimKey ) ");
		sb.append("group by groupId, resourcePrimKey");

		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);
		User user = company.getDefaultUser();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				SQLTransformer.transform(sb.toString()))) {

			ps1.setLong(1, company.getCompanyId());
			ps1.setLong(2, journalArticleClassNameId);

			List<SaveAssetDisplayPageEntryCallable>
				saveAssetDisplayPageEntryCallables = new ArrayList<>();

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long resourcePrimKey = rs.getLong("resourcePrimKey");

					SaveAssetDisplayPageEntryCallable
						saveAssetDisplayPageEntryCallable =
							new SaveAssetDisplayPageEntryCallable(
								groupId, user.getUserId(),
								journalArticleClassNameId, resourcePrimKey);

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

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeAssetDisplayPageEntry.class);

	private final AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;
	private final CompanyLocalService _companyLocalService;

	private class SaveAssetDisplayPageEntryCallable
		implements Callable<Boolean> {

		public SaveAssetDisplayPageEntryCallable(
			long groupId, long userId, long classNameId, long classPK) {

			_groupId = groupId;
			_userId = userId;
			_classNameId = classNameId;
			_classPK = classPK;
		}

		@Override
		public Boolean call() throws Exception {
			try {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setUuid(PortalUUIDUtil.generate());

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

	}

}