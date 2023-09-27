/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.resource.v2_0.test.util;

import com.liferay.data.engine.rest.client.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.client.dto.v2_0.DataRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;

/**
 * @author Leonardo Barros
 */
public class DataLayoutTestUtil {

	public static DataLayout createDataLayout(
		long dataDefinitionId, String name, long siteId) {

		DataLayout dataLayout = new DataLayout() {
			{
				dataLayoutKey = RandomTestUtil.randomString();
				dataRules = new DataRule[0];
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = new HashMap<>();
				paginationMode = "wizard";
			}
		};

		dataLayout.setDataDefinitionId(dataDefinitionId);
		dataLayout.setDataLayoutPages(
			new DataLayoutPage[] {
				new DataLayoutPage() {
					{
						dataLayoutRows = new DataLayoutRow[] {
							new DataLayoutRow() {
								{
									dataLayoutColumns = new DataLayoutColumn[] {
										new DataLayoutColumn() {
											{
												columnSize = 12;
												fieldNames = new String[] {
													RandomTestUtil.
														randomString()
												};
											}
										}
									};
								}
							}
						};
						description = HashMapBuilder.<String, Object>put(
							"en_US", "Page Description"
						).build();
						title = HashMapBuilder.<String, Object>put(
							"en_US", "Page Title"
						).build();
					}
				}
			});
		dataLayout.setName(
			HashMapBuilder.<String, Object>put(
				"en_US", name
			).build());
		dataLayout.setSiteId(siteId);

		return dataLayout;
	}

}