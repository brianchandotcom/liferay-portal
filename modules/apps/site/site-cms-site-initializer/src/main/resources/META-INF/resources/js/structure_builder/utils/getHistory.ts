/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../contexts/StateContext';
import {Structure} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import findChild from './findChild';

export default function getHistory({
	deletedChildrenUuids,
	initialHistory,
	publishedChildren,
	structure,
}: {
	deletedChildrenUuids: Set<Uuid>;
	initialHistory: State['history'];
	publishedChildren: State['publishedChildren'];
	structure: Structure;
}) {
	let nextHistory = {...initialHistory};

	for (const deletedChildUuid of deletedChildrenUuids) {
		const child = findChild({
			root: structure,
			uuid: deletedChildUuid,
		});

		if (!child) {
			continue;
		}

		if (publishedChildren.has(deletedChildUuid)) {
			nextHistory = {
				...nextHistory,
				deletedChildren: [...nextHistory.deletedChildren, child],
			};

			if (child.type === 'related-content' && !child.multiselection) {
				nextHistory = {
					...nextHistory,
					deletedRelationships: [
						...nextHistory.deletedRelationships,
						{
							relationshipERC: child.erc,
							structureERC: child.relatedStructureERC,
						},
					],
				};
			}

			if (child.type === 'repeatable-group') {
				nextHistory = {
					...nextHistory,
					deletedGroupERCs: [
						...nextHistory.deletedGroupERCs,
						child.erc,
					],
				};
			}
		}
	}

	return nextHistory;
}
