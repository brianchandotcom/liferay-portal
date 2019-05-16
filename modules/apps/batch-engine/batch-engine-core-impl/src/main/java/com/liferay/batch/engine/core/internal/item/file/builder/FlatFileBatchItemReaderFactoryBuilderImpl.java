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

package com.liferay.batch.engine.core.internal.item.file.builder;

import com.liferay.batch.engine.core.constants.BatchConstants;
import com.liferay.batch.engine.core.internal.item.file.FlatFileBatchItemReader;
import com.liferay.batch.engine.core.item.BatchItemReader;
import com.liferay.batch.engine.core.item.configuration.BatchItemReaderFactory;
import com.liferay.batch.engine.core.item.file.builder.FlatFileBatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.core.item.io.Resource;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Objects;

/**
 * @author Ivica Cardic
 */
public class FlatFileBatchItemReaderFactoryBuilderImpl
	implements FlatFileBatchItemReaderFactoryBuilder {

	@Override
	public BatchItemReaderFactory build() {
		Objects.requireNonNull(_itemType);
		Objects.requireNonNull(_resource);

		return new BatchItemReaderFactoryImpl();
	}

	@Override
	public FlatFileBatchItemReaderFactoryBuilder setColumnNames(
		String... columnNames) {

		_columnNames = columnNames;

		return this;
	}

	@Override
	public FlatFileBatchItemReaderFactoryBuilder setDelimiter(
		String delimiter) {

		_delimiter = delimiter;

		return this;
	}

	@Override
	public FlatFileBatchItemReaderFactoryBuilder setItemType(Class itemType) {
		_itemType = itemType;

		return this;
	}

	@Override
	public FlatFileBatchItemReaderFactoryBuilder setLinesToSkip(
		int linesToSkip) {

		_linesToSkip = linesToSkip;

		return this;
	}

	@Override
	public FlatFileBatchItemReaderFactoryBuilder setResource(
		Resource resource) {

		_resource = resource;

		return this;
	}

	private String[] _columnNames;
	private String _delimiter = ",";
	private Class<?> _itemType;
	private int _linesToSkip;
	private Resource _resource;

	private class BatchItemReaderFactoryImpl implements BatchItemReaderFactory {

		@Override
		public BatchItemReader create(UnicodeProperties jobSettingsProperties) {
			if (jobSettingsProperties.containsKey(
					BatchConstants.COLUMN_NAMES)) {

				_columnNames = StringUtil.split(
					jobSettingsProperties.get(BatchConstants.COLUMN_NAMES));
			}

			return new FlatFileBatchItemReader<>(
				Objects.requireNonNull(_itemType),
				Objects.requireNonNull(_delimiter), _columnNames, _linesToSkip,
				Objects.requireNonNull(
					_resource.getInputStream(jobSettingsProperties)));
		}

	}

}