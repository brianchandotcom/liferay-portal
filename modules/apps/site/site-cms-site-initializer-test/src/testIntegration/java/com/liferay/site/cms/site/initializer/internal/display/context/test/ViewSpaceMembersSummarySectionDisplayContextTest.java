/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Carolina Barbosa
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-58677")}
)
@RunWith(Arquillian.class)
public class ViewSpaceMembersSummarySectionDisplayContextTest
	extends BaseDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetHeaderProps() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		mockHttpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM,
			ObjectEntryTestUtil.addObjectEntry(
				depotEntry.getGroupId(),
				_objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						"L_CMS_BASIC_WEB_CONTENT",
						TestPropsValues.getCompanyId()),
				HashMapBuilder.<String, Serializable>put(
					"title_i18n",
					HashMapBuilder.put(
						"en_US", RandomTestUtil.randomString()
					).build()
				).build()));

		_assertHeaderProps(null, depotEntry.getGroup());

		DepotEntry projectDepotEntry = _depotEntryLocalService.addDepotEntry(
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), DepotConstants.TYPE_PROJECT,
			ServiceContextTestUtil.getServiceContext());

		mockHttpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM,
			ObjectEntryTestUtil.addObjectEntry(
				projectDepotEntry.getGroupId(),
				_objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						"L_CMS_BASIC_WEB_CONTENT",
						TestPropsValues.getCompanyId()),
				HashMapBuilder.<String, Serializable>put(
					"title_i18n",
					HashMapBuilder.put(
						"en_US", RandomTestUtil.randomString()
					).build()
				).build()));

		_assertHeaderProps(
			HashMapBuilder.<String, Object>put(
				"filter",
				() -> {
					List<Long> depotEntryGroupIds =
						_depotEntryLocalService.getDepotEntryGroupIds(
							group.getCompanyId(), DepotConstants.TYPE_SPACE);

					return "groupIds in (" +
						StringUtil.merge(depotEntryGroupIds) + ")";
				}
			).build(),
			projectDepotEntry.getGroup());
	}

	private void _assertHeaderProps(
			Map<String, Object> additionalSpaceModalProps, Group group)
		throws Exception {

		AssertUtils.assertEquals(
			HashMapBuilder.<String, Object>put(
				"apiURL",
				StringBundler.concat(
					"/o/headless-asset-library/v1.0/asset-libraries/",
					group.getExternalReferenceCode(),
					"/user-accounts?page=1&pageSize=8&nestedFields=roles")
			).put(
				"creationMenu", StringPool.BLANK
			).put(
				"label", "View All Members"
			).put(
				"permissions",
				HashMapBuilder.<String, Object>put(
					"hasAssignMembersPermission", true
				).build()
			).put(
				"spaceModalProps",
				HashMapBuilder.<String, Object>put(
					"action", "open-members-modal"
				).put(
					"assetLibraryCreatorUserId", TestPropsValues.getUserId()
				).put(
					"externalReferenceCode", group.getExternalReferenceCode()
				).putAll(
					additionalSpaceModalProps
				).build()
			).put(
				"title", "Members (1)"
			).put(
				"url", StringPool.BLANK
			).build(),
			ReflectionTestUtil.invoke(
				_getSectionDisplayContext(), "getHeaderProps",
				new Class<?>[0]));
	}

	private Object _getSectionDisplayContext() throws Exception {
		_fragmentRenderer.render(
			null, mockHttpServletRequest, new MockHttpServletResponse());

		return mockHttpServletRequest.getAttribute(
			"com.liferay.site.cms.site.initializer.internal.display.context." +
				"ViewSpaceMembersSummarySectionDisplayContext");
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.ViewSpaceMembersSummaryJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}