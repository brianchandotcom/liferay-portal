/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
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
@Sync
public class ViewRelatedAssetsSectionDisplayContextTest
	extends BaseDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAssetTagNames(
			new String[] {
				_objectDefinition.getExternalReferenceCode() + _KEYWORD_SUFFIX,
				RandomTestUtil.randomString()
			});

		_objectEntry = _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			Collections.emptyMap(), serviceContext);
	}

	@Test
	public void testGetAdditionalProps() throws Exception {
		HashMap<String, Object> additionalProps = ReflectionTestUtil.invoke(
			_getViewRelatedAssetsSectionDisplayContext(mockHttpServletRequest),
			"getAdditionalProps", new Class<?>[0]);

		Assert.assertEquals(
			_objectDefinition.getExternalReferenceCode() + _KEYWORD_SUFFIX,
			additionalProps.get("keywords"));
	}

	@Test
	public void testGetAPIURL() throws Exception {
		String apiURL = ReflectionTestUtil.invoke(
			_getViewRelatedAssetsSectionDisplayContext(mockHttpServletRequest),
			"getAPIURL", new Class<?>[0]);

		Assert.assertTrue(
			apiURL.contains(
				StringBundler.concat(
					"(cmsSection eq 'contents' or cmsSection eq 'files') and ",
					"keywords/any(k:k in ('",
					_objectDefinition.getExternalReferenceCode(),
					_KEYWORD_SUFFIX, "'))")));

		_objectEntry = _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			Collections.emptyMap(), ServiceContextTestUtil.getServiceContext());

		apiURL = ReflectionTestUtil.invoke(
			_getViewRelatedAssetsSectionDisplayContext(mockHttpServletRequest),
			"getAPIURL", new Class<?>[0]);

		Assert.assertTrue(
			apiURL.contains(
				"(cmsSection eq 'contents' or cmsSection eq 'files') and " +
					"keywords/any(k:k in (''))"));
	}

	@Test
	public void testGetCreationMenu() throws Exception {
		_depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		CreationMenu creationMenu = ReflectionTestUtil.invoke(
			_getViewRelatedAssetsSectionDisplayContext(
				getMockHttpServletRequest()),
			"getCreationMenu", new Class<?>[0]);

		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		Assert.assertEquals(dropdownItems.toString(), 2, dropdownItems.size());

		_assertDropdownItem(
			dropdownItems.get(0), "uploadMultipleFiles",
			_objectDefinition.getExternalReferenceCode() + _KEYWORD_SUFFIX,
			"Upload");

		DropdownItem dropdownItem = dropdownItems.get(1);

		_assertDropdownItem(
			dropdownItem, "selectAssets",
			_objectDefinition.getExternalReferenceCode() + _KEYWORD_SUFFIX,
			"CMS Assets");

		Map<String, Object> data = (Map<String, Object>)dropdownItem.get(
			"data");

		String searchAPIURL = (String)data.get("searchAPIURL");

		Assert.assertTrue(
			searchAPIURL.contains(
				StringBundler.concat(
					"(cmsSection eq 'contents' or cmsSection eq 'files') and ",
					"not (keywords/any(k:k in ('",
					_objectDefinition.getExternalReferenceCode(),
					_KEYWORD_SUFFIX, "'))) and objectDefinitionId gt 0")));
	}

	@Test
	public void testGetFDSActionDropdownItems() throws Exception {
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			ReflectionTestUtil.invoke(
				_getViewRelatedAssetsSectionDisplayContext(
					getMockHttpServletRequest()),
				"getFDSActionDropdownItems", new Class<?>[0]);

		Assert.assertEquals(
			fdsActionDropdownItems.toString(), 5,
			fdsActionDropdownItems.size());

		assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(0), "pencil", "actionLink", "Edit",
			"get", "item", null);
		assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(1), "view", "view-content", "View", null,
			"item", null);
		assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(2), "view", "view-file", "View", null,
			"item", null);
		assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(3), "share", "share", "Share", "get",
			"item", null);
		assertFDSActionDropdownItem(
			fdsActionDropdownItems.get(4), "chain-broken", "unlink-asset",
			"Remove from " + _objectDefinition.getLabel(LocaleUtil.US), null,
			"item", null);
	}

	private void _assertDropdownItem(
		DropdownItem dropdownItem, String expectedAction,
		String expectedKeywords, String expectedLabel) {

		Map<String, Object> data = (Map<String, Object>)dropdownItem.get(
			"data");

		Assert.assertEquals(expectedAction, data.get("action"));
		Assert.assertEquals(expectedKeywords, data.get("keywords"));

		Assert.assertEquals(expectedLabel, dropdownItem.get("label"));
	}

	private Object _getViewRelatedAssetsSectionDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception {

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM, _objectEntry);

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		return httpServletRequest.getAttribute(
			"com.liferay.site.cms.site.initializer.internal.display.context." +
				"ViewRelatedAssetsSectionDisplayContext");
	}

	private static final String _KEYWORD_SUFFIX = RandomTestUtil.randomString();

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.ViewRelatedAssetsJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	private ObjectEntry _objectEntry;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}