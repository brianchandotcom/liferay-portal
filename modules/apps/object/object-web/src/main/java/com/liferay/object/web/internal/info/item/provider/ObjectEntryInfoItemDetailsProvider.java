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

import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Objects;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryInfoItemDetailsProvider
	implements InfoItemDetailsProvider<ObjectEntry> {

	public ObjectEntryInfoItemDetailsProvider(
		ObjectDefinition objectDefinition) {

		_objectDefinition = objectDefinition;
	}

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(
			_objectDefinition.getClassName(),
			InfoLocalizedValue.<String>builder(
			).defaultLocale(
				LocaleUtil.fromLanguageId(
					_objectDefinition.getDefaultLanguageId())
			).values(
				_objectDefinition.getLabelMap()
			).build());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(ObjectEntry objectEntry) {
		if (Objects.equals(
				_objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE)) {

			return new InfoItemDetails(
				getInfoItemClassDetails(),
				new InfoItemReference(
					_objectDefinition.getClassName(),
					new ERCInfoItemIdentifier(
						objectEntry.getExternalReferenceCode())));
		}

		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				_objectDefinition.getClassName(),
				objectEntry.getObjectEntryId()));
	}

	private final ObjectDefinition _objectDefinition;

}