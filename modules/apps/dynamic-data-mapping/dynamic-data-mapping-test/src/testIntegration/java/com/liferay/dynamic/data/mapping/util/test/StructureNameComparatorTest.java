/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
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
public class StructureNameComparatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		_ddmStructure1 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), defaultLocale);

		_ddmStructure1.setName("default name A", defaultLocale);
		_ddmStructure1.setName("spanish name A", _esLocale);

		_ddmStructure2 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), defaultLocale);

		_ddmStructure2.setName("default name b", defaultLocale);
		_ddmStructure2.setName("spanish name B", _esLocale);

		_ddmStructure3 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), defaultLocale);

		_ddmStructure3.setName("default name c", defaultLocale);
		_ddmStructure3.setName("spanish name C", _esLocale);

		_ddmStructure4 = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), defaultLocale);

		_ddmStructure4.setName("default name D", defaultLocale);
	}

	@Test
	public void testCompareWithDefaultLocale() {
		List<DDMStructure> ddmStructures = new ArrayList<>();

		ddmStructures.add(_ddmStructure2);
		ddmStructures.add(_ddmStructure1);
		ddmStructures.add(_ddmStructure3);
		ddmStructures.add(_ddmStructure4);

		_structureNameComparator = new StructureNameComparator(true);

		Collections.sort(ddmStructures, _structureNameComparator);

		Assert.assertEquals(_ddmStructure1, ddmStructures.get(0));
		Assert.assertEquals(_ddmStructure2, ddmStructures.get(1));
		Assert.assertEquals(_ddmStructure3, ddmStructures.get(2));
		Assert.assertEquals(_ddmStructure4, ddmStructures.get(3));
	}

	@Test
	public void testCompareWithLocale() {
		List<DDMStructure> ddmStructures = new ArrayList<>();

		ddmStructures.add(_ddmStructure2);
		ddmStructures.add(_ddmStructure1);
		ddmStructures.add(_ddmStructure3);
		ddmStructures.add(_ddmStructure4);

		_structureNameComparator = new StructureNameComparator(true, _esLocale);

		Collections.sort(ddmStructures, _structureNameComparator);

		Assert.assertEquals(_ddmStructure4, ddmStructures.get(0));
		Assert.assertEquals(_ddmStructure1, ddmStructures.get(1));
		Assert.assertEquals(_ddmStructure2, ddmStructures.get(2));
		Assert.assertEquals(_ddmStructure3, ddmStructures.get(3));
	}

	private DDMStructure _ddmStructure1;
	private DDMStructure _ddmStructure2;
	private DDMStructure _ddmStructure3;
	private DDMStructure _ddmStructure4;
	private final Locale _esLocale = LocaleUtil.SPAIN;

	@DeleteAfterTestRun
	private Group _group;

	private StructureNameComparator _structureNameComparator;

}