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
import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
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
public class PhoneResourceTest extends BasePhoneResourceTestCase {

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
		return new String[] {"extension", "phoneNumber", "primary"};
	}

	@Override
	protected Phone randomPhone() {
		return new Phone() {
			{
				extension = String.valueOf(RandomTestUtil.randomInt());
				phoneNumber = RandomTestUtil.randomString();
				primary = false;
			}
		};
	}

	@Override
	protected Phone testDeletePhone_addPhone() throws Exception {
		return _addPhone(
			randomPhone(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_PHONE);
	}

	@Override
	protected Phone testDeletePhoneByExternalReferenceCode_addPhone()
		throws Exception {

		return _addPhone(
			randomPhone(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_PHONE);
	}

	@Override
	protected Phone testGetAccountByExternalReferenceCodePhonesPage_addPhone(
			String externalReferenceCode, Phone phone)
		throws Exception {

		return _addPhone(
			phone, AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(),
			AccountListTypeConstants.ACCOUNT_ENTRY_PHONE);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodePhonesPage_getExternalReferenceCode()
		throws Exception {

		return _accountEntry.getExternalReferenceCode();
	}

	@Override
	protected Phone testGetAccountPhonesPage_addPhone(
			Long accountId, Phone phone)
		throws Exception {

		return _addPhone(
			phone, AccountEntry.class.getName(), accountId,
			AccountListTypeConstants.ACCOUNT_ENTRY_PHONE);
	}

	@Override
	protected Long testGetAccountPhonesPage_getAccountId() throws Exception {
		return _accountEntry.getAccountEntryId();
	}

	@Override
	protected Phone
			testGetOrganizationByExternalReferenceCodePhonesPage_addPhone(
				String externalReferenceCode, Phone phone)
		throws Exception {

		return _addPhone(
			phone, Organization.class.getName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_PHONE);
	}

	@Override
	protected String
			testGetOrganizationByExternalReferenceCodePhonesPage_getExternalReferenceCode()
		throws Exception {

		return _organization.getExternalReferenceCode();
	}

	@Override
	protected Phone testGetOrganizationPhonesPage_addPhone(
			String organizationId, Phone phone)
		throws Exception {

		return _addPhone(
			phone, Organization.class.getName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_PHONE);
	}

	@Override
	protected String testGetOrganizationPhonesPage_getOrganizationId() {
		return String.valueOf(_organization.getOrganizationId());
	}

	@Override
	protected Phone testGetPhone_addPhone() throws Exception {
		return _addPhone(
			randomPhone(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_PHONE);
	}

	@Override
	protected Phone testGetPhoneByExternalReferenceCode_addPhone()
		throws Exception {

		return _addPhone(
			randomPhone(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_PHONE);
	}

	@Override
	protected Phone
			testGetUserAccountByExternalReferenceCodePhonesPage_addPhone(
				String externalReferenceCode, Phone phone)
		throws Exception {

		return _addPhone(
			phone, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_PHONE);
	}

	@Override
	protected String
			testGetUserAccountByExternalReferenceCodePhonesPage_getExternalReferenceCode()
		throws Exception {

		return _user.getExternalReferenceCode();
	}

	@Override
	protected Phone testGetUserAccountPhonesPage_addPhone(
			Long userAccountId, Phone phone)
		throws Exception {

		return _addPhone(
			phone, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_PHONE);
	}

	@Override
	protected Long testGetUserAccountPhonesPage_getUserAccountId() {
		return _user.getUserId();
	}

	@Override
	protected Phone testGraphQLPhone_addPhone() throws Exception {
		return testGetPhone_addPhone();
	}

	@Override
	protected Phone testPatchPhone_addPhone() throws Exception {
		return _addPhone(
			randomPhone(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_PHONE);
	}

	@Override
	protected Phone testPatchPhoneByExternalReferenceCode_addPhone()
		throws Exception {

		return _addPhone(
			randomPhone(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_PHONE);
	}

	private Phone _addPhone(
			Phone phone, String className, long classPK, String listTypeId)
		throws Exception {

		return _toPhone(
			PhoneLocalServiceUtil.addPhone(
				RandomTestUtil.randomString(), _user.getUserId(), className,
				classPK, phone.getPhoneNumber(), phone.getExtension(),
				_getListTypeId(listTypeId), phone.getPrimary(),
				new ServiceContext()));
	}

	private long _getListTypeId(String listTypeId) {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(
			_user.getCompanyId(), listTypeId);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
	}

	private Phone _toPhone(com.liferay.portal.kernel.model.Phone phone) {
		return new Phone() {
			{
				extension = phone.getExtension();
				externalReferenceCode = phone.getExternalReferenceCode();
				id = phone.getPhoneId();
				phoneNumber = phone.getNumber();
				primary = phone.isPrimary();
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