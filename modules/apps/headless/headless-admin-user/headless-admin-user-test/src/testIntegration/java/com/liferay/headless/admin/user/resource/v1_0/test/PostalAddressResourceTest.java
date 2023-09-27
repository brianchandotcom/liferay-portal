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
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
public class PostalAddressResourceTest
	extends BasePostalAddressResourceTestCase {

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
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"name", "postalCode", "primary", "streetAddressLine1"
		};
	}

	@Override
	protected PostalAddress randomPostalAddress() {
		return new PostalAddress() {
			{
				addressLocality = RandomTestUtil.randomString();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				postalCode = RandomTestUtil.randomString();
				primary = false;
				streetAddressLine1 = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected PostalAddress testGetAccountPostalAddressesPage_addPostalAddress(
			Long accountId, PostalAddress postalAddress)
		throws Exception {

		return _addPostalAddress(
			postalAddress, AccountEntry.class.getName(), accountId,
			AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
	}

	@Override
	protected Long testGetAccountPostalAddressesPage_getAccountId()
		throws Exception {

		AccountEntry accountEntry = _addAccountEntry();

		return accountEntry.getAccountEntryId();
	}

	@Override
	protected Long testGetAccountPostalAddressesPage_getIrrelevantAccountId()
		throws Exception {

		AccountEntry accountEntry = _addAccountEntry();

		return accountEntry.getAccountEntryId();
	}

	@Override
	protected PostalAddress
			testGetOrganizationPostalAddressesPage_addPostalAddress(
				String organizationId, PostalAddress postalAddress)
		throws Exception {

		return _addPostalAddress(
			postalAddress, _organization.getModelClassName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_ADDRESS);
	}

	@Override
	protected String
		testGetOrganizationPostalAddressesPage_getOrganizationId() {

		return String.valueOf(_organization.getOrganizationId());
	}

	@Override
	protected PostalAddress testGetPostalAddress_addPostalAddress()
		throws Exception {

		return _addPostalAddress(
			randomPostalAddress(), Contact.class.getName(),
			_user.getContactId(), ListTypeConstants.CONTACT_ADDRESS);
	}

	@Override
	protected PostalAddress
			testGetUserAccountPostalAddressesPage_addPostalAddress(
				Long userAccountId, PostalAddress postalAddress)
		throws Exception {

		return _addPostalAddress(
			postalAddress, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_ADDRESS);
	}

	@Override
	protected Long testGetUserAccountPostalAddressesPage_getUserAccountId() {
		return _user.getUserId();
	}

	@Override
	protected PostalAddress testGraphQLPostalAddress_addPostalAddress()
		throws Exception {

		return testGetPostalAddress_addPostalAddress();
	}

	private AccountEntry _addAccountEntry() throws Exception {
		return _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, new String[0], null, null,
			null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private PostalAddress _addPostalAddress(
			PostalAddress postalAddress, String className, long classPK,
			String listTypeId)
		throws Exception {

		return _toPostalAddress(
			AddressLocalServiceUtil.addAddress(
				null, _user.getUserId(), className, classPK, null, null,
				postalAddress.getStreetAddressLine1(),
				postalAddress.getStreetAddressLine2(),
				postalAddress.getStreetAddressLine3(),
				postalAddress.getAddressLocality(),
				postalAddress.getPostalCode(), 0, 0, _getListTypeId(listTypeId),
				false, postalAddress.getPrimary(), null, new ServiceContext()));
	}

	private long _getListTypeId(String listTypeId) {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(listTypeId);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
	}

	private PostalAddress _toPostalAddress(Address address) {
		return new PostalAddress() {
			{
				addressLocality = address.getCity();
				id = address.getAddressId();
				name = address.getName();
				postalCode = address.getZip();
				primary = address.isPrimary();
				streetAddressLine1 = address.getStreet1();
			}
		};
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private User _user;

}