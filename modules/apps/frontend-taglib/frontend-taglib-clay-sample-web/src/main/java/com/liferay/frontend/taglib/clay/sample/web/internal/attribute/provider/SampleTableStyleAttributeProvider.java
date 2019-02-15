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

package com.liferay.frontend.taglib.clay.sample.web.internal.attribute.provider;

import com.liferay.frontend.taglib.clay.attribute.provider.ClayComponentAttributeProvider;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true,
	property = {
		"clay.component.attribute.provider.key=SampleTableAttributeProvider",
		"service.ranking:Integer=1"
	},
	service = ClayComponentAttributeProvider.class
)
public class SampleTableStyleAttributeProvider
	implements ClayComponentAttributeProvider {

	@Override
	public Map<String, Object> getAttributes() {
		HashMap<String, Object> attributes = new HashMap<>();

		attributes.put("tableClasses", "sample-table");

		return attributes;
	}

}