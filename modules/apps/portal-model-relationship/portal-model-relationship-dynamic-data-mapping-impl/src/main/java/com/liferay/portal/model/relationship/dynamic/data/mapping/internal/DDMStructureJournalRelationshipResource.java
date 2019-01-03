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
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.relationship.Relationship;
import com.liferay.portal.relationship.RelationshipResource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure",
	service = RelationshipResource.class
)
public class DDMStructureJournalRelationshipResource
	implements RelationshipResource<DDMStructure> {

	@Override
	public Relationship<DDMStructure> relationship(
		Relationship.Builder<DDMStructure> builder) {

		return builder.modelSupplier(
			structureId -> _ddmStructureLocalService.fetchStructure(structureId)
		).outboundMultiRelationship(
			this::_getStructureArticles
		).outboundMultiRelationship(
			this::_getStructureFolders
		).build();
	}

	private List<JournalArticle> _getStructureArticles(DDMStructure structure) {
		return _journalArticleLocalService.getArticlesByStructureId(
			structure.getGroupId(), structure.getStructureKey(), -1, -1, null);
	}

	private List<JournalFolder> _getStructureFolders(DDMStructure structure) {
		List<DDMStructureLink> structureLinks =
			_ddmStructureLinkLocalService.getStructureLinks(
				structure.getStructureId());

		Stream<DDMStructureLink> stream = structureLinks.stream();

		long classNameId = _classNameLocalService.getClassNameId(
			JournalFolder.class);

		return stream.filter(
			structureLink -> structureLink.getClassNameId() == classNameId
		).map(
			structureLink -> _journalFolderLocalService.fetchFolder(
				structureLink.getClassPK())
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalFolderLocalService _journalFolderLocalService;

}