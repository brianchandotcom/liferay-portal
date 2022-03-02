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

import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.object.model.ObjectField",
	service = ModelDocumentContributor.class
)
public class ObjectFieldModelDocumentContributor
	implements ModelDocumentContributor<ObjectField> {

	@Override
	public void contribute(Document document, ObjectField objectField) {
		document.addText(Field.NAME, objectField.getName());
		document.addLocalizedText(
			"label",
			LocalizationUtil.populateLocalizationMap(
				objectField.getLabelMap(), objectField.getDefaultLanguageId(),
				0));
		document.addKeyword(
			"objectDefinitionId", objectField.getObjectDefinitionId());
		document.remove(Field.USER_NAME);
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

}