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

package com.liferay.wiki.engine.html.processor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.wiki.processor.BaseWikiPageRenameContentProcessor;
import com.liferay.wiki.processor.WikiPageRenameContentProcessor;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Roberto Díaz
 * @author Daniel Sanz
 */
@Component(
	immediate = true, property = "wiki.format.name=html",
	service = WikiPageRenameContentProcessor.class
)
public class WikiPageRenameHTMLContentProcessor
	extends BaseWikiPageRenameContentProcessor {

	@Activate
	@Modified
	public void activate() {
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
		catch (UnsupportedEncodingException uee) {
			if (_log.isWarnEnabled()) {
				_log.warn(uee, uee);
			}
		}

		return content;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageRenameHTMLContentProcessor.class);

}