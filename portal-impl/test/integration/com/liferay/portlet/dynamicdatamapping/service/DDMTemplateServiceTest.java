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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.dynamicdatamapping.RequiredTemplateException;
import com.liferay.portlet.dynamicdatamapping.TemplateDuplicateTemplateKeyException;
import com.liferay.portlet.dynamicdatamapping.TemplateNameException;
import com.liferay.portlet.dynamicdatamapping.TemplateScriptException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 */
@Sync
public class DDMTemplateServiceTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_classNameId = PortalUtil.getClassNameId(AssetEntry.class);
	}

	@Test
	public void testAddTemplateWithDuplicateKey() throws Exception {
		String templateKey = RandomTestUtil.randomString();
		String language = TemplateConstants.LANG_TYPE_VM;

		try {
			addTemplate(
				_classNameId, 0, templateKey, "Test Template 1",
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
				DDMTemplateConstants.TEMPLATE_MODE_CREATE, language,
				getTestTemplateScript(language));
			addTemplate(
				_classNameId, 0, templateKey, "Test Template 2",
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
				DDMTemplateConstants.TEMPLATE_MODE_CREATE, language,
				getTestTemplateScript(language));

			Assert.fail();
		}
		catch (TemplateDuplicateTemplateKeyException tdtke) {
		}
	}

	@Test
	public void testAddTemplateWithoutName() throws Exception {
		String language = TemplateConstants.LANG_TYPE_VM;

		try {
			addTemplate(
				_classNameId, 0, null, StringPool.BLANK,
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
				DDMTemplateConstants.TEMPLATE_MODE_CREATE, language,
				getTestTemplateScript(language));

			Assert.fail();
		}
		catch (TemplateNameException tne) {
		}
	}

	@Test
	public void testAddTemplateWithoutScript() throws Exception {
		try {
			addTemplate(
				_classNameId, 0, null, "Test Template",
				DDMTemplateConstants.TEMPLATE_TYPE_FORM,
				DDMTemplateConstants.TEMPLATE_MODE_CREATE,
				TemplateConstants.LANG_TYPE_VM, StringPool.BLANK);

			Assert.fail();
		}
		catch (TemplateScriptException tse) {
		}
	}

	@Test
	public void testCopyTemplates() throws Exception {
		int initialCount = DDMTemplateLocalServiceUtil.getTemplatesCount(
			group.getGroupId(), _classNameId, 0);

		DDMTemplate template = addDisplayTemplate(
			_classNameId, 0, "Test Template");

		copyTemplate(template);

		int count = DDMTemplateLocalServiceUtil.getTemplatesCount(
			group.getGroupId(), _classNameId, 0);

		Assert.assertEquals(initialCount + 2, count);
	}

	@Test
	public void testDeleteTemplate() throws Exception {
		DDMTemplate template = addDisplayTemplate(
			_classNameId, 0, "Test Template");

		DDMTemplateLocalServiceUtil.deleteTemplate(template.getTemplateId());

		Assert.assertNull(
			DDMTemplateLocalServiceUtil.fetchDDMTemplate(
				template.getTemplateId()));
	}

	@Test
	public void testDeleteTemplateReferencedByJournalArticles()
		throws Exception {

		DDMStructure structure = addStructure(
			PortalUtil.getClassNameId(JournalArticle.class.getName()),
			"Test Structure");

		DDMTemplate template = addDisplayTemplate(
			structure.getPrimaryKey(), "Test Display Template");

		JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), "<title>Test Article</title>",
			structure.getStructureKey(), template.getTemplateKey());

		try {
			DDMTemplateLocalServiceUtil.deleteTemplate(
				template.getTemplateId());

			Assert.fail();
		}
		catch (RequiredTemplateException rse) {
		}
	}

	@Test
	public void testFetchTemplate() throws Exception {
		DDMTemplate template = addDisplayTemplate(
			_classNameId, 0, "Test Template");

		Assert.assertNotNull(
			DDMTemplateLocalServiceUtil.fetchTemplate(
				template.getGroupId(), _classNameId,
				template.getTemplateKey()));
	}

	@Test
	public void testGetTemplates() throws Exception {
		DDMTemplate template = addDisplayTemplate(
			_classNameId, 0, "Test Template");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.getTemplates(
			template.getGroupId(), template.getClassNameId());

		Assert.assertTrue(templates.contains(template));
	}

	@Test
	public void testSearch() throws Exception {
		DDMTemplate template = addDisplayTemplate(
			_classNameId, 0, "Test Template 1");

		addDisplayTemplate(_classNameId, 0, "Test Template 2");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.search(
			template.getCompanyId(), template.getGroupId(),
			template.getClassNameId(), template.getClassPK(),
			template.getName(), template.getDescription(), template.getType(),
			template.getMode(), template.getLanguage(), false, 0, 1, null);

		Assert.assertEquals(1, templates.size());
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		DDMTemplate template = addDisplayTemplate(
			_classNameId, 0, "Test Template 1");

		addDisplayTemplate(_classNameId, 0, "Test Template 2");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.search(
			template.getCompanyId(), template.getGroupId(),
			template.getClassNameId(), template.getClassPK(), null,
			template.getType(), template.getMode(), 0, 1, null);

		Assert.assertEquals(1, templates.size());
	}

	@Test
	public void testSearchCount() throws Exception {
		int initialCount = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), group.getGroupId(), _classNameId, 0,
			"Test Template", null, null, null, null, false);

		addDisplayTemplate(_classNameId, 0, "Test Template");

		int count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), group.getGroupId(), _classNameId, 0,
			"Test Template", null, null, null, null, false);

		Assert.assertEquals(initialCount + 1, count);
	}

	@Test
	public void testSearchCountByKeywords() throws Exception {
		int initialCount = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), group.getGroupId(), _classNameId, 0,
			null, null, null);

		addDisplayTemplate(_classNameId, 0, "Test Template");

		int count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), group.getGroupId(), _classNameId, 0,
			null, null, null);

		Assert.assertEquals(initialCount + 1, count);
	}

	protected DDMTemplate copyTemplate(DDMTemplate template) throws Exception {
		return DDMTemplateLocalServiceUtil.copyTemplate(
			template.getUserId(), template.getTemplateId(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	protected DDMTemplate updateTemplate(DDMTemplate template)
		throws Exception {

		return DDMTemplateLocalServiceUtil.updateTemplate(
			template.getTemplateId(), template.getClassPK(),
			template.getNameMap(), template.getDescriptionMap(),
			template.getType(), template.getMode(), template.getLanguage(),
			template.getScript(), template.isCacheable(),
			template.isSmallImage(), template.getSmallImageURL(), null,
			ServiceContextTestUtil.getServiceContext());
	}

	private long _classNameId;

}