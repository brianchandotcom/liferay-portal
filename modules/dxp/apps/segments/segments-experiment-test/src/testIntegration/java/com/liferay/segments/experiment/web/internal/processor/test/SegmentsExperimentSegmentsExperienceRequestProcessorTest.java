/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.experiment.web.internal.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PrefsPropsImpl;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.processor.SegmentsExperienceRequestProcessor;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class SegmentsExperimentSegmentsExperienceRequestProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);
	}

	@Test
	public void testGetSegmentsExperienceIds() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				TestPropsValues.getUserId(), _group.getGroupId(),
				segmentsEntry.getSegmentsEntryId(), classNameId,
				_layout.getPlid(), RandomTestUtil.randomLocaleStringMap(), 0,
				true, new UnicodeProperties(true),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		AnalyticsSyncedPrefsPropsWrapper analyticsSyncedPrefsPropsWrapper =
			new AnalyticsSyncedPrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			analyticsSyncedPrefsPropsWrapper);

		try {
			long[] segmentsExperienceIds =
				_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					_group.getGroupId(), classNameId, _layout.getPlid(),
					new long[] {segmentsExperience.getSegmentsEntryId()});

			Assert.assertEquals(
				Arrays.toString(segmentsExperienceIds), 1,
				segmentsExperienceIds.length);

			Assert.assertEquals(
				segmentsExperience.getSegmentsEntryId(),
				segmentsExperienceIds[0]);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	@Test
	public void testGetSegmentsExperienceIdsWithAnalyticsUnSyncedPrefsProps()
		throws Exception {

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		AnalyticsUnSyncedPrefsPropsWrapper analyticsUnSyncedPrefsPropsWrapper =
			new AnalyticsUnSyncedPrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			analyticsUnSyncedPrefsPropsWrapper);

		try {
			long classNameId = _classNameLocalService.getClassNameId(
				Layout.class.getName());

			long[] segmentsExperienceIds =
				_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					_group.getGroupId(), classNameId, _layout.getPlid(),
					new long[] {12345L});

			Assert.assertEquals(
				Arrays.toString(segmentsExperienceIds), 1,
				segmentsExperienceIds.length);

			Assert.assertEquals(12345L, segmentsExperienceIds[0]);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	@Test
	public void testGetSegmentsExperienceIdsWithCookie() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				TestPropsValues.getUserId(), _group.getGroupId(),
				segmentsEntry.getSegmentsEntryId(), classNameId,
				_layout.getPlid(), RandomTestUtil.randomLocaleStringMap(), 0,
				true, new UnicodeProperties(true),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		AnalyticsSyncedPrefsPropsWrapper analyticsSyncedPrefsPropsWrapper =
			new AnalyticsSyncedPrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			analyticsSyncedPrefsPropsWrapper);

		try {
			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest();

			mockHttpServletRequest.setCookies(
				new Cookie(
					"ab_test_variant_id",
					segmentsExperiment.getSegmentsExperienceKey()));

			long[] segmentsExperienceIds =
				_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
					mockHttpServletRequest, new MockHttpServletResponse(),
					_group.getGroupId(), classNameId, _layout.getPlid(),
					new long[0]);

			Assert.assertEquals(
				Arrays.toString(segmentsExperienceIds), 1,
				segmentsExperienceIds.length);

			Assert.assertEquals(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperienceIds[0]);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	@Test
	public void testGetSegmentsExperienceIdsWithoutSegmentsExperienceIds()
		throws Exception {

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		AnalyticsSyncedPrefsPropsWrapper analyticsSyncedPrefsPropsWrapper =
			new AnalyticsSyncedPrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			analyticsSyncedPrefsPropsWrapper);

		try {
			long classNameId = _classNameLocalService.getClassNameId(
				Layout.class.getName());

			long[] segmentsExperienceIds =
				_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					_group.getGroupId(), classNameId, _layout.getPlid(),
					new long[0]);

			Assert.assertEquals(
				Arrays.toString(segmentsExperienceIds), 0,
				segmentsExperienceIds.length);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	@Test
	public void testGetSegmentsExperienceIdsWithSegmentsEntryIds()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				TestPropsValues.getUserId(), _group.getGroupId(),
				segmentsEntry.getSegmentsEntryId(), classNameId,
				_layout.getPlid(), RandomTestUtil.randomLocaleStringMap(), 0,
				true, new UnicodeProperties(true),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			SegmentsExperimentConstants.STATUS_RUNNING);

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		AnalyticsSyncedPrefsPropsWrapper analyticsSyncedPrefsPropsWrapper =
			new AnalyticsSyncedPrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			analyticsSyncedPrefsPropsWrapper);

		try {
			long[] segmentsExperienceIds =
				_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
					_getMockHttpServletRequest(), new MockHttpServletResponse(),
					_group.getGroupId(), classNameId, _layout.getPlid(),
					new long[] {segmentsEntry.getSegmentsEntryId()},
					new long[] {segmentsExperience.getSegmentsEntryId()});

			Assert.assertEquals(
				Arrays.toString(segmentsExperienceIds), 1,
				segmentsExperienceIds.length);

			Assert.assertEquals(
				segmentsExperience.getSegmentsEntryId(),
				segmentsExperienceIds[0]);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	@Test
	public void testGetSegmentsExperienceIdsWithSegmentsExperienceId()
		throws Exception {

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		AnalyticsSyncedPrefsPropsWrapper analyticsSyncedPrefsPropsWrapper =
			new AnalyticsSyncedPrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			analyticsSyncedPrefsPropsWrapper);

		try {
			SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
				_group.getGroupId());

			long classNameId = _classNameLocalService.getClassNameId(
				Layout.class.getName());

			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.addSegmentsExperience(
					TestPropsValues.getUserId(), _group.getGroupId(),
					segmentsEntry.getSegmentsEntryId(), classNameId,
					_layout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
					0, true, new UnicodeProperties(true),
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest();

			mockHttpServletRequest.setParameter(
				"segmentsExperienceId",
				new String[] {
					String.valueOf(segmentsExperience.getSegmentsExperienceId())
				});

			long[] segmentsExperienceIds =
				_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
					mockHttpServletRequest, new MockHttpServletResponse(),
					_group.getGroupId(), classNameId, _layout.getPlid(),
					new long[0]);

			Assert.assertEquals(
				Arrays.toString(segmentsExperienceIds), 1,
				segmentsExperienceIds.length);

			Assert.assertEquals(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperienceIds[0]);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	@Test
	public void testGetSegmentsExperienceIdsWithSegmentsExperienceKey()
		throws Exception {

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		AnalyticsSyncedPrefsPropsWrapper analyticsSyncedPrefsPropsWrapper =
			new AnalyticsSyncedPrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			analyticsSyncedPrefsPropsWrapper);

		try {
			SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
				_group.getGroupId());

			long classNameId = _classNameLocalService.getClassNameId(
				Layout.class.getName());

			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.addSegmentsExperience(
					TestPropsValues.getUserId(), _group.getGroupId(),
					segmentsEntry.getSegmentsEntryId(), classNameId,
					_layout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
					0, true, new UnicodeProperties(true),
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest();

			mockHttpServletRequest.setParameter(
				"segmentsExperienceKey",
				new String[] {segmentsExperience.getSegmentsExperienceKey()});

			long[] segmentsExperienceIds =
				_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
					mockHttpServletRequest, new MockHttpServletResponse(),
					_group.getGroupId(), classNameId, _layout.getPlid(),
					new long[0]);

			Assert.assertEquals(
				Arrays.toString(segmentsExperienceIds), 1,
				segmentsExperienceIds.length);

			Assert.assertEquals(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperienceIds[0]);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	@Test
	public void testGetSegmentsExperienceIdsWithSegmentsExperimentKey()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				TestPropsValues.getUserId(), _group.getGroupId(),
				segmentsEntry.getSegmentsEntryId(), classNameId,
				_layout.getPlid(), RandomTestUtil.randomLocaleStringMap(), 0,
				true, new UnicodeProperties(true),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		AnalyticsSyncedPrefsPropsWrapper analyticsSyncedPrefsPropsWrapper =
			new AnalyticsSyncedPrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps",
			analyticsSyncedPrefsPropsWrapper);

		try {
			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest();

			mockHttpServletRequest.setParameter(
				"segmentsExperimentKey",
				new String[] {segmentsExperiment.getSegmentsExperimentKey()});

			long[] segmentsExperienceIds =
				_segmentsExperienceRequestProcessor.getSegmentsExperienceIds(
					mockHttpServletRequest, new MockHttpServletResponse(),
					_group.getGroupId(), classNameId, _layout.getPlid(),
					new long[0]);

			Assert.assertEquals(
				Arrays.toString(segmentsExperienceIds), 1,
				segmentsExperienceIds.length);

			Assert.assertEquals(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperienceIds[0]);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSignedIn(true);
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject(
		filter = "component.name=*.SegmentsExperimentSegmentsExperienceRequestProcessor"
	)
	private SegmentsExperienceRequestProcessor
		_segmentsExperienceRequestProcessor;

	@Inject
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	@Inject
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

	private final class AnalyticsSyncedPrefsPropsWrapper
		extends PrefsPropsImpl {

		public AnalyticsSyncedPrefsPropsWrapper(PrefsProps prefsProps) {
			_prefsProps = prefsProps;
		}

		@Override
		public boolean getBoolean(long companyId, String name) {
			if (Objects.equals("liferayAnalyticsEnableAllGroupIds", name)) {
				return true;
			}

			return _prefsProps.getBoolean(companyId, name);
		}

		@Override
		public String getString(long companyId, String name) {
			if (Objects.equals("liferayAnalyticsDataSourceId", name) ||
				Objects.equals(
					name, "liferayAnalyticsFaroBackendSecuritySignature") ||
				Objects.equals("liferayAnalyticsFaroBackendURL", name)) {

				return "test";
			}

			return _prefsProps.getString(companyId, name);
		}

		private final PrefsProps _prefsProps;

	}

	private final class AnalyticsUnSyncedPrefsPropsWrapper
		extends PrefsPropsImpl {

		public AnalyticsUnSyncedPrefsPropsWrapper(PrefsProps prefsProps) {
			_prefsProps = prefsProps;
		}

		@Override
		public boolean getBoolean(long companyId, String name) {
			if (Objects.equals("liferayAnalyticsEnableAllGroupIds", name)) {
				return false;
			}

			return _prefsProps.getBoolean(companyId, name);
		}

		@Override
		public String getString(long companyId, String name) {
			if (Objects.equals("liferayAnalyticsDataSourceId", name) ||
				Objects.equals(
					name, "liferayAnalyticsFaroBackendSecuritySignature") ||
				Objects.equals("liferayAnalyticsFaroBackendURL", name)) {

				return "test";
			}

			return _prefsProps.getString(companyId, name);
		}

		private final PrefsProps _prefsProps;

	}

}