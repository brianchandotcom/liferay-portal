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

package com.liferay.dynamic.data.mapping.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.dynamic.data.mapping.util.comparator.DDMTemplateNameComparator;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Attila Bakay
 */
@RunWith(Arquillian.class)
public class TemplateNameComparatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), defaultLocale);

		long resourceClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);

		_ddmTemplate1 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			resourceClassNameId, defaultLocale);

		_ddmTemplate1.setName("default name A", defaultLocale);
		_ddmTemplate1.setName("spanish name A", _esLocale);

		_ddmTemplate2 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			resourceClassNameId, defaultLocale);

		_ddmTemplate2.setName("default name b", defaultLocale);
		_ddmTemplate2.setName("spanish name B", _esLocale);

		_ddmTemplate3 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			resourceClassNameId, defaultLocale);

		_ddmTemplate3.setName("default name c", defaultLocale);
		_ddmTemplate3.setName("spanish name C", _esLocale);

		_ddmTemplate4 = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			resourceClassNameId, defaultLocale);

		_ddmTemplate4.setName("default name D", defaultLocale);
	}

	@Test
	public void testCompareWithDefaultLocale() {
		List<DDMTemplate> ddmTemplates = new ArrayList<>();

		ddmTemplates.add(_ddmTemplate2);
		ddmTemplates.add(_ddmTemplate1);
		ddmTemplates.add(_ddmTemplate3);
		ddmTemplates.add(_ddmTemplate4);

		_ddmTemplateNameComparator = new DDMTemplateNameComparator(true);

		Collections.sort(ddmTemplates, _ddmTemplateNameComparator);

		Assert.assertEquals(_ddmTemplate1, ddmTemplates.get(0));
		Assert.assertEquals(_ddmTemplate2, ddmTemplates.get(1));
		Assert.assertEquals(_ddmTemplate3, ddmTemplates.get(2));
		Assert.assertEquals(_ddmTemplate4, ddmTemplates.get(3));
	}

	@Test
	public void testCompareWithLocale() {
		List<DDMTemplate> ddmTemplates = new ArrayList<>();

		ddmTemplates.add(_ddmTemplate2);
		ddmTemplates.add(_ddmTemplate1);
		ddmTemplates.add(_ddmTemplate3);
		ddmTemplates.add(_ddmTemplate4);

		_ddmTemplateNameComparator = new DDMTemplateNameComparator(
			true, _esLocale);

		Collections.sort(ddmTemplates, _ddmTemplateNameComparator);

		Assert.assertEquals(_ddmTemplate4, ddmTemplates.get(0));
		Assert.assertEquals(_ddmTemplate1, ddmTemplates.get(1));
		Assert.assertEquals(_ddmTemplate2, ddmTemplates.get(2));
		Assert.assertEquals(_ddmTemplate3, ddmTemplates.get(3));
	}

	private DDMTemplate _ddmTemplate1;
	private DDMTemplate _ddmTemplate2;
	private DDMTemplate _ddmTemplate3;
	private DDMTemplate _ddmTemplate4;
	private DDMTemplateNameComparator _ddmTemplateNameComparator;
	private final Locale _esLocale = LocaleUtil.SPAIN;

	@DeleteAfterTestRun
	private Group _group;

}