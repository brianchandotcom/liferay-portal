/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cmp.site.initializer.internal.util.ActionUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Franca
 */
@Component(service = FragmentRenderer.class)
public class EditorToolbarComponentSectionFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "project-editor";
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (Objects.equals(layoutMode, Constants.READ)) {
			return;
		}

		super.render(
			fragmentRendererContext, httpServletRequest, httpServletResponse);
	}

	@Override
	protected String getComponentName() {
		return "EditorToolbar";
	}

	@Override
	protected String getLabelKey() {
		return "editor-management-bar";
	}

	@Override
	protected String getModuleName() {
		return "site-cmp-site-initializer";
	}

	@Override
	protected Map<String, Object> getProps(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		ObjectEntry objectEntry = _getObjectEntry(httpServletRequest);

		if (objectEntry == null) {
			return Collections.emptyMap();
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return HashMapBuilder.<String, Object>put(
			"backURL", ParamUtil.getString(httpServletRequest, "redirect")
		).put(
			"title",
			() -> {
				if (Objects.equals(
						objectDefinition.getExternalReferenceCode(),
						"L_CMP_PROJECT")) {

					return LanguageUtil.get(
						themeDisplay.getLocale(), "new-project");
				}

				return LanguageUtil.get(themeDisplay.getLocale(), "new-task");
			}
		).put(
			"viewProjectURL",
			() -> {
				if (!objectEntry.isDraft()) {
					return null;
				}

				String viewProjectURL = ActionUtil.getBaseViewProjectURL(
					_objectDefinitionLocalService.
						getObjectDefinitionByExternalReferenceCode(
							"L_CMP_PROJECT", themeDisplay.getCompanyId()),
					themeDisplay);

				if (Objects.equals(
						objectDefinition.getExternalReferenceCode(),
						"L_CMP_PROJECT")) {

					return viewProjectURL + objectEntry.getObjectEntryId();
				}

				ObjectEntry parentObjectEntry =
					_objectEntryLocalService.fetchObjectEntry(
						MapUtil.getLong(
							objectEntry.getValues(),
							"r_cmpProjectToCMPTasks_c_cmpProjectId"));

				if (parentObjectEntry == null) {
					return null;
				}

				return viewProjectURL + parentObjectEntry.getObjectEntryId();
			}
		).build();
	}

	private ObjectEntry _getObjectEntry(HttpServletRequest httpServletRequest) {
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider == null) {
			return null;
		}

		Object displayObject =
			layoutDisplayPageObjectProvider.getDisplayObject();

		if (!(displayObject instanceof ObjectEntry)) {
			return null;
		}

		return (ObjectEntry)displayObject;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}