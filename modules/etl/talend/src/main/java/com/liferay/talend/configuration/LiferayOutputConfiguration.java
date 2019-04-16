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

package com.liferay.talend.configuration;

import com.liferay.talend.dataset.OutputDataSet;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.DefaultValue;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayouts;

/**
 * @author Zoltán Takács
 */
@GridLayouts(
	{
		@GridLayout(
			names = GridLayout.FormType.MAIN,
			value = {
				@GridLayout.Row("_operationAction"),
				@GridLayout.Row("_outputDataSet")
			}
		),
		@GridLayout(
			names = GridLayout.FormType.ADVANCED,
			value = @GridLayout.Row("_timeout")
		)
	}
)
public class LiferayOutputConfiguration implements Serializable {

	public OutputDataSet getOutputDataSet() {
		return _outputDataSet;
	}

	public int getTimeout() {
		return _timeout;
	}

	public LiferayOutputConfiguration setOutputDataSet(
		OutputDataSet outputDataSet) {

		_outputDataSet = outputDataSet;

		return this;
	}

	public LiferayOutputConfiguration setTimeout(int timeout) {
		_timeout = timeout;

		return this;
	}

	@DefaultValue("INSERT")
	@Option
	private OperationAction _operationAction;

	@Option
	private OutputDataSet _outputDataSet;

	@Option
	private int _timeout = 60000;

}