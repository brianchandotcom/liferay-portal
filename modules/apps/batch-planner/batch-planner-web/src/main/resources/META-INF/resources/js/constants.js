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

export const PROCESS_STARTED = 'STARTED';
export const PROCESS_COMPLETED = 'COMPLETED';
export const PROCESS_FAILED = 'FAILED';

export const POLL_INTERVAL = 1000;
export const EXPORT_FILE_NAME = 'Export.zip';
export const FILE_SCHEMA_EVENT = 'file-schema';
export const SCHEMA_SELECTED_EVENT = 'schema-selected';
export const TEMPLATE_SELECTED_EVENT = 'template-selected';

export const PARSE_FILE_CHUNK_SIZE = 64 * 1024;

export const HEADERS = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
});

export const HEADLESS_BATCH_ENGINE_URL = '/o/headless-batch-engine/v1.0';
export const HEADERS_BATCH_PLANNER_URL = '/o/batch-planner/v1.0';
