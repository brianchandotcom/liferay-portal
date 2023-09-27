/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.engine.html.internal.processor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.wiki.processor.BaseWikiPageRenameContentProcessor;
import com.liferay.wiki.processor.WikiPageRenameContentProcessor;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 * @author Daniel Sanz
 */
@Component(
	property = "wiki.format.name=html",
	service = WikiPageRenameContentProcessor.class
)
public class WikiPageRenameHTMLContentProcessor
	extends BaseWikiPageRenameContentProcessor {

	@Activate
	protected void activate() {
		regexps.put(
			"(<img [^s]*src=\"[^g]+get_page_attachment\\?[^t]+title=)" +
				"@old_title@&",
			"$1@new_title@&");
		regexps.put(
			"(<a [^h]*href=\"[^g]+get_page_attachment\\?[^t]+title=)" +
				"@old_title@&",
			"$1@new_title@&");
	}

	@Override
	protected String runRegexps(String content, String title, String newTitle) {
		try {
			return super.runRegexps(
				content, URLEncoder.encode(title, StringPool.UTF8),
				URLEncoder.encode(newTitle, StringPool.UTF8));
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			if (_log.isWarnEnabled()) {
				_log.warn(unsupportedEncodingException);
			}
		}

		return content;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageRenameHTMLContentProcessor.class);

}