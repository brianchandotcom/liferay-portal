/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.DuplicateAccountEntryExternalReferenceCodeException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryModel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.AccountContactInformation;
import com.liferay.headless.admin.user.client.dto.v1_0.EmailAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.Phone;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.problem.Problem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@FeatureFlags("LPD-10855")
@RunWith(Arquillian.class)
public class AccountResourceTest extends BaseAccountResourceTestCase {

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Override
	@Test
	public void testDeleteOrganizationAccounts() throws Exception {
		List<AccountEntry> accountEntries = Arrays.asList(
			_addAccountEntry(), _addAccountEntry(), _addAccountEntry());

		Organization organization = OrganizationTestUtil.addOrganization();

		for (AccountEntry accountEntry : accountEntries) {
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					accountEntry.getAccountEntryId(),
					organization.getOrganizationId());
		}

		Assert.assertEquals(
			3,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization.getOrganizationId()));

		Long[] accountEntryIds = ListUtil.toArray(
			accountEntries.subList(1, accountEntries.size()),
			AccountEntry.ACCOUNT_ENTRY_ID_ACCESSOR);

		accountResource.deleteOrganizationAccounts(
			organization.getOrganizationId(), accountEntryIds);

		Assert.assertEquals(
			1,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization.getOrganizationId()));

		AccountEntry accountEntry = accountEntries.get(0);

		Assert.assertTrue(
			_accountEntryOrganizationRelLocalService.
				hasAccountEntryOrganizationRel(
					accountEntry.getAccountEntryId(),
					organization.getOrganizationId()));
	}

	@Override
	@Test
	public void testDeleteOrganizationAccountsByExternalReferenceCode()
		throws Exception {

		List<AccountEntry> accountEntries = Arrays.asList(
			_addAccountEntry(), _addAccountEntry(), _addAccountEntry());

		Organization organization = OrganizationTestUtil.addOrganization();

		for (AccountEntry accountEntry : accountEntries) {
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					accountEntry.getAccountEntryId(),
					organization.getOrganizationId());
		}

		Assert.assertEquals(
			3,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization.getOrganizationId()));

		String[] externalReferenceCodes = TransformUtil.transformToArray(
			accountEntries.subList(1, accountEntries.size()),
			AccountEntryModel::getExternalReferenceCode, String.class);

		accountResource.deleteOrganizationAccountsByExternalReferenceCode(
			organization.getOrganizationId(), externalReferenceCodes);

		Assert.assertEquals(
			1,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization.getOrganizationId()));

		AccountEntry accountEntry = accountEntries.get(0);

		Assert.assertTrue(
			_accountEntryOrganizationRelLocalService.
				hasAccountEntryOrganizationRel(
					accountEntry.getAccountEntryId(),
					organization.getOrganizationId()));
	}

	@Override
	@Test
	public void testGetAccountsPage() throws Exception {
		super.testGetAccountsPage();

		AccountEntry accountEntry1 = _addAccountEntry();
		AccountEntry accountEntry2 = _addAccountEntry();
		Organization organization = OrganizationTestUtil.addOrganization();

		_addAccountEntry();

		_testGetAccountsPage(Arrays.asList(accountEntry1, accountEntry2), null);
		_testGetAccountsPage(
			Collections.emptyList(), organization.getOrganizationId());

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry1.getAccountEntryId(),
			organization.getOrganizationId());

		_testGetAccountsPage(
			Collections.singletonList(accountEntry1),
			organization.getOrganizationId());
	}

	@Override
	@Test
	public void testPatchAccount() throws Exception {
		super.testPatchAccount();

		_testPatchAccountWithContactInformation();
		_testPatchAccountWithPostalAddressPhoneNumber();
	}

	@Override
	@Test
	public void testPatchOrganizationMoveAccounts() throws Exception {
		List<AccountEntry> accountEntries = Arrays.asList(
			_addAccountEntry(), _addAccountEntry(), _addAccountEntry());

		Organization organization1 = OrganizationTestUtil.addOrganization();

		for (AccountEntry accountEntry : accountEntries) {
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					accountEntry.getAccountEntryId(),
					organization1.getOrganizationId());
		}

		Assert.assertEquals(
			3,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization1.getOrganizationId()));

		Organization organization2 = OrganizationTestUtil.addOrganization();

		Assert.assertEquals(
			0,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization2.getOrganizationId()));

		accountResource.patchOrganizationMoveAccounts(
			organization1.getOrganizationId(),
			organization2.getOrganizationId(),
			ListUtil.toArray(
				accountEntries, AccountEntry.ACCOUNT_ENTRY_ID_ACCESSOR));

		Assert.assertEquals(
			0,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization1.getOrganizationId()));

		Assert.assertEquals(
			3,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization2.getOrganizationId()));
	}

	@Override
	@Test
	public void testPatchOrganizationMoveAccountsByExternalReferenceCode()
		throws Exception {

		List<AccountEntry> accountEntries = Arrays.asList(
			_addAccountEntry(), _addAccountEntry(), _addAccountEntry());

		Organization organization1 = OrganizationTestUtil.addOrganization();

		for (AccountEntry accountEntry : accountEntries) {
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					accountEntry.getAccountEntryId(),
					organization1.getOrganizationId());
		}

		Assert.assertEquals(
			3,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization1.getOrganizationId()));

		Organization organization2 = OrganizationTestUtil.addOrganization();

		Assert.assertEquals(
			0,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization2.getOrganizationId()));

		String[] externalReferenceCodes = TransformUtil.transformToArray(
			accountEntries, AccountEntryModel::getExternalReferenceCode,
			String.class);

		accountResource.patchOrganizationMoveAccountsByExternalReferenceCode(
			organization1.getOrganizationId(),
			organization2.getOrganizationId(), externalReferenceCodes);

		Assert.assertEquals(
			0,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization1.getOrganizationId()));

		Assert.assertEquals(
			3,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization2.getOrganizationId()));
	}

	@Override
	@Test
	public void testPostAccount() throws Exception {
		super.testPostAccount();

		_testPostAccountDuplicateExternalReferenceCode();
		_testPostAccountWithContactInformation();
		_testPostAccountWithPostalAddressPhoneNumber();
	}

	@Override
	@Test
	public void testPostOrganizationAccounts() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		Assert.assertEquals(
			0,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization.getOrganizationId()));

		List<AccountEntry> accountEntries = Arrays.asList(
			_addAccountEntry(), _addAccountEntry(), _addAccountEntry());

		Long[] accountEntryIds = ListUtil.toArray(
			accountEntries, AccountEntry.ACCOUNT_ENTRY_ID_ACCESSOR);

		accountResource.postOrganizationAccounts(
			organization.getOrganizationId(), accountEntryIds);

		for (Long accountEntryId : accountEntryIds) {
			Assert.assertTrue(
				_accountEntryOrganizationRelLocalService.
					hasAccountEntryOrganizationRel(
						accountEntryId, organization.getOrganizationId()));
		}
	}

	@Override
	@Test
	public void testPostOrganizationAccountsByExternalReferenceCode()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Assert.assertEquals(
			0,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCountByOrganizationId(
					organization.getOrganizationId()));

		List<AccountEntry> accountEntries = Arrays.asList(
			_addAccountEntry(), _addAccountEntry(), _addAccountEntry());

		String[] externalReferenceCodes = TransformUtil.transformToArray(
			accountEntries, AccountEntryModel::getExternalReferenceCode,
			String.class);

		accountResource.postOrganizationAccountsByExternalReferenceCode(
			organization.getOrganizationId(), externalReferenceCodes);

		for (AccountEntry accountEntry : accountEntries) {
			Assert.assertTrue(
				_accountEntryOrganizationRelLocalService.
					hasAccountEntryOrganizationRel(
						accountEntry.getAccountEntryId(),
						organization.getOrganizationId()));
		}
	}

	@Override
	@Test
	public void testPutAccount() throws Exception {
		super.testPutAccount();

		_testPutAccountWithContactInformation();
		_testPutAccountWithPostalAddressPhoneNumber();
	}

	@Override
	@Test
	public void testPutAccountByExternalReferenceCode() throws Exception {
		super.testPutAccountByExternalReferenceCode();

		_testPutAccountByExternalReferenceCodeWithContactInformation();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name", "type"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"type"};
	}

	@Override
	protected Account randomAccount() throws Exception {
		Account account = super.randomAccount();

		account.setLogoId(0L);
		account.setParentAccountId(AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);
		account.setStatus(WorkflowConstants.STATUS_APPROVED);
		account.setType(
			Account.Type.create(AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS));

		return account;
	}

	@Override
	protected Account testDeleteAccount_addAccount() throws Exception {
		return _postAccount();
	}

	@Override
	protected Account testDeleteAccountByExternalReferenceCode_addAccount()
		throws Exception {

		return accountResource.putAccountByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomAccount());
	}

	@Override
	protected Account testGetAccount_addAccount() throws Exception {
		return _postAccount();
	}

	@Override
	protected Account testGetAccountByExternalReferenceCode_addAccount()
		throws Exception {

		return accountResource.putAccountByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomAccount());
	}

	@Override
	protected Account testGetAccountsPage_addAccount(Account account)
		throws Exception {

		return _postAccount(account);
	}

	@Override
	protected Account testGetOrganizationAccountsPage_addAccount(
			String organizationId, Account account)
		throws Exception {

		Account putAccount = accountResource.putAccountByExternalReferenceCode(
			account.getExternalReferenceCode(), account);

		accountResource.postOrganizationAccounts(
			Long.valueOf(organizationId), new Long[] {putAccount.getId()});

		return putAccount;
	}

	@Override
	protected String testGetOrganizationAccountsPage_getOrganizationId()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		return String.valueOf(organization.getOrganizationId());
	}

	@Override
	protected Account testGraphQLAccount_addAccount() throws Exception {
		return _postAccount();
	}

	@Override
	protected Account testPatchAccount_addAccount() throws Exception {
		return _postAccount();
	}

	@Override
	protected Account testPatchAccountByExternalReferenceCode_addAccount()
		throws Exception {

		return accountResource.putAccountByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomAccount());
	}

	@Override
	protected Account testPostAccount_addAccount(Account account)
		throws Exception {

		return _postAccount(account);
	}

	@Override
	protected Account testPutAccount_addAccount() throws Exception {
		return _postAccount();
	}

	@Override
	protected Account testPutAccountByExternalReferenceCode_addAccount()
		throws Exception {

		return accountResource.putAccountByExternalReferenceCode(
			StringUtil.toLowerCase(RandomTestUtil.randomString()),
			randomAccount());
	}

	private AccountEntry _addAccountEntry() throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		accountEntry.setExternalReferenceCode(RandomTestUtil.randomString());

		return _accountEntryLocalService.updateAccountEntry(accountEntry);
	}

	private void _assertEquals(
		AccountContactInformation accountContactInformation1,
		AccountContactInformation accountContactInformation2) {

		_assertEquals(
			accountContactInformation1.getEmailAddresses(),
			accountContactInformation2.getEmailAddresses());
		Assert.assertEquals(
			accountContactInformation1.getFacebook(),
			accountContactInformation2.getFacebook());
		Assert.assertEquals(
			accountContactInformation1.getJabber(),
			accountContactInformation2.getJabber());
		_assertEquals(
			accountContactInformation1.getPostalAddresses(),
			accountContactInformation2.getPostalAddresses());
		Assert.assertEquals(
			accountContactInformation1.getSkype(),
			accountContactInformation2.getSkype());
		Assert.assertEquals(
			accountContactInformation1.getSms(),
			accountContactInformation2.getSms());
		_assertEquals(
			accountContactInformation1.getTelephones(),
			accountContactInformation2.getTelephones());
		Assert.assertEquals(
			accountContactInformation1.getTwitter(),
			accountContactInformation2.getTwitter());
		_assertEquals(
			accountContactInformation1.getWebUrls(),
			accountContactInformation2.getWebUrls());
	}

	private void _assertEquals(
		EmailAddress[] emailAddresses1, EmailAddress[] emailAddresses2) {

		Assert.assertEquals(
			Arrays.toString(emailAddresses1) + " does not equal " +
				Arrays.toString(emailAddresses2),
			emailAddresses1.length, emailAddresses2.length);

		for (int i = 0; i < emailAddresses1.length; i++) {
			Assert.assertTrue(
				emailAddresses1[i] + " does not equal " + emailAddresses2[i],
				_equals(emailAddresses1[i], emailAddresses2[i]));
		}
	}

	private void _assertEquals(Phone[] phones1, Phone[] phones2) {
		Assert.assertEquals(
			Arrays.toString(phones1) + " does not equal " +
				Arrays.toString(phones2),
			phones1.length, phones2.length);

		for (int i = 0; i < phones1.length; i++) {
			Assert.assertTrue(
				phones1[i] + " does not equal " + phones2[i],
				_equals(phones1[i], phones2[i]));
		}
	}

	private void _assertEquals(
		PostalAddress[] postalAddresses1, PostalAddress[] postalAddresses2) {

		Assert.assertEquals(
			Arrays.toString(postalAddresses1) + " does not equal " +
				Arrays.toString(postalAddresses2),
			postalAddresses1.length, postalAddresses2.length);

		for (int i = 0; i < postalAddresses1.length; i++) {
			Assert.assertTrue(
				postalAddresses1[i] + " does not equal " + postalAddresses2[i],
				_equals(postalAddresses1[i], postalAddresses2[i]));
		}
	}

	private void _assertEquals(WebUrl[] webUrls1, WebUrl[] webUrls2) {
		Assert.assertEquals(
			Arrays.toString(webUrls1) + " does not equal " +
				Arrays.toString(webUrls2),
			webUrls1.length, webUrls2.length);

		for (int i = 0; i < webUrls1.length; i++) {
			Assert.assertTrue(
				webUrls1[i] + " does not equal " + webUrls2[i],
				_equals(webUrls1[i], webUrls2[i]));
		}
	}

	private boolean _equals(
		EmailAddress emailAddress1, EmailAddress emailAddress2) {

		if (emailAddress1 == emailAddress2) {
			return true;
		}

		if (!Objects.equals(
				emailAddress1.getEmailAddress(),
				emailAddress2.getEmailAddress()) ||
			!Objects.equals(emailAddress1.getType(), emailAddress2.getType())) {

			return false;
		}

		return true;
	}

	private boolean _equals(Phone phone1, Phone phone2) {
		if (phone1 == phone2) {
			return true;
		}

		if (!Objects.equals(phone1.getExtension(), phone2.getExtension()) ||
			!Objects.equals(phone1.getPhoneNumber(), phone2.getPhoneNumber()) ||
			!Objects.equals(phone1.getPhoneType(), phone2.getPhoneType())) {

			return false;
		}

		return true;
	}

	private boolean _equals(
		PostalAddress postalAddress1, PostalAddress postalAddress2) {

		if (postalAddress1 == postalAddress2) {
			return true;
		}

		if (!Objects.equals(
				postalAddress1.getAddressCountry(),
				postalAddress2.getAddressCountry()) ||
			!Objects.equals(
				postalAddress1.getAddressLocality(),
				postalAddress2.getAddressLocality()) ||
			!Objects.equals(
				postalAddress1.getAddressRegion(),
				postalAddress2.getAddressRegion()) ||
			!Objects.equals(
				postalAddress1.getAddressType(),
				postalAddress2.getAddressType()) ||
			!Objects.equals(
				postalAddress1.getPostalCode(),
				postalAddress2.getPostalCode()) ||
			!Objects.equals(
				postalAddress1.getStreetAddressLine1(),
				postalAddress2.getStreetAddressLine1())) {

			return false;
		}

		return true;
	}

	private boolean _equals(WebUrl webUrl1, WebUrl webUrl2) {
		if (webUrl1 == webUrl2) {
			return true;
		}

		if (!Objects.equals(webUrl1.getUrl(), webUrl2.getUrl()) ||
			!Objects.equals(webUrl1.getUrlType(), webUrl2.getUrlType())) {

			return false;
		}

		return true;
	}

	private Account _postAccount() throws Exception {
		return _postAccount(randomAccount());
	}

	private Account _postAccount(Account account) throws Exception {
		return accountResource.postAccount(account);
	}

	private AccountContactInformation _randomAccountContactInformation() {
		return new AccountContactInformation() {
			{
				emailAddresses = new EmailAddress[] {
					_randomEmailAddress(), _randomEmailAddress()
				};
				facebook = RandomTestUtil.randomString();
				jabber = RandomTestUtil.randomString();
				postalAddresses = new PostalAddress[] {
					_randomPostalAddress(), _randomPostalAddress()
				};
				skype = RandomTestUtil.randomString();
				sms = RandomTestUtil.randomString();
				telephones = new Phone[] {_randomPhone(), _randomPhone()};
				twitter = RandomTestUtil.randomString();
				webUrls = new WebUrl[] {_randomWebUrl(), _randomWebUrl()};
			}
		};
	}

	private EmailAddress _randomEmailAddress() {
		return new EmailAddress() {
			{
				emailAddress = RandomTestUtil.randomString() + "@liferay.com";
				type = "email-address";
			}
		};
	}

	private Phone _randomPhone() {
		return new Phone() {
			{
				extension = String.valueOf(RandomTestUtil.randomInt());
				phoneNumber = String.valueOf(RandomTestUtil.randomInt());
				phoneType = "fax";
			}
		};
	}

	private PostalAddress _randomPostalAddress() {
		return new PostalAddress() {
			{
				addressCountry = "United States";
				addressLocality = RandomTestUtil.randomString();
				addressRegion = "California";
				addressType = "other";
				postalCode = String.valueOf(RandomTestUtil.randomInt());
				streetAddressLine1 = RandomTestUtil.randomString();
			}
		};
	}

	private WebUrl _randomWebUrl() {
		return new WebUrl() {
			{
				url = "https://liferay" + StringUtil.randomString() + ".com";
				urlType = "intranet";
			}
		};
	}

	private void _testGetAccountsPage(
			List<AccountEntry> expectedAccountEntries, Long organizationId)
		throws Exception {

		StringBundler sb = new StringBundler();

		for (AccountEntry accountEntry : expectedAccountEntries) {
			if (sb.length() != 0) {
				sb.append(" or ");
			}

			sb.append(
				String.format("contains(name, '%s')", accountEntry.getName()));
		}

		if (organizationId != null) {
			if (sb.length() != 0) {
				sb.append(" and ");
			}

			sb.append(String.format("organizationIds eq '%s'", organizationId));
		}

		Page<Account> accountsPage = accountResource.getAccountsPage(
			null, sb.toString(), null, null);

		Assert.assertEquals(
			expectedAccountEntries.size(), accountsPage.getTotalCount());

		for (Account account : accountsPage.getItems()) {
			expectedAccountEntries.contains(
				_accountEntryLocalService.getAccountEntry(account.getId()));
		}
	}

	private void _testPatchAccountWithContactInformation() throws Exception {
		Account postAccount = testPatchAccount_addAccount();

		Account randomPatchAccount = randomPatchAccount();

		AccountContactInformation accountContactInformation =
			_randomAccountContactInformation();

		randomPatchAccount.setAccountContactInformation(
			accountContactInformation);

		Account patchAccount = accountResource.patchAccount(
			postAccount.getId(), randomPatchAccount);

		_assertEquals(
			accountContactInformation,
			patchAccount.getAccountContactInformation());
	}

	private void _testPatchAccountWithPostalAddressPhoneNumber()
		throws Exception {

		Account postAccount = testPatchAccount_addAccount();

		Account randomPatchAccount = randomPatchAccount();

		PostalAddress postalAddress = _randomPostalAddress();

		postalAddress.setPhoneNumber(RandomTestUtil.randomString());

		randomPatchAccount.setPostalAddresses(
			new PostalAddress[] {postalAddress});

		Account patchAccount = accountResource.patchAccount(
			postAccount.getId(), randomPatchAccount);

		List<Address> addresses = _addressLocalService.getAddresses(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			patchAccount.getId());

		Address address = addresses.get(0);

		Assert.assertEquals(
			postalAddress.getPhoneNumber(), address.getPhoneNumber());
	}

	private void _testPostAccountDuplicateExternalReferenceCode()
		throws Exception {

		Account account1 = randomAccount();

		Account postAccount = accountResource.postAccount(account1);

		Assert.assertEquals(
			account1.getExternalReferenceCode(),
			postAccount.getExternalReferenceCode());

		try {
			Account account2 = randomAccount();

			account2.setExternalReferenceCode(
				postAccount.getExternalReferenceCode());

			_postAccount(account2);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(
				DuplicateAccountEntryExternalReferenceCodeException.class.
					getSimpleName(),
				problem.getType());
		}
	}

	private void _testPostAccountWithContactInformation() throws Exception {
		Account account = randomAccount();

		AccountContactInformation accountContactInformation =
			_randomAccountContactInformation();

		account.setAccountContactInformation(accountContactInformation);

		Account postAccount = accountResource.postAccount(account);

		_assertEquals(
			accountContactInformation,
			postAccount.getAccountContactInformation());
	}

	private void _testPostAccountWithPostalAddressPhoneNumber()
		throws Exception {

		Account account = randomAccount();

		PostalAddress postalAddress = _randomPostalAddress();

		postalAddress.setPhoneNumber(RandomTestUtil.randomString());

		account.setPostalAddresses(new PostalAddress[] {postalAddress});

		Account postAccount = accountResource.postAccount(account);

		List<Address> addresses = _addressLocalService.getAddresses(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			postAccount.getId());

		Address address = addresses.get(0);

		Assert.assertEquals(
			postalAddress.getPhoneNumber(), address.getPhoneNumber());
	}

	private void _testPutAccountByExternalReferenceCodeWithContactInformation()
		throws Exception {

		Account postAccount =
			testPutAccountByExternalReferenceCode_addAccount();

		Account randomAccount = randomAccount();

		AccountContactInformation accountContactInformation =
			_randomAccountContactInformation();

		randomAccount.setAccountContactInformation(accountContactInformation);

		Account putAccount = accountResource.putAccountByExternalReferenceCode(
			postAccount.getExternalReferenceCode(), randomAccount);

		_assertEquals(
			accountContactInformation,
			putAccount.getAccountContactInformation());
	}

	private void _testPutAccountWithContactInformation() throws Exception {
		Account postAccount = testPutAccount_addAccount();

		Account randomAccount = randomAccount();

		AccountContactInformation accountContactInformation =
			_randomAccountContactInformation();

		randomAccount.setAccountContactInformation(accountContactInformation);

		Account putAccount = accountResource.putAccount(
			postAccount.getId(), randomAccount);

		_assertEquals(
			accountContactInformation,
			putAccount.getAccountContactInformation());
	}

	private void _testPutAccountWithPostalAddressPhoneNumber()
		throws Exception {

		Account postAccount = testPutAccount_addAccount();

		Account randomAccount = randomAccount();

		PostalAddress postalAddress = _randomPostalAddress();

		postalAddress.setPhoneNumber(RandomTestUtil.randomString());

		randomAccount.setPostalAddresses(new PostalAddress[] {postalAddress});

		Account putAccount = accountResource.putAccount(
			postAccount.getId(), randomAccount);

		List<Address> addresses = _addressLocalService.getAddresses(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			putAccount.getId());

		Address address = addresses.get(0);

		Assert.assertEquals(
			postalAddress.getPhoneNumber(), address.getPhoneNumber());
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private AddressLocalService _addressLocalService;

}