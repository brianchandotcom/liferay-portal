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

package com.liferay.translation.web.internal.portlet.action;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.GroupKeyInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterTracker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Optional;

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
		"javax.portlet.name=" + TranslationPortletKeys.TRANSLATION,
		"mvc.command.name=/translation/export_translation"
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

			long classNameId = ParamUtil.getLong(
				resourceRequest, "classNameId");

			String className = _portal.getClassName(classNameId);

			InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class, className);

			InfoItemObjectProvider<Object> infoItemObjectProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class, className);

			Object object = infoItemObjectProvider.getInfoItem(
				_getInfoItemIdentifier(resourceRequest));

			InfoFieldValue<Object> infoFieldValue = _getTitleInfoFieldValue(
				infoItemFieldValuesProvider, object);

			String escapedTitle = StringUtil.removeSubstrings(
				(String)infoFieldValue.getValue(themeDisplay.getLocale()),
				PropsValues.DL_CHAR_BLACKLIST);

			String sourceLanguageId = ParamUtil.getString(
				resourceRequest, "sourceLanguageId");

			ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

			String exportMimeType = ParamUtil.getString(
				resourceRequest, "exportMimeType");

			Optional<TranslationInfoItemFieldValuesExporter>
				exportFileFormatOptional =
					_translationInfoItemFieldValuesExporterTracker.
						getTranslationInfoItemFieldValuesExporterOptional(
							exportMimeType);

			TranslationInfoItemFieldValuesExporter
				translationInfoItemFieldValuesExporter =
					exportFileFormatOptional.orElseThrow(
						() -> new PortalException(
							"Unknown export mime type: " + exportMimeType));

			for (String targetLanguageId :
					ParamUtil.getStringValues(
						resourceRequest, "targetLanguageIds")) {

				zipWriter.addEntry(
					StringBundler.concat(
						StringPool.FORWARD_SLASH, escapedTitle, StringPool.DASH,
						sourceLanguageId, StringPool.DASH, targetLanguageId,
						".xlf"),
					translationInfoItemFieldValuesExporter.
						exportInfoItemFieldValues(
							infoItemFieldValuesProvider.getInfoItemFieldValues(
								object),
							LocaleUtil.fromLanguageId(sourceLanguageId),
							LocaleUtil.fromLanguageId(targetLanguageId)));
			}

			try (InputStream inputStream = new FileInputStream(
					zipWriter.getFile())) {

				PortletResponseUtil.sendFile(
					resourceRequest, resourceResponse,
					StringBundler.concat(
						escapedTitle, StringPool.DASH, sourceLanguageId,
						".zip"),
					inputStream, ContentTypes.APPLICATION_ZIP);
			}

			return false;
		}
		catch (IOException | PortalException exception) {
			throw new PortletException(exception);
		}
	}

	private InfoItemIdentifier _getInfoItemIdentifier(
		ResourceRequest resourceRequest) {

		long groupId = ParamUtil.getLong(resourceRequest, "groupId");

		if (groupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			return new ClassPKInfoItemIdentifier(
				ParamUtil.getLong(resourceRequest, "key"));
		}

		return new GroupKeyInfoItemIdentifier(
			groupId, ParamUtil.getString(resourceRequest, "key"));
	}

	private InfoFieldValue<Object> _getTitleInfoFieldValue(
		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider,
		Object object) {

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValuesProvider.getInfoFieldValue(object, "title");

		if (infoFieldValue != null) {
			return infoFieldValue;
		}

		return infoItemFieldValuesProvider.getInfoFieldValue(object, "name");
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Portal _portal;

	@Reference
	private TranslationInfoItemFieldValuesExporterTracker
		_translationInfoItemFieldValuesExporterTracker;

}