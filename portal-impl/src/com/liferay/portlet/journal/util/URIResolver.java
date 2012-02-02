/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;

import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Brian Wing Shun Chan
 */
public class URIResolver implements javax.xml.transform.URIResolver {

	public URIResolver(Map<String, String> tokens, String languageId) {
		_tokens = tokens;
		_languageId = languageId;
	}

	public Source resolve(String href, String base) {
		try {
			String content = null;

			int templatePathIndex = href.indexOf(_GET_TEMPLATE_PATH);

			if (templatePathIndex >= 0) {
				int templateIdIndex =
					templatePathIndex + _GET_TEMPLATE_PATH.length();

				long groupId = GetterUtil.getLong(_tokens.get("group_id"));
				String templateId = href.substring(
					templateIdIndex, href.length());

				content = JournalUtil.getTemplateScript(
					groupId, templateId, _tokens, _languageId);
			}
			else {
				content = HttpUtil.URLtoString(href);
			}

			return new StreamSource(new UnsyncStringReader(content));
		}
		catch (Exception e) {
			_log.error(href + " does not reference a valid template");

			return null;
		}
	}

	private static final String _GET_TEMPLATE_PATH =
		"/c/journal/get_template?template_id=";

	private static Log _log = LogFactoryUtil.getLog(URIResolver.class);

	private String _languageId;
	private Map<String, String> _tokens;

}