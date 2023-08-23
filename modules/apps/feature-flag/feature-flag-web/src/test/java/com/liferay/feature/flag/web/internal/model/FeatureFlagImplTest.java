/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.FeatureFlagType;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.CompanyWrapper;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsUtil;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
@NewEnv(type = NewEnv.Type.JVM)
public class FeatureFlagImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());

		_companyIdThreadLocal = ReflectionTestUtil.getFieldValue(
			CompanyThreadLocal.class, "_companyId");
	}

	@Test
	public void testGetDependencyKeys() {
		String key = "ABC-123";
		String value = "ABC-456,XYZ-789";

		withFeatureFlag(
			featureFlag -> Assert.assertArrayEquals(
				new String[0], featureFlag.getDependencyKeys()),
			key);

		PropsUtil.set(FeatureFlagConstants.getKey(key, "dependencies"), value);

		withFeatureFlag(
			featureFlag -> Assert.assertArrayEquals(
				StringUtil.split(value), featureFlag.getDependencyKeys()),
			key);
	}

	@Test
	public void testGetDescription() {
		String key = "ABC-123";
		String value = RandomTestUtil.randomString();

		withFeatureFlag(
			featureFlag -> Assert.assertEquals(
				GetterUtil.DEFAULT_STRING, featureFlag.getDescription(null)),
			key);

		PropsUtil.set(FeatureFlagConstants.getKey(key, "description"), value);

		withFeatureFlag(
			featureFlag -> Assert.assertEquals(
				value, featureFlag.getDescription(null)),
			key);
	}

	@Test
	public void testGetTitle() {
		String key = "ABC-123";
		String value = RandomTestUtil.randomString();

		withFeatureFlag(
			featureFlag -> Assert.assertEquals(key, featureFlag.getTitle(null)),
			key);

		PropsUtil.set(FeatureFlagConstants.getKey(key, "title"), value);

		withFeatureFlag(
			featureFlag -> Assert.assertEquals(
				value, featureFlag.getTitle(null)),
			key);
	}

	@Test
	public void testGetType() {
		String betaKey = "BETA-123";
		String devKey = "DEV-123";
		String releaseKey = "RELEASE-123";

		_setType(betaKey, FeatureFlagType.BETA);
		_setType(devKey, FeatureFlagType.DEV);
		_setType(releaseKey, FeatureFlagType.RELEASE);

		withFeatureFlag(
			featureFlag -> Assert.assertEquals(
				FeatureFlagType.BETA, featureFlag.getFeatureFlagType()),
			betaKey);
		withFeatureFlag(
			featureFlag -> Assert.assertEquals(
				FeatureFlagType.DEV, featureFlag.getFeatureFlagType()),
			devKey);
		withFeatureFlag(
			featureFlag -> Assert.assertEquals(
				FeatureFlagType.RELEASE, featureFlag.getFeatureFlagType()),
			releaseKey);

		withFeatureFlag(
			featureFlag -> Assert.assertEquals(
				FeatureFlagType.DEV, featureFlag.getFeatureFlagType()),
			"ABC-123");
	}

	@NewEnv.JVMArgsLine("-Dcompany-id-properties=true")
	@Test
	public void testIsEnabled() {
		String systemKey1 = "ABC-123";
		String systemKey2 = "ABC-456";

		PropsUtil.set(
			FeatureFlagConstants.getKey(systemKey1), Boolean.TRUE.toString());
		PropsUtil.set(
			FeatureFlagConstants.getKey(systemKey2), Boolean.FALSE.toString());

		Company company1 = new TestCompany(1);
		String company1key1 = "DEF-123";
		String company1key2 = "DEF-456";

		PropsUtil.set(
			company1, FeatureFlagConstants.getKey(company1key1),
			Boolean.TRUE.toString());
		PropsUtil.set(
			company1, FeatureFlagConstants.getKey(company1key2),
			Boolean.FALSE.toString());

		Company company2 = new TestCompany(2);
		String company2key1 = "XYZ-123";

		PropsUtil.set(
			company2, FeatureFlagConstants.getKey(systemKey1),
			Boolean.FALSE.toString());
		PropsUtil.set(
			company2, FeatureFlagConstants.getKey(company2key1),
			Boolean.TRUE.toString());

		withFeatureFlag(
			CompanyConstants.SYSTEM,
			featureFlag -> Assert.assertTrue(featureFlag.isEnabled()),
			systemKey1);
		withFeatureFlag(
			CompanyConstants.SYSTEM,
			featureFlag -> Assert.assertFalse(featureFlag.isEnabled()),
			systemKey2);
		withFeatureFlag(
			CompanyConstants.SYSTEM,
			featureFlag -> Assert.assertFalse(featureFlag.isEnabled()),
			company1key1);

		withFeatureFlag(
			company1.getCompanyId(),
			featureFlag -> Assert.assertFalse(featureFlag.isEnabled()),
			systemKey1);
		withFeatureFlag(
			company1.getCompanyId(),
			featureFlag -> Assert.assertTrue(featureFlag.isEnabled()),
			company1key1);
		withFeatureFlag(
			company1.getCompanyId(),
			featureFlag -> Assert.assertFalse(featureFlag.isEnabled()),
			company1key2);

		withFeatureFlag(
			company2.getCompanyId(),
			featureFlag -> Assert.assertFalse(featureFlag.isEnabled()),
			systemKey1);
		withFeatureFlag(
			company2.getCompanyId(),
			featureFlag -> Assert.assertTrue(featureFlag.isEnabled()),
			company2key1);
	}

	protected void withFeatureFlag(
		Consumer<FeatureFlag> consumer, String featureFlagKey) {

		consumer.accept(new FeatureFlagImpl(featureFlagKey));
	}

	protected void withFeatureFlag(
		long companyId, Consumer<FeatureFlag> consumer, String featureFlagKey) {

		try (SafeCloseable safeCloseable =
				_companyIdThreadLocal.setWithSafeCloseable(companyId)) {

			withFeatureFlag(consumer, featureFlagKey);
		}
	}

	private void _setType(
		String featureFlagKey, FeatureFlagType featureFlagType) {

		PropsUtil.set(
			FeatureFlagConstants.getKey(featureFlagKey, "type"),
			featureFlagType.toString());
	}

	private CentralizedThreadLocal<Long> _companyIdThreadLocal;

	private static class TestCompany extends CompanyWrapper {

		public TestCompany(long companyId) {
			super(null);

			_companyId = companyId;
		}

		@Override
		public long getCompanyId() {
			return _companyId;
		}

		@Override
		public String getWebId() {
			return String.valueOf(_companyId);
		}

		private final long _companyId;

	}

}