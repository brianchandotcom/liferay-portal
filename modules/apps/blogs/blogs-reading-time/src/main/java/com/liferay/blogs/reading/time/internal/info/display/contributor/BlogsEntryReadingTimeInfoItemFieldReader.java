/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.reading.time.internal.info.display.contributor;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.field.reader.InfoItemFieldReader;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.reading.time.message.ReadingTimeMessageProvider;
import com.liferay.reading.time.model.ReadingTimeEntry;
import com.liferay.reading.time.service.ReadingTimeEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = InfoItemFieldReader.class)
public class BlogsEntryReadingTimeInfoItemFieldReader
	implements InfoItemFieldReader<BlogsEntry> {

	@Override
	public InfoField getInfoField() {
		return InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			BlogsEntry.class.getSimpleName()
		).name(
			"readingTime"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "descriptive-reading-time")
		).build();
	}

	@Override
	public Object getValue(BlogsEntry blogsEntry) {
		return InfoLocalizedValue.function(
			locale -> {
				ReadingTimeEntry readingTimeEntry =
					_readingTimeEntryLocalService.fetchOrAddReadingTimeEntry(
						blogsEntry);

				return _readingTimeMessageProvider.provide(
					readingTimeEntry, locale);
			});
	}

	@Reference
	private ReadingTimeEntryLocalService _readingTimeEntryLocalService;

	@Reference(target = "(display.style=descriptive)")
	private ReadingTimeMessageProvider _readingTimeMessageProvider;

}