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

package com.liferay.change.tracking.internal.reference.builder;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;

import java.util.function.Function;

/**
 * @author Preston Crary
 */
public class ChildTableReferenceInfoBuilderImpl<T extends Table<T>>
	extends BaseTableReferenceInfoBuilder<T>
	implements ChildTableReferenceInfoBuilder<T> {

	public ChildTableReferenceInfoBuilderImpl(
		TableReferenceDefinition<T> tableReferenceDefinition,
		Column<T, Long> primaryKeyColumn) {

		super(tableReferenceDefinition, primaryKeyColumn, false);
	}

	@Override
	public ChildTableReferenceInfoBuilder<T> referenceInnerJoin(
		Function<FromStep, JoinStep> joinFunction) {

		applyReferenceInnerJoin(joinFunction);

		return this;
	}

}