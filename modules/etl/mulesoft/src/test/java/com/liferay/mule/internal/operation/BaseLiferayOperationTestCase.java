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

package com.liferay.mule.internal.operation;

import java.util.regex.Pattern;

import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.TypedValue;

/**
 * @author Matija Petanjek
 */
public abstract class BaseLiferayOperationTestCase
	extends MuleArtifactFunctionalTestCase {

	protected String getPayload(String flowName) throws Exception {
		Event event = runFlow(flowName);

		Message message = event.getMessage();

		TypedValue<String> payloadTypedValue = message.getPayload();

		return payloadTypedValue.getValue();
	}

	protected static final Pattern productPattern = Pattern.compile(
		"[\\s\\S]+\"active\"[\\s\\S]+\"catalogId\"[\\s\\S]+" +
			"\"name\"[\\s\\S]+\"productType\"[\\s\\S]+");

}