/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_11;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

import java.sql.PreparedStatement;

/**
 * @author Jürgen Kappler
 */
public class DLFileEntryTypesDDMStructureUpgradeProcess extends UpgradeProcess {

	public DLFileEntryTypesDDMStructureUpgradeProcess(Portal portal) {
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update DDMStructure set type_ = ? where classNameId = ? and " +
					"structureKey not in ('DL_VIDEO_EXTERNAL_SHORTCUT', " +
						"'GOOGLE_DOCS')")) {

			preparedStatement.setLong(1, DDMStructureConstants.TYPE_DEFAULT);
			preparedStatement.setLong(
				2, _portal.getClassNameId(DLFileEntryMetadata.class.getName()));

			preparedStatement.executeUpdate();
		}
	}

	private final Portal _portal;

}