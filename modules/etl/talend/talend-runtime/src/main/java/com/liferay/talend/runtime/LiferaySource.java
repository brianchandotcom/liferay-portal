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

import com.liferay.talend.runtime.reader.LiferayReader;
import com.liferay.talend.tliferayinput.TLiferayInputProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.Reader;
import org.talend.components.api.component.runtime.Source;
import org.talend.components.api.container.RuntimeContainer;

/**
 * @author Zoltán Takács
 */
public class LiferaySource extends LiferaySourceOrSink implements Source {

	@Override
	public Reader<?> createReader(RuntimeContainer runtimeContainer) {
		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Creating reader for fetching data from the datastore");
		}

		Object componentData = runtimeContainer.getComponentData(
			runtimeContainer.getCurrentComponentId(),
			"COMPONENT_RUNTIME_PROPERTIES");

		if (!(componentData instanceof TLiferayInputProperties)) {
			throw new IllegalArgumentException(
				String.format(
					"Unable to locate %s in given runtime container",
					TLiferayInputProperties.class));
		}

		return new LiferayReader(this, (TLiferayInputProperties)componentData);
	}

	@Override
	protected String getLiferayConnectionPropertiesPath() {
		return "resource." + super.getLiferayConnectionPropertiesPath();
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferaySource.class);

	private static final long serialVersionUID = 7966201253956643887L;

}