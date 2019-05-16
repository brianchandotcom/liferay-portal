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

package com.liferay.batch.engine.core.item.xls.builder;

import com.liferay.batch.engine.core.item.builder.BatchItemReaderFactoryBuilder;
import com.liferay.batch.engine.core.item.io.Resource;

/**
 * @author Ivica Cardic
 */
public interface XLSBatchItemReaderFactoryBuilder
	extends BatchItemReaderFactoryBuilder {

	public XLSBatchItemReaderFactoryBuilder setColumnNames(
		String... columnNames);

	public XLSBatchItemReaderFactoryBuilder setItemType(Class itemType);

	public XLSBatchItemReaderFactoryBuilder setLinesToSkip(int linesToSkip);

	public XLSBatchItemReaderFactoryBuilder setResource(Resource resource);

}