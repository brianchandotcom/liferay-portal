/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.journal.atom;

import com.liferay.portal.atom.AtomPager;
import com.liferay.portal.atom.AtomUtil;
import com.liferay.portal.kernel.atom.AtomEntryContent;
import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.atom.BaseAtomCollectionAdapter;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.util.comparator.ArticleVersionComparator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JournalArticleAtomCollectionProvider
	extends BaseAtomCollectionAdapter<JournalArticle> {

	@Override
	public String getCollectionName() {
		return _COLLECTION_NAME;
	}

	@Override
	public List<String> getEntryAuthors(JournalArticle journalArticle) {
		List<String> authors = new ArrayList<String>(1);

		authors.add(journalArticle.getUserName());

		return authors;
	}

	@Override
	public AtomEntryContent getEntryContent(
		JournalArticle journalArticle, AtomRequestContext atomRequestContext) {

		return new AtomEntryContent(
			journalArticle.getContent(), AtomEntryContent.Type.XML);
	}

	@Override
	public String getEntryId(JournalArticle journalArticle) {
		return journalArticle.getArticleId();
	}

	@Override
	public String getEntrySummary(JournalArticle entry) {
		return null;
	}

	@Override
	public String getEntryTitle(JournalArticle journalArticle) {
		return journalArticle.getTitle();
	}

	@Override
	public Date getEntryUpdated(JournalArticle journalArticle) {
		return journalArticle.getModifiedDate();
	}

	@Override
	public String getFeedTitle(AtomRequestContext atomRequestContext) {
		return AtomUtil.createFeedTitleFromPortletName(
			atomRequestContext, PortletKeys.JOURNAL);
	}

	@Override
	protected void doDeleteEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = atomRequestContext.getLongParameter("groupId");
		String articleId = resourceName;

		ServiceContext serviceContext = new ServiceContext();

		JournalArticleServiceUtil.deleteArticle(
			groupId, articleId, null, serviceContext);
	}

	@Override
	protected JournalArticle doGetEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = atomRequestContext.getLongParameter("groupId");
		String articleId = resourceName;

		return JournalArticleServiceUtil.getArticle(groupId, articleId);
	}

	@Override
	protected Iterable<JournalArticle> doGetFeedEntries(
			AtomRequestContext atomRequestContext)
		throws Exception {

		List<JournalArticle> journalArticles = new ArrayList<JournalArticle>();

		long companyId = CompanyThreadLocal.getCompanyId();
		long groupId = atomRequestContext.getLongParameter("groupId");

		if ((companyId <= 0) || (groupId <= 0)) {
			return journalArticles;
		}

		List<Long> folderIds = Collections.emptyList();
		long classNameId = 0;
		String keywords = null;
		Double version = null;
		String ddmStructureKey = null;
		String ddmTemplateKey = null;
		Date displayDateGT = null;
		Date displayDateLT = new Date();
		int status = WorkflowConstants.STATUS_APPROVED;
		Date reviewDate = null;

		OrderByComparator<JournalArticle> obc = new ArticleVersionComparator();

		int count = JournalArticleServiceUtil.searchCount(
			companyId, groupId, folderIds, classNameId, keywords, version,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			status, reviewDate);

		AtomPager atomPager = new AtomPager(atomRequestContext, count);

		AtomUtil.saveAtomPagerInRequest(atomRequestContext, atomPager);

		journalArticles = JournalArticleServiceUtil.search(
			companyId, groupId, folderIds, classNameId, keywords, version,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			status, reviewDate, atomPager.getStart(), atomPager.getEnd() + 1,
			obc);

		return journalArticles;
	}

	@Override
	protected JournalArticle doPostEntry(
			String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = atomRequestContext.getLongParameter("groupId");
		long folderId = 0;
		long classNameId = 0;
		long classPK = 0;
		String articleId = StringPool.BLANK;
		boolean autoArticleId = true;

		Locale locale = LocaleUtil.getDefault();

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(locale, title);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		String ddmStructureKey = null;
		String ddmTemplateKey = null;
		String layoutUuid = null;

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		int displayDateMonth = cal.get(Calendar.MONTH);
		int displayDateDay = cal.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = cal.get(Calendar.YEAR);
		int displayDateHour = cal.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = cal.get(Calendar.MINUTE);

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		boolean neverExpire = true;
		int reviewDateMonth = 0;
		int reviewDateDay = 0;
		int reviewDateYear = 0;
		int reviewDateHour = 0;
		int reviewDateMinute = 0;
		boolean neverReview = true;
		boolean indexable = true;
		String articleURL = StringPool.BLANK;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);
		serviceContext.setScopeGroupId(groupId);

		JournalArticle journalArticle = JournalArticleServiceUtil.addArticle(
			groupId, folderId, classNameId, classPK, articleId, autoArticleId,
			titleMap, descriptionMap, content, ddmStructureKey, ddmTemplateKey,
			layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay,
			reviewDateYear, reviewDateHour, reviewDateMinute, neverReview,
			indexable, articleURL, serviceContext);

		double version = journalArticle.getVersion();
		int status = WorkflowConstants.STATUS_APPROVED;

		journalArticle = JournalArticleServiceUtil.updateStatus(
			groupId, journalArticle.getArticleId(), version, status, articleURL,
			serviceContext);

		return journalArticle;
	}

	@Override
	protected void doPutEntry(
			JournalArticle journalArticle, String title, String summary,
			String content, Date date, AtomRequestContext atomRequestContext)
		throws Exception {

		long groupId = journalArticle.getGroupId();
		long folderId = journalArticle.getFolderId();
		String articleId = journalArticle.getArticleId();
		double version = journalArticle.getVersion();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		journalArticle = JournalArticleServiceUtil.updateArticle(
			groupId, folderId, articleId, version, content, serviceContext);

		int status = WorkflowConstants.STATUS_APPROVED;
		String articleURL = StringPool.BLANK;

		JournalArticleServiceUtil.updateStatus(
			groupId, journalArticle.getArticleId(), journalArticle.getVersion(),
			status, articleURL, serviceContext);
	}

	private static final String _COLLECTION_NAME = "web-content";

}