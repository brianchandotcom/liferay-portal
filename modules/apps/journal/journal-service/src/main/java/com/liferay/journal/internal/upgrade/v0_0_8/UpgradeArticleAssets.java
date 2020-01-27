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

package com.liferay.journal.internal.upgrade.v0_0_8;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Preston Crary
 * @author Alberto Chaparro
 */
public class UpgradeArticleAssets extends UpgradeProcess {

	public UpgradeArticleAssets(
		AssetEntryLocalService assetEntryLocalService,
		CompanyLocalService companyLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateDefaultDraftArticleAssets();
	}

	protected void updateDefaultDraftArticleAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<Company> companies = _companyLocalService.getCompanies();

			for (Company company : companies) {
				updateDefaultDraftArticleAssets(company.getCompanyId());
			}
		}
	}

	protected void updateDefaultDraftArticleAssets(long companyId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"select resourcePrimKey, indexable from JournalArticle ",
					"where companyId = ", companyId, " and version = ",
					JournalArticleConstants.VERSION_DEFAULT, " and status = ",
					WorkflowConstants.STATUS_DRAFT));
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long resourcePrimKey = rs.getLong("resourcePrimKey");

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					JournalArticle.class.getName(), resourcePrimKey);

				if (assetEntry == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Journal article with resourcePrimKey ",
								resourcePrimKey, " does not have associated ",
								"AssetEntry"));
					}

					continue;
				}

				boolean indexable = rs.getBoolean("indexable");

				_assetEntryLocalService.updateEntry(
					assetEntry.getClassName(), assetEntry.getClassPK(), null,
					null, indexable, assetEntry.isVisible());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeArticleAssets.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final CompanyLocalService _companyLocalService;

}