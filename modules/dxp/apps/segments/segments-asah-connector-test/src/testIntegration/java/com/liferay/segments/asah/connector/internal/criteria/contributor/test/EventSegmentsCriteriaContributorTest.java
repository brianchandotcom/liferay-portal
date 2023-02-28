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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.field.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
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

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			EventSegmentsCriteriaContributorTest.class);

		_serviceTracker = new ServiceTracker<>(
			bundle.getBundleContext(),
			FrameworkUtil.createFilter(
				"(component.name=com.liferay.segments.asah.connector." +
					"internal.criteria.contributor." +
						"EventSegmentsCriteriaContributor)"),
			null);

		_serviceTracker.open();
	}

	@After
	public void tearDown() {
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
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);
		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		SegmentsCriteriaContributor segmentsCriteriaContributor =
			_getSegmentsCriteriaContributor();

		List<Field> fields = segmentsCriteriaContributor.getFields(
			mockLiferayPortletRenderRequest);

		Map<String, Field> fieldsMap = new HashMap<>();

		for (Field field : fields) {
			fieldsMap.put(field.getName(), field);
		}

		Field field1 = fieldsMap.get("commentPosted");

		Assert.assertNotNull(field1);
		Assert.assertEquals("commentPosted", field1.getName());
		Assert.assertNotNull(field1.getLabel());
		Assert.assertEquals("event", field1.getType());

		Field field2 = fieldsMap.get("blogViewed");

		Assert.assertNotNull(field2);
		Assert.assertEquals("blogViewed", field2.getName());
		Assert.assertNotNull(field2.getLabel());
		Assert.assertEquals("event", field2.getType());

		Field field3 = fieldsMap.get("documentDownloaded");

		Assert.assertNotNull(field3);
		Assert.assertEquals("documentDownloaded", field3.getName());
		Assert.assertNotNull(field3.getLabel());
		Assert.assertEquals("event", field3.getType());

		Field field4 = fieldsMap.get("documentPreviewed");

		Assert.assertNotNull(field4);
		Assert.assertEquals("documentPreviewed", field4.getName());
		Assert.assertNotNull(field4.getLabel());
		Assert.assertEquals("event", field4.getType());

		Field field5 = fieldsMap.get("formSubmitted");

		Assert.assertNotNull(field5);
		Assert.assertEquals("formSubmitted", field5.getName());
		Assert.assertNotNull(field5.getLabel());
		Assert.assertEquals("event", field5.getType());

		Field field6 = fieldsMap.get("formViewed");

		Assert.assertNotNull(field6);
		Assert.assertEquals("formViewed", field6.getName());
		Assert.assertNotNull(field6.getLabel());
		Assert.assertEquals("event", field6.getType());

		Field field7 = fieldsMap.get("pageViewed");

		Assert.assertNotNull(field7);
		Assert.assertEquals("pageViewed", field7.getName());
		Assert.assertNotNull(field7.getLabel());
		Assert.assertEquals("event", field7.getType());

		Field field8 = fieldsMap.get("webContentViewed");

		Assert.assertNotNull(field8);
		Assert.assertEquals("webContentViewed", field8.getName());
		Assert.assertNotNull(field8.getLabel());
		Assert.assertEquals("event", field8.getType());
	}

	private SegmentsCriteriaContributor _getSegmentsCriteriaContributor() {
		return (SegmentsCriteriaContributor)_serviceTracker.getService();
	}

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.US);

		return themeDisplay;
	}

	private ServiceTracker<Object, Object> _serviceTracker;

}