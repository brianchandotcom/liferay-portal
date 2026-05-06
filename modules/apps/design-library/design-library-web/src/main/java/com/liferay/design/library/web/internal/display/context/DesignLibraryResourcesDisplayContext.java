/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.display.context;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.ThemeCSSCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.design.library.web.internal.constants.DesignLibraryConstants;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.constants.FrontendTokenDefinitionConstants;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.constants.StyleBookActionKeys;
import com.liferay.style.book.constants.StyleBookConstants;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.util.StyleBookUtil;

import jakarta.portlet.PortletRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Gabriel Prates
 */
public class DesignLibraryResourcesDisplayContext {

	public DesignLibraryResourcesDisplayContext(
		CETManager cetManager,
		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry,
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_cetManager = cetManager;
		_frontendTokenDefinitionRegistry = frontendTokenDefinitionRegistry;
		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() {
		return "/o/search/v1.0/search?cmsRoot=true&cmsSection='files'" +
			"&emptySearch=true&filter=cmsRoot eq true and cmsSection eq " +
				"'files'&nestedFields=embedded&page=1&pageSize=20";
	}

	public Map<String, Object> getBreadcrumbProps(long designLibraryEntryId)
		throws PortalException {

		Group group = DepotEntryLocalServiceUtil.fetchDepotEntry(
			designLibraryEntryId
		).getGroup();

		return HashMapBuilder.<String, Object>put(
			"actionItems", _getActionItemsJSONArray(group, designLibraryEntryId)
		).put(
			"breadcrumbItems", _getBreadcrumbItemsJSONArray(group)
		).build();
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				_httpServletRequest,
				"click-new-to-create-or-import-your-design-resource")
		).put(
			"image", "/states/resources_empty_state.svg"
		).put(
			"title",
			LanguageUtil.get(_httpServletRequest, "no-design-resources-yet")
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				"#edit/{embedded.id}", "pencil", "edit",
				LanguageUtil.get(_httpServletRequest, "edit"), null, null,
				"link"),
			new FDSActionDropdownItem(
				"#remove/{embedded.id}", "trash", "remove",
				LanguageUtil.get(_httpServletRequest, "remove"), null, null,
				"link"));
	}

	public Map<String, Object> getFDSAdditionalProps(long designLibraryEntryId)
		throws PortalException {

		Group group = DepotEntryLocalServiceUtil.fetchDepotEntry(
			designLibraryEntryId
		).getGroup();

		long depotGroupId = group.getGroupId();

		if (!_hasManageStyleBookEntriesPermission(depotGroupId)) {
			return HashMapBuilder.<String, Object>put(
				"canAddStyleBook", false
			).build();
		}

		return HashMapBuilder.<String, Object>put(
			"addStyleBookEntryURL",
			_getAddStyleBookEntryURL(depotGroupId, designLibraryEntryId)
		).put(
			"canAddStyleBook", true
		).put(
			"frontendTokenDefinitionProviders",
			_getFrontendTokenDefinitionProviders()
		).put(
			"styleBookNamespace",
			PortalUtil.getPortletNamespace(StyleBookPortletKeys.STYLE_BOOK)
		).build();
	}

	private JSONArray _getActionItemsJSONArray(
			Group group, long designLibraryEntryId)
		throws PortalException {

		return JSONUtil.putAll(
			JSONUtil.put(
				"href",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setMVCRenderCommandName(
					"/design_library/design_library_settings"
				).setParameter(
					DesignLibraryConstants.DESIGN_LIBRARY_ENTRY_ID_KEY,
					designLibraryEntryId
				).buildString()
			).put(
				"label", LanguageUtil.get(_httpServletRequest, "settings")
			).put(
				"symbolLeft", "cog"
			),
			JSONUtil.put(
				"externalReferenceCode", group.getExternalReferenceCode()
			).put(
				"href", "#connected-sites"
			).put(
				"label",
				LanguageUtil.get(_httpServletRequest, "connected-sites")
			).put(
				"symbolLeft", "globe"
			).put(
				"target", "connected-sites"
			),
			JSONUtil.put(
				"href", "#manage-members"
			).put(
				"label", LanguageUtil.get(_httpServletRequest, "manage-members")
			).put(
				"symbolLeft", "users"
			),
			JSONUtil.put(
				"href", "#import"
			).put(
				"label", LanguageUtil.get(_httpServletRequest, "import")
			).put(
				"symbolLeft", "import"
			),
			JSONUtil.put(
				"href", "#export"
			).put(
				"label", LanguageUtil.get(_httpServletRequest, "export")
			).put(
				"symbolLeft", "export"
			),
			JSONUtil.put(
				"descriptiveName", group.getDescriptiveName()
			).put(
				"href",
				"/o/headless-asset-library/v1.0/asset-libraries/" +
					group.getExternalReferenceCode()
			).put(
				"label", LanguageUtil.get(_httpServletRequest, "delete")
			).put(
				"redirect",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).buildString()
			).put(
				"symbolLeft", "trash"
			).put(
				"target", "delete"
			));
	}

	private String _getAddStyleBookEntryURL(
		long depotGroupId, long designLibraryEntryId) {

		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_httpServletRequest, StyleBookPortletKeys.STYLE_BOOK,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/style_book/add_style_book_entry"
		).setRedirect(
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCRenderCommandName(
				"/design_library/design_library_resources"
			).setParameter(
				DesignLibraryConstants.DESIGN_LIBRARY_ENTRY_ID_KEY,
				designLibraryEntryId
			).buildString()
		).setParameter(
			"groupId", depotGroupId
		).buildString();
	}

	private JSONArray _getBreadcrumbItemsJSONArray(Group group) {
		return JSONUtil.putAll(
			JSONUtil.put(
				"active", false
			).put(
				"href",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).buildString()
			).put(
				"label",
				LanguageUtil.get(_httpServletRequest, "design-libraries")
			),
			JSONUtil.put(
				"active", true
			).put(
				"href", "#top"
			).put(
				"label", group.getName(_httpServletRequest.getLocale())
			));
	}

	private List<Map<String, Object>> _getFrontendTokenDefinitionProviders() {
		List<Map<String, Object>> frontendTokenDefinitionProviders =
			new ArrayList<>();

		for (FrontendTokenDefinition frontendTokenDefinition :
				_frontendTokenDefinitionRegistry.getFrontendTokenDefinitions(
					_themeDisplay.getCompanyId())) {

			if (Objects.equals(
					frontendTokenDefinition.getThemeType(),
					FrontendTokenDefinitionConstants.
						THEME_TYPE_THEME_CSS_CET)) {

				CET cet = _cetManager.getCET(
					_themeDisplay.getCompanyId(),
					frontendTokenDefinition.getThemeId());

				ThemeCSSCET themeCSSCET = (ThemeCSSCET)cet;

				if (StringUtil.equalsIgnoreCase(
						themeCSSCET.getScope(), "controlPanel")) {

					continue;
				}
			}

			frontendTokenDefinitionProviders.add(
				HashMapBuilder.<String, Object>put(
					"name",
					StyleBookUtil.getThemeName(
						_themeDisplay.getCompanyId(), _themeDisplay.getLocale(),
						frontendTokenDefinition.getThemeId())
				).put(
					"themeId", frontendTokenDefinition.getThemeId()
				).build());
		}

		return frontendTokenDefinitionProviders;
	}

	private boolean _hasManageStyleBookEntriesPermission(long groupId) {
		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionSnapshot.get();

		if (portletResourcePermission == null) {
			return false;
		}

		return portletResourcePermission.contains(
			_themeDisplay.getPermissionChecker(), groupId,
			StyleBookActionKeys.MANAGE_STYLE_BOOK_ENTRIES);
	}

	private static final Snapshot<PortletResourcePermission>
		_portletResourcePermissionSnapshot = new Snapshot<>(
			DesignLibraryResourcesDisplayContext.class,
			PortletResourcePermission.class,
			"(resource.name=" + StyleBookConstants.RESOURCE_NAME + ")");

	private final CETManager _cetManager;
	private final FrontendTokenDefinitionRegistry
		_frontendTokenDefinitionRegistry;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}