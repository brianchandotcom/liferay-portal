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

	public String getOSGiJaxRsName();

	public List<Schema> getSchemas();

	public String getTitle();

	public String getVersion();

	public interface Endpoint {

		public Http.Method getMethod();

		public String getPath();

		public Schema getRequestSchema();

		public Schema getResponseSchema();

		public Scope getScope();

		public enum Scope {

			COMPANY, GROUP

		}

	}

	public interface Property {

		public String getDescription();

		public String getName();

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

		public String getName();

		public List<Property> getProperties();

	}

}