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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.dynamicdatamapping.TemplateDuplicateTemplateKeyException;
import com.liferay.portlet.dynamicdatamapping.TemplateNameException;
import com.liferay.portlet.dynamicdatamapping.TemplateScriptException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.test.BaseDDMServiceTestCase;
import com.liferay.portlet.dynamicdatamapping.util.comparator.TemplateIdComparator;

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
	@Override
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
	public void testSearchByDescription() throws Exception {
		long resourceClassNameId = RandomTestUtil.randomLong();

		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId, "Event", "Event");
		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId, "Contact", "Contact");
		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId, "Meeting", "Meeting");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			null, null, resourceClassNameId, null, "Meeting", null, null, null,
			true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, templates.size());

		DDMTemplate template = templates.get(0);

		Assert.assertEquals(
			"Meeting", template.getDescription(group.getDefaultLanguageId()));
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		addDisplayTemplate(_classNameId, 0, "Event Template");
		addDisplayTemplate(_classNameId, 0, "Contact Template");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			null, null, 0, "Event", null, null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new TemplateIdComparator(true));

		Assert.assertEquals(1, templates.size());
		Assert.assertEquals(
			"Event Template", getTemplateName(templates.get(0)));

		templates = DDMTemplateLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			null, null, 0, "Template", null, null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new TemplateIdComparator(true));

		Assert.assertEquals(
			"Event Template", getTemplateName(templates.get(0)));
		Assert.assertEquals(
			"Contact Template", getTemplateName(templates.get(1)));
	}

	@Test
	public void testSearchByName() throws Exception {
		addDisplayTemplate(_classNameId, 0, "Event");
		addDisplayTemplate(_classNameId, 0, "Contact");
		addDisplayTemplate(_classNameId, 0, "Meeting");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			null, null, 0, "Event", null, null, null, null, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(1, templates.size());
		Assert.assertEquals("Event", getTemplateName(templates.get(0)));
	}

	@Test
	public void testSearchByNameAndDescription() throws Exception {
		long resourceClassNameId = RandomTestUtil.randomLong();

		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId, "Event", "Event");
		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId, "Contact", "Contact");
		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId, "Meeting", "Meeting");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			null, null, 0, "Event", "Meeting", null, null, null, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(0, templates.size());
	}

	@Test
	public void testSearchByNameOrDescription() throws Exception {
		long resourceClassNameId = RandomTestUtil.randomLong();

		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId, "Event", "Event");
		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId, "Contact", "Contact");
		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId, "Meeting", "Meeting");

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			null, null, resourceClassNameId, "Event", "Meeting", null, null,
			null, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new TemplateIdComparator(true));

		Assert.assertEquals("Event", getTemplateName(templates.get(0)));
		Assert.assertEquals("Meeting", getTemplateName(templates.get(1)));
	}

	@Test
	public void testSearchCount() throws Exception {
		int initialCount = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), group.getGroupId(), _classNameId, 0,
			0, "Test Template", null, null, null, null, false);

		addDisplayTemplate(_classNameId, 0, "Test Template");

		int count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), group.getGroupId(), _classNameId, 0,
			0, "Test Template", null, null, null, null, false);

		Assert.assertEquals(initialCount + 1, count);
	}

	@Test
	public void testSearchCountByClassNameIdAndClassPK() throws Exception {
		long classNameId1 = RandomTestUtil.randomLong();
		long classPK1 = RandomTestUtil.randomLong();

		addDisplayTemplate(classNameId1, classPK1, StringUtil.randomString());
		addDisplayTemplate(
			classNameId1, RandomTestUtil.randomLong(),
			StringUtil.randomString());

		long classNameId2 = RandomTestUtil.randomLong();

		addDisplayTemplate(
			classNameId2, RandomTestUtil.randomLong(),
			StringUtil.randomString());

		int count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			new long[] {classNameId1}, new long[] {classPK1}, 0, null, null,
			null);

		Assert.assertEquals(1, count);

		count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			new long[] {classNameId2}, new long[] {classPK1}, 0, null, null,
			null);

		Assert.assertEquals(0, count);
	}

	@Test
	public void testSearchCountByClassNameIds() throws Exception {
		long classNameId1 = RandomTestUtil.randomLong();

		addDisplayTemplate(classNameId1, 0, StringUtil.randomString());
		addDisplayTemplate(classNameId1, 0, StringUtil.randomString());
		addDisplayTemplate(classNameId1, 0, StringUtil.randomString());

		long classNameId2 = RandomTestUtil.randomLong();

		addDisplayTemplate(classNameId2, 0, StringUtil.randomString());
		addDisplayTemplate(classNameId2, 0, StringUtil.randomString());

		int count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			new long[] {classNameId1}, null, 0, null, null, null);

		Assert.assertEquals(3, count);

		count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			new long[] {classNameId1, classNameId2}, null, 0, null, null, null);

		Assert.assertEquals(5, count);
	}

	@Test
	public void testSearchCountByClassPKs() throws Exception {
		long classPK1 = RandomTestUtil.randomLong();

		addDisplayTemplate(_classNameId, classPK1, StringUtil.randomString());

		long classPK2 = RandomTestUtil.randomLong();

		addDisplayTemplate(_classNameId, classPK2, StringUtil.randomString());

		long classPK3 = RandomTestUtil.randomLong();

		addDisplayTemplate(_classNameId, classPK3, StringUtil.randomString());

		int count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			null, new long[] {classPK1, classPK2, classPK3}, 0, null, null,
			null);

		Assert.assertEquals(3, count);
	}

	@Test
	public void testSearchCountByKeywords() throws Exception {
		int initialCount = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), group.getGroupId(), _classNameId, 0,
			0, null, null, null);

		addDisplayTemplate(_classNameId, 0, "Test Template");

		int count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), group.getGroupId(), _classNameId, 0,
			0, "Test", null, null);

		Assert.assertEquals(initialCount + 1, count);
	}

	@Test
	public void testSearchCountByLanguage() throws Exception {
		String velocityLanguage = TemplateConstants.LANG_TYPE_VM;

		addTemplate(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			velocityLanguage, getTestTemplateScript(velocityLanguage));

		addTemplate(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			velocityLanguage, getTestTemplateScript(velocityLanguage));

		String freeMarkerLanguage = TemplateConstants.LANG_TYPE_FTL;

		addTemplate(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			StringUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			freeMarkerLanguage, getTestTemplateScript(freeMarkerLanguage));

		int count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			null, null, 0, null, null, null, null,
			TemplateConstants.LANG_TYPE_VM, true);

		Assert.assertEquals(2, count);
	}

	@Test
	public void testSearchCountByResourceClassNameId() throws Exception {
		long resourceClassNameId1 = RandomTestUtil.randomLong();

		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId1, StringUtil.randomString(),
			StringUtil.randomString());
		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId1, StringUtil.randomString(),
			StringUtil.randomString());

		long resourceClassNameId2 = RandomTestUtil.randomLong();

		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId2, StringUtil.randomString(),
			StringUtil.randomString());
		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId2, StringUtil.randomString(),
			StringUtil.randomString());
		addDisplayTemplate(
			_classNameId, 0, resourceClassNameId2, StringUtil.randomString(),
			StringUtil.randomString());

		int count = DDMTemplateLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), new long[] {group.getGroupId()},
			null, null, resourceClassNameId2, null, null, null);

		Assert.assertEquals(3, count);
	}

	protected DDMTemplate copyTemplate(DDMTemplate template) throws Exception {
		return DDMTemplateLocalServiceUtil.copyTemplate(
			template.getUserId(), template.getTemplateId(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	protected String getTemplateName(DDMTemplate template) {
		return template.getName(group.getDefaultLanguageId());
	}

	protected DDMTemplate updateTemplate(DDMTemplate template)
		throws Exception {

		return DDMTemplateLocalServiceUtil.updateTemplate(
			template.getUserId(), template.getTemplateId(), 
			template.getClassPK(), template.getNameMap(), 
			template.getDescriptionMap(), template.getType(), 
			template.getMode(), template.getLanguage(), template.getScript(), 
			template.isCacheable(), template.isSmallImage(), 
			template.getSmallImageURL(), null, 
			ServiceContextTestUtil.getServiceContext());
	}

	private long _classNameId;

}