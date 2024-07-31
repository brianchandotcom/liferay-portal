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
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		_user = UserTestUtil.addGroupAdminUser(testGroup);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			_user.getUserId(), AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), null, new String[0], null, null,
			null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
		_country = _countryLocalService.addCountry(
			"X" + RandomTestUtil.randomString(1),
			"X" + RandomTestUtil.randomString(2), true, true,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.nextLong(), true,
			false, false, serviceContext);

		_organization = OrganizationTestUtil.addOrganization();
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
				addressCountry = _country.getTitle(LocaleUtil.getDefault());
				addressLocality = RandomTestUtil.randomString();
				addressType = "billing";
				externalReferenceCode = RandomTestUtil.randomString();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				postalCode = RandomTestUtil.randomString();
				primary = false;
				streetAddressLine1 = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected PostalAddress testDeletePostalAddress_addPostalAddress()
		throws Exception {

		return _addPostalAddress(
			randomPostalAddress(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(),
			AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
	}

	@Override
	protected PostalAddress
			testDeletePostalAddressByExternalReferenceCode_addPostalAddress()
		throws Exception {

		return testDeletePostalAddress_addPostalAddress();
	}

	@Override
	protected PostalAddress
			testGetAccountByExternalReferenceCodePostalAddressesPage_addPostalAddress(
				String externalReferenceCode, PostalAddress postalAddress)
		throws Exception {

		return _addPostalAddress(
			postalAddress, AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(),
			AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodePostalAddressesPage_getExternalReferenceCode()
		throws Exception {

		return _accountEntry.getExternalReferenceCode();
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

		return _accountEntry.getAccountEntryId();
	}

	@Override
	protected Map<String, Map<String, String>>
			testGetAccountPostalAddressesPage_getExpectedActions(Long accountId)
		throws Exception {

		return new HashMap<>();
	}

	@Override
	protected PostalAddress
			testGetOrganizationByExternalReferenceCodePostalAddressesPage_addPostalAddress(
				String externalReferenceCode, PostalAddress postalAddress)
		throws Exception {

		return _addPostalAddress(
			postalAddress, Organization.class.getName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_ADDRESS);
	}

	@Override
	protected String
			testGetOrganizationByExternalReferenceCodePostalAddressesPage_getExternalReferenceCode()
		throws Exception {

		return _organization.getExternalReferenceCode();
	}

	@Override
	protected PostalAddress
			testGetOrganizationPostalAddressesPage_addPostalAddress(
				String organizationId, PostalAddress postalAddress)
		throws Exception {

		return _addPostalAddress(
			postalAddress, Organization.class.getName(),
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
			testGetPostalAddressByExternalReferenceCode_addPostalAddress()
		throws Exception {

		return testGetPostalAddress_addPostalAddress();
	}

	@Override
	protected PostalAddress
			testGetUserAccountByExternalReferenceCodePostalAddressesPage_addPostalAddress(
				String externalReferenceCode, PostalAddress postalAddress)
		throws Exception {

		return _addPostalAddress(
			postalAddress, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_ADDRESS);
	}

	@Override
	protected String
			testGetUserAccountByExternalReferenceCodePostalAddressesPage_getExternalReferenceCode()
		throws Exception {

		return _user.getExternalReferenceCode();
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

		return _addPostalAddress(
			randomPostalAddress(), Contact.class.getName(),
			_user.getContactId(), ListTypeConstants.CONTACT_ADDRESS);
	}

	@Override
	protected PostalAddress testPatchPostalAddress_addPostalAddress()
		throws Exception {

		return _addPostalAddress(
			randomPostalAddress(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(),
			AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
	}

	@Override
	protected PostalAddress
			testPatchPostalAddressByExternalReferenceCode_addPostalAddress()
		throws Exception {

		return testPatchPostalAddress_addPostalAddress();
	}

	@Override
	protected PostalAddress testPutPostalAddress_addPostalAddress()
		throws Exception {

		return _addPostalAddress(
			randomPostalAddress(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(),
			AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
	}

	@Override
	protected PostalAddress
			testPutPostalAddressByExternalReferenceCode_addPostalAddress()
		throws Exception {

		return testPutPostalAddress_addPostalAddress();
	}

	@Override
	protected PostalAddress
			testPutPostalAddressByExternalReferenceCode_createPostalAddress()
		throws Exception {

		return testPutPostalAddress_addPostalAddress();
	}

	private PostalAddress _addPostalAddress(
			PostalAddress postalAddress, String className, long classPK,
			String listTypeId)
		throws Exception {

		return _toPostalAddress(
			AddressLocalServiceUtil.addAddress(
				postalAddress.getExternalReferenceCode(), _user.getUserId(),
				className, classPK, null, null,
				postalAddress.getStreetAddressLine1(),
				postalAddress.getStreetAddressLine2(),
				postalAddress.getStreetAddressLine3(),
				postalAddress.getAddressLocality(),
				postalAddress.getPostalCode(), 0, _country.getCountryId(),
				_getListTypeId(listTypeId), false, postalAddress.getPrimary(),
				null, new ServiceContext()));
	}

	private long _getListTypeId(String listTypeId) {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(
			_user.getCompanyId(), listTypeId);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
	}

	private PostalAddress _toPostalAddress(Address address) {
		Country country = address.getCountry();
		ListType listType = address.getListType();

		return new PostalAddress() {
			{
				addressCountry = country.getTitle();
				addressLocality = address.getCity();
				addressType = listType.getName();
				externalReferenceCode = address.getExternalReferenceCode();
				id = address.getAddressId();
				name = address.getName();
				postalCode = address.getZip();
				primary = address.isPrimary();
				streetAddressLine1 = address.getStreet1();
			}
		};
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@DeleteAfterTestRun
	private Country _country;

	@Inject
	private CountryLocalService _countryLocalService;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private User _user;

}