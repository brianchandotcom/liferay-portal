/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.util.comparator.StructureLinkStructureNameComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class DDMStructureLinkLocalServiceTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_classNameId = PortalUtil.getClassNameId(DDL_RECORD_SET_CLASS_NAME);
		_classPK = RandomTestUtil.randomLong();
	}

	@Test
	public void testGetCountStructureLinkStructures1() throws Exception {
		DDMStructure ddmStructure1 = addStructure(
			_classNameId, "Abc Test1", "Abc Test1");

		DDMStructureLinkLocalServiceUtil.addStructureLink(
			_classNameId, _classPK, ddmStructure1.getStructureId());

		DDMStructure ddmStructure2 = addStructure(
			_classNameId, "Abc Test2", "Abc Test2");

		DDMStructureLinkLocalServiceUtil.addStructureLink(
			_classNameId, _classPK, ddmStructure2.getStructureId());

		int count =
			DDMStructureLinkLocalServiceUtil.getStructureLinkStructuresCount(
				_classNameId, _classPK, "Abc");

		Assert.assertEquals(2, count);
	}

	@Test
	public void testGetCountStructureLinkStructures2() throws Exception {
		DDMStructure ddmStructure1 = addStructure(
			_classNameId, "Abc Test1", "Abc Test1");

		DDMStructureLinkLocalServiceUtil.addStructureLink(
			_classNameId, _classPK, ddmStructure1.getStructureId());

		DDMStructure ddmStructure2 = addStructure(
			_classNameId, "Abc Test2", "Abc Test2");

		DDMStructureLinkLocalServiceUtil.addStructureLink(
			_classNameId, _classPK, ddmStructure2.getStructureId());

		int count =
			DDMStructureLinkLocalServiceUtil.getStructureLinkStructuresCount(
				_classNameId, _classPK, "Test1");

		Assert.assertEquals(1, count);
	}

	@Test
	public void testGetStructureLinkStructures() throws Exception {
		DDMStructure ddmStructure1 = addStructure(
			_classNameId, "Abc Test1", "Abc Test1");

		DDMStructureLinkLocalServiceUtil.addStructureLink(
			_classNameId, _classPK, ddmStructure1.getStructureId());

		DDMStructure ddmStructure2 = addStructure(
			_classNameId, "Abc Test2", "Abc Test2");

		DDMStructureLinkLocalServiceUtil.addStructureLink(
			_classNameId, _classPK, ddmStructure2.getStructureId());

		List<DDMStructure> ddmStructures =
			DDMStructureLinkLocalServiceUtil.getStructureLinkStructures(
				_classNameId, _classPK, "Abc");

		Assert.assertEquals(ddmStructures.toString(), 2, ddmStructures.size());
		Assert.assertTrue(
			ddmStructures.toString(), ddmStructures.contains(ddmStructure1));
		Assert.assertTrue(
			ddmStructures.toString(), ddmStructures.contains(ddmStructure2));

		ddmStructures =
			DDMStructureLinkLocalServiceUtil.getStructureLinkStructures(
				_classNameId, _classPK, "Test1");

		Assert.assertEquals(ddmStructures.toString(), 1, ddmStructures.size());
		Assert.assertTrue(
			ddmStructures.toString(), ddmStructures.contains(ddmStructure1));
	}

	@Test
	public void testGetStructureLinkStructuresWithOrderByNameAsc()
		throws Exception {

		DDMStructure ddmStructure1 = addStructure(
			_classNameId, "Abc Test1", "Abc Test1");

		DDMStructureLinkLocalServiceUtil.addStructureLink(
			_classNameId, _classPK, ddmStructure1.getStructureId());

		DDMStructure ddmStructure2 = addStructure(
			_classNameId, "Def Test2", "Def Test2");

		DDMStructureLinkLocalServiceUtil.addStructureLink(
			_classNameId, _classPK, ddmStructure2.getStructureId());

		List<DDMStructure> ddmStructures =
			DDMStructureLinkLocalServiceUtil.getStructureLinkStructures(
				_classNameId, _classPK, "Test", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new StructureLinkStructureNameComparator(true));

		Assert.assertEquals(ddmStructures.toString(), 2, ddmStructures.size());
		Assert.assertEquals(ddmStructure1, ddmStructures.get(0));
		Assert.assertEquals(ddmStructure2, ddmStructures.get(1));
	}

	@Test
	public void testGetStructureLinkStructuresWithOrderByNameDesc()
		throws Exception {

		DDMStructure ddmStructure1 = addStructure(
			_classNameId, "Abc Test1", "Abc Test1");

		DDMStructureLinkLocalServiceUtil.addStructureLink(
			_classNameId, _classPK, ddmStructure1.getStructureId());

		DDMStructure ddmStructure2 = addStructure(
			_classNameId, "Def Test2", "Def Test2");

		DDMStructureLinkLocalServiceUtil.addStructureLink(
			_classNameId, _classPK, ddmStructure2.getStructureId());

		List<DDMStructure> ddmStructures =
			DDMStructureLinkLocalServiceUtil.getStructureLinkStructures(
				_classNameId, _classPK, "Test", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new StructureLinkStructureNameComparator());

		Assert.assertEquals(ddmStructures.toString(), 2, ddmStructures.size());
		Assert.assertEquals(ddmStructure1, ddmStructures.get(1));
		Assert.assertEquals(ddmStructure2, ddmStructures.get(0));
	}

	private static long _classNameId;
	private static long _classPK;

}