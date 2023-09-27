/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const FORM_BUILDER = {
	FOCUSED_FIELD: {
		CHANGE: 'form_builder_focused_field_change',
	},
	PAGES: {
		UPDATE: 'form_builder_pages_update',
	},
};

const FORM_VIEW = {
	REPEATABLE_FIELD: {
		CHANGE_ORDER: 'form_view_repeatable_field_change_order',
	},
};

const OBJECT = {
	FIELDS_CHANGE: 'object_fields_change',
	RELATIONSHIPS_CHANGE: 'object_relationships_change',
};

const PAGE = {
	ADD: 'page_add',
	DELETE: 'page_delete',
	DESCRIPTION_CHANGE: 'page_description_change',
	RESET: 'page_reset',
	SWAP: 'page_swap',
	TITLE_CHANGE: 'page_title_change',
};

const PAGINATION = {
	CHANGE: 'pagination_change',
	NEXT: 'pagination_next',
	PREVIOUS: 'pagination_previous',
};

const RULES = {
	UPDATE: 'rules_update',
};

export const EVENT_TYPES = {
	FORM_BUILDER,
	FORM_VIEW,
	OBJECT,
	PAGE,
	PAGINATION,
	RULES,
	SUCCESS_PAGE: 'success_page',
};
