/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.object.client.dto.v1_0.ObjectEntryFolder;
import com.liferay.headless.object.client.dto.v1_0.ParentObjectEntryFolderBrief;
import com.liferay.headless.object.client.pagination.Page;
import com.liferay.headless.object.client.pagination.Pagination;
import com.liferay.headless.object.client.problem.Problem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@FeatureFlags("LPD-17564")
@RunWith(Arquillian.class)
public class ObjectEntryFolderResourceTest
	extends BaseObjectEntryFolderResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_testDepotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			new ServiceContext() {
				{
					setCompanyId(testGroup.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});

		_testDepotEntryGroup = _groupLocalService.getGroup(
			_testDepotEntry.getGroupId());
	}

	@Override
	@Test
	public void testGetScopeScopeKeyObjectEntryFoldersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		String scopeKey =
			testGetScopeScopeKeyObjectEntryFoldersPage_getScopeKey();

		ObjectEntryFolder objectEntryFolder1 = randomObjectEntryFolder();

		objectEntryFolder1 =
			testGetScopeScopeKeyObjectEntryFoldersPage_addObjectEntryFolder(
				scopeKey, objectEntryFolder1);

		EntityField nameEntityField = new StringEntityField(
			"name", locale -> Field.getSortableFieldName(Field.NAME));

		for (EntityField entityField : entityFields) {
			Page<ObjectEntryFolder> page =
				objectEntryFolderResource.
					getScopeScopeKeyObjectEntryFoldersPage(
						scopeKey, null, null, null,
						StringBundler.concat(
							getFilterString(
								entityField, "between", objectEntryFolder1),
							" and ",
							getFilterString(
								nameEntityField, "contains",
								objectEntryFolder1)),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(objectEntryFolder1),
				(List<ObjectEntryFolder>)page.getItems());
		}
	}

	@Override
	@Test
	public void testPatchScopeScopeKeyObjectEntryFolderByExternalReferenceCode()
		throws Exception {

		super.testPatchScopeScopeKeyObjectEntryFolderByExternalReferenceCode();

		_testPatchScopeScopeKeyObjectEntryFolderByExternalReferenceCodeWithGroupKey();
	}

	@Override
	@Test
	public void testPostScopeScopeKeyObjectEntryFolder() throws Exception {
		super.testPostScopeScopeKeyObjectEntryFolder();

		_testPostScopeScopeKeyObjectEntryFolderWithExistingParentObjectEntryFolder();
		_testPostScopeScopeKeyObjectEntryFolderWithNonexistentParentObjectEntryFolder();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"label", "name"};
	}

	@Override
	protected ObjectEntryFolder randomObjectEntryFolder() throws Exception {
		return new ObjectEntryFolder() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				label = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				numberOfObjectEntries = RandomTestUtil.randomInt();
				numberOfObjectEntryFolders = RandomTestUtil.randomInt();
				parentObjectEntryFolderId = 0L;
			}
		};
	}

	@Override
	protected ObjectEntryFolder
			testDeleteObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testDeleteScopeScopeKeyObjectEntryFolderByExternalReferenceCode_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder testGetObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testGetScopeScopeKeyObjectEntryFolderByExternalReferenceCode_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testGetScopeScopeKeyObjectEntryFoldersPage_addObjectEntryFolder(
				String scopeKey, ObjectEntryFolder objectEntryFolder)
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			scopeKey, objectEntryFolder);
	}

	@Override
	protected String testGetScopeScopeKeyObjectEntryFoldersPage_getScopeKey() {
		return String.valueOf(_testDepotEntry.getGroupId());
	}

	@Override
	protected ObjectEntryFolder
			testGraphQLObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testPatchObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testPatchScopeScopeKeyObjectEntryFolderByExternalReferenceCode_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testPostScopeScopeKeyObjectEntryFolder_addObjectEntryFolder(
				ObjectEntryFolder objectEntryFolder)
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()), objectEntryFolder);
	}

	@Override
	protected ObjectEntryFolder
			testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCode_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCode_createObjectEntryFolder()
		throws Exception {

		ObjectEntryFolder objectEntryFolder = randomObjectEntryFolder();

		objectEntryFolder.setScopeKey(
			String.valueOf(_testDepotEntry.getGroupId()));

		return objectEntryFolder;
	}

	private ParentObjectEntryFolderBrief _randomParentObjectEntryFolderBrief(
			String parentExternalReferenceCode, long parentObjectEntryFolderId)
		throws Exception {

		return new ParentObjectEntryFolderBrief() {
			{
				externalReferenceCode = parentExternalReferenceCode;
				id = parentObjectEntryFolderId;
				label = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	private void _testPatchScopeScopeKeyObjectEntryFolderByExternalReferenceCodeWithGroupKey()
		throws Exception {

		ObjectEntryFolder postObjectEntryFolder =
			testPatchScopeScopeKeyObjectEntryFolderByExternalReferenceCode_addObjectEntryFolder();

		ObjectEntryFolder randomPatchObjectEntryFolder =
			randomPatchObjectEntryFolder();

		ObjectEntryFolder patchObjectEntryFolder =
			objectEntryFolderResource.
				patchScopeScopeKeyObjectEntryFolderByExternalReferenceCode(
					_testDepotEntryGroup.getGroupKey(),
					postObjectEntryFolder.getExternalReferenceCode(),
					randomPatchObjectEntryFolder);

		ObjectEntryFolder expectedPatchObjectEntryFolder =
			postObjectEntryFolder.clone();

		BeanTestUtil.copyProperties(
			randomPatchObjectEntryFolder, expectedPatchObjectEntryFolder);

		ObjectEntryFolder getObjectEntryFolder =
			objectEntryFolderResource.
				getScopeScopeKeyObjectEntryFolderByExternalReferenceCode(
					_testDepotEntryGroup.getGroupKey(),
					patchObjectEntryFolder.getExternalReferenceCode());

		assertEquals(expectedPatchObjectEntryFolder, getObjectEntryFolder);
		assertValid(getObjectEntryFolder);
	}

	private void _testPostScopeScopeKeyObjectEntryFolderWithExistingParentObjectEntryFolder()
		throws Exception {

		ObjectEntryFolder randomObjectEntryFolder = randomObjectEntryFolder();

		ObjectEntryFolder parentObjectEntryFolder =
			testPostScopeScopeKeyObjectEntryFolder_addObjectEntryFolder(
				randomObjectEntryFolder());

		randomObjectEntryFolder.setParentObjectEntryFolderBrief(
			_randomParentObjectEntryFolderBrief(
				parentObjectEntryFolder.getExternalReferenceCode(), 0));

		ObjectEntryFolder postObjectEntryFolder =
			testPostScopeScopeKeyObjectEntryFolder_addObjectEntryFolder(
				randomObjectEntryFolder);

		assertEquals(randomObjectEntryFolder, postObjectEntryFolder);
		assertValid(postObjectEntryFolder);

		Assert.assertEquals(
			postObjectEntryFolder.
				getParentObjectEntryFolderExternalReferenceCode(),
			parentObjectEntryFolder.getExternalReferenceCode());
		Assert.assertEquals(
			postObjectEntryFolder.getParentObjectEntryFolderId(),
			parentObjectEntryFolder.getId());
	}

	private void _testPostScopeScopeKeyObjectEntryFolderWithNonexistentParentObjectEntryFolder()
		throws Exception {

		ObjectEntryFolder randomObjectEntryFolder = randomObjectEntryFolder();

		randomObjectEntryFolder.setParentObjectEntryFolderBrief(
			_randomParentObjectEntryFolderBrief(
				StringUtil.toLowerCase(RandomTestUtil.randomString()), 0L));

		try {
			testPostScopeScopeKeyObjectEntryFolder_addObjectEntryFolder(
				randomObjectEntryFolder);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private DepotEntry _testDepotEntry;

	private Group _testDepotEntryGroup;

}