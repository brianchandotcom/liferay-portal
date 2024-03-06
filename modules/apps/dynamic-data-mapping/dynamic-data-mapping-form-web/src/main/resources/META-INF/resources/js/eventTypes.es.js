/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const RULE = {
	ADD: 'rule_add',
	CHANGE: 'rule_change',
	DELETE: 'rule_delete',
	EDIT: 'rule_edit',
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
};
