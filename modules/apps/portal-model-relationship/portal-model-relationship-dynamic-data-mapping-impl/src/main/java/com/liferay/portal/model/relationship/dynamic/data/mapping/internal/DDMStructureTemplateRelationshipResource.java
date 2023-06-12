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

package com.liferay.portal.model.relationship.dynamic.data.mapping.internal;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.relationship.Relationship;
import com.liferay.portal.relationship.RelationshipResource;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 *
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure",
	service = RelationshipResource.class
)
@Deprecated
public class DDMStructureTemplateRelationshipResource
	implements RelationshipResource<DDMStructure> {

	@Override
	public Relationship<DDMStructure> relationship(
		Relationship.Builder<DDMStructure> builder) {

		return builder.modelSupplier(
			structureId -> _ddmStructureLocalService.fetchStructure(structureId)
		).outboundMultiRelationship(
			this::_getStructureTemplates
		).build();
	}

	private List<DDMTemplate> _getStructureTemplates(DDMStructure structure) {
		return _ddmTemplateLocalService.getTemplates(
			structure.getGroupId(),
			_classNameLocalService.getClassNameId(DDMStructure.class));
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

}