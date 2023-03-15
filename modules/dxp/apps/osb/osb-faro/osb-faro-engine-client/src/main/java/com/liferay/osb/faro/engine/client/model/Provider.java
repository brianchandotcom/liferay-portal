/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.engine.client.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.liferay.osb.faro.engine.client.model.provider.CSVProvider;
import com.liferay.osb.faro.engine.client.model.provider.LiferayProvider;
import com.liferay.osb.faro.engine.client.model.provider.SalesforceProvider;

/**
 * @author Matthew Kong
 */
@JsonSubTypes(
	{
		@JsonSubTypes.Type(name = CSVProvider.TYPE, value = CSVProvider.class),
		@JsonSubTypes.Type(
			name = LiferayProvider.TYPE, value = LiferayProvider.class
		),
		@JsonSubTypes.Type(
			name = SalesforceProvider.TYPE, value = SalesforceProvider.class
		)
	}
)
@JsonTypeInfo(
	include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type",
	use = JsonTypeInfo.Id.NAME
)
public interface Provider {

	public String getType();

}