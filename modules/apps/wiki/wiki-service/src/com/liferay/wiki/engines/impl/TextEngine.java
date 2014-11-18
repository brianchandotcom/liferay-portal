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

package com.liferay.wiki.engines.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.wiki.engines.WikiEngine;
import com.liferay.wiki.model.WikiPage;

import java.util.Collections;
import java.util.Map;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(
	service = WikiEngine.class,
	property = {
		"enabled=false", "format=text",
		"edit.page=/html/portlet/wiki/edit/plain_text.jsp",
	}
)
public class TextEngine implements WikiEngine {

	@Override
	public String convert(
		WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
		String attachmentURLPrefix) {

		if (page.getContent() == null) {
			return StringPool.BLANK;
		}
		else {
			return "<pre>" + page.getContent() + "</pre>";
		}
	}

	@Override
	public Map<String, Boolean> getOutgoingLinks(WikiPage page) {
		return Collections.emptyMap();
	}

	@Override
	public boolean validate(long nodeId, String newContent) {
		return true;
	}

}