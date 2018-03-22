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

import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

import com.liferay.wiki.uad.constants.WikiUADConstants;
import com.liferay.wiki.uad.entity.WikiPageUADEntity;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + WikiUADConstants.CLASS_NAME_WIKI_PAGE}, service = UADEntityDisplay.class)
public class WikiPageUADEntityDisplay implements UADEntityDisplay {
	public String getApplicationName() {
		return WikiUADConstants.UAD_ENTITY_SET_NAME;
	}

	public String[] getDisplayFieldNames() {
		return _wikiPageUADEntityDisplayHelper.getDisplayFieldNames();
	}

	@Override
	public String getEditURL(UADEntity uadEntity,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse)
		throws Exception {
		WikiPageUADEntity wikiPageUADEntity = (WikiPageUADEntity)uadEntity;

		return _wikiPageUADEntityDisplayHelper.getWikiPageEditURL(wikiPageUADEntity.getWikiPage(),
			liferayPortletRequest, liferayPortletResponse);
	}

	public String getKey() {
		return WikiUADConstants.CLASS_NAME_WIKI_PAGE;
	}

	@Override
	public Map<String, Object> getUADEntityNonanonymizableFieldValues(
		UADEntity uadEntity) {
		WikiPageUADEntity wikiPageUADEntity = (WikiPageUADEntity)uadEntity;

		return _wikiPageUADEntityDisplayHelper.getUADEntityNonanonymizableFieldValues(wikiPageUADEntity.getWikiPage());
	}

	@Override
	public String getUADEntityTypeDescription() {
		return "A wiki page";
	}

	@Override
	public String getUADEntityTypeName() {
		return "WikiPage";
	}

	@Reference
	private WikiPageUADEntityDisplayHelper _wikiPageUADEntityDisplayHelper;
	@Reference(target = "(model.class.name=" +
	WikiUADConstants.CLASS_NAME_WIKI_PAGE + ")")
	private UADEntityAnonymizer _uadEntityAnonymizer;
}