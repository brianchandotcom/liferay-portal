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

package com.liferay.wiki.engine;

import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.exception.WikiFormatException;
import com.liferay.wiki.model.WikiPage;

import java.util.Collection;

import javax.portlet.PortletURL;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Preston Crary
 */
@ProviderType
public interface WikiEngineRenderer {

	public String convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException, WikiFormatException;

	public String diffHtml(
			WikiPage sourcePage, WikiPage targetPage, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws Exception;

	public WikiEngine fetchWikiEngine(String format);

	public Collection<String> getFormats();

}