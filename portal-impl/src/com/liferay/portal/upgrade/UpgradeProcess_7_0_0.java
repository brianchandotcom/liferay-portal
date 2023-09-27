/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeModulesFactory;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.verify.model.VerifiableUUIDModel;
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
import com.liferay.portal.upgrade.v7_0_0.UpgradeUser;
import com.liferay.portal.upgrade.v7_0_0.UpgradeWebsite;
import com.liferay.portal.upgrade.v7_0_0.UpgradeWorkflow;
import com.liferay.portal.verify.VerifyUUID;
import com.liferay.portal.verify.model.AssetTagVerifiableUUIDModel;

/**
 * @author Julio Camarero
 */
public class UpgradeProcess_7_0_0 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_7_0_0_BUILD_NUMBER;
	}

	public class RatingsEntryVerifiableUUIDModel
		implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "entryId";
		}

		@Override
		public String getTableName() {
			return "RatingsEntry";
		}

	}

	public class TeamVerifiableUUIDModel implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "teamId";
		}

		@Override
		public String getTableName() {
			return "Team";
		}

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
		upgrade(
			UpgradeModulesFactory.create(
				_BUNDLE_SYMBOLIC_NAMES, _CONVERTED_LEGACY_MODULES));
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
		upgrade(new UpgradeUser());
		upgrade(new UpgradeWebsite());
		upgrade(new UpgradeWorkflow());

		upgrade(new UpgradeMobileDeviceRules());

		populateUUIDModels();

		clearIndexesCache();
	}

	protected void populateUUIDModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			VerifyUUID.verify(
				new AssetTagVerifiableUUIDModel(),
				new RatingsEntryVerifiableUUIDModel(),
				new TeamVerifiableUUIDModel());
		}
	}

	private static final String[] _BUNDLE_SYMBOLIC_NAMES = {
		"com.liferay.amazon.rankings.web", "com.liferay.asset.browser.web",
		"com.liferay.asset.categories.navigation.web",
		"com.liferay.asset.publisher.web",
		"com.liferay.asset.tags.compiler.web",
		"com.liferay.asset.tags.navigation.web",
		"com.liferay.blogs.recent.bloggers.web", "com.liferay.blogs.web",
		"com.liferay.bookmarks.service", "com.liferay.bookmarks.web",
		"com.liferay.calendar.web", "com.liferay.comment.page.comments.web",
		"com.liferay.currency.converter.web", "com.liferay.dictionary.web",
		"com.liferay.document.library.service",
		"com.liferay.document.library.web",
		"com.liferay.dynamic.data.lists.service",
		"com.liferay.dynamic.data.lists.web",
		"com.liferay.dynamic.data.mapping.service",
		"com.liferay.dynamic.data.mapping.web",
		"com.liferay.exportimport.service", "com.liferay.exportimport.web",
		"com.liferay.flags.web", "com.liferay.hello.velocity.web",
		"com.liferay.hello.world.web", "com.liferay.iframe.web",
		"com.liferay.invitation.web", "com.liferay.item.selector.web",
		"com.liferay.journal.content.search.web",
		"com.liferay.journal.content.web", "com.liferay.journal.service",
		"com.liferay.journal.web", "com.liferay.knowledge.base.web",
		"com.liferay.layout.admin.web", "com.liferay.license.manager.web",
		"com.liferay.loan.calculator.web", "com.liferay.login.web",
		"com.liferay.message.boards.web",
		"com.liferay.mobile.device.rules.service",
		"com.liferay.mobile.device.rules.web", "com.liferay.my.account.web",
		"com.liferay.my.subscriptions.web", "com.liferay.nested.portlets.web",
		"com.liferay.network.utilities.web",
		"com.liferay.password.generator.web", "com.liferay.plugins.admin.web",
		"com.liferay.polls.service",
		"com.liferay.portal.background.task.service",
		"com.liferay.portal.instances.web", "com.liferay.portal.lock.service",
		"com.liferay.portal.scheduler", "com.liferay.portal.scheduler.quartz",
		"com.liferay.portal.search.web", "com.liferay.portal.settings.web",
		"com.liferay.portlet.configuration.css.web",
		"com.liferay.portlet.configuration.web",
		"com.liferay.product.navigation.product.menu.web",
		"com.liferay.quick.note.web", "com.liferay.ratings.page.ratings.web",
		"com.liferay.rss.web", "com.liferay.server.admin.web",
		"com.liferay.shopping.service", "com.liferay.site.browser.web",
		"com.liferay.site.my.sites.web",
		"com.liferay.site.navigation.breadcrumb.web",
		"com.liferay.site.navigation.directory.web",
		"com.liferay.site.navigation.language.web",
		"com.liferay.site.navigation.menu.web",
		"com.liferay.site.navigation.site.map.web",
		"com.liferay.social.activities.web", "com.liferay.social.activity.web",
		"com.liferay.social.group.statistics.web",
		"com.liferay.social.requests.web",
		"com.liferay.social.user.statistics.web",
		"com.liferay.softwarecatalog.service", "com.liferay.staging.bar.web",
		"com.liferay.translator.web", "com.liferay.trash.web",
		"com.liferay.unit.converter.web", "com.liferay.web.proxy.web",
		"com.liferay.wiki.navigation.web", "com.liferay.wiki.service",
		"com.liferay.wiki.web", "com.liferay.xsl.content.web"
	};

	private static final String[][] _CONVERTED_LEGACY_MODULES = {
		{"calendar-portlet", "com.liferay.calendar.service", "Calendar"},
		{"chat-portlet", "com.liferay.chat.service", "Chat"},
		{"contacts-portlet", "com.liferay.contacts.service", "Contacts"},
		{
			"kaleo-designer-portlet",
			"com.liferay.portal.workflow.kaleo.designer.web", "KaleoDesigner"
		},
		{
			"kaleo-forms-portlet",
			"com.liferay.portal.workflow.kaleo.forms.service", "KaleoForms"
		},
		{"kaleo-web", "com.liferay.portal.workflow.kaleo.service", "Kaleo"},
		{"knowledge-base-portlet", "com.liferay.knowledge.base.service", "KB"},
		{
			"marketplace-portlet", "com.liferay.marketplace.service",
			"Marketplace"
		},
		{"microblogs-portlet", "com.liferay.microblogs.service", "Microblogs"},
		{"opensocial-portlet", "opensocial-portlet", "OpenSocial"},
		{
			"private-messaging-portlet",
			"com.liferay.social.privatemessaging.service", "PM"
		},
		{"so-portlet", "com.liferay.invitation.invite.members.service", "SO"},
		{
			"social-networking-portlet",
			"com.liferay.social.networking.service", "SN"
		}
	};

}