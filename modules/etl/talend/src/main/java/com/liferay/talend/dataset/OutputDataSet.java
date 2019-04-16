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
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 * @review
 */
@DataSet("OutputDataSet")
@GridLayout({@GridLayout.Row("_baseDataStore"), @GridLayout.Row("_endpoint")})
public class OutputDataSet implements Serializable {

	public String getEndpoint() {
		return _endpoint;
	}

	public BaseDataStore getInputDataStore() {
		return _baseDataStore;
	}

	public OutputDataSet setEndpoint(String endpoint) {
		_endpoint = endpoint;

		return this;
	}

	public OutputDataSet setInputDataStore(BaseDataStore baseDataStore) {
		_baseDataStore = baseDataStore;

		return this;
	}

	@Option
	private BaseDataStore _baseDataStore;

	/**
	 * DataStore parameter now is not needed, just an example how we can use it
	 * in UIActionService if needed.
	 */
	@Option
	@Required
	@Suggestable(parameters = "_baseDataStore", value = "fetchEndpoints")
	private String _endpoint;

}