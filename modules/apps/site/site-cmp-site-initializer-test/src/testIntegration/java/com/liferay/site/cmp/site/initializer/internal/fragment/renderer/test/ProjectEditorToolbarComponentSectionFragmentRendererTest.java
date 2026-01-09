/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-58677")}
)
@RunWith(Arquillian.class)
public class ProjectEditorToolbarComponentSectionFragmentRendererTest
	extends BaseComponentSectionFragmentRendererTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetProps() throws Exception {
		Assert.assertEquals(
			StringBundler.concat(
				themeDisplay.getPathFriendlyURLPublic(),
				GroupConstants.CMS_FRIENDLY_URL, "/e/project/",
				PortalUtil.getClassNameId(objectDefinition.getClassName()),
				StringPool.SLASH, objectEntry.getObjectEntryId()),
			MapUtil.getString(new HashMap<>(getProps()), "viewProjectURL"));
	}

	@Override
	protected FragmentRenderer getFragmentRenderer() {
		return _fragmentRenderer;
	}

	@Inject(
		filter = "component.name=com.liferay.site.cmp.site.initializer.internal.fragment.renderer.ProjectEditorToolbarComponentSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

}