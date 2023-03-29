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

package com.liferay.osb.faro.web.internal.util;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.FieldMapping;
import com.liferay.osb.faro.engine.client.model.FieldMappingMap;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Matthew Kong
 */
public class FieldMappingUtil {

	public static List<FieldMappingMap> getNewFieldMappingMaps(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String context, List<FieldMappingMap> fieldMappingMaps) {

		List<FieldMappingMap> newFieldMappingMaps = new ArrayList<>();

		Stream<FieldMappingMap> fieldMappingMapsStream =
			fieldMappingMaps.stream();

		Results<FieldMapping> results = contactsEngineClient.getFieldMappings(
			faroProject, context,
			fieldMappingMapsStream.map(
				FieldMappingMap::getName
			).collect(
				Collectors.toList()
			),
			1, 10000, null);

		List<FieldMapping> fieldMappings = results.getItems();

		Stream<FieldMapping> fieldMappingsStream = fieldMappings.stream();

		Set<String> currentFieldNames = fieldMappingsStream.map(
			FieldMapping::getFieldName
		).collect(
			Collectors.toSet()
		);

		Set<String> newFieldMappingNames = new HashSet<>();

		for (FieldMappingMap fieldMappingMap : fieldMappingMaps) {
			String name = fieldMappingMap.getName();

			if (currentFieldNames.contains(name) ||
				newFieldMappingNames.contains(name)) {

				continue;
			}

			newFieldMappingNames.add(name);

			newFieldMappingMaps.add(fieldMappingMap);
		}

		return newFieldMappingMaps;
	}

}