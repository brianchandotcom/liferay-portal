/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../contexts/StateContext';
import {ObjectDefinition} from '../types/ObjectDefinition';
import buildStructure from './buildStructure';

export default function buildState(
	objectDefinition: ObjectDefinition
): State | null {
	if (!objectDefinition) {
		return null;
	}

	const structure = buildStructure(objectDefinition);

	return {
		error: null,
		history: {
			deletedFields: false,
		},
		invalids: new Map(),
		publishedFields:
			structure.status === 'published'
				? new Set(structure.fields.keys())
				: new Set(),
		selection: [],
		structure,
		unsavedChanges: false,
	};
}
