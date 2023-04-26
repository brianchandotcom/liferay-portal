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

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectRelationshipElementsParser;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;

/**
 * @author Carlos Correa
 * @author Sergio Jimenez del Coso
 */
public abstract class BaseObjectRelationshipElementsParserImpl<T>
	implements ObjectRelationshipElementsParser {

	public BaseObjectRelationshipElementsParserImpl(
		ObjectDefinition objectDefinition) {

		this.objectDefinition = objectDefinition;
	}

	@Override
	public String getClassName() {
		return objectDefinition.getClassName();
	}

	@Override
	public long getCompanyId() {
		return objectDefinition.getCompanyId();
	}

	protected List<T> parseMany(Object object) {
		List<T> objects = null;

		if (object instanceof List) {
			objects = (List<T>)object;
		}
		else if (object instanceof Object[]) {
			objects = (List<T>)Arrays.asList((Object[])object);
		}
		else {
			throw new BadRequestException(
				"Unable to create nested object entries");
		}

		return TransformUtil.transform(objects, this::parseOne);
	}

	protected abstract T parseOne(Object object);

	protected ObjectEntry toObjectEntry(
		Map<String, Object> nestedObjectEntryProperties) {

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setExternalReferenceCode(
			(String)nestedObjectEntryProperties.remove(
				"externalReferenceCode"));
		objectEntry.setProperties(nestedObjectEntryProperties);

		return objectEntry;
	}

	protected void validateOne(Object object) {
		if (!(object instanceof Map)) {
			throw new BadRequestException(
				"Unable to create nested object entries");
		}
	}

	protected ObjectDefinition objectDefinition;

}