/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
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
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			StringPool.BLANK);

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			journalArticle = _journalArticleLocalService.updateArticle(
				journalArticle.getId(), RandomTestUtil.randomString());
		}

		_journalArticleLocalService.deleteArticle(
			_group.getGroupId(), journalArticle.getArticleId(),
			ServiceContextTestUtil.getServiceContext());

		_publishCTCollection(ctCollection.getCtCollectionId());

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
		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			JournalTestUtil.addArticle(
				_group.getGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK);
		}

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

			_publishCTCollection(ctCollection.getCtCollectionId());

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
	public void testGetLinkToViewConflictsPage() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			StringPool.BLANK);

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			journalArticle = _journalArticleLocalService.updateArticle(
				journalArticle.getId(), RandomTestUtil.randomString());
		}

		_journalArticleLocalService.deleteArticle(
			_group.getGroupId(), journalArticle.getArticleId(),
			ServiceContextTestUtil.getServiceContext());

		_publishCTCollection(ctCollection.getCtCollectionId());

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

	private ServiceContext _getServiceContext() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setRequest(new MockHttpServletRequest());

		return serviceContext;
	}

	private void _publishCTCollection(long ctCollectionId) throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollectionId);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to execute background task", logEntry.getMessage());
		}
	}

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTCollectionService _ctCollectionService;

	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Portal _portal;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

	@Inject(filter = "javax.portlet.name=" + CTPortletKeys.PUBLICATIONS)
	private UserNotificationHandler _userNotificationHandler;

}