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

package com.liferay.journal.service;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.io.InputStream;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class BaseJournalServiceTestCase {

	@Before
	public void setUp() throws Exception {
		Group group = GroupTestUtil.addGroup();

		companyId = group.getCompanyId();
		groupId = group.getGroupId();
	}

	protected String generateId() throws Exception {
		String id = RandomTestUtil.randomString();

		return StringUtil.toUpperCase(id);
	}

	protected long getCompanyGroupId() throws Exception {
		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(companyId);

		return companyGroup.getGroupId();
	}

	protected String getDefultXsd() throws Exception {
		String xsd = readText("test-journal-structure-all-fields.xml");

		return DDMXMLUtil.formatXML(xsd);
	}

	protected ServiceContext getServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return serviceContext;
	}

	protected String readText(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/portlet/journal/dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected long companyId;
	protected long groupId;

}