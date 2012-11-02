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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public class ViewCounterTransformerListener implements TransformerListener {

	public String onOutput(
		String output, String languageId, Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onOutput");
		}

		return replace(output, tokens);
	}

	public String onScript(
		String script, String xml, String languageId,
		Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onScript");
		}

		return script;
	}

	public String onXml(
		String xml, String languageId, Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onXml");
		}

		return xml;
	}

	/**
	 * Replace the counter token with the increment call.
	 *
	 * @return the processed string
	 */
	protected String replace(String s, Map<String, String> tokens) {
		String articleResourcePK = tokens.get("article_resource_pk");

		String counterToken = StringPool.AT + "view_counter" + StringPool.AT;

		StringBundler sb = new StringBundler(8);

		sb.append("<script type=\"text/javascript\">");
		sb.append("Liferay.Service.Asset.AssetEntry.incrementViewCounter");
		sb.append("({userId:0, className:'");
		sb.append("com.liferay.portlet.journal.model.JournalArticle', ");
		sb.append("classPK:");
		sb.append(articleResourcePK);
		sb.append("});");
		sb.append("</script>");

		s = StringUtil.replace(s, counterToken, sb.toString());

		return s;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ViewCounterTransformerListener.class);

}