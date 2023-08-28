/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.application;

import com.liferay.portal.kernel.util.Http;

import java.util.List;

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

		public PathParameter getPathParameter();

		public Schema getRequestSchema();

		public Schema getResponseSchema();

		public RetrieveType getRetrieveType();

		public Scope getScope();

		public Sort getSort();

		public enum PathParameter {

			EXTERNAL_REFERENCE_CODE, ID, NONE

		}

		public enum RetrieveType {

			COLLECTION, SINGLE_ELEMENT

		}

		public enum Scope {

			COMPANY, GROUP

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

		public String getSourceFieldName();

		public Type getType();

		public enum Type {

			AGGREGATION, ATTACHMENT, BOOLEAN, DATE, DATE_TIME, DECIMAL, INTEGER,
			LONG_INTEGER, LONG_TEXT, MULTISELECT_PICKLIST, PICKLIST,
			PRECISION_DECIMAL, RICH_TEXT, TEXT

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