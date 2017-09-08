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

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntrySerializer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, service = AMImageConfigurationEntrySerializer.class
)
public class AMImageConfigurationEntrySerializerImpl
	implements AMImageConfigurationEntrySerializer {

	@Override
	public AMImageConfigurationEntry deserialize(String s) {
		return _amImageConfigurationEntryParser.parse(s);
	}

	@Override
	public String serialize(
		AMImageConfigurationEntry amImageConfigurationEntry) {

		return _amImageConfigurationEntryParser.getConfigurationString(
			amImageConfigurationEntry);
	}

	@Reference
	private AMImageConfigurationEntryParser _amImageConfigurationEntryParser;

}