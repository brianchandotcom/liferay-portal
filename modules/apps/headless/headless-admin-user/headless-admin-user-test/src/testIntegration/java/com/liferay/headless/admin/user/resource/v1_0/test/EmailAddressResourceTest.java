/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.EmailAddress;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class EmailAddressResourceTest extends BaseEmailAddressResourceTestCase {

	@ClassRule
	@Rule
	public static final SynchronousMailTestRule synchronousMailTestRule =
		SynchronousMailTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addGroupAdminUser(testGroup);

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"emailAddress", "primary"};
	}

	@Override
	protected EmailAddress randomEmailAddress() {
		return new EmailAddress() {
			{
				emailAddress = RandomTestUtil.randomString() + "@liferay.com";
				externalReferenceCode = RandomTestUtil.randomString();
				primary = false;
				type = "email-address-3";
			}
		};
	}

	@Override
	protected EmailAddress testDeleteEmailAddress_addEmailAddress()
		throws Exception {

		return _addEmailAddress(
			randomEmailAddress(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_EMAIL_ADDRESS);
	}

	@Override
	protected EmailAddress
			testDeleteEmailAddressByExternalReferenceCode_addEmailAddress()
		throws Exception {

		return _addEmailAddress(
			randomEmailAddress(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_EMAIL_ADDRESS);
	}

	@Override
	protected EmailAddress
			testGetAccountByExternalReferenceCodeEmailAddressesPage_addEmailAddress(
				String externalReferenceCode, EmailAddress emailAddress)
		throws Exception {

		return _addEmailAddress(
			emailAddress, AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(),
			AccountListTypeConstants.ACCOUNT_ENTRY_EMAIL_ADDRESS);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeEmailAddressesPage_getExternalReferenceCode()
		throws Exception {

		return _accountEntry.getExternalReferenceCode();
	}

	@Override
	protected EmailAddress testGetAccountEmailAddressesPage_addEmailAddress(
			Long accountId, EmailAddress emailAddress)
		throws Exception {

		return _addEmailAddress(
			emailAddress, AccountEntry.class.getName(), accountId,
			AccountListTypeConstants.ACCOUNT_ENTRY_EMAIL_ADDRESS);
	}

	@Override
	protected Long testGetAccountEmailAddressesPage_getAccountId()
		throws Exception {

		return _accountEntry.getAccountEntryId();
	}

	@Override
	protected EmailAddress testGetEmailAddress_addEmailAddress()
		throws Exception {

		return _addEmailAddress(
			randomEmailAddress(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_EMAIL_ADDRESS);
	}

	@Override
	protected EmailAddress
			testGetEmailAddressByExternalReferenceCode_addEmailAddress()
		throws Exception {

		return _addEmailAddress(
			randomEmailAddress(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_EMAIL_ADDRESS);
	}

	@Override
	protected EmailAddress
			testGetOrganizationByExternalReferenceCodeEmailAddressesPage_addEmailAddress(
				String externalReferenceCode, EmailAddress emailAddress)
		throws Exception {

		return _addEmailAddress(
			emailAddress, Organization.class.getName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_EMAIL_ADDRESS);
	}

	@Override
	protected String
			testGetOrganizationByExternalReferenceCodeEmailAddressesPage_getExternalReferenceCode()
		throws Exception {

		return _organization.getExternalReferenceCode();
	}

	@Override
	protected EmailAddress
			testGetOrganizationEmailAddressesPage_addEmailAddress(
				String organizationId, EmailAddress emailAddress)
		throws Exception {

		return _addEmailAddress(
			emailAddress, Organization.class.getName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_EMAIL_ADDRESS);
	}

	@Override
	protected String testGetOrganizationEmailAddressesPage_getOrganizationId() {
		return String.valueOf(_organization.getOrganizationId());
	}

	@Override
	protected EmailAddress
			testGetUserAccountByExternalReferenceCodeEmailAddressesPage_addEmailAddress(
				String externalReferenceCode, EmailAddress emailAddress)
		throws Exception {

		return _addEmailAddress(
			emailAddress, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_EMAIL_ADDRESS);
	}

	@Override
	protected String
			testGetUserAccountByExternalReferenceCodeEmailAddressesPage_getExternalReferenceCode()
		throws Exception {

		return _user.getExternalReferenceCode();
	}

	@Override
	protected EmailAddress testGetUserAccountEmailAddressesPage_addEmailAddress(
			Long userAccountId, EmailAddress emailAddress)
		throws Exception {

		return _addEmailAddress(
			emailAddress, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_EMAIL_ADDRESS);
	}

	@Override
	protected Long testGetUserAccountEmailAddressesPage_getUserAccountId() {
		return _user.getUserId();
	}

	@Override
	protected EmailAddress testGraphQLEmailAddress_addEmailAddress()
		throws Exception {

		return testGetEmailAddress_addEmailAddress();
	}

	@Override
	protected EmailAddress testPatchEmailAddress_addEmailAddress()
		throws Exception {

		return _addEmailAddress(
			randomEmailAddress(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_EMAIL_ADDRESS);
	}

	@Override
	protected EmailAddress
			testPatchEmailAddressByExternalReferenceCode_addEmailAddress()
		throws Exception {

		return _addEmailAddress(
			randomEmailAddress(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_EMAIL_ADDRESS);
	}

	private EmailAddress _addEmailAddress(
			EmailAddress emailAddress, String className, long classPK,
			String listTypeId)
		throws Exception {

		return _toEmailAddress(
			EmailAddressLocalServiceUtil.addEmailAddress(
				emailAddress.getExternalReferenceCode(), _user.getUserId(),
				className, classPK, emailAddress.getEmailAddress(),
				_getListTypeId(listTypeId), emailAddress.getPrimary(),
				new ServiceContext()));
	}

	private long _getListTypeId(String listTypeId) {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(
			_user.getCompanyId(), listTypeId);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
	}

	private EmailAddress _toEmailAddress(
		com.liferay.portal.kernel.model.EmailAddress
			serviceBuilderEmailAddress) {

		return new EmailAddress() {
			{
				emailAddress = serviceBuilderEmailAddress.getAddress();
				externalReferenceCode =
					serviceBuilderEmailAddress.getExternalReferenceCode();
				id = serviceBuilderEmailAddress.getEmailAddressId();
				primary = serviceBuilderEmailAddress.isPrimary();
			}
		};
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private User _user;

}