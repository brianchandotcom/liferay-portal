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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.web.internal.display.context.TrashDisplayContext;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class TrashContainerActionDropdownItemsProvider {

	public TrashContainerActionDropdownItemsProvider(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			TrashDisplayContext trashDisplayContext)
		throws PortalException {

		_liferayPortletResponse = liferayPortletResponse;
		_trashDisplayContext = trashDisplayContext;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		_trashEntry = trashDisplayContext.getTrashEntry();
		_trashHandler = trashDisplayContext.getTrashHandler();
		_trashRenderer = trashDisplayContext.getTrashRenderer();
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (_trashEntry != null) {
					if (_trashHandler.isRestorable(_trashEntry.getClassPK()) &&
						!_trashHandler.isInTrashContainer(
							_trashEntry.getClassPK())) {

						add(_getRestoreActionDropdownItem());
					}
					else if (!_trashHandler.isRestorable(
								_trashEntry.getClassPK()) &&
							 _trashHandler.isMovable(
								 _trashEntry.getClassPK())) {

						add(_getMoveTrashEntryActionDropdownItem());
					}

					if (_trashHandler.isDeletable(
							_trashRenderer.getClassPK())) {

						add(_getDeleteTrashEntryActionDropdownItem());
					}
				}
				else {
					if (_trashHandler.isMovable(_trashRenderer.getClassPK())) {
						add(_getMoveActionDropdownItem());
					}
					else if (_trashHandler.isDeletable(
								_trashRenderer.getClassPK())) {

						add(_getDeleteActionDropdownItem());
					}
				}
			}
		};
	}

	private DropdownItem _getDeleteActionDropdownItem() throws Exception {
		PortletURL deleteEntryURL = _liferayPortletResponse.createActionURL();

		deleteEntryURL.setParameter(ActionRequest.ACTION_NAME, "deleteEntries");
		deleteEntryURL.setParameter(
			"redirect", _trashDisplayContext.getViewContentRedirectURL());
		deleteEntryURL.setParameter("className", _trashRenderer.getClassName());
		deleteEntryURL.setParameter(
			"classPK", String.valueOf(_trashRenderer.getClassPK()));

		return new DropdownItem() {
			{
				putData("action", "deleteEntry");
				putData("deleteEntryURL", deleteEntryURL.toString());
				setLabel(LanguageUtil.get(_themeDisplay.getLocale(), "delete"));
			}
		};
	}

	private DropdownItem _getDeleteTrashEntryActionDropdownItem()
		throws Exception {

		PortletURL deleteEntryURL = _liferayPortletResponse.createActionURL();

		deleteEntryURL.setParameter(ActionRequest.ACTION_NAME, "deleteEntries");
		deleteEntryURL.setParameter(
			"redirect", _trashDisplayContext.getViewContentRedirectURL());
		deleteEntryURL.setParameter(
			"trashEntryId", String.valueOf(_trashEntry.getEntryId()));

		return new DropdownItem() {
			{
				putData("action", "deleteEntry");
				putData("deleteEntryURL", deleteEntryURL.toString());
				setLabel(LanguageUtil.get(_themeDisplay.getLocale(), "delete"));
			}
		};
	}

	private DropdownItem _getMoveActionDropdownItem() throws Exception {
		PortletURL moveEntryURL = _liferayPortletResponse.createRenderURL();

		moveEntryURL.setParameter("mvcPath", "/view_container_model.jsp");

		long trashRendererClassNameId = PortalUtil.getClassNameId(
			_trashRenderer.getClassName());

		moveEntryURL.setParameter(
			"classNameId", String.valueOf(trashRendererClassNameId));

		moveEntryURL.setParameter(
			"classPK", String.valueOf(_trashRenderer.getClassPK()));

		String containerModelClassName =
			_trashHandler.getContainerModelClassName(
				_trashDisplayContext.getClassPK());

		moveEntryURL.setParameter(
			"containerModelClassNameId",
			String.valueOf(PortalUtil.getClassNameId(containerModelClassName)));

		moveEntryURL.setWindowState(LiferayWindowState.POP_UP);

		return new DropdownItem() {
			{
				putData("action", "moveEntry");
				putData("moveEntryURL", moveEntryURL.toString());
				setLabel(
					LanguageUtil.get(_themeDisplay.getLocale(), "restore"));
			}
		};
	}

	private DropdownItem _getMoveTrashEntryActionDropdownItem()
		throws Exception {

		PortletURL moveEntryURL = _liferayPortletResponse.createRenderURL();

		moveEntryURL.setParameter("mvcPath", "/view_container_model.jsp");
		moveEntryURL.setParameter(
			"classNameId", String.valueOf(_trashEntry.getClassNameId()));
		moveEntryURL.setParameter(
			"classPK", String.valueOf(_trashEntry.getClassPK()));

		String trashHandlerEntryContainerModelClassName =
			_trashHandler.getContainerModelClassName(_trashEntry.getClassPK());

		moveEntryURL.setParameter(
			"containerModelClassNameId",
			String.valueOf(
				PortalUtil.getClassNameId(
					trashHandlerEntryContainerModelClassName)));

		moveEntryURL.setWindowState(LiferayWindowState.POP_UP);

		return new DropdownItem() {
			{
				putData("action", "moveEntry");
				putData("moveEntryURL", moveEntryURL.toString());
				setLabel(
					LanguageUtil.get(_themeDisplay.getLocale(), "restore"));
			}
		};
	}

	private DropdownItem _getRestoreActionDropdownItem() throws Exception {
		PortletURL restoreEntryURL = _liferayPortletResponse.createActionURL();

		restoreEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "restoreEntries");
		restoreEntryURL.setParameter(
			"redirect", _trashDisplayContext.getViewContentRedirectURL());
		restoreEntryURL.setParameter(
			"trashEntryId", String.valueOf(_trashEntry.getEntryId()));

		return new DropdownItem() {
			{
				putData("action", "restoreEntry");
				putData("restoreEntryURL", restoreEntryURL.toString());
				setLabel(
					LanguageUtil.get(_themeDisplay.getLocale(), "restore"));
			}
		};
	}

	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;
	private final TrashDisplayContext _trashDisplayContext;
	private final TrashEntry _trashEntry;
	private final TrashHandler _trashHandler;
	private final TrashRenderer _trashRenderer;

}