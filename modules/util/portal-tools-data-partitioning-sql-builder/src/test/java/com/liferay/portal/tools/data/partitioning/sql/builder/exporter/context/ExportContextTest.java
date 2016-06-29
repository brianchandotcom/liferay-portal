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

package com.liferay.portal.tools.data.partitioning.sql.builder.exporter.context;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class ExportContextTest {

	@Test
	public void testConstructorWithCatalog() {
		String catalogName = "catalogName";

		ExportContext exportProcess = new ExportContext(
			catalogName, null, null, null, "schemaName", true);

		Assert.assertNotEquals(
			exportProcess.getSchemaName(), exportProcess.getCatalogName());
		Assert.assertEquals(catalogName, exportProcess.getCatalogName());
	}

	@Test
	public void testConstructorWithoutCatalogShouldUseSchema() {
		ExportContext exportProcess = new ExportContext(
			null, null, null, "schemaName", true);

		Assert.assertEquals(
			exportProcess.getSchemaName(), exportProcess.getCatalogName());
	}

}