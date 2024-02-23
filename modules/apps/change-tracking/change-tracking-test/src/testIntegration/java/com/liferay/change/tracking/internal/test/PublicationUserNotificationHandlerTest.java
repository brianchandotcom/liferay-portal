/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.constants.PublicationRoleConstants;
import com.liferay.change.tracking.internal.test.util.CTCollectionTestUtil;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Gislayne Vitorino
 * @author Brooke Dalton
 */
@FeatureFlags("LPD-11018")
@RunWith(Arquillian.class)
public class PublicationUserNotificationHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetBodyForConflict() throws Exception {
		CTCollection ctCollection =
			CTCollectionTestUtil.createCTCollectionWithConflict(
				TestPropsValues.getUser());

		CTCollectionTestUtil.publishCTCollectionWithError(
			ctCollection.getCtCollectionId());

		List<UserNotificationEvent> userNotificationEvents =
			_userNotificationEventLocalService.getUserNotificationEvents(
				TestPropsValues.getUserId());

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			if (!Objects.equals(
					CTPortletKeys.PUBLICATIONS,
					userNotificationEvent.getType())) {

				continue;
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				userNotificationEvent.getPayload());

			if ((jsonObject.getInt("notificationType") !=
					UserNotificationDefinition.
						NOTIFICATION_TYPE_REVIEW_ENTRY) ||
				!jsonObject.getBoolean("showConflicts")) {

				continue;
			}

			UserNotificationFeedEntry userNotificationFeedEntry =
				_userNotificationHandler.interpret(
					userNotificationEvent, _getServiceContext());

			Assert.assertEquals(
				StringBundler.concat(
					"<div class=\"title\">", ctCollection.getName(),
					" scheduled publication failed.</div><div class=\"body\">",
					"Click on this notification to see the list of conflicts ",
					"that need to be manually resolved.</div>"),
				userNotificationFeedEntry.getBody());
		}
	}

	@Test
	public void testGetBodyForStoppedService() throws Exception {
		CTCollection ctCollection =
			CTCollectionTestUtil.createCTCollectionwithJournalArticle();

		_group = _groupLocalService.addGroup(
			TestPropsValues.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			CTCollection.class.getName(), ctCollection.getCtCollectionId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			RandomTestUtil.randomLocaleStringMap(), null,
			GroupConstants.TYPE_SITE_OPEN, false,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);

		User user = UserTestUtil.addUser();

		_addPublicationUserGroupRole(user);

		Bundle journalServiceBundle = null;

		try {
			Bundle bundle = FrameworkUtil.getBundle(
				JournalArticleService.class);

			BundleContext bundleContext = bundle.getBundleContext();

			for (Bundle curBundle : bundleContext.getBundles()) {
				if (Objects.equals(
						curBundle.getSymbolicName(),
						"com.liferay.journal.service") &&
					(curBundle.getState() == Bundle.ACTIVE)) {

					curBundle.stop();

					journalServiceBundle = curBundle;

					break;
				}
			}

			CTCollectionTestUtil.publishCTCollectionWithError(
				ctCollection.getCtCollectionId());

			List<UserNotificationEvent> userNotificationEvents1 =
				_userNotificationEventLocalService.getUserNotificationEvents(
					user.getUserId());

			for (UserNotificationEvent userNotificationEvent :
					userNotificationEvents1) {

				if (!Objects.equals(
						CTPortletKeys.PUBLICATIONS,
						userNotificationEvent.getType())) {

					continue;
				}

				JSONObject jsonObject = _jsonFactory.createJSONObject(
					userNotificationEvent.getPayload());

				if ((jsonObject.getInt("notificationType") !=
						UserNotificationDefinition.
							NOTIFICATION_TYPE_REVIEW_ENTRY) ||
					jsonObject.getBoolean("showConflicts")) {

					continue;
				}

				UserNotificationFeedEntry userNotificationFeedEntry =
					_userNotificationHandler.interpret(
						userNotificationEvent,
						ServiceContextTestUtil.getServiceContext());

				Assert.assertEquals(
					StringBundler.concat(
						"<div class=\"title\">", ctCollection.getName(),
						" scheduled publication failed.</div><div class=",
						"\"body\">An unexpected error occurred while ",
						"publishing the scheduled publication. Please contact ",
						"your system administrator to resolve the issue.",
						"</div>"),
					userNotificationFeedEntry.getBody());
			}

			List<UserNotificationEvent> userNotificationEvents2 =
				_userNotificationEventLocalService.getUserNotificationEvents(
					TestPropsValues.getUserId());

			for (UserNotificationEvent userNotificationEvent :
					userNotificationEvents2) {

				if (!Objects.equals(
						CTPortletKeys.PUBLICATIONS,
						userNotificationEvent.getType())) {

					continue;
				}

				JSONObject jsonObject = _jsonFactory.createJSONObject(
					userNotificationEvent.getPayload());

				if (_isAdminUser(userNotificationEvent) &&
					!jsonObject.getBoolean("showConflicts")) {

					Assert.assertNotNull(userNotificationEvent);

					Assert.assertEquals(
						_getAdminUserId(userNotificationEvent.getCompanyId()),
						userNotificationEvent.getUserId());
				}
			}
		}
		finally {
			if (journalServiceBundle != null) {
				journalServiceBundle.start();
			}
		}
	}

	@Test
	public void testGetLinkToViewConflicts() throws Exception {
		CTCollection ctCollection =
			CTCollectionTestUtil.createCTCollectionWithConflict(
				TestPropsValues.getUser());

		CTCollectionTestUtil.publishCTCollectionWithError(
			ctCollection.getCtCollectionId());

		List<UserNotificationEvent> userNotificationEvents =
			_userNotificationEventLocalService.getUserNotificationEvents(
				TestPropsValues.getUserId());

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			if (!Objects.equals(
					CTPortletKeys.PUBLICATIONS,
					userNotificationEvent.getType())) {

				continue;
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				userNotificationEvent.getPayload());

			if ((jsonObject.getInt("notificationType") !=
					UserNotificationDefinition.
						NOTIFICATION_TYPE_REVIEW_ENTRY) ||
				!jsonObject.getBoolean("showConflicts")) {

				continue;
			}

			ServiceContext serviceContext = _getServiceContext();

			UserNotificationFeedEntry userNotificationFeedEntry =
				_userNotificationHandler.interpret(
					userNotificationEvent, serviceContext);

			Assert.assertEquals(
				userNotificationFeedEntry.getLink(),
				PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						serviceContext.getRequest(),
						serviceContext.getScopeGroup(),
						CTPortletKeys.PUBLICATIONS, 0, 0,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/change_tracking/view_conflicts"
				).setParameter(
					"ctCollectionId",
					_jsonFactory.createJSONObject(
						userNotificationEvent.getPayload()
					).getLong(
						"ctCollectionId"
					)
				).buildString());
		}
	}

	@Test
	public void testGetLinkToViewStackTrace() throws Exception {
		CTCollection ctCollection =
			CTCollectionTestUtil.createCTCollectionwithJournalArticle();

		Bundle journalServiceBundle = null;

		try {
			Bundle bundle = FrameworkUtil.getBundle(
				JournalArticleService.class);

			BundleContext bundleContext = bundle.getBundleContext();

			for (Bundle curBundle : bundleContext.getBundles()) {
				if (Objects.equals(
						curBundle.getSymbolicName(),
						"com.liferay.journal.service") &&
					(curBundle.getState() == Bundle.ACTIVE)) {

					curBundle.stop();

					journalServiceBundle = curBundle;

					break;
				}
			}

			CTCollectionTestUtil.publishCTCollectionWithError(
				ctCollection.getCtCollectionId());

			List<UserNotificationEvent> userNotificationEvents =
				_userNotificationEventLocalService.getUserNotificationEvents(
					TestPropsValues.getUserId());

			for (UserNotificationEvent userNotificationEvent :
					userNotificationEvents) {

				if (!Objects.equals(
						CTPortletKeys.PUBLICATIONS,
						userNotificationEvent.getType())) {

					continue;
				}

				JSONObject jsonObject = _jsonFactory.createJSONObject(
					userNotificationEvent.getPayload());

				if ((jsonObject.getInt("notificationType") !=
						UserNotificationDefinition.
							NOTIFICATION_TYPE_REVIEW_ENTRY) ||
					jsonObject.getBoolean("showConflicts") ||
					!Objects.equals(
						userNotificationEvent.getUserId(),
						_getAdminUserId(
							userNotificationEvent.getCompanyId()))) {

					continue;
				}

				ServiceContext serviceContext = _getServiceContext();

				UserNotificationFeedEntry userNotificationFeedEntry =
					_userNotificationHandler.interpret(
						userNotificationEvent, serviceContext);

				Assert.assertEquals(
					userNotificationFeedEntry.getLink(),
					PortletURLBuilder.create(
						_portal.getControlPanelPortletURL(
							serviceContext.getRequest(),
							serviceContext.getScopeGroup(),
							CTPortletKeys.PUBLICATIONS, 0, 0,
							PortletRequest.RENDER_PHASE)
					).setMVCRenderCommandName(
						"/change_tracking/view_stack_trace"
					).setParameter(
						"backgroundTaskId",
						_jsonFactory.createJSONObject(
							userNotificationEvent.getPayload()
						).getLong(
							"backgroundTaskId"
						)
					).setParameter(
						"ctCollectionName",
						jsonObject.getString("ctCollectionName")
					).buildString());
			}
		}
		finally {
			if (journalServiceBundle != null) {
				journalServiceBundle.start();
			}
		}
	}

	private void _addPublicationUserGroupRole(User user) throws Exception {
		Role role = _roleLocalService.fetchRole(
			user.getCompanyId(), PublicationRoleConstants.NAME_PUBLISHER);

		if (role == null) {
			role = RoleTestUtil.addRole(
				PublicationRoleConstants.NAME_PUBLISHER,
				RoleConstants.TYPE_PUBLICATIONS);
		}

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), _group.getGroupId(), role.getRoleId());
	}

	private long _getAdminUserId(long companyId) throws Exception {
		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		long[] userIds = _userLocalService.getRoleUserIds(role.getRoleId());

		return userIds[0];
	}

	private ServiceContext _getServiceContext() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));

		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setSiteGroupId(_group.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		serviceContext.setRequest(mockHttpServletRequest);

		return serviceContext;
	}

	private boolean _isAdminUser(UserNotificationEvent userNotificationEvent)
		throws Exception {

		Role role = _roleLocalService.getRole(
			userNotificationEvent.getCompanyId(), RoleConstants.ADMINISTRATOR);

		return _userLocalService.hasRoleUser(
			role.getRoleId(), userNotificationEvent.getUserId());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Portal _portal;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

	@Inject(filter = "javax.portlet.name=" + CTPortletKeys.PUBLICATIONS)
	private UserNotificationHandler _userNotificationHandler;

}