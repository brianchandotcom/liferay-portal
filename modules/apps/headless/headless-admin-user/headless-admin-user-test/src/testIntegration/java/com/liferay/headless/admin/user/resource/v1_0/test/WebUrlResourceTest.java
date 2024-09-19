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
import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WebsiteLocalServiceUtil;
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
public class WebUrlResourceTest extends BaseWebUrlResourceTestCase {

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
		return new String[] {"url"};
	}

	@Override
	protected WebUrl randomWebUrl() {
		return new WebUrl() {
			{
				url = "http://" + RandomTestUtil.randomString() + ".com";
				urlType = "blog";
			}
		};
	}

	@Override
	protected WebUrl testDeleteWebUrl_addWebUrl() throws Exception {
		return _addWebUrl(
			randomWebUrl(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_WEBSITE);
	}

	@Override
	protected WebUrl testDeleteWebUrlByExternalReferenceCode_addWebUrl()
		throws Exception {

		return _addWebUrl(
			randomWebUrl(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_WEBSITE);
	}

	@Override
	protected WebUrl testGetAccountByExternalReferenceCodeWebUrlsPage_addWebUrl(
			String externalReferenceCode, WebUrl webUrl)
		throws Exception {

		return _addWebUrl(
			webUrl, AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(),
			AccountListTypeConstants.ACCOUNT_ENTRY_WEBSITE);
	}

	@Override
	protected String
			testGetAccountByExternalReferenceCodeWebUrlsPage_getExternalReferenceCode()
		throws Exception {

		return _accountEntry.getExternalReferenceCode();
	}

	@Override
	protected WebUrl testGetAccountWebUrlsPage_addWebUrl(
			Long accountId, WebUrl webUrl)
		throws Exception {

		return _addWebUrl(
			webUrl, AccountEntry.class.getName(), accountId,
			AccountListTypeConstants.ACCOUNT_ENTRY_WEBSITE);
	}

	@Override
	protected Long testGetAccountWebUrlsPage_getAccountId() throws Exception {
		return _accountEntry.getAccountEntryId();
	}

	@Override
	protected WebUrl
			testGetOrganizationByExternalReferenceCodeWebUrlsPage_addWebUrl(
				String externalReferenceCode, WebUrl webUrl)
		throws Exception {

		return _addWebUrl(
			webUrl, Organization.class.getName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_WEBSITE);
	}

	@Override
	protected String
			testGetOrganizationByExternalReferenceCodeWebUrlsPage_getExternalReferenceCode()
		throws Exception {

		return _organization.getExternalReferenceCode();
	}

	@Override
	protected WebUrl testGetOrganizationWebUrlsPage_addWebUrl(
			String organizationId, WebUrl webUrl)
		throws Exception {

		return _addWebUrl(
			webUrl, Organization.class.getName(),
			_organization.getOrganizationId(),
			ListTypeConstants.ORGANIZATION_WEBSITE);
	}

	@Override
	protected String testGetOrganizationWebUrlsPage_getOrganizationId() {
		return String.valueOf(_organization.getOrganizationId());
	}

	@Override
	protected WebUrl
			testGetUserAccountByExternalReferenceCodeWebUrlsPage_addWebUrl(
				String externalReferenceCode, WebUrl webUrl)
		throws Exception {

		return _addWebUrl(
			webUrl, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_WEBSITE);
	}

	@Override
	protected String
			testGetUserAccountByExternalReferenceCodeWebUrlsPage_getExternalReferenceCode()
		throws Exception {

		return _user.getExternalReferenceCode();
	}

	@Override
	protected WebUrl testGetUserAccountWebUrlsPage_addWebUrl(
			Long userAccountId, WebUrl webUrl)
		throws Exception {

		return _addWebUrl(
			webUrl, Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_WEBSITE);
	}

	@Override
	protected Long testGetUserAccountWebUrlsPage_getUserAccountId() {
		return _user.getUserId();
	}

	@Override
	protected WebUrl testGetWebUrl_addWebUrl() throws Exception {
		return _addWebUrl(
			randomWebUrl(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_WEBSITE);
	}

	@Override
	protected WebUrl testGetWebUrlByExternalReferenceCode_addWebUrl()
		throws Exception {

		return _addWebUrl(
			randomWebUrl(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_WEBSITE);
	}

	@Override
	protected WebUrl testGraphQLWebUrl_addWebUrl() throws Exception {
		return testGetWebUrl_addWebUrl();
	}

	@Override
	protected WebUrl testPatchWebUrl_addWebUrl() throws Exception {
		return _addWebUrl(
			randomWebUrl(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_WEBSITE);
	}

	@Override
	protected WebUrl testPatchWebUrlByExternalReferenceCode_addWebUrl()
		throws Exception {

		return _addWebUrl(
			randomWebUrl(), Contact.class.getName(), _user.getContactId(),
			ListTypeConstants.CONTACT_WEBSITE);
	}

	private WebUrl _addWebUrl(
			WebUrl webUrl, String className, long classPK, String listTypeId)
		throws Exception {

		return _toWebUrl(
			WebsiteLocalServiceUtil.addWebsite(
				RandomTestUtil.randomString(), _user.getUserId(), className,
				classPK, webUrl.getUrl(), _getListTypeId(listTypeId), false,
				new ServiceContext()));
	}

	private long _getListTypeId(String listTypeId) {
		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(
			_user.getCompanyId(), listTypeId);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
	}

	private WebUrl _toWebUrl(Website website) {
		return new WebUrl() {
			{
				externalReferenceCode = website.getExternalReferenceCode();
				id = website.getWebsiteId();
				url = website.getUrl();
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