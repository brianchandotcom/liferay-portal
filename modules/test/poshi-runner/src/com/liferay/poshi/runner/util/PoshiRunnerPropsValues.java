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

package com.liferay.poshi.runner.util;

/**
 * @author Brian Wing Shun Chan
 */
public class PoshiRunnerPropsValues {

	public static final String BROWSER_COMMANDS_DIR_NAME =
		PoshiRunnerPropsUtil.get("browser.commands.dir");

	public static final String BROWSER_TYPE = PoshiRunnerPropsUtil.get(
		"browser.type");

	public static final String CLUSTER_NODE_1 = PoshiRunnerPropsUtil.get(
		"cluster.node1");

	public static final String CLUSTER_NODE_2 = PoshiRunnerPropsUtil.get(
		"cluster.node2");

	public static final String EMAIL_ADDRESS_1 = PoshiRunnerPropsUtil.get(
		"email.address.1");

	public static final String EMAIL_ADDRESS_2 = PoshiRunnerPropsUtil.get(
		"email.address.2");

	public static final String EMAIL_ADDRESS_3 = PoshiRunnerPropsUtil.get(
		"email.address.3");

	public static final String EMAIL_ADDRESS_4 = PoshiRunnerPropsUtil.get(
		"email.address.4");

	public static final String EMAIL_ADDRESS_5 = PoshiRunnerPropsUtil.get(
		"email.address.5");

	public static final String EMAIL_PASSWORD_1 = PoshiRunnerPropsUtil.get(
		"email.password.1");

	public static final String EMAIL_PASSWORD_2 = PoshiRunnerPropsUtil.get(
		"email.password.2");

	public static final String EMAIL_PASSWORD_3 = PoshiRunnerPropsUtil.get(
		"email.password.3");

	public static final String EMAIL_PASSWORD_4 = PoshiRunnerPropsUtil.get(
		"email.password.4");

	public static final String EMAIL_PASSWORD_5 = PoshiRunnerPropsUtil.get(
		"email.password.5");

	public static final String[] FIXED_ISSUES = StringUtil.split(
		PoshiRunnerPropsUtil.get("fixed.issues"));

	public static final String IGNORE_ERRORS = PoshiRunnerPropsUtil.get(
		"ignore.errors");

	public static final String IGNORE_ERRORS_DELIMITER =
		PoshiRunnerPropsUtil.get("ignore.errors.delimiter");

	public static final String LIFERAY_PORTAL_BRANCH = PoshiRunnerPropsUtil.get(
		"liferay.portal.branch");

	public static final String LIFERAY_PORTAL_BUNDLE = PoshiRunnerPropsUtil.get(
		"liferay.portal.bundle");

	public static final boolean MOBILE_DEVICE_ENABLED = GetterUtil.getBoolean(
		PoshiRunnerPropsUtil.get("mobile.device.enabled"));

	public static final String MOBILE_DEVICE_RESOLUTION =
		PoshiRunnerPropsUtil.get("mobile.device.resolution");

	public static final String MOBILE_DEVICE_USER_AGENT =
		PoshiRunnerPropsUtil.get("mobile.device.user.agent");

	public static final String OUTPUT_DIR_NAME = PoshiRunnerPropsUtil.get(
		"output.dir");

	public static final String PORTAL_URL = PoshiRunnerPropsUtil.get(
		"portal.url");

	public static final boolean SAVE_SCREENSHOT = GetterUtil.getBoolean(
		PoshiRunnerPropsUtil.get("save.screenshot"));

	public static final boolean SAVE_SOURCE = GetterUtil.getBoolean(
		PoshiRunnerPropsUtil.get("save.source"));

	public static final String SELENIUM_EXECUTABLE_DIR_NAME =
		PoshiRunnerPropsUtil.get("selenium.executable.dir");

	public static final String SELENIUM_HOST = PoshiRunnerPropsUtil.get(
		"selenium.host");

	public static final String SELENIUM_IMPLEMENTATION =
		PoshiRunnerPropsUtil.get("selenium.implementation");

	public static final boolean SELENIUM_LOGGER_ENABLED = GetterUtil.getBoolean(
		PoshiRunnerPropsUtil.get("selenium.logger.enabled"));

	public static final int SELENIUM_PORT = GetterUtil.getInteger(
		PoshiRunnerPropsUtil.get("selenium.port"));

	public static final String TCAT_ADMIN_REPOSITORY = PoshiRunnerPropsUtil.get(
		"tcat.admin.repository");

	public static final boolean TCAT_ENABLED = GetterUtil.getBoolean(
		PoshiRunnerPropsUtil.get("tcat.enabled"));

	public static final boolean TEAR_DOWN_BEFORE_TEST = GetterUtil.getBoolean(
		PoshiRunnerPropsUtil.get("tear.down.before.test"));

	public static final boolean TEST_ASSERT_JAVASCRIPT_ERRORS =
		GetterUtil.getBoolean(
			PoshiRunnerPropsUtil.get("test.assert.javascript.errors"));

	public static final boolean TEST_ASSERT_LIFERAY_ERRORS =
		GetterUtil.getBoolean(
			PoshiRunnerPropsUtil.get("test.assert.liferay.errors"));

	public static final String TEST_BASE_DIR_NAME = PoshiRunnerPropsUtil.get(
		"test.basedir");

	public static final boolean TEST_DATABASE_MINIMAL = GetterUtil.getBoolean(
		PoshiRunnerPropsUtil.get("test.database.minimal"));

	public static final boolean TEST_SKIP_TEAR_DOWN = GetterUtil.getBoolean(
		PoshiRunnerPropsUtil.get("test.skip.tear.down"));

	public static final boolean TESTING_CLASS_METHOD = GetterUtil.getBoolean(
		PoshiRunnerPropsUtil.get("testing.class.method"));

	public static final String[] THEME_IDS = StringUtil.split(
		PoshiRunnerPropsUtil.get("theme.ids"));

	public static final int TIMEOUT_EXPLICIT_WAIT = GetterUtil.getInteger(
		PoshiRunnerPropsUtil.get("timeout.explicit.wait"));

	public static final int TIMEOUT_IMPLICIT_WAIT = GetterUtil.getInteger(
		PoshiRunnerPropsUtil.get("timeout.implicit.wait"));

	public static final String VM_HOST = PoshiRunnerPropsUtil.get("vm.host");

}