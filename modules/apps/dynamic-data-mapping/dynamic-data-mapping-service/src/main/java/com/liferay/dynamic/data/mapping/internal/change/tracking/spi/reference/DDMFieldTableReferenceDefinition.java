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

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttributeTable;
import com.liferay.dynamic.data.mapping.model.DDMFieldTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersionTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFieldPersistence;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleTable;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMFieldTableReferenceDefinition
	implements TableReferenceDefinition<DDMFieldTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDMFieldTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			DDMFieldTable.INSTANCE.fieldId,
			DDMFieldAttributeTable.INSTANCE.fieldId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDMFieldTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			DDMFieldTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DDMFieldTable.INSTANCE.structureVersionId,
			DDMStructureVersionTable.INSTANCE.structureVersionId
		).parentColumnReference(
			DDMFieldTable.INSTANCE.fieldId, DDMFieldTable.INSTANCE.parentFieldId
		).referenceInnerJoin(
			fromStep -> {
				DDMFieldAttributeTable classNameIdDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"classNameIdDDMFieldAttributeTable");
				DDMFieldAttributeTable classPKDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"classPKDDMFieldAttributeTable");

				return fromStep.from(
					JournalArticleTable.INSTANCE
				).innerJoinON(
					DDMFieldTable.INSTANCE,
					DDMFieldTable.INSTANCE.fieldType.eq("ddm-journal-article")
				).innerJoinON(
					ClassNameTable.INSTANCE,
					ClassNameTable.INSTANCE.value.eq(
						JournalArticle.class.getName())
				).innerJoinON(
					classNameIdDDMFieldAttributeTable,
					classNameIdDDMFieldAttributeTable.attributeName.eq(
						"classNameId"
					).and(
						classNameIdDDMFieldAttributeTable.smallAttributeValue.
							eq(
								DSLFunctionFactoryUtil.concat(
									_quoteExpression,
									DSLFunctionFactoryUtil.castText(
										ClassNameTable.INSTANCE.classNameId),
									_quoteExpression))
					).and(
						classNameIdDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				).innerJoinON(
					classPKDDMFieldAttributeTable,
					classPKDDMFieldAttributeTable.attributeName.eq(
						"classPK"
					).and(
						classPKDDMFieldAttributeTable.smallAttributeValue.eq(
							DSLFunctionFactoryUtil.concat(
								_quoteExpression,
								DSLFunctionFactoryUtil.castText(
									JournalArticleTable.INSTANCE.
										resourcePrimKey),
								_quoteExpression))
					).and(
						classPKDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				);
			}
		).referenceInnerJoin(
			fromStep -> {
				DDMFieldAttributeTable fileEntryIdDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"fileEntryIdDDMFieldAttributeTable");
				DDMFieldAttributeTable groupIdDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"groupIdDDMFieldAttributeTable");

				return fromStep.from(
					DLFileEntryTable.INSTANCE
				).innerJoinON(
					DDMFieldTable.INSTANCE,
					DDMFieldTable.INSTANCE.fieldType.eq("document_library")
				).innerJoinON(
					fileEntryIdDDMFieldAttributeTable,
					fileEntryIdDDMFieldAttributeTable.attributeName.eq(
						"fileEntryId"
					).and(
						fileEntryIdDDMFieldAttributeTable.smallAttributeValue.
							eq(
								DSLFunctionFactoryUtil.concat(
									_quoteExpression,
									DSLFunctionFactoryUtil.castText(
										DLFileEntryTable.INSTANCE.fileEntryId),
									_quoteExpression))
					).and(
						fileEntryIdDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				).innerJoinON(
					groupIdDDMFieldAttributeTable,
					groupIdDDMFieldAttributeTable.attributeName.eq(
						"groupId"
					).and(
						groupIdDDMFieldAttributeTable.smallAttributeValue.eq(
							DSLFunctionFactoryUtil.concat(
								_quoteExpression,
								DSLFunctionFactoryUtil.castText(
									DLFileEntryTable.INSTANCE.groupId),
								_quoteExpression))
					).and(
						groupIdDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				);
			}
		).referenceInnerJoin(
			fromStep -> {
				DDMFieldAttributeTable fileEntryIdDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"fileEntryIdDDMFieldAttributeTable");
				DDMFieldAttributeTable groupIdDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"groupIdDDMFieldAttributeTable");

				return fromStep.from(
					DLFileEntryTable.INSTANCE
				).innerJoinON(
					DDMFieldTable.INSTANCE,
					DDMFieldTable.INSTANCE.fieldType.eq("image")
				).innerJoinON(
					fileEntryIdDDMFieldAttributeTable,
					fileEntryIdDDMFieldAttributeTable.attributeName.eq(
						"fileEntryId"
					).and(
						fileEntryIdDDMFieldAttributeTable.smallAttributeValue.
							eq(
								DSLFunctionFactoryUtil.concat(
									_quoteExpression,
									DSLFunctionFactoryUtil.castText(
										DLFileEntryTable.INSTANCE.fileEntryId),
									_quoteExpression))
					).and(
						fileEntryIdDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				).innerJoinON(
					groupIdDDMFieldAttributeTable,
					groupIdDDMFieldAttributeTable.attributeName.eq(
						"groupId"
					).and(
						groupIdDDMFieldAttributeTable.smallAttributeValue.eq(
							DSLFunctionFactoryUtil.concat(
								_quoteExpression,
								DSLFunctionFactoryUtil.castText(
									DLFileEntryTable.INSTANCE.groupId),
								_quoteExpression))
					).and(
						groupIdDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				);
			}
		).referenceInnerJoin(
			fromStep -> {
				DDMFieldAttributeTable classNameIdDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"classNameIdDDMFieldAttributeTable");
				DDMFieldAttributeTable classPKDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"classPKDDMFieldAttributeTable");

				return fromStep.from(
					JournalArticleTable.INSTANCE
				).innerJoinON(
					DDMFieldTable.INSTANCE,
					DDMFieldTable.INSTANCE.fieldType.eq("journal_article")
				).innerJoinON(
					ClassNameTable.INSTANCE,
					ClassNameTable.INSTANCE.value.eq(
						JournalArticle.class.getName())
				).innerJoinON(
					classNameIdDDMFieldAttributeTable,
					classNameIdDDMFieldAttributeTable.attributeName.eq(
						"classNameId"
					).and(
						classNameIdDDMFieldAttributeTable.smallAttributeValue.
							eq(
								DSLFunctionFactoryUtil.concat(
									_quoteExpression,
									DSLFunctionFactoryUtil.castText(
										ClassNameTable.INSTANCE.classNameId),
									_quoteExpression))
					).and(
						classNameIdDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				).innerJoinON(
					classPKDDMFieldAttributeTable,
					classPKDDMFieldAttributeTable.attributeName.eq(
						"classPK"
					).and(
						classPKDDMFieldAttributeTable.smallAttributeValue.eq(
							DSLFunctionFactoryUtil.concat(
								_quoteExpression,
								DSLFunctionFactoryUtil.castText(
									JournalArticleTable.INSTANCE.
										resourcePrimKey),
								_quoteExpression))
					).and(
						classPKDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				);
			}
		).referenceInnerJoin(
			fromStep -> {
				DDMFieldAttributeTable groupIdDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"groupIdDDMFieldAttributeTable");
				DDMFieldAttributeTable privateLayoutDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"privateLayoutDDMFieldAttributeTable");
				DDMFieldAttributeTable layoutIdDDMFieldAttributeTable =
					DDMFieldAttributeTable.INSTANCE.as(
						"layoutIdDDMFieldAttributeTable");

				return fromStep.from(
					LayoutTable.INSTANCE
				).innerJoinON(
					DDMFieldTable.INSTANCE,
					DDMFieldTable.INSTANCE.fieldType.eq("link_to_layout")
				).innerJoinON(
					groupIdDDMFieldAttributeTable,
					groupIdDDMFieldAttributeTable.attributeName.eq(
						"groupId"
					).and(
						groupIdDDMFieldAttributeTable.smallAttributeValue.eq(
							DSLFunctionFactoryUtil.concat(
								_quoteExpression,
								DSLFunctionFactoryUtil.castText(
									LayoutTable.INSTANCE.groupId),
								_quoteExpression))
					).and(
						groupIdDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				).innerJoinON(
					privateLayoutDDMFieldAttributeTable,
					privateLayoutDDMFieldAttributeTable.attributeName.eq(
						"privateLayout"
					).and(
						_getPrivateLayoutPredicate(
							privateLayoutDDMFieldAttributeTable)
					).and(
						privateLayoutDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				).innerJoinON(
					layoutIdDDMFieldAttributeTable,
					layoutIdDDMFieldAttributeTable.attributeName.eq(
						"layoutId"
					).and(
						layoutIdDDMFieldAttributeTable.smallAttributeValue.eq(
							DSLFunctionFactoryUtil.concat(
								_quoteExpression,
								DSLFunctionFactoryUtil.castText(
									LayoutTable.INSTANCE.layoutId),
								_quoteExpression))
					).and(
						layoutIdDDMFieldAttributeTable.fieldId.eq(
							DDMFieldTable.INSTANCE.fieldId)
					)
				);
			}
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmFieldPersistence;
	}

	@Override
	public DDMFieldTable getTable() {
		return DDMFieldTable.INSTANCE;
	}

	private Predicate _getPrivateLayoutPredicate(
		DDMFieldAttributeTable privateLayoutDDMFieldAttributeTable) {

		DB db = _ddmFieldPersistence.getDB();

		DBType dbType = db.getDBType();

		if (dbType == DBType.HYPERSONIC) {
			return privateLayoutDDMFieldAttributeTable.smallAttributeValue.eq(
				DSLFunctionFactoryUtil.caseWhenThen(
					LayoutTable.INSTANCE.privateLayout.eq(Boolean.TRUE),
					DSLFunctionFactoryUtil.castText(
						new Scalar<>(Boolean.TRUE.toString()))
				).elseEnd(
					DSLFunctionFactoryUtil.castText(
						new Scalar<>(Boolean.FALSE.toString()))
				));
		}

		return privateLayoutDDMFieldAttributeTable.smallAttributeValue.eq(
			DSLFunctionFactoryUtil.caseWhenThen(
				LayoutTable.INSTANCE.privateLayout.eq(Boolean.TRUE),
				Boolean.TRUE.toString()
			).elseEnd(
				Boolean.FALSE.toString()
			));
	}

	private static final Expression<String> _quoteExpression = new Scalar<>(
		StringPool.QUOTE);

	@Reference
	private DDMFieldPersistence _ddmFieldPersistence;

}