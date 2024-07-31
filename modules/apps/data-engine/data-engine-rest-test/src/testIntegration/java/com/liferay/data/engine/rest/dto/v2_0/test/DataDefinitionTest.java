/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.dto.v2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DataDefinitionTest {

	@Test
	public void test() throws Exception {
		DataDefinition dataDefinition = new DataDefinition() {
			{
				setDataDefinitionFields(
					new DataDefinitionField[] {
						new DataDefinitionField() {
							{
								setCustomProperties(
									HashMapBuilder.<String, Object>put(
										RandomTestUtil.randomString(),
										new Object[] {
											HashMapBuilder.put(
												RandomTestUtil.randomString(),
												RandomTestUtil.randomString()
											).build()
										}
									).build());
							}
						}
					});
			}
		};

		Assert.assertTrue(JSONUtil.isJSONObject(dataDefinition.toString()));
	}

}