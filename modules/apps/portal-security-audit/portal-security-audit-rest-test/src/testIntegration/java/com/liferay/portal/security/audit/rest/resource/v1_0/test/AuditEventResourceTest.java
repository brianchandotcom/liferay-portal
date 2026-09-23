/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.security.audit.rest.client.dto.v1_0.AuditEvent;
import com.liferay.portal.security.audit.rest.client.dto.v1_0.Creator;
import com.liferay.portal.security.audit.rest.client.pagination.Page;
import com.liferay.portal.security.audit.rest.client.pagination.Pagination;
import com.liferay.portal.security.audit.rest.client.problem.Problem;
import com.liferay.portal.security.audit.rest.client.resource.v1_0.AuditEventResource;
import com.liferay.portal.security.audit.storage.service.AuditEventLocalService;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 * @author Manuele Castro
 */
@RunWith(Arquillian.class)
public class AuditEventResourceTest extends BaseAuditEventResourceTestCase {

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		for (com.liferay.portal.security.audit.storage.model.AuditEvent
				serviceBuilderAuditEvent : _serviceBuilderAuditEvents) {

			_auditEventLocalService.deleteAuditEvent(serviceBuilderAuditEvent);
		}
	}

	@Override
	@Test
	public void testGetAuditEventsPage() throws Exception {
		Page<AuditEvent> page = auditEventResource.getAuditEventsPage(
			null, null, null, null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		AccountEntry accountEntry1 = _accountEntryLocalService.addAccountEntry(
			StringPool.BLANK, TestPropsValues.getUserId(), 0L,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		String contextName = RandomTestUtil.randomString();

		AuditEvent auditEvent1 = testGetAuditEventsPage_addAuditEvent(
			_randomAuditEvent(
				accountEntry1.getAccountEntryId(), contextName,
				EventTypes.ADD));

		AccountEntry accountEntry2 = _accountEntryLocalService.addAccountEntry(
			StringPool.BLANK, TestPropsValues.getUserId(), 0L,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		AuditEvent auditEvent2 = testGetAuditEventsPage_addAuditEvent(
			_randomAuditEvent(
				accountEntry2.getAccountEntryId(), contextName, null));

		page = auditEventResource.getAuditEventsPage(
			null, null, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount, page.getTotalCount());

		page = auditEventResource.getAuditEventsPage(
			new Long[] {accountEntry1.getAccountEntryId()}, contextName, null,
			EventTypes.ADD, null, Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertContains(auditEvent1, (List<AuditEvent>)page.getItems());

		page = auditEventResource.getAuditEventsPage(
			new Long[] {
				accountEntry1.getAccountEntryId(),
				accountEntry2.getAccountEntryId()
			},
			contextName, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertContains(auditEvent1, (List<AuditEvent>)page.getItems());
		assertContains(auditEvent2, (List<AuditEvent>)page.getItems());

		User user = UserTestUtil.addUser(
			testCompany, PropsValues.DEFAULT_ADMIN_PASSWORD);

		_addUserToAccount(
			accountEntry1,
			_roleLocalService.getRole(
				TestPropsValues.getCompanyId(),
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR),
			user);
		_addUserToAccount(
			accountEntry2,
			_roleLocalService.getRole(
				TestPropsValues.getCompanyId(),
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER),
			user);

		AuditEventResource auditEventResource = AuditEventResource.builder(
		).authentication(
			user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();

		page = auditEventResource.getAuditEventsPage(
			new Long[] {accountEntry1.getAccountEntryId()}, contextName, null,
			EventTypes.ADD, null, Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertContains(auditEvent1, (List<AuditEvent>)page.getItems());

		Assert.assertThrows(
			Problem.ProblemException.class,
			() -> auditEventResource.getAuditEventsPage(
				new Long[] {
					accountEntry1.getAccountEntryId(),
					accountEntry2.getAccountEntryId()
				},
				contextName, null, null, null, Pagination.of(1, 10), null));

		_testGetAuditEventsPageCreator();
	}

	@Override
	protected AuditEvent randomAuditEvent() throws Exception {
		AuditEvent auditEvent = super.randomAuditEvent();

		auditEvent.setAccountId(0L);
		auditEvent.setContextName((String)null);

		return auditEvent;
	}

	@Override
	protected AuditEvent testGetAuditEventsPage_addAuditEvent(
		AuditEvent auditEvent) {

		com.liferay.portal.security.audit.storage.model.AuditEvent
			serviceBuilderAuditEvent = _auditEventLocalService.addAuditEvent(
				new AuditMessage(
					0L, testCompany.getCompanyId(), 0L, null, null,
					auditEvent.getAccountId(), null, auditEvent.getEntityType(),
					String.valueOf(auditEvent.getEntityId()),
					auditEvent.getContextName(), auditEvent.getEventType(),
					null));

		_serviceBuilderAuditEvents.add(serviceBuilderAuditEvent);

		return new AuditEvent() {
			{
				id = serviceBuilderAuditEvent.getAuditEventId();
			}
		};
	}

	private void _addAuditEvent(
		String contextName, boolean pseudonymized, long userId,
		String userName) {

		AuditMessage auditMessage = new AuditMessage(
			testCompany.getCompanyId(), userId, userName,
			RandomTestUtil.randomString());

		auditMessage.setContextName(contextName);
		auditMessage.setPseudonymized(pseudonymized);

		_serviceBuilderAuditEvents.add(
			_auditEventLocalService.addAuditEvent(auditMessage));
	}

	private void _addUserToAccount(
			AccountEntry accountEntry, Role role, User user)
		throws Exception {

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), user.getUserId());

		AccountRole accountRole =
			_accountRoleLocalService.getAccountRoleByRoleId(role.getRoleId());

		_accountRoleLocalService.associateUser(
			accountEntry.getAccountEntryId(), accountRole.getAccountRoleId(),
			user.getUserId());
	}

	private Creator _getCreator(String contextName) throws Exception {
		Page<AuditEvent> page = auditEventResource.getAuditEventsPage(
			null, contextName, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		AuditEvent auditEvent = page.fetchFirstItem();

		return auditEvent.getCreator();
	}

	private AuditEvent _randomAuditEvent(
			long accountId, String contextName, String eventType)
		throws Exception {

		AuditEvent auditEvent = randomAuditEvent();

		auditEvent.setAccountId(accountId);
		auditEvent.setContextName(contextName);
		auditEvent.setEventType(eventType);

		return auditEvent;
	}

	private void _testGetAuditEventsPageCreator() throws Exception {
		String contextName = RandomTestUtil.randomString();
		User user = TestPropsValues.getUser();

		_addAuditEvent(
			contextName, false, user.getUserId(),
			RandomTestUtil.randomString());

		Creator creator = _getCreator(contextName);

		Assert.assertEquals("UserAccount", creator.getContentType());
		Assert.assertEquals(Long.valueOf(user.getUserId()), creator.getId());
		Assert.assertEquals(user.getFullName(), creator.getName());

		contextName = RandomTestUtil.randomString();
		long userId = RandomTestUtil.randomLong();
		String userName = RandomTestUtil.randomString();

		_addAuditEvent(contextName, true, userId, userName);

		creator = _getCreator(contextName);

		Assert.assertNull(creator.getContentType());
		Assert.assertEquals(Long.valueOf(userId), creator.getId());
		Assert.assertEquals(userName, creator.getName());

		contextName = RandomTestUtil.randomString();

		_addAuditEvent(contextName, true, 0, StringPool.BLANK);

		Assert.assertNull(_getCreator(contextName));
	}

	private static final List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
			_serviceBuilderAuditEvents = new ArrayList<>();

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private AuditEventLocalService _auditEventLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}