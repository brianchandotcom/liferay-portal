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
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class TrashEntryActionDropdownItems {

	public TrashEntryActionDropdownItems(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			TrashEntry trashEntry)
		throws PortalException {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_trashEntry = trashEntry;

		_themeDisplay = (ThemeDisplay)_liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			trashEntry.getClassName());

		_trashRenderer = _trashHandler.getTrashRenderer(
			_trashEntry.getClassPK());
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (_isRestorable()) {
					add(_getRestoreActionDropdownItem());
				}

				if (_isDeletable()) {
					add(_getDeleteActionDropdownItem());
				}
			}
		};
	}

	private DropdownItem _getDeleteActionDropdownItem() throws PortalException {
		return new DropdownItem() {
			{
				putData("action", "deleteEntry");
				putData("deleteURL", _getDeleteURL());
				setLabel(LanguageUtil.get(_themeDisplay.getLocale(), "delete"));
			}
		};
	}

	private String _getDeleteURL() throws PortalException {
		if (_trashEntry.getRootEntry() == null) {
			PortletURL deleteURL = _liferayPortletResponse.createActionURL();

			deleteURL.setParameter(ActionRequest.ACTION_NAME, "deleteEntries");
			deleteURL.setParameter("redirect", _getRedirectURL());
			deleteURL.setParameter(
				"trashEntryId", String.valueOf(_trashEntry.getEntryId()));

			return deleteURL.toString();
		}

		PortletURL deleteURL = _liferayPortletResponse.createActionURL();

		deleteURL.setParameter(ActionRequest.ACTION_NAME, "deleteEntries");
		deleteURL.setParameter("redirect", _getRedirectURL());
		deleteURL.setParameter("className", _trashRenderer.getClassName());
		deleteURL.setParameter(
			"classPK", String.valueOf(_trashRenderer.getClassPK()));

		return deleteURL.toString();
	}

	private String _getRedirectURL() throws PortalException {
		long trashEntryId = ParamUtil.getLong(
			_liferayPortletRequest, "trashEntryId");

		if (trashEntryId <= 0) {
			return _themeDisplay.getURLCurrent();
		}

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getTrashEntry(
			trashEntryId);

		TrashEntry rootTrashEntry = trashEntry.getRootEntry();

		PortletURL redirectURL = _liferayPortletResponse.createRenderURL();

		if (rootTrashEntry != null) {
			redirectURL.setParameter("mvcPath", "/view_content.jsp");
			redirectURL.setParameter(
				"classNameId", String.valueOf(rootTrashEntry.getClassNameId()));
			redirectURL.setParameter(
				"classPK", String.valueOf(rootTrashEntry.getClassPK()));
		}
		else {
			redirectURL.setParameter("mvcPath", "/view.jsp");
		}

		return redirectURL.toString();
	}

	private DropdownItem _getRestoreActionDropdownItem() throws Exception {
		boolean inTrashContainer = _trashHandler.isInTrashContainer(
			_trashEntry.getClassPK());

		return new DropdownItem() {
			{
				putData("action", "restoreEntry");
				putData("restoreURL", _getRestoreURL());
				putData(
					"move",
					String.valueOf(
						(_trashEntry.getRootEntry() != null) ||
						inTrashContainer));
				setLabel(
					LanguageUtil.get(_themeDisplay.getLocale(), "restore"));
			}
		};
	}

	private String _getRestoreURL() throws Exception {
		boolean inTrashContainer = _trashHandler.isInTrashContainer(
			_trashEntry.getClassPK());

		if ((_trashEntry.getRootEntry() == null) && !inTrashContainer) {
			PortletURL restoreURL = _liferayPortletResponse.createActionURL();

			restoreURL.setParameter(
				ActionRequest.ACTION_NAME, "restoreEntries");
			restoreURL.setParameter("redirect", _getRedirectURL());
			restoreURL.setParameter(
				"trashEntryId", String.valueOf(_trashEntry.getEntryId()));

			return restoreURL.toString();
		}

		PortletURL moveURL = _liferayPortletResponse.createRenderURL();

		String className = _trashRenderer.getClassName();
		long classPK = _trashRenderer.getClassPK();

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		moveURL.setParameter("mvcPath", "/view_container_model.jsp");
		moveURL.setParameter("redirect", _getRedirectURL());
		moveURL.setParameter(
			"classNameId",
			String.valueOf(PortalUtil.getClassNameId(className)));
		moveURL.setParameter("classPK", String.valueOf(classPK));
		moveURL.setParameter(
			"containerModelClassNameId",
			String.valueOf(
				PortalUtil.getClassNameId(
					trashHandler.getContainerModelClassName(classPK))));

		moveURL.setWindowState(LiferayWindowState.POP_UP);

		return moveURL.toString();
	}

	private boolean _isDeletable() {
		if (_trashEntry.getRootEntry() == null) {
			return true;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			_trashRenderer.getClassName());

		return trashHandler.isDeletable();
	}

	private boolean _isRestorable() throws PortalException {
		if (_trashEntry.getRootEntry() == null) {
			boolean movable = false;

			if (!_trashHandler.isRestorable(_trashEntry.getClassPK()) &&
				_trashHandler.isMovable()) {

				movable = true;
			}

			boolean restorable = false;

			if (_trashHandler.isRestorable(_trashEntry.getClassPK()) &&
				!_trashHandler.isInTrashContainer(_trashEntry.getClassPK())) {

				restorable = true;
			}

			if (movable || restorable) {
				return true;
			}

			return false;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			_trashRenderer.getClassName());

		return trashHandler.isMovable();
	}

	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;
	private final TrashEntry _trashEntry;
	private final TrashHandler _trashHandler;
	private final TrashRenderer _trashRenderer;

}