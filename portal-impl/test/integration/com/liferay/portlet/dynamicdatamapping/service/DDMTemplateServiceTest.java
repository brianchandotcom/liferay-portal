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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DDMTemplateServiceTest extends BaseDDMServiceTestCase {

	@Test
	@Transactional
	public void testAddTemplateWithDuplicatedKey() throws Exception {
		String templateKey = ServiceTestUtil.randomString();

		String language = DDMTemplateConstants.LANG_TYPE_VM;
		String script = getTestTemplateScript(language);
		String type = DDMTemplateConstants.TEMPLATE_TYPE_DETAIL;
		String mode = DDMTemplateConstants.TEMPLATE_MODE_CREATE;

		try {
			addTemplate(
				_testClassNameId, 0, templateKey, "Test Template 1", type, mode,
				language, script);

			addTemplate(
				_testClassNameId, 0, templateKey, "Test Template 2", type, mode,
				language, script);

			Assert.fail("Should not be able to add Template because " +
				"templateKey is duplicated");
		}
		catch (Exception e) {
		}
	}

	@Test
	@Transactional
	public void testAddTemplateWithoutName() throws Exception {
		String language = DDMTemplateConstants.LANG_TYPE_VM;
		String script = getTestTemplateScript(language);
		String type = DDMTemplateConstants.TEMPLATE_TYPE_DETAIL;
		String mode = DDMTemplateConstants.TEMPLATE_MODE_CREATE;

		try {
			addTemplate(
				_testClassNameId, 0, null, StringPool.BLANK, type, mode,
				language, script);

			Assert.fail("Should not be able to add Template because " +
				"name is empty");
		}
		catch (Exception e) {
		}
	}

	@Test
	@Transactional
	public void testAddTemplateWithoutScript() throws Exception {
		String language = DDMTemplateConstants.LANG_TYPE_VM;
		String type = DDMTemplateConstants.TEMPLATE_TYPE_DETAIL;
		String mode = DDMTemplateConstants.TEMPLATE_MODE_CREATE;

		try {
			addTemplate(
				_testClassNameId, 0, null, "Test Template", type, mode,
				language, StringPool.BLANK);

			Assert.fail("Should not be able to add Template because " +
				"script is empty");
		}
		catch (Exception e) {
		}
	}

	@Test
	@Transactional
	public void testCopyTemplates() throws Exception {
		DDMTemplate template = addListTemplate(
			_testClassNameId, 0, "Test Template");

		List<DDMTemplate> templates = copyTemplate(template);

		Assert.assertTrue(templates.size() >= 1);
	}

	@Test
	@Transactional
	public void testDeleteTemplate() throws Exception {
		DDMTemplate template = addListTemplate(
			_testClassNameId, 0, "Test Template");

		long templateId = template.getTemplateId();

		DDMTemplateLocalServiceUtil.deleteTemplate(templateId);

		DDMTemplate fetchTemplate =
			DDMTemplateLocalServiceUtil.fetchDDMTemplate(templateId);

		Assert.assertNull(fetchTemplate);
	}

	@Test
	@Transactional
	public void testFetchTemplate() throws Exception {
		DDMTemplate template = addListTemplate(
			_testClassNameId, 0, "Test Template");

		DDMTemplate fetchTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
			template.getGroupId(), template.getTemplateKey());

		Assert.assertNotNull(fetchTemplate);
	}

	@Test
	@Transactional
	public void testGetTemplates() throws Exception {
		DDMTemplate template = addListTemplate(
			_testClassNameId, 0, "Test Template");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.getTemplates(
			template.getGroupId(), template.getClassNameId());

		Assert.assertTrue(templates.contains(template));
	}

	@Test
	@Transactional
	public void testSearch() throws Exception {
		DDMTemplate template = addListTemplate(
			_testClassNameId, 0, "Test Template 1");

		addListTemplate(_testClassNameId, 0, "Test Template 2");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.search(
			template.getCompanyId(), template.getGroupId(),
			template.getClassNameId(), template.getClassPK(),
			template.getName(), template.getDescription(), template.getType(),
			template.getMode(), template.getLanguage(), false, 0, 1, null);

		Assert.assertTrue(templates.size() == 1);
	}

	@Test
	@Transactional
	public void testSearchByKeywords() throws Exception {
		DDMTemplate template = addListTemplate(
			_testClassNameId, 0, "Test Template 1");

		addListTemplate(_testClassNameId, 0, "Test Template 2");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.search(
			template.getCompanyId(), template.getGroupId(),
			template.getClassNameId(), template.getClassPK(), null,
			template.getType(), template.getMode(), 0, 1, null);

		Assert.assertTrue(templates.size() == 1);
	}

	@Test
	@Transactional
	public void testSearchCount() throws Exception {
		DDMTemplate template = addListTemplate(
			_testClassNameId, 0, "Test Template");

		int count = DDMTemplateLocalServiceUtil.searchCount(
			template.getCompanyId(), template.getGroupId(),
			template.getClassNameId(), template.getClassPK(),
			template.getName(), template.getDescription(), template.getType(),
			template.getMode(), template.getLanguage(), false);

		Assert.assertTrue(count > 0);
	}

	@Test
	@Transactional
	public void testSearchCountByKeywords() throws Exception {
		DDMTemplate template = addListTemplate(
			_testClassNameId, 0, "Test Template");

		int count = DDMTemplateLocalServiceUtil.searchCount(
			template.getCompanyId(), template.getGroupId(),
			template.getClassNameId(), template.getClassPK(),
			template.getName(), template.getType(), template.getMode());

		Assert.assertTrue(count > 0);
	}

	protected List<DDMTemplate> copyTemplate(DDMTemplate template)
		throws Exception {

		long newClassPK = -1;

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return DDMTemplateLocalServiceUtil.copyTemplates(
			template.getUserId(), template.getClassNameId(),
			template.getClassPK(), newClassPK, template.getType(),
			serviceContext);
	}

	protected DDMTemplate updateTemplate(DDMTemplate template)
			throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return DDMTemplateLocalServiceUtil.updateTemplate(
			template.getTemplateId(), template.getNameMap(),
			template.getDescriptionMap(), template.getType(),
			template.getMode(), template.getLanguage(), template.getScript(),
			serviceContext);
	}

	private long _testClassNameId = PortalUtil.getClassNameId(AssetEntry.class);

}