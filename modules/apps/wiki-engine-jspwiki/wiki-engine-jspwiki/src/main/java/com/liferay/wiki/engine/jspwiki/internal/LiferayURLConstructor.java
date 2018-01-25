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

package com.liferay.wiki.engine.jspwiki.internal;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.WikiEngine;
import com.ecyrd.jspwiki.url.URLConstructor;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.escape.WikiEscapeUtil;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 */
public class LiferayURLConstructor implements URLConstructor {

	@Override
	public String getForwardPage(HttpServletRequest request) {
		return "Wiki.jsp";
	}

	@Override
	public void initialize(WikiEngine engine, Properties props) {
	}

	@Override
	public String makeURL(
		String context, String name, boolean absolute, String parameters) {

		if (Validator.isNotNull(parameters)) {
			if (context.equals(WikiContext.ATTACH)) {
				parameters = StringPool.QUESTION + parameters;
			}
			else if (context.equals(WikiContext.NONE)) {
				if (name.indexOf(CharPool.QUESTION) != -1) {
					parameters = "&amp;" + parameters;
				}
				else {
					parameters = StringPool.QUESTION + parameters;
				}
			}
			else {
				parameters = "&amp;" + parameters;
			}
		}
		else {
			parameters = StringPool.BLANK;
		}

		String path = null;

		if (context.equals(WikiContext.EDIT)) {
			path =
				"[$BEGIN_PAGE_TITLE_EDIT$]" +
					JSPWikiEngine.decodeJSPWikiName(name) +
						"[$END_PAGE_TITLE_EDIT$]";
		}
		else if (context.equals(WikiContext.VIEW)) {
			String escapedName = WikiEscapeUtil.escapeName(
				JSPWikiEngine.decodeJSPWikiName(name));

			path = "[$BEGIN_PAGE_TITLE$]" + escapedName + "[$END_PAGE_TITLE$]";
		}
		else if (context.equals(WikiContext.ATTACH)) {
			if (name.indexOf(CharPool.SLASH) == -1) {
				path =
					"[$ATTACHMENT_URL_PREFIX$][$WIKI_PAGE_NAME$]/" +
						URLCodec.encodeURL(
							JSPWikiEngine.decodeJSPWikiName(name));
			}
			else {
				path =
					"[$ATTACHMENT_URL_PREFIX$]" +
						URLCodec.encodeURL(
							JSPWikiEngine.decodeJSPWikiName(name));
			}
		}
		else {
			path = JSPWikiEngine.decodeJSPWikiName(name);
		}

		return path + parameters;
	}

	@Override
	public String parsePage(
		String context, HttpServletRequest request, String encoding) {

		return "Wiki.jsp";
	}

}