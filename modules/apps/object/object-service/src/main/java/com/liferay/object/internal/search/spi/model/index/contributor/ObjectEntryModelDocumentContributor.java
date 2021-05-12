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

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.object.model.ObjectEntry",
	service = ModelDocumentContributor.class
)
public class ObjectEntryModelDocumentContributor
	implements ModelDocumentContributor<ObjectEntry> {

	@Override
	public void contribute(Document document, ObjectEntry objectEntry) {
		try {
			_contribute(document, objectEntry);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index object entry " +
						objectEntry.getObjectEntryId(),
					exception);
			}
		}
	}

	private void _contribute(Document document, ObjectEntry objectEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + document);
			_log.debug("Object entry " + objectEntry);
		}

		document.addKeyword(
			"objectDefinitionId", objectEntry.getObjectDefinitionId());

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry.getObjectEntryId());

		for (Map.Entry<String, Serializable> entry : values.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();

			if (value == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Object entry ", objectEntry.getObjectEntryId(),
							" has name \"", name, "\" with a null value"));
				}

				continue;
			}

			if (value instanceof BigDecimal) {
				document.addNumber(name, (BigDecimal)value);
			}
			else if (value instanceof Boolean) {
				document.addKeyword(name, (Boolean)value);
			}
			else if (value instanceof Date) {
				document.addDateSortable(name, (Date)value);
			}
			else if (value instanceof Double) {
				document.addNumberSortable(name, (Double)value);
			}
			else if (value instanceof Integer) {
				document.addNumberSortable(name, (Integer)value);
			}
			else if (value instanceof Long) {
				document.addNumberSortable(name, (Long)value);
			}
			else if (value instanceof String) {
				document.addKeywordSortable(name, (String)value);
				document.addText(name, (String)value);
			}
			else if (value instanceof byte[]) {
				document.addText(name, new String((byte[])value));
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Object entry ", objectEntry.getObjectEntryId(),
							" has name \"", name, "\" with unsupported value ",
							value));
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryModelDocumentContributor.class);

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}