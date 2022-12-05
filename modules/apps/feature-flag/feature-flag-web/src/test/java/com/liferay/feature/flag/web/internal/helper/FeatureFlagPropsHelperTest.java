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

package com.liferay.feature.flag.web.internal.helper;

import com.liferay.feature.flag.web.internal.constants.FeatureFlagConstants;
import com.liferay.feature.flag.web.internal.model.FeatureFlagStatus;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.CompanyWrapper;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsUtil;

import java.util.Set;
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
public class FeatureFlagPropsHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_companyIdThreadLocal = ReflectionTestUtil.getFieldValue(
			CompanyThreadLocal.class, "_companyId");

		com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testGetDescription() {
		String key = "ABC-123";
		String value = RandomTestUtil.randomString();

		PropsUtil.set(FeatureFlagConstants.getKey(key, "description"), value);

		withFeatureFlagPropsHelper(
			featureFlagPropsHelper -> {
				Assert.assertEquals(
					value, featureFlagPropsHelper.getDescription(key));
				Assert.assertEquals(
					StringPool.BLANK,
					featureFlagPropsHelper.getDescription("XYZ-123"));
			});
	}

	@Test
	public void testGetKeySet() {
		String validKey = "ABC-123";
		String invalidKey = "invalid";
		String absentKey = "absent";

		PropsUtil.set(
			FeatureFlagConstants.getKey(validKey), Boolean.TRUE.toString());
		PropsUtil.set(
			FeatureFlagConstants.getKey(invalidKey), Boolean.TRUE.toString());

		withFeatureFlagPropsHelper(
			featureFlagPropsHelper -> {
				Set<String> keySet = featureFlagPropsHelper.getKeySet();

				Assert.assertTrue(keySet.contains(validKey));
				Assert.assertFalse(keySet.contains(invalidKey));
				Assert.assertFalse(keySet.contains(absentKey));
			});
	}

	@Test
	public void testGetStatus() {
		String betaKey = "BETA-123";
		String devKey = "DEV-123";
		String releaseKey = "RELEASE-123";

		_setStatus(betaKey, FeatureFlagStatus.BETA);
		_setStatus(devKey, FeatureFlagStatus.DEV);
		_setStatus(releaseKey, FeatureFlagStatus.RELEASE);

		withFeatureFlagPropsHelper(
			featureFlagPropsHelper -> {
				Assert.assertEquals(
					FeatureFlagStatus.BETA,
					featureFlagPropsHelper.getStatus(betaKey));
				Assert.assertEquals(
					FeatureFlagStatus.DEV,
					featureFlagPropsHelper.getStatus(devKey));
				Assert.assertEquals(
					FeatureFlagStatus.RELEASE,
					featureFlagPropsHelper.getStatus(releaseKey));

				Assert.assertEquals(
					FeatureFlagStatus.DEV,
					featureFlagPropsHelper.getStatus("ABC-123"));
			});
	}

	@Test
	public void testGetTitle() {
		String key1 = "ABC-123";
		String value = RandomTestUtil.randomString();

		PropsUtil.set(FeatureFlagConstants.getKey(key1, "title"), value);

		withFeatureFlagPropsHelper(
			featureFlagPropsHelper -> {
				Assert.assertEquals(
					value, featureFlagPropsHelper.getTitle(key1));

				String key2 = "XYZ-123";

				Assert.assertEquals(
					key2, featureFlagPropsHelper.getTitle(key2));
			});
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

		withFeatureFlagPropsHelper(
			CompanyConstants.SYSTEM,
			featureFlagPropsHelper -> {
				Set<String> keySet = featureFlagPropsHelper.getKeySet();

				Assert.assertTrue(keySet.contains(systemKey1));
				Assert.assertTrue(keySet.contains(systemKey2));
				Assert.assertFalse(keySet.contains(company1key1));

				Assert.assertTrue(featureFlagPropsHelper.isEnabled(systemKey1));
				Assert.assertFalse(
					featureFlagPropsHelper.isEnabled(systemKey2));
				Assert.assertFalse(
					featureFlagPropsHelper.isEnabled(company1key1));
			});

		withFeatureFlagPropsHelper(
			company1.getCompanyId(),
			featureFlagPropsHelper -> {
				Set<String> keySet = featureFlagPropsHelper.getKeySet();

				Assert.assertFalse(keySet.contains(systemKey1));
				Assert.assertTrue(keySet.contains(company1key1));
				Assert.assertTrue(keySet.contains(company1key2));
				Assert.assertFalse(keySet.contains(company2key1));

				Assert.assertFalse(
					featureFlagPropsHelper.isEnabled(systemKey1));
				Assert.assertTrue(
					featureFlagPropsHelper.isEnabled(company1key1));
				Assert.assertFalse(
					featureFlagPropsHelper.isEnabled(company1key2));
			});

		withFeatureFlagPropsHelper(
			company2.getCompanyId(),
			featureFlagPropsHelper -> {
				Set<String> keySet = featureFlagPropsHelper.getKeySet();

				Assert.assertTrue(keySet.contains(systemKey1));
				Assert.assertTrue(keySet.contains(company2key1));
				Assert.assertFalse(keySet.contains(company1key1));

				Assert.assertFalse(
					featureFlagPropsHelper.isEnabled(systemKey1));
				Assert.assertTrue(
					featureFlagPropsHelper.isEnabled(company2key1));
			});
	}

	protected void withFeatureFlagPropsHelper(
		Consumer<FeatureFlagPropsHelper> featureFlagPropsHelperConsumer) {

		featureFlagPropsHelperConsumer.accept(new FeatureFlagPropsHelper());
	}

	protected void withFeatureFlagPropsHelper(
		long companyId,
		Consumer<FeatureFlagPropsHelper> featureFlagPropsHelperConsumer) {

		try (SafeCloseable safeCloseable =
				_companyIdThreadLocal.setWithSafeCloseable(companyId)) {

			withFeatureFlagPropsHelper(featureFlagPropsHelperConsumer);
		}
	}

	private void _setStatus(
		String featureFlagKey, FeatureFlagStatus featureFlagStatus) {

		PropsUtil.set(
			FeatureFlagConstants.getKey(featureFlagKey, "status"),
			featureFlagStatus.toString());
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