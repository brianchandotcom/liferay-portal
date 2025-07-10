/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Field} from '../utils/field';
import {Uuid} from './Uuid';

type Status = 'new' | 'draft' | 'published';

type Spaces = 'all' | string[];

export type ReferencedStructure = {
	children: Map<Uuid, StructureChild>;
	editURL: string;
	erc: string;
	label: Liferay.Language.LocalizedValue<string>;
	name: string;
	parent: Uuid;
	relationshipName: string;
	spaces: Spaces;
	type: 'referenced-structure';
	uuid: Uuid;
};

export type RepeatableGroup = {
	children: Map<Uuid, StructureChild>;
	erc: string;
	label: Liferay.Language.LocalizedValue<string>;
	name: string;
	parent: Uuid;
	relationshipName: string;
	type: 'repeatable-group';
	uuid: Uuid;
};

export type StructureChild = Field | ReferencedStructure | RepeatableGroup;

export type Structure = {
	children: Map<Uuid, StructureChild>;
	erc: string;
	id: number | null;
	label: Liferay.Language.LocalizedValue<string>;
	name: string;
	spaces: Spaces;
	status: Status;
	type?: 'L_CMS_CONTENT_STRUCTURES' | 'L_CMS_FILE_TYPES';
	uuid: Uuid;
};

export type Structures = Map<Structure['erc'], Structure>;
