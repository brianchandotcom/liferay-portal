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

package com.liferay.portal.upgrade.v7_0_0;

/**
 * @author Roberto Díaz
 */
public class UpgradeModules
	extends com.liferay.portal.upgrade.util.UpgradeModules {

	@Override
	public String[] getBundleSymbolicNames() {
		return _bundleSymbolicNames;
	}

	@Override
	public String[][] getConvertedLegacyModules() {
		return _convertedLegacyModules;
	}

	private static final String[] _bundleSymbolicNames = new String[] {
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
		"com.liferay.exportimport.service", "com.liferay.exportimport.web",
		"com.liferay.flags.web", "com.liferay.hello.velocity.web",
		"com.liferay.hello.world.web", "com.liferay.iframe.web",
		"com.liferay.invitation.web", "com.liferay.item.selector.web",
		"com.liferay.journal.content.search.web",
		"com.liferay.journal.content.web", "com.liferay.journal.service",
		"com.liferay.journal.web", "com.liferay.layout.admin.web",
		"com.liferay.license.manager.web", "com.liferay.loan.calculator.web",
		"com.liferay.login.web", "com.liferay.message.boards.web",
		"com.liferay.mobile.device.rules.service",
		"com.liferay.mobile.device.rules.web", "com.liferay.my.account.web",
		"com.liferay.nested.portlets.web", "com.liferay.network.utilities.web",
		"com.liferay.password.generator.web", "com.liferay.plugins.admin.web",
		"com.liferay.polls.service",
		"com.liferay.portal.background.task.service",
		"com.liferay.portal.instances.web", "com.liferay.portal.lock.service",
		"com.liferay.portal.scheduler.quartz", "com.liferay.portal.search.web",
		"com.liferay.portal.settings.web",
		"com.liferay.portlet.configuration.css.web",
		"com.liferay.portlet.configuration.web", "com.liferay.quick.note.web",
		"com.liferay.ratings.page.ratings.web", "com.liferay.rss.web",
		"com.liferay.server.admin.web", "com.liferay.shopping.service",
		"com.liferay.shopping.web", "com.liferay.site.browser.web",
		"com.liferay.site.my.sites.web",
		"com.liferay.site.navigation.breadcrumb.web",
		"com.liferay.site.navigation.directory.web",
		"com.liferay.site.navigation.language.web",
		"com.liferay.site.navigation.menu.web",
		"com.liferay.site.navigation.site.map.web",
		"com.liferay.social.activities.web", "com.liferay.social.activity.web",
		"com.liferay.social.group.statistics.web",
		"com.liferay.social.requests.web",
		"com.liferay.social.user.statistics.web", "com.liferay.staging.bar.web",
		"com.liferay.translator.web", "com.liferay.trash.web",
		"com.liferay.unit.converter.web", "com.liferay.web.proxy.web",
		"com.liferay.wiki.service", "com.liferay.wiki.web",
		"com.liferay.xsl.content.web"
	};
	private static final String[][] _convertedLegacyModules = {
		{
			"calendar-portlet", "com.liferay.calendar.service", "Calendar"
		},
		{
			"kaleo-designer-portlet",
			"com.liferay.portal.workflow.kaleo.designer.web", "KaleoDesigner"
		},
		{
			"kaleo-forms-portlet",
			"com.liferay.portal.workflow.kaleo.forms.web", "KaleoForms"
		},
		{
			"kaleo-web", "com.liferay.portal.workflow.kaleo.service", "Kaleo"
		},
		{
			"marketplace-portlet", "com.liferay.marketplace.service",
			"Marketplace"
		},
		{
			"microblogs-portlet", "com.liferay.microblogs.service", "Microblogs"
		},
		{
			"so-portlet", "com.liferay.invitation.invite.members.service", "SO"
		},
		{
			"social-networking-portlet",
			"com.liferay.social.networking.service", "SN"
		}
	};

}