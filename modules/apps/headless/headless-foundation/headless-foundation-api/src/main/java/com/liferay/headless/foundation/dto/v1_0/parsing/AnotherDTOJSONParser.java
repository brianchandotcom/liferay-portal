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

package com.liferay.headless.foundation.dto.v1_0.parsing;

import com.liferay.headless.foundation.dto.v1_0.AnotherDTO;
import com.liferay.headless.foundation.dto.v1_0.MyDTO;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Rubén Pulido
 */
public class AnotherDTOJSONParser extends JSONParser<AnotherDTO> {

	@Override
	protected AnotherDTO[] arrayOfObjectsToArrayOfDtos(Object[] objects) {
		return Stream.of(
			objects
		).map(
			object -> AnotherDTO.toAnotherDto((String)object)
		).toArray(
			size -> new AnotherDTO[size]
		);
	}

	@Override
	protected AnotherDTO createDto() {
		return new AnotherDTO();
	}

	@Override
	protected AnotherDTO[] createDtos() {
		return new AnotherDTO[0];
	}

	@Override
	protected void setField(AnotherDTO dto, String fieldName, Object value) {
		if (Objects.equals(fieldName, "a1")) {
			dto.setA1((String)value);
		}
		else if (Objects.equals(fieldName, "a2")) {
			dto.setA2((Boolean)value);
		}
		else if (Objects.equals(fieldName, "a3")) {
			if (value == null) {
				dto.setA3(null);
			}
			else {
				dto.setA3(Long.valueOf((String)value));
			}
		}
		else if (Objects.equals(fieldName, "a4")) {
			if (value == null) {
				dto.setA4(null);
			}
			else {
				dto.setA4(Integer.valueOf((String)value));
			}
		}
		else if (Objects.equals(fieldName, "a5")) {
			if (value == null) {
				dto.setA5(null);
			}
			else {
				dto.setA5(arrayOfObjectsToArrayOfStrings((Object[])value));
			}
		}
		else if (Objects.equals(fieldName, "a6")) {
			if (value == null) {
				dto.setA6(null);
			}
			else {
				dto.setA6(arrayOfObjectsToArrayOfBooleans((Object[])value));
			}
		}
		else if (Objects.equals(fieldName, "a7")) {
			if (value == null) {
				dto.setA7(null);
			}
			else {
				dto.setA7(arrayOfObjectsToArrayOfLongs((Object[])value));
			}
		}
		else if (Objects.equals(fieldName, "a8")) {
			if (value == null) {
				dto.setA8(null);
			}
			else {
				dto.setA8(arrayOfObjectsToArrayOfIntegers((Object[])value));
			}
		}
		else if (Objects.equals(fieldName, "a9")) {
			if (value == null) {
				dto.setA9(null);
			}
			else {
				dto.setA9(MyDTO.toMyDto((String)value));
			}
		}
		else if (Objects.equals(fieldName, "a10")) {
			if (value == null) {
				dto.setA10(null);
			}
			else {
				dto.setA10(arrayOfObjectsToArrayOfMyDtos((Object[])value));
			}
		}
		else {
			throw new IllegalArgumentException(
				"setField() not supported in AnotherDTOJSONParser for " +
					"fieldName " + fieldName);
		}
	}

}