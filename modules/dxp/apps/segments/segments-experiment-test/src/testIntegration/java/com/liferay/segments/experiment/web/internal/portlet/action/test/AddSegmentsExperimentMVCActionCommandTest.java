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

package com.liferay.segments.experiment.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PrefsPropsImpl;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Objects;

import javax.portlet.ActionRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class AddSegmentsExperimentMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddSegmentsExperiment() throws Exception {
		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		String liferayAnalyticsURL = "http://localhost:8080/";

		PrefsPropsWrapper prefsPropsWrapper = new PrefsPropsWrapper(
			liferayAnalyticsURL, prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps", prefsPropsWrapper);

		String description = RandomTestUtil.randomString();

		SegmentsExperimentConstants.Goal goal =
			SegmentsExperimentConstants.Goal.BOUNCE_RATE;

		String name = RandomTestUtil.randomString();

		String segmentsEntryName = RandomTestUtil.randomString();

		SegmentsExperience segmentsExperience = _addSegmentsExperience(
			segmentsEntryName);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(
				description, goal.getLabel(), name, segmentsExperience);

		try {
			JSONObject jsonObject = ReflectionTestUtil.invoke(
				_mvcActionCommand, "_addSegmentsExperiment",
				new Class<?>[] {ActionRequest.class},
				mockLiferayPortletActionRequest);

			JSONObject segmentsExperimentJSONObject =
				(JSONObject)jsonObject.get("segmentsExperiment");

			Assert.assertEquals(
				0.0, segmentsExperimentJSONObject.getDouble("confidenceLevel"),
				0);
			Assert.assertEquals(
				description,
				segmentsExperimentJSONObject.getString("description"));

			SegmentsExperiment segmentsExperiment =
				_segmentsExperimentLocalService.fetchSegmentsExperiment(
					segmentsExperience.getSegmentsExperienceId(),
					segmentsExperience.getClassNameId(),
					segmentsExperience.getClassPK(),
					new int[] {
						SegmentsExperimentConstants.Status.DRAFT.getValue()
					});

			Assert.assertEquals(
				liferayAnalyticsURL + "/tests/overview/" +
					segmentsExperiment.getSegmentsExperimentKey(),
				segmentsExperimentJSONObject.getString("detailsURL"));

			Assert.assertTrue(
				segmentsExperimentJSONObject.getBoolean("editable"));
			Assert.assertEquals(
				String.valueOf(
					JSONUtil.put(
						"label", "Bounce Rate"
					).put(
						"value", goal.getLabel()
					)),
				String.valueOf(
					segmentsExperimentJSONObject.getJSONObject("goal")));
			Assert.assertEquals(
				name, segmentsExperimentJSONObject.getString("name"));
			Assert.assertEquals(
				segmentsEntryName,
				segmentsExperimentJSONObject.getString("segmentsEntryName"));
			Assert.assertEquals(
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				segmentsExperimentJSONObject.getString("segmentsExperienceId"));
			Assert.assertEquals(
				String.valueOf(segmentsExperiment.getSegmentsExperimentId()),
				segmentsExperimentJSONObject.getString("segmentsExperimentId"));
			Assert.assertEquals(
				String.valueOf(
					JSONUtil.put(
						"label", "Draft"
					).put(
						"value",
						SegmentsExperimentConstants.Status.DRAFT.getValue()
					)),
				String.valueOf(
					segmentsExperimentJSONObject.getJSONObject("status")));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);
		}
	}

	private SegmentsExperience _addSegmentsExperience(String segmentsEntryName)
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), RandomTestUtil.randomString(),
			segmentsEntryName, RandomTestUtil.randomString(), StringPool.BLANK,
			SegmentsEntryConstants.SOURCE_DEFAULT);

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		return SegmentsTestUtil.addSegmentsExperience(
			segmentsEntry.getSegmentsEntryId(), classNameId, layout.getPlid(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			String description, String goal, String name,
			SegmentsExperience segmentsExperience)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletActionRequest.setParameter(
			"classNameId", String.valueOf(segmentsExperience.getClassNameId()));
		mockLiferayPortletActionRequest.setParameter(
			"classPK", String.valueOf(segmentsExperience.getClassPK()));
		mockLiferayPortletActionRequest.setParameter(
			"description", description);
		mockLiferayPortletActionRequest.setParameter("goal", goal);
		mockLiferayPortletActionRequest.setParameter("name", name);
		mockLiferayPortletActionRequest.setParameter(
			"segmentsExperienceId",
			String.valueOf(segmentsExperience.getSegmentsExperienceId()));

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/segments_experiment/add_segments_experiment"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	private static class PrefsPropsWrapper extends PrefsPropsImpl {

		public PrefsPropsWrapper(
			String liferayAnalyticsURL, PrefsProps prefsProps) {

			_liferayAnalyticsURL = liferayAnalyticsURL;
			_prefsProps = prefsProps;
		}

		@Override
		public String getString(long companyId, String name) {
			if (Objects.equals("liferayAnalyticsFaroBackendURL", name)) {
				return null;
			}

			if (Objects.equals("liferayAnalyticsURL", name)) {
				return _liferayAnalyticsURL;
			}

			return _prefsProps.getString(companyId, name);
		}

		private final String _liferayAnalyticsURL;
		private final PrefsProps _prefsProps;

	}

}