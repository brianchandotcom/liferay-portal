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

package com.liferay.portal.portlet.container.upload.test;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 */
@Component(immediate = true, service = Portlet.class)
public class TestUploadPortlet extends MVCPortlet {

	public static final String TEST_MVC_COMMAND_NAME =
		TestUploadPortlet.TEST_UPLOAD_PORTLET + "/" +
			TestUploadPortlet.TEST_UPLOAD_STRUTS_PATH;

	public static final String TEST_UPLOAD_FILE_NAME_PARAMETER =
		TestUploadPortlet.class.getCanonicalName();

	public static final String TEST_UPLOAD_PORTLET =
		"com_liferay_portal_portlet_container_test_upload_portlet";

	public static final String TEST_UPLOAD_STRUTS_PATH = "upload_test";

	public static TestFileEntry get(String key) {
		return _testFileEntriesMap.get(key);
	}

	public static void put(TestFileEntry testFileEntry) {
		_testFileEntriesMap.put(testFileEntry.toString(), testFileEntry);
	}

	private static final Map<String, TestFileEntry> _testFileEntriesMap =
		new HashMap<>();

}