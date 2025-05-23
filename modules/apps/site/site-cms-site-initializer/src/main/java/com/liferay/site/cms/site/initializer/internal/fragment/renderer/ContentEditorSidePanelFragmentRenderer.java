/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.frontend.taglib.react.servlet.taglib.ComponentTag;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PageContextFactoryUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sandro Chinea
 */
@Component(service = FragmentRenderer.class)
public class ContentEditorSidePanelFragmentRenderer
	extends BaseSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-editor";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "content-editor-side-panel");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write("<div><span aria-hidden=\"true\" class=\"");
			printWriter.write("loading-animation\"></span>");

			ComponentTag componentTag = new ComponentTag();

			componentTag.setModule(
				"{ContentEditorSidePanel} from site-cms-site-initializer");
			componentTag.setPageContext(
				PageContextFactoryUtil.create(
					httpServletRequest, httpServletResponse));
			componentTag.setProps(_getProps(httpServletRequest));
			componentTag.setServletContext(_servletContext);

			componentTag.doStartTag();

			componentTag.doEndTag();

			printWriter.write("</div>");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private Map<String, Object> _getProps(
		HttpServletRequest httpServletRequest) {

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider == null) {
			return Collections.emptyMap();
		}

		Object displayObject =
			layoutDisplayPageObjectProvider.getDisplayObject();

		if (!(displayObject instanceof ObjectEntry)) {
			return Collections.emptyMap();
		}

		ObjectEntry objectEntry = (ObjectEntry)displayObject;

		return HashMapBuilder.<String, Object>put(
			"id", String.valueOf(objectEntry.getObjectEntryId())
		).put(
			"type",
			() -> {
				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.fetchObjectDefinition(
						objectEntry.getObjectDefinitionId());

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return objectDefinition.getLabel(themeDisplay.getLocale());
			}
		).put(
			"version", () -> String.valueOf(objectEntry.getVersion())
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentEditorSidePanelFragmentRenderer.class);

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.cms.site.initializer)"
	)
	private ServletContext _servletContext;

}