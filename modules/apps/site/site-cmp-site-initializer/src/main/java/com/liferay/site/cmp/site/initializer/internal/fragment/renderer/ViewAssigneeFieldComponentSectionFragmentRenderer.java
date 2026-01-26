/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Franca
 */
@Component(service = FragmentRenderer.class)
public class ViewAssigneeFieldComponentSectionFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "assignee-field";
	}

	@Override
	protected String getComponentName() {
		return "Assignee";
	}

	@Override
	protected String getLabelKey() {
		return "assignee-field";
	}

	@Override
	protected String getModuleName() {
		return "object-dynamic-data-mapping-form-field-type";
	}

	@Override
	protected Map<String, Object> getProps(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return HashMapBuilder.<String, Object>put(
			"customClasses", "form-control"
		).put(
			"label", _language.get(themeDisplay.getLocale(), "assignee")
		).put(
			"name", "ObjectField_assignTo"
		).put(
			"searchURL",
			themeDisplay.getPortalURL() + "/o/cmp/assignee-context/"
		).put(
			"value",
			() -> {
				LayoutDisplayPageObjectProvider<?>
					layoutDisplayPageObjectProvider =
						(LayoutDisplayPageObjectProvider<?>)
							httpServletRequest.getAttribute(
								LayoutDisplayPageWebKeys.
									LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

				if (layoutDisplayPageObjectProvider == null) {
					return null;
				}

				Object displayObject =
					layoutDisplayPageObjectProvider.getDisplayObject();

				if (!(displayObject instanceof ObjectEntry)) {
					return null;
				}

				ObjectEntry objectEntry = (ObjectEntry)displayObject;

				Map<String, Serializable> values = objectEntry.getValues();

				Map<String, Long> assignTo = (Map<String, Long>)values.get(
					"assignTo");

				ClassName className = _classNameLocalService.fetchClassName(
					assignTo.get("classNameId"));

				if (className == null) {
					return null;
				}

				if (StringUtil.equals(
						className.getClassName(), Role.class.getName())) {

					Role role = _roleLocalService.fetchRole(
						assignTo.get("classPK"));

					return HashMapBuilder.<String, Object>put(
						"externalReferenceCode", role.getExternalReferenceCode()
					).put(
						"name", role.getName()
					).put(
						"type", "role"
					).build();
				}

				User user = _userLocalService.fetchUser(
					assignTo.get("classPK"));

				return HashMapBuilder.<String, Object>put(
					"externalReferenceCode", user.getExternalReferenceCode()
				).put(
					"image", user.getPortraitURL(themeDisplay)
				).put(
					"name", user.getFullName()
				).put(
					"type", "user"
				).build();
			}
		).put(
			"visible", true
		).build();
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Language _language;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}