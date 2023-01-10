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

package com.liferay.journal.web.internal.portlet.action.util;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public class DataDefinitionFieldNameUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testNormalizeFieldName() {
		DataDefinition dataDefinition = new DataDefinition() {
			{
				dataDefinitionFields = new DataDefinitionField[] {
					new DataDefinitionField() {
						{
							customProperties =
								HashMapBuilder.<String, Object>put(
									"fieldReference", "textFieldReference"
								).build();
							name = "textFieldName";
						}
					},
					new DataDefinitionField() {
						{
							customProperties =
								HashMapBuilder.<String, Object>put(
									"fieldReference", "selectFieldReference"
								).put(
									"options",
									HashMapBuilder.put(
										"en_US",
										new Object[] {
											HashMapBuilder.put(
												"reference", "optionReference1"
											).put(
												"value", "optionValue1"
											).build(),
											HashMapBuilder.put(
												"reference", "optionReference2"
											).put(
												"value", "optionValue2"
											).build()
										}
									).build()
								).build();
							name = "selectFieldName";
						}
					}
				};
			}
		};

		String[] dataDefinitionFieldNames = {
			"textFieldName", "selectFieldName"
		};

		DataLayout dataLayout = new DataLayout() {
			{
				dataLayoutPages = new DataLayoutPage[] {
					new DataLayoutPage() {
						{
							dataLayoutRows = new DataLayoutRow[] {
								new DataLayoutRow() {
									{
										dataLayoutColumns =
											new DataLayoutColumn[] {
												new DataLayoutColumn() {
													{
														fieldNames =
															dataDefinitionFieldNames;
													}
												}
											};
									}
								}
							};
						}
					}
				};
			}
		};

		DataDefinitionFieldNameUtil.normalizeFieldName(
			dataDefinition, dataLayout);

		DataDefinitionField[] dataDefinitionFields =
			dataDefinition.getDataDefinitionFields();

		Assert.assertEquals(
			"textFieldReference", dataDefinitionFields[0].getName());
		Assert.assertEquals(
			"selectFieldReference", dataDefinitionFields[1].getName());

		Map<String, Object> customProperties =
			dataDefinitionFields[1].getCustomProperties();

		Map<String, Object[]> optionsMap =
			(Map<String, Object[]>)customProperties.get("options");

		Assert.assertArrayEquals(
			new Object[] {
				HashMapBuilder.put(
					"reference", "optionReference1"
				).put(
					"value", "optionReference1"
				).build(),
				HashMapBuilder.put(
					"reference", "optionReference2"
				).put(
					"value", "optionReference2"
				).build()
			},
			optionsMap.get("en_US"));

		DataLayoutPage dataLayoutPage = dataLayout.getDataLayoutPages()[0];

		DataLayoutRow dataLayoutRow = dataLayoutPage.getDataLayoutRows()[0];

		DataLayoutColumn dataLayoutColumn =
			dataLayoutRow.getDataLayoutColumns()[0];

		Assert.assertEquals(
			"textFieldReference", dataLayoutColumn.getFieldNames()[0]);
		Assert.assertEquals(
			"selectFieldReference", dataLayoutColumn.getFieldNames()[1]);
	}

}