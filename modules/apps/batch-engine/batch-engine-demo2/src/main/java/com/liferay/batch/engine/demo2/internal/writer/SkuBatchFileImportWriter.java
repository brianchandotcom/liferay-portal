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

import com.liferay.batch.engine.core.item.BatchItemWriter;
import com.liferay.batch.engine.demo2.internal.model.Sku;
import com.liferay.batch.engine.demo2.internal.resource.SkuResource;
import com.liferay.batch.engine.fileimport.writer.BaseBatchFileImportWriter;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	factory = "SkuBatchFileImportWriter", service = BatchItemWriter.class
)
public class SkuBatchFileImportWriter extends BaseBatchFileImportWriter {

	@Override
	public void write(List items) throws Exception {
		for (Object item : items) {
			_skuResource.create((Sku)item);
		}
	}

	@Reference
	private SkuResource _skuResource;

}