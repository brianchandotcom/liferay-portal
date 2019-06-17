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

package com.liferay.portal.vulcan.internal.graphql.servlet;

import graphql.language.StringValue;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @author Javier Gamarra
 */
public class DateGraphQLScalarType extends GraphQLScalarType {

	public DateGraphQLScalarType() {
		super(
			"Date", "An RFC-3339 compliant DateTime Scalar",
			new Coercing<Date, String>() {

				@Override
				public Date parseLiteral(Object value)
					throws CoercingParseLiteralException {

					return _parse(value);
				}

				@Override
				public Date parseValue(Object value)
					throws CoercingParseValueException {

					return _parse(value);
				}

				@Override
				public String serialize(Object value)
					throws CoercingSerializeException {

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");

					return simpleDateFormat.format((Date)value);
				}

				private Date _parse(Object value) {
					if (value instanceof Date) {
						return (Date)value;
					}

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");

					try {
						if (value instanceof StringValue) {
							return simpleDateFormat.parse(
								((StringValue)value).getValue());
						}

						return simpleDateFormat.parse(value.toString());
					}
					catch (ParseException pe) {
						throw new CoercingSerializeException(
							"Expected something we can convert to " +
								"'java.util.Date'",
							pe);
					}
				}

			});
	}

}