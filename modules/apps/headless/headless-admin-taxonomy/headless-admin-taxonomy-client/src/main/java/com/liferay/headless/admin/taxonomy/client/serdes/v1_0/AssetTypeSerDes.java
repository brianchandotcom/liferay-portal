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

package com.liferay.headless.admin.taxonomy.client.serdes.v1_0;

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.AssetType;
import com.liferay.headless.admin.taxonomy.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class AssetTypeSerDes {

	public static AssetType toDTO(String json) {
		AssetTypeJSONParser assetTypeJSONParser = new AssetTypeJSONParser();

		return assetTypeJSONParser.parseToDTO(json);
	}

	public static AssetType[] toDTOs(String json) {
		AssetTypeJSONParser assetTypeJSONParser = new AssetTypeJSONParser();

		return assetTypeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AssetType assetType) {
		if (assetType == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"required\": ");

		if (assetType.getRequired() == null) {
			sb.append("null");
		}
		else {
			sb.append(assetType.getRequired());
		}

		sb.append(", ");

		sb.append("\"subtype\": ");

		if (assetType.getSubtype() == null) {
			sb.append("null");
		}
		else {
			sb.append(assetType.getSubtype());
		}

		sb.append(", ");

		sb.append("\"type\": ");

		if (assetType.getType() == null) {
			sb.append("null");
		}
		else {
			sb.append(assetType.getType());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<AssetType> assetTypes) {
		if (assetTypes == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (AssetType assetType : assetTypes) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(assetType));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class AssetTypeJSONParser extends BaseJSONParser<AssetType> {

		protected AssetType createDTO() {
			return new AssetType();
		}

		protected AssetType[] createDTOArray(int size) {
			return new AssetType[size];
		}

		protected void setField(
			AssetType assetType, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					assetType.setRequired((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subtype")) {
				if (jsonParserFieldValue != null) {
					assetType.setSubtype((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					assetType.setType((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}