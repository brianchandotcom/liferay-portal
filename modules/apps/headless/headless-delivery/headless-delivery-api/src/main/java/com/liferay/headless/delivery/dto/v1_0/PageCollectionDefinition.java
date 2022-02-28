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

package com.liferay.headless.delivery.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Represents a definition of a Page Collection.",
	value = "PageCollectionDefinition"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "PageCollectionDefinition")
public class PageCollectionDefinition implements Serializable {

	public static PageCollectionDefinition toDTO(String json) {
		return ObjectMapperUtil.readValue(PageCollectionDefinition.class, json);
	}

	public static PageCollectionDefinition unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			PageCollectionDefinition.class, json);
	}

	@Schema
	@Valid
	public CollectionConfig getCollectionConfig() {
		return collectionConfig;
	}

	public void setCollectionConfig(CollectionConfig collectionConfig) {
		this.collectionConfig = collectionConfig;
	}

	@JsonIgnore
	public void setCollectionConfig(
		UnsafeSupplier<CollectionConfig, Exception>
			collectionConfigUnsafeSupplier) {

		try {
			collectionConfig = collectionConfigUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected CollectionConfig collectionConfig;

	@Schema(
		description = "Whether to show all items when pagination is disabled."
	)
	public Boolean getDisplayAllItems() {
		return displayAllItems;
	}

	public void setDisplayAllItems(Boolean displayAllItems) {
		this.displayAllItems = displayAllItems;
	}

	@JsonIgnore
	public void setDisplayAllItems(
		UnsafeSupplier<Boolean, Exception> displayAllItemsUnsafeSupplier) {

		try {
			displayAllItems = displayAllItemsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Whether to show all items when pagination is disabled."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean displayAllItems;

	@Schema(
		description = "Whether to show all pages when pagination is enabled."
	)
	public Boolean getDisplayAllPages() {
		return displayAllPages;
	}

	public void setDisplayAllPages(Boolean displayAllPages) {
		this.displayAllPages = displayAllPages;
	}

	@JsonIgnore
	public void setDisplayAllPages(
		UnsafeSupplier<Boolean, Exception> displayAllPagesUnsafeSupplier) {

		try {
			displayAllPages = displayAllPagesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Whether to show all pages when pagination is enabled."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean displayAllPages;

	@Schema(description = "The fragment style of the page collection.")
	@Valid
	public FragmentStyle getFragmentStyle() {
		return fragmentStyle;
	}

	public void setFragmentStyle(FragmentStyle fragmentStyle) {
		this.fragmentStyle = fragmentStyle;
	}

	@JsonIgnore
	public void setFragmentStyle(
		UnsafeSupplier<FragmentStyle, Exception> fragmentStyleUnsafeSupplier) {

		try {
			fragmentStyle = fragmentStyleUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The fragment style of the page collection.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FragmentStyle fragmentStyle;

	@Schema(description = "The fragment viewports of the page collection.")
	@Valid
	public FragmentViewport[] getFragmentViewports() {
		return fragmentViewports;
	}

	public void setFragmentViewports(FragmentViewport[] fragmentViewports) {
		this.fragmentViewports = fragmentViewports;
	}

	@JsonIgnore
	public void setFragmentViewports(
		UnsafeSupplier<FragmentViewport[], Exception>
			fragmentViewportsUnsafeSupplier) {

		try {
			fragmentViewports = fragmentViewportsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The fragment viewports of the page collection."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FragmentViewport[] fragmentViewports;

	@Schema(
		description = "The style of a list of items in the page collection."
	)
	public String getListItemStyle() {
		return listItemStyle;
	}

	public void setListItemStyle(String listItemStyle) {
		this.listItemStyle = listItemStyle;
	}

	@JsonIgnore
	public void setListItemStyle(
		UnsafeSupplier<String, Exception> listItemStyleUnsafeSupplier) {

		try {
			listItemStyle = listItemStyleUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The style of a list of items in the page collection."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String listItemStyle;

	@Schema(description = "The style of a list in the page collection.")
	public String getListStyle() {
		return listStyle;
	}

	public void setListStyle(String listStyle) {
		this.listStyle = listStyle;
	}

	@JsonIgnore
	public void setListStyle(
		UnsafeSupplier<String, Exception> listStyleUnsafeSupplier) {

		try {
			listStyle = listStyleUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The style of a list in the page collection.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String listStyle;

	@Schema(description = "The number of columns in the page collection.")
	public Integer getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(Integer numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	@JsonIgnore
	public void setNumberOfColumns(
		UnsafeSupplier<Integer, Exception> numberOfColumnsUnsafeSupplier) {

		try {
			numberOfColumns = numberOfColumnsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The number of columns in the page collection.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer numberOfColumns;

	@Schema(description = "The number of items in the page collection.")
	public Integer getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(Integer numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	@JsonIgnore
	public void setNumberOfItems(
		UnsafeSupplier<Integer, Exception> numberOfItemsUnsafeSupplier) {

		try {
			numberOfItems = numberOfItemsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The number of items in the page collection.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer numberOfItems;

	@Schema(
		description = "The number of items per page in the page collection."
	)
	public Integer getNumberOfItemsPerPage() {
		return numberOfItemsPerPage;
	}

	public void setNumberOfItemsPerPage(Integer numberOfItemsPerPage) {
		this.numberOfItemsPerPage = numberOfItemsPerPage;
	}

	@JsonIgnore
	public void setNumberOfItemsPerPage(
		UnsafeSupplier<Integer, Exception> numberOfItemsPerPageUnsafeSupplier) {

		try {
			numberOfItemsPerPage = numberOfItemsPerPageUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The number of items per page in the page collection."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer numberOfItemsPerPage;

	@Schema(
		description = "The maximum number of pages to show when pagination is enabled."
	)
	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	@JsonIgnore
	public void setNumberOfPages(
		UnsafeSupplier<Integer, Exception> numberOfPagesUnsafeSupplier) {

		try {
			numberOfPages = numberOfPagesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "The maximum number of pages to show when pagination is enabled."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer numberOfPages;

	@Schema(description = "The type of pagination.")
	@Valid
	public PaginationType getPaginationType() {
		return paginationType;
	}

	@JsonIgnore
	public String getPaginationTypeAsString() {
		if (paginationType == null) {
			return null;
		}

		return paginationType.toString();
	}

	public void setPaginationType(PaginationType paginationType) {
		this.paginationType = paginationType;
	}

	@JsonIgnore
	public void setPaginationType(
		UnsafeSupplier<PaginationType, Exception>
			paginationTypeUnsafeSupplier) {

		try {
			paginationType = paginationTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The type of pagination.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected PaginationType paginationType;

	@Schema(
		description = "Whether to show all items when pagination is enabled."
	)
	public Boolean getShowAllItems() {
		return showAllItems;
	}

	public void setShowAllItems(Boolean showAllItems) {
		this.showAllItems = showAllItems;
	}

	@JsonIgnore
	public void setShowAllItems(
		UnsafeSupplier<Boolean, Exception> showAllItemsUnsafeSupplier) {

		try {
			showAllItems = showAllItemsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(
		description = "Whether to show all items when pagination is enabled."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean showAllItems;

	@Schema(description = "The page collection's template key.")
	public String getTemplateKey() {
		return templateKey;
	}

	public void setTemplateKey(String templateKey) {
		this.templateKey = templateKey;
	}

	@JsonIgnore
	public void setTemplateKey(
		UnsafeSupplier<String, Exception> templateKeyUnsafeSupplier) {

		try {
			templateKey = templateKeyUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField(description = "The page collection's template key.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String templateKey;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageCollectionDefinition)) {
			return false;
		}

		PageCollectionDefinition pageCollectionDefinition =
			(PageCollectionDefinition)object;

		return Objects.equals(toString(), pageCollectionDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (collectionConfig != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collectionConfig\": ");

			sb.append(String.valueOf(collectionConfig));
		}

		if (displayAllItems != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayAllItems\": ");

			sb.append(displayAllItems);
		}

		if (displayAllPages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayAllPages\": ");

			sb.append(displayAllPages);
		}

		if (fragmentStyle != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentStyle\": ");

			sb.append(String.valueOf(fragmentStyle));
		}

		if (fragmentViewports != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentViewports\": ");

			sb.append("[");

			for (int i = 0; i < fragmentViewports.length; i++) {
				sb.append(String.valueOf(fragmentViewports[i]));

				if ((i + 1) < fragmentViewports.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (listItemStyle != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"listItemStyle\": ");

			sb.append("\"");

			sb.append(_escape(listItemStyle));

			sb.append("\"");
		}

		if (listStyle != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"listStyle\": ");

			sb.append("\"");

			sb.append(_escape(listStyle));

			sb.append("\"");
		}

		if (numberOfColumns != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfColumns\": ");

			sb.append(numberOfColumns);
		}

		if (numberOfItems != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfItems\": ");

			sb.append(numberOfItems);
		}

		if (numberOfItemsPerPage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfItemsPerPage\": ");

			sb.append(numberOfItemsPerPage);
		}

		if (numberOfPages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfPages\": ");

			sb.append(numberOfPages);
		}

		if (paginationType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paginationType\": ");

			sb.append("\"");

			sb.append(paginationType);

			sb.append("\"");
		}

		if (showAllItems != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showAllItems\": ");

			sb.append(showAllItems);
		}

		if (templateKey != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"templateKey\": ");

			sb.append("\"");

			sb.append(_escape(templateKey));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.delivery.dto.v1_0.PageCollectionDefinition",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("PaginationType")
	public static enum PaginationType {

		NONE("None"), NUMERIC("Numeric"), REGULAR("Regular"), SIMPLE("Simple");

		@JsonCreator
		public static PaginationType create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (PaginationType paginationType : values()) {
				if (Objects.equals(paginationType.getValue(), value)) {
					return paginationType;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private PaginationType(String value) {
			_value = value;
		}

		private final String _value;

	}

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

}