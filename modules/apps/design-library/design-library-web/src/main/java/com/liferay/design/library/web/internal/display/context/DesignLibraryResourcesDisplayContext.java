/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.display.context;

import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Prates
 */
public class DesignLibraryResourcesDisplayContext {

	public DesignLibraryResourcesDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public String getAPIURL() {
		return "/o/search/v1.0/search?cmsRoot=true&cmsSection='files'" +
			"&emptySearch=true&filter=cmsRoot eq true and cmsSection eq " +
				"'files'&nestedFields=embedded&page=1&pageSize=20";
	}

	public Map<String, Object> getBreadcrumbProps(long designLibraryEntryId)
		throws PortalException {

		Group group = _getGroup(designLibraryEntryId);

		return HashMapBuilder.<String, Object>put(
			"actionItems", _getActionItemsJSONArray(group)
		).put(
			"breadcrumbItems",
			_getBreadcrumbItemsJSONArray(group)
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

	private JSONArray _getActionItemsJSONArray(Group group) throws PortalException {
		return JSONUtil.putAll(
			JSONUtil.put(
				"href", "#settings"
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
				"href",
				StringBundler.concat(
					"/o/headless-asset-library/v1.0",
					"/asset-libraries/",
					group.getExternalReferenceCode())
			).put(
				"descriptiveName", group.getDescriptiveName()
			).put(
				"redirect",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).buildString()
			).put(
				"label", LanguageUtil.get(_httpServletRequest, "delete")
			).put(
				"symbolLeft", "trash"
			).put(
				"target", "delete"
			));
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
				"label",
				group.getName(
					_httpServletRequest.getLocale()
				)
			));
	}

	private Group _getGroup(long designLibraryEntryId) throws PortalException {
		return DepotEntryLocalServiceUtil.fetchDepotEntry(
					designLibraryEntryId
				).getGroup();
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}