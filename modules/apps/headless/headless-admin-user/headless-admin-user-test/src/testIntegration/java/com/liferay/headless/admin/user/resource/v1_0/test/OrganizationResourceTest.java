/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.admin.user.client.dto.v1_0.CustomField;
import com.liferay.headless.admin.user.client.dto.v1_0.CustomValue;
import com.liferay.headless.admin.user.client.dto.v1_0.Organization;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.io.InputStream;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class OrganizationResourceTest extends BaseOrganizationResourceTestCase {

	@ClassRule
	@Rule
	public static final SynchronousMailTestRule synchronousMailTestRule =
		SynchronousMailTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_accountEntry = _accountEntryLocalService.addOrUpdateAccountEntry(
			RandomTestUtil.randomString(20), TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(20), RandomTestUtil.randomString(20),
			null, null, null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@Override
	@Test
	public void testDeleteAccountByExternalReferenceCodeOrganization()
		throws Exception {

		Organization organization =
			testDeleteAccountByExternalReferenceCodeOrganization_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.
				deleteAccountByExternalReferenceCodeOrganizationHttpResponse(
					_accountEntry.getExternalReferenceCode(),
					organization.getId()));
	}

	@Override
	@Test
	public void testDeleteAccountOrganization() throws Exception {
		com.liferay.portal.kernel.model.Organization organization =
			OrganizationTestUtil.addOrganization();

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			_accountEntry.getAccountEntryId(),
			organization.getOrganizationId());

		Assert.assertNotNull(
			_accountEntryOrganizationRelLocalService.
				fetchAccountEntryOrganizationRel(
					_accountEntry.getAccountEntryId(),
					organization.getOrganizationId()));
	}

	@Override
	@Test
	public void testDeleteOrganizationByExternalReferenceCodeUserAccountByEmailAddress()
		throws Exception {

		Organization organization = _addOrganization(randomOrganization(), "0");
		User user = UserTestUtil.addUser();

		_organizationLocalService.addUserOrganization(
			user.getUserId(), GetterUtil.getLong(organization.getId()));

		Assert.assertTrue(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));

		organizationResource.
			deleteOrganizationByExternalReferenceCodeUserAccountByEmailAddress(
				organization.getExternalReferenceCode(),
				user.getEmailAddress());

		Assert.assertFalse(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));
	}

	@Override
	@Test
	public void testDeleteOrganizationByExternalReferenceCodeUserAccountsByEmailAddress()
		throws Exception {

		Organization organization = _addOrganization(randomOrganization(), "0");

		long organizationId = GetterUtil.getLong(organization.getId());

		List<User> users = Arrays.asList(
			UserTestUtil.addUser(), UserTestUtil.addUser(),
			UserTestUtil.addUser(), UserTestUtil.addUser());

		_userLocalService.addOrganizationUsers(organizationId, users);

		for (User user : users) {
			Assert.assertTrue(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}

		List<User> deleteUsers = users.subList(0, 2);

		organizationResource.
			deleteOrganizationByExternalReferenceCodeUserAccountsByEmailAddress(
				organization.getExternalReferenceCode(),
				_toEmailAddresses(deleteUsers));

		for (User user : deleteUsers) {
			Assert.assertFalse(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}

		List<User> keepUsers = users.subList(2, 4);

		for (User user : keepUsers) {
			Assert.assertTrue(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}
	}

	@Override
	@Test
	public void testDeleteUserAccountByEmailAddress() throws Exception {
		Organization organization = _addOrganization(randomOrganization(), "0");
		User user = UserTestUtil.addUser();

		_organizationLocalService.addUserOrganization(
			user.getUserId(), GetterUtil.getLong(organization.getId()));

		Assert.assertTrue(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));

		organizationResource.deleteUserAccountByEmailAddress(
			organization.getId(), user.getEmailAddress());

		Assert.assertFalse(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));
	}

	@Override
	@Test
	public void testDeleteUserAccountsByEmailAddress() throws Exception {
		Organization organization = _addOrganization(randomOrganization(), "0");

		long organizationId = GetterUtil.getLong(organization.getId());

		List<User> users = Arrays.asList(
			UserTestUtil.addUser(), UserTestUtil.addUser(),
			UserTestUtil.addUser(), UserTestUtil.addUser());

		_userLocalService.addOrganizationUsers(organizationId, users);

		for (User user : users) {
			Assert.assertTrue(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}

		List<User> deleteUsers = users.subList(0, 2);

		organizationResource.deleteUserAccountsByEmailAddress(
			organization.getId(), _toEmailAddresses(deleteUsers));

		for (User user : deleteUsers) {
			Assert.assertFalse(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}

		List<User> keepUsers = users.subList(2, 4);

		for (User user : keepUsers) {
			Assert.assertTrue(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}
	}

	@Override
	@Test
	public void testGetAccountByExternalReferenceCodeOrganization()
		throws Exception {

		testGetAccountOrganization();
	}

	@Override
	@Test
	public void testGetAccountOrganization() throws Exception {
		com.liferay.portal.kernel.model.Organization organization =
			OrganizationTestUtil.addOrganization();

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			_accountEntry.getAccountEntryId(),
			organization.getOrganizationId());

		Assert.assertNotNull(
			_accountEntryOrganizationRelLocalService.
				fetchAccountEntryOrganizationRel(
					_accountEntry.getAccountEntryId(),
					organization.getOrganizationId()));

		organizationResource.deleteAccountOrganization(
			_accountEntry.getAccountEntryId(),
			String.valueOf(organization.getOrganizationId()));

		Assert.assertNull(
			_accountEntryOrganizationRelLocalService.
				fetchAccountEntryOrganizationRel(
					_accountEntry.getAccountEntryId(),
					organization.getOrganizationId()));
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountByExternalReferenceCodeOrganization()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountByExternalReferenceCodeOrganizationNotFound()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountOrganization() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAccountOrganizationNotFound() throws Exception {
	}

	@Override
	@Test
	public void testPatchOrganization() throws Exception {
		super.testPatchOrganization();

		_testPatchOrganizationWithImageExternalReferenceCode();
	}

	@Override
	@Test
	public void testPatchOrganizationByExternalReferenceCode()
		throws Exception {

		super.testPatchOrganizationByExternalReferenceCode();

		_testPatchOrganizationByExternalReferenceCodeWithImageExternalReferenceCode();
	}

	@Override
	@Test
	public void testPostAccountByExternalReferenceCodeOrganization()
		throws Exception {

		Organization organization =
			testPostAccountByExternalReferenceCodeOrganization_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.
				postAccountByExternalReferenceCodeOrganizationHttpResponse(
					_accountEntry.getExternalReferenceCode(),
					organization.getId()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.
				postAccountByExternalReferenceCodeOrganizationHttpResponse(
					_accountEntry.getExternalReferenceCode(), "-"));
	}

	@Override
	@Test
	public void testPostAccountOrganization() throws Exception {
		Organization organization =
			testPostAccountOrganization_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.postAccountOrganizationHttpResponse(
				_accountEntry.getAccountEntryId(), organization.getId()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.postAccountOrganizationHttpResponse(
				_accountEntry.getAccountEntryId(), "-"));
	}

	@Override
	@Test
	public void testPostOrganization() throws Exception {
		super.testPostOrganization();

		_testPostOrganizationWithCustomFields();
		_testPostOrganizationWithNameOverMaximumLength();
		_testPostOrganizationWithImageExternalReferenceCode();
	}

	@Override
	@Test
	public void testPostOrganizationByExternalReferenceCodeUserAccountByEmailAddress()
		throws Exception {

		Organization organization = _addOrganization(randomOrganization(), "0");
		User user = UserTestUtil.addUser();

		Assert.assertFalse(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));

		organizationResource.
			postOrganizationByExternalReferenceCodeUserAccountByEmailAddress(
				organization.getExternalReferenceCode(),
				user.getEmailAddress());

		Assert.assertTrue(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));
	}

	@Override
	@Test
	public void testPostOrganizationByExternalReferenceCodeUserAccountsByEmailAddress()
		throws Exception {

		Organization organization = _addOrganization(randomOrganization(), "0");

		long organizationId = GetterUtil.getLong(organization.getId());

		List<User> users = Arrays.asList(
			UserTestUtil.addUser(), UserTestUtil.addUser(),
			UserTestUtil.addUser(), UserTestUtil.addUser());

		for (User user : users) {
			Assert.assertFalse(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}

		organizationResource.
			postOrganizationByExternalReferenceCodeUserAccountsByEmailAddress(
				organization.getExternalReferenceCode(), null,
				_toEmailAddresses(users));

		for (User user : users) {
			Assert.assertTrue(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}
	}

	@Override
	@Test
	public void testPostUserAccountByEmailAddress() throws Exception {
		Organization organization = _addOrganization(randomOrganization(), "0");
		User user = UserTestUtil.addUser();

		Assert.assertFalse(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));

		organizationResource.postUserAccountByEmailAddress(
			organization.getId(), user.getEmailAddress());

		Assert.assertTrue(
			_organizationLocalService.hasUserOrganization(
				user.getUserId(), GetterUtil.getLong(organization.getId())));
	}

	@Override
	@Test
	public void testPostUserAccountsByEmailAddress() throws Exception {
		Organization organization = _addOrganization(randomOrganization(), "0");

		long organizationId = GetterUtil.getLong(organization.getId());

		List<User> users = Arrays.asList(
			UserTestUtil.addUser(), UserTestUtil.addUser(),
			UserTestUtil.addUser(), UserTestUtil.addUser());

		for (User user : users) {
			Assert.assertFalse(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}

		organizationResource.postUserAccountsByEmailAddress(
			organization.getId(), null, _toEmailAddresses(users));

		for (User user : users) {
			Assert.assertTrue(
				_userLocalService.hasOrganizationUser(
					organizationId, user.getUserId()));
		}
	}

	@Override
	@Test
	public void testPutOrganization() throws Exception {
		super.testPutOrganization();

		_testPutOrganizationWithImageExternalReferenceCode();
	}

	@Override
	@Test
	public void testPutOrganizationByExternalReferenceCode() throws Exception {
		super.testPutOrganizationByExternalReferenceCode();

		_testPutOrganizationByExternalReferenceCodeWithImageExternalReferenceCode();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name", "externalReferenceCode"};
	}

	@Override
	protected Organization randomOrganization() throws Exception {
		Organization organization = super.randomOrganization();

		organization.setImageId(0L);

		return organization;
	}

	@Override
	protected Organization
			testDeleteAccountByExternalReferenceCodeOrganization_addOrganization()
		throws Exception {

		Organization organization =
			organizationResource.putOrganizationByExternalReferenceCode(
				RandomTestUtil.randomString(), randomOrganization());

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			_accountEntry.getAccountEntryId(),
			GetterUtil.getLong(organization.getId()));

		return organization;
	}

	@Override
	protected Organization testDeleteAccountOrganization_addOrganization()
		throws Exception {

		return testDeleteAccountByExternalReferenceCodeOrganization_addOrganization();
	}

	@Override
	protected Long testDeleteAccountOrganization_getAccountId()
		throws Exception {

		return _accountEntry.getAccountEntryId();
	}

	@Override
	protected Organization testDeleteOrganization_addOrganization()
		throws Exception {

		return _addOrganization(randomOrganization(), "0");
	}

	@Override
	protected Organization
			testDeleteOrganizationByExternalReferenceCode_addOrganization()
		throws Exception {

		return organizationResource.putOrganizationByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomOrganization());
	}

	@Override
	protected Organization
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				String externalReferenceCode, Organization organization)
		throws Exception {

		organization = organizationResource.postOrganization(organization);

		organizationResource.postAccountByExternalReferenceCodeOrganization(
			externalReferenceCode, organization.getId());

		return organization;
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeOrganizationsPage_getExternalReferenceCode()
		throws Exception {

		return _accountEntry.getExternalReferenceCode();
	}

	@Override
	protected Organization testGetAccountOrganizationsPage_addOrganization(
			Long accountId, Organization organization)
		throws Exception {

		organization = organizationResource.postOrganization(organization);

		organizationResource.postAccountOrganization(
			accountId, organization.getId());

		return organization;
	}

	@Override
	protected Long testGetAccountOrganizationsPage_getAccountId()
		throws Exception {

		return _accountEntry.getAccountEntryId();
	}

	@Override
	protected Organization testGetOrganization_addOrganization()
		throws Exception {

		return _addOrganization(randomOrganization(), "0");
	}

	@Override
	protected Organization
			testGetOrganizationByExternalReferenceCode_addOrganization()
		throws Exception {

		return organizationResource.putOrganizationByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomOrganization());
	}

	@Override
	protected Organization
			testGetOrganizationByExternalReferenceCodeChildOrganizationsPage_addOrganization(
				String externalReferenceCode, Organization organization)
		throws Exception {

		Organization parentOrganization =
			organizationResource.getOrganizationByExternalReferenceCode(
				externalReferenceCode);

		return _addOrganization(organization, parentOrganization.getId());
	}

	@Override
	protected String
			testGetOrganizationByExternalReferenceCodeChildOrganizationsPage_getExternalReferenceCode()
		throws Exception {

		Organization organization = organizationResource.postOrganization(
			randomOrganization());

		return organization.getExternalReferenceCode();
	}

	@Override
	protected Organization
			testGetOrganizationChildOrganizationsPage_addOrganization(
				String parentOrganizationId, Organization organization)
		throws Exception {

		return _addOrganization(organization, parentOrganizationId);
	}

	@Override
	protected String
			testGetOrganizationChildOrganizationsPage_getOrganizationId()
		throws Exception {

		Organization organization = organizationResource.postOrganization(
			randomOrganization());

		return String.valueOf(organization.getId());
	}

	@Override
	protected Organization testGetOrganizationOrganizationsPage_addOrganization(
			String parentOrganizationId, Organization organization)
		throws Exception {

		return _addOrganization(organization, parentOrganizationId);
	}

	@Override
	protected String
			testGetOrganizationOrganizationsPage_getParentOrganizationId()
		throws Exception {

		Organization organization = _addOrganization(randomOrganization(), "0");

		return String.valueOf(organization.getId());
	}

	@Override
	protected Organization testGetOrganizationsPage_addOrganization(
			Organization organization)
		throws Exception {

		return _addOrganization(organization, "0");
	}

	@Override
	protected Organization testGraphQLOrganization_addOrganization()
		throws Exception {

		return _addOrganization(randomOrganization(), "0");
	}

	@Override
	protected Organization testPatchOrganization_addOrganization()
		throws Exception {

		return _addOrganization(randomOrganization(), "0");
	}

	@Override
	protected Organization
			testPatchOrganizationByExternalReferenceCode_addOrganization()
		throws Exception {

		return organizationResource.putOrganizationByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomOrganization());
	}

	@Override
	protected Organization
			testPostAccountByExternalReferenceCodeOrganization_addOrganization()
		throws Exception {

		return organizationResource.putOrganizationByExternalReferenceCode(
			_accountEntry.getExternalReferenceCode(), randomOrganization());
	}

	@Override
	protected Organization testPostAccountOrganization_addOrganization()
		throws Exception {

		return organizationResource.putOrganizationByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomOrganization());
	}

	@Override
	protected Organization testPostOrganization_addOrganization(
			Organization organization)
		throws Exception {

		return _addOrganization(organization, "0");
	}

	@Override
	protected Organization testPutOrganization_addOrganization()
		throws Exception {

		return _addOrganization(randomOrganization(), "0");
	}

	@Override
	protected Organization
			testPutOrganizationByExternalReferenceCode_addOrganization()
		throws Exception {

		return organizationResource.putOrganizationByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomOrganization());
	}

	private FileEntry _addImageFileEntry() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group group = company.getGroup();

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(group.getGroupId());

		byte[] bytes = FileUtil.getBytes(getClass(), "/images/liferay.png");

		InputStream inputStream = new UnsyncByteArrayInputStream(bytes);

		return localRepository.addFileEntry(
			null, TestPropsValues.getUserId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, inputStream, bytes.length, null,
			null, null,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private Organization _addOrganization(
			Organization organization, String parentOrganizationId)
		throws Exception {

		organization.setParentOrganization(
			() -> {
				if (Validator.isNull(parentOrganizationId)) {
					return null;
				}

				return new Organization() {
					{
						id = parentOrganizationId;
					}
				};
			});

		return organizationResource.postOrganization(organization);
	}

	private void _testPatchOrganizationByExternalReferenceCodeWithImageExternalReferenceCode()
		throws Exception {

		Organization postOrganization =
			testPatchOrganizationByExternalReferenceCode_addOrganization();

		Organization randomPatchOrganization = randomPatchOrganization();

		FileEntry fileEntry = _addImageFileEntry();

		randomPatchOrganization.setImageExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		randomPatchOrganization.setImageId(0L);

		Organization patchOrganization =
			organizationResource.patchOrganizationByExternalReferenceCode(
				postOrganization.getExternalReferenceCode(),
				randomPatchOrganization);

		Assert.assertTrue(patchOrganization.getImageId() > 0);
	}

	private void _testPatchOrganizationWithImageExternalReferenceCode()
		throws Exception {

		Organization postOrganization = testPatchOrganization_addOrganization();

		Organization randomPatchOrganization = randomPatchOrganization();

		FileEntry fileEntry = _addImageFileEntry();

		randomPatchOrganization.setImageExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		randomPatchOrganization.setImageId(0L);

		Organization patchOrganization = organizationResource.patchOrganization(
			postOrganization.getId(), randomPatchOrganization);

		Assert.assertTrue(patchOrganization.getImageId() > 0);
	}

	private void _testPostOrganizationWithCustomFields() throws Exception {
		ExpandoTable expandoTable = _expandoTableLocalService.addTable(
			testGroup.getCompanyId(),
			_classNameLocalService.getClassNameId(
				com.liferay.portal.kernel.model.Organization.class),
			"CUSTOM_FIELDS");

		ExpandoColumn expandoColumn = _expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), "A" + RandomTestUtil.randomString(),
			ExpandoColumnConstants.STRING);

		UnicodeProperties unicodeProperties =
			expandoColumn.getTypeSettingsProperties();

		unicodeProperties.setProperty(
			ExpandoColumnConstants.INDEX_TYPE,
			String.valueOf(ExpandoColumnConstants.INDEX_TYPE_KEYWORD));

		expandoColumn.setTypeSettingsProperties(unicodeProperties);

		_expandoColumnLocalService.updateExpandoColumn(expandoColumn);

		Organization randomOrganization = randomOrganization();

		String value = RandomTestUtil.randomString();

		randomOrganization.setCustomFields(
			() -> new CustomField[] {
				new CustomField() {
					{
						customValue = new CustomValue() {
							{
								data = value;
							}
						};
						dataType = "Text";
						name = expandoColumn.getName();
					}
				}
			});

		Organization postOrganization = testPostOrganization_addOrganization(
			randomOrganization);

		assertEquals(randomOrganization, postOrganization);
		assertValid(postOrganization);

		Assert.assertNotNull(postOrganization.getCustomFields());

		CustomField postOrganizationCustomField =
			postOrganization.getCustomFields()[0];

		Assert.assertEquals(
			expandoColumn.getName(), postOrganizationCustomField.getName());
	}

	private void _testPostOrganizationWithImageExternalReferenceCode()
		throws Exception {

		Organization randomOrganization = randomOrganization();

		FileEntry fileEntry = _addImageFileEntry();

		randomOrganization.setImageExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		randomOrganization.setImageId(0L);

		Organization postOrganization = organizationResource.postOrganization(
			randomOrganization);

		Assert.assertTrue(postOrganization.getImageId() > 0);
	}

	private void _testPostOrganizationWithNameOverMaximumLength()
		throws Exception {

		Organization organization = randomOrganization();

		organization.setName(RandomTestUtil.randomString(101));

		assertHttpResponseStatusCode(
			400,
			organizationResource.postOrganizationHttpResponse(organization));
	}

	private void _testPutOrganizationByExternalReferenceCodeWithImageExternalReferenceCode()
		throws Exception {

		Organization postOrganization =
			testPutOrganizationByExternalReferenceCode_addOrganization();

		Organization randomPutOrganization = randomOrganization();

		FileEntry fileEntry = _addImageFileEntry();

		randomPutOrganization.setImageExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		randomPutOrganization.setImageId(0L);

		Organization putOrganization =
			organizationResource.putOrganizationByExternalReferenceCode(
				postOrganization.getExternalReferenceCode(),
				randomPutOrganization);

		Assert.assertTrue(putOrganization.getImageId() > 0);
	}

	private void _testPutOrganizationWithImageExternalReferenceCode()
		throws Exception {

		Organization postOrganization = testPutOrganization_addOrganization();

		Organization randomPutOrganization = randomOrganization();

		FileEntry fileEntry = _addImageFileEntry();

		randomPutOrganization.setImageExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		randomPutOrganization.setImageId(0L);

		Organization putOrganization = organizationResource.putOrganization(
			postOrganization.getId(), randomPutOrganization);

		Assert.assertTrue(putOrganization.getImageId() > 0);
	}

	private String[] _toEmailAddresses(List<User> users) {
		return TransformUtil.transformToArray(
			users, User::getEmailAddress, String.class);
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Inject
	private ExpandoTableLocalService _expandoTableLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}