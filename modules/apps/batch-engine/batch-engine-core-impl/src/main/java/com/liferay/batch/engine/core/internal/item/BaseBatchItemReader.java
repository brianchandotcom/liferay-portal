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

package com.liferay.batch.engine.core.internal.item;

import com.liferay.batch.engine.core.item.BatchItemReader;
import com.liferay.batch.engine.core.item.BatchItemStream;
import com.liferay.batch.engine.core.item.ItemStreamException;
import com.liferay.batch.engine.core.item.ParseException;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Ivica Cardic
 */
public abstract class BaseBatchItemReader<T>
	implements BatchItemReader<T>, BatchItemStream {

	@Override
	public void close() throws ItemStreamException {
		try {
			doClose();
		}
		catch (Exception e) {
			throw new ItemStreamException("Error while closing item reader", e);
		}
	}

	@Override
	public void open(UnicodeProperties jobSettingsProperties)
		throws ItemStreamException {

		try {
			doOpen();
		}
		catch (Exception e) {
			throw new ItemStreamException("Failed to initialize the reader", e);
		}
	}

	@Override
	public T read() throws Exception, ParseException {
		currentItemCount++;

		return doRead();
	}

	protected abstract void doClose() throws Exception;

	protected abstract void doOpen() throws Exception;

	protected abstract T doRead() throws Exception, ParseException;

	protected int currentItemCount = -1;

}