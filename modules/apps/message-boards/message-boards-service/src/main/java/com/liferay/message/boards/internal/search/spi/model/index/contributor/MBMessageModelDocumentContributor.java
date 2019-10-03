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

package com.liferay.message.boards.internal.search.spi.model.index.contributor;

import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.message.boards.model.MBMessage",
	service = ModelDocumentContributor.class
)
public class MBMessageModelDocumentContributor
	implements ModelDocumentContributor<MBMessage> {

	@Override
	public void contribute(Document document, MBMessage mbMessage) {
		document.addKeyword(Field.CATEGORY_ID, mbMessage.getCategoryId());

		for (Locale locale :
				LanguageUtil.getAvailableLocales(mbMessage.getGroupId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addText(
				LocalizationUtil.getLocalizedName(Field.CONTENT, languageId),
				processContent(mbMessage));
			document.addText(
				LocalizationUtil.getLocalizedName(Field.TITLE, languageId),
				mbMessage.getSubject());
		}

		document.addKeyword(
			Field.ROOT_ENTRY_CLASS_PK, mbMessage.getRootMessageId());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(mbMessage.getTreePath(), CharPool.SLASH));

		if (mbMessage.isAnonymous()) {
			document.remove(Field.USER_NAME);
		}

		document.addKeywordSortable("answer", mbMessage.isAnswer());

		MBDiscussion discussion =
			mbDiscussionLocalService.fetchThreadDiscussion(
				mbMessage.getThreadId());

		if (discussion == null) {
			document.addKeyword("discussion", false);
		}
		else {
			document.addKeyword("discussion", true);
		}

		document.addKeyword("parentMessageId", mbMessage.getParentMessageId());

		if (mbMessage.getMessageId() == mbMessage.getRootMessageId()) {
			MBThread mbThread = mbThreadLocalService.fetchMBThread(
				mbMessage.getThreadId());

			document.addKeyword("question", mbThread.isQuestion());
		}

		document.addKeyword("threadId", mbMessage.getThreadId());

		if (mbMessage.isDiscussion()) {
			List<RelatedEntryIndexer> relatedEntryIndexers =
				RelatedEntryIndexerRegistryUtil.getRelatedEntryIndexers(
					mbMessage.getClassName());

			if (relatedEntryIndexers != null) {
				for (RelatedEntryIndexer relatedEntryIndexer :
						relatedEntryIndexers) {

					Comment comment = commentManager.fetchComment(
						mbMessage.getMessageId());

					if (comment != null) {
						try {
							relatedEntryIndexer.addRelatedEntryFields(
								document, comment);
						}
						catch (Exception e) {
							throw new SystemException(e);
						}

						document.addKeyword(Field.RELATED_ENTRY, true);
					}
				}
			}
		}
	}

	protected String processContent(MBMessage message) {
		String content = message.getBody();

		try {
			if (message.isFormatBBCode()) {
				content = BBCodeTranslatorUtil.getHTML(content);
			}
		}
		catch (Exception e) {
			_log.error(
				StringBundler.concat(
					"Unable to parse message ", message.getMessageId(), ": ",
					e.getMessage()),
				e);
		}

		content = HtmlUtil.extractText(content);

		return content;
	}

	@Reference
	protected CommentManager commentManager;

	@Reference
	protected MBDiscussionLocalService mbDiscussionLocalService;

	@Reference
	protected MBThreadLocalService mbThreadLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		MBMessageModelDocumentContributor.class);

}