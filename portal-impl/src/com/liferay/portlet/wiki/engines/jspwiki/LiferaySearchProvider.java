/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.engines.jspwiki;

import com.ecyrd.jspwiki.WikiEngine;
import com.ecyrd.jspwiki.WikiPage;
import com.ecyrd.jspwiki.search.SearchProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

/**
 * @author Jorge Ferrer
 */
public class LiferaySearchProvider implements SearchProvider {

	@Override
	public Collection<WikiPage> findPages(String query) {
		return Collections.emptyList();
	}

	@Override
	public String getProviderInfo() {
		return LiferaySearchProvider.class.getName();
	}

	@Override
	public void initialize(WikiEngine engine, Properties props) {
	}

	@Override
	public void pageRemoved(WikiPage page) {
	}

	@Override
	public void reindexPage(WikiPage page) {
	}

}