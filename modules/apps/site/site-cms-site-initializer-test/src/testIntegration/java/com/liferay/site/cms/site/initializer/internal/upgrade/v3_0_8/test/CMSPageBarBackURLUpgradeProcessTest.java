/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.upgrade.v3_0_8.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

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

		FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(0);

		String layoutBackURL = "/web/guest";
		String layoutBackURLTitle = RandomTestUtil.randomString();

		String processedHTML = _getProcessedHTML(
			fragmentEntryLink, group, layoutBackURL, layoutBackURLTitle);

		Assert.assertTrue(
			processedHTML,
			processedHTML.contains(
				"aria-label=\"Go to " + layoutBackURLTitle + "\""));
		Assert.assertTrue(
			processedHTML,
			processedHTML.contains("href=\"" + layoutBackURL + "\""));

		processedHTML = _getProcessedHTML(
			fragmentEntryLink, group, layoutBackURL, null);

		Assert.assertTrue(
			processedHTML, processedHTML.contains("aria-label=\"Back\""));
		Assert.assertTrue(
			processedHTML,
			processedHTML.contains("href=\"" + layoutBackURL + "\""));

		processedHTML = _getProcessedHTML(
			fragmentEntryLink, group, null, layoutBackURLTitle);

		Assert.assertFalse(
			processedHTML,
			processedHTML.contains("href=\"" + layoutBackURL + "\""));
		Assert.assertFalse(
			processedHTML, processedHTML.contains(layoutBackURLTitle));
	}

	private String _getProcessedHTML(
			FragmentEntryLink fragmentEntryLink, Group group,
			String layoutBackURL, String layoutBackURLTitle)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_companyLocalService.getCompany(group.getCompanyId()), group,
				_layoutLocalService.getLayout(fragmentEntryLink.getPlid()));

		if (layoutBackURL != null) {
			mockHttpServletRequest.setParameter("p_l_back_url", layoutBackURL);
		}

		if (layoutBackURLTitle != null) {
			mockHttpServletRequest.setParameter(
				"p_l_back_url_title", layoutBackURLTitle);
		}

		FragmentEntryLink backLinkFragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		Matcher matcher = _backLinkPattern.matcher(fragmentEntryLink.getHtml());

		Assert.assertTrue(matcher.find());

		backLinkFragmentEntryLink.setHtml(matcher.group());

		return _fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
			backLinkFragmentEntryLink,
			new DefaultFragmentEntryProcessorContext(
				group.getCompanyId(), mockHttpServletRequest,
				new MockHttpServletResponse(), LocaleUtil.US, null,
				group.getGroupId()));
	}

	private static final Pattern _backLinkPattern = Pattern.compile(
		"\\[#assign backURL[\\s\\S]*?\\[/#if\\]");

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.upgrade.registry.SiteCMSSiteInitializerUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}