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

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.BoundedReader;
import org.talend.components.api.component.runtime.BoundedSource;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;

/**
 * @author Zoltán Takács
 */
public class LiferaySource
	extends LiferaySourceOrSink implements BoundedSource {

	@Override
	public BoundedReader<?> createReader(RuntimeContainer runtimeContainer) {
		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Creating reader for fetching data from the datastore");
		}

		return new LiferayInputReader(
			runtimeContainer, this, _tLiferayInputProperties);
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

		return Collections.singletonList(this);
	}

	@Override
	public ValidationResult validate(RuntimeContainer runtimeContainer) {
		ValidationResult validationResult = super.validate(runtimeContainer);

		if (validationResult.getStatus() == ValidationResult.Result.ERROR) {
			return validationResult;
		}

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable(validationResult);

		Class<?> propertiesClass =
			liferayConnectionPropertiesProvider.getClass();

		if (!(liferayConnectionPropertiesProvider instanceof
				TLiferayInputProperties)) {

			validationResultMutable.setMessage(
				i18nMessages.getMessage(
					"error.validation.properties",
					propertiesClass.getCanonicalName()));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			return validationResultMutable;
		}

		_tLiferayInputProperties =
			(TLiferayInputProperties)liferayConnectionPropertiesProvider;

		_tLiferayInputProperties.connection = getEffectiveConnection(
			runtimeContainer);
		_tLiferayInputProperties.resource.connection = getEffectiveConnection(
			runtimeContainer);

		return validationResultMutable;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferaySource.class);

	private static final long serialVersionUID = 7966201253956643887L;

	private TLiferayInputProperties _tLiferayInputProperties;

}