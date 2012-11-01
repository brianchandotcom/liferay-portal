/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;

/**
 * @author Marcellus Tavares
 */
public class BaseJournalServiceTestCase {

	@Before
	public void setUp() throws Exception {
		companyId = TestPropsValues.getCompanyId();
		groupId = TestPropsValues.getGroupId();
		user = TestPropsValues.getUser();
	}

	protected JournalArticle addArticle(
			String content, String structureId, String templateId)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(Locale.US, "Test Article");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return JournalArticleLocalServiceUtil.addArticle(
			user.getUserId(), groupId, 0, 0, 0, StringPool.BLANK, true, 0,
			titleMap, null, content, "general", structureId, templateId, null,
			1, 1, 1965, 0, 0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, false,
			false, null, null, null, null, serviceContext);
	}

	protected JournalStructure addStrucure(String xsd) throws Exception {
		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(Locale.US, "Test Structure");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return JournalStructureLocalServiceUtil.addStructure(
			user.getUserId(), groupId, StringPool.BLANK, true, null, nameMap,
			null, xsd, serviceContext);
	}

	protected JournalTemplate addTemplate(
			String structureId, String xsl, String langType)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(Locale.US, "Test Template");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return JournalTemplateLocalServiceUtil.addTemplate(
			user.getUserId(), groupId, StringPool.BLANK, true, structureId,
			nameMap, null, xsl, true, langType, false, false, null, null,
			serviceContext);
	}

	protected long companyId;
	protected long groupId;

	protected User user;

}