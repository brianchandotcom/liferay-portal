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

package com.liferay.translation.internal.util;

import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.importer.TranslationInfoItemFieldValuesImporter;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;
import com.liferay.translation.util.TranslationEntryInfoItemFieldValuesHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = TranslationEntryInfoItemFieldValuesHelper.class)
public class TranslationEntryInfoItemFieldValuesHelperImpl
	implements TranslationEntryInfoItemFieldValuesHelper {

	@Override
	public TranslationEntry addOrUpdateTranslationEntry(
		long groupId, InfoItemClassPKReference infoItemClassPKReference, InfoItemFieldValues infoItemFieldValues, String languageId, ServiceContext serviceContext)
		throws IOException {
		return _translationEntryLocalService.addOrUpdateTranslationEntry(
			groupId,
			infoItemClassPKReference.getClassName(),
			infoItemClassPKReference.getClassPK(),
			languageId,
			StreamUtil.toString(
				_xliffTranslationInfoItemFieldValuesExporter.
					exportInfoItemFieldValues(
						infoItemFieldValues, LocaleUtil.getDefault(),
						LocaleUtil.fromLanguageId(languageId))),
			_xliffTranslationInfoItemFieldValuesExporter.getMimeType(),
			serviceContext);
	}

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
			TranslationEntry translationEntry)
		throws IOException, PortalException {

		String content = translationEntry.getContent();

		return _xliffTranslationInfoItemFieldValuesImporter.
			importInfoItemFieldValues(
				translationEntry.getGroupId(),
				new InfoItemClassPKReference(
					translationEntry.getClassName(),
					translationEntry.getClassPK()),
				new ByteArrayInputStream(content.getBytes()));
	}

	@Reference(target = "(content.type=application/xliff+xml)")
	private TranslationInfoItemFieldValuesImporter
		_xliffTranslationInfoItemFieldValuesImporter;

	@Reference(target = "(content.type=application/xliff+xml)")
	private TranslationInfoItemFieldValuesExporter
		_xliffTranslationInfoItemFieldValuesExporter;

	@Reference
	private TranslationEntryLocalService _translationEntryLocalService;
}