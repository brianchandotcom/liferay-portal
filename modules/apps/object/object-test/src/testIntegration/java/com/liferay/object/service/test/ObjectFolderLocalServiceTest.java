/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.exportimport.test.util.ExportImportConfigurationTemporarySwapper;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.exception.DuplicateObjectFolderExternalReferenceCodeException;
import com.liferay.object.exception.NoSuchObjectFolderException;
import com.liferay.object.exception.ObjectFolderLabelException;
import com.liferay.object.exception.ObjectFolderNameException;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.List;
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
		_defaultObjectFolder = _objectFolderLocalService.getObjectFolder(
			TestPropsValues.getCompanyId(), ObjectFolderConstants.NAME_DEFAULT);
	}

	@Test
	public void testAddObjectFolder() throws Exception {
		AssertUtils.assertFailure(
			DuplicateObjectFolderExternalReferenceCodeException.class,
			StringBundler.concat(
				"Duplicate object folder with external reference code ",
				_defaultObjectFolder.getExternalReferenceCode(),
				" and company ", _defaultObjectFolder.getCompanyId()),
			() -> _objectFolderLocalService.addObjectFolder(
				_defaultObjectFolder.getExternalReferenceCode(),
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
			"Duplicate name " + _defaultObjectFolder.getName(),
			() -> _objectFolderLocalService.addObjectFolder(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				_defaultObjectFolder.getName()));
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
			externalReferenceCode, labelMap, name,
			WorkflowConstants.STATUS_APPROVED, objectFolder);

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
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		String originalName = PrincipalThreadLocal.getName();

		Company company = CompanyTestUtil.addCompany();

		PortalInstances.initCompany(company);

		ObjectFolder objectFolder = null;

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					company.getCompanyId())) {

			User user = UserTestUtil.getAdminUser(company.getCompanyId());

			Assert.assertNotNull(user);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));
			PrincipalThreadLocal.setName(user.getUserId());

			objectFolder = _addObjectFolder(user);

			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				objectFolder.getObjectFolderId(),
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.emptyList(), user.getUserId());
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				objectFolder.getObjectFolderId(),
				ObjectDefinitionTestUtil.getRandomName(),
				Collections.emptyList(), user.getUserId());

			Assert.assertNotNull(
				_objectFolderLocalService.getObjectFolder(
					company.getCompanyId(),
					ObjectFolderConstants.NAME_DEFAULT));
		}
		finally {
			_companyLocalService.deleteCompany(company);

			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}

		Assert.assertNull(
			_objectFolderLocalService.fetchObjectFolder(
				company.getCompanyId(), ObjectFolderConstants.NAME_DEFAULT));

		Assert.assertNull(
			_objectFolderLocalService.fetchObjectFolder(
				objectFolder.getObjectFolderId()));
	}

	@Test
	public void testDeleteObjectFolder() throws Exception {
		AssertUtils.assertFailure(
			UnsupportedOperationException.class, "Default cannot be deleted",
			() -> _objectFolderLocalService.deleteObjectFolder(
				_defaultObjectFolder.getObjectFolderId()));

		ObjectFolder objectFolder = _addObjectFolder(TestPropsValues.getUser());

		ObjectDefinitionTestUtil.addCustomObjectDefinition(
			objectFolder.getObjectFolderId());
		ObjectDefinitionTestUtil.addCustomObjectDefinition(
			objectFolder.getObjectFolderId());

		int count =
			_objectDefinitionLocalService.getObjectFolderObjectDefinitionsCount(
				_defaultObjectFolder.getObjectFolderId());

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
			count + 2,
			_objectDefinitionLocalService.getObjectFolderObjectDefinitionsCount(
				_defaultObjectFolder.getObjectFolderId()));
	}

	@Test
	public void testGetOrAddEmptyObjectFolder() throws Exception {

		// Lazy referencing disabled

		String externalReferenceCode = RandomTestUtil.randomString();

		AssertUtils.assertFailure(
			NoSuchObjectFolderException.class,
			String.format(
				"No ObjectFolder exists with the key {" +
					"externalReferenceCode=%s, companyId=%s}",
				externalReferenceCode, TestPropsValues.getCompanyId()),
			() -> _objectFolderLocalService.getOrAddEmptyObjectFolder(
				externalReferenceCode, TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId()));

		// Lazy referencing enabled

		long exportImportConfigurationId = RandomTestUtil.randomLong();

		try (AutoCloseable autoCloseable =
				new ExportImportConfigurationTemporarySwapper(
					exportImportConfigurationId)) {

			ObjectFolder objectFolder =
				_objectFolderLocalService.getOrAddEmptyObjectFolder(
					externalReferenceCode, TestPropsValues.getCompanyId(),
					TestPropsValues.getUserId());

			Assert.assertEquals(
				WorkflowConstants.STATUS_EMPTY, objectFolder.getStatus());

			List<ExportImportReportEntry> exportImportReportEntries =
				_exportImportReportEntryLocalService.
					getExportImportReportEntries(
						TestPropsValues.getCompanyId(),
						exportImportConfigurationId);

			Assert.assertEquals(
				exportImportReportEntries.toString(), 1,
				exportImportReportEntries.size());

			ExportImportReportEntry exportImportReportEntry =
				exportImportReportEntries.get(0);

			Assert.assertEquals(
				externalReferenceCode,
				exportImportReportEntry.getClassExternalReferenceCode());
			Assert.assertEquals(
				ExportImportReportEntryConstants.TYPE_EMPTY,
				exportImportReportEntry.getType());

			objectFolder = _objectFolderLocalService.updateObjectFolder(
				objectFolder.getExternalReferenceCode(),
				objectFolder.getObjectFolderId(), objectFolder.getLabelMap());

			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, objectFolder.getStatus());
		}
	}

	@Test
	public void testUpdateObjectFolder() throws Exception {
		ObjectFolder objectFolder1 = _addObjectFolder(
			TestPropsValues.getUser());

		AssertUtils.assertFailure(
			DuplicateObjectFolderExternalReferenceCodeException.class,
			StringBundler.concat(
				"Duplicate object folder with external reference code ",
				_defaultObjectFolder.getExternalReferenceCode(),
				" and company ", _defaultObjectFolder.getCompanyId()),
			() -> _objectFolderLocalService.updateObjectFolder(
				_defaultObjectFolder.getExternalReferenceCode(),
				objectFolder1.getObjectFolderId(),
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString())));
		AssertUtils.assertFailure(
			ObjectFolderLabelException.class,
			"Label is null for locale " + LocaleUtil.US.getDisplayName(),
			() -> _objectFolderLocalService.updateObjectFolder(
				RandomTestUtil.randomString(),
				objectFolder1.getObjectFolderId(), null));

		_objectFolderLocalService.deleteObjectFolder(objectFolder1);

		ObjectFolder objectFolder2 = _addObjectFolder(
			TestPropsValues.getUser());

		String externalReferenceCode = RandomTestUtil.randomString();
		Map<Locale, String> labelMap = LocalizedMapUtil.getLocalizedMap(
			RandomTestUtil.randomString());

		objectFolder2 = _objectFolderLocalService.updateObjectFolder(
			externalReferenceCode, objectFolder2.getObjectFolderId(), labelMap);

		_assertObjectFolder(
			externalReferenceCode, labelMap, objectFolder2.getName(),
			WorkflowConstants.STATUS_APPROVED, objectFolder2);

		_objectFolderLocalService.deleteObjectFolder(objectFolder2);
	}

	private ObjectFolder _addObjectFolder(User user) throws Exception {
		return _objectFolderLocalService.addObjectFolder(
			RandomTestUtil.randomString(), user.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString());
	}

	private void _assertObjectFolder(
			String expectedExternalReferenceCode,
			Map<Locale, String> expectedLabelMap, String expectedName,
			int expectedStatus, ObjectFolder objectFolder)
		throws Exception {

		Assert.assertEquals(
			expectedExternalReferenceCode,
			objectFolder.getExternalReferenceCode());
		Assert.assertEquals(
			TestPropsValues.getCompanyId(), objectFolder.getCompanyId());
		Assert.assertEquals(
			TestPropsValues.getUserId(), objectFolder.getUserId());
		Assert.assertEquals(expectedLabelMap, objectFolder.getLabelMap());
		Assert.assertEquals(expectedName, objectFolder.getName());
		Assert.assertEquals(expectedStatus, objectFolder.getStatus());
	}

	private static ObjectFolder _defaultObjectFolder;

	@Inject
	private static ObjectFolderLocalService _objectFolderLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}