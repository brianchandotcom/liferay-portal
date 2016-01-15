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

package com.liferay.wiki.web.display.context;

import com.liferay.wiki.display.context.WikiInfoPanelDisplayContext;
import com.liferay.wiki.web.display.context.util.WikiInfoPanelRequestHelper;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class DefaultWikiInfoPanelDisplayContext
	implements WikiInfoPanelDisplayContext {

	public DefaultWikiInfoPanelDisplayContext(
		HttpServletRequest request, HttpServletResponse response) {

		_wikiInfoPanelRequestHelper = new WikiInfoPanelRequestHelper(request);
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	private static final UUID _UUID = UUID.fromString(
		"7099F1F8-ED73-47D8-9CDC-ED292BF7779F");

	private final WikiInfoPanelRequestHelper _wikiInfoPanelRequestHelper;

}