/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ACTION_TYPE_ITEMS} from '../../plugins/page_rules/components/Action';
import {Action} from '../../types/Rule';

type Item = {label: string; value: string};

type Props = {
	actions: Action[];
	items: Item[];
};

export type ActionValues = {
	description: string;
	id: string;
	item: string | undefined;
	itemId?: string;
	prefix: string;
	type: string | undefined;
};

export default function useActionValues({
	actions,
	items,
}: Props): ActionValues[] {
	return actions.map((_action, index) => {
		const item = getItem(items, _action.itemId);
		const prefix = getPrefix(index);
		const type = getType(_action.type);

		const description = getDescription(item, prefix, type);

		return {
			description,
			id: _action.id,
			item,
			prefix,
			type,
		};
	});
}

function getDescription(item?: string, prefix?: string, type?: string) {
	return [prefix, type, Liferay.Language.get('fragment'), item]
		.filter((item) => item)
		.join(' ');
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

	return ACTION_TYPE_ITEMS[type].label;
}
