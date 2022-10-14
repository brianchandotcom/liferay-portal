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
import com.liferay.portal.kernel.upgrade.util.UpgradeVersionTreeMap;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;

/**
 * @author Pei-Jung Lan
 */
public class PortalUpgradeProcessRegistryImpl
	implements PortalUpgradeProcessRegistry {

	@Override
	public void registerUpgradeProcesses(
		UpgradeVersionTreeMap upgradeVersionTreeMap) {

		upgradeVersionTreeMap.put(
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

		upgradeVersionTreeMap.put(
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

		upgradeVersionTreeMap.put(new Version(9, 1, 0), new UpgradeRegion());

		upgradeVersionTreeMap.put(new Version(9, 2, 0), new UpgradeCountry());

		upgradeVersionTreeMap.put(new Version(9, 2, 1), new UpgradeListType());

		upgradeVersionTreeMap.put(
			new Version(10, 0, 0), new UpgradePortletPreferences());

		upgradeVersionTreeMap.put(
			new Version(11, 0, 0), new UpgradeAssetEntry());

		upgradeVersionTreeMap.put(
			new Version(12, 0, 0), new UpgradePortalPreferences());

		upgradeVersionTreeMap.put(
			new Version(12, 0, 1), new UpgradeResourceAction());

		upgradeVersionTreeMap.put(
			new Version(12, 0, 2), new UpgradeDLFileEntryType());

		upgradeVersionTreeMap.put(
			new Version(12, 1, 0), new UpgradeDLFileEntry());

		upgradeVersionTreeMap.put(
			new Version(12, 1, 1),
			UpgradeProcessFactory.addColumns(
				"DLFileVersion", "expirationDate DATE null",
				"reviewDate DATE null"));

		upgradeVersionTreeMap.put(
			new Version(12, 2, 0), new UpgradeCompanyId());

		upgradeVersionTreeMap.put(
			new Version(12, 2, 1),
			UpgradeProcessFactory.alterColumnType(
				"AssetEntry", "title", "TEXT null"));

		upgradeVersionTreeMap.put(
			new Version(12, 2, 2), new UpgradePortalPreferenceValue());

		upgradeVersionTreeMap.put(new Version(13, 0, 0), new UpgradeAccount());

		upgradeVersionTreeMap.put(new Version(13, 0, 1), new UpgradeLayout());

		upgradeVersionTreeMap.put(
			new Version(13, 1, 0), new UpgradeAssetVocabulary());

		upgradeVersionTreeMap.put(
			new Version(13, 2, 0), new UpgradeAssetCategory());

		upgradeVersionTreeMap.put(
			new Version(13, 3, 0),
			new CTModelUpgradeProcess("Repository", "RepositoryEntry"));

		upgradeVersionTreeMap.put(
			new Version(13, 3, 1), new UpgradeRepository());

		upgradeVersionTreeMap.put(
			new Version(13, 3, 2), new UpgradeMappingTables());

		upgradeVersionTreeMap.put(new Version(13, 3, 3), new UpgradeGroup());

		upgradeVersionTreeMap.put(
			new Version(13, 3, 4), new UpgradeExpandoColumn());

		upgradeVersionTreeMap.put(
			new Version(13, 3, 5),
			UpgradeProcessFactory.alterColumnType(
				"Contact_", "prefixId", "LONG NULL"));

		upgradeVersionTreeMap.put(
			new Version(13, 3, 6),
			UpgradeProcessFactory.alterColumnType(
				"Contact_", "suffixId", "LONG NULL"));

		upgradeVersionTreeMap.put(
			new Version(14, 0, 0), new UpgradeExternalReferenceCode());

		upgradeVersionTreeMap.put(
			new Version(14, 0, 1), new UpgradeFaviconFileEntryIdColumn());

		upgradeVersionTreeMap.put(
			new Version(14, 0, 2), new UpgradeCountryCode());

		upgradeVersionTreeMap.put(
			new Version(15, 0, 0), new UpgradeOrgGroupRole());

		upgradeVersionTreeMap.put(
			new Version(16, 0, 0), new DummyUpgradeProcess());

		upgradeVersionTreeMap.put(
			new Version(16, 1, 0),
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {{"DLFolder", "folderId"}};
				}

			});

		upgradeVersionTreeMap.put(
			new Version(16, 1, 1), new UpgradeGroupType());

		upgradeVersionTreeMap.put(
			new Version(16, 1, 2),
			UpgradeModulesFactory.create(
				new String[] {
					"com.liferay.analytics.settings.web",
					"com.liferay.client.extension.web",
					"com.liferay.commerce.image.service",
					"com.liferay.commerce.product.content.web",
					"com.liferay.document.library.google.docs",
					"com.liferay.dynamic.data.mapping.form.web",
					"com.liferay.image.impl",
					"com.liferay.journal.image.service",
					"com.liferay.message.boards.moderation",
					"com.liferay.monitoring.web",
					"com.liferay.portal.configuration.persistence.impl",
					"com.liferay.portal.search.elasticsearch.monitoring.web",
					"com.liferay.portal.security.ldap.impl",
					"com.liferay.portal.security.sso.token.impl",
					"com.liferay.push.notifications.web",
					"com.liferay.saml.addon.keep.alive.web",
					"com.liferay.saml.impl", "com.liferay.saml.web",
					"com.liferay.segments.content.targeting.upgrade"
				},
				null));

		upgradeVersionTreeMap.put(
			new Version(17, 0, 0),
			UpgradeProcessFactory.dropColumns("Company", "system"));

		upgradeVersionTreeMap.put(
			new Version(18, 0, 0),
			UpgradeProcessFactory.alterColumnName(
				"Address", "typeId", "listTypeId LONG"));

		upgradeVersionTreeMap.put(
			new Version(18, 0, 1), new UpgradeVirtualHost());

		upgradeVersionTreeMap.put(
			new Version(19, 0, 0),
			UpgradeProcessFactory.alterColumnName(
				"Phone", "typeId", "listTypeId LONG"));

		upgradeVersionTreeMap.put(
			new Version(20, 0, 0),
			UpgradeProcessFactory.alterColumnName(
				"EmailAddress", "typeId", "listTypeId LONG"));

		upgradeVersionTreeMap.put(
			new Version(21, 0, 0),
			UpgradeProcessFactory.alterColumnName(
				"Website", "typeId", "listTypeId LONG"));

		upgradeVersionTreeMap.put(
			new Version(22, 0, 0),
			UpgradeProcessFactory.alterColumnName(
				"Organization_", "statusId", "statusListTypeId LONG"));
	}

}