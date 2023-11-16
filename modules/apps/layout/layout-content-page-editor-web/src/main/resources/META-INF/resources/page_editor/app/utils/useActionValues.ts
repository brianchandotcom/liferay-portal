/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ACTION_ITEMS,
	ACTION_TYPE_ITEMS,
	Action,
} from '../../plugins/page_rules/components/Action';

type Item = {label: string; value: string};

type Props = {
	actions: Action[];
	items: Item[];
};

export default function useActionValues({actions, items}: Props) {
	return actions.map((_action, index) => {
		const action = getAction(_action.action);
		const item = getItem(items, _action.itemId);
		const prefix = getPrefix(index);
		const type = getType(_action.type);

		const description = getDescription(action, item, prefix, type);

		return {
			action,
			description,
			id: _action.id,
			item,
			prefix,
			type,
		};
	});
}

function getAction(action: Action['action']) {
	if (!action) {
		return '';
	}

	return ACTION_ITEMS.find(({value}) => value === action)?.label;
}

function getDescription(
	action?: string,
	item?: string,
	prefix?: string,
	type?: string
) {
	return [prefix, type, action, item].filter((item) => item).join(' ');
}

function getItem(items: Item[], itemId?: Action['itemId']) {
	if (!itemId) {
		return '';
	}

	return items.find(({value}) => value === itemId)?.label;
}

function getPrefix(index: number) {
	return index > 0 ? Liferay.Language.get('and') : '';
}

function getType(type?: Action['type']) {
	if (!type) {
		return '';
	}

	return ACTION_TYPE_ITEMS.find(({value}) => value === type)?.label;
}
