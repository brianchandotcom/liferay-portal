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

package com.liferay.polls.internal.search;

import com.liferay.petra.string.StringPool;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lino Alves
 */
@Component(immediate = true, service = Indexer.class)
public class PollsQuestionIndexer extends BaseIndexer<PollsQuestion> {

	public static final String CLASS_NAME = PollsQuestion.class.getName();

	public PollsQuestionIndexer() {
		setDefaultSelectedFieldNames(
			Field.ASSET_TAG_NAMES, Field.CREATE_DATE, Field.COMPANY_ID,
			Field.DESCRIPTION, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID);
		setFilterSearch(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	protected void doDelete(PollsQuestion pollsQuestion) throws Exception {
		deleteDocument(
			pollsQuestion.getCompanyId(), pollsQuestion.getQuestionId());
	}

	@Override
	protected Document doGetDocument(PollsQuestion pollsQuestion)
		throws Exception {

		Document document = getBaseModelDocument(CLASS_NAME, pollsQuestion);

		document.addDateSortable(
			Field.CREATE_DATE, pollsQuestion.getCreateDate());
		document.addText(Field.DESCRIPTION, getDescriptionField(pollsQuestion));
		document.addText(Field.TITLE, getTitleField(pollsQuestion));

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		Summary summary = createSummary(document);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(PollsQuestion pollsQuestion) throws Exception {
		Document document = getDocument(pollsQuestion);

		indexWriterHelper.updateDocument(
			getSearchEngineId(), pollsQuestion.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		PollsQuestion pollsQuestion =
			pollsQuestionLocalService.getPollsQuestion(classPK);

		doReindex(pollsQuestion);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);
	}

	protected String getDescriptionField(PollsQuestion pollsQuestion) {
		String[] availableLanguageIds = pollsQuestion.getAvailableLanguageIds();

		StringBundler sb = new StringBundler();

		for (String languageId : availableLanguageIds) {
			sb.append(pollsQuestion.getDescription(languageId));
			sb.append(StringPool.SPACE);
		}

		return sb.toString();
	}

	protected String getTitleField(PollsQuestion pollsQuestion) {
		String[] availableLanguageIds = pollsQuestion.getAvailableLanguageIds();

		StringBundler sb = new StringBundler();

		for (String languageId : availableLanguageIds) {
			sb.append(pollsQuestion.getTitle(languageId));
			sb.append(StringPool.SPACE);
		}

		return sb.toString();
	}

	protected void reindexEntries(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			pollsQuestionLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<PollsQuestion>() {

				@Override
				public void performAction(PollsQuestion pollsQuestion) {
					try {
						Document document = getDocument(pollsQuestion);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index Polls Question " +
									pollsQuestion.getQuestionId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	@Reference
	protected IndexWriterHelper indexWriterHelper;

	@Reference
	protected PollsQuestionLocalService pollsQuestionLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		PollsQuestionIndexer.class);

}