/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {uuidv4} from 'frontend-js-web';

import {Group, SerializedGroup} from '../../types';
import {isSerializedGroup} from './isSerializedGroup';

export function parseGroup(serialized: SerializedGroup): Group {
	return {
		conjunction: serialized.conjunction || 'AND',
		id: `group-${uuidv4()}`,
		items: (serialized.rules ?? [])
			.filter(
				(node) => isSerializedGroup(node) || Boolean(node.attribute)
			)
			.map((node) =>
				isSerializedGroup(node)
					? parseGroup(node)
					: {
							attribute: node.attribute,
							id: `rule-${uuidv4()}`,
							operator: node.operator,
							value: String(node.value ?? ''),
						}
			),
	};
}
