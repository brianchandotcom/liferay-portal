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

const DATA_DEFINITION = {
	ADD: 'data_definition_add',
	CHANGE: 'data_definition_change',
	DELETE: 'data_definition_delete',
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
};

/**
 * Event mapping for compatibility with events for
 * the LayoutProvider component.
 */
export const MAPPED_EVENT_TYPES = {
	[DND.MOVE]: 'fieldMoved',
	[DND.RESIZE]: 'columnResized',
	[FIELD_SET.ADD]: 'fieldSetAdded',
	[FIELD.ADD]: 'fieldAdded',
	[FIELD.BLUR]: 'fieldBlurred',
	[FIELD.CHANGE]: 'fieldEdited',
	[FIELD.CLICK]: 'fieldClicked',
	[FIELD.DELETE]: 'fieldDeleted',
	[FIELD.DUPLICATE]: 'fieldDuplicated',
	[FIELD.EVALUATE]: 'focusedFieldEvaluationEnded',
	[FIELD.HOVER]: 'fieldHovered',
	[PAGE.VALIDATION_FAILED]: 'pageValidationFailed',
	[SECTION.ADD]: 'sectionAdded',
};

export const EVENT_TYPES = {
	...LEGACY_EVENTS,
	DATA_DEFINITION,
	DATA_LAYOUT,
	DND,
	FIELD,
	FIELD_SET,
	LANGUAGE,
	PAGE,
	SECTION,
};
