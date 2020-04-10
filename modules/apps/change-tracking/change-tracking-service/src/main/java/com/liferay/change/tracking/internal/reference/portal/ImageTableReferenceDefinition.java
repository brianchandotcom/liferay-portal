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

package com.liferay.change.tracking.internal.reference.portal;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
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
	public void defineTableReferences(
		TableReferenceDefinitionHelper<ImageTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				ImageTable.INSTANCE,
				ImageTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ImageTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ImageTable.INSTANCE.type);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ImageTable.INSTANCE.height);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ImageTable.INSTANCE.width);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ImageTable.INSTANCE.size);
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