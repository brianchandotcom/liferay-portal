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
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemTemplatedRenderer;
import com.liferay.info.item.renderer.template.InfoItemRendererTemplate;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.staging.StagingGroupHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "service.ranking:Integer=100", service = InfoItemRenderer.class
)
public class JournalArticleDDMTemplateInfoItemTemplatedRenderer
	implements InfoItemTemplatedRenderer<JournalArticle> {

	@Override
	public List<InfoItemRendererTemplate> getInfoItemRendererTemplates(
		JournalArticle article, Locale locale) {

		List<InfoItemRendererTemplate> infoItemRendererTemplates =
			new ArrayList<>();

		DDMStructure ddmStructure = article.getDDMStructure();

		for (DDMTemplate ddmTemplate : ddmStructure.getTemplates()) {
			if (_stagingGroupHelper.isLiveGroup(ddmTemplate.getGroupId())) {
				continue;
			}

			infoItemRendererTemplates.add(
				new InfoItemRendererTemplate(
					ddmTemplate.getName(locale), ddmTemplate.getTemplateKey()));
		}

		return infoItemRendererTemplates;
	}

	@Override
	public List<InfoItemRendererTemplate> getInfoItemRendererTemplates(
		String className, String classTypeKey, Locale locale) {

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getClassStructures(
				CompanyThreadLocal.getCompanyId(),
				_portal.getClassNameId(className));

		if (Validator.isNotNull(classTypeKey)) {
			ddmStructures = ListUtil.filter(
				ddmStructures,
				ddmStructure -> Objects.equals(
					ddmStructure.getStructureId(),
					GetterUtil.getLong(classTypeKey)));
		}

		Stream<DDMStructure> stream = ddmStructures.stream();

		return stream.flatMap(
			ddmStructure -> {
				List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

				return ddmTemplates.stream();
			}
		).map(
			ddmTemplate -> new InfoItemRendererTemplate(
				ddmTemplate.getName(locale), ddmTemplate.getTemplateKey())
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public String getInfoItemRendererTemplatesGroupLabel(
		JournalArticle article, Locale locale) {

		DDMStructure ddmStructure = article.getDDMStructure();

		return ddmStructure.getName(locale);
	}

	@Override
	public String getInfoItemRendererTemplatesGroupLabel(
		String className, String classTypeKey, Locale locale) {

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getClassStructures(
				CompanyThreadLocal.getCompanyId(),
				_portal.getClassNameId(className));

		ddmStructures = ListUtil.filter(
			ddmStructures,
			ddmStructure -> Objects.equals(
				ddmStructure.getStructureId(),
				GetterUtil.getLong(classTypeKey)));

		if (ddmStructures.size() != 1) {
			return StringPool.BLANK;
		}

		DDMStructure ddmStructure = ddmStructures.get(0);

		return ddmStructure.getName(locale);
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "ddm-template");
	}

	@Override
	public void render(
		JournalArticle article, String templateKey,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (!JournalArticleRendererUtil.isShowArticle(
				httpServletRequest, article)) {

			return;
		}

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

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private ServletContext _servletContext;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}