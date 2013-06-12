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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class JournalTemplateServiceTest extends BaseJournalServiceTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		deleteTemplates();
	}

	@Test
	public void testAddTemplate() throws Exception {
		String templateId = generateId();

		JournalTemplate template = addTemplate(
			groupId, templateId, StringPool.BLANK, "Template Content");

		Assert.assertEquals(templateId, template.getTemplateId());
	}

	@Test
	public void testCheckNewLine() throws Exception {
		String templateId = generateId();

		addTemplate(
			groupId, templateId, StringPool.BLANK, "Template \\n Content");

		JournalTemplateLocalServiceUtil.checkNewLine(groupId, templateId);

		JournalTemplate template = JournalTemplateLocalServiceUtil.getTemplate(
			groupId, templateId);

		Assert.assertFalse(template.getXsl().contains("\\n"));
	}

	@Test
	public void testCopyTemplate() throws Exception {
		String oldTemplateId = generateId();

		String xsl = "Template Content";

		addTemplate(groupId, oldTemplateId, StringPool.BLANK, xsl);

		String newTemplateId = generateId();

		JournalTemplate template = JournalTemplateLocalServiceUtil.copyTemplate(
			TestPropsValues.getUserId(), groupId, oldTemplateId, newTemplateId,
			false);

		Assert.assertEquals(newTemplateId, template.getTemplateId());

		Assert.assertEquals(xsl, template.getXsl());
	}

	@Test
	public void testDeleteTemplate() throws Exception {
		String templateId = generateId();

		JournalTemplate template = addTemplate(
			groupId, templateId, StringPool.BLANK, "Template Content");

		JournalTemplateLocalServiceUtil.deleteTemplate(template);

		boolean hasTemplate = hasTemplate(templateId, false);

		Assert.assertFalse(hasTemplate);
	}

	@Test
	public void testDeleteTemplates() throws Exception {
		addTemplate(
			groupId, generateId(), StringPool.BLANK, "Template Content 1");

		addTemplate(
			groupId, generateId(), StringPool.BLANK, "Template Content 2");

		int templatesCount = JournalTemplateLocalServiceUtil.getTemplatesCount(
			groupId);

		Assert.assertEquals(2, templatesCount);

		JournalTemplateLocalServiceUtil.deleteTemplates(groupId);

		templatesCount = JournalTemplateLocalServiceUtil.getTemplatesCount(
			groupId);

		Assert.assertEquals(0, templatesCount);
	}

	@Test
	public void testGetStructureTemplates() throws Exception {
		String structureId = generateId();

		addStructure(groupId, structureId, getDefultXsd());

		addTemplate(groupId, generateId(), structureId, "Template Content");

		List<JournalTemplate> templates =
			JournalTemplateLocalServiceUtil.getStructureTemplates(
				groupId, structureId);

		Assert.assertEquals(1, templates.size());
	}

	@Test
	public void testGetStructureTemplatesCount() throws Exception {
		String structureId = generateId();

		addStructure(groupId, structureId, getDefultXsd());

		addTemplate(groupId, generateId(), structureId, "Template Content 1");

		addTemplate(groupId, generateId(), structureId, "Template Content 2");

		int templatesCount =
			JournalTemplateLocalServiceUtil.getStructureTemplatesCount(
				groupId, structureId);

		Assert.assertEquals(2, templatesCount);
	}

	@Test
	public void testGetStructureTemplatesGlobalIncluded() throws Exception {
		String structureId = generateId();

		addStructure(getCompanyGroupId(), structureId, getDefultXsd());

		addTemplate(groupId, generateId(), structureId, "Template Content 1");

		addTemplate(
			getCompanyGroupId(), generateId(), structureId,
			"Template Content 2");

		List<JournalTemplate> templates =
			JournalTemplateLocalServiceUtil.getStructureTemplates(
				groupId, structureId, true);

		Assert.assertEquals(2, templates.size());
	}

	@Test
	public void testGetTemplate() throws Exception {
		String templateId = generateId();

		String structureId = generateId();

		addStructure(groupId, structureId, getDefultXsd());

		addTemplate(groupId, templateId, structureId, "Template Content");

		boolean hasTemplate = hasTemplate(templateId, false);

		Assert.assertTrue(hasTemplate);
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		String structureId = generateId();

		addStructure(getCompanyGroupId(), structureId, getDefultXsd());

		addTemplate(
			groupId, generateId(), structureId, "First Template",
			"Template Content 1");

		addTemplate(
			getCompanyGroupId(), generateId(), structureId, "Second Template",
			"Template Content 2");

		long[] groupIds = new long[] {getCompanyGroupId(), groupId};

		List<JournalTemplate> templates =
			JournalTemplateLocalServiceUtil.search(
				companyId, groupIds, "Template", null, StringPool.LIKE,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(2, templates.size());
	}

	@Test
	public void testSearchCountByKeywords() throws Exception {
		String structureId = generateId();

		addStructure(getCompanyGroupId(), structureId, getDefultXsd());

		addTemplate(
			groupId, generateId(), structureId, "First Template",
			"Template Content 1");

		addTemplate(
			getCompanyGroupId(), generateId(), structureId, "Second Template",
			"Template Content 2");

		long[] groupIds = new long[] {groupId};

		int templatesCount =
			JournalTemplateLocalServiceUtil.searchCount(
				companyId, groupIds, "Template", null, StringPool.LIKE);

		Assert.assertEquals(1, templatesCount);
	}

	@Test
	public void testUpdateTemplate() throws Exception {
		String structureId = generateId();

		String templateId = generateId();

		addStructure(groupId, structureId, getDefultXsd());

		addTemplate(groupId, templateId, structureId, "Template Content");

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(Locale.US, "New Test Template");

		JournalTemplate template =
			JournalTemplateLocalServiceUtil.updateTemplate(
				groupId, templateId, structureId, nameMap, null,
				"New Template Content", false, TemplateConstants.LANG_TYPE_FTL,
				true, false, null, null, getServiceContext());

		Assert.assertEquals("New Test Template", template.getName(Locale.US));

		Assert.assertEquals("New Template Content", template.getXsl());
	}

	protected void deleteTemplates() throws Exception {
		JournalTemplateLocalServiceUtil.deleteTemplates(getCompanyGroupId());

		JournalTemplateLocalServiceUtil.deleteTemplates(groupId);
	}

	protected boolean hasTemplate(
			String templateId, boolean includeGlobalStructures)
		throws Exception {

		try {
			JournalTemplateLocalServiceUtil.getTemplate(
				groupId, templateId, includeGlobalStructures);

			return true;
		}
		catch (PortalException pe) {
			return false;
		}
	}

}