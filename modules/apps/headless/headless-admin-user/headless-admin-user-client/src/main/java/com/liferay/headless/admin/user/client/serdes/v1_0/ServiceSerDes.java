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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.HoursAvailable;
import com.liferay.headless.admin.user.client.dto.v1_0.Service;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ServiceSerDes {

	public static Service toDTO(String json) {
		ServiceJSONParser serviceJSONParser = new ServiceJSONParser();

		return serviceJSONParser.parseToDTO(json);
	}

	public static Service[] toDTOs(String json) {
		ServiceJSONParser serviceJSONParser = new ServiceJSONParser();

		return serviceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Service service) {
		if (service == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"hoursAvailable\": ");

		if (service.getHoursAvailable() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < service.getHoursAvailable().length; i++) {
				sb.append(service.getHoursAvailable()[i]);

				if ((i + 1) < service.getHoursAvailable().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (service.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(service.getId());
		}

		sb.append(", ");

		sb.append("\"serviceType\": ");

		if (service.getServiceType() == null) {
			sb.append("null");
		}
		else {
			sb.append(service.getServiceType());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<Service> services) {
		if (services == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Service service : services) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(service));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class ServiceJSONParser extends BaseJSONParser<Service> {

		protected Service createDTO() {
			return new Service();
		}

		protected Service[] createDTOArray(int size) {
			return new Service[size];
		}

		protected void setField(
			Service service, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "hoursAvailable")) {
				if (jsonParserFieldValue != null) {
					service.setHoursAvailable(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> HoursAvailableSerDes.toDTO((String)object)
						).toArray(
							size -> new HoursAvailable[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					service.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "serviceType")) {
				if (jsonParserFieldValue != null) {
					service.setServiceType((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}