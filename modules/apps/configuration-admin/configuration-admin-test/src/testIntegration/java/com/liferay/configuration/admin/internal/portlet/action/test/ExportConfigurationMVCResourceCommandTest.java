/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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