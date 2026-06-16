/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class GetLayoutDataMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_serviceContext = new ServiceContext();

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetLayoutData() throws Exception {
		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());

		JSONObject jsonObject = _serveResource(segmentsExperienceId);

		Assert.assertTrue(jsonObject.has("fragmentEntryLinks"));
		Assert.assertTrue(jsonObject.has("layoutData"));

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		Assert.assertTrue(layoutDataJSONObject.has("items"));
		Assert.assertTrue(layoutDataJSONObject.has("rootItems"));

		JSONObject rootItemsJSONObject = layoutDataJSONObject.getJSONObject(
			"rootItems");

		String mainItemId = rootItemsJSONObject.getString("main");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		Assert.assertTrue(itemsJSONObject.has(mainItemId));
	}

	@Test
	public void testGetLayoutDataWithFragmentEntryLink() throws Exception {
		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());

		FragmentEntryLink fragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				StringPool.BLANK, _layout, segmentsExperienceId);

		JSONObject jsonObject = _serveResource(segmentsExperienceId);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		Assert.assertTrue(
			_hasFragmentEntryLink(fragmentEntryLink, itemsJSONObject));

		JSONObject fragmentEntryLinksJSONObject = jsonObject.getJSONObject(
			"fragmentEntryLinks");

		Assert.assertTrue(
			fragmentEntryLinksJSONObject.has(
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())));
	}

	@Test
	public void testGetLayoutDataWithSegmentsExperience() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				segmentsEntry.getExternalReferenceCode(), null,
				_layout.getPlid(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				true, new UnicodeProperties(true), _serviceContext);

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());

		FragmentEntryLink fragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				StringPool.BLANK, _layout, segmentsExperienceId);

		FragmentEntryLink experienceFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				StringPool.BLANK, _layout,
				segmentsExperience.getSegmentsExperienceId());

		JSONObject jsonObject = _serveResource(segmentsExperienceId);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		Assert.assertTrue(
			_hasFragmentEntryLink(fragmentEntryLink, itemsJSONObject));
		Assert.assertFalse(
			_hasFragmentEntryLink(
				experienceFragmentEntryLink, itemsJSONObject));

		JSONObject fragmentEntryLinksJSONObject = jsonObject.getJSONObject(
			"fragmentEntryLinks");

		Assert.assertTrue(
			fragmentEntryLinksJSONObject.has(
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())));
		Assert.assertFalse(
			fragmentEntryLinksJSONObject.has(
				String.valueOf(
					experienceFragmentEntryLink.getFragmentEntryLinkId())));

		JSONObject experienceJSONObject = _serveResource(
			segmentsExperience.getSegmentsExperienceId());

		layoutDataJSONObject = experienceJSONObject.getJSONObject("layoutData");

		itemsJSONObject = layoutDataJSONObject.getJSONObject("items");

		Assert.assertTrue(
			_hasFragmentEntryLink(
				experienceFragmentEntryLink, itemsJSONObject));
		Assert.assertFalse(
			_hasFragmentEntryLink(fragmentEntryLink, itemsJSONObject));

		fragmentEntryLinksJSONObject = experienceJSONObject.getJSONObject(
			"fragmentEntryLinks");

		Assert.assertTrue(
			fragmentEntryLinksJSONObject.has(
				String.valueOf(
					experienceFragmentEntryLink.getFragmentEntryLinkId())));
		Assert.assertFalse(
			fragmentEntryLinksJSONObject.has(
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())));
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
			long segmentsExperienceId)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			ContentLayoutTestUtil.getThemeDisplay(
				_companyLocalService.fetchCompany(_group.getCompanyId()),
				_group, _layout));

		mockLiferayResourceRequest.setParameter(
			"segmentsExperienceId", String.valueOf(segmentsExperienceId));

		return mockLiferayResourceRequest;
	}

	private boolean _hasFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink, JSONObject itemsJSONObject) {

		String fragmentEntryLinkId = String.valueOf(
			fragmentEntryLink.getFragmentEntryLinkId());

		for (String itemId : itemsJSONObject.keySet()) {
			JSONObject itemJSONObject = itemsJSONObject.getJSONObject(itemId);

			JSONObject configJSONObject = itemJSONObject.getJSONObject(
				"config");

			if (configJSONObject == null) {
				continue;
			}

			if (fragmentEntryLinkId.equals(
					configJSONObject.getString("fragmentEntryLinkId"))) {

				return true;
			}
		}

		return false;
	}

	private JSONObject _serveResource(long segmentsExperienceId)
		throws Exception {

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			_getMockLiferayResourceRequest(segmentsExperienceId),
			mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONObject(
			byteArrayOutputStream.toString());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/get_layout_data"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

}