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

package com.liferay.talend.runtime;

import com.liferay.talend.runtime.reader.LiferayInputReader;
import com.liferay.talend.tliferayinput.TLiferayInputProperties;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.BoundedReader;
import org.talend.components.api.component.runtime.BoundedSource;
import org.talend.components.api.container.RuntimeContainer;

/**
 * @author Zoltán Takács
 */
public class LiferaySource
	extends LiferaySourceOrSink implements BoundedSource {

	@Override
	public BoundedReader<?> createReader(RuntimeContainer runtimeContainer) {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating reader for fetching data from the datastore");
		}

		if (liferayConnectionPropertiesProvider instanceof
				TLiferayInputProperties) {

			TLiferayInputProperties tLiferayInputProperties =
				(TLiferayInputProperties)liferayConnectionPropertiesProvider;

			return new LiferayInputReader(
				runtimeContainer, this, tLiferayInputProperties);
		}

		_log.error("Unexpected property instance");

		return new LiferayInputReader(
			runtimeContainer, this,
			(TLiferayInputProperties)liferayConnectionPropertiesProvider);
	}

	@Override
	public long getEstimatedSizeBytes(RuntimeContainer runtimeContainer) {
		return 0;
	}

	@Override
	public boolean producesSortedKeys(RuntimeContainer runtimeContainer) {
		return false;
	}

	@Override
	public List<? extends BoundedSource> splitIntoBundles(
			long desiredBundleSizeBytes, RuntimeContainer runtimeContainer)
		throws Exception {

		List<BoundedSource> boundedSources = new ArrayList<>();

		boundedSources.add(this);

		return boundedSources;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferaySource.class);

	private static final long serialVersionUID = 7966201253956643887L;

}