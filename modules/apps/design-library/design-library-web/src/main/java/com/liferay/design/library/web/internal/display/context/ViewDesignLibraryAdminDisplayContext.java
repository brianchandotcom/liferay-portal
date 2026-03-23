/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.display.context;

import com.liferay.design.library.web.internal.constants.DesignLibraryConstants;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Mario Leandro
 */
public class ViewDesignLibraryAdminDisplayContext {

	public ViewDesignLibraryAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public String getAPIURL() {
		return "/o/headless-asset-library/v1.0/asset-libraries?filter=type " +
			"eq 'DesignLibrary'";
	}

	public Map<String, Object> getBreadcrumbProps() {
		return HashMapBuilder.<String, Object>put(
			"actionItems", _getActionItemsJSONArray()
		).put(
			"breadcrumbItems", _getBreadcrumbItemsJSONArray()
		).build();
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				_httpServletRequest,
				"click-new-to-create-your-first-design-library")
		).put(
			"image", "/states/design_library_empty_state.svg"
		).put(
			"title",
			LanguageUtil.get(_httpServletRequest, "no-design-libraries-yet")
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setMVCRenderCommandName(
					"/design_library/design_library_resources"
				).setParameter(
					DesignLibraryConstants.DESIGN_LIBRARY_ENTRY_ID_KEY, "{id}"
				).buildString(),
				"pencil", "edit", LanguageUtil.get(_httpServletRequest, "edit"),
				null, null, "link"),
			new FDSActionDropdownItem(
				"{actions.delete.href}", "trash", "delete",
				LanguageUtil.get(_httpServletRequest, "delete"), "delete",
				"delete", null));
	}

	public Map<String, Object> getFDSAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"entryIdKey", DesignLibraryConstants.DESIGN_LIBRARY_ENTRY_ID_KEY
		).put(
			"redirectURL",
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCRenderCommandName(
				"/design_library/design_library_resources"
			).buildString()
		).build();
	}

	private JSONArray _getActionItemsJSONArray() {
		return JSONUtil.putAll(
			JSONUtil.put(
				"href", "#import"
			).put(
				"symbolLeft", "import"
			).put(
				"title", LanguageUtil.get(_httpServletRequest, "import")
			));
	}

	private JSONArray _getBreadcrumbItemsJSONArray() {
		return JSONUtil.putAll(
			JSONUtil.put(
				"active", true
			).put(
				"label",
				LanguageUtil.get(_httpServletRequest, "design-libraries")
			));
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}