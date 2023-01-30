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

package com.liferay.segments.asah.connector.internal.messaging.test;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.asah.connector.test.util.MockHttpUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.lang.reflect.Constructor;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class IndividualSegmentsCheckerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();

		_setIndividualSegmentsCheckerTest();
	}

	@Test
	public void testCheckIndividualSegments() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AnalyticsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"liferayAnalyticsDataSourceId", "123456789"
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

			Object asahFaroBackendClient = ReflectionTestUtil.getFieldValue(
				_individualSegmentsChecker, "_asahFaroBackendClient");

			ReflectionTestUtil.setFieldValue(
				asahFaroBackendClient, "_http",
				MockHttpUtil.geHttp(
					HashMapBuilder.
						<String, UnsafeSupplier<String, Exception>>put(
							"/api/1.0/individual-segments",
							() -> JSONUtil.put(
								"_embedded",
								JSONUtil.put(
									"individual-segments",
									JSONUtil.putAll(
										JSONUtil.put(
											"id", "1234567"
										).put(
											"name", "Test segment"
										)))
							).put(
								"page",
								JSONUtil.put(
									"number", 0
								).put(
									"size", 100
								).put(
									"totalElements", 1
								).put(
									"totalPages", 1
								)
							).put(
								"total", 0
							).toString()
						).put(
							"/api/1.0/individual-segments/1234567/individuals",
							() -> JSONUtil.put(
								"_embedded",
								JSONUtil.put(
									"individuals",
									JSONUtil.putAll(
										JSONUtil.put(
											"dataSourceIndividualPKs",
											JSONUtil.putAll(
												JSONUtil.put(
													"dataSourceId", "123456789"
												).put(
													"individualPKs",
													JSONUtil.putAll(
														_user.getUuid())
												)))))
							).put(
								"page",
								JSONUtil.put(
									"number", 0
								).put(
									"size", 100
								).put(
									"totalElements", 1
								).put(
									"totalPages", 1
								)
							).put(
								"total", 0
							).toString()
						).build()));

			ReflectionTestUtil.invoke(
				_individualSegmentsChecker, "checkIndividualSegments",
				new Class<?>[0]);

			List<SegmentsEntry> segmentsEntryList =
				_segmentsEntryLocalService.getSegmentsEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Assert.assertEquals(
				segmentsEntryList.toString(), 1, segmentsEntryList.size());

			SegmentsEntry segmentsEntry = segmentsEntryList.get(0);

			Assert.assertEquals(
				"Test segment",
				segmentsEntry.getName(LocaleUtil.getSiteDefault()));

			List<SegmentsEntryRel> segmentsEntryRelList =
				_segmentsEntryRelLocalService.getSegmentsEntryRels(
					segmentsEntry.getSegmentsEntryId());

			Assert.assertEquals(
				segmentsEntryRelList.toString(), 1,
				segmentsEntryRelList.size());

			SegmentsEntryRel segmentsEntryRel = segmentsEntryRelList.get(0);

			Assert.assertEquals(
				_user.getUserId(), segmentsEntryRel.getClassPK());
		}
	}

	private Object _getInstance(String className, Bundle bundle)
		throws Exception {

		Class<?> clazz = bundle.loadClass(className);

		Constructor<?> constructor = clazz.getConstructor();

		return constructor.newInstance();
	}

	private void _setIndividualSegmentsCheckerTest() throws Exception {
		Bundle bundle1 = FrameworkUtil.getBundle(
			IndividualSegmentsCheckerTest.class);

		BundleContext bundleContext = bundle1.getBundleContext();

		Bundle bundle2 = null;

		for (Bundle curBundle : bundleContext.getBundles()) {
			if (Objects.equals(
					curBundle.getSymbolicName(),
					"com.liferay.segments.asah.connector")) {

				bundle2 = curBundle;

				break;
			}
		}

		Assert.assertNotNull(
			"Unable to find com.liferay.segments.asah.connector bundle",
			bundle2);

		_individualSegmentsChecker = _getInstance(
			"com.liferay.segments.asah.connector.internal.messaging." +
				"IndividualSegmentsChecker",
			bundle2);

		ReflectionTestUtil.setFieldValue(
			_individualSegmentsChecker, "_analyticsSettingsManager",
			_analyticsSettingsManager);
		ReflectionTestUtil.setFieldValue(
			_individualSegmentsChecker, "_asahSegmentsEntryCache",
			_getInstance(
				"com.liferay.segments.asah.connector.internal.cache." +
					"AsahSegmentsEntryCache",
				bundle2));
		ReflectionTestUtil.setFieldValue(
			_individualSegmentsChecker, "_companyLocalService",
			_companyLocalService);
		ReflectionTestUtil.setFieldValue(
			_individualSegmentsChecker, "_moduleServiceLifecycle",
			_moduleServiceLifecycle);
		ReflectionTestUtil.setFieldValue(
			_individualSegmentsChecker, "_portal", _portal);
		ReflectionTestUtil.setFieldValue(
			_individualSegmentsChecker, "_segmentsEntryLocalService",
			_segmentsEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_individualSegmentsChecker, "_segmentsEntryRelLocalService",
			_segmentsEntryRelLocalService);
		ReflectionTestUtil.setFieldValue(
			_individualSegmentsChecker, "_userLocalService", _userLocalService);

		ReflectionTestUtil.invoke(
			_individualSegmentsChecker, "activate", new Class<?>[0]);
	}

	@Inject
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Object _individualSegmentsChecker;

	@Inject
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}