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

package com.liferay.segments.asah.connector.internal.criteria.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.profile.PortalProfile;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.field.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class EventSegmentsCriteriaContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(_portalProfile.getClass());

		_componentDescriptionDTO =
			_serviceComponentRuntime.getComponentDescriptionDTO(
				bundle, _CLASS_NAME_EVENT_SEGMENTS_CRITERIA_CONTRIBUTOR);

		Promise<?> promise = _serviceComponentRuntime.enableComponent(
			_componentDescriptionDTO);

		promise.getValue();

		_serviceTracker = new ServiceTracker<>(
			bundle.getBundleContext(),
			FrameworkUtil.createFilter(
				"(component.name=" +
					_CLASS_NAME_EVENT_SEGMENTS_CRITERIA_CONTRIBUTOR + ")"),
			null);

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		Promise<?> promise = _serviceComponentRuntime.disableComponent(
			_componentDescriptionDTO);

		promise.getValue();

		if (_serviceTracker != null) {
			_serviceTracker.close();
		}
	}

	@Test
	public void testGetCriteriaJSONObject() throws Exception {
		SegmentsCriteriaContributor segmentsCriteriaContributor =
			_getSegmentsCriteriaContributor();

		Criteria criteria = new Criteria();

		segmentsCriteriaContributor.contribute(
			criteria,
			"(activities.filterByCount(filter='(activityKey eq " +
				"''Blog#commentPosted#606267140307242696'' and day gt " +
					"''last24Hours'')',operator='ge',value=1))",
			Criteria.Conjunction.AND);

		JSONObject jsonObject =
			segmentsCriteriaContributor.getCriteriaJSONObject(criteria);

		Assert.assertEquals(
			String.valueOf(Criteria.Conjunction.AND),
			jsonObject.getString("conjunctionId"));
		Assert.assertEquals(
			String.valueOf("event"), jsonObject.getString("propertyKey"));
		Assert.assertEquals(
			JSONUtil.put(
				"conjunctionName", String.valueOf(Criteria.Conjunction.AND)
			).put(
				"groupId", "group_0"
			).put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						"assetId", "606267140307242696"
					).put(
						"day",
						JSONUtil.put(
							"operatorName", "gt"
						).put(
							"value", "last24Hours"
						)
					).put(
						"operatorName", "ge"
					).put(
						"propertyName", "commentPosted"
					).put(
						"value", "1"
					))
			).toString(),
			jsonObject.getString("query"));
	}

	@Test
	public void testGetFields() {
		SegmentsCriteriaContributor segmentsCriteriaContributor =
			_getSegmentsCriteriaContributor();

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);
		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		Map<String, Field> fieldsMap = new HashMap<>();

		List<Field> fields = segmentsCriteriaContributor.getFields(
			mockLiferayPortletRenderRequest);

		for (Field field : fields) {
			fieldsMap.put(field.getName(), field);
		}

		for (String name :
				new String[] {
					"blogViewed", "commentPosted", "documentDownloaded",
					"documentPreviewed", "formSubmitted", "formViewed",
					"pageViewed", "webContentViewed"
				}) {

			Field field = fieldsMap.get(name);

			Assert.assertNotNull(field);
			Assert.assertNotNull(field.getLabel());
			Assert.assertEquals(name, field.getName());
			Assert.assertEquals("event", field.getType());
		}
	}

	private SegmentsCriteriaContributor _getSegmentsCriteriaContributor() {
		return (SegmentsCriteriaContributor)_serviceTracker.getService();
	}

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.US);

		return themeDisplay;
	}

	private static final String
		_CLASS_NAME_EVENT_SEGMENTS_CRITERIA_CONTRIBUTOR =
			"com.liferay.segments.asah.connector.internal.criteria." +
				"contributor.EventSegmentsCriteriaContributor";

	private static ComponentDescriptionDTO _componentDescriptionDTO;

	@Inject(
		filter = "component.name=com.liferay.segments.asah.connector.internal.portal.profile.ModulePortalProfile"
	)
	private static PortalProfile _portalProfile;

	@Inject
	private static ServiceComponentRuntime _serviceComponentRuntime;

	private static ServiceTracker<Object, Object> _serviceTracker;

}