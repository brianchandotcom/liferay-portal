/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const CSV_ENCLOSING_CHARACTERS = ['', '"', "'"];
export const CSV_FORMAT = 'csv';
export const EXPORT_FILE_NAME = 'Export.zip';
export const FILE_EXTENSION_INPUT_PARTIAL_NAME = 'externalType';
export const FILE_SCHEMA_EVENT = 'file-schema';

export const HEADERS = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
});

export const CREATE_STRATEGIES = [
	{
		default: false,
		label: Liferay.Language.get('only-add-new-records'),
		name: 'INSERT',
	},
	{
		default: false,
		label: Liferay.Language.get('only-update-records'),
		name: 'UPDATE',
	},
	{
		default: true,
		label: Liferay.Language.get('add-or-update-records'),
		name: 'UPSERT',
	},
];
export const UPDATE_STRATEGIES = [
	{
		default: false,
		label: Liferay.Language.get('update-changed-record-fields'),
		name: 'PARTIAL_UPDATE',
	},
	{
		default: true,
		label: Liferay.Language.get('overwrite-records'),
		name: 'UPDATE',
	},
];
export const HEADLESS_BATCH_PLANNER_URL = '/o/batch-planner/v1.0';
export const HEADLESS_BATCH_ENGINE_URL = '/o/headless-batch-engine/v1.0';
export const JSON_FORMAT = 'json';
export const JSONL_FORMAT = 'jsonl';
export const NULL_TEMPLATE_VALUE = '';
export const PARSE_FILE_CHUNK_SIZE = 64 * 1024;
export const POLL_INTERVAL = 1000;
export const PROCESS_COMPLETED = 'COMPLETED';
export const PROCESS_FAILED = 'FAILED';
export const PROCESS_STARTED = 'STARTED';

export const SCHEMA_SELECTED_EVENT = 'ie-schema-selected';
export const IMPORT_STRATEGY_SELECTED_EVENT = 'ie-import-strategy-selected';
export const TEMPLATE_CREATED_EVENT = 'ie-template-created';
export const TEMPLATE_SELECTED_EVENT = 'ie-template-selected';
export const TEMPLATE_SOILED_EVENT = 'ie-template-soiled';

export const IMPORT_FILE_FORMATS = [CSV_FORMAT, JSON_FORMAT, JSONL_FORMAT];
