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

import com.liferay.talend.properties.output.LiferayOutputProperties;
import com.liferay.talend.runtime.writer.LiferayWriteOperation;

import org.talend.components.api.component.runtime.Sink;
import org.talend.components.api.component.runtime.WriteOperation;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.daikon.properties.ValidationResult;

/**
 * @author Zoltán Takács
 */
public class LiferaySink extends LiferaySourceOrSink implements Sink {

	@Override
	public WriteOperation<?> createWriteOperation() {
		return new LiferayWriteOperation(this, _liferayOutputProperties);
	}

	@Override
	public ValidationResult validate(RuntimeContainer runtimeContainer) {
		Object componentData = runtimeContainer.getComponentData(
			runtimeContainer.getCurrentComponentId(),
			"COMPONENT_RUNTIME_PROPERTIES");

		if (!(componentData instanceof LiferayOutputProperties)) {
			return new ValidationResult(
				ValidationResult.Result.ERROR,
				String.format(
					"Unable to locate %s in given runtime container",
					LiferayOutputProperties.class));
		}

		_liferayOutputProperties = (LiferayOutputProperties)componentData;

		if (_liferayOutputProperties.getOperation() == null) {
			return new ValidationResult(
				ValidationResult.Result.ERROR,
				"Unable to configure Sink without operation properly set");
		}

		ValidationResult validationResult = super.validate(runtimeContainer);

		if (validationResult.getStatus() == ValidationResult.Result.ERROR) {
			return validationResult;
		}

		return validationResult;
	}

	@Override
	protected String getLiferayConnectionPropertiesPath() {
		return "resource." + super.getLiferayConnectionPropertiesPath();
	}

	private LiferayOutputProperties _liferayOutputProperties;

}