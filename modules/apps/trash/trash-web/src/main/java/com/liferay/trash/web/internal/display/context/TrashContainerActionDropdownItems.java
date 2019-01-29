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

package com.liferay.trash.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class TrashContainerActionDropdownItems {

	public TrashContainerActionDropdownItems(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			TrashedModel trashedModel)
		throws PortalException {

		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			((ClassedModel)trashedModel).getModelClassName());

		_trashRenderer = trashHandler.getTrashRenderer(
			trashedModel.getTrashEntryClassPK());
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				TrashHandler trashHandler =
					TrashHandlerRegistryUtil.getTrashHandler(
						_trashRenderer.getClassName());

				if (trashHandler.isDeletable()) {
					add(_getRestoreAction());
				}

				if (trashHandler.isMovable()) {
					add(_getDeleteAction());
				}
			}
		};
	}

	private DropdownItem _getDeleteAction() {
		return new DropdownItem() {
			{
				putData("action", "deleteEntry");
				putData("deleteURL", _getDeleteURL());
				setLabel(LanguageUtil.get(_themeDisplay.getLocale(), "delete"));
			}
		};
	}

	private String _getDeleteURL() {
		PortletURL deleteURL = _liferayPortletResponse.createActionURL();

		deleteURL.setParameter(ActionRequest.ACTION_NAME, "deleteEntries");
		deleteURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		deleteURL.setParameter("className", _trashRenderer.getClassName());
		deleteURL.setParameter(
			"classPK", String.valueOf(_trashRenderer.getClassPK()));

		return deleteURL.toString();
	}

	private DropdownItem _getRestoreAction() throws Exception {
		return new DropdownItem() {
			{
				putData("action", "restoreEntry");
				putData("restoreURL", _getRestoreURL());
				putData("move", Boolean.TRUE.toString());
				setLabel(
					LanguageUtil.get(_themeDisplay.getLocale(), "restore"));
			}
		};
	}

	private String _getRestoreURL() throws Exception {
		PortletURL moveURL = _liferayPortletResponse.createRenderURL();

		String className = _trashRenderer.getClassName();
		long classPK = _trashRenderer.getClassPK();

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		moveURL.setParameter("mvcPath", "/view_container_model.jsp");
		moveURL.setParameter("redirect", _themeDisplay.getURLCurrent());
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

	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;
	private final TrashRenderer _trashRenderer;

}