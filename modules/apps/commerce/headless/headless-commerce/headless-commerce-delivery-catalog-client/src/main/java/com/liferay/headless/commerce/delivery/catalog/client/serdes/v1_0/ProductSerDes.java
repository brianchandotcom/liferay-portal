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

package com.liferay.headless.commerce.delivery.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.Attachment;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.Category;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.RelatedProduct;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.delivery.catalog.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class ProductSerDes {

	public static Product toDTO(String json) {
		ProductJSONParser productJSONParser = new ProductJSONParser();

		return productJSONParser.parseToDTO(json);
	}

	public static Product[] toDTOs(String json) {
		ProductJSONParser productJSONParser = new ProductJSONParser();

		return productJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Product product) {
		if (product == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (product.getAttachments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"attachments\": ");

			sb.append("[");

			for (int i = 0; i < product.getAttachments().length; i++) {
				sb.append(String.valueOf(product.getAttachments()[i]));

				if ((i + 1) < product.getAttachments().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (product.getCategories() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"categories\": ");

			sb.append("[");

			for (int i = 0; i < product.getCategories().length; i++) {
				sb.append(String.valueOf(product.getCategories()[i]));

				if ((i + 1) < product.getCategories().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (product.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(product.getCreateDate()));

			sb.append("\"");
		}

		if (product.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(product.getDescription()));

			sb.append("\"");
		}

		if (product.getExpando() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expando\": ");

			sb.append(_toJSON(product.getExpando()));
		}

		if (product.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(product.getId());
		}

		if (product.getImages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"images\": ");

			sb.append("[");

			for (int i = 0; i < product.getImages().length; i++) {
				sb.append(String.valueOf(product.getImages()[i]));

				if ((i + 1) < product.getImages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (product.getMetaDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"metaDescription\": ");

			sb.append("\"");

			sb.append(_escape(product.getMetaDescription()));

			sb.append("\"");
		}

		if (product.getMetaKeyword() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"metaKeyword\": ");

			sb.append("\"");

			sb.append(_escape(product.getMetaKeyword()));

			sb.append("\"");
		}

		if (product.getMetaTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"metaTitle\": ");

			sb.append("\"");

			sb.append(_escape(product.getMetaTitle()));

			sb.append("\"");
		}

		if (product.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(product.getModifiedDate()));

			sb.append("\"");
		}

		if (product.getMultipleOrderQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"multipleOrderQuantity\": ");

			sb.append(product.getMultipleOrderQuantity());
		}

		if (product.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(product.getName()));

			sb.append("\"");
		}

		if (product.getProductConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productConfiguration\": ");

			sb.append(String.valueOf(product.getProductConfiguration()));
		}

		if (product.getProductId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(product.getProductId());
		}

		if (product.getProductOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productOptions\": ");

			sb.append("[");

			for (int i = 0; i < product.getProductOptions().length; i++) {
				sb.append(String.valueOf(product.getProductOptions()[i]));

				if ((i + 1) < product.getProductOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (product.getProductSpecifications() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productSpecifications\": ");

			sb.append("[");

			for (int i = 0; i < product.getProductSpecifications().length;
				 i++) {

				sb.append(
					String.valueOf(product.getProductSpecifications()[i]));

				if ((i + 1) < product.getProductSpecifications().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (product.getProductType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productType\": ");

			sb.append("\"");

			sb.append(_escape(product.getProductType()));

			sb.append("\"");
		}

		if (product.getRelatedProducts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relatedProducts\": ");

			sb.append("[");

			for (int i = 0; i < product.getRelatedProducts().length; i++) {
				sb.append(String.valueOf(product.getRelatedProducts()[i]));

				if ((i + 1) < product.getRelatedProducts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (product.getShortDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shortDescription\": ");

			sb.append("\"");

			sb.append(_escape(product.getShortDescription()));

			sb.append("\"");
		}

		if (product.getSkus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skus\": ");

			sb.append("[");

			for (int i = 0; i < product.getSkus().length; i++) {
				sb.append(String.valueOf(product.getSkus()[i]));

				if ((i + 1) < product.getSkus().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (product.getSlug() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"slug\": ");

			sb.append("\"");

			sb.append(_escape(product.getSlug()));

			sb.append("\"");
		}

		if (product.getTags() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tags\": ");

			sb.append("[");

			for (int i = 0; i < product.getTags().length; i++) {
				sb.append("\"");

				sb.append(_escape(product.getTags()[i]));

				sb.append("\"");

				if ((i + 1) < product.getTags().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (product.getUrlImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"urlImage\": ");

			sb.append("\"");

			sb.append(_escape(product.getUrlImage()));

			sb.append("\"");
		}

		if (product.getUrls() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"urls\": ");

			sb.append(_toJSON(product.getUrls()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProductJSONParser productJSONParser = new ProductJSONParser();

		return productJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Product product) {
		if (product == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (product.getAttachments() == null) {
			map.put("attachments", null);
		}
		else {
			map.put("attachments", String.valueOf(product.getAttachments()));
		}

		if (product.getCategories() == null) {
			map.put("categories", null);
		}
		else {
			map.put("categories", String.valueOf(product.getCategories()));
		}

		if (product.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(product.getCreateDate()));
		}

		if (product.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(product.getDescription()));
		}

		if (product.getExpando() == null) {
			map.put("expando", null);
		}
		else {
			map.put("expando", String.valueOf(product.getExpando()));
		}

		if (product.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(product.getId()));
		}

		if (product.getImages() == null) {
			map.put("images", null);
		}
		else {
			map.put("images", String.valueOf(product.getImages()));
		}

		if (product.getMetaDescription() == null) {
			map.put("metaDescription", null);
		}
		else {
			map.put(
				"metaDescription",
				String.valueOf(product.getMetaDescription()));
		}

		if (product.getMetaKeyword() == null) {
			map.put("metaKeyword", null);
		}
		else {
			map.put("metaKeyword", String.valueOf(product.getMetaKeyword()));
		}

		if (product.getMetaTitle() == null) {
			map.put("metaTitle", null);
		}
		else {
			map.put("metaTitle", String.valueOf(product.getMetaTitle()));
		}

		if (product.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(product.getModifiedDate()));
		}

		if (product.getMultipleOrderQuantity() == null) {
			map.put("multipleOrderQuantity", null);
		}
		else {
			map.put(
				"multipleOrderQuantity",
				String.valueOf(product.getMultipleOrderQuantity()));
		}

		if (product.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(product.getName()));
		}

		if (product.getProductConfiguration() == null) {
			map.put("productConfiguration", null);
		}
		else {
			map.put(
				"productConfiguration",
				String.valueOf(product.getProductConfiguration()));
		}

		if (product.getProductId() == null) {
			map.put("productId", null);
		}
		else {
			map.put("productId", String.valueOf(product.getProductId()));
		}

		if (product.getProductOptions() == null) {
			map.put("productOptions", null);
		}
		else {
			map.put(
				"productOptions", String.valueOf(product.getProductOptions()));
		}

		if (product.getProductSpecifications() == null) {
			map.put("productSpecifications", null);
		}
		else {
			map.put(
				"productSpecifications",
				String.valueOf(product.getProductSpecifications()));
		}

		if (product.getProductType() == null) {
			map.put("productType", null);
		}
		else {
			map.put("productType", String.valueOf(product.getProductType()));
		}

		if (product.getRelatedProducts() == null) {
			map.put("relatedProducts", null);
		}
		else {
			map.put(
				"relatedProducts",
				String.valueOf(product.getRelatedProducts()));
		}

		if (product.getShortDescription() == null) {
			map.put("shortDescription", null);
		}
		else {
			map.put(
				"shortDescription",
				String.valueOf(product.getShortDescription()));
		}

		if (product.getSkus() == null) {
			map.put("skus", null);
		}
		else {
			map.put("skus", String.valueOf(product.getSkus()));
		}

		if (product.getSlug() == null) {
			map.put("slug", null);
		}
		else {
			map.put("slug", String.valueOf(product.getSlug()));
		}

		if (product.getTags() == null) {
			map.put("tags", null);
		}
		else {
			map.put("tags", String.valueOf(product.getTags()));
		}

		if (product.getUrlImage() == null) {
			map.put("urlImage", null);
		}
		else {
			map.put("urlImage", String.valueOf(product.getUrlImage()));
		}

		if (product.getUrls() == null) {
			map.put("urls", null);
		}
		else {
			map.put("urls", String.valueOf(product.getUrls()));
		}

		return map;
	}

	public static class ProductJSONParser extends BaseJSONParser<Product> {

		@Override
		protected Product createDTO() {
			return new Product();
		}

		@Override
		protected Product[] createDTOArray(int size) {
			return new Product[size];
		}

		@Override
		protected void setField(
			Product product, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "attachments")) {
				if (jsonParserFieldValue != null) {
					product.setAttachments(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AttachmentSerDes.toDTO((String)object)
						).toArray(
							size -> new Attachment[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "categories")) {
				if (jsonParserFieldValue != null) {
					product.setCategories(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CategorySerDes.toDTO((String)object)
						).toArray(
							size -> new Category[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					product.setCreateDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					product.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expando")) {
				if (jsonParserFieldValue != null) {
					product.setExpando(
						(Map)ProductSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					product.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "images")) {
				if (jsonParserFieldValue != null) {
					product.setImages(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AttachmentSerDes.toDTO((String)object)
						).toArray(
							size -> new Attachment[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "metaDescription")) {
				if (jsonParserFieldValue != null) {
					product.setMetaDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "metaKeyword")) {
				if (jsonParserFieldValue != null) {
					product.setMetaKeyword((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "metaTitle")) {
				if (jsonParserFieldValue != null) {
					product.setMetaTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					product.setModifiedDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "multipleOrderQuantity")) {

				if (jsonParserFieldValue != null) {
					product.setMultipleOrderQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					product.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "productConfiguration")) {

				if (jsonParserFieldValue != null) {
					product.setProductConfiguration(
						ProductConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productId")) {
				if (jsonParserFieldValue != null) {
					product.setProductId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productOptions")) {
				if (jsonParserFieldValue != null) {
					product.setProductOptions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ProductOptionSerDes.toDTO((String)object)
						).toArray(
							size -> new ProductOption[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "productSpecifications")) {

				if (jsonParserFieldValue != null) {
					product.setProductSpecifications(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ProductSpecificationSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ProductSpecification[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productType")) {
				if (jsonParserFieldValue != null) {
					product.setProductType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "relatedProducts")) {
				if (jsonParserFieldValue != null) {
					product.setRelatedProducts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RelatedProductSerDes.toDTO((String)object)
						).toArray(
							size -> new RelatedProduct[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shortDescription")) {
				if (jsonParserFieldValue != null) {
					product.setShortDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skus")) {
				if (jsonParserFieldValue != null) {
					product.setSkus(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> SkuSerDes.toDTO((String)object)
						).toArray(
							size -> new Sku[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "slug")) {
				if (jsonParserFieldValue != null) {
					product.setSlug((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "tags")) {
				if (jsonParserFieldValue != null) {
					product.setTags(toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "urlImage")) {
				if (jsonParserFieldValue != null) {
					product.setUrlImage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "urls")) {
				if (jsonParserFieldValue != null) {
					product.setUrls(
						(Map)ProductSerDes.toMap((String)jsonParserFieldValue));
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}