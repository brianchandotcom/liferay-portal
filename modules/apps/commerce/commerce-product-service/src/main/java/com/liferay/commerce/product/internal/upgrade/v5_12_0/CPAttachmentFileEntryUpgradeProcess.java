/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v5_12_0;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;

/**
 * @author Andrea Sbarra
 */
public class CPAttachmentFileEntryUpgradeProcess extends UpgradeProcess {

	public CPAttachmentFileEntryUpgradeProcess(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String className =
			"com.liferay.commerce.product.model.CPAttachmentFileEntry";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from CPAttachmentFileEntry");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long cpAttachmentFileEntryId = resultSet.getLong(
					"CPAttachmentFileEntryId");

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					className, cpAttachmentFileEntryId);

				if (assetEntry != null) {
					continue;
				}

				long userId = resultSet.getLong("userId");
				long groupId = resultSet.getLong("groupId");
				Date date = new Date(System.currentTimeMillis());
				String title = resultSet.getString("title");

				_assetEntryLocalService.updateEntry(
					userId, groupId, date, date, className,
					cpAttachmentFileEntryId, null, 0, new long[0],
					new String[0], true, true, null, null, null, null,
					ContentTypes.TEXT_PLAIN, title, StringPool.BLANK, null,
					null, null, 0, 0, null);
			}
		}
	}

	private final AssetEntryLocalService _assetEntryLocalService;

}