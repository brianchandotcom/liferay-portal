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

package com.liferay.batch.planner.constants;

import java.util.Objects;

/**
 * @author Igor Beslic
 */
public class BatchPlannerConstants {

	public static final String EXTERNAL_TYPE_CSV = "CSV";

	public static final String EXTERNAL_TYPE_JSON = "JSON";

	public static final String EXTERNAL_TYPE_JSONL = "JSONL";

	public static final String EXTERNAL_TYPE_TXT = "TXT";

	public static final String EXTERNAL_TYPE_XLS = "XLS";

	public static final String EXTERNAL_TYPE_XLSX = "XLSX";

	public static final String EXTERNAL_TYPE_XML = "XML";

	public static final String[] EXTERNAL_TYPES = {
		EXTERNAL_TYPE_CSV, EXTERNAL_TYPE_JSON, EXTERNAL_TYPE_JSONL,
		EXTERNAL_TYPE_TXT, EXTERNAL_TYPE_XLS, EXTERNAL_TYPE_XLSX,
		EXTERNAL_TYPE_XML
	};

	public static boolean isExternalType(String value) {
		for (String externalType : EXTERNAL_TYPES) {
			if (Objects.equals(value, externalType)) {
				return true;
			}
		}

		return false;
	}

}