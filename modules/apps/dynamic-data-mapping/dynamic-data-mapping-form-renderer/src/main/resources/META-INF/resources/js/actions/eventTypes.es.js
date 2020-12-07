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

const PUBLIC_EVENTS = {
	CHANGE_ACTIVE_PAGE: 'activePageUpdated',
	FIELD_BLUR: 'fieldBlurred',
	FIELD_CHANGE: 'fieldEdited',
	FIELD_CLICKED: 'fieldClicked',
	FIELD_DELETED: 'fieldDeleted',
	FIELD_DROP: 'fieldDrop',
	FIELD_DUPLICATED: 'fieldDuplicated',
	FIELD_EVALUATED: 'evaluated',
	FIELD_EVALUATION_ERROR: 'evaluationError',
	FIELD_FOCUS: 'fieldFocused',
	FIELD_HOVERED: 'fieldHovered',
	FIELD_REMOVED: 'fieldRemoved',
	FIELD_REPEATED: 'fieldRepeated',
	PAGE_ADDED: 'pageAdded',
	PAGE_DELETED: 'pageDeleted',
	PAGE_RESET: 'pageReset',
	PAGE_SWAPPED: 'pagesSwapped',
	PAGE_UPDATED: 'pagesUpdated',
	PAGE_VALIDATION_FAILED: 'pageValidationFailed',
	SUCCESS_CHANGED: 'successPageChanged',
};

const PRIVATE_EVENTS = {
	ALL: 'all',
	UPDATE_DATA_RECORD_VALUES: 'update_data_record_values',
	UPDATE_PAGES: 'update_pages',
};

export const EVENT_TYPES = {
	...PUBLIC_EVENTS,
	...PRIVATE_EVENTS,
};
