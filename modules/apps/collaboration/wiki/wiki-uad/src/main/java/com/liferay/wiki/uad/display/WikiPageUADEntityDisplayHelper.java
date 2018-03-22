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

import com.liferay.wiki.model.WikiPage;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = WikiPageUADEntityDisplayHelper.class)
public class WikiPageUADEntityDisplayHelper {
	/**
	 * Returns an ordered string array of the fields' names to be displayed.
	 * Each field name corresponds to a table column based on the order they are
	 * specified.
	 *
	 * @return the array of field names to display
	 */
	public String[] getDisplayFieldNames() {
		return new String[] { "title", "content", "summary" };
	}

	/**
	 * Implement getWikiPageEditURL() to enable editing WikiPages from the GDPR UI.
	 *
	 * <p>
	 * Editing WikiPages in the GDPR UI depends on generating valid edit URLs. Implement getWikiPageEditURL() such that it returns a valid edit URL for the specified WikiPage.
	 * </p>
	 *
	 */
	public String getWikiPageEditURL(WikiPage wikiPage,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {
		return "";
	}

	@Override
	public Map<String, Object> getUADEntityNonanonymizableFieldValues(
		WikiPage wikiPage) {
		Map<String, Object> uadEntityNonanonymizableFieldValues = new HashMap<String, Object>();

		uadEntityNonanonymizableFieldValues.put("title", wikiPage.getTitle());
		uadEntityNonanonymizableFieldValues.put("content", wikiPage.getContent());
		uadEntityNonanonymizableFieldValues.put("summary", wikiPage.getSummary());

		return uadEntityNonanonymizableFieldValues;
	}
}