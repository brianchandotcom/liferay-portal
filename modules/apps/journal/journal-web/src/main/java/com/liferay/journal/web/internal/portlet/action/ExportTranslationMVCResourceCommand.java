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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormProviderTracker;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.translation.exporter.TranslationInfoFormValuesExporter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/export_translation"
	},
	service = MVCResourceCommand.class
)
public class ExportTranslationMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			JournalArticle article = _journalArticleLocalService.getArticle(
				themeDisplay.getScopeGroupId(),
				ParamUtil.getString(resourceRequest, "articleId"));

			InfoItemFormProvider infoItemFormProvider =
				_infoItemFormProviderTracker.getInfoItemFormProvider(
					JournalArticle.class.getName());

			ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

			for (String targetLanguageId :
					ParamUtil.getStringValues(
						resourceRequest, "targetLanguageIds")) {

				zipWriter.addEntry(
					"/" + targetLanguageId + ".xliff",
					_translationInfoFormValuesExporter.export(
						infoItemFormProvider.getInfoFormValues(article),
						LocaleUtil.fromLanguageId(
							ParamUtil.getString(
								resourceRequest, "sourceLanguageId")),
						LocaleUtil.fromLanguageId(targetLanguageId)));
			}

			try (InputStream inputStream = new FileInputStream(
					zipWriter.getFile())) {

				PortletResponseUtil.sendFile(
					resourceRequest, resourceResponse,
					article.getTitle(themeDisplay.getLocale()) + ".zip",
					inputStream, ContentTypes.APPLICATION_ZIP);
			}

			return false;
		}
		catch (IOException | PortalException exception) {
			throw new PortletException(exception);
		}
	}

	@Reference
	private InfoItemFormProviderTracker _infoItemFormProviderTracker;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private TranslationInfoFormValuesExporter
		_translationInfoFormValuesExporter;

}