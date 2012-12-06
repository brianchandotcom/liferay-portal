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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class BaseJournalServiceTestCase {

	protected JournalArticle addArticle(
			String content, String structureId, String templateId)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(Locale.US, "Test Article");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(TestPropsValues.getGroupId());

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(), 0, 0, 0,
			StringPool.BLANK, true, 0, titleMap, null, content, "general",
			structureId, templateId, null, 1, 1, 1965, 0, 0, 0, 0, 0, 0, 0,
			true, 0, 0, 0, 0, 0, true, false, false, null, null, null, null,
			serviceContext);
	}

	protected DDMStructure addStructure(String xsd) throws Exception {
		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(Locale.US, "Test Structure");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class.getName()), nameMap,
			null, xsd, serviceContext);
	}

	protected DDMTemplate addTemplate(long structureId, String xsl, String lang)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(Locale.US, "Test Template");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class.getName()),
			structureId, nameMap, null,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null, lang, xsl,
			serviceContext);
	}

}