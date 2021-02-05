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

const PAGINATION = {
	CHANGE: 'pagination_change',
	NEXT: 'pagination_next',
	PREVIOUS: 'pagination_previous',
};

const PAGE = {
	ADD: 'page_add',
	CHANGE: 'page_change',
	DELETE: 'page_delete',
	RESET: 'page_reset',
	SWAP: 'page_swap',
};

const RULE = {
	ADD: 'rule_add',
	CHANGE: 'rule_change',
	DELETE: 'rule_delete',
};

const SIDEBAR = {
	BLUR: 'sidebar_blur',
	CHANGES_CANCEL: 'sidebar_changes_cancel',
	EVALUATE: 'sidebar_evaluate',
};

export const EVENT_TYPES = {
	ELEMENT_SET_ADD: 'element_set_add',
	PAGE,
	PAGINATION,
	RULE,
	SIDEBAR,
	SUCCESS_PAGE: 'success_page',
};
