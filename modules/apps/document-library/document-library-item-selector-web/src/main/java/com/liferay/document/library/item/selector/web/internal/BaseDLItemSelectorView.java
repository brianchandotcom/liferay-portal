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

package com.liferay.document.library.item.selector.web.internal;

import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.item.selector.web.internal.constants.DLItemSelectorWebKeys;
import com.liferay.document.library.item.selector.web.internal.display.context.DLItemSelectorViewDisplayContext;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.staging.StagingGroupHelper;

import java.io.IOException;

import java.util.Arrays;
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

import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
public abstract class BaseDLItemSelectorView<T extends ItemSelectorCriterion>
	implements DLItemSelectorView<T> {

	@Override
	public String[] getExtensions() {
		return new String[0];
	}

	@Override
	public String[] getMimeTypes() {
		return new String[0];
	}

	@Override
	public List<String> getPortletIds() {
		return _portletIds;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundleLoader resourceBundleLoader =
			LanguageResources.RESOURCE_BUNDLE_LOADER;

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return ResourceBundleUtil.getString(
			resourceBundle, "documents-and-media");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse, T t,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher("/documents.jsp");

		DLItemSelectorViewDisplayContext dlItemSelectorViewDisplayContext =
			new DLItemSelectorViewDisplayContext<>(
				assetVocabularyService, classNameLocalService, this,
				folderModelResourcePermission,
				(HttpServletRequest)servletRequest, t, itemSelectedEventName,
				itemSelectorReturnTypeResolverHandler, portletURL, search,
				stagingGroupHelper);

		servletRequest.setAttribute(
			DLItemSelectorWebKeys.DL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			dlItemSelectorViewDisplayContext);

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Reference
	protected AssetVocabularyService assetVocabularyService;

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	protected ModelResourcePermission<Folder> folderModelResourcePermission;

	@Reference
	protected ItemSelectorReturnTypeResolverHandler
		itemSelectorReturnTypeResolverHandler;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.item.selector.web)"
	)
	protected ServletContext servletContext;

	@Reference
	protected StagingGroupHelper stagingGroupHelper;

	private static final List<String> _portletIds = Arrays.asList(
		DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, DLPortletKeys.DOCUMENT_LIBRARY);

}