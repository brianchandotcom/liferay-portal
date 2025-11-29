/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntryModel;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoom;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.io.File;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
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
	@Test
	public void testPostDigitalSalesRoom() throws Exception {
		super.testPostDigitalSalesRoom();

		_testPostDigitalSalesRoomWithSiteInitializer();
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

	private void _testPostDigitalSalesRoomWithSiteInitializer()
		throws Exception {

		DigitalSalesRoom randomDigitalSalesRoom = randomDigitalSalesRoom();

		DigitalSalesRoom postDigitalSalesRoom =
			testPostDigitalSalesRoom_addDigitalSalesRoom(
				randomDigitalSalesRoom);

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.fetchFragmentCollection(
				postDigitalSalesRoom.getId(), "dsr");

		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToArray(
					_fragmentEntryLocalService.getFragmentEntries(
						postDigitalSalesRoom.getId(),
						fragmentCollection.getFragmentCollectionId(), 0),
					FragmentEntryModel::getName, String.class),
				new String[] {"DSR Header Main", "DSR Header User"}));

		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToArray(
					_layoutLocalService.getLayouts(
						postDigitalSalesRoom.getId(), false),
					layout -> layout.getName(LocaleUtil.getSiteDefault()),
					String.class),
				new String[] {"Documents", "Onboarding"}));

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.fetchStyleBookEntry(
				postDigitalSalesRoom.getId(), "dsr-classic");

		JSONObject jsonObject1 = _jsonFactory.createJSONObject(
			styleBookEntry.getFrontendTokensValues());

		JSONObject jsonObject2 = jsonObject1.getJSONObject("brandColor1");

		Assert.assertEquals("primaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			randomDigitalSalesRoom.getPrimaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("brandColor2");

		Assert.assertEquals("secondaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			randomDigitalSalesRoom.getSecondaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("btnPrimaryBackgroundColor");

		Assert.assertEquals("primaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			randomDigitalSalesRoom.getPrimaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("btnPrimaryBorderColor");

		Assert.assertEquals("primaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			randomDigitalSalesRoom.getPrimaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject(
			"btnPrimaryHoverBackgroundColor");

		Assert.assertEquals("primaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			randomDigitalSalesRoom.getPrimaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("btnSecondaryBackgroundColor");

		Assert.assertEquals("secondaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			randomDigitalSalesRoom.getSecondaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("btnSecondaryBorderColor");

		Assert.assertEquals("secondaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			randomDigitalSalesRoom.getSecondaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject(
			"btnSecondaryHoverBackgroundColor");

		Assert.assertEquals("secondaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			randomDigitalSalesRoom.getSecondaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("primaryColor");

		Assert.assertEquals(
			randomDigitalSalesRoom.getPrimaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("secondaryColor");

		Assert.assertEquals(
			randomDigitalSalesRoom.getSecondaryColor(),
			jsonObject2.getString("value"));
	}

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private BatchEngineUnitReader _batchEngineUnitReader;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}