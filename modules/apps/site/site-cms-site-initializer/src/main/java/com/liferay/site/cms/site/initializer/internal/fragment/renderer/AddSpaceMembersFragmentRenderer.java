/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.frontend.taglib.react.servlet.taglib.ComponentTag;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.taglib.servlet.PageContextFactoryUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albertinin Mourato Santos
 */
@Component(service = FragmentRenderer.class)
public class AddSpaceMembersFragmentRenderer
	extends BaseSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "add-members");
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.write("<div><span aria-hidden=\"true\" class=\"");
			printWriter.write("loading-animation\"></span>");

			ComponentTag componentTag = new ComponentTag();

			componentTag.setModule(
				"{AddSpaceMembers} from site-cms-site-initializer");
			componentTag.setPageContext(
				PageContextFactoryUtil.create(
					httpServletRequest, httpServletResponse));

			long assetLibraryId = ParamUtil.getLong(
				httpServletRequest, "assetLibraryId");

			componentTag.setProps(
				HashMapBuilder.<String, Object>put(
					"assetLibraryId", assetLibraryId
				).build());

			componentTag.setServletContext(_servletContext);

			componentTag.doStartTag();

			componentTag.doEndTag();

			printWriter.write("</div>");
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.cms.site.initializer)"
	)
	private ServletContext _servletContext;

}