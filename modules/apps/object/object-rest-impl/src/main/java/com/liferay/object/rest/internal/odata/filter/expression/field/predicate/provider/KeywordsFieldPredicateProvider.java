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

package com.liferay.object.rest.internal.odata.filter.expression.field.predicate.provider;

import com.liferay.asset.kernel.model.AssetEntries_AssetTagsTable;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.model.AssetTagTable;
import com.liferay.object.odata.filter.expression.field.predicate.provider.FieldPredicateProvider;
import com.liferay.object.rest.internal.util.BinaryExpressionConverterUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.odata.filter.expression.BinaryExpression;

import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "field.predicate.provider.key=keywords",
	service = FieldPredicateProvider.class
)
public class KeywordsFieldPredicateProvider implements FieldPredicateProvider {

	@Override
	public Predicate getBinaryExpressionPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		Object left, long objectDefinitionId,
		BinaryExpression.Operation operation, Object right) {

		return _getKeywordsPredicate(
			objectDefinitionColumnSupplier,
			BinaryExpressionConverterUtil.getExpressionPredicate(
				AssetTagTable.INSTANCE.name, operation, (String)right));
	}

	@Override
	public Predicate getContainsPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		Object fieldValue) {

		return _getKeywordsPredicate(
			objectDefinitionColumnSupplier,
			AssetTagTable.INSTANCE.name.like(
				StringPool.PERCENT + fieldValue + StringPool.PERCENT));
	}

	@Override
	public Predicate getInPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		List<Object> rights) {

		return _getKeywordsPredicate(
			objectDefinitionColumnSupplier,
			AssetTagTable.INSTANCE.name.in(
				TransformUtil.transformToArray(
					rights, String::valueOf, Object.class)));
	}

	@Override
	public Predicate getStartsWithPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		Object fieldValue) {

		return _getKeywordsPredicate(
			objectDefinitionColumnSupplier,
			AssetTagTable.INSTANCE.name.like(fieldValue + StringPool.PERCENT));
	}

	private Predicate _getKeywordsPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		Expression<Boolean> valueExpression) {

		Column<?, ?> column = objectDefinitionColumnSupplier.apply("id");

		return column.in(
			DSLQueryFactoryUtil.select(
				AssetEntryTable.INSTANCE.classPK
			).from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				AssetEntries_AssetTagsTable.INSTANCE,
				AssetEntryTable.INSTANCE.entryId.eq(
					AssetEntries_AssetTagsTable.INSTANCE.entryId)
			).innerJoinON(
				AssetTagTable.INSTANCE,
				AssetTagTable.INSTANCE.tagId.eq(
					AssetEntries_AssetTagsTable.INSTANCE.tagId
				).and(
					valueExpression
				)
			));
	}

}