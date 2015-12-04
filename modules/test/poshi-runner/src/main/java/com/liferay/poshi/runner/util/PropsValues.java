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
public class PropsValues {

	public static final String BROWSER_COMMANDS_DIR_NAME = PropsUtil.get(
		"browser.commands.dir.name");

	public static final String BROWSER_TYPE = PropsUtil.get("browser.type");

	public static final String CLUSTER_NODE_1 = PropsUtil.get("cluster.node1");

	public static final String CLUSTER_NODE_2 = PropsUtil.get("cluster.node2");

	public static final String COMPONENT_NAMES = PropsUtil.get(
		"component.names");

	public static final String EMAIL_ADDRESS_1 = PropsUtil.get(
		"email.address.1");

	public static final String EMAIL_ADDRESS_2 = PropsUtil.get(
		"email.address.2");

	public static final String EMAIL_ADDRESS_3 = PropsUtil.get(
		"email.address.3");

	public static final String EMAIL_ADDRESS_4 = PropsUtil.get(
		"email.address.4");

	public static final String EMAIL_ADDRESS_5 = PropsUtil.get(
		"email.address.5");

	public static final String EMAIL_PASSWORD_1 = PropsUtil.get(
		"email.password.1");

	public static final String EMAIL_PASSWORD_2 = PropsUtil.get(
		"email.password.2");

	public static final String EMAIL_PASSWORD_3 = PropsUtil.get(
		"email.password.3");

	public static final String EMAIL_PASSWORD_4 = PropsUtil.get(
		"email.password.4");

	public static final String EMAIL_PASSWORD_5 = PropsUtil.get(
		"email.password.5");

	public static final String[] FIXED_ISSUES = StringUtil.split(
		PropsUtil.get("fixed.issues"));

	public static final String IGNORE_ERRORS = PropsUtil.get("ignore.errors");

	public static final String IGNORE_ERRORS_DELIMITER = PropsUtil.get(
		"ignore.errors.delimiter");

	public static final String IGNORE_ERRORS_FILE_NAME = PropsUtil.get(
		"ignore.errors.file.name");

	public static final String LIFERAY_HOME = PropsUtil.get("liferay.home");

	public static final String LIFERAY_PORTAL_BRANCH = PropsUtil.get(
		"liferay.portal.branch");

	public static final String LIFERAY_PORTAL_BUNDLE = PropsUtil.get(
		"liferay.portal.bundle");

	public static final String LOGGER_RESOURCES_URL = PropsUtil.get(
		"logger.resources.url");

	public static final String MOBILE_ANDROID_HOME = PropsUtil.get(
		"mobile.android.home");

	public static final boolean MOBILE_BROWSER = GetterUtil.getBoolean(
		PropsUtil.get("mobile.browser"));

	public static final String MOBILE_DEVICE_NAME = PropsUtil.get(
		"mobile.device.name");

	public static final String OUTPUT_DIR_NAME = PropsUtil.get(
		"output.dir.name");

	public static final String PORTAL_URL = PropsUtil.get("portal.url");

	public static final String PRINT_JAVA_PROCESS_ON_FAIL = PropsUtil.get(
		"print.java.process.on.fail");

	public static final String PRODUCT_NAMES = PropsUtil.get("product.names");

	public static final String PROJECT_DIR = PropsUtil.get("project.dir");

	public static final boolean SAVE_SCREENSHOT = GetterUtil.getBoolean(
		PropsUtil.get("save.screenshot"));

	public static final boolean SAVE_SOURCE = GetterUtil.getBoolean(
		PropsUtil.get("save.source"));

	public static final boolean SAVE_WEB_PAGE = GetterUtil.getBoolean(
		PropsUtil.get("save.web.page"));

	public static final String SELENIUM_CHROME_DRIVER_EXECUTABLE =
		PropsUtil.get("selenium.chrome.driver.executable");

	public static final String SELENIUM_DESIRED_CAPABILITIES_PLATFORM =
		PropsUtil.get("selenium.desired.capabilities.platform");

	public static final String SELENIUM_DESIRED_CAPABILITIES_VERSION =
		PropsUtil.get("selenium.desired.capabilities.version");

	public static final String SELENIUM_EXECUTABLE_DIR_NAME =
		PropsUtil.get("selenium.executable.dir.name");

	public static final String SELENIUM_HOST = PropsUtil.get("selenium.host");

	public static final String SELENIUM_IE_DRIVER_EXECUTABLE =
		PropsUtil.get("selenium.ie.driver.executable");

	public static final String SELENIUM_IMPLEMENTATION = PropsUtil.get(
		"selenium.implementation");

	public static final boolean SELENIUM_LOGGER_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get("selenium.logger.enabled"));

	public static final int SELENIUM_PORT = GetterUtil.getInteger(
		PropsUtil.get("selenium.port"));

	public static final boolean SELENIUM_REMOTE_DRIVER_ENABLED =
		GetterUtil.getBoolean(PropsUtil.get("selenium.remote.driver.enabled"));

	public static final String SELENIUM_REMOTE_DRIVER_HUB =
		PropsUtil.get("selenium.remote.driver.hub");

	public static final String TCAT_ADMIN_REPOSITORY = PropsUtil.get(
		"tcat.admin.repository");

	public static final boolean TCAT_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get("tcat.enabled"));

	public static final boolean TEAR_DOWN_BEFORE_TEST = GetterUtil.getBoolean(
		PropsUtil.get("tear.down.before.test"));

	public static final boolean TEST_ASSERT_CONSOLE_ERRORS =
		GetterUtil.getBoolean(PropsUtil.get("test.assert.console.errors"));

	public static final boolean TEST_ASSERT_JAVASCRIPT_ERRORS =
		GetterUtil.getBoolean(PropsUtil.get("test.assert.javascript.errors"));

	public static final boolean TEST_ASSERT_WARNING_EXCEPTIONS =
		GetterUtil.getBoolean(PropsUtil.get("test.assert.warning.exceptions"));

	public static final String TEST_BASE_DIR_NAME = PropsUtil.get(
		"test.base.dir.name");

	public static final int TEST_BATCH_MAX_GROUP_SIZE = GetterUtil.getInteger(
		PropsUtil.get("test.batch.max.group.size"));

	public static final String[] TEST_BATCH_PROPERTY_NAMES = StringUtil.split(
		PropsUtil.get("test.batch.property.names"));

	public static final String[] TEST_BATCH_PROPERTY_VALUES = StringUtil.split(
		PropsUtil.get("test.batch.property.values"));

	public static final String TEST_CASE_AVAILABLE_PROPERTY_NAMES =
		PropsUtil.get("test.case.available.property.names");

	public static final String TEST_CASE_REQUIRED_PROPERTY_NAMES =
		PropsUtil.get("test.case.required.property.names");

	public static final String TEST_CONSOLE_LOG_FILE_NAME = PropsUtil.get(
		"test.console.log.file.name");

	public static final String TEST_CONSOLE_SHUT_DOWN_FILE_NAME = PropsUtil.get(
		"test.console.shut.down.file.name");

	public static final boolean TEST_DATABASE_MINIMAL = GetterUtil.getBoolean(
		PropsUtil.get("test.database.minimal"));

	public static final String TEST_DEPENDENCIES_DIR_NAME = PropsUtil.get(
		"test.dependencies.dir.name");

	public static final String[] TEST_INCLUDE_DIR_NAMES = StringUtil.split(
		PropsUtil.get("test.include.dir.names"));

	public static final boolean TEST_PORTAL_INSTANCE = GetterUtil.getBoolean(
		PropsUtil.get("test.portal.instance"));

	public static final String TEST_POSHI_WARNINGS_FILE_NAME = PropsUtil.get(
		"test.poshi.warnings.file.name");

	public static final boolean TEST_RUN_LOCALLY = GetterUtil.getBoolean(
		PropsUtil.get("test.run.locally"));

	public static final String TEST_NAME = PropsUtil.get("test.name");

	public static final boolean TEST_SKIP_TEAR_DOWN = GetterUtil.getBoolean(
		PropsUtil.get("test.skip.tear.down"));

	public static final boolean TESTING_CLASS_METHOD = GetterUtil.getBoolean(
		PropsUtil.get("testing.class.method"));

	public static final String[] THEME_IDS = StringUtil.split(
		PropsUtil.get("theme.ids"));

	public static final int TIMEOUT_EXPLICIT_WAIT = GetterUtil.getInteger(
		PropsUtil.get("timeout.explicit.wait"));

	public static final int TIMEOUT_IMPLICIT_WAIT = GetterUtil.getInteger(
		PropsUtil.get("timeout.implicit.wait"));

	public static final String VM_HOST = PropsUtil.get("vm.host");

}