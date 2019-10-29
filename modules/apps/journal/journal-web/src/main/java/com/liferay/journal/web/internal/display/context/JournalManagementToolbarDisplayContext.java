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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.security.permission.resource.JournalFolderPermission;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;
import com.liferay.trash.TrashHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public JournalManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest httpServletRequest,
			JournalDisplayContext journalDisplayContext,
			TrashHelper trashHelper)
		throws PortalException {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			journalDisplayContext.getSearchContainer());

		_journalDisplayContext = journalDisplayContext;
		_trashHelper = trashHelper;

		_journalWebConfiguration =
			(JournalWebConfiguration)httpServletRequest.getAttribute(
				JournalWebConfiguration.class.getName());
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteEntries");

						boolean trashEnabled = _trashHelper.isTrashEnabled(
							_themeDisplay.getScopeGroupId());

						dropdownItem.setIcon(
							trashEnabled ? "trash" : "times-circle");

						String label = "delete";

						if (trashEnabled) {
							label = "recycle-bin";
						}

						dropdownItem.setLabel(LanguageUtil.get(request, label));

						dropdownItem.setQuickAction(true);
					});

				add(
					dropdownItem -> {
						dropdownItem.putData("action", "expireEntries");
						dropdownItem.setIcon("time");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "expire"));
						dropdownItem.setQuickAction(true);
					});

				add(
					dropdownItem -> {
						dropdownItem.putData("action", "moveEntries");
						dropdownItem.setIcon("move-folder");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "move"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("navigation", StringPool.BLANK);
		clearResultsURL.setParameter("ddmStructureKey", StringPool.BLANK);
		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter(
			"status", String.valueOf(WorkflowConstants.STATUS_ANY));

		return clearResultsURL.toString();
	}

	public Map<String, Object> getComponentContext() throws Exception {
		PortletURL addArticleURL = liferayPortletResponse.createRenderURL();

		addArticleURL.setParameter("mvcPath", "/edit_article.jsp");
		addArticleURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		addArticleURL.setParameter(
			"groupId", String.valueOf(_themeDisplay.getScopeGroupId()));
		addArticleURL.setParameter(
			"folderId", String.valueOf(_journalDisplayContext.getFolderId()));

		PortletURL moveEntriesURL = liferayPortletResponse.createRenderURL();

		moveEntriesURL.setParameter("mvcPath", "/move_entries.jsp");

		String redirect = ParamUtil.getString(
			liferayPortletRequest, "redirect", _themeDisplay.getURLCurrent());

		moveEntriesURL.setParameter("redirect", redirect);

		String referringPortletResource = ParamUtil.getString(
			liferayPortletRequest, "referringPortletResource");

		moveEntriesURL.setParameter(
			"referringPortletResource", referringPortletResource);

		PortletURL openViewMoreStructuresURL =
			liferayPortletResponse.createRenderURL();

		openViewMoreStructuresURL.setParameter(
			"mvcPath", "/view_more_menu_items.jsp");
		openViewMoreStructuresURL.setParameter(
			"folderId", String.valueOf(_journalDisplayContext.getFolderId()));
		openViewMoreStructuresURL.setParameter(
			"eventName",
			liferayPortletResponse.getNamespace() + "selectAddMenuItem");
		openViewMoreStructuresURL.setWindowState(LiferayWindowState.POP_UP);

		PortletURL selectEntityURL = liferayPortletResponse.createRenderURL();

		selectEntityURL.setParameter("mvcPath", "/select_ddm_structure.jsp");
		selectEntityURL.setWindowState(LiferayWindowState.POP_UP);

		PortletURL viewDDMStructureArticlesURL =
			liferayPortletResponse.createRenderURL();

		viewDDMStructureArticlesURL.setParameter("navigation", "structure");
		viewDDMStructureArticlesURL.setParameter(
			"folderId",
			String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID));

		return HashMapBuilder.<String, Object>put(
			"addArticleURL", addArticleURL.toString()
		).put(
			"folderId",
			String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)
		).put(
			"moveEntriesURL", moveEntriesURL.toString()
		).put(
			"openViewMoreStructuresURL", openViewMoreStructuresURL.toString()
		).put(
			"selectEntityURL", selectEntityURL.toString()
		).put(
			"trashEnabled",
			_trashHelper.isTrashEnabled(_themeDisplay.getScopeGroupId())
		).put(
			"viewDDMStructureArticlesURL",
			viewDDMStructureArticlesURL.toString()
		).build();
	}

	@Override
	public String getComponentId() {
		return "journalWebManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		try {
			return _getCreationMenu();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get creation menu", pe);
			}
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return "journalManagementToolbarDefaultEventHandler";
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							getFilterNavigationDropdownItemsLabel());
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterStatusDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "filter-by-status"));
					});

				if (!_journalDisplayContext.isNavigationRecent()) {
					addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								getOrderByDropdownItems());
							dropdownGroupItem.setLabel(
								getOrderByDropdownItemsLabel());
						});
				}
			}
		};
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return new LabelItemList() {
			{
				if (_journalDisplayContext.isNavigationMine()) {
					add(
						labelItem -> {
							PortletURL removeLabelURL = PortletURLUtil.clone(
								currentURLObj, liferayPortletResponse);

							removeLabelURL.setParameter(
								"navigation", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

							ThemeDisplay themeDisplay =
								(ThemeDisplay)request.getAttribute(
									WebKeys.THEME_DISPLAY);

							User user = themeDisplay.getUser();

							labelItem.setLabel(
								LanguageUtil.get(request, "owner") + ": " +
									user.getFullName());
						});
				}

				if (_journalDisplayContext.isNavigationRecent()) {
					add(
						labelItem -> {
							PortletURL removeLabelURL = PortletURLUtil.clone(
								currentURLObj, liferayPortletResponse);

							removeLabelURL.setParameter(
								"navigation", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

							labelItem.setLabel(
								LanguageUtil.get(request, "recent"));
						});
				}

				if (_journalDisplayContext.isNavigationStructure()) {
					add(
						labelItem -> {
							PortletURL removeLabelURL = PortletURLUtil.clone(
								currentURLObj, liferayPortletResponse);

							removeLabelURL.setParameter(
								"navigation", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

							String ddmStructureName =
								_journalDisplayContext.getDDMStructureName();

							labelItem.setLabel(
								LanguageUtil.get(request, "structures") + ": " +
									ddmStructureName);
						});
				}

				int status = _journalDisplayContext.getStatus();

				if (status != _journalDisplayContext.getDefaultStatus()) {
					add(
						labelItem -> {
							PortletURL removeLabelURL = PortletURLUtil.clone(
								currentURLObj, liferayPortletResponse);

							removeLabelURL.setParameter("status", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

							labelItem.setLabel(
								LanguageUtil.get(request, "status") + ": " +
									_getStatusLabel(status));
						});
				}
			}
		};
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"folderId", String.valueOf(_journalDisplayContext.getFolderId()));

		return portletURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "articles";
	}

	@Override
	public String getSearchFormName() {
		return "fm1";
	}

	@Override
	public String getSortingOrder() {
		if (Objects.equals(getOrderByCol(), "relevance")) {
			return null;
		}

		return super.getSortingOrder();
	}

	@Override
	public Boolean isDisabled() {
		if (getItemsTotal() > 0) {
			return false;
		}

		if (_journalDisplayContext.isSearch()) {
			return false;
		}

		if (!_journalDisplayContext.isNavigationHome() ||
			(_journalDisplayContext.getStatus() !=
				WorkflowConstants.STATUS_ANY)) {

			return false;
		}

		return true;
	}

	@Override
	public Boolean isShowCreationMenu() {
		try {
			return _isShowAddButton();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get creation menu", pe);
			}

			return false;
		}
	}

	@Override
	public Boolean isShowInfoButton() {
		return _journalDisplayContext.isShowInfoButton();
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return _journalWebConfiguration.defaultDisplayView();
	}

	@Override
	protected String[] getDisplayViews() {
		return _journalDisplayContext.getDisplayViews();
	}

	@Override
	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		List<DropdownItem> filterNavigationDropdownItems =
			super.getFilterNavigationDropdownItems();

		DropdownItem dropdownItem = new DropdownItem();

		dropdownItem.setActive(_journalDisplayContext.isNavigationStructure());
		dropdownItem.putData("action", "openDDMStructuresSelector");
		dropdownItem.setLabel(LanguageUtil.get(request, "structures"));

		filterNavigationDropdownItems.add(dropdownItem);

		return filterNavigationDropdownItems;
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all", "mine", "recent"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return _journalDisplayContext.getOrderColumns();
	}

	private CreationMenu _getCreationMenu() throws PortalException {
		return new CreationMenu() {
			{
				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						_journalDisplayContext.getFolderId(),
						ActionKeys.ADD_FOLDER)) {

					addPrimaryDropdownItem(
						dropdownItem -> {
							dropdownItem.setHref(
								liferayPortletResponse.createRenderURL(),
								"mvcPath", "/edit_folder.jsp", "redirect",
								PortalUtil.getCurrentURL(request), "groupId",
								String.valueOf(_themeDisplay.getScopeGroupId()),
								"parentFolderId",
								String.valueOf(
									_journalDisplayContext.getFolderId()));

							String label = "folder";

							if (_journalDisplayContext.getFolder() != null) {
								label = "subfolder";
							}

							dropdownItem.setLabel(
								LanguageUtil.get(request, label));
						});
				}

				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						_journalDisplayContext.getFolderId(),
						ActionKeys.ADD_ARTICLE)) {

					List<DDMStructure> ddmStructures =
						_journalDisplayContext.getDDMStructures();

					for (DDMStructure ddmStructure : ddmStructures) {
						PortletURL portletURL =
							liferayPortletResponse.createRenderURL();

						portletURL.setParameter("mvcPath", "/edit_article.jsp");
						portletURL.setParameter(
							"redirect", PortalUtil.getCurrentURL(request));
						portletURL.setParameter(
							"groupId",
							String.valueOf(_themeDisplay.getScopeGroupId()));
						portletURL.setParameter(
							"folderId",
							String.valueOf(
								_journalDisplayContext.getFolderId()));
						portletURL.setParameter(
							"ddmStructureKey", ddmStructure.getStructureKey());

						UnsafeConsumer<DropdownItem, Exception> unsafeConsumer =
							dropdownItem -> {
								dropdownItem.setHref(portletURL);
								dropdownItem.setLabel(
									ddmStructure.getUnambiguousName(
										ddmStructures,
										_themeDisplay.getScopeGroupId(),
										_themeDisplay.getLocale()));
							};

						if (ArrayUtil.contains(
								_journalDisplayContext.getAddMenuFavItems(),
								ddmStructure.getStructureKey())) {

							addFavoriteDropdownItem(unsafeConsumer);
						}
						else {
							addRestDropdownItem(unsafeConsumer);
						}
					}
				}

				setHelpText(
					LanguageUtil.get(
						request,
						"you-can-customize-this-menu-or-see-all-you-have-by-" +
							"clicking-more"));
			}
		};
	}

	private List<DropdownItem> _getFilterStatusDropdownItems() {
		return new DropdownItemList() {
			{
				for (int status : _getStatuses()) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(
								_journalDisplayContext.getStatus() == status);
							dropdownItem.setHref(
								getPortletURL(), "status",
								String.valueOf(status));

							dropdownItem.setLabel(_getStatusLabel(status));
						});
				}
			}
		};
	}

	private List<Integer> _getStatuses() {
		List<Integer> statuses = new ArrayList<>();

		statuses.add(WorkflowConstants.STATUS_ANY);
		statuses.add(WorkflowConstants.STATUS_DRAFT);

		int workflowDefinitionLinksCount =
			WorkflowDefinitionLinkLocalServiceUtil.
				getWorkflowDefinitionLinksCount(
					_themeDisplay.getCompanyId(),
					_themeDisplay.getScopeGroupId(),
					JournalFolder.class.getName());

		if (workflowDefinitionLinksCount > 0) {
			statuses.add(WorkflowConstants.STATUS_PENDING);
			statuses.add(WorkflowConstants.STATUS_DENIED);
		}

		statuses.add(WorkflowConstants.STATUS_SCHEDULED);
		statuses.add(WorkflowConstants.STATUS_APPROVED);
		statuses.add(WorkflowConstants.STATUS_EXPIRED);

		return statuses;
	}

	private String _getStatusLabel(int status) {
		String label = WorkflowConstants.getStatusLabel(status);

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			label = "with-expired-versions";
		}

		return LanguageUtil.get(request, label);
	}

	private boolean _isShowAddButton() throws PortalException {
		Group group = _themeDisplay.getScopeGroup();

		if (group.isLayout()) {
			group = group.getParentGroup();
		}

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if ((stagingGroupHelper.isLocalLiveGroup(group) ||
			 stagingGroupHelper.isRemoteLiveGroup(group)) &&
			stagingGroupHelper.isStagedPortlet(
				group, JournalPortletKeys.JOURNAL)) {

			return false;
		}

		if (JournalFolderPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				_journalDisplayContext.getFolderId(), ActionKeys.ADD_FOLDER) ||
			JournalFolderPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				_journalDisplayContext.getFolderId(), ActionKeys.ADD_ARTICLE)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalManagementToolbarDisplayContext.class);

	private final JournalDisplayContext _journalDisplayContext;
	private final JournalWebConfiguration _journalWebConfiguration;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}