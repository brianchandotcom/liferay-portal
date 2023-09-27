/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.web.internal.constants.TrashWebKeys;
import com.liferay.trash.web.internal.display.context.TrashDisplayContext;

import java.util.List;

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
		_trashHelper = (TrashHelper)liferayPortletRequest.getAttribute(
			TrashWebKeys.TRASH_HELPER);
		_trashRenderer = trashDisplayContext.getTrashRenderer();
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							(_trashEntry != null) &&
							_trashHandler.isRestorable(
								_trashEntry.getClassPK()) &&
							!_trashHelper.isInTrashContainer(
								_trashHandler.getTrashedModel(
									_trashEntry.getClassPK())),
						_getRestoreActionDropdownItem()
					).add(
						() ->
							(_trashEntry != null) &&
							!_trashHandler.isRestorable(
								_trashEntry.getClassPK()) &&
							_trashHandler.isMovable(_trashEntry.getClassPK()),
						_getMoveTrashEntryActionDropdownItem()
					).add(
						() ->
							(_trashEntry == null) &&
							_trashHandler.isMovable(
								_trashRenderer.getClassPK()),
						_getMoveActionDropdownItem()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							CTCollectionThreadLocal.isProductionMode() &&
							(_trashEntry != null) &&
							_trashHandler.isDeletable(
								_trashRenderer.getClassPK()),
						_getDeleteTrashEntryActionDropdownItem()
					).add(
						() ->
							CTCollectionThreadLocal.isProductionMode() &&
							(_trashEntry == null) &&
							_trashHandler.isDeletable(
								_trashRenderer.getClassPK()),
						_getDeleteActionDropdownItem()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private DropdownItem _getDeleteActionDropdownItem() throws Exception {
		return DropdownItemBuilder.putData(
			"action", "deleteEntry"
		).putData(
			"deleteEntryURL",
			PortletURLBuilder.createActionURL(
				_liferayPortletResponse
			).setActionName(
				"deleteEntries"
			).setRedirect(
				_trashDisplayContext.getViewContentRedirectURL()
			).setParameter(
				"className", _trashRenderer.getClassName()
			).setParameter(
				"classPK", _trashRenderer.getClassPK()
			).buildString()
		).setIcon(
			"trash"
		).setLabel(
			LanguageUtil.get(_themeDisplay.getLocale(), "delete")
		).build();
	}

	private DropdownItem _getDeleteTrashEntryActionDropdownItem()
		throws Exception {

		return DropdownItemBuilder.putData(
			"action", "deleteEntry"
		).putData(
			"deleteEntryURL",
			PortletURLBuilder.createActionURL(
				_liferayPortletResponse
			).setActionName(
				"deleteEntries"
			).setRedirect(
				_trashDisplayContext.getViewContentRedirectURL()
			).setParameter(
				"trashEntryId", _trashDisplayContext.getTrashEntryId()
			).buildString()
		).setIcon(
			"trash"
		).setLabel(
			LanguageUtil.get(_themeDisplay.getLocale(), "delete")
		).build();
	}

	private DropdownItem _getMoveActionDropdownItem() throws Exception {
		return DropdownItemBuilder.putData(
			"action", "moveEntry"
		).putData(
			"moveEntryURL",
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCPath(
				"/view_container_model.jsp"
			).setParameter(
				"classNameId",
				PortalUtil.getClassNameId(_trashRenderer.getClassName())
			).setParameter(
				"classPK", _trashRenderer.getClassPK()
			).setParameter(
				"containerModelClassNameId",
				() -> PortalUtil.getClassNameId(
					_trashHandler.getContainerModelClassName(
						_trashDisplayContext.getClassPK()))
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).setIcon(
			"restore"
		).setLabel(
			LanguageUtil.get(_themeDisplay.getLocale(), "restore")
		).build();
	}

	private DropdownItem _getMoveTrashEntryActionDropdownItem()
		throws Exception {

		return DropdownItemBuilder.putData(
			"action", "moveEntry"
		).putData(
			"moveEntryURL",
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCPath(
				"/view_container_model.jsp"
			).setParameter(
				"classNameId", _trashDisplayContext.getClassNameId()
			).setParameter(
				"classPK", _trashDisplayContext.getClassPK()
			).setParameter(
				"containerModelClassNameId",
				() -> {
					String trashHandlerEntryContainerModelClassName =
						_trashHandler.getContainerModelClassName(
							_trashDisplayContext.getClassPK());

					return PortalUtil.getClassNameId(
						trashHandlerEntryContainerModelClassName);
				}
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).setIcon(
			"restore"
		).setLabel(
			LanguageUtil.get(_themeDisplay.getLocale(), "restore")
		).build();
	}

	private DropdownItem _getRestoreActionDropdownItem() throws Exception {
		return DropdownItemBuilder.putData(
			"action", "restoreEntry"
		).putData(
			"restoreEntryURL",
			PortletURLBuilder.createActionURL(
				_liferayPortletResponse
			).setActionName(
				"restoreEntries"
			).setRedirect(
				_trashDisplayContext.getViewContentRedirectURL()
			).setParameter(
				"trashEntryId", _trashDisplayContext.getTrashEntryId()
			).buildString()
		).setIcon(
			"restore"
		).setLabel(
			LanguageUtil.get(_themeDisplay.getLocale(), "restore")
		).build();
	}

	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;
	private final TrashDisplayContext _trashDisplayContext;
	private final TrashEntry _trashEntry;
	private final TrashHandler _trashHandler;
	private final TrashHelper _trashHelper;
	private final TrashRenderer _trashRenderer;

}