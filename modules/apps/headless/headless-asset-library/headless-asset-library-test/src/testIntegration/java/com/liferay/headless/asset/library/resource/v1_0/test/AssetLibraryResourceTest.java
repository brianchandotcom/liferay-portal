/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.asset.library.client.dto.v1_0.AssetLibrary;
import com.liferay.headless.asset.library.client.problem.Problem;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@FeatureFlags("LPD-17564")
@RunWith(Arquillian.class)
public class AssetLibraryResourceTest extends BaseAssetLibraryResourceTestCase {

	@Override
	@Test
	public void testDeleteAssetLibrary() throws Exception {
		super.testDeleteAssetLibrary();

		// Nonexistent asset library ID

		long assetLibraryId = RandomTestUtil.randomLong();

		try {
			assetLibraryResource.deleteAssetLibrary(assetLibraryId);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testDeleteAssetLibraryLinkToSite() throws Exception {
		AssetLibrary assetLibrary =
			testDeleteAssetLibraryLinkToSite_addAssetLibrary();

		_assertLinkedSites(assetLibrary);

		assetLibrary = assetLibraryResource.deleteAssetLibraryLinkToSite(
			assetLibrary.getId(), testGroup.getGroupId());

		Assert.assertTrue(ArrayUtil.isEmpty(assetLibrary.getLinkedSiteIds()));
		Assert.assertTrue(
			ArrayUtil.isEmpty(
				assetLibrary.getLinkedSitesExternalReferenceCodes()));
	}

	@Override
	@Test
	public void testDeleteAssetLibraryUserAccountUser() throws Exception {
		AssetLibrary assetLibrary = _addAssetLibrary();

		Integer initialUsersCount = assetLibrary.getUsersCount();

		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		assetLibraryResource.postAssetLibraryUserAccountUser(
			assetLibrary.getId(), user1.getUserId());
		assetLibraryResource.postAssetLibraryUserAccountUser(
			assetLibrary.getId(), user2.getUserId());

		assetLibrary = assetLibraryResource.deleteAssetLibraryUserAccountUser(
			assetLibrary.getId(), user1.getUserId());

		Assert.assertEquals(
			(Integer)(initialUsersCount + 1), assetLibrary.getUsersCount());

		List<User> groupUsers = _userLocalService.getGroupUsers(
			assetLibrary.getSiteId());

		Assert.assertFalse(groupUsers.contains(user1));
		Assert.assertTrue(groupUsers.contains(user2));
	}

	@Override
	@Test
	public void testDeleteAssetLibraryUserGroup() throws Exception {
		AssetLibrary assetLibrary = _addAssetLibrary();

		UserGroup userGroup1 = UserGroupTestUtil.addUserGroup();
		UserGroup userGroup2 = UserGroupTestUtil.addUserGroup();

		assetLibraryResource.postAssetLibraryUserGroup(
			assetLibrary.getId(), userGroup1.getUserGroupId());
		assetLibraryResource.postAssetLibraryUserGroup(
			assetLibrary.getId(), userGroup2.getUserGroupId());

		assetLibrary = assetLibraryResource.deleteAssetLibraryUserGroup(
			assetLibrary.getId(), userGroup1.getUserGroupId());

		List<UserGroup> groupUserGroups =
			_userGroupLocalService.getGroupUserGroups(assetLibrary.getSiteId());

		Assert.assertEquals(
			groupUserGroups.toString(), 1, groupUserGroups.size());

		Assert.assertFalse(groupUserGroups.contains(userGroup1));
		Assert.assertTrue(groupUserGroups.contains(userGroup2));
	}

	@Override
	@Test
	public void testPatchAssetLibraryBySite() throws Exception {
		AssetLibrary postAssetLibrary =
			testPatchAssetLibraryBySite_addAssetLibrary();

		AssetLibrary randomPatchAssetLibrary = randomPatchAssetLibrary();

		assetLibraryResource.patchAssetLibraryBySite(
			postAssetLibrary.getSiteId(), randomPatchAssetLibrary);

		AssetLibrary expectedPatchAssetLibrary = postAssetLibrary.clone();

		BeanTestUtil.copyProperties(
			randomPatchAssetLibrary, expectedPatchAssetLibrary);

		AssetLibrary getAssetLibrary =
			assetLibraryResource.getAssetLibraryBySite(
				postAssetLibrary.getSiteId());

		assertEquals(expectedPatchAssetLibrary, getAssetLibrary);
		assertValid(getAssetLibrary);
	}

	@Override
	@Test
	public void testPostAssetLibraryLinkToSite() throws Exception {
		AssetLibrary assetLibrary =
			testPostAssetLibraryLinkToSite_addAssetLibrary(
				randomAssetLibrary());

		_assertLinkedSites(assetLibrary);
	}

	@Override
	@Test
	public void testPostAssetLibraryUserAccountUser() throws Exception {
		AssetLibrary assetLibrary = _addAssetLibrary();

		Integer initialUsersCount = assetLibrary.getUsersCount();

		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		assetLibraryResource.postAssetLibraryUserAccountUser(
			assetLibrary.getId(), user1.getUserId());

		assetLibrary = assetLibraryResource.postAssetLibraryUserAccountUser(
			assetLibrary.getId(), user2.getUserId());

		Assert.assertEquals(
			(Integer)(initialUsersCount + 2), assetLibrary.getUsersCount());

		List<User> groupUsers = _userLocalService.getGroupUsers(
			assetLibrary.getSiteId());

		Assert.assertTrue(groupUsers.contains(user1));
		Assert.assertTrue(groupUsers.contains(user2));
	}

	@Override
	@Test
	public void testPostAssetLibraryUserGroup() throws Exception {
		AssetLibrary assetLibrary = _addAssetLibrary();

		UserGroup userGroup1 = UserGroupTestUtil.addUserGroup();
		UserGroup userGroup2 = UserGroupTestUtil.addUserGroup();

		assetLibraryResource.postAssetLibraryUserGroup(
			assetLibrary.getId(), userGroup1.getUserGroupId());

		assetLibrary = assetLibraryResource.postAssetLibraryUserGroup(
			assetLibrary.getId(), userGroup2.getUserGroupId());

		List<UserGroup> groupUserGroups =
			_userGroupLocalService.getGroupUserGroups(assetLibrary.getSiteId());

		Assert.assertEquals(
			groupUserGroups.toString(), 2, groupUserGroups.size());
		Assert.assertTrue(groupUserGroups.contains(userGroup1));
		Assert.assertTrue(groupUserGroups.contains(userGroup2));
	}

	@Override
	protected void assertValid(AssetLibrary assetLibrary) throws Exception {
		DepotEntry originalTestDepotEntry = testDepotEntry;
		Group originalTestGroup = testGroup;

		DepotEntry depotEntry = _depotEntryLocalService.getDepotEntry(
			assetLibrary.getId());

		testDepotEntry = depotEntry;
		testGroup = depotEntry.getGroup();

		super.assertValid(assetLibrary);

		testDepotEntry = originalTestDepotEntry;
		testGroup = originalTestGroup;
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return new ArrayList<>();
	}

	@Override
	protected AssetLibrary randomAssetLibrary() throws Exception {
		return new AssetLibrary() {
			{
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected AssetLibrary randomPatchAssetLibrary() throws Exception {
		AssetLibrary assetLibrary = randomAssetLibrary();

		assetLibrary.setName(RandomTestUtil.randomString());

		return assetLibrary;
	}

	@Override
	protected AssetLibrary testDeleteAssetLibrary_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary testDeleteAssetLibraryBySite_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary testDeleteAssetLibraryLinkToSite_addAssetLibrary()
		throws Exception {

		AssetLibrary assetLibrary = _addAssetLibrary();

		DepotEntry depotEntry = _depotEntryLocalService.getGroupDepotEntry(
			assetLibrary.getSiteId());

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), testGroup.getGroupId());

		return assetLibraryResource.getAssetLibrary(assetLibrary.getId());
	}

	@Override
	protected AssetLibrary testGetAssetLibrariesPage_addAssetLibrary(
			AssetLibrary assetLibrary)
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary testGetAssetLibrary_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary testGetAssetLibraryBySite_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary testPatchAssetLibrary_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary testPatchAssetLibraryBySite_addAssetLibrary()
		throws Exception {

		return _addAssetLibrary();
	}

	@Override
	protected AssetLibrary testPostAssetLibrary_addAssetLibrary(
			AssetLibrary assetLibrary)
		throws Exception {

		return assetLibraryResource.postAssetLibrary(assetLibrary);
	}

	@Override
	protected AssetLibrary testPostAssetLibraryLinkToSite_addAssetLibrary(
			AssetLibrary assetLibrary)
		throws Exception {

		assetLibrary = assetLibraryResource.postAssetLibrary(assetLibrary);

		DepotEntry depotEntry = _depotEntryLocalService.getGroupDepotEntry(
			assetLibrary.getSiteId());

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), testGroup.getGroupId());

		return assetLibraryResource.getAssetLibrary(assetLibrary.getId());
	}

	private AssetLibrary _addAssetLibrary() throws Exception {
		return assetLibraryResource.postAssetLibrary(randomAssetLibrary());
	}

	private void _assertLinkedSites(AssetLibrary assetLibrary) {
		Long[] linkedSiteIds = assetLibrary.getLinkedSiteIds();
		String[] linkedSitesExternalReferenceCodes =
			assetLibrary.getLinkedSitesExternalReferenceCodes();

		Assert.assertEquals(testGroup.getGroupId(), (long)linkedSiteIds[0]);
		Assert.assertEquals(
			testGroup.getExternalReferenceCode(),
			linkedSitesExternalReferenceCodes[0]);
	}

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserLocalService _userLocalService;

}