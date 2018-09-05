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

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.content.space.apio.architect.identifier.ContentSpaceIdentifier;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.forms.apio.architect.identifier.StructureIdentifier;
import com.liferay.forms.apio.internal.helper.FormStructureRepresentorBuilderHelper;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameService;

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

	@Override
	public NestedCollectionRoutes<DDMStructure, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<DDMStructure, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "form-structures";
	}

	@Override
	public ItemRoutes<DDMStructure, Long> itemRoutes(
		ItemRoutes.Builder<DDMStructure, Long> builder) {

		return builder.addGetter(
			_ddmStructureLocalService::getStructure
		).build();
	}

	@Override
	public Representor<DDMStructure> representor(
		Representor.Builder<DDMStructure, Long> builder) {

		Representor.FirstStep<DDMStructure> ddmStructureFirstStep =
			_formStructureRepresentorBuilderHelper.buildDDMStructureFirstStep(
				builder);

		return ddmStructureFirstStep.build();
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

}