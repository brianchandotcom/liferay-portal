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

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.ImagePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class ImageTableReferenceDefinition
	implements TableReferenceDefinition<ImageTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<ImageTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				CTSContentTable.INSTANCE
			).innerJoinON(
				ImageTable.INSTANCE,
				CTSContentTable.INSTANCE.companyId.eq(
					0L
				).and(
					CTSContentTable.INSTANCE.repositoryId.eq(0L)
				).and(
					CTSContentTable.INSTANCE.path.eq(
						DSLFunctionFactoryUtil.concat(
							DSLFunctionFactoryUtil.castText(
								ImageTable.INSTANCE.imageId),
							new Scalar<>(StringPool.PERIOD),
							ImageTable.INSTANCE.type))
				)
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<ImageTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			ImageTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _imagePersistence;
	}

	@Override
	public ImageTable getTable() {
		return ImageTable.INSTANCE;
	}

	@Reference
	private ImagePersistence _imagePersistence;

}