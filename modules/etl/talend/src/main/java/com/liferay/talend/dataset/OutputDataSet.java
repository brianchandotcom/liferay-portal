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

import com.liferay.talend.data.store.OutputDataStore;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Suggestable;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.DefaultValue;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.Structure;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 * @review
 */
@DataSet("OutputDataSet")
@GridLayout(
	{
		@GridLayout.Row("_outputDataStore"), @GridLayout.Row("endpoint"),
		@GridLayout.Row(
			{"firstPathParam", "secondPathParam", "thirdPathParam"}
		),
		@GridLayout.Row("_inputRecordSchemaFieldNames"),
		@GridLayout.Row("_mainFlowEmitterSchemaFieldNames"),
		@GridLayout.Row("_rejectFlowEmitterSchemaFieldNames")
	}
)
public class OutputDataSet implements Serializable {

	public String getEndpoint() {
		return endpoint;
	}

	public String getFirstPathParam() {
		return firstPathParam;
	}

	public OutputDataStore getOutputDataStore() {
		return _outputDataStore;
	}

	public String getSecondPathParam() {
		return secondPathParam;
	}

	public String getThirdPathParam() {
		return thirdPathParam;
	}

	public OutputDataSet setEndpoint(String endpoint) {
		this.endpoint = endpoint;

		return this;
	}

	public OutputDataSet setFirstPathParam(String firstPathParam) {
		this.firstPathParam = firstPathParam;

		return this;
	}

	public OutputDataSet setOutputDataStore(OutputDataStore outputDataStore) {
		_outputDataStore = outputDataStore;

		return this;
	}

	public OutputDataSet setSecondPathParam(String secondPathParam) {
		this.secondPathParam = secondPathParam;

		return this;
	}

	public OutputDataSet setThirdPathParam(String thirdPathParam) {
		this.thirdPathParam = thirdPathParam;

		return this;
	}

	/**
	 * DataStore parameter now is not needed, just an example how we can use it
	 * in UIActionService if needed.
	 */
	@Option
	@Required
	@Suggestable(
		parameters = "_outputDataStore", value = "fetchOutputEndpoints"
	)
	protected String endpoint;

	@DefaultValue("\"\"")
	@Option
	protected String firstPathParam;

	@DefaultValue("\"\"")
	@Option
	protected String secondPathParam;

	@DefaultValue("\"\"")
	@Option
	protected String thirdPathParam;

	@Option
	@Structure(type = Structure.Type.IN)
	private final List<String> _inputRecordSchemaFieldNames = new ArrayList<>();

	@Option
	@Structure(type = Structure.Type.OUT)
	private final List<String> _mainFlowEmitterSchemaFieldNames =
		new ArrayList<>();

	@Option
	private OutputDataStore _outputDataStore;

	@Option
	@Structure(type = Structure.Type.OUT, value = "REJECT")
	private final List<String> _rejectFlowEmitterSchemaFieldNames =
		new ArrayList<>();

}