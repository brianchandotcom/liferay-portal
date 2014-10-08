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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.BaseTransformerListener;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public class ViewCounterTransformerListener extends BaseTransformerListener {

	@Override
	public String onOutput(
		String output, String languageId, Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onOutput");
		}

		return replace(output, tokens);
	}

	/**
	 * Replace the counter token with the increment call.
	 *
	 * @return the processed string
	 */
	protected String replace(String s, Map<String, String> tokens) {
		if (!s.contains(_COUNTER_TOKEN)) {
			return s;
		}

		String articleResourcePK = tokens.get("article_resource_pk");

		StringBundler sb = new StringBundler(8);

		sb.append("<script type=\"text/javascript\">");
		sb.append("Liferay.Service.Asset.AssetEntry.incrementViewCounter");
		sb.append("({userId:0, className:'");
		sb.append("com.liferay.portlet.journal.model.JournalArticle', ");
		sb.append("classPK:");
		sb.append(articleResourcePK);
		sb.append("});");
		sb.append("</script>");

		s = StringUtil.replace(s, _COUNTER_TOKEN, sb.toString());

		return s;
	}

	private static final String _COUNTER_TOKEN =
		StringPool.AT + "view_counter" + StringPool.AT;

	private static final Log _log = LogFactoryUtil.getLog(
		ViewCounterTransformerListener.class);

}