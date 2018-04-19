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

package com.liferay.wiki.uad.display;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import com.liferay.user.associated.data.display.UADDisplay;

import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.uad.constants.WikiUADConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + WikiUADConstants.CLASS_NAME_WIKI_NODE}, service = UADDisplay.class)
public class WikiNodeUADDisplay implements UADDisplay<WikiNode> {
	public String getApplicationName() {
		return WikiUADConstants.APPLICATION_NAME;
	}

	public String[] getDisplayFieldNames() {
		return _wikiNodeUADDisplayHelper.getDisplayFieldNames();
	}

	@Override
	public String getEditURL(WikiNode wikiNode,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse)
		throws Exception {
		return _wikiNodeUADDisplayHelper.getWikiNodeEditURL(wikiNode,
			liferayPortletRequest, liferayPortletResponse);
	}

	public String getKey() {
		return WikiUADConstants.CLASS_NAME_WIKI_NODE;
	}

	@Override
	public Map<String, Object> getNonanonymizableFieldValues(WikiNode wikiNode) {
		return _wikiNodeUADDisplayHelper.getUADEntityNonanonymizableFieldValues(wikiNode);
	}

	@Override
	public String getTypeDescription() {
		return "A wiki node";
	}

	@Override
	public String getTypeName() {
		return "WikiNode";
	}

	@Reference
	private WikiNodeUADDisplayHelper _wikiNodeUADDisplayHelper;
}