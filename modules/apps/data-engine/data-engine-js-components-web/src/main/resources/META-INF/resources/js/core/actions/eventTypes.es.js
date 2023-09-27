/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const LEGACY_EVENTS = {
	FIELD_EVALUATION_ERROR: 'evaluationError',
};

const PAGE = {
	CHANGE: 'page_change',
	UPDATE: 'pages_update',
	VALIDATION_FAILED: 'page_validation_failed',
};

const FIELD = {
	ADD: 'field_add',
	BLUR: 'field_blur',
	CHANGE: 'field_change',
	CLICK: 'field_click',
	DELETE: 'field_delete',
	DUPLICATE: 'field_duplicate',
	EVALUATE: 'field_evaluate',
	FOCUS: 'field_focus',
	HOVER: 'field_hover',
	REMOVED: 'field_removed',
	REPEATED: 'field_repeated',
};

const DATA_LAYOUT = {
	NAME: 'data_layout_name',
};

const DND = {
	MOVE: 'field_move',
	RESIZE: 'field_resize',
};

const SECTION = {
	ADD: 'section_add',
};

const FIELD_SET = {
	ADD: 'fieldset_add',
};

const LANGUAGE = {
	ADD: 'language_add',
	CHANGE: 'language_change',
	DELETE: 'language_delete',
	UPDATE: 'language_update',
};

export const EVENT_TYPES = {
	...LEGACY_EVENTS,
	DATA_LAYOUT,
	DND,
	FIELD,
	FIELD_SET,
	LANGUAGE,
	PAGE,
	SECTION,
};
