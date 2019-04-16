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

package com.liferay.talend.dataset;

import com.liferay.talend.data.store.BaseDataStore;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Suggestable;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.DefaultValue;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 * @review
 */
@DataSet("InputDataSet")
@GridLayout(
	{
		@GridLayout.Row("_baseDataStore"), @GridLayout.Row("endpoint"),
		@GridLayout.Row({"firstPathParam", "secondPathParam", "thirdPathParam"})
	}
)
public class InputDataSet implements Serializable {

	public String getEndpoint() {
		return endpoint;
	}

	public String getFirstPathParam() {
		return firstPathParam;
	}

	public BaseDataStore getInputDataStore() {
		return _baseDataStore;
	}

	public String getSecondPathParam() {
		return secondPathParam;
	}

	public String getThirdPathParam() {
		return thirdPathParam;
	}

	public InputDataSet setEndpoint(String endpoint) {
		this.endpoint = endpoint;

		return this;
	}

	public InputDataSet setFirstPathParam(String firstPathParam) {
		this.firstPathParam = firstPathParam;

		return this;
	}

	public InputDataSet setInputDataStore(BaseDataStore baseDataStore) {
		_baseDataStore = baseDataStore;

		return this;
	}

	public InputDataSet setSecondPathParam(String secondPathParam) {
		this.secondPathParam = secondPathParam;

		return this;
	}

	public InputDataSet setThirdPathParam(String thirdPathParam) {
		this.thirdPathParam = thirdPathParam;

		return this;
	}

	/**
	 * DataStore parameter now is not needed, just an example how we can use it
	 * in UIActionService if needed.
	 */
	@Option
	@Required
	@Suggestable(parameters = "_baseDataStore", value = "fetchEndpoints")
	protected String endpoint;

	@DefaultValue("")
	@Option
	protected String firstPathParam;

	@DefaultValue("")
	@Option
	protected String secondPathParam;

	@DefaultValue("")
	@Option
	protected String thirdPathParam;

	@Option
	private BaseDataStore _baseDataStore;

}