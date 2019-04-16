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

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.TaxonomyCategoryBulkSelection;
import com.liferay.bulk.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class TaxonomyCategoryBulkSelectionSerDes {

	public static TaxonomyCategoryBulkSelection toDTO(String json) {
		TaxonomyCategoryBulkSelectionJSONParser
			taxonomyCategoryBulkSelectionJSONParser =
				new TaxonomyCategoryBulkSelectionJSONParser();

		return taxonomyCategoryBulkSelectionJSONParser.parseToDTO(json);
	}

	public static TaxonomyCategoryBulkSelection[] toDTOs(String json) {
		TaxonomyCategoryBulkSelectionJSONParser
			taxonomyCategoryBulkSelectionJSONParser =
				new TaxonomyCategoryBulkSelectionJSONParser();

		return taxonomyCategoryBulkSelectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		TaxonomyCategoryBulkSelection taxonomyCategoryBulkSelection) {

		if (taxonomyCategoryBulkSelection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"documentBulkSelection\": ");

		sb.append(
			DocumentBulkSelectionSerDes.toJSON(
				taxonomyCategoryBulkSelection.getDocumentBulkSelection()));
		sb.append(", ");

		sb.append("\"taxonomyCategoryIdsToAdd\": ");

		if (taxonomyCategoryBulkSelection.getTaxonomyCategoryIdsToAdd() ==
				null) {

			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < taxonomyCategoryBulkSelection.
					 getTaxonomyCategoryIdsToAdd().length;
				 i++) {

				sb.append(
					taxonomyCategoryBulkSelection.getTaxonomyCategoryIdsToAdd()
						[i]);

				if ((i + 1) < taxonomyCategoryBulkSelection.
						getTaxonomyCategoryIdsToAdd().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyCategoryIdsToRemove\": ");

		if (taxonomyCategoryBulkSelection.getTaxonomyCategoryIdsToRemove() ==
				null) {

			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < taxonomyCategoryBulkSelection.
					 getTaxonomyCategoryIdsToRemove().length;
				 i++) {

				sb.append(
					taxonomyCategoryBulkSelection.
						getTaxonomyCategoryIdsToRemove()[i]);

				if ((i + 1) < taxonomyCategoryBulkSelection.
						getTaxonomyCategoryIdsToRemove().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		TaxonomyCategoryBulkSelection taxonomyCategoryBulkSelection) {

		if (taxonomyCategoryBulkSelection == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		map.put(
			"documentBulkSelection",
			DocumentBulkSelectionSerDes.toJSON(
				taxonomyCategoryBulkSelection.getDocumentBulkSelection()));

		map.put(
			"taxonomyCategoryIdsToAdd",
			String.valueOf(
				taxonomyCategoryBulkSelection.getTaxonomyCategoryIdsToAdd()));

		map.put(
			"taxonomyCategoryIdsToRemove",
			String.valueOf(
				taxonomyCategoryBulkSelection.
					getTaxonomyCategoryIdsToRemove()));

		return map;
	}

	private static class TaxonomyCategoryBulkSelectionJSONParser
		extends BaseJSONParser<TaxonomyCategoryBulkSelection> {

		@Override
		protected TaxonomyCategoryBulkSelection createDTO() {
			return new TaxonomyCategoryBulkSelection();
		}

		@Override
		protected TaxonomyCategoryBulkSelection[] createDTOArray(int size) {
			return new TaxonomyCategoryBulkSelection[size];
		}

		@Override
		protected void setField(
			TaxonomyCategoryBulkSelection taxonomyCategoryBulkSelection,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "documentBulkSelection")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategoryBulkSelection.setDocumentBulkSelection(
						DocumentBulkSelectionSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryIdsToAdd")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategoryBulkSelection.setTaxonomyCategoryIdsToAdd(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryIdsToRemove")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategoryBulkSelection.
						setTaxonomyCategoryIdsToRemove(
							toLongs((Object[])jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}