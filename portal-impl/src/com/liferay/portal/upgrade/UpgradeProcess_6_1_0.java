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
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v6_1_0.UpgradeAdminPortlets;
import com.liferay.portal.upgrade.v6_1_0.UpgradeAsset;
import com.liferay.portal.upgrade.v6_1_0.UpgradeAssetPublisher;
import com.liferay.portal.upgrade.v6_1_0.UpgradeBlogs;
import com.liferay.portal.upgrade.v6_1_0.UpgradeCamelCasePortletPreferences;
import com.liferay.portal.upgrade.v6_1_0.UpgradeCountry;
import com.liferay.portal.upgrade.v6_1_0.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v6_1_0.UpgradeExpando;
import com.liferay.portal.upgrade.v6_1_0.UpgradeGroup;
import com.liferay.portal.upgrade.v6_1_0.UpgradeIFrame;
import com.liferay.portal.upgrade.v6_1_0.UpgradeImageGallery;
import com.liferay.portal.upgrade.v6_1_0.UpgradeJournal;
import com.liferay.portal.upgrade.v6_1_0.UpgradeLayout;
import com.liferay.portal.upgrade.v6_1_0.UpgradeLock;
import com.liferay.portal.upgrade.v6_1_0.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v6_1_0.UpgradeNavigation;
import com.liferay.portal.upgrade.v6_1_0.UpgradePermission;
import com.liferay.portal.upgrade.v6_1_0.UpgradePortletPreferences;
import com.liferay.portal.upgrade.v6_1_0.UpgradeResourcePermission;
import com.liferay.portal.upgrade.v6_1_0.UpgradeSchema;
import com.liferay.portal.upgrade.v6_1_0.UpgradeScopes;
import com.liferay.portal.upgrade.v6_1_0.UpgradeSocial;
import com.liferay.portal.upgrade.v6_1_0.UpgradeSubscription;
import com.liferay.portal.upgrade.v6_1_0.UpgradeUserName;
import com.liferay.portal.upgrade.v6_1_0.UpgradeVirtualHost;
import com.liferay.portal.upgrade.v6_1_0.UpgradeWorkflow;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 * @author Miguel Pastor
 */
public class UpgradeProcess_6_1_0 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_1_0_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);
		upgrade(UpgradeUserName.class);
		upgrade(UpgradeAdminPortlets.class);
		upgrade(UpgradeBlogs.class);
		upgrade(UpgradeCamelCasePortletPreferences.class);
		upgrade(UpgradeCountry.class);
		upgrade(UpgradeDocumentLibrary.class);
		upgrade(UpgradeExpando.class);
		upgrade(UpgradeGroup.class);
		upgrade(UpgradeIFrame.class);
		upgrade(UpgradeImageGallery.class);
		upgrade(UpgradeJournal.class);
		upgrade(UpgradeLayout.class);
		upgrade(UpgradeLock.class);
		upgrade(UpgradeMessageBoards.class);
		upgrade(UpgradeNavigation.class);
		upgrade(UpgradePermission.class);
		upgrade(UpgradePortletPreferences.class);
		upgrade(UpgradeResourcePermission.class);
		upgrade(UpgradeScopes.class);
		upgrade(UpgradeSocial.class);
		upgrade(UpgradeSubscription.class);
		upgrade(UpgradeVirtualHost.class);
		upgrade(UpgradeWorkflow.class);
		upgrade(UpgradeAsset.class);
		upgrade(UpgradeAssetPublisher.class);
	}

}