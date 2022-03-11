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

package com.liferay.layout.type.controller.collection.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRenderer;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionResponse;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Arrays;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class AddCollectionItemMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testProcessAction() throws Exception {
		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntry assetListEntry = _addAssetListEntry(_group.getGroupId());

		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			_group.getGroupId(), TestPropsValues.getUser());

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"assetListEntryId",
			new String[] {
				String.valueOf(assetListEntry.getAssetListEntryId())
			});
		mockLiferayPortletActionRequest.addParameter(
			"className", new String[] {assetEntry.getClassName()});
		mockLiferayPortletActionRequest.addParameter(
			"classPK", new String[] {String.valueOf(assetEntry.getClassPK())});

		mockLiferayPortletActionRequest.setAttribute(WebKeys.LAYOUT, _layout);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.PORTLET_ID, RandomTestUtil.randomString());
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.USER_ID, TestPropsValues.getUserId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		serviceContext.setRequest(
			mockLiferayPortletActionRequest.getHttpServletRequest());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		ServiceRegistration<AssetRendererFactory<?>>
			assetRendererFactoryServiceRegistration =
				_registerAssetRendererFactory(new TestAssetRendererFactory());

		try {
			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest, new MockActionResponse());

			long[] segmentsEntryIds =
				(long[])mockLiferayPortletActionRequest.getAttribute(
					"SEGMENTS_ENTRY_IDS");

			Assert.assertEquals(
				Arrays.toString(segmentsEntryIds), 2, segmentsEntryIds.length);
			Assert.assertEquals(
				segmentsEntry.getSegmentsEntryId(), segmentsEntryIds[0]);
			Assert.assertEquals(
				SegmentsEntryConstants.ID_DEFAULT, segmentsEntryIds[1]);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			assetRendererFactoryServiceRegistration.unregister();
		}
	}

	private AssetListEntry _addAssetListEntry(long groupId) throws Exception {
		return _assetListEntryLocalService.addAssetListEntry(
			TestPropsValues.getUserId(), groupId, RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_MANUAL,
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId()));
	}

	private SegmentsEntry _addSegmentsEntry(long groupId, User user)
		throws Exception {

		Criteria criteria = new Criteria();

		_segmentsCriteriaContributor.contribute(
			criteria, String.format("(firstName eq '%s')", user.getFirstName()),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			groupId, CriteriaSerializer.serialize(criteria),
			User.class.getName());
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private ServiceRegistration<AssetRendererFactory<?>>
		_registerAssetRendererFactory(
			AssetRendererFactory<?> assetRendererFactory) {

		Bundle bundle = FrameworkUtil.getBundle(
			AddCollectionItemMVCActionCommandTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			(Class<AssetRendererFactory<?>>)
				(Class<?>)AssetRendererFactory.class,
			assetRendererFactory, null);
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(filter = "mvc.command.name=/control_menu/add_collection_item")
	private MVCActionCommand _mvcActionCommand;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _segmentsCriteriaContributor;

	private class TestAssetRendererFactory
		extends BaseAssetRendererFactory<Object> {

		@Override
		public AssetRenderer<Object> getAssetRenderer(long classPK, int type) {
			return new BaseAssetRenderer() {

				@Override
				public Object getAssetObject() {
					return null;
				}

				@Override
				public String getClassName() {
					return AddCollectionItemMVCActionCommandTest.
						TestAssetRendererFactory.class.getName();
				}

				@Override
				public long getClassPK() {
					return RandomTestUtil.randomLong();
				}

				@Override
				public long getGroupId() {
					return _group.getGroupId();
				}

				@Override
				public String getSummary(
					PortletRequest portletRequest,
					PortletResponse portletResponse) {

					return null;
				}

				@Override
				public String getTitle(Locale locale) {
					return null;
				}

				@Override
				public long getUserId() {
					return RandomTestUtil.randomLong();
				}

				@Override
				public String getUserName() {
					return null;
				}

				@Override
				public String getUuid() {
					return null;
				}

				@Override
				public boolean include(
					HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse, String template) {

					return false;
				}

			};
		}

		@Override
		public String getClassName() {
			return TestAssetRendererFactory.class.getName();
		}

		@Override
		public String getType() {
			return "test";
		}

	}

}