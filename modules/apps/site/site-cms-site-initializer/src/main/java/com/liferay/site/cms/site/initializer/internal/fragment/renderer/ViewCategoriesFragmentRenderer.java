/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR
 * LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.cms.site.initializer.internal.display.context.ViewCategoriesDisplayContext;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = FragmentRenderer.class)
public class ViewCategoriesFragmentRenderer
	extends BaseSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "categories";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "categories");
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/view_categories.jsp");

			httpServletRequest.setAttribute(
				ViewCategoriesDisplayContext.class.getName(),
				new ViewCategoriesDisplayContext(
					_assetVocabularyLocalService, httpServletRequest,
					_jsonFactory, _layoutLocalService, _language, _portal));

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.cms.site.initializer)"
	)
	private ServletContext _servletContext;

}