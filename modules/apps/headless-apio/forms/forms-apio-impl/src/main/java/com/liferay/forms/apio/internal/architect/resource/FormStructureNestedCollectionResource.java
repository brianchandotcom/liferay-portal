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

package com.liferay.forms.apio.internal.architect.resource;

import static com.liferay.forms.apio.internal.util.LocalizedValueUtil.getLocalizedString;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.content.space.apio.architect.identifier.ContentSpaceIdentifier;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.forms.apio.architect.identifier.StructureIdentifier;
import com.liferay.forms.apio.internal.helper.FormStructureRepresentorBuilderHelper;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameService;
import com.liferay.structure.apio.architect.util.StructureRepresentorUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose Structure resources associated
 * to a DDMStructure through a web API. The resources are mapped from the
 * internal model {@code DDMStructure}.
 *
 * @author Paulo Cruz
 * @review
 */
@Component
public class FormStructureNestedCollectionResource
	implements NestedCollectionResource
		<DDMStructure, Long, StructureIdentifier, Long,
			ContentSpaceIdentifier> {

	public static DDMFormSuccessPageSettings getSuccessPage(
		DDMStructure ddmStructure) {

		DDMForm ddmForm = ddmStructure.getDDMForm();

		return ddmForm.getDDMFormSuccessPageSettings();
	}

	public static DDMStructureVersion getVersion(DDMStructure ddmStructure) {
		return Try.fromFallible(
			ddmStructure::getStructureVersion
		).orElse(
			null
		);
	}

	@Override
	public NestedCollectionRoutes<DDMStructure, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<DDMStructure, Long, Long> builder) {

		return builder.addGetter(this::_getPageItems).build();
	}

	@Override
	public String getName() {
		return "form-structures";
	}

	@Override
	public ItemRoutes<DDMStructure, Long> itemRoutes(
		ItemRoutes.Builder<DDMStructure, Long> builder) {

		return builder.addGetter(this::_getItem).build();
	}

	@Override
	public Representor<DDMStructure> representor(
		Representor.Builder<DDMStructure, Long> builder) {

		Representor.FirstStep<DDMStructure> ddmStructureFirstStep =
			_formStructureRepresentorBuilderHelper.buildDDMStructureFirstStep(
				builder);

		Representor.FirstStep<DDMStructure> bidirectionalModelStepBuilder =
			ddmStructureFirstStep.addBidirectionalModel(
				"contentSpace", "formStructures", ContentSpaceIdentifier.class,
				DDMStructure::getGroupId);

		bidirectionalModelStepBuilder.addNested(
			"version", FormStructureNestedCollectionResource::getVersion,
			FormStructureNestedCollectionResource::_buildVersion
		).addNested(
			"successPage",
			FormStructureNestedCollectionResource::getSuccessPage,
			FormStructureNestedCollectionResource::_buildSuccessPageSettings
		).addNestedList(
			"fields", _structureRepresentorUtil::getPages,
			ddmFormFieldBuilder ->
				_formStructureRepresentorBuilderHelper.buildFormPages(
					ddmFormFieldBuilder).build()
		);

		return bidirectionalModelStepBuilder.build();
	}

	private static NestedRepresentor<DDMFormSuccessPageSettings>
		_buildSuccessPageSettings(
			NestedRepresentor.Builder<DDMFormSuccessPageSettings> builder) {

		return builder.types(
			"FormSuccessPageSettings"
		).addBoolean(
			"isEnabled", DDMFormSuccessPageSettings::isEnabled
		).addLocalizedStringByLocale(
			"headline", getLocalizedString(DDMFormSuccessPageSettings::getTitle)
		).addLocalizedStringByLocale(
			"text", getLocalizedString(DDMFormSuccessPageSettings::getBody)
		).build();
	}

	private static NestedRepresentor<DDMStructureVersion> _buildVersion(
		NestedRepresentor.Builder<DDMStructureVersion> nestedBuilder) {

		return nestedBuilder.types(
			"StructureVersion"
		).addLinkedModel(
			"creator", PersonIdentifier.class, DDMStructureVersion::getUserId
		).addString(
			"name", DDMStructureVersion::getVersion
		).build();
	}

	private DDMStructure _getItem(Long structureId) throws PortalException {
		return _ddmStructureLocalService.getStructure(structureId);
	}

	private PageItems<DDMStructure> _getPageItems(
		Pagination pagination, Long groupId) {

		ClassName className = _classNameService.fetchClassName(
			DDLRecordSet.class.getName());

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getStructures(
				groupId, className.getClassNameId(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);
		int count = _ddmStructureLocalService.getStructuresCount(
			groupId, className.getClassNameId());

		return new PageItems<>(ddmStructures, count);
	}

	@Reference
	private ClassNameService _classNameService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private FormStructureRepresentorBuilderHelper
		_formStructureRepresentorBuilderHelper;

	@Reference
	private StructureRepresentorUtil _structureRepresentorUtil;

}