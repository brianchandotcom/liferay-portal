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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.util.UpgradeModulesFactory;
import com.liferay.portal.kernel.upgrade.util.VersionTreeMap;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;

/**
 * @author Pei-Jung Lan
 */
public class PortalUpgradeProcessRegistryImpl
	implements PortalUpgradeProcessRegistry {

	@Override
	public void registerUpgradeProcesses(VersionTreeMap versionTreeMap) {
		versionTreeMap.put(
			new Version(9, 0, 0),
			UpgradeProcessFactory.addColumns(
				"Address", "externalReferenceCode VARCHAR(75) null",
				"description STRING null", "latitude DOUBLE",
				"longitude DOUBLE", "name VARCHAR(255) null",
				"validationDate DATE null", "validationStatus INTEGER"),
			UpgradeProcessFactory.alterColumnType(
				"Address", "street1", "VARCHAR(255) null"),
			UpgradeProcessFactory.alterColumnType(
				"Address", "street2", "VARCHAR(255) null"),
			UpgradeProcessFactory.alterColumnType(
				"Address", "street3", "VARCHAR(255) null"));

		versionTreeMap.put(
			new Version(9, 0, 1),
			UpgradeModulesFactory.create(
				new String[] {
					"com.liferay.change.tracking.web",
					"com.liferay.document.library.asset.auto.tagger.tensorflow",
					"com.liferay.portal.bundle.blacklist.impl",
					"com.liferay.portal.component.blacklist.impl",
					"com.liferay.portal.search", "com.liferay.template.web"
				},
				new String[][] {
					{"opensocial-portlet", "opensocial-portlet", "OpenSocial"}
				},
				new String[][] {
					{
						"com.liferay.softwarecatalog.service",
						"SCFrameworkVersion"
					}
				}));

		versionTreeMap.put(new Version(9, 1, 0), new UpgradeRegion());

		versionTreeMap.put(new Version(9, 2, 0), new UpgradeCountry());

		versionTreeMap.put(new Version(9, 2, 1), new UpgradeListType());

		versionTreeMap.put(
			new Version(10, 0, 0), new UpgradePortletPreferences());

		versionTreeMap.put(new Version(11, 0, 0), new UpgradeAssetEntry());

		versionTreeMap.put(
			new Version(12, 0, 0), new UpgradePortalPreferences());

		versionTreeMap.put(new Version(12, 0, 1), new UpgradeResourceAction());

		versionTreeMap.put(new Version(12, 0, 2), new UpgradeDLFileEntryType());

		versionTreeMap.put(new Version(12, 1, 0), new UpgradeDLFileEntry());

		versionTreeMap.put(
			new Version(12, 1, 1),
			UpgradeProcessFactory.addColumns(
				"DLFileVersion", "expirationDate DATE null",
				"reviewDate DATE null"));

		versionTreeMap.put(new Version(12, 2, 0), new UpgradeCompanyId());

		versionTreeMap.put(
			new Version(12, 2, 1),
			UpgradeProcessFactory.alterColumnType(
				"AssetEntry", "title", "TEXT null"));

		versionTreeMap.put(
			new Version(12, 2, 2), new UpgradePortalPreferenceValue());

		versionTreeMap.put(new Version(13, 0, 0), new UpgradeAccount());

		versionTreeMap.put(new Version(13, 0, 1), new UpgradeLayout());

		versionTreeMap.put(new Version(13, 1, 0), new UpgradeAssetVocabulary());

		versionTreeMap.put(new Version(13, 2, 0), new UpgradeAssetCategory());

		versionTreeMap.put(
			new Version(13, 3, 0),
			new CTModelUpgradeProcess("Repository", "RepositoryEntry"));

		versionTreeMap.put(new Version(13, 3, 1), new UpgradeRepository());

		versionTreeMap.put(new Version(13, 3, 2), new UpgradeMappingTables());

		versionTreeMap.put(new Version(13, 3, 3), new UpgradeGroup());

		versionTreeMap.put(new Version(13, 3, 4), new UpgradeExpandoColumn());

		versionTreeMap.put(
			new Version(13, 3, 5),
			UpgradeProcessFactory.alterColumnType(
				"Contact_", "prefixId", "LONG NULL"));

		versionTreeMap.put(
			new Version(13, 3, 6),
			UpgradeProcessFactory.alterColumnType(
				"Contact_", "suffixId", "LONG NULL"));

		versionTreeMap.put(
			new Version(14, 0, 0), new UpgradeExternalReferenceCode());

		versionTreeMap.put(
			new Version(14, 0, 1), new UpgradeFaviconFileEntryIdColumn());

		versionTreeMap.put(new Version(14, 0, 2), new UpgradeCountryCode());

		versionTreeMap.put(new Version(15, 0, 0), new UpgradeOrgGroupRole());

		versionTreeMap.put(new Version(16, 0, 0), new DummyUpgradeProcess());

		versionTreeMap.put(
			new Version(16, 1, 0),
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {{"DLFolder", "folderId"}};
				}

			});
	}

}