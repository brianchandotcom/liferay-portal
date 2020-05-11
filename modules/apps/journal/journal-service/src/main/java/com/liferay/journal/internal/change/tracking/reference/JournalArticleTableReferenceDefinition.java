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

package com.liferay.journal.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.dynamic.data.mapping.model.DDMTemplateLinkTable;
import com.liferay.dynamic.data.mapping.model.DDMTemplateTable;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResourceTable;
import com.liferay.journal.model.JournalArticleTable;
import com.liferay.journal.model.JournalFolderTable;
import com.liferay.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class JournalArticleTableReferenceDefinition
	implements TableReferenceDefinition<JournalArticleTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<JournalArticleTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			JournalArticleTable.INSTANCE
		).nonreferenceColumn(
			JournalArticleTable.INSTANCE.uuid
		).singleColumnReference(
			JournalArticleTable.INSTANCE.resourcePrimKey,
			JournalArticleResourceTable.INSTANCE.resourcePrimKey
		).singleColumnReference(
			JournalArticleTable.INSTANCE.folderId,
			JournalFolderTable.INSTANCE.folderId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureTable.INSTANCE
			).innerJoinON(
				JournalArticleTable.INSTANCE,
				JournalArticleTable.INSTANCE.classPK.eq(
					DDMStructureTable.INSTANCE.structureId)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					DDMStructure.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						JournalArticleTable.INSTANCE.classNameId)
				)
			)
		).nonreferenceColumns(
			JournalArticleTable.INSTANCE.treePath,
			JournalArticleTable.INSTANCE.articleId,
			JournalArticleTable.INSTANCE.version
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				FriendlyURLEntryTable.INSTANCE
			).innerJoinON(
				JournalArticleTable.INSTANCE,
				JournalArticleTable.INSTANCE.groupId.eq(
					FriendlyURLEntryTable.INSTANCE.groupId
				).and(
					FriendlyURLEntryTable.INSTANCE.classPK.eq(
						JournalArticleTable.INSTANCE.resourcePrimKey)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					JournalArticle.class.getName()
				).and(
					FriendlyURLEntryTable.INSTANCE.classNameId.eq(
						ClassNameTable.INSTANCE.classNameId)
				)
			)
		).nonreferenceColumns(
			JournalArticleTable.INSTANCE.urlTitle,
			JournalArticleTable.INSTANCE.content
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureTable.INSTANCE
			).innerJoinON(
				JournalArticleTable.INSTANCE,
				JournalArticleTable.INSTANCE.DDMStructureKey.eq(
					DDMStructureTable.INSTANCE.structureKey
				).and(
					JournalArticleTable.INSTANCE.companyId.eq(
						DDMStructureTable.INSTANCE.companyId)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					JournalArticle.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						DDMStructureTable.INSTANCE.classNameId)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMTemplateTable.INSTANCE
			).innerJoinON(
				JournalArticleTable.INSTANCE,
				JournalArticleTable.INSTANCE.DDMTemplateKey.eq(
					DDMTemplateTable.INSTANCE.templateKey
				).and(
					JournalArticleTable.INSTANCE.companyId.eq(
						DDMTemplateTable.INSTANCE.companyId)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					DDMStructure.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						DDMTemplateTable.INSTANCE.classNameId)
				)
			)
		).nonreferenceColumn(
			JournalArticleTable.INSTANCE.defaultLanguageId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutTable.INSTANCE
			).innerJoinON(
				JournalArticleTable.INSTANCE,
				JournalArticleTable.INSTANCE.layoutUuid.eq(
					LayoutTable.INSTANCE.uuid
				).and(
					JournalArticleTable.INSTANCE.groupId.eq(
						LayoutTable.INSTANCE.groupId)
				)
			)
		).nonreferenceColumns(
			JournalArticleTable.INSTANCE.displayDate,
			JournalArticleTable.INSTANCE.expirationDate,
			JournalArticleTable.INSTANCE.reviewDate,
			JournalArticleTable.INSTANCE.indexable
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				ImageTable.INSTANCE
			).innerJoinON(
				JournalArticleTable.INSTANCE,
				JournalArticleTable.INSTANCE.smallImageId.eq(
					ImageTable.INSTANCE.imageId
				).and(
					JournalArticleTable.INSTANCE.smallImage.eq(Boolean.TRUE)
				)
			)
		).nonreferenceColumns(
			JournalArticleTable.INSTANCE.smallImageURL,
			JournalArticleTable.INSTANCE.lastPublishDate,
			JournalArticleTable.INSTANCE.status
		).singleColumnReference(
			JournalArticleTable.INSTANCE.statusByUserId,
			UserTable.INSTANCE.userId
		).nonreferenceColumns(
			JournalArticleTable.INSTANCE.statusByUserName,
			JournalArticleTable.INSTANCE.statusDate
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureLinkTable.INSTANCE
			).innerJoinON(
				JournalArticleTable.INSTANCE,
				JournalArticleTable.INSTANCE.id.eq(
					DDMStructureLinkTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					JournalArticle.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						DDMStructureLinkTable.INSTANCE.classNameId)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMTemplateLinkTable.INSTANCE
			).innerJoinON(
				JournalArticleTable.INSTANCE,
				JournalArticleTable.INSTANCE.id.eq(
					DDMTemplateLinkTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					JournalArticle.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						DDMTemplateLinkTable.INSTANCE.classNameId)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _journalArticlePersistence;
	}

	@Override
	public JournalArticleTable getTable() {
		return JournalArticleTable.INSTANCE;
	}

	@Reference
	private JournalArticlePersistence _journalArticlePersistence;

}