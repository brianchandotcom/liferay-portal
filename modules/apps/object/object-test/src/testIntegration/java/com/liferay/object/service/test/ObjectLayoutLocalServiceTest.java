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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectLayout;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.object.model.ObjectLayoutColumn;
import com.liferay.object.model.ObjectLayoutRow;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.service.ObjectLayoutLocalServiceUtil;
import com.liferay.object.service.persistence.ObjectLayoutBoxPersistence;
import com.liferay.object.service.persistence.ObjectLayoutColumnPersistence;
import com.liferay.object.service.persistence.ObjectLayoutRowPersistence;
import com.liferay.object.service.persistence.ObjectLayoutTabPersistence;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class ObjectLayoutLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Test"), "Test", null, null,
				LocalizedMapUtil.getLocalizedMap("Tests"),
				ObjectDefinitionConstants.SCOPE_COMPANY, null);
	}

	@Test
	public void testAddObjectLayout() throws Exception {
		ObjectLayout objectLayout = _addRandomObjectLayout();

		List<ObjectLayoutTab> objectLayoutTabs =
			objectLayout.getObjectLayoutTabs();

		Assert.assertEquals(
			objectLayoutTabs.toString(), 1, objectLayoutTabs.size());

		ObjectLayoutTab objectLayoutTab = objectLayoutTabs.get(0);

		List<ObjectLayoutBox> objectLayoutBoxes =
			objectLayoutTab.getObjectLayoutBoxes();

		Assert.assertEquals(
			objectLayoutBoxes.toString(), 1, objectLayoutBoxes.size());

		ObjectLayoutBox objectLayoutBox = objectLayoutBoxes.get(0);

		List<ObjectLayoutRow> objectLayoutRows =
			objectLayoutBox.getObjectLayoutRows();

		Assert.assertEquals(
			objectLayoutRows.toString(), 1, objectLayoutRows.size());

		ObjectLayoutRow objectLayoutRow = objectLayoutRows.get(0);

		List<ObjectLayoutColumn> objectLayoutColumns =
			objectLayoutRow.getObjectLayoutColumns();

		Assert.assertEquals(
			objectLayoutColumns.toString(), 2, objectLayoutColumns.size());

		ObjectLayoutLocalServiceUtil.deleteObjectLayout(
			objectLayout.getObjectLayoutId());
	}

	@Test
	public void testGetObjectLayout() throws Exception {
		ObjectLayout objectLayout = _addRandomObjectLayout();

		objectLayout = ObjectLayoutLocalServiceUtil.getObjectLayout(
			objectLayout.getObjectLayoutId());

		List<ObjectLayoutTab> objectLayoutTabs =
			objectLayout.getObjectLayoutTabs();

		Assert.assertEquals(
			objectLayoutTabs.toString(), 1, objectLayoutTabs.size());

		ObjectLayoutTab objectLayoutTab = objectLayoutTabs.get(0);

		List<ObjectLayoutBox> objectLayoutBoxes =
			objectLayoutTab.getObjectLayoutBoxes();

		Assert.assertEquals(
			objectLayoutBoxes.toString(), 1, objectLayoutBoxes.size());

		ObjectLayoutBox objectLayoutBox = objectLayoutBoxes.get(0);

		List<ObjectLayoutRow> objectLayoutRows =
			objectLayoutBox.getObjectLayoutRows();

		Assert.assertEquals(
			objectLayoutRows.toString(), 1, objectLayoutRows.size());

		ObjectLayoutRow objectLayoutRow = objectLayoutRows.get(0);

		List<ObjectLayoutColumn> objectLayoutColumns =
			objectLayoutRow.getObjectLayoutColumns();

		Assert.assertEquals(
			objectLayoutColumns.toString(), 2, objectLayoutColumns.size());

		ObjectLayoutLocalServiceUtil.deleteObjectLayout(
			objectLayout.getObjectLayoutId());
	}

	private ObjectField _addObjectField(long objectDefinitionId, String name)
		throws Exception {

		return ObjectFieldLocalServiceUtil.addCustomObjectField(
			TestPropsValues.getUserId(), 0, objectDefinitionId, false, false,
			null, LocalizedMapUtil.getLocalizedMap(name), "a" + name, true,
			"String");
	}

	private ObjectLayout _addRandomObjectLayout() throws Exception {
		ObjectField objectField1 = _addObjectField(
			_objectDefinition.getObjectDefinitionId(), "Able");

		ObjectLayoutColumn objectLayoutColumn1 =
			_objectLayoutColumnPersistence.create(0L);

		objectLayoutColumn1.setObjectFieldId(objectField1.getObjectFieldId());
		objectLayoutColumn1.setPriority(0);

		ObjectField objectField2 = _addObjectField(
			_objectDefinition.getObjectDefinitionId(), "Baker");

		ObjectLayoutColumn objectLayoutColumn2 =
			_objectLayoutColumnPersistence.create(0L);

		objectLayoutColumn2.setObjectFieldId(objectField2.getObjectFieldId());
		objectLayoutColumn2.setPriority(0);

		ObjectLayoutRow objectLayoutRow = _objectLayoutRowPersistence.create(
			0L);

		objectLayoutRow.setPriority(0);
		objectLayoutRow.setObjectLayoutColumns(
			Arrays.asList(objectLayoutColumn1, objectLayoutColumn2));

		ObjectLayoutBox objectLayoutBox = _objectLayoutBoxPersistence.create(
			0L);

		objectLayoutBox.setCollapsable(false);
		objectLayoutBox.setNameMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
		objectLayoutBox.setPriority(0);
		objectLayoutBox.setObjectLayoutRows(
			Collections.singletonList(objectLayoutRow));

		ObjectLayoutTab objectLayoutTab = _objectLayoutTabPersistence.create(
			0L);

		objectLayoutTab.setNameMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()));
		objectLayoutTab.setPriority(0);
		objectLayoutTab.setObjectLayoutBoxes(
			Collections.singletonList(objectLayoutBox));

		return ObjectLayoutLocalServiceUtil.addObjectLayout(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			Collections.singletonList(objectLayoutTab));
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectLayoutBoxPersistence _objectLayoutBoxPersistence;

	@Inject
	private ObjectLayoutColumnPersistence _objectLayoutColumnPersistence;

	@Inject
	private ObjectLayoutRowPersistence _objectLayoutRowPersistence;

	@Inject
	private ObjectLayoutTabPersistence _objectLayoutTabPersistence;

}