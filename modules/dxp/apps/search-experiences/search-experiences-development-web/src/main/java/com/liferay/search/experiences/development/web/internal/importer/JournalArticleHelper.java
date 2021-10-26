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

package com.liferay.search.experiences.development.web.internal.importer;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;

/**
 * @author Petteri Karttunen
 * @author André de Oliveira
 */
public class JournalArticleHelper {

	public JournalArticleHelper(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	public JournalArticle addJournalArticle(
		PortletRequest portletRequest, long userId, long groupId,
		String languageId, String title, String content,
		String[] assetTagNames) {

		if (_log.isInfoEnabled()) {
			_log.info("Adding journal article " + title);
		}

		ServiceContext serviceContext = _getServiceContext(portletRequest);

		serviceContext.setAssetTagNames(_cleanAssetTagNames(assetTagNames));

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		try {
			return _journalArticleLocalService.addArticle(
				userId, groupId, 0,
				HashMapBuilder.put(
					locale, title
				).build(),
				HashMapBuilder.put(
					locale, StringUtil.shorten(HtmlUtil.stripHtml(content), 500)
				).build(),
				_createArticleXML(content, languageId), "BASIC-WEB-CONTENT",
				"BASIC-WEB-CONTENT", serviceContext);
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	public void updateJournalArticle(JournalArticle journalArticle) {
		_journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	private String[] _cleanAssetTagNames(String[] assetTagNames) {
		return Stream.of(
			assetTagNames
		).map(
			assetTagName -> StringUtil.removeChars(
				assetTagName, _INVALID_CHARACTERS)
		).map(
			assetTagName -> StringUtil.replace(
				assetTagName, CharPool.UNDERLINE, CharPool.SPACE)
		).toArray(
			String[]::new
		);
	}

	private String _createArticleXML(String content, String languageId) {
		StringBundler sb = new StringBundler(13);

		sb.append("<root available-locales=\"en_US\" default-locale=\"");
		sb.append(languageId);
		sb.append("\">");
		sb.append("<dynamic-element name=\"content\" type=\"text_area\" ");
		sb.append("index-type=\"text\" instance-id=\"");
		sb.append(_generateInstanceId());
		sb.append("\">");
		sb.append("<dynamic-content language-id=\"");
		sb.append(languageId);
		sb.append("\"><![CDATA[");
		sb.append(
			StringUtil.shorten(
				content, _ELASTICSEARCH_FIELD_SIZE_LIMIT, "</html>"));
		sb.append("]]></dynamic-content></dynamic-element>");
		sb.append("</root>");

		return sb.toString();
	}

	private String _generateInstanceId() {
		StringBuilder instanceId = new StringBuilder(8);

		String key = PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3;

		for (int i = 0; i < 8; i++) {
			int pos = (int)Math.floor(Math.random() * key.length());

			instanceId.append(key.charAt(pos));
		}

		return instanceId.toString();
	}

	private ServiceContext _getServiceContext(PortletRequest portletRequest) {
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				JournalArticle.class.getName(), portletRequest);

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			return serviceContext;
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private static final int _ELASTICSEARCH_FIELD_SIZE_LIMIT = 32000;

	private static final char[] _INVALID_CHARACTERS = {
		CharPool.AMPERSAND, CharPool.APOSTROPHE, CharPool.AT,
		CharPool.BACK_SLASH, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.COLON, CharPool.COMMA, CharPool.EQUAL, CharPool.GREATER_THAN,
		CharPool.FORWARD_SLASH, CharPool.LESS_THAN, CharPool.NEW_LINE,
		CharPool.OPEN_BRACKET, CharPool.OPEN_CURLY_BRACE, CharPool.PERCENT,
		CharPool.PIPE, CharPool.PLUS, CharPool.POUND, CharPool.PRIME,
		CharPool.QUESTION, CharPool.QUOTE, CharPool.RETURN, CharPool.SEMICOLON,
		CharPool.SLASH, CharPool.STAR, CharPool.TILDE
	};

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleHelper.class);

	private final JournalArticleLocalService _journalArticleLocalService;

}