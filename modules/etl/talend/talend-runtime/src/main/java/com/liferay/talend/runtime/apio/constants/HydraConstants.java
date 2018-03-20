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

package com.liferay.talend.runtime.apio.constants;

/**
 * Hydra Vocabulary for Hypermedia-Driven Web APIs, see <a
 * href="https://www.w3.org/ns/hydra/core">Hydra Core Vocabulary</a>
 *
 * @author Zoltán Takács
 */
public interface HydraConstants {

	public interface FieldNames {

		public static final String DESCRIPTION = "description";

		public static final String EXPECTS = "expects";

		public static final String FIRST = "first";

		public static final String LAST = "last";

		public static final String MEMBER = "member";

		public static final String METHOD = "method";

		public static final String NEXT = "next";

		public static final String NUMBER_OF_ITEMS = "numberOfItems";

		public static final String OPERATION = "operation";

		public static final String PREVIOUS = "previous";

		public static final String PROPERTY = "property";

		public static final String READABLE = "readable";

		public static final String REQUIRED = "required";

		public static final String SUPPORTED_PROPERTY = "supportedProperty";

		public static final String TITLE = "title";

		public static final String TOTAL_ITEMS = "totalItems";

		public static final String VIEW = "view";

		public static final String WRITEABLE = "writeable";

	}

	public interface FieldTypes {

		public static final String API_DOCUMENTATION = "ApiDocumentation";

		public static final String CLASS = "Class";

		public static final String COLLECTION = "Collection";

		public static final String PARTIAL_COLLECTION_VIEW =
			"PartialCollectionView";

		public static final String SUPPORTED_PROPERTY = "SupportedProperty";

	}

}