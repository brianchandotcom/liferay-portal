/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexWebKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordIndexReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public abstract class KeywordEntryDisplayBuilder {

	public KeywordEntryDisplayBuilder(
		HttpServletRequest httpServletRequest, Language language, Portal portal,
		RenderRequest renderRequest, RenderResponse renderResponse,
		KeywordIndexNameBuilder keywordIndexNameBuilder,
		KeywordIndexReader keywordIndexReader) {

		this.httpServletRequest = httpServletRequest;
		this.language = language;
		this.portal = portal;
		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;
		this.keywordIndexNameBuilder = keywordIndexNameBuilder;
		this.keywordIndexReader = keywordIndexReader;

		keywordEntryId = ParamUtil.getString(
			renderRequest, KeywordIndexWebKeys.KEYWORD_ENTRY_ID);

		keywordEntryOptional = getKeywordEntryOptional();

		themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	protected long getCompanyId() {
		return portal.getCompanyId(renderRequest);
	}

	protected String getContent() {
		return keywordEntryOptional.map(
			KeywordEntry::getContent
		).orElse(
			StringPool.BLANK
		);
	}

	protected Map<String, Object> getContext() {
		return HashMapBuilder.<String, Object>put(
			"locale", themeDisplay.getLanguageId()
		).put(
			"namespace", renderResponse.getNamespace()
		).build();
	}

	protected String getCreated() {
		return keywordEntryOptional.map(
			keywordEntry -> keywordEntry.getCreated(
			).toString()
		).orElse(
			StringPool.BLANK
		);
	}

	protected Long getGroupId() {
		return keywordEntryOptional.map(
			KeywordEntry::getGroupId
		).orElse(
			null
		);
	}

	protected Long getHitCount() {
		return keywordEntryOptional.map(
			KeywordEntry::getHitCount
		).orElse(
			0L
		);
	}

	protected Optional<KeywordEntry> getKeywordEntryOptional() {
		return keywordIndexReader.fetchKeywordEntryOptional(
			keywordIndexNameBuilder.getKeywordIndexName(getCompanyId()),
			keywordEntryId);
	}

	protected String getLanguageId() {
		return keywordEntryOptional.map(
			KeywordEntry::getLanguageId
		).orElse(
			StringPool.BLANK
		);
	}

	protected Map<String, Object> getProps() {
		return HashMapBuilder.<String, Object>put(
			"content", getContent()
		).put(
			"created", getCreated()
		).put(
			"groupId", getGroupId()
		).put(
			"hitCount", getHitCount()
		).put(
			"keywordEntry", keywordEntryId
		).put(
			"languageId", getLanguageId()
		).put(
			"redirectURL", getRedirect()
		).put(
			"reportCount", getReportCount()
		).put(
			"reports", getReports()
		).put(
			"status", getStatus()
		).put(
			"statusDate", getStatusDate()
		).build();
	}

	protected String getRedirect() {
		return ParamUtil.getString(httpServletRequest, "redirect");
	}

	protected Long getReportCount() {
		return keywordEntryOptional.map(
			KeywordEntry::getReportCount
		).orElse(
			0L
		);
	}

	protected Map<String, String> getReports() {
		Map<String, String> reportsMap = new HashMap<>();

		if (keywordEntryOptional.isPresent()) {
			return reportsMap;
		}

		KeywordEntry keywordEntry = keywordEntryOptional.get();

		List<String> reports = keywordEntry.getReports();

		Stream<String> stream = reports.stream();

		stream.forEach(
			s -> {
				try {
					String[] arr = s.split("-");

					reportsMap.put(arr[0], arr[1]);
				}
				catch (Exception exception) {
					_log.error(exception.getMessage(), exception);
				}
			});

		return reportsMap;
	}

	protected String getStatus() {
		return keywordEntryOptional.map(
			keywordEntry -> keywordEntry.getStatus(
			).name()
		).orElse(
			StringPool.BLANK
		);
	}

	protected String getStatusDate() {
		return keywordEntryOptional.map(
			keywordEntry -> keywordEntry.getStatusDate(
			).toString()
		).orElse(
			StringPool.BLANK
		);
	}

	protected void setData(
		KeywordEntryDisplayContext keywordEntryDisplayContext) {

		keywordEntryDisplayContext.setData(
			HashMapBuilder.<String, Object>put(
				"context", getContext()
			).put(
				"props", getProps()
			).build());
	}

	protected void setKeywordEntryId(
		KeywordEntryDisplayContext keywordEntryDisplayContext) {

		keywordEntryOptional.ifPresent(
			keywordEntry -> keywordEntryDisplayContext.setKeywordEntryId(
				keywordEntry.getKeywordEntryId()));
	}

	protected void setRedirect(
		KeywordEntryDisplayContext keywordEntryDisplayContext) {

		keywordEntryDisplayContext.setRedirect(getRedirect());
	}

	protected final HttpServletRequest httpServletRequest;
	protected final String keywordEntryId;
	protected final Optional<KeywordEntry> keywordEntryOptional;
	protected final KeywordIndexNameBuilder keywordIndexNameBuilder;
	protected final KeywordIndexReader keywordIndexReader;
	protected final Language language;
	protected final Portal portal;
	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;
	protected final ThemeDisplay themeDisplay;

	private static final Log _log = LogFactoryUtil.getLog(
		KeywordEntryDisplayBuilder.class);

}