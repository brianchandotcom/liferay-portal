/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class UnlinkAssetStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testExecute() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		String keyword1 = RandomTestUtil.randomString();
		String keyword2 = RandomTestUtil.randomString();
		String keyword3 = RandomTestUtil.randomString();
		String keyword4 = RandomTestUtil.randomString();

		serviceContext.setAssetTagNames(
			new String[] {keyword1, keyword2, keyword3, keyword4});

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0, null,
			Collections.emptyMap(), serviceContext);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			new ThemeDisplay() {
				{
					setSiteDefaultLocale(LocaleUtil.US);
					setUser(TestPropsValues.getUser());
				}
			});
		mockHttpServletRequest.setParameter(
			"keywords", StringUtil.merge(new String[] {keyword1, keyword2}));
		mockHttpServletRequest.setParameter(
			"objectEntryId", String.valueOf(objectEntry.getObjectEntryId()));

		_unlinkAssetStrutsAction.execute(
			mockHttpServletRequest, new MockHttpServletResponse());

		AssertUtils.assertEqualsSorted(
			new String[] {keyword3, keyword4},
			_assetTagLocalService.getTagNames(
				objectDefinition.getClassName(),
				objectEntry.getObjectEntryId()));
	}

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject(filter = "path=/cms/unlink_asset")
	private StrutsAction _unlinkAssetStrutsAction;

}