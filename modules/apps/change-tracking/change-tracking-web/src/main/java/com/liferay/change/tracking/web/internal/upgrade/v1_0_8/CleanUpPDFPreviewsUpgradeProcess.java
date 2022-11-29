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

package com.liferay.change.tracking.web.internal.upgrade.v1_0_8;

import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author David Truong
 */
public class CleanUpPDFPreviewsUpgradeProcess extends UpgradeProcess {

	public CleanUpPDFPreviewsUpgradeProcess(
		CTCollectionLocalService ctCollectionLocalService, Portal portal) {

		_ctCollectionLocalService = ctCollectionLocalService;
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (hasTable("DLFileVersionPreview")) {
			_cleanUpDLFileVersionPreviews();
		}
	}

	private void _cleanUpDLFileVersionPreviews() throws Exception {
		long dlFileVersionPreviewClassNameId = _portal.getClassNameId(
			"com.liferay.document.library.model.DLFileVersionPreview");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select distinct CTEntry.ctCollectionId, ",
					"CTEntry.modelClassNameId, CTEntry.modelClassPK from ",
					"CTEntry inner join DLFileVersionPreview on ",
					"CTEntry.modelClassNameId = ",
					dlFileVersionPreviewClassNameId,
					" and DLFileVersionPreview.dlFileVersionPreviewId = ",
					"CTEntry.modelClassPK inner join DLFileEntry on ",
					"(DLFileVersionPreview.fileEntryId = ",
					"DLFileEntry.fileEntryId and DLFileEntry.ctCollectionId ",
					"!= CTEntry.ctCollectionId)"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long ctCollectionId = resultSet.getLong(
					"CTEntry.ctCollectionId");
				long classNameId = resultSet.getLong(
					"CTEntry.modelClassNameId");
				long classPK = resultSet.getLong("CTEntry.modelClassPK");

				_ctCollectionLocalService.discardCTEntry(
					ctCollectionId, classNameId, classPK, true);
			}
		}
	}

	private final CTCollectionLocalService _ctCollectionLocalService;
	private final Portal _portal;

}