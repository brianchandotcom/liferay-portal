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

package com.liferay.portlet.wiki.engines.jspwiki.filters;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.WikiEngine;
import com.ecyrd.jspwiki.filters.FilterException;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 * @author Samuel Liu
 */
public class CreoleFilter extends com.ecyrd.jspwiki.filters.CreoleFilter {

	@Override
	public void destroy(WikiEngine wikiEngine) {
		super.destroy(wikiEngine);
	}

	@Override
	public void initialize(WikiEngine wikiEngine, Properties properties) {
		if (_log.isDebugEnabled()) {
			_log.debug("Initializing");
		}
	}

	@Override
	public void postSave(WikiContext wikiContext, String content)
		throws FilterException {

		super.postSave(wikiContext, content);
	}

	@Override
	public String postTranslate(WikiContext wikiContext, String htmlContent)
		throws FilterException {

		return super.postTranslate(wikiContext, htmlContent);
	}

	@Override
	public String preSave(WikiContext wikiContext, String content)
		throws FilterException {

		return super.preSave(wikiContext, content);
	}

	@Override
	public String preTranslate(WikiContext wikiContext, String content)
		throws FilterException {

		return super.preTranslate(wikiContext, content);
	}

	private static final Log _log = LogFactoryUtil.getLog(CreoleFilter.class);

}