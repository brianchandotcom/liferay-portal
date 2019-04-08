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

import com.liferay.talend.dataset.RestDataSet;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

/**
 * @author Zoltán Takács
 */
@Documentation("TODO fill the documentation for this configuration")
@GridLayout(
	{
		@GridLayout.Row("_restDataSet"), @GridLayout.Row("_timeout"),
		@GridLayout.Row("_maxItemsPerRequest")
	}
)
public class LiferayInputMapperConfiguration implements Serializable {

	public int getMaxItemsPerRequest() {
		return _maxItemsPerRequest;
	}

	public RestDataSet getRestDataSet() {
		return _restDataSet;
	}

	public int getTimeout() {
		return _timeout;
	}

	public LiferayInputMapperConfiguration setMaxItemsPerRequest(
		int maxItemsPerRequest) {

		_maxItemsPerRequest = maxItemsPerRequest;

		return this;
	}

	public LiferayInputMapperConfiguration setRestDataSet(
		RestDataSet restDataSet) {

		_restDataSet = restDataSet;

		return this;
	}

	public LiferayInputMapperConfiguration setTimeout(int timeout) {
		_timeout = timeout;

		return this;
	}

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private int _maxItemsPerRequest;

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private RestDataSet _restDataSet;

	@Documentation("TODO fill the documentation for this parameter")
	@Option
	private int _timeout;

}