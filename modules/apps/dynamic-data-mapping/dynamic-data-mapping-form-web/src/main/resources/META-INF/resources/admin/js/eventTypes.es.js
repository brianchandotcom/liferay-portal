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

const RULE = {
	ADD: 'rule_add',
	CHANGE: 'rule_change',
	DELETE: 'rule_delete',
	EDIT: 'rule_edit',
};

const SIDEBAR = {
	CHANGE_FIELD_TYPE: 'sidebar_change_field_type',
	CHANGES_CANCEL: 'sidebar_changes_cancel',
};

const FORM_INFO = {
	DESCRIPTION_CHANGE: 'form_info_description_change',
	LANGUAGE_DELETE: 'form_info_language_delete',
	NAME_CHANGE: 'form_info_name_change',
};

export const EVENT_TYPES = {
	ELEMENT_SET_ADD: 'element_set_add',
	FORM_INFO,
	RULE,
	SIDEBAR,
};
