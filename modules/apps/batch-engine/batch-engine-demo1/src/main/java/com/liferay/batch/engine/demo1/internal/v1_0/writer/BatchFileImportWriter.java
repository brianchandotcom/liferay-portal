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

package com.liferay.batch.engine.demo1.internal.v1_0.writer;

import com.liferay.batch.engine.BatchFileImportOperation;
import com.liferay.batch.engine.core.item.BatchItemWriter;
import com.liferay.batch.engine.demo1.internal.v1_0.model.Product;
import com.liferay.batch.engine.demo1.internal.v1_0.model.Sku;
import com.liferay.batch.engine.demo1.internal.v1_0.resource.ProductResource;
import com.liferay.batch.engine.demo1.internal.v1_0.resource.SkuResource;
import com.liferay.batch.engine.fileimport.writer.BaseBatchFileImportWriter;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	factory = "Demo1BatchFileImportWriter_v1_0", service = BatchItemWriter.class
)
public class BatchFileImportWriter extends BaseBatchFileImportWriter {

	@Override
	public void write(List items) throws Exception {
		for (Object item : items) {
			if (domainClass == Product.class) {
				if (batchFileImportOperation ==
						BatchFileImportOperation.CREATE) {

					_productResource.create((Product)item);
				}
				else if (batchFileImportOperation ==
							BatchFileImportOperation.UPDATE) {

					_productResource.update((Product)item);
				}
				else {
					_productResource.delete((Product)item);
				}
			}
			else if (domainClass == Sku.class) {
				if (batchFileImportOperation ==
						BatchFileImportOperation.CREATE) {

					_skuResource.create((Sku)item);
				}
				else if (batchFileImportOperation ==
							BatchFileImportOperation.UPDATE) {

					_skuResource.update((Sku)item);
				}
				else {
					_skuResource.delete((Sku)item);
				}
			}
		}
	}

	@Reference
	private ProductResource _productResource;

	@Reference
	private SkuResource _skuResource;

}