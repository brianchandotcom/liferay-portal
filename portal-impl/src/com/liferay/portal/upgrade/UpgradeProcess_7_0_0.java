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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v7_0_0.UpgradeAddress;
import com.liferay.portal.upgrade.v7_0_0.UpgradeAsset;
import com.liferay.portal.upgrade.v7_0_0.UpgradeAssetTagsResourcePermission;
import com.liferay.portal.upgrade.v7_0_0.UpgradeCompanyId;
import com.liferay.portal.upgrade.v7_0_0.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v7_0_0.UpgradeDocumentLibraryPortletId;
import com.liferay.portal.upgrade.v7_0_0.UpgradeDocumentLibraryPreferences;
import com.liferay.portal.upgrade.v7_0_0.UpgradeEmailAddress;
import com.liferay.portal.upgrade.v7_0_0.UpgradeEmailNotificationPreferences;
import com.liferay.portal.upgrade.v7_0_0.UpgradeExpando;
import com.liferay.portal.upgrade.v7_0_0.UpgradeGroup;
import com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage;
import com.liferay.portal.upgrade.v7_0_0.UpgradeLastPublishDate;
import com.liferay.portal.upgrade.v7_0_0.UpgradeLayout;
import com.liferay.portal.upgrade.v7_0_0.UpgradeListType;
import com.liferay.portal.upgrade.v7_0_0.UpgradeLookAndFeel;
import com.liferay.portal.upgrade.v7_0_0.UpgradeMembershipRequest;
import com.liferay.portal.upgrade.v7_0_0.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v7_0_0.UpgradeMobileDeviceRules;
import com.liferay.portal.upgrade.v7_0_0.UpgradeModules;
import com.liferay.portal.upgrade.v7_0_0.UpgradeMySQL;
import com.liferay.portal.upgrade.v7_0_0.UpgradeOrgLabor;
import com.liferay.portal.upgrade.v7_0_0.UpgradeOrganization;
import com.liferay.portal.upgrade.v7_0_0.UpgradePhone;
import com.liferay.portal.upgrade.v7_0_0.UpgradePortalPreferences;
import com.liferay.portal.upgrade.v7_0_0.UpgradePortletDisplayTemplatePreferences;
import com.liferay.portal.upgrade.v7_0_0.UpgradePortletId;
import com.liferay.portal.upgrade.v7_0_0.UpgradePostgreSQL;
import com.liferay.portal.upgrade.v7_0_0.UpgradeRatings;
import com.liferay.portal.upgrade.v7_0_0.UpgradeRelease;
import com.liferay.portal.upgrade.v7_0_0.UpgradeRepository;
import com.liferay.portal.upgrade.v7_0_0.UpgradeRepositoryEntry;
import com.liferay.portal.upgrade.v7_0_0.UpgradeResourcePermission;
import com.liferay.portal.upgrade.v7_0_0.UpgradeSchema;
import com.liferay.portal.upgrade.v7_0_0.UpgradeSharding;
import com.liferay.portal.upgrade.v7_0_0.UpgradeSocial;
import com.liferay.portal.upgrade.v7_0_0.UpgradeSubscription;
import com.liferay.portal.upgrade.v7_0_0.UpgradeWebsite;
import com.liferay.portal.upgrade.v7_0_0.UpgradeWorkflow;
import com.liferay.portal.verify.VerifyUUID;
import com.liferay.portal.verify.model.AssetTagVerifiableModel;
import com.liferay.portal.verify.model.RatingsEntryVerifiableModel;
import com.liferay.portal.verify.model.TeamVerifiableModel;

/**
 * @author Julio Camarero
 */
public class UpgradeProcess_7_0_0 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_7_0_0_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(new UpgradeSharding());

		upgrade(new UpgradeSchema());

		upgrade(new UpgradeKernelPackage());

		upgrade(new UpgradeAddress());
		upgrade(new UpgradeAsset());
		upgrade(new UpgradeAssetTagsResourcePermission());
		upgrade(new UpgradeCompanyId());
		upgrade(new UpgradeDocumentLibrary());
		upgrade(new UpgradeDocumentLibraryPortletId());
		upgrade(new UpgradeDocumentLibraryPreferences());
		upgrade(new UpgradeEmailAddress());
		upgrade(new UpgradeEmailNotificationPreferences());
		upgrade(new UpgradeExpando());
		upgrade(new UpgradeGroup());
		upgrade(new UpgradeLastPublishDate());
		upgrade(new UpgradeLayout());
		upgrade(new UpgradeListType());
		upgrade(new UpgradeLookAndFeel());
		upgrade(new UpgradeMembershipRequest());
		upgrade(new UpgradeMessageBoards());
		upgrade(new UpgradeModules());
		upgrade(new UpgradeMySQL());
		upgrade(new UpgradeOrganization());
		upgrade(new UpgradeOrgLabor());
		upgrade(new UpgradePhone());
		upgrade(new UpgradePortalPreferences());
		upgrade(new UpgradePortletDisplayTemplatePreferences());
		upgrade(new UpgradePortletId());
		upgrade(new UpgradePostgreSQL());
		upgrade(new UpgradeRatings());
		upgrade(new UpgradeRelease());
		upgrade(new UpgradeRepository());
		upgrade(new UpgradeRepositoryEntry());
		upgrade(new UpgradeResourcePermission());
		upgrade(new UpgradeSocial());
		upgrade(new UpgradeSubscription());
		upgrade(new UpgradeWebsite());
		upgrade(new UpgradeWorkflow());

		upgrade(new UpgradeMobileDeviceRules());

		populateUUIDModels();

		clearIndexesCache();
	}

	protected void populateUUIDModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			VerifyUUID.verify(
				new AssetTagVerifiableModel(),
				new RatingsEntryVerifiableModel(), new TeamVerifiableModel());
		}
	}

}