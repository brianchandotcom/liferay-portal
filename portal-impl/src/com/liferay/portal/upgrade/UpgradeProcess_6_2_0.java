/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.verify.model.VerifiableAuditedModel;
import com.liferay.portal.kernel.verify.model.VerifiableUUIDModel;
import com.liferay.portal.upgrade.v6_2_0.UpgradeAnnouncements;
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
import com.liferay.portal.verify.model.LayoutPrototypeVerifiableModel;
import com.liferay.portal.verify.model.LayoutSetPrototypeVerifiableModel;
import com.liferay.portal.verify.model.RoleVerifiableModel;
import com.liferay.portal.verify.model.UserGroupVerifiableModel;

/**
 * @author Raymond Augé
 * @author Juan Fernández
 */
public class UpgradeProcess_6_2_0 extends Pre7UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER;
	}

	public class AddressVerifiableUUIDModel implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "addressId";
		}

		@Override
		public String getTableName() {
			return "Address";
		}

	}

	public class DLFileVersionVerifiableUUIDModel
		implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "fileVersionId";
		}

		@Override
		public String getTableName() {
			return "DLFileVersion";
		}

	}

	public class EmailAddressVerifiableUUIDModel
		implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "emailAddressId";
		}

		@Override
		public String getTableName() {
			return "EmailAddress";
		}

	}

	public class GroupVerifiableUUIDModel implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "groupId";
		}

		@Override
		public String getTableName() {
			return "Group_";
		}

	}

	public class JournalArticleResourceVerifiableUUIDModel
		implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "resourcePrimKey";
		}

		@Override
		public String getTableName() {
			return "JournalArticleResource";
		}

	}

	public class MBBanVerifiableUUIDModel implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "banId";
		}

		@Override
		public String getTableName() {
			return "MBBan";
		}

	}

	public class MBDiscussionVerifiableUUIDModel
		implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "discussionId";
		}

		@Override
		public String getTableName() {
			return "MBDiscussion";
		}

	}

	public class MBThreadFlagVerifiableUUIDModel
		implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "threadFlagId";
		}

		@Override
		public String getTableName() {
			return "MBThreadFlag";
		}

	}

	public class MBThreadVerifiableUUIDModel implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "threadId";
		}

		@Override
		public String getTableName() {
			return "MBThread";
		}

	}

	public class OrganizationVerifiableAuditedModel
		implements VerifiableAuditedModel, VerifiableUUIDModel {

		@Override
		public String getJoinByTableName() {
			return null;
		}

		@Override
		public String getPrimaryKeyColumnName() {
			return "organizationId";
		}

		@Override
		public String getRelatedModelName() {
			return null;
		}

		@Override
		public String getRelatedPKColumnName() {
			return null;
		}

		@Override
		public String getTableName() {
			return "Organization_";
		}

		@Override
		public boolean isAnonymousUserAllowed() {
			return false;
		}

		@Override
		public boolean isUpdateDates() {
			return true;
		}

	}

	public class PasswordPolicyVerifiableUUIDModel
		implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "passwordPolicyId";
		}

		@Override
		public String getTableName() {
			return "PasswordPolicy";
		}

	}

	public class PhoneVerifiableUUIDModel implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "phoneId";
		}

		@Override
		public String getTableName() {
			return "Phone";
		}

	}

	public class PollsVoteVerifiableUUIDModel implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "voteId";
		}

		@Override
		public String getTableName() {
			return "PollsVote";
		}

	}

	public class WebsiteVerifiableUUIDModel implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "websiteId";
		}

		@Override
		public String getTableName() {
			return "Website";
		}

	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(new UpgradeSchema());

		upgrade(new UpgradeAnnouncements());
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
				new AddressVerifiableUUIDModel(),
				new DLFileVersionVerifiableUUIDModel(),
				new EmailAddressVerifiableUUIDModel(),
				new GroupVerifiableUUIDModel(),
				new JournalArticleResourceVerifiableUUIDModel(),
				new LayoutPrototypeVerifiableModel(),
				new LayoutSetPrototypeVerifiableModel(),
				new MBBanVerifiableUUIDModel(),
				new MBDiscussionVerifiableUUIDModel(),
				new MBThreadFlagVerifiableUUIDModel(),
				new MBThreadVerifiableUUIDModel(),
				new PollsVoteVerifiableUUIDModel(),
				new OrganizationVerifiableAuditedModel(),
				new PasswordPolicyVerifiableUUIDModel(),
				new PhoneVerifiableUUIDModel(), new RoleVerifiableModel(),
				new UserGroupVerifiableModel(),
				new WebsiteVerifiableUUIDModel());
		}
	}

}