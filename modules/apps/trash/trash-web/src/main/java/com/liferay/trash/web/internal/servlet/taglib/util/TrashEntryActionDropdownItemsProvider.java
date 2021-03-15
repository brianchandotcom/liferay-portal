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

package com.liferay.trash.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.model.TrashEntry;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class TrashEntryActionDropdownItemsProvider {

	public TrashEntryActionDropdownItemsProvider(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, TrashEntry trashEntry) {

		_liferayPortletResponse = liferayPortletResponse;
		_trashEntry = trashEntry;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			trashEntry.getClassName());
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (_trashHandler.isRestorable(_trashEntry.getClassPK()) &&
					!_trashHandler.isInTrashContainer(
						_trashEntry.getClassPK())) {

					add(_getRestoreActionDropdownItem());
				}
				else if (!_trashHandler.isRestorable(
							_trashEntry.getClassPK()) &&
						 _trashHandler.isMovable(_trashEntry.getClassPK())) {

					add(_getMoveActionDropdownItem());
				}

				if (_trashHandler.isDeletable(_trashEntry.getClassPK())) {
					add(_getDeleteActionDropdownItem());
				}
			}
		};
	}

	private DropdownItem _getDeleteActionDropdownItem() {
		return new DropdownItem() {
			{
				putData("action", "deleteEntry");

				putData(
					"deleteEntryURL",
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"deleteEntries"
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).setParameter(
						"trashEntryId", _trashEntry.getEntryId()
					).buildString());

				setLabel(LanguageUtil.get(_themeDisplay.getLocale(), "delete"));
			}
		};
	}

	private DropdownItem _getMoveActionDropdownItem() throws Exception {
		return new DropdownItem() {
			{
				putData("action", "moveEntry");

				putData(
					"moveEntryURL",
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/view_container_model.jsp"
					).setParameter(
						"classNameId", _trashEntry.getClassNameId()
					).setParameter(
						"classPK", _trashEntry.getClassPK()
					).setParameter(
						"containerModelClassNameId",
						PortalUtil.getClassNameId(
							_trashHandler.getContainerModelClassName(
								_trashEntry.getClassPK()))
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildString());

				setLabel(
					LanguageUtil.get(_themeDisplay.getLocale(), "restore"));
			}
		};
	}

	private DropdownItem _getRestoreActionDropdownItem() {
		return new DropdownItem() {
			{
				putData("action", "restoreEntry");

				putData(
					"restoreEntryURL",
					PortletURLBuilder.createActionURL(
						_liferayPortletResponse
					).setActionName(
						"restoreEntries"
					).setRedirect(
						_themeDisplay.getURLCurrent()
					).setParameter(
						"trashEntryId", _trashEntry.getEntryId()
					).buildString());

				setLabel(
					LanguageUtil.get(_themeDisplay.getLocale(), "restore"));
			}
		};
	}

	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;
	private final TrashEntry _trashEntry;
	private final TrashHandler _trashHandler;

}