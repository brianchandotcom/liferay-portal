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

import com.ecyrd.jspwiki.WikiEngine;
import com.ecyrd.jspwiki.WikiException;
import com.ecyrd.jspwiki.WikiPage;

import java.util.Collection;
import java.util.Properties;

import javax.servlet.ServletContext;

/**
 * @author Jorge Ferrer
 */
public class LiferayJSPWikiEngine extends WikiEngine {

	public LiferayJSPWikiEngine(Properties properties) throws WikiException {
		super(properties);
	}

	public LiferayJSPWikiEngine(
			ServletContext context, String appId, Properties props)
		throws WikiException {

		super(context, appId, props);
	}

	@Override
	public Collection<String> scanWikiLinks(WikiPage page, String pageData) {
		return super.scanWikiLinks(page, pageData);
	}

}