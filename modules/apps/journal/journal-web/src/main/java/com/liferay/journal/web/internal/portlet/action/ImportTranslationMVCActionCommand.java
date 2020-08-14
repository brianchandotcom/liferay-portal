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

import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.exception.XLIFFFileException;
import com.liferay.translation.importer.TranslationInfoItemFieldValuesImporter;
import com.liferay.translation.service.TranslationEntryService;

import java.io.InputStream;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/import_translation"
	},
	service = MVCActionCommand.class
)
public class ImportTranslationMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JournalArticle article = ActionUtil.getArticle(actionRequest);

		try {
			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(actionRequest);

			_checkExceededSizeLimit(uploadPortletRequest);

			String contentType = uploadPortletRequest.getContentType("file");

			_checkContentType(contentType);

			try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"file")) {

				_translationEntryService.addOrUpdateTranslationEntry(
					themeDisplay.getScopeGroupId(),
					new InfoItemReference(
						JournalArticle.class.getName(),
						article.getResourcePrimKey()),
					StreamUtil.toString(inputStream), contentType,
					ServiceContextFactory.getInstance(actionRequest));
			}
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass(), exception);

			actionResponse.setRenderParameter(
				"mvcPath", "/import_translation.jsp");

			if (exception instanceof XLIFFFileException) {
				hideDefaultErrorMessage(actionRequest);
			}
		}
	}

	private void _checkContentType(String contentType)
		throws XLIFFFileException {

		if (!Objects.equals("application/x-xliff+xml", contentType) &&
			!Objects.equals("application/xliff+xml", contentType)) {

			throw new XLIFFFileException.MustBeValid(
				"Unsupported content type: " + contentType);
		}
	}

	private void _checkExceededSizeLimit(HttpServletRequest httpServletRequest)
		throws PortalException {

		UploadException uploadException =
			(UploadException)httpServletRequest.getAttribute(
				WebKeys.UPLOAD_EXCEPTION);

		if (uploadException != null) {
			Throwable throwable = uploadException.getCause();

			if (uploadException.isExceededFileSizeLimit()) {
				throw new FileSizeException(throwable);
			}

			if (uploadException.isExceededLiferayFileItemSizeLimit()) {
				throw new LiferayFileItemException(throwable);
			}

			if (uploadException.isExceededUploadRequestSizeLimit()) {
				throw new UploadRequestSizeException(throwable);
			}

			throw new PortalException(throwable);
		}
	}

	@Reference(
		target = "(item.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private InfoItemFieldValuesUpdater<JournalArticle>
		_journalArticleInfoItemFieldValuesUpdater;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Portal _portal;

	@Reference
	private TranslationEntryService _translationEntryService;

	@Reference
	private TranslationInfoItemFieldValuesImporter
		_translationInfoItemFieldValuesImporter;

}