/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.DueDateRangeFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.ProjectManagerSelectionFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.ProjectSponsorSelectionFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.StateSelectionFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.util.ActionUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ViewProjectsSectionDisplayContext
	extends BaseSectionDisplayContext {

	public ViewProjectsSectionDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition, UserLocalService userLocalService) {

		super(httpServletRequest, objectDefinition);

		_userLocalService = userLocalService;
	}

	public String getAPIURL() {
		StringBundler sb = new StringBundler(4);

		sb.append("/o/search/v1.0/search?emptySearch=true&");
		sb.append("filter=objectDefinitionId eq ");
		sb.append(objectDefinition.getObjectDefinitionId());
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
						Layout layout = themeDisplay.getLayout();

						if (layout == null) {
							return null;
						}

						return layout.getName(themeDisplay.getLocale(), true);
					}
				))
		).put(
			"hideSpace", true
		).build();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "createProject");
				dropdownItem.putData(
					"objectDefinitionId",
					String.valueOf(objectDefinition.getObjectDefinitionId()));
				dropdownItem.putData(
					"redirect",
					ActionUtil.getAddProjectURL(
						objectDefinition, themeDisplay));
				dropdownItem.putData(
					"title",
					objectDefinition.getLabel(themeDisplay.getLocale()));
				dropdownItem.setIcon("forms");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "new-project"));
			}
		).build();
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				httpServletRequest, "click-new-to-create-your-first-project")
		).put(
			"image", "/states/cmp_empty_state_projects.svg"
		).put(
			"title", LanguageUtil.get(httpServletRequest, "no-projects-yet")
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		String baseViewProjectURL = ActionUtil.getBaseViewProjectURL(
			objectDefinition, themeDisplay);

		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				StringBundler.concat(
					ActionUtil.getBaseEditProjectURL(
						objectDefinition, themeDisplay),
					"{embedded.id}?redirect=", themeDisplay.getURLCurrent()),
				"pencil", "edit", LanguageUtil.get(httpServletRequest, "edit"),
				"get", "update", null),
			new FDSActionDropdownItem(
				baseViewProjectURL + "{embedded.id}", "view", "actionLink",
				LanguageUtil.get(httpServletRequest, "view"), null, "get",
				null),
			new FDSActionDropdownItem(
				StringBundler.concat(
					"/o", objectDefinition.getRESTContextPath(),
					"/scopes/{embedded.scopeId}/by-external-reference-code",
					"/{embedded.externalReferenceCode}/subscribe"),
				"bell-on", "subscribe",
				LanguageUtil.get(httpServletRequest, "watch-project"), "post",
				"subscribe", "async"),
			new FDSActionDropdownItem(
				StringBundler.concat(
					"/o", objectDefinition.getRESTContextPath(),
					"/scopes/{embedded.scopeId}/by-external-reference-code",
					"/{embedded.externalReferenceCode}/unsubscribe"),
				"bell-off", "unsubscribe",
				LanguageUtil.get(httpServletRequest, "stop-watching-project"),
				"post", "unsubscribe", "async"),
			new FDSActionDropdownItem(
				null, "users", "view-members",
				LanguageUtil.get(httpServletRequest, "view-members"), null,
				"get", null),
			new FDSActionDropdownItem(
				null, "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), null, "delete",
				null));
	}

	public List<FDSFilter> getFDSFilters() {
		return ListUtil.fromArray(
			new DueDateRangeFDSFilter(),
			new ProjectManagerSelectionFDSFilter(_userLocalService),
			new ProjectSponsorSelectionFDSFilter(_userLocalService),
			new StateSelectionFDSFilter());
	}

	private final UserLocalService _userLocalService;

}