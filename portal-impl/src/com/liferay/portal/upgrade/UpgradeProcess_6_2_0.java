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

import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v6_2_0.UpgradeAnnouncements;
import com.liferay.portal.upgrade.v6_2_0.UpgradeAssetPublisher;
import com.liferay.portal.upgrade.v6_2_0.UpgradeBlogs;
import com.liferay.portal.upgrade.v6_2_0.UpgradeBlogsAggregator;
import com.liferay.portal.upgrade.v6_2_0.UpgradeCalendar;
import com.liferay.portal.upgrade.v6_2_0.UpgradeCompany;
import com.liferay.portal.upgrade.v6_2_0.UpgradeCustomizablePortlets;
import com.liferay.portal.upgrade.v6_2_0.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v6_2_0.UpgradeDynamicDataListDisplay;
import com.liferay.portal.upgrade.v6_2_0.UpgradeDynamicDataMapping;
import com.liferay.portal.upgrade.v6_2_0.UpgradeGroup;
import com.liferay.portal.upgrade.v6_2_0.UpgradeImageGallery;
import com.liferay.portal.upgrade.v6_2_0.UpgradeJournal;
import com.liferay.portal.upgrade.v6_2_0.UpgradeLayout;
import com.liferay.portal.upgrade.v6_2_0.UpgradeLayoutFriendlyURL;
import com.liferay.portal.upgrade.v6_2_0.UpgradeLayoutRevision;
import com.liferay.portal.upgrade.v6_2_0.UpgradeLayoutSet;
import com.liferay.portal.upgrade.v6_2_0.UpgradeLayoutSetBranch;
import com.liferay.portal.upgrade.v6_2_0.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v6_2_0.UpgradeMessageBoardsAttachments;
import com.liferay.portal.upgrade.v6_2_0.UpgradePortletItem;
import com.liferay.portal.upgrade.v6_2_0.UpgradePortletPreferences;
import com.liferay.portal.upgrade.v6_2_0.UpgradeRepository;
import com.liferay.portal.upgrade.v6_2_0.UpgradeSQLServer;
import com.liferay.portal.upgrade.v6_2_0.UpgradeSchema;
import com.liferay.portal.upgrade.v6_2_0.UpgradeSearch;
import com.liferay.portal.upgrade.v6_2_0.UpgradeSocial;
import com.liferay.portal.upgrade.v6_2_0.UpgradeUser;
import com.liferay.portal.upgrade.v6_2_0.UpgradeUuid;
import com.liferay.portal.upgrade.v6_2_0.UpgradeWiki;
import com.liferay.portal.upgrade.v6_2_0.UpgradeWikiAttachments;
import com.liferay.portal.verify.VerifyUUID;
import com.liferay.portal.verify.model.AddressVerifiableModel;
import com.liferay.portal.verify.model.DLFileVersionVerifiableModel;
import com.liferay.portal.verify.model.EmailAddressVerifiableModel;
import com.liferay.portal.verify.model.GroupVerifiableModel;
import com.liferay.portal.verify.model.JournalArticleResourceVerifiableModel;
import com.liferay.portal.verify.model.LayoutPrototypeVerifiableModel;
import com.liferay.portal.verify.model.LayoutSetPrototypeVerifiableModel;
import com.liferay.portal.verify.model.MBBanVerifiableUUIDModel;
import com.liferay.portal.verify.model.MBDiscussionVerifiableUUIDModel;
import com.liferay.portal.verify.model.MBThreadFlagVerifiableUUIDModel;
import com.liferay.portal.verify.model.MBThreadVerifiableUUIDModel;
import com.liferay.portal.verify.model.OrganizationVerifiableAuditedModel;
import com.liferay.portal.verify.model.PasswordPolicyVerifiableModel;
import com.liferay.portal.verify.model.PhoneVerifiableModel;
import com.liferay.portal.verify.model.PollsVoteVerifiableUUIDModel;
import com.liferay.portal.verify.model.RoleVerifiableModel;
import com.liferay.portal.verify.model.UserGroupVerifiableModel;
import com.liferay.portal.verify.model.WebSiteVerifiableModel;

/**
 * @author Raymond Augé
 * @author Juan Fernández
 */
public class UpgradeProcess_6_2_0 extends Pre7UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(new UpgradeSchema());

		upgrade(new UpgradeAnnouncements());
		upgrade(new UpgradeAssetPublisher());
		upgrade(new UpgradeBlogs());
		upgrade(new UpgradeBlogsAggregator());
		upgrade(new UpgradeCalendar());
		upgrade(new UpgradeCompany());
		upgrade(new UpgradeCustomizablePortlets());
		upgrade(new UpgradeDocumentLibrary());
		upgrade(new UpgradeDynamicDataListDisplay());
		upgrade(new UpgradeDynamicDataMapping());
		upgrade(new UpgradeGroup());
		upgrade(new UpgradeImageGallery());
		upgrade(new UpgradeJournal());
		upgrade(new UpgradeLayout());
		upgrade(new UpgradeLayoutFriendlyURL());
		upgrade(new UpgradeLayoutRevision());
		upgrade(new UpgradeLayoutSet());
		upgrade(new UpgradeLayoutSetBranch());
		upgrade(new UpgradeMessageBoards());
		upgrade(new UpgradeMessageBoardsAttachments());
		upgrade(new UpgradePortletItem());
		upgrade(new UpgradePortletPreferences());
		upgrade(new UpgradeRepository());
		upgrade(new UpgradeSearch());
		upgrade(new UpgradeSocial());
		upgrade(new UpgradeSQLServer());
		upgrade(new UpgradeUser());
		upgrade(new UpgradeUuid());
		upgrade(new UpgradeWiki());
		upgrade(new UpgradeWikiAttachments());

		populateUUIDModels();

		clearIndexesCache();
	}

	protected void populateUUIDModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			VerifyUUID.verify(
				new AddressVerifiableModel(),
				new DLFileVersionVerifiableModel(),
				new EmailAddressVerifiableModel(), new GroupVerifiableModel(),
				new JournalArticleResourceVerifiableModel(),
				new LayoutPrototypeVerifiableModel(),
				new LayoutSetPrototypeVerifiableModel(),
				new MBBanVerifiableUUIDModel(),
				new MBDiscussionVerifiableUUIDModel(),
				new MBThreadFlagVerifiableUUIDModel(),
				new MBThreadVerifiableUUIDModel(),
				new PollsVoteVerifiableUUIDModel(),
				new OrganizationVerifiableAuditedModel(),
				new PasswordPolicyVerifiableModel(), new PhoneVerifiableModel(),
				new RoleVerifiableModel(), new UserGroupVerifiableModel(),
				new WebSiteVerifiableModel());
		}
	}

}