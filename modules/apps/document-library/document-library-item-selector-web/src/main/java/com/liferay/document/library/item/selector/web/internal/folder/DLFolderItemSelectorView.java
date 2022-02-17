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

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.item.selector.web.internal.configuration.FFFolderItemSelectorGroupSelectorConfiguration;
import com.liferay.document.library.item.selector.web.internal.constants.DLItemSelectorViewConstants;
import com.liferay.document.library.item.selector.web.internal.display.context.DLSelectFolderDisplayContext;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.PortletItemSelectorView;
import com.liferay.item.selector.criteria.FolderItemSelectorReturnType;
import com.liferay.item.selector.criteria.folder.criterion.FolderItemSelectorCriterion;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.language.LanguageResources;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.item.selector.web.internal.configuration.FFFolderItemSelectorGroupSelectorConfiguration",
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

		ThemeDisplay themeDisplay = (ThemeDisplay)servletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(
			(HttpServletRequest)servletRequest, "folderId",
			itemSelectorCriterion.getFolderId());
		long repositoryId = ParamUtil.getLong(
			(HttpServletRequest)servletRequest, "repositoryId",
			itemSelectorCriterion.getRepositoryId());

		if (themeDisplay.getScopeGroupId() != _getRepositoryGroupId(
				itemSelectorCriterion.getRepositoryId())) {

			folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			repositoryId = themeDisplay.getScopeGroupId();
		}

		Folder folder = _fetchFolder(folderId);

		if (folder != null) {
			repositoryId = folder.getRepositoryId();
		}

		Group group = _getGroup(repositoryId);

		if ((group != null) && group.isDepot()) {
			List<Long> groupConnectedDepotEntries =
				_getGroupConnectedDepotEntries(themeDisplay);

			if (!groupConnectedDepotEntries.contains(group.getGroupId())) {
				folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
				repositoryId = themeDisplay.getRefererGroupId();
			}
		}

		servletRequest.setAttribute(
			DLSelectFolderDisplayContext.class.getName(),
			new DLSelectFolderDisplayContext(
				_dlAppService, _fetchFolder(folderId),
				(HttpServletRequest)servletRequest, portletURL, repositoryId,
				itemSelectorCriterion.getSelectedFolderId(),
				itemSelectorCriterion.getSelectedRepositoryId(),
				_isShowGroupSelector(itemSelectorCriterion)));

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_ffFolderItemSelectorGroupSelectorConfiguration =
			ConfigurableUtil.createConfigurable(
				FFFolderItemSelectorGroupSelectorConfiguration.class,
				properties);
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

	private Group _getGroup(long groupId) {
		try {
			return _groupService.getGroup(groupId);
		}
		catch (Exception exception) {
			return null;
		}
	}

	private List<Long> _getGroupConnectedDepotEntries(
		ThemeDisplay themeDisplay) {

		try {
			return ListUtil.toList(
				_depotEntryService.getGroupConnectedDepotEntries(
					themeDisplay.getRefererGroupId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS),
				DepotEntry::getGroupId);
		}
		catch (Exception exception) {
			return Collections.emptyList();
		}
	}

	private long _getRepositoryGroupId(long repositoryId) {
		Repository repository = _repositoryLocalService.fetchRepository(
			repositoryId);

		if (repository == null) {
			return repositoryId;
		}

		return repository.getGroupId();
	}

	private boolean _isShowGroupSelector(
		FolderItemSelectorCriterion itemSelectorCriterion) {

		if (!_ffFolderItemSelectorGroupSelectorConfiguration.enabled()) {
			return false;
		}

		return itemSelectorCriterion.isShowGroupSelector();
	}

	private static final List<String> _portletIds = Arrays.asList(
		DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, DLPortletKeys.DOCUMENT_LIBRARY);
	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new FolderItemSelectorReturnType());

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private DLAppService _dlAppService;

	private volatile FFFolderItemSelectorGroupSelectorConfiguration
		_ffFolderItemSelectorGroupSelectorConfiguration;

	@Reference
	private GroupService _groupService;

	@Reference
	private Portal _portal;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.item.selector.web)"
	)
	private ServletContext _servletContext;

}