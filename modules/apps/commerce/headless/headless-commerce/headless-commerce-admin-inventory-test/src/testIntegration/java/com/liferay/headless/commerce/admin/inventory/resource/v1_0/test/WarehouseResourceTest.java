/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.commerce.admin.inventory.client.dto.v1_0.Warehouse;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class WarehouseResourceTest extends BaseWarehouseResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testGetWarehousesPageWithFilterStringEquals() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetWarehousesPageWithSortDouble() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetWarehousesPageWithSortString() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetWarehouseByExternalReferenceCode()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetWarehouseByExternalReferenceCodeNotFound()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetWarehouseId() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetWarehouseIdNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetWarehousesPage() throws Exception {
	}

	@Override
	@Test
	public void testPatchWarehouseByExternalReferenceCode() throws Exception {
		Warehouse postWarehouse = _addWarehouse();

		postWarehouse.setName(
			Collections.singletonMap(
				"en_US", "testPatchWarehouseByExternalReferenceCode"));

		warehouseResource.patchWarehouseByExternalReferenceCode(
			postWarehouse.getExternalReferenceCode(), postWarehouse);

		Warehouse patchWarehouse =
			warehouseResource.getWarehouseByExternalReferenceCode(
				postWarehouse.getExternalReferenceCode());

		assertEquals(postWarehouse, patchWarehouse);
	}

	@Override
	@Test
	public void testPatchWarehouseId() throws Exception {
		Warehouse postWarehouse = _addWarehouse();

		postWarehouse.setName(
			Collections.singletonMap("en_US", "testPatchWarehouseId"));

		warehouseResource.patchWarehouseId(
			postWarehouse.getId(), postWarehouse);

		Warehouse patchWarehouse =
			warehouseResource.getWarehouseByExternalReferenceCode(
				postWarehouse.getExternalReferenceCode());

		assertEquals(postWarehouse, patchWarehouse);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"city", "name"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"city", "countryISOCode", "name", "regionISOCode"};
	}

	@Override
	protected Warehouse randomWarehouse() throws Exception {
		return new Warehouse() {
			{
				active = RandomTestUtil.randomBoolean();
				city = "Milano";
				countryISOCode = "IT";
				latitude = RandomTestUtil.randomDouble();
				longitude = RandomTestUtil.randomDouble();
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				regionISOCode = "25";
				street1 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				street2 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				street3 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
				zip = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected Warehouse
			testDeleteWarehouseByExternalReferenceCode_addWarehouse()
		throws Exception {

		return _addWarehouse();
	}

	@Override
	protected Warehouse testDeleteWarehouseId_addWarehouse() throws Exception {
		return _addWarehouse();
	}

	@Override
	protected Warehouse testGetWarehouseByExternalReferenceCode_addWarehouse()
		throws Exception {

		return _addWarehouse();
	}

	@Override
	protected Warehouse testGetWarehouseId_addWarehouse() throws Exception {
		return _addWarehouse();
	}

	@Override
	protected Warehouse testGetWarehousesPage_addWarehouse(Warehouse warehouse)
		throws Exception {

		return _addWarehouse(warehouse);
	}

	@Override
	protected Warehouse testGraphQLWarehouse_addWarehouse() throws Exception {
		return _addWarehouse();
	}

	@Override
	protected Warehouse testPostWarehouse_addWarehouse(Warehouse warehouse)
		throws Exception {

		return _addWarehouse(warehouse);
	}

	private Warehouse _addWarehouse() throws Exception {
		return _addWarehouse(randomWarehouse());
	}

	private Warehouse _addWarehouse(Warehouse warehouse) throws Exception {
		return warehouseResource.postWarehouse(warehouse);
	}

}