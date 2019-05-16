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

package com.liferay.batch.engine.core.internal.item.configuration;

import com.liferay.batch.engine.core.internal.item.file.builder.FlatFileBatchItemReaderFactoryBuilderImpl;
import com.liferay.batch.engine.core.internal.item.json.builder.JSONBatchItemReaderFactoryBuilderImpl;
import com.liferay.batch.engine.core.internal.item.xls.builder.XLSBatchItemReaderFactoryBuilderImpl;
import com.liferay.batch.engine.core.item.builder.BatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.core.item.configuration.BatchItemReaderFactoryBuilderFactory;
import com.liferay.batch.engine.core.item.file.builder.FlatFileBatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.core.item.json.builder.JSONBatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.core.item.xls.builder.XLSBatchItemReaderFactoryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(service = BatchItemReaderFactoryBuilderFactory.class)
public class BatchItemReaderFactoryBuilderFactoryImpl
	implements BatchItemReaderFactoryBuilderFactory {

	@Override
	@SuppressWarnings("unchecked")
	public <T extends BatchItemReaderFactoryBuilder> T get(
		Class<T> builderClass) {

		if (builderClass == FlatFileBatchItemReaderFactoryBuilder.class) {
			return (T)new FlatFileBatchItemReaderFactoryBuilderImpl();
		}
		else if (builderClass == JSONBatchItemReaderFactoryBuilder.class) {
			return (T)new JSONBatchItemReaderFactoryBuilderImpl();
		}
		else if (builderClass == XLSBatchItemReaderFactoryBuilder.class) {
			return (T)new XLSBatchItemReaderFactoryBuilderImpl();
		}
		else {
			throw new IllegalArgumentException(
				"Batch item reader factory builder does not exist");
		}
	}

}