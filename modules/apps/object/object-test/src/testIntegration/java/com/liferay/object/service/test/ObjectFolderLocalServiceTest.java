/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.exception.DuplicateObjectFolderExternalReferenceCodeException;
import com.liferay.object.exception.ObjectFolderLabelException;
import com.liferay.object.exception.ObjectFolderNameException;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Murilo Stodolni
 */
@RunWith(Arquillian.class)
public class ObjectFolderLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_uncategorizedObjectFolder = _objectFolderLocalService.getObjectFolder(
			TestPropsValues.getCompanyId(),
			ObjectFolderConstants.NAME_UNCATEGORIZED);
	}

	@Test
	public void testAddObjectFolder() throws Exception {
		AssertUtils.assertFailure(
			DuplicateObjectFolderExternalReferenceCodeException.class,
			StringBundler.concat(
				"Duplicate object folder with external reference code ",
				_uncategorizedObjectFolder.getExternalReferenceCode(),
				" and company ", _uncategorizedObjectFolder.getCompanyId()),
			() -> _objectFolderLocalService.addObjectFolder(
				_uncategorizedObjectFolder.getExternalReferenceCode(),
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				RandomTestUtil.randomString()));

		AssertUtils.assertFailure(
			ObjectFolderLabelException.class,
			"Label is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _objectFolderLocalService.addObjectFolder(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				null, RandomTestUtil.randomString()));

		AssertUtils.assertFailure(
			ObjectFolderNameException.MustBeLessThan41Characters.class,
			"Name must be less than 41 characters",
			() -> _objectFolderLocalService.addObjectFolder(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				RandomTestUtil.randomString(42)));

		AssertUtils.assertFailure(
			ObjectFolderNameException.MustNotBeDuplicate.class,
			"Duplicate name " + _uncategorizedObjectFolder.getName(),
			() -> _objectFolderLocalService.addObjectFolder(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				_uncategorizedObjectFolder.getName()));

		AssertUtils.assertFailure(
			ObjectFolderNameException.MustNotBeNull.class, "Name is null",
			() -> _objectFolderLocalService.addObjectFolder(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				null));

		AssertUtils.assertFailure(
			ObjectFolderNameException.MustOnlyContainLettersAndDigits.class,
			"Name must only contain letters and digits",
			() -> _objectFolderLocalService.addObjectFolder(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Abl e"));

		AssertUtils.assertFailure(
			ObjectFolderNameException.MustOnlyContainLettersAndDigits.class,
			"Name must only contain letters and digits",
			() -> _objectFolderLocalService.addObjectFolder(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"Abl-e"));

		String externalReferenceCode = RandomTestUtil.randomString();
		Map<Locale, String> labelMap = LocalizedMapUtil.getLocalizedMap(
			RandomTestUtil.randomString());
		String name = RandomTestUtil.randomString();

		ObjectFolder objectFolder = _objectFolderLocalService.addObjectFolder(
			externalReferenceCode, TestPropsValues.getUserId(), labelMap, name);

		_assertObjectFolder(
			externalReferenceCode, labelMap, name, objectFolder);

		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				objectFolder.getCompanyId(), ObjectFolder.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectFolder.getObjectFolderId())));

		_objectFolderLocalService.deleteObjectFolder(objectFolder);
	}

	@Test
	public void testDeleteCompanyObjectFolders() throws Exception {
		String originalName = PrincipalThreadLocal.getName();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Company company = CompanyTestUtil.addCompany();

		ObjectFolder objectFolder = null;

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(
					company.getCompanyId())) {

			User user = UserTestUtil.getAdminUser(company.getCompanyId());

			Assert.assertNotNull(user);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			PrincipalThreadLocal.setName(user.getUserId());

			objectFolder = _addObjectFolder(user);

			ObjectDefinitionTestUtil.addObjectDefinition(
				objectFolder.getObjectFolderId(),
				_objectDefinitionLocalService);
			ObjectDefinitionTestUtil.addObjectDefinition(
				objectFolder.getObjectFolderId(),
				_objectDefinitionLocalService);

			Assert.assertNotNull(
				_objectFolderLocalService.getObjectFolder(
					company.getCompanyId(),
					ObjectFolderConstants.NAME_UNCATEGORIZED));
		}
		finally {
			_companyLocalService.deleteCompany(company);

			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}

		Assert.assertNull(
			_objectFolderLocalService.fetchObjectFolder(
				company.getCompanyId(),
				ObjectFolderConstants.NAME_UNCATEGORIZED));

		Assert.assertNull(
			_objectFolderLocalService.fetchObjectFolder(
				objectFolder.getObjectFolderId()));
	}

	@Test
	public void testDeleteObjectFolder() throws Exception {
		AssertUtils.assertFailure(
			UnsupportedOperationException.class,
			"Uncategorized cannot be deleted",
			() -> _objectFolderLocalService.deleteObjectFolder(
				_uncategorizedObjectFolder.getObjectFolderId()));

		ObjectFolder objectFolder = _addObjectFolder(TestPropsValues.getUser());

		ObjectDefinitionTestUtil.addObjectDefinition(
			objectFolder.getObjectFolderId(), _objectDefinitionLocalService);
		ObjectDefinitionTestUtil.addObjectDefinition(
			objectFolder.getObjectFolderId(), _objectDefinitionLocalService);

		int uncategorizedObjectFolderObjectDefinitionsCount =
			_objectDefinitionLocalService.getObjectFolderObjectDefinitionsCount(
				_uncategorizedObjectFolder.getObjectFolderId());

		objectFolder = _objectFolderLocalService.deleteObjectFolder(
			objectFolder.getObjectFolderId());

		Assert.assertNull(
			_objectFolderLocalService.fetchObjectFolder(
				objectFolder.getObjectFolderId()));

		Assert.assertEquals(
			0,
			_objectDefinitionLocalService.getObjectFolderObjectDefinitionsCount(
				objectFolder.getObjectFolderId()));

		Assert.assertEquals(
			uncategorizedObjectFolderObjectDefinitionsCount + 2,
			_objectDefinitionLocalService.getObjectFolderObjectDefinitionsCount(
				_uncategorizedObjectFolder.getObjectFolderId()));
	}

	@Test
	public void testUpdateObjectFolder() throws Exception {
		ObjectFolder objectFolder1 = _addObjectFolder(
			TestPropsValues.getUser());

		AssertUtils.assertFailure(
			DuplicateObjectFolderExternalReferenceCodeException.class,
			StringBundler.concat(
				"Duplicate object folder with external reference code ",
				_uncategorizedObjectFolder.getExternalReferenceCode(),
				" and company ", _uncategorizedObjectFolder.getCompanyId()),
			() -> _objectFolderLocalService.updateObjectFolder(
				_uncategorizedObjectFolder.getExternalReferenceCode(),
				objectFolder1.getObjectFolderId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				Collections.emptyList()));

		AssertUtils.assertFailure(
			ObjectFolderLabelException.class,
			"Label is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _objectFolderLocalService.updateObjectFolder(
				RandomTestUtil.randomString(),
				objectFolder1.getObjectFolderId(), null,
				Collections.emptyList()));

		_objectFolderLocalService.deleteObjectFolder(objectFolder1);

		ObjectFolder objectFolder2 = _addObjectFolder(
			TestPropsValues.getUser());

		String externalReferenceCode = RandomTestUtil.randomString();
		Map<Locale, String> labelMap = LocalizedMapUtil.getLocalizedMap(
			RandomTestUtil.randomString());

		objectFolder2 = _objectFolderLocalService.updateObjectFolder(
			externalReferenceCode, objectFolder2.getObjectFolderId(), labelMap,
			Collections.emptyList());

		_assertObjectFolder(
			externalReferenceCode, labelMap, objectFolder2.getName(),
			objectFolder2);

		_objectFolderLocalService.deleteObjectFolder(objectFolder2);
	}

	private ObjectFolder _addObjectFolder(User user) throws Exception {
		return _objectFolderLocalService.addObjectFolder(
			RandomTestUtil.randomString(), user.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString());
	}

	private void _assertObjectFolder(
			String externalReferenceCode, Map<Locale, String> labelMap,
			String name, ObjectFolder objectFolder)
		throws Exception {

		Assert.assertEquals(
			externalReferenceCode, objectFolder.getExternalReferenceCode());
		Assert.assertEquals(
			TestPropsValues.getCompanyId(), objectFolder.getCompanyId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), objectFolder.getUserId());
		Assert.assertEquals(labelMap, objectFolder.getLabelMap());
		Assert.assertEquals(name, objectFolder.getName());
	}

	@Inject
	private static ObjectFolderLocalService _objectFolderLocalService;

	private static ObjectFolder _uncategorizedObjectFolder;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}