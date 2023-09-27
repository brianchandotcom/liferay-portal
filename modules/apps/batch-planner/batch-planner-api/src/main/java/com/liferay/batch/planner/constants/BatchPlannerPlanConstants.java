/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.constants;

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Igor Beslic
 */
public class BatchPlannerPlanConstants {

	public static final String EXTERNAL_TYPE_CSV = "CSV";

	public static final String EXTERNAL_TYPE_JSON = "JSON";

	public static final String EXTERNAL_TYPE_JSONL = "JSONL";

	public static final String EXTERNAL_TYPE_JSONT = "JSONT";

	public static final String EXTERNAL_TYPE_TXT = "TXT";

	public static final String EXTERNAL_TYPE_XLS = "XLS";

	public static final String EXTERNAL_TYPE_XLSX = "XLSX";

	public static final String EXTERNAL_TYPE_XML = "XML";

	public static final String[] EXTERNAL_TYPES = {
		EXTERNAL_TYPE_CSV, EXTERNAL_TYPE_JSON, EXTERNAL_TYPE_JSONL,
		EXTERNAL_TYPE_JSONT, EXTERNAL_TYPE_TXT, EXTERNAL_TYPE_XLS,
		EXTERNAL_TYPE_XLSX, EXTERNAL_TYPE_XML
	};

	public static final String LABEL_COMPLETED = "completed";

	public static final String LABEL_FAILED = "failed";

	public static final String LABEL_INACTIVE = "inactive";

	public static final String LABEL_PARTIALLY_COMPLETED =
		"partially-completed";

	public static final String LABEL_QUEUED = "queued";

	public static final String LABEL_RUNNING = "running";

	public static final int STATUS_COMPLETED = 3;

	public static final int STATUS_FAILED = 4;

	public static final int STATUS_INACTIVE = 0;

	public static final int STATUS_PARTIALLY_COMPLETED = 5;

	public static final int STATUS_QUEUED = 1;

	public static final int STATUS_RUNNING = 2;

	public static String getContentType(String externalType) {
		return _contentTypes.get(externalType);
	}

	public static int getStatus(
		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus) {

		if (batchEngineTaskExecuteStatus ==
				BatchEngineTaskExecuteStatus.COMPLETED) {

			return STATUS_COMPLETED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.FAILED) {

			return STATUS_FAILED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.INITIAL) {

			return STATUS_QUEUED;
		}
		else if (batchEngineTaskExecuteStatus ==
					BatchEngineTaskExecuteStatus.STARTED) {

			return STATUS_RUNNING;
		}

		return STATUS_INACTIVE;
	}

	public static String getStatusCssClass(int status) {
		if (status == STATUS_COMPLETED) {
			return "text-success";
		}
		else if (status == STATUS_FAILED) {
			return "text-danger";
		}
		else if ((status == STATUS_QUEUED) || (status == STATUS_RUNNING)) {
			return "text-info";
		}

		return "text-warning";
	}

	public static String getStatusLabel(int status) {
		if (status == STATUS_COMPLETED) {
			return LABEL_COMPLETED;
		}
		else if (status == STATUS_FAILED) {
			return LABEL_FAILED;
		}
		else if (status == STATUS_PARTIALLY_COMPLETED) {
			return LABEL_PARTIALLY_COMPLETED;
		}
		else if (status == STATUS_QUEUED) {
			return LABEL_QUEUED;
		}
		else if (status == STATUS_RUNNING) {
			return LABEL_RUNNING;
		}

		return LABEL_INACTIVE;
	}

	private static final Map<String, String> _contentTypes = HashMapBuilder.put(
		EXTERNAL_TYPE_CSV, ContentTypes.TEXT_CSV
	).put(
		EXTERNAL_TYPE_JSON, ContentTypes.APPLICATION_JSON
	).put(
		EXTERNAL_TYPE_JSONL, "application/x-ndjson"
	).put(
		EXTERNAL_TYPE_JSONT, ContentTypes.APPLICATION_JSON
	).put(
		EXTERNAL_TYPE_TXT, ContentTypes.TEXT_PLAIN
	).put(
		EXTERNAL_TYPE_XLS, EXTERNAL_TYPE_XLS
	).put(
		EXTERNAL_TYPE_XLSX, EXTERNAL_TYPE_XLSX
	).put(
		EXTERNAL_TYPE_XML, ContentTypes.TEXT_XML
	).build();

}