/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.internal.test.util.CTCollectionTestUtil;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
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

	@Test
	public void testGetUserNotificationEvent() throws Exception {
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

				if (Objects.equals(
						userNotificationEvent.getUserId(),
						_getAdminUserId(
							userNotificationEvent.getCompanyId()))) {

					Assert.assertNotNull(userNotificationEvent);

					Assert.assertFalse(jsonObject.getBoolean("showConflicts"));

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

	private long _getAdminUserId(long companyId) throws Exception {
		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		long[] userIds = _userLocalService.getRoleUserIds(role.getRoleId());

		return userIds[0];
	}

	private ServiceContext _getServiceContext() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setRequest(new MockHttpServletRequest());

		return serviceContext;
	}

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	private Group _group;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Portal _portal;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

	@Inject(filter = "javax.portlet.name=" + CTPortletKeys.PUBLICATIONS)
	private UserNotificationHandler _userNotificationHandler;

}