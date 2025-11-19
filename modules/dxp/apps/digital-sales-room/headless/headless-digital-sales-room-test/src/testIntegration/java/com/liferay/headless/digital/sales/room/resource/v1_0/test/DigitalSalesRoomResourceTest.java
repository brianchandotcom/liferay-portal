/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoom;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;

import java.io.File;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-66359")
@RunWith(Arquillian.class)
public class DigitalSalesRoomResourceTest
	extends BaseDigitalSalesRoomResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", TestPropsValues.getCompanyId());

		if (objectDefinition != null) {
			return;
		}

		Bundle testBundle = FrameworkUtil.getBundle(
			DigitalSalesRoomResourceTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (!Objects.equals(
					bundle.getSymbolicName(),
					"com.liferay.digital.sales.room.impl")) {

				continue;
			}

			_deleteFile(bundle, "01.object.folder");
			_deleteFile(bundle, "02.object.definition");

			CompletableFuture<Void> completableFuture =
				_batchEngineUnitProcessor.processBatchEngineUnits(
					_batchEngineUnitReader.getBatchEngineUnits(bundle));

			completableFuture.join();
		}
	}

	@Ignore
	@Override
	@Test
	public void testGetDigitalSalesRoomsPageWithPagination() throws Exception {
		super.testGetDigitalSalesRoomsPageWithPagination();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"accountId", "channelId", "clientName", "description",
			"externalReferenceCode", "friendlyUrlPath", "name", "primaryColor",
			"secondaryColor"
		};
	}

	@Override
	protected DigitalSalesRoom randomDigitalSalesRoom() throws Exception {
		return new DigitalSalesRoom() {
			{
				accountId = 0L;
				channelId = 0L;
				clientName = RandomTestUtil.randomString();
				description = RandomTestUtil.randomString();
				externalReferenceCode = RandomTestUtil.randomString();
				friendlyUrlPath = StringUtil.toLowerCase(
					StringPool.SLASH + RandomTestUtil.randomString());
				name = RandomTestUtil.randomString();
				primaryColor = RandomTestUtil.randomString();
				secondaryColor = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected DigitalSalesRoom testGetDigitalSalesRoom_addDigitalSalesRoom()
		throws Exception {

		return digitalSalesRoomResource.postDigitalSalesRoom(
			randomDigitalSalesRoom());
	}

	@Override
	protected DigitalSalesRoom testGetDigitalSalesRoomsPage_addDigitalSalesRoom(
			DigitalSalesRoom digitalSalesRoom)
		throws Exception {

		return digitalSalesRoomResource.postDigitalSalesRoom(digitalSalesRoom);
	}

	@Override
	protected DigitalSalesRoom testPostDigitalSalesRoom_addDigitalSalesRoom(
			DigitalSalesRoom digitalSalesRoom)
		throws Exception {

		return digitalSalesRoomResource.postDigitalSalesRoom(digitalSalesRoom);
	}

	private void _deleteFile(Bundle bundle, String fileName) {
		File file = bundle.getDataFile(
			".com.liferay.digital.sales.room.internal.batch." + fileName +
				".batch.engine.data.json.0.processed");

		if ((file != null) && file.exists()) {
			file.delete();
		}
	}

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private BatchEngineUnitReader _batchEngineUnitReader;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}