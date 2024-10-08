/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.constants.SiteNavigationMenuPortletKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.test.util.SiteNavigationMenuTestUtil;
import com.liferay.sites.kernel.util.Sites;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Moral
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class SiteNavigationMenuPropagationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testSiteTemplatePropagationWhenDuplicateSiteNavigationMenusExist()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		Layout prototypeLayout = LayoutTestUtil.addTypePortletLayout(
			layoutSetPrototype.getGroup(), true);

		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		_sites.updateLayoutSetPrototypesLinks(
			group, layoutSetPrototype.getLayoutSetPrototypeId(), 0, true, true);

		Layout layout = _layoutLocalService.getFriendlyURLLayout(
			group.getGroupId(), false, prototypeLayout.getFriendlyURL());

		layout.setLayoutPrototypeUuid(prototypeLayout.getUuid());
		layout.setLayoutPrototypeLinkEnabled(true);

		_layoutLocalService.updateLayout(layout);

		String name = RandomTestUtil.randomString();

		SiteNavigationMenuTestUtil.addSiteNavigationMenu(group, name);

		LayoutTestUtil.addPortletToLayout(
			prototypeLayout, SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU,
			HashMapBuilder.put(
				"siteNavigationMenuExternalReferenceCode",
				() -> {
					SiteNavigationMenu siteNavigationMenu =
						SiteNavigationMenuTestUtil.addSiteNavigationMenu(
							layoutSetPrototype.getGroup(), name);

					return new String[] {
						siteNavigationMenu.getExternalReferenceCode()
					};
				}
			).build());

		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		MergeLayoutPrototypesThreadLocal.setSkipMerge(false);

		_sites.mergeLayoutSetPrototypeLayouts(
			group, group.getPublicLayoutSet());

		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();

		UnicodeProperties layoutSetPrototypeSettingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		Assert.assertEquals(
			0,
			GetterUtil.getInteger(
				layoutSetPrototypeSettingsUnicodeProperties.getProperty(
					Sites.MERGE_FAIL_COUNT)));
	}

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private Sites _sites;

}