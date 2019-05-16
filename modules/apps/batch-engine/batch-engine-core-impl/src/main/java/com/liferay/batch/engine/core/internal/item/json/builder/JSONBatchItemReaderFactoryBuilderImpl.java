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

package com.liferay.batch.engine.core.internal.item.json.builder;

import com.liferay.batch.engine.core.internal.item.json.JSONBatchItemReader;
import com.liferay.batch.engine.core.item.BatchItemReader;
import com.liferay.batch.engine.core.item.configuration.BatchItemReaderFactory;
import com.liferay.batch.engine.core.item.io.Resource;
import com.liferay.batch.engine.core.item.json.builder.JSONBatchItemReaderFactoryBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Objects;

/**
 * @author Ivica Cardic
 */
public class JSONBatchItemReaderFactoryBuilderImpl
	implements JSONBatchItemReaderFactoryBuilder {

	@Override
	public BatchItemReaderFactory build() {
		Objects.requireNonNull(_itemType);
		Objects.requireNonNull(_resource);

		return new BatchItemReaderFactoryImpl();
	}

	@Override
	public JSONBatchItemReaderFactoryBuilder setItemType(Class itemType) {
		_itemType = itemType;

		return this;
	}

	@Override
	public JSONBatchItemReaderFactoryBuilder setResource(Resource resource) {
		_resource = resource;

		return this;
	}

	private Class<?> _itemType;
	private Resource _resource;

	private class BatchItemReaderFactoryImpl implements BatchItemReaderFactory {

		@Override
		public BatchItemReader create(UnicodeProperties jobSettingsProperties) {
			return new JSONBatchItemReader<>(
				Objects.requireNonNull(_itemType),
				Objects.requireNonNull(
					_resource.getInputStream(jobSettingsProperties)));
		}

	}

}