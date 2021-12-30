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
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 * @author Alberto Chaparro
 */
public class ArticleAssetsUpgradeProcess extends UpgradeProcess {

	public ArticleAssetsUpgradeProcess(
		AssetEntryLocalService assetEntryLocalService,
		CompanyLocalService companyLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateDefaultDraftArticleAssets();
	}

	private void _updateDefaultDraftArticleAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_companyLocalService.forEachCompanyId(
				companyId -> _updateDefaultDraftArticleAssets(companyId));
		}
	}

	private void _updateDefaultDraftArticleAssets(long companyId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select resourcePrimKey, indexable from JournalArticle ",
					"where companyId = ", companyId, " and version = ",
					JournalArticleConstants.VERSION_DEFAULT, " and status = ",
					WorkflowConstants.STATUS_DRAFT));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long resourcePrimKey = resultSet.getLong("resourcePrimKey");

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					JournalArticle.class.getName(), resourcePrimKey);

				if (assetEntry == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Journal article with resource primary key ",
								resourcePrimKey, " does not have associated ",
								"asset entry"));
					}

					continue;
				}

				boolean indexable = resultSet.getBoolean("indexable");

				_assetEntryLocalService.updateEntry(
					assetEntry.getClassName(), assetEntry.getClassPK(), null,
					null, indexable, assetEntry.isVisible());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ArticleAssetsUpgradeProcess.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final CompanyLocalService _companyLocalService;

}