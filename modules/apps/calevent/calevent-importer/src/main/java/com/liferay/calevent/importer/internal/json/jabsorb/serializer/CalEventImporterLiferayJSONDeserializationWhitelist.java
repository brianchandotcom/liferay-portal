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

package com.liferay.calevent.importer.internal.json.jabsorb.serializer;

import com.liferay.portal.kernel.util.PropsKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true,
	property = {
		PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES +
			"=com.liferay.portal.kernel.cal.DayAndPosition",
		PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES +
			"=com.liferay.portal.kernel.cal.Duration",
		PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES +
			"=com.liferay.portal.kernel.cal.TZSRecurrence",
		PropsKeys.JSON_DESERIALIZATION_WHITELIST_CLASS_NAMES +
			"=java.util.GregorianCalendar"
	},
	service = CalEventImporterLiferayJSONDeserializationWhitelist.class
)
public class CalEventImporterLiferayJSONDeserializationWhitelist {
}