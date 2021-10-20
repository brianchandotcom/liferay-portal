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

package com.liferay.document.library.item.selector.web.internal.folder;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.item.selector.web.internal.constants.DLItemSelectorViewConstants;
import com.liferay.document.library.item.selector.web.internal.display.context.DLSelectFolderDisplayContext;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.PortletItemSelectorView;
import com.liferay.item.selector.criteria.FolderItemSelectorReturnType;
import com.liferay.item.selector.criteria.folder.criterion.FolderItemSelectorCriterion;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"item.selector.view.key=" + DLItemSelectorViewConstants.DL_FOLDER_ITEM_SELECTOR_VIEW_KEY,
		"item.selector.view.order:Integer=100"
	},
	service = ItemSelectorView.class
)
public class DLFolderItemSelectorView
	implements PortletItemSelectorView<FolderItemSelectorCriterion> {

	@Override
	public Class<FolderItemSelectorCriterion> getItemSelectorCriterionClass() {
		return FolderItemSelectorCriterion.class;
	}

	@Override
	public List<String> getPortletIds() {
		return _portletIds;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundleLoader resourceBundleLoader =
			LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER;

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return ResourceBundleUtil.getString(
			resourceBundle, "documents-and-media");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FolderItemSelectorCriterion itemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/select_folder.jsp");

		long folderId = BeanParamUtil.getLong(
			itemSelectorCriterion, (HttpServletRequest)servletRequest,
			"folderId");

		servletRequest.setAttribute(
			DLSelectFolderDisplayContext.class.getName(),
			new DLSelectFolderDisplayContext(
				_dlAppService, _fetchFolder(folderId),
				(HttpServletRequest)servletRequest, portletURL,
				BeanParamUtil.getLong(
					itemSelectorCriterion, (HttpServletRequest)servletRequest,
					"selectedFolderId", folderId)));

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private Folder _fetchFolder(long folderId) {
		try {
			if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				return null;
			}

			return _dlAppService.getFolder(folderId);
		}
		catch (Exception exception) {
			return null;
		}
	}

	private static final List<String> _portletIds = Arrays.asList(
		DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, DLPortletKeys.DOCUMENT_LIBRARY);
	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new FolderItemSelectorReturnType());

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.item.selector.web)"
	)
	private ServletContext _servletContext;

}