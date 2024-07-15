/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.internal.resource.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.dto.v1_0_0.TestObject;
import com.liferay.portal.tools.rest.builder.test.resource.v1_0_0.TestObjectResource;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0_0/test-object.properties",
	scope = ServiceScope.PROTOTYPE, service = TestObjectResource.class
)
public class TestObjectResourceImpl extends BaseTestObjectResourceImpl {

	@Override
	public Page<TestObject> getTestObjectsPage() {
		return Page.of(_testObjects);
	}

	@Override
	public TestObject postTestObject(TestObject testObject) {
		_testObjects.add(testObject);

		testObject.setDateCreated(new Date());
		testObject.setDateModified(new Date());

		return testObject;
	}

	private static final List<TestObject> _testObjects = new ArrayList<>();

}