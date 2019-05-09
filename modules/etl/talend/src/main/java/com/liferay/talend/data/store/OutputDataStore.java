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

package com.liferay.talend.data.store;

import com.liferay.talend.configuration.OperationAction;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.DefaultValue;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;

/**
 * @author Igor Beslic
 */
@DataStore("OutputDataStore")
@GridLayout(
	{@GridLayout.Row("_operationAction"), @GridLayout.Row("_genericDataStore")}
)
public class OutputDataStore implements Serializable {

	public GenericDataStore getGenericDataStore() {
		return _genericDataStore;
	}

	public OperationAction getOperationAction() {
		return _operationAction;
	}

	public OutputDataStore setGenericDataStore(
		GenericDataStore genericDataStore) {

		_genericDataStore = genericDataStore;

		return this;
	}

	public OutputDataStore setOperationAction(OperationAction operationAction) {
		_operationAction = operationAction;

		return this;
	}

	@Option
	private GenericDataStore _genericDataStore;

	@DefaultValue("INSERT")
	@Option
	private OperationAction _operationAction;

}