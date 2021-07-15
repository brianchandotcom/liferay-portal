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

import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(immediate = true, service = InfoItemFormVariationsProvider.class)
public class ObjectEntryInfoItemFormVariationsProvider
	implements InfoItemFormVariationsProvider<ObjectEntry> {

	@Override
	public Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long groupId) {

		List<InfoItemFormVariation> infoItemFormVariations = new ArrayList<>();

		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			infoItemFormVariations.add(
				new InfoItemFormVariation(
					String.valueOf(objectDefinition.getObjectDefinitionId()),
					InfoLocalizedValue.localize(
						ObjectEntryInfoItemFormVariationsProvider.class,
						objectDefinition.getName())));
		}

		return infoItemFormVariations;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}