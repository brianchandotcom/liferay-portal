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

package com.liferay.polls.internal.search.internal.search.spi.model.index.contributor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luan Maoski
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.polls.model.PollsQuestion",
	service = ModelDocumentContributor.class
)
public class PollsQuestionModelDocumentContributor
	implements ModelDocumentContributor<PollsQuestion> {

	@Override
	public void contribute(Document document, PollsQuestion pollsQuestion) {
		document.addDateSortable(
			Field.CREATE_DATE, pollsQuestion.getCreateDate());
		document.addText(Field.DESCRIPTION, getDescriptionField(pollsQuestion));
		document.addText(Field.TITLE, getTitleField(pollsQuestion));
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

}