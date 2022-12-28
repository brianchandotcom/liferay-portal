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

package com.liferay.journal.internal.transformer;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.BaseTransformerListener;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	property = "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
	service = TransformerListener.class
)
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

		return StringUtil.replace(
			s, _COUNTER_TOKEN,
			StringBundler.concat(
				"<script type=\"text/javascript\">",
				"Liferay.Service('/assetentry/increment-view-counter',",
				"{userId:0, className:'",
				"com.liferay.journal.model.JournalArticle', classPK:",
				articleResourcePK, "});</script>"));
	}

	private static final String _COUNTER_TOKEN = "@view_counter@";

	private static final Log _log = LogFactoryUtil.getLog(
		ViewCounterTransformerListener.class);

}