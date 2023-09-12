/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.security.permission.resource.JournalFolderPermission;
import com.liferay.journal.web.internal.util.JournalUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;
import com.liferay.translation.url.provider.TranslationURLProvider;
import com.liferay.trash.TrashHelper;

import java.util.ArrayList;
import java.util.Collections;
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
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			JournalDisplayContext journalDisplayContext,
			TrashHelper trashHelper)
		throws PortalException {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			journalDisplayContext.getSearchContainer());

		_journalDisplayContext = journalDisplayContext;
		_trashHelper = trashHelper;

		_journalWebConfiguration =
			(JournalWebConfiguration)httpServletRequest.getAttribute(
				JournalWebConfiguration.class.getName());
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		_translationURLProvider =
			(TranslationURLProvider)httpServletRequest.getAttribute(
				TranslationURLProvider.class.getName());
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.putData("action", "expireEntries");
							dropdownItem.setIcon("time");
							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, "expire"));
							dropdownItem.setQuickAction(true);
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.putData("action", "moveEntries");
							dropdownItem.setIcon("move-folder");
							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, "move"));
							dropdownItem.setQuickAction(true);
						}
					).add(
						dropdownItem -> {
							dropdownItem.putData("action", "exportTranslation");
							dropdownItem.setIcon("upload");
							dropdownItem.setLabel(
								LanguageUtil.get(
									httpServletRequest,
									"export-for-translations"));
							dropdownItem.setQuickAction(true);
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.putData("action", "deleteEntries");
							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, "delete"));
							dropdownItem.setQuickAction(true);
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"addArticleURL",
			PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setMVCPath(
				"/edit_article.jsp"
			).setRedirect(
				_themeDisplay.getURLCurrent()
			).setParameter(
				"folderId", _journalDisplayContext.getFolderId()
			).setParameter(
				"groupId", _themeDisplay.getScopeGroupId()
			).buildString()
		).put(
			"exportTranslationURL",
			() -> PortletURLBuilder.create(
				_translationURLProvider.getExportTranslationURL(
					_themeDisplay.getScopeGroupId(),
					PortalUtil.getClassNameId(JournalArticle.class.getName()),
					RequestBackedPortletURLFactoryUtil.create(
						liferayPortletRequest))
			).setRedirect(
				_themeDisplay.getURLCurrent()
			).buildString()
		).put(
			"moveArticlesAndFoldersURL",
			() -> {
				String redirect = ParamUtil.getString(
					liferayPortletRequest, "redirect",
					_themeDisplay.getURLCurrent());

				String referringPortletResource = ParamUtil.getString(
					liferayPortletRequest, "referringPortletResource");

				return PortletURLBuilder.createRenderURL(
					liferayPortletResponse
				).setMVCPath(
					"/move_articles_and_folders.jsp"
				).setRedirect(
					redirect
				).setParameter(
					"referringPortletResource", referringPortletResource
				).buildString();
			}
		).put(
			"openViewMoreStructuresURL",
			PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setMVCPath(
				"/view_more_menu_items.jsp"
			).setParameter(
				"eventName",
				liferayPortletResponse.getNamespace() + "selectAddMenuItem"
			).setParameter(
				"folderId", _journalDisplayContext.getFolderId()
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).put(
			"selectEntityURL", _journalDisplayContext.getSelectDDMStructureURL()
		).put(
			"trashEnabled", _isTrashEnabled()
		).put(
			"viewDDMStructureArticlesURL",
			PortletURLBuilder.create(
				getPortletURL()
			).setNavigation(
				"structure"
			).setParameter(
				"ddmStructureId", (String)null
			).buildString()
		).build();
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).setNavigation(
			StringPool.BLANK
		).setParameter(
			"ddmStructureId", (String)null
		).setParameter(
			"orderByCol", StringPool.BLANK
		).setParameter(
			"orderByType", StringPool.BLANK
		).setParameter(
			"status", WorkflowConstants.STATUS_ANY
		).buildString();
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
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get creation menu", portalException);
			}
		}

		return null;
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					getFilterNavigationDropdownItemsLabel());
			}
		).addGroup(
			_journalDisplayContext::isIndexAllArticleVersions,
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					getFilterStatusDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "filter-by-status"));
			}
		).addGroup(
			() ->
				!_journalDisplayContext.isNavigationRecent() &&
				!FeatureFlagManagerUtil.isEnabled("LPS-144527"),
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getOrderByDropdownItems());
				dropdownGroupItem.setLabel(getOrderByDropdownItemsLabel());
			}
		).build();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		int status = _journalDisplayContext.getStatus();

		return LabelItemListBuilder.add(
			_journalDisplayContext::isNavigationMine,
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							currentURLObj, liferayPortletResponse)
					).setNavigation(
						(String)null
					).buildString());

				labelItem.setCloseable(true);

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				User user = themeDisplay.getUser();

				labelItem.setLabel(
					LanguageUtil.get(httpServletRequest, "owner") + ": " +
						user.getFullName());
			}
		).add(
			_journalDisplayContext::isNavigationRecent,
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							currentURLObj, liferayPortletResponse)
					).setNavigation(
						(String)null
					).buildString());

				labelItem.setCloseable(true);
				labelItem.setLabel(
					LanguageUtil.get(httpServletRequest, "recent"));
			}
		).add(
			_journalDisplayContext::isNavigationStructure,
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							currentURLObj, liferayPortletResponse)
					).setNavigation(
						(String)null
					).setParameter(
						"ddmStructureId", (String)null
					).buildString());

				labelItem.setCloseable(true);

				String ddmStructureName =
					_journalDisplayContext.getDDMStructureName();

				labelItem.setLabel(
					LanguageUtil.get(httpServletRequest, "structures") + ": " +
						ddmStructureName);
			}
		).add(
			() -> status != WorkflowConstants.STATUS_ANY,
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						PortletURLUtil.clone(
							currentURLObj, liferayPortletResponse)
					).setParameter(
						"status", WorkflowConstants.STATUS_ANY
					).buildString());

				labelItem.setCloseable(true);
				labelItem.setLabel(
					LanguageUtil.get(httpServletRequest, "status") + ": " +
						_getStatusLabel(status));
			}
		).build();
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setParameter(
			"folderId", _journalDisplayContext.getFolderId()
		).setParameter(
			"status", _journalDisplayContext.getStatus()
		).buildString();
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
		if ((getItemsTotal() > 0) || _journalDisplayContext.isSearch() ||
			!_journalDisplayContext.isNavigationHome() ||
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
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get creation menu", portalException);
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
	protected String getDisplayStyle() {
		return _journalDisplayContext.getDisplayStyle();
	}

	@Override
	protected String[] getDisplayViews() {
		return _journalDisplayContext.getDisplayViews();
	}

	@Override
	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		List<DropdownItem> filterNavigationDropdownItems = getDropdownItems(
			getNavigationEntriesMap(),
			PortletURLBuilder.create(
				getPortletURL()
			).setKeywords(
				StringPool.BLANK
			).setParameter(
				"ddmStructureId", (String)null
			).buildPortletURL(),
			getNavigationParam(), getNavigation());

		filterNavigationDropdownItems.add(
			DropdownItemBuilder.putData(
				"action", "openDDMStructuresSelector"
			).setActive(
				_journalDisplayContext.isNavigationStructure()
			).setLabel(
				LanguageUtil.get(httpServletRequest, "structures")
			).build());

		return filterNavigationDropdownItems;
	}

	protected List<DropdownItem> getFilterStatusDropdownItems() {
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
								PortalUtil.getCurrentURL(httpServletRequest),
								"groupId",
								String.valueOf(_themeDisplay.getScopeGroupId()),
								"parentFolderId",
								String.valueOf(
									_journalDisplayContext.getFolderId()));
							dropdownItem.setIcon("folder");

							String label = "folder";

							if (_journalDisplayContext.getFolder() != null) {
								label = "subfolder";
							}

							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, label));
						});
				}

				if (JournalFolderPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						_journalDisplayContext.getFolderId(),
						ActionKeys.ADD_ARTICLE)) {

					List<DDMStructure> ddmStructures =
						_journalDisplayContext.getDDMStructures();

					Collections.sort(
						ddmStructures, _getDDMStructureOrderByComparator());

					for (DDMStructure ddmStructure : ddmStructures) {
						PortletURL portletURL =
							PortletURLBuilder.createRenderURL(
								liferayPortletResponse
							).setMVCPath(
								"/edit_article.jsp"
							).setRedirect(
								PortalUtil.getCurrentURL(httpServletRequest)
							).setParameter(
								"ddmStructureId", ddmStructure.getStructureId()
							).setParameter(
								"folderId", _journalDisplayContext.getFolderId()
							).setParameter(
								"groupId", _themeDisplay.getScopeGroupId()
							).setParameter(
								"showSelectFolder", false
							).buildPortletURL();

						UnsafeConsumer<DropdownItem, Exception> unsafeConsumer =
							dropdownItem -> {
								dropdownItem.setHref(portletURL);
								dropdownItem.setLabel(
									HtmlUtil.escape(
										ddmStructure.getUnambiguousName(
											ddmStructures,
											_themeDisplay.getScopeGroupId(),
											_themeDisplay.getLocale())));
							};

						if (ArrayUtil.contains(
								_journalDisplayContext.getAddMenuFavItems(),
								ddmStructure.getStructureId())) {

							addFavoriteDropdownItem(unsafeConsumer);
						}
						else {
							addRestDropdownItem(unsafeConsumer);
						}
					}
				}

				setHelpText(
					LanguageUtil.get(
						httpServletRequest,
						"you-can-customize-this-menu-or-see-all-you-have-by-" +
							"clicking-more"));
			}
		};
	}

	private String _getDDMStructureOrderByCol() {
		if (Validator.isNotNull(_ddmStructureOrderByCol)) {
			return _ddmStructureOrderByCol;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				httpServletRequest);

		String orderByCol = portalPreferences.getValue(
			JournalPortletKeys.JOURNAL, "view-more-items-order-by-col",
			"modified-date");

		if (Validator.isNull(orderByCol)) {
			orderByCol = ParamUtil.getString(
				httpServletRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM);
		}

		_ddmStructureOrderByCol = orderByCol;

		return _ddmStructureOrderByCol;
	}

	private OrderByComparator<DDMStructure>
		_getDDMStructureOrderByComparator() {

		OrderByComparator<DDMStructure> orderByComparator = null;

		boolean orderByAsc = false;

		if (Objects.equals(_getDDMStructureOrderByType(), "asc")) {
			orderByAsc = true;
		}

		String orderByCol = _getDDMStructureOrderByCol();

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new StructureModifiedDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new StructureNameComparator(
				orderByAsc, _themeDisplay.getLocale());
		}

		return orderByComparator;
	}

	private String _getDDMStructureOrderByType() {
		if (Validator.isNotNull(_ddmStructureOrderByType)) {
			return _ddmStructureOrderByType;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				httpServletRequest);

		String orderByType = portalPreferences.getValue(
			JournalPortletKeys.JOURNAL, "view-more-items-order-by-type",
			"desc");

		if (Validator.isNull(orderByType)) {
			orderByType = ParamUtil.getString(
				httpServletRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM);
		}

		_ddmStructureOrderByType = orderByType;

		return _ddmStructureOrderByType;
	}

	private List<Integer> _getStatuses() {
		List<Integer> statuses = new ArrayList<>();

		statuses.add(WorkflowConstants.STATUS_ANY);
		statuses.add(WorkflowConstants.STATUS_DRAFT);

		if (JournalUtil.hasWorkflowDefinitionsLinks(_themeDisplay)) {
			statuses.add(WorkflowConstants.STATUS_PENDING);
			statuses.add(WorkflowConstants.STATUS_DENIED);
		}

		statuses.add(WorkflowConstants.STATUS_APPROVED);
		statuses.add(WorkflowConstants.STATUS_EXPIRED);
		statuses.add(WorkflowConstants.STATUS_SCHEDULED);

		return statuses;
	}

	private String _getStatusLabel(int status) {
		String label = null;

		if (status == WorkflowConstants.STATUS_APPROVED) {
			label = "with-approved-versions";
		}
		else if (status == WorkflowConstants.STATUS_EXPIRED) {
			label = "with-expired-versions";
		}
		else if (status == WorkflowConstants.STATUS_SCHEDULED) {
			label = "with-scheduled-versions";
		}
		else {
			label = WorkflowConstants.getStatusLabel(status);
		}

		return LanguageUtil.get(httpServletRequest, label);
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

	private boolean _isTrashEnabled() {
		try {
			return _trashHelper.isTrashEnabled(_themeDisplay.getScopeGroupId());
		}
		catch (PortalException portalException) {

			// LPS-52675

			_log.error(portalException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalManagementToolbarDisplayContext.class);

	private String _ddmStructureOrderByCol;
	private String _ddmStructureOrderByType;
	private final JournalDisplayContext _journalDisplayContext;
	private final JournalWebConfiguration _journalWebConfiguration;
	private final ThemeDisplay _themeDisplay;
	private final TranslationURLProvider _translationURLProvider;
	private final TrashHelper _trashHelper;

}