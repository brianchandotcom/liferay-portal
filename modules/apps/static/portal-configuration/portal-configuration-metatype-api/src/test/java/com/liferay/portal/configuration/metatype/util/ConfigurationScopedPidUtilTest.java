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

package com.liferay.portal.configuration.metatype.util;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringBundler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class ConfigurationScopedPidUtilTest {

	@Before
	public void setUp() {
		_basePid = RandomTestUtil.randomString();
		_scopePrimKey = String.valueOf(RandomTestUtil.randomLong());
	}

	@Test
	public void testBuildScopedPid() {
		Assert.assertEquals(
			_basePid,
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				_basePid, Scope.SYSTEM, _scopePrimKey));
		Assert.assertEquals(
			_basePid,
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				_basePid, null, _scopePrimKey));
		Assert.assertEquals(
			StringBundler.concat(
				_basePid, Scope.COMPANY.getDelimiterString(), _scopePrimKey),
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				_basePid, Scope.COMPANY, _scopePrimKey));
		Assert.assertEquals(
			StringBundler.concat(
				_basePid, Scope.GROUP.getDelimiterString(), _scopePrimKey),
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				_basePid, Scope.GROUP, _scopePrimKey));
		Assert.assertEquals(
			StringBundler.concat(
				_basePid, Scope.PORTLET_INSTANCE.getDelimiterString(),
				_scopePrimKey),
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				_basePid, Scope.PORTLET_INSTANCE, _scopePrimKey));

		try {
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				null, Scope.COMPANY, _scopePrimKey);

			Assert.fail(
				"Build configuration scoped PID must not allow a null base " +
					"PID");
		}
		catch (NullPointerException npe) {
		}

		try {
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				_basePid, Scope.COMPANY, null);

			Assert.fail(
				"Build configuration scoped PID must not allow a null scope " +
					"primary key");
		}
		catch (NullPointerException npe) {
		}
	}

	@Test
	public void testGetBasePid() {
		Assert.assertEquals(null, ConfigurationScopedPidUtil.getBasePid(null));

		Assert.assertEquals(
			_basePid, ConfigurationScopedPidUtil.getBasePid(_basePid));

		String encodedPid =
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				_basePid, Scope.COMPANY, _scopePrimKey);

		Assert.assertEquals(
			_basePid, ConfigurationScopedPidUtil.getBasePid(encodedPid));

		encodedPid = ConfigurationScopedPidUtil.buildConfigurationScopedPid(
			_basePid, Scope.GROUP, _scopePrimKey);

		Assert.assertEquals(
			_basePid, ConfigurationScopedPidUtil.getBasePid(encodedPid));

		encodedPid = ConfigurationScopedPidUtil.buildConfigurationScopedPid(
			_basePid, Scope.PORTLET_INSTANCE, _scopePrimKey);

		Assert.assertEquals(
			_basePid, ConfigurationScopedPidUtil.getBasePid(encodedPid));
	}

	@Test
	public void testGetScope() {
		Assert.assertEquals(null, ConfigurationScopedPidUtil.getScope(null));

		Assert.assertEquals(
			Scope.SYSTEM, ConfigurationScopedPidUtil.getScope(_basePid));

		String encodedPid =
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				_basePid, Scope.COMPANY, _scopePrimKey);

		Assert.assertEquals(
			Scope.COMPANY, ConfigurationScopedPidUtil.getScope(encodedPid));

		encodedPid = ConfigurationScopedPidUtil.buildConfigurationScopedPid(
			_basePid, Scope.GROUP, _scopePrimKey);

		Assert.assertEquals(
			Scope.GROUP, ConfigurationScopedPidUtil.getScope(encodedPid));

		encodedPid = ConfigurationScopedPidUtil.buildConfigurationScopedPid(
			_basePid, Scope.PORTLET_INSTANCE, _scopePrimKey);

		Assert.assertEquals(
			Scope.PORTLET_INSTANCE,
			ConfigurationScopedPidUtil.getScope(encodedPid));
	}

	@Test
	public void testGetScopePK() {
		Assert.assertEquals(
			null, ConfigurationScopedPidUtil.getScopePrimKey(null));

		Assert.assertEquals(
			null, ConfigurationScopedPidUtil.getScopePrimKey(_basePid));

		String encodedPid =
			ConfigurationScopedPidUtil.buildConfigurationScopedPid(
				_basePid, Scope.COMPANY, _scopePrimKey);

		Assert.assertEquals(
			_scopePrimKey,
			ConfigurationScopedPidUtil.getScopePrimKey(encodedPid));

		encodedPid = ConfigurationScopedPidUtil.buildConfigurationScopedPid(
			_basePid, Scope.GROUP, _scopePrimKey);

		Assert.assertEquals(
			_scopePrimKey,
			ConfigurationScopedPidUtil.getScopePrimKey(encodedPid));

		encodedPid = ConfigurationScopedPidUtil.buildConfigurationScopedPid(
			_basePid, Scope.PORTLET_INSTANCE, _scopePrimKey);

		Assert.assertEquals(
			_scopePrimKey,
			ConfigurationScopedPidUtil.getScopePrimKey(encodedPid));
	}

	@Test
	public void testGetScopeSeparatorString() {
		Assert.assertEquals("__COMPANY__", Scope.COMPANY.getDelimiterString());
		Assert.assertEquals("__GROUP__", Scope.GROUP.getDelimiterString());
		Assert.assertEquals(
			"__PORTLET_INSTANCE__",
			Scope.PORTLET_INSTANCE.getDelimiterString());
		Assert.assertEquals("__SYSTEM__", Scope.SYSTEM.getDelimiterString());
	}

	private String _basePid;
	private String _scopePrimKey;

}