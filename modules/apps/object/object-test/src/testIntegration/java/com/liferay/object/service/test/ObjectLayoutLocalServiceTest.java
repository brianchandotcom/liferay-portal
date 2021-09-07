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
import com.liferay.object.model.ObjectLayoutBoxColumn;
import com.liferay.object.model.ObjectLayoutBoxRow;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.service.ObjectLayoutBoxColumnLocalServiceUtil;
import com.liferay.object.service.ObjectLayoutBoxLocalServiceUtil;
import com.liferay.object.service.ObjectLayoutBoxRowLocalServiceUtil;
import com.liferay.object.service.ObjectLayoutLocalServiceUtil;
import com.liferay.object.service.ObjectLayoutTabLocalServiceUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
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

	@Test
	public void testAddObjectLayout() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Test"), "Test", null, null,
				LocalizedMapUtil.getLocalizedMap("Tests"),
				ObjectDefinitionConstants.SCOPE_COMPANY, null);

		ObjectLayout objectLayout =
			ObjectLayoutLocalServiceUtil.addObjectLayout(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId(), true,
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));

		ObjectLayoutTab objectLayoutTab =
			ObjectLayoutTabLocalServiceUtil.addObjectLayoutTab(
				TestPropsValues.getUserId(), objectLayout.getObjectLayoutId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				0);

		ObjectLayoutBox objectLayoutBox =
			ObjectLayoutBoxLocalServiceUtil.addObjectLayoutBox(
				TestPropsValues.getUserId(),
				objectLayoutTab.getObjectLayoutTabId(), true,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				0);

		ObjectLayoutBoxRow objectLayoutBoxRow =
			ObjectLayoutBoxRowLocalServiceUtil.addObjectLayoutBoxRow(
				TestPropsValues.getUserId(),
				objectLayoutBox.getObjectLayoutBoxId(), 0);

		ObjectField objectField1 = _addObjectField(
			objectDefinition.getObjectDefinitionId(), "Able");

		ObjectField objectField2 = _addObjectField(
			objectDefinition.getObjectDefinitionId(), "Baker");

		ObjectLayoutBoxColumn objectLayoutBoxColumn1 =
			ObjectLayoutBoxColumnLocalServiceUtil.addObjectLayoutBoxColumn(
				TestPropsValues.getUserId(), objectField1.getObjectFieldId(),
				objectLayoutBoxRow.getObjectLayoutBoxRowId(), 0);

		ObjectLayoutBoxColumn objectLayoutBoxColumn2 =
			ObjectLayoutBoxColumnLocalServiceUtil.addObjectLayoutBoxColumn(
				TestPropsValues.getUserId(), objectField2.getObjectFieldId(),
				objectLayoutBoxRow.getObjectLayoutBoxRowId(), 0);

		List<ObjectLayoutBoxColumn> objectLayoutBoxColumns =
			ObjectLayoutBoxColumnLocalServiceUtil.getObjectLayoutBoxColumns(
				objectLayoutBoxRow.getObjectLayoutBoxRowId());

		Assert.assertEquals(
			objectLayoutBoxColumns.toString(), 2,
			objectLayoutBoxColumns.size());

		ObjectLayoutBoxColumnLocalServiceUtil.deleteObjectLayoutBoxColumn(
			objectLayoutBoxColumn1);

		ObjectLayoutBoxColumnLocalServiceUtil.deleteObjectLayoutBoxColumn(
			objectLayoutBoxColumn2);

		ObjectLayoutBoxRowLocalServiceUtil.deleteObjectLayoutBoxRow(
			objectLayoutBoxRow);

		ObjectLayoutBoxLocalServiceUtil.deleteObjectLayoutBox(objectLayoutBox);

		ObjectLayoutTabLocalServiceUtil.deleteObjectLayoutTab(objectLayoutTab);

		ObjectLayoutLocalServiceUtil.deleteObjectLayout(objectLayout);

		ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
			objectDefinition);
	}

	private ObjectField _addObjectField(long objectDefinitionId, String name)
		throws Exception {

		return ObjectFieldLocalServiceUtil.addCustomObjectField(
			TestPropsValues.getUserId(), 0, objectDefinitionId, false, false,
			null, LocalizedMapUtil.getLocalizedMap(name), "a" + name, true,
			"String");
	}

}