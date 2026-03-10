/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.display.context;

import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Gabriel Prates
 */
public class DesignLibraryDashboardDisplayContext {

	public DesignLibraryDashboardDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public String getAPIURL() {
		return StringBundler.concat(
			"/o/search/v1.0/search?page=1&pageSize=20&emptySearch=true",
			"&filter=cmsRoot eq true and cmsSection eq 'files' and status in ",
			"(0, 2, 3, 1, 7)&nestedFields=embedded,embeddedTaxonomyCategory,",
			"file.metadata,file.previewURL,file.thumbnailURL,",
			"numberOfObjectEntries,numberOfObjectEntryFolders,",
			"systemProperties.objectDefinitionBrief");
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

	public Map<String, Object> getHeaderProps(long designLibraryEntryId)
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"actionItems", _getActionItemsJSONArray()
		).put(
			"breadcrumbProps", _getBreadcrumbProps(designLibraryEntryId)
		).build();
	}

	private JSONArray _getActionItemsJSONArray() {
		return JSONUtil.putAll(
			JSONUtil.put(
				"href", "#settings"
			).put(
				"symbolLeft", "cog"
			).put(
				"title", LanguageUtil.get(_httpServletRequest, "settings")
			),
			JSONUtil.put(
				"href", "#connected-sites"
			).put(
				"symbolLeft", "globe"
			).put(
				"title",
				LanguageUtil.get(_httpServletRequest, "connected-sites")
			),
			JSONUtil.put(
				"href", "#manage-members"
			).put(
				"symbolLeft", "users"
			).put(
				"title", LanguageUtil.get(_httpServletRequest, "manage-members")
			),
			JSONUtil.put(
				"href", "#import"
			).put(
				"symbolLeft", "import"
			).put(
				"title", LanguageUtil.get(_httpServletRequest, "import")
			),
			JSONUtil.put(
				"href", "#export"
			).put(
				"symbolLeft", "export"
			).put(
				"title", LanguageUtil.get(_httpServletRequest, "export")
			),
			JSONUtil.put(
				"href", "#delete"
			).put(
				"symbolLeft", "trash"
			).put(
				"title", LanguageUtil.get(_httpServletRequest, "delete")
			));
	}

	private Map<String, Object> _getBreadcrumbProps(long designLibraryEntryId) {
		return HashMapBuilder.<String, Object>put(
			"current",
			() -> HashMapBuilder.<String, Object>put(
				"active", true
			).put(
				"href", "#top"
			).put(
				"label",
				DepotEntryLocalServiceUtil.fetchDepotEntry(
					designLibraryEntryId
				).getGroup(
				).getName(
					_httpServletRequest.getLocale()
				)
			).build()
		).put(
			"redirect",
			HashMapBuilder.<String, Object>put(
				"active", false
			).put(
				"href",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).buildString()
			).put(
				"label",
				LanguageUtil.get(_httpServletRequest, "design-libraries")
			).build()
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}