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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.translation.importer.TranslationInfoItemFieldValuesImporter;
import com.liferay.translation.model.TranslationEntry;
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
	public InfoItemFieldValues getInfoItemFieldValues(
			TranslationEntry translationEntry)
		throws IOException, PortalException {

		String content = translationEntry.getContent();

		return _translationInfoItemFieldValuesImporter.
			importInfoItemFieldValues(
				translationEntry.getGroupId(),
				new InfoItemClassPKReference(
					translationEntry.getClassName(),
					translationEntry.getClassPK()),
				new ByteArrayInputStream(content.getBytes()));
	}

	@Reference
	private TranslationInfoItemFieldValuesImporter
		_translationInfoItemFieldValuesImporter;

}