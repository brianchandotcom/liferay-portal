/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.reference.builder;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;

import java.util.function.Function;

/**
 * @author Preston Crary
 */
public class ParentTableReferenceInfoBuilderImpl<T extends Table<T>>
	extends BaseTableReferenceInfoBuilder<T>
	implements ParentTableReferenceInfoBuilder<T> {

	public ParentTableReferenceInfoBuilderImpl(
		TableReferenceDefinition<T> tableReferenceDefinition,
		Column<T, Long> primaryKeyColumn) {

		super(tableReferenceDefinition, primaryKeyColumn, true);
	}

	@Override
	public ParentTableReferenceInfoBuilder<T> referenceInnerJoin(
		Function<FromStep, JoinStep> joinFunction) {

		applyReferenceInnerJoin(joinFunction);

		return this;
	}

}