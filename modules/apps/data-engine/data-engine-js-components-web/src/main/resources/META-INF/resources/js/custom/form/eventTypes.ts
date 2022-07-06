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

const FORM_BUILDER = {
	FOCUSED_FIELD: {
		CHANGE: 'form_builder_focused_field_change',
	},
	PAGES: {
		UPDATE: 'form_builder_pages_update',
	},
};

const OBJECT_FIELDS = {
	ADD: 'object_fields_add',
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
	OBJECT_FIELDS,
	PAGE,
	PAGINATION,
	RULES,
	SUCCESS_PAGE: 'success_page',
};
