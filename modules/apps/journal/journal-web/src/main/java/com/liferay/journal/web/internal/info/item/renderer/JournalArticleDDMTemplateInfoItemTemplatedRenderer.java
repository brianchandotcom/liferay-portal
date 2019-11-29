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

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemTemplatedRenderer;
import com.liferay.info.item.renderer.template.InfoItemRendererTemplate;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
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
 * @author Eudaldo Alonso
 */
@Component(
	property = "service.ranking:Integer=300", service = InfoItemRenderer.class
)
public class JournalArticleDDMTemplateInfoItemTemplatedRenderer
	implements InfoItemTemplatedRenderer<JournalArticle> {

	@Override
	public List<InfoItemRendererTemplate> getInfoItemRendererTemplate(
		JournalArticle article, Locale locale) {

		List<InfoItemRendererTemplate> infoItemTemplates = new ArrayList<>();

		DDMStructure ddmStructure = article.getDDMStructure();

		for (DDMTemplate ddmTemplate : ddmStructure.getTemplates()) {
			infoItemTemplates.add(
				new InfoItemRendererTemplate(
					ddmTemplate.getName(locale), ddmTemplate.getTemplateKey()));
		}

		return infoItemTemplates;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "ddm-template");
	}

	@Override
	public void render(
		JournalArticle article, String templateKey,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (Validator.isNull(templateKey)) {
			render(article, httpServletRequest, httpServletResponse);

			return;
		}

		try {
			httpServletRequest.setAttribute(WebKeys.JOURNAL_ARTICLE, article);
			httpServletRequest.setAttribute(
				WebKeys.JOURNAL_TEMPLATE_ID, templateKey);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/info/item/renderer/ddm_template_article_renderer.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
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