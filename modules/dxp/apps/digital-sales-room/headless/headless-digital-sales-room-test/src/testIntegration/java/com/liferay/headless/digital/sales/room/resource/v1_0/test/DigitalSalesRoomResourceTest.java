/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntryModel;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoom;
import com.liferay.headless.digital.sales.room.client.dto.v1_0.UserAccountBrief;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.io.File;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Before;
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

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			StringPool.BLANK, TestPropsValues.getUserId(), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				TestPropsValues.getUserId()));

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

	@Override
	@Test
	public void testPatchDigitalSalesRoom() throws Exception {
		super.testPatchDigitalSalesRoom();

		_testPatchDigitalSalesRoomWithStyleBookEntry();
		_testPatchDigitalSalesRoomWithUserAccount();
	}

	@Override
	@Test
	public void testPostDigitalSalesRoom() throws Exception {
		super.testPostDigitalSalesRoom();

		_testPostDigitalSalesRoomWithSiteInitializer();
		_testPostDigitalSalesRoomWithUserAccount();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"accountId", "channelId", "channelName", "clientName",
			"description", "friendlyUrlPath", "name", "primaryColor",
			"secondaryColor"
		};
	}

	@Override
	protected DigitalSalesRoom randomDigitalSalesRoom() throws Exception {
		return new DigitalSalesRoom() {
			{
				accountId = _accountEntry.getAccountEntryId();
				channelId = RandomTestUtil.nextLong();
				channelName = RandomTestUtil.randomString();
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
	protected DigitalSalesRoom testDeleteDigitalSalesRoom_addDigitalSalesRoom()
		throws Exception {

		return digitalSalesRoomResource.postDigitalSalesRoom(
			randomDigitalSalesRoom());
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
	protected DigitalSalesRoom testPatchDigitalSalesRoom_addDigitalSalesRoom()
		throws Exception {

		return digitalSalesRoomResource.postDigitalSalesRoom(
			randomDigitalSalesRoom());
	}

	@Override
	protected DigitalSalesRoom testPostDigitalSalesRoom_addDigitalSalesRoom(
			DigitalSalesRoom digitalSalesRoom)
		throws Exception {

		return digitalSalesRoomResource.postDigitalSalesRoom(digitalSalesRoom);
	}

	private void _assertEqualsStyleBookEntry(
			DigitalSalesRoom digitalSalesRoom, long groupId)
		throws Exception {

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.fetchStyleBookEntry(
				groupId, "dsr-classic");

		JSONObject jsonObject1 = _jsonFactory.createJSONObject(
			styleBookEntry.getFrontendTokensValues());

		JSONObject jsonObject2 = jsonObject1.getJSONObject("brandColor1");

		Assert.assertEquals("primaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			digitalSalesRoom.getPrimaryColor(), jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("brandColor2");

		Assert.assertEquals("secondaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			digitalSalesRoom.getSecondaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("btnPrimaryBackgroundColor");

		Assert.assertEquals("primaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			digitalSalesRoom.getPrimaryColor(), jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("btnPrimaryBorderColor");

		Assert.assertEquals("primaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			digitalSalesRoom.getPrimaryColor(), jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject(
			"btnPrimaryHoverBackgroundColor");

		Assert.assertEquals("primaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			digitalSalesRoom.getPrimaryColor(), jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("btnSecondaryBackgroundColor");

		Assert.assertEquals("secondaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			digitalSalesRoom.getSecondaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("btnSecondaryBorderColor");

		Assert.assertEquals("secondaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			digitalSalesRoom.getSecondaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject(
			"btnSecondaryHoverBackgroundColor");

		Assert.assertEquals("secondaryColor", jsonObject2.getString("name"));
		Assert.assertEquals(
			digitalSalesRoom.getSecondaryColor(),
			jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("primaryColor");

		Assert.assertEquals(
			digitalSalesRoom.getPrimaryColor(), jsonObject2.getString("value"));

		jsonObject2 = jsonObject1.getJSONObject("secondaryColor");

		Assert.assertEquals(
			digitalSalesRoom.getSecondaryColor(),
			jsonObject2.getString("value"));
	}

	private void _deleteFile(Bundle bundle, String fileName) {
		File file = bundle.getDataFile(
			".com.liferay.digital.sales.room.internal.batch." + fileName +
				".batch.engine.data.json.0.processed");

		if ((file != null) && file.exists()) {
			file.delete();
		}
	}

	private void _testPatchDigitalSalesRoomWithStyleBookEntry()
		throws Exception {

		DigitalSalesRoom postDigitalSalesRoom =
			testPatchDigitalSalesRoom_addDigitalSalesRoom();

		DigitalSalesRoom randomPatchDigitalSalesRoom =
			randomPatchDigitalSalesRoom();

		digitalSalesRoomResource.patchDigitalSalesRoom(
			postDigitalSalesRoom.getId(), randomPatchDigitalSalesRoom);

		_assertEqualsStyleBookEntry(
			randomPatchDigitalSalesRoom, postDigitalSalesRoom.getId());
	}

	private void _testPatchDigitalSalesRoomWithUserAccount() throws Exception {
		DigitalSalesRoom randomDigitalSalesRoom = randomDigitalSalesRoom();

		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		randomDigitalSalesRoom.setUserAccountBriefs(
			new UserAccountBrief[] {
				new UserAccountBrief() {
					{
						setEmailAddress(user1.getEmailAddress());
					}
				},
				new UserAccountBrief() {
					{
						setEmailAddress(user2.getEmailAddress());
					}
				}
			});

		DigitalSalesRoom postDigitalSalesRoom =
			testPostDigitalSalesRoom_addDigitalSalesRoom(
				randomDigitalSalesRoom);

		UserAccountBrief[] userAccountBriefs =
			postDigitalSalesRoom.getUserAccountBriefs();

		Assert.assertEquals(
			Arrays.toString(userAccountBriefs), 3, userAccountBriefs.length);

		long[] userAccountBriefIds = TransformUtil.transformToLongArray(
			userAccountBriefs, UserAccountBrief::getId);

		Assert.assertTrue(
			ArrayUtil.containsAll(
				userAccountBriefIds,
				new long[] {
					TestPropsValues.getUserId(), user1.getUserId(),
					user2.getUserId()
				}));

		User user3 = UserTestUtil.addUser();

		randomDigitalSalesRoom.setUserAccountBriefs(
			new UserAccountBrief[] {
				new UserAccountBrief() {
					{
						setEmailAddress(user3.getEmailAddress());
					}
				}
			});

		DigitalSalesRoom patchDigitalSalesRoom =
			digitalSalesRoomResource.patchDigitalSalesRoom(
				postDigitalSalesRoom.getId(), randomDigitalSalesRoom);

		userAccountBriefs = patchDigitalSalesRoom.getUserAccountBriefs();

		Assert.assertEquals(
			Arrays.toString(userAccountBriefs), 4, userAccountBriefs.length);

		userAccountBriefIds = TransformUtil.transformToLongArray(
			userAccountBriefs, UserAccountBrief::getId);

		Assert.assertTrue(
			ArrayUtil.containsAll(
				userAccountBriefIds,
				new long[] {
					TestPropsValues.getUserId(), user1.getUserId(),
					user2.getUserId(), user3.getUserId()
				}));

		randomDigitalSalesRoom.setUserAccountBriefs((UserAccountBrief[])null);

		patchDigitalSalesRoom = digitalSalesRoomResource.patchDigitalSalesRoom(
			postDigitalSalesRoom.getId(), randomDigitalSalesRoom);

		userAccountBriefs = patchDigitalSalesRoom.getUserAccountBriefs();

		Assert.assertEquals(
			Arrays.toString(userAccountBriefs), 4, userAccountBriefs.length);

		userAccountBriefIds = TransformUtil.transformToLongArray(
			userAccountBriefs, UserAccountBrief::getId);

		Assert.assertTrue(
			ArrayUtil.containsAll(
				userAccountBriefIds,
				new long[] {
					TestPropsValues.getUserId(), user1.getUserId(),
					user2.getUserId(), user3.getUserId()
				}));
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

		_assertEqualsStyleBookEntry(
			randomDigitalSalesRoom, postDigitalSalesRoom.getId());
	}

	private void _testPostDigitalSalesRoomWithUserAccount() throws Exception {
		DigitalSalesRoom randomDigitalSalesRoom = randomDigitalSalesRoom();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);
		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		randomDigitalSalesRoom.setUserAccountBriefs(
			new UserAccountBrief[] {
				new UserAccountBrief() {
					{
						setEmailAddress(user1.getEmailAddress());
					}
				},
				new UserAccountBrief() {
					{
						setEmailAddress(user2.getEmailAddress());
						setRoleKey(role.getName());
					}
				}
			});

		DigitalSalesRoom postDigitalSalesRoom =
			testPostDigitalSalesRoom_addDigitalSalesRoom(
				randomDigitalSalesRoom);

		UserAccountBrief[] userAccountBriefs =
			postDigitalSalesRoom.getUserAccountBriefs();

		Assert.assertEquals(
			Arrays.toString(userAccountBriefs), 3, userAccountBriefs.length);

		for (UserAccountBrief userAccountBrief :
				postDigitalSalesRoom.getUserAccountBriefs()) {

			if (userAccountBrief.getId() == TestPropsValues.getUserId()) {
				Assert.assertEquals(
					userAccountBrief,
					_toUserAccountBrief(
						_roleLocalService.fetchRole(
							TestPropsValues.getCompanyId(),
							RoleConstants.SITE_OWNER),
						TestPropsValues.getUser()));
			}
			else if (userAccountBrief.getId() == user1.getUserId()) {
				Assert.assertEquals(
					userAccountBrief, _toUserAccountBrief(null, user1));
			}
			else if (userAccountBrief.getId() == user2.getUserId()) {
				Assert.assertEquals(
					userAccountBrief, _toUserAccountBrief(role, user2));
			}
			else {
				Assert.assertTrue(false);
			}
		}
	}

	private UserAccountBrief _toUserAccountBrief(Role role, User user) {
		return new UserAccountBrief() {
			{
				setAlternateName(user::getScreenName);
				setEmailAddress(user::getEmailAddress);
				setExternalReferenceCode(user::getExternalReferenceCode);
				setId(user::getUserId);
				setName(user::getFullName);
				setRoleKey(
					() -> {
						if (role == null) {
							return null;
						}

						return role.getName();
					});
			}
		};
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

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
	private RoleLocalService _roleLocalService;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}