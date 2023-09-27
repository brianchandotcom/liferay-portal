/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.taglib.internal.frontend.taglib.clay.servlet;

import com.liferay.document.library.taglib.internal.display.context.RepositoryBrowserTagDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.List;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class FolderHorizontalCard implements HorizontalCard {

	public FolderHorizontalCard(
		Set<String> actions, Folder folder,
		RepositoryBrowserTagDisplayContext repositoryBrowserTagDisplayContext) {

		_actions = actions;
		_folder = folder;
		_repositoryBrowserTagDisplayContext =
			repositoryBrowserTagDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return _repositoryBrowserTagDisplayContext.getActionDropdownItems(
			_folder);
	}

	@Override
	public String getDefaultEventHandler() {
		return "repositoryBrowserEventHandler";
	}

	@Override
	public String getHref() {
		return _repositoryBrowserTagDisplayContext.getFolderURL(_folder);
	}

	@Override
	public String getInputName() {
		try {
			SearchContainer<Object> searchContainer =
				_repositoryBrowserTagDisplayContext.getSearchContainer();

			RowChecker rowChecker = searchContainer.getRowChecker();

			if (rowChecker == null) {
				return null;
			}

			return rowChecker.getRowIds();
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public String getInputValue() {
		return String.valueOf(_folder.getFolderId());
	}

	@Override
	public String getTitle() {
		return _folder.getName();
	}

	@Override
	public boolean isSelectable() {
		if (_actions.isEmpty()) {
			return false;
		}

		return true;
	}

	private final Set<String> _actions;
	private final Folder _folder;
	private final RepositoryBrowserTagDisplayContext
		_repositoryBrowserTagDisplayContext;

}