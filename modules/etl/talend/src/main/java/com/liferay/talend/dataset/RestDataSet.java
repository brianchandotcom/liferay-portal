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

import java.io.Serializable;

import com.liferay.talend.datastore.InputDataStore;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Suggestable;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
@DataSet("RestDataSet")
@GridLayout({
	@GridLayout.Row({ "inputDataStore" }),
    @GridLayout.Row({ "endpoint" })
})
@Documentation("TODO fill the documentation for this configuration")
public class RestDataSet implements Serializable {

	@Option
	private InputDataStore inputDataStore;

    @Option
    @Required
    @Suggestable(value = "fetchEndpoints", parameters = { "inputDataStore" })
    @Documentation("Endpoint")
    /**
     * DataStore parameter now is not needed, just an example how we can use it
     * in UIActionService if needed.
     */
    private String endpoint;

    public InputDataStore getInputDataStore() {
        return inputDataStore;
    }

    public RestDataSet setInputDataStore(InputDataStore inputDataStore) {
        this.inputDataStore = inputDataStore;
        return this;
    }

}