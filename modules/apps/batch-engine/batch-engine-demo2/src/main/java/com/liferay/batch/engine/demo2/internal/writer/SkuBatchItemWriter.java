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

package com.liferay.batch.engine.demo2.internal.writer;

import com.liferay.batch.engine.BatchItemWriter;
import com.liferay.batch.engine.BatchOperation;
import com.liferay.batch.engine.demo2.internal.model.Sku;
import com.liferay.batch.engine.demo2.internal.resource.SkuResource;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	property = {
		"class.name=com.liferay.batch.engine.demo2.internal.model.Sku",
		"version=v0.0"
	},
	service = BatchItemWriter.class
)
public class SkuBatchItemWriter implements BatchItemWriter<Sku> {

	@Override
	public void write(List<? extends Sku> skus, BatchOperation batchOperation) {
		for (Sku sku : skus) {
			_skuResource.create(sku);
		}
	}

	@Reference
	private SkuResource _skuResource;

}