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

package com.liferay.commerce.qualifier.service.impl;

import com.liferay.commerce.qualifier.helper.CommerceQualifierHelper;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadata;
import com.liferay.commerce.qualifier.metadata.CommerceQualifierMetadataRegistry;
import com.liferay.commerce.qualifier.model.CommerceQualifierEntry;
import com.liferay.commerce.qualifier.model.CommerceQualifierEntryTable;
import com.liferay.commerce.qualifier.search.context.CommerceQualifierSearchContext;
import com.liferay.commerce.qualifier.service.base.CommerceQualifierEntryLocalServiceBaseImpl;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.qualifier.model.CommerceQualifierEntry",
	service = AopService.class
)
public class CommerceQualifierEntryLocalServiceImpl
	extends CommerceQualifierEntryLocalServiceBaseImpl {

	@Override
	public CommerceQualifierEntry addCommerceQualifierEntry(
			long userId, String sourceClassName, long sourceClassPK,
			String sourceCommerceQualifierMetadataKey, String targetClassName,
			long targetClassPK, String targetCommerceQualifierMetadataKey)
		throws PortalException {

		CommerceQualifierEntry commerceQualifierEntry =
			commerceQualifierEntryPersistence.create(
				counterLocalService.increment());

		User user = userLocalService.getUser(userId);

		commerceQualifierEntry.setCompanyId(user.getCompanyId());
		commerceQualifierEntry.setUserId(user.getUserId());
		commerceQualifierEntry.setUserName(user.getFullName());

		commerceQualifierEntry.setSourceClassNameId(
			classNameLocalService.getClassNameId(sourceClassName));
		commerceQualifierEntry.setSourceClassPK(sourceClassPK);
		commerceQualifierEntry.setSourceCommerceQualifierMetadataKey(
			sourceCommerceQualifierMetadataKey);
		commerceQualifierEntry.setTargetClassNameId(
			classNameLocalService.getClassNameId(targetClassName));
		commerceQualifierEntry.setTargetClassPK(targetClassPK);
		commerceQualifierEntry.setTargetCommerceQualifierMetadataKey(
			targetCommerceQualifierMetadataKey);

		commerceQualifierEntry = commerceQualifierEntryPersistence.update(
			commerceQualifierEntry);

		reindexSource(sourceClassName, sourceClassPK);

		return commerceQualifierEntry;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceQualifierEntry deleteCommerceQualifierEntry(
			CommerceQualifierEntry commerceQualifierEntry)
		throws PortalException {

		commerceQualifierEntryPersistence.remove(commerceQualifierEntry);

		reindexSource(
			commerceQualifierEntry.getSourceClassNameId(),
			commerceQualifierEntry.getSourceClassPK());

		return commerceQualifierEntry;
	}

	@Override
	public CommerceQualifierEntry deleteCommerceQualifierEntry(
			long commerceQualifierEntryId)
		throws PortalException {

		CommerceQualifierEntry commerceQualifierEntry =
			commerceQualifierEntryPersistence.findByPrimaryKey(
				commerceQualifierEntryId);

		return commerceQualifierEntryLocalService.deleteCommerceQualifierEntry(
			commerceQualifierEntry);
	}

	@Override
	public void deleteSourceCommerceQualifierEntries(
			String sourceClassName, long sourceClassPK)
		throws PortalException {

		List<CommerceQualifierEntry> commerceQualifierEntries =
			commerceQualifierEntryPersistence.findByS_S(
				classNameLocalService.getClassNameId(sourceClassName),
				sourceClassPK);

		for (CommerceQualifierEntry commerceQualifierEntry :
				commerceQualifierEntries) {

			commerceQualifierEntryLocalService.deleteCommerceQualifierEntry(
				commerceQualifierEntry);
		}
	}

	@Override
	public void deleteTargetCommerceQualifierEntries(
			String targetClassName, long targetClassPK)
		throws PortalException {

		List<CommerceQualifierEntry> commerceQualifierEntries =
			commerceQualifierEntryPersistence.findByT_T(
				classNameLocalService.getClassNameId(targetClassName),
				targetClassPK);

		for (CommerceQualifierEntry commerceQualifierEntry :
				commerceQualifierEntries) {

			commerceQualifierEntryLocalService.deleteCommerceQualifierEntry(
				commerceQualifierEntry);
		}
	}

	@Override
	public CommerceQualifierEntry fetchCommerceQualifierEntry(
		String sourceClassName, long sourceClassPK, String targetClassName,
		long targetClassPK) {

		return commerceQualifierEntryPersistence.fetchByS_S_T_T(
			classNameLocalService.getClassNameId(sourceClassName),
			sourceClassPK,
			classNameLocalService.getClassNameId(targetClassName),
			targetClassPK);
	}

	@Override
	public <E> List<E> getSourceCommerceQualifierEntries(
		long companyId, Class<E> sourceClass,
		String sourceCommerceQualifierMetadataKey,
		CommerceQualifierSearchContext commerceQualifierSearchContext) {

		CommerceQualifierMetadata sourceCommerceQualifierMetadata =
			_commerceQualifierMetadataRegistry.getCommerceQualifierMetadata(
				sourceCommerceQualifierMetadataKey);

		if (sourceCommerceQualifierMetadata == null) {
			return Collections.emptyList();
		}

		PersistedModelLocalService persistedModelLocalService =
			sourceCommerceQualifierMetadata.getPersistedModelLocalService();

		return (List<E>)persistedModelLocalService.dslQuery(
			_getGroupByStep(
				companyId,
				DSLQueryFactoryUtil.select(
					sourceCommerceQualifierMetadata.getTable()),
				sourceCommerceQualifierMetadata,
				commerceQualifierSearchContext.getSourceAttributes(),
				commerceQualifierSearchContext.getTargetAttributes()
			).orderBy(
				sourceCommerceQualifierMetadata.getOrderByExpressions(
					commerceQualifierSearchContext.getTargetAttributes())
			));
	}

	@Override
	public List<CommerceQualifierEntry> getSourceCommerceQualifierEntries(
			long companyId, String sourceClassName, long sourceClassPK,
			String targetCommerceQualifierMetadataKey, String keywords,
			int start, int end)
		throws PortalException {

		CommerceQualifierMetadata targetCommerceQualifierMetadata =
			_commerceQualifierMetadataRegistry.getCommerceQualifierMetadata(
				targetCommerceQualifierMetadataKey);

		if (targetCommerceQualifierMetadata == null) {
			return Collections.emptyList();
		}

		Column<?, Long> primaryKeyColumn =
			targetCommerceQualifierMetadata.getPrimaryKeyColumn();

		return dslQuery(
			_getGroupByStep(
				companyId,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceQualifierEntryTable.INSTANCE),
				targetCommerceQualifierMetadata.getTable(),
				primaryKeyColumn.eq(
					CommerceQualifierEntryTable.INSTANCE.targetClassPK
				).and(
					targetCommerceQualifierMetadata.getFilterPredicate()
				),
				false, sourceClassName, sourceClassPK,
				targetCommerceQualifierMetadata.getModelClassName(), keywords,
				targetCommerceQualifierMetadata.getKeywordsColumn()
			).limit(
				start, end
			));
	}

	@Override
	public int getSourceCommerceQualifierEntriesCount(
			long companyId, String sourceClassName, long sourceClassPK,
			String targetCommerceQualifierMetadataKey, String keywords)
		throws PortalException {

		CommerceQualifierMetadata targetCommerceQualifierMetadata =
			_commerceQualifierMetadataRegistry.getCommerceQualifierMetadata(
				targetCommerceQualifierMetadataKey);

		if (targetCommerceQualifierMetadata == null) {
			return 0;
		}

		Column<?, Long> primaryKeyColumn =
			targetCommerceQualifierMetadata.getPrimaryKeyColumn();

		return dslQueryCount(
			_getGroupByStep(
				companyId,
				DSLQueryFactoryUtil.countDistinct(
					CommerceQualifierEntryTable.INSTANCE.
						commerceQualifierEntryId),
				targetCommerceQualifierMetadata.getTable(),
				primaryKeyColumn.eq(
					CommerceQualifierEntryTable.INSTANCE.targetClassPK
				).and(
					targetCommerceQualifierMetadata.getFilterPredicate()
				),
				false, sourceClassName, sourceClassPK,
				targetCommerceQualifierMetadata.getModelClassName(), keywords,
				targetCommerceQualifierMetadata.getKeywordsColumn()));
	}

	@Override
	public List<CommerceQualifierEntry> getTargetCommerceQualifierEntries(
			long companyId, String sourceCommerceQualifierMetadataKey,
			String targetClassName, long targetClassPK, String keywords,
			int start, int end)
		throws PortalException {

		CommerceQualifierMetadata sourceCommerceQualifierMetadata =
			_commerceQualifierMetadataRegistry.getCommerceQualifierMetadata(
				sourceCommerceQualifierMetadataKey);

		if (sourceCommerceQualifierMetadata == null) {
			return Collections.emptyList();
		}

		Column<?, Long> primaryKeyColumn =
			sourceCommerceQualifierMetadata.getPrimaryKeyColumn();

		return dslQuery(
			_getGroupByStep(
				companyId,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceQualifierEntryTable.INSTANCE),
				sourceCommerceQualifierMetadata.getTable(),
				primaryKeyColumn.eq(
					CommerceQualifierEntryTable.INSTANCE.sourceClassPK
				).and(
					sourceCommerceQualifierMetadata.getFilterPredicate()
				),
				true, targetClassName, targetClassPK,
				sourceCommerceQualifierMetadata.getModelClassName(), keywords,
				sourceCommerceQualifierMetadata.getKeywordsColumn()
			).limit(
				start, end
			));
	}

	@Override
	public int getTargetCommerceQualifierEntriesCount(
			long companyId, String sourceCommerceQualifierMetadataKey,
			String targetClassName, long targetClassPK, String keywords)
		throws PortalException {

		CommerceQualifierMetadata sourceCommerceQualifierMetadata =
			_commerceQualifierMetadataRegistry.getCommerceQualifierMetadata(
				sourceCommerceQualifierMetadataKey);

		if (sourceCommerceQualifierMetadata == null) {
			return 0;
		}

		Column<?, Long> primaryKeyColumn =
			sourceCommerceQualifierMetadata.getPrimaryKeyColumn();

		return dslQueryCount(
			_getGroupByStep(
				companyId,
				DSLQueryFactoryUtil.countDistinct(
					CommerceQualifierEntryTable.INSTANCE.
						commerceQualifierEntryId),
				sourceCommerceQualifierMetadata.getTable(),
				primaryKeyColumn.eq(
					CommerceQualifierEntryTable.INSTANCE.sourceClassPK
				).and(
					sourceCommerceQualifierMetadata.getFilterPredicate()
				),
				true, targetClassName, targetClassPK,
				sourceCommerceQualifierMetadata.getModelClassName(), keywords,
				sourceCommerceQualifierMetadata.getKeywordsColumn()));
	}

	protected void reindexSource(long sourceClassNameId, long sourceClassPK)
		throws PortalException {

		ClassName sourceClassName = classNameLocalService.getClassName(
			sourceClassNameId);

		reindexSource(sourceClassName.getClassName(), sourceClassPK);
	}

	protected void reindexSource(String sourceClassName, long sourceClassPK)
		throws PortalException {

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			sourceClassName);

		indexer.reindex(sourceClassName, sourceClassPK);
	}

	private GroupByStep _getGroupByStep(
		long companyId, FromStep fromStep,
		CommerceQualifierMetadata sourceCommerceQualifierMetadata,
		Map<String, ?> sourceAttributes, Map<String, ?> targetAttributes) {

		Table sourceTable = sourceCommerceQualifierMetadata.getTable();

		JoinStep joinStep = fromStep.from(sourceTable);

		Predicate predicate = sourceTable.getColumn(
			"companyId"
		).eq(
			companyId
		).and(
			sourceCommerceQualifierMetadata.getFilterPredicate()
		);

		if (sourceAttributes != null) {
			for (Map.Entry<String, ?> sourceAttribute :
					sourceAttributes.entrySet()) {

				predicate = predicate.and(
					() -> {
						Object value = sourceAttribute.getValue();

						if (value instanceof Object[]) {
							return sourceTable.getColumn(
								sourceAttribute.getKey()
							).in(
								(Object[])value
							);
						}

						return sourceTable.getColumn(
							sourceAttribute.getKey()
						).eq(
							value
						);
					});
			}
		}

		if (targetAttributes == null) {
			return joinStep.leftJoinOn(
				CommerceQualifierEntryTable.INSTANCE,
				CommerceQualifierEntryTable.INSTANCE.sourceClassNameId.eq(
					classNameLocalService.getClassNameId(
						sourceCommerceQualifierMetadata.getModelClassName())
				).and(
					CommerceQualifierEntryTable.INSTANCE.sourceClassPK.eq(
						sourceCommerceQualifierMetadata.getPrimaryKeyColumn())
				)
			).where(
				predicate.and(
					CommerceQualifierEntryTable.INSTANCE.
						commerceQualifierEntryId.isNull())
			);
		}

		String[] allowedTargetKeys = Stream.of(
			sourceCommerceQualifierMetadata.getAllowedTargetKeysArray()
		).flatMap(
			Stream::of
		).toArray(
			String[]::new
		);

		if (allowedTargetKeys.length == 0) {
			return joinStep.where(predicate);
		}

		Predicate subpredicate = null;

		for (String allowedTargetKey : allowedTargetKeys) {
			CommerceQualifierEntryTable aliasCommerceQualifierEntryTable =
				_commerceQualifierHelper.getAliasCommerceQualifierEntryTable(
					sourceCommerceQualifierMetadata.getKey(), allowedTargetKey);

			joinStep = joinStep.leftJoinOn(
				aliasCommerceQualifierEntryTable,
				_getPredicate(
					aliasCommerceQualifierEntryTable.sourceClassNameId,
					sourceCommerceQualifierMetadata.getModelClassName(),
					aliasCommerceQualifierEntryTable.sourceClassPK,
					sourceCommerceQualifierMetadata.getPrimaryKeyColumn(),
					aliasCommerceQualifierEntryTable.targetClassNameId,
					aliasCommerceQualifierEntryTable.
						targetCommerceQualifierMetadataKey,
					allowedTargetKey));

			Predicate targetPredicate = _getTargetPredicate(
				aliasCommerceQualifierEntryTable.commerceQualifierEntryId,
				aliasCommerceQualifierEntryTable.targetClassPK,
				targetAttributes.get(allowedTargetKey));

			if (subpredicate == null) {
				subpredicate = targetPredicate;
			}
			else {
				subpredicate = subpredicate.and(targetPredicate);
			}
		}

		return joinStep.where(predicate.and(subpredicate.withParentheses()));
	}

	private GroupByStep _getGroupByStep(
		long companyId, FromStep fromStep, Table innerJoinTable,
		Predicate innerJoinPredicate, boolean target, String className1,
		Long classPK1, String className2, String keywords,
		Expression<String> keywordsPredicateExpression) {

		JoinStep joinStep = fromStep.from(
			CommerceQualifierEntryTable.INSTANCE
		).innerJoinON(
			innerJoinTable, innerJoinPredicate
		);

		return joinStep.where(
			() -> CommerceQualifierEntryTable.INSTANCE.companyId.eq(
				companyId
			).and(
				() -> {
					if (target) {
						return CommerceQualifierEntryTable.INSTANCE.
							targetClassNameId.eq(
								classNameLocalService.getClassNameId(className1)
							).and(
								CommerceQualifierEntryTable.INSTANCE.
									targetClassPK.eq(classPK1)
							).and(
								CommerceQualifierEntryTable.INSTANCE.
									sourceClassNameId.eq(
										classNameLocalService.getClassNameId(
											className2))
							);
					}

					return CommerceQualifierEntryTable.INSTANCE.
						sourceClassNameId.eq(
							classNameLocalService.getClassNameId(className1)
						).and(
							CommerceQualifierEntryTable.INSTANCE.sourceClassPK.
								eq(classPK1)
						).and(
							CommerceQualifierEntryTable.INSTANCE.
								targetClassNameId.eq(
									classNameLocalService.getClassNameId(
										className2))
						);
				}
			).and(
				() -> {
					if (Validator.isNotNull(keywords)) {
						return Predicate.withParentheses(
							_customSQL.getKeywordsPredicate(
								DSLFunctionFactoryUtil.lower(
									keywordsPredicateExpression),
								_customSQL.keywords(keywords, true)));
					}

					return null;
				}
			));
	}

	private Predicate _getPredicate(
		Column<CommerceQualifierEntryTable, Long> sourceClassNameIdColumn,
		String sourceClassName,
		Column<CommerceQualifierEntryTable, Long> sourceClassPKColumn,
		Column<?, Long> sourceCommerceQualifierPrimaryColumn,
		Column<CommerceQualifierEntryTable, Long> targetClassNameIdColumn,
		Column<CommerceQualifierEntryTable, String>
			targetCommerceQualifierMetadataKeyColumn,
		String targetCommerceQualifierMetadataKey) {

		CommerceQualifierMetadata targetCommerceQualifierMetadata =
			_commerceQualifierMetadataRegistry.getCommerceQualifierMetadata(
				targetCommerceQualifierMetadataKey);

		return targetClassNameIdColumn.eq(
			classNameLocalService.getClassNameId(
				targetCommerceQualifierMetadata.getModelClassName())
		).and(
			targetCommerceQualifierMetadataKeyColumn.eq(
				targetCommerceQualifierMetadataKey)
		).and(
			sourceClassNameIdColumn.eq(
				classNameLocalService.getClassNameId(sourceClassName))
		).and(
			sourceClassPKColumn.eq(sourceCommerceQualifierPrimaryColumn)
		);
	}

	private Predicate _getTargetPredicate(
		Column<?, Long> primaryKeyColumn, Column<?, Long> classPKColumn,
		Object value) {

		if (value == null) {
			return primaryKeyColumn.isNull();
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Long[] longValueArray = (Long[])value;

			if (longValueArray.length == 0) {
				longValueArray = new Long[] {0L};
			}

			return classPKColumn.in(
				longValueArray
			).or(
				primaryKeyColumn.isNull()
			).withParentheses();
		}

		return classPKColumn.eq(
			(Long)value
		).or(
			primaryKeyColumn.isNull()
		).withParentheses();
	}

	@Reference
	private CommerceQualifierHelper _commerceQualifierHelper;

	@Reference
	private CommerceQualifierMetadataRegistry
		_commerceQualifierMetadataRegistry;

	@Reference
	private CustomSQL _customSQL;

}