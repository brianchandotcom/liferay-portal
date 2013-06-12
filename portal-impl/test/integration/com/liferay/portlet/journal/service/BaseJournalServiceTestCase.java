/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;

/**
 * @author Marcellus Tavares
 */
@SuppressWarnings("deprecation")
public class BaseJournalServiceTestCase {

	@Before
	public void setUp() throws Exception {
		Group group = GroupTestUtil.addGroup();

		companyId = group.getCompanyId();
		groupId = group.getGroupId();
	}

	protected JournalStructure addStructure(
			long groupId, String structureId, String xsd)
		throws Exception {

		return addStructure(groupId, structureId, "Test Structure", xsd);
	}

	protected JournalStructure addStructure(
			long groupId, String structureId, String name, String xsd)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(Locale.US, name);

		return JournalStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), groupId, structureId, false, null,
			nameMap, null, xsd, getServiceContext());
	}

	protected JournalTemplate addTemplate(
			long groupId, String templateId, String structureId, String xsl)
		throws Exception {

		return addTemplate(
			groupId, templateId, structureId, "Test template", xsl);
	}

	protected JournalTemplate addTemplate(
			long groupId, String templateId, String structureId, String name,
			String xsl)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(Locale.US, name);

		return JournalTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), groupId, templateId, false,
			structureId, nameMap, null, xsl, true,
			TemplateConstants.LANG_TYPE_VM, true, false, null, null,
			getServiceContext());
	}

	protected String generateId() throws Exception {
		String id = ServiceTestUtil.randomString();

		return id.toUpperCase();
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