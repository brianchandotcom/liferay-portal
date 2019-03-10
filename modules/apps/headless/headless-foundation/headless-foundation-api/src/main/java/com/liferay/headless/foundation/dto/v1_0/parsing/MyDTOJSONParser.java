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

import java.text.ParseException;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Rubén Pulido
 */
public class MyDTOJSONParser extends JSONParser<MyDTO> {

	@Override
	protected MyDTO[] arrayOfObjectsToArrayOfDtos(Object[] objects) {
		return Stream.of(
			objects
		).map(
			object -> MyDTO.toMyDto((String)object)
		).toArray(
			size -> new MyDTO[size]
		);
	}

	@Override
	protected MyDTO createDto() {
		return new MyDTO();
	}

	@Override
	protected MyDTO[] createDtos() {
		return new MyDTO[0];
	}

	@Override
	protected void setField(MyDTO dto, String fieldName, Object value) {
		if (Objects.equals(fieldName, "f1")) {
			dto.setF1((String)value);
		}
		else if (Objects.equals(fieldName, "f2")) {
			dto.setF2((Boolean)value);
		}
		else if (Objects.equals(fieldName, "f3")) {
			if (value == null) {
				dto.setF3(null);
			}
			else {
				dto.setF3(Long.valueOf((String)value));
			}
		}
		else if (Objects.equals(fieldName, "f4")) {
			if (value == null) {
				dto.setF4(null);
			}
			else {
				dto.setF4(Integer.valueOf((String)value));
			}
		}
		else if (Objects.equals(fieldName, "f5")) {
			if (value == null) {
				dto.setF5(null);
			}
			else {
				dto.setF5(arrayOfObjectsToArrayOfStrings((Object[])value));
			}
		}
		else if (Objects.equals(fieldName, "f6")) {
			if (value == null) {
				dto.setF6(null);
			}
			else {
				dto.setF6(arrayOfObjectsToArrayOfBooleans((Object[])value));
			}
		}
		else if (Objects.equals(fieldName, "f7")) {
			if (value == null) {
				dto.setF7(null);
			}
			else {
				dto.setF7(arrayOfObjectsToArrayOfLongs((Object[])value));
			}
		}
		else if (Objects.equals(fieldName, "f8")) {
			if (value == null) {
				dto.setF8(null);
			}
			else {
				dto.setF8(arrayOfObjectsToArrayOfIntegers((Object[])value));
			}
		}
		else if (Objects.equals(fieldName, "f9")) {
			if (value == null) {
				dto.setF9(null);
			}
			else {
				dto.setF9(AnotherDTO.toAnotherDto((String)value));
			}
		}
		else if (Objects.equals(fieldName, "f10")) {
			if (value == null) {
				dto.setF10(null);
			}
			else {
				dto.setF10(arrayOfObjectsToArrayOfAnotherDtos((Object[])value));
			}
		}
		else if (Objects.equals(fieldName, "f11")) {
			if (value == null) {
				dto.setF11(null);
			}
			else {
				dto.setF11(MyDTO.toMyDto((String)value));
			}
		}
		else if (Objects.equals(fieldName, "f12")) {
			if (value == null) {
				dto.setF12(null);
			}
			else {
				dto.setF12(arrayOfObjectsToArrayOfMyDtos((Object[])value));
			}
		}
		else if (Objects.equals(fieldName, "f13")) {
			if (value == null) {
				dto.setF13(null);
			}
			else {
				try {
					dto.setF13(dateFormat.parse((String)value));
				}
				catch (ParseException pe) {
					throw new IllegalArgumentException(
						"Could not parse date from value " + value, pe);
				}
			}
		}
		else if (Objects.equals(fieldName, "f14")) {
			if (value == null) {
				dto.setF14(null);
			}
			else {
				dto.setF14(arrayOfObjectsToArrayOfDates((Object[])value));
			}
		}
		else {
			throw new IllegalArgumentException(
				"setField() not supported in MyDTOJSONParser for fieldName " +
					fieldName);
		}
	}

}