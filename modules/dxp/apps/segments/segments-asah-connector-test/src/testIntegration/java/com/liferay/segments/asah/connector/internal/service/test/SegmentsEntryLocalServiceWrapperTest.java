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

package com.liferay.segments.asah.connector.internal.service.test;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsUtil;
import com.liferay.segments.asah.connector.test.util.MockHttpUtil;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class SegmentsEntryLocalServiceWrapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-171194", "true"
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-171194", "false"
			).build());
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetSegmentsEntry() throws Exception {
		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			CriteriaSerializer.serialize(new Criteria()),
			SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND,
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getCompanyId(), _group.getGroupId(),
				TestPropsValues.getUserId()));

		String filter =
			"(activities.filterByCount(filter='(activityKey eq and " +
				"''Document#documentDownloaded#604076357016170302'' " +
					"between(day,''2028-04-29'',''2028-12-29''))'," +
						"operator='ge',value=12))";
		String name = "Test segment";

		Object asahFaroBackendClient = ReflectionTestUtil.getFieldValue(
			_segmentsEntryLocalServiceWrapper, "_asahFaroBackendClient");

		ReflectionTestUtil.setFieldValue(
			asahFaroBackendClient, "_http",
			MockHttpUtil.geHttp(
				HashMapBuilder.<String, UnsafeSupplier<String, Exception>>put(
					"/api/1.0/individual-segments/" +
						segmentsEntry1.getSegmentsEntryKey(),
					() -> JSONUtil.put(
						"filter", filter
					).put(
						"id", segmentsEntry1.getSegmentsEntryKey()
					).put(
						"name", name
					).toString()
				).build()));

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AnalyticsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"liferayAnalyticsDataSourceId",
							RandomTestUtil.randomString()
						).put(
							"liferayAnalyticsEnableAllGroupIds", true
						).put(
							"liferayAnalyticsFaroBackendSecuritySignature",
							RandomTestUtil.randomString()
						).put(
							"liferayAnalyticsFaroBackendURL",
							RandomTestUtil.randomString()
						).build(),
						SettingsFactoryUtil.getSettingsFactory())) {

			SegmentsEntry segmentsEntry2 =
				_segmentsEntryLocalService.getSegmentsEntry(
					segmentsEntry1.getSegmentsEntryId());

			Criteria criteria = segmentsEntry2.getCriteriaObj();

			Criteria.Criterion criteriaCriterion = criteria.getCriterion(
				"event");

			Assert.assertEquals(
				filter,
				StringBundler.concat(
					"(", criteriaCriterion.getFilterString(), ")"));

			Assert.assertEquals(name, name);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.segments.asah.connector.internal.service.SegmentsEntryLocalServiceWrapper"
	)
	private ServiceWrapper<SegmentsEntry> _segmentsEntryLocalServiceWrapper;

}