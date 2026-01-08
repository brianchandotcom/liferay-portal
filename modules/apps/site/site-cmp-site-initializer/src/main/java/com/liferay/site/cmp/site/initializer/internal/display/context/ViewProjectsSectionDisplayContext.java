/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cmp.site.initializer.internal.util.ActionUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ViewProjectsSectionDisplayContext {

	public ViewProjectsSectionDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition) {

		_httpServletRequest = httpServletRequest;
		_objectDefinition = objectDefinition;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() {
		StringBundler sb = new StringBundler(4);

		sb.append("/o/search/v1.0/search?emptySearch=true&");
		sb.append("filter=objectDefinitionId eq ");
		sb.append(_objectDefinition.getObjectDefinitionId());
		sb.append("&nestedFields=embedded");

		return sb.toString();
	}

	public Map<String, Object> getBreadcrumbProps() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"breadcrumbItems",
			JSONUtil.putAll(
				JSONUtil.put(
					"active", false
				).put(
					"label",
					() -> {
						Layout layout = _themeDisplay.getLayout();

						if (layout == null) {
							return null;
						}

						return layout.getName(_themeDisplay.getLocale(), true);
					}
				))
		).put(
			"hideSpace", true
		).build();
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return Collections.emptyList();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "createProject");
				dropdownItem.putData(
					"objectDefinitionId",
					String.valueOf(_objectDefinition.getObjectDefinitionId()));
				dropdownItem.putData(
					"redirect",
					ActionUtil.getAddProjectURL(
						_objectDefinition, _themeDisplay));
				dropdownItem.putData(
					"title",
					_objectDefinition.getLabel(_themeDisplay.getLocale()));
				dropdownItem.setIcon("forms");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "new-project"));
			}
		).build();
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				_httpServletRequest, "click-new-to-create-your-first-project")
		).put(
			"image", "/states/cmp_empty_state_projects.svg"
		).put(
			"title", LanguageUtil.get(_httpServletRequest, "no-projects-yet")
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		String baseViewProjectURL = ActionUtil.getBaseViewProjectURL(
			_objectDefinition, _themeDisplay);

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				StringBundler.concat(
					ActionUtil.getBaseEditProjectURL(
						_objectDefinition, _themeDisplay),
					"{embedded.id}?redirect=", _themeDisplay.getURLCurrent()),
				"pencil", "edit", LanguageUtil.get(_httpServletRequest, "edit"),
				"get", "update", null),
			new FDSActionDropdownItem(
				baseViewProjectURL + "{embedded.id}", "view", "actionLink",
				LanguageUtil.get(_httpServletRequest, "view"), null, "get",
				null),
			new FDSActionDropdownItem(
				null, "users", "view-members",
				LanguageUtil.get(_httpServletRequest, "view-members"), null,
				"get", null),
			new FDSActionDropdownItem(
				null, "trash", "delete",
				LanguageUtil.get(_httpServletRequest, "delete"), null, "delete",
				null));
	}

	private final HttpServletRequest _httpServletRequest;
	private final ObjectDefinition _objectDefinition;
	private final ThemeDisplay _themeDisplay;

}