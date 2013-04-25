/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.upgrade.v6_2_0.UpgradeAssetPublisher;
import com.liferay.portal.upgrade.v6_2_0.UpgradeBlogs;
import com.liferay.portal.upgrade.v6_2_0.UpgradeBlogsAggregator;
import com.liferay.portal.upgrade.v6_2_0.UpgradeCompany;
import com.liferay.portal.upgrade.v6_2_0.UpgradeCustomizablePortlets;
import com.liferay.portal.upgrade.v6_2_0.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v6_2_0.UpgradeDynamicDataListDisplay;
import com.liferay.portal.upgrade.v6_2_0.UpgradeDynamicDataMapping;
import com.liferay.portal.upgrade.v6_2_0.UpgradeGroup;
import com.liferay.portal.upgrade.v6_2_0.UpgradeImageGallery;
import com.liferay.portal.upgrade.v6_2_0.UpgradeJournal;
import com.liferay.portal.upgrade.v6_2_0.UpgradeLayout;
import com.liferay.portal.upgrade.v6_2_0.UpgradeLayoutRevision;
import com.liferay.portal.upgrade.v6_2_0.UpgradeLayoutSet;
import com.liferay.portal.upgrade.v6_2_0.UpgradeLayoutSetBranch;
import com.liferay.portal.upgrade.v6_2_0.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v6_2_0.UpgradeMessageBoardsAttachments;
import com.liferay.portal.upgrade.v6_2_0.UpgradePortletPreferences;
import com.liferay.portal.upgrade.v6_2_0.UpgradeSchema;
import com.liferay.portal.upgrade.v6_2_0.UpgradeSocial;
import com.liferay.portal.upgrade.v6_2_0.UpgradeUser;
import com.liferay.portal.upgrade.v6_2_0.UpgradeWikiAttachments;

/**
 * @author Raymond Augé
 * @author Juan Fernández
 */
public class UpgradeProcess_6_2_0 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);
		upgrade(UpgradeAssetPublisher.class);
		upgrade(UpgradeBlogs.class);
		upgrade(UpgradeBlogsAggregator.class);
		upgrade(UpgradeCompany.class);
		upgrade(UpgradeCustomizablePortlets.class);
		upgrade(UpgradeDocumentLibrary.class);
		upgrade(UpgradeDynamicDataListDisplay.class);
		upgrade(UpgradeDynamicDataMapping.class);
		upgrade(UpgradeGroup.class);
		upgrade(UpgradeImageGallery.class);
		upgrade(UpgradeJournal.class);
		upgrade(UpgradeLayout.class);
		upgrade(UpgradeLayoutRevision.class);
		upgrade(UpgradeLayoutSet.class);
		upgrade(UpgradeLayoutSetBranch.class);
		upgrade(UpgradeMessageBoards.class);
		upgrade(UpgradeMessageBoardsAttachments.class);
		upgrade(UpgradePortletPreferences.class);
		upgrade(UpgradeSocial.class);
		upgrade(UpgradeUser.class);
		upgrade(UpgradeWikiAttachments.class);
	}

}