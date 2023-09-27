/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.notifications.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.notifications.test.util.BaseUserNotificationTestCase;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class JournalUserNotificationTest extends BaseUserNotificationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Test
	public void testUserNotificationWhenJournalArticleExpiredAutomatically()
		throws Exception {

		JournalArticle expiredArticle = (JournalArticle)addBaseModel();

		expiredArticle.setExpirationDate(
			new Date(System.currentTimeMillis() - (Time.HOUR * 2)));

		expiredArticle = _journalArticleLocalService.updateJournalArticle(
			expiredArticle);

		subscribeToContainer();

		_journalArticleLocalService.checkArticles(group.getCompanyId());

		_assertExpiredJournalArticleNotifications(expiredArticle);
	}

	@Test
	public void testUserNotificationWhenJournalArticleExpiredManually()
		throws Exception {

		JournalArticle expiredArticle = (JournalArticle)addBaseModel();

		subscribeToContainer();

		expiredArticle = JournalArticleLocalServiceUtil.expireArticle(
			TestPropsValues.getUserId(), group.getGroupId(),
			expiredArticle.getArticleId(), expiredArticle.getVersion(),
			expiredArticle.getUrlTitle(),
			ServiceContextTestUtil.getServiceContext());

		_assertExpiredJournalArticleNotifications(expiredArticle);
	}

	@Override
	protected BaseModel<?> addBaseModel() throws Exception {
		return JournalTestUtil.addArticleWithWorkflow(
			group.getGroupId(), _folder.getFolderId(), true);
	}

	@Override
	protected void addContainerModel() throws Exception {
		_folder = JournalTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());
	}

	@Override
	protected String getPortletId() {
		return JournalPortletKeys.JOURNAL;
	}

	@Override
	protected void subscribeToContainer() throws Exception {
		JournalFolderLocalServiceUtil.subscribe(
			user.getUserId(), group.getGroupId(), _folder.getFolderId());
	}

	@Override
	protected BaseModel<?> updateBaseModel(BaseModel<?> baseModel)
		throws Exception {

		return JournalTestUtil.updateArticleWithWorkflow(
			(JournalArticle)baseModel, true);
	}

	private void _assertExpiredJournalArticleNotifications(
			JournalArticle expiredArticle)
		throws Exception {

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(user.getUserId());

		Assert.assertEquals(
			userNotificationEventsJSONObjects.toString(), 1,
			userNotificationEventsJSONObjects.size());

		JSONObject jsonObject = userNotificationEventsJSONObjects.get(0);

		Assert.assertEquals(
			expiredArticle.getId(), jsonObject.getLong("classPK"));
		Assert.assertEquals(
			UserNotificationDefinition.NOTIFICATION_TYPE_EXPIRED_ENTRY,
			jsonObject.getInt("notificationType"));
	}

	private JournalFolder _folder;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

}