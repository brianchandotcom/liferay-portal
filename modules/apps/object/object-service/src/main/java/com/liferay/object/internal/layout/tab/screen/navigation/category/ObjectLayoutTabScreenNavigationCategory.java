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

package com.liferay.object.internal.layout.tab.screen.navigation.category;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.object.constants.ObjectWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectRelationshipLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Feliphe Marinho
 */
public class ObjectLayoutTabScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<ObjectLayoutTab> {

	public ObjectLayoutTabScreenNavigationCategory(
		ObjectDefinition objectDefinition, ObjectLayoutTab objectLayoutTab) {

		_objectDefinition = objectDefinition;
		_objectLayoutTab = objectLayoutTab;
	}

	@Override
	public String getCategoryKey() {
		return String.valueOf(_objectLayoutTab.getObjectLayoutTabId());
	}

	@Override
	public String getEntryKey() {
		return String.valueOf(_objectLayoutTab.getObjectLayoutTabId());
	}

	@Override
	public String getLabel(Locale locale) {
		return _objectLayoutTab.getName(locale);
	}

	@Override
	public String getScreenNavigationKey() {
		return _objectDefinition.getClassName();
	}

	@Override
	public boolean isVisible(User user, ObjectLayoutTab objectLayoutTab) {
		long objectRelationshipId = _objectLayoutTab.getObjectRelationshipId();

		if (objectRelationshipId == 0) {
			return true;
		}

		try {
			ObjectRelationship objectRelationship =
				ObjectRelationshipLocalServiceUtil.getObjectRelationship(
					objectRelationshipId);

			if (objectRelationship != null) {
				ObjectDefinition objectDefinition =
					ObjectDefinitionLocalServiceUtil.getObjectDefinition(
						objectRelationship.getObjectDefinitionId2());

				if (objectDefinition.isActive()) {
					return true;
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			ObjectWebKeys.REGULAR_OBJECT_LAYOUT_TAB, Boolean.TRUE);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectLayoutTabScreenNavigationCategory.class);

	private final ObjectDefinition _objectDefinition;
	private final ObjectLayoutTab _objectLayoutTab;

}