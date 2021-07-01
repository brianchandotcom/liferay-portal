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

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.web.internal.info.item.ObjectEntryInfoItemFields;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Guilherme Camacho
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class ObjectEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<ObjectEntry> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(ObjectEntry objectEntry) {
		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getObjectEntryInfoFieldValues(objectEntry)
		).infoItemReference(
			new InfoItemReference(
				ObjectEntry.class.getName(), objectEntry.getObjectEntryId())
		).build();
	}

	private List<InfoFieldValue<Object>> _getObjectEntryInfoFieldValues(
		ObjectEntry objectEntry) {

		List<InfoFieldValue<Object>> objectEntryFieldValues = new ArrayList<>();

		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.createDateInfoField,
				objectEntry.getCreateDate()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.modifiedDateInfoField,
				objectEntry.getModifiedDate()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.publishDateInfoField,
				objectEntry.getLastPublishDate()));
		objectEntryFieldValues.add(
			new InfoFieldValue<>(
				ObjectEntryInfoItemFields.userNameInfoField,
				objectEntry.getUserName()));

		return objectEntryFieldValues;
	}

}