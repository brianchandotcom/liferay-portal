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

package com.liferay.data.engine.rest.resource.v2_0.test.util;

import com.liferay.data.engine.rest.client.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutRow;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

/**
 * @author Leonardo Barros
 */
public class DataLayoutTestUtil {

	public static DataLayout createDataLayout(
		long dataDefinitionId, String name, long siteId) {

		DataLayout dataLayout = new DataLayout() {
			{
				dataLayoutKey = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
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