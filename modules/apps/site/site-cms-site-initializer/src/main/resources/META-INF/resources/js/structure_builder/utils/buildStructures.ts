/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectDefinition} from '../types/ObjectDefinition';
import {Structures} from '../types/Structure';
import buildStructure from './buildStructure';

export default function buildStructures(objectDefinitions: ObjectDefinition[]) {
	const structures: Structures = new Map();

	for (const objectDefinition of objectDefinitions) {
		const structure = buildStructure(objectDefinition);

		if (!structure || structure.status === 'draft') {
			continue;
		}

		structures.set(structure.erc, structure);
	}

	return structures;
}
