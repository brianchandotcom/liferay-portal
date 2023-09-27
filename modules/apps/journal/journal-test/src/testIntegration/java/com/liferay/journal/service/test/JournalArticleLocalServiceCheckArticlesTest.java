/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Oláh
 */
@RunWith(Arquillian.class)
public class JournalArticleLocalServiceCheckArticlesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testApproveScheduledJournalArticleWithFutureExpirationDate()
		throws Exception {

		long now = System.currentTimeMillis();

		Date displayDate = new Date(now - Time.HOUR);
		Date expirationDate = new Date(now + (Time.YEAR * 2));

		_assertCheckArticles(
			displayDate, expirationDate, WorkflowConstants.STATUS_APPROVED);
	}

	@Test
	public void testApproveScheduledJournalArticleWithNeverExpires()
		throws Exception {

		long now = System.currentTimeMillis();

		Date displayDate = new Date(now - Time.HOUR);

		_assertCheckArticles(
			displayDate, null, WorkflowConstants.STATUS_APPROVED);
	}

	@Test
	public void testExpireApprovedArticle() throws Exception {
		testExpireArticle(true, _MODE_DEFAULT);
	}

	@Test
	public void testExpireApprovedArticlePostponeExpiration() throws Exception {
		testExpireArticle(true, _MODE_POSTPONE_EXPIRATION);
	}

	@Test
	public void testExpireDraftArticle() throws Exception {
		testExpireArticle(false, _MODE_DEFAULT);
	}

	@Test
	public void testExpireDraftArticlePostponeExpiration() throws Exception {
		testExpireArticle(false, _MODE_POSTPONE_EXPIRATION);
	}

	@Test
	public void testExpireScheduledJournalArticleDisplayDateAndExpirationDateWithinTheSameInterval()
		throws Exception {

		long now = System.currentTimeMillis();

		Date displayDate = new Date(now - Time.HOUR);
		Date expirationDate = new Date(now - (Time.HOUR * 2));

		_assertCheckArticles(
			displayDate, expirationDate, WorkflowConstants.STATUS_EXPIRED);
	}

	@Test
	public void testExpireScheduledJournalArticleEqualsDisplayDateAndExpirationDate()
		throws Exception {

		long now = System.currentTimeMillis();

		Date date = new Date(now - Time.HOUR);

		_assertCheckArticles(date, date, WorkflowConstants.STATUS_EXPIRED);
	}

	@Test
	public void testSetFutureExpirationDate() throws Exception {
		JournalArticle article = addArticle(
			_group.getGroupId(), false, true, false);

		Date modifiedDate = article.getModifiedDate();

		JournalArticle updatedArticle = updateArticle(
			article, _MODE_POSTPONE_EXPIRATION);

		article = _journalArticleLocalService.getArticle(article.getId());

		Assert.assertEquals(modifiedDate, article.getModifiedDate());

		updatedArticle.setExpirationDate(
			new Date(System.currentTimeMillis() - (Time.HOUR * 2)));

		_journalArticleLocalService.updateJournalArticle(updatedArticle);

		_journalArticleLocalService.checkArticles(_group.getCompanyId());

		article = _journalArticleLocalService.getArticle(article.getId());

		Assert.assertTrue(modifiedDate.before(article.getModifiedDate()));
	}

	protected JournalArticle addArticle(
			long groupId, boolean neverExpires, boolean publish,
			boolean scheduled)
		throws Exception {

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			groupId, JournalArticle.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			groupId, ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		Calendar displayDateCalendar = new GregorianCalendar();

		long time = System.currentTimeMillis();

		if (scheduled) {
			time = time + Time.HOUR;
		}

		displayDateCalendar.setTime(new Date(time));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		if (publish) {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}
		else {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		Calendar expirationDateCalendar = getExpirationCalendar(Time.HOUR, 1);

		return _journalArticleLocalService.addArticle(
			null, TestPropsValues.getUserId(), groupId,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT, titleMap,
			descriptionMap, titleMap, content, ddmStructure.getStructureId(),
			ddmTemplate.getTemplateKey(), null,
			displayDateCalendar.get(Calendar.MONTH),
			displayDateCalendar.get(Calendar.DAY_OF_MONTH),
			displayDateCalendar.get(Calendar.YEAR),
			displayDateCalendar.get(Calendar.HOUR_OF_DAY),
			displayDateCalendar.get(Calendar.MINUTE),
			expirationDateCalendar.get(Calendar.MONTH),
			expirationDateCalendar.get(Calendar.DAY_OF_MONTH),
			expirationDateCalendar.get(Calendar.YEAR),
			expirationDateCalendar.get(Calendar.HOUR_OF_DAY),
			expirationDateCalendar.get(Calendar.MINUTE), neverExpires, 0, 0, 0,
			0, 0, true, true, false, 0, 0, null, null, null, null,
			serviceContext);
	}

	protected Calendar getExpirationCalendar(long timeUnit, int timeValue)
		throws PortalException {

		Calendar calendar = new GregorianCalendar();

		calendar.setTime(
			new Date(System.currentTimeMillis() + (timeUnit * timeValue)));

		User user = TestPropsValues.getUser();

		calendar.setTimeZone(user.getTimeZone());

		return calendar;
	}

	protected void testExpireArticle(boolean approved, int mode)
		throws Exception {

		// Add expiring, approved Article

		JournalArticle article = addArticle(
			_group.getGroupId(), false, approved, false);

		// Add a version of the article, changing expire date

		article = updateArticle(article, mode);

		// Simulate automatic expiration

		Date expirationDate = article.getExpirationDate();

		article.setExpirationDate(
			new Date(expirationDate.getTime() - (Time.HOUR * 2)));

		article = _journalArticleLocalService.updateJournalArticle(article);

		_journalArticleLocalService.checkArticles(_group.getCompanyId());

		article = _journalArticleLocalService.getArticle(article.getId());

		if (approved) {
			if (mode == _MODE_POSTPONE_EXPIRATION) {
				Assert.assertFalse(article.isExpired());
			}
			else {
				Assert.assertTrue(article.isExpired());
			}
		}
		else {
			Assert.assertFalse(article.isExpired());
		}
	}

	protected JournalArticle updateArticle(JournalArticle article, int mode)
		throws Exception {

		if (mode == _MODE_POSTPONE_EXPIRATION) {
			Calendar displayDateCalendar = new GregorianCalendar();

			displayDateCalendar.setTime(article.getDisplayDate());

			Calendar expirationDateCalendar = getExpirationCalendar(
				Time.YEAR, 1);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(article.getGroupId());

			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			return _journalArticleLocalService.updateArticle(
				TestPropsValues.getUserId(), article.getGroupId(),
				article.getFolderId(), article.getArticleId(),
				article.getVersion(), article.getTitleMap(),
				article.getDescriptionMap(), article.getFriendlyURLMap(),
				article.getContent(), article.getDDMTemplateKey(),
				article.getLayoutUuid(),
				displayDateCalendar.get(Calendar.MONTH),
				displayDateCalendar.get(Calendar.DAY_OF_MONTH),
				displayDateCalendar.get(Calendar.YEAR),
				displayDateCalendar.get(Calendar.HOUR_OF_DAY),
				displayDateCalendar.get(Calendar.MINUTE),
				expirationDateCalendar.get(Calendar.MONTH),
				expirationDateCalendar.get(Calendar.DAY_OF_MONTH),
				expirationDateCalendar.get(Calendar.YEAR),
				expirationDateCalendar.get(Calendar.HOUR_OF_DAY),
				expirationDateCalendar.get(Calendar.MINUTE), false, 0, 0, 0, 0,
				0, true, article.isIndexable(), article.isSmallImage(), 0,
				article.getSmallImageSource(), article.getSmallImageURL(), null,
				null, null, serviceContext);
		}

		return article;
	}

	private void _assertCheckArticles(
			Date displayDate, Date expirationDate, int status)
		throws Exception {

		JournalArticle journalArticle = addArticle(
			_group.getGroupId(), expirationDate == null, true, true);

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, journalArticle.getStatus());

		journalArticle.setDisplayDate(displayDate);
		journalArticle.setExpirationDate(expirationDate);

		journalArticle = _journalArticleLocalService.updateJournalArticle(
			journalArticle);

		Assert.assertEquals(displayDate, journalArticle.getDisplayDate());
		Assert.assertEquals(expirationDate, journalArticle.getExpirationDate());

		_journalArticleLocalService.checkArticles(_group.getCompanyId());

		journalArticle = _journalArticleLocalService.getArticle(
			journalArticle.getId());

		Assert.assertEquals(displayDate, journalArticle.getDisplayDate());
		Assert.assertEquals(expirationDate, journalArticle.getExpirationDate());
		Assert.assertEquals(status, journalArticle.getStatus());
	}

	private static final int _MODE_DEFAULT = 0;

	private static final int _MODE_POSTPONE_EXPIRATION = 1;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

}