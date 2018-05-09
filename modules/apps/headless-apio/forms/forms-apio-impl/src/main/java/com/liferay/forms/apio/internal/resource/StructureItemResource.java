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

package com.liferay.forms.apio.internal.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.forms.apio.architect.identifier.StructureIdentifier;
import com.liferay.person.apio.identifier.PersonIdentifier;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose Structure resources through a
 * web API. The resources are mapped from the internal model {@code
 * DDMStructure}.
 *
 * @author Paulo Cruz
 */
@Component(immediate = true)
public class StructureItemResource
	implements ItemResource<DDMStructure, Long, StructureIdentifier> {

	@Override
	public String getName() {
		return "structures";
	}

	@Override
	public ItemRoutes<DDMStructure, Long> itemRoutes(
		ItemRoutes.Builder<DDMStructure, Long> builder) {

		return builder.addGetter(
			_ddmStructureLocalService::getStructure
		).build();
	}

	@Override
	public Representor<DDMStructure, Long> representor(
		Representor.Builder<DDMStructure, Long> builder) {

		return builder.types(
			"Structure"
		).identifier(
			DDMStructure::getStructureId
		).addDate(
			"dateCreated", DDMStructure::getCreateDate
		).addDate(
			"dateModified", DDMStructure::getCreateDate
		).addDate(
			"datePublished", DDMStructure::getLastPublishDate
		).addLinkedModel(
			"author", PersonIdentifier.class, DDMStructure::getUserId
		).addLinkedModel(
			"structure", StructureIdentifier.class,
			DDMStructure::getParentStructureId
		).addNested(
			"successPage", this::_getSuccessPage,
			nestedBuilder -> nestedBuilder.nestedTypes(
				"FormSuccessPageSettings"
			).addBoolean(
				"isEnabled", DDMFormSuccessPageSettings::isEnabled
			).addLocalizedStringByLocale(
				"headline",
				(ddmFormSuccessPageSettings, locale) ->
					ddmFormSuccessPageSettings.getTitle().getString(locale)
			).addLocalizedStringByLocale(
				"text",
				(ddmFormSuccessPageSettings, locale) ->
					ddmFormSuccessPageSettings.getBody().getString(locale)
			).build()
		).addNested(
			"version", this::_getVersion,
			nestedBuilder -> nestedBuilder.nestedTypes(
				"StructureVersion"
			).addLinkedModel(
				"author", PersonIdentifier.class, DDMStructureVersion::getUserId
			).addString(
				"name", DDMStructureVersion::getVersion
			).build()
		).addLocalizedStringByLocale(
			"description", DDMStructure::getDescription
		).addLocalizedStringByLocale(
			"name", DDMStructure::getName
		).addNumber(
			"additionalType", DDMStructure::getType
		).addString(
			"definition", DDMStructure::getDefinition
		).addString(
			"storageType", DDMStructure::getStorageType
		).addString(
			"structureKey", DDMStructure::getStructureKey
		).build();
	}

	private DDMFormSuccessPageSettings _getSuccessPage(
		DDMStructure ddmStructure) {

		DDMForm ddmForm = ddmStructure.getDDMForm();

		return ddmForm.getDDMFormSuccessPageSettings();
	}

	private DDMStructureVersion _getVersion(DDMStructure ddmStructure) {
		return Try.fromFallible(
			ddmStructure::getStructureVersion
		).orElse(
			null
		);
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}