/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v7_0_0;

import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.internal.upgrade.v7_0_0.util.ObjectFolderTable;
import com.liferay.object.model.ObjectFolder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Murilo Stodolni
 */
public class ObjectDefinitionUpgradeProcess extends UpgradeProcess {

	public ObjectDefinitionUpgradeProcess(
		CompanyLocalService companyLocalService, PortalUUID portalUUID,
		ResourceLocalService resourceLocalService) {

		_companyLocalService = companyLocalService;
		_portalUUID = portalUUID;
		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _addObjectFolder(
				company.getCompanyId(), company.getGuestUser()));
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			ObjectFolderTable.create(),
			UpgradeProcessFactory.addColumns(
				"ObjectDefinition", "objectFolderId LONG")
		};
	}

	private void _addObjectFolder(long companyId, User user)
		throws PortalException, SQLException {

		PreparedStatement preparedStatement1 = connection.prepareStatement(
			StringBundler.concat(
				"insert into ObjectFolder (uuid_, externalReferenceCode, ",
				"objectFolderId, companyId, userId, userName, createDate, ",
				"modifiedDate, label, name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ",
				"?, ?)"));

		preparedStatement1.setString(1, _portalUUID.generate());
		preparedStatement1.setString(
			2, ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_UNCATEGORIZED);

		long objectFolderId = increment();

		preparedStatement1.setLong(3, objectFolderId);

		preparedStatement1.setLong(4, companyId);
		preparedStatement1.setLong(5, user.getUserId());
		preparedStatement1.setString(6, user.getFullName());

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		preparedStatement1.setTimestamp(7, timestamp);
		preparedStatement1.setTimestamp(8, timestamp);

		preparedStatement1.setString(
			9,
			LocalizationUtil.getXml(
				new LocalizedValuesMap() {
					{
						put(
							LocaleUtil.fromLanguageId(
								UpgradeProcessUtil.getDefaultLanguageId(
									companyId)),
							ObjectFolderConstants.NAME_UNCATEGORIZED);
					}
				},
				"Label"));
		preparedStatement1.setString(
			10, ObjectFolderConstants.NAME_UNCATEGORIZED);

		preparedStatement1.execute();

		_resourceLocalService.addResources(
			companyId, 0, user.getUserId(), ObjectFolder.class.getName(),
			objectFolderId, false, true, true);

		PreparedStatement preparedStatement2 = connection.prepareStatement(
			"update ObjectDefinition set objectFolderId = ? where companyId " +
				"= ?");

		preparedStatement2.setLong(1, objectFolderId);
		preparedStatement2.setLong(2, companyId);

		preparedStatement2.execute();
	}

	private final CompanyLocalService _companyLocalService;
	private final PortalUUID _portalUUID;
	private final ResourceLocalService _resourceLocalService;

}