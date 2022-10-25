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

package com.liferay.poshi.core.util;

/**
 * @author Brian Wing Shun Chan
 */
public class PropsValues {

	public static final String ACCESSIBILITY_STANDARDS_TAGS = PropsUtil.get(
		"accessibility.standards.tags");

	public static final String BROWSER_CHROME_BIN_ARGS = PropsUtil.get(
		"browser.chrome.bin.args");

	public static final String BROWSER_CHROME_BIN_FILE = PropsUtil.get(
		"browser.chrome.bin.file");

	public static final String BROWSER_FIREFOX_BIN_FILE = PropsUtil.get(
		"browser.firefox.bin.file");

	public static final String BROWSER_TYPE = PropsUtil.get("browser.type");

	public static final String BROWSER_VERSION = PropsUtil.get(
		"browser.version");

	public static final Boolean DEBUG_STACKTRACE = GetterUtil.getBoolean(
		PropsUtil.get("debug.stacktrace"));

	public static final int GET_LOCATION_MAX_RETRIES = GetterUtil.getInteger(
		PropsUtil.get("get.location.max.retries"));

	public static final int GET_LOCATION_TIMEOUT = GetterUtil.getInteger(
		PropsUtil.get("get.location.timeout"));

	public static final String IGNORE_ERRORS = PropsUtil.get("ignore.errors");

	public static final String IGNORE_ERRORS_DELIMITER = PropsUtil.get(
		"ignore.errors.delimiter");

	public static final String IGNORE_ERRORS_FILE_NAME = PropsUtil.get(
		"ignore.errors.file.name");

	public static final Boolean LIFERAY_DATA_GUARD_ENABLED = GetterUtil.get(
		PropsUtil.get("liferay.data.guard.enabled"), false);

	public static final String LIFERAY_PORTAL_BRANCH = PropsUtil.get(
		"liferay.portal.branch");

	public static final String LIFERAY_PORTAL_BUNDLE = PropsUtil.get(
		"liferay.portal.bundle");

	public static final String LOGGER_RESOURCES_URL = PropsUtil.get(
		"logger.resources.url");

	public static final String OUTPUT_DIR_NAME = PropsUtil.get(
		"output.dir.name");

	public static final String PORTAL_URL = PropsUtil.get("portal.url");

	public static final int POSHI_FILE_READ_THREAD_POOL = GetterUtil.getInteger(
		PropsUtil.get("poshi.file.read.thread.pool"));

	public static final String PRINT_JAVA_PROCESS_ON_FAIL = PropsUtil.get(
		"print.java.process.on.fail");

	public static final String PROJECT_DIR = PropsUtil.get("project.dir");

	public static final Boolean PROXY_SERVER_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get("proxy.server.enabled"));

	public static final boolean SAVE_SCREENSHOT = GetterUtil.getBoolean(
		PropsUtil.get("save.screenshot"));

	public static final boolean SAVE_WEB_PAGE = GetterUtil.getBoolean(
		PropsUtil.get("save.web.page"));

	public static final String SELENIUM_CHROME_DRIVER_EXECUTABLE =
		PropsUtil.get("selenium.chrome.driver.executable");

	public static final String SELENIUM_DESIRED_CAPABILITIES_PLATFORM =
		PropsUtil.get("selenium.desired.capabilities.platform");

	public static final String SELENIUM_EDGE_DRIVER_EXECUTABLE = PropsUtil.get(
		"selenium.edge.driver.executable");

	public static final String SELENIUM_EXECUTABLE_DIR_NAME = PropsUtil.get(
		"selenium.executable.dir.name");

	public static final String SELENIUM_GECKO_DRIVER_EXECUTABLE = PropsUtil.get(
		"selenium.gecko.driver.executable");

	public static final String SELENIUM_IE_DRIVER_EXECUTABLE = PropsUtil.get(
		"selenium.ie.driver.executable");

	public static final boolean SELENIUM_REMOTE_DRIVER_ENABLED =
		GetterUtil.getBoolean(PropsUtil.get("selenium.remote.driver.enabled"));

	public static final String SELENIUM_REMOTE_DRIVER_URL = PropsUtil.get(
		"selenium.remote.driver.url");

	public static final String TCAT_ADMIN_REPOSITORY = PropsUtil.get(
		"tcat.admin.repository");

	public static final boolean TCAT_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get("tcat.enabled"));

	public static final boolean TEST_ASSERT_CONSOLE_ERRORS =
		GetterUtil.getBoolean(PropsUtil.get("test.assert.console.errors"));

	public static final boolean TEST_ASSERT_JAVASCRIPT_ERRORS =
		GetterUtil.getBoolean(PropsUtil.get("test.assert.javascript.errors"));

	public static final boolean TEST_ASSERT_WARNING_EXCEPTIONS =
		GetterUtil.getBoolean(PropsUtil.get("test.assert.warning.exceptions"));

	public static final String TEST_BASE_DIR_NAME = PropsUtil.get(
		"test.base.dir.name");

	public static final String TEST_BATCH_GROUP_IGNORE_REGEX = PropsUtil.get(
		"test.batch.group.ignore.regex");

	public static final int TEST_BATCH_MAX_GROUP_SIZE = GetterUtil.getInteger(
		PropsUtil.get("test.batch.max.group.size"));

	public static final int TEST_BATCH_MAX_SUBGROUP_SIZE =
		GetterUtil.getInteger(PropsUtil.get("test.batch.max.subgroup.size"));

	public static final String TEST_BATCH_PROPERTY_QUERY = PropsUtil.get(
		"test.batch.property.query");

	public static final String TEST_BATCH_RUN_TYPE = PropsUtil.get(
		"test.batch.run.type");

	public static final String TEST_CASE_AVAILABLE_PROPERTY_NAMES =
		PropsUtil.get("test.case.available.property.names");

	public static final String TEST_CASE_REQUIRED_PROPERTY_NAMES =
		PropsUtil.get("test.case.required.property.names");

	public static final String TEST_CONSOLE_LOG_FILE_NAME = PropsUtil.get(
		"test.console.log.file.name");

	public static final String TEST_CONSOLE_SHUT_DOWN_FILE_NAME = PropsUtil.get(
		"test.console.shut.down.file.name");

	public static final String[] TEST_CSV_REPORT_PROPERTY_NAMES =
		StringUtil.split(PropsUtil.get("test.csv.report.property.names"));

	public static final String TEST_DEPENDENCIES_DIR_NAME = PropsUtil.get(
		"test.dependencies.dir.name");

	public static final String[] TEST_DIRS = StringUtil.split(
		PropsUtil.get("test.dirs"));

	public static final String[] TEST_INCLUDE_DIR_NAMES = StringUtil.split(
		PropsUtil.get("test.include.dir.names"));

	public static final int TEST_JVM_MAX_RETRIES = GetterUtil.getInteger(
		PropsUtil.get("test.jvm.max.retries"));

	public static final String TEST_NAME = PropsUtil.get("test.name");

	public static final boolean TEST_POSHI_SCRIPT_VALIDATION =
		GetterUtil.getBoolean(PropsUtil.get("test.poshi.script.validation"));

	public static final String TEST_POSHI_WARNINGS_FILE_NAME = PropsUtil.get(
		"test.poshi.warnings.file.name");

	public static final int TEST_RETRY_COMMAND_WAIT_TIME =
		GetterUtil.getInteger(PropsUtil.get("test.retry.command.wait.time"));

	public static final String TEST_RUN_ENVIRONMENT = PropsUtil.get(
		"test.run.environment");

	public static final boolean TEST_RUN_LOCALLY = GetterUtil.getBoolean(
		PropsUtil.get("test.run.locally"));

	public static final boolean TEST_SKIP_TEAR_DOWN = GetterUtil.getBoolean(
		PropsUtil.get("test.skip.tear.down"));

	public static final String[] TEST_SUBREPO_DIRS = StringUtil.split(
		PropsUtil.get("test.subrepo.dirs"));

	public static final String[] TEST_SUPPORT_DIRS = StringUtil.split(
		PropsUtil.get("test.support.dirs"));

	public static final int TEST_TESTCASE_MAX_RETRIES = GetterUtil.getInteger(
		PropsUtil.get("test.testcase.max.retries"));

	public static final int TIMEOUT_EXPLICIT_WAIT = GetterUtil.getInteger(
		PropsUtil.get("timeout.explicit.wait"));

	public static final int TIMEOUT_IMPLICIT_WAIT = GetterUtil.getInteger(
		PropsUtil.get("timeout.implicit.wait"));

}