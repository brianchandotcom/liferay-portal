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

export const CSV_FORMAT = 'csv';
export const EXPORT_FILE_NAME = 'Export.zip';
export const FILE_EXTENSION_INPUT_PARTIAL_NAME = 'externalType';
export const FILE_SCHEMA_EVENT = 'file-schema';

export const HEADERS = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
});

export const HEADLESS_BATCH_PLANNER_URL = '/o/batch-planner/v1.0';
export const HEADLESS_BATCH_ENGINE_URL = '/o/headless-batch-engine/v1.0';
export const HEADLESS_ENDPOINT_POLICY_NAME = 'headlessEndpoint';
export const JSON_FORMAT = 'json';
export const JSONL_FORMAT = 'jsonl';
export const NULL_TEMPLATE_VALUE = '';
export const PARSE_FILE_CHUNK_SIZE = 64 * 1024;
export const POLL_INTERVAL = 1000;
export const PROCESS_COMPLETED = 'COMPLETED';
export const PROCESS_FAILED = 'FAILED';
export const PROCESS_STARTED = 'STARTED';

export const SCHEMA_SELECTED_EVENT = 'ie-schema-selected';
export const TEMPLATE_CREATED_EVENT = 'ie-template-created';
export const TEMPLATE_SELECTED_EVENT = 'ie-template-selected';
export const TEMPLATE_SOILED_EVENT = 'ie-template-soiled';

export const IMPORT_FILE_FORMATS = [CSV_FORMAT, JSON_FORMAT, JSONL_FORMAT];
