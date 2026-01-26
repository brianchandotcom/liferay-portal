/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Igor Franca
 */
public class ViewAssigneeSectionDisplayContext {

	public ViewAssigneeSectionDisplayContext(
		ClassNameLocalService classNameLocalService,
		HttpServletRequest httpServletRequest, Language language,
		ObjectEntry objectEntry, RoleLocalService roleLocalService,
		UserLocalService userLocalService, ThemeDisplay themeDisplay) {

		this.classNameLocalService = classNameLocalService;
		this.httpServletRequest = httpServletRequest;
		this.language = language;
		this.objectEntry = objectEntry;
		this.roleLocalService = roleLocalService;
		this.userLocalService = userLocalService;
		this.themeDisplay = themeDisplay;
	}

	public Map<String, Object> getProperties() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		Object assigneeValue = null;

		if (layoutDisplayPageObjectProvider != null) {
			Object displayObject =
				layoutDisplayPageObjectProvider.getDisplayObject();

			if (displayObject instanceof ObjectEntry) {
				ObjectEntry objectEntry = (ObjectEntry)displayObject;

				Map<String, Serializable> values = objectEntry.getValues();

				Map<String, Long> assignTo = (Map<String, Long>)values.get(
					"assignTo");

				ClassName className = classNameLocalService.fetchClassName(
					assignTo.get("classNameId"));

				if (className != null) {
					if (StringUtil.equals(
							className.getClassName(), Role.class.getName())) {

						Role role = roleLocalService.fetchRole(
							assignTo.get("classPK"));

						assigneeValue = HashMapBuilder.<String, Object>put(
							"externalReferenceCode",
							role.getExternalReferenceCode()
						).put(
							"name", role.getName()
						).put(
							"type", "role"
						).build();
					}
					else {
						User user = userLocalService.fetchUser(
							assignTo.get("classPK"));

						assigneeValue = HashMapBuilder.<String, Object>put(
							"externalReferenceCode",
							user.getExternalReferenceCode()
						).put(
							"image", user.getPortraitURL(themeDisplay)
						).put(
							"name", user.getFullName()
						).put(
							"type", "user"
						).build();
					}
				}
			}
		}

		return HashMapBuilder.<String, Object>put(
			"label", language.get(themeDisplay.getLocale(), "assignee")
		).put(
			"name", "ObjectField_assignTo"
		).put(
			"searchURL",
			themeDisplay.getPortalURL() + "/o/cmp/assignee-context/"
		).put(
			"triggerClassName", "form-control"
		).put(
			"value", assigneeValue
		).put(
			"visible", true
		).build();
	}

	protected final ClassNameLocalService classNameLocalService;
	protected final HttpServletRequest httpServletRequest;
	protected final Language language;
	protected final ObjectEntry objectEntry;
	protected final RoleLocalService roleLocalService;
	protected final ThemeDisplay themeDisplay;
	protected final UserLocalService userLocalService;

}