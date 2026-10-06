/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {uuidv4} from 'frontend-js-web';

import {CriteriaNode} from '../../types';
import {isGroup} from './isGroup';

export function cloneNode(node: CriteriaNode): CriteriaNode {
	if (isGroup(node)) {
		return {
			...node,
			id: `group-${uuidv4()}`,
			items: node.items.map(cloneNode),
		};
	}

	return {...node, id: `rule-${uuidv4()}`};
}
