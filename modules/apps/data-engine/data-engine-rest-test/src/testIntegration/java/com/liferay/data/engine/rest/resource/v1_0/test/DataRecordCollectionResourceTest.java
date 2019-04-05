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

package com.liferay.data.engine.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.data.engine.rest.resource.v1_0.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class DataRecordCollectionResourceTest
	extends BaseDataRecordCollectionResourceTestCase {

	@Ignore
	@Override
	public void testDeleteDataRecordCollection() throws Exception {
	}

	@Ignore
	@Override
	public void testGetDataDefinitionDataRecordCollectionsPage()
		throws Exception {
	}

	@Ignore
	@Override
	public void testGetDataDefinitionDataRecordCollectionsPageWithPagination()
		throws Exception {
	}

	@Ignore
	@Override
	public void testGetSiteDataRecordCollectionsPage() throws Exception {
	}

	@Ignore
	@Override
	public void testGetSiteDataRecordCollectionsPageWithPagination()
		throws Exception {
	}

	@Ignore
	@Override
	public void testPutDataRecordCollection() throws Exception {
	}

	protected void assertValid(DataRecordCollection dataRecordCollection) {
		boolean valid = false;

		if ((dataRecordCollection.getDataDefinitionId() != null) &&
			(dataRecordCollection.getId() != null) &&
			(dataRecordCollection.getName() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		DataRecordCollection dataRecordCollection1,
		DataRecordCollection dataRecordCollection2) {

		return Arrays.equals(
			dataRecordCollection1.getName(), dataRecordCollection2.getName());
	}

	@Override
	protected DataRecordCollection randomDataRecordCollection() {
		try {
			DDMStructure ddmStructure = DataDefinitionTestUtil.addDDMStructure(
				testGroup);

			return new DataRecordCollection() {
				{
					dataDefinitionId = ddmStructure.getStructureId();
					id = RandomTestUtil.randomLong();
					name = new LocalizedValue[] {
						new LocalizedValue() {
							{
								key = "en_US";
								value = RandomTestUtil.randomString();
							}
						}
					};
				}
			};
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected DataRecordCollection
			testGetDataRecordCollection_addDataRecordCollection()
		throws Exception {

		DataRecordCollection dataRecordCollection =
			randomDataRecordCollection();

		return invokePostDataDefinitionDataRecordCollection(
			dataRecordCollection.getDataDefinitionId(), dataRecordCollection);
	}

	@Override
	protected DataRecordCollection
			testPostDataDefinitionDataRecordCollection_addDataRecordCollection(
				DataRecordCollection dataRecordCollection)
		throws Exception {

		return invokePostDataDefinitionDataRecordCollection(
			dataRecordCollection.getDataDefinitionId(), dataRecordCollection);
	}

}