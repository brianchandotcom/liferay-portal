/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.feature.flag.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntryModel;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.props.test.util.PropsTemporarySwapper;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.dsr.site.initializer.test.util.DSRLayoutTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class DSRFeatureFlagListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void test() throws Exception {
		Group group = _groupLocalService.fetchGroup(
			TestPropsValues.getCompanyId(), GroupConstants.DSR);

		if (group != null) {
			Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
				group.getGroupId(), false, "/rooms");

			if (layout != null) {
				_layoutLocalService.deleteLayout(layout);
			}
		}

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					FeatureFlagConstants.getKey("LPD-66359"),
					Boolean.TRUE.toString())) {

			FeatureFlagTestUtil.invokeFeatureFlagListeners(
				TestPropsValues.getCompanyId(), true, "LPD-66359");

			group = _groupLocalService.fetchGroup(
				TestPropsValues.getCompanyId(), GroupConstants.DSR);

			Assert.assertNotNull(group);
			Assert.assertNotNull(
				_layoutLocalService.fetchLayoutByFriendlyURL(
					group.getGroupId(), false, "/rooms"));

			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.
					fetchLayoutSetPrototypeByUuidAndCompanyId(
						"L_DSR_LAYOUT_SET_PROTOTYPE",
						TestPropsValues.getCompanyId());

			FragmentCollection fragmentCollection =
				FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
					layoutSetPrototype.getGroupId(), "dsr");

			Assert.assertTrue(
				ArrayUtil.containsAll(
					TransformUtil.transformToArray(
						FragmentEntryLocalServiceUtil.getFragmentEntries(
							layoutSetPrototype.getGroupId(),
							fragmentCollection.getFragmentCollectionId(), 0),
						FragmentEntryModel::getName, String.class),
					new String[] {
						"Gallery Block", "Header Main", "Header User",
						"Our Team Block", "PDF Preview Block",
						"Question and Answer Block", "Text Block",
						"Timeline Block", "Video Block", "Welcome Block"
					}));

			DSRLayoutTestUtil.assertLayouts(
				layoutSetPrototype.getGroupId(),
				new String[] {"Documents", "Login", "Onboarding"}, true);
		}
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

}