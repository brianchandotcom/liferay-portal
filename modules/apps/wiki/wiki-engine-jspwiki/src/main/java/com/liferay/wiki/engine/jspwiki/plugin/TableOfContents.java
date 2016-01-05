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

package com.liferay.wiki.engine.jspwiki.plugin;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.parser.Heading;
import com.ecyrd.jspwiki.plugin.PluginException;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Map;

/**
 * <p>
 * This is a modification of JSPWiki's core TableOfContents plugin for use
 * within Liferay. This plugin modifies the original behavior by producing
 * ordered lists and making contents collapsable.
 * </p>
 *
 * @author Alexander Chow
 * @author Jorge Ferrer
 */
public class TableOfContents extends com.ecyrd.jspwiki.plugin.TableOfContents {

	@Override
	@SuppressWarnings("rawtypes")
	public String execute(WikiContext context, Map params)
		throws PluginException {

		if (!params.containsKey(PARAM_NUMBERED)) {
			params.put(PARAM_NUMBERED, Boolean.TRUE.toString());
		}

		String result = super.execute(context, params);

		if (_count < 2) {
			return StringPool.BLANK;
		}

		int x = result.indexOf("<div class=\"collapsebox\">\n");

		x = result.indexOf("</h4>", x);

		int y = x + "</h4>".length();

		if ((x != -1) && (y != -1)) {
			StringBundler sb = new StringBundler(7);

			sb.append(result.substring(0, x));
			sb.append(StringPool.NBSP);
			sb.append("<a class=\"toc-trigger\" href=\"javascript:;\">[-]</a>");
			sb.append("</h4>");

			sb.append("<div class=\"toc-index\">\n");
			sb.append(result.substring(y));
			sb.append("</div>\n");

			result = sb.toString();
		}

		return result;
	}

	@Override
	public void headingAdded(WikiContext context, Heading heading) {
		super.headingAdded(context, heading);

		_count++;
	}

	private int _count;

}