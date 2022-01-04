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

package com.liferay.configuration.admin.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class ExportConfigurationMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetFileNameForFactoryConfiguration() {
		_assertGetFileName(
			_getExpectedFileName(_FACTORY_PID, "xyz.123"), _FACTORY_PID,
			_FACTORY_PID + ".xyz.123");
	}

	@Test
	public void testGetFileNameForScopedConfiguration() {
		_assertGetFileName(
			_getExpectedFileName(_FACTORY_PID + ".scoped", "xyz.123"),
			_FACTORY_PID, _FACTORY_PID + ".scoped.xyz.123");
	}

	@Test
	public void testGetFileNameForSystemConfiguration() {
		_assertGetFileName(
			_getExpectedFileName(_FACTORY_PID, null), _FACTORY_PID,
			_FACTORY_PID);
	}

	private void _assertGetFileName(
		String expectedFileName, String factoryPid, String pid) {

		Assert.assertEquals(
			expectedFileName,
			ReflectionTestUtil.invoke(
				_mvcResourceCommand, "_getFileName",
				new Class<?>[] {String.class, String.class}, factoryPid, pid));
	}

	private String _getExpectedFileName(String pid, String subname) {
		StringBundler sb = new StringBundler(4);

		sb.append(pid);

		if (Validator.isNotNull(subname)) {
			sb.append(StringPool.TILDE);
			sb.append(subname);
		}

		sb.append(".config");

		return sb.toString();
	}

	private static final String _FACTORY_PID =
		"com.liferay.configuration.admin.TestConfiguration";

	@Inject(
		filter = "mvc.command.name=/configuration_admin/export_configuration"
	)
	private MVCResourceCommand _mvcResourceCommand;

}