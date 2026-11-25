/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.upgrade.v3_0_8.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class CMSPageBarBackURLUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		Group group = _groupLocalService.getGroup(
			TestPropsValues.getCompanyId(), GroupConstants.CMS);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				group.getGroupId(), "page-bar");

		String html = fragmentEntry.getHtml();

		FragmentEntry draftFragmentEntry = _fragmentEntryLocalService.getDraft(
			fragmentEntry);

		draftFragmentEntry.setHtml(
			html.replaceFirst(
				"\\n\\t*\\[#assign backURL[\\s\\S]*?\\[/#if\\]", ""));

		fragmentEntry = _fragmentEntryLocalService.publishDraft(
			_fragmentEntryLocalService.updateFragmentEntry(draftFragmentEntry));

		String fragmentEntryHTML = fragmentEntry.getHtml();

		Assert.assertFalse(
			fragmentEntryHTML, fragmentEntryHTML.contains("p_l_back_url"));

		for (FragmentEntryLink fragmentEntryLink :
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinksByFragmentEntry(
						group.getGroupId(), fragmentEntry)) {

			_fragmentEntryLinkLocalService.updateLatestChanges(
				fragmentEntry, fragmentEntryLink);
		}

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.site.cms.site.initializer.internal.upgrade.v3_0_8." +
				"CMSPageBarBackURLUpgradeProcess");

		upgradeProcess.upgrade();

		fragmentEntry = _fragmentEntryLocalService.fetchFragmentEntry(
			group.getGroupId(), "page-bar");

		Assert.assertEquals(html, fragmentEntry.getHtml());

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByFragmentEntry(
				group.getGroupId(), fragmentEntry);

		Assert.assertFalse(fragmentEntryLinks.isEmpty());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			String fragmentEntryLinkHTML = fragmentEntryLink.getHtml();

			Assert.assertTrue(
				fragmentEntryLinkHTML,
				fragmentEntryLinkHTML.contains("p_l_back_url"));
		}
	}

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.upgrade.registry.SiteCMSSiteInitializerUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}