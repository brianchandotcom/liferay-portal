/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.portlet.container.upload.test;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Manuel de la Peña
 */
public class TestUploadPortlet extends MVCPortlet {

	public static final String MVC_COMMAND_NAME =
		TestUploadPortlet.PORTLET_NAME + "/" + TestUploadPortlet.MVC_PATH;

	public static final String MVC_PATH = "upload_test";

	public static final String PARAMETER_NAME =
		TestUploadPortlet.class.getSimpleName();

	public static final String PORTLET_NAME =
		"com_liferay_portal_portlet_container_upload_test_TestUploadPortlet";

	public TestFileEntry get(String key) {
		return _testFileEntries.get(key);
	}

	public void put(TestFileEntry testFileEntry) {
		_testFileEntries.put(testFileEntry.toString(), testFileEntry);
	}

	private final Map<String, TestFileEntry> _testFileEntries =
		new ConcurrentHashMap<>();

}