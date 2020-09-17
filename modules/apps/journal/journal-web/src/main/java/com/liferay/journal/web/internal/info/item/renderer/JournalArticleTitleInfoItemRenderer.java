/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.web.internal.info.item.renderer;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "service.ranking:Integer=150", service = InfoItemRenderer.class
)
public class JournalArticleTitleInfoItemRenderer
	implements InfoItemRenderer<JournalArticle> {

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "title");
	}

	@Override
	public void render(
		JournalArticle article, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
					JournalArticle.class);

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					article.getResourcePrimKey());

			httpServletRequest.setAttribute(
				WebKeys.ASSET_RENDERER, assetRenderer);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/info/item/renderer/title.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.journal.web)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

	private ServletContext _servletContext;

}