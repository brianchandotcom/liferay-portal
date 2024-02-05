/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.application;

import com.liferay.portal.kernel.util.Http;

import java.util.List;
import java.util.Objects;

/**
 * @author Alejandro Tardín
 */
public interface APIApplication {

	public String getBaseURL();

	public long getCompanyId();

	public String getDescription();

	public List<Endpoint> getEndpoints();

	public List<Schema> getSchemas();

	public String getTitle();

	public String getVersion();

	public interface Endpoint {

		public Filter getFilter();

		public Http.Method getMethod();

		public String getPath();

		public String getPathParameter();

		public Schema getRequestSchema();

		public Schema getResponseSchema();

		public RetrieveType getRetrieveType();

		public Scope getScope();

		public Sort getSort();

		public enum RetrieveType {

			COLLECTION("collection"), SINGLE_ELEMENT("singleElement");

			public static RetrieveType parse(String value) {
				for (RetrieveType retrieveType : RetrieveType.values()) {
					if (Objects.equals(retrieveType.getValue(), value)) {
						return retrieveType;
					}
				}

				throw new IllegalArgumentException("Invalid value " + value);
			}

			public String getValue() {
				return _value;
			}

			private RetrieveType(String value) {
				_value = value;
			}

			private final String _value;

		}

		public enum Scope {

			COMPANY("company"), SITE("site");

			public static Scope parse(String value) {
				for (Scope scope : Scope.values()) {
					if (Objects.equals(scope.getValue(), value)) {
						return scope;
					}
				}

				throw new IllegalArgumentException("Invalid value " + value);
			}

			public String getValue() {
				return _value;
			}

			private Scope(String value) {
				_value = value;
			}

			private final String _value;

		}

	}

	public interface Filter {

		public String getODataFilterString();

	}

	public interface Property {

		public String getDescription();

		public String getExternalReferenceCode();

		public String getName();

		public List<String> getObjectRelationshipNames();

		public List<Property> getProperties();

		public PropertyType getPropertyType();

		public String getSourceFieldName();

		public Type getType();

		public enum PropertyType {

			NORMAL("normal"), SINGLE_CONTAINER("singleContainer");

			public static PropertyType parse(String value) {
				for (PropertyType propertyType :
						Property.PropertyType.values()) {

					if (Objects.equals(propertyType.getValue(), value)) {
						return propertyType;
					}
				}

				throw new IllegalArgumentException("Invalid value " + value);
			}

			public String getValue() {
				return _value;
			}

			private PropertyType(String value) {
				_value = value;
			}

			private final String _value;

		}

		public enum Type {

			AGGREGATION, ATTACHMENT, BOOLEAN, DATE, DATE_TIME, DECIMAL, INTEGER,
			LONG_INTEGER, LONG_TEXT, MULTISELECT_PICKLIST, PICKLIST,
			PRECISION_DECIMAL, RICH_TEXT, SINGLE_CONTAINER, TEXT

		}

	}

	public interface Schema {

		public String getDescription();

		public String getExternalReferenceCode();

		public String getMainObjectDefinitionExternalReferenceCode();

		public String getName();

		public List<Property> getProperties();

	}

	public interface Sort {

		public String getODataSortString();

	}

}